package com.masters.daoImplMYSQL;

import java.util.ArrayList;
import java.util.ResourceBundle;

import com.masters.dao.PCDMasterDAO;
import com.masters.vo.pcdMasterVo;
import com.connect.*;

import java.text.DecimalFormat;

import org.apache.commons.lang.StringEscapeUtils;



import java.util.logging.Logger;



public class PCDMasterDAOImpl implements PCDMasterDAO {
	private static final Logger logger = Logger.getLogger(PCDMasterDAOImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	
	String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
	String dateFormat = resource.getString("lbl.dateInDao");

	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	
	public ArrayList<pcdMasterVo> searchScreenPolicyListData(Object ob) {
		String productId = "";
		String schemeId = "";
		String stage= "";
		String ruleCode = "";
		String ruleDesc = "";
		String action = "";
		String appLevel = "";
		int count = 0;
		int startRecordIndex = 0;
		int endRecordIndex = no;
		StringBuffer bufInsSqlTmp =  new StringBuffer();
		ArrayList searchlist = new ArrayList();
		pcdMasterVo pcdMasterVo = (pcdMasterVo) ob;
		ArrayList<pcdMasterVo> detailList = new ArrayList<pcdMasterVo>();
		try {
			logger.info("In searchScreenPolicyListData().....................................Dao Impl");
			
			productId = StringEscapeUtils.escapeSql(CommonFunction.checkNull(pcdMasterVo.getLbxProductID()).trim());
			
			schemeId = StringEscapeUtils.escapeSql(CommonFunction.checkNull(pcdMasterVo.getLbxSchemeID()).trim());
			
			stage = StringEscapeUtils.escapeSql(CommonFunction.checkNull(pcdMasterVo.getStage()).trim());
			
			
			boolean appendSQL = false;
			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql.append("select DISTINCT pm.PRODUCT_DESC,pcm.product_id,sm.SCHEME_DESC,pcm.scheme_id,cs.STAGE_DESCRIPTION," +
							"if(pcm.REC_STATUS='A','Active','Inactive')as REC_STATUS,pcm.STAGE_ID  from cr_policy_check_m pcm inner join cr_product_m pm on " +
							"pcm.PRODUCT_ID=pm.PRODUCT_ID inner join cr_scheme_m sm on pcm.SCHEME_ID=sm.SCHEME_ID " +
							"inner join policy_check_stages cs on cs.STAGE_ID=pcm.stage_id group by pcm.product_id,pcm.scheme_id,pcm.stage_id ");

//			if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(pcdMasterVo.getLbxProductID()).trim()).equalsIgnoreCase("")))
//					&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(pcdMasterVo.getLbxSchemeID())).trim().equalsIgnoreCase("")))
//					&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(pcdMasterVo.getStage()))	.trim().equalsIgnoreCase("")))){
//				bufInsSql.append("WHERE pcm.product_id ="
//									+productId
//									+" and pcm.scheme_id="
//									+ schemeId
//									+" and pcm.stage_id="
//									+ stage+"' ");
//			}
//
//			if (((pcdMasterVo.getLbxProductID().equalsIgnoreCase("")))	
//					|| ((pcdMasterVo.getLbxSchemeID().equalsIgnoreCase(""))) 
//					|| ((pcdMasterVo.getStage().equalsIgnoreCase("")))) {
//				appendSQL = true;
//			}
			//**************************** Asesh workspace start ***************
			bufInsSqlTmp.append("select count(1) from ( select DISTINCT pm.PRODUCT_DESC,pcm.product_id,sm.SCHEME_DESC,pcm.scheme_id,cs.STAGE_DESCRIPTION," +
					"if(pcm.REC_STATUS='A','Active','Inactive')as REC_STATUS,pcm.STAGE_ID  from cr_policy_check_m pcm inner join cr_product_m pm on " +
					"pcm.PRODUCT_ID=pm.PRODUCT_ID inner join cr_scheme_m sm on pcm.SCHEME_ID=sm.SCHEME_ID " +
					"inner join policy_check_stages cs on cs.STAGE_ID=pcm.stage_id group by pcm.product_id,pcm.scheme_id,pcm.stage_id )  tabl ");

			//bufInsSqlTmp.append("SELECT COUNT(1) FROM (select count(0) from cr_policy_check_m group by product_id) as total");
			//***************************  Asesh workspace end  ******************
//			
//			if (appendSQL) {
//				logger.info("In Where Clause");
//				bufInsSql.append("WHERE product_id='"+ StringEscapeUtils.escapeSql(pcdMasterVo.getLbxProductID()).trim()+ "'");
//				bufInsSqlTmp.append("WHERE product_id='"+ StringEscapeUtils.escapeSql(pcdMasterVo.getLbxProductID()).trim()+ "'");
//			}
//
//			if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(pcdMasterVo.getLbxProductID())).trim().equalsIgnoreCase("")))) {
//				bufInsSql.append("product_id='"+ StringEscapeUtils.escapeSql(pcdMasterVo.getLbxProductID()).trim()+ "' AND");
//				bufInsSqlTmp.append("product_id='"+ StringEscapeUtils.escapeSql(pcdMasterVo.getLbxProductID()).trim()+ "' AND");
//				appendSQL = true;
//
//			}
//			if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(pcdMasterVo.getLbxSchemeID())).trim().equalsIgnoreCase("")))) {
//				bufInsSql.append(" scheme_id='"+ StringEscapeUtils.escapeSql(pcdMasterVo.getLbxSchemeID()).trim() + "' AND");
//				bufInsSqlTmp.append(" scheme_id='"+ StringEscapeUtils.escapeSql(pcdMasterVo.getLbxSchemeID()).trim() +"' AND");
//				appendSQL = true;
//
//			}
//
//			if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(pcdMasterVo.getStage())).trim().equalsIgnoreCase("")))) {
//				bufInsSql.append(" stage_id like '%"+ StringEscapeUtils.escapeSql(pcdMasterVo.getStage()).trim() + "%' AND");
//				bufInsSqlTmp.append(" stage_id like '%"+ StringEscapeUtils.escapeSql(pcdMasterVo.getStage()).trim() + "%' AND");
//				appendSQL = true;
//
//			}
			count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTmp.toString()));
			
			if((ruleCode.trim()==null && ruleDesc.trim()==null) || (ruleCode.trim().equalsIgnoreCase("") && ruleDesc.trim().equalsIgnoreCase("")) || pcdMasterVo.getCurrentPageLink()>1)
			{
			
			logger.info("current PAge Link no .................... "+pcdMasterVo.getCurrentPageLink());
			if(pcdMasterVo.getCurrentPageLink()>1)
			{
				startRecordIndex = (pcdMasterVo.getCurrentPageLink()-1)*no;
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

				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					pcdMasterVo pcdMVO = new pcdMasterVo();
					
					pcdMVO.setProduct("<a href=policyChecklistEditMode.do?method=openForEditting&productId="+(CommonFunction.checkNull(data.get(1)).toString()).trim()+ "" +
							"&schemeId="+(CommonFunction.checkNull(data.get(3)).toString()).trim()+ "" +
							"&stage="+(CommonFunction.checkNull(data.get(6)).toString()).trim()+ ">"+ (CommonFunction.checkNull(data.get(0)).toString()).trim() + "</a>");
//					pcdMVO.setLbxProductID(CommonFunction.checkNull(data.get(1)).toString().trim());
					pcdMVO.setScheme(CommonFunction.checkNull(data.get(2)).toString());
					pcdMVO.setLbxSchemeID(CommonFunction.checkNull(data.get(3)).toString());
					pcdMVO.setStage(CommonFunction.checkNull(data.get(4)).toString());
					//pcdMVO.setRuleCode(CommonFunction.checkNull(data.get(5)).toString());
					//pcdMVO.setAction(CommonFunction.checkNull(data.get(5)).toString());
					//pcdMVO.setAppLevel(CommonFunction.checkNull(data.get(6)).toString());
					pcdMVO.setStatus(CommonFunction.checkNull(data.get(5)).toString());
					pcdMVO.setTotalRecordSize(count);
					detailList.add(pcdMVO);
					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
//		if(searchlist.size()==0)
//		{
//			pcdMasterVo pcdMVO = new pcdMasterVo();
//			pcdMVO.setTotalRecordSize(count);
//			detailList.add(pcdMVO);
////			request.setAttribute("flag","yes");
//			logger.info("Detail List when searchList is null: "+detailList.size());
//		}
		return detailList;
	}

	
	public boolean insertPolicyCheckListMaster( String[] ruleCodeList,String[] ruleDescList,
												String[] actionList,  String[] appLevelList,pcdMasterVo vo) {

		ArrayList<PrepStmtObject> qryList = new ArrayList<PrepStmtObject>();
		boolean status = true;
		logger.info("In insertPolicyCheckListMaster.....................................");
		String schemeId=CommonFunction.checkNull(vo.getSchemeId());
		StringBuffer bufInsSql = null;
		PrepStmtObject insertPrepStmtObject = null;
		try {
			
			String countQry = "SELECT COUNT(1) FROM cr_policy_check_m WHERE PRODUCT_ID='"+CommonFunction.checkNull(vo.getLbxProductID())+"' " +
							"AND SCHEME_ID='"+CommonFunction.checkNull(vo.getLbxSchemeID())+"' AND STAGE_ID ='"+CommonFunction.checkNull(vo.getLbxPCDStage())+"'";
			logger.info("... countQry ..."+countQry);
			String duplicateCount = ConnectionDAOforEJB.singleReturn(countQry);
			logger.info("Inside the for loop to check"+ruleCodeList.length);
			logger.info("... duplicateCount ..."+duplicateCount);
			
			if(duplicateCount.equalsIgnoreCase("0"))
			{
				
			for (int i = 0; i < ruleCodeList.length; i++) {
				
				logger.info("Inside the for loop to check"+ruleCodeList.length);
				
				bufInsSql = new StringBuffer();
				insertPrepStmtObject = new PrepStmtObject();

				logger.info("In insertPolicyCheckListMaster..ID.. "+CommonFunction.checkNull(vo.getLbxProductID()));

				bufInsSql.append(" insert into cr_policy_check_m(PRODUCT_ID,SCHEME_ID,STAGE_ID,RULE_CODE,POLICY_TYPE,APPROVAL_LEVEL,REC_STATUS,MAKER_ID,MAKER_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" ?,");
				bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND))");
			
				if (CommonFunction.checkNull(vo.getLbxProductID()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxProductID().toUpperCase().trim());

				if (CommonFunction.checkNull(vo.getLbxSchemeID()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxSchemeID().toUpperCase().trim());

				if (CommonFunction.checkNull(vo.getLbxPCDStage()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxPCDStage().toUpperCase().trim());

				if (CommonFunction.checkNull(ruleCodeList[i]).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(ruleCodeList[i].toUpperCase().trim());

				if (CommonFunction.checkNull(actionList[i]).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else if (actionList[i].equalsIgnoreCase("S")){
					insertPrepStmtObject.addString("S");
				}else if(actionList[i].equalsIgnoreCase("D")){
						insertPrepStmtObject.addString("D");
				}

				if (CommonFunction.checkNull(appLevelList[i]).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(appLevelList[i].toUpperCase().trim());

				if (CommonFunction.checkNull(vo.getStatus()).equalsIgnoreCase("on"))
					insertPrepStmtObject.addString("A");
				else
					insertPrepStmtObject.addString("X");
				
				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());
				if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());
				
				insertPrepStmtObject.setSql(bufInsSql.toString());
				qryList.add(insertPrepStmtObject);
			}	
				logger.info("IN insertPolicyCheckListMaster() insert query1 ### "+ insertPrepStmtObject.printQuery());
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In insertPolicyCheckListMaster......................"+ status);
				logger.info("insertPrepStmtObject...."+insertPrepStmtObject.printQuery());
		  }else {
			  status = false;
		  }
				

		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;

	}
	

	public ArrayList<pcdMasterVo> searchPolicyListData(Object ob) {
			String productId = "";
			String schemeId = "";
			String stage= "";
			String ruleCode = "";
			String ruleDesc = "";
			String action = "";
			String appLevel = "";
			int count = 0;
			int startRecordIndex=0;
			int endRecordIndex = no;
			StringBuffer bufInsSqlTmp =  new StringBuffer();
			ArrayList header = new ArrayList();
			ArrayList searchlist = new ArrayList();
			pcdMasterVo pcdMasterVo = (pcdMasterVo) ob;
			ArrayList<pcdMasterVo> detailList = new ArrayList<pcdMasterVo>();
			try {
				logger.info("In searchPolicyListData().....................................Dao Impl");
				
				productId = StringEscapeUtils.escapeSql(CommonFunction.checkNull(pcdMasterVo.getLbxProductID()).trim());
				schemeId = StringEscapeUtils.escapeSql(CommonFunction.checkNull(pcdMasterVo.getLbxSchemeID()).trim());
				stage = StringEscapeUtils.escapeSql(CommonFunction.checkNull(pcdMasterVo.getLbxPCDStage()).trim());
				
				boolean appendSQL = false;
				
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append("select DISTINCT pm.PRODUCT_DESC,pcm.product_id,sm.SCHEME_DESC,pcm.scheme_id,cs.STAGE_DESCRIPTION," +
						"if(pcm.REC_STATUS='A','Active','Inactive')as REC_STATUS,pcm.STAGE_ID  from cr_policy_check_m pcm left outer join cr_product_m pm on " +
						"pcm.PRODUCT_ID=pm.PRODUCT_ID left outer join cr_scheme_m sm on pcm.SCHEME_ID=sm.SCHEME_ID " +
						"left outer join policy_check_stages cs on cs.STAGE_ID=pcm.stage_id ");

				if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(pcdMasterVo.getLbxProductID()).trim()).equalsIgnoreCase("")))
						&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(pcdMasterVo.getLbxSchemeID())).trim().equalsIgnoreCase("")))
						&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(pcdMasterVo.getLbxPCDStage())).trim().equalsIgnoreCase("")))){
					//bufInsSql.append("WHERE pcm.product_id ='"+productId+"' and pcm.scheme_id='"+ schemeId+"' and pcm.stage_id='"+ stage+"' group by product_desc");
					appendSQL = true;
				}

				if (((pcdMasterVo.getLbxProductID().equalsIgnoreCase(""))) || ((pcdMasterVo.getLbxSchemeID().equalsIgnoreCase(""))) || ((pcdMasterVo.getStage().equalsIgnoreCase("")))) {
					appendSQL = true;
				}
				bufInsSqlTmp.append("SELECT COUNT(1) FROM ( select DISTINCT pm.PRODUCT_DESC,pcm.product_id,sm.SCHEME_DESC,pcm.scheme_id,cs.STAGE_DESCRIPTION," +
						"if(pcm.REC_STATUS='A','Active','Inactive')as REC_STATUS,pcm.STAGE_ID  from cr_policy_check_m pcm left outer join cr_product_m pm on " +
						"pcm.PRODUCT_ID=pm.PRODUCT_ID left outer join cr_scheme_m sm on pcm.SCHEME_ID=sm.SCHEME_ID " +
						"left outer join policy_check_stages cs on cs.STAGE_ID=pcm.stage_id ");

				//bufInsSqlTmp.append("SELECT COUNT(1) FROM ( select pm.PRODUCT_DESC,pcm.product_id,sm.SCHEME_DESC,pcm.scheme_id,cs.STAGE_DESCRIPTION,pcm.rule_code,pcm.policy_type,pcm.approval_level,if(pcm.REC_STATUS='A','Active','Inactive')as REC_STATUS  from cr_policy_check_m pcm left outer join cr_product_m pm on pcm.PRODUCT_ID=pm.PRODUCT_ID left outer join cr_scheme_m sm on pcm.SCHEME_ID=sm.SCHEME_ID left outer join policy_check_stages cs on cs.STAGE_ID=pcm.stage_id ");

				if (appendSQL) {
					logger.info("In Where Clause");
					bufInsSql.append(" WHERE ");
					bufInsSqlTmp.append(" WHERE ");
				}

				if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(pcdMasterVo.getLbxProductID())).trim().equalsIgnoreCase("")))) {
					bufInsSql.append(" pcm.product_id='"+ StringEscapeUtils.escapeSql(pcdMasterVo.getLbxProductID()).trim()+ "' AND");
					bufInsSqlTmp.append(" pcm.product_id='"+ StringEscapeUtils.escapeSql(pcdMasterVo.getLbxProductID()).trim()+ "' AND");
					appendSQL = true;

				}
				if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(pcdMasterVo.getLbxSchemeID())).trim().equalsIgnoreCase("")))) {
					bufInsSql.append(" pcm.scheme_id='"+ StringEscapeUtils.escapeSql(pcdMasterVo.getLbxSchemeID()).trim() + "' AND");
					bufInsSqlTmp.append(" pcm.scheme_id='"+ StringEscapeUtils.escapeSql(pcdMasterVo.getLbxSchemeID()).trim() +"' AND");
					appendSQL = true;

				}

				if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(pcdMasterVo.getStage())).trim().equalsIgnoreCase("")))) {
					bufInsSql.append(" pcm.stage_id ='"+ StringEscapeUtils.escapeSql(pcdMasterVo.getLbxPCDStage()).trim() + "' AND");
					bufInsSqlTmp.append(" pcm.stage_id ='"+ StringEscapeUtils.escapeSql(pcdMasterVo.getLbxPCDStage()).trim() + "' AND");
					appendSQL = true;

				}
				
				bufInsSql.replace(bufInsSql.lastIndexOf("AND"),bufInsSql.lastIndexOf("AND")+3, " ")	;
				bufInsSqlTmp.replace(bufInsSqlTmp.lastIndexOf("AND"),bufInsSqlTmp.lastIndexOf("AND")+3, " ")	;
				logger.info("bufInsSql"+bufInsSql.toString() );
				
				bufInsSql.append(" group by product_id,scheme_id");
				bufInsSqlTmp.append(" group by product_id,scheme_id ) abc");
				
				if (appendSQL) {
					logger.info("In appendSQL true---- ");
					String tmp = bufInsSql.toString();
					String bufInsSqlTemp = bufInsSqlTmp.toString();
//					logger.info("In In getexistingData() ## tmp ## " + tmp);
					logger.info("In appendSQL true----  in check index Of"+ tmp.lastIndexOf("AND") + "------"+ (tmp.length() - 3));
					
					if (tmp.lastIndexOf("AND") == (tmp.length() - 3)) { 
						
//						logger.info("In appendSQL true----  in check index Of");
						
						tmp = (tmp).substring(0, (tmp.length() - 4));
						bufInsSqlTemp = (bufInsSqlTemp).substring(0, (bufInsSqlTemp.length() - 4));
						
						logger.info("In getexistingData()  Query...if()." + tmp);
						
						searchlist = ConnectionDAOforEJB.sqlSelect(tmp);
						count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTemp));
					} else {
								logger.info("search Query...tmp." + tmp);
								count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTemp));
								
								searchlist = ConnectionDAOforEJB.sqlSelect(tmp);
							}
					}else {
						logger.info("search Query...else-------." + bufInsSql);
						logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTmp.toString());
			            
						
						if((productId.trim()==null && schemeId.trim()==null && schemeId.trim()==null && stage.trim()==null) 
							|| (productId.trim().equalsIgnoreCase("") && schemeId.trim().equalsIgnoreCase("") && schemeId.trim().equalsIgnoreCase("") && stage.trim().equalsIgnoreCase("") || pcdMasterVo.getCurrentPageLink()>1))
						{
						
						logger.info("current PAge Link no .................... "+pcdMasterVo.getCurrentPageLink());
						if(pcdMasterVo.getCurrentPageLink()>1)
						{
							startRecordIndex = (pcdMasterVo.getCurrentPageLink()-1)*no;
							endRecordIndex = no;
							logger.info("startRecordIndex .................... "+startRecordIndex);
							logger.info("endRecordIndex .................... "+endRecordIndex);
						}
						
						bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
						
						}
						logger.info("In getexistingData()  Query...tmp." + bufInsSql);
						
						searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
					}
				logger.info("IN searchPolicyListData() search query1 ### "+ bufInsSql.toString());

				for (int i = 0; i < searchlist.size(); i++) {

					ArrayList data = (ArrayList) searchlist.get(i);
					if (data.size() > 0) {
						pcdMasterVo docMVO = new pcdMasterVo();
						
						
						docMVO.setProduct("<a href=policyChecklistEditMode.do?method=openForEditting&productId="+(CommonFunction.checkNull(data.get(1)).toString()).trim()+ "" +
								"&schemeId="+(CommonFunction.checkNull(data.get(3)).toString()).trim()+ "" +
								"&stage="+(CommonFunction.checkNull(data.get(6)).toString()).trim()+ ">"+ (CommonFunction.checkNull(data.get(0)).toString()).trim() + "</a>");
						docMVO.setLbxProductID(CommonFunction.checkNull(data.get(1)).toString().trim());
						docMVO.setScheme(CommonFunction.checkNull(data.get(2)).toString());
						docMVO.setLbxSchemeID(CommonFunction.checkNull(data.get(3)).toString());
						docMVO.setStage(CommonFunction.checkNull(data.get(4)).toString());
						//docMVO.setRuleCode(CommonFunction.checkNull(data.get(5)).toString());

//						docMVO.setAction(CommonFunction.checkNull(data.get(5)).toString());
						//docMVO.setAppLevel(CommonFunction.checkNull(data.get(6)).toString());
						docMVO.setStatus(CommonFunction.checkNull(data.get(5)).toString());
						docMVO.setTotalRecordSize(count);
						
						detailList.add(docMVO);
					}
				}
				
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
//			if(searchlist.size()==0)
//			{
//				pcdMasterVo docMVO = new pcdMasterVo();
//				docMVO.setTotalRecordSize(count);
//				detailList.add(docMVO);
////				request.setAttribute("flag","yes");
//				logger.info("Detail List when searchList is null: "+detailList.size());
//			}
			return detailList;
		}
	
	
	public ArrayList<pcdMasterVo> searchPolicyListDataForEditting(Object ob,String product,String scheme, String stageId) {
		String productId = product;
		String schemeId = scheme;
		String stage= stageId;
		int count = 0;
		int startRecordIndex = 0;
		int endRecordIndex = no;
		StringBuffer bufInsSqlTmp =  new StringBuffer();
		ArrayList header = new ArrayList();
		ArrayList searchlist = new ArrayList();
		pcdMasterVo pcdMasterVo = (pcdMasterVo) ob;
		ArrayList<pcdMasterVo> detailList = new ArrayList<pcdMasterVo>();
		try {
			logger.info("In searchDocChildData().....................................Dao Impl");
			
			
			boolean appendSQL = false;
			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql.append("select pm.PRODUCT_DESC,pcm.product_id,sm.SCHEME_DESC,pcm.scheme_id,cs.STAGE_DESCRIPTION,pcm.rule_code," +
							"pcm.policy_type,pcm.approval_level,if(pcm.REC_STATUS='A','on','off')as REC_STATUS,rm.RULE_DESCRIPTION,pcm.STAGE_ID,pcm.RULE_DTL_ID from cr_policy_check_m pcm left outer join cr_product_m pm on " +
							"pcm.PRODUCT_ID=pm.PRODUCT_ID left outer join cr_scheme_m sm on pcm.SCHEME_ID=sm.SCHEME_ID " +
							"left outer join policy_check_stages cs on cs.STAGE_ID=pcm.stage_id " +
							"left outer JOIN cr_rule_m rm on rm.RULE_CODE=pcm.RULE_CODE where pcm.product_id ='"+productId+ "' and pcm.scheme_id='"+schemeId+"' and pcm.stage_id='"+stage+"'");
			
			
			bufInsSqlTmp.append("select count(1) from cr_policy_check_m pcm left outer join cr_product_m pm on " +
					"pcm.PRODUCT_ID=pm.PRODUCT_ID left outer join cr_scheme_m sm on pcm.SCHEME_ID=sm.SCHEME_ID " +
					"left outer join policy_check_stages cs on cs.STAGE_ID=pcm.stage_id " +
					"left outer JOIN cr_rule_m rm on rm.RULE_CODE=pcm.RULE_CODE where pcm.product_id ='"+productId+ "' and pcm.scheme_id='"+schemeId+"' and pcm.stage_id='"+stage+"'");
	
						
			logger.info("query : "+bufInsSql);
			
			count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTmp.toString()));
			logger.info("In searchPolicyListDataForEditting method: Count query : "+bufInsSqlTmp.toString());


			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("searchRuleMaster " + searchlist.size());
			
			for (int i = 0; i < searchlist.size(); i++) {

				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					pcdMasterVo pcdMVO = new pcdMasterVo();
					
					pcdMVO.setProduct(CommonFunction.checkNull(data.get(0)).toString());
					pcdMVO.setLbxProductID(CommonFunction.checkNull(data.get(1)).toString().trim());
					pcdMVO.setScheme(CommonFunction.checkNull(data.get(2)).toString());
					pcdMVO.setLbxSchemeID(CommonFunction.checkNull(data.get(3)).toString());
					pcdMVO.setStage(CommonFunction.checkNull(data.get(4)).toString());
					pcdMVO.setRuleCode(CommonFunction.checkNull(data.get(5)).toString());
					pcdMVO.setAction(CommonFunction.checkNull(data.get(6)).toString());
					pcdMVO.setAppLevel(CommonFunction.checkNull(data.get(7)).toString());
					pcdMVO.setStatus(CommonFunction.checkNull(data.get(8)).toString());
					pcdMVO.setRuleDesc(CommonFunction.checkNull(data.get(9)).toString());
					pcdMVO.setLbxPCDStage(CommonFunction.checkNull(data.get(10)).toString());
					pcdMVO.setPcdCheckId(CommonFunction.checkNull(data.get(11)).toString());
					pcdMVO.setIdCount(CommonFunction.checkNull(data.get(11)).toString());
					pcdMVO.setTotalRecordSize(count);
					detailList.add(pcdMVO);
					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return detailList;
	}

	
	public boolean updatePolicyCheckListMaster(String[] pcdCheckId,String[] ruleCodeList,String[] ruleDescList,  
											   String[] actionList, String[] appLevelList,
											   pcdMasterVo vo) {

		StringBuffer bufInsSql = null;
		ArrayList<PrepStmtObject> qryList = null;		
		
		boolean status = false;
		logger.info("In insertPolicyCheckListMaster.....................................");

		qryList = new ArrayList<PrepStmtObject>();
		PrepStmtObject insertPrepStmtObject = null;
		try {
					
					logger.info(" docIdList value:------"+pcdCheckId.length);
							bufInsSql = new StringBuffer();
							insertPrepStmtObject = new PrepStmtObject();

							bufInsSql.append("DELETE FROM cr_policy_check_m WHERE product_id=? and scheme_id=? and stage_id=?");
							if (CommonFunction.checkNull(vo.getLbxProductID()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(vo.getLbxProductID().toUpperCase().trim());

							if (CommonFunction.checkNull(vo.getLbxSchemeID()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(vo.getLbxSchemeID().toUpperCase().trim());

							if (CommonFunction.checkNull(vo.getLbxPCDStage()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(vo.getLbxPCDStage().toUpperCase().trim());
							insertPrepStmtObject.setSql(bufInsSql.toString());
							qryList.add(insertPrepStmtObject);
							logger.info("delete query:-------"+ insertPrepStmtObject.printQuery());


					insertPrepStmtObject=null;
					bufInsSql=null;
					for (int i = 0; i < pcdCheckId.length; i++) {
					bufInsSql = new StringBuffer();
					insertPrepStmtObject = new PrepStmtObject();

					bufInsSql.append(" insert into cr_policy_check_m(PRODUCT_ID,SCHEME_ID,STAGE_ID,RULE_CODE,POLICY_TYPE,APPROVAL_LEVEL,REC_STATUS,MAKER_ID,MAKER_DATE)");
					bufInsSql.append(" values ( ");
					bufInsSql.append(" ?,");
					bufInsSql.append(" ?,");
					bufInsSql.append(" ?,");
					bufInsSql.append(" ?,");
					bufInsSql.append(" ?,");
					bufInsSql.append(" ?,");
					bufInsSql.append(" ?,");
					bufInsSql.append(" ?,");
					bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+ dateFormatWithTime+ "'),INTERVAL CURTIME() HOUR_SECOND) )");

					if (CommonFunction.checkNull(vo.getLbxProductID()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getLbxProductID().toUpperCase().trim());

					if (CommonFunction.checkNull(vo.getLbxSchemeID()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getLbxSchemeID().toUpperCase().trim());

					if (CommonFunction.checkNull(vo.getLbxPCDStage()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getLbxPCDStage().toUpperCase().trim());

					if (CommonFunction.checkNull(ruleCodeList[i]).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(ruleCodeList[i].toUpperCase().trim());

					if (CommonFunction.checkNull(actionList[i]).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else if (actionList[i].equalsIgnoreCase("S")) {
						insertPrepStmtObject.addString("S");
					} else if (actionList[i].equalsIgnoreCase("D")) {
						insertPrepStmtObject.addString("D");
					}

					if (CommonFunction.checkNull(appLevelList[i]).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(appLevelList[i].toUpperCase().trim());

					if (CommonFunction.checkNull(vo.getStatus())
							.equalsIgnoreCase("on"))
						insertPrepStmtObject.addString("A");
					else
						insertPrepStmtObject.addString("X");

					if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getMakerId());
					
					if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getMakerDate());

					insertPrepStmtObject.setSql(bufInsSql.toString());
					
					qryList.add(insertPrepStmtObject);
					
					logger.info("IN insertPolicyCheckListMaster() insert query1 ### "+ insertPrepStmtObject.printQuery());
				}
				
			
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				
			logger.info("In insertPolicyCheckListMaster......................"+ status);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;

	}


}


