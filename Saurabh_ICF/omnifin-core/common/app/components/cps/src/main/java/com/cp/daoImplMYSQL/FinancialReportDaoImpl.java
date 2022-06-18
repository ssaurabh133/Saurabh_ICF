package com.cp.daoImplMYSQL;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.ConnectionDAOforEJB;
import com.connect.PrepStmtObject;
import com.cp.dao.FinancialReportDao;
import com.cp.vo.CodeDescVo;
import com.cp.vo.FinancialReportVo;

public class FinancialReportDaoImpl implements FinancialReportDao{
	private static final Logger logger = Logger.getLogger(FinancialReportDaoImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	@Override
	public boolean saveReportLink(FinancialReportVo vo) {
		logger.info("saveReportLink---------");
		boolean flag = false;
		/*String query = "select count(1) from generated_report_link_dtl where DEAL_ID = '"+caseId+"' and report_type='"+reportType+"'";
		logger.info("query---"+query);
		String queryValue = ConnectionDAOforEJB.singleReturn("query");*/
		/*if(!CommonFunction.checkNull(queryValue).equalsIgnoreCase("0"))
		{*/
			String caseId = vo.getCaseId();
			String makerId = vo.getMakerId();
			String reportType = vo.getReportType();
			String businessDate = vo.getMakerDate();
			/*String updateQuery = "update generated_report_link_dtl set IGNORE_FLAG = 'Y'where DEAL_ID = '"+caseId+"' and report_type='"+reportType+"' and stage_id = '"+vo.getStageId()+"'";
			logger.info("updateQuery---"+updateQuery);
			ArrayList updateList = new ArrayList();
			updateList.add(updateQuery);*/
			
			PrepStmtObject object = new PrepStmtObject();
			StringBuffer insertQuery = new StringBuffer();
			 insertQuery.append("insert into generated_report_link_dtl(DEAL_ID,REPORT_TYPE,IGNORE_FLAG,MAKER_ID,MAKER_DATE,STAGE_ID)");
			 insertQuery.append("values (");
			 insertQuery.append("?,");
			 insertQuery.append("?,");
			 insertQuery.append("?,");
			 insertQuery.append("?,");
			 insertQuery.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND), " );
			 insertQuery.append("?");
			 insertQuery.append(" )");
			 
			 object.addString(caseId);
			 object.addString(reportType);
			 object.addString("N");
			 object.addString(makerId);
			 object.addString(businessDate);
			 object.addString(vo.getStageId());
			 object.setSql(insertQuery.toString());
			 logger.info("insertQuery---"+insertQuery.toString());
			logger.info("insertQuery---"+object.printQuery());
			ArrayList insertList = new ArrayList();
			insertList.add(object);
			
			try {
				/*if(ConnectionDAOforEJB.sqlInsUpdDelete(updateList))*/
				flag = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(insertList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		/*}*/
			
			
		return flag;
	}
	@Override
	public ArrayList getReportLink(String caseId, String makerId,
			String reportType,String refId,String customerName, String stageId, boolean isCreditApproval) {
		logger.info("getReportLink---------"+reportType);
		
		String query = "select report_type,MAKER_ID,date_format(Maker_date,'%d-%m-%Y %h:%m:%s') as Maker_date from "
				+ "generated_report_link_dtl where DEAL_ID = '"+caseId+"'   and stage_id = '"+stageId+"' and report_type like '%"+reportType+"%' ";
		
		if(!isCreditApproval)
		{
			if (!CommonFunction.checkNull(makerId).equalsIgnoreCase("")) {
				query= query+" and maker_id ='"+makerId+"' ";
			}
		}	
		query = query+" order by link_id desc";
		logger.info("query---"+query);
		ArrayList main = null;
		ArrayList sub = null;
		ArrayList returnList = new ArrayList();
		FinancialReportVo vo = null;
		try {
			main = 	ConnectionDAO.sqlSelect(query);
			int size = main!=null?main.size():0;
			for(int i = 0; i<size;i++)
			{
				sub = (ArrayList)main.get(i);
				if(sub.size()>0)
				{
					vo = new FinancialReportVo();
					vo.setCaseId(caseId);
					vo.setRefId(refId);
					vo.setCustomerName(customerName);
					vo.setReportType("<a href='#' onclick = generateBankAccAnalysisReport('reportName','"+CommonFunction.checkNull(sub.get(0))+"') > "+CommonFunction.checkNull(sub.get(0)).replace("/", "")+" </a>");
					vo.setMakerId(CommonFunction.checkNull(sub.get(1)));
					vo.setMakerDate(CommonFunction.checkNull(sub.get(2)));
					vo.setTotalRecordSize(size);
				}
				returnList.add(vo);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnList;
	}
	
	@Override
	public ArrayList getReportList(String key) {
logger.info("getReportList---------");
		
		String query = "select value,description  from generic_master where generic_key = '"+key+"' and rec_status='A'";
		logger.info("query---"+query);
		ArrayList main = null;
		ArrayList sub = null;
		ArrayList returnList = new ArrayList();
		CodeDescVo vo = null;
		try {
			main = 	ConnectionDAO.sqlSelect(query);
			int size = main!=null?main.size():0;
			for(int i = 0; i<size;i++)
			{
				sub = (ArrayList)main.get(i);
				if(sub.size()>0)
				{
					vo = new CodeDescVo();
					vo.setValue(CommonFunction.checkNull(sub.get(0)));
					vo.setDescription(CommonFunction.checkNull(sub.get(1)));
					
				}
				returnList.add(vo);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnList;
	}
	@Override
	public boolean checkFundFlowForwardStatus(String caseId,String stageId) {
		logger.info("checkFundFlowForwardStatus caseId----------"+caseId);
		String returnValue = "";
		String checkQuery="";
		if(stageId.equalsIgnoreCase("FA"))
		{
		 checkQuery = "select count(1) from cr_financial_data_dtl where DEAL_ID = '"+caseId+"' and ifnull(REC_STATUS,'P') = 'P'";
		}
		else
			checkQuery = "select count(1) from cr_bank_analysis_dtl where DEAL_ID = '"+caseId+"' and ifnull(REC_STATUS,'P') = 'P'";	
		logger.info("select Query-------"+checkQuery);
		try {
			returnValue = ConnectionDAO.singleReturn(checkQuery);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return returnValue.equalsIgnoreCase("0")?true:false;
	}

}
