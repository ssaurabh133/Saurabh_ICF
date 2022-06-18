package com.communication.engn.daoImplMySql;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.commonFunction.daoImplMYSQL.commonFunctionDaoImpl;
import com.communication.engn.dao.EmailDAO;
import com.communication.engn.vo.EmailVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;

public class EmailDAOImpl implements EmailDAO {

	private static final Logger logger = Logger
			.getLogger(commonFunctionDaoImpl.class.getName());
	ResourceBundle resource = ResourceBundle
			.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
	String dateFormat = resource.getString("lbl.dateInDao");
	int no = Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");

	@Override
	public String variableReplaceInFile(EmailVO vo) 
	{
		 File file = new File(vo.getFilePath());
		 int ch;
		 StringBuffer strContent = new StringBuffer("");
		 FileInputStream fin = null;
		 String[] a = null; // I want to store the contens of file into this String a....??
		 try {
		 fin = new FileInputStream(file);
		 while ((ch = fin.read()) != -1)
		 strContent.append((char) ch);
		 fin.close();
		 } catch (Exception e) {
		 System.out.println(e);
		 }
		 String strContentReplace1=strContent.toString().replaceAll("#CustomerName#", vo.getCustomerName());
		 strContentReplace1=strContentReplace1.toString().replaceAll("#EventName#", vo.getEventName());
		 strContentReplace1=strContentReplace1.toString().replaceAll("#EventNo#", vo.getEventNo());		 
		 return strContentReplace1;
	}

	@Override
	public EmailVO getSmtpParameter() 
	{
		logger.info("In getSmtpParameter() of EmailDAOImpl");
		EmailVO vo=new EmailVO();
		ArrayList list = new ArrayList();
		String qrySmtpHostRes="";
		String qrySmtpAddRes="";
		String qrySmtpPsswdRes="";
		String qrySmtpPortRes="";
		
		String qrySmtpHost="select parameter_value from parameter_mst where parameter_key='SMTP_HOST'";
		String qrySmtpAdd="select parameter_value from parameter_mst where  parameter_key='SMTP_MAIL_ADDRESS' ";
		String qrySmtpPsswd="select parameter_value from parameter_mst where  parameter_key='SMTP_MAIL_PASSWORD'";
		String qrySmtpPort="select parameter_value from parameter_mst where  parameter_key='SMTP_PORT'";
		
		qrySmtpHostRes = ConnectionDAO.singleReturn(qrySmtpHost);
		qrySmtpAddRes=ConnectionDAO.singleReturn(qrySmtpAdd);
		qrySmtpPsswdRes=ConnectionDAO.singleReturn(qrySmtpPsswd);
		qrySmtpPortRes=ConnectionDAO.singleReturn(qrySmtpPort);
		vo.setSmtpHost(qrySmtpHostRes);
		vo.setSmtpMailAddress(qrySmtpAddRes);
		vo.setSmtpMailPassword(qrySmtpPsswdRes);
		vo.setPort(qrySmtpPortRes);
		return vo;		
	}
	
	@Override
	public ArrayList getEmailList() 
	{
		logger.info("In resultRelpaceDataQuery()");
		EmailVO vo=null;
		ArrayList list = new ArrayList();
		ArrayList resultList = new ArrayList();
		try 
		{
			StringBuilder query = new StringBuilder();
			query.append("select EVENT_NAME,CUSTOMER_NAME,EMAIL_ID,EMAIL_SUBJECT,MESSAGE,RECORD_ID from comm_event_data where EMAIL_ID is not null "
					+ " and TEMPLATE_TYPE='E' AND IFNULL(RECORD_STATUS,'N')='P' and EVENT_NAME not in ('DEAL_REJECT','DEAL_REJECT_EXTERNAL','WELCOME_LETTER','INTEREST_CERTIFICATE','STATEMENT_OF_ACCOUNT') ");
			logger.info("In getEmailList()  query   :   " + query.toString());
			list = ConnectionDAO.sqlSelect(query.toString());
			int size = list.size();
			for (int i = 0; i < size; i++) 
			{
				ArrayList dataList = (ArrayList) list.get(i);
				if (dataList.size() > 0) 
				{
					vo=new EmailVO();
					vo.setEventName(CommonFunction.checkNull(dataList.get(0)).toString());
					vo.setCustomerName(CommonFunction.checkNull(dataList.get(1)).toString());
					vo.setCustomerEmail(CommonFunction.checkNull(dataList.get(2)).toString());
					vo.setEmailSub(CommonFunction.checkNull(dataList.get(3)).toString());
					vo.setMassage(CommonFunction.checkNull(dataList.get(4)).toString());
					vo.setRecordId(Integer.parseInt(CommonFunction.checkNull(dataList.get(5))));
					resultList.add(vo);					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}
	public void updateEmailFlag() 
	{
		PrepStmtObject insertPrepStmtObject= new PrepStmtObject();
		ArrayList qryList2= new ArrayList();
		String queryUpdate="update comm_event_data set RECORD_STATUS='A'" +
		"where  IFNULL(RECORD_STATUS,'N')='P'";			
		try
		{
			insertPrepStmtObject.setSql(queryUpdate.toString());
	        qryList2.add(insertPrepStmtObject);
	        ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList2);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			insertPrepStmtObject=null;
			qryList2.clear();
			queryUpdate=null;
		}
	}
//Rohit Changes start for Onclick Email update
	public boolean updateEmailFlag(String deal,String event) 
	{
		boolean flag=false;
		PrepStmtObject insertPrepStmtObject= new PrepStmtObject();
		ArrayList qryList2= new ArrayList();
		String queryUpdate="update comm_event_data set RECORD_STATUS='A'" +
		"where TEMPLATE_TYPE='E' AND IFNULL(RECORD_STATUS,'N')='P' and (DEAL_ID='"+deal+"' OR LOAN_ID='"+deal+"' ) and EVENT_NAME='"+event+"'";			
		try
		{
			insertPrepStmtObject.setSql(queryUpdate.toString());
	        qryList2.add(insertPrepStmtObject);
	       flag=  ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList2);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			insertPrepStmtObject=null;
			qryList2.clear();
			queryUpdate=null;
		}
		return flag;
	}

	@Override
	public ArrayList getEmailListRejection() {
		logger.info("In resultRelpaceDataQuery()");
		EmailVO vo=null;
		ArrayList list = new ArrayList();
		ArrayList resultList = new ArrayList();
		try 
		{
			String Day = CommonFunction.checkNull(ConnectionDAO.singleReturn("SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY = 'DEAL_REJECTION_COMMUNICATION_DAY'"));
			StringBuilder query = new StringBuilder();
			query.append(" select EVENT_NAME,CUSTOMER_NAME,EMAIL_ID,EMAIL_SUBJECT,MESSAGE,RECORD_ID "
					+ " from comm_event_data c "
					+ " join cr_deal_dtl d on c.deal_id = d.deal_id and d.rec_status = 'X' "
					+ " where EMAIL_ID is not null and TEMPLATE_TYPE='E' AND IFNULL(RECORD_STATUS,'N')='P' "
					+ " and EVENT_NAME in ('DEAL_REJECT','DEAL_REJECT_EXTERNAL') "
					+ " and ( (date_add(date(d.deal_approval_date), interval "+Day+" day) = (select parameter_value from parameter_mst where parameter_key = 'BUSINESS_DATE')) "
					+ " or (date_add(date(d.deal_approval_date), interval "+Day+" day) < (select parameter_value from parameter_mst where parameter_key = 'BUSINESS_DATE'))) ");
			logger.info("In getEmailList()  query   :   " + query.toString());
			list = ConnectionDAO.sqlSelect(query.toString());
			int size = list.size();
			for (int i = 0; i < size; i++) 
			{
				ArrayList dataList = (ArrayList) list.get(i);
				if (dataList.size() > 0) 
				{
					vo=new EmailVO();
					vo.setEventName(CommonFunction.checkNull(dataList.get(0)).toString());
					vo.setCustomerName(CommonFunction.checkNull(dataList.get(1)).toString());
					vo.setCustomerEmail(CommonFunction.checkNull(dataList.get(2)).toString());
					vo.setEmailSub(CommonFunction.checkNull(dataList.get(3)).toString());
					vo.setMassage(CommonFunction.checkNull(dataList.get(4)).toString());
					vo.setRecordId(Integer.parseInt(CommonFunction.checkNull(dataList.get(5))));
					resultList.add(vo);					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}
	
	//Rohit Changes end for Onclick Email update

}
