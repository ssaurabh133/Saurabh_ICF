/**
 * 
 */
package com.cm.daoImplMYSQL;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.cm.dao.InterestWorkingDAO;
import com.cm.vo.InterestWorkingVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.ConnectionReportDumpsDAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author pranaya.gajpure
 *
 */
public class InterestWorkingDAOImpl implements InterestWorkingDAO{
	
	private static final Logger logger = Logger.getLogger(InterestWorkingDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.utill");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	
	public ArrayList generateInterestWorkingDao(InterestWorkingVO ivo) {
		ArrayList dataList = new ArrayList();
		ArrayList result= new ArrayList();
		String loan_id = ivo.getLoanNo();
		StringBuilder query = new StringBuilder();
		
		logger.info("Interest From Working Date------------"+ivo.getInterestWorkingFromDate());
		logger.info("Interest To Working Date------------"+ivo.getInterestWorkingToDate());
		try{
			 //  query.append(" SELECT B.LOAN_ID AS LOAN_NUMBER, CUSTOMER_NAME, "
					  query.append(" SELECT B.LOAN_ID AS LOAN_NUMBER,B.SHEET_NAME,CUSTOMER_NAME,  "
					+ " ROUND(OPENING_BALANCE,0) AS OPENINGBALANCE, DATE_FORMAT(CURR_DATE,'%d-%b-%y') AS TRANSACTION_DATE, "
					+ " ROUND(DISBURSALS,0), ROUND(PREPAYMENT,0), ROUND(RECEIPTS,0),"
					+ " CONCAT(ROUND(B.EFF_RATE,2),'%'), ROUND(B.INTEREST,0) AS INTERESTAMOUNT,ROUND(CLOSING_BALANCE,0), A.DAYS, "
					+ "concat(ROUND(IFNULL(CTD.DEAL_CHARGE_CALCULATED_AMOUNT,0),0),' ','%') AS DEAL_CHARGE_FINAL_AMOUNT, SEQ_NO, "
					+ " ROUND(C.INTEREST,0), ROUND((OPENING_BALANCE * CTD.DEAL_CHARGE_FINAL_AMOUNT),0) AS PENAL_INT "
					+ " FROM SEPARATE_INTEREST_REPORT_TEMP B"
					+ " LEFT JOIN (SELECT LOAN_ID, SUM(DAYS) AS DAYS FROM SEPARATE_INTEREST_REPORT_TEMP GROUP BY LOAN_ID) AS A ON A.LOAN_ID = B.LOAN_ID "
					+ " LEFT JOIN (SELECT LOAN_ID,SUM(INTEREST)AS INTEREST  FROM SEPARATE_INTEREST_REPORT_TEMP GROUP BY LOAN_ID)C ON C.LOAN_ID=B.LOAN_ID "
					+ " LEFT JOIN CR_TXNCHARGES_DTL CTD ON CTD.TXN_ID = B.LOAN_ID AND DEAL_CHARGE_CODE = 109 AND TXN_TYPE = 'LIM' "
					+ " ORDER BY B.LOAN_ID, SEQ_NO; "
					+ " ");
			logger.info("Interest Report query is:--------------"+query.toString());
			dataList = ConnectionReportDumpsDAO.sqlSelect(query.toString());
			logger.info("Interest Report data list size----------------"+dataList.size());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			query = null;
		}
		return dataList;
	}
	/*public int generateInterestWorkingCount(InterestWorkingVO ivo) {
		int count = 0;
		StringBuilder query = new StringBuilder();
		
		logger.info("Interest Working Date------------"+ivo.getInterestWorkingDate());
		try{
			query.append(" SELECT COUNT(DISTINCT B.LOAN_ID) AS LOAN_NUMBER "
					+ " FROM SEPARATE_INTEREST_REPORT_TEMP B"
					+ " LEFT JOIN (SELECT LOAN_ID, SUM(DAYS) AS DAYS FROM SEPARATE_INTEREST_REPORT_TEMP GROUP BY LOAN_ID) AS A ON A.LOAN_ID = B.LOAN_ID "
					+ " LEFT JOIN CR_TXNCHARGES_DTL CTD ON CTD.TXN_ID = B.LOAN_ID AND DEAL_CHARGE_CODE = 109 AND TXN_TYPE = 'LIM' "
					+ " ORDER BY B.LOAN_ID, SEQ_NO; "
					+ " ");
			logger.info("InterestCount query is:--------------"+query.toString());
			String value = ConnectionReportDumpsDAO.singleReturn(query.toString());
			count = Integer.parseInt(value);
			logger.info("Count value is----------------"+count);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			query = null;
		}
		return count;
	}*/
	
	public ArrayList getInterestCustomerList(InterestWorkingVO ivo) {
		ArrayList dataList = new ArrayList();
		StringBuilder query = new StringBuilder();
		
		logger.info("Interest Working From Date------------"+ivo.getInterestWorkingFromDate());
		logger.info("Interest Working To Date------------"+ivo.getInterestWorkingToDate());
		try{
			query.append(" SELECT DISTINCT B.LOAN_ID AS LOAN_NUMBER , CUSTOMER_NAME "
					/*+ " ,case when INT_FREQ = 'M' then 'Monthly' "
					+ " when INT_FREQ = 'Q' THEN 'Quarterly' "
					+ " when INT_FREQ = 'H' then 'Half Yearly' "
					+ " when INT_FREQ = 'Y' then 'Yearly' end as Int_Frequency "
					+ " ,CONCAT(ROUND(B.EFF_RATE,2),'%') AS ROI, ROUND(C.INTEREST,2) AS INTEREST, "
					+ " ROUND((C.INTEREST * 0.9),2) AS TDS "*/
					/*+ " ROUND(SUM(((CLOSING_BALANCE * CTD.DEAL_CHARGE_FINAL_AMOUNT)/365)),2) AS PENAL_INT_SUM, "
					+ " ROUND((SUM(((CLOSING_BALANCE * CTD.DEAL_CHARGE_FINAL_AMOUNT)/365))*0.9),2) AS NET_TDS, "
					+ " ROUND(((C.INTEREST * 0.9)+(SUM(((CLOSING_BALANCE * CTD.DEAL_CHARGE_FINAL_AMOUNT)/365))*0.9)),2) AS TOTAL_NET_TDS "*/
					+ " FROM SEPARATE_INTEREST_REPORT_TEMP B"
					+ " LEFT JOIN (SELECT LOAN_ID, SUM(DAYS) AS DAYS FROM SEPARATE_INTEREST_REPORT_TEMP GROUP BY LOAN_ID) AS A ON A.LOAN_ID = B.LOAN_ID "
					+ " LEFT JOIN CR_TXNCHARGES_DTL CTD ON CTD.TXN_ID = B.LOAN_ID AND DEAL_CHARGE_CODE = 109 AND TXN_TYPE = 'LIM' "
					+ " LEFT JOIN (SELECT LOAN_ID, SUM(INTEREST)AS INTEREST  FROM SEPARATE_INTEREST_REPORT_TEMP GROUP BY LOAN_ID)C ON C.LOAN_ID=B.LOAN_ID "
					+ " ORDER BY B.LOAN_ID, SEQ_NO; "
					+ " ");
			
			logger.info("Interest Report customer query is:--------------"+query.toString());
			dataList = ConnectionReportDumpsDAO.sqlSelect(query.toString());
			logger.info("Interest Report customer data list size----------------"+dataList.size());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			query = null;
		}
		return dataList;
	}
	
	
	public ArrayList reportledgerDump() {
		logger.info("In InterestWorkingDAOImpl ............reportledgerDump()...");
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement ptmt = null;
		CallableStatement csmt = null;
		ArrayList vColumn = new ArrayList();
		ArrayList vRecords = new ArrayList();
		
		String accountNo ="";
		String fromDate ="";
		String toDate = "";
		String type = "117";
		String asOnDate = "";
		try
		 {
			con = ConnectionDAO.getConnection();
			csmt = (CallableStatement) con.prepareCall("{call Generate_ledger_dump(?,str_to_date(?,'"+dateFormat+"'),str_to_date(?,'"+dateFormat+"'),?,str_to_date(?,'"+dateFormat+"'))}");
			csmt.setString(1, accountNo);
			csmt.setString(2, fromDate);
			csmt.setString(3, toDate);
			csmt.setString(4, type);
			csmt.setString(5,asOnDate);
			rs = csmt.executeQuery();

			ResultSetMetaData rsmd = rs.getMetaData();
		    int numColumns = rsmd.getColumnCount();
			
		    // for adding column name 
		    for (int i=1; i<numColumns+1; i++) {
		    	vColumn.add(rsmd.getColumnName(i));
		    }
		    vRecords.add(vColumn);

		    //for adding data
	        int noOfFields = (rs.getMetaData()).getColumnCount();
	        while (rs.next()) {
	               int counter = 1;
	               ArrayList records = new ArrayList();
	               while (counter <= noOfFields) {
	                    records.add(rs.getString(counter));
	                    counter++;
	                }
	            vRecords.add(records);
	        } 
		}
		catch(Exception e)	{
			e.printStackTrace();}
		finally{
			 accountNo=null;
			 fromDate=null;
			 toDate=null;
			 type=null;
			 asOnDate=null;
			 vColumn=null;
			ConnectionDAO.closeConnection(con, stmt, rs);
		}
		return vRecords;
	}
}
