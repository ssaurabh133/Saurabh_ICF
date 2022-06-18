package com.communication.engn.daoImplMySql;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javax.jms.Session;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import javax.jms.Session;
import javax.servlet.http.HttpServletRequest;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;









import com.cm.vo.ManualAdviceCreationVo;
import com.commonFunction.daoImplMYSQL.commonFunctionDaoImpl;
import com.commonFunction.vo.ShowCustomerDetailVo;
import com.communication.engn.dao.EmailDAO;
import com.communication.engn.dao.SmsDAO;
import com.communication.engn.utils.SendMailUsingAuthentication;
import com.communication.engn.vo.EmailVO;
import com.communication.engn.vo.SmsVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.ConnectionDAOforEJB;
import com.connect.DaoImplInstanceFactory;
import com.connect.PrepStmtObject;
import com.cp.vo.CommonDealVo;
import com.ibm.icu.util.Calendar;
import com.login.dao.LoginDao;
import com.mysql.jdbc.PreparedStatement;
import com.utils.ChCrypto;

import org.tempuri.Service;


public class SmsDAOImpl implements SmsDAO {

	private static final Logger logger = Logger
			.getLogger(commonFunctionDaoImpl.class.getName());
	ResourceBundle resource = ResourceBundle
			.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
	String dateFormat = resource.getString("lbl.dateInDao");
	int no = Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	
	@Override
	public String callProcCommEngnProcess() {
		logger.info("Inside callProcCommEngnProcess() Method ");		
		ArrayList<Object> in =new ArrayList<Object>();
		ArrayList<Object> out =new ArrayList<Object>();
		ArrayList outMessages = new ArrayList();
		String s1="";
		String s2="";
		try
		{
			in.add("Omnifin");
			out.add(s1);
			out.add(s2);
			logger.info("COMM_ENGN_PROCESS ("+in.toString()+","+out.toString());
			outMessages=(ArrayList) ConnectionDAO.callSP("COMM_ENGN_PROCESS",in,out);
			s1=CommonFunction.checkNull(outMessages.get(0));
			s2=CommonFunction.checkNull(outMessages.get(1));
	        logger.info("s1  : "+s1);
	        logger.info("s2  : "+s2);
	     }
		 catch(Exception ex)
		 {ex.printStackTrace();}
		 finally
		 {
			 in.clear();
			 in=null;
			 out.clear();
			 out=null;
			 outMessages.clear();
			 outMessages=null;
		 }
		return s1;
	}

	
	
	@Override
	public ArrayList<SmsVO> getSmsData(String eventName) 
	{
		SmsVO vo=null;
		ArrayList<SmsVO> resultList = new ArrayList<SmsVO>();
		ArrayList qryList = new ArrayList<SmsVO>();
		try 
		{
			String qry="";
			if(eventName.equalsIgnoreCase("Legal"))
			{
				 qry="select RECORD_ID,PHONE_NO,MESSAGE,SMS_ENTITY_ID,TEMPLATE_ID from comm_event_data " +
						"where TEMPLATE_TYPE='S' and PHONE_NO is not null and EVENT_NAME='"+eventName+"' AND SOURCE='ONCLICK' AND IFNULL(RECORD_STATUS,'N')='P' AND IFNULL(DELIVERY_STATUS,'N') IN ('P','F')";
			}
			else if(eventName.equalsIgnoreCase("DEAL_REJECT") || eventName.equalsIgnoreCase("DEAL_REJECT_EXTERNAL"))
			{
			String Day = CommonFunction.checkNull(ConnectionDAO.singleReturn("SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY = 'DEAL_REJECTION_COMMUNICATION_DAY'"));	
			 qry=" select RECORD_ID,PHONE_NO,MESSAGE,SMS_ENTITY_ID,TEMPLATE_ID from comm_event_data c "
			 		+ " join cr_deal_dtl d on c.deal_id = d.deal_id and d.rec_status = 'X' "
			 		+ " where TEMPLATE_TYPE='S' and PHONE_NO is not null and EVENT_NAME='"+eventName+"' "
			 		+ " and ( (date_add(date(d.deal_approval_date), interval "+Day+" day) = (select parameter_value from parameter_mst where parameter_key = 'BUSINESS_DATE')) "
			 		+ " or (date_add(date(d.deal_approval_date), interval "+Day+" day) < (select parameter_value from parameter_mst where parameter_key = 'BUSINESS_DATE'))) "
			 		+ " AND IFNULL(RECORD_STATUS,'N')='P' AND IFNULL(DELIVERY_STATUS,'N') IN ('P','F')";
			}else{
				qry="select RECORD_ID,PHONE_NO,MESSAGE,SMS_ENTITY_ID,TEMPLATE_ID from comm_event_data " +
						"where TEMPLATE_TYPE='S' and PHONE_NO is not null and EVENT_NAME='"+eventName+"' AND IFNULL(RECORD_STATUS,'N')='P' AND IFNULL(DELIVERY_STATUS,'N') IN ('P','F')";
			}
			logger.info("In getSmsData()  query   :   " + qry);
			qryList = ConnectionDAO.sqlSelect(qry);
			int size = qryList.size();
			for (int i = 0; i < size; i++) 
			{
				vo=new SmsVO();
				ArrayList dataList = (ArrayList) qryList.get(i);
				if (dataList.size() > 0) 
				{
					vo.setRecordId(Integer.parseInt(CommonFunction.checkNull(dataList.get(0))));
					vo.setMobileNo(CommonFunction.checkNull(dataList.get(1)).toString());
					vo.setMessage(CommonFunction.checkNull(dataList.get(2)).toString());
					vo.setEntityId(CommonFunction.checkNull(dataList.get(3)).toString());
					vo.setTemplateId(CommonFunction.checkNull(dataList.get(4)).toString());
					resultList.add(vo);
				}
				vo=null;
				dataList.clear();
				dataList=null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			qryList.clear();
			qryList=null;			
		}
		return resultList;
	}

	@Override
	public void writeToFile(ArrayList<SmsVO> vo,String eventName) 
	{
		String dateQry="select REPLACE(REPLACE(DATE_ADD(STR_TO_DATE(parameter_value,'%Y-%m-%d %H:%i'),INTERVAL CURTIME() HOUR_SECOND),' ','_'),':','_')parameter_value  from parameter_mst where parameter_key='business_date';";
		logger.info("In docUpload(): dateQry:==>>" + dateQry);
		String currentDate = ConnectionDAO.singleReturn(dateQry);
		boolean status = false;
		String fileName = "SmsFile_"+eventName+"_"+currentDate+".txt";
		String filePath = "";
		String message = "";
		FileOutputStream fileOutStream = null;
		File fileToCreate = null;
		File file =null;
		FileWriter fw=null;
		BufferedWriter bw=null;
		try {
			String query = "select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='Sms_File_Upload'";
			logger.info("In docUpload(): query:==>>" + query);

			String rpt = ConnectionDAO.singleReturn(query);
            file = new File(rpt + "/"+fileName);
			
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			for(int i=0;i<vo.size();i++){
			 fw = new FileWriter(file.getAbsoluteFile(),true);
			 bw = new BufferedWriter(fw);
			 bw.write(vo.get(i).getMessage());
			 bw.newLine();
			bw.close();
		}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		finally
		{
			PrepStmtObject insertPrepStmtObject= new PrepStmtObject();
			ArrayList qryList2= new ArrayList();
			String queryUpdate="update comm_event_data set RECORD_STATUS='A',SMS_FILE_NEME='"+CommonFunction.checkNull(fileName).trim()+"' " +
			"where TEMPLATE_TYPE='S' and EVENT_NAME='"+eventName+"' AND IFNULL(RECORD_STATUS,'N')='P'";			
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
		
	}



	@Override
	public ArrayList getSmsEventList() 
	{
		SmsVO vo=null;
		ArrayList resultList = new ArrayList();
		ArrayList queryList = new ArrayList();
		String fetchQry="select distinct EVENT_NAME from comm_event_list_m WHERE TEMPLATE_TYPE='S' and rec_status='A' ";
		logger.info("In getSmsEventList()  query   :   " + fetchQry);
		try 
		{
			queryList = ConnectionDAO.sqlSelect(fetchQry);
			int size = queryList.size();
			for (int i = 0; i < size; i++) 
			{
				vo=new SmsVO();
				ArrayList dataList = (ArrayList) queryList.get(i);
				if (dataList.size() > 0) 
				{
					vo.setEventName(CommonFunction.checkNull(dataList.get(0)).toString());
					resultList.add(vo);
				}
				vo=null;
				dataList.clear();
				dataList=null;
			}
		}
		catch (Exception e) 
		{e.printStackTrace();}
		finally
		{
			fetchQry=null;
			queryList.clear();
			queryList=null;
		}
		return resultList;
	}
//	Ashish
	public boolean updateSmsData(ArrayList<SmsVO> resultList, String eventName) 
	{
		SmsVO vo=null;
		PrepStmtObject insertPrepStmtObject;
		ArrayList qryList2= new ArrayList();
		String recordStatus="",deliveryStatus="";
		int referenceId=0,recordId=0;
		String phoneNumber="";
		String queryUpdate="";
		 boolean Stsus=false;
		
		for (int i = 0; i < resultList.size(); i++) 
		{
			insertPrepStmtObject= new PrepStmtObject();
			vo = (SmsVO) resultList.get(i);
			if (null != vo) 
			{
				recordStatus= vo.getRecordStatus();
				deliveryStatus=vo.getDeliveryStatus();
				referenceId=vo.getReferenceId();
				recordId=vo.getRecordId();
				phoneNumber=vo.getPhoneNumber();
			}
			queryUpdate="update comm_event_data set REFERENCE_ID='"+referenceId+"',RECORD_STATUS='"+recordStatus+"',DELIVERY_STATUS='" +deliveryStatus+"' "+
					"where TEMPLATE_TYPE='S' and EVENT_NAME='"+eventName+"' AND IFNULL(RECORD_STATUS,'N')='P' AND PHONE_NO='"+phoneNumber+"' AND RECORD_ID='"+recordId+"' ";	
			try
			{
				insertPrepStmtObject.setSql(queryUpdate.toString());
	            qryList2.add(insertPrepStmtObject);
	            logger.info("In updateSmsData::::::::::::");
	            logger.info("queryUpdate  : "+queryUpdate);
	            logger.info("recordStatus  : "+recordStatus);
	            logger.info("eventName  : "+eventName);
	            logger.info("phoneNumber  : "+phoneNumber);
	          Stsus=   ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList2);
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
		return Stsus;
	}
//	Ashish
	
	
	//Rohit Changes starts for sms...............
	
	//method starts for calling onclick sms & email from actions..........................................
	
	public boolean getEmailDetails(String dealId,String bDate,String EventName){
		SmsVO vo = new SmsVO();
	ArrayList list =new ArrayList();
	StringBuilder bufInsSql =new StringBuilder();
	ArrayList queryLIST=new ArrayList();
	ArrayList QueryList=new ArrayList();
	boolean status=false;
	boolean statusRO=false;
	boolean senddata=false;
	boolean senddataRO=false;	
	String DeviationStatsus = bDate;
	if(CommonFunction.checkNull(bDate).equalsIgnoreCase("A"))
		bDate = "Approved";
	if(CommonFunction.checkNull(bDate).equalsIgnoreCase("X"))
		bDate = "Rejected";
	
	String mesg=" ";
	ArrayList result = null;
	vo.setbDate(bDate);
	logger.info("Date: "+vo.getbDate());
	String productId = "";
	vo.setStage("ONCLICK");
	String CovDes = bDate;
	vo.setbDate(CommonFunction.checkNull(ConnectionDAO.singleReturn(" select date_format(PARAMETER_VALUE,'%d-%m-%Y') from parameter_mst where PARAMETER_KEY = 'BUSINESS_DATE' ")));
	bDate = vo.getbDate();
	try{
		vo.setEventName(EventName);
		vo.setbDate(bDate);
			
			if (CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("DEAL_LOGIN_EXTERNAL") 
					|| CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("DEAL_APPROVED_EXTERNAL")) {
				vo.setDealId(dealId);
				bufInsSql.append("select distinct b.DEAL_NO,c.customer_name,c.customer_email,d.PRIMARY_PHONE,round(e.deal_loan_amount),DATE_FORMAT(b.DEAL_FORWARDED_DATE,'%d-%m-%Y'),br.branch_desc,e.DEAL_PRODUCT from cr_deal_customer_role a ");
				bufInsSql.append("left Join cr_deal_dtl b on a.deal_id=b.DEAL_ID ");
				bufInsSql.append("left join cr_deal_customer_m c on a.deal_customer_id=c.CUSTOMER_ID ");
				bufInsSql.append("left join  cr_deal_address_m d on c.CUSTOMER_ID=d.BPID and d.COMMUNICATION_ADDRESS='Y'");
				bufInsSql.append("left join cr_deal_loan_dtl e on a.deal_id=e.DEAL_ID ");
				bufInsSql.append("left join sec_user_m s on b.maker_id=s.user_ID ");
				bufInsSql.append("left join com_branch_m  br on br.branch_id=b.deal_branch ");
				bufInsSql.append("where a.deal_id= '" + dealId + "' and DEAL_CUSTOMER_ROLE_TYPE='PRAPPL' ");
				
				productId = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT DEAL_PRODUCT FROM CR_DEAL_LOAN_DTL WHERE DEAL_ID = '"+dealId+"'  "));
				
				logger.info("DEAL_LOGIN_EXTERNAL::::" + bufInsSql.toString());
			}
			if (CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("DEAL_REJECT_EXTERNAL")) {
				vo.setDealId(dealId);
				
				int count = Integer.parseInt(ConnectionDAO.singleReturn("SELECT COUNT(1) FROM comm_event_data WHERE deal_id= '" + dealId + "' "
						+ " AND event_name = 'DEAL_REJECT_EXTERNAL' and record_status = 'P' "));
				if(count==0){
				bufInsSql.append("select distinct IFNULL(DEAL_APPLICATION_FORM_NO,''),c.customer_name,c.customer_email,d.PRIMARY_PHONE,round(e.deal_loan_amount),DATE_FORMAT(b.DEAL_FORWARDED_DATE,'%d-%m-%Y'),br.branch_desc,e.DEAL_PRODUCT from cr_deal_customer_role a ");
				bufInsSql.append("left Join cr_deal_dtl b on a.deal_id=b.DEAL_ID ");
				bufInsSql.append("left join cr_deal_customer_m c on a.deal_customer_id=c.CUSTOMER_ID ");
				bufInsSql.append("left join  cr_deal_address_m d on c.CUSTOMER_ID=d.BPID and d.COMMUNICATION_ADDRESS='Y'");
				bufInsSql.append("left join cr_deal_loan_dtl e on a.deal_id=e.DEAL_ID ");
				bufInsSql.append("left join sec_user_m s on b.maker_id=s.user_ID ");
				bufInsSql.append("left join com_branch_m  br on br.branch_id=b.deal_branch "
								+ " left join comm_event_data  comm on b.deal_id=comm.deal_id and event_name = 'DEAL_REJECT_EXTERNAL' and record_status <> 'P' ");
				bufInsSql.append("where a.deal_id= '" + dealId + "' and DEAL_CUSTOMER_ROLE_TYPE='PRAPPL' ");
				}
				
				productId = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT DEAL_PRODUCT FROM CR_DEAL_LOAN_DTL WHERE DEAL_ID = '"+dealId+"'  "));
				
				logger.info("DEAL_LOGIN_EXTERNAL::::" + bufInsSql.toString());
			}
			if (CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("DEAL_LOGIN_INTERNAL") || CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("UNDERWRITER_QUEUE")) {
				vo.setDealId(dealId);
				bufInsSql.append("select distinct b.DEAL_NO,c.customer_name,s.user_email,s.user_PHONE1,round(e.deal_loan_amount),DATE_FORMAT(B.DEAL_FORWARDED_DATE,'%d-%m-%Y'),br.branch_desc,e.DEAL_PRODUCT from cr_deal_customer_role a "); 
			    bufInsSql.append("left Join cr_deal_dtl b on a.deal_id=b.DEAL_ID  ");
				bufInsSql.append("left join cr_deal_customer_m c on a.deal_customer_id=c.CUSTOMER_ID ");
				bufInsSql.append("left join cr_deal_loan_dtl e on a.deal_id=e.DEAL_ID  ");
				bufInsSql.append("left join sec_user_m s on user_designation in ('ACM','ASM') and S.REC_STATUS='A' ");
				bufInsSql.append("join sec_user_branch_dtl sb on s.user_id=sb.user_id and sb.branch_id=b.deal_branch and sb.rec_status='A' ");
                bufInsSql.append("left join com_branch_m  br on br.branch_id=b.deal_branch ");
                bufInsSql.append("where a.deal_id= '" + dealId + "' and DEAL_CUSTOMER_ROLE_TYPE='PRAPPL' ");
                productId = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT DEAL_PRODUCT FROM CR_DEAL_LOAN_DTL WHERE DEAL_ID = '"+dealId+"'  "));
				logger.info("DEAL_LOGIN_INTERNAL::::" + bufInsSql.toString());
			}
			if (CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("DEAL_APPROVED_INTERNAL")) {
				vo.setDealId(dealId);
				bufInsSql.append("select distinct b.DEAL_NO,c.customer_name,s.user_email,s.user_PHONE1,round(e.deal_loan_amount),DATE_FORMAT(B.DEAL_FORWARDED_DATE,'%d-%m-%Y'),br.branch_desc,e.DEAL_PRODUCT from cr_deal_customer_role a "); 
			    bufInsSql.append("left Join cr_deal_dtl b on a.deal_id=b.DEAL_ID  ");
				bufInsSql.append("left join cr_deal_customer_m c on a.deal_customer_id=c.CUSTOMER_ID ");
				bufInsSql.append("left join cr_deal_loan_dtl e on a.deal_id=e.DEAL_ID  ");
				bufInsSql.append("left join sec_user_m s on user_designation in ('ACM','ASM') and S.REC_STATUS='A' ");
				bufInsSql.append("join sec_user_branch_dtl sb on s.user_id=sb.user_id and sb.branch_id=b.deal_branch and sb.rec_status='A' ");
                bufInsSql.append("left join com_branch_m  br on br.branch_id=b.deal_branch ");
                bufInsSql.append("where a.deal_id= '" + dealId + "' and DEAL_CUSTOMER_ROLE_TYPE='PRAPPL'  ");
                productId = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT DEAL_PRODUCT FROM CR_DEAL_LOAN_DTL WHERE DEAL_ID = '"+dealId+"'  "));
                
				logger.info("UNDERWRITER_QUEUE::::" + bufInsSql.toString());
			}
			if (CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("DEAL_REJECT")) {
				vo.setDealId(dealId);
				int count = Integer.parseInt(ConnectionDAO.singleReturn("SELECT COUNT(1) FROM comm_event_data WHERE deal_id= '" + dealId + "' "
						+ " AND event_name = 'DEAL_REJECT' and record_status = 'P' "));
				if(count==0){
					bufInsSql.append(" select distinct b.DEAL_NO,c.customer_name,s.user_email,s.user_PHONE1,round(e.deal_loan_amount),"
						+ " DATE_FORMAT(B.DEAL_FORWARDED_DATE,'%d-%m-%Y'),br.branch_desc,e.DEAL_PRODUCT "
						+ " from cr_deal_customer_role a "
						+ " left Join cr_deal_dtl b on a.deal_id=b.DEAL_ID "
						+ " left join cr_deal_customer_m c on a.deal_customer_id=c.CUSTOMER_ID "
						+ " left join cr_deal_loan_dtl e on a.deal_id=e.DEAL_ID "
						+ " left join sec_user_m s on b.deal_rm = s.user_id and S.REC_STATUS='A' "
						+ " join sec_user_branch_dtl sb on s.user_id=sb.user_id and sb.branch_id=b.deal_branch and sb.rec_status='A' "
						+ " left join com_branch_m  br on br.branch_id=b.deal_branch  "
						+ " where a.deal_id = '" + dealId + "' and DEAL_CUSTOMER_ROLE_TYPE='PRAPPL' group by a.deal_customer_id  "
						+ " union all ( select distinct b.DEAL_NO,c.customer_name,s.user_email,s.user_PHONE1,round(e.deal_loan_amount),"
						+ " DATE_FORMAT(B.DEAL_FORWARDED_DATE,'%d-%m-%Y'),br.branch_desc,e.DEAL_PRODUCT "
						+ " from cr_deal_customer_role a "
						+ " left Join cr_deal_dtl b on a.deal_id=b.DEAL_ID "
						+ " left join cr_deal_customer_m c on a.deal_customer_id=c.CUSTOMER_ID "
						+ " left join cr_deal_loan_dtl e on a.deal_id=e.DEAL_ID "
						+ " left join sec_user_m s on b.deal_ro = s.user_id and S.REC_STATUS='A' "
						+ " join sec_user_branch_dtl sb on s.user_id=sb.user_id and sb.branch_id=b.deal_branch and sb.rec_status='A' "
						+ " left join com_branch_m  br on br.branch_id=b.deal_branch "
						+ " where a.deal_id = '" + dealId + "' and DEAL_CUSTOMER_ROLE_TYPE='PRAPPL' group by a.deal_customer_id ) "); 
				}
                productId = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT DEAL_PRODUCT FROM CR_DEAL_LOAN_DTL WHERE DEAL_ID = '"+dealId+"'  "));
                
				logger.info("DEAL_REJECT::::" + bufInsSql.toString());
			}
			if (CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("DEAL_APPROVED_INTERNAL")) {
				vo.setDealId(dealId);
				bufInsSql.append("select distinct b.DEAL_NO,c.customer_name,s.user_email,s.user_PHONE1,round(e.deal_loan_amount),DATE_FORMAT(B.DEAL_FORWARDED_DATE,'%d-%m-%Y'),br.branch_desc,e.DEAL_PRODUCT from cr_deal_customer_role a "); 
			    bufInsSql.append("left Join cr_deal_dtl b on a.deal_id=b.DEAL_ID  ");
				bufInsSql.append("left join cr_deal_customer_m c on a.deal_customer_id=c.CUSTOMER_ID ");
				bufInsSql.append("left join cr_deal_loan_dtl e on a.deal_id=e.DEAL_ID  ");
				bufInsSql.append("left join sec_user_m s on user_designation in ('ACM','ASM') and S.REC_STATUS='A' ");
				bufInsSql.append("join sec_user_branch_dtl sb on s.user_id=sb.user_id and sb.branch_id=b.deal_branch and sb.rec_status='A' ");
                bufInsSql.append("left join com_branch_m  br on br.branch_id=b.deal_branch ");
                bufInsSql.append("where a.deal_id= '" + dealId + "' and DEAL_CUSTOMER_ROLE_TYPE='PRAPPL'; ");
                productId = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT DEAL_PRODUCT FROM CR_DEAL_LOAN_DTL WHERE DEAL_ID = '"+dealId+"'  "));
                
				logger.info("UNDERWRITER_QUEUE::::" + bufInsSql.toString());
			}
			if (CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("DEAL_SEND_BACK")) {
				vo.setDealId(dealId);
				bufInsSql.append("select distinct b.DEAL_NO,c.customer_name,s.user_email,s.user_PHONE1,round(e.deal_loan_amount),DATE_FORMAT(B.DEAL_FORWARDED_DATE,'%d-%m-%Y'),br.branch_desc,e.DEAL_PRODUCT from cr_deal_customer_role a "); 
			    bufInsSql.append("left Join cr_deal_dtl b on a.deal_id=b.DEAL_ID  ");
				bufInsSql.append("left join cr_deal_customer_m c on a.deal_customer_id=c.CUSTOMER_ID ");
				bufInsSql.append("left join cr_deal_loan_dtl e on a.deal_id=e.DEAL_ID  ");
				bufInsSql.append("left join sec_user_m s on (s.user_id=b.maker_id or user_designation in ('ACM','ASM')) and S.REC_STATUS='A' ");
				bufInsSql.append("join sec_user_branch_dtl sb on s.user_id=sb.user_id and sb.branch_id=b.deal_branch and sb.rec_status='A' ");
                bufInsSql.append("left join com_branch_m  br on br.branch_id=b.deal_branch ");
                bufInsSql.append("where a.deal_id= '" + dealId + "' and DEAL_CUSTOMER_ROLE_TYPE='PRAPPL'; ");
                productId = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT DEAL_PRODUCT FROM CR_DEAL_LOAN_DTL WHERE DEAL_ID = '"+dealId+"'  "));
				logger.info("UNDERWRITER_QUEUE::::" + bufInsSql.toString());
			}
			
			if(CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("Disbursement_INTERNAL"))
			{
				vo.setLoanId(dealId);	
				bufInsSql.append("select distinct a.Loan_NO,b.customer_name,s.user_email,s.user_PHONE1,round(disb.disbursal_amount),DATE_FORMAT(disb.DISBURSAL_DATE,'%d-%m-%Y'),br.branch_desc,a.LOAN_PRODUCT from cr_loan_dtl a ");
				bufInsSql.append("left join gcd_customer_m b on b.customer_id=a.loan_customer_id  ");
				bufInsSql.append("left join cr_deal_dtl c on a.loan_deal_id= c.deal_id ");
				bufInsSql.append("left join sec_user_m s on (user_designation in ('ACM','ASM')) and S.REC_STATUS='A' ");
				bufInsSql.append("join sec_user_branch_dtl sb on s.user_id=sb.user_id and sb.branch_id=a.loan_branch and sb.rec_status='A' ");
				bufInsSql.append("join cr_loan_disbursal_dtl disb on disb.loan_id=a.loan_id  and disb.DISBURSAL_NO= (select max(DISBURSAL_NO)  from cr_loan_disbursal_dtl where loan_id='"+dealId+"' ) ");
				 bufInsSql.append("left join com_branch_m  br on br.branch_id=a.LOAN_branch ");
				bufInsSql.append("where a.loan_id='"+dealId+"'   ");
				
				productId = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT LOAN_PRODUCT FROM CR_LOAN_DTL WHERE LOAN_ID = '"+dealId+"'  "));
				
				logger.info("Disbursement::::"+bufInsSql.toString());
			}
			
	// Changes by hina for Disbursement event				
		if(CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("Disbursement"))
		{
			vo.setLoanId(dealId);	
			bufInsSql.append("select distinct a.Loan_NO,b.customer_name,CUSTOMER_EMAIL,d.primary_phone,round(disb.disbursal_amount),DATE_FORMAT(disb.DISBURSAL_DATE,'%d-%m-%Y'),br.branch_desc,a.LOAN_PRODUCT from cr_loan_dtl a ");
			bufInsSql.append("left join gcd_customer_m b on b.customer_id=a.loan_customer_id  ");
			bufInsSql.append("left join cr_deal_dtl c on a.loan_deal_id= c.deal_id  left join com_address_m d on d.bpid= b.customer_id  and d.COMMUNICATION_ADDRESS='Y' ");
			bufInsSql.append("join cr_loan_disbursal_dtl disb on disb.loan_id=a.loan_id  and disb.DISBURSAL_NO= (select max(DISBURSAL_NO)  from cr_loan_disbursal_dtl where loan_id='"+dealId+"' ) ");
			 bufInsSql.append("left join com_branch_m  br on br.branch_id=a.LOAN_branch ");
			bufInsSql.append("where a.loan_id='"+dealId+"'   ");
			
			productId = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT LOAN_PRODUCT FROM CR_LOAN_DTL WHERE LOAN_ID = '"+dealId+"'  "));
			
			logger.info("Disbursement::::"+bufInsSql.toString());
		}
		
// Changes by hina for loan closure event
		if(CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("Loan_Closure1") || CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("Loan_Closure2"))
		{
			vo.setLoanId(dealId);	
			bufInsSql.append("select distinct a.Loan_NO,b.customer_name,B.CUSTOMER_EMAIL,d.primary_phone,round(a.LOAN_LOAN_AMOUNT),'',br.branch_desc,a.LOAN_PRODUCT from cr_loan_dtl a ");
			bufInsSql.append("left join gcd_customer_m b on b.customer_id=a.loan_customer_id  ");
			bufInsSql.append("left join cr_deal_dtl c on a.loan_deal_id= c.deal_id  left join com_address_m d on d.bpid= b.customer_id  and d.COMMUNICATION_ADDRESS='Y' ");
			bufInsSql.append("left join com_branch_m  br on br.branch_id=a.LOAN_branch ");
			bufInsSql.append("where a.loan_id='"+dealId+"' and a.rec_status='C' ");
			
			productId = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT LOAN_PRODUCT FROM CR_LOAN_DTL WHERE LOAN_ID = '"+dealId+"'  "));
			
			logger.info("Loan Closure::::"+bufInsSql.toString());
		} 
		if(CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("Loan_Closure_Internal") )
		{
			vo.setLoanId(dealId);	
			bufInsSql.append("select distinct a.Loan_NO,b.customer_name,s.user_EMAIL,s.user_phone1,round(a.LOAN_LOAN_AMOUNT),'',br.branch_desc,a.LOAN_PRODUCT from cr_loan_dtl a ");
			bufInsSql.append("left join gcd_customer_m b on b.customer_id=a.loan_customer_id  ");
			bufInsSql.append("left join cr_deal_dtl c on a.loan_deal_id= c.deal_id ");
			bufInsSql.append("left join sec_user_m s on (user_designation in ('ACM','ASM')) and S.REC_STATUS='A' ");
			bufInsSql.append("join sec_user_branch_dtl sb on s.user_id=sb.user_id and sb.branch_id=a.loan_branch and sb.rec_status='A' ");
			bufInsSql.append("left join com_branch_m  br on br.branch_id=a.LOAN_branch ");
			bufInsSql.append("where a.loan_id='"+dealId+"'  and a.rec_status='C' ");
			
			productId = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT LOAN_PRODUCT FROM CR_LOAN_DTL WHERE LOAN_ID = '"+dealId+"'  "));
			
			logger.info("Loan Closure::::"+bufInsSql.toString());
		} 
		
// Changes by hina for CHEQUE bounce & Realized event	
		if(CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("Cheque_Realized"))					
			{
			String dealQury="select TxnID from cr_instrument_dtl where INSTRUMENT_ID='"+dealId+"'";
				String loanId=ConnectionDAO.singleReturn(dealQury);

					vo.setLoanId(loanId);	
					bufInsSql.append("select distinct d.LOAN_NO,c.customer_name,c.customer_email,bp.primary_phone,round(ins.INSTRUMENT_AMOUNT),DATE_FORMAT(ins.INSTRUMENT_DATE,'%d-%m-%Y'),br.branch_desc,d.LOAN_PRODUCT "); 
					bufInsSql.append("from cr_loan_dtl d  ");
					bufInsSql.append("left join gcd_customer_m c on d.LOAN_CUSTOMER_ID=c.CUSTOMER_ID  ");
					bufInsSql.append("left join com_address_m bp on bp.BPID=c.CUSTOMER_ID  and bp.COMMUNICATION_ADDRESS='Y'  ");
					bufInsSql.append("join cr_instrument_dtl ins on ins.txnid=d.Loan_id and ins.INSTRUMENT_ID='"+dealId+"' and TXN_TYPE='LIM' "); 
					bufInsSql.append("join cr_pmnt_dtl pmnt on pmnt.INSTRUMENT_ID=ins.INSTRUMENT_ID ");
					bufInsSql.append("join cr_txnadvice_dtl txn on txn.TXNADVICE_ID=pmnt.TXNADVICEID and CHARGE_CODE_ID='7' ");
					bufInsSql.append("left join com_branch_m  br on br.branch_id=d.LOAN_branch "); 
					bufInsSql.append("where d.Loan_id='"+vo.getLoanId()+"' ");
					
					productId = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT LOAN_PRODUCT FROM CR_LOAN_DTL WHERE LOAN_ID = '"+loanId+"'  "));
					
					logger.info("ChequeRealized && ChequeBounce ::::"+bufInsSql.toString());
				}
		
		if(CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("Cheque_Bounce"))		
			{
			String dealQury="select TxnID from cr_instrument_dtl where INSTRUMENT_ID='"+dealId+"'";
				String loanId=ConnectionDAO.singleReturn(dealQury);
			
				
					vo.setLoanId(loanId);	
					bufInsSql.append("select distinct d.LOAN_NO,c.customer_name,c.customer_email,bp.primary_phone,round(ins.INSTRUMENT_AMOUNT),DATE_FORMAT(ins.INSTRUMENT_DATE,'%d-%m-%Y'),br.branch_desc,d.LOAN_PRODUCT ");
					bufInsSql.append(" from cr_loan_dtl d ");
					bufInsSql.append("left join gcd_customer_m c on d.LOAN_CUSTOMER_ID=c.CUSTOMER_ID ");
					bufInsSql.append("left join com_address_m bp on bp.BPID=c.CUSTOMER_ID  and bp.COMMUNICATION_ADDRESS='Y' ");
					bufInsSql.append("left join cr_instrument_dtl ins on ins.txnid=d.Loan_id and INSTRUMENT_ID='"+dealId+"' and TXN_TYPE='LIM' ");
					bufInsSql.append("left join com_branch_m  br on br.branch_id=d.LOAN_branch ");
					bufInsSql.append("where Loan_id='"+vo.getLoanId()+"' ");
					
					productId = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT LOAN_PRODUCT FROM CR_LOAN_DTL WHERE LOAN_ID = '"+loanId+"'  "));
					
					logger.info("ChequeRealized && ChequeBounce ::::"+bufInsSql.toString());
				}
					
// Changes by hina for Interest_Rate_Change event			
				if(CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("ON_SANCTION"))
				{
				vo.setLoanId(dealId);	
					bufInsSql.append("select distinct a.Loan_NO,b.customer_name,CUSTOMER_EMAIL,d.primary_phone,round(a.LOAN_LOAN_AMOUNT),'',br.branch_desc,a.LOAN_PRODUCT from cr_loan_dtl a ");
					bufInsSql.append("left join gcd_customer_m b on b.customer_id=a.loan_customer_id  ");
					bufInsSql.append("left join cr_deal_dtl c on a.loan_deal_id= c.deal_id  left join com_address_m d on d.bpid= b.customer_id and d.COMMUNICATION_ADDRESS='Y' ");
					bufInsSql.append("left join com_branch_m  br on br.branch_id=a.LOAN_branch ");
					bufInsSql.append("where a.loan_id='"+dealId+"' " );
					
					productId = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT LOAN_PRODUCT FROM CR_LOAN_DTL WHERE LOAN_ID = '"+dealId+"'  "));
					
					logger.info("ON_SANCTION::::"+bufInsSql.toString());
					}
// Changes by hina for ON_SANCTION event			
				if(CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("Interest_Rate_Change"))
				{
				vo.setLoanId(dealId);	
					bufInsSql.append("select distinct a.Loan_NO,b.customer_name,CUSTOMER_EMAIL,d.primary_phone,cr.new_rate,'',br.branch_desc,a.LOAN_PRODUCT from cr_loan_dtl a ");
					bufInsSql.append("left join gcd_customer_m b on b.customer_id=a.loan_customer_id  ");
					bufInsSql.append("left join cr_deal_dtl c on a.loan_deal_id= c.deal_id  left join com_address_m d on d.bpid= b.customer_id  and d.COMMUNICATION_ADDRESS='Y' left join cr_resch_dtl cr on cr.loan_id=a.loan_id ");
					bufInsSql.append("left join com_branch_m  br on br.branch_id=a.LOAN_branch ");
					bufInsSql.append("where a.loan_id='"+dealId+"'  ");
					
					productId = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT LOAN_PRODUCT FROM CR_LOAN_DTL WHERE LOAN_ID = '"+dealId+"'  "));
					
					logger.info("Interest_Rate_Change::::"+bufInsSql.toString());
					}		
				/*if(CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("Before_EMI_Presentat"))
				{
				vo.setLoanId(dealId);	
					bufInsSql.append("select distinct a.Loan_NO,cust.customer_name,cust.CUSTOMER_EMAIL,addr.primary_phone,round(rep.INSTL_AMOUNT,0),DATE_FORMAT(rep.INSTL_DATE,'%d-%m-%Y'),br.branch_desc,a.LOAN_PRODUCT  ");
					bufInsSql.append("from cr_loan_dtl a ");
					bufInsSql.append("join cr_pdc_instrument_dtl b on(b.PDC_LOAN_ID = a.LOAN_ID and b.PDC_PURPOSE = 'INSTALLMENT') ");
					bufInsSql.append("left join gcd_customer_m cust on(a.REC_STATUS='A' and a.DISBURSAL_STATUS='F' and cust.customer_id=a.loan_customer_id) ");
					bufInsSql.append("left join com_address_m addr ON(addr.BPID=cust.CUSTOMER_ID AND addr.COMMUNICATION_ADDRESS='Y') ");
					bufInsSql.append("join cr_repaysch_dtl rep on(rep.LOAN_ID=a.LOAN_ID and rep.INSTL_NO = b.PDC_INSTL_NO) ");
					bufInsSql.append("join com_bank_m bnk on(b.PDC_ISSUEING_BANK_ID=bnk.BANK_CODE) ");
					bufInsSql.append("left join com_branch_m  br on br.branch_id=a.LOAN_branch ");
					bufInsSql.append("where  (date_add(rep.instl_date,INTERVAL -4 DAY)='"+bDate+"'  OR date_add(rep.instl_date,INTERVAL -1 DAY)='"+bDate+"') ");
					logger.info("Interest_Rate_Change::::"+bufInsSql.toString());
					}	*/
				if(CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("Covenant_Condition_Forward_Deal"))
				{
				vo.setDealId(dealId);	
					bufInsSql.append("SELECT D.DEAL_NO,CONCAT(IFNULL(CUSTOMER_NAME,''),' ',IFNULL(CUSTOMER_FNAME,''),' ',IFNULL(CUSTOMER_MNAME,''),' ',"
										+ " IFNULL(CUSTOMER_LNAME,'')) AS CUSTOMER_NAME,USER.USER_EMAIL,USER.USER_PHONE1,ROUND(DL.DEAL_LOAN_AMOUNT,2),'',"
										+ " BRANCH_DESC,PRODUCT.PRODUCT_DESC,USER.USER_NAME "
										+ " FROM cr_covenant_conditions_dtl C "
										+ " JOIN CR_DEAL_DTL D ON C.DEAL_ID = D.DEAL_ID  "
										+ " JOIN CR_DEAL_LOAN_DTL DL ON D.DEAL_ID = DL.DEAL_ID "
										+ " JOIN COM_BRANCH_M BRANCH ON D.DEAL_BRANCH = BRANCH.BRANCH_ID "
										+ " JOIN CR_PRODUCT_M PRODUCT ON DL.DEAL_PRODUCT = PRODUCT.PRODUCT_ID "
										+ " JOIN CR_DEAL_CUSTOMER_M CUST ON C.CUSTOMER_ID = CUST.CUSTOMER_ID "
										+ " JOIN CR_COVENANT_USER_MAPPING COVUSER ON C.DEAL_ID = COVUSER.DEAL_ID "
										+ " JOIN SEC_USER_M USER ON COVUSER.USER_ID = USER.USER_ID "
										+ " WHERE C.DEAL_ID = '"+dealId+"' GROUP BY COVUSER.USER_ID, COVUSER.DEAL_ID ");
					
					productId = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT DEAL_PRODUCT FROM CR_DEAL_LOAN_DTL WHERE DEAL_ID = '"+dealId+"'  "));
					
					logger.info("Covenant_Condition_Forward_Deal::::"+bufInsSql.toString());
					}
				if(CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("Covenant_Condition_Author_deal"))
				{
				vo.setDealId(dealId);	
					bufInsSql.append("SELECT D.DEAL_NO,CONCAT(IFNULL(CUSTOMER_NAME,''),' ',IFNULL(CUSTOMER_FNAME,''),' ',IFNULL(CUSTOMER_MNAME,''),' ',"
										+ " IFNULL(CUSTOMER_LNAME,'')) AS CUSTOMER_NAME,USER.USER_EMAIL,USER.USER_PHONE1,ROUND(DL.DEAL_LOAN_AMOUNT,2),'',"
										+ " BRANCH_DESC,PRODUCT.PRODUCT_DESC,USER.USER_NAME "
										+ " FROM cr_covenant_conditions_dtl C "
										+ " JOIN CR_DEAL_DTL D ON C.DEAL_ID = D.DEAL_ID  "
										+ " JOIN CR_DEAL_LOAN_DTL DL ON D.DEAL_ID = DL.DEAL_ID "
										+ " JOIN COM_BRANCH_M BRANCH ON D.DEAL_BRANCH = BRANCH.BRANCH_ID "
										+ " JOIN CR_PRODUCT_M PRODUCT ON DL.DEAL_PRODUCT = PRODUCT.PRODUCT_ID "
										+ " JOIN CR_DEAL_CUSTOMER_M CUST ON C.CUSTOMER_ID = CUST.CUSTOMER_ID "
										+ " JOIN CR_COVENANT_USER_MAPPING COVUSER ON C.DEAL_ID = COVUSER.DEAL_ID "
										+ " JOIN SEC_USER_M USER ON COVUSER.USER_ID = USER.USER_ID "
										+ " WHERE C.DEAL_ID = '"+dealId+"' GROUP BY COVUSER.USER_ID, COVUSER.DEAL_ID");
					
					productId = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT DEAL_PRODUCT FROM CR_DEAL_LOAN_DTL WHERE DEAL_ID = '"+dealId+"'  "));
					
					logger.info("Covenant_Condition_Author_deal::::"+bufInsSql.toString());
					}
				if(CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("Covenant_Condition_Author_Loan"))
				{
				vo.setLoanId(dealId);	
					bufInsSql.append("SELECT L.LOAN_NO,CONCAT(IFNULL(CUSTOMER_NAME,''),' ',IFNULL(CUSTOMER_FNAME,''),' ',IFNULL(CUSTOMER_MNAME,''),' ',"
							+ " IFNULL(CUSTOMER_LNAME,'')) AS CUSTOMER_NAME,USER.USER_EMAIL,USER.USER_PHONE1,ROUND(L.LOAN_LOAN_AMOUNT,2),'',BRANCH_DESC,"
							+ " PRODUCT.PRODUCT_DESC,USER.USER_NAME "
							+ " FROM cr_covenant_conditions_dtl C "
							+ " JOIN CR_LOAN_DTL L ON C.LOAN_ID = L.LOAN_ID "
							+ " JOIN COM_BRANCH_M BRANCH ON L.LOAN_BRANCH = BRANCH.BRANCH_ID "
							+ " JOIN CR_PRODUCT_M PRODUCT ON L.LOAN_PRODUCT = PRODUCT.PRODUCT_ID "
							+ " JOIN GCD_CUSTOMER_M CUST ON C.CUSTOMER_ID = CUST.CUSTOMER_ID "
							+ " JOIN CR_COVENANT_USER_MAPPING COVUSER ON C.LOAN_ID = COVUSER.LOAN_ID "
							+ " JOIN SEC_USER_M USER ON COVUSER.USER_ID = USER.USER_ID "
							+ " WHERE C.LOAN_ID = '"+dealId+"' GROUP BY COVUSER.USER_ID, COVUSER.LOAN_ID ");
					
					productId = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT LOAN_PRODUCT FROM CR_LOAN_DTL WHERE LOAN_ID = '"+dealId+"'  "));
					
					logger.info("Covenant_Condition_Author_Loan::::"+bufInsSql.toString());
					}
				if(CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("Covenant_Condition_Forward_Loan"))
				{
				vo.setLoanId(dealId);	
					bufInsSql.append("SELECT L.LOAN_NO,CONCAT(IFNULL(CUSTOMER_NAME,''),' ',IFNULL(CUSTOMER_FNAME,''),' ',IFNULL(CUSTOMER_MNAME,''),' ',"
							+ " IFNULL(CUSTOMER_LNAME,'')) AS CUSTOMER_NAME,USER.USER_EMAIL,USER.USER_PHONE1,ROUND(L.LOAN_LOAN_AMOUNT,2),'',BRANCH_DESC,"
							+ " PRODUCT.PRODUCT_DESC,USER.USER_NAME "
							+ " FROM cr_covenant_conditions_dtl C "
							+ " JOIN CR_LOAN_DTL L ON C.LOAN_ID = L.LOAN_ID "
							+ " JOIN COM_BRANCH_M BRANCH ON L.LOAN_BRANCH = BRANCH.BRANCH_ID "
							+ " JOIN CR_PRODUCT_M PRODUCT ON L.LOAN_PRODUCT = PRODUCT.PRODUCT_ID "
							+ " JOIN GCD_CUSTOMER_M CUST ON C.CUSTOMER_ID = CUST.CUSTOMER_ID "
							+ " JOIN CR_COVENANT_USER_MAPPING COVUSER ON C.LOAN_ID = COVUSER.LOAN_ID "
							+ " JOIN SEC_USER_M USER ON COVUSER.USER_ID = USER.USER_ID "
							+ " WHERE C.LOAN_ID = '"+dealId+"' GROUP BY COVUSER.USER_ID, COVUSER.LOAN_ID ");
					
					productId = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT LOAN_PRODUCT FROM CR_LOAN_DTL WHERE LOAN_ID = '"+dealId+"'  "));
					
					logger.info("Covenant_Condition_Forward_Loan::::"+bufInsSql.toString());
					}
				
				if(CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("MANUAL_DEVIATION"))
				{
				vo.setDealId(dealId);	
					bufInsSql.append("SELECT DISTINCT D.DEAL_NO,C.CUSTOMER_NAME,T.USER_EMAIL,T.USER_PHONE1,T.USER_NAME,UA.LEVEL,B.BRANCH_DESC,N.DEAL_PRODUCT "
									+ " FROM CR_DEAL_DTL D  "
									+ " INNER JOIN CR_DEAL_LOAN_DTL N ON D.DEAL_ID=N.DEAL_ID "
									+ " INNER JOIN COM_BRANCH_M B ON D.DEAL_BRANCH=B.BRANCH_ID "
									+ " INNER JOIN CR_POLICY_DECISION MD  ON D.DEAL_ID=MD.DEAL_ID  AND  MD.REC_STATUS in ('P','I') "
									+ " INNER JOIN CR_USER_APPROVAL_M UA ON UA.USER_ROLE='P' AND MD.APPROVAL_LEVEL=UA.LEVEL AND UA.REC_STATUS='A' "
									+ " INNER JOIN SEC_USER_M T ON T.USER_ID=UA.USER_ID AND T.REC_STATUS = 'A' "
									+ " INNER JOIN SEC_USER_BRANCH_DTL A ON A.USER_ID= UA.USER_ID AND A.BRANCH_ID=D.DEAL_BRANCH "
									+ " INNER JOIN CR_DEAL_CUSTOMER_M C ON D.DEAL_CUSTOMER_ID = C.CUSTOMER_ID "
									+ " WHERE  D.DEAL_ID='"+dealId+"' GROUP BY LEVEL");
					
					productId = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT DEAL_PRODUCT FROM CR_DEAL_LOAN_DTL WHERE DEAL_ID = '"+dealId+"'  "));
					
					logger.info("Covenant_Condition_Author_Loan::::"+bufInsSql.toString());
					
					}
				if(CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("DEVIATION_APPROVAL"))
				{
				vo.setDealId(dealId);	
					bufInsSql.append("SELECT DISTINCT D.DEAL_NO,C.CUSTOMER_NAME,T.USER_EMAIL,T.USER_PHONE1,T.USER_NAME,UA.LEVEL,B.BRANCH_DESC,N.DEAL_PRODUCT "
									+ " FROM CR_DEAL_DTL D  "
									+ " INNER JOIN CR_DEAL_LOAN_DTL N ON D.DEAL_ID=N.DEAL_ID "
									+ " INNER JOIN COM_BRANCH_M B ON D.DEAL_BRANCH=B.BRANCH_ID "
									+ " INNER JOIN CR_POLICY_DECISION MD  ON D.DEAL_ID=MD.DEAL_ID  AND  MD.REC_STATUS = '"+CommonFunction.checkNull(DeviationStatsus)+"' " 
									+ " INNER JOIN CR_USER_APPROVAL_M UA ON UA.USER_ROLE='P' AND MD.APPROVAL_LEVEL=UA.LEVEL AND UA.REC_STATUS='A' "
									+ " INNER JOIN SEC_USER_M T ON T.USER_ID=UA.USER_ID AND T.REC_STATUS = 'A' "
									+ " INNER JOIN SEC_USER_BRANCH_DTL A ON A.USER_ID= UA.USER_ID AND A.BRANCH_ID=D.DEAL_BRANCH "
									+ " INNER JOIN CR_DEAL_CUSTOMER_M C ON D.DEAL_CUSTOMER_ID = C.CUSTOMER_ID "
									+ " WHERE  D.DEAL_ID='"+dealId+"' GROUP BY LEVEL,MD.REC_STATUS ");
					
					productId = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT DEAL_PRODUCT FROM CR_DEAL_LOAN_DTL WHERE DEAL_ID = '"+dealId+"'  "));
					
					logger.info("Deviation Approval::::"+bufInsSql.toString());
					
					}

					list= ConnectionDAO.sqlSelect(bufInsSql.toString());
			  for(int i=0;i<list.size();i++)
	  	    {	    	    
	  	    	ArrayList bdetails1=(ArrayList)list.get(i);
	  	    	if(bdetails1.size()>0)
	  			{
		  	    		vo.setApplicationNo((CommonFunction.checkNull(bdetails1.get(0))).trim());
		  	    		vo.setCustomerName((CommonFunction.checkNull(bdetails1.get(1))).trim());
		  	    		vo.setEmailId((CommonFunction.checkNull(bdetails1.get(2))).trim());
		  	    		vo.setMobileNo((CommonFunction.checkNull(bdetails1.get(3))).trim());
		  	    		vo.setLoanAmount((CommonFunction.checkNull(bdetails1.get(4))).trim());
		  	    		vo.setbDate((CommonFunction.checkNull(bdetails1.get(5))).trim());
		  	    		vo.setBranch((CommonFunction.checkNull(bdetails1.get(6))).trim());
		  	    		vo.setProduct((CommonFunction.checkNull(bdetails1.get(7))).trim());
		  	    		if(CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("Covenant_Condition_Forward_Deal") || 
		  	    				CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("Covenant_Condition_Author_deal") ||
		  	    				CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("Covenant_Condition_Author_Loan")||
		  	    				CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("Covenant_Condition_Forward_Loan"))
		  	    			vo.setUserName((CommonFunction.checkNull(bdetails1.get(8))).trim());
		  	    			
		  	    		
		  	    	//message making starts for as per event.....................................
		  	    		
		  	    		String query2="select MESSAGE from comm_sms_data where EVENT_NAME='"+EventName+"' and PRODUCT_ID = '"+productId+"' and REC_STATUS = 'A' ";
		  				String mail_text= CommonFunction.checkNull(ConnectionDAO.singleReturn(query2.toString()));
		  				
		  				String queryentity="select SMS_ENTITY_ID from comm_sms_data where EVENT_NAME='"+EventName+"' and PRODUCT_ID = '"+productId+"' and REC_STATUS = 'A' ";
		  				String entityId= CommonFunction.checkNull(ConnectionDAO.singleReturn(queryentity.toString()));
		  				vo.setEntityId(entityId);
		  				String queryTemplate="select TEMPLATE_ID from comm_sms_data where EVENT_NAME='"+EventName+"' and PRODUCT_ID = '"+productId+"' and REC_STATUS = 'A' ";
		  				String templateId= CommonFunction.checkNull(ConnectionDAO.singleReturn(queryTemplate.toString()));
		  				vo.setTemplateId(templateId);
		  				String MessageStatus = CommonFunction.checkNull(ConnectionDAO.singleReturn("select REC_STATUS from comm_sms_data where EVENT_NAME='"+EventName+"' and PRODUCT_ID = '"+productId+"' "));
		  	    		
		  				if(!CommonFunction.checkNull(mail_text).equalsIgnoreCase("") && CommonFunction.checkNull(MessageStatus).equalsIgnoreCase("A")){
		  					
		  					String temp1=mail_text.replaceAll("<<DEAL_NO>>", vo.getApplicationNo());
			  	    		String temp2=temp1.replaceAll("<<LOAN_NO>>", vo.getApplicationNo());
			  	    		String temp3=temp2.replaceAll("<<CUSTOMER_NAME>>", vo.getCustomerName());
			  	    		String temp4=temp3.replaceAll("<<DATE>>", vo.getbDate());
			  	    		String temp5=temp4.replaceAll("<<AMT>>", vo.getLoanAmount());
			  	    		String temp6=temp5.replaceAll("<<BRANCH>>", vo.getBranch());
			  	    		String temp7=temp6.replaceAll("<<PRODUCT>>", vo.getProduct());
			  	    		String temp8=temp7.replaceAll("<<USER_NAME>>", vo.getUserName());
			  	    		String temp9=temp8.replaceAll("<<DECISION>>", CovDes);
			  	    		mesg=temp9.replaceAll("<<EMI_DATE>>", vo.getbDate());
			  	    		
			  	    		
		  	    			vo.setMessage(mesg);
		  	    		  //message making end for as per event........................................
		  	    			
		  	    			vo.setbDate(bDate);
							logger.info("Date: "+vo.getbDate());
		  	    		//checking starts for email event................................................	
		  	    			String qury2="select REC_STATUS  from comm_event_list_m where EVENT_NAME='"+vo.getEventName()+"' and TEMPLATE_TYPE='E' ";
			  	    		String recemail=CommonFunction.checkNull( ConnectionDAO.singleReturn(qury2.toString()));
			  	    		if(recemail.equalsIgnoreCase("A"))
			  	    		{
		  	    			vo.setTemplate("E");
		  	    			status=InsertData(vo);//calling insert method into comm_event_data table 
		  	    			//boolean flag=sendEmail(vo);//calling email send method
		  	    			boolean flag = false;
		  	    			logger.info("email Send status:::::"+flag);
		  	    			if(flag)
		  	    			{
		  	    			senddata=new EmailDAOImpl().updateEmailFlag(dealId,vo.getEventName());
		  	    			}
		  	    			}
			  	    		else
			  	    		{
			  	    			logger.info("Email is not active on event: '"+vo.getEventName()+"'");
			  	    		}
			  	    	//email end.....................................................................
			  	    		
			  	    			
			  	    	//checking starts for sms event...................................... 	
			  	    		String qury1="select REC_STATUS  from comm_event_list_m where EVENT_NAME='"+vo.getEventName()+"' and TEMPLATE_TYPE='S' ";
			  	    		String recSMS=CommonFunction.checkNull( ConnectionDAO.singleReturn(qury1.toString()));
			  	    		if(recSMS.equalsIgnoreCase("A"))
			  	    		{
			  	    			vo.setTemplate("S");
			  	    			status=InsertData(vo);//calling insert method for insert into comm_event_data table 
			  	    			//senddata=	sendShreeSMS(vo);//calling shree sms send method
			  	    			senddata = false;
			  	    		logger.info("SMS Send status:::::"+senddata);
			  	    		
			  	    		}
			  	    		else
			  	    		{
			  	    			logger.info("SMS is not active on event: '"+vo.getEventName()+"'");
			  	    		}
		  					
		  				}
		  				
		  	    		
	  	    		
	  	    		//sms end............................................................
	  			 }
	  	    	   		
	  	    }
		
		}
	catch(Exception e){
		e.printStackTrace();
	}	
		return senddata;
	}
	public boolean getEmailDetailsTrigger(String dealId,String bDate,String EventName){
		SmsVO vo = new SmsVO();
	ArrayList list =new ArrayList();
	StringBuilder bufInsSql =new StringBuilder();
	StringBuilder bufInsSql1 =new StringBuilder();
	StringBuilder bufInsSqlEmail =new StringBuilder();
	StringBuilder bufInsSqlSms =new StringBuilder();
	ArrayList QueryListEmail=new ArrayList();
	ArrayList QueryListSms=new ArrayList();
	ArrayList QueryListSms1=new ArrayList();
	boolean status=false;
	boolean statusRO=false;
	boolean senddata=false;
	boolean senddataRO=false;	
	String productId = "";
	String mesg=" ";
	ArrayList result = new ArrayList();
	vo.setbDate(bDate);
	String subject="";
	vo.setStage("ONCLICK");
	boolean flag=false;
	boolean flag1=false;
	try{
		vo.setEventName(EventName);
		
			// String query2="select MESSAGE from comm_sms_data where EVENT_NAME='"+EventName+"'";
			//String mail_text= "";
		
	
			
			
			String qury="select EMAIL_SUBJECT from comm_event_list_m where EVENT_NAME='"+vo.getEventName()+"' and TEMPLATE_TYPE='E' and REC_STATUS='A' ";
			 subject=ConnectionDAO.singleReturn(qury.toString());
			String qury2="select count(1)  from comm_event_list_m where EVENT_NAME='"+vo.getEventName()+"' and TEMPLATE_TYPE='E' and REC_STATUS='A' ";
			String recemail=CommonFunction.checkNull( ConnectionDAO.singleReturn(qury2.toString()));
			
			String qury1="select count(1)  from comm_event_list_m where EVENT_NAME='"+vo.getEventName()+"' and TEMPLATE_TYPE='S'  and REC_STATUS='A' ";
	    		String recSMS=CommonFunction.checkNull( ConnectionDAO.singleReturn(qury1.toString()));
	
				if(CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("Before_EMI_Presentat") && !CommonFunction.checkNull(recemail).equalsIgnoreCase("0"))
				{
					result = new ArrayList();
				vo.setLoanId(dealId);	
					bufInsSql.append("INSERT INTO comm_event_data (EVENT_NAME,TEMPLATE_TYPE,LOAN_ID,CUSTOMER_NAME,EMAIL_ID,EMAIL_SUBJECT,PHONE_NO,MESSAGE,RECORD_DATE,PROCESS_DATE,RECORD_STATUS,SOURCE) ");
					bufInsSql.append("select distinct '"+EventName+"','E', a.Loan_id,cust.customer_name,cust.CUSTOMER_EMAIL,'"+subject+"',addr.primary_phone,");
					bufInsSql.append("CONCAT((select replace(replace(replace(replace(SMS.MESSAGE,'<<AMT>>',round(rep.INSTL_AMOUNT,0)),'<<LOAN_NO>>',a.LOAN_NO),'<<EMI_DATE>>',DATE_FORMAT(rep.INSTL_DATE,'%d-%m-%Y')),'<<PRODUCT>>',a.loan_product) ),'')SMS_MSG,  ");
					bufInsSql.append("DATE_ADD(STR_TO_DATE('"+bDate+"','%Y-%m-%d %H:%i'),INTERVAL CURTIME() HOUR_SECOND),NULL,'P','Omnifin' ");
					bufInsSql.append("from cr_loan_dtl a ");
					bufInsSql.append(" join gcd_customer_m cust on(a.REC_STATUS='A' and a.DISBURSAL_STATUS='F' and cust.customer_id=a.loan_customer_id) ");
					bufInsSql.append(" join com_address_m addr ON(addr.BPID=cust.CUSTOMER_ID AND addr.COMMUNICATION_ADDRESS='Y') ");
					bufInsSql.append("join cr_repaysch_dtl rep on(rep.LOAN_ID=a.LOAN_ID ) ");
					bufInsSql.append("left join com_branch_m  br on br.branch_id=a.LOAN_branch ");
					bufInsSql.append(" join COMM_SMS_DATA SMS ON (A.LOAN_PRODUCT = SMS.PRODUCT_ID AND SMS.EVENT_NAME = 'Before_EMI_Presentat' AND SMS.REC_STATUS = 'A') ");
					bufInsSql.append("where  (date_add(rep.instl_date,INTERVAL -4 DAY)='"+bDate+"'  OR date_add(rep.instl_date,INTERVAL -1 DAY)='"+bDate+"') ");
					logger.info("Before_EMI_Presentat::::"+bufInsSql.toString());
					result.add(bufInsSql.toString());
					 flag= ConnectionDAOforEJB.sqlInsUpdDelete(result);
					
					
					}	
				if(CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("Before_EMI_Presentat") && !CommonFunction.checkNull(recSMS).equalsIgnoreCase("0"))
				{
					result = new ArrayList();
				vo.setLoanId(dealId);	
					bufInsSql1.append("INSERT INTO comm_event_data (EVENT_NAME,TEMPLATE_TYPE,LOAN_ID,CUSTOMER_NAME,EMAIL_ID,EMAIL_SUBJECT,PHONE_NO,MESSAGE,RECORD_DATE,PROCESS_DATE,RECORD_STATUS,SOURCE,SMS_ENTITY_ID,TEMPLATE_ID) ");
					bufInsSql1.append("select distinct '"+EventName+"','S', a.Loan_id,cust.customer_name,cust.CUSTOMER_EMAIL,'"+subject+"',addr.primary_phone,");
					bufInsSql1.append("CONCAT((select replace(replace(replace(replace(SMS.MESSAGE,'<<AMT>>',round(rep.INSTL_AMOUNT,0)),'<<LOAN_NO>>',a.LOAN_NO),'<<EMI_DATE>>',DATE_FORMAT(rep.INSTL_DATE,'%d-%m-%Y')),'<<PRODUCT>>',a.loan_product) ),'')SMS_MSG,  ");
					bufInsSql1.append("DATE_ADD(STR_TO_DATE('"+bDate+"','%Y-%m-%d %H:%i'),INTERVAL CURTIME() HOUR_SECOND),NULL,'P','Omnifin',SMS.SMS_ENTITY_ID,SMS.TEMPLATE_ID ");
					bufInsSql1.append("from cr_loan_dtl a ");
					bufInsSql1.append(" join gcd_customer_m cust on(a.REC_STATUS='A' and a.DISBURSAL_STATUS='F' and cust.customer_id=a.loan_customer_id) ");
					bufInsSql1.append(" join com_address_m addr ON(addr.BPID=cust.CUSTOMER_ID AND addr.COMMUNICATION_ADDRESS='Y') ");
					bufInsSql1.append("join cr_repaysch_dtl rep on(rep.LOAN_ID=a.LOAN_ID ) ");
					bufInsSql1.append("left join com_branch_m  br on br.branch_id=a.LOAN_branch ");
					bufInsSql1.append(" join COMM_SMS_DATA SMS ON (A.LOAN_PRODUCT = SMS.PRODUCT_ID AND SMS.EVENT_NAME = 'Before_EMI_Presentat' AND SMS.REC_STATUS = 'A') ");
					bufInsSql1.append("where  (date_add(rep.instl_date,INTERVAL -4 DAY)='"+bDate+"'  OR date_add(rep.instl_date,INTERVAL -1 DAY)='"+bDate+"') ");
					logger.info("Before_EMI_Presentat::::"+bufInsSql1.toString());
					result.add(bufInsSql1.toString());
					 flag1= ConnectionDAOforEJB.sqlInsUpdDelete(result);
					
					}	
				
				if(CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("PENDING_PDDS") && !CommonFunction.checkNull(recemail).equalsIgnoreCase("0"))
				{
					result = new ArrayList();
				vo.setLoanId(dealId);	
				bufInsSql.append("INSERT INTO comm_event_data (EVENT_NAME,TEMPLATE_TYPE,DEAL_ID,LOAN_ID,CUSTOMER_NAME,EMAIL_ID,EMAIL_SUBJECT,PHONE_NO,MESSAGE,RECORD_DATE,PROCESS_DATE,RECORD_STATUS,SOURCE) ");
				bufInsSql.append("select '"+EventName+"','E',a.DEAL_ID,cld.LOAN_NO,cust.CUSTOMER_NAME,cust.CUSTOMER_EMAIL EMAIL_ID,'"+subject+"',addr.PRIMARY_PHONE, ");
				bufInsSql.append("	CONCAT((select replace(replace(SMS.MESSAGE,'<<LOAN_NO>>',cld.LOAN_NO),'<<PRODUCT>>',CLD.LOAN_product) ");  
				bufInsSql.append("	 ),'')SMS_MSG, ");
				bufInsSql.append("DATE_ADD(STR_TO_DATE('"+bDate+"','%Y-%m-%d %H:%i'),INTERVAL CURTIME() HOUR_SECOND),NULL,'P','Omnifin' ");
				bufInsSql.append("from cr_deal_dtl a ");
				bufInsSql.append("join sec_user_m um on um.user_id=a.deal_rm  ");
				bufInsSql.append("left join cr_deal_loan_dtl n on a.DEAL_ID=n.DEAL_ID  ");
				bufInsSql.append("left join cr_document_dtl_temp doc on doc.txnid=a.deal_id  ");
				bufInsSql.append("left join cr_deal_customer_m cust on cust.CUSTOMER_ID=a.DEAL_CUSTOMER_ID  ");
				bufInsSql.append("join cr_deal_address_m addr ON(addr.BPID=cust.CUSTOMER_ID AND addr.COMMUNICATION_ADDRESS='Y') ");
				bufInsSql.append("join cr_loan_dtl cld on cld.LOAN_DEAL_ID = a.deal_id ");
				bufInsSql.append(" join COMM_SMS_DATA SMS ON (n.DEAL_PRODUCT = SMS.PRODUCT_ID AND SMS.EVENT_NAME = 'PENDING_PDDS' AND SMS.REC_STATUS = 'A') ");
				bufInsSql.append("where date(doc.maker_date)='"+bDate+"' ");
				bufInsSql.append("and doc.DOC_STATUS='P' and doc.stage_id='POD' group by a.DEAL_ID ");
					logger.info("PENDING_PDDS::::"+bufInsSql.toString());
					result.add(bufInsSql.toString());
					 flag= ConnectionDAOforEJB.sqlInsUpdDelete(result);
					
					
					}	
				if(CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("PENDING_PDDS") && !CommonFunction.checkNull(recSMS).equalsIgnoreCase("0"))
				{
					result = new ArrayList();
				vo.setLoanId(dealId);	
				bufInsSql1.append("INSERT INTO comm_event_data (EVENT_NAME,TEMPLATE_TYPE,DEAL_ID,LOAN_ID,CUSTOMER_NAME,EMAIL_ID,EMAIL_SUBJECT,PHONE_NO,MESSAGE,RECORD_DATE,PROCESS_DATE,RECORD_STATUS,SOURCE,SMS_ENTITY_ID,TEMPLATE_ID) ");
				bufInsSql1.append("select '"+EventName+"','S',a.DEAL_ID,cld.LOAN_NO,cust.CUSTOMER_NAME,cust.CUSTOMER_EMAIL EMAIL_ID,'"+subject+"',addr.PRIMARY_PHONE, ");
				bufInsSql1.append("	CONCAT((select replace(replace(SMS.MESSAGE,'<<LOAN_NO>>',cld.LOAN_NO),'<<PRODUCT>>',CLD.LOAN_product) ");  
				bufInsSql1.append("	 ),'')SMS_MSG, ");
				bufInsSql1.append("DATE_ADD(STR_TO_DATE('"+bDate+"','%Y-%m-%d %H:%i'),INTERVAL CURTIME() HOUR_SECOND),NULL,'P','Omnifin',SMS.SMS_ENTITY_ID,SMS.TEMPLATE_ID ");
				bufInsSql1.append("from cr_deal_dtl a ");
				bufInsSql1.append("join sec_user_m um on um.user_id=a.deal_rm  ");
				bufInsSql1.append("left join cr_deal_loan_dtl n on a.DEAL_ID=n.DEAL_ID  ");
				bufInsSql1.append("left join cr_document_dtl_temp doc on doc.txnid=a.deal_id  ");
				bufInsSql1.append("left join cr_deal_customer_m cust on cust.CUSTOMER_ID=a.DEAL_CUSTOMER_ID  ");
				bufInsSql1.append("join cr_deal_address_m addr ON(addr.BPID=cust.CUSTOMER_ID AND addr.COMMUNICATION_ADDRESS='Y') ");
				bufInsSql1.append("join cr_loan_dtl cld on cld.LOAN_DEAL_ID = a.deal_id ");
				bufInsSql.append(" join COMM_SMS_DATA SMS ON (A.DEAL_PRODUCT = SMS.PRODUCT_ID AND SMS.EVENT_NAME = 'PENDING_PDDS' AND SMS.REC_STATUS = 'A') ");
				bufInsSql1.append("where date(doc.maker_date)='"+bDate+"' ");
				bufInsSql1.append("and doc.DOC_STATUS='P' and doc.stage_id='POD' group by a.DEAL_ID ");
				logger.info("PENDING_PDDS::::"+bufInsSql1.toString());
					result.add(bufInsSql1.toString());
					 flag1= ConnectionDAOforEJB.sqlInsUpdDelete(result);
					
					}	
			
				if ((CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("LOAN_REJECT_POSTDAY")) && (!CommonFunction.checkNull(recemail).equalsIgnoreCase("0")))
			      {
			        result = new ArrayList();
					
					String Day = CommonFunction.checkNull(ConnectionDAO.singleReturn("SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY = 'LOAN_REJECTION_COMMUNICATION_DAY'"));	
					bufInsSql.append("  INSERT INTO comm_event_data (EVENT_NAME,TEMPLATE_TYPE,LOAN_ID,CUSTOMER_NAME,EMAIL_ID,EMAIL_SUBJECT,PHONE_NO,MESSAGE,RECORD_DATE,PROCESS_DATE,RECORD_STATUS,SOURCE) "); 
					bufInsSql.append("  select distinct '"+EventName+"','E',a.LOAN_ID,cust.customer_name,cust.CUSTOMER_EMAIL,'"+subject+"',addr.primary_phone,    ");
					bufInsSql.append("  CONCAT((select replace(replace(replace(replace(replace(MESSAGE,'<<LOAN_NO>>',a.LOAN_NO),'<<AMT>>',round(a.loan_loan_amount)),'<<AMTINWORD>>',AmountToWords(round(a.loan_loan_amount))) ,'<<MOF>>',(SELECT ROUND(DEAL_CHARGE_FINAL_AMOUNT) FROM CR_TXNCHARGES_DTL WHERE TXN_ID=a.loan_id AND TXN_TYPE='LIM' AND DEAL_CHARGE_CODE=297)),'<<MOFINWORD>>',(SELECT AmountToWords(round(DEAL_CHARGE_FiNAL_AMOUNT)) FROM CR_TXNCHARGES_DTL WHERE TXN_ID=a.loan_id AND TXN_TYPE='LIM' AND DEAL_CHARGE_CODE=297)) from COMM_SMS_DATA where EVENT_NAME='"+EventName+"' and PRODUCT_ID=a.LOAN_PRODUCT AND REC_STATUS='A'   ),'')MESSAGE_BODY, "); 
					bufInsSql.append("  DATE_ADD(STR_TO_DATE('"+bDate+"','%Y-%m-%d %H:%i'),INTERVAL CURTIME() HOUR_SECOND),NULL,'P','Omnifin'  ");
					bufInsSql.append("  from cr_LOAN_dtl a   ");
					bufInsSql.append("  join GCD_customer_m cust on a.loan_customer_id=cust.customer_id "); 
					bufInsSql.append("  join com_address_m addr on addr.bpid=cust.customer_id AND addr.COMMUNICATION_ADDRESS='Y'  and BPTYPE='CS' "); 
					bufInsSql.append("  join COMM_SMS_DATA SMS ON (a.LOAN_PRODUCT = SMS.PRODUCT_ID AND SMS.EVENT_NAME = '"+EventName+"' AND SMS.REC_STATUS = 'A') ");  
					bufInsSql.append("  where   a.rec_status='X' AND  date_add(date(a.LOAN_APPROVAL_DATE), interval "+Day+" day) = (select parameter_value from parameter_mst where parameter_key = 'BUSINESS_DATE') and ifnull(cust.CUSTOMER_EMAIL,'')<>''  ");
			
		
					 result.add(bufInsSql.toString());
				        flag1 = ConnectionDAOforEJB.sqlInsUpdDelete(result);
		}
			      if ((CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("LOAN_REJECT_POSTDAY")) && (!CommonFunction.checkNull(recSMS).equalsIgnoreCase("0")))
			      {
			        result = new ArrayList();
			       
					String Day = CommonFunction.checkNull(ConnectionDAO.singleReturn("SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY = 'LOAN_REJECTION_COMMUNICATION_DAY'"));	
					bufInsSql1.append("  INSERT INTO comm_event_data (EVENT_NAME,TEMPLATE_TYPE,LOAN_ID,CUSTOMER_NAME,EMAIL_ID,EMAIL_SUBJECT,PHONE_NO,MESSAGE,RECORD_DATE,PROCESS_DATE,RECORD_STATUS,SOURCE,SMS_ENTITY_ID,TEMPLATE_ID) "); 
					bufInsSql1.append("  select distinct '"+EventName+"','S',a.LOAN_ID,cust.customer_name,cust.CUSTOMER_EMAIL,'"+subject+"',addr.primary_phone,    ");
					bufInsSql1.append("  CONCAT((select replace(replace(replace(replace(replace(MESSAGE,'<<LOAN_NO>>',a.LOAN_NO),'<<AMT>>',round(a.loan_loan_amount)),'<<AMTINWORD>>',AmountToWords(round(a.loan_loan_amount))) ,'<<MOF>>',(SELECT ROUND(DEAL_CHARGE_FINAL_AMOUNT) FROM CR_TXNCHARGES_DTL WHERE TXN_ID=a.loan_id AND TXN_TYPE='LIM' AND DEAL_CHARGE_CODE=297)),'<<MOFINWORD>>',(SELECT AmountToWords(round(DEAL_CHARGE_FiNAL_AMOUNT)) FROM CR_TXNCHARGES_DTL WHERE TXN_ID=a.loan_id AND TXN_TYPE='LIM' AND DEAL_CHARGE_CODE=297)) from COMM_SMS_DATA where EVENT_NAME='"+EventName+"' and PRODUCT_ID=a.LOAN_PRODUCT AND REC_STATUS='A'   ),'')MESSAGE_BODY, "); 
					bufInsSql1.append("  DATE_ADD(STR_TO_DATE('"+bDate+"','%Y-%m-%d %H:%i'),INTERVAL CURTIME() HOUR_SECOND),NULL,'P','Omnifin' ,SMS.SMS_ENTITY_ID,SMS.TEMPLATE_ID ");
					bufInsSql1.append("  from cr_LOAN_dtl a   ");
					bufInsSql1.append("  join GCD_customer_m cust on a.loan_customer_id=cust.customer_id "); 
					bufInsSql1.append("  join com_address_m addr on addr.bpid=cust.customer_id AND addr.COMMUNICATION_ADDRESS='Y'  and BPTYPE='CS' "); 
					bufInsSql1.append("  join COMM_SMS_DATA SMS ON (a.LOAN_PRODUCT = SMS.PRODUCT_ID AND SMS.EVENT_NAME = '"+EventName+"' AND SMS.REC_STATUS = 'A') ");  
					bufInsSql1.append("  where   a.rec_status='X' AND  date_add(date(a.LOAN_APPROVAL_DATE), interval "+Day+" day) = (select parameter_value from parameter_mst where parameter_key = 'BUSINESS_DATE') and addr.primary_phone is not null  ");
				       
					 result.add(bufInsSql1.toString());
				        flag1 = ConnectionDAOforEJB.sqlInsUpdDelete(result);
			      }
			      
			      if ((CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("LOAN_CANCELLATION")) && (!CommonFunction.checkNull(recemail).equalsIgnoreCase("0")))
			      {
			        result = new ArrayList();
			       
					String Day = CommonFunction.checkNull(ConnectionDAO.singleReturn("SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY = 'LOAN_CANCELLATION_COMMUNICATION_DAY'"));	
					bufInsSql.append("  INSERT INTO comm_event_data (EVENT_NAME,TEMPLATE_TYPE,LOAN_ID,CUSTOMER_NAME,EMAIL_ID,EMAIL_SUBJECT,PHONE_NO,MESSAGE,RECORD_DATE,PROCESS_DATE,RECORD_STATUS,SOURCE) "); 
					bufInsSql.append("  select distinct '"+EventName+"','E',a.LOAN_ID,cust.customer_name,cust.CUSTOMER_EMAIL,'"+subject+"',addr.primary_phone,    ");
					bufInsSql.append("  CONCAT((select replace(replace(replace(replace(replace(replace(replace(MESSAGE,'<<LOAN_NO>>',a.LOAN_NO),'<<AMT>>',round(a.loan_loan_amount)),'<<AMTINWORD>>',AmountToWords(round(a.loan_loan_amount))) ,'<<MOF>>',(SELECT ROUND(DEAL_CHARGE_FINAL_AMOUNT) FROM CR_TXNCHARGES_DTL WHERE TXN_ID=a.loan_id AND TXN_TYPE='LIM' AND DEAL_CHARGE_CODE=297)),'<<MOFINWORD>>',(SELECT AmountToWords(round(DEAL_CHARGE_FiNAL_AMOUNT)) FROM CR_TXNCHARGES_DTL WHERE TXN_ID=a.loan_id AND TXN_TYPE='LIM' AND DEAL_CHARGE_CODE=297)),'<<PROAMT>>',(SELECT ROUND(DEAL_CHARGE_FINAL_AMOUNT) FROM CR_TXNCHARGES_DTL WHERE TXN_ID=a.loan_id AND TXN_TYPE='LIM' AND DEAL_CHARGE_CODE=106)),'<<PROAMTINWORD>>',(SELECT AmountToWords(round(DEAL_CHARGE_FiNAL_AMOUNT)) FROM CR_TXNCHARGES_DTL WHERE TXN_ID=a.loan_id AND TXN_TYPE='LIM' AND DEAL_CHARGE_CODE=106)) from COMM_SMS_DATA where EVENT_NAME='"+EventName+"' and PRODUCT_ID=a.LOAN_PRODUCT AND REC_STATUS='A'   ),'')MESSAGE_BODY, "); 
					bufInsSql.append("  DATE_ADD(STR_TO_DATE('"+bDate+"','%Y-%m-%d %H:%i'),INTERVAL CURTIME() HOUR_SECOND),NULL,'P','Omnifin'  ");
					bufInsSql.append("  from cr_LOAN_dtl a   ");
					bufInsSql.append("  JOIN CR_TERMINATION_DTL t on t.loan_id=a.loan_id and t.TERMINATION_TYPE='X' ");
					bufInsSql.append("  join GCD_customer_m cust on a.loan_customer_id=cust.customer_id  ");
					bufInsSql.append("  join com_address_m addr on addr.bpid=cust.customer_id AND addr.COMMUNICATION_ADDRESS='Y'  and BPTYPE='CS' "); 
					bufInsSql.append("  join COMM_SMS_DATA SMS ON (a.LOAN_PRODUCT = SMS.PRODUCT_ID AND SMS.EVENT_NAME = '"+EventName+"' AND SMS.REC_STATUS = 'A') ");  
					bufInsSql.append("  where   a.rec_status='L' AND  date_add(date(t.TERMINATION_DATE), interval "+Day+" day) = (select parameter_value from parameter_mst where parameter_key = 'BUSINESS_DATE') and ifnull(cust.CUSTOMER_EMAIL,'')<>''  ");
				       
					
					 result.add(bufInsSql.toString());
				        flag1 = ConnectionDAOforEJB.sqlInsUpdDelete(result);
			      }
			      if ((CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("LOAN_CANCELLATION")) && (!CommonFunction.checkNull(recSMS).equalsIgnoreCase("0")))
			      {
			        result = new ArrayList();
			       
					String Day = CommonFunction.checkNull(ConnectionDAO.singleReturn("SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY = 'LOAN_CANCELLATION_COMMUNICATION_DAY'"));	
					bufInsSql1.append("  INSERT INTO comm_event_data (EVENT_NAME,TEMPLATE_TYPE,LOAN_ID,CUSTOMER_NAME,EMAIL_ID,EMAIL_SUBJECT,PHONE_NO,MESSAGE,RECORD_DATE,PROCESS_DATE,RECORD_STATUS,SOURCE,SMS_ENTITY_ID,TEMPLATE_ID) "); 
					bufInsSql1.append("  select distinct '"+EventName+"','S',a.LOAN_ID,cust.customer_name,cust.CUSTOMER_EMAIL,'"+subject+"',addr.primary_phone,    ");
					bufInsSql1.append("  CONCAT((select replace(replace(replace(replace(replace(replace(replace(MESSAGE,'<<LOAN_NO>>',a.LOAN_NO),'<<AMT>>',round(a.loan_loan_amount)),'<<AMTINWORD>>',AmountToWords(round(a.loan_loan_amount))) ,'<<MOF>>',(SELECT ROUND(DEAL_CHARGE_FINAL_AMOUNT) FROM CR_TXNCHARGES_DTL WHERE TXN_ID=a.loan_id AND TXN_TYPE='LIM' AND DEAL_CHARGE_CODE=297)),'<<MOFINWORD>>',(SELECT AmountToWords(round(DEAL_CHARGE_FiNAL_AMOUNT)) FROM CR_TXNCHARGES_DTL WHERE TXN_ID=a.loan_id AND TXN_TYPE='LIM' AND DEAL_CHARGE_CODE=297)),'<<PROAMT>>',(SELECT ROUND(DEAL_CHARGE_FINAL_AMOUNT) FROM CR_TXNCHARGES_DTL WHERE TXN_ID=a.loan_id AND TXN_TYPE='LIM' AND DEAL_CHARGE_CODE=106)),'<<PROAMTINWORD>>',(SELECT AmountToWords(round(DEAL_CHARGE_FiNAL_AMOUNT)) FROM CR_TXNCHARGES_DTL WHERE TXN_ID=a.loan_id AND TXN_TYPE='LIM' AND DEAL_CHARGE_CODE=106)) from COMM_SMS_DATA where EVENT_NAME='"+EventName+"' and PRODUCT_ID=a.LOAN_PRODUCT AND REC_STATUS='A'   ),'')MESSAGE_BODY, "); 
					bufInsSql1.append("  DATE_ADD(STR_TO_DATE('"+bDate+"','%Y-%m-%d %H:%i'),INTERVAL CURTIME() HOUR_SECOND),NULL,'P','Omnifin',SMS.SMS_ENTITY_ID,SMS.TEMPLATE_ID  ");
					bufInsSql1.append("  from cr_LOAN_dtl a   ");
					bufInsSql1.append("  JOIN CR_TERMINATION_DTL t on t.loan_id=a.loan_id and t.TERMINATION_TYPE='X' ");
					bufInsSql1.append("  join GCD_customer_m cust on a.loan_customer_id=cust.customer_id  ");
					bufInsSql1.append("  join com_address_m addr on addr.bpid=cust.customer_id AND addr.COMMUNICATION_ADDRESS='Y'  and BPTYPE='CS' "); 
					bufInsSql1.append("  join COMM_SMS_DATA SMS ON (a.LOAN_PRODUCT = SMS.PRODUCT_ID AND SMS.EVENT_NAME = '"+EventName+"' AND SMS.REC_STATUS = 'A') ");  
					bufInsSql1.append("  where   a.rec_status='L' AND  date_add(date(t.TERMINATION_DATE), interval "+Day+" day) = (select parameter_value from parameter_mst where parameter_key = 'BUSINESS_DATE') and ifnull(cust.CUSTOMER_EMAIL,'')<>''  ");
				       
					 result.add(bufInsSql1.toString());
				        flag1 = ConnectionDAOforEJB.sqlInsUpdDelete(result);
			      }
				 
					
			
		
		}
	catch(Exception e){
		e.printStackTrace();
	}	
		return senddata;
	}
	//method end for calling onclick sms & email from actions..........................................
	
	//Method starts for sending onclick email /////////////////////////////////////
	public boolean  sendEmail(SmsVO vo)
	{
		boolean flag =true;
		EmailVO emailVO = new EmailVO();
		
			String qury="select EMAIL_SUBJECT from comm_event_list_m where EVENT_NAME='"+vo.getEventName()+"' and TEMPLATE_TYPE='E' and REC_STATUS='A' ";
			String subject=ConnectionDAO.singleReturn(qury.toString());
			vo.setSubject(subject);
			EmailDAO dao=(EmailDAO)DaoImplInstanceFactory.getDaoImplInstance(EmailDAO.IDENTITY);
			emailVO=dao.getSmtpParameter();
			String finalData;
			try
			{
				SendMailUsingAuthentication mailObj=null;
				mailObj=new SendMailUsingAuthentication(emailVO.getSmtpHost(),emailVO.getSmtpMailAddress(),emailVO.getSmtpMailPassword(),emailVO.getPort());
				
					
					String[] emailListQry=new String[]{vo.getEmailId()};//only one record forever
					finalData=vo.getMessage();							
					mailObj.mailAuthenticationFunc(emailListQry ,subject, finalData, emailVO.getSmtpMailAddress());
						
			}
			catch(Exception e)
			{
				flag=false;
				e.printStackTrace();
			}
		return flag;
	}

	//Method end for sending onclick email/////////////////////////////////////	
	
	
	
	//Method starts for sending onclick sms from shreeSMS/////////////////////////////////////
	public boolean  sendShreeSMS(SmsVO vo)
	{
		String user="select parameter_value from parameter_mst where parameter_Key='mobile_UserId' ";
		String pass="select parameter_value from parameter_mst where parameter_Key='mobile_Password' ";
		String sender="select parameter_value from parameter_mst where parameter_Key='mobile_senderId' ";
		String	userId=CommonFunction.checkNull( ConnectionDAO.singleReturn(user.toString()));
		String	passWord=CommonFunction.checkNull(ConnectionDAO.singleReturn(pass.toString()));
		String	senderId=CommonFunction.checkNull(ConnectionDAO.singleReturn(sender.toString()));
		boolean flashSMS=false;
		String strResp ="";
		boolean flag=true;
		String templateId="";
		if(!CommonFunction.checkNull(vo.getTemplateId()).equalsIgnoreCase("")){
			templateId=vo.getTemplateId();
		}
		try{
		StringBuilder massage=new StringBuilder();
		massage.append("<?xml version='1.0' encoding='ISO-8859-1'?>");
		massage.append("<MESSAGE>"); 
		massage.append("<SMS TO='+91");
		massage.append(vo.getMobileNo()+"'  ");
		massage.append("TEXT='"+vo.getMessage()+"' TemplateId='"+templateId+"' />");
		massage.append("</MESSAGE>");
		String xmlmobileData=massage.toString();
		System.out.println(xmlmobileData);
		Service service = new Service();
		strResp = service.getServiceSoap().multySMSMultyNumbers(userId, passWord,flashSMS, senderId, xmlmobileData);
		logger.info("strResp:" +strResp);
			}
	catch(Exception e)
		{
		flag= false;
		e.printStackTrace();
		}
		return flag;
	}
	//Method starts for sending onclick sms from shreeSMS/////////////////////////////////////
	
	// method starts to insert the data for ONClick events in comm_event_data table......................
	public boolean  InsertData(SmsVO vo)
	{
		StringBuilder bufInsql = new StringBuilder();
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject =new PrepStmtObject();
		boolean	flag=false;
		try{	
			if(vo.getTemplate().equalsIgnoreCase("S"))
			{
			String tmp=	vo.getMessage();
			String tmp1=tmp.replaceAll("<p>", "");
  			String tmp2=tmp1.replaceAll("</p>","\n");
  			String tmp3=tmp2.replaceAll("<br>", ""); 
  			String tmp4=tmp3.replaceAll("<BR>", ""); 
  			vo.setMessage(tmp4);
			}
			else
			{
				String qury="select EMAIL_SUBJECT from comm_event_list_m where EVENT_NAME='"+vo.getEventName()+"' and TEMPLATE_TYPE='E' and REC_STATUS='A' ";
				String subject=ConnectionDAO.singleReturn(qury.toString());
				vo.setSubject(subject);
			}
			bufInsql.append("Insert into comm_event_data (EVENT_NAME,TEMPLATE_TYPE,LEAD_ID,DEAL_ID,LOAN_ID,CUSTOMER_NAME,PHONE_NO,EMAIL_ID,MESSAGE,EMAIL_SUBJECT,RECORD_DATE,PROCESS_DATE,RECORD_STATUS,DELIVERY_STATUS,SOURCE,SMS_ENTITY_ID,TEMPLATE_ID,TXN_ID) ");
			bufInsql.append("values ( ");
			bufInsql.append(" ?, ");//EVENT_NAME
			bufInsql.append(" ?, ");//TEMPLATE_TYPE
			bufInsql.append(" ?, ");//LEAD_ID
			bufInsql.append(" ?, ");//DEAL_ID
			bufInsql.append(" ?, ");//LOAN_ID
			bufInsql.append(" ?, ");//CUSTOMER_NAME
			bufInsql.append(" ?, ");//PHONE_NO
			bufInsql.append(" ?, ");//EMAIL_ID
			bufInsql.append(" ?, ");//MESSAGE
			bufInsql.append(" ?, ");//Email_Subject
			bufInsql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) , " ); //RECORD_DATE
			bufInsql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) , " ); //PROCESS_DATE
			bufInsql.append(" ?, ");//RECORD_STATUS
			bufInsql.append(" ?, ");//DELIVERY_STATUS
			bufInsql.append(" ?, ");//SOURCE
			bufInsql.append(" ?, ");//SMS_ENTITY_ID
			bufInsql.append(" ?, ");//TEMPLATE_ID
			bufInsql.append(" ? ) ");//TXN_ID
			
			if (CommonFunction.checkNull(vo.getEventName()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getEventName()).trim()));
			
			if (CommonFunction.checkNull(vo.getTemplate()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getTemplate()).trim()));
				
				if (CommonFunction.checkNull(vo.getLeadId()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getLeadId()).trim()));
				
				if (CommonFunction.checkNull(vo.getDealId()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getDealId()).trim()));
				
				if (CommonFunction.checkNull(vo.getLoanId()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getLoanId()).trim()));
				
				if (CommonFunction.checkNull(vo.getCustomerName()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getCustomerName()).trim()));
			
				
				if (CommonFunction.checkNull(vo.getMobileNo()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMobileNo()).trim()));
				
				if (CommonFunction.checkNull(vo.getEmailId()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getEmailId()).trim()));
					
				if (CommonFunction.checkNull(vo.getMessage()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMessage()).trim()));
				
				if(!vo.getTemplate().equalsIgnoreCase("S"))
				{
				if (CommonFunction.checkNull(vo.getSubject()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getSubject()).trim()));
				}
				else
					insertPrepStmtObject.addNull();
				if (CommonFunction.checkNull(vo.getbDate()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getbDate()).trim()));
				
				if (CommonFunction.checkNull(vo.getbDate()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getbDate()).trim()));
				
				if(vo.getTemplate().equalsIgnoreCase("S"))
					insertPrepStmtObject.addString("P");
				else
					insertPrepStmtObject.addString("P");
				
					insertPrepStmtObject.addString("P");
					
				if(CommonFunction.checkNull(vo.getStage()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else 
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getStage()).trim()));
				
				if(CommonFunction.checkNull(vo.getEntityId()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else 
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getEntityId()).trim()));
				
				if(CommonFunction.checkNull(vo.getTemplateId()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else 
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getTemplateId()).trim()));
					
				if(CommonFunction.checkNull(vo.getTxnId()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else 
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getTxnId()).trim()));
					
					insertPrepStmtObject.setSql(bufInsql.toString());
					logger.info("IN '"+vo.getEventName()+"' EMAIL EVENT insert query: "+insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);	
					
					flag=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
					logger.info("In '"+vo.getEventName()+"' EMAIL...............flag: "+flag);
			}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return flag;
	}
	
	// method end to insert the data for ONClick events in comm_event_data table......................
	//Rohit Changes end for sms................
	   public ArrayList DealApproval(String dealId, String bDate, String user, String UserName, String branch) {
	     SmsVO vo = new SmsVO();
	     ArrayList list = new ArrayList();
	     ArrayList headerlist = new ArrayList();
	     StringBuilder bufInsSql = new StringBuilder();
	     boolean status = false;
	     String productId = "";
	    boolean senddata = false;
	     try
	     {
	       String EventName = "UnderWriter";
	       vo.setEventName(EventName);
	       vo.setDealId(dealId);

	     bufInsSql.append("select distinct b.DEAL_NO,c.customer_name,round(e.deal_loan_amount),b.LEAD_ID,br.branch_desc from cr_deal_customer_role a ");
	       bufInsSql.append("left Join cr_deal_dtl b on a.deal_id=b.DEAL_ID ");
	       bufInsSql.append("left join cr_deal_customer_m c on a.deal_customer_id=c.CUSTOMER_ID ");
	      bufInsSql.append("left join  cr_deal_address_m d on c.CUSTOMER_ID=d.BPID ");
	       bufInsSql.append("left join cr_deal_loan_dtl e on a.deal_id=e.DEAL_ID ");
	       bufInsSql.append("left join com_branch_m  br on br.branch_id=b.deal_branch ");
	       bufInsSql.append("where a.deal_id= '"+dealId+"' and DEAL_CUSTOMER_ROLE_TYPE='PRAPPL'; ");

	       productId = CommonFunction.checkNull(ConnectionDAO.singleReturn(" SELECT DEAL_PRODUCT FROM CR_DEAL_LOAN_DTL WHERE DEAL_ID = '"+dealId+"'  "));

	       
	       String phone = "select user_phone1 from sec_user_m where user_name='"+user+"' and REC_STATUS='A'; ";
	       String email = "select user_email from sec_user_m where user_name='"+user+"' and REC_STATUS='A';";
	       String bran = "select BRANCH_DESC from com_branch_m where branch_id='"+branch+"'; ";
	       String phoneNo = CommonFunction.checkNull(ConnectionDAO.singleReturn(phone.toString()));
	       String EmailId = CommonFunction.checkNull(ConnectionDAO.singleReturn(email.toString()));
	       String branchName = CommonFunction.checkNull(ConnectionDAO.singleReturn(bran.toString()));
	       logger.info(new StringBuilder().append("query of phone:").append(phone.toString()).toString());
	       logger.info(new StringBuilder().append("query of email:").append(email).toString());
	       logger.info(new StringBuilder().append("query of branch:").append(bran).toString());

	       vo.setEmailId(EmailId);
	       vo.setMobileNo(phoneNo);
	       vo.setDealRo(user);
	 
	       logger.info(new StringBuilder().append("Underwriter::::").append(bufInsSql.toString()).toString());
	       list = ConnectionDAO.sqlSelect(bufInsSql.toString());

	       ArrayList bdetails1 = (ArrayList)list.get(0);
	      if (bdetails1.size() > 0)
	       {
	         vo.setApplicationNo(CommonFunction.checkNull(bdetails1.get(0)).trim());
	         vo.setCustomerName(CommonFunction.checkNull(bdetails1.get(1)).trim());
	         vo.setLoanAmount(CommonFunction.checkNull(bdetails1.get(2)).trim());
	         vo.setLeadId(CommonFunction.checkNull(bdetails1.get(3)).trim());
	         vo.setBranch(CommonFunction.checkNull(bdetails1.get(4)).trim());
	 
	         vo.setbDate(bDate);
	         vo.setStage("ONCLICK");
	 
	         String qury2 = "select REC_STATUS  from comm_event_list_m where EVENT_NAME='"+vo.getEventName()+"' and TEMPLATE_TYPE='E' ";
	         String recemail = CommonFunction.checkNull(ConnectionDAO.singleReturn(qury2.toString()));
	         if (recemail.equalsIgnoreCase("A"))
	        {
	          String query2="select MESSAGE from comm_sms_data where EVENT_NAME='"+EventName+"' and PRODUCT_ID = '"+productId+"' and REC_STATUS = 'A' ";
	          String mail_text= CommonFunction.checkNull(ConnectionDAO.singleReturn(query2.toString()));
	          
	      		String queryentity="select SMS_ENTITY_ID from comm_sms_data where EVENT_NAME='"+EventName+"' and PRODUCT_ID = '"+productId+"' and REC_STATUS = 'A' ";
				String entityId= CommonFunction.checkNull(ConnectionDAO.singleReturn(queryentity.toString()));
				vo.setEntityId(entityId);
				
				String queryTemplate="select TEMPLATE_ID from comm_sms_data where EVENT_NAME='"+EventName+"' and PRODUCT_ID = '"+productId+"' and REC_STATUS = 'A' ";
				String templateId= CommonFunction.checkNull(ConnectionDAO.singleReturn(queryTemplate.toString()));
				vo.setTemplateId(templateId);
				
	          String MessageStatus = CommonFunction.checkNull(ConnectionDAO.singleReturn("select REC_STATUS from comm_sms_data where EVENT_NAME='"+EventName+"' and PRODUCT_ID = '"+productId+"' "));
	        
	          if(!CommonFunction.checkNull(mail_text).equalsIgnoreCase("") && CommonFunction.checkNull(MessageStatus).equalsIgnoreCase("A")){
	        	  String temp1 = mail_text.replaceAll("<<DEAL_NO>>", vo.getApplicationNo());
		           String tmp2 = temp1.replaceAll("<<CUSTOMER_NAME>>", vo.getCustomerName());
		           String mesg = tmp2.replaceAll("<<BRANCH>>", vo.getBranch());
		 
		          vo.setMessage(mesg);
		           vo.setTemplate("E");
		           status = InsertData(vo);
		           //boolean flag = sendEmail(vo);
		         //  logger.info(new StringBuilder().append("email Send status:::::").append(flag).toString());
		         // senddata = new EmailDAOImpl().updateEmailFlag(dealId, vo.getEventName());
		         }
		         else
		         {
		           logger.info(new StringBuilder().append("Email is not active on event: '").append(vo.getEventName()).append("'").toString());
		         }

	          }

	         String qury1 = new StringBuilder().append("select REC_STATUS  from comm_event_list_m where EVENT_NAME='").append(vo.getEventName()).append("' and TEMPLATE_TYPE='S' ").toString();
	         String recSMS = CommonFunction.checkNull(ConnectionDAO.singleReturn(qury1.toString()));
	         if (recSMS.equalsIgnoreCase("A"))
	         {
	           
	           	  String query3="select MESSAGE from comm_sms_data where EVENT_NAME='"+EventName+"' and PRODUCT_ID = '"+productId+"' and REC_STATUS = 'A' ";
		          String mail_text1= CommonFunction.checkNull(ConnectionDAO.singleReturn(query3.toString()));
		          
		          String queryentity="select SMS_ENTITY_ID from comm_sms_data where EVENT_NAME='"+EventName+"' and PRODUCT_ID = '"+productId+"' and REC_STATUS = 'A' ";
					String entityId= CommonFunction.checkNull(ConnectionDAO.singleReturn(queryentity.toString()));
					vo.setEntityId(entityId);
					
					String queryTemplate="select TEMPLATE_ID from comm_sms_data where EVENT_NAME='"+EventName+"' and PRODUCT_ID = '"+productId+"' and REC_STATUS = 'A' ";
					String templateId= CommonFunction.checkNull(ConnectionDAO.singleReturn(queryTemplate.toString()));
					vo.setTemplateId(templateId);
					
		          String MessageStatus = CommonFunction.checkNull(ConnectionDAO.singleReturn("select REC_STATUS from comm_sms_data where EVENT_NAME='"+EventName+"' and PRODUCT_ID = '"+productId+"' "));
		        
		          if(!CommonFunction.checkNull(mail_text1).equalsIgnoreCase("") && CommonFunction.checkNull(MessageStatus).equalsIgnoreCase("A")){
		        	  String tmp1 = mail_text1.replaceAll("<<DEAL_NO>>", vo.getApplicationNo());
			           String tmp2 = tmp1.replaceAll("<p>Regards</p>", "");
			           String tmp3 = tmp2.replaceAll("<p><<USER_NAME>></p>", "");
			           String mesg1 = tmp3.replaceAll("<p><<BRANCH_NAME>></p>", "");

			          vo.setMessage(mesg1);
			           vo.setTemplate("S");
			           status = InsertData(vo);
			           // senddata = sendShreeSMS(vo);
			           logger.info(new StringBuilder().append("SMS Send status:::::").append(senddata).toString());
			         }
			         else
			         {
			           logger.info(new StringBuilder().append("SMS is not active on event: '").append(vo.getEventName()).append("'").toString());
			        }
			 
			       }
		          }
		        
		        	  
	           
	          
	 
	       list = null;
	       bdetails1 = null;
	       bufInsSql = null;
	       vo = null;
	     }
	     catch (Exception e) {
	       e.printStackTrace();
	     }
	 
	     return headerlist;
	  }
	   public ArrayList getUnderWriterDealDetail(String txnid, String userId)
	   {
	    ArrayList list = new ArrayList();
	     CommonDealVo fetchVo = new CommonDealVo();
	     try
	    {
	       ArrayList header = null;
	       int count = 0;
	       int startRecordIndex = 0;
	       int endRecordIndex = this.no;
	 
	       boolean appendSQL = false;
	       StringBuffer bufInsSql = new StringBuffer();
	 
	      bufInsSql.append(" SELECT Distinct USER_NAME FROM UNDER_WRITER_USERS_TEMP_QUEUE WHERE USER_ID='"+CommonFunction.checkNull(userId)+"' and DEAL_ID = '"+txnid+"'");
	       logger.info(new StringBuilder().append("query : ").append(bufInsSql.toString()).toString());
	      header = ConnectionDAO.sqlSelect(bufInsSql.toString());
	       bufInsSql = null;
	 
	       int size = header.size();
	 
	      for (int i = 0; i < size; i++)
	       {
	         ArrayList header1 = (ArrayList)header.get(i);
	         if ((header1 != null) && (header1.size() > 0))
	         {
	           fetchVo = new CommonDealVo();
	           fetchVo.setUserName(CommonFunction.checkNull(header1.get(0)).trim());
	           fetchVo.setTotalRecordSize(count);
	           list.add(fetchVo);
	        }
	      }
	 
	       fetchVo = null;
	     }
	     catch (Exception e)
	     {
	      e.printStackTrace();
	     }
	     return list;
	   }



	@Override
	public void proForSmsEmailBOD(String userId, String bDate) {
		boolean status=false;
		boolean flag=false;
		boolean flag1=false;
		try{
		String bDateQuery="SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY='BUSINESS_DATE'"; 
		bDate=ConnectionDAO.singleReturn(bDateQuery);
			String EventName="Before_EMI_Presentat";
			status=getEmailDetailsTrigger("",bDate,EventName);
			
			 EventName="PENDING_PDDS";
			 status=getEmailDetailsTrigger("",bDate,EventName);
			 
			 EventName="LOAN_REJECT_POSTDAY";
			 status=getEmailDetailsTrigger("",bDate,EventName);
			 
			 EventName="LOAN_CANCELLATION";
			 status=getEmailDetailsTrigger("",bDate,EventName);
			 
			/*flag=sendEmailTrigger();
			flag1=sendSMSTrigger();
			*/
			ArrayList QueryListSms1=new ArrayList();
			/*String updatequery="update comm_event_data set RECORD_STATUS='A'  ";
			logger.info("updatequery::"+updatequery);
			QueryListSms1.add(updatequery);
			boolean sms=ConnectionDAOforEJB.sqlInsUpdDelete(QueryListSms1);
			logger.info("update Result::"+sms);*/
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}



	private boolean sendEmailTrigger() {
		StringBuilder query = new StringBuilder();
		ArrayList list=new ArrayList();
		SmsVO vo=new SmsVO();
		boolean senddata=false;
		ArrayList QueryListSms=new ArrayList();
		ArrayList QueryListSms1=new ArrayList();
		try{
			
			query.append("select RECORD_ID,EMAIL_ID,MESSAGE from comm_event_data where EMAIL_ID is not null and TEMPLATE_TYPE='E' AND IFNULL(RECORD_STATUS,'N')='P'");
			logger.info("In getEmailList()  query   :   " + query.toString());
			list = ConnectionDAO.sqlSelect(query.toString());
			int size = list.size();
			for (int i = 0; i < size; i++) 
			{
				ArrayList dataList = (ArrayList) list.get(i);
				if (dataList.size() > 0) 
				{
					vo=new SmsVO();
					vo.setRecordId(Integer.parseInt(CommonFunction.checkNull(dataList.get(0))));
					vo.setEmailId(CommonFunction.checkNull(dataList.get(1)).toString());
					vo.setMessage(CommonFunction.checkNull(dataList.get(2)).toString());
					
					// senddata=sendEmail(vo);//calling email send method
	  	    		
					logger.info("SMS Send status:::::"+senddata);
	  	    		QueryListSms1=new ArrayList();
					//String updatequery="update comm_event_data set RECORD_STATUS='A' where RECORD_ID='"+vo.getRecordId()+"' ";
				//	logger.info("updatequery::"+updatequery);
					// QueryListSms1.add(updatequery);
					// boolean email=ConnectionDAOforEJB.sqlInsUpdDelete(QueryListSms1);		
				//	logger.info("update Result::"+email);
				}
			}
			
	}catch(Exception e){
		e.printStackTrace();
	}
		return senddata;
	}
	private boolean sendSMSTrigger() {
		StringBuilder query = new StringBuilder();
		ArrayList list=new ArrayList();
		SmsVO vo=new SmsVO();
		boolean senddata=false;
		ArrayList QueryListSms=new ArrayList();
		ArrayList QueryListSms1=new ArrayList();
		try{
			
			String qry="";
			 qry="select RECORD_ID,PHONE_NO,MESSAGE from comm_event_data " +
					"where TEMPLATE_TYPE='S' and PHONE_NO is not null and  IFNULL(RECORD_STATUS,'N')='P' ";
			
			logger.info("In getSmsData()  query   :   " + qry);
			QueryListSms = ConnectionDAO.sqlSelect(qry);
			int size = QueryListSms.size();
			for (int i = 0; i < size; i++) 
			{
				vo=new SmsVO();
				ArrayList dataList = (ArrayList) QueryListSms.get(i);
				if (dataList.size() > 0) 
				{
					vo.setRecordId(Integer.parseInt(CommonFunction.checkNull(dataList.get(0))));
					vo.setMobileNo(CommonFunction.checkNull(dataList.get(1)).toString());
					vo.setMessage(CommonFunction.checkNull(dataList.get(2)).toString());
					
					senddata=	sendShreeSMS(vo);//calling shree sms send method
	  	    		logger.info("SMS Send status:::::"+senddata);
	  	    		QueryListSms1=new ArrayList();
					String updatequery="update comm_event_data set RECORD_STATUS='A' where RECORD_ID='"+vo.getRecordId()+"' ";
					logger.info("updatequery::"+updatequery);
					QueryListSms1.add(updatequery);
					boolean sms=ConnectionDAOforEJB.sqlInsUpdDelete(QueryListSms1);
					logger.info("update Result::"+sms);
				}
				
			}
			
	}catch(Exception e){
		e.printStackTrace();
	}
		return senddata;
	}
	//Rohit Changes end for sms&email................
	public boolean  MultipleInToSendEmail(SmsVO vo,String ToEmailId,String CCEmailId)
	{
		boolean flag =true;
		EmailVO emailVO = new EmailVO();
			
			String qury="select EMAIL_SUBJECT from comm_event_list_m where EVENT_NAME='"+vo.getEventName()+"' and TEMPLATE_TYPE='E' and REC_STATUS='A' ";
			String subject=ConnectionDAO.singleReturn(qury.toString());
			vo.setSubject(subject);
			EmailDAO dao=(EmailDAO)DaoImplInstanceFactory.getDaoImplInstance(EmailDAO.IDENTITY);
			emailVO=dao.getSmtpParameter();
			String finalData;
			try
			{
				SendMailUsingAuthentication mailObj=null;
				mailObj=new SendMailUsingAuthentication(emailVO.getSmtpHost(),emailVO.getSmtpMailAddress(),emailVO.getSmtpMailPassword(),emailVO.getPort());
						
						String[] emailListQryTo=new String[]{ToEmailId};// Only For To Email
						String[] emailListQryCC=new String[]{CCEmailId};// Only For CC Email
						
						finalData=vo.getMessage();							
						mailObj.mailAuthenticationFuncCC(emailListQryTo,emailListQryCC ,subject, finalData, emailVO.getSmtpMailAddress());
						
			}
			catch(Exception e)
			{
				flag=false;
				e.printStackTrace();
			}
		return flag;
	}
//Shashank Starts Here EMAIL /SMS Verification
	public boolean getEmailVerificationDetails(String txnId,String bDate,String EventName,String table,String otp){
	SmsVO vo = new SmsVO();
ArrayList list =new ArrayList();
StringBuilder bufInsSql =new StringBuilder();
ArrayList queryLIST=new ArrayList();
ArrayList QueryList=new ArrayList();
boolean status=false;
boolean senddata=false;
String table2="";
if (CommonFunction.checkNull(table).trim().equalsIgnoreCase("cr_deal_customer_m")){
	table2 = "cr_deal_address_m";
}
else if (CommonFunction.checkNull(table).trim().equalsIgnoreCase("gcd_customer_m")){
	table2 = "com_address_m";
}
else if (CommonFunction.checkNull(table).trim().equalsIgnoreCase("gcd_customer_m_edit")){
	table2 = "com_address_m_edit";
}
else if (CommonFunction.checkNull(table).trim().equalsIgnoreCase("gcd_customer_m_temp")){
	table2 = "com_address_m_temp";
}
	
String mesg=" ";
ArrayList result = null;
vo.setbDate(bDate);
logger.info("Date: "+vo.getbDate());
String productId = "";
vo.setStage("ONCLICK");
String CovDes = bDate;
vo.setbDate(CommonFunction.checkNull(ConnectionDAO.singleReturn(" select date_format(PARAMETER_VALUE,'%d-%m-%Y') from parameter_mst where PARAMETER_KEY = 'BUSINESS_DATE' ")));
bDate = vo.getbDate();
try{
	vo.setEventName(EventName);
	vo.setbDate(bDate);
	String maxDealQuery="";
	String dealId="";
		if (CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("EMAIL_ID_VERIFICATION") ) {
			vo.setTxnId(txnId);
			bufInsSql.append("select distinct a.CUSTOMER_ID,a.customer_name,a.customer_email,d.PRIMARY_PHONE ");
			bufInsSql.append(" from " + table + " a ");
			bufInsSql.append("left join  " + table2 + " d on a.CUSTOMER_ID=d.BPID and d.COMMUNICATION_ADDRESS='Y'");
			bufInsSql.append("where a.CUSTOMER_ID= '" + txnId + "'  ");
			
			if (CommonFunction.checkNull(table).trim().equalsIgnoreCase("cr_deal_customer_m")){
				 maxDealQuery="select max(deal_id) from cr_deal_customer_role where deal_customer_id='" + txnId + "' ";
				 dealId=ConnectionDAO.singleReturn(maxDealQuery);
			}
			else if (CommonFunction.checkNull(table).trim().equalsIgnoreCase("gcd_customer_m")){
				maxDealQuery="select max(loan_id) from cr_loan_customer_role where gcd_id='" + txnId + "' ";
				String loanId=ConnectionDAO.singleReturn(maxDealQuery);
				if(CommonFunction.checkNull(loanId).equalsIgnoreCase("")){
					maxDealQuery="select max(deal_id) from cr_loan_customer_role where gcd_id='" + txnId + "' ";
					 dealId=ConnectionDAO.singleReturn(maxDealQuery);	
				}else{
					String dealQuery="select loan_deal_id from cr_loan_dtl where loan_id='"+loanId+"'";
					dealId=ConnectionDAO.singleReturn(dealQuery);
				}
			}
			else if (CommonFunction.checkNull(table).trim().equalsIgnoreCase("gcd_customer_m_edit")){
				maxDealQuery="select max(loan_id) from cr_loan_customer_role where gcd_id='" + txnId + "' ";
				String loanId=ConnectionDAO.singleReturn(maxDealQuery);
				if(CommonFunction.checkNull(loanId).equalsIgnoreCase("")){
					maxDealQuery="select max(deal_id) from cr_loan_customer_role where gcd_id='" + txnId + "' ";
					 dealId=ConnectionDAO.singleReturn(maxDealQuery);	
				}else{
					String dealQuery="select loan_deal_id from cr_loan_dtl where loan_id='"+loanId+"'";
					dealId=ConnectionDAO.singleReturn(dealQuery);
				}
			}
			else if (CommonFunction.checkNull(table).trim().equalsIgnoreCase("gcd_customer_m_temp")){
				maxDealQuery="select max(loan_id) from cr_loan_customer_role where gcd_id='" + txnId + "' ";
				String loanId=ConnectionDAO.singleReturn(maxDealQuery);
				if(CommonFunction.checkNull(loanId).equalsIgnoreCase("")){
					maxDealQuery="select max(deal_id) from cr_loan_customer_role where gcd_id='" + txnId + "' ";
					 dealId=ConnectionDAO.singleReturn(maxDealQuery);	
				}else{
					String dealQuery="select loan_deal_id from cr_loan_dtl where loan_id='"+loanId+"'";
					dealId=ConnectionDAO.singleReturn(dealQuery);
				}
			}
			
			
			logger.info("EMAIL_ID_VERIFICATION::::" + bufInsSql.toString());
		}
		if (CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("MOBILE_NO_VERIFICATION") ) {
			vo.setTxnId(txnId);
			bufInsSql.append("select distinct d.ADDRESS_ID,a.customer_name,a.customer_email,d.PRIMARY_PHONE ");
			bufInsSql.append(" from " + table + " a ");
			bufInsSql.append(" join  " + table2 + " d on a.CUSTOMER_ID=d.BPID ");
			bufInsSql.append("where d.address_id= '" + txnId + "'  ");
			
			logger.info("MOBILE_NO_VERIFICATION::::" + bufInsSql.toString());
			
			String customerId="";
			if (CommonFunction.checkNull(table).trim().equalsIgnoreCase("cr_deal_customer_m")){
				String custIdquery="select bpid from cr_deal_address_m where address_id='"+txnId+"'";
				customerId=ConnectionDAO.singleReturn(custIdquery);
				 maxDealQuery="select max(deal_id) from cr_deal_customer_role where deal_customer_id='" + customerId + "' ";
				 dealId=ConnectionDAO.singleReturn(maxDealQuery);
			}
			else if (CommonFunction.checkNull(table).trim().equalsIgnoreCase("gcd_customer_m")){
				String custIdquery="select bpid from com_address_m where address_id='"+txnId+"'";
				customerId=ConnectionDAO.singleReturn(custIdquery);
				maxDealQuery="select max(loan_id) from cr_loan_customer_role where gcd_id='" + customerId + "' ";
				String loanId=ConnectionDAO.singleReturn(maxDealQuery);
				if(CommonFunction.checkNull(loanId).equalsIgnoreCase("")){
					maxDealQuery="select max(deal_id) from cr_loan_customer_role where gcd_id='" + customerId + "' ";
					 dealId=ConnectionDAO.singleReturn(maxDealQuery);	
				}else{
					String dealQuery="select loan_deal_id from cr_loan_dtl where loan_id='"+loanId+"'";
					dealId=ConnectionDAO.singleReturn(dealQuery);
				}
			}
			else if (CommonFunction.checkNull(table).trim().equalsIgnoreCase("gcd_customer_m_edit")){
				String custIdquery="select bpid from com_address_m_edit where address_id='"+txnId+"'";
				customerId=ConnectionDAO.singleReturn(custIdquery);
				maxDealQuery="select max(loan_id) from cr_loan_customer_role where gcd_id='" + customerId + "' ";
				String loanId=ConnectionDAO.singleReturn(maxDealQuery);
				if(CommonFunction.checkNull(loanId).equalsIgnoreCase("")){
					maxDealQuery="select max(deal_id) from cr_loan_customer_role where gcd_id='" + customerId + "' ";
					 dealId=ConnectionDAO.singleReturn(maxDealQuery);	
				}else{
					String dealQuery="select loan_deal_id from cr_loan_dtl where loan_id='"+loanId+"'";
					dealId=ConnectionDAO.singleReturn(dealQuery);
				}
			}
			else if (CommonFunction.checkNull(table).trim().equalsIgnoreCase("gcd_customer_m_temp")){
				String custIdquery="select bpid from com_address_m_temp where address_id='"+txnId+"'";
				customerId=ConnectionDAO.singleReturn(custIdquery);
				maxDealQuery="select max(loan_id) from cr_loan_customer_role where gcd_id='" + customerId + "' ";
				String loanId=ConnectionDAO.singleReturn(maxDealQuery);
				if(CommonFunction.checkNull(loanId).equalsIgnoreCase("")){
					maxDealQuery="select max(deal_id) from cr_loan_customer_role where gcd_id='" + customerId + "' ";
					 dealId=ConnectionDAO.singleReturn(maxDealQuery);	
				}else{
					String dealQuery="select loan_deal_id from cr_loan_dtl where loan_id='"+loanId+"'";
					dealId=ConnectionDAO.singleReturn(dealQuery);
				}
			}
		}
		

				list= ConnectionDAO.sqlSelect(bufInsSql.toString());
		  for(int i=0;i<list.size();i++)
  	    {	    	    
  	    	ArrayList bdetails1=(ArrayList)list.get(i);
  	    	if(bdetails1.size()>0)
  			{
	  	    		vo.setTxnId((CommonFunction.checkNull(bdetails1.get(0))).trim());
	  	    		vo.setCustomerName((CommonFunction.checkNull(bdetails1.get(1))).trim());
	  	    		vo.setEmailId((CommonFunction.checkNull(bdetails1.get(2))).trim());
	  	    		vo.setMobileNo((CommonFunction.checkNull(bdetails1.get(3))).trim());
	  	    		
	  	    		 String secretKeyQuery="select PARAMETER_VALUE from parameter_mst where parameter_key='DMS_SECRET_KEY'";
	 				String secretKey=ConnectionDAO.singleReturn(secretKeyQuery);
	 				//String finalString=encrypt(encryptedString,secretKey);
	 				
	 				String URL="";
	 				if (CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("EMAIL_ID_VERIFICATION") ) {
	 					ChCrypto chCrypto= new ChCrypto();
	 					String encryptString=txnId+","+table;
	 					logger.info("encryptString: "+encryptString);
		 				String encryptedString=chCrypto.encrypt(encryptString, secretKey);
		 				logger.info("encryptedString: "+encryptedString);
	 				String verificationQuery="select concat(PARAMETER_VALUE,'"+encryptedString+"') from parameter_mst where parameter_key='EMAIL_VERIFICATION_URL'";
	 				logger.info("verificationQuery: "+verificationQuery);  
	 				URL=ConnectionDAO.singleReturn(verificationQuery);
	 				logger.info("Email: "+URL);
	 				}
	 				if (CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("MOBILE_NO_VERIFICATION") ) {
	 					ChCrypto chCrypto= new ChCrypto();
	 					
		 				  logger.info("In insert ::::");
		 			      StringBuffer bufInsSqlInsert = new StringBuffer();
		 			     PrepStmtObject insertPrepStmtObject =new PrepStmtObject();
		 			     ArrayList qryList=new ArrayList();
		 			    bufInsSqlInsert.append("insert into COMM_MOBILE_VERIFICATION_LINK_DTL(TXN_ID,TABLE_NAME,MOBILE_NO)");
		 			    bufInsSqlInsert.append(" values ( ");
		 			    bufInsSqlInsert.append(" '"+txnId+"',");
		 			    bufInsSqlInsert.append(" '"+table+"',");
		 			    bufInsSqlInsert.append(" '"+vo.getMobileNo()+"' )");

		 			      insertPrepStmtObject.setSql(bufInsSqlInsert.toString());
		 			      logger.info(new StringBuilder().append("IN COMM_MOBILE_VERIFICATION_LINK_DTL() insert query1 ### ").append(insertPrepStmtObject.printQuery()).toString());
		 			      bufInsSqlInsert = null;
		 			      qryList.add(insertPrepStmtObject);
		 			      boolean statusInsert = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		 			      logger.info(new StringBuilder().append("In COMM_MOBILE_VERIFICATION_LINK_DTL......................").append(statusInsert).toString());
		 			     String maxId="";
		 			      if (statusInsert)
		 			      {
		 			        StringBuilder query = new StringBuilder();
		 			        query.append("select max(ID) from COMM_MOBILE_VERIFICATION_LINK_DTL for update");
		 			         maxId = ConnectionDAO.singleReturn(query.toString());
		 			        logger.info(new StringBuilder().append("In maxId......................").append(maxId).toString());
		 			        query = null;
		 			      }
		 			     String encryptString="&Id="+maxId+"&mobileNo=";
		 				String verificationQuery="select concat(PARAMETER_VALUE,"+encryptString+","+vo.getMobileNo()+") from parameter_mst where parameter_key='MOBILE_VERIFICATION_URL'";
		 				logger.info("verificationQuery: "+verificationQuery); 
		 				URL=ConnectionDAO.singleReturn(verificationQuery);
		 				logger.info("Email: "+URL);
		 				
		 				}
	  	    	//message making starts for as per event.....................................
	  	    		
	  	    		String query2="select MESSAGE from comm_sms_data where EVENT_NAME='"+EventName+"' and REC_STATUS = 'A' ";
	  				String mail_text= CommonFunction.checkNull(ConnectionDAO.singleReturn(query2.toString()));
	  				
	  				String queryentity="select SMS_ENTITY_ID from comm_sms_data where EVENT_NAME='"+EventName+"' and REC_STATUS = 'A' limit 1 ";
	  				String entityId= CommonFunction.checkNull(ConnectionDAO.singleReturn(queryentity.toString()));
	  				vo.setEntityId(entityId);
	  				String queryTemplate="select TEMPLATE_ID from comm_sms_data where EVENT_NAME='"+EventName+"'  and REC_STATUS = 'A' limit 1";
	  				String templateId= CommonFunction.checkNull(ConnectionDAO.singleReturn(queryTemplate.toString()));
	  				vo.setTemplateId(templateId);
	  				
	  				String MessageStatus = CommonFunction.checkNull(ConnectionDAO.singleReturn("select REC_STATUS from comm_sms_data where EVENT_NAME='"+EventName+"'  "));
	  	    		
	  				if(!CommonFunction.checkNull(mail_text).equalsIgnoreCase("") && CommonFunction.checkNull(MessageStatus).equalsIgnoreCase("A")){
	  					String dealApplicationNo="";
	  					String dealRM="";
	  					String dealRMMobile="";
	  					if(!CommonFunction.checkNull(dealId).equalsIgnoreCase("")){
	  						String dealApplicationNoQuery="select DEAL_APPLICATION_FORM_NO from CR_DEAL_DTL where deal_id='"+dealId+"'";
	  						String dealRMQuery="select USER_NAME from CR_DEAL_DTL d join sec_user_m s on s.user_id=d.deal_RM where deal_id='"+dealId+"'";
	  						String dealRMMobileQuery="select USER_PHONE1 from CR_DEAL_DTL d join sec_user_m s on s.user_id=d.deal_RM where deal_id='"+dealId+"'";
	  						dealApplicationNo=ConnectionDAO.singleReturn(dealApplicationNoQuery);
	  						dealRM=ConnectionDAO.singleReturn(dealRMQuery);
	  						dealRMMobile=ConnectionDAO.singleReturn(dealRMMobileQuery);
	  					}
	  					String temp1=mail_text.replaceAll("<<DEAL_NO>>", dealApplicationNo);
		  	    		String temp2=temp1.replaceAll("<<RM_NAME>>", dealRM);
		  	    		String temp3=temp2.replaceAll("<<CUSTOMER_NAME>>", vo.getCustomerName());
		  	    		String temp4=temp3.replaceAll("<<DATE>>", vo.getbDate());
		  	    		String temp5=temp4.replaceAll("<<AMT>>", vo.getLoanAmount());
		  	    		String temp6=temp5.replaceAll("<<RM_MOBILE_NUMBER>>", dealRMMobile);
		  	    		String temp7=temp6.replaceAll("<<PRODUCT>>", vo.getProduct());
		  	    		String temp8=temp7.replaceAll("<<USER_NAME>>", vo.getUserName());
		  	    		String temp9=temp8.replaceAll("<<DECISION>>", CovDes);
		  	    		String temp10=temp9.replaceAll("<<EMI_DATE>>", vo.getbDate());
		  	    		String temp11=temp10.replaceAll("<<OTP>>", otp);
		  	    		mesg=temp11.replaceAll("<<LINK>>", URL);
		  	    		
		  	    		
	  	    			vo.setMessage(mesg);
	  	    		  //message making end for as per event........................................
	  	    			
	  	    			vo.setbDate(bDate);
						logger.info("Date: "+vo.getbDate());
	  	    		//checking starts for email event................................................	
	  	    			String qury2="select REC_STATUS  from comm_event_list_m where EVENT_NAME='"+vo.getEventName()+"' and TEMPLATE_TYPE='E' ";
		  	    		String recemail=CommonFunction.checkNull( ConnectionDAO.singleReturn(qury2.toString()));
		  	    		if(recemail.equalsIgnoreCase("A"))
		  	    		{
	  	    			vo.setTemplate("E");
	  	    			status=InsertData(vo);//calling insert method into comm_event_data table 
	  	    			//boolean flag=sendEmail(vo);//calling email send method
	  	    			boolean flag = false;
	  	    			logger.info("email Send status:::::"+flag);
	  	    			
	  	    			}
		  	    		else
		  	    		{
		  	    			logger.info("Email is not active on event: '"+vo.getEventName()+"'");
		  	    		}
		  	    	//email end.....................................................................
		  	    		
		  	    			
		  	    	//checking starts for sms event...................................... 	
		  	    		String qury1="select REC_STATUS  from comm_event_list_m where EVENT_NAME='"+vo.getEventName()+"' and TEMPLATE_TYPE='S' ";
		  	    		String recSMS=CommonFunction.checkNull( ConnectionDAO.singleReturn(qury1.toString()));
		  	    		if(recSMS.equalsIgnoreCase("A"))
		  	    		{
		  	    			vo.setTemplate("S");
		  	    			status=InsertData(vo);//calling insert method for insert into comm_event_data table 
		  	    			//senddata=	sendShreeSMS(vo);//calling shree sms send method
		  	    			senddata = false;
		  	    		logger.info("SMS Send status:::::"+senddata);
		  	    		
		  	    		}
		  	    		else
		  	    		{
		  	    			logger.info("SMS is not active on event: '"+vo.getEventName()+"'");
		  	    		}
	  					
	  				}
	  				
	  	    		
  	    		
  	    		//sms end............................................................
  			 }
  	    	   		
  	    }
	
	}
catch(Exception e){
	e.printStackTrace();
}	
	return status;
}
	public boolean getConfirmationVerificationDetails(String txnId,String bDate,String EventName,String table){
		SmsVO vo = new SmsVO();
	ArrayList list =new ArrayList();
	StringBuilder bufInsSql =new StringBuilder();
	ArrayList queryLIST=new ArrayList();
	ArrayList QueryList=new ArrayList();
	boolean status=false;
	boolean senddata=false;
	String table2="";
	if (CommonFunction.checkNull(table).trim().equalsIgnoreCase("cr_deal_customer_m")){
		table2 = "cr_deal_address_m";
	}
	else if (CommonFunction.checkNull(table).trim().equalsIgnoreCase("gcd_customer_m")){
		table2 = "com_address_m";
	}
	else if (CommonFunction.checkNull(table).trim().equalsIgnoreCase("gcd_customer_m_edit")){
		table2 = "com_address_m_edit";
	}
	else if (CommonFunction.checkNull(table).trim().equalsIgnoreCase("gcd_customer_m_temp")){
		table2 = "com_address_m_temp";
	}
		
	String mesg=" ";
	ArrayList result = null;
	vo.setbDate(bDate);
	logger.info("Date: "+vo.getbDate());
	String productId = "";
	vo.setStage("ONCLICK");
	String CovDes = bDate;
	vo.setbDate(CommonFunction.checkNull(ConnectionDAO.singleReturn(" select date_format(PARAMETER_VALUE,'%d-%m-%Y') from parameter_mst where PARAMETER_KEY = 'BUSINESS_DATE' ")));
	bDate = vo.getbDate();
	try{
		vo.setEventName(EventName);
		vo.setbDate(bDate);
			String maxDealQuery="";
			String dealId="";
			if (CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("EMAIL_ID_CONFIRMATION") ) {
				vo.setTxnId(txnId);
				bufInsSql.append("select distinct a.CUSTOMER_ID,a.customer_name,a.customer_email,d.PRIMARY_PHONE ");
				bufInsSql.append(" from " + table + " a ");
				bufInsSql.append("left join  " + table2 + " d on a.CUSTOMER_ID=d.BPID and d.COMMUNICATION_ADDRESS='Y'");
				bufInsSql.append("where a.CUSTOMER_ID= '" + txnId + "'  ");
				if (CommonFunction.checkNull(table).trim().equalsIgnoreCase("cr_deal_customer_m")){
					 maxDealQuery="select max(deal_id) from cr_deal_customer_role where deal_customer_id='" + txnId + "' ";
					 dealId=ConnectionDAO.singleReturn(maxDealQuery);
				}
				else if (CommonFunction.checkNull(table).trim().equalsIgnoreCase("gcd_customer_m")){
					maxDealQuery="select max(loan_id) from cr_loan_customer_role where gcd_id='" + txnId + "' ";
					String loanId=ConnectionDAO.singleReturn(maxDealQuery);
					if(CommonFunction.checkNull(loanId).equalsIgnoreCase("")){
						maxDealQuery="select max(deal_id) from cr_loan_customer_role where gcd_id='" + txnId + "' ";
						 dealId=ConnectionDAO.singleReturn(maxDealQuery);	
					}else{
						String dealQuery="select loan_deal_id from cr_loan_dtl where loan_id='"+loanId+"'";
						dealId=ConnectionDAO.singleReturn(dealQuery);
					}
				}
				else if (CommonFunction.checkNull(table).trim().equalsIgnoreCase("gcd_customer_m_edit")){
					maxDealQuery="select max(loan_id) from cr_loan_customer_role where gcd_id='" + txnId + "' ";
					String loanId=ConnectionDAO.singleReturn(maxDealQuery);
					if(CommonFunction.checkNull(loanId).equalsIgnoreCase("")){
						maxDealQuery="select max(deal_id) from cr_loan_customer_role where gcd_id='" + txnId + "' ";
						 dealId=ConnectionDAO.singleReturn(maxDealQuery);	
					}else{
						String dealQuery="select loan_deal_id from cr_loan_dtl where loan_id='"+loanId+"'";
						dealId=ConnectionDAO.singleReturn(dealQuery);
					}
				}
				else if (CommonFunction.checkNull(table).trim().equalsIgnoreCase("gcd_customer_m_temp")){
					maxDealQuery="select max(loan_id) from cr_loan_customer_role where gcd_id='" + txnId + "' ";
					String loanId=ConnectionDAO.singleReturn(maxDealQuery);
					if(CommonFunction.checkNull(loanId).equalsIgnoreCase("")){
						maxDealQuery="select max(deal_id) from cr_loan_customer_role where gcd_id='" + txnId + "' ";
						 dealId=ConnectionDAO.singleReturn(maxDealQuery);	
					}else{
						String dealQuery="select loan_deal_id from cr_loan_dtl where loan_id='"+loanId+"'";
						dealId=ConnectionDAO.singleReturn(dealQuery);
					}
				}
				logger.info("EMAIL_ID_VERIFICATION::::" + bufInsSql.toString());
			}
			if (CommonFunction.checkNull(EventName).trim().equalsIgnoreCase("MOBILE_NO_CONFIRMATION") ) {
				vo.setTxnId(txnId);
				bufInsSql.append("select distinct d.ADDRESS_ID,a.customer_name,a.customer_email,d.PRIMARY_PHONE ");
				bufInsSql.append(" from " + table + " a ");
				bufInsSql.append(" join  " + table2 + " d on a.CUSTOMER_ID=d.BPID ");
				bufInsSql.append("where d.address_id= '" + txnId + "'  ");
				
				logger.info("MOBILE_NO_VERIFICATION::::" + bufInsSql.toString());
				String customerId="";
				if (CommonFunction.checkNull(table).trim().equalsIgnoreCase("cr_deal_customer_m")){
					String custIdquery="select bpid from cr_deal_address_m where address_id='"+txnId+"'";
					customerId=ConnectionDAO.singleReturn(custIdquery);
					 maxDealQuery="select max(deal_id) from cr_deal_customer_role where deal_customer_id='" + customerId + "' ";
					 dealId=ConnectionDAO.singleReturn(maxDealQuery);
				}
				else if (CommonFunction.checkNull(table).trim().equalsIgnoreCase("gcd_customer_m")){
					String custIdquery="select bpid from com_address_m where address_id='"+txnId+"'";
					customerId=ConnectionDAO.singleReturn(custIdquery);
					maxDealQuery="select max(loan_id) from cr_loan_customer_role where gcd_id='" + customerId + "' ";
					String loanId=ConnectionDAO.singleReturn(maxDealQuery);
					if(CommonFunction.checkNull(loanId).equalsIgnoreCase("")){
						maxDealQuery="select max(deal_id) from cr_loan_customer_role where gcd_id='" + customerId + "' ";
						 dealId=ConnectionDAO.singleReturn(maxDealQuery);	
					}else{
						String dealQuery="select loan_deal_id from cr_loan_dtl where loan_id='"+loanId+"'";
						dealId=ConnectionDAO.singleReturn(dealQuery);
					}
				}
				else if (CommonFunction.checkNull(table).trim().equalsIgnoreCase("gcd_customer_m_edit")){
					String custIdquery="select bpid from com_address_m_edit where address_id='"+txnId+"'";
					customerId=ConnectionDAO.singleReturn(custIdquery);
					maxDealQuery="select max(loan_id) from cr_loan_customer_role where gcd_id='" + customerId + "' ";
					String loanId=ConnectionDAO.singleReturn(maxDealQuery);
					if(CommonFunction.checkNull(loanId).equalsIgnoreCase("")){
						maxDealQuery="select max(deal_id) from cr_loan_customer_role where gcd_id='" + customerId + "' ";
						 dealId=ConnectionDAO.singleReturn(maxDealQuery);	
					}else{
						String dealQuery="select loan_deal_id from cr_loan_dtl where loan_id='"+loanId+"'";
						dealId=ConnectionDAO.singleReturn(dealQuery);
					}
				}
				else if (CommonFunction.checkNull(table).trim().equalsIgnoreCase("gcd_customer_m_temp")){
					String custIdquery="select bpid from com_address_m_temp where address_id='"+txnId+"'";
					customerId=ConnectionDAO.singleReturn(custIdquery);
					maxDealQuery="select max(loan_id) from cr_loan_customer_role where gcd_id='" + customerId + "' ";
					String loanId=ConnectionDAO.singleReturn(maxDealQuery);
					if(CommonFunction.checkNull(loanId).equalsIgnoreCase("")){
						maxDealQuery="select max(deal_id) from cr_loan_customer_role where gcd_id='" + customerId + "' ";
						 dealId=ConnectionDAO.singleReturn(maxDealQuery);	
					}else{
						String dealQuery="select loan_deal_id from cr_loan_dtl where loan_id='"+loanId+"'";
						dealId=ConnectionDAO.singleReturn(dealQuery);
					}
				}
			}
			

					list= ConnectionDAO.sqlSelect(bufInsSql.toString());
			  for(int i=0;i<list.size();i++)
	  	    {	    	    
	  	    	ArrayList bdetails1=(ArrayList)list.get(i);
	  	    	if(bdetails1.size()>0)
	  			{
		  	    		vo.setTxnId((CommonFunction.checkNull(bdetails1.get(0))).trim());
		  	    		vo.setCustomerName((CommonFunction.checkNull(bdetails1.get(1))).trim());
		  	    		vo.setEmailId((CommonFunction.checkNull(bdetails1.get(2))).trim());
		  	    		vo.setMobileNo((CommonFunction.checkNull(bdetails1.get(3))).trim());
		  	    		
		  	    		
		  	    	//message making starts for as per event.....................................
		  	    		
		  	    		String query2="select MESSAGE from comm_sms_data where EVENT_NAME='"+EventName+"' and REC_STATUS = 'A' ";
		  				String mail_text= CommonFunction.checkNull(ConnectionDAO.singleReturn(query2.toString()));
		  				
		  				String queryentity="select SMS_ENTITY_ID from comm_sms_data where EVENT_NAME='"+EventName+"'  and REC_STATUS = 'A' ";
		  				String entityId= CommonFunction.checkNull(ConnectionDAO.singleReturn(queryentity.toString()));
		  				vo.setEntityId(entityId);
		  				String queryTemplate="select TEMPLATE_ID from comm_sms_data where EVENT_NAME='"+EventName+"' and REC_STATUS = 'A' ";
		  				String templateId= CommonFunction.checkNull(ConnectionDAO.singleReturn(queryTemplate.toString()));
		  				vo.setTemplateId(templateId);
		  				String MessageStatus = CommonFunction.checkNull(ConnectionDAO.singleReturn("select REC_STATUS from comm_sms_data where EVENT_NAME='"+EventName+"'  "));
		  	    		
		  				if(!CommonFunction.checkNull(mail_text).equalsIgnoreCase("") && CommonFunction.checkNull(MessageStatus).equalsIgnoreCase("A")){
		  					
		  					String dealApplicationNo="";
		  					String dealRM="";
		  					String dealRMMobile="";
		  					if(!CommonFunction.checkNull(dealId).equalsIgnoreCase("")){
		  						String dealApplicationNoQuery="select DEAL_APPLICATION_FORM_NO from CR_DEAL_DTL where deal_id='"+dealId+"'";
		  						String dealRMQuery="select USER_NAME from CR_DEAL_DTL d join sec_user_m s on s.user_id=d.deal_RM where deal_id='"+dealId+"'";
		  						String dealRMMobileQuery="select USER_PHONE1 from CR_DEAL_DTL d join sec_user_m s on s.user_id=d.deal_RM where deal_id='"+dealId+"'";
		  						dealApplicationNo=ConnectionDAO.singleReturn(dealApplicationNoQuery);
		  						dealRM=ConnectionDAO.singleReturn(dealRMQuery);
		  						dealRMMobile=ConnectionDAO.singleReturn(dealRMMobileQuery);
		  					}
		  					
		  					String temp1=mail_text.replaceAll("<<DEAL_NO>>", dealApplicationNo);
			  	    		String temp2=temp1.replaceAll("<<RM_NAME>>", dealRM);
			  	    		String temp3=temp2.replaceAll("<<CUSTOMER_NAME>>", vo.getCustomerName());
			  	    		String temp4=temp3.replaceAll("<<DATE>>", vo.getbDate());
			  	    		String temp5=temp4.replaceAll("<<RM_MOBILE_NUMBER>>", dealRMMobile);
			  	    		String temp6=temp5.replaceAll("<<EMAIL>>", vo.getEmailId());
			  	    		String temp7=temp6.replaceAll("<<MOBILE>>", vo.getMobileNo());
			  	    		String temp8=temp7.replaceAll("<<USER_NAME>>", vo.getUserName());
			  	    		String temp9=temp8.replaceAll("<<DECISION>>", CovDes);
			  	    		 mesg=temp9.replaceAll("<<EMI_DATE>>", vo.getbDate());
			  	    		
			  	    		
		  	    			vo.setMessage(mesg);
		  	    		  //message making end for as per event........................................
		  	    			
		  	    			vo.setbDate(bDate);
							logger.info("Date: "+vo.getbDate());
		  	    		//checking starts for email event................................................	
		  	    			String qury2="select REC_STATUS  from comm_event_list_m where EVENT_NAME='"+vo.getEventName()+"' and TEMPLATE_TYPE='E' ";
			  	    		String recemail=CommonFunction.checkNull( ConnectionDAO.singleReturn(qury2.toString()));
			  	    		if(recemail.equalsIgnoreCase("A"))
			  	    		{
		  	    			vo.setTemplate("E");
		  	    			status=InsertData(vo);//calling insert method into comm_event_data table 
		  	    			//boolean flag=sendEmail(vo);//calling email send method
		  	    			boolean flag = false;
		  	    			logger.info("email Send status:::::"+flag);
		  	    			
		  	    			}
			  	    		else
			  	    		{
			  	    			logger.info("Email is not active on event: '"+vo.getEventName()+"'");
			  	    		}
			  	    	//email end.....................................................................
			  	    		
			  	    			
			  	    	//checking starts for sms event...................................... 	
			  	    		String qury1="select REC_STATUS  from comm_event_list_m where EVENT_NAME='"+vo.getEventName()+"' and TEMPLATE_TYPE='S' ";
			  	    		String recSMS=CommonFunction.checkNull( ConnectionDAO.singleReturn(qury1.toString()));
			  	    		if(recSMS.equalsIgnoreCase("A"))
			  	    		{
			  	    			vo.setTemplate("S");
			  	    			status=InsertData(vo);//calling insert method for insert into comm_event_data table 
			  	    			//senddata=	sendShreeSMS(vo);//calling shree sms send method
			  	    			senddata = false;
			  	    		logger.info("SMS Send status:::::"+senddata);
			  	    		
			  	    		}
			  	    		else
			  	    		{
			  	    			logger.info("SMS is not active on event: '"+vo.getEventName()+"'");
			  	    		}
		  					
		  				}
		  				
		  	    		
	  	    		
	  	    		//sms end............................................................
	  			 }
	  	    	   		
	  	    }
		
		}
	catch(Exception e){
		e.printStackTrace();
	}	
		return status;
	}
	//Shashank Starts Ends EMAIL ?SMS Verification
	public boolean  sendSMSByShreeSmsURL(String mobileNo, String messageBody,String smsEventId,String tempalteId)
	{
		boolean flag=true;
		Session session=null;
		URL url;
		HttpURLConnection connection =null;
		try{
			
			String user="select parameter_value from parameter_mst where parameter_Key='mobile_UserId' ";
			String pass="select parameter_value from parameter_mst where parameter_Key='mobile_Password' ";
			String sender="select parameter_value from parameter_mst where parameter_Key='mobile_senderId' ";
			String urlBaseQuery="select parameter_value from parameter_mst where parameter_Key='ShreeSMSURL' ";
			String smsTypeQuery="select parameter_value from parameter_mst where parameter_Key='SMS_TYPE' ";
			String	userId=CommonFunction.checkNull( ConnectionDAO.singleReturn(user.toString()));
			String	password=CommonFunction.checkNull(ConnectionDAO.singleReturn(pass.toString()));
			String	senderId=CommonFunction.checkNull(ConnectionDAO.singleReturn(sender.toString()));
			String	urlBase=CommonFunction.checkNull(ConnectionDAO.singleReturn(urlBaseQuery.toString()));
			String	smsType=CommonFunction.checkNull(ConnectionDAO.singleReturn(smsTypeQuery.toString()));
			if(CommonFunction.checkNull(urlBase).equalsIgnoreCase("")){
				smsType="2";
			}
			if(CommonFunction.checkNull(urlBase).equalsIgnoreCase("")){
				urlBase="http://web.shreesms.net/API/SendSMS.aspx";
			}


			String param1AfterEncoding = URLEncoder.encode(messageBody, "UTF-8");
			String urlParameters = "?UserID="+userId+"&Password="+password+"&SenderID="+senderId
					+"&SMSType="+smsType+"&Mobile="+mobileNo+"&MsgText="+param1AfterEncoding+"&Entityid="+smsEventId+"&Templateid="+tempalteId;
			logger.info("urlParameters :"+urlParameters);
			String completeURL=urlBase.concat(urlParameters);
		
			
			logger.info("completeURL :"+completeURL);
			logger.info("param1AfterEncoding :"+param1AfterEncoding);
			url = new URL(completeURL);
			connection =(HttpURLConnection) url.openConnection();
			connection.connect();
			if (connection.getResponseCode() == 200) 
			{
				logger.info("Connection established!!");
			}else{
				flag= false;
				logger.info("connection.getResponseCode()::"+connection.getResponseCode());
				logger.info("No Connection");
			}
			
			
			connection.disconnect();
		}catch(Exception e){
		flag= false;
		e.printStackTrace();
		}finally{
			if(connection!=null){
			connection.disconnect();
			}
		}
		return flag;
	}
}