package com.cm.daoImplMSSQL;


import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.connect.CommonFunction;
import com.connect.ConnectionReportDumpsDAO;
import com.connect.PrepStmtObject;
import com.cm.dao.DumpDownLoadDAO;
import com.cm.vo.DumpDownLoadVO;

public class MSSQLDumpDownLoadDAOImpl implements DumpDownLoadDAO
{
	private static final Logger logger = Logger.getLogger(MSSQLDumpDownLoadDAOImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
	String dateFormatWithTime =resource.getString("lbl.dateWithTimeInDao");
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");

	
	public ArrayList<DumpDownLoadVO> getGeneratFieldInformation(int recordID) 
	{
		logger.info("in getGeneratFieldInformation() of DumpDownLoadDAOImpl.");
		ArrayList searchlist=new ArrayList();
		StringBuilder bufInsSql =	new StringBuilder();
		ArrayList data= null;
		DumpDownLoadVO vo= null;
		
		ArrayList<DumpDownLoadVO> detailList=new ArrayList();
	    try{
			bufInsSql.append("select RECORD_ID,RECORD_DESC,PARAM_KEY_ONE,PARAM_VALUE_ONE,PARAM_KEY_TWO,PARAM_VALUE_TWO,AS_ON_DATE,SUB_QUERY1,SUB_QUERY2,SUB_QUERY3,SUB_QUERY4,DATE_RANGE from cr_dump_download_dtl where RECORD_ID="+recordID);
			logger.info("in getGeneratFieldInformation() of DumpDownLoadDAOImpl Query..."+bufInsSql.toString());
			searchlist = ConnectionReportDumpsDAO.sqlSelect(bufInsSql.toString());
			int size = searchlist.size();
            logger.info("size: "+size);
            for(int i=0;i<size;i++)
            {
               data=(ArrayList)searchlist.get(i);
               if(data.size()>0)
               {
            	 vo= new DumpDownLoadVO();
            	 vo.setLbxRecordID((CommonFunction.checkNull(data.get(0)).trim()));
            	 vo.setRecordDesc((CommonFunction.checkNull(data.get(1)).trim()));
            	 vo.setParamKeyOne((CommonFunction.checkNull(data.get(2)).trim()));
            	 vo.setParamValueOne((CommonFunction.checkNull(data.get(3)).trim()));
            	 vo.setParamKeyTwo((CommonFunction.checkNull(data.get(4)).trim()));
            	 vo.setParamValueTwo((CommonFunction.checkNull(data.get(5)).trim()));
            	 vo.setAsOnDate((CommonFunction.checkNull(data.get(6)).trim()));
            	 vo.setQuery1((CommonFunction.checkNull(data.get(7)).trim()));
            	 vo.setQuery2((CommonFunction.checkNull(data.get(8)).trim()));
            	 vo.setQuery3((CommonFunction.checkNull(data.get(9)).trim()));
            	 vo.setQuery4((CommonFunction.checkNull(data.get(10)).trim()));
            	 vo.setDateRange((CommonFunction.checkNull(data.get(11)).trim()));
            	 detailList.add(vo);
            }
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			searchlist= null;
			bufInsSql = null;
			data= null;
			vo= null;
		}
		return detailList;
	}
	 
	public ArrayList<Object> reportGenerator(DumpDownLoadVO vo) 
	{
		logger.info("in reportGenerator() of DumpDownLoadDAOImpl");
		ArrayList resultList=new ArrayList();
		String recordId=CommonFunction.checkNull(vo.getLbxRecordID()).trim();
		String paramValOne=CommonFunction.checkNull(vo.getParamValueOne()).trim();
		String paramValTwo=CommonFunction.checkNull(vo.getParamValueTwo()).trim();
		String fromDate=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getFromDate())); 
		String toDate=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getToDate())); 
		String asOnDate=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getAsOnDate())); 
		String query1=CommonFunction.checkNull(vo.getQuery1());
		String query2=CommonFunction.checkNull(vo.getQuery2());
		String query3=CommonFunction.checkNull(vo.getQuery3());
		String query4=CommonFunction.checkNull(vo.getQuery4());
		logger.info("reportGenerator()   recordId      :  "+recordId);
		logger.info("reportGenerator()   paramValOne   :  "+paramValOne);
		logger.info("reportGenerator()   paramValTwo   :  "+paramValTwo);
		logger.info("reportGenerator()   fromDate      :  "+fromDate);
		logger.info("reportGenerator()   toDate        :  "+toDate);
		logger.info("reportGenerator()   asOnDate        :  "+asOnDate);
		String query="select QUERY from cr_dump_download_dtl where RECORD_ID="+recordId;
		String finalQuery=ConnectionReportDumpsDAO.singleReturn(query);
		 // code added by aditi
		finalQuery=finalQuery.replace("#u",vo.getMaker_ID());
         // aditi space end
		finalQuery=finalQuery.replace("#p1",fromDate);
		finalQuery=finalQuery.replace("#p2",toDate);
		finalQuery=finalQuery.replace("#p3",asOnDate);
		finalQuery=finalQuery.replace("#p4",query1);
		finalQuery=finalQuery.replace("#p5",query2);
		finalQuery=finalQuery.replace("#p6",query3);
		finalQuery=finalQuery.replace("#p7",query4);
		if(!paramValOne.trim().equalsIgnoreCase(""))
		{
			String query12="select PARAM_VALUE_ONE from cr_dump_download_dtl where RECORD_ID="+recordId;
			String paramLabOne=ConnectionReportDumpsDAO.singleReturn(query12);
			finalQuery=finalQuery+" and "+paramLabOne+"='"+paramValOne+"'";
		}
		if(!paramValTwo.trim().equalsIgnoreCase(""))
		{
			String query12="select PARAM_VALUE_TWO from cr_dump_download_dtl where RECORD_ID="+recordId;
			String paramLabTwo=ConnectionReportDumpsDAO.singleReturn(query12);
			finalQuery=finalQuery+" and "+paramLabTwo+"='"+paramValTwo+"'";
		}
		finalQuery=finalQuery+")";
		logger.info("final query for going to execute  :  "+finalQuery);
		try
		{
			resultList = ConnectionReportDumpsDAO.sqlSelect(finalQuery);
		}
		catch(Exception e)
		{e.printStackTrace();}
		
//		IF I_PRM_VAL_1 <>'' THEN
//		select concat(@str,' and ',V_PARAM_VALUE_ONE,'=','\'',I_PRM_VAL_1,'\'') into @str;		
//	END IF;
//	IF I_PRM_VAL_2 <>'' THEN
//		select concat(@str,' and ',V_PARAM_VALUE_TWO,'=','\'',I_PRM_VAL_2,'\'') into @str;
		
		/*ArrayList result=new ArrayList();
		ArrayList<Object> in =new ArrayList<Object>();
		ArrayList<Object> out =new ArrayList<Object>();
		ArrayList outMessages = new ArrayList();
		String recordLocation="";
		String dbLocation="";
		String s1="";
		String s2="";
		String status="ok";
		try
		{
			in.add(vo.getLbxRecordID());
			String fDate=CommonFunction.changeFormat(vo.getFromDate()); 
			in.add(fDate);
			String tDate=CommonFunction.changeFormat(vo.getToDate()); 
			in.add(tDate);
			in.add(vo.getParamValueOne());
			in.add(vo.getParamValueTwo());
			in.add(vo.getMaker_ID());
			out.add(recordLocation);
			out.add(dbLocation);			
			out.add(s1);
			out.add(s2);

			
			logger.info("CR_Dump_Creation ("+in.toString()+","+out.toString()+")");
			outMessages=(ArrayList) ConnectionReportDumpsDAO.callSP("CR_Dump_Creation",in,out);
			recordLocation=CommonFunction.checkNull(outMessages.get(0));
			dbLocation=CommonFunction.checkNull(outMessages.get(1));
			s1=CommonFunction.checkNull(outMessages.get(2));
			s2=CommonFunction.checkNull(outMessages.get(3));
			logger.info("downloadPath  :     "+recordLocation);
			logger.info("dbLocation  :     "+dbLocation);
			logger.info("s1            :     "+s1);
        	logger.info("s2            :     "+s2);	
        }
		catch (Exception e) 
		{	status="Error";
			e.printStackTrace();
		}
		result.add(status);
		result.add(s1);
		result.add(s2);
		result.add(recordLocation);
		result.add(dbLocation);
		return result;*/
		return resultList;
	}
//	sidharth space
	public ArrayList<Object> reportAdHoc(DumpDownLoadVO vo) 
	{
		logger.info("in reportAdHoc() of DumpDownLoadDAOImpl");
		ArrayList resultList=new ArrayList();
		String query=CommonFunction.checkNull(vo.getQuery().trim());	
		logger.info(query);	
		
		try
		{
			resultList = ConnectionReportDumpsDAO.sqlColumnWithResult(query);
		}
		catch(Exception e)
		{e.printStackTrace();}
		logger.info("size::::::"+resultList.size());
		return resultList;
	}
	// SIDHARTH ENDS
	public ArrayList getReportList(String query) 
	{
		logger.info("in getReportList() of DumpDownLoadDAOImpl.");
		ArrayList searchlist=new ArrayList();
		StringBuilder bufInsSql =	new StringBuilder();
	   try
	   {
			bufInsSql.append(query);
			logger.info("in getGeneratFieldInformation() of DumpDownLoadDAOImpl Query..."+bufInsSql.toString());
			searchlist = ConnectionReportDumpsDAO.sqlSelect(bufInsSql.toString());
			int size = searchlist.size();
            logger.info("size: "+size);

		}catch(Exception e)	{
			e.printStackTrace();
		}
		finally
		{
			bufInsSql = null;
		}
		return searchlist;
	}
	
	public void saveFunctionLogData(String userId,String moduleID,String functionId,String accessDate,String ipAddress,String sessionNo,String reportName,String reportParam)
	{
		logger.info("Inside ReportsDAOImpl.............saveFunctionLogData()");
		ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.utill");
		String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		StringBuffer bufInsSql =	new StringBuffer();
		
		ArrayList qryList = new ArrayList();
		try{
			String qry="select convert(varchar(8),getdate(),108)";
			String bTime=ConnectionReportDumpsDAO.singleReturn(qry);
			accessDate = accessDate.concat(" "+bTime);
			
			bufInsSql.append("insert into SEC_USER_FUNCTION_LOG(USER_ID,MODULE_ID,FUNCTION_ID,ACCESS_DATE,IP_ADDRESS,SESSION_NO,REPORT_NAME,REPORT_PARAM)");
			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?," ); //USER_ID
			bufInsSql.append(" ?," ); //MODULE_ID
			bufInsSql.append(" ?," ); //FUNCTION_ID
			bufInsSql.append(dbo);
			bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"')," ); //ACCESS_DATE
			bufInsSql.append(" ?," ); //IP_ADDRESS
			bufInsSql.append(" ?," ); //SESSION_NO
			bufInsSql.append(" ?," ); //REPORT_NAME
			bufInsSql.append(" ? )" ); //REPORT_PARAM
		
			if((CommonFunction.checkNull(userId)).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((CommonFunction.checkNull(userId).trim()));

			if((CommonFunction.checkNull(moduleID).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(moduleID.trim()); //MODULE_ID

					
			if((CommonFunction.checkNull(functionId).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((functionId).trim()); //FUNCTION_ID
			
			if((CommonFunction.checkNull(accessDate).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((accessDate).trim()); //ACCESS_DATE
			
			insertPrepStmtObject.addNull();  //IP_ADDRESS
			
			insertPrepStmtObject.addNull();  //SESSION_NO
			
			if((CommonFunction.checkNull(reportName).trim()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((reportName).trim()); //REPORT_NAME
			
			insertPrepStmtObject.addString(reportParam); //REPORT_PARAM

			insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("IN saveUserFunctionLog() from reports insert query1 ### "+insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			boolean status1=ConnectionReportDumpsDAO.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("In saveUserFunctionLog from Reports......................"+status1);

			
		  }
			catch(Exception e)
			{
				logger.info("In saveUserFunctionLog......................"+e);
			}
			finally
			{
				insertPrepStmtObject=null;
				qryList=null;
				bufInsSql=null;
				dateFormatWithTime=null;
				moduleID=null;
			}

		}
		public ArrayList<Object> fetchCorpHunterData(String dealId,String custId,int j,String userId){
			//dummy implementation
			return null;
		}
		public ArrayList<Object> CorpHunterCustCSV(String dealId){
			//dummy implementation
			return null;
		}
		public ArrayList<Object> HunterCustCSV(String dealId){
			//dummy implementation
			return null;
		}
		public ArrayList<Object> fetchDealID(String hunterCustTypeVar){
			//dummy implementation
			return null;
		}
		
	
	}