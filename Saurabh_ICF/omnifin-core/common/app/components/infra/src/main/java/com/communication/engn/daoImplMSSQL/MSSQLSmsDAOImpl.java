package com.communication.engn.daoImplMSSQL;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.communication.engn.dao.SmsDAO;
import com.communication.engn.vo.SmsVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;


public class MSSQLSmsDAOImpl implements SmsDAO {

	private static final Logger logger = Logger.getLogger(MSSQLSmsDAOImpl.class.getName());
	ResourceBundle resource = ResourceBundle
			.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
	String dateFormat = resource.getString("lbl.dateInDao");
	int no = Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	
	@Override
	public String callProcCommEngnProcess() 
	{
		logger.info("Inside callProcCommEngnProcess() Method ");		
		ArrayList<Object> in =new ArrayList<Object>();
		ArrayList<Object> out =new ArrayList<Object>();
		ArrayList outMessages = new ArrayList();
		String s1="";
		String s2="";
		try
		{
			in.add("OmniFin");
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
		return s1;
	}

	@Override
	public ArrayList<SmsVO> getSmsData(String qry) 
	{
		SmsVO vo=null;
		ArrayList list = new ArrayList();
		ArrayList<SmsVO> listString = new ArrayList<SmsVO>();
		try 
		{
			logger.info("In getSmsData()  query   :   " + qry);
			list = ConnectionDAO.sqlSelect(qry);
			int size = list.size();
			for (int i = 0; i < size; i++) 
			{
				vo=new SmsVO();
				ArrayList dataList = (ArrayList) list.get(i);
				if (dataList.size() > 0) 
				{					
					vo.setMessage(CommonFunction.checkNull(dataList.get(0))	.toString());					
					listString.add(vo);
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
			list.clear();
			list=null;
		}
		return listString;
	}

	@Override
	public void writeToFile(ArrayList<SmsVO> vo,String eventName) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		String currentDate=dateFormat.format(date);
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
				System.out.println("file exists"+file.exists());
				System.out.println("file exists abs"+file.getAbsoluteFile());
				
			for(int i=0;i<vo.size();i++){
			fw = new FileWriter(file.getAbsoluteFile(),true);
			bw = new BufferedWriter(fw);
			bw.write(vo.get(i).getMessage());
		    bw.newLine();
			bw.close();
		}
			System.out.println("Done");
							 

		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

	@Override
	public ArrayList getSmsEventList() 
	{
		SmsVO vo=null;
		ArrayList list = new ArrayList();
		String fetchQry="select EVENT_NAME,EVENT_QUERY from COMM_SMS_TEMPLATE WHERE REC_STATUS='A'";
		logger.info("In getSmsEventList()  query   :   " + fetchQry);
		try 
		{
			list = ConnectionDAO.sqlSelect(fetchQry);
		}
		catch (Exception e) 
		{e.printStackTrace();}
		finally
		{
			fetchQry=null;
		}
		return list;
	}

	@Override
	public void proForSmsEmailBOD(String userId, String bDate) {
		// TODO Auto-generated method stub
		
	}
	}
