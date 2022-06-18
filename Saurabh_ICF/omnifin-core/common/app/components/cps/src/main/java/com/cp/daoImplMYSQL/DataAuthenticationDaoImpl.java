package com.cp.daoImplMYSQL;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.cp.actionform.ReportsForm;
import com.cp.actions.FinancialAnalysisForwardAction;
import com.cp.dao.DataAuthenticationDao;
import com.cp.util.DateUtility;
import com.cp.util.FormatUtility;
import com.cp.vo.BankDetailsVO;
import com.cp.vo.CodeDescVo;
import com.cp.vo.CommonDealVo;
import com.cp.vo.FinancialAnalysisParamVo;
import com.cp.vo.FinancialAnalysisVo;
import com.cp.vo.QuesBankVo;
import com.cp.vo.UnderwritingDocUploadVo;

public class DataAuthenticationDaoImpl implements DataAuthenticationDao {
	private static final Logger logger = Logger.getLogger(DataAuthenticationDaoImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00");
	String dbo=resource.getString("lbl.dbPrefix");
	
	
	
	@Override
	public ArrayList<QuesBankVo> getQuestionForAuthentication(String caseId,String stageId,String docType,String businessDate,String documentId) {
		logger.info("getQuestionForAuthentication-------------");
		
		StringBuffer buffer = new StringBuffer();
		ArrayList<QuesBankVo> returnList =  new ArrayList<QuesBankVo>();
		
		QuesBankVo bankVo = null;
		QuesBankVo balanceBankVo = null;
		
		UnderwritingDocUploadVo uwDocVo = null;
		Date date =  DateUtility.convertToDate(businessDate);
		
		businessDate = FormatUtility.dateFormat(date);
		StringBuilder query=new StringBuilder();
		StringBuilder query1=new StringBuilder();
		StringBuilder query2=new StringBuilder();
		StringBuilder openingBalanceQuery=new StringBuilder();
		StringBuilder closingBalanceQuery=new StringBuilder();
		StringBuilder openingBalanceQueryfetchdata=new StringBuilder();
		StringBuilder closingBalanceQueryfetchdata=new StringBuilder();
		
		
		logger.info("DocType In getQuestionForAuthentication ================="+docType);
		
		
		
		query.append("select count(1) stage_code from  cr_ques_answer_dtl where document_type = '"+docType+"'  and stage_code = '"+stageId+"' and deal_id = '"+caseId+"' and document_id = '"+documentId+"' and last_updated_by is null "); 
		
		String val=ConnectionDAO.singleReturn(query.toString());
		int count=Integer.parseInt(val);
		
		if(count>0)
		{
			query2.append("select question_code,question_desc,stage_code, question_id,financial_year from  cr_ques_answer_dtl where document_type = '"+docType+"'  and stage_code = '"+stageId+"' and deal_id = '"+caseId+"' and document_id = '"+documentId+"' and last_updated_by is null  ");
			
			logger.info("Select Query "+query2.toString());
			try 
			{
				ArrayList list = (ArrayList)ConnectionDAO.sqlSelect(query2.toString());
				ArrayList sublist = new ArrayList();
				int size = list!=null?list.size():0;
				for(int i = 0;i<size;i++)
					{
						sublist =  (ArrayList)list.get(i);
						if(sublist.size()>0)
							{
								bankVo =  new QuesBankVo();
								bankVo.setQuestionCode(CommonFunction.checkNull(sublist.get(0)));
								bankVo.setQuestionDesc(CommonFunction.checkNull(sublist.get(1)));
								bankVo.setStageCode(CommonFunction.checkNull(sublist.get(2)));
								//bankVo.setResultCalcFormula(CommonFunction.checkNull(sublist.get(3)));
								bankVo.setQuestionId(CommonFunction.checkNull(sublist.get(3)));
								bankVo.setFinancialYear(CommonFunction.checkNull(sublist.get(4)));
						
							}
					/*
						logger.info("QuestionCode============="+bankVo.getQuestionCode());
						logger.info("StageCode============="+bankVo.getStageCode());
						logger.info("DocType In DaoImp ======="+docType);
						logger.info("caseID =============="+caseId);*/
						returnList.add(bankVo);
					}
				
			}
		catch (Exception e) 
			{
				logger.info("Error messages "+e.getMessage());
			}
		return returnList;
			
		
		
		}
		else
		{
			String currentYearQuery = "select date_format(to_date,'%Y') from cr_uploaded_documents where document_id = '"+documentId+"'";
			String currentYear = ConnectionDAO.singleReturn(currentYearQuery);
			logger.info("currentYearQuery----"+currentYearQuery);
			logger.info("currentYear----"+currentYear);
			logger.info("cr_ques_answer_dtl Table Is blank so, Select data from cr_ques_bank_m..................................");
			docType="PL".equalsIgnoreCase(docType)?"P&L":docType;	
			String tableName = null;
			String sourceType = null;
			logger.info("docType=========="+docType);
			logger.info("stageId=========="+stageId);
			if(docType.equalsIgnoreCase("P&L"))
			{
				sourceType = "P";
				tableName  = "cr_financial_data_dtl";	
			}
			else if(docType.equalsIgnoreCase("BALS"))
			{
				sourceType = "B";
				tableName  = "cr_financial_data_dtl";	
			}
			else if(docType.equalsIgnoreCase("BS"))
			{
				sourceType = "BS";
				tableName  = "cr_bank_stmnt_dtl";	
			}
			
			
			
			if(tableName.equalsIgnoreCase("cr_financial_data_dtl"))
 
			query1.append("select d.param_code,param_name,param_value,d.FINANCIAL_DATA_ID,FINANCIAL_YEAR "
					+ " from  cr_financial_data_dtl d"
					+ " join cr_financial_param p on d.param_code =p.param_code "
					+ " where deal_id = '"+caseId+"' and d.source_type  = '"+sourceType+"' and ifnull(document_id,0) = '"+documentId+"' and financial_year = '"+currentYear+"' and ifnull(AUTO_CALCULATED,'N')='N'  and param_value <> 0 and param_value is not null "
							+ " order by FINANCIAL_YEAR desc  ");
			else
			{
			query1.append("select total_dr,total_cr,balance_amount,STMNT_ENTRY_ID, if(DOCUMENT_SEQ_ID is null,@a:=@a+1 ,DOCUMENT_SEQ_ID) as sequence_no,date_format(statment_date,'%d/%m/%Y') as statment_date,error_flag_pdf from  "+tableName+",  (select @a:=0) as a where deal_id = '"+caseId+"' and ifnull(document_id,0) = '"+documentId+"' and  STATMENT_DATE is not null ");
			
			openingBalanceQueryfetchdata.append("insert into tmp_min_cr_bank_stmnt_dtl"
					+ "(STMNT_ENTRY_ID ,BALANCE_AMOUNT,STATEMENT_MONTH,statment_date,error_flag_pdf,deal_id,document_id) "
					+ "select min(a.STMNT_ENTRY_ID) as STMNT_ENTRY_ID ,a.BALANCE_AMOUNT,a.STATEMENT_MONTH,date_format(a.statment_date,'%d/%m/%Y') "
					+ "as statment_date,a.error_flag_pdf,a.deal_id,a.document_id from cr_bank_stmnt_dtl a "
					+ "where   a.deal_id = '"+caseId+"' and  ifnull(a.document_id,0)  = '"+documentId+"' and  a.STATMENT_DATE is not null  group by a.statement_month; ");
			
			openingBalanceQuery.append("select STMNT_ENTRY_ID,BALANCE_AMOUNT,STATEMENT_MONTH,date_format(statment_date,'%d/%m/%Y') as statment_date,error_flag_pdf "
					+ "from cr_bank_stmnt_dtl where ifnull(deal_id,0) = '"+caseId+"' and  ifnull(document_id,0) = '"+documentId+"' "
							+ "and STMNT_ENTRY_ID in(select STMNT_ENTRY_ID from tmp_min_cr_bank_stmnt_dtl where deal_id='"+caseId+"' and  ifnull(document_id,0) = '"+documentId+"' )");
			
			
			closingBalanceQueryfetchdata.append("insert into tmp_max_cr_bank_stmnt_dtl"
					+ "(STMNT_ENTRY_ID ,BALANCE_AMOUNT,STATEMENT_MONTH,statment_date,error_flag_pdf,deal_id,document_id) "
					+ "select max(a.STMNT_ENTRY_ID) as STMNT_ENTRY_ID ,a.BALANCE_AMOUNT,a.STATEMENT_MONTH,date_format(a.statment_date,'%d/%m/%Y') "
					+ "as statment_date,a.error_flag_pdf,a.deal_id,a.document_id from cr_bank_stmnt_dtl a "
					+ "where   a.deal_id = '"+caseId+"' and  ifnull(a.document_id,0) = '"+documentId+"' and  a.STATMENT_DATE is not null  group by a.statement_month; ");
			
			
			
			closingBalanceQuery.append("select STMNT_ENTRY_ID,BALANCE_AMOUNT,STATEMENT_MONTH,date_format(statment_date,'%d/%m/%Y') as statment_date,error_flag_pdf "
					+ "from cr_bank_stmnt_dtl where ifnull(deal_id,0) = '"+caseId+"' and  ifnull(document_id,0) = '"+documentId+"' "
							+ " and STMNT_ENTRY_ID in(select STMNT_ENTRY_ID from tmp_max_cr_bank_stmnt_dtl where deal_id='"+caseId+"' and  ifnull(document_id,0) = '"+documentId+"' )");
			
			}
			logger.info("Select Query ========== "+query1.toString());
		
			try 
			{
				ArrayList list = (ArrayList)ConnectionDAO.sqlSelect(query1.toString());
				ArrayList sublist = new ArrayList();
				int size = list!=null?list.size():0;
				for(int i = 0;i<size;i++)
					{
						sublist =  (ArrayList)list.get(i);
						if(sublist.size()>0)
							{
							
								if(tableName.equalsIgnoreCase("cr_financial_data_dtl"))
								{
									bankVo =  new QuesBankVo();
									bankVo.setQuestionCode(CommonFunction.checkNull(sublist.get(0)));
									bankVo.setQuestionDesc(CommonFunction.checkNull(sublist.get(1)));
									bankVo.setOrgValue(CommonFunction.checkNull(sublist.get(2)));
									
									bankVo.setQuestionId(CommonFunction.checkNull(sublist.get(3)));
									bankVo.setStageCode(CommonFunction.checkNull(stageId));
									bankVo.setFinancialYear(currentYear);
								}
								else
								{
									bankVo =  new QuesBankVo();
									if(!CommonFunction.checkNull(sublist.get(6)).equalsIgnoreCase("E"))
									{
										if(!CommonFunction.checkNull(sublist.get(0)).equalsIgnoreCase(""))
										{
											bankVo.setQuestionCode(CommonFunction.checkNull(sublist.get(4))+"_No_Entry"+"("+CommonFunction.checkNull(sublist.get(5))+")");
											bankVo.setQuestionDesc(CommonFunction.checkNull(sublist.get(4))+"_No_Entry"+"("+CommonFunction.checkNull(sublist.get(5))+")");
											bankVo.setOrgValue(CommonFunction.checkNull(sublist.get(0)));
										}
										else {
											bankVo.setQuestionCode(CommonFunction.checkNull(sublist.get(4))+"_No_Entry"+"("+CommonFunction.checkNull(sublist.get(5))+")");
											bankVo.setQuestionDesc(CommonFunction.checkNull(sublist.get(4))+"_No_Entry"+"("+CommonFunction.checkNull(sublist.get(5))+")");
											bankVo.setOrgValue(CommonFunction.checkNull(sublist.get(1)));
										}
										bankVo.setQuestionId(CommonFunction.checkNull(sublist.get(3)));
										bankVo.setStageCode(CommonFunction.checkNull(stageId));
										bankVo.setFinancialYear(currentYear);
									}
									else
										bankVo=null;
								}
								
							}
					
						if(bankVo!=null)
						returnList.add(bankVo);
					}
				 if(docType.equalsIgnoreCase("BS"))
					{
					 logger.info("in Opening and closing block");
					 logger.info("openingBalanceQuery----"+openingBalanceQuery);
					 logger.info("closingBalanceQuery----"+closingBalanceQuery);
					 logger.info("closingBalanceQuery----"+openingBalanceQueryfetchdata);
					 logger.info("closingBalanceQuery----"+closingBalanceQueryfetchdata);
					 ArrayList openinglist = (ArrayList)ConnectionDAO.sqlSelect(openingBalanceQuery.toString());
					 ArrayList closinglist = (ArrayList)ConnectionDAO.sqlSelect(closingBalanceQuery.toString());
					 logger.info("closinglist size --------------------->"+closinglist.size());
					 logger.info("openinglist size --------------------->"+openinglist.size());
					
					 ArrayList minQueryfetchdata=new ArrayList();
					 ArrayList maxclosingQueryfetchdata=new ArrayList();
					 
					 minQueryfetchdata.add(openingBalanceQueryfetchdata);
					 ConnectionDAO.sqlInsUpdDelete(minQueryfetchdata);
					 maxclosingQueryfetchdata.add(closingBalanceQueryfetchdata);
					 ConnectionDAO.sqlInsUpdDelete(maxclosingQueryfetchdata);
					 
					 
					 
						ArrayList openingSublist = new ArrayList();
						ArrayList closingSublist = new ArrayList(); 
						 logger.info("openingSublist size --------------------->"+openingSublist.size());
						 logger.info("closingSublist size --------------------->"+closingSublist.size());
						int size1 = openinglist!=null?openinglist.size():0;
						logger.info("size1----"+size1);
						for(int i = 0;i<size1;i++)
							{
							openingSublist =  (ArrayList)openinglist.get(i);
							closingSublist =  (ArrayList)closinglist.get(i);
								if(openingSublist.size()>0 )
								{
									if(!CommonFunction.checkNull(openingSublist.get(4)).equalsIgnoreCase("E") )
									{
											bankVo =  new QuesBankVo();
											
											bankVo.setQuestionCode(CommonFunction.checkNull(openingSublist.get(2))+"_Month_Opening_Balance"+"("+CommonFunction.checkNull(openingSublist.get(3))+")");
											bankVo.setQuestionDesc(CommonFunction.checkNull(openingSublist.get(2))+"_Month_Opening_Balance"+"("+CommonFunction.checkNull(openingSublist.get(3))+")");
											bankVo.setOrgValue(CommonFunction.checkNull(openingSublist.get(1)));
											bankVo.setQuestionId(CommonFunction.checkNull(openingSublist.get(0)));
											bankVo.setStageCode(CommonFunction.checkNull(stageId));
											bankVo.setFinancialYear(currentYear);
											
									}
									else
									{
										bankVo=null;
									}
									if(!CommonFunction.checkNull(closingSublist.get(4)).equalsIgnoreCase("E"))	
									{
											balanceBankVo= new QuesBankVo();
											balanceBankVo.setQuestionCode(CommonFunction.checkNull(closingSublist.get(2))+"_Month_Closing_Balance"+"("+CommonFunction.checkNull(closingSublist.get(3))+")");
											balanceBankVo.setQuestionDesc(CommonFunction.checkNull(closingSublist.get(2))+"_Month_Closing_Balance"+"("+CommonFunction.checkNull(closingSublist.get(3))+")");
											balanceBankVo.setOrgValue(CommonFunction.checkNull(closingSublist.get(1)));
											balanceBankVo.setQuestionId(CommonFunction.checkNull(closingSublist.get(0)));
											balanceBankVo.setStageCode(CommonFunction.checkNull(stageId));
											balanceBankVo.setFinancialYear(currentYear);
									}else 
									{
									balanceBankVo=null;
									}
								}
								if(bankVo!=null)
								{
								returnList.add(bankVo);
								
								}
								if(balanceBankVo!=null) {
									returnList.add(balanceBankVo);
								}
							}
							
								
								
							}
					}
		catch (Exception e) 
			{
			e.printStackTrace();
				/*logger.info("Error messages "+e.getMessage());*/
			}
		return returnList;
			
		
	}
			
}

	
	@Override
	public boolean insertDataInTable(String caseId, String docType,String businessDate,String makerId,String docId, List<QuesBankVo> list) 
		{
			StringBuffer buffer = new StringBuffer();
			
			
			QuesBankVo bankVo = null;
			
			/*Date date =  DateUtility.convertToDate(businessDate);
			businessDate = FormatUtility.dateFormat(date);*/
			
			
			
					
			ArrayList qryList = new ArrayList();
				
			boolean status = false;
			
			PrepStmtObject insertPrepStmtObject =null;
			
			StringBuffer bufInsSql =	new StringBuffer();
			bufInsSql.append("insert into cr_ques_answer_dtl(question_id,Stage_Code,Document_Type,question_code ,question_desc,org_value, deal_id , document_id, financial_year , maker_date, maker_id )");
			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?," ); //question_id
			bufInsSql.append(" ?," ); //Stage_Code
			bufInsSql.append(" ?," ); //Document_Type
			bufInsSql.append(" ?," ); //question_code
			bufInsSql.append(" ?," ); //question_desc
			bufInsSql.append(" ?," ); //org_Value
			bufInsSql.append(" ?," ); //deal_id
			bufInsSql.append(" ?," ); //document_id
			bufInsSql.append(" ?," ); //financialYear
			bufInsSql.append(" STR_TO_DATE(?,'"+dateFormat+"')," ); //maker_date
			bufInsSql.append(" ? );" );  //maker_id
			
			int size = list==null?0:list.size();
			
			for(int i = 0;i<size; i++)
			{
				
				
				insertPrepStmtObject =  new PrepStmtObject();
				bankVo = list.get(i);
				
			if((CommonFunction.checkNull(bankVo.getQuestionId()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((CommonFunction.checkNull(bankVo.getQuestionId()).trim()));
			
			
			if((CommonFunction.checkNull(bankVo.getStageCode()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((CommonFunction.checkNull(bankVo.getStageCode()).trim()));
			
			if((CommonFunction.checkNull(docType).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((CommonFunction.checkNull(docType).trim()));
			
			if((CommonFunction.checkNull(bankVo.getQuestionCode()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((CommonFunction.checkNull(bankVo.getQuestionCode()).trim()));
			
			if((CommonFunction.checkNull(bankVo.getQuestionDesc()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((CommonFunction.checkNull(bankVo.getQuestionDesc()).trim()));
			
			if((CommonFunction.checkNull(bankVo.getOrgValue()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((CommonFunction.checkNull(bankVo.getOrgValue()).trim()));
			
			if((CommonFunction.checkNull(caseId).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((CommonFunction.checkNull(caseId).trim()));
			
			if((CommonFunction.checkNull(docId).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((CommonFunction.checkNull(docId).trim()));
			
			if((CommonFunction.checkNull(bankVo.getFinancialYear()).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((CommonFunction.checkNull(bankVo.getFinancialYear()).trim()));
			
			if((CommonFunction.checkNull(businessDate).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((CommonFunction.checkNull(businessDate).trim()));
			if((CommonFunction.checkNull(makerId).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((CommonFunction.checkNull(makerId).trim()));
			insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("insert query ------"+insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			}
			
			try {
			status	=	ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			/*if(status)
			{	
				bufInsSql =null;
				bufInsSql.append("update cr_ques_answer_dtl que,cr_financial_data_dtl fin set que.org_value=fin.param_value where que.deal_id=fin.deal_id and que.question_code=fin.PARAM_CODE  "
						+ " que.deal_id='"+CommonFunction.checkNull(caseId).trim()+"' and document_type='"+CommonFunction.checkNull(docType).trim()+"' ");  
				ArrayList qryUUpdlist=new ArrayList();
				qryUUpdlist.add(bufInsSql.toString());
				boolean updStat = ConnectionDAO.sqlInsUpdDelete(qryUUpdlist);
				logger.info("Update Status::::::"+updStat);

			}*/
			} 
			catch (Exception e) {
				logger.info("Error messages "+e.getMessage());
			}
						
			return status;
		}
	
	
	@Override
	public int getYearForFinancialAnalysis() {
		ArrayList list = new ArrayList();
		int count = 0;
		try {
			logger.info("In getApprovalfromPmst.............inside ejb server file............Dao Impl");
			String query = "select PARAMETER_VALUE from parameter_mst where parameter_key = 'FINANCIAL_ANALYSIS_SET_YEAR'";
			FinancialAnalysisVo vO = null;
			String countStr = ConnectionDAO.singleReturn(query);
			count = Integer.parseInt(countStr);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	@Override
	public ArrayList getUploadedData(String dealId, String userId, String sourceType) {
		logger.info("In getScheme()");
		ArrayList reportlist = new ArrayList();
		String[] pCode;
		String[] year1;
		String[] year2;
		String[] year3;
		String[] year4;
		String[] year5;
		ArrayList<String>tparameter=new ArrayList<String>();
		ArrayList<String>tyear1=new ArrayList<String>();
		ArrayList<String>tyear2=new ArrayList<String>();
		ArrayList<String>tyear3=new ArrayList<String>();
		ArrayList<String>tyear4=new ArrayList<String>();
		ArrayList<String>tyear5=new ArrayList<String>();
		ReportsForm reportName = null;
		String query=null;
		ArrayList totalList =new ArrayList(); 
		ArrayList result=new ArrayList();
		
		try 
		{		
			 query ="SELECT PARAMETER,YEAR1,YEAR2,YEAR3,YEAR4,YEAR5 " +
					"from cr_financial_analysis_upload where DEAL_ID='"+CommonFunction.checkNull(dealId).trim()+"' " +
					"and SOURCE_TYPE='"+CommonFunction.checkNull(sourceType).trim()+"' and MAKER_ID='"+CommonFunction.checkNull(userId).trim()+"';";		
			logger.info("getUploadedData  query  :   "+query);		
			totalList = ConnectionDAO.sqlSelect(query.toString());
			for (int i = 0; i < totalList.size(); i++) 
			{
				ArrayList<String> data = (ArrayList) totalList.get(i);
				tparameter.add(data.get(0));
				tyear1.add(data.get(1));
				tyear2.add(data.get(2));
				tyear3.add(data.get(3));
				tyear4.add(data.get(4));
				tyear5.add(data.get(5));
				data.clear();
				data=null;
			}
			pCode=(String[])tparameter.toArray(new String[0]);
			year1=(String[])tyear1.toArray(new String[0]);
			year2=(String[])tyear2.toArray(new String[0]);
			year3=(String[])tyear3.toArray(new String[0]);
			year4=(String[])tyear4.toArray(new String[0]);
			year5=(String[])tyear5.toArray(new String[0]);
			
			result.add(pCode);
			result.add(year1);
			result.add(year2);
			result.add(year3);
			result.add(year4);
			result.add(year5);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			
			pCode=null;
			year1=null;
			year2=null;
			year3=null;
			year4=null;
			year5=null;
			tparameter.clear();
			tparameter=null;
			tyear1.clear();
			tyear1=null;
			tyear2.clear();
			tyear2=null;
			tyear3.clear();
			tyear3=null;
			tyear4.clear();
			tyear4=null;
			tyear5.clear();
			tyear5=null;
			query=null;
			totalList.clear();
			totalList=null;
		}
		return result;
	}
	
	
	
	
	@Override
	public ArrayList getParamDetailDetails(String sourceType) {
		ArrayList<Object> list=new ArrayList<Object>();
//		String query="";
		StringBuilder query=new StringBuilder();
		
		try{
			
		if(sourceType.equalsIgnoreCase(""))
		{
			query.append("SELECT PARAM_CODE,PARAM_NAME,IFNULL(AUTO_CALCULATED,'N'),FINANCIAL_FORMULA FROM cr_financial_param WHERE REC_STATUS='A'");
			
		}
		else
		{
			query.append("SELECT PARAM_CODE,PARAM_NAME,SUB_TYPE,CASE SUB_TYPE WHEN 'A' THEN 'ASSET' WHEN 'L' THEN 'LIABILITY' END AS SUB_TYPE_DESC,IFNULL(AUTO_CALCULATED,'N'),FINANCIAL_FORMULA,NEGATIVE_ALLOWED FROM cr_financial_param WHERE REC_STATUS='A' and SOURCE_TYPE='"+CommonFunction.checkNull(sourceType)+"' AND NEGATIVE_ALLOWED IN('A','X') ORDER BY SUB_TYPE,SEQUENCE_NO");
			
		}
		logger.info("getParamDetailDetails query : "+query.toString());
		ArrayList paramDetail = ConnectionDAO.sqlSelect(query.toString());
		
		
		query=null;
		
		for(int i=0;i<paramDetail.size();i++){

			ArrayList subParamDetail=(ArrayList)paramDetail.get(i);
			if(subParamDetail.size()>0)
			{
				FinancialAnalysisVo vo = new FinancialAnalysisVo();
				vo.setParameCode((CommonFunction.checkNull(subParamDetail.get(0))).trim());
				//vo.setDealNo(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subParamDetail.get(1))).trim());
				vo.setParamName((CommonFunction.checkNull(subParamDetail.get(1))).trim());
				vo.setSubType((CommonFunction.checkNull(subParamDetail.get(2))).trim());
				vo.setSubTypeDesc((CommonFunction.checkNull(subParamDetail.get(3))).trim());
				vo.setAutoCalculated((CommonFunction.checkNull(subParamDetail.get(4))).trim());
				vo.setFinancialFormula((CommonFunction.checkNull(subParamDetail.get(5))).trim());
				vo.setNegativeAllow((CommonFunction.checkNull(subParamDetail.get(6))).trim());
				
                list.add(vo);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.info("paramDetail size is .................................."+list.size());
		return list;
	}
	@Override
	public ArrayList getParamMinusDetails(String sourceType) {
		ArrayList<Object> list=new ArrayList<Object>();
//		String query="";
		StringBuilder query=new StringBuilder();
		try{
			
			query.append("SELECT PARAM_CODE,PARAM_NAME,SUB_TYPE,CASE SUB_TYPE WHEN 'A' THEN 'ASSET' WHEN 'L' THEN 'LIABILITY' END AS SUB_TYPE_DESC,IFNULL(AUTO_CALCULATED,'N') FROM cr_financial_param WHERE REC_STATUS='A' and SOURCE_TYPE='"+CommonFunction.checkNull(sourceType)+"' AND NEGATIVE_ALLOWED IN('A','X') ORDER BY SUB_TYPE,SEQUENCE_NO");
	
		logger.info("query : "+query);
		ArrayList paramDetail = ConnectionDAO.sqlSelect(query.toString());
		
		query=null;
		
		for(int i=0;i<paramDetail.size();i++){

			ArrayList subParamDetail=(ArrayList)paramDetail.get(i);
			if(subParamDetail.size()>0)
			{
				FinancialAnalysisParamVo vo = new FinancialAnalysisParamVo();
				vo.setNegativeParamCode((CommonFunction.checkNull(subParamDetail.get(0))).trim());
				//vo.setDealNo(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(subParamDetail.get(1))).trim());
				vo.setNegativeparamName((CommonFunction.checkNull(subParamDetail.get(1))).trim());
				vo.setSubType((CommonFunction.checkNull(subParamDetail.get(2))).trim());
				vo.setSubTypeDesc((CommonFunction.checkNull(subParamDetail.get(3))).trim());
				vo.setAutoCalculated((CommonFunction.checkNull(subParamDetail.get(4))).trim());
                list.add(vo);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.info("paramDetail size is .................................."+list.size());
		return list;
	}
	@Override
	public String saveBalanceSheet(Object ob, String caseId, String stageId , String docType , String docId , String businessDate , String makerId , int year) {
		logger.info("In saveBalanceSheet DaoImpl.............................. ");
		FinancialAnalysisVo vo=(FinancialAnalysisVo)ob;
		FinancialAnalysisVo vos;
		UnderwritingDocUploadVo uwDocVo = null;
		boolean status=false;
		boolean flag=false;
		boolean updateFlag= false;
		String maxId ="";
		String ids="";
		int z=0;
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		
		    String pCode[] = vo.getpCode();
		    String pName[] = vo.getpName();
		    String year1[] = vo.getYear1();
		    
		    String questionId[] = vo.getFinancialIds();
		    String analysisYear[]=vo.getAnalysisYear();
		    ArrayList<FinancialAnalysisVo> finanFormula=new ArrayList<FinancialAnalysisVo>();
		    ArrayList qryList=new ArrayList();
			StringBuffer bufInsSql =	new StringBuffer();
			String[][] pCodeWithYear=new String [50][50];
			ArrayList<CodeDescVo> list = new ArrayList<CodeDescVo>();
			//sachin work start
			try
			{
				
				String query = "select distinct question_desc,org_value from  cr_ques_answer_dtl  WHERE deal_id='"+CommonFunction.checkNull(caseId)+"' and document_type='"+CommonFunction.checkNull(docType)+"' and document_id = '"+docId+"' and last_updated_by is null and last_update_date is null ";
				logger.info("query--------"+query);
				ArrayList orgList = ConnectionDAO.sqlSelect(query);
				ArrayList subOrgValueList = new ArrayList();
				
				int size = orgList!=null?orgList.size():0;
				CodeDescVo codeDescVo = null;
				for(int i = 0;i<size;i++)
				{
					subOrgValueList = (ArrayList)orgList.get(i);
					codeDescVo= new CodeDescVo();
					if(subOrgValueList!=null && subOrgValueList.size()>0)
					{
						codeDescVo.setName(CommonFunction.checkNull(subOrgValueList.get(0)));
						codeDescVo.setValue(CommonFunction.checkNull(subOrgValueList.get(1)));
					}
					list.add(codeDescVo);
				}
				
				
				for(int i=0,k=0;i<pCode.length;i++)
				{
					k=0; 
					vos=new FinancialAnalysisVo();
					StringBuffer code =	new StringBuffer();
					code.append("SELECT PARAM_NAME,FINANCIAL_FORMULA FROM cr_financial_param WHERE PARAM_CODE='"+pCode[i]+"'");
					logger.info("In saveBalanceSheet Code Query  :  "+code.toString());
					ArrayList pval= ConnectionDAO.sqlSelect(code.toString());
					for(int t=0;t<pval.size();t++)
					{
						ArrayList subPval=(ArrayList)pval.get(t);
						if(subPval.size()>0)
						{
							vos.setParamName((CommonFunction.checkNull(subPval.get(0))).trim());
							vos.setFinancialFormula((CommonFunction.checkNull(subPval.get(1))).trim());
						    finanFormula.add(vos); 
						}
					}
				}			
			}
			catch (Exception e) 
			{e.printStackTrace();}

		    if(finanFormula.size()>0)
     		{
				 ArrayList ratioParamValue = new ArrayList();				
				 ArrayList ratioAnalysisList = new ArrayList();
				 ScriptEngineManager mgr = new ScriptEngineManager();
				 ScriptEngine engine = mgr.getEngineByName("JavaScript");
				 for(int i=0 ; i< finanFormula.size() ; i++)
				 {
					flag =false;
					FinancialAnalysisVo  vo1 = (FinancialAnalysisVo)finanFormula.get(i);
					String ratioExprFstYear ="";
					String ratioExprSecYear ="";
					String ratioExprThrdYear ="";
					String ratioExprForthYear ="";
					String ratioExprFifthYear ="";
					if(!vo1.getFinancialFormula().equalsIgnoreCase(""))
					{
							ratioExprFstYear =vo1.getFinancialFormula();
							ratioExprSecYear =vo1.getFinancialFormula();
							ratioExprThrdYear =vo1.getFinancialFormula();
							ratioExprForthYear =vo1.getFinancialFormula();
							ratioExprFifthYear =vo1.getFinancialFormula();
							
							FinancialAnalysisVo ratioVo = new FinancialAnalysisVo();
							if(pCode.length>0)
							{
								for(int j=0;j<pCode.length;j++)
								{
									String paramCode=pCode[j];
									if(ratioExprFstYear.indexOf(paramCode) >= 0)
									{
										try
										{
												String yearValue1= myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(year1[j]).trim())).toString();
												/*String yearValue2=myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(year2[j]).trim())).toString();
												String yearValue3=myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(year3[j]).trim())).toString();
												String yearValue4=myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(year4[j]).trim())).toString();
												String yearValue5=myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(year5[j]).trim())).toString();*/
												yearValue1="("+yearValue1+")";
												/*yearValue2="("+yearValue2+")";
												yearValue3="("+yearValue3+")";
												yearValue4="("+yearValue4+")";
												yearValue5="("+yearValue5+")";*/
												ratioExprFstYear =ratioExprFstYear.replaceAll(paramCode,yearValue1);
												/*ratioExprSecYear = ratioExprSecYear.replaceAll(paramCode,yearValue2); 
												ratioExprThrdYear = ratioExprThrdYear.replaceAll(paramCode,yearValue3);
												ratioExprForthYear = ratioExprForthYear.replaceAll(paramCode,yearValue4);
												ratioExprFifthYear =ratioExprFifthYear.replaceAll(paramCode,yearValue5);*/
												flag=true;
										}
										catch (Exception e) 
										{e.printStackTrace();}
									}	
								}
								BigDecimal d1 = new BigDecimal("0.00");
								if(flag)
								{
									try
									{
										if(!CommonFunction.checkNull(engine.eval(ratioExprFstYear)+"").equalsIgnoreCase(""))
							    		{
											d1= new BigDecimal(engine.eval(ratioExprFstYear)+"");//) engine.eval(ratioExprFstYear);											 
											Number reconNum =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(engine.eval(ratioExprFstYear)+"")).trim());  
							    			year1[z]=myFormatter.format(reconNum);
							    		}
									}
									catch (Exception e) 
									{e.printStackTrace();}
									/*try
									{
										if(!CommonFunction.checkNull(engine.eval(ratioExprSecYear)+"").equalsIgnoreCase(""))
							    		{
											d1=new BigDecimal(engine.eval(ratioExprSecYear)+"");// (BigDecimal) engine.eval(ratioExprSecYear);
											Number reconNum =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(engine.eval(ratioExprSecYear)+"")).trim());  
											year2[z]=myFormatter.format(reconNum);
							    		}
									}
									catch (Exception e) 
									{e.printStackTrace();}					    			
									try
									{
										if(!CommonFunction.checkNull(engine.eval(ratioExprThrdYear)+"").equalsIgnoreCase(""))
							    		{
											d1= new BigDecimal(engine.eval(ratioExprThrdYear)+""); //(Double) engine.eval(ratioExprThrdYear);
											Number reconNum =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(engine.eval(ratioExprThrdYear)+"")).trim());  
											year3[z]=myFormatter.format(reconNum);
							    		}
									}
									catch (Exception e) 
									{e.printStackTrace();}
								
								
									try{
										if(!CommonFunction.checkNull(engine.eval(ratioExprForthYear)+"").equalsIgnoreCase(""))
							    		{
											d1= new BigDecimal(engine.eval(ratioExprForthYear)+"");// (Double) engine.eval(ratioExprForthYear);
											Number reconNum =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(engine.eval(ratioExprForthYear)+"")).trim());  
											year4[z]=myFormatter.format(reconNum);
							    		}
									}
									catch (Exception e) 
									{e.printStackTrace();}
									try
									{
										if(!CommonFunction.checkNull(engine.eval(ratioExprFifthYear)+"").equalsIgnoreCase(""))
							    		{
											d1=new BigDecimal(engine.eval(ratioExprFifthYear)+"");// (Double) engine.eval(ratioExprFifthYear);
											Number reconNum =myFormatter.parse(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(engine.eval(ratioExprFifthYear)+"")).trim());  
											year5[z]=myFormatter.format(reconNum);
							    		}
									}
									catch (Exception e) 
									{e.printStackTrace();}*/
					    		}
								z++;
							}
					}
					else
					{
						year1[z] =year1[z];
						/*year2[z] = year2[z];
						year3[z] =year3[z]; 
						year4[z] = year4[z];
						year5[z]=year5[z];*/
						z++;
					}
				 }
			 }
     		logger.info("pCode.length ........ "+pCode.length );
		    if(pCode.length >0 )
		    {
		    	for(int i=0,k=0;i<pCode.length;i++,k++)
		    	{
		    		for(int j=0;j<5;j++)
		    		{
		    			if(j==0)
		    				pCodeWithYear[i][j]=year1[k];
		    			/*if(j==1)
		    				pCodeWithYear[i][j]=year2[k];
		    			if(j==2)
		    				pCodeWithYear[i][j]=year3[k];
		    			if(j==3)
		    				pCodeWithYear[i][j]=year4[k];
		    			if(j==4)
		    				pCodeWithYear[i][j]=year5[k];*/	    			
		    		}
		    	}		  		    
		    	String setYear = ConnectionDAO.singleReturn("select PARAMETER_VALUE from parameter_mst where parameter_key = 'FINANCIAL_ANALYSIS_SET_YEAR'");
		    	int getYear = Integer.parseInt(setYear);
		    	int currBusinessDateYear = year;//Integer.parseInt(StringEscapeUtils.escapeSql(vo.getCurrBusinessDateYear()));
		    	//currBusinessDateYear = currBusinessDateYear + (5 - getYear);
		    	try
		    	{
		    		if(!CommonFunction.checkNull(vo.getpCode()).equalsIgnoreCase("") )
		    		{
		    			maxId = ConnectionDAO.singleReturn("select max(question_id) from cr_ques_answer_dtl for update");
		    			int count=0;
		    			String paramValue[]=null;
		    			String paramVal="";
		    			for(int n=0;n<pCode.length;n++)
		    			{
		    				String query1=  "SELECT distinct question_code FROM cr_ques_answer_dtl WHERE deal_id='"+CommonFunction.checkNull(caseId)+"' and document_type='"+CommonFunction.checkNull(docType)+"' and  question_code='"+CommonFunction.checkNull(pCode[n])+"' and document_id = '"+docId+"' ";
		    				logger.info("query1--saveBalanceSheet-  :  >"+query1);
		    				String paramCode=ConnectionDAO.singleReturn(query1);
		    				logger.info("status1--saveBalanceSheet-->"+paramCode);
		    				
		    				if(! CommonFunction.checkNull(paramCode).equals(""))
		    				{
		    					if(paramVal.equals(""))	
		    						paramVal=paramCode;
		    					else
		    						paramVal=paramVal+","+paramCode;
		    					count++;
		    					paramValue=paramVal.split(",");
		    					logger.info("paramValue-length------>"+paramValue.length);
				     				logger.info("count-"+count);
		    				}
		    			}
		    			if(! CommonFunction.checkNull(paramValue).equals("") && CommonFunction.checkNull(vo.getUpdateFlag()).equalsIgnoreCase("Y"))
		    			{
		    				
		    				
		    				insertPrepStmtObject = new PrepStmtObject();
    						String query = "update  cr_ques_answer_dtl set last_updated_by= '"+makerId+"', last_update_date=DATE_ADD(STR_TO_DATE('"+businessDate+"', '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) WHERE deal_id='"+CommonFunction.checkNull(caseId)+"' and document_type='"+CommonFunction.checkNull(docType)+"' and document_id = '"+docId+"' and last_updated_by is null and last_update_date is null ";
    						logger.info("query--saveBalanceSheet-->"+query);
    						insertPrepStmtObject.setSql(query);
    						qryList.add(insertPrepStmtObject);	
    						updateFlag= true;
    						
		    			}
		    			else
		    			{
		    				/*insertPrepStmtObject = new PrepStmtObject();
    						String query = "update  cr_ques_answer_dtl set last_updated_by= '"+makerId+"', last_update_date=DATE_ADD(STR_TO_DATE('"+businessDate+"', '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) WHERE deal_id='"+CommonFunction.checkNull(caseId)+"' and document_type='"+CommonFunction.checkNull(docType)+"' and document_id = '"+docId+"' and last_updated_by is null and last_update_date is null ";
    						logger.info("query--saveBalanceSheet-->"+query);
    						insertPrepStmtObject.setSql(query);
    						qryList.add(insertPrepStmtObject);	*/
		    				
		    				QuesBankVo bankVo = null; 
			    			logger.info("CaseId In Impl================="+caseId);
			    			logger.info("StageId In Impl================"+stageId);
			    			for(int k=0;k<pCode.length; k++)
			    			{
			    				/*for(int y=currBusinessDateYear, pVal=0;y >currBusinessDateYear-5;y--,pVal++)
			    				{*/
			    					insertPrepStmtObject = new PrepStmtObject();
			    					bufInsSql =	new StringBuffer();	    			
			    					
			    					bufInsSql.append("update cr_ques_answer_dtl set answer_value = ?,financial_year=? where question_code=? and document_id = ? and  deal_id=? and stage_code=? and document_type = ? and last_updated_by is null ");
			    					
			    					if((CommonFunction.checkNull(year1[k])).trim().equalsIgnoreCase(""))
					    				insertPrepStmtObject.addString("0.00");
									else
										try {
											insertPrepStmtObject.addString(myFormatter.parse((year1[k].trim())).toString());
										} catch (ParseException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
			    					if((CommonFunction.checkNull(year)).trim().equalsIgnoreCase(""))
					    				insertPrepStmtObject.addNull();
					    			else
					    				insertPrepStmtObject.addInt(year);
			    					
			    					if((CommonFunction.checkNull(pCode[k])).trim().equalsIgnoreCase(""))
					    				insertPrepStmtObject.addNull();
					    			else
					    				insertPrepStmtObject.addString((pCode[k]).trim());
			    					
			    					if((CommonFunction.checkNull(docId)).trim().equalsIgnoreCase(""))
					    				insertPrepStmtObject.addNull();
					    			else
					    				insertPrepStmtObject.addString((docId).trim()); 
			    					
			    					if((CommonFunction.checkNull(caseId)).trim().equalsIgnoreCase(""))
					    				insertPrepStmtObject.addNull();
					    			else
					    				insertPrepStmtObject.addString((caseId).trim()); 
					    			
			    					if((CommonFunction.checkNull(stageId)).trim().equalsIgnoreCase(""))
					    				insertPrepStmtObject.addNull();
					    			else
					    				insertPrepStmtObject.addString((stageId).trim());
			    					
					    			if((CommonFunction.checkNull(docType)).trim().equalsIgnoreCase(""))
					    				insertPrepStmtObject.addNull();
					    			else
					    				insertPrepStmtObject.addString((docType).trim());
					    			
					    			insertPrepStmtObject.setSql(bufInsSql.toString());
					    			logger.info("IN saveBalanceSheet() update query ### "+insertPrepStmtObject.printQuery());				    				
					    			qryList.add(insertPrepStmtObject);
					    			
			    				//}
			    			}
		    			}
		    			QuesBankVo bankVo = null; 
		    			logger.info("CaseId In Impl================="+caseId);
		    			logger.info("StageId In Impl================"+stageId);
		    			if(updateFlag && CommonFunction.checkNull(vo.getUpdateFlag()).equalsIgnoreCase("Y"))
		    			for(int k=0;k<pCode.length; k++)
		    			{
		    				for(int y=currBusinessDateYear, pVal=0;y >currBusinessDateYear-5;y--,pVal++)
		    				{
		    					insertPrepStmtObject = new PrepStmtObject();
		    					bufInsSql =	new StringBuffer();	    			
		    					bufInsSql.append("insert into cr_ques_answer_dtl(deal_id,question_code, financial_year,answer_value,maker_id,maker_date,stage_code,question_id,document_type,document_id,question_desc,last_update_date,last_updated_by)");
				    			bufInsSql.append(" values ( ");
				    			bufInsSql.append(" ?," );//deal_id
				    			bufInsSql.append(" ?," );//question_code
				    			bufInsSql.append(" ?," );//financial_year
				    			bufInsSql.append(" ?," );//answer_value
				    			bufInsSql.append(" ?," );//maker_id
				    			bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND), " );//maker_date
				    			bufInsSql.append(" ?," );//stage_code
				    			bufInsSql.append(" ?," );//question_id
				    			bufInsSql.append(" ?," );//document_type
				    			bufInsSql.append(" ?," );//document_id
				    			bufInsSql.append(" ?," );//question_desc
				    			bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND), " );//last_update_date
				    			bufInsSql.append(" ? );" );//last_updated_by
				    			
				    			logger.info("financial deal id **************** "+vo.getLbxDealNo());
				    		  	
				    			logger.info("caseId For saveBalanceSheet ========="+caseId);
				    			//......................ref_id.......................................
				    			if((CommonFunction.checkNull(caseId)).trim().equalsIgnoreCase(""))
				    				insertPrepStmtObject.addNull();
				    			else
				    				insertPrepStmtObject.addString((caseId).trim()); 
				    			
				    			
				    			//......................Question_code.......................................
				    			if((CommonFunction.checkNull(pCode[k])).trim().equalsIgnoreCase(""))
				    				insertPrepStmtObject.addNull();
				    			else
				    				insertPrepStmtObject.addString((pCode[k]).trim());
				    				
				    			//......................financial_year.......................................
				    			if((CommonFunction.checkNull(year)).trim().equalsIgnoreCase(""))
				    				insertPrepStmtObject.addNull();
				    			else
				    				insertPrepStmtObject.addInt(year);
				    			
				    			
				    		//	......................answer_value.......................................
				    			if((CommonFunction.checkNull(pCodeWithYear[k][pVal])).trim().equalsIgnoreCase(""))
				    				insertPrepStmtObject.addString("0.00");
								else
									try {
										insertPrepStmtObject.addString(myFormatter.parse((pCodeWithYear[k][pVal].trim())).toString());
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
				    			
				    			//......................maker_id.......................................
				    			if((CommonFunction.checkNull(makerId)).trim().equals(""))//,MAKER_ID
				    				insertPrepStmtObject.addNull();
				    			else
				    				insertPrepStmtObject.addString((makerId).trim());
			
				    			
				    			//......................maker_date.......................................
				    			if((CommonFunction.checkNull(businessDate)).equalsIgnoreCase(""))//,MAKER_DATE
				    				insertPrepStmtObject.addNull();
				    			else
				    				insertPrepStmtObject.addString((businessDate));
				    			
				    			
				    			//......................Stage_code.......................................
				    			if((CommonFunction.checkNull(stageId)).trim().equalsIgnoreCase(""))
				    				insertPrepStmtObject.addNull();
				    			else
				    				insertPrepStmtObject.addString((stageId).trim());
				    			
				    			//......................question_id.......................................
				    			if((CommonFunction.checkNull(questionId[k]).trim()).equalsIgnoreCase(""))
				    				insertPrepStmtObject.addNull();
				    			else
				    				insertPrepStmtObject.addString((CommonFunction.checkNull(questionId[k]).trim()));
				    			
				    			//......................document_type.......................................
				    			if((CommonFunction.checkNull(docType)).trim().equalsIgnoreCase(""))
				    				insertPrepStmtObject.addNull();
				    			else
				    				insertPrepStmtObject.addString((docType).trim());
				    			logger.info("DocType in Impl==============="+docType);
				    			
				    			//......................document_id.......................................
				    			if((CommonFunction.checkNull(docId)).trim().equalsIgnoreCase(""))
				    				insertPrepStmtObject.addNull();
				    			else
				    				insertPrepStmtObject.addString((docId).trim()); 
				    			
				    			if((CommonFunction.checkNull(pName[k])).trim().equalsIgnoreCase(""))
				    				insertPrepStmtObject.addNull();
				    			else
				    				insertPrepStmtObject.addString((pName[k]).trim());
				    			logger.info("DocId in Impl==============="+docId);
				    			
				    			//......................last_update_date.......................................
				    			if((CommonFunction.checkNull(vo.getBusinessDate())).equalsIgnoreCase(""))//,MAKER_DATE
				    				insertPrepStmtObject.addNull();
				    			else
				    				insertPrepStmtObject.addString((vo.getBusinessDate()));
				    			
				    			
				    			//......................last_updates_by.......................................
				    			if((CommonFunction.checkNull(vo.getUserId())).trim().equals(""))//,MAKER_ID
				    				insertPrepStmtObject.addNull();
				    			else
				    				insertPrepStmtObject.addString((vo.getUserId()).trim());
				    			
				    			
				    			
				    			insertPrepStmtObject.setSql(bufInsSql.toString());
				    			logger.info("IN saveBalanceSheet() update query ### "+insertPrepStmtObject.printQuery());				    				
				    			qryList.add(insertPrepStmtObject);
				    			break;
	    			
		    				}
		    			}
		    		}
	         		if(qryList.size()>0)
	         		{
	         			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
	         			if(status)
	         			{
	         				ids = ConnectionDAO.singleReturn("select GROUP_CONCAT(question_id SEPARATOR ',') from cr_ques_answer_dtl where question_id >'"+maxId+"'" );
	         			}
	         			/*if(status)
	         			{
	         				bufInsSql =null;
	         				bufInsSql = new StringBuffer();
	         				bufInsSql.append("update cr_ques_answer_dtl q1 ,cr_ques_answer_dtl q2 set q1.org_value = q2.org_value where q1.question_code=q2.question_code and q1.last_updated_by is  null and q2.org_value is not null and q2.last_updated_by is not null "
	        						+ " and q1.deal_id='"+CommonFunction.checkNull(caseId).trim()+"' and q1.document_type='"+CommonFunction.checkNull(docType).trim()+"' and q1.stage_code = '"+stageId+"' ");  
  
	        				ArrayList qryUUpdlist=new ArrayList();
	        				qryUUpdlist.add(bufInsSql.toString());
	        				logger.info("update query ------"+bufInsSql.toString());
	        				boolean updStat = ConnectionDAO.sqlInsUpdDelete(qryUUpdlist);
	        				logger.info("Update Status::::::"+updStat);
	         			}*/
	         			bufInsSql = null;
	         			bufInsSql = new StringBuffer();
	         			PrepStmtObject insertPrepStmtObject1 = null;
	         			qryList = null;
	         			qryList = new ArrayList();
	         			bufInsSql.append("update cr_ques_answer_dtl set org_value = ? where question_desc=? and document_id = ? and  deal_id=? and stage_code=? and document_type = ? and last_updated_by is null ");
	         			for(int i= 0;i<list.size();i++)
	         			{
	         			CodeDescVo	codeDescVo = list.get(i);
	         			insertPrepStmtObject1  = new PrepStmtObject();
	         			
	         			if((CommonFunction.checkNull(codeDescVo.getValue())).trim().equalsIgnoreCase(""))
	         				insertPrepStmtObject1.addString("0.00");
						else
							try {
								insertPrepStmtObject1.addString(myFormatter.parse((codeDescVo.getValue())).toString());
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
    					if((CommonFunction.checkNull(StringEscapeUtils.escapeSql(codeDescVo.getName()))).trim().equalsIgnoreCase(""))
    						insertPrepStmtObject1.addNull();
		    			else
		    				insertPrepStmtObject1.addString(StringEscapeUtils.escapeSql(codeDescVo.getName()));
    					
    					
    					
    					if((CommonFunction.checkNull(docId)).trim().equalsIgnoreCase(""))
    						insertPrepStmtObject1.addNull();
		    			else
		    				insertPrepStmtObject1.addString((docId).trim()); 
    					
    					if((CommonFunction.checkNull(caseId)).trim().equalsIgnoreCase(""))
    						insertPrepStmtObject1.addNull();
		    			else
		    				insertPrepStmtObject1.addString((caseId).trim()); 
		    			
    					if((CommonFunction.checkNull(stageId)).trim().equalsIgnoreCase(""))
    						insertPrepStmtObject1.addNull();
		    			else
		    				insertPrepStmtObject1.addString((stageId).trim());
    					
		    			if((CommonFunction.checkNull(docType)).trim().equalsIgnoreCase(""))
		    				insertPrepStmtObject1.addNull();
		    			else
		    				insertPrepStmtObject1.addString((docType).trim());
		    			
		    			insertPrepStmtObject1.setSql(bufInsSql.toString());
		    			logger.info("IN saveBalanceSheet() update query ### "+insertPrepStmtObject1.printQuery());				    				
		    			qryList.add(insertPrepStmtObject1);
		    			
	         		}
	         			logger.info("qryList-----"+qryList.size());
	         			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
	         		}
				} catch (Exception e) {
					e.printStackTrace();
				}
			    logger.info("In saveBalanceSheet......................"+status);
		    }
		    maxId=null;		 
			return ids;
		
	}
	
	@Override
	public FinancialAnalysisVo getParamCode(String pcode,FinancialAnalysisVo vo) {
		// TODO Auto-generated method stub
		try{
		ArrayList pval= ConnectionDAO.sqlSelect("SELECT PARAM_NAME,SUB_TYPE,CASE SUB_TYPE WHEN 'A' THEN 'ASSET' WHEN 'L' THEN 'LIABILITY' END AS SUB_TYPE_DESC,IFNULL(AUTO_CALCULATED,'N'),FINANCIAL_FORMULA,NEGATIVE_ALLOWED FROM cr_financial_param WHERE PARAM_CODE='"+pcode+"'");
		for(int t=0;t<pval.size();t++){
			ArrayList subPval=(ArrayList)pval.get(t);
			if(subPval.size()>0)
			{
				vo.setParamName((CommonFunction.checkNull(subPval.get(0))).trim());
				vo.setSubType((CommonFunction.checkNull(subPval.get(1))).trim());
				vo.setSubTypeDesc((CommonFunction.checkNull(subPval.get(2))).trim());
				vo.setAutoCalculated((CommonFunction.checkNull(subPval.get(3))).trim());
				vo.setFinancialFormula((CommonFunction.checkNull(subPval.get(4))).trim());
				vo.setNegativeAllow((CommonFunction.checkNull(subPval.get(5))).trim());
                
			}
		}
		}
		 catch (Exception e) {
				e.printStackTrace();
			}
		return vo;
	}
	
	@Override
	public boolean saveDataForAuthor(String caseId , String docType,String businessDate,String makerId,String docId, List<QuesBankVo> list)
		{
			
			StringBuffer buffer = new StringBuffer();
			QuesBankVo bankVo = null;
			ArrayList qryList = new ArrayList();
				
			boolean status = false;
			PrepStmtObject insertPrepStmtObject =null;
			//logger.info("stage Code1 ============="+bankVo.getStageCode());
			logger.info("DocType in Impl================"+docType);
			logger.info("DocId in Impl================="+docId);
			logger.info("In SaveDataForAuthor.......................................");
			StringBuffer bufInsSql =	new StringBuffer();
			bufInsSql.append("insert into cr_ques_answer_dtl(question_id,Stage_Code,Document_Type,question_code , deal_id , document_id  , maker_date, maker_id , awswer_value, financial_year )");
			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?," ); //question_id
			bufInsSql.append(" ?," ); //Stage_Code
			bufInsSql.append(" ?," ); //Document_Type
			bufInsSql.append(" ?," ); //question_code
			bufInsSql.append(" ?," ); //deal_id
			bufInsSql.append(" ?," ); //document_id
			bufInsSql.append(" STR_TO_DATE(?,'"+dateFormat+"')," ); //maker_date
			bufInsSql.append(" ?," );	//maker_id
			bufInsSql.append(" ?," );	//awswer_value
			bufInsSql.append(" ? );" ); //financial_year
			
			int size = list==null?0:list.size();
			
			for(int i = 0;i<size; i++)
			{
				logger.info("List Size ============"+list.size());
				logger.info("DocId=================="+docId);
				logger.info("DocType================="+docType);
				logger.info("caseId================"+caseId);
				
				insertPrepStmtObject =  new PrepStmtObject();
				bankVo = list.get(i);
				
				if((CommonFunction.checkNull(bankVo.getQuestionId()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(bankVo.getQuestionId()).trim()));
				
				
				
				if((CommonFunction.checkNull(bankVo.getStageCode()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(bankVo.getStageCode()).trim()));
				
				
				if((CommonFunction.checkNull(docType).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(docType).trim()));
				
				if((CommonFunction.checkNull(bankVo.getQuestionCode()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(bankVo.getQuestionCode()).trim()));
				if((CommonFunction.checkNull(caseId).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(caseId).trim()));
				
				if((CommonFunction.checkNull(docId).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(docId).trim()));
				
				if((CommonFunction.checkNull(businessDate).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(businessDate).trim()));
				if((CommonFunction.checkNull(makerId).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(makerId).trim()));
				
				
				
				if((CommonFunction.checkNull(bankVo.getAnswerValue()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(bankVo.getAnswerValue()).trim()));
				
				
				
				if((CommonFunction.checkNull(bankVo.getFinancialYear()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(bankVo.getFinancialYear()).trim()));
				
				
				insertPrepStmtObject.setSql(bufInsSql.toString());
				qryList.add(insertPrepStmtObject);
			}
			
			try 
				{
					status	=	ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
					/*if(status)
					{	
						bufInsSql =null;
						bufInsSql.append("update cr_ques_answer_dtl que,cr_financial_data_dtl fin set que.org_value=fin.param_value where que.deal_id=fin.deal_id and que.question_code=fin.PARAM_CODE  "
								+ " que.deal_id='"+CommonFunction.checkNull(caseId).trim()+"' and document_type='"+CommonFunction.checkNull(docType).trim()+"' ");  
						ArrayList qryUUpdlist=new ArrayList();
						qryUUpdlist.add(bufInsSql.toString());
						boolean updStat = ConnectionDAO.sqlInsUpdDelete(qryUUpdlist);
						logger.info("Update Status::::::"+updStat);

					}*/
				} 
			catch (Exception e) 
				{
					logger.info("Error messages "+e.getMessage());
				}
				
			return status;
		}


	@Override
	public boolean checkQuestionBankStatus(String caseId, String stageId,
			String docType, String businessDate, String documentId) {
			logger.info("checkQuestionBankStatus=================");
		StringBuffer query = new StringBuffer();
		
		
		query.append("select count(1)  from  cr_ques_answer_dtl where document_type = '"+docType+"'  and stage_code = '"+stageId+"' and deal_id = '"+caseId+"' and document_id = '"+documentId+"' and last_updated_by is null "); 
		logger.info("select Count ----------"+query);
		String val=ConnectionDAO.singleReturn(query.toString());
		int count=Integer.parseInt(val);
		return count>0?false:true;
		
		
	}
	//space by saorabh 
	public ArrayList getQuestionAnswerList(String caseId,String stageId,String docType,String businessDate,String documentId) {
		logger.info("getQuestionAnswerList------------------>");
		ArrayList<Object> list=new ArrayList<Object>();
//		String query="";
		StringBuilder query=new StringBuilder();
		try{

			String query1="select  distinct f.question_code,f.question_desc,f.answer_value ,f.question_id,f.org_value,f.financial_year from cr_ques_answer_dtl f  where  "+
			" f.document_type = '"+docType+"' and document_id = '"+documentId+"'  and f.stage_code = '"+stageId+"' and f.deal_id = '"+caseId+"' and last_updated_by is null and last_update_date is null  ";
			logger.info("In getQuestionAnswerList query1 "+query1);
			ArrayList fParamCodelist=ConnectionDAO.sqlSelect(query1);
			
			logger.info("fParamCodelist...size...."+fParamCodelist.size());
			/*query.append("select f.question_code ,f.answer_value ,f.question_id,f.question_desc " +
			" from cr_ques_answer_dtl f  " +
			"where  f.document_type = '"+docType+"'  and f.stage_code = '"+stageId+"' and f.deal_id = '"+caseId+"' and last_updated_by is null and last_update_date is null group by f.question_code,f.FINANCIAL_YEAR asc");
			logger.info("In getdealAllMinusParamDeatils query : "+query.toString());
		
		ArrayList dataList = ConnectionDAO.sqlSelect(query.toString());
		logger.info("dataList........size......"+dataList.size());
		String[] paramValues=null ;
		
		query=null;
		String paramCode="";
		String paramValue="";
		String finnId="";
		String[] finnIdss=null ;*/
		QuesBankVo vo = new QuesBankVo();
		
		
		    logger.info("fParamCode---size----- "+fParamCodelist.size());
			for(int k=0;k<fParamCodelist.size();k++)
			{
				ArrayList subList=(ArrayList) fParamCodelist.get(k);
				if(subList.size()>0)
				{
				 vo = new QuesBankVo();
				 vo.setQuestionCode(CommonFunction.checkNull(subList.get(0)));
				 vo.setQuestionDesc(CommonFunction.checkNull(subList.get(1)));
					if(!CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase(""))
		    		{
		    			Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());  
			    		vo.setFirstYear(myFormatter.format(reconNum));
		    		}
				 vo.setQuestionId(CommonFunction.checkNull(subList.get(3)).trim());
				 if(!CommonFunction.checkNull(subList.get(4)).equalsIgnoreCase(""))
		    		{
		    			Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(4))).trim());  
			    		vo.setOrgValue(myFormatter.format(reconNum));
		    		}
				 vo.setFinancialYear(CommonFunction.checkNull(subList.get(5)));
				}
				list.add(vo);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.info("paramDetail size is .................................."+list.size());
		return list;
	}


	@Override
	public boolean statusForQuestionAnswer(String caseId, String stageId,
			String docType, String businessDate,String documentId) {
		logger.info("statusForQuestionAnswer=================");
		StringBuffer query = new StringBuffer();
		
		
		query.append("select count(1)  from  cr_ques_answer_dtl where document_id = '"+documentId+"' and document_type = '"+docType+"'  and stage_code = '"+stageId+"' and deal_id = '"+caseId+"' and answer_value is null "); 
		logger.info("select Count ----------"+query);
		String val=ConnectionDAO.singleReturn(query.toString());
		int count=Integer.parseInt(val);
		return count>0?true:false;
	
	}


	@Override
	public ArrayList dataValidationProcess(String caseId , String businessDate , String docId) 
		{
			logger.info("In DataValidationProcess In DataAuthenticationDaoImpl..................");
			
			StringBuffer buffer = new StringBuffer();
			ArrayList<QuesBankVo> returnList =  new ArrayList<QuesBankVo>();
			
			QuesBankVo bankVo = null;
			UnderwritingDocUploadVo uwDocVo = null;
			Date date =  DateUtility.convertToDate(businessDate);
			
			businessDate = FormatUtility.dateFormat(date);
			StringBuilder query=new StringBuilder();
			StringBuilder query1=new StringBuilder();
			
			logger.info("caseId In dataValidationProcess ================="+caseId);
			
			query.append("select  question_code , question_desc , document_type , answer_value , financial_year from cr_ques_answer_dtl where deal_id = '"+caseId+"' "); 
			query1.append("select  param_code , financial_year , document_Id , PARAM_VALUE from cr_financial_data_dtl where deal_Id = '"+caseId+"' ");
			String val=ConnectionDAO.singleReturn(query.toString());
						
			try 
				{
					ArrayList list = (ArrayList)ConnectionDAO.sqlSelect(query.toString());
					ArrayList sublist = new ArrayList();
					int size = list!=null?list.size():0;
					for(int i = 0;i<size;i++)
						{
							sublist =  (ArrayList)list.get(i);
							if(sublist.size()>0)
								{
									bankVo =  new QuesBankVo();
									bankVo.setQuestionCode(CommonFunction.checkNull(sublist.get(0)));
									bankVo.setQuestionDesc(CommonFunction.checkNull(sublist.get(1)));
									bankVo.setDocumentType(CommonFunction.checkNull(sublist.get(2)));
									bankVo.setAnswerValue(CommonFunction.checkNull(sublist.get(3)));
									bankVo.setFinancialYear(CommonFunction.checkNull(sublist.get(4)));
								
									logger.info("Question Code In dataValidationProcess ======"+bankVo.getQuestionCode());
									logger.info("Question Description In dataValidationProcess ======"+bankVo.getQuestionDesc());
									logger.info("Document Type In dataValidationProcess ======"+bankVo.getDocumentType());
									logger.info("Answer Value In dataValidationProcess ======"+bankVo.getAnswerValue());
									logger.info("Financial year In dataValidationProcess ======"+bankVo.getFinancialYear());
								}
							returnList.add(bankVo);
						}
				}
			catch (Exception e) 
			{
				logger.info("Error messages "+e.getMessage());
			}
			return returnList;
		}


	
}