package com.cm.daoImplMYSQL;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.cm.dao.BeginOfDayProcessDAO;
import com.cm.vo.processVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;

public class BeginOfDayProcessDAOImpl implements BeginOfDayProcessDAO{

	private static final Logger logger = Logger.getLogger(BeginOfDayProcessDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	 public String getBodStatus()
		{
		 logger.info("In getBodStatu() method of  BeginOfDayProcessDAOImpl");
			String status="";
			try
			{
			String query="select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='EOD_BOD_FLAG'";
			logger.info("Query: "+query);
			status=ConnectionDAO.singleReturn(query);
			logger.info("staus:"+status);
		    status=CommonFunction.checkNull(status);
			}
			catch (Exception e) {
				// TODO: handle exception
			}
			return status;
		}
		
	
	 public ArrayList showBodData(String busnissDate)
	 {
		 
		 logger.info("In showBodData() method of  BeginOfDayProcessDAOImpl");
			//eod_bod_process_m...
			ArrayList list = new ArrayList();
			ArrayList list1 = new ArrayList();
		try
			{	
		    StringBuffer query= new StringBuffer();
			//query.append("SELECT A.PROCESS_NAME,B.START_TIME,B.END_TIME,B.PROCESS_STATUS FROM eod_bod_process_m A LEFT OUTER JOIN EOD_BOD_PROCESS_LOG_DTL B ON A.PROCESS_NAME=B.PROCESS_NAME AND B.EOD_BOD_PROCESS_ID = (SELECT MAX(EOD_BOD_PROCESS_ID) FROM EOD_BOD_PROCESS_LOG_DTL) where a.REC_STATUS='A' And PROCESS_TYPE='B'");
		    query.append( "SELECT A.PROCESS_NAME,B.START_TIME,B.END_TIME,B.PROCESS_STATUS FROM eod_bod_process_m A " +
		    		" LEFT OUTER JOIN EOD_BOD_PROCESS_LOG_DTL B ON A.PROCESS_NAME=B.PROCESS_NAME AND  " +
		    		"B.EOD_BOD_PROCESS_ID IN  (SELECT MAX(EOD_BOD_PROCESS_ID) FROM EOD_BOD_PROCESS_LOG_DTL " +
		    		"where DATE_FORMAT(PROCESS_DATE,'"+dateFormat+"')='"+busnissDate+"' GROUP BY PROCESS_NAME) " +
		    		"where a.REC_STATUS='A' And A.PROCESS_TYPE='B'");
		    logger.info("query : "+query);
		    list=ConnectionDAO.sqlSelect(query.toString());
			logger.info("list..query "+query.toString());
			logger.info("list..size.."+list);
			
			}
			catch (Exception e)
			{
				logger.error(e.getMessage());
			}		
			
			
			return list;
			
			 
		 
	 }
	 
	 
	 
	 public ArrayList showBodProcessData(processVO vo)
		{
		 logger.info("In showBodProcessData() method of  BeginOfDayProcessDAOImpl");
			//eod_bod_process_m...
		
			ArrayList list = new ArrayList();
			ArrayList list1 = new ArrayList();
			 int statusProc=0;
			    ArrayList<Object> in =new ArrayList<Object>();
				ArrayList<Object> out =new ArrayList<Object>();
				ArrayList outMessages = new ArrayList();
				 String s1="";
				String s2="";
		
//			CallableStatement cst=null;
//			Connection con=ConnectionDAO.getConnection();
			try
			{	
//				con.setAutoCommit(false);
				String process="SELECT VALUE FROM generate_sequence_tbl WHERE SEQ_KEY='BOD_PROCESSID'";
				String processId=ConnectionDAO.singleReturn(process);
				logger.info(" In showBodProcessData processId: "+processId);
				int processID=Integer.parseInt(processId)+1;
				logger.info(" In showBodProcessData processID: "+processID);
				String processUpdate="UPDATE generate_sequence_tbl SET VALUE="+processID+" WHERE SEQ_KEY='BOD_PROCESSID'";
				
		
				logger.info(" In showBodProcessData BeforeProc: company id:"+vo.getCompanyId()+"currdate:"+vo.getCurrDate()+"userID:"+vo.getUserId()+"date: "+dateFormatWithTime);
//				con.setAutoCommit(false);
//				cst=con.prepareCall("call EOD_BOD_PROC(?,?,STR_TO_DATE(?,'"+dateFormatWithTime+"'),?,?,?)");
					
				in.add(vo.getCompanyId());		
					in.add("B");
					 String date=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getCurrDate()).trim());
					    if(date != null)
					    	in.add(date);
//					cst.setString(3, vo.getCurrDate());
					    in.add(vo.getUserId());
					out.add(s1);
					 out.add(s2);
					 outMessages=(ArrayList) ConnectionDAO.callSP("EOD_BOD_PROC",in,out);
					 s1=CommonFunction.checkNull(outMessages.get(0));
					s2=CommonFunction.checkNull(outMessages.get(1));
//					cst.registerOutParameter(5, Types.CHAR);
//					cst.registerOutParameter(6, Types.CHAR);
//					statusProc=cst.executeUpdate();
//					String s1 = cst.getString(5);
//					String s2 = cst.getString(6);
					logger.info("s1: "+s1);
					logger.info("s2: "+s2);
					
					logger.info("Status for Proc: "+statusProc);
					logger.info("After Proc: ");
				
				
		    StringBuffer query= new StringBuffer();
			//query.append("SELECT A.PROCESS_NAME,B.START_TIME,B.END_TIME,B.PROCESS_STATUS FROM eod_bod_process_m A LEFT OUTER JOIN EOD_BOD_PROCESS_LOG_DTL B ON A.PROCESS_NAME=B.PROCESS_NAME AND B.EOD_BOD_PROCESS_ID = (SELECT MAX(EOD_BOD_PROCESS_ID) FROM EOD_BOD_PROCESS_LOG_DTL) where a.REC_STATUS='A' And PROCESS_TYPE='E'");
		    query.append("SELECT A.PROCESS_NAME,B.START_TIME,B.END_TIME,B.PROCESS_STATUS FROM eod_bod_process_m A LEFT OUTER JOIN EOD_BOD_PROCESS_LOG_DTL B ON A.PROCESS_NAME=B.PROCESS_NAME AND   B.EOD_BOD_PROCESS_ID IN  (SELECT MAX(EOD_BOD_PROCESS_ID) FROM EOD_BOD_PROCESS_LOG_DTL where date(EOD_BOD_PROCESS_LOG_DTL.PROCESS_DATE)>=(select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'BUSINESS_DATE' ) GROUP BY PROCESS_NAME) where a.REC_STATUS='A' And PROCESS_TYPE='B'");
		    list=ConnectionDAO.sqlSelect(query.toString());
			logger.info("After Proc: query"+query.toString());
			logger.info("After Proc: list"+list);
			}
			catch (Exception e)
			{
				logger.error(e.getMessage());
			}	
			
			
			return list;
			
		}
	 public ArrayList showBodErrorData(String ProcessName)
		{
		 logger.info("In showBodErrorData() method of  BeginOfDayProcessDAOImpl");
			String query="";
			String pname=CommonFunction.checkNull(ProcessName);
			ArrayList list= new ArrayList();		
			try
			{
				query="select LOAN_ID,TXN_TYPE,TXN_ID,ERROR_MSG from eod_bod_process_error_log where PROCESS_NAME='"+ProcessName+"'";
				logger.info("query: "+query);
				list=ConnectionDAO.sqlSelect(query);
			}	
			catch (Exception e) {
				logger.info(e.getMessage());
			}
			return list;	
		}
	 public String getBodProcessStatus()
		{
		 logger.info("In getBodProcessStatus() method of  BeginOfDayProcessDAOImpl");
			String status="";
			try
			{
			String query="select PROCESS_STATUS from eod_bod_process_log_dtl where PROCESS_STATUS='P'";	
			status=ConnectionDAO.singleReturn(query);
			logger.info("In ..getBodProcessStatus() of CM DAIO IMPL. staus:"+status);
		    status=CommonFunction.checkNull(status);
			}
			catch (Exception e) {
				// TODO: handle exception
			}
			return status;
		}
	 
	
	
	 
	 
	 
	 
	 
	 
	 
	 
	
}
