package com.communication.engn.daoImplMSSQL;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.communication.engn.dao.EmailDAO;
import com.communication.engn.vo.EmailVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;

public class MSSQLEmailDAOImpl implements EmailDAO {

	private static final Logger logger = Logger.getLogger(MSSQLEmailDAOImpl.class.getName());
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
		 try 
		 {
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
	public EmailVO getSmtpParameter() {
		EmailVO vo=new EmailVO();
		ArrayList list = new ArrayList();
		String qrySmtpHostRes="";
		String qrySmtpAddRes="";
		String qrySmtpPsswdRes="";
		
		String qrySmtpHost="select parameter_value from parameter_mst where parameter_key='SMTP_HOST'";
		String qrySmtpAdd="select parameter_value from parameter_mst where  parameter_key='SMTP_MAIL_ADDRESS' ";
		String qrySmtpPsswd="select parameter_value from parameter_mst where  parameter_key='SMTP_MAIL_PASSWORD'";
		
			
			qrySmtpHostRes = ConnectionDAO.singleReturn(qrySmtpHost);
			qrySmtpAddRes=ConnectionDAO.singleReturn(qrySmtpAdd);
			qrySmtpPsswdRes=ConnectionDAO.singleReturn(qrySmtpPsswd);
			vo.setSmtpHost(qrySmtpHostRes);
			vo.setSmtpMailAddress(qrySmtpAddRes);
			vo.setSmtpMailPassword(qrySmtpPsswdRes);
			logger.info("In getSmtpParameter()$$$$$$$$$$$$$$$$$$$$$");
		return vo;
		
	}

	
	public ArrayList getEmailList() 
	{
		logger.info("In resultRelpaceDataQuery()");
		EmailVO vo=null;
		ArrayList list = new ArrayList();
		ArrayList resultList = new ArrayList();
		try 
		{
			StringBuilder query = new StringBuilder();
			query.append("select EVENT_NAME,CUSTOMER_NAME,EMAIL_ID,EMAIL_SUBJECT,EVENT_NO from comm_event_data where EMAIL_ID is not null and TEMPLATE_TYPE='E'");
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
					vo.setEventNo(CommonFunction.checkNull(dataList.get(4)).toString());
					resultList.add(vo);					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

	@Override
	public void updateEmailFlag() {
		// TODO Auto-generated method stub
		
	}

	public ArrayList getEmailListRejection()
	  {
		  //dummy method
		  
		return null;
	  }	

}
