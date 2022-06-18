package com.cp.daoImplMYSQL;

import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.cm.actionform.ReportsForm;
import com.cm.dao.DownloadDAO;
import com.communication.engn.daoImplMySql.SmsDAOImpl;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.connect.PrepStmtObject;
import com.cp.dao.DealSanctionLetterDAO;
import com.cp.vo.DealSanctionLetterVo;
import com.cp.vo.UnderwritingDocUploadVo;

import java.util.*;


public class DealSanctionLetterDAOImpl implements DealSanctionLetterDAO
{
	 private static final Logger logger = Logger.getLogger(DealSanctionLetterDAOImpl.class.getName());
	 ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	  String dateFormatWithTime = this.resource.getString("lbl.dateWithTimeInDao");
	  String dateFormat = this.resource.getString("lbl.dateInDao");
	  
	public boolean saveLoanSanctionLetter(DealSanctionLetterVo vo) {
		boolean status = false;
		ArrayList qryList = new ArrayList();
		ArrayList searchlist = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		try {
			String table = "CR_LOANSANCTION_LETTER_DTL";
			String customerId = ConnectionDAO.singleReturn("select GCD_CUSTOMER_ID from CR_DEAL_DTL where deal_id = '"+vo.getDealId()+"' limit 1 ");
			/*	String totalLoanAmtQuery="SELECT IFNULL(ROUND(CDLD.DEAL_LOAN_AMOUNT_NEW,2),0.00) AS DEAL_SANCTION_AMOUNT FROM CR_DEAL_DTL CDD LEFT JOIN CR_DEAL_LOAN_DTL CDLD ON CDLD.DEAL_ID=CDD.DEAL_ID where cdd.deal_id='"+vo.getDealId()+"' ";
				String totalLoanAmt=ConnectionDAO.singleReturn(totalLoanAmtQuery);*/
				//vo.setDealId(dealId);
			StringBuffer bufInsSql = new StringBuffer();
			qryList=new ArrayList();
			insertPrepStmtObject = new PrepStmtObject();
			bufInsSql.append("insert into "+table+" (DEAL_ID,CUSTOMER_ID,Insurance_Premium,ROI,Processing_Fees,Margin_Money_Amount,Margin_Money_Remarks,"
					+ " MAKER_ID,MAKER_DATE)");
			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?," );//DEAL_ID
			//bufInsSql.append(" ?," );//LOAN_ID
			bufInsSql.append(" ?," );//CUSTOMER_ID
			bufInsSql.append(" ?," );//Insurance_Premium
			bufInsSql.append(" ?," );//ROI
			bufInsSql.append(" ?," );//Processing_Fees
			bufInsSql.append(" ?," );//Margin_Money_Amount
			bufInsSql.append(" ?," );//Margin_Money_Remarks
			bufInsSql.append(" ?," );//MAKER_ID
			bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) " );//MAKER_DATE
			bufInsSql.append("  )" );
			
			insertPrepStmtObject = new PrepStmtObject();

	/*		if(CommonFunction.checkNull(dealId).trim().equalsIgnoreCase(""))  // deal_id
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((dealId).trim());  // deal_id
*/			if ((CommonFunction.checkNull(vo.getDealId())).trim().equalsIgnoreCase("")) // DEAL_ID
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getDealId()).trim()); // loan_id	
	        if(CommonFunction.checkNull(customerId).trim().equalsIgnoreCase(""))  // customerId
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((customerId).trim());  // customerId
			if ((CommonFunction.checkNull(vo.getInsurancePremium())).trim().equalsIgnoreCase("")) // Insurance_Premium
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getInsurancePremium()).trim()); // Insurance_Premium
			if ((CommonFunction.checkNull(vo.getRois())).trim().equalsIgnoreCase("")) // ROI
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getRois()).trim()); // ROI
			if ((CommonFunction.checkNull(vo.getProcessingFee())).trim().equalsIgnoreCase("")) // Processing_Fees
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getProcessingFee()).trim()); // Processing_Fees
			if ((CommonFunction.checkNull(vo.getMarginMoneyAmount())).trim().equalsIgnoreCase("")) // Margin_Money_Amount
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getMarginMoneyAmount()).trim()); // Margin_Money_Amount
			if ((CommonFunction.checkNull(vo.getMarginMoneyRemarks())).trim().equalsIgnoreCase("")) // Margin_Money_Remarks
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getMarginMoneyRemarks()).trim()); // Margin_Money_Remarks
			if ((CommonFunction.checkNull(vo.getUserId())).trim().equalsIgnoreCase("")) // MAKER_ID
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getUserId()).trim()); // MAKER_ID
			if ((CommonFunction.checkNull(vo.getMakerDate())).trim().equalsIgnoreCase("")) // MAKER_DATE
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getMakerDate()).trim()); // MAKER_DATE
		
			insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("IN saveLoanSanctionLetter() insert query1 ### " + insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("In saveLoanSanctionLetter......................" + status);
			

if(status){
	
	String totalLoanAmtQuery="SELECT sum(IFNULL(ROUND(CDLD.deal_sanction_amount,4),0.00)+(IFNULL(ROUND(clld.Insurance_Premium,4),0.00))) AS DEAL_SANCTION_AMOUNT FROM  cr_deal_loan_dtl CDLD left join CR_LOANSANCTION_LETTER_DTL clld on (CDLD.deal_id=clld.deal_id) where CDLD.deal_id='"+vo.getDealId()+"' limit 1 ";
	String totalLoanAmt=ConnectionDAO.singleReturn(totalLoanAmtQuery);
	
	//String monthlyRoiQuery="select ROUND(IFNULL(roi,0.00)/(12*100),4) as roi from CR_LOANSANCTION_LETTER_DTL where  deal_id='"+vo.getDealId()+"' limit 1 ";
	String monthlyRoiQuery="select ROUND(IFNULL(roi,0.00)/(12*100),6) as roi from CR_LOANSANCTION_LETTER_DTL where  deal_id='"+vo.getDealId()+"' limit 1 ";
	//String monthlyRoiQuery="select ROUND(IFNULL(roi,0.00),2) as roi from CR_LOANSANCTION_LETTER_DTL where  deal_id='"+vo.getDealId()+"' limit 1 ";
	String monthlyRoi=ConnectionDAO.singleReturn(monthlyRoiQuery);
	
	String noOfInstallmentQuery="select round(IFNULL(DEAL_NO_OF_INSTALLMENT,0),4) as DEAL_NO_OF_INSTALLMENT from cr_deal_loan_dtl where  deal_id='"+vo.getDealId()+"' limit 1 ";
	String noOfInstallment=ConnectionDAO.singleReturn(noOfInstallmentQuery);
	
	
	String loanAmtQuery="SELECT IFNULL(ROUND(deal_sanction_amount,2),0.00) AS DEAL_LOAN_AMOUNT  from cr_deal_loan_dtl where  deal_id='"+vo.getDealId()+"' limit 1 ";
	String loanAmt=ConnectionDAO.singleReturn(loanAmtQuery);
	
	
	
	StringBuffer bufInsUpdSql = new StringBuffer();
	ArrayList qryLists = new ArrayList();
	boolean status1 = false;
		      bufInsUpdSql.append(" UPDATE CR_LOANSANCTION_LETTER_DTL");
		      bufInsUpdSql.append(" SET total_loan_amount =?,monthly_roi =?,Loan_Amt=?,No_Of_Installment=? ");

		      bufInsUpdSql.append(" WHERE deal_id=?");
		      PrepStmtObject insertPrepStmtObjects = new PrepStmtObject();

		      if (CommonFunction.checkNull(totalLoanAmt).trim().equalsIgnoreCase(""))
		    	  insertPrepStmtObjects.addNull();
		      else {
		    	  insertPrepStmtObjects.addString(totalLoanAmt.trim());
		      }
		      if (CommonFunction.checkNull(monthlyRoi).trim().equalsIgnoreCase(""))
		    	  insertPrepStmtObjects.addNull();
		      else {
		    	  insertPrepStmtObjects.addString(monthlyRoi.trim());
		      }
		      if (CommonFunction.checkNull(loanAmt).trim().equalsIgnoreCase(""))
		    	  insertPrepStmtObjects.addNull();
		      else {
		    	  insertPrepStmtObjects.addString(loanAmt.trim());
		      }
		      if (CommonFunction.checkNull(noOfInstallment).trim().equalsIgnoreCase(""))
		    	  insertPrepStmtObjects.addNull();
		      else {
		    	  insertPrepStmtObjects.addString(noOfInstallment.trim());
		      }
		      if ((CommonFunction.checkNull(vo.getDealId())).trim().equalsIgnoreCase("")) 
		    	  insertPrepStmtObjects.addNull();
				else
					insertPrepStmtObjects.addString((vo.getDealId()).trim());
		      insertPrepStmtObjects.setSql(bufInsUpdSql.toString());
		      logger.info(new StringBuilder().append("IN totalLoanAmt,monthlyRoi,noOfInstallment  UPDATE query1 ### ").append(insertPrepStmtObjects.printQuery()).toString());
		      qryLists.add(insertPrepStmtObjects);

		      status1 = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryLists);
		      logger.info(new StringBuilder().append("In totalLoanAmt,monthlyRoi,noOfInstallment......................").append(status1).toString());
		      
		      if(status1)
		      {
		    	  /* 
		    	  	String newemisTotalLoanAmtQuery="SELECT sum(IFNULL(ROUND(CDLD.deal_sanction_amount,4),0.00)+(IFNULL(ROUND(clld.Insurance_Premium,4),0.00))) AS DEAL_SANCTION_AMOUNT FROM  cr_deal_loan_dtl CDLD left join CR_LOANSANCTION_LETTER_DTL clld on (CDLD.deal_id=clld.deal_id) where CDLD.deal_id='"+vo.getDealId()+"' limit 1 ";
		    		String totalLoanAmt11=ConnectionDAO.singleReturn(newemisTotalLoanAmtQuery);
		    		
		    		String newemisNoOfInstallmentQuery="select round(IFNULL(DEAL_NO_OF_INSTALLMENT,0),4) as DEAL_NO_OF_INSTALLMENT from cr_deal_loan_dtl where  deal_id='"+vo.getDealId()+"' limit 1 ";
		    		String noOfInstallment11=ConnectionDAO.singleReturn(newemisNoOfInstallmentQuery);
		    		
		    		String newemisLoanRateTypeQuery="SELECT IFNULL(DEAL_RATE_TYPE,'') AS DEAL_RATE_TYPE  from cr_deal_loan_dtl where  deal_id='"+vo.getDealId()+"' limit 1 ";
		    		String loanRateType11=ConnectionDAO.singleReturn(newemisLoanRateTypeQuery);
		    	  
		    		String loanFinalRate11= (vo.getRois()).trim();
		    	  
		    		
		    		
		    		String newemisDealInstallmentModeQuery="SELECT IFNULL(DEAL_INSTALLMENT_MODE,'') AS DEAL_INSTALLMENT_MODE  from cr_deal_loan_dtl where  deal_id='"+vo.getDealId()+"' limit 1 ";
		    		String dealInstallmentMode11=ConnectionDAO.singleReturn(newemisDealInstallmentModeQuery);
		    		
		    		
		    		
		    		
		    		String newemisIntCompFreqQuery="SELECT IFNULL(INT_COMP_FREQ,'0.00') AS INT_COMP_FREQ  from cr_deal_loan_dtl where  deal_id='"+vo.getDealId()+"' limit 1 ";
		    		String intCompFreq11=ConnectionDAO.singleReturn(newemisIntCompFreqQuery);
		    		
		    		String newemisDealRepaymentFreqQuery="SELECT IFNULL(DEAL_REPAYMENT_FREQ,'') AS DEAL_REPAYMENT_FREQ  from cr_deal_loan_dtl where  deal_id='"+vo.getDealId()+"' limit 1 ";
		    		String dealRepaymentFreq11=ConnectionDAO.singleReturn(newemisDealRepaymentFreqQuery);
		    		
		    		
		    	 // double monthlyRois = Double.valueOf(monthlyRoi);	
		    	  double noOfInstallments1 = Double.valueOf(noOfInstallment11);
		    	  double totalLoanAmts1 = Double.valueOf(totalLoanAmt11);
		    	  double loanFinalRate1 = Double.valueOf(loanFinalRate11);
		     	 logger.info("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
		     	 logger.info("noOfInstallments1  >>>>>>>>>>>>  "+noOfInstallments1);
		     	 logger.info("totalLoanAmts1  >>>>>>>>>>>>  "+totalLoanAmts1);
		     	 logger.info("loanFinalRate1  >>>>>>>>>>>>  "+loanFinalRate1);
		     	 logger.info("intCompFreq11  >>>>>>>>>>>>  "+intCompFreq11);
		     	 logger.info("dealRepaymentFreq11  >>>>>>>>>>>>  "+dealRepaymentFreq11);
		     	 logger.info("loanRateType11  >>>>>>>>>>>>  "+loanRateType11);
		     	 logger.info("dealInstallmentMode11  >>>>>>>>>>>>  "+dealInstallmentMode11);
		    	
		    	  double rvAmount1= 0;
		    	  double iSeqNo1 = 0;
		    	  double recoveryPercent1 = 100;
		    	  double instlRounding1=0;
		    	  String instlRoundType1="R";
		    	  double freqMonth1;
		    	  if(dealRepaymentFreq11.equals("M") || dealRepaymentFreq11.equals("D"))
		    	  {freqMonth1=1;}
		    	  else if(dealRepaymentFreq11.equals("B"))
		    	  {freqMonth1=2;}
		    	  else if(dealRepaymentFreq11.equals("Q"))
		    	  {freqMonth1=3;}
		    	  else if(dealRepaymentFreq11.equals("H"))
		    	  {freqMonth1=6;}
		    	  else if(dealRepaymentFreq11.equals("A") || dealRepaymentFreq11.equals("Y"))
		    	  {freqMonth1=12;}
		    	  else if(dealRepaymentFreq11.equals("W"))
		    	  {freqMonth1=7;}
		    	  else if(dealRepaymentFreq11.equals("F"))
		    	  {freqMonth1=15;}
		    	  else
		    	  {freqMonth1=0;} 
		    	  logger.info("rvAmount1  >>>>>>>>>>>>  "+rvAmount1);
		    	  logger.info("iSeqNo1  >>>>>>>>>>>>  "+iSeqNo1);
		    	  logger.info("recoveryPercent1  >>>>>>>>>>>>  "+recoveryPercent1);
		    	  logger.info("instlRounding1  >>>>>>>>>>>>  "+instlRounding1);
		    	  logger.info("instlRoundType1  >>>>>>>>>>>>  "+instlRoundType1);
		    	  logger.info("freqMonth1  >>>>>>>>>>>>  "+freqMonth1);

		    	  
		    	  String newemisQuery= "SELECT GET_INSTALLMENT_AMT("+totalLoanAmts1+", "+recoveryPercent1+", "+noOfInstallments1+", "+freqMonth1+", "+noOfInstallments1+","+noOfInstallments1+",'"+loanRateType11+"',"+loanFinalRate1+","+rvAmount1+",'"+dealInstallmentMode11+"','"+instlRoundType1+"',"+instlRounding1+","+iSeqNo1+",'"+intCompFreq11+"','"+dealRepaymentFreq11+"')";
		    	  logger.info("newemisQuery  >>>>>>>>>>>>  "+newemisQuery);
		    	  String newemis=ConnectionDAO.singleReturn(newemisQuery); 
		    	  
		    	  logger.info("newemiss  >>>>>>>>>>>>  "+newemis);
		    	  logger.info("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
		    	  logger.info("monthlyRois  >>>>>>>>>>>>  "+monthlyRois);
		    	  logger.info("noOfInstallments  >>>>>>>>>>>>  "+noOfInstallments);
		    	  logger.info("totalLoanAmts  >>>>>>>>>>>>  "+totalLoanAmts);
double newemi= (totalLoanAmts*monthlyRois*Math.pow(1+monthlyRois,noOfInstallments))/(Math.pow(1+monthlyRois,noOfInstallments)-1);
double newemiss=Math.ceil(newemi);
double newemissround=Math.round(newemi);
//double newemissmin=Math.min(newemi);
double newemissfloor=Math.floor(newemi);
double newemissabs=Math.abs(newemi);
logger.info("newemiss  >>>>>>>>>>>>  "+newemiss);
logger.info("newemissround  >>>>>>>>>>>>  "+newemissround);
//logger.info("newemissmin  >>>>>>>>>>>>  "+newemissmin);
logger.info("newemissfloor  >>>>>>>>>>>>  "+newemissfloor);
logger.info("newemissabs  >>>>>>>>>>>>  "+newemissabs);
logger.info("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
*/
/*String newemis=Double.toString(newemiss);
logger.info("newemiss  >>>>>>>>>>>>  "+newemiss);
logger.info("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
		    		StringBuffer bufInsUpdSql2 = new StringBuffer();
		    		ArrayList qryLists2 = new ArrayList();
		    		boolean status2 = false;
		    		
		    		bufInsUpdSql2.append(" UPDATE CR_LOANSANCTION_LETTER_DTL");
		    		bufInsUpdSql2.append(" SET EMI =? ");

		    		bufInsUpdSql2.append(" WHERE deal_id=?");
				      PrepStmtObject insertPrepStmtObjects2 = new PrepStmtObject();

				      if (CommonFunction.checkNull(newemis).trim().equalsIgnoreCase(""))
				    	  insertPrepStmtObjects2.addNull();
				      else {
				    	  insertPrepStmtObjects2.addString(newemis.trim());
				      }
				      
				      if ((CommonFunction.checkNull(vo.getDealId())).trim().equalsIgnoreCase("")) 
				    	  insertPrepStmtObjects2.addNull();
						else
							insertPrepStmtObjects2.addString((vo.getDealId()).trim());
				      insertPrepStmtObjects2.setSql(bufInsUpdSql2.toString());
				      logger.info(new StringBuilder().append("IN  EMI UPDATE query2 ### ").append(insertPrepStmtObjects2.printQuery()).toString());
				      qryLists2.add(insertPrepStmtObjects2);

				      status2 = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryLists2);
				      logger.info(new StringBuilder().append("In EMI UPDATE......................").append(status2).toString());
				     */
		    	  
		    	  //Shashank Agnihotri Code Starts
		    	 try {
		    		 String txn_id = vo.getDealId().trim();
		    		
		    			 
						ArrayList<Object> in =new ArrayList<Object>();
						ArrayList<Object> out =new ArrayList<Object>();
						ArrayList outMessages = new ArrayList();
						String s1="";
						String s2="";
					
							in.add(txn_id);
							out.add(s1);
							out.add(s2);
							logger.info("GENERATE_INSTL_AMOUNT ("+in.toString()+","+out.toString()+")");
							outMessages=(ArrayList) ConnectionDAO.callSP("GENERATE_INSTL_AMOUNT",in,out);
						
						
						s1=CommonFunction.checkNull(outMessages.get(0));
						s2=CommonFunction.checkNull(outMessages.get(1));
					    logger.info("s1  : "+s1);
					    logger.info("s2  : "+s2);	
					    if(s1.equalsIgnoreCase("S"))
						{
							logger.info("Procedure Error Message----"+s2);
						}
						else
						{
							logger.info("Procedure Error Message----"+s2);
						}		
					     in.clear();
					     in=null;
					     out.clear();
					     out=null;
					} catch (Exception e)
					{e.printStackTrace();}
				
		    	 //Shashank Agnihotri Code Ends
		    		
		      }
}
			
			return status;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			qryList = null;
			insertPrepStmtObject = null;
		}
		return false;
	}
	
	
	  public ArrayList getUploadUnderwritingDataForSanctionLetter(String dealId)
	  {
	    ArrayList list = new ArrayList();
	    ArrayList data = null;
	    try {
	      StringBuilder query = new StringBuilder(new StringBuilder().append(" SELECT A.TXN_ID, A.FILE_NAME, ifnull(DOCUMENT_DESC,'Sanction letter'), B.USER_NAME,S.STAGE_DESC  , DMS_DOC_URL, A.DOCUMENT_ID,A.DMS_DOC_NUMBER,DATE_FORMAT(A.UPLOADED_DATE,'").append(this.dateFormatWithTime).append("')UPLOADED_DATE,A.TXN_TYPE,1 CAT ").append(" FROM CR_UPLOADED_DOCUMENTS A ").append(" JOIN SEC_USER_M B ON A.UPLOADED_BY=B.USER_ID  ").append(" LEFT JOIN CR_STAGE_M S ON S.STAGE_ID=A.TXN_TYPE ").append(" where txn_id='").append(dealId).append("' and TXN_TYPE='LIA' ").toString());

	      logger.info(new StringBuilder().append("In getUploadUnderwritingDataForSanctionLetter...............query...........DAOImpl").append(query).toString());
	      UnderwritingDocUploadVo uwDocVo = null;
	      ArrayList product = ConnectionDAO.sqlSelect(query.toString());
	    
	      query = null;
	      for (int i = 0; i < product.size(); i++)
	      {
	        data = (ArrayList)product.get(i);
	        if (data.size() > 0) {
	          uwDocVo = new UnderwritingDocUploadVo();
	          uwDocVo.setTxnId(CommonFunction.checkNull(data.get(0)).trim());
	          uwDocVo.setFileName(CommonFunction.checkNull(data.get(1)).trim());
	          uwDocVo.setDocDescription(CommonFunction.checkNull(data.get(2)).trim());
	          uwDocVo.setUserName(CommonFunction.checkNull(data.get(3)).trim());
	          uwDocVo.setUploadedState(CommonFunction.checkNull(data.get(4)).trim());
	          uwDocVo.setDmsDocUrl(CommonFunction.checkNull(data.get(5)).trim());
	          uwDocVo.setLbxDocId(CommonFunction.checkNull(data.get(6)).trim());
	          uwDocVo.setDmsDocNumber(CommonFunction.checkNull(data.get(7)).trim());
	          uwDocVo.setUploadedDate(CommonFunction.checkNull(data.get(8)).trim());
	          uwDocVo.setTxnType(CommonFunction.checkNull(data.get(9)).trim());
	          
	          list.add(uwDocVo);
	          uwDocVo = null;
	        }
	      }
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	    finally {
	      data = null;
	    }

	    return list;
	  }
	
}
