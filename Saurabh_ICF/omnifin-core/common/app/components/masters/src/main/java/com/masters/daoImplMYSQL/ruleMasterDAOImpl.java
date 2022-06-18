package com.masters.daoImplMYSQL;

import java.util.ArrayList;
import java.util.ResourceBundle;


import com.connect.*;
import com.masters.dao.ruleMasterDAO;
import com.masters.vo.ruleParamMasterVo;

import java.text.DecimalFormat;
import org.apache.commons.lang.StringEscapeUtils;
import java.util.logging.Logger;

public class ruleMasterDAOImpl implements ruleMasterDAO {
	private static final Logger logger = Logger.getLogger(MasterDAOImpl.class.getName());
	ResourceBundle resource = ResourceBundle
			.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
	String dateFormat = resource.getString("lbl.dateInDao");

	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	
public boolean insertRuleScoreMaster(Object ob) {
	ruleParamMasterVo ruleParamMasterVo = (ruleParamMasterVo) ob;
	boolean status = false;
	logger.info("In insertScoreMaster.........."+ ruleParamMasterVo.getParamName());
	ArrayList qryList = new ArrayList();
	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	String stat = "X";
	String query = "SELECT RULE_PARAM_CODE FROM cr_rule_param_m where RULE_PARAM_CODE='"
		+ StringEscapeUtils.escapeSql(ruleParamMasterVo.getParameterCode().trim()) + "' ";
	
	logger.info("In insertScoreMaster.....................................Dao Impl"+ query);
	boolean st = ConnectionDAOforEJB.checkStatus(query);
	logger.info("st -----" + st);
	try {

		if (!st) {
			if (ruleParamMasterVo.getParameterStatus() != null && ruleParamMasterVo.getParameterStatus().equalsIgnoreCase("on")) {
				stat = "A";
			} else {
				stat = "X";

			}
			String DataType = ruleParamMasterVo.getDataType();
			logger.info("DataType"+DataType);
			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql.append("INSERT INTO cr_rule_param_m(RULE_PARAM_CODE,RULE_PARAM_NAME,SOURCE_TABLE,SOURCE_COLUMN,SOURCE_DATA_TYPE,REC_STATUS,MAKER_ID,MAKER_DATE)");
			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) )");

			if (CommonFunction.checkNull(ruleParamMasterVo.getParameterCode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(ruleParamMasterVo.getParameterCode().toUpperCase().trim());
			
			if (CommonFunction.checkNull(ruleParamMasterVo.getParamName()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(ruleParamMasterVo.getParamName().toUpperCase().trim());
			
			if (CommonFunction.checkNull(ruleParamMasterVo.getSourceTable()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(ruleParamMasterVo.getSourceTable().toUpperCase().trim());
			if (CommonFunction.checkNull(ruleParamMasterVo.getSourceColumn()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(ruleParamMasterVo.getSourceColumn().toUpperCase().trim());
			
			
			logger.info("DataType"+DataType);
			
			if (CommonFunction.checkNull(ruleParamMasterVo.getDataType()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(ruleParamMasterVo.getDataType().toUpperCase().trim());
		

			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat);

			if (CommonFunction.checkNull(ruleParamMasterVo.getMakerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(ruleParamMasterVo.getMakerId());

			if (CommonFunction.checkNull(ruleParamMasterVo.getMakerDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(ruleParamMasterVo.getMakerDate());


			insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("IN insertRulMaster() insert query1 ### "+ insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
			
			logger.info("In insertRuleMaster status....................."+ status);
		} else {
			status = false;
		}
	} catch (Exception e) {
		e.printStackTrace();
	}

	return status;
}


public ArrayList<ruleParamMasterVo> searchRuleMaster(Object ob) {
	int count=0;
	int startRecordIndex=0;
	int endRecordIndex = no;
	String ruleCode = "";
	String ruleDesc = "";
	String dataType = "";
	ArrayList searchlist = new ArrayList();
	ruleParamMasterVo ruleParamMasterVo = (ruleParamMasterVo) ob;
	ArrayList<ruleParamMasterVo> detailList = new ArrayList<ruleParamMasterVo>();
	try {
				
		ruleCode = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(ruleParamMasterVo.getParameterCode())).trim());
		ruleDesc = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(ruleParamMasterVo.getParamName())).trim());
		

		StringBuffer bufInsSql = new StringBuffer();
		StringBuffer bufInsSqlTempCount = new StringBuffer();
		
		bufInsSql.append("select RULE_PARAM_CODE,RULE_PARAM_NAME,SOURCE_TABLE,SOURCE_COLUMN,SOURCE_DATA_TYPE," );
		bufInsSql.append("if(REC_STATUS='A','Active','Inactive')as REC_STATUS ");
		bufInsSql.append(" from cr_rule_param_m");
		
		bufInsSqlTempCount.append("SELECT COUNT(1) FROM cr_rule_param_m ");

		if (!(ruleCode.equals("")) && !(ruleDesc.equals(""))) {
			bufInsSql.append(" WHERE RULE_PARAM_CODE = '" + ruleCode + "' AND RULE_PARAM_NAME like '%" + ruleDesc + "%' ");
			bufInsSqlTempCount.append(" WHERE RULE_PARAM_CODE ='" + ruleCode + "' AND RULE_PARAM_NAME like '%" + ruleDesc + "%' ");
		}

		else if (!ruleCode.equals("")) {
			bufInsSql.append(" WHERE RULE_PARAM_CODE = '" + ruleCode + "' ");
			bufInsSqlTempCount.append(" WHERE RULE_PARAM_CODE = '" + ruleCode + "' ");
		}
		else if (!ruleDesc.equals("")) {
			bufInsSql.append(" WHERE RULE_PARAM_NAME like '%" + ruleDesc + "%' ");
			bufInsSqlTempCount.append(" WHERE RULE_PARAM_NAME like '%" + ruleDesc + "%' ");
		}
		bufInsSql.append(" ORDER BY RULE_PARAM_CODE");
		logger.info("search Query...." + bufInsSql);
		logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
        
		count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
		
		if((ruleCode.trim()==null && ruleDesc.trim()==null) || (ruleCode.trim().equalsIgnoreCase("") && ruleDesc.trim().equalsIgnoreCase("")) || ruleParamMasterVo.getCurrentPageLink()>1)
		{
		
		logger.info("current PAge Link no .................... "+ruleParamMasterVo.getCurrentPageLink());
		if(ruleParamMasterVo.getCurrentPageLink()>1)
		{
			startRecordIndex = (ruleParamMasterVo.getCurrentPageLink()-1)*no;
			endRecordIndex = no;
			logger.info("startRecordIndex .................... "+startRecordIndex);
			logger.info("endRecordIndex .................... "+endRecordIndex);
		}
		
		bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
		
		}
		logger.info("query : "+bufInsSql);

		searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

		logger.info("searchRuleMaster " + searchlist.size());
		

		for (int i = 0; i < searchlist.size(); i++) {
			// logger.info("searchCountryDataList "+searchlist.get(i).toString());

			ArrayList data = (ArrayList) searchlist.get(i);

			if (data.size() > 0) {
				ruleParamMasterVo deptMVO = new ruleParamMasterVo();
				deptMVO.setParameterCode("<a href=ruleParameterAdd.do?method=openRuleMaster&ruleCode="
								+ CommonFunction.checkNull(data.get(0)).toString()+"&paramDesc="+CommonFunction.checkNull(data.get(1)).toString()
								+ ">"
								+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
				deptMVO.setHiddenparameterCode(CommonFunction.checkNull(data.get(0)).toString());
				deptMVO.setParamName(CommonFunction.checkNull(data.get(1)).toString());
				deptMVO.setSourceTable(CommonFunction.checkNull(data.get(2)).toString());
				deptMVO.setSourceColumn(CommonFunction.checkNull(data.get(3)).toString());
				deptMVO.setDataType(CommonFunction.checkNull(data.get(4)).toString());
				deptMVO.setParameterStatus(CommonFunction.checkNull(data.get(5)).toString());
				deptMVO.setTotalRecordSize(count);
				detailList.add(deptMVO);
			}

		}

	} catch (Exception e) {
		e.printStackTrace();
	}
//	if(searchlist.size()==0)
//	{
//		ruleParamMasterVo deptMVO = new ruleParamMasterVo();
//		deptMVO.setTotalRecordSize(count);
//		detailList.add(deptMVO);
//		request.setAttribute("flag","yes");
//		logger.info("Detail List when searchList is null: "+detailList.size());
//	}

	return detailList;
}


public boolean updateRuleMaster(Object ob) {
	ruleParamMasterVo vo = (ruleParamMasterVo) ob;
	logger.info("updateScoreMaster():-" + vo.getParameterCode());
	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	ArrayList updatelist = new ArrayList();

	boolean status = false;
	String stat = "";

	try {
		
		if (vo.getParameterStatus() != null && vo.getParameterStatus().equals("on")) {
			stat = "A";
		} else {
			stat = "X";
		}
		StringBuffer bufInsSql = new StringBuffer();

		bufInsSql.append(" UPDATE cr_rule_param_m set RULE_PARAM_NAME=?,SOURCE_TABLE =?,SOURCE_COLUMN=?,SOURCE_DATA_TYPE=?, REC_STATUS=?," +
					"MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) where RULE_PARAM_CODE=?");

		if (CommonFunction.checkNull(vo.getParamName()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getParamName().toUpperCase().trim());

		if (CommonFunction.checkNull(vo.getSourceTable()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getSourceTable().toUpperCase().trim());
		if (CommonFunction.checkNull(vo.getSourceColumn()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getSourceColumn().toUpperCase().trim());
		if (CommonFunction.checkNull(vo.getDataType()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getDataType().toUpperCase().trim());
		
		
		if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(stat);

		if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getMakerId());

		if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getMakerDate());
		
		if (CommonFunction.checkNull(vo.getParameterCode()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getParameterCode().toUpperCase().trim());
		

		insertPrepStmtObject.setSql(bufInsSql.toString());

		updatelist.add(insertPrepStmtObject);
		
		logger.info("In getScoreCode " + vo.getParameterCode());
//		logger.info("In getHiddenScoreCode " + vo.getHiddenScoreCode());
//		logger.info("In getScoreDesc " + vo.getScoreDesc());
//		logger.info("In getScoreStatus " + vo.getScoreStatus());
		
		logger.info("In getListOfValues" + bufInsSql.toString());
		status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);

		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return status;
}

}


