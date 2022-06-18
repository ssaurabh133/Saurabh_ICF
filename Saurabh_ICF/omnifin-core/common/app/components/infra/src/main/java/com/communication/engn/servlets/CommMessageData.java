package com.communication.engn.servlets;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import com.communication.engn.dao.EmailDAO;
import com.communication.engn.dao.SmsDAO;
import com.communication.engn.daoImplMySql.SmsDAOImpl;
import com.communication.engn.utils.SendMailUsingAuthentication;
import com.communication.engn.vo.EmailVO;
import com.communication.engn.vo.SmsVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.login.dao.LoginDao;
import com.utils.Mail;


public class CommMessageData
{	
	public void startEmailTask(String path)
	{
		ArrayList smsEventList=new ArrayList();
		String status="S";
		SmsDAO smsDao=null;
		//Client smsClient=null;
		ArrayList smsDataList=null;
		ArrayList emailList=new ArrayList();
		ArrayList QueryListSms=new ArrayList();
		EmailVO vo = null;
		EmailVO emailVO = new EmailVO();
		SmsVO smsvo=null;
		System.out.println("Himanshu Testing Message Call. Current timestamp : " + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
		try
		{
			
			smsDao=(SmsDAO)DaoImplInstanceFactory.getDaoImplInstance(SmsDAO.IDENTITY);
			String q1="select ifnull(PARAMETER_VALUE,'Y') from parameter_mst where parameter_Key='METHOD_OF_SHREE_SMS'";
			String methodOfSMS=ConnectionDAO.singleReturn(q1);
			//status=smsDao.callProcCommEngnProcess();
			if (CommonFunction.checkNull(status).trim().equalsIgnoreCase("S"))
			{
				smsEventList=smsDao.getSmsEventList();
				for(int i=0;i<smsEventList.size();i++)
				{   
					smsvo=(SmsVO)smsEventList.get(i);
					String eventName=smsvo.getEventName();
					ArrayList<SmsVO> list=smsDao.getSmsData(eventName);
					for(int j=0;j<list.size();j++){
						smsvo=(SmsVO)list.get(j);
						smsvo.getRecordId();
						smsvo.getMessage();
						smsvo.getMobileNo();
						smsvo.getEntityId();
						smsvo.getTemplateId();
						smsvo.setStage("schedular");
						if(CommonFunction.checkNull(methodOfSMS).equalsIgnoreCase("Y")){
						new SmsDAOImpl().sendShreeSMS(smsvo);
						}else{
							new SmsDAOImpl().sendSMSByShreeSmsURL(smsvo.getMobileNo(), smsvo.getMessage(), smsvo.getEntityId(), smsvo.getTemplateId());	
						}
						QueryListSms=new ArrayList();
						String updatequery="update comm_event_data set RECORD_STATUS='A',RECORD_DATE=(select DATE_ADD(PARAMETER_VALUE,INTERVAL CURTIME() HOUR_SECOND) from parameter_mst where parameter_key='BUSINESS_DATE') where RECORD_ID='"+CommonFunction.checkNull(smsvo.getRecordId())+"' ";
						QueryListSms.add(updatequery);
						boolean sms=ConnectionDAO.sqlInsUpdDelete(QueryListSms);
				}	
				}
				//SMS Code starts	
				//Email Code starts    
				EmailDAO dao=(EmailDAO)DaoImplInstanceFactory.getDaoImplInstance(EmailDAO.IDENTITY);
				emailVO=dao.getSmtpParameter();
				String finalData;
				try
				{
					SendMailUsingAuthentication mailObj=null;
					mailObj=new SendMailUsingAuthentication(emailVO.getSmtpHost(),emailVO.getSmtpMailAddress(),emailVO.getSmtpMailPassword(),emailVO.getPort());
					emailList=dao.getEmailList();				        
					for(int i=0;i<emailList.size();i++)
					{
						vo=(EmailVO)emailList.get(i);
						String[] emailListQry=new String[]{vo.getCustomerEmail()};//only one record forever
						finalData=vo.getMassage();
						int recordId=vo.getRecordId();
						mailObj.mailAuthenticationFunc(emailListQry ,vo.getEmailSub(), finalData, emailVO.getSmtpMailAddress());
						QueryListSms=new ArrayList();
						String updatequery="update comm_event_data set RECORD_STATUS='A',RECORD_DATE=(select DATE_ADD(PARAMETER_VALUE,INTERVAL CURTIME() HOUR_SECOND) from parameter_mst where parameter_key='BUSINESS_DATE') where RECORD_ID='"+recordId+"' ";
						QueryListSms.add(updatequery);
						boolean sms=ConnectionDAO.sqlInsUpdDelete(QueryListSms);
					}
					
					 emailList = null;
					 emailList = new ArrayList();
					 emailList = dao.getEmailListRejection();
					 for(int i=0;i<emailList.size();i++)
						{
							vo=(EmailVO)emailList.get(i);
							String[] emailListQry=new String[]{vo.getCustomerEmail()};//only one record forever
							finalData=vo.getMassage();
							int recordId=vo.getRecordId();
							mailObj.mailAuthenticationFunc(emailListQry ,vo.getEmailSub(), finalData, emailVO.getSmtpMailAddress());
							QueryListSms=new ArrayList();
							String updatequery="update comm_event_data set RECORD_STATUS='A',RECORD_DATE=(select DATE_ADD(PARAMETER_VALUE,INTERVAL CURTIME() HOUR_SECOND) from parameter_mst where parameter_key='BUSINESS_DATE') where RECORD_ID='"+recordId+"' ";
							QueryListSms.add(updatequery);
							boolean sms=ConnectionDAO.sqlInsUpdDelete(QueryListSms);
						}
				}
				catch(Exception e)
				{e.printStackTrace();}
			}
			
		}
		catch(Exception e)
		{e.printStackTrace();}
		finally
		{
			smsEventList.clear();
			smsEventList=null;			
			status=null;
			smsDao=null;
			smsDataList=null;
			emailList.clear();
			emailList=null;			
			vo = null;
			emailVO=null;		
		}
	}	
}
	
	
	
	