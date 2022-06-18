package com.cp.daoImplMYSQL;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.ConnectionDAOforEJB;
import com.connect.PrepStmtObject;
import com.cp.dao.EligibilityCalculationProcessDao;
import com.cp.vo.CustomerDetailsVo;
import com.cp.vo.FundFlowDownloadUploadVo;
import com.cp.vo.PushDataInTargetSheetVo;
import com.cp.vo.UnderwritingDocUploadVo;

public class EligibilityCalculationProcessDaoImpl implements EligibilityCalculationProcessDao{
	private static final Logger logger = Logger.getLogger(EligibilityCalculationProcessDaoImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	@Override
	public ArrayList getCustomerDemographicsData(String dealId,
			String customerId) {
		ArrayList list = new ArrayList();
		try{
		String str = "select * from com_ocr_dtl where deal_id='" + dealId+ "'";
		logger.info("Eligibility Query:    " + str.toString());
		 list  = ConnectionDAO.sqlSelect(str);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public ArrayList getSystemInputData$CustDetailsIndv(UnderwritingDocUploadVo vo,String qry) {
		logger.info("getSystemInputData$CustDetailsIndv---------");
		ArrayList<CustomerDetailsVo> voList = new ArrayList<CustomerDetailsVo>();
		CustomerDetailsVo custVo = null;
		StringBuffer buffer = new StringBuffer();
		ArrayList mainList = null;
		buffer.append("select "+qry
				+ " from cr_deal_customer_m m"
				+ " left join  cr_deal_customer_role r on r.deal_customer_id = m.customer_id  "
				+ " left join com_industry_m i on i.INDUSTRY_ID = m.CUSTOMER_INDUSTRY "
				+ " left join com_sub_industry_m si on si.SUB_INDUSTRY_ID = m.CUSTOMER_SUB_INDUSTRY "
				+ " left join generic_master g on g.generic_key = m.CUSTOMER_PROFILE"
				+ " left join cr_financial_data_dtl f on f.deal_id = r.deal_id  and f.deal_customer_id = m.customer_id and f.VERIFICATION_METHOD is not null" 
				+ " LEFT JOIN GENERIC_MASTER G1 ON G1.GENERIC_KEY='CUST_BUS_SEGMENT' AND G1.VALUE = CUSTOMER_BUSINESS_SEGMENT"
				+ " LEFT JOIN GENERIC_MASTER G2 ON G2.GENERIC_KEY='CUST_CONSTITUTION' AND G2.VALUE = CUSTOMER_CONSTITUTION and g2.parent_value ='CORP' "
				+ " LEFT JOIN GENERIC_MASTER G3 ON G3.GENERIC_KEY='NATURE_OF_BUSINESS' AND G3.VALUE = NATURE_OF_BUSINESS " );
		if(! CommonFunction.checkNull(vo.getCaseId()).equalsIgnoreCase(""))
			buffer.append(" where r.deal_id = '"+vo.getCaseId()+"'");
		
		buffer.append(" group by customer_id");
		logger.info("query -------------"+buffer.toString());
			try {
				 mainList = ConnectionDAOforEJB.sqlSelect(buffer.toString());
						
			} catch (Exception e) {
				e.printStackTrace();
			}
		return mainList;
	}
	@Override
	public ArrayList getSystemInputData$CustDetailsCorp(
			UnderwritingDocUploadVo vo, String qry) {
		logger.info("getSystemInputData$CustDetailsCorp---------");
		ArrayList<CustomerDetailsVo> voList = new ArrayList<CustomerDetailsVo>();
		CustomerDetailsVo custVo = null;
		StringBuffer buffer = new StringBuffer();
		ArrayList mainList = null;
		buffer.append("select "+qry
				+ " from cr_deal_customer_m m"
				+ " left join  cr_deal_customer_role r on r.deal_customer_id = m.customer_id and m.customer_type = 'C' "
				+ " left join com_industry_m i on i.INDUSTRY_ID = m.CUSTOMER_INDUSTRY "
				+ " left join com_sub_industry_m si on si.SUB_INDUSTRY_ID = m.CUSTOMER_SUB_INDUSTRY "
				+ " LEFT JOIN GENERIC_MASTER G ON G.VALUE = M.CUSTOMER_CATEGORY AND GENERIC_KEY = 'CUST_CATEGORY'"
				+ " LEFT JOIN GENERIC_MASTER G1 ON G1.GENERIC_KEY='CUST_BUS_SEGMENT' AND G1.VALUE = CUSTOMER_BUSINESS_SEGMENT"
				+ " LEFT JOIN GENERIC_MASTER G2 ON G2.GENERIC_KEY='CUST_CONSTITUTION' AND G2.VALUE = CUSTOMER_CONSTITUTION and g2.parent_value ='CORP' "
				+ " LEFT JOIN GENERIC_MASTER G3 ON G3.GENERIC_KEY='NATURE_OF_BUSINESS' AND G3.VALUE = NATURE_OF_BUSINESS "  );
		
		if(! CommonFunction.checkNull(vo.getCaseId()).equalsIgnoreCase(""))
			buffer.append(" where r.deal_id = '"+vo.getCaseId()+"'");
		
		logger.info("query -------------"+buffer.toString());
			try {
				 mainList = ConnectionDAOforEJB.sqlSelect(buffer.toString());
						
			} catch (Exception e) {
				e.printStackTrace();
			}
		return mainList;
	}
	@Override
	public ArrayList getSystemInputData$CustDemographic(
			UnderwritingDocUploadVo vo, String qry) {
		logger.info("getSystemInputData$CustDemographic--------");
		StringBuffer buffer = new StringBuffer();
		ArrayList mainList = null;
		buffer.append("select "+qry+ " from cr_deal_dtl d "
				+ " left join cr_product_m p on p.product_id =d.product "
				+ " left join cr_scheme_m s on s.scheme_id = d.scheme");
		
		if(! CommonFunction.checkNull(vo.getCaseId()).equalsIgnoreCase(""))
			buffer.append(" where deal_id = '"+vo.getCaseId()+"'");
		
		logger.info("query -------------"+buffer.toString());
			try {
				 mainList = ConnectionDAOforEJB.sqlSelect(buffer.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		return mainList;
	}
	@Override
	public ArrayList getSystemInputData$Obligation(UnderwritingDocUploadVo vo,
			String qry) {
		logger.info("getSystemInputData$Obligation--------");
		StringBuffer buffer = new StringBuffer();
		StringBuffer notCLFBuffer = new StringBuffer();
		ArrayList mainList = new ArrayList();
		ArrayList cflList = null;
		ArrayList notCLFList = null;
		logger.info("vo.getProductId()-------"+vo.getProductId());
		/*buffer.append("select "+qry+ " from "
				+ " CR_OBLIGATION_ANALYSIS_DTL o "
				+ " LEFT JOIN CR_deal_CUSTOMER_M M ON O.deal_CUSTOMER_ID = M.CUSTOMER_ID"
				+ " LEFT JOIN CR_deal_CUSTOMER_ROLE R ON M.CUSTOMER_ID = R.deal_CUSTOMER_ID"
				+ " LEFT JOIN  GENERIC_MASTER G ON G.GENERIC_KEY='OBLIGATION_TYPE' AND G.VALUE = OBLIGATION_TYPE"
				+ " LEFT JOIN  GENERIC_MASTER G1 ON G1.GENERIC_KEY='TYPE_OF_LOAN' AND G.VALUE = LOAN_TYPE");*/
		if(!CommonFunction.checkNull(qry).equalsIgnoreCase("Obligation & RTR- BIL") || !vo.getProductId().equalsIgnoreCase("BIL"))
		{
		buffer.append("select @a:=@a+1 as a,b.CUSTOMER_NAME,INSTITUTION_NAME,EMI_PAID_FROM,OBLIGATION_TYPE,FINANCED_AMOUNT,EMI,LOAN_TENURE,EMI_PAID,"
				+ "OUTSTANDING_AMOUNT,EMI_BOUNCED,EMI_PENDING, EMI_RECEIVED_DATE "
				+ " from cr_obligation_analysis_dtl a "
				+ " join cr_deal_customer_m b on  a.deal_CUSTOMER_ID=b.CUSTOMER_ID,  (select @a:=0) as a  ");
		
		notCLFBuffer.append("select @a:=@a+1 as a,b.CUSTOMER_NAME,INSTITUTION_NAME,EMI_PAID_FROM,OBLIGATION_TYPE,FINANCED_AMOUNT,EMI,LOAN_TENURE,EMI_PAID,"
				+ "OUTSTANDING_AMOUNT,EMI_BOUNCED,EMI_PENDING, EMI_RECEIVED_DATE "
				+ " from cr_obligation_analysis_dtl a "
				+ " join cr_deal_customer_m b on  a.deal_CUSTOMER_ID=b.CUSTOMER_ID,  (select @a:=0) as a  ");
		
		
		if(! CommonFunction.checkNull(vo.getCaseId()).equalsIgnoreCase(""))
		{
			buffer.append(" where a.deal_id = '"+vo.getCaseId()+"' and ifnull(OBLIGATION_TO_BE_CONSIDER,'Y')='Y' ");
			notCLFBuffer.append(" where a.deal_id = '"+vo.getCaseId()+"' and ifnull(OBLIGATION_TO_BE_CONSIDER,'Y')='Y' ");
		}
		
			buffer.append(" and (INSTITUTION_NAME like '%Capital First%' or INSTITUTION_NAME like '%Future Capital%' or INSTITUTION_NAME like '%CFL%'or INSTITUTION_NAME like 'FCH') ");
			notCLFBuffer.append(" and !(INSTITUTION_NAME like '%Capital First%' or INSTITUTION_NAME like '%Future Capital%' or INSTITUTION_NAME like '%CFL%'or INSTITUTION_NAME like 'FCH') ");
		
		logger.info("query -------------"+buffer.toString());
		logger.info("notCLFBufferquery -------------"+notCLFBuffer.toString());
			try {
				 cflList = ConnectionDAOforEJB.sqlSelect(buffer.toString());
				 notCLFList = ConnectionDAOforEJB.sqlSelect(notCLFBuffer.toString());
				 if(cflList!=null && cflList.size()>=3)
				 {
					for(int i=0;i<cflList.size();i++) 
					{
						mainList.add(cflList.get(i));
					}
				 }else
				 {
					 if(cflList!=null && cflList.size()<3)
					 {
						for(int i=1;i<=3;i++) 
						{
							if(cflList.size()>=i)
							mainList.add(cflList.get(i-1));
							else
							{
								ArrayList l = new ArrayList();
								//l.add(null);
							mainList.add(l);
							}
						}
					 }
					 else
					 {
						 ArrayList l = new ArrayList();
							//l.add("");
						 mainList.add(l);
						 mainList.add(l);
						 mainList.add(l);
					 }
				 }
				 
				 if(notCLFList!=null && notCLFList.size()>0)
				 {
					for(int i=0;i<notCLFList.size();i++) 
					{
						mainList.add(notCLFList.get(i));
					}
				 }
				 
				 
				 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else
		{
			buffer.append("select @a:=@a+1 as a,b.CUSTOMER_NAME,INSTITUTION_NAME,EMI_PAID_FROM,OBLIGATION_TYPE,FINANCED_AMOUNT,EMI,LOAN_TENURE,EMI_PAID,"
					+ "OUTSTANDING_AMOUNT,EMI_BOUNCED,EMI_PENDING, EMI_RECEIVED_DATE "
					+ " from cr_obligation_analysis_dtl a "
					+ " join cr_deal_customer_m b on  a.deal_CUSTOMER_ID=b.CUSTOMER_ID,  (select @a:=0) as a  ");
			
			
			if(! CommonFunction.checkNull(vo.getCaseId()).equalsIgnoreCase(""))
				buffer.append(" where a.deal_id = '"+vo.getCaseId()+"' and ifnull(OBLIGATION_TO_BE_CONSIDER,'Y')='Y' ");
			
			logger.info("query -------------"+buffer.toString());
				try {
					 mainList = ConnectionDAOforEJB.sqlSelect(buffer.toString());
					 logger.info("mainList--------"+mainList.size());
				} catch (Exception e) {
					e.printStackTrace();
				}
			
		}
		return mainList;
	}
	@Override
	public void saveDataInExcelErrorLog(UnderwritingDocUploadVo vo) {
		logger.info("saveDataInExcelErrorLog--------");
		boolean returnFlag = false;
		StringBuffer buffer =  new StringBuffer();
		buffer.append("insert into push_data_into_excel_error_log (excel_sheet_name,deal_id,error_msg,error_flag,maker_id,maker_date)");
		buffer.append(" values (");
		buffer.append("?,");
		buffer.append("?,");
		buffer.append("?,");
		buffer.append("?,");
		buffer.append("?,");
		buffer.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) " );
		buffer.append(")");
		ArrayList qryList = new ArrayList();
		PrepStmtObject object = new PrepStmtObject();
		
			object.addString(vo.getExcelSheetName());
			object.addString(vo.getCaseId());
	
		
			object.addString(CommonFunction.checkNull(vo.getErrorMsg()));
			object.addString("E");
			object.addString(vo.getMakerId());
			object.addString(vo.getMakerDate());
			object.setSql(buffer.toString());
			logger.info("saveDataInExcelErrorLog----"+object.printQuery());
			qryList.add(object);
			try {
				returnFlag = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	@Override
	public ArrayList getSystemInputData$BS(UnderwritingDocUploadVo vo,
			String qry) {
		logger.info("getSystemInputData$BS--------");
		StringBuffer buffer = new StringBuffer();
		ArrayList mainList = null;
		buffer.append("select "+qry+ " from "
				+ "  CR_BANK_STMNT_DTL B "
				+ " LEFT JOIN CR_deal_DTL D ON D.APPLICATION_REFERENCE_NO = B.REF_ID"
				+ " LEFT JOIN CR_deal_CUSTOMER_ROLE R ON R.deal_CUSTOMER_ID=B.ENTITY_ID"
				+ " LEFT JOIN CR_deal_CUSTOMER_M M ON R.deal_CUSTOMER_ID = M.CUSTOMER_ID");
		
		if(! CommonFunction.checkNull(vo.getCaseId()).equalsIgnoreCase(""))
			buffer.append(" where d.deal_id = '"+vo.getCaseId()+"'");
		
		logger.info("query -------------"+buffer.toString());
			try {
				 mainList = ConnectionDAOforEJB.sqlSelect(buffer.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		return mainList;
	}
	@Override
	public ArrayList getSystemInputData$BALS(UnderwritingDocUploadVo vo,String sourceType) {
		logger.info("getSystemInputData$BALS--------");
		
		StringBuffer buffer = new StringBuffer();
		ArrayList mainList = null;
	
		buffer.append("select  param_name , GROUP_CONCAT(if(ifnull(param_value,0)='',0,ifnull(param_value,0)) SEPARATOR ',') ,GROUP_CONCAT(round(FINANCIAL_YEAR) SEPARATOR ',' )"
				+ " ,deal_customer_id,deal_customer_role_type, f.param_code "
				+ " from cr_financial_data_dtl  f"
				+ " left join cr_financial_param p on f.PARAM_CODE=p.PARAM_CODE"
				+ " where  f.source_type = '"+sourceType+"' ");
		
		if(! CommonFunction.checkNull(vo.getCaseId()).equalsIgnoreCase(""))
			buffer.append(" and f.deal_id = '"+vo.getCaseId()+"'");
		
		buffer.append(" group by param_name");

		logger.info("query -------------"+buffer.toString());
			try {
				 mainList = ConnectionDAOforEJB.sqlSelect(buffer.toString());
				 
				 mainList = handlingMultipleCustomerWithSeQuenceNo(vo, mainList);
				 
				 
				 
			} catch (Exception e) {
				e.printStackTrace();
			}
		return mainList;
	}
	/*@Override
	public ArrayList getSystemInputData$PL(UnderwritingDocUploadVo vo) {
		logger.info("getSystemInputData$PL--------");
		
		StringBuffer	 buffer = new StringBuffer();
		ArrayList mainList = null;
		
		buffer.append("select param_name , GROUP_CONCAT(if(ifnull(param_value,0)='',0,ifnull(param_value,0)) SEPARATOR ',') ,GROUP_CONCAT(round(FINANCIAL_YEAR) SEPARATOR ',' )"
				+ " ,deal_customer_id,deal_customer_role_type, f.param_code "
				+ " from cr_financial_data_dtl  f"
				+ " left join cr_financial_param p on f.PARAM_CODE=p.PARAM_CODE"
				+ " where  f.source_type = 'P' ");
		
		if(! CommonFunction.checkNull(vo.getCaseId()).equalsIgnoreCase(""))
			buffer.append(" and f.deal_id = '"+vo.getCaseId()+"'");
		
		buffer.append(" group by param_name");

		logger.info("query -------------"+buffer.toString());
			try {
				 mainList = ConnectionDAOforEJB.sqlSelect(buffer.toString());
				 mainList = handlingMultipleCustomerWithSeQuenceNo(vo, mainList);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		return mainList;
	}*/
	
	@Override
	public ArrayList getSystemInputData$EcalManual(UnderwritingDocUploadVo vo) {
		logger.info("getSystemInputData$EcalManual--------");
		
		StringBuffer	 buffer = new StringBuffer();
		ArrayList mainList = null;
		
			buffer.append("select  TARGET_SHEET_NAME,f.param_code , GROUP_CONCAT(if(ifnull(param_value,0)='',0,ifnull(param_value,0)) SEPARATOR ',') ,"
					+ " GROUP_CONCAT(round(FINANCIAL_YEAR) SEPARATOR ',' ) ,f.deal_customer_id,deal_customer_role_type  from cr_manual_fields_dtl  f"
					+ " left join cr_deal_customer_role r on f.deal_customer_id = r.deal_customer_id");
		if(! CommonFunction.checkNull(vo.getCaseId()).equalsIgnoreCase(""))
			buffer.append(" where f.deal_id = '"+vo.getCaseId()+"'");
		
		buffer.append(" group by param_code, f.deal_customer_id, TARGET_SHEET_NAME");

		logger.info("query -------------"+buffer.toString());
			try {
				 mainList = ConnectionDAOforEJB.sqlSelect(buffer.toString());
				 mainList = handlingMultipleCustomerWithSeQuenceNo(vo, mainList);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		return mainList;
	}
	
	
	
	public  ArrayList handlingMultipleCustomerWithSeQuenceNo(UnderwritingDocUploadVo vo, ArrayList mainList)
	{
		String returnString ="";
		ArrayList listCOAPPL = null;
		ArrayList subListCOAPPL = null;
		ArrayList listGAURANTOR = null;
		ArrayList subListGAURANTOR = null;
		/*StringBuffer bufferCOAPPL = new StringBuffer(); 
		bufferCOAPPL.append("select @a:=@a+1,customer_id,deal_customer_role_type"
				+ " from cr_deal_customer_m m "
				+ " left join cr_deal_customer_role r on r.deal_customer_id = m.customer_id "
				+ " and deal_customer_role_type = 'COAPPL',"
				+ "  (select @a:=0) as a " );
		
		StringBuffer bufferGAURANTOR = new StringBuffer(); 
		
		bufferGAURANTOR.append("select @a:=@a+1,customer_id,deal_customer_role_type"
				+ " from cr_deal_customer_m m "
				+ " left join cr_deal_customer_role r on r.deal_customer_id = m.customer_id "
				+ " and deal_customer_role_type = 'GUARANTOR',"
				+ "  (select @a:=0) as a " );
		if(! CommonFunction.checkNull(vo.getCaseId()).equalsIgnoreCase(""))
		{
			bufferCOAPPL.append(" where deal_id = '"+vo.getCaseId()+"'");
			bufferGAURANTOR.append(" where deal_id = '"+vo.getCaseId()+"'");
		}*/
		try {
			/*logger.info("select sequence No query For PL-------"+bufferCOAPPL.toString());
			logger.info("select sequence No query For PL-------"+bufferGAURANTOR.toString());
			listCOAPPL = ConnectionDAOforEJB.sqlSelect(bufferCOAPPL.toString());
			listGAURANTOR = ConnectionDAOforEJB.sqlSelect(bufferGAURANTOR.toString());*/
			listCOAPPL = getCoapplCustomerListWithSequence(vo);
			listGAURANTOR = getGaurantorCustomerListWithSequence(vo);
			ArrayList 	mainSubList = null;
		
				int size = mainList==null?0:mainList.size();
		 		for (int i = 0; i<size;i++)
				 {
					 mainSubList = (ArrayList)mainList.get(i);
					  if(mainSubList.size()> 0 )
							  {
						  			boolean flag =false;
						  		  
								  String paramName=CommonFunction.checkNull(mainSubList.get(0));
								  String customerId=CommonFunction.checkNull(mainSubList.get(3));
								  String customerRoleType=CommonFunction.checkNull(mainSubList.get(4));
								  
								  int sizeListCO = listCOAPPL==null? 0 : listCOAPPL.size();
								  for (int j = 0; j<sizeListCO;j++)
										 {
									  subListCOAPPL = (ArrayList)listCOAPPL.get(j);
											  if(subListCOAPPL.size()> 0 )
											  {
												  String seqNo=CommonFunction.checkNull(subListCOAPPL.get(0));
												  String seqNoCustomerId=CommonFunction.checkNull(subListCOAPPL.get(1));
												  String seqNoCustoemrRoleType=CommonFunction.checkNull(subListCOAPPL.get(2));
												  if(seqNoCustomerId.equalsIgnoreCase(customerId) && customerRoleType.equalsIgnoreCase(seqNoCustoemrRoleType)  )
												  {
													  paramName= seqNoCustoemrRoleType+"_"+seqNo+"_"+paramName;
													  flag =true;
													  break ;
												  } 
												  if(customerRoleType.equalsIgnoreCase("PRAPPL"))
												  {
													 paramName="PRAPPL_"+paramName;
													  break ;
												  }
											  }
										 }
								  if(!flag)
								  {
								  int sizeListGAU = listGAURANTOR==null? 0 : listGAURANTOR.size();
								  for (int j = 0; j<sizeListGAU;j++)
										 {
									  subListGAURANTOR = (ArrayList)listGAURANTOR.get(j);
											  if(subListGAURANTOR.size()> 0 )
											  {
												  String seqNo=CommonFunction.checkNull(subListGAURANTOR.get(0));
												  String seqNoCustomerId=CommonFunction.checkNull(subListGAURANTOR.get(1));
												  String seqNoCustoemrRoleType=CommonFunction.checkNull(subListGAURANTOR.get(2));
												  if(seqNoCustomerId.equalsIgnoreCase(customerId) && customerRoleType.equalsIgnoreCase(seqNoCustoemrRoleType)  )
												  {
													  paramName= seqNoCustoemrRoleType+"_"+seqNo+"_"+paramName;
													  flag =true;
													  break ;
												  } 
												  if(customerRoleType.equalsIgnoreCase("PRAPPL"))
												  {
													 paramName="PRAPPL_"+paramName;
													  break ;
												  }
											  }
										 }
								  }
								  if(!flag)
								  {
									  paramName="PRAPPL_"+paramName;
								  }
								  mainSubList.remove(0);
								  mainSubList.add(0,paramName);
						 }
				 }
		 }catch (Exception e) {
				e.printStackTrace();
			}
		return mainList;
	}
	
public ArrayList getCoapplCustomerListWithSequence(UnderwritingDocUploadVo vo)
{
	ArrayList listCOAPPL = null;
	ArrayList subListCOAPPL = null;
	logger.info("getCoapplCustomerListWithSequence-----------");
	StringBuffer bufferCOAPPL = new StringBuffer(); 
	bufferCOAPPL.append("select @a:=@a+1,customer_id,deal_customer_role_type"
			+ " from cr_deal_customer_m m "
			+ " left join cr_deal_customer_role r on r.deal_customer_id = m.customer_id "
			+ " and deal_customer_role_type = 'COAPPL',"
			+ "  (select @a:=0) as a " );
	
	if(! CommonFunction.checkNull(vo.getCaseId()).equalsIgnoreCase(""))
	{
		bufferCOAPPL.append(" where deal_id = '"+vo.getCaseId()+"'");
	}
	try {
		logger.info("select sequence No query For PL-------"+bufferCOAPPL.toString());
		listCOAPPL = ConnectionDAOforEJB.sqlSelect(bufferCOAPPL.toString());
	}catch(Exception e)
	{
		e.printStackTrace();
	}
	return listCOAPPL;
}
	
public ArrayList getGaurantorCustomerListWithSequence(UnderwritingDocUploadVo vo)
{
	ArrayList listGAURANTOR = null;
	ArrayList subListGAURANTOR = null;
	StringBuffer bufferGAURANTOR = new StringBuffer(); 
	
	bufferGAURANTOR.append("select @a:=@a+1,customer_id,deal_customer_role_type"
			+ " from cr_deal_customer_m m "
			+ " left join cr_deal_customer_role r on r.deal_customer_id = m.customer_id "
			+ " and deal_customer_role_type = 'GUARANTOR',"
			+ "  (select @a:=0) as a " );
	logger.info("getGaurantorCustomerListWithSequence-----------");
	
	if(! CommonFunction.checkNull(vo.getCaseId()).equalsIgnoreCase(""))
	{
		bufferGAURANTOR.append(" where deal_id = '"+vo.getCaseId()+"'");
	}
	try {
		logger.info("select sequence No query For PL-------"+bufferGAURANTOR.toString());
		listGAURANTOR = ConnectionDAOforEJB.sqlSelect(bufferGAURANTOR.toString());
	}catch(Exception e)
	{
		e.printStackTrace();
	}
	return listGAURANTOR;
}
@Override
public ArrayList getDataEntryForBankingReport(UnderwritingDocUploadVo vo) {
	logger.info("getDataEntryForBankingReport-----------");
	/*String query1 = "select  statement_month, statement_year,count(total_cr_amt), ifnull(sum(total_cr_amt),0),count(total_dr_amt),"
			+ "ifnull(sum(total_dr_amt),0),count(INWARD_CHEQUE_RETURN_COUNT), count(OUTWARD_CHEQUE_RETURN_COUNT), ifnull(sum(ODCCIMIT_MONTHLY_INTEREST),0) "
			+ "from cr_bank_analysis_dtl    where deal_id = '"+vo.getCaseId()+"'  GROUP by statement_month";*/
	
	String query1 = "select  statement_month, statement_year,TOTAL_CREDIT_ENTRY , ifnull(sum(total_cr_amt),0),TOTAL_DEBIT_ENTRY ,"
			+ "	ifnull(sum(total_dr_amt),0),ifnull(INWARD_CHEQUE_RETURN_COUNT,0) ,"
			+ "	ifnull(OUTWARD_CHEQUE_RETURN_COUNT,0), ifnull(sum(ODCCIMIT_MONTHLY_INTEREST),0) "
					+ "	from cr_bank_analysis_dtl    where deal_id = '"+vo.getCaseId()+"'   GROUP by statement_month order by STATEMENT_YEAR asc,STATEMENT_MONTH asc ";
	logger.info("query--------"+query1);
	
	ArrayList list  = new ArrayList();
	try {
		list = ConnectionDAOforEJB.sqlSelect(query1);
	} catch (SQLException e) {
		e.printStackTrace();
	}
	
	return list;
}
@Override
public ArrayList<PushDataInTargetSheetVo> getTargetFieldValueData(String caseId) {
	logger.info("getTargetFieldValueData-----------");
	String query1 = "select  target_sheet_name, target_cell,str_value, maker_id "
			+ "from target_values_tmp    where deal_id = '"+caseId+"' ";
	logger.info("query--------"+query1);
	
	ArrayList<PushDataInTargetSheetVo> returnList  = new ArrayList<PushDataInTargetSheetVo>();
	ArrayList list  = new ArrayList();
	ArrayList subList  = new ArrayList();
	try {
		list = ConnectionDAOforEJB.sqlSelect(query1);
		PushDataInTargetSheetVo vo  = null;
		int size = list!=null?list.size():0;
		for(int i = 0;i<size;i++)
		{
			subList = (ArrayList)list.get(i);
			vo = new PushDataInTargetSheetVo();
			if(subList.size()>0)
			{
				vo.setTargetWorksheetName(CommonFunction.checkNull(subList.get(0)));
				vo.setTargetWorksheetCell(CommonFunction.checkNull(subList.get(1)));
				vo.setValue(CommonFunction.checkNull(subList.get(2)));
			}
			returnList.add(vo);
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	
	return returnList;
}

	@Override
	public ArrayList getDataForBankingInput(UnderwritingDocUploadVo vo) {
		logger.info("getDataForBankingInput-----------");
		String query1 = "select sum(total_cr_amt) total_cr_amt ,STATEMENT_YEAR,STATEMENT_MONTH from cr_bank_analysis_dtl where "
				+ "deal_id = '"+vo.getCaseId()+"' group by STATEMENT_MONTH order by STATEMENT_MONTH desc, STATEMENT_YEAR desc ";
		logger.info("query--------" + query1);

		ArrayList list = new ArrayList();
		try {
			list = ConnectionDAOforEJB.sqlSelect(query1);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
    @Override
    public boolean insertSchemeWiseEligibilityAmount(ArrayList<FundFlowDownloadUploadVo> list)
    {
    	int maxId = 0;
		PrepStmtObject insertPrepStmtObject = null;
		FundFlowDownloadUploadVo vo = null;
		Boolean resultStatus = false;
		ArrayList list1 = new ArrayList();
		ArrayList qryList = new ArrayList();
		boolean delStatus = false;
		vo = list.get(0);
		String caseId = CommonFunction.checkNull(vo.getCaseId()).replace(".00", "").trim();
		StringBuffer bufInsSql = new StringBuffer();
		try {
			String delQuery = "delete from temp_scheme_report where deal_id='"+ caseId + "' ";
			logger.info("delQuery::::" + delQuery);
			list1.add(delQuery);

			delStatus = ConnectionDAOforEJB.sqlInsUpdDelete(list1);


			bufInsSql
					.append(" insert into temp_scheme_report(deal_id,scheme_id,product,eligibile_loan_amount,cam_program)");
			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?,");// deal_ID
			bufInsSql.append(" ?,");// SCHEME_ID
			bufInsSql.append(" ?,");// PRODUCT
			bufInsSql.append(" ?,");// ELIGIBILE_LOAN_AMOUNT
			bufInsSql.append(" ?");// cam_program
			bufInsSql.append(" )");
			for (int i = 0; i < list.size(); i++) {
				vo = list.get(i);
				insertPrepStmtObject = new PrepStmtObject();
				if (caseId.equalsIgnoreCase(""))// deal_ID
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(caseId).trim());

				if (CommonFunction.checkNull(vo.getScheme()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getScheme()).replace(".00", "").trim());
				if (CommonFunction.checkNull(vo.getProduct()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getProduct()).replace(".00", "").trim());

				if (CommonFunction.checkNull(vo.getLoanAmount()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLoanAmount()));

				if (CommonFunction.checkNull(vo.getType()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getType()));
				
				insertPrepStmtObject.setSql(bufInsSql.toString());
				qryList.add(insertPrepStmtObject);
			}

			boolean status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
			if (status) 
				resultStatus=true;

			logger.info("In insertSchemeWiseEligibilityAmount......................"+ resultStatus);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bufInsSql = null;
			qryList = null;
			list = null;
			insertPrepStmtObject = null;
		}
		return resultStatus;

    }
    //Abhishek Start for Push BTO value in CAM. 
	@Override
	public String getBTOValue(UnderwritingDocUploadVo vo) {
		logger.info("In EligibilityCalculationProcessDaoImpl getBTOValue -----------");
		String BTO = "";
		String query = "select ifnull (round(((select round(sum(total_cr_amt)*2) from cr_bank_analysis_dtl where deal_id='"+vo.getCaseId()+"') / "
				+ "(select ifnull(round(sum(ifnull(param_value,0))),0) from cr_financial_data_dtl "
				+ "where param_code in('P&L_F1_Y_NET_SALES_(NET_OF_EXCISE)_(I.E','P&L_F2_Y_NET_SALES_(NET_OF_EXCISE)_(I.E','P&L_F3_Y_NET_SALES_(NET_OF_EXCISE)_(I.E')"
				+ " and FINANCIAL_YEAR=(select SUBSTRING(FINANCIAL_YEAR,6,10) from cr_deal_dtl where deal_id='"+vo.getCaseId()+"')"
				+ " and deal_id='"+vo.getCaseId()+"')) * 100),0) as bto ";
		logger.info("In EligibilityCalculationProcessDaoImpl getBTOValue query--------" + query);

		
		try {
			BTO = ConnectionDAOforEJB.singleReturn(query);
			BTO = BTO.concat("%");
			logger.info("In EligibilityCalculationProcessDaoImpl getBTOValue BTO--------" + BTO);
		} catch (Exception e) 
		{e.printStackTrace();}
		finally
		{
			query=null;
		}

		return BTO;
	}
	//Abhishek End 
	@Override
	public ArrayList getSystemInputData$LoanSheetObligation(
			UnderwritingDocUploadVo vo, String qry) {

			logger.info("getSystemInputData$LoanSheetObligation  $Obligation--------");
			StringBuffer buffer = new StringBuffer();
			StringBuffer notCLFBuffer = new StringBuffer();
			ArrayList mainList = new ArrayList();
			ArrayList cflList = null;
			ArrayList notCLFList = null;
			logger.info("vo.getProductId()-------"+vo.getProductId());
		
			buffer.append("select @a:=@a+1 as a,b.CUSTOMER_NAME,INSTITUTION_NAME,LOAN_NO,SOURCE_OBLIGATION,CLOSURE_DATE,OBLIGATION_TYPE,FINANCED_AMOUNT,EMI,LOAN_TENURE,EMI_PAID,EMI_PAID_FROM,CLOSED_LIVE,collateral,cibil,OUTSTANDING_AMOUNT,COMMENTS,EMI_BOUNCED"
					+ ", EMI_RECEIVED_DATE "
					+ " from cr_obligation_analysis_dtl a "
					+ " join cr_deal_customer_m b on  a.deal_CUSTOMER_ID=b.CUSTOMER_ID,  (select @a:=0) as a  ");
			
			notCLFBuffer.append("select @a:=@a+1 as a,b.CUSTOMER_NAME,INSTITUTION_NAME,LOAN_NO,SOURCE_OBLIGATION,CLOSURE_DATE,OBLIGATION_TYPE,FINANCED_AMOUNT,EMI,LOAN_TENURE,EMI_PAID,EMI_PAID_FROM,CLOSED_LIVE,collateral,cibil,OUTSTANDING_AMOUNT,COMMENTS,EMI_BOUNCED"
					+ ", EMI_RECEIVED_DATE "
					+ " from cr_obligation_analysis_dtl a "
					+ " join cr_deal_customer_m b on  a.deal_CUSTOMER_ID=b.CUSTOMER_ID,  (select @a:=0) as a  ");
			
			
			if(! CommonFunction.checkNull(vo.getCaseId()).equalsIgnoreCase(""))
			{
				buffer.append(" where a.deal_id = '"+vo.getCaseId()+"' ");
				notCLFBuffer.append(" where a.deal_id = '"+vo.getCaseId()+"'  ");
			}
			
				buffer.append(" and (INSTITUTION_NAME like '%Capital First%' or INSTITUTION_NAME like '%Future Capital%' or INSTITUTION_NAME like '%CFL%'or INSTITUTION_NAME like 'FCH') ");
				notCLFBuffer.append(" and !(INSTITUTION_NAME like '%Capital First%' or INSTITUTION_NAME like '%Future Capital%' or INSTITUTION_NAME like '%CFL%'or INSTITUTION_NAME like 'FCH') ");
			
			logger.info("query -------------"+buffer.toString());
			logger.info("notCLFBufferquery -------------"+notCLFBuffer.toString());
				try {
					 cflList = ConnectionDAOforEJB.sqlSelect(buffer.toString());
					 notCLFList = ConnectionDAOforEJB.sqlSelect(notCLFBuffer.toString());
					 if(cflList!=null && cflList.size()>=3)
					 {
						for(int i=0;i<cflList.size();i++) 
						{
							mainList.add(cflList.get(i));
						}
					 }else
					 {
						 if(cflList!=null && cflList.size()<3)
						 {
							for(int i=1;i<=3;i++) 
							{
								if(cflList.size()>=i)
								mainList.add(cflList.get(i-1));
								else
								{
									ArrayList l = new ArrayList();
									//l.add(null);
								mainList.add(l);
								}
							}
						 }
						 else
						 {
							 ArrayList l = new ArrayList();
								//l.add("");
							 mainList.add(l);
							 mainList.add(l);
							 mainList.add(l);
						 }
					 }
					 
					 if(notCLFList!=null && notCLFList.size()>0)
					 {
						for(int i=0;i<notCLFList.size();i++) 
						{
							mainList.add(notCLFList.get(i));
						}
					 }
					 
					 
					 
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			return mainList;
		}
}
	

