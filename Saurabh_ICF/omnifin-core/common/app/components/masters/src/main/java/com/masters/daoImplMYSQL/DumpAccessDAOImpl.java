package com.masters.daoImplMYSQL;

import java.util.ArrayList;

import java.util.logging.Logger;

import org.apache.commons.lang.StringEscapeUtils;

import com.connect.CommonFunction;
import com.connect.ConnectionDAOforEJB;
import com.connect.PrepStmtObject;
import com.masters.dao.DumpAccessDAO;
import com.masters.vo.DumpFunctionAccessVo;




public class DumpAccessDAOImpl implements DumpAccessDAO
 {
	
	private static final Logger logger = Logger.getLogger(DumpAccessDAOImpl.class.getName());

	public ArrayList<DumpFunctionAccessVo> getDumpName(Object ob) 
	{
		logger.info("In getReportName()..............DumpAccessDAOImpl.......................Dao Impl");
		String roleId = "";
		String moduleId = "";
		ArrayList searchlist = new ArrayList();
		DumpFunctionAccessVo ReportFunctionAccessVo = (DumpFunctionAccessVo) ob;
		ArrayList<DumpFunctionAccessVo> detailList = new ArrayList<DumpFunctionAccessVo>();

		try {
					
			roleId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(ReportFunctionAccessVo.getLbxRoleId())).trim());
			moduleId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(ReportFunctionAccessVo.getLbxModuleId())).trim());
			logger.info("In getReportName()  roleId  :  "+roleId);
			logger.info("In getReportName()  moduleId  :  "+moduleId);


			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql.append("SELECT DISTINCT CDDD.RECORD_DESC,CDDD.RECORD_DESC,IFNULL(CDAM.REC_STATUS,'X') from cr_dump_download_dtl CDDD  ");
			bufInsSql.append("LEFT JOIN cr_dump_access_m CDAM ON TRIM(TRAILING '\r\n' FROM CDDD.RECORD_DESC) = CDAM.REPORT_NAME  AND CDAM.ROLE_ID='" + roleId	+ "' AND CDAM.MODULE_ID='" + moduleId + "'" );
			bufInsSql.append("ORDER BY CDDD.RECORD_DESC ");
			logger.info("search Query...." + bufInsSql);
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
						
			for (int i = 0; i < searchlist.size(); i++)
			{
				ArrayList data = (ArrayList) searchlist.get(i);				
				if (data.size() > 0) 
				{
					DumpFunctionAccessVo reportfunctionAccessVo = new DumpFunctionAccessVo();
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
	public boolean deleteDumpFunction(String moduleId,String roleId)
	{
		logger.info("In deleteDumpFunction().............DumpAccessDAOImpl........................Dao Impl");		
		boolean status = false;
		ArrayList list = new ArrayList();
		StringBuilder query=new StringBuilder();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		query.append("Delete from cr_DUMP_ACCESS_M  where ROLE_ID='"+roleId.trim()+"' AND MODULE_ID='"+moduleId.trim()+"'");
		logger.info("In deleteDumpFunction.........delete Query  : "+query.toString());
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
	public boolean insertDump(String moduleId, String roleId,	String[] checkedList, String[] unCheckedList) 
	{
		logger.info("In insertDump().....................................Dao Impl");	
		boolean status=false;
		ArrayList qryList =  new ArrayList();
		StringBuffer bufInsSql =null;
		PrepStmtObject insertPrepStmtObject = null;			
		for(int i=0;i<checkedList.length;i++)
		{
			 bufInsSql =	new StringBuffer();
			 insertPrepStmtObject = new PrepStmtObject();
			 bufInsSql.append("insert into cr_DUMP_ACCESS_M (MODULE_ID,ROLE_ID,REPORT_NAME,REC_STATUS) values(?,?,?,?)");
			 
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
			 bufInsSql.append("insert into cr_DUMP_ACCESS_M (MODULE_ID,ROLE_ID,REPORT_NAME,REC_STATUS) values(?,?,?,?)");
			 
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
}






