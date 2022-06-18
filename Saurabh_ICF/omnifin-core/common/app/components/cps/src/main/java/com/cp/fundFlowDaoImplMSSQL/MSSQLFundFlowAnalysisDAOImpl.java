package com.cp.fundFlowDaoImplMSSQL;

import java.util.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.cp.fundFlowDao.FundFlowAnalysisDAO;
import com.cp.vo.BankAccountAnalysisVo;


import com.cp.vo.CodeDescVo;
import com.cp.vo.CommonDealVo;
import com.cp.vo.FundFlowAutherVo;
import com.cp.vo.FundFlowDownloadUploadVo; // Add BY ABhishek
import com.cp.vo.ObligationVo;
import com.cp.vo.SalesAnalysisVo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class MSSQLFundFlowAnalysisDAOImpl implements FundFlowAnalysisDAO 
{
	private static final Logger logger = Logger.getLogger(MSSQLFundFlowAnalysisDAOImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	//String selectFrom = resource.getString("lbl.selectFrom");
	DecimalFormat myFormatter = new DecimalFormat("###,###.##");
	//change by sachin
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");
	//end by sachin
	
	public int saveBankAccountAnalysis(Object ob)
	
	{
		BankAccountAnalysisVo vo=(BankAccountAnalysisVo)ob;
		int rowcount=0;
		int maxId=0;
		PrepStmtObject insertPrepStmtObject =null;
		StringBuffer bufInsSql =null;
			try{
				
				ArrayList qryList = new ArrayList();
			
			 bufInsSql =	new StringBuffer();
			insertPrepStmtObject=new PrepStmtObject();
			bufInsSql.append("insert into cr_bank_analysis_dtl(DEAL_ID,BANK_NAME,BANK_BRANCH,ACCOUNT_NO,ACCOUNT_TYPE,STATEMENT_YEAR,STATEMENT_MONTH," +
					"TOTAL_CR,TOTAL_DR,TOTAL_EMI,HIGHEST_BALANCE,LOWEST_BALANCE,CHECK_BOUNCING_FREQ,CHECK_BOUNCING_AMOUNT,OVER_LIMIT_FREQ," +
					"OVER_LIMIT_AMOUNT,REC_STATUS,BOUNCING_INWARD_OUTWARD,LIMIT_OBLIGATION,MAKER_ID,MAKER_DATE,TOTAL_CR_TXN,TOTAL_DR_TXN,VERIFICATION_METHOD,BALANCE_AMOUNT,REMARKS,INWARD_CHEQUE_RETURN_COUNT,OUTWARD_CHEQUE_RETURN_COUNT,TOTAL_DEBIT_ENTRY,TOTAL_CREDIT_ENTRY,TOTAL_INWARD_BOUNCING_PERC,TOTAL_OUTWARD_BOUNCING_PERC,REPAYMENT_FROM_THIS_ACCOUNT,BANKING_SINCE,JOINT_HOLDING,PRIMARY_ACCOUNT,AVGBANK_BALANCE,INPUTW_RETURN,OUTPUTW_RETURN,ODCCIMIT_AMOUNT,COLLATERAL_DETAILS)");
			bufInsSql.append(" values ( ");
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
			bufInsSql.append(" ?," );
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			bufInsSql.append("  ?," );
			bufInsSql.append("  ?," );
			bufInsSql.append("  ?," );
			bufInsSql.append("  ?," );
			bufInsSql.append("  ?," );
			
			bufInsSql.append("  ?," );
			bufInsSql.append("  ?," );
			bufInsSql.append("  ?," );
			bufInsSql.append("  ?," );
			bufInsSql.append("  ?," );
			bufInsSql.append("  ?," );
			
			bufInsSql.append("  ?," );
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			bufInsSql.append("  ?," );
			bufInsSql.append("  ?," );
			bufInsSql.append("  ?," );
			bufInsSql.append("  ?," );
			bufInsSql.append("  ?," );
			bufInsSql.append("  ?," );
			bufInsSql.append("  ?)" );
			
						
			if(CommonFunction.checkNull(vo.getLbxDealNo()).trim().equalsIgnoreCase(""))//DEAL_ID
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getLbxDealNo()).trim());
			
			if(CommonFunction.checkNull(vo.getLbxBankID()).trim().equalsIgnoreCase(""))//BANK_NAME
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getLbxBankID()).trim());
			
			if(CommonFunction.checkNull(vo.getLbxBranchID()).trim().equalsIgnoreCase(""))//BANK_BRANCH
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getLbxBranchID()).trim());
				
			if(CommonFunction.checkNull(vo.getAccountNo()).trim().equalsIgnoreCase(""))//ACCOUNT_NO
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getAccountNo()).trim());
			if(CommonFunction.checkNull(vo.getAccountType()).trim().equalsIgnoreCase(""))//ACCOUNT_TYPE
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getAccountType()).trim());
			
			if(CommonFunction.checkNull(vo.getYear()).trim().equalsIgnoreCase(""))//STATEMENT_YEAR
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getYear()).trim());
			if(CommonFunction.checkNull(vo.getMonth()).trim().equalsIgnoreCase(""))//STATEMENT_MONTH
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getMonth()).trim());
			
			if(CommonFunction.checkNull(vo.getCredit()).trim().equalsIgnoreCase(""))//TOTAL_CR
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getCredit()).trim()).toString());
			if(CommonFunction.checkNull(vo.getDebit()).trim().equalsIgnoreCase(""))//TOTAL_DR
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getDebit()).trim()).toString());
			if(CommonFunction.checkNull(vo.getTotalEMI()).trim().equalsIgnoreCase(""))//TOTAL_EMI
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getTotalEMI()).trim()).toString());
			if(CommonFunction.checkNull(vo.getHighestBalance()).trim().equalsIgnoreCase(""))//HIGHEST_BALANCE
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getHighestBalance()).trim()).toString());
			if(CommonFunction.checkNull(vo.getLowestBalance()).trim().equalsIgnoreCase(""))//LOWEST_BALANCE
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getLowestBalance()).trim()).toString());
					
			if(CommonFunction.checkNull(vo.getCheckBounceFrequency()).trim().equalsIgnoreCase(""))//CHECK_BOUNCING_FREQ
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getCheckBounceFrequency()).trim());
			if(CommonFunction.checkNull(vo.getCheckBounceAmount()).trim().equalsIgnoreCase(""))//CHECK_BOUNCING_AMOUNT
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getCheckBounceAmount()).trim()).toString());
			if(CommonFunction.checkNull(vo.getOverLimitFrequency()).trim().equalsIgnoreCase(""))//OVER_LIMIT_FREQ
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getOverLimitFrequency()).trim());
			logger.info("over limit frequency .... "+(vo.getOverLimitFrequency()).trim());
			if(CommonFunction.checkNull(vo.getOverLimitAmount()).trim().equalsIgnoreCase(""))//OVER_LIMIT_AMOUNT
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getOverLimitAmount()).trim()).toString());
			insertPrepStmtObject.addString("P");//,REC_STATUS
			
			if(CommonFunction.checkNull(vo.getChequeBouncing()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getChequeBouncing()).trim());
			
			logger.info("value of cyheque bouncing is "+vo.getChequeBouncing());
			
			if(CommonFunction.checkNull(vo.getLimitObligation()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getLimitObligation()).trim());			
			logger.info("value of cyheque bouncing is "+vo.getLimitObligation());
			
			if((CommonFunction.checkNull(vo.getUserId())).trim().equals(""))//,MAKER_ID
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getUserId()).trim());

			if((CommonFunction.checkNull(vo.getBusinessDate())).equalsIgnoreCase(""))//,MAKER_DATE
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getBusinessDate()));
			
			//Added by Arun
			if(CommonFunction.checkNull(vo.getTotalCreditTxn()).trim().equalsIgnoreCase(""))//TOTAL_CR_TXN
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getTotalCreditTxn()).trim()).toString());
			
			if(CommonFunction.checkNull(vo.getTotalDebitTxn()).trim().equalsIgnoreCase(""))//TOTAL_DR_TXN
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getTotalDebitTxn()).trim()).toString());
			
			if(CommonFunction.checkNull(vo.getVarificationMethod()).trim().equalsIgnoreCase(""))//VERIFICATION_METHOD
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getVarificationMethod()).trim());
			
			if(CommonFunction.checkNull(vo.getBalanceAmount()).trim().equalsIgnoreCase(""))//BALANCE_AMOUNT
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getBalanceAmount()).trim()).toString());
			
			if(CommonFunction.checkNull(vo.getRemarks()).trim().equalsIgnoreCase(""))//REMARKS
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getRemarks()).trim());
			
			if(CommonFunction.checkNull(vo.getInwardChequeReturnCount()).trim().equalsIgnoreCase(""))//INWARD_CHEQUE_RETURN_COUNT
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getInwardChequeReturnCount()).trim()).toString());
			
			if(CommonFunction.checkNull(vo.getOutwardChequeReturnCount()).trim().equalsIgnoreCase(""))//OUTWARD_CHEQUE_RETURN_COUNT
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getOutwardChequeReturnCount()).trim()).toString());
			
			if(CommonFunction.checkNull(vo.getTotalDebitEntry()).trim().equalsIgnoreCase(""))//TOTAL_DEBIT_ENTRY
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getTotalDebitEntry()).trim()).toString());
			
			if(CommonFunction.checkNull(vo.getTotalCreditEntry()).trim().equalsIgnoreCase(""))//TOTAL_CREDIT_ENTRY
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getTotalCreditEntry()).trim()).toString());
			
			if(CommonFunction.checkNull(vo.getTotalInwardBouncingPerc()).trim().equalsIgnoreCase(""))//TOTAL_INWARD_BOUNCING_PERC
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getTotalInwardBouncingPerc()).trim()).toString());
			
			if(CommonFunction.checkNull(vo.getTotalOutwardBouncingPerc()).trim().equalsIgnoreCase(""))//TOTAL_OUTWARD_BOUNCING_PERC
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getTotalOutwardBouncingPerc()).trim()).toString());
			
			if(CommonFunction.checkNull(vo.getRepaymentFromThisAccount()).trim().equalsIgnoreCase(""))//REPAYMENT_FROM_THIS_ACCOUNT
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getRepaymentFromThisAccount()).trim());
			
			if(CommonFunction.checkNull(vo.getBankingSince()).trim().equalsIgnoreCase(""))//BANKING_SINCE
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getBankingSince()).trim());
			
			if(CommonFunction.checkNull(vo.getJointHolding()).trim().equalsIgnoreCase(""))//JOINT_HOLDING
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getJointHolding()).trim());
			
			if(CommonFunction.checkNull(vo.getPrimaryAccount()).trim().equalsIgnoreCase(""))//PRIMARY_ACCOUNT
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getPrimaryAccount().trim());
			
			if(CommonFunction.checkNull(vo.getAvgBankBalance()).trim().equalsIgnoreCase(""))//AVGBANK_BALANCE
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getAvgBankBalance()).trim()).toString());
			
			if(CommonFunction.checkNull(vo.getInputWReturn()).trim().equalsIgnoreCase(""))//INPUTW_RETURN
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getInputWReturn()).trim()).toString());
			
			if(CommonFunction.checkNull(vo.getOutputWReturn()).trim().equalsIgnoreCase(""))//OUTPUTW_RETURN
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getOutputWReturn()).trim()).toString());
			
			if(CommonFunction.checkNull(vo.getOdccimitamount()).trim().equalsIgnoreCase(""))//ODCCIMIT_AMOUNT
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getOdccimitamount()).trim()).toString());
			
			if(CommonFunction.checkNull(vo.getCollateralDetails()).trim().equalsIgnoreCase(""))//COLLATERAL_DETAILS
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getCollateralDetails().trim());
			
			//Added By Arun Ends Here
				insertPrepStmtObject.setSql(bufInsSql.toString());
				
				logger.info("IN saveBankAccountAnalysis() insert query1 ### "+insertPrepStmtObject.printQuery());
				
				qryList.add(insertPrepStmtObject);
		      
		        boolean status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		        
			    logger.info("In saveBankAccountAnalysis......................"+status);
				if(status)
				{
					StringBuilder query3=new StringBuilder();
				query3.append("Select max(BANK_ANALYSIS_ID) from cr_bank_analysis_dtl  WITH (ROWLOCK) ");
				StringBuilder id=new StringBuilder();
					 id.append(ConnectionDAO.singleReturn(query3.toString()));
					 maxId=Integer.parseInt(id.toString());
					 logger.info("maxId : "+maxId);
					 query3=null;
					 id=null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}finally{
				 bufInsSql =null;
			}
		
			return maxId;
		
	}
	
	public ArrayList getBankAccountAnalysisDetails(String bankAcctAnsId,String dealId,String recStatus) {
		ArrayList<Object> list=new ArrayList<Object>();
		try{
			StringBuilder query=new StringBuilder();
	 query.append("select b.DEAL_ID,d.DEAL_NO,b.BANK_NAME,bnk.BANK_NAME,b.BANK_BRANCH,br.BANK_BRANCH_NAME,ACCOUNT_NO,ACCOUNT_TYPE," +
				"STATEMENT_YEAR,STATEMENT_MONTH,TOTAL_CR,TOTAL_DR,TOTAL_EMI,HIGHEST_BALANCE,LOWEST_BALANCE,CHECK_BOUNCING_FREQ," +
				"CHECK_BOUNCING_AMOUNT,OVER_LIMIT_FREQ,OVER_LIMIT_AMOUNT,BANK_ANALYSIS_ID,BOUNCING_INWARD_OUTWARD,LIMIT_OBLIGATION,deal.CUSTOMER_NAME," +
				" TOTAL_CR_TXN,TOTAL_DR_TXN,VERIFICATION_METHOD,BALANCE_AMOUNT,REMARKS,b.INWARD_CHEQUE_RETURN_COUNT,b.OUTWARD_CHEQUE_RETURN_COUNT,b.TOTAL_DEBIT_ENTRY,b.TOTAL_CREDIT_ENTRY,b.TOTAL_INWARD_BOUNCING_PERC,b.TOTAL_OUTWARD_BOUNCING_PERC,REPAYMENT_FROM_THIS_ACCOUNT, " );
	 query.append(dbo);
	 query.append("DATE_FORMAT(BANKING_SINCE,'"+dateFormat+"'),JOINT_HOLDING,PRIMARY_ACCOUNT,AVGBANK_BALANCE,INPUTW_RETURN,OUTPUTW_RETURN,ODCCIMIT_AMOUNT,COLLATERAL_DETAILS from cr_bank_analysis_dtl b left join cr_deal_dtl d on b.DEAL_ID=d.DEAL_ID and b.DEAL_ID=d.DEAL_ID" +
				" left join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID left join com_bank_m bnk on bnk.BANK_ID=b.BANK_NAME" +
				" left join com_bankbranch_m br on br.BANK_BRANCH_ID=b.BANK_BRANCH" +
				" where b.REC_STATUS='"+recStatus+"'");
		if(bankAcctAnsId.trim()!=null && !bankAcctAnsId.trim().equalsIgnoreCase(""))
		{
			logger.info("bankAcctAnsId: "+bankAcctAnsId);
			query.append(" and b.BANK_ANALYSIS_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(bankAcctAnsId)).trim());
		}
		if(dealId!=null && !dealId.equalsIgnoreCase(""))
		{
			logger.info("dealId: "+dealId);
			query.append(" and b.DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
		}
		logger.info("getBankAccountAnalysisDetails: "+query.toString());
		BankAccountAnalysisVo vo = null;
		ArrayList bankAcDetail = ConnectionDAO.sqlSelect(query.toString());
		
		query=null;
		
		for(int i=0;i<bankAcDetail.size();i++){

			ArrayList bankAcDetail1=(ArrayList)bankAcDetail.get(i);
			if(bankAcDetail1.size()>0)
			{
				vo = new BankAccountAnalysisVo();
				vo.setLbxDealNo((CommonFunction.checkNull(bankAcDetail1.get(0))).trim());
				vo.setDealNo((CommonFunction.checkNull(bankAcDetail1.get(1))).trim());
				vo.setLbxBankID((CommonFunction.checkNull(bankAcDetail1.get(2))).trim());
				vo.setBankName((CommonFunction.checkNull(bankAcDetail1.get(3))).trim());
				vo.setLbxBranchID((CommonFunction.checkNull(bankAcDetail1.get(4))).trim());
				vo.setBankBranch((CommonFunction.checkNull(bankAcDetail1.get(5))).trim());
				vo.setAccountNo((CommonFunction.checkNull(bankAcDetail1.get(6))).trim());
				vo.setAccountType((CommonFunction.checkNull(bankAcDetail1.get(7))).trim());
				
				vo.setYear((CommonFunction.checkNull(bankAcDetail1.get(8))).trim());
				vo.setMonth((CommonFunction.checkNull(bankAcDetail1.get(9))).trim());
				if((CommonFunction.checkNull(bankAcDetail1.get(9))).trim().equalsIgnoreCase("1"))
				{
					vo.setStatementMonthAndYear("Jan "+(CommonFunction.checkNull(bankAcDetail1.get(8))).trim());
					
				}
				else if((CommonFunction.checkNull(bankAcDetail1.get(9))).trim().equalsIgnoreCase("2"))
				{
					vo.setStatementMonthAndYear("Feb "+(CommonFunction.checkNull(bankAcDetail1.get(8))).trim());
				}
				else if((CommonFunction.checkNull(bankAcDetail1.get(9))).trim().equalsIgnoreCase("3"))
				{
					vo.setStatementMonthAndYear("Mar "+(CommonFunction.checkNull(bankAcDetail1.get(8))).trim());
				}
				else if((CommonFunction.checkNull(bankAcDetail1.get(9))).trim().equalsIgnoreCase("4"))
				{
					vo.setStatementMonthAndYear("Apr "+(CommonFunction.checkNull(bankAcDetail1.get(8))).trim());
				}
				else if((CommonFunction.checkNull(bankAcDetail1.get(9))).trim().equalsIgnoreCase("5"))
				{
					vo.setStatementMonthAndYear("May "+(CommonFunction.checkNull(bankAcDetail1.get(8))).trim());
				}
				else if((CommonFunction.checkNull(bankAcDetail1.get(9))).trim().equalsIgnoreCase("6"))
				{
					vo.setStatementMonthAndYear("Jun "+(CommonFunction.checkNull(bankAcDetail1.get(8))).trim());
				}
				else if((CommonFunction.checkNull(bankAcDetail1.get(9))).trim().equalsIgnoreCase("7"))
				{
					vo.setStatementMonthAndYear("Jul "+(CommonFunction.checkNull(bankAcDetail1.get(8))).trim());
				}
				else if((CommonFunction.checkNull(bankAcDetail1.get(9))).trim().equalsIgnoreCase("8"))
				{
					vo.setStatementMonthAndYear("Aug "+(CommonFunction.checkNull(bankAcDetail1.get(8))).trim());
				}
				else if((CommonFunction.checkNull(bankAcDetail1.get(9))).trim().equalsIgnoreCase("9"))
				{
					vo.setStatementMonthAndYear("Sep "+(CommonFunction.checkNull(bankAcDetail1.get(8))).trim());
				}
				else if((CommonFunction.checkNull(bankAcDetail1.get(9))).trim().equalsIgnoreCase("10"))
				{
					vo.setStatementMonthAndYear("Oct "+(CommonFunction.checkNull(bankAcDetail1.get(8))).trim());
				}
				else if((CommonFunction.checkNull(bankAcDetail1.get(9))).trim().equalsIgnoreCase("11"))
				{
					vo.setStatementMonthAndYear("Nov "+(CommonFunction.checkNull(bankAcDetail1.get(8))).trim());
				}
				else if((CommonFunction.checkNull(bankAcDetail1.get(9))).trim().equalsIgnoreCase("12"))
				{
					vo.setStatementMonthAndYear("Dec "+(CommonFunction.checkNull(bankAcDetail1.get(8))).trim());
				}
				
				
				if(!CommonFunction.checkNull(bankAcDetail1.get(10)).equalsIgnoreCase(""))
				{
					Number reconNumb =myFormatter.parse((CommonFunction.checkNull(bankAcDetail1.get(10))).trim());
					vo.setCredit(myFormatter.format(reconNumb));
				}
				if(!CommonFunction.checkNull(bankAcDetail1.get(11)).equalsIgnoreCase(""))
				{
					Number reconNumb =myFormatter.parse((CommonFunction.checkNull(bankAcDetail1.get(11))).trim());
					vo.setDebit(myFormatter.format(reconNumb));
				}
			
				if(!CommonFunction.checkNull(bankAcDetail1.get(12)).equalsIgnoreCase(""))
				{
					Number reconNumb =myFormatter.parse((CommonFunction.checkNull(bankAcDetail1.get(12))).trim());
					vo.setTotalEMI(myFormatter.format(reconNumb));
				}
				logger.info("total emi:::?????"+bankAcDetail1.get(12));
				Double higherBal=0.0000;
				Double lowerBal=0.0000;
				
				if(!CommonFunction.checkNull(bankAcDetail1.get(13)).equalsIgnoreCase(""))
				{
					Number reconNumb =myFormatter.parse((CommonFunction.checkNull(bankAcDetail1.get(13))).trim());
					vo.setHighestBalance(myFormatter.format(reconNumb));
					higherBal=Double.parseDouble((CommonFunction.checkNull(bankAcDetail1.get(13))).trim());
				}
				logger.info("hjighest bal:::::::"+bankAcDetail1.get(13));
				logger.info("lowest bal:::::::"+bankAcDetail1.get(14));
				
				if(!CommonFunction.checkNull(bankAcDetail1.get(14)).equalsIgnoreCase(""))
				{
					Number reconNumb =myFormatter.parse((CommonFunction.checkNull(bankAcDetail1.get(14))).trim());
					vo.setLowestBalance(myFormatter.format(reconNumb));
					lowerBal=Double.parseDouble((CommonFunction.checkNull(bankAcDetail1.get(14))).trim());
				}
			
				vo.setCheckBounceFrequency((CommonFunction.checkNull(bankAcDetail1.get(15))).trim());
				
				if(!CommonFunction.checkNull(bankAcDetail1.get(16)).equalsIgnoreCase(""))
				{
					Number reconNumb =myFormatter.parse((CommonFunction.checkNull(bankAcDetail1.get(16))).trim());
					vo.setCheckBounceAmount(myFormatter.format(reconNumb));
				}
				vo.setOverLimitFrequency((CommonFunction.checkNull(bankAcDetail1.get(17))).trim());
				if(!CommonFunction.checkNull(bankAcDetail1.get(18)).equalsIgnoreCase(""))
				{
					Number reconNumb =myFormatter.parse((CommonFunction.checkNull(bankAcDetail1.get(18))).trim());
					vo.setOverLimitAmount(myFormatter.format(reconNumb));
				}
				vo.setBankAcAnId((CommonFunction.checkNull(bankAcDetail1.get(19))).trim());
				logger.info("swing amount higherBal-lowerBal ................................."+(higherBal-lowerBal)*100);
				if(higherBal-lowerBal>=0 && !(CommonFunction.checkNull((bankAcDetail1.get(13))).trim().equalsIgnoreCase("")) && !(CommonFunction.checkNull((bankAcDetail1.get(14))).trim().equalsIgnoreCase("")))
				{
					Number reconNumb =myFormatter.parse((CommonFunction.checkNull(higherBal-lowerBal)).trim());
					vo.setSwingAmt(myFormatter.format(reconNumb));
					
				}
				else{
					Number reconNumb=0;
					vo.setSwingAmt(myFormatter.format(reconNumb));
					logger.info("this is highest:::::::");
				}
				logger.info("swing ((higherBal-lowerBal)*higherBal)/100) ................................."+(((higherBal-lowerBal)/lowerBal)*100));
			
				if(((higherBal-lowerBal)*higherBal)/100 >=0 && lowerBal!=0 &&!(CommonFunction.checkNull((bankAcDetail1.get(13))).trim().equalsIgnoreCase("")) && !(CommonFunction.checkNull((bankAcDetail1.get(14))).trim().equalsIgnoreCase("")))
				{
					Number reconNumb =myFormatter.parse((CommonFunction.checkNull(((higherBal-lowerBal)/lowerBal)*100)).trim());
					vo.setSwingPercent(myFormatter.format(reconNumb));
				
					
				}
				else{
					Number reconNumb =0;
					vo.setSwingPercent(myFormatter.format(reconNumb));
					logger.info("this is lowest:::::::");
				}
				
				
				vo.setChequeBouncing((CommonFunction.checkNull(bankAcDetail1.get(20))).trim());
				vo.setLimitObligation((CommonFunction.checkNull(bankAcDetail1.get(21))).trim());
				logger.info("limi obligation value is::::::::"+bankAcDetail1.get(21));
				logger.info("cheque bouncing value is::::::::"+bankAcDetail1.get(20));
				vo.setCustomerName((CommonFunction.checkNull(bankAcDetail1.get(22))).trim());
				logger.info("CustomerName value is::::::::"+bankAcDetail1.get(22));
				//Added by Arun
				if(!CommonFunction.checkNull(bankAcDetail1.get(23)).equalsIgnoreCase(""))
				{
					Number reconNumb =myFormatter.parse((CommonFunction.checkNull(bankAcDetail1.get(23))).trim());
					vo.setTotalCreditTxn(myFormatter.format(reconNumb));
					
				}
				if(!CommonFunction.checkNull(bankAcDetail1.get(24)).equalsIgnoreCase(""))
				{
					Number reconNumb =myFormatter.parse((CommonFunction.checkNull(bankAcDetail1.get(24))).trim());
					vo.setTotalDebitTxn(myFormatter.format(reconNumb));
					
				}
				vo.setVarificationMethod((CommonFunction.checkNull(bankAcDetail1.get(25))).trim());
				if(!CommonFunction.checkNull(bankAcDetail1.get(26)).equalsIgnoreCase(""))
				{
					Number reconNumb =myFormatter.parse((CommonFunction.checkNull(bankAcDetail1.get(26))).trim());
					vo.setBalanceAmount(myFormatter.format(reconNumb));
					
				}
				vo.setRemarks((CommonFunction.checkNull(bankAcDetail1.get(27))).trim());
				if(!CommonFunction.checkNull(bankAcDetail1.get(28)).equalsIgnoreCase(""))
				{
					Number reconNumb =myFormatter.parse((CommonFunction.checkNull(bankAcDetail1.get(28))).trim());
					vo.setInwardChequeReturnCount(myFormatter.format(reconNumb));
					
				}
				if(!CommonFunction.checkNull(bankAcDetail1.get(29)).equalsIgnoreCase(""))
				{
					Number reconNumb =myFormatter.parse((CommonFunction.checkNull(bankAcDetail1.get(29))).trim());
					vo.setOutwardChequeReturnCount(myFormatter.format(reconNumb));
					
				}
				if(!CommonFunction.checkNull(bankAcDetail1.get(30)).equalsIgnoreCase(""))
				{
					Number reconNumb =myFormatter.parse((CommonFunction.checkNull(bankAcDetail1.get(30))).trim());
					vo.setTotalDebitEntry(myFormatter.format(reconNumb));
					
				}
				if(!CommonFunction.checkNull(bankAcDetail1.get(31)).equalsIgnoreCase(""))
				{
					Number reconNumb =myFormatter.parse((CommonFunction.checkNull(bankAcDetail1.get(31))).trim());
					vo.setTotalCreditEntry(myFormatter.format(reconNumb));
					
				}
				if(!CommonFunction.checkNull(bankAcDetail1.get(32)).equalsIgnoreCase(""))
				{
					Number reconNumb =myFormatter.parse((CommonFunction.checkNull(bankAcDetail1.get(32))).trim());
					vo.setTotalInwardBouncingPerc(myFormatter.format(reconNumb));
					
				}
				if(!CommonFunction.checkNull(bankAcDetail1.get(33)).equalsIgnoreCase(""))
				{
					Number reconNumb =myFormatter.parse((CommonFunction.checkNull(bankAcDetail1.get(33))).trim());
					vo.setTotalOutwardBouncingPerc(myFormatter.format(reconNumb));
					
				}
				vo.setRepaymentFromThisAccount(CommonFunction.checkNull(bankAcDetail1.get(34)).trim());
				vo.setBankingSince(CommonFunction.checkNull(bankAcDetail1.get(35)).trim());
				vo.setJointHolding(CommonFunction.checkNull(bankAcDetail1.get(36)).trim());
				vo.setPrimaryAccount(CommonFunction.checkNull(bankAcDetail1.get(37)).trim());
				
				if(!CommonFunction.checkNull(bankAcDetail1.get(38)).equalsIgnoreCase(""))
				{
					Number reconNumb =myFormatter.parse((CommonFunction.checkNull(bankAcDetail1.get(38))).trim());
					vo.setAvgBankBalance(myFormatter.format(reconNumb));
					
				}
				if(!CommonFunction.checkNull(bankAcDetail1.get(39)).equalsIgnoreCase(""))
				{
					Number reconNumb =myFormatter.parse((CommonFunction.checkNull(bankAcDetail1.get(39))).trim());
					vo.setInputWReturn(myFormatter.format(reconNumb));
					
				}
				
				if(!CommonFunction.checkNull(bankAcDetail1.get(40)).equalsIgnoreCase(""))
				{
					Number reconNumb =myFormatter.parse((CommonFunction.checkNull(bankAcDetail1.get(40))).trim());
					vo.setOutputWReturn(myFormatter.format(reconNumb));
					
				}
			
				if(!CommonFunction.checkNull(bankAcDetail1.get(41)).equalsIgnoreCase(""))
				{
					Number reconNumb =myFormatter.parse((CommonFunction.checkNull(bankAcDetail1.get(41))).trim());
					vo.setOdccimitamount(myFormatter.format(reconNumb));
					
				}
				
				vo.setCollateralDetails(CommonFunction.checkNull(bankAcDetail1.get(42)).trim());
				//added by arun Ends here
                list.add(vo);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.info("bankAcDetail1 size is .................................."+list.size());
		return list;
	}
	
	public int deleteBankAnalysis(String id)
	{
		
            int status=0;
            boolean qryStatus = false;
           
            ArrayList qryList = new ArrayList();
   		
		try{
			StringBuilder query=new StringBuilder();
			 query.append("delete from cr_bank_analysis_dtl where BANK_ANALYSIS_ID=?");
			PrepStmtObject insPrepStmtObject = new PrepStmtObject();
			if(CommonFunction.checkNull(id).trim().equalsIgnoreCase(""))
				insPrepStmtObject.addNull();
			else
				insPrepStmtObject.addString((CommonFunction.checkNull(id)).trim());
			
			insPrepStmtObject.setSql(query.toString());
	        logger.info("IN deleteBankAnalysis() update query ### "+insPrepStmtObject.printQuery());
			qryList.add(insPrepStmtObject);
			qryStatus=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("Deletion Status :."+qryStatus);
		query=null;	
		}catch(Exception e){
				e.printStackTrace();
			}
			if(qryStatus)
			{
				status=1;
			}
			return status;
}
	
	
	public boolean updateBankAccountAnalysis(Object ob)
	  {
		logger.info("In update updateBankAccountAnalysis........ ");
		BankAccountAnalysisVo vo=(BankAccountAnalysisVo)ob;
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		boolean status = false;
		try
		{
			StringBuilder queryUpdate=new StringBuilder();
			queryUpdate.append("update cr_bank_analysis_dtl set DEAL_ID=?,BANK_NAME=?,BANK_BRANCH=?,ACCOUNT_NO=?,ACCOUNT_TYPE=?,STATEMENT_YEAR=?,STATEMENT_MONTH=?,TOTAL_CR=?," );
			queryUpdate.append("TOTAL_DR=?,TOTAL_EMI=?,HIGHEST_BALANCE=?,LOWEST_BALANCE=?,CHECK_BOUNCING_FREQ=?,CHECK_BOUNCING_AMOUNT=?,OVER_LIMIT_FREQ=?," );
			queryUpdate.append("OVER_LIMIT_AMOUNT=?,BOUNCING_INWARD_OUTWARD=?,LIMIT_OBLIGATION=?,MAKER_ID=?,MAKER_DATE=");
			queryUpdate.append(dbo);
			queryUpdate.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			
			queryUpdate.append("TOTAL_CR_TXN=?,TOTAL_DR_TXN=?,VERIFICATION_METHOD=?,BALANCE_AMOUNT=?,REMARKS=?,INWARD_CHEQUE_RETURN_COUNT=?,OUTWARD_CHEQUE_RETURN_COUNT=?,TOTAL_DEBIT_ENTRY=?,TOTAL_CREDIT_ENTRY=?,TOTAL_INWARD_BOUNCING_PERC=?,TOTAL_OUTWARD_BOUNCING_PERC=?,REPAYMENT_FROM_THIS_ACCOUNT=?,BANKING_SINCE=" );
			queryUpdate.append(dbo);
			queryUpdate.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			queryUpdate.append("JOINT_HOLDING=?,PRIMARY_ACCOUNT=?,AVGBANK_BALANCE=?,INPUTW_RETURN=?,OUTPUTW_RETURN=?,ODCCIMIT_AMOUNT=?,COLLATERAL_DETAILS=? where BANK_ANALYSIS_ID=?"); 
			if((CommonFunction.checkNull(vo.getLbxDealNo())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getLbxDealNo()).trim());
			
			if((CommonFunction.checkNull(vo.getLbxBankID())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getLbxBankID()).trim());
			
			if((CommonFunction.checkNull(vo.getLbxBranchID())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getLbxBranchID()).trim());
			
			if((CommonFunction.checkNull(vo.getAccountNo())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getAccountNo()).trim());
			
			if((CommonFunction.checkNull(vo.getAccountType())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getAccountType()).trim());
			
			if((CommonFunction.checkNull(vo.getYear())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getYear()).trim());
			if((CommonFunction.checkNull(vo.getMonth())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getMonth()).trim());
			
			if((CommonFunction.checkNull(vo.getCredit())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getCredit()).trim()).toString());
			
			if((CommonFunction.checkNull(vo.getDebit())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getDebit().trim())).toString());
			
			if((CommonFunction.checkNull(vo.getTotalEMI())).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getTotalEMI().trim())).toString());
			
			if(CommonFunction.checkNull((vo.getHighestBalance())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getHighestBalance()).trim()).toString());
			
			if((CommonFunction.checkNull(vo.getLowestBalance())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getLowestBalance()).trim()).toString());
			
			
			
			if((CommonFunction.checkNull(vo.getCheckBounceFrequency())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getCheckBounceFrequency()).trim());
			if((CommonFunction.checkNull(vo.getCheckBounceAmount())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getCheckBounceAmount()).trim()).toString());
			if((CommonFunction.checkNull(vo.getOverLimitFrequency())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getOverLimitFrequency()).trim());
			if((CommonFunction.checkNull(vo.getOverLimitAmount())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getOverLimitAmount()).trim()).toString());
			if((CommonFunction.checkNull(vo.getChequeBouncing())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getChequeBouncing()).trim());
			if((CommonFunction.checkNull(vo.getLimitObligation())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getLimitObligation()).trim());					
			
			if((CommonFunction.checkNull(vo.getUserId())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getUserId()).trim());
			
			if((CommonFunction.checkNull(vo.getBusinessDate())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getBusinessDate()).trim());
			
			//Added by Arun
			if(CommonFunction.checkNull(vo.getTotalCreditTxn()).trim().equalsIgnoreCase(""))//TOTAL_CR_TXN
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getTotalCreditTxn()).trim()).toString());
			
			if(CommonFunction.checkNull(vo.getTotalDebitTxn()).trim().equalsIgnoreCase(""))//TOTAL_DR_TXN
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getTotalDebitTxn()).trim()).toString());
			
			if(CommonFunction.checkNull(vo.getVarificationMethod()).trim().equalsIgnoreCase(""))//VERIFICATION_METHOD
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getVarificationMethod()).trim());
			
			if(CommonFunction.checkNull(vo.getBalanceAmount()).trim().equalsIgnoreCase(""))//BALANCE_AMOUNT
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getBalanceAmount()).trim()).toString());
			
			if(CommonFunction.checkNull(vo.getRemarks()).trim().equalsIgnoreCase(""))//REMARKS
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getRemarks()).trim());
			
			if(CommonFunction.checkNull(vo.getInwardChequeReturnCount()).trim().equalsIgnoreCase(""))//INWARD_CHEQUE_RETURN_COUNT
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getInwardChequeReturnCount()).trim()).toString());
			
			if(CommonFunction.checkNull(vo.getOutwardChequeReturnCount()).trim().equalsIgnoreCase(""))//OUTWARD_CHEQUE_RETURN_COUNT
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getOutwardChequeReturnCount()).trim()).toString());
			
			if(CommonFunction.checkNull(vo.getTotalDebitEntry()).trim().equalsIgnoreCase(""))//TOTAL_DEBIT_ENTRY
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getTotalDebitEntry()).trim()).toString());
			
			if(CommonFunction.checkNull(vo.getTotalCreditEntry()).trim().equalsIgnoreCase(""))//TOTAL_CREDIT_ENTRY
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getTotalCreditEntry()).trim()).toString());
			
			if(CommonFunction.checkNull(vo.getTotalInwardBouncingPerc()).trim().equalsIgnoreCase(""))//TOTAL_INWARD_BOUNCING_PERC
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getTotalInwardBouncingPerc()).trim()).toString());
			
			if(CommonFunction.checkNull(vo.getTotalOutwardBouncingPerc()).trim().equalsIgnoreCase(""))//TOTAL_OUTWARD_BOUNCING_PERC
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getTotalOutwardBouncingPerc()).trim()).toString());
			
			if(CommonFunction.checkNull(vo.getRepaymentFromThisAccount()).trim().equalsIgnoreCase(""))//REPAYMENT_FROM_THIS_ACCOUNT
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getRepaymentFromThisAccount()).trim());
			
			if(CommonFunction.checkNull(vo.getBankingSince()).trim().equalsIgnoreCase(""))//BANKING_SINCE
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getBankingSince()).trim());
			
			if(CommonFunction.checkNull(vo.getJointHolding()).trim().equalsIgnoreCase(""))//JOINT_HOLDING
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getJointHolding()).trim());
			
			if(CommonFunction.checkNull(vo.getPrimaryAccount()).trim().equalsIgnoreCase(""))//PRIMARY_ACCOUNT
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getPrimaryAccount().trim());
			
			if(CommonFunction.checkNull(vo.getAvgBankBalance()).trim().equalsIgnoreCase(""))//AVGBANK_BALANCE
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getAvgBankBalance()).trim()).toString());
			
			if(CommonFunction.checkNull(vo.getInputWReturn()).trim().equalsIgnoreCase(""))//INPUTW_RETURN
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getInputWReturn()).trim()).toString());
			
			if(CommonFunction.checkNull(vo.getOutputWReturn()).trim().equalsIgnoreCase(""))//OUTPUTW_RETURN
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getOutputWReturn()).trim()).toString());
			
			if(CommonFunction.checkNull(vo.getOdccimitamount()).trim().equalsIgnoreCase(""))//ODCCIMIT_AMOUNT
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse((vo.getOdccimitamount()).trim()).toString());
			
			if(CommonFunction.checkNull(vo.getCollateralDetails()).trim().equalsIgnoreCase(""))//COLLATERAL_DETAILS
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getCollateralDetails().trim());
			//Added By Arun Ends Here
			
			if((CommonFunction.checkNull(vo.getBankAcAnId())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getBankAcAnId()).trim());
			
			logger.info("cheque bouncing value is::::::++++"+vo.getChequeBouncing());
			logger.info(" limit obligation value is::::::++++"+vo.getLimitObligation());

			insertPrepStmtObject.setSql(queryUpdate.toString());
			logger.info("IN updateBankAccountAnalysis() update query ### "+insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			logger.info("In updateBankAccountAnalysis ........ update query: "+queryUpdate.toString());
			
				status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In updateBankAccountAnalysis.........update status: "+status);
			
			queryUpdate=null;
			
		}catch(Exception e){
			e.printStackTrace();
		}

		return status;

	}
	
	
	public ArrayList<Object> getYears(String businessDate)
	{

		ArrayList<Object> list=new ArrayList<Object>();
		Date date;
		DateFormat formatter ; 
		formatter = new SimpleDateFormat("dd-MM-yyyy");
		try {
			date =(Date)formatter.parse(businessDate);
		
			 System.out.println("businessDate: " +businessDate);
			 SimpleDateFormat simpleDateformat=new SimpleDateFormat("yyyy");
			 int year =Integer.parseInt(simpleDateformat.format(date));
			  System.out.println("Year: " + simpleDateformat.format(date));
			  for(int i=year;i>=year-5;i--)
			  {
				  BankAccountAnalysisVo vo = new BankAccountAnalysisVo();
				  vo.setStatementMonthAndYear(i+"");
				  list.add(vo);
			  }
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	public boolean saveSalesAnalysis(Object ob)
	{
		SalesAnalysisVo vo=(SalesAnalysisVo)ob;
		logger.info("Inside saveSalesAnalysis DAOImpl");
		//int maxId=0;
		//PrepStmtObject insertPrepStmtObject =null;
		ArrayList subglAcYr=null;
		//ArrayList subglAcYrr=null;
		ArrayList glAcYr=null;
		//ArrayList glAcYrr=null;
		//CallableStatement cst=null;
		boolean status=false;
		//Connection con=ConnectionDAO.getConnection();
		ArrayList<Object> in =new ArrayList<Object>();
			ArrayList<Object> out =new ArrayList<Object>();
			ArrayList qryList = new ArrayList();
//			String s1="";
//			String s2="";
//			String s3="";
			
			StringBuffer bufInsSql=null;
			PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
			String netSales[]=null;
			String increaseInSale[]=null;
			String averageSale[]=null;
			String procval="";
			try{
				StringBuilder query=new StringBuilder();
			/*	query.append("select extract(day from start_date), extract(month from start_date), extract(year from start_date), " +
						"extract(day from end_date),extract(month from end_date),extract(year from end_date)" +
						" from gl_accounting_year_m" +
						" where STR_TO_DATE('"+vo.getBusinessDate()+"','"+dateFormat+"') >= start_date and STR_TO_DATE('"+vo.getBusinessDate()+"','"+dateFormat+"') <= end_date");
				logger.info("query of gl_accounting year ............................. "+query);
				 glAcYr = ConnectionDAO.sqlSelect(query.toString());
				
				 query=null;
				 
				if(glAcYr!=null)
				{
				for(int i=0;i<glAcYr.size();i++){

					 subglAcYr=(ArrayList)glAcYr.get(i);
					if(subglAcYr.size()>0)
					{
						vo.setStartMonth((CommonFunction.checkNull(subglAcYr.get(1))).trim());
						vo.setStartYear((CommonFunction.checkNull(subglAcYr.get(2))).trim());
						vo.setEndMonth((CommonFunction.checkNull(subglAcYr.get(4))).trim());
						vo.setEndYear((CommonFunction.checkNull(subglAcYr.get(5))).trim());
					}
				}
				}
				*/
					logger.info("In saveSalesAnalysis Procedure................................. ");
				
	        	  /*  in.add(vo.getEndYear());
	        	    in.add(vo.getEndMonth());
	        	    in.add(vo.getStartYear());
	        	    in.add(vo.getStartMonth());
	        	  
	        	    in.add(Integer.parseInt(vo.getDealId()));
	        	    in.add(Integer.parseInt(vo.getYear()));
	        	    in.add(Integer.parseInt(vo.getMonth()));
	        	    in.add(Double.parseDouble(myFormatter.parse((vo.getNetsales()).trim()).toString()));
	        	    in.add(vo.getUserId());
	        	 
	        	    StringBuilder aDate=new StringBuilder();
	        	    
	        	   aDate.append(vo.getBusinessDate());
	        	   
	        	   StringBuilder date=new StringBuilder();
	        	   
	        		date.append(aDate.substring(6)+"-"+aDate.substring(3,5)+"-"+aDate.substring(0,2));
	        		
	        	    in.add(date);
	        	    in.add("A");
	        	    out.add(s1);
	        	    out.add(s2);
	        	    out.add(s3);
	        	
	        	    date=null;
	        	    aDate=null;

	        	    outMessages=(ArrayList) ConnectionDAO.callSP("Sales_Analysis",in,out);
	        	    s1.append(CommonFunction.checkNull(outMessages.get(0)));
	        	    s2.append(CommonFunction.checkNull(outMessages.get(1)));
	        	    s3.append(CommonFunction.checkNull(outMessages.get(2)));

					if(s2!=null && s2.toString().equalsIgnoreCase("S"))
					{
						status=true;
						procval=s2.toString();
						//con.commit();
						logger.info("Procedure Error Message----"+s2);
					}
					else
					{
						//con.rollback();
						procval=s3.toString();
						logger.info("Procedure Error Message----"+s3);
					}*/
					
					
					netSales=vo.getNetsales(); 
					averageSale=vo.getAvgsales();
					increaseInSale=vo.getInterest();
					String query1=  "SELECT count(1) FROM cr_sales_analysis_dtl WHERE DEAL_ID='"+CommonFunction.checkNull(vo.getDealId())+"' and REC_STATUS='P'";
			    	logger.info("query1--saveBalanceSheet->"+query1);
			    	String countt="";
			    	countt=CommonFunction.checkNull(ConnectionDAO.singleReturn(query1));
			int count=0;
			count=Integer.parseInt(countt);
			  if(count>0){
				    		
				    		String query2 = "DELETE FROM cr_sales_analysis_dtl WHERE DEAL_ID='"+CommonFunction.checkNull(vo.getDealId())+"' and REC_STATUS='P' and year='"+CommonFunction.checkNull(vo.getYear())+"'";
				    		logger.info("query--saveBalanceSheet-->"+query2);
				    		insertPrepStmtObject.setSql(query2);
				    		qryList.add(insertPrepStmtObject);
				    	}
					
					for(int i=0;i<12; i++)
		    		{
	    			
	    			 insertPrepStmtObject = new PrepStmtObject();
	    			 bufInsSql =	new StringBuffer();
	    			
	    			
	    			bufInsSql.append("insert into CR_SALES_ANALYSIS_DTL(DEAL_ID,YEAR,MONTH,NET_SALES,AVERAGE_MONTHLY_SALES,INCREASE,REC_STATUS,MAKER_ID,MAKER_DATE)");
	    			bufInsSql.append(" values ( ");
	    			bufInsSql.append(" ?," );//DEAL_ID
	    			bufInsSql.append(" ?," );//YEAR
	    			bufInsSql.append(" ?," );//MONTH
	    			bufInsSql.append(" ?," );//NET_SALES
	    			bufInsSql.append(" ?," );//AVERAGE_MONTHLY_SALES
	    			bufInsSql.append(" ?," );//INCREASE
	    			bufInsSql.append(" ?," );//REC_STATUS
	    			bufInsSql.append(" ?," );//MAKER_ID
	    			bufInsSql.append(dbo);
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) )");//MAKER_DATE
	    			
	    		  	logger.info("financial deal id **************** "+vo.getDealId());
	    		  	
	    			if((CommonFunction.checkNull(vo.getDealId())).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addString((vo.getDealId()).trim());
	    			
	    			if((CommonFunction.checkNull(vo.getYear())).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addString((vo.getYear()).trim());
	    			
	    			if((CommonFunction.checkNull(i+1)).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject.addString("0");
	    			else
	    				insertPrepStmtObject.addInt(i+1);

	    			if((CommonFunction.checkNull(netSales[i])).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject.addString("0.00");
	    			else
	    				insertPrepStmtObject.addString(myFormatter.parse(netSales[i].trim()).toString());
	    			if((CommonFunction.checkNull(averageSale[i])).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject.addString("0.00");
	    			else
	    				insertPrepStmtObject.addString(myFormatter.parse(averageSale[i].trim()).toString());
	    			if((CommonFunction.checkNull(increaseInSale[i])).trim().equalsIgnoreCase(""))
	    				insertPrepStmtObject.addString("0.00");
					else{
						insertPrepStmtObject.addString(myFormatter.parse(increaseInSale[i].trim()).toString());
					}
	    		
	    			insertPrepStmtObject.addString("P");//,REC_STATUS

	    			if((CommonFunction.checkNull(vo.getUserId())).trim().equals(""))//,MAKER_ID
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addString((vo.getUserId()).trim());

	    			if((CommonFunction.checkNull(vo.getBusinessDate())).equalsIgnoreCase(""))//,MAKER_DATE
	    				insertPrepStmtObject.addNull();
	    			else
	    				insertPrepStmtObject.addString((vo.getBusinessDate()));
	    			
	    				insertPrepStmtObject.setSql(bufInsSql.toString());
	    				logger.info("IN saveSalesAnalysis() insert query ### "+insertPrepStmtObject.printQuery());
	    				
	    				
	    				qryList.add(insertPrepStmtObject);
	    				
	    				
	    			
	    		 }
					if(qryList.size()>0){
						status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
					}
			
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				
				
				return status;
	}
	
	public ArrayList getSalesAnalysisDetails(String salesAnsId,String dealId,String recStatus,String year)
	{
		ArrayList<Object> list=new ArrayList<Object>();
		String arr[]={"January","February","March","April","May","June","July","August","September","October","November","December"};
		
		try{
		
			StringBuilder query=new StringBuilder();
			query.append("select s.SALES_ANALYSIS_ID,d.DEAL_ID,s.YEAR,s.MONTH,s.NET_SALES,s.AVERAGE_MONTHLY_SALES,s.INCREASE,s.REC_STATUS,s.MAKER_ID,s.MAKER_DATE" );
			query.append(" from cr_sales_analysis_dtl s left join cr_deal_dtl d on s.DEAL_ID=d.DEAL_ID where s.REC_STATUS='"+recStatus+"' and YEAR='"+year+"' ");
		if(salesAnsId.trim()!=null && !salesAnsId.trim().equalsIgnoreCase(""))
		{
			query.append(" and s.SALES_ANALYSIS_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(salesAnsId)).trim());
		}
		if(dealId!=null && !dealId.trim().equalsIgnoreCase(""))
		{
			query.append(" and s.DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
		}
	
		logger.info("getSalesAnalysisDetails: "+query.toString());
		SalesAnalysisVo vo = null;
		ArrayList salesDetail = ConnectionDAO.sqlSelect(query.toString());
		
		query=null;
		if(salesDetail.size()>0){
		for(int i=0;i<salesDetail.size();i++){

			ArrayList subSalesDetail=(ArrayList)salesDetail.get(i);
			if(subSalesDetail.size()>0)
			{
				vo = new SalesAnalysisVo();
				vo.setSalesId((CommonFunction.checkNull(subSalesDetail.get(0))).trim());
				vo.setDealId((CommonFunction.checkNull(subSalesDetail.get(1))).trim());
				
				vo.setYear((CommonFunction.checkNull(year)).trim());
				
				vo.setMonth(CommonFunction.checkNull(arr[i]));
				if(!CommonFunction.checkNull(subSalesDetail.get(4)).equalsIgnoreCase(""))
				{
					Number reconNumb =myFormatter.parse((CommonFunction.checkNull(subSalesDetail.get(4))).trim());
					vo.setNetSales(myFormatter.format(reconNumb));
				}
				
				if(!CommonFunction.checkNull(subSalesDetail.get(5)).equalsIgnoreCase(""))
				{
					Number reconNumb =myFormatter.parse((CommonFunction.checkNull(subSalesDetail.get(5))).trim());
					vo.setAverageSale(myFormatter.format(reconNumb));
				}
				if(!CommonFunction.checkNull(subSalesDetail.get(6)).equalsIgnoreCase(""))
				{
					Number reconNumb =myFormatter.parse((CommonFunction.checkNull(subSalesDetail.get(6))).trim());
					vo.setIncreaseInSale(myFormatter.format(reconNumb));
				}
				list.add(vo);
			}
		}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	public ArrayList getSalesyear(String salesAnsId,String dealId,String recStatus)
	{
		ArrayList<Object> list=new ArrayList<Object>();
	
		try{
		
			StringBuilder query=new StringBuilder();
			query.append("select distinct s.YEAR " );
			query.append(" from cr_sales_analysis_dtl s left join cr_deal_dtl d on s.DEAL_ID=d.DEAL_ID where s.REC_STATUS='"+recStatus+"' ");
		if(salesAnsId.trim()!=null && !salesAnsId.trim().equalsIgnoreCase(""))
		{
			query.append(" and s.SALES_ANALYSIS_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(salesAnsId)).trim());
		}
		if(dealId!=null && !dealId.trim().equalsIgnoreCase(""))
		{
			query.append(" and s.DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
		}
	
		logger.info("getSalesyear: "+query);
		BankAccountAnalysisVo vo = null;
		ArrayList salesDetail = ConnectionDAO.sqlSelect(query.toString());
		
		query=null;
		if(salesDetail.size()>0){
		for(int i=0;i<salesDetail.size();i++){

			ArrayList subSalesDetail=(ArrayList)salesDetail.get(i);
			if(subSalesDetail.size()>0)
			{
				vo = new BankAccountAnalysisVo();
				
				vo.setYear((CommonFunction.checkNull(subSalesDetail.get(0))).trim());
				
			}
		}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}

	
	
	public String deleteSalesAnalysis(String id,String businessDate,String userId)
	{
		
            boolean status=false;
            //CallableStatement cst=null;
          
            SalesAnalysisVo vo=new SalesAnalysisVo();
    		logger.info("Inside deleteSalesAnalysis DAOImpl");
    		ArrayList subglAcYr=null;
    		ArrayList glAcYr=null;
    	 	ArrayList<Object> in =new ArrayList<Object>();
 			ArrayList<Object> out =new ArrayList<Object>();
 			ArrayList outMessages = new ArrayList();
// 			String s1="";
// 			String s2="";
// 			String s3="";
 			StringBuilder s1=new StringBuilder();
 			StringBuilder s2=new StringBuilder();
 			StringBuilder s3=new StringBuilder();
 			
 			String procval="";
		try{
			//Connection con=ConnectionDAO.getConnection();
			
			StringBuilder query=new StringBuilder();
			 query.append("select extract(day from start_date), extract(month from start_date), extract(year from start_date), " );
			 query.append("extract(day from end_date),extract(month from end_date),extract(year from end_date)" );
			 query.append(" from gl_accounting_year_m" );
			 query.append(" where ");
			 query.append(dbo);
			 query.append("STR_TO_DATE('"+businessDate+"','"+dateFormat+"') >= start_date and ");
			 query.append(dbo);
			 query.append("STR_TO_DATE('"+businessDate+"','"+dateFormat+"') <= end_date");
			logger.info("query of gl_accounting year ............................. "+query.toString());
			 glAcYr = ConnectionDAO.sqlSelect(query.toString());
			
			 query=null;
			 
			if(glAcYr!=null)
			{
			for(int i=0;i<glAcYr.size();i++){
		
				 subglAcYr=(ArrayList)glAcYr.get(i);
				if(subglAcYr.size()>0)
				{
					vo.setStartMonth((CommonFunction.checkNull(subglAcYr.get(1))).trim());
					vo.setStartYear((CommonFunction.checkNull(subglAcYr.get(2))).trim());
					vo.setEndMonth((CommonFunction.checkNull(subglAcYr.get(4))).trim());
					vo.setEndYear((CommonFunction.checkNull(subglAcYr.get(5))).trim());
				}
			}
			}
			StringBuilder query1=new StringBuilder();
			query1.append("select YEAR,MONTH,NET_SALES,DEAL_ID from cr_sales_analysis_dtl where SALES_ANALYSIS_ID="+id);
	
			ArrayList salesDetail = ConnectionDAO.sqlSelect(query1.toString());
			
			query1=null;
			
			for(int i=0;i<salesDetail.size();i++){

				ArrayList subSalesDetail=(ArrayList)salesDetail.get(i);
				if(subSalesDetail.size()>0)
				{
					vo.setYear((CommonFunction.checkNull(subSalesDetail.get(0))).trim());
					vo.setMonth((CommonFunction.checkNull(subSalesDetail.get(1))).trim());
					vo.setNetSales((CommonFunction.checkNull(subSalesDetail.get(2))).trim());
					vo.setDealId((CommonFunction.checkNull(subSalesDetail.get(3))).trim());
				}
			}
			
			logger.info("In Sales_Analysis Procedure ................................. ");
//			con.setAutoCommit(false);
//			cst=con.prepareCall("call Sales_Analysis(?,?,?,?,?,?,?,?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?)");
//			cst.setString(1, vo.getEndYear());
//			cst.setString(2, vo.getEndMonth());
//			cst.setString(3, vo.getStartYear());
//			cst.setString(4, vo.getStartMonth());
//			cst.setInt(5, Integer.parseInt(vo.getDealId()));
//			cst.setInt(6, Integer.parseInt(vo.getYear()));
//			cst.setInt(7, Integer.parseInt(vo.getMonth()));
//			cst.setDouble(8, Double.parseDouble(myFormatter.parse(StringEscapeUtils.escapeSql(vo.getNetsales()).trim()).toString()));
//			cst.setString(9, userId);
//			cst.setString(10, businessDate);
//			cst.setString(11, "X");
			logger.info("vo.getEndYear()" +vo.getEndYear());
			logger.info("vo.getEndMonth()" +vo.getEndMonth());
			logger.info("vo.getStartYear()" +vo.getStartYear());
			logger.info("vo.getStartMonth()" +vo.getStartMonth());
			logger.info("Integer.parseInt(vo.getDealId())" +Integer.parseInt(vo.getDealId()));
			logger.info("Integer.parseInt(vo.getYear())" +Integer.parseInt(vo.getYear()));
			logger.info("Integer.parseInt(vo.getMonth())" +Integer.parseInt(vo.getMonth()));
			logger.info("vo.getNetsales"+Double.parseDouble(myFormatter.parse((vo.getNetSales()).trim()).toString()));
			logger.info("userId" +userId);
			logger.info("businessDate" +businessDate);
			
		    in.add(vo.getEndYear());
    	    in.add(vo.getEndMonth());
    	    in.add(vo.getStartYear());
    	    in.add(vo.getStartMonth());
    
    	    in.add(Integer.parseInt(vo.getDealId()));
    	    in.add(Integer.parseInt(vo.getYear()));
    	    in.add(Integer.parseInt(vo.getMonth()));
    	    in.add(Double.parseDouble(myFormatter.parse((vo.getNetSales()).trim()).toString()));
    	    in.add(userId);
    	    
    	    StringBuilder aDate=new StringBuilder();
    	    aDate.append(businessDate);
    	    
    	    StringBuilder date=new StringBuilder();
   		  date.append(aDate.substring(6)+"-"+aDate.substring(3,5)+"-"+aDate.substring(0,2));
   		 	in.add(date);
    	   // in.add(businessDate);
    	    in.add("X");
    	    out.add(s1);
    	    out.add(s2);
    	    out.add(s3);

    	    date=null;
    	    aDate=null;
//			cst.registerOutParameter(12, Types.CHAR);
//			cst.registerOutParameter(13, Types.CHAR);
//			cst.registerOutParameter(14, Types.CHAR);
//			cst.executeUpdate();
    	    outMessages=(ArrayList) ConnectionDAO.callSP("Sales_Analysis",in,out);
    	    s1.append(CommonFunction.checkNull(outMessages.get(0)));
    	    s2.append(CommonFunction.checkNull(outMessages.get(1)));
    	    s3.append(CommonFunction.checkNull(outMessages.get(2)));
//			String s1= cst.getString(12);
//			String s2 = cst.getString(13);
//			String s3 = cst.getString(14);
			if(s2!=null && s2.toString().equalsIgnoreCase("S"))
			{
				status=true;
				procval=s2.toString();
			//	con.commit();
				logger.info("Procedure Error Message----"+s2);
			}
			else
			{
				procval=s3.toString();
				//con.rollback();
				logger.info("Procedure Error Message----"+s3);
			}
			logger.info("s1: "+s1);
			logger.info("s2: "+s2);
			logger.info("s3: "+s3);
			
//			query="delete from cr_sales_analysis_dtl where SALES_ANALYSIS_ID=?";
//			PrepStmtObject insPrepStmtObject = new PrepStmtObject();
//			if(CommonFunction.checkNull(id).trim().equalsIgnoreCase(""))
//				insPrepStmtObject.addNull();
//			else
//				insPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(id)).trim());
//			
//			insPrepStmtObject.setSql(query.toString());
//	        logger.info("IN deleteSalesAnalysis() update query ### "+insPrepStmtObject.printQuery());
//			qryList.add(insPrepStmtObject);
//			qryStatus=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
//			logger.info("Deletion Status :."+qryStatus);
			}catch(Exception e){
				e.printStackTrace();
			}
			finally
			{
				s1=null;
				s2=null;
				s3=null;
				
			}
//			if(qryStatus)
//			{
//				status=1;
//			}
			return procval;
		}
	
	public String updateSalesAnalysis(Object ob)
	  {
		logger.info("In update updateSalesAnalysis........ ");
		SalesAnalysisVo vo=(SalesAnalysisVo)ob;
    	boolean status = false;
		//CallableStatement cst=null;
		//Connection con=ConnectionDAO.getConnection();
		ArrayList<Object> in =new ArrayList<Object>();
			ArrayList<Object> out =new ArrayList<Object>();
			ArrayList outMessages = new ArrayList();
//			String s1="";
//			String s2="";
//        	 String s3=""; 
      	 String procval="";
			StringBuilder s1=new StringBuilder();
			StringBuilder s2=new StringBuilder();
			StringBuilder s3=new StringBuilder();
			
		try
		{ 
			StringBuilder query=new StringBuilder();
			 query.append("select extract(day from start_date), extract(month from start_date), extract(year from start_date), " );
			 query.append("extract(day from end_date),extract(month from end_date),extract(year from end_date)" );
			 query.append(" from gl_accounting_year_m" );
			 query.append(" where ");
			 query.append(dbo);
			 query.append("STR_TO_DATE('"+vo.getBusinessDate()+"','"+dateFormat+"') >= start_date and ");
			 query.append(dbo);
			 query.append("STR_TO_DATE('"+vo.getBusinessDate()+"','"+dateFormat+"') <= end_date");
			logger.info("query of gl_accounting year ............................. "+query.toString());
			ArrayList glAcYr = ConnectionDAO.sqlSelect(query.toString());
			
			query=null;
			
			if(glAcYr!=null)
			{
			for(int i=0;i<glAcYr.size();i++)
			{
		
				ArrayList subglAcYr=(ArrayList)glAcYr.get(i);
				if(subglAcYr.size()>0)
				{
					vo.setStartMonth((CommonFunction.checkNull(subglAcYr.get(1))).trim());
					vo.setStartYear((CommonFunction.checkNull(subglAcYr.get(2))).trim());
					vo.setEndMonth((CommonFunction.checkNull(subglAcYr.get(4))).trim());
					vo.setEndYear((CommonFunction.checkNull(subglAcYr.get(5))).trim());
				}
			}
			}		
			
			logger.info("In updateSalesAnalysis ................................. ");
//			con.setAutoCommit(false);
//			cst=con.prepareCall("call Sales_Analysis(?,?,?,?,?,?,?,?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?)");
//			cst.setString(1, vo.getEndYear());
//			cst.setString(2, vo.getEndMonth());
//			cst.setString(3, vo.getStartYear());
//			cst.setString(4, vo.getStartMonth());
//			cst.setInt(5, Integer.parseInt(vo.getDealId()));
//			cst.setInt(6, Integer.parseInt(vo.getYear()));
//			cst.setInt(7, Integer.parseInt(vo.getMonth()));
//			cst.setDouble(8, Double.parseDouble(myFormatter.parse(StringEscapeUtils.escapeSql(vo.getNetsales()).trim()).toString()));
//			cst.setString(9, vo.getUserId());
//			cst.setString(10, vo.getBusinessDate());
//			cst.setString(11, "A");
//			cst.registerOutParameter(12, Types.CHAR);
//			cst.registerOutParameter(13, Types.CHAR);
//			cst.registerOutParameter(14, Types.CHAR);
//			cst.executeUpdate();
//			String s1= cst.getString(12);
//			String s2 = cst.getString(13);
//			String s3 = cst.getString(14);
/*			in.add(vo.getEndYear());
     	    in.add(vo.getEndMonth());
     	    in.add(vo.getStartYear());
     	    in.add(vo.getStartMonth());
     	  
     	    in.add(Integer.parseInt(vo.getDealId()));
     	    in.add(Integer.parseInt(vo.getYear()));
     	    in.add(Integer.parseInt(vo.getMonth()));
     	    in.add(Double.parseDouble(myFormatter.parse((vo.getNetsales()).trim()).toString()));
     	    in.add(vo.getUserId());
     	    
     	   StringBuilder aDate=new StringBuilder();
     	  StringBuilder date=new StringBuilder();
     	    aDate.append(vo.getBusinessDate());
     	   date.append(aDate.substring(6)+"-"+aDate.substring(3,5)+"-"+aDate.substring(0,2));
     	    in.add(date);
     	    
     	    date=null;
     	    aDate=null;
     	    
     	    //in.add(vo.getBusinessDate());
     	    in.add("A");
     	    out.add(s1);
     	   	out.add(s2);
   	    	out.add(s3);
   	    	outMessages=(ArrayList) ConnectionDAO.callSP("Sales_Analysis",in,out);
   	    	s1.append(CommonFunction.checkNull(outMessages.get(0)));
   	    	s2.append(CommonFunction.checkNull(outMessages.get(1)));
   	     	s3.append(CommonFunction.checkNull(outMessages.get(2)));
			if(s2!=null && s2.toString().equalsIgnoreCase("S"))
			{
				procval=s2.toString();
				status=true;
				//con.commit();
				logger.info("Procedure Error Message----"+s2);
			}
			else
			{
				procval=s3.toString();
				//con.rollback();
				logger.info("Procedure Error Message----"+s3);
			}
			logger.info("s1: "+s1);
			logger.info("s2: "+s2);
			logger.info("s3: "+s3);*/
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	
		return null;
	}
	
	public String saveObligation(Object ob)
	{
		ObligationVo vo=(ObligationVo)ob;
		int rowcount=0;
		int maxId=0;
		PrepStmtObject insertPrepStmtObject =null;
		String result="";
			try{
				ArrayList qryList = new ArrayList();
			
			StringBuffer bufInsSql =	new StringBuffer();
		/*	String query="select deal_id from cr_obligation_analysis_dtl where DEAL_CUSTOMER_ROLE_TYPE='"+vo.getCustomerType()+"' and OBLIGATION_TYPE='"+vo.getObligationType()+"' and DEAL_ID='"+vo.getDealId()+"'";
			Boolean check=ConnectionDAO.checkStatus(query);
			logger.info("check:-----"+check)	;
			logger.info("check:-----Data Already Exists")	; 
			if(!check){ */
			insertPrepStmtObject=new PrepStmtObject();
			bufInsSql.append("insert into cr_obligation_analysis_dtl(DEAL_ID,DEAL_CUSTOMER_ROLE_TYPE,DEAL_CUSTOMER_ID,INSTITUTION_NAME,PURPOSE,TENURE,CLOSURE_DATE," +
					"OUTSTANDING_AMOUNT,EMI,FINANCED_AMOUNT,OBLIGATION_TYPE,VERIFICATION_METHOD,REC_STATUS,MAKER_ID,MAKER_DATE,LOAN_NO,LOAN_TYPE,LOAN_AMOUNT,LOAN_MONTH,LOAN_TENURE,EMI_PAID,EMI_PENDING,LOAN_STATUS,OBLIGATION_TO_BE_CONSIDER,TRACK_PER,EMI_PAID_FROM,COMMENTS,"+
					"LAST_STATEMENT_DATE,LAST_MONTH_1,BOUNCE_1,LAST_MONTH_2,BOUNCE_2,LAST_MONTH_3,BOUNCE_3,LAST_MONTH_4,BOUNCE_4,LAST_MONTH_5,BOUNCE_5,LAST_MONTH_6,BOUNCE_6, "+
					"LAST_MONTH_7,BOUNCE_7,LAST_MONTH_8,BOUNCE_8,LAST_MONTH_9,BOUNCE_9,LAST_MONTH_10,BOUNCE_10,LAST_MONTH_11,BOUNCE_11,LAST_MONTH_12,BOUNCE_12)"); 
					 
			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"')," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(" ?," );
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			bufInsSql.append(" ?," ); //LOAN_NO
			bufInsSql.append(" ?," ); //LOAN_TYPE
			bufInsSql.append(" ?," ); //LOAN_AMOUNT
			bufInsSql.append(" ?," ); //LOAN_MONTH
			bufInsSql.append(" ?," ); //LOAN_TENURE
			bufInsSql.append(" ?," ); //EMI_PAID
			bufInsSql.append(" ?," ); //EMI_PENDING
			bufInsSql.append(" ?," ); //LOAN_STATUS
			bufInsSql.append(" ?," ); //OBLIGATION_TO_BE_CONSIDER
			bufInsSql.append(" ?," ); //TRACK_PER
			bufInsSql.append(" ?," ); //EMI_PAID_FROM
			bufInsSql.append(" ?," ); //COMMENTS
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"')," ); //LAST_STATEMENT_DATE 
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"')," ); //LAST_MONTH_1  
			bufInsSql.append(" ?," ); //BOUNCE_1
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"')," ); //LAST_MONTH_2  
			bufInsSql.append(" ?," ); //BOUNCE_2 
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"')," ); //LAST_MONTH_3 
			bufInsSql.append(" ?," ); //BOUNCE_3 
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"')," ); //LAST_MONTH_4
			bufInsSql.append(" ?," ); //BOUNCE_4 
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"')," ); //LAST_MONTH_5 
			bufInsSql.append(" ?," ); //BOUNCE_5 
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"')," ); //LAST_MONTH_6 
			bufInsSql.append(" ?," ); //BOUNCE_6 
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"')," ); //LAST_MONTH_7 
			bufInsSql.append(" ?," ); //BOUNCE_7
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"')," ); //LAST_MONTH_8 
			bufInsSql.append(" ?," ); //BOUNCE_8 
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"')," ); //LAST_MONTH_9 
			bufInsSql.append(" ?," ); //BOUNCE_9 
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"')," ); //LAST_MONTH_10 
			bufInsSql.append(" ?," ); //BOUNCE_10 
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"')," ); //LAST_MONTH_11 
			bufInsSql.append(" ?," ); //BOUNCE_11
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"')," ); //LAST_MONTH_12 
			bufInsSql.append(" ?" ); //BOUNCE_12 
			bufInsSql.append(" )" ); 
			logger.info("bufInsSql....bufInsSql.saveObligation"+bufInsSql.toString());
				
			if(CommonFunction.checkNull(vo.getDealId()).trim().equalsIgnoreCase(""))//DEAL_ID
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getDealId()).trim());
			
			if(CommonFunction.checkNull(vo.getCustomerType()).trim().equalsIgnoreCase(""))//DEAL_CUSTOMER_ROLE_TYPE
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getCustomerType()).trim());
			
			if(CommonFunction.checkNull(vo.getCustomerName()).trim().equalsIgnoreCase(""))//DEAL_CUSTOMER_ID
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getCustomerName()).trim());
			
			if(CommonFunction.checkNull(vo.getInstitutionName()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getInstitutionName()).trim());
			
			if(CommonFunction.checkNull(vo.getPurpose()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getPurpose()).trim());
				
			if(CommonFunction.checkNull(vo.getTenure()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getTenure()).trim());
			if(CommonFunction.checkNull(vo.getMaturityDate()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getMaturityDate()).trim());
			if(CommonFunction.checkNull(vo.getOutstandingLoanAmount()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(vo.getOutstandingLoanAmount()).trim()).toString());
			
			if(CommonFunction.checkNull(vo.getEmiAmount()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(vo.getEmiAmount()).trim()).toString());
			
			if(CommonFunction.checkNull(vo.getFinanceAmount()).trim().equalsIgnoreCase(""))//FINANCED_AMOUNT
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(vo.getFinanceAmount()).trim()).toString());
				
			
			if(CommonFunction.checkNull(vo.getObligationType()).trim().equalsIgnoreCase(""))//OBLIGATION_TYPE
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getObligationType()).trim());
			
			if(CommonFunction.checkNull(vo.getVarificationMethod()).trim().equalsIgnoreCase(""))//VERIFICATION_METHOD
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getVarificationMethod()).trim());
			
			insertPrepStmtObject.addString("P");//,REC_STATUS
		
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getUserId())).trim().equals(""))//,MAKER_ID
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getUserId()).trim());
		
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getBusinessDate())).equalsIgnoreCase(""))//,MAKER_DATE
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getBusinessDate()));
			//Nishant Space starts
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLoanNo())).trim().equals(""))//LOAN_NO
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLoanNo()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getTypeOfLoan())).trim().equals(""))//LOAN_TYPE
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getTypeOfLoan()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLoanAmount())).trim().equals(""))//LOAN_AMOUNT
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(vo.getLoanAmount()).trim()).toString());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLoanMonth())).trim().equals(""))//LOAN_MONTH
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLoanMonth()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLoanTenure())).trim().equals(""))//LOAN_TENURE
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLoanTenure()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getEmiPaid())).trim().equals(""))//EMI_PAID
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getEmiPaid()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getEmiPending())).trim().equals(""))//EMI_PENDING
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getEmiPending()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLoanStatus())).trim().equals(""))//LOAN_STATUS
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLoanStatus()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getObligationConsider())).trim().equals(""))//OBLIGATION_TO_BE_CONSIDER
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getObligationConsider()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getTrackPer())).trim().equals(""))//TRACK_PER 
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getTrackPer()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getEmiPaidFrom())).trim().equals(""))//EMI_PAID_FROM
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getEmiPaidFrom()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getComment())).trim().equals(""))//COMMENTS
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getComment()).trim());
			//Nishant Space end
			
			// Nishant Space ends on 15-05-2013 
			if(CommonFunction.checkNull(vo.getLastStatementDate()).trim().equalsIgnoreCase(""))//LAST_STATEMENT_DATE
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLastStatementDate()).trim());
			if(CommonFunction.checkNull(vo.getLastMonth1()).trim().equalsIgnoreCase(""))//LAST_MONTH_1
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLastMonth1()).trim());
			if(CommonFunction.checkNull(vo.getBounce1()).trim().equalsIgnoreCase(""))//BOUNCE_1
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getBounce1()).trim());
			if(CommonFunction.checkNull(vo.getLastMonth2()).trim().equalsIgnoreCase(""))//LAST_MONTH_2
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLastMonth2()).trim());
			if(CommonFunction.checkNull(vo.getBounce2()).trim().equalsIgnoreCase(""))//BOUNCE_2
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getBounce2()).trim());
			if(CommonFunction.checkNull(vo.getLastMonth3()).trim().equalsIgnoreCase(""))//LAST_MONTH_3
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLastMonth3()).trim());
			if(CommonFunction.checkNull(vo.getBounce3()).trim().equalsIgnoreCase(""))//BOUNCE_3
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getBounce3()).trim());
			if(CommonFunction.checkNull(vo.getLastMonth4()).trim().equalsIgnoreCase(""))//LAST_MONTH_4
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLastMonth4()).trim());
			if(CommonFunction.checkNull(vo.getBounce4()).trim().equalsIgnoreCase(""))//BOUNCE_4
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getBounce4()).trim());
			if(CommonFunction.checkNull(vo.getLastMonth5()).trim().equalsIgnoreCase(""))//LAST_MONTH_5
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLastMonth5()).trim());
			if(CommonFunction.checkNull(vo.getBounce5()).trim().equalsIgnoreCase(""))//BOUNCE_5
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getBounce5()).trim());
			if(CommonFunction.checkNull(vo.getLastMonth6()).trim().equalsIgnoreCase(""))//LAST_MONTH_6
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLastMonth6()).trim());
			if(CommonFunction.checkNull(vo.getBounce6()).trim().equalsIgnoreCase(""))//BOUNCE_6
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getBounce6()).trim());
			if(CommonFunction.checkNull(vo.getLastMonth7()).trim().equalsIgnoreCase(""))//LAST_MONTH_7
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLastMonth7()).trim());
			if(CommonFunction.checkNull(vo.getBounce7()).trim().equalsIgnoreCase(""))//BOUNCE_7
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getBounce7()).trim());
			if(CommonFunction.checkNull(vo.getLastMonth8()).trim().equalsIgnoreCase(""))//LAST_MONTH_8
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLastMonth8()).trim());
			if(CommonFunction.checkNull(vo.getBounce8()).trim().equalsIgnoreCase(""))//BOUNCE_8
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getBounce8()).trim());
			if(CommonFunction.checkNull(vo.getLastMonth9()).trim().equalsIgnoreCase(""))//LAST_MONTH_9
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLastMonth9()).trim());
			if(CommonFunction.checkNull(vo.getBounce9()).trim().equalsIgnoreCase(""))//BOUNCE_9
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getBounce9()).trim());
			if(CommonFunction.checkNull(vo.getLastMonth10()).trim().equalsIgnoreCase(""))//LAST_MONTH_10
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLastMonth10()).trim());
			if(CommonFunction.checkNull(vo.getBounce10()).trim().equalsIgnoreCase(""))//BOUNCE_10
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getBounce10()).trim());
			if(CommonFunction.checkNull(vo.getLastMonth11()).trim().equalsIgnoreCase(""))//LAST_MONTH_11
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLastMonth11()).trim());
			if(CommonFunction.checkNull(vo.getBounce11()).trim().equalsIgnoreCase(""))//BOUNCE_11
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getBounce11()).trim());
			if(CommonFunction.checkNull(vo.getLastMonth12()).trim().equalsIgnoreCase(""))//LAST_MONTH_12
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLastMonth12()).trim());
			if(CommonFunction.checkNull(vo.getBounce12()).trim().equalsIgnoreCase(""))//BOUNCE_12
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getBounce12()).trim());
			// Nishant Space ends on 15-05-2013 

				insertPrepStmtObject.setSql(bufInsSql.toString());
				
				logger.info("IN saveObligation() insert query ### "+insertPrepStmtObject.printQuery());
				
				qryList.add(insertPrepStmtObject);
		      
		        boolean status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		        
			    logger.info("In saveObligation......................"+status);
				if(status)
				{
					StringBuilder query3=new StringBuilder();
					query3.append("Select max(OBLIGATION_ID) from cr_obligation_analysis_dtl  WITH (ROWLOCK) ");
					 String id = ConnectionDAO.singleReturn(query3.toString());
					 maxId=Integer.parseInt(id);
					 logger.info("maxId : "+maxId);
					 result="saved";
					 query3=null;
				}else{
					result="notsaved";	
				}
				bufInsSql=null;
	/*		}else{
				result="already";	
			} */
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return result;
		
	}
	
	public ArrayList getObligationDetails(String obligationId,String dealId,String recStatus) {
		ArrayList<Object> list=new ArrayList<Object>();
		try{
			StringBuilder query=new StringBuilder();
	query.append(" select OBLIGATION_ID,DEAL_ID,INSTITUTION_NAME,PURPOSE,TENURE,");
	query.append(dbo);
	query.append("DATE_FORMAT(CLOSURE_DATE,'"+dateFormat+"'),OUTSTANDING_AMOUNT,EMI," );
	query.append("DEAL_CUSTOMER_ROLE_TYPE,DEAL_CUSTOMER_ID,FINANCED_AMOUNT,OBLIGATION_TYPE,VERIFICATION_METHOD,LOAN_NO,LOAN_TYPE,LOAN_AMOUNT,LOAN_MONTH,LOAN_TENURE,EMI_PAID,EMI_PENDING,LOAN_STATUS,OBLIGATION_TO_BE_CONSIDER,TRACK_PER,EMI_PAID_FROM,COMMENTS,");
	query.append(dbo);
	query.append("DATE_FORMAT(LAST_STATEMENT_DATE,'"+dateFormat+"'),");
	query.append(dbo);
	query.append("DATE_FORMAT(LAST_MONTH_1,'"+dateFormat+"'),BOUNCE_1,");
	query.append(dbo);
	query.append("DATE_FORMAT(LAST_MONTH_2,'"+dateFormat+"'),BOUNCE_2,");
	query.append(dbo);
	query.append("DATE_FORMAT(LAST_MONTH_3,'"+dateFormat+"'),BOUNCE_3,");
	query.append(dbo);
	query.append("DATE_FORMAT(LAST_MONTH_4,'"+dateFormat+"'),BOUNCE_4,");
	query.append(dbo);
	query.append("DATE_FORMAT(LAST_MONTH_5,'"+dateFormat+"'),BOUNCE_5,");
	query.append(dbo);
	query.append("DATE_FORMAT(LAST_MONTH_6,'"+dateFormat+"'),BOUNCE_6,");
	query.append(dbo);
	query.append("DATE_FORMAT(LAST_MONTH_7,'"+dateFormat+"'),BOUNCE_7,");
	query.append(dbo);
	query.append("DATE_FORMAT(LAST_MONTH_8,'"+dateFormat+"'),BOUNCE_8,");
	query.append(dbo);
	query.append("DATE_FORMAT(LAST_MONTH_9,'"+dateFormat+"'),BOUNCE_9,");
	query.append(dbo);
	query.append("DATE_FORMAT(LAST_MONTH_10,'"+dateFormat+"'),BOUNCE_10,");
	query.append(dbo);
	query.append("DATE_FORMAT(LAST_MONTH_11,'"+dateFormat+"'),BOUNCE_11,");
	query.append(dbo);
	query.append("DATE_FORMAT(LAST_MONTH_12,'"+dateFormat+"'),BOUNCE_12 ");
	query.append(" from cr_obligation_analysis_dtl where REC_STATUS='"+recStatus+"'");
		if(obligationId.trim()!=null && !obligationId.trim().equalsIgnoreCase(""))
		{
			query.append(" and OBLIGATION_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(obligationId)).trim());
		}
		if(dealId!=null && !dealId.trim().equalsIgnoreCase(""))
		{
			query.append( " and DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim());
		}
		logger.info("getObligationDetails: "+query.toString());
		ObligationVo vo = null;
		ArrayList obligationDetail = ConnectionDAO.sqlSelect(query.toString());
		
		query=null;

		for(int i=0;i<obligationDetail.size();i++){

			ArrayList subObligationDetail=(ArrayList)obligationDetail.get(i);
			if(subObligationDetail.size()>0)
			{
				vo = new ObligationVo();
				
				vo.setObligationId((CommonFunction.checkNull(subObligationDetail.get(0))).trim());
				vo.setDealId((CommonFunction.checkNull(subObligationDetail.get(1))).trim());
				vo.setInstitutionName((CommonFunction.checkNull(subObligationDetail.get(2))).trim());
				vo.setPurpose((CommonFunction.checkNull(subObligationDetail.get(3))).trim());
				vo.setTenure((CommonFunction.checkNull(subObligationDetail.get(4))).trim());
				vo.setMaturityDate((CommonFunction.checkNull(subObligationDetail.get(5))).trim());
				
				if(!CommonFunction.checkNull(subObligationDetail.get(6)).equalsIgnoreCase(""))
				{
					Number reconNumb =myFormatter.parse((CommonFunction.checkNull(subObligationDetail.get(6))).trim());
					vo.setOutstandingLoanAmount(myFormatter.format(reconNumb));
				}
				if(!CommonFunction.checkNull(subObligationDetail.get(7)).equalsIgnoreCase(""))
				{
					Number reconNumb =myFormatter.parse((CommonFunction.checkNull(subObligationDetail.get(7))).trim());
					vo.setEmiAmount(myFormatter.format(reconNumb));
				}
				vo.setCustomerType(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(8))).trim());
				vo.setCustomerName(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(9))).trim());
				if(!CommonFunction.checkNull(subObligationDetail.get(10)).equalsIgnoreCase(""))
				{
					Number reconNumb =myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(10))).trim());
					vo.setFinanceAmount(myFormatter.format(reconNumb));
				}
				vo.setObligationType(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(11))).trim());
				vo.setVarificationMethod(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(12))).trim());
				vo.setLoanNo(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(13))).trim());
				vo.setTypeOfLoan(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(14))).trim());
				vo.setLoanAmount(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(15))).trim());
				vo.setLoanMonth(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(16))).trim());
				vo.setLoanTenure(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(17))).trim());
				vo.setEmiPaid(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(18))).trim());
				vo.setEmiPending(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(19))).trim());
				vo.setLoanStatus(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(20))).trim());
				vo.setObligationConsider(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(21))).trim());
				vo.setTrackPer(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(22))).trim());
				vo.setEmiPaidFrom(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(23))).trim());
				vo.setComment(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(24))).trim());
				vo.setLastStatementDate(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(25))).trim()); //LAST_STATEMENT_DATE 
				vo.setLastMonth1(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(26))).trim());//LAST_MONTH_1  
				vo.setBounce1(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(27))).trim());//BOUNCE_1
				vo.setLastMonth2(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(28))).trim());//LAST_MONTH_2  
				vo.setBounce2(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(29))).trim()); //BOUNCE_2 
				vo.setLastMonth3(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(30))).trim()); //LAST_MONTH_3 
				vo.setBounce3(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(31))).trim());//BOUNCE_3 
				vo.setLastMonth4(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(32))).trim()); //LAST_MONTH_4
				vo.setBounce4(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(33))).trim());//BOUNCE_4 
				vo.setLastMonth5(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(34))).trim());//LAST_MONTH_5 
				vo.setBounce5(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(35))).trim());//BOUNCE_5 
				vo.setLastMonth6(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(36))).trim());//LAST_MONTH_6 
				vo.setBounce6(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(37))).trim());//BOUNCE_6 
				vo.setLastMonth7(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(38))).trim()); //LAST_MONTH_7 
				vo.setBounce7(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(39))).trim());//BOUNCE_7
				vo.setLastMonth8(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(40))).trim());//LAST_MONTH_8 
				vo.setBounce8(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(41))).trim());//BOUNCE_8 
				vo.setLastMonth9(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(42))).trim()); //LAST_MONTH_9 
				vo.setBounce9(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(43))).trim()); //BOUNCE_9 
				vo.setLastMonth10(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(44))).trim()); //LAST_MONTH_10 
				vo.setBounce10(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(45))).trim());//BOUNCE_10 
				vo.setLastMonth11(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(46))).trim());//LAST_MONTH_11 
				vo.setBounce11(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(47))).trim());//BOUNCE_11
				vo.setLastMonth12(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(48))).trim()); //LAST_MONTH_12 
				vo.setBounce12(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subObligationDetail.get(49))).trim());//BOUNCE_12 
			   list.add(vo);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.info("size of list in dao................................"+list.size());
		return list;
	}
	
	public int deleteObligationDetails(String id)
	{
		
            int status=0;
            boolean qryStatus = false;
           
            ArrayList qryList = new ArrayList();
   		
		try{
			StringBuilder query=new StringBuilder();
		query.append("delete from cr_obligation_analysis_dtl where OBLIGATION_ID=?");
			PrepStmtObject insPrepStmtObject = new PrepStmtObject();
			if(CommonFunction.checkNull(id).trim().equalsIgnoreCase(""))
				insPrepStmtObject.addNull();
			else
				insPrepStmtObject.addString((CommonFunction.checkNull(id)).trim());
			
			insPrepStmtObject.setSql(query.toString());
	        logger.info("IN deleteObligationDetails() update query ### "+insPrepStmtObject.printQuery());
			qryList.add(insPrepStmtObject);
			qryStatus=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("Deletion Status :."+qryStatus);
			
			query=null;
			
			}catch(Exception e){
				e.printStackTrace();
			}
			if(qryStatus)
			{
				status=1;
			}
			return status;
		}
	
	public String updateObligation(Object ob)
	  {
		logger.info("In update updateObligation........ ");
		ObligationVo vo=(ObligationVo)ob;
		int maxId=0;
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		boolean status = false;
		String result="";
		try
		{
			StringBuilder queryUpdate=new StringBuilder();
		/*	String query="select deal_id from cr_obligation_analysis_dtl where DEAL_CUSTOMER_ROLE_TYPE='"+vo.getCustomerType()+"' and OBLIGATION_TYPE='"+vo.getObligationType()+"' and DEAL_ID='"+vo.getDealId()+"' and OBLIGATION_ID <>'"+vo.getObligationId()+"'";
			Boolean check=ConnectionDAO.checkStatus(query);
	   		logger.info("check:-----"+check)	;
	   		logger.info("check:-----Data Already Exists")	;
	   		if(!check){ */
	   			queryUpdate.append("update cr_obligation_analysis_dtl set DEAL_CUSTOMER_ROLE_TYPE=?,DEAL_CUSTOMER_ID=?, INSTITUTION_NAME=?,PURPOSE=?,TENURE=?,CLOSURE_DATE=");
	   			queryUpdate.append(dbo);
	   			queryUpdate.append("STR_TO_DATE(?,'"+dateFormatWithTime+"'),OUTSTANDING_AMOUNT=?," );
	   			queryUpdate.append("EMI=?,FINANCED_AMOUNT=?,OBLIGATION_TYPE=?,VERIFICATION_METHOD=?,MAKER_ID=?,MAKER_DATE=");
			    queryUpdate.append(dbo);
	   			queryUpdate.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),LOAN_NO=?,LOAN_TYPE=?,LOAN_AMOUNT=?,LOAN_MONTH=?,LOAN_TENURE=?,EMI_PAID=?,EMI_PENDING=?,LOAN_STATUS=?,OBLIGATION_TO_BE_CONSIDER=?,TRACK_PER=?,EMI_PAID_FROM=?,COMMENTS=?,");
	   			queryUpdate.append("LAST_STATEMENT_DATE=");
	   			queryUpdate.append(dbo);
	   			queryUpdate.append("STR_TO_DATE(?,'"+dateFormatWithTime+"'),LAST_MONTH_1=");
	   			queryUpdate.append(dbo);
	   			queryUpdate.append("STR_TO_DATE(?,'"+dateFormatWithTime+"'),BOUNCE_1=?,LAST_MONTH_2=");
	   			queryUpdate.append(dbo);
	   			queryUpdate.append("STR_TO_DATE(?,'"+dateFormatWithTime+"'),BOUNCE_2=?,LAST_MONTH_3=");
	   			queryUpdate.append(dbo);
	   			queryUpdate.append("STR_TO_DATE(?,'"+dateFormatWithTime+"'),BOUNCE_3=?,LAST_MONTH_4=");
	   			queryUpdate.append(dbo);
	   			queryUpdate.append("STR_TO_DATE(?,'"+dateFormatWithTime+"'),BOUNCE_4=?,LAST_MONTH_5=");
	   			queryUpdate.append(dbo);
	   			queryUpdate.append("STR_TO_DATE(?,'"+dateFormatWithTime+"'),BOUNCE_5=?,LAST_MONTH_6=");
	   			queryUpdate.append(dbo);
	   			queryUpdate.append("STR_TO_DATE(?,'"+dateFormatWithTime+"'),BOUNCE_6=?,LAST_MONTH_7=");
	   			queryUpdate.append(dbo);
	   			queryUpdate.append("STR_TO_DATE(?,'"+dateFormatWithTime+"'),BOUNCE_7=?,LAST_MONTH_8=");
	   			queryUpdate.append(dbo);
	   			queryUpdate.append("STR_TO_DATE(?,'"+dateFormatWithTime+"'),BOUNCE_8=?,LAST_MONTH_9=");
	   			queryUpdate.append(dbo);
	   			queryUpdate.append("STR_TO_DATE(?,'"+dateFormatWithTime+"'),BOUNCE_9=?,LAST_MONTH_10=");
	   			queryUpdate.append(dbo);
	   			queryUpdate.append("STR_TO_DATE(?,'"+dateFormatWithTime+"'),BOUNCE_10=?,LAST_MONTH_11=");
	   			queryUpdate.append(dbo);
	   			queryUpdate.append("STR_TO_DATE(?,'"+dateFormatWithTime+"'),BOUNCE_11=?,LAST_MONTH_12=");
	   			queryUpdate.append(dbo);
	   			queryUpdate.append("STR_TO_DATE(?,'"+dateFormatWithTime+"'),BOUNCE_12=? ");
	   			queryUpdate.append(" where OBLIGATION_ID=?");
		
		  PrepStmtObject prepStmt = new PrepStmtObject();
			if(CommonFunction.checkNull(vo.getCustomerType()).trim().equalsIgnoreCase(""))//DEAL_CUSTOMER_ROLE_TYPE
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getCustomerType()).trim());
			
			if(CommonFunction.checkNull(vo.getCustomerName()).trim().equalsIgnoreCase(""))//DEAL_CUSTOMER_ID
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getCustomerName()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInstitutionName())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getInstitutionName()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getPurpose())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getPurpose()).trim());
			
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getTenure())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getTenure()).trim());
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMaturityDate())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getMaturityDate()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getOutstandingLoanAmount())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(vo.getOutstandingLoanAmount()).trim()).toString());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getEmiAmount())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(vo.getEmiAmount()).trim()).toString());
			
			if(CommonFunction.checkNull(vo.getFinanceAmount()).trim().equalsIgnoreCase(""))//FINANCED_AMOUNT
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(vo.getFinanceAmount()).trim()).toString());
				
			
			if(CommonFunction.checkNull(vo.getObligationType()).trim().equalsIgnoreCase(""))//OBLIGATION_TYPE
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getObligationType()).trim());
			
			if(CommonFunction.checkNull(vo.getVarificationMethod()).trim().equalsIgnoreCase(""))//VERIFICATION_METHOD
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getVarificationMethod()).trim());
			
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getUserId())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getUserId()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getBusinessDate())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getBusinessDate()).trim());
			
			//Nishant Space starts
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLoanNo())).trim().equals(""))//LOAN_NO
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLoanNo()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getTypeOfLoan())).trim().equals(""))//LOAN_TYPE
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getTypeOfLoan()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLoanAmount())).trim().equals(""))//LOAN_AMOUNT
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse(StringEscapeUtils.escapeSql(vo.getLoanAmount()).trim()).toString());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLoanMonth())).trim().equals(""))//LOAN_MONTH
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLoanMonth()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLoanTenure())).trim().equals(""))//LOAN_TENURE
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLoanTenure()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getEmiPaid())).trim().equals(""))//EMI_PAID
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getEmiPaid()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getEmiPending())).trim().equals(""))//EMI_PENDING
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getEmiPending()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLoanStatus())).trim().equals(""))//LOAN_STATUS
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLoanStatus()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getObligationConsider())).trim().equals(""))//OBLIGATION_TO_BE_CONSIDER
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getObligationConsider()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getTrackPer())).trim().equals(""))//TRACK_PER 
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getTrackPer()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getEmiPaidFrom())).trim().equals(""))//EMI_PAID_FROM
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getEmiPaidFrom()).trim());
			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getComment())).trim().equals(""))//COMMENTS
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getComment()).trim());
			//Nishant Space end
			
			// Nishant Space ends on 15-05-2013 
			if(CommonFunction.checkNull(vo.getLastStatementDate()).trim().equalsIgnoreCase(""))//LAST_STATEMENT_DATE
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLastStatementDate()).trim());
			if(CommonFunction.checkNull(vo.getLastMonth1()).trim().equalsIgnoreCase(""))//LAST_MONTH_1
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLastMonth1()).trim());
			if(CommonFunction.checkNull(vo.getBounce1()).trim().equalsIgnoreCase(""))//BOUNCE_1
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getBounce1()).trim());
			if(CommonFunction.checkNull(vo.getLastMonth2()).trim().equalsIgnoreCase(""))//LAST_MONTH_2
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLastMonth2()).trim());
			if(CommonFunction.checkNull(vo.getBounce2()).trim().equalsIgnoreCase(""))//BOUNCE_2
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getBounce2()).trim());
			if(CommonFunction.checkNull(vo.getLastMonth3()).trim().equalsIgnoreCase(""))//LAST_MONTH_3
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLastMonth3()).trim());
			if(CommonFunction.checkNull(vo.getBounce3()).trim().equalsIgnoreCase(""))//BOUNCE_3
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getBounce3()).trim());
			if(CommonFunction.checkNull(vo.getLastMonth4()).trim().equalsIgnoreCase(""))//LAST_MONTH_4
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLastMonth4()).trim());
			if(CommonFunction.checkNull(vo.getBounce4()).trim().equalsIgnoreCase(""))//BOUNCE_4
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getBounce4()).trim());
			if(CommonFunction.checkNull(vo.getLastMonth5()).trim().equalsIgnoreCase(""))//LAST_MONTH_5
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLastMonth5()).trim());
			if(CommonFunction.checkNull(vo.getBounce5()).trim().equalsIgnoreCase(""))//BOUNCE_5
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getBounce5()).trim());
			if(CommonFunction.checkNull(vo.getLastMonth6()).trim().equalsIgnoreCase(""))//LAST_MONTH_6
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLastMonth6()).trim());
			if(CommonFunction.checkNull(vo.getBounce6()).trim().equalsIgnoreCase(""))//BOUNCE_6
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getBounce6()).trim());
			if(CommonFunction.checkNull(vo.getLastMonth7()).trim().equalsIgnoreCase(""))//LAST_MONTH_7
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLastMonth7()).trim());
			if(CommonFunction.checkNull(vo.getBounce7()).trim().equalsIgnoreCase(""))//BOUNCE_7
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getBounce7()).trim());
			if(CommonFunction.checkNull(vo.getLastMonth8()).trim().equalsIgnoreCase(""))//LAST_MONTH_8
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLastMonth8()).trim());
			if(CommonFunction.checkNull(vo.getBounce8()).trim().equalsIgnoreCase(""))//BOUNCE_8
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getBounce8()).trim());
			if(CommonFunction.checkNull(vo.getLastMonth9()).trim().equalsIgnoreCase(""))//LAST_MONTH_9
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLastMonth9()).trim());
			if(CommonFunction.checkNull(vo.getBounce9()).trim().equalsIgnoreCase(""))//BOUNCE_9
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getBounce9()).trim());
			if(CommonFunction.checkNull(vo.getLastMonth10()).trim().equalsIgnoreCase(""))//LAST_MONTH_10
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLastMonth10()).trim());
			if(CommonFunction.checkNull(vo.getBounce10()).trim().equalsIgnoreCase(""))//BOUNCE_10
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getBounce10()).trim());
			if(CommonFunction.checkNull(vo.getLastMonth11()).trim().equalsIgnoreCase(""))//LAST_MONTH_11
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLastMonth11()).trim());
			if(CommonFunction.checkNull(vo.getBounce11()).trim().equalsIgnoreCase(""))//BOUNCE_11
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getBounce11()).trim());
			if(CommonFunction.checkNull(vo.getLastMonth12()).trim().equalsIgnoreCase(""))//LAST_MONTH_12
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLastMonth12()).trim());
			if(CommonFunction.checkNull(vo.getBounce12()).trim().equalsIgnoreCase(""))//BOUNCE_12
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getBounce12()).trim());
			// Nishant Space ends on 15-05-2013 

			
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getObligationId())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getObligationId()).trim());
			
			insertPrepStmtObject.setSql(queryUpdate.toString());
			logger.info("IN updateObligation() update query ### "+insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			logger.info("In updateObligation ........ update query: "+queryUpdate.toString());
			
				status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In updateObligation.........update status: "+status);
			if(status){
				result="saved";
			}else{
				result="notSaved";	
			}
				queryUpdate=null;
	  /* 		}else{
	   			result="already";	
	   		}	*/
		}catch(Exception e){
			e.printStackTrace();
		}

		return result;

	}
	
	public ArrayList<CommonDealVo> fundFlowSearchGetDetail(CommonDealVo vo) {
		ArrayList list=new ArrayList();
		try
		{
			
			
			logger.info("Inside fundFlowSearchGetDetail.....");
			ArrayList header=null;
			ArrayList headerCount=null;
			int count=0;
			int startRecordIndex=0;
			int endRecordIndex=no;
			StringBuffer bufInsSqlTempCount = new StringBuffer();
//			logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+vo.getReportingToUserId());
//			 			String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+vo.getReportingToUserId()+"'";
//			 			String userName=ConnectionDAO.singleReturn(userNameQ);
//			 			logger.info("userNameQ: "+userNameQ+" userName: "+userName);
//			 			
//			 			userNameQ=null;
			 			
			CommonDealVo fetchVo= (CommonDealVo) vo;
			boolean appendSQL=false;
			
			StringBuffer bufInsSql=new StringBuffer();
			//StringBuffer bufInsSqlTempCount = new StringBuffer();
			bufInsSql.append(" select distinct d.DEAL_ID,DEAL_NO,deal.CUSTOMER_NAME,p.PRODUCT_DESC,s.SCHEME_DESC,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=d.MAKER_ID) MAKER_ID ");
			bufInsSql.append(" from cr_deal_dtl d");
			bufInsSql.append(" left join cr_deal_loan_dtl n on d.DEAL_ID=n.DEAL_ID  ");			
			bufInsSql.append(" left join cr_product_m p on n.DEAL_PRODUCT=p.PRODUCT_ID  ");			
			bufInsSql.append(" left join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID ");			
			bufInsSql.append(" left join cr_scheme_m s on n.DEAL_SCHEME=s.SCHEME_ID ");
			bufInsSql.append(" INNER JOIN cr_deal_movement_dtl M ON M.DEAL_ID=D.DEAL_ID AND M.DEAL_RECEIVED IS NOT NULL AND M.DEAL_FORWARDED IS NULL AND M.DEAL_STAGE_ID='FFC' AND M.REC_STATUS='A' ");
			
			bufInsSqlTempCount.append(" select count(1) from(select distinct d.DEAL_ID from cr_deal_dtl d");
			bufInsSqlTempCount.append(" left join cr_deal_loan_dtl n on d.DEAL_ID=n.DEAL_ID  ");			
			bufInsSqlTempCount.append(" left join cr_product_m p on n.DEAL_PRODUCT=p.PRODUCT_ID  ");			
			bufInsSqlTempCount.append(" left join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID ");			
			bufInsSqlTempCount.append(" left join cr_scheme_m s on n.DEAL_SCHEME=s.SCHEME_ID ");		
			bufInsSqlTempCount.append(" INNER JOIN cr_deal_movement_dtl M ON M.DEAL_ID=D.DEAL_ID AND M.DEAL_RECEIVED IS NOT NULL AND M.DEAL_FORWARDED IS NULL AND M.DEAL_STAGE_ID='FFC' AND M.REC_STATUS='A' ");
			
			
			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomername()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme()).trim()).equalsIgnoreCase(""))))
			{
				bufInsSql.append("WHERE d.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and d.DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' AND deal.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomername()).trim()+"%' AND n.DEAL_PRODUCT='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"'AND n.DEAL_SCHEME='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"' AND d.Rec_status='F'");
				bufInsSqlTempCount.append("WHERE d.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and d.DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' AND deal.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomername()).trim()+"%' AND n.DEAL_PRODUCT='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"'AND n.DEAL_SCHEME='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"' AND d.Rec_status='F'");
			}
			
			if(((vo.getLbxDealNo().equalsIgnoreCase("")))||((vo.getInitiationDate().equalsIgnoreCase("")))||((vo.getCustomername().equalsIgnoreCase("")))||((vo.getLbxProductID().equalsIgnoreCase("")))||((vo.getLbxscheme().equalsIgnoreCase("")))){
				appendSQL=true;
			}
			logger.info("value of deaol no "+vo.getLbxDealNo());
			if(appendSQL){
				logger.info("In Where Clause");
				bufInsSql.append(" WHERE d.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and d.Rec_status='F' and d.DEAL_ID not in (select DEAL_ID from cr_bank_analysis_dtl where REC_STATUS='"+vo.getRecStatus()+"' ");
				bufInsSqlTempCount.append(" WHERE d.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and d.Rec_status='F' and d.DEAL_ID not in  (select DEAL_ID from cr_bank_analysis_dtl where REC_STATUS='"+vo.getRecStatus()+"' ");
				
				if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getReportingToUserId())).trim().equalsIgnoreCase("")))) {
				   	bufInsSql.append("AND MAKER_ID!='"+StringEscapeUtils.escapeSql(vo.getReportingToUserId()).trim()+"') ");
				   	bufInsSqlTempCount.append("AND MAKER_ID!='"+StringEscapeUtils.escapeSql(vo.getReportingToUserId()).trim()+"') ");
				   	  appendSQL=true;
				     }
				else{
					bufInsSql.append("AND MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getUserId()).trim()+"') ");				   
					bufInsSqlTempCount.append("AND MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getUserId()).trim()+"') ");
					appendSQL=true;					
				}
			}

			 if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo())).trim().equalsIgnoreCase("")))) {
		        bufInsSql.append("AND d.DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' ");		      
		        bufInsSqlTempCount.append("AND d.DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' ");
		   	 appendSQL=true;
		   	  
		     }
			
			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomername())).trim().equalsIgnoreCase("")))) {
		   	  bufInsSql.append("AND deal.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomername()).trim()+"%' ");		   
		   	bufInsSqlTempCount.append("AND deal.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomername()).trim()+"%' ");
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
			bufInsSqlTempCount.append(" ) as b");
		
			logger.info("query.....fundFlowSearchGetDetail"+bufInsSql.toString());
			logger.info("count query new is ::::::::"+bufInsSqlTempCount.toString());
			
			//count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
			//logger.info("count query is :::::::"+count);
		    header = ConnectionDAO.sqlSelect(bufInsSql.toString());
//		    headerCount= ConnectionDAO.sqlSelect(bufInsSqlTempCount.toString());
			count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
		   
//		    if((StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()==null && StringEscapeUtils.escapeSql(vo.getCustomername()).trim()==null) || (StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim().equalsIgnoreCase("") && StringEscapeUtils.escapeSql(vo.getCustomername()).trim().equalsIgnoreCase("")) || (StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()==null  && StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim().equalsIgnoreCase("")) || ( StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()==null && StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim().equalsIgnoreCase("")) || vo.getCurrentPageLink()>1)
//			{
			
			logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
			if(vo.getCurrentPageLink()>1)
			{
				startRecordIndex = (vo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex ....................fund "+startRecordIndex);
				logger.info("endRecordIndex ....................fund "+endRecordIndex);
			}
			bufInsSql.append(" ORDER BY d.DEAL_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");		
//			bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));		
//			}
		    bufInsSql=null;
		    //bufInsSqlTempCount=null;
//		    for(int i=0;i<headerCount.size();i++){
//				logger.info("header: "+headerCount.size());
//				ArrayList headerCount1=(ArrayList)headerCount.get(i);
//				if(headerCount1!=null && headerCount1.size()>0)
//				{
//					fetchVo = new CommonDealVo();
//					count=Integer.parseInt((CommonFunction.checkNull(headerCount1.get(6))).trim());
//					logger.info("vaue of my modified count::::::"+count);				
//				}
//			}
						
			for(int i=0;i<header.size();i++){
				logger.info("header: "+header.size());
				ArrayList header1=(ArrayList)header.get(i);
				if(header1!=null && header1.size()>0)
				{
					fetchVo = new CommonDealVo();				
					fetchVo.setLbxDealNo((CommonFunction.checkNull(CommonFunction.checkNull(header1.get(0)))).trim());
					//fetchVo.setDealNo((CommonFunction.checkNull(header1.get(1))).trim());
					fetchVo.setDealNo("<a href=\"#\" onclick=\"editFundFlowAnalysis('"+CommonFunction.checkNull(header1.get(0)).toString()+ "','"+CommonFunction.checkNull(header1.get(1)).toString()+"','"+CommonFunction.checkNull(header1.get(2)).toString()+"');\">"+ CommonFunction.checkNull(header1.get(1)).toString() + "</a>");
					fetchVo.setCustomername((CommonFunction.checkNull(header1.get(2))).trim());
					fetchVo.setProduct((CommonFunction.checkNull(header1.get(3))).trim());
					fetchVo.setScheme((CommonFunction.checkNull(header1.get(4))).trim());
					fetchVo.setReportingToUserId(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(header1.get(5))).trim());
					fetchVo.setTotalRecordSize(count);
					list.add(fetchVo);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		logger.info("Detail List when searchList is : "+list.size());
		
		return list;
	}
	
	public ArrayList<CommonDealVo> fundFlowSearchGetDetailBehind(CommonDealVo vo,HttpServletRequest request) {
		ArrayList list=new ArrayList();
		
		
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex =no;
		ArrayList headerCount=null;

//		logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+vo.getReportingToUserId());
//		 			String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+vo.getReportingToUserId()+"'";
//		 			String userName=ConnectionDAO.singleReturn(userNameQ);
//		 			logger.info("userNameQ: "+userNameQ+" userName: "+userName);
//		 			
//		 			userNameQ=null;
		 			
		ArrayList header=new ArrayList();
		try
		{
			logger.info("Inside fundFlowSearchGetDetailBehind.....");
			
			CommonDealVo fetchVo= (CommonDealVo) vo;
			boolean appendSQL=false;
			StringBuffer bufInsSql=new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
					
			bufInsSql.append(" select distinct d.DEAL_ID,DEAL_NO,deal.CUSTOMER_NAME,p.PRODUCT_DESC,s.SCHEME_DESC,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=d.MAKER_ID) MAKER_ID ");
			bufInsSql.append("  from cr_deal_dtl d");
			bufInsSql.append(" left join cr_deal_loan_dtl n on d.DEAL_ID=n.DEAL_ID  ");			
			bufInsSql.append(" left join cr_product_m p on n.DEAL_PRODUCT=p.PRODUCT_ID  ");			
			bufInsSql.append(" left join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID ");			
			bufInsSql.append(" left join cr_scheme_m s on n.DEAL_SCHEME=s.SCHEME_ID ");	
			bufInsSql.append(" INNER JOIN cr_deal_movement_dtl M ON M.DEAL_ID=D.DEAL_ID AND M.DEAL_RECEIVED IS NOT NULL AND M.DEAL_FORWARDED IS NULL  AND M.DEAL_STAGE_ID='FFC' AND M.REC_STATUS='A' ");		
			
			bufInsSqlTempCount.append(" select count(1) from (select distinct d.DEAL_ID,DEAL_NO,deal.CUSTOMER_NAME,p.PRODUCT_DESC,s.SCHEME_DESC,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=d.MAKER_ID) MAKER_ID ");
			bufInsSqlTempCount.append("  from cr_deal_dtl d");
			bufInsSqlTempCount.append(" left join cr_deal_loan_dtl n on d.DEAL_ID=n.DEAL_ID  ");			
			bufInsSqlTempCount.append(" left join cr_product_m p on n.DEAL_PRODUCT=p.PRODUCT_ID  ");			
			bufInsSqlTempCount.append(" left join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID ");			
			bufInsSqlTempCount.append(" left join cr_scheme_m s on n.DEAL_SCHEME=s.SCHEME_ID ");	
			bufInsSqlTempCount.append(" INNER JOIN cr_deal_movement_dtl M ON M.DEAL_ID=D.DEAL_ID AND M.DEAL_RECEIVED IS NOT NULL AND M.DEAL_FORWARDED IS NULL AND M.DEAL_STAGE_ID='FFC' AND M.REC_STATUS='A' ");
			//bufInsSqlTempCount.append("SELECT COUNT(distinct deal_id) FROM cr_deal_dtl d ");
			
			if((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomername()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID()).trim()).equalsIgnoreCase("")))&&(!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme()).trim()).equalsIgnoreCase(""))))
			{
				bufInsSql.append("WHERE d.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and d.DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' AND deal.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomername()).trim()+"%' AND n.DEAL_PRODUCT='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"'AND n.DEAL_SCHEME='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"' AND d.Rec_status='F'");
				bufInsSqlTempCount.append("WHERE d.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and d.DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+"' AND deal.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomername()).trim()+"%' AND n.DEAL_PRODUCT='"+StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()+"'AND n.DEAL_SCHEME='"+StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+"' AND d.Rec_status='F'");
			}
			
			if(((vo.getLbxDealNo().equalsIgnoreCase("")) ||(vo.getCustomername().equalsIgnoreCase(""))||(vo.getLbxProductID().equalsIgnoreCase(""))||(vo.getLbxscheme().equalsIgnoreCase("")))){
				appendSQL=true;
			}
			
			if(appendSQL)
			{
				logger.info("In Where Clause");
				bufInsSql.append(" WHERE d.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and d.Rec_status='F' or d.DEAL_ID in (select DEAL_ID from cr_bank_analysis_dtl where REC_STATUS='"+vo.getFundFlowDealStatus()+"'");
				bufInsSqlTempCount.append(" WHERE d.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and d.Rec_status='F' or d.DEAL_ID in (select DEAL_ID from cr_bank_analysis_dtl where REC_STATUS='"+vo.getFundFlowDealStatus()+"' ");
				
				if (((StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getReportingToUserId())).trim().equalsIgnoreCase(""))))
				{
					bufInsSql.append(" AND MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getUserId()).trim()+"') ");	
					bufInsSqlTempCount.append(" AND MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getUserId()).trim()+"') GROUP BY d.DEAL_ID,DEAL_NO,deal.CUSTOMER_NAME,p.PRODUCT_DESC,s.SCHEME_DESC,d.MAKER_ID");
					appendSQL = true;
					logger.debug("Rituuuuuuuuuuu a");				
				}
				else{
					bufInsSql.append(" AND MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getReportingToUserId()).trim()+"') ");	
					bufInsSqlTempCount.append(" AND MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getReportingToUserId()).trim()+"') GROUP BY d.DEAL_ID,DEAL_NO,deal.CUSTOMER_NAME,p.PRODUCT_DESC,s.SCHEME_DESC,d.MAKER_ID ");
					appendSQL = true;
					logger.debug("Rituuuuuuuuuuu b");
				    }
				
			}
			bufInsSqlTempCount.append(") as b");

			logger.info("query : "+bufInsSql);
			
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
			
//			headerCount= ConnectionDAO.sqlSelect(bufInsSqlTempCount.toString());
			 count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
			
			//count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
			
			
			bufInsSqlTempCount=null;
//			if((StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()==null && StringEscapeUtils.escapeSql(vo.getCustomername()).trim()==null) || (StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim().equalsIgnoreCase("") && StringEscapeUtils.escapeSql(vo.getCustomername()).trim().equalsIgnoreCase("")) || (StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()==null  && StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim().equalsIgnoreCase("")) || ( StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()==null && StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim().equalsIgnoreCase("")) || vo.getCurrentPageLink()>1)
//			{
			
			logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
			if(vo.getCurrentPageLink()>1)
			{
				startRecordIndex = (vo.getCurrentPageLink()-1)*no;
				endRecordIndex =no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			bufInsSql.append(" ORDER BY d.DEAL_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
//			bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));		
			
//			}
			logger.info("query : "+bufInsSql);
			
		    header = ConnectionDAO.sqlSelect(bufInsSql.toString());
//		    for(int i=0;i<headerCount.size();i++){
//				logger.info("header: "+headerCount.size());
//				ArrayList headerCount1=(ArrayList)headerCount.get(i);
//				if(headerCount1!=null && headerCount1.size()>0)
//				{
//					fetchVo = new CommonDealVo();
//					count=Integer.parseInt((CommonFunction.checkNull(headerCount1.get(6))).trim());
//					logger.info("vaue of my modified count::::::"+count);				
//				}
//			}
		    
		    bufInsSql=null;
		    
						
			for(int i=0;i<header.size();i++){
				logger.info("header: "+header.size());
				ArrayList header1=(ArrayList)header.get(i);
				if(header1!=null && header1.size()>0)
				{
					fetchVo = new CommonDealVo();
				
					fetchVo.setLbxDealNo((CommonFunction.checkNull(CommonFunction.checkNull(header1.get(0)))).trim());
					//fetchVo.setDealNo("<a href=\"#\" onclick=\"editFlowAnalysis.do?dealId="+ CommonFunction.checkNull(header1.get(0)).toString()+ "&dealNo="+CommonFunction.checkNull(header1.get(1)).toString()+"&customerName="+CommonFunction.checkNull(header1.get(2)).toString()+">"+ CommonFunction.checkNull(header1.get(1)).toString() + "</a>");
					fetchVo.setDealNo("<a href=\"#\" onclick=\"editFundFlowAnalysis('"+CommonFunction.checkNull(header1.get(0)).toString()+ "','"+CommonFunction.checkNull(header1.get(1)).toString()+"','"+CommonFunction.checkNull(header1.get(2)).toString().replaceAll("'","\\\\'")+"');\">"+ CommonFunction.checkNull(header1.get(1)).toString() + "</a>");
					
					fetchVo.setCustomername((CommonFunction.checkNull(header1.get(2))).trim());
					fetchVo.setProduct((CommonFunction.checkNull(header1.get(3))).trim());
					fetchVo.setScheme((CommonFunction.checkNull(header1.get(4))).trim());
					fetchVo.setReportingToUserId(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(header1.get(5))).trim());
					fetchVo.setTotalRecordSize(count);
					list.add(fetchVo);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
//		if(header.size()==0)
//		{
//			CommonDealVo fetchVo = new CommonDealVo();
//			fetchVo.setTotalRecordSize(count);
//			list.add(fetchVo);
//			request.setAttribute("flag","yes");
//			logger.info("Detail List when searchList is null: "+list.size());
//		}
		logger.info("Detail List when searchList is : "+list.size());
		
		return list;
	}
	
	
	public boolean fundFlowForward(String dealId) {

		logger.info("In fundFlowForward....... "+dealId);
		ArrayList qryList = null;
		qryList=new ArrayList();
	 	boolean status=false;
//		String query1="update cr_bank_analysis_dtl b,cr_sales_analysis_dtl s,cr_obligation_analysis_dtl o set b.REC_STATUS='A' " +
//				",s.REC_STATUS='A',o.REC_STATUS='A' " +
//				"WHERE b.DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+"" +
//					" or s.DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+"" +
//					" or o.DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim();
		String query="Select deal_id from cr_bank_analysis_dtl where deal_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim();
		boolean check=ConnectionDAO.checkStatus(query);
		logger.info("check forword:--------------"+check);
		if(check){
		StringBuilder query1=new StringBuilder();
//		 query1.append("update (select "+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+" as DealID) Dummy" +
//				" left join  cr_bank_analysis_dtl  on dummy.DealID  = cr_bank_analysis_dtl.DEAL_ID " +
//				"left join cr_sales_analysis_dtl on dummy.DealID  = cr_bank_analysis_dtl.DEAL_ID " +
//				"left join cr_obligation_analysis_dtl on dummy.DealID  = cr_obligation_analysis_dtl.DEAL_ID set cr_bank_analysis_dtl.REC_STATUS='A' ," +
//				"cr_sales_analysis_dtl.REC_STATUS='A',cr_obligation_analysis_dtl.REC_STATUS='A'");
//		logger.info("In query ::: "+query1.toString());
//        qryList.add(query1);
        
		  //change by sachin
        query1.append(" update cr_bank_analysis_dtl set REC_STATUS='A' where  DEAL_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+"'");
		logger.info("In query ::: "+query1.toString());
		qryList.add(query1);
		
		StringBuilder query2=new StringBuilder();

		query2.append(" update cr_sales_analysis_dtl set REC_STATUS='A' where  DEAL_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+"'");
		logger.info("In query ::: "+query2.toString());
		qryList.add(query2);
		
		StringBuilder query3=new StringBuilder();

		query3.append(" update cr_obligation_analysis_dtl set REC_STATUS='A' where  DEAL_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+"'");
		logger.info("In query ::: "+query3.toString());
		qryList.add(query3);
        //end by sachin
        
        
        
        query1=null;
        query2=null;
        query3=null;

        try {
			status = ConnectionDAO.sqlInsUpdDelete(qryList);
			logger.info("In stageForward......................status= "+status);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		}
		return status;
	}
	
	public boolean fundFlowAuthor(Object ob) {

		logger.info("In update fundFlowAuthor........ ");
		FundFlowAutherVo vo=(FundFlowAutherVo)ob;	
     	
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		boolean status = false;
		try
		{
			StringBuilder queryUpdate=new StringBuilder();
			
			queryUpdate.append("update cr_bank_analysis_dtl b , cr_sales_analysis_dtl s, cr_obligation_analysis_dtl o " );
			queryUpdate.append("set  b.REC_STATUS=?,s.REC_STATUS=?,o.REC_STATUS=?,b.REMARKS=?,s.REMARKS=?,o.REMARKS=?," );
			queryUpdate.append(" b.AUTHOR_ID=?,s.AUTHOR_ID=?,o.AUTHOR_ID=?,b.AUTHOR_DATE=");
			queryUpdate.append(dbo);
			queryUpdate.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			queryUpdate.append("s.AUTHOR_DATE=");
			queryUpdate.append(dbo);
			queryUpdate.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
			queryUpdate.append("o.AUTHOR_DATE=");
			queryUpdate.append(dbo);
			queryUpdate.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ");
			
			queryUpdate.append("WHERE b.DEAL_ID=? and s.DEAL_ID=? and o.DEAL_ID=?"); 
			logger.info("getComments *********************** "+vo.getDecision());
			if((CommonFunction.checkNull(vo.getDecision())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getDecision()).trim());
			if((CommonFunction.checkNull(vo.getDecision())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getDecision()).trim());
			if((CommonFunction.checkNull(vo.getDecision())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getDecision()).trim());
						
			logger.info("getComments *********************** "+vo.getComments());
			if((CommonFunction.checkNull(vo.getComments())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getComments()).trim());
			if((CommonFunction.checkNull(vo.getComments())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getComments()).trim());
			if((CommonFunction.checkNull(vo.getComments())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getComments()).trim());
			
			logger.info("getAuthorId*********************** "+vo.getAuthorId());
			if((CommonFunction.checkNull(vo.getAuthorId())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getAuthorId()).trim());
			if((CommonFunction.checkNull(vo.getAuthorId())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getAuthorId()).trim());
			if((CommonFunction.checkNull(vo.getAuthorId())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getAuthorId()).trim());
			
			logger.info("getAuthorDate*********************** "+vo.getAuthorDate());
			if((CommonFunction.checkNull(vo.getAuthorDate())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getAuthorDate()).trim());
			if((CommonFunction.checkNull(vo.getAuthorDate())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getAuthorDate()).trim());
			if((CommonFunction.checkNull(vo.getAuthorDate())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getAuthorDate()).trim());
			
			logger.info("deal id *********************** "+vo.getDealId());
			if((CommonFunction.checkNull(vo.getDealId())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getDealId()).trim());
			if((CommonFunction.checkNull(vo.getDealId())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getDealId()).trim());
			if((CommonFunction.checkNull(vo.getDealId())).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getDealId()).trim());
			
			insertPrepStmtObject.setSql(queryUpdate.toString());
			logger.info("IN fundFlowAuthor() update query ### "+insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			logger.info("In fundFlowAuthor ........ update query: "+queryUpdate.toString());
			
				status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In fundFlowAuthor.........update status: "+status);
			queryUpdate=null;
			
		}catch(Exception e){
			e.printStackTrace();
		}

		return status;
		}
	
//Abhishek Start
			@Override
			public String InsertBankAccountDtl(
					ArrayList<FundFlowDownloadUploadVo> list) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public ArrayList getAccountStatmentData() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getBalanceAsOnDateData(String caseId,
					String balanceAsOnDate, String custRef, String customerId,
					String documentId) {
				// TODO Auto-generated method stub
				return null;
			}
//Abhishek End

			@Override
			public ArrayList getBankAnalysisDetails(String bankAcctAnsId,
					String dealId, String recStatus, String bptype) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public ArrayList getBankStatementDtl(String dealId) {
				// TODO Auto-generated method stub
				return null;
			}

			//added by Pranaya
			@Override
			public ArrayList getBankStatementData(String deal_no) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String InsertUpdateFundFlowBankDtl(
					FundFlowDownloadUploadVo vo) {
				// TODO Auto-generated method stub
				return null;
			}
			//end by Pranaya
}
