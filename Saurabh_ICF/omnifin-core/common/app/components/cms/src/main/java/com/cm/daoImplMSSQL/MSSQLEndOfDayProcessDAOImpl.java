package com.cm.daoImplMSSQL;


import java.text.DecimalFormat;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.apache.commons.lang.StringEscapeUtils;
import com.cm.dao.EndOfDayProcessDAO;
import com.cm.vo.processVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.cp.vo.CodeDescVo;
import com.cp.vo.RelationalManagerVo;

public class MSSQLEndOfDayProcessDAOImpl implements EndOfDayProcessDAO{
	private static final Logger logger = Logger.getLogger(MSSQLEndOfDayProcessDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");
	
	
	
	public ArrayList showEodBodData(String businessDate){
		

		//eod_bod_process_m...
		logger.info("In showEodBodData() method of EndOfDayProcessDAOImpl");
		ArrayList list = new ArrayList();
		ArrayList list1 = new ArrayList();
	try
		{	
		
	    StringBuffer query= new StringBuffer();
		//query.append("SELECT A.PROCESS_NAME,B.START_TIME,B.END_TIME,B.PROCESS_STATUS FROM eod_bod_process_m A LEFT OUTER JOIN EOD_BOD_PROCESS_LOG_DTL B ON A.PROCESS_NAME=B.PROCESS_NAME AND B.EOD_BOD_PROCESS_ID = (SELECT MAX(EOD_BOD_PROCESS_ID) FROM EOD_BOD_PROCESS_LOG_DTL) where a.REC_STATUS='A' And PROCESS_TYPE='E'");
	    query.append("SELECT A.PROCESS_NAME,B.START_TIME,B.END_TIME,B.PROCESS_STATUS " +
	    		"FROM eod_bod_process_m A LEFT OUTER JOIN EOD_BOD_PROCESS_LOG_DTL B ON A.PROCESS_NAME=B.PROCESS_NAME " +
	    		"AND B.EOD_BOD_PROCESS_ID IN  (SELECT MAX(EOD_BOD_PROCESS_ID) FROM EOD_BOD_PROCESS_LOG_DTL" +
	    		" where ");
	    query.append(dbo);
	    query.append("DATE_FORMAT(PROCESS_DATE,'"+dateFormat+"')='"+businessDate+"' GROUP BY PROCESS_NAME)" +
	    		" where a.REC_STATUS='A' And PROCESS_TYPE='E'");
	    list=ConnectionDAO.sqlSelect(query.toString());
		logger.info("list..query "+query.toString());
		logger.info("list..size.."+list.size());
		
		query=null;
		
		}
		catch (Exception e)
		{
			logger.error(e.getMessage());
		}		
		
		
		return list;
		
	
	}
	
	
	 
	 public ArrayList showProcessData(processVO vo)
	 { 
		 logger.info("In showProcessData() method of EndOfDayProcessDAOImpl");
			//eod_bod_process_m...
			logger.info("In.  showProcessData()...DAIOIMPL.....");
			ArrayList list = new ArrayList();
			ArrayList list1 = new ArrayList();
			 int statusProc=0;
		   	    ArrayList<Object> in =new ArrayList<Object>();
		   	  ArrayList<Object> in1 =new ArrayList<Object>();
        		ArrayList<Object> out =new ArrayList<Object>();
        		ArrayList<Object> out1 =new ArrayList<Object>();
        		ArrayList outMessages = new ArrayList();
//        		 String s1="";
//        		String s2="";
        		
        		StringBuilder s1=new StringBuilder();
         		StringBuilder s2=new StringBuilder();
         		
        		//			CallableStatement cst=null;
//			Connection con=ConnectionDAO.getConnection();
			try
			{	
//				con.setAutoCommit(false);
				String process="SELECT VALUE FROM generate_sequence_tbl WHERE SEQ_KEY='EOD_PROCESSID'";
				String processId=ConnectionDAO.singleReturn(process);
				logger.info(" In showEodBodData processId: "+processId);
				int processID=Integer.parseInt(processId)+1;
				logger.info(" In showEodBodData processID: "+processID);
				String processUpdate="UPDATE generate_sequence_tbl SET VALUE="+processID+" WHERE SEQ_KEY='EOD_PROCESSID'";
				in1.add(vo.getUserId());
				out1.add(s1);
				out1.add(s2);
				outMessages=(ArrayList) ConnectionDAO.callSP("CHECK_TRANSACTIONS_BEFORE_EOD",in1,out1);
				s1.append(CommonFunction.checkNull(outMessages.get(0)));
				s2.append(CommonFunction.checkNull(outMessages.get(1)));
			   logger.info("CHECK_TRANSACTIONS_BEFORE_EOD s1: "+s1);
			   logger.info("CHECK_TRANSACTIONS_BEFORE_EOD s2: "+s2);
					
				 if(s2.toString().equalsIgnoreCase(""))
				 {
						logger.info(" In showEodBodData BeforeProc: ");
		//				cst=con.prepareCall("call EOD_BOD_PROC(?,?,STR_TO_DATE(?,'"+dateFormatWithTime+"'),?,?,?)");
							
						in.add(vo.getCompanyId());		
						in.add("E");
							//cst.setInt(3, 0);
						 String date=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getCurrDate()).trim());
		      	    if(date != null)
		      	    	in.add(date);
		
		//				in.add(vo.getCurrDate());
						    in.add(vo.getUserId());
							out.add(s1);
							out.add(s2);
		
							outMessages=(ArrayList) ConnectionDAO.callSP("EOD_BOD_PROC",in,out);
							s1.append(CommonFunction.checkNull(outMessages.get(0)));
							s2.append(CommonFunction.checkNull(outMessages.get(1)));
		//				
		//					cst.registerOutParameter(5, Types.CHAR);
		//					cst.registerOutParameter(6, Types.CHAR);
		//					statusProc=cst.executeUpdate();
		//					String s1 = cst.getString(5);
		//					String s2 = cst.getString(6);
							logger.info("EOD_BOD_PROC s1: "+s1);
							logger.info("EOD_BOD_PROC s2: "+s2);
							logger.info("Status for Proc: "+statusProc);
							logger.info("After Proc: ");
							boolean updateStatus=updateEodRunningFlag("N", vo.getUserId(), vo.getCurrDate());
							logger.info(" updateEodRunningFlag updateStatus:---"+updateStatus);
							StringBuffer query= new StringBuffer();
							//query.append("SELECT A.PROCESS_NAME,B.START_TIME,B.END_TIME,B.PROCESS_STATUS FROM eod_bod_process_m A LEFT OUTER JOIN EOD_BOD_PROCESS_LOG_DTL B ON A.PROCESS_NAME=B.PROCESS_NAME AND B.EOD_BOD_PROCESS_ID = (SELECT MAX(EOD_BOD_PROCESS_ID) FROM EOD_BOD_PROCESS_LOG_DTL) where a.REC_STATUS='A' And PROCESS_TYPE='E'");
						    query.append("SELECT A.PROCESS_NAME,B.START_TIME,B.END_TIME,B.PROCESS_STATUS FROM eod_bod_process_m A LEFT OUTER JOIN EOD_BOD_PROCESS_LOG_DTL B ON A.PROCESS_NAME=B.PROCESS_NAME AND   B.EOD_BOD_PROCESS_ID IN  (SELECT MAX(EOD_BOD_PROCESS_ID) FROM EOD_BOD_PROCESS_LOG_DTL where dbo.date(EOD_BOD_PROCESS_LOG_DTL.PROCESS_DATE)>=(select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'BUSINESS_DATE' ) GROUP BY PROCESS_NAME) where a.REC_STATUS='A' And PROCESS_TYPE='E'");
						    
						    list=ConnectionDAO.sqlSelect(query.toString());
						    list.add("");
							logger.info("After Proc: query"+query.toString());
							logger.info("After Proc: list"+list.size());
							query=null;
						
			  }
				 else
				 {
					 list.add(s2);
				 }
		    
			}
			catch (Exception e)
			{
				boolean updateStatus=updateEodRunningFlag("N", vo.getUserId(), vo.getCurrDate());
				logger.info(" updateEodRunningFlag updateStatus:---"+updateStatus);
				logger.error(e.getMessage());
			}	
			finally
			{
				s1=null;
				s2=null;
				
			}
	
            return list;
			
		}
	 public ArrayList showErrorData(String ProcessName)
		{
		 logger.info("In showErrorData() method of EndOfDayProcessDAOImpl");
			StringBuilder query=new StringBuilder();
			//String pname=CommonFunction.checkNull(ProcessName);
			ArrayList list= new ArrayList();		
			try
			{
				query.append("select LOAN_ID,TXN_TYPE,TXN_ID,ERROR_MSG from eod_bod_process_error_log where PROCESS_NAME='"+ProcessName+"'");
				list=ConnectionDAO.sqlSelect(query.toString());
			}	
			catch (Exception e) {
				logger.info(e.getMessage());
			}
			finally
			{
				query=null;
			}
			return list;	
		}
	 public String getProcessStatus()
		{
		 logger.info("In getProcessStatus() method of EndOfDayProcessDAOImpl");
			String status="";
			try
			{
				StringBuilder query=new StringBuilder();
			 query.append("select PROCESS_STATUS from eod_bod_process_log_dtl where PROCESS_STATUS='P'");	
			status=ConnectionDAO.singleReturn(query.toString());
			logger.info("In ..getProcessStatus() of CM DAIO IMPL. staus:"+status);
		    status=CommonFunction.checkNull(status);
			query=null;
			}
			catch (Exception e) {
				// TODO: handle exception
			}
			return status;
		}
	 
	 public void updateUserLoginStatus(processVO vo)
     {
    	 logger.info("In updateUserLoginStatus......................");
        	logger.info("vo.getUserId() ................... "+vo.getUserId());
 			ArrayList qryList=new ArrayList();
 			boolean status=false;
 			try {
 				  PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
 				   StringBuffer bufInsSql =	new StringBuffer();
 				   
 				   bufInsSql.append("UPDATE sec_user_log set LOGIN_STATUS='N' WHERE USER_ID!=? ");
 						
            			 	if ((CommonFunction.checkNull(vo.getUserId())).trim().equalsIgnoreCase(""))
            			 		insertPrepStmtObject.addNull();
            			 	else
            			 		insertPrepStmtObject.addString((vo.getUserId()).trim());
 		
 					insertPrepStmtObject.setSql(bufInsSql.toString());
 					logger.info("IN updateInstallPlan() insert query1 ### "+insertPrepStmtObject.printQuery());
 					qryList.add(insertPrepStmtObject);
 					status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
 					
 					bufInsSql=null;
 				} 
 			catch (Exception e) {
 					e.printStackTrace();
 				}
 			    logger.info("In updateUserLoginStatus......................"+status);
 		}



	public ArrayList showProcessDataAfterConfirm(processVO vo) {
		logger.info("In showProcessDataAfterConfirm() method of EndOfDayProcessDAOImpl");
		//eod_bod_process_m...
		logger.info("In.  showProcessDataAfterConfirm()...DAIOIMPL.....");
		ArrayList list = new ArrayList();
		ArrayList list1 = new ArrayList();
		 int statusProc=0;
	   	    ArrayList<Object> in =new ArrayList<Object>();
	   	 // ArrayList<Object> in1 =new ArrayList<Object>();
    		ArrayList<Object> out =new ArrayList<Object>();
    	//	ArrayList<Object> out1 =new ArrayList<Object>();
    		ArrayList outMessages = new ArrayList();
//    		 String s1="";
//    		String s2="";
    		StringBuilder s1=new StringBuilder();
    		StringBuilder s2=new StringBuilder();
//		CallableStatement cst=null;
//		Connection con=ConnectionDAO.getConnection();
		try
		{	
//			con.setAutoCommit(false);
			StringBuilder process=new StringBuilder();
			 process.append("SELECT VALUE FROM generate_sequence_tbl WHERE SEQ_KEY='EOD_PROCESSID'");
			 StringBuilder processId=new StringBuilder();
			 processId.append(ConnectionDAO.singleReturn(process.toString()));
			logger.info(" In showProcessDataAfterConfirm processId: "+processId);
			int processID=Integer.parseInt(processId.toString())+1;
			logger.info(" In showProcessDataAfterConfirm processID: "+processID);
			String processUpdate="UPDATE generate_sequence_tbl SET VALUE="+processID+" WHERE SEQ_KEY='EOD_PROCESSID'";
			//in1.add(vo.getUserId());
			//out1.add(s1);
			//out1.add(s2);
			//outMessages=(ArrayList) ConnectionDAO.callSP("CHECK_TRANSACTIONS_BEFORE_EOD",in1,out1);
			//s1=CommonFunction.checkNull(outMessages.get(0));
			//s2=CommonFunction.checkNull(outMessages.get(1));
		  // logger.info("CHECK_TRANSACTIONS_BEFORE_EOD s1: "+s1);
		  // logger.info("CHECK_TRANSACTIONS_BEFORE_EOD s2: "+s2);
				
			 //if(s2.equalsIgnoreCase(""))
			// {
					logger.info(" In showProcessDataAfterConfirm BeforeProc: EOD_BOD_PROC ");
	//				cst=con.prepareCall("call EOD_BOD_PROC(?,?,STR_TO_DATE(?,'"+dateFormatWithTime+"'),?,?,?)");
						
					in.add(vo.getCompanyId());		
					in.add("E");
						//cst.setInt(3, 0);
					 String date=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getCurrDate()).trim());
	      	    if(date != null)
	      	    	in.add(date);
	
	//				in.add(vo.getCurrDate());
					    in.add(vo.getUserId());
						out.add(s1);
						out.add(s2);
	
						outMessages=(ArrayList) ConnectionDAO.callSP("EOD_BOD_PROC",in,out);
						s1.append(CommonFunction.checkNull(outMessages.get(0)));
						s2.append(CommonFunction.checkNull(outMessages.get(1)));
	//				
	//					cst.registerOutParameter(5, Types.CHAR);
	//					cst.registerOutParameter(6, Types.CHAR);
	//					statusProc=cst.executeUpdate();
	//					String s1 = cst.getString(5);
	//					String s2 = cst.getString(6);
						
						logger.info("EOD_BOD_PROC s1: "+s1);
						logger.info("EOD_BOD_PROC s2: "+s2);
						logger.info("Status for Proc: "+statusProc);
						logger.info("After Proc:EOD_BOD_PROC ");
						StringBuffer query= new StringBuffer();
						//query.append("SELECT A.PROCESS_NAME,B.START_TIME,B.END_TIME,B.PROCESS_STATUS FROM eod_bod_process_m A LEFT OUTER JOIN EOD_BOD_PROCESS_LOG_DTL B ON A.PROCESS_NAME=B.PROCESS_NAME AND B.EOD_BOD_PROCESS_ID = (SELECT MAX(EOD_BOD_PROCESS_ID) FROM EOD_BOD_PROCESS_LOG_DTL) where a.REC_STATUS='A' And PROCESS_TYPE='E'");
					    query.append("SELECT A.PROCESS_NAME,B.START_TIME,B.END_TIME,B.PROCESS_STATUS FROM eod_bod_process_m A LEFT OUTER JOIN EOD_BOD_PROCESS_LOG_DTL B ON A.PROCESS_NAME=B.PROCESS_NAME AND   B.EOD_BOD_PROCESS_ID IN  (SELECT MAX(EOD_BOD_PROCESS_ID) FROM EOD_BOD_PROCESS_LOG_DTL where dbo.date(EOD_BOD_PROCESS_LOG_DTL.PROCESS_DATE)>=(select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'BUSINESS_DATE' ) GROUP BY PROCESS_NAME) where a.REC_STATUS='A' And PROCESS_TYPE='E'");
					    
					    list=ConnectionDAO.sqlSelect(query.toString());
					    list.add("");
						logger.info("After Proc: query"+query.toString());
						logger.info("After Proc: list"+list.size());
						
					boolean updateStatus=updateEodRunningFlag("N", vo.getUserId(), vo.getCurrDate());
					logger.info(" updateEodRunningFlag updateStatus:---"+updateStatus);
						
					
		//  }
			// else
			 //{
				// list.add(s2);
			 //}
	    process=null;
	    processId=null;
	    query=null;
		}
		catch (Exception e)
		{
			boolean updateStatus=updateEodRunningFlag("N", vo.getUserId(), vo.getCurrDate());
			logger.info(" updateEodRunningFlag updateStatus in exception :---"+updateStatus);
			logger.error(e.getMessage());
			
		}	
		finally
		{
			s1=null;
			s2=null;
		}

        return list;
	}    
	 
public String getEodRunningFlag(){
	 logger.info("In getEodRunningFlag() method of EndOfDayProcessDAOImpl");
		String status="";
		try
		{
		StringBuilder query=new StringBuilder();
		 query.append("select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='EOD_RUNNING_FLAG'");	
		status=ConnectionDAO.singleReturn(query.toString());
		logger.info("In ..getEodRunningFlag() Eod running flag is. staus:-"+status);
	    status=CommonFunction.checkNull(status);
		query=null;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	
}
public boolean updateEodRunningFlag(String flagValue,String userId,String businessDate){
	logger.info("In updateEodRunningFlag() method of EndOfDayProcessDAOImpl");
	//String status="";
	ArrayList qryList=new ArrayList();
	boolean status=false;
	
	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	try
	{
	StringBuilder query=new StringBuilder();
	query.append("UPDATE parameter_mst SET PARAMETER_VALUE='"+flagValue+"' WHERE PARAMETER_KEY ='EOD_RUNNING_FLAG'");
	insertPrepStmtObject.setSql(query.toString());
	logger.info("IN updateInstallPlan() insert query1 ### "+insertPrepStmtObject.printQuery());
	qryList.add(insertPrepStmtObject);
	status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
	logger.info("In ..updateEodRunningFlag() Eod running flag is. staus:-"+status);
    
	query=null;
	}
	catch (Exception e) {
		e.printStackTrace();
	}
	return status;
}
public String getSmtpHost()
{
	String host="";
	logger.info("Enter smtpHost");
	try {	
			String query=" SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='SMTP_HOST'; ";
			host=CommonFunction.checkNull(ConnectionDAO.singleReturn(query));
		}catch(Exception e){
				e.getStackTrace();	 
		}
		return  host;
}
public String getSmtpPort()
{
	String port="";
	logger.info("Enter smtpPort");
	try {	
			String query=" SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='SMTP_PORT'; ";
			port=CommonFunction.checkNull(ConnectionDAO.singleReturn(query));
		}catch(Exception e){
				e.getStackTrace();	 
		}
		return  port;
}
public String getSmtpMail()
{
	String mail="";
	logger.info("Enter getSmtpMail");
	try {	
			String query=" SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='SMTP_MAIL_ADDRESS'; ";
			mail=CommonFunction.checkNull(ConnectionDAO.singleReturn(query));
		}catch(Exception e){
				e.getStackTrace();	 
		}
		return  mail;
}
public String getSmtpPwd()
{
	String pwd="";
	logger.info("Enter getSmtpPwd");
	try {	
			String query=" SELECT PARAMETER_VALUE FROM parameter_mst WHERE PARAMETER_KEY='SMTP_MAIL_PASSWORD'; ";
			pwd=CommonFunction.checkNull(ConnectionDAO.singleReturn(query));
		}catch(Exception e){
				e.getStackTrace();	 
		}
		return  pwd;
}

public ArrayList<String> getFileAttachment(String mailTo,String businessDate)
{		
	logger.info("getFileAttachment:::::::::: ");
	ArrayList<String> list=new ArrayList<String>();
		try{
			String reportDate="";
			String reportDaily="";
			StringBuilder query=new StringBuilder();
			StringBuilder query1=new StringBuilder();
			StringBuilder query2=new StringBuilder();
			StringBuilder query3=new StringBuilder();
			query1.append("select  ");
			query1.append(dbo);
			query1.append("DATE_FORMAT(REPORT_DATE,'"+dateFormat+"')as REPORT_DATE from eod_reports_dtl WHERE USER_ID=(SELECT USER_ID FROM SEC_USER_M WHERE USER_EMAIL='"+mailTo+"') AND DAILY='N' ");
		    reportDate=CommonFunction.checkNull(ConnectionDAO.singleReturn(query1.toString()));
			logger.info("getAttachhList:::::: query1:::::::"+query1.toString());
			query3.append("select count(1)  from eod_reports_dtl WHERE USER_ID=(SELECT USER_ID FROM SEC_USER_M WHERE USER_EMAIL='"+mailTo+"') AND DAILY='Y' ");
		    reportDaily=CommonFunction.checkNull(ConnectionDAO.singleReturn(query3.toString()));
			logger.info("getAttachhList:::::: query3:::::::"+query3.toString()+"--------------reportDaily--------"+reportDaily);

			if(!reportDaily.equalsIgnoreCase("0"))
			{
				logger.info(" first if block::::::" );
				query.append("select concat((select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='EOD_REPORT_PATH'),'/', REPORT_NAME,'.pdf') REPORT_PATH from eod_reports_dtl WHERE USER_ID=(SELECT USER_ID FROM SEC_USER_M WHERE USER_EMAIL='"+mailTo+"') AND DAILY='Y' ");
				logger.info(" first if block query :::::: query:::::::"+query.toString());
				ArrayList attachhList = ConnectionDAO.sqlSelect(query.toString());
				logger.info("getAttachhList: query"+query.toString());
				logger.info("getAttachhList "+attachhList.size());
				logger.info("getAttachhList businessDate ::::: "+businessDate);			
				query=null;
				
				for(int i=0;i<attachhList.size();i++)
				{
					logger.info("getFileAttachment...Outer FOR loop "+CommonFunction.checkNull(attachhList.get(i)).toString());
					ArrayList data=(ArrayList)attachhList.get(i);
					if(data.size()>0)	
					{
						list.add((CommonFunction.checkNull(data.get(0))).trim().toString());
					}
				}
			}
			if(reportDate.equalsIgnoreCase(businessDate))
			{
				logger.info(" second if block::::::" );
				query2.append("select concat((select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='EOD_REPORT_PATH'),'/', REPORT_NAME,'.pdf') REPORT_PATH from eod_reports_dtl WHERE USER_ID=(SELECT USER_ID FROM SEC_USER_M WHERE USER_EMAIL='"+mailTo+"') AND DAILY='N' and ");
				query2.append(dbo);
				query2.append("DATE_FORMAT(REPORT_DATE,'"+dateFormat+"')='"+businessDate+"'");
				logger.info(" second if block query :::::: query:::::::"+query2.toString());
				ArrayList attachhList = ConnectionDAO.sqlSelect(query2.toString());
				logger.info("getAttachhList: query"+query2.toString());
				logger.info("getAttachhList "+attachhList.size());
				logger.info("getAttachhList businessDate ##### "+businessDate);
				query2=null;
				
				for(int i=0;i<attachhList.size();i++)
				{
					logger.info("getFileAttachment...Outer FOR loop "+CommonFunction.checkNull(attachhList.get(i)).toString());
					ArrayList data=(ArrayList)attachhList.get(i);
					if(data.size()>0)	
					{
						list.add((CommonFunction.checkNull(data.get(0))).trim().toString());
					}
				}
			}
			
			query1=null;

			}catch(Exception e){
			e.printStackTrace();
		}
		return list;
}

public ArrayList<String> getMailToDetail()
{		
	ArrayList<String> list=new ArrayList<String>();
		try{
			StringBuilder query=new StringBuilder();
			query.append("SELECT USER_EMAIL FROM SEC_USER_M WHERE USER_ID IN (SELECT USER_ID FROM EOD_REPORTS_DTL)");
			ArrayList attachhList = ConnectionDAO.sqlSelect(query.toString());
			logger.info("getMailToDetail "+attachhList.size());
		
			query=null;
		
			for(int i=0;i<attachhList.size();i++)
			{
				logger.info("getMailToDetail...Outer FOR loop "+CommonFunction.checkNull(attachhList.get(i)).toString());
				ArrayList data=(ArrayList)attachhList.get(i);
				if(data.size()>0)	
				{
					list.add((CommonFunction.checkNull(data.get(0))).trim().toString());
				}
			}
			}catch(Exception e){
			e.printStackTrace();
		}
		return list;
}
public boolean checkBdateStatus(String businessDate)
{
	StringBuilder bq=new StringBuilder();
	bq.append("select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY = 'BUSINESS_DATE' and ");
	bq.append("dbo.DATE_FORMAT(PARAMETER_VALUE,'"+dateFormat+"')='"+businessDate+"'");
	logger.info("Query: "+bq.toString());
	boolean bflg=ConnectionDAO.checkStatus(bq.toString());
	return bflg;
}
}
