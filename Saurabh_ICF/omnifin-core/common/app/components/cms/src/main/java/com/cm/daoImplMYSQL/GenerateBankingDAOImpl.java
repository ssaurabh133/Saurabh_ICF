package com.cm.daoImplMYSQL;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.ConnectionReportDumpsDAO;
import com.cm.dao.GenerateBankingDAO;
import com.cm.vo.GenerateReportVO;

public class GenerateBankingDAOImpl implements GenerateBankingDAO {
	private static final Logger logger = Logger.getLogger(GenerateBankingDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.utill");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	public ArrayList generateReportDao(GenerateReportVO generateReportVO) {
		ArrayList generateReportData = new ArrayList();
		ArrayList mainlist = new ArrayList();
		StringBuilder query = new StringBuilder();
		StringBuilder curTime = new StringBuilder();
		StringBuilder currentTime = new StringBuilder();
		
		logger.info("In generateReportDao  " );
		logger.info(" getGenerateBankingDate  " +generateReportVO.getGenerateBankingDate());
		
		try {
			
			/*Abhishek Start For Update PDC Instrument*/
			 	 ArrayList<Object> in =new ArrayList<Object>();
	    		 ArrayList<Object> out =new ArrayList<Object>();
	    		 ArrayList outMessages = new ArrayList();
	    		 StringBuilder s1= new StringBuilder();
	    		 StringBuilder s2= new StringBuilder();
				 in.add(CommonFunction.checkNull(generateReportVO.getGenerateBankingDate()));
	    		 out.add(s1.toString());
	    		 out.add(s2.toString());
	    		 outMessages=(ArrayList) ConnectionDAO.callSP("UPDATE_PDC_INSTUMENT",in,out);
	     		 s1.append(CommonFunction.checkNull(outMessages.get(0)));
	 		     s2.append(CommonFunction.checkNull(outMessages.get(1)));
	 	   		 logger.info("s1: "+s1);
	 	   		 logger.info("s2: "+s2);		   		
	 	   		 logger.info("After UPDATE_PDC_INSTUMENT call....");   	
	 	   	/*Abhishek End*/	 
			
			ArrayList subList = new ArrayList();
			                      
//			String query = "SELECT PDC_INSTRUMENT_ID,ECS_TRANSACTION_CODE,PDC_ISSUING_MICR_CODE,ECUSTOMER_ACCOUNT_TYPE,PDC_ISSUEING_BANK_ACCOUNT," +
//					"g.CUSTOMER_NAME,UTILITY_NUMBER,l.LOAN_NO,PDC_INSTRUMENT_AMOUNT,PDC_INSTRUMENT_MODE,PDC_CLEARING_TYPE,SPONSOR_BANKBRANCH_CODE," +
//					"PDC_INSTRUMENT_NO, PDC_REMARKS, PDC_STATUS,b.bank_name , br.BRANCH_DESC, PDC_ISSUING_IFSC_CODE,DATE_FORMAT(PDC_INSTRUMENT_DATE," +
//					"'"+dateFormat+"') ,PDC_BIN_NO,PDC_LOCATION,MAKER_REMARKS,DATE_FORMAT(re.INSTL_DATE,'"+dateFormat+"'),l.LOAN_TENURE,PDC_INSTL_NO" +
//					 " FROM CR_PDC_INSTRUMENT_DTL left join gcd_customer_m g on PDC_BPID=g.CUSTOMER_ID left join cr_loan_dtl l on PDC_LOAN_ID=l.LOAN_ID" +
//					 " left JOIN com_bank_m b on PDC_ISSUEING_BANK_ID=b.BANK_ID left join com_branch_m br on PDC_ISSUEING_BRANCH_ID=br.BRANCH_ID left join cr_repaysch_dtl re on PDC_LOAN_ID=re.LOAN_ID and PDC_INSTL_NO=re.INSTL_NO " +
//                      "  WHERE PDC_STATUS = 'A'" +
//                     " AND PDC_INSTRUMENT_DATE = STR_TO_DATE('"+generateReportVO.getGenerateBankingDate()+"','"+dateFormat+"') " +
//                   "  ORDER BY PDC_INSTRUMENT_MODE, PDC_CLEARING_TYPE, PDC_INSTRUMENT_ID ";
			
			query.append("SELECT PDC_INSTRUMENT_ID,ECS_TRANSACTION_CODE,PDC_ISSUING_MICR_CODE,ECUSTOMER_ACCOUNT_TYPE,PDC_ISSUEING_BANK_ACCOUNT," +
					" g.CUSTOMER_NAME,ifnull((select description from generic_master where generic_key = 'UTILITY_NUMBER' and rec_status ='A' and value=UTILITY_NUMBER ),'')UTILITY_NUMBER," +
					" l.LOAN_NO, PDC_INSTRUMENT_AMOUNT,"
					+ " CASE PDC_INSTRUMENT_MODE WHEN 'Q' THEN 'PDC' WHEN 'E' THEN 'ECS' WHEN 'H' THEN 'ACH' WHEN 'DIR' THEN 'DIRECT DEBIT' END AS INSTRUMENT_MODE,(select description from generic_master where generic_key = 'CLEARING_TYPE' and rec_status ='A' and value=PDC_CLEARING_TYPE )PDC_CLEARING_TYPE,SPONSOR_BANKBRANCH_CODE," +
					" PDC_INSTRUMENT_NO, PDC_REMARKS, PDC_STATUS,b.bank_name ,br.bank_BRANCH_name,PDC_ISSUING_IFSC_CODE,DATE_FORMAT(PDC_INSTRUMENT_DATE,'"+dateFormat+"'), " +
					" PDC_BIN_NO,PDC_LOCATION,MAKER_REMARKS,DATE_FORMAT(re.INSTL_DATE,'"+dateFormat+"')," +
					" l.LOAN_TENURE,PDC_INSTL_NO,PDC_ISSUEING_BRANCH_ID,NAME_OF_PDC_SUBMIT_BY,PDC_PURPOSE,if(t.bank_id is null,0,1),t.bank_id  " +
					" FROM CR_PDC_INSTRUMENT_DTL left join gcd_customer_m g on PDC_BPID=g.CUSTOMER_ID " +
					" left join cr_loan_dtl l on PDC_LOAN_ID=l.LOAN_ID and l.REC_STATUS='A' " +
					" left join TEMP_GENERATE_BATCH t on t.loan_no=l.loan_no and t.UPLOADED_FLAG='Y' " +
					" left JOIN com_bank_m b on PDC_ISSUEING_BANK_ID=b.BANK_ID " +
					" left join com_bankbranch_m br on PDC_ISSUEING_BRANCH_ID=br.bank_BRANCH_ID " +
					" join cr_repaysch_dtl re on pdc_loan_id = re.LOAN_ID and PDC_INSTL_NO=re.INSTL_NO " +
					"  AND re.REC_STATUS = 'A' AND re.INSTL_DATE = STR_TO_DATE('"+generateReportVO.getGenerateBankingDate()+"','"+dateFormat+"') " +
					" WHERE PDC_STATUS = 'A' and pdc_instrument_date <= STR_TO_DATE('"+generateReportVO.getGenerateBankingDate()+"','"+dateFormat+"') " +
					" and if(PDC_PURPOSE='PRE EMI',true,l.DISBURSAL_STATUS='F')" +
					" ORDER BY t.bank_id,PDC_INSTRUMENT_MODE, PDC_CLEARING_TYPE, PDC_INSTRUMENT_ID ");
			
			logger.info("In generateReportDao query is : " + query.toString());
			mainlist = ConnectionReportDumpsDAO.sqlSelect(query.toString());
			//searchlist = ConnectionReportDumpsDAO.sqlSelect(bufInsSql.toString());
			logger.info("In generateReportDao mainlist size: "+ mainlist.size());
			
			//logger.info("In generateReportDao mainlist size: "+ mainlist.toString());
			curTime.append("select curtime()");
			currentTime.append(ConnectionReportDumpsDAO.singleReturn(curTime.toString()));
			logger.info("currentTime : " + currentTime);
			generateReportVO.setCurTime(currentTime.toString());
			/*if(mainlist.size()>0)
			{
			for (int i = 0; i < mainlist.size(); i++) {

				subList = (ArrayList) mainlist.get(i);
				logger.info("In selectWaiveOffData......sublist size: "
						+ subList.size());
				if (subList.size() > 0) {
					GenerateReportVO vo = new GenerateReportVO();
				

					vo.setBusinessPartnerDesc((String) subList.get(1));
					vo.setInstrumentMode((String) subList.get(2));
					vo.setInstrumentType((String) subList.get(4));
					
								
					generateReportData.add(vo);
				}
			}
			}*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			query = null;
			curTime = null;
			currentTime = null;
		}

		return mainlist;

	}
}
