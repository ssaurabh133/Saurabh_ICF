package com.masters.daoImplMYSQL;

import java.util.ArrayList;
import java.util.ResourceBundle;

import com.masters.dao.ScoreParamMasterDAO;
import com.masters.vo.scoreCardMasterVo;
import com.masters.vo.scoreMasterVo;
import com.connect.*;
import java.text.DecimalFormat;
import org.apache.commons.lang.StringEscapeUtils;

import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

public class ScoreParamMasterDAOImpl implements ScoreParamMasterDAO {
	private static final Logger logger = Logger.getLogger(MasterDAOImpl.class.getName());
	
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
	String dateFormat = resource.getString("lbl.dateInDao");

	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));

	public boolean insertScoreMaster(Object ob) {
		scoreMasterVo scoreMasterVo = (scoreMasterVo) ob;
		boolean status = false;
		logger.info("In insertScoreMaster.........."+ scoreMasterVo.getScoreDesc());
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		String query = "SELECT SCORING_PARAM_CODE FROM cr_scoring_param where  SCORING_PARAM_CODE='"
			+ StringEscapeUtils.escapeSql(scoreMasterVo.getScoreCode().trim()) + "' ";
		
		logger.info("In insertScoreMaster.....................................Dao Impl"+ query);
		boolean st = ConnectionDAO.checkStatus(query);
		logger.info("st -----" + st);
		try {

			if (!st) {
				if (scoreMasterVo.getScoreStatus() != null && scoreMasterVo.getScoreStatus().equalsIgnoreCase("on")) {
					stat = "A";
				} else {
					stat = "X";

				}
				String DataType = scoreMasterVo.getDataType();
				logger.info("DataType"+DataType);
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql
						.append("INSERT INTO cr_scoring_param(SCORING_PARAM_CODE,SCORING_PARAM_NAME,SOURCE_TABLE,SOURCE_COLUMN,DATA_TYPE,REC_STATUS,MAKER_ID,MAKER_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) )");

				if (CommonFunction.checkNull(scoreMasterVo.getScoreCode()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(scoreMasterVo.getScoreCode().toUpperCase().trim());
				
				if (CommonFunction.checkNull(scoreMasterVo.getScoreDesc()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(scoreMasterVo.getScoreDesc().toUpperCase().trim());
				
				if (CommonFunction.checkNull(scoreMasterVo.getSourceTable()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(scoreMasterVo.getSourceTable().toUpperCase().trim());
				if (CommonFunction.checkNull(scoreMasterVo.getSourceColumn()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(scoreMasterVo.getSourceColumn().toUpperCase().trim());
				
				
				logger.info("DataType"+DataType);
				
				if (CommonFunction.checkNull(scoreMasterVo.getDataType()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(scoreMasterVo.getDataType().toUpperCase().trim());
			

				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);

				if (CommonFunction.checkNull(scoreMasterVo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(scoreMasterVo.getMakerId());

				if (CommonFunction.checkNull(scoreMasterVo.getMakerDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(scoreMasterVo.getMakerDate());


				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN insertScoreMaster() insert query1 ### "+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				
				logger.info("In insertScoreMaster status....................."+ status);
			} else {
				status = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}


	public ArrayList<scoreMasterVo> searchScoreMaster(Object ob,HttpServletRequest request) {
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		String scoreCode = "";
		String scoreDesc = "";
		String dataType = "";
		ArrayList searchlist = new ArrayList();
		scoreMasterVo scoreMasterVo = (scoreMasterVo) ob;
		ArrayList<scoreMasterVo> detailList = new ArrayList<scoreMasterVo>();
		try {
					
			scoreCode = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(scoreMasterVo.getScoreCode())).trim());
			scoreDesc = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(scoreMasterVo.getScoreDesc())).trim());
			

			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			bufInsSql.append("select SCORING_PARAM_CODE,scoring_param_name,SOURCE_TABLE,SOURCE_COLUMN,DATA_TYPE," );
			bufInsSql.append("if(REC_STATUS='A','Active','Inactive')as REC_STATUS ");
			bufInsSql.append(" from cr_scoring_param");
			
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM cr_scoring_param ");

			if (!(scoreCode.equals("")) && !(scoreDesc.equals(""))) {
				bufInsSql.append(" WHERE SCORING_PARAM_CODE like '" + scoreCode + "' AND scoring_param_name like '%" + scoreDesc + "%' ");
				bufInsSqlTempCount.append(" WHERE SCORING_PARAM_CODE like '" + scoreCode + "' AND scoring_param_name like '%" + scoreDesc + "%' ");
			}

			else if (!scoreCode.equals("")) {
				bufInsSql.append(" WHERE SCORING_PARAM_CODE like '" + scoreCode + "' ");
				bufInsSqlTempCount.append(" WHERE SCORING_PARAM_CODE like '" + scoreCode + "' ");
			}
			else if (!scoreDesc.equals("")) {
				bufInsSql.append(" WHERE scoring_param_name like '%" + scoreDesc + "%' ");
				bufInsSqlTempCount.append(" WHERE scoring_param_name like '%" + scoreDesc + "%' ");
			}
			bufInsSql.append(" ORDER BY SCORING_PARAM_CODE");
			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
	        
			count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
			
			if((scoreCode.trim()==null && scoreDesc.trim()==null) || (scoreCode.trim().equalsIgnoreCase("") && scoreDesc.trim().equalsIgnoreCase("")) || scoreMasterVo.getCurrentPageLink()>1)
			{
			
			logger.info("current PAge Link no .................... "+scoreMasterVo.getCurrentPageLink());
			if(scoreMasterVo.getCurrentPageLink()>1)
			{
				startRecordIndex = (scoreMasterVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			
			bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
			
			}
			logger.info("query : "+bufInsSql);

			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());

			logger.info("searchScoreMaster " + searchlist.size());
			

			for (int i = 0; i < searchlist.size(); i++) {
				// logger.info("searchCountryDataList "+searchlist.get(i).toString());

				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					scoreMasterVo deptMVO = new scoreMasterVo();
					deptMVO.setScoreCode("<a href=scoringMastersForModify.do?method=openScoreMaster&scoreCode="
									+ CommonFunction.checkNull(data.get(0)).toString()+">"
									+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
					deptMVO.setHiddenScoreCode(CommonFunction.checkNull(data.get(0)).toString());
					deptMVO.setScoreDesc(CommonFunction.checkNull(data.get(1)).toString());
					deptMVO.setSourceTable(CommonFunction.checkNull(data.get(2)).toString());
					deptMVO.setSourceColumn(CommonFunction.checkNull(data.get(3)).toString());
					deptMVO.setDataType(CommonFunction.checkNull(data.get(4)).toString());
					deptMVO.setScoreStatus(CommonFunction.checkNull(data.get(5)).toString());
					deptMVO.setTotalRecordSize(count);
					detailList.add(deptMVO);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
//		if(searchlist.size()==0)
//		{
//			scoreMasterVo deptMVO = new scoreMasterVo();
//			deptMVO.setTotalRecordSize(count);
//			detailList.add(deptMVO);
//			request.setAttribute("flag","yes");
//			logger.info("Detail List when searchList is null: "+detailList.size());
//		}

		return detailList;
	}

	public ArrayList<scoreMasterVo> searchScoreMasterForModify(Object ob,String scoringCode) {
	
		String scoreCode = "";
		
		ArrayList searchlist = new ArrayList();
		scoreMasterVo scoreMasterVo = (scoreMasterVo) ob;
		ArrayList<scoreMasterVo> detailList = new ArrayList<scoreMasterVo>();
		try {
					
			scoreCode = CommonFunction.checkNull(StringEscapeUtils.escapeSql(scoringCode));
			logger.info("scoreCode:-----"+scoreCode);
			
			StringBuffer bufInsSql = new StringBuffer();
			
			bufInsSql.append("select SCORING_PARAM_CODE,scoring_param_name,SOURCE_TABLE,SOURCE_COLUMN,DATA_TYPE," );
			bufInsSql.append("if(REC_STATUS='A','Active','Inactive')as REC_STATUS  ");
			bufInsSql.append(" from cr_scoring_param where SCORING_PARAM_CODE='"+scoreCode+"'");
			

			logger.info("search Query...." + bufInsSql);
		
			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			for (int i = 0; i < searchlist.size(); i++) {
				// logger.info("searchCountryDataList "+searchlist.get(i).toString());

				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					
					scoreMasterVo.setHiddenScoreCode(CommonFunction.checkNull(data.get(0)).toString());
					scoreMasterVo.setScoreDesc(CommonFunction.checkNull(data.get(1)).toString());
					scoreMasterVo.setSourceTable(CommonFunction.checkNull(data.get(2)).toString());
					scoreMasterVo.setSourceColumn(CommonFunction.checkNull(data.get(3)).toString());
					scoreMasterVo.setDataType(CommonFunction.checkNull(data.get(4)).toString());
					scoreMasterVo.setScoreStatus(CommonFunction.checkNull(data.get(5)).toString());
					detailList.add(scoreMasterVo);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return detailList;
	}


	public boolean updateScoreMaster(Object ob) {
		scoreMasterVo vo = (scoreMasterVo) ob;
		logger.info("updateScoreMaster():-" + vo.getScoreCode());
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList updatelist = new ArrayList();

		boolean status = false;
		String stat = "";

		try {
			
			if (vo.getScoreStatus() != null && vo.getScoreStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";
			}
			StringBuffer bufInsSql = new StringBuffer();

			bufInsSql.append(" UPDATE cr_scoring_param set SCORING_PARAM_NAME=?,SOURCE_TABLE =?,SOURCE_COLUMN=?,DATA_TYPE=?, REC_STATUS=?,MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)  where SCORING_PARAM_CODE=?");

			if (CommonFunction.checkNull(vo.getScoreDesc()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getScoreDesc().toUpperCase().trim());

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
			
			if (CommonFunction.checkNull(vo.getScoreCode()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getScoreCode().toUpperCase().trim());
			

			insertPrepStmtObject.setSql(bufInsSql.toString());

			updatelist.add(insertPrepStmtObject);
			
			logger.info("In getScoreCode " + vo.getScoreCode());
			logger.info("In getHiddenScoreCode " + vo.getHiddenScoreCode());
			logger.info("In getScoreDesc " + vo.getScoreDesc());
			logger.info("In getScoreStatus " + vo.getScoreStatus());
			
			logger.info("In getListOfValues" + bufInsSql.toString());
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(updatelist);

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}


	public ArrayList<scoreCardMasterVo> searchCardScoreMaster(Object ob,HttpServletRequest request) {
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		String scoreCardId = "";
		String scoreCardDesc = "";
		ArrayList searchlist = new ArrayList();
		scoreCardMasterVo scoreCardMasterVo = (scoreCardMasterVo) ob;
		ArrayList<scoreCardMasterVo> detailList = new ArrayList<scoreCardMasterVo>();
		try {

			logger.info("In searchScoreMaster()-------------------------------"+scoreCardMasterVo.getScoreCardId()+"-----"+scoreCardMasterVo.getHiddenScoreCode());
			
			scoreCardId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(scoreCardMasterVo.getScoreCardId())).trim());
			scoreCardDesc = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(scoreCardMasterVo.getScoreCardDesc())).trim());

			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			bufInsSql.append("select sm.SCORE_CARD_ID,sm.SCORE_CARD_DESC,pm.PRODUCT_DESC,");
			bufInsSql.append("if(sm.REC_STATUS='A','Active','Inactive')as REC_STATUS,sm.product_id ");
			bufInsSql.append(" from cr_scorecard_m sm left outer JOIN cr_product_m pm on pm.PRODUCT_ID = sm.PRODUCT_ID");
			
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM cr_scorecard_m ");

			if (!(scoreCardId.equals("")) && !(scoreCardDesc.equals(""))) {
				bufInsSql.append(" WHERE SCORE_CARD_ID = '" + scoreCardId + "' AND SCORE_CARD_DESC like '%" + scoreCardDesc + "%' ");
				bufInsSqlTempCount.append(" WHERE SCORE_CARD_ID = '" + scoreCardId + "' AND SCORE_CARD_DESC like '%" + scoreCardDesc + "%' ");
			}

			else if (!scoreCardId.equals("")) {
				bufInsSql.append(" WHERE SCORE_CARD_ID = '" + scoreCardId + "' ");
				bufInsSqlTempCount.append(" WHERE SCORE_CARD_ID = '" + scoreCardId + "' ");
			}
			else if (!scoreCardDesc.equals("")) {
				bufInsSql.append(" WHERE SCORE_CARD_DESC like '%" + scoreCardDesc + "%' ");
				bufInsSqlTempCount.append(" WHERE SCORE_CARD_DESC like '%" + scoreCardDesc + "%' ");
			}
			bufInsSql.append(" ORDER BY SCORE_CARD_ID");
			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
	        
			count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
			
			if((scoreCardId.trim()==null && scoreCardDesc.trim()==null) || (scoreCardId.trim().equalsIgnoreCase("") && scoreCardDesc.trim().equalsIgnoreCase("")) || scoreCardMasterVo.getCurrentPageLink()>1)
			{
			
			logger.info("current PAge Link no .................... "+scoreCardMasterVo.getCurrentPageLink());
			if(scoreCardMasterVo.getCurrentPageLink()>1)
			{
				startRecordIndex = (scoreCardMasterVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			
			bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
			
			}
			logger.info("query : "+bufInsSql);

			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());

			logger.info("searchScoreMaster " + searchlist.size());

			for (int i = 0; i < searchlist.size(); i++) {
				// logger.info("searchCountryDataList "+searchlist.get(i).toString());

				ArrayList data = (ArrayList) searchlist.get(i);

				
				if (data.size() > 0) {
					scoreCardMasterVo deptMVO = new scoreCardMasterVo();
					deptMVO.setScoreCardId("<a href=scoreCardAdd.do?method=openCardScoreMaster&cardId="
									+ CommonFunction.checkNull(data.get(0)).toString()+">"
									+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
					deptMVO.setHiddenScoreCode(CommonFunction.checkNull(data.get(0)).toString());
					deptMVO.setScoreCardDesc(CommonFunction.checkNull(data.get(1)).toString());
					deptMVO.setProductId(CommonFunction.checkNull(data.get(2)).toString());
					deptMVO.setCardStatus(CommonFunction.checkNull(data.get(3)).toString());
					deptMVO.setLbxProductID(CommonFunction.checkNull(data.get(4)).toString());
					deptMVO.setTotalRecordSize(count);
					detailList.add(deptMVO);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
//		if(searchlist.size()==0)
//		{
//			scoreCardMasterVo deptMVO = new scoreCardMasterVo();
//			deptMVO.setTotalRecordSize(count);
//			detailList.add(deptMVO);
//			request.setAttribute("flag","yes");
//			logger.info("Detail List when searchList is null: "+detailList.size());
//		}

		return detailList;
	}


	public boolean insertCardScoreMaster(Object ob) {
		scoreCardMasterVo scoreCardMasterVo = (scoreCardMasterVo) ob;
		boolean status = false;
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		String query = "SELECT SCORE_CARD_DESC FROM cr_scorecard_m where  SCORE_CARD_ID='"+ StringEscapeUtils.escapeSql(scoreCardMasterVo.getScoreCardId().trim()) + "' ";
		
		logger.info("In insertScoreMaster.....................................Dao Impl"+ query);

		boolean st = ConnectionDAO.checkStatus(query);
		logger.info("st -----" + st);
		try {

			if (!st) {
				if (scoreCardMasterVo.getCardStatus() != null && scoreCardMasterVo.getCardStatus().equalsIgnoreCase("on")) {
					stat = "A";
				} else {
					stat = "X";

				}
				logger.info("In insert Score  master");
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql
						.append("INSERT INTO cr_scorecard_m(SCORE_CARD_DESC,PRODUCT_ID,REC_STATUS,MAKER_ID,MAKER_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) )");

				if (CommonFunction.checkNull(
						scoreCardMasterVo.getScoreCardDesc())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(scoreCardMasterVo
							.getScoreCardDesc().toUpperCase().trim());
				
				if (CommonFunction.checkNull(
						scoreCardMasterVo.getLbxProductID())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(scoreCardMasterVo
							.getLbxProductID().toUpperCase().trim());

				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);

				if (CommonFunction.checkNull(scoreCardMasterVo.getMakerId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(scoreCardMasterVo
							.getMakerId());

				if (CommonFunction.checkNull(scoreCardMasterVo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(scoreCardMasterVo
							.getMakerDate());


				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN insertScoreMaster() insert query1 ### "+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				
				logger.info("In insertScoreCardMaster status....................."+ status);
			} else {
				status = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

	public boolean updateCardScoreMaster(Object ob) {
				scoreCardMasterVo vo = (scoreCardMasterVo) ob;
				PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
				
				logger.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+vo.getScoreCardId());
				logger.info("In getScoreCode " + vo.getHiddenScoreCode());

				ArrayList updatelist = new ArrayList();
				 boolean status = false;
					String stat = "";
				try{	
						if (vo.getCardStatus() != null && vo.getCardStatus().equals("on")) {
							stat = "A";
						} else {
							stat = "X";
						}
					StringBuffer bufInsSql = new StringBuffer();
			
					bufInsSql.append(" UPDATE cr_scorecard_m set SCORE_CARD_DESC=?,PRODUCT_ID=?, REC_STATUS=?,MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) where SCORE_CARD_ID=?");
			
					if (CommonFunction.checkNull(vo.getScoreCardDesc()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getScoreCardDesc().toUpperCase().trim());
					
					if (CommonFunction.checkNull(vo.getLbxProductID()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getLbxProductID().toUpperCase().trim());
			
			
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
						insertPrepStmtObject.addString(vo
								.getMakerDate());
					
					if (CommonFunction.checkNull(vo.getHiddenScoreCode()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getHiddenScoreCode().toUpperCase().trim());
					
			
					insertPrepStmtObject.setSql(bufInsSql.toString());
			
					updatelist.add(insertPrepStmtObject);
					
					logger.info("In getScoreCode " + vo.getHiddenScoreCode());
					logger.info("In getScoreDesc " + vo.getScoreCardDesc());
					logger.info("In getScoreStatus " + vo.getCardStatus());
					
					logger.info("In getListOfValues" + bufInsSql.toString());
					status = ConnectionDAO.sqlInsUpdDeletePrepStmt(updatelist);

				} catch (Exception e) {
					e.printStackTrace();
				}
				return status;
			}

}

