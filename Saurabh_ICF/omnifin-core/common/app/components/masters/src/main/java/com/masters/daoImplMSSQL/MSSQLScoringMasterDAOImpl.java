package com.masters.daoImplMSSQL;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import org.apache.commons.lang.StringEscapeUtils;
import com.connect.CommonFunction;
import com.connect.ConnectionDAOforEJB;
import com.connect.PrepStmtObject;
import com.masters.dao.ScoringMasterDAO;
import com.masters.vo.ScoringMasterVO;

public class MSSQLScoringMasterDAOImpl implements ScoringMasterDAO{
	private static final Logger logger = Logger.getLogger(MSSQLScoringMasterDAOImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
	String dateFormat = resource.getString("lbl.dateInDao");
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");
	//String selectFrom = resource.getString("lbl.selectFrom");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	
	@Override
	public String saveScoringMaster(ScoringMasterVO vo) {
		logger.info("In saveScoringMaster..........");
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		String scoreCardId ="";
		boolean status=false;
		String result="";
		
		try {
			String query = "select SCORE_CARD_DESC FROM cr_scorecard_m WHERE PRODUCT_ID='"+StringEscapeUtils.escapeSql(vo.getLbxProductID().trim())+"'and  SCHEME_ID='"+ StringEscapeUtils.escapeSql(vo.getLbxSchemeId().trim()) + "'";
			logger.info("In saveScoringMaster.....................................Dao Impl"+ query);
			boolean st = ConnectionDAOforEJB.checkStatus(query);
			logger.info("st -----" + st);
			if (!st) {
				if (vo.getRecStatus() != null&& vo.getRecStatus().equalsIgnoreCase("on")) {
					stat = "A";
				} else {
					stat = "X";

				}
				
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append("INSERT INTO cr_scorecard_m(SCORE_CARD_DESC,PRODUCT_ID,SCHEME_ID,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
				
				if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(vo.getScoreingDesc())).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getScoreingDesc().trim()));
				
				if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			   else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim());

				
				if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(vo.getLbxSchemeId()).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
				else
						insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLbxSchemeId()).trim());

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

				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());

				if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());

				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN saveScoringMaster() insert query Header ### "	+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In insertGroupCodeMaster status....................."+ status);
				if(status){
					String query1="select max(SCORE_CARD_ID) FROM cr_scorecard_m  WITH (ROWLOCK)  ";
					scoreCardId = ConnectionDAOforEJB.singleReturn(query1);
				}
				 bufInsSql = new StringBuffer();
				 insertPrepStmtObject = new PrepStmtObject();
				 qryList=new ArrayList();
				 bufInsSql.append("insert into  cr_scorecard_weightage (SCORE_CARD_ID,SCORING_PARAM_CODE,SCORING_WEIGHTAGE,SCORING_DEFAULT_SCORE,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				 bufInsSql.append(" values ( ");
				 bufInsSql.append(" ?,");
				 bufInsSql.append(" ?,");
				 bufInsSql.append(" ?,");
				 bufInsSql.append(" ?,");
				 bufInsSql.append(" ?,");
				 bufInsSql.append(" ?,");
				 bufInsSql.append(dbo);
				 bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				 bufInsSql.append(" ?,");
				 bufInsSql.append(dbo);
				 bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
                 String scoringParamArr[]=vo.getLbxScoringParam();
                 String weightageArr[]=vo.getWeightage();
                 String defaultValueArr[]=vo.getDefaultValue();
                 int size=scoringParamArr.length;
                 for(int i=0;i<size;i++){
                	
                	 insertPrepStmtObject = new PrepStmtObject();
                	 
                	 if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(scoreCardId)).equalsIgnoreCase(""))
     					insertPrepStmtObject.addNull();
     				else
     					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(scoreCardId));
                	 
                	 if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(scoringParamArr[i])).equalsIgnoreCase(""))
      					insertPrepStmtObject.addNull();
      				else
      					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(scoringParamArr[i]));
     				
     				if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(weightageArr[i])).equalsIgnoreCase(""))
     					insertPrepStmtObject.addNull();
     			   else
     					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(weightageArr[i]).trim());

     				
     				if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(defaultValueArr[i]).trim()).equalsIgnoreCase(""))
     						insertPrepStmtObject.addNull();
     				else
     						insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(defaultValueArr[i]).trim());

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

     				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
     					insertPrepStmtObject.addNull();
     				else
     					insertPrepStmtObject.addString(vo.getMakerId());

     				if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
     					insertPrepStmtObject.addNull();
     				else
     					insertPrepStmtObject.addString(vo.getMakerDate());

     				insertPrepStmtObject.setSql(bufInsSql.toString());
     				logger.info("IN saveScoringMaster() insert query Detagil ### "	+ insertPrepStmtObject.printQuery());
     				qryList.add(insertPrepStmtObject); 
                 }
                 status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList); 
                 logger.info("In insertGroupCodeMaster status....................."+ status);
                 if(status){
                	 result="saved";  
                 }else{
                	 result="notsaved";  
                 }
			}else{
				  result="Already"; 
			  }
		} catch (Exception e) {
			e.printStackTrace();
		}
      
		return result;
		
	}
	
	@Override
	public ArrayList<ScoringMasterVO> editScoringMasterHeader(ScoringMasterVO vo) {
		ArrayList searchlist = new ArrayList();
		ArrayList<ScoringMasterVO>  detailList = new ArrayList<ScoringMasterVO> ();
		try {
			logger.info("In editScoringMasterHeader...............");
			StringBuffer bufInsSql = new StringBuffer();
			
			bufInsSql.append("select a.SCORE_CARD_ID,a.SCORE_CARD_DESC,a.PRODUCT_ID,b.product_desc,a.SCHEME_ID,c.scheme_desc,case when a.REC_STATUS='A' then 'Active' else 'Inactive' end as REC_STATUS from cr_scorecard_m a JOIN cr_product_m b");
			bufInsSql.append(" on a.PRODUCT_ID=b.product_id JOIN cr_scheme_m  c on a.SCHEME_ID=c.SCHEME_ID where a.SCORE_CARD_ID='"+vo.getScoringId()+"'  ");

	
			logger.info("IN editScoringMasterHeader()  query1 ### "+ bufInsSql.toString());
			
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			
			for (int i = 0; i < searchlist.size(); i++) {
				//logger.info("getScoringMasterList "+ searchlist.get(i).toString());
				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					ScoringMasterVO scoringVo = new ScoringMasterVO();
					scoringVo.setScoringId(CommonFunction.checkNull(data.get(0)).toString());
					scoringVo.setScoreingDesc(CommonFunction.checkNull(data.get(1)).toString());
					scoringVo.setLbxProductID(CommonFunction.checkNull(data.get(2)).toString());
					scoringVo.setProduct(CommonFunction.checkNull(data.get(3)).toString());
					scoringVo.setLbxSchemeId(CommonFunction.checkNull(data.get(4)).toString());
					scoringVo.setScheme(CommonFunction.checkNull(data.get(5)).toString());
					scoringVo.setRecStatus(CommonFunction.checkNull(data.get(6)).toString());
					detailList.add(scoringVo);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return detailList;

	}

	@Override
	public ArrayList<ScoringMasterVO> editScoringMasterdtl(ScoringMasterVO vo) {
		ArrayList searchlist = new ArrayList();
		ArrayList<ScoringMasterVO>  detailList = new ArrayList<ScoringMasterVO> ();
		try {
			logger.info("In editScoringMasterdtl...............");
			StringBuffer bufInsSql = new StringBuffer();
			
			bufInsSql.append("Select a.SCORING_PARAM_CODE,b.SCORING_PARAM_NAME,ISNULL(a.SCORING_WEIGHTAGE,0),ISNULL(a.SCORING_DEFAULT_SCORE,0) from cr_scorecard_weightage a join cr_scoring_param b");
			bufInsSql.append(" on a.SCORING_PARAM_CODE=b.SCORING_PARAM_CODE where SCORE_CARD_ID='"+vo.getScoringId()+"'  ");

	
			logger.info("IN editScoringMasterdtl()  query1 ### "+ bufInsSql.toString());
			
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			
			for (int i = 0; i < searchlist.size(); i++) {
				//logger.info("getScoringMasterList "+ searchlist.get(i).toString());
				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					ScoringMasterVO scoringVo = new ScoringMasterVO();
					scoringVo.setScoringParamCode(CommonFunction.checkNull(data.get(0)).toString());
					scoringVo.setScoringParamStr(CommonFunction.checkNull(data.get(1)).toString());
					scoringVo.setWeightageStr(myFormatter.parse(CommonFunction.checkNull(data.get(2)).toString()).toString());
					scoringVo.setDefaultValueStr(myFormatter.parse(CommonFunction.checkNull(data.get(3)).toString()).toString());
					detailList.add(scoringVo);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return detailList;
	}

	@Override
	public ArrayList<ScoringMasterVO> getScoringMasterList(ScoringMasterVO vo) {
		ArrayList searchlist = new ArrayList();
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		String searchScoringDesc = "";
		ArrayList<ScoringMasterVO>  detailList = new ArrayList<ScoringMasterVO> ();
		try {
			logger.info("In getScoringMasterList...............");
			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			searchScoringDesc = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSearchScoringDesc())).trim());
			
			bufInsSql.append("select a.SCORE_CARD_ID,a.SCORE_CARD_DESC,b.product_desc,c.scheme_desc,case when a.REC_STATUS='A' then 'Active' else 'Inactive' end as REC_STATUS ");
			bufInsSql.append("from cr_scorecard_m a JOIN cr_product_m b");
			bufInsSql.append(" on a.PRODUCT_ID=b.product_id JOIN cr_scheme_m  c on a.SCHEME_ID=c.SCHEME_ID where 'a'='a'  ");

			bufInsSqlTempCount.append("SELECT COUNT(1) from cr_scorecard_m a JOIN cr_product_m b on a.PRODUCT_ID=b.product_id JOIN cr_scheme_m  c on a.SCHEME_ID=c.SCHEME_ID where 'a'='a'");
			

			if (!searchScoringDesc.equals("") ) {
				
				bufInsSql.append(" AND a.SCORE_CARD_DESC LIKE '%" + searchScoringDesc+ "%'  ");
				bufInsSqlTempCount.append(" AND a.SCORE_CARD_DESC LIKE '%" + searchScoringDesc+ "%' ");
			}

			logger.info("IN getScoringMasterList() search query1 ### "+ bufInsSql.toString());
			
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
//			if(searchScoringDesc.trim()==null  || searchScoringDesc.trim().equalsIgnoreCase("") || vo.getCurrentPageLink()>1)
//			{
			
			logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
			if(vo.getCurrentPageLink()>1)
			{
				startRecordIndex = (vo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			
			bufInsSql.append(" ORDER BY a.SCORE_CARD_DESC OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
			logger.info("Search getScoringMasterList query for SQL SERVER : " + bufInsSql.toString());

			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));		
			
		
			//query = query +" limit "+startRecordIndex+","+endRecordIndex;
//			}
			logger.info("query : "+bufInsSql);
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			
			for (int i = 0; i < searchlist.size(); i++) {
				//logger.info("getScoringMasterList "+ searchlist.get(i).toString());
				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					ScoringMasterVO scoringVo = new ScoringMasterVO();
			
					scoringVo.setScoringId("<a href=scoringMasterDispatch.do?method=editOpenScoringMaster&scoringId="
							+ CommonFunction.checkNull(data.get(0)).toString()+ ">"
							+ CommonFunction.checkNull(data.get(1)).toString() + "</a>");
					
					scoringVo.setProduct(CommonFunction.checkNull(data.get(2)).toString());
					scoringVo.setScheme(CommonFunction.checkNull(data.get(3)).toString());
					scoringVo.setRecStatus(CommonFunction.checkNull(data.get(4)).toString());
					scoringVo.setTotalRecordSize(count);
					detailList.add(scoringVo);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return detailList;
	}

	@Override
	public String updateScoringMaster(ScoringMasterVO vo) {
		logger.info("In updateScoringMaster..........");
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		String scoreCardId ="";
		boolean status=false;
		String result="";
		try {
			String query = "select SCORE_CARD_DESC FROM cr_scorecard_m WHERE PRODUCT_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID().trim()))+"'and  SCHEME_ID='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxSchemeId().trim())) + "' AND SCORE_CARD_ID<>'"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getScoringId().trim())) + "'";
			logger.info("In saveScoringMaster.....................................Dao Impl"+ query);
			boolean st = ConnectionDAOforEJB.checkStatus(query);
			logger.info("st -----" + st);
			if (!st) {
				if (vo.getRecStatus() != null&& vo.getRecStatus().equalsIgnoreCase("on")) {
					stat = "A";
				} else {
					stat = "X";

				}
				
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append("UPDATE cr_scorecard_m SET SCORE_CARD_DESC=?,PRODUCT_ID=?,SCHEME_ID=?,REC_STATUS=?,MAKER_ID=?, MAKER_DATE=");
				bufInsSql.append(dbo);
				bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append("AUTHOR_ID=?,AUTHOR_DATE=");
				bufInsSql.append(dbo);
				bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				bufInsSql.append(" WHERE SCORE_CARD_ID=?");

				if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(vo.getScoreingDesc())).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getScoreingDesc().trim()));
				
				if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
			   else
					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim());

				
				if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(vo.getLbxSchemeId()).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
				else
						insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getLbxSchemeId()).trim());

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

				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());

				if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());

				if (CommonFunction.checkNull(vo.getScoringId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getScoringId());
				
				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN updateScoringMaster() UPDATE query Header ### "	+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				
		
				
				insertPrepStmtObject = new PrepStmtObject();
				String query1="DELETE FROM cr_scorecard_weightage WHERE SCORE_CARD_ID='"+StringEscapeUtils.escapeSql(vo.getScoringId().trim())+"'";
				insertPrepStmtObject.setSql(query1);
				qryList.add(insertPrepStmtObject);
				
				 bufInsSql = new StringBuffer();
				 insertPrepStmtObject = new PrepStmtObject();
				 
				 bufInsSql.append("insert into  cr_scorecard_weightage (SCORE_CARD_ID,SCORING_PARAM_CODE,SCORING_WEIGHTAGE,SCORING_DEFAULT_SCORE,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				 bufInsSql.append(" values ( ");
				 bufInsSql.append(" ?,");
				 bufInsSql.append(" ?,");
				 bufInsSql.append(" ?,");
				 bufInsSql.append(" ?,");
				 bufInsSql.append(" ?,");
				 bufInsSql.append(" ?,");
				 bufInsSql.append(dbo);
				 bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				 bufInsSql.append(" ?,");
				 bufInsSql.append(dbo);
				 bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
                 String scoringParamArr[]=vo.getLbxScoringParam();
                 String weightageArr[]=vo.getWeightage();
                 String defaultValueArr[]=vo.getDefaultValue();
                 int size=scoringParamArr.length;
                 for(int i=0;i<size;i++){
                	
                	 insertPrepStmtObject = new PrepStmtObject();
                	 
                	 if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(vo.getScoringId())).equalsIgnoreCase(""))
     					insertPrepStmtObject.addNull();
     				else
     					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getScoringId()));
                	 
                	 if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(scoringParamArr[i])).equalsIgnoreCase(""))
      					insertPrepStmtObject.addNull();
      				else
      					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(scoringParamArr[i]));
     				
     				if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(weightageArr[i])).equalsIgnoreCase(""))
     					insertPrepStmtObject.addNull();
     			   else
     					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(weightageArr[i]).trim());

     				
     				if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(defaultValueArr[i]).trim()).equalsIgnoreCase(""))
     						insertPrepStmtObject.addNull();
     				else
     						insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(defaultValueArr[i]).trim());

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

     				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
     					insertPrepStmtObject.addNull();
     				else
     					insertPrepStmtObject.addString(vo.getMakerId());

     				if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
     					insertPrepStmtObject.addNull();
     				else
     					insertPrepStmtObject.addString(vo.getMakerDate());

     				insertPrepStmtObject.setSql(bufInsSql.toString());
     				logger.info("IN updateScoringMaster() insert query Detagil ### "	+ insertPrepStmtObject.printQuery());
     				qryList.add(insertPrepStmtObject); 
                 }
                 status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList); 
                 logger.info("In updateScoringMaster status....................."+ status);
                 if(status){
                	 result="saved";  
                 }else{
                	 result="notsaved";  
                 }
			}else{
				  result="Already"; 
			  }
		} catch (Exception e) {
			e.printStackTrace();
		}
      
		return result;
	}

	@Override
	public ArrayList<ScoringMasterVO> editScoringParamValueDtl(
			ScoringMasterVO vo) {
		ArrayList searchlist = new ArrayList();
		ArrayList<ScoringMasterVO>  detailList = new ArrayList<ScoringMasterVO> ();
		try {
			logger.info("In editScoringParamValueDtl...............");
			StringBuffer bufInsSql = new StringBuffer();
			
			bufInsSql.append("Select a.SCORING_PARAM_CODE,b.SCORING_PARAM_NAME,a.NUMERIC_VALUE_FROM,a.NUMERIC_VALUE_TO,a.CHARACTER_VALUE,a.PARAMETER_SCORE,b.DATA_TYPE ");
			bufInsSql.append(" from cr_scorecard_values a join cr_scoring_param b on a.SCORING_PARAM_CODE=b.SCORING_PARAM_CODE where SCORE_CARD_ID='"+vo.getScoringId()+"'  ");

	
			logger.info("IN editScoringParamValueDtl()  query1 ### "+ bufInsSql.toString());
			
			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			
			for (int i = 0; i < searchlist.size(); i++) {
				//logger.info("getScoringMasterList "+ searchlist.get(i).toString());
				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					ScoringMasterVO scoringVo = new ScoringMasterVO();
					scoringVo.setScoringParamCode(CommonFunction.checkNull(data.get(0)).toString());
					scoringVo.setScoringParamStr(CommonFunction.checkNull(data.get(1)).toString());
					if(CommonFunction.checkNull(data.get(2)).equalsIgnoreCase("")){
						scoringVo.setFromStr("");
					}else{
						scoringVo.setFromStr(myFormatter.parse(CommonFunction.checkNull(data.get(2)).toString()).toString());
					}
					if(CommonFunction.checkNull(data.get(3)).equalsIgnoreCase("")){
						scoringVo.setToStr("");
					}else{
						scoringVo.setToStr(myFormatter.parse(CommonFunction.checkNull(data.get(3)).toString()).toString());	
					}
					scoringVo.setCharValueStr(CommonFunction.checkNull(data.get(4)).toString());
					scoringVo.setScoreStr(myFormatter.parse(CommonFunction.checkNull(data.get(5)).toString()).toString());
					scoringVo.setDataType(CommonFunction.checkNull(data.get(6)).toString());
					detailList.add(scoringVo);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return detailList;
	}

	@Override
	public String saveScoringMasterParamValue(ScoringMasterVO vo) {
		logger.info("In saveScoringMasterParamValue..........");
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		String scoreCardId ="";
		boolean status=false;
		String result="";
		
		try {
			String query = "Select SCORE_CARD_ID from cr_scorecard_values WHERE SCORE_CARD_ID='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getScoringId()).trim()) + "'";
			logger.info("In saveScoringMasterParamValue.....................................Dao Impl"+ query);
			boolean st = ConnectionDAOforEJB.checkStatus(query);
			logger.info("st -----" + st);
			String recStatus=" Select ISNULL(REC_STATUS,'X') REC_STATUS from cr_scorecard_m where SCORE_CARD_ID='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getScoringId()).trim()) + "'";
			stat=ConnectionDAOforEJB.singleReturn(recStatus);
			if (st) {
				String delQuery="delete from cr_scorecard_values WHERE SCORE_CARD_ID='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getScoringId()).trim()) + "'";
				insertPrepStmtObject.setSql(delQuery);
				logger.info("IN saveScoringMasterParamValue() Delete query  ### "	+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
			    }
				
				StringBuffer bufInsSql = new StringBuffer();
				insertPrepStmtObject = new PrepStmtObject();
				 
				 bufInsSql.append("insert into cr_scorecard_values (SCORE_CARD_ID,SCORING_PARAM_CODE,NUMERIC_VALUE_FROM,NUMERIC_VALUE_TO,CHARACTER_VALUE,PARAMETER_SCORE,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				 bufInsSql.append(" values ( ");
				 bufInsSql.append(" ?,");
				 bufInsSql.append(" ?,");
				 bufInsSql.append(" ?,");
				 bufInsSql.append(" ?,");
				 bufInsSql.append(" ?,");
				 bufInsSql.append(" ?,");
				 bufInsSql.append(" ?,");
				 bufInsSql.append(" ?,");
				 bufInsSql.append(dbo);
				 bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				 bufInsSql.append(" ?,");
				 bufInsSql.append(dbo);
				 bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
                 String scoringParamArr[]=vo.getLbxScoringParam();
                 String fromArr[]=vo.getFrom();
                 String toArr[]=vo.getTo();
                 String charValueArr[]=vo.getCharValue();
                 String scoreArr[]=vo.getScore();
                 int size=scoringParamArr.length;
                 for(int i=0;i<size;i++){
                	
                	 insertPrepStmtObject = new PrepStmtObject();
                	 
                	 if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(vo.getScoringId())).equalsIgnoreCase(""))
     					insertPrepStmtObject.addNull();
     				else
     					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(vo.getScoringId()));
                	 
                	 if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(scoringParamArr[i])).equalsIgnoreCase(""))
      					insertPrepStmtObject.addNull();
      				else
      					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(scoringParamArr[i]));
     				
     				if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(fromArr[i])).equalsIgnoreCase(""))
     					insertPrepStmtObject.addNull();
     			   else
     					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(fromArr[i]).trim());

     				
     				if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(toArr[i]).trim()).equalsIgnoreCase(""))
     						insertPrepStmtObject.addNull();
     				else
     					insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(toArr[i]).trim());

     				if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(charValueArr[i]).trim()).equalsIgnoreCase(""))
 						insertPrepStmtObject.addNull();
 				    else
 					    insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(charValueArr[i]).trim());
     				
     				if (CommonFunction.checkNull(StringEscapeUtils.escapeSql(scoreArr[i]).trim()).equalsIgnoreCase(""))
 						insertPrepStmtObject.addNull();
 				    else
 					    insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(scoreArr[i]).trim());
     				
     				
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

     				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
     					insertPrepStmtObject.addNull();
     				else
     					insertPrepStmtObject.addString(vo.getMakerId());

     				if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
     					insertPrepStmtObject.addNull();
     				else
     					insertPrepStmtObject.addString(vo.getMakerDate());

     				insertPrepStmtObject.setSql(bufInsSql.toString());
     				logger.info("IN saveScoringMasterParamValue() insert query Detagil ### "	+ insertPrepStmtObject.printQuery());
     				qryList.add(insertPrepStmtObject); 
                 }
                 status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList); 
                 logger.info("In saveScoringMasterParamValue status....................."+ status);
                 if(status){
                	 result="saved";  
                 }else{
                	 result="notsaved";  
                 }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
      
		return result;
	}

	

}
