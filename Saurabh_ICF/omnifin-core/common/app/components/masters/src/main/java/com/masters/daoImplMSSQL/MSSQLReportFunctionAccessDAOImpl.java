package com.masters.daoImplMSSQL;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.apache.commons.lang.StringEscapeUtils;

import com.connect.CommonFunction;
import com.connect.ConnectionDAOforEJB;
import com.connect.PrepStmtObject;
import com.masters.dao.ReportFunctionAccessDAO;
import com.masters.vo.ReportFunctionAccessVo;




public class MSSQLReportFunctionAccessDAOImpl implements ReportFunctionAccessDAO
 {
	
	private static final Logger logger = Logger.getLogger(MSSQLReportFunctionAccessDAOImpl.class.getName());

	public ArrayList<ReportFunctionAccessVo> getReportName(Object ob) 
	{
		logger.info("In getReportName().....................................Dao Impl");
		String roleId = "";
		String moduleId = "";
		ArrayList searchlist = new ArrayList();
		ReportFunctionAccessVo ReportFunctionAccessVo = (ReportFunctionAccessVo) ob;
		ArrayList<ReportFunctionAccessVo> detailList = new ArrayList<ReportFunctionAccessVo>();

		try {
					
			roleId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(ReportFunctionAccessVo.getLbxRoleId())).trim());
			moduleId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(ReportFunctionAccessVo.getLbxModuleId())).trim());
			logger.info("In getReportName()  roleId  :  "+roleId);
			logger.info("In getReportName()  moduleId  :  "+moduleId);


			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql.append("Select DISTINCT reports.REPORT_NAME,reports.REPORT_DESC,ISNULL(CRM.REC_STATUS,'X') from reports ");
			bufInsSql.append("LEFT JOIN cr_report_m CRM ON reports.REPORT_NAME=CRM.REPORT_NAME  AND CRM.ROLE_ID='" + roleId	+ "' AND CRM.MODULE_ID='" + moduleId + "'" );
			bufInsSql.append("WHERE reports.MODULE_ID='" + moduleId + "' AND reports.REC_STATUS='A' ORDER BY reports.REPORT_DESC ");
			logger.info("search Query...." + bufInsSql);
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
						
			for (int i = 0; i < searchlist.size(); i++)
			{
				ArrayList data = (ArrayList) searchlist.get(i);				
				if (data.size() > 0) 
				{
					ReportFunctionAccessVo reportfunctionAccessVo = new ReportFunctionAccessVo();
					reportfunctionAccessVo.setReportName(CommonFunction.checkNull(data.get(0)).toString());
					reportfunctionAccessVo.setReportDesc(CommonFunction.checkNull(data.get(1)).toString());
					reportfunctionAccessVo.setStatus(CommonFunction.checkNull(data.get(2)).toString());			
					detailList.add(reportfunctionAccessVo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return detailList;
	}	
	public boolean deleteReportFunction(String moduleId,String roleId)
	{
		logger.info("In deleteReportFunction().....................................Dao Impl");		
		boolean status = false;
		ArrayList list = new ArrayList();
		StringBuilder query=new StringBuilder();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		query.append("Delete from cr_report_m  where ROLE_ID='"+roleId.trim()+"' AND MODULE_ID='"+moduleId.trim()+"'");
		logger.info("In deleteReportFunction.........delete Query  : "+query.toString());
		insertPrepStmtObject.setSql(query.toString());
		list.add(insertPrepStmtObject);
		try
		{
			status =ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(list);
			logger.info("In deleteReportFunction.........delete status: "+status);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return status;
	}
	@Override
	public boolean insertReport(String moduleId, String roleId,	String[] checkedList, String[] unCheckedList) 
	{
		logger.info("In insertReport().....................................Dao Impl");	
		boolean status=false;
		ArrayList qryList =  new ArrayList();
		StringBuffer bufInsSql =null;
		PrepStmtObject insertPrepStmtObject = null;			
		for(int i=0;i<checkedList.length;i++)
		{
			 bufInsSql =	new StringBuffer();
			 insertPrepStmtObject = new PrepStmtObject();
			 bufInsSql.append("insert into cr_report_m (MODULE_ID,ROLE_ID,REPORT_NAME,REC_STATUS) values(?,?,?,?)");
			 
			 if(CommonFunction.checkNull(moduleId).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			 else
				insertPrepStmtObject.addString(moduleId);
			 
			 if(CommonFunction.checkNull(roleId).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			 else
				insertPrepStmtObject.addString(roleId);
			 
			 if(CommonFunction.checkNull(checkedList[i]).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			 else
				insertPrepStmtObject.addString(checkedList[i]);
			 
			 insertPrepStmtObject.addString("A");
			 insertPrepStmtObject.setSql(bufInsSql.toString());
			 logger.info("Insert Query :  "+insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
		}
		for(int i=0;i<unCheckedList.length;i++)
		{
			 bufInsSql =	new StringBuffer();
			 insertPrepStmtObject = new PrepStmtObject();
			 bufInsSql.append("insert into cr_report_m (MODULE_ID,ROLE_ID,REPORT_NAME,REC_STATUS) values(?,?,?,?)");
			 
			 if(CommonFunction.checkNull(moduleId).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			 else
				insertPrepStmtObject.addString(moduleId);
			 
			 if(CommonFunction.checkNull(roleId).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			 else
				insertPrepStmtObject.addString(roleId);
			 
			 if(CommonFunction.checkNull(unCheckedList[i]).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			 else
				insertPrepStmtObject.addString(unCheckedList[i]);
			 
			 insertPrepStmtObject.addString("X");
			 insertPrepStmtObject.setSql(bufInsSql.toString());
			 logger.info("Insert Query :  "+insertPrepStmtObject.printQuery());
			 qryList.add(insertPrepStmtObject);
		}
		try
		{
			status=ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("Insert Status   :  "+status);
		}
		catch(Exception e)
		{e.printStackTrace();}
		return status;
	}
	  public  String getcamTemplatePath(){
		  return null;
	  }
}





