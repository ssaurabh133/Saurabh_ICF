package com.repo.daoImplMSSQL;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.connect.CommonFunction;
import com.connect.ConnectionDAOforEJB;
import com.connect.PrepStmtObject;
import com.repo.dao.RepoMasterDAO;
import com.repo.vo.RepoAssetChecklistVo;
import com.repo.vo.StockyardMasterVo;



public class MSSQLRepoMasterDaoImpl implements RepoMasterDAO{

	private static final Logger logger = Logger.getLogger(MSSQLRepoMasterDaoImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
	String dateFormat = resource.getString("lbl.dateInDao");
	String dbo=resource.getString("lbl.dbPrefix");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	
	//Richa Work space start
	
	//Stockyard master start
	
	public ArrayList searchStockyardMasterData(Object ob) {
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		String lbxUserId = null;
		String stockyardDesc = null;
		ArrayList searchlist = new ArrayList();
		StockyardMasterVo StockyardMasterVo = (StockyardMasterVo) ob;
		ArrayList<StockyardMasterVo> detailList = new ArrayList<StockyardMasterVo>();
		try {

			logger.info("In searchStockyardMasterData()..............inside ejb server file.......................Dao Impl");
			
			lbxUserId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(StockyardMasterVo.getLbxUserId())).trim());
			stockyardDesc = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(StockyardMasterVo.getStockyardDesc())).trim());
           	StringBuilder bufInsSql = new StringBuilder();
			StringBuilder bufInsSqlTempCount = new StringBuilder();
			bufInsSql.append("SELECT STOCKYARD_CODE,STOCKYARD_DESC,");
			bufInsSql.append("case when REC_STATUS='A' then 'Active' else 'Inactive' end as REC_STATUS ");
			bufInsSql.append(" FROM REPO_STOCKYARD_DEF_M ");
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM REPO_STOCKYARD_DEF_M ");
			bufInsSql.append(" WHERE 5=5 ");
			bufInsSqlTempCount.append(" WHERE 5=5 ");

			 if (!lbxUserId.equals("")) {
				bufInsSql.append(" AND STOCKYARD_CODE = '" + lbxUserId + "' ");
				bufInsSqlTempCount.append(" AND STOCKYARD_CODE = '" + lbxUserId + "' ");
			}
			 if (!stockyardDesc.equals("")) {
				bufInsSql.append(" AND STOCKYARD_DESC like '%" + stockyardDesc + "%' ");
				bufInsSqlTempCount.append(" AND STOCKYARD_DESC like '%" + stockyardDesc + "%' ");
			}
			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            
			count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			

				logger.info("current PAge Link no .................... "+StockyardMasterVo.getCurrentPageLink());
				if(StockyardMasterVo.getCurrentPageLink()>1)
				{
					startRecordIndex = (StockyardMasterVo.getCurrentPageLink()-1)*no;
					endRecordIndex = no;
					logger.info("startRecordIndex .................... "+startRecordIndex);
					logger.info("endRecordIndex .................... "+endRecordIndex);
				}
				//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
				bufInsSql.append(" ORDER BY STOCKYARD_CODE OFFSET ");
				bufInsSql.append(startRecordIndex);
				bufInsSql.append(" ROWS FETCH next ");
				bufInsSql.append(endRecordIndex);
				bufInsSql.append(" ROWS ONLY ");
							

			logger.info("query : "+bufInsSql);

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("IN searchStockyardMasterData() search query1 ### "+ bufInsSql.toString());
			
			logger.info("searchStockyardMasterData " + searchlist.size());

			for (int i = 0; i < searchlist.size(); i++) {
				// logger.info("searchCaseTypeMasterDataList "+searchlist.get(i).toString());

				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					StockyardMasterVo deptMVO = new StockyardMasterVo();
					deptMVO.setStockyard("<a href=repoStockyardMasterDispatch.do?method=openEditStockyardMaster&stockyard="
									+ CommonFunction.checkNull(data.get(0)).toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
					//deptMVO.setCaseTypeCode(CommonFunction.checkNull(data.get(0)).toString());
					deptMVO.setStockyardDesc(CommonFunction.checkNull(data.get(1)).toString());
					deptMVO.setRecStatus(CommonFunction.checkNull(data.get(2)).toString());
					deptMVO.setTotalRecordSize(count);
					detailList.add(deptMVO);
				}

			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	
       finally{
	searchlist.clear();
	searchlist = null;
	
         }
		return detailList;
	}
	
	public boolean insertStockyardMaster(Object ob) {
		StockyardMasterVo vo = (StockyardMasterVo) ob;
		boolean status = false;
		
		logger.info("In insertStockyardMaster.........inside ejb server file...........Dao Impl"
						+ vo.getRecStatus());
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";

		String query = "select STOCKYARD_CODE from REPO_STOCKYARD_DEF_M where STOCKYARD_CODE='"
				+ StringEscapeUtils.escapeSql(vo.getStockyard().trim()) + "'";
		logger.info("In insertStockyardMaster.......inside ejb server file..........Dao Impl"
						+ query);
		boolean st = ConnectionDAOforEJB.checkStatus(query);
		try {

			if (!st) {
				if (vo.getRecStatus() != null
						&& vo.getRecStatus().equals("on")) {
					stat = "A";
				} else {
					stat = "X";

				}

				logger.info("In insert Stockyard master");
				StringBuilder bufInsSql = new StringBuilder();
				bufInsSql.append("insert into REPO_STOCKYARD_DEF_M(STOCKYARD_CODE,STOCKYARD_DESC,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,"); //caseTypeCode
				bufInsSql.append(" ?,"); //caseTypeDesc
				bufInsSql.append(" ?,"); //rec_status
				bufInsSql.append(" ?,"); //makerId
				//bufInsSql.append(dbo);
				//bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				//bufInsSql.append(" ?,");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,"); //authorId
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
			
				if (CommonFunction.checkNull(vo.getLbxUserId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxUserId().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getStockyardDesc()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getStockyardDesc().toUpperCase().trim());
				

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
				
				/*
				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());
				
				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());
				
				*/

				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN insertStockyardMaster() insert query1 ### "+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In insertStockyardMaster......................"+ status);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			qryList.clear();
			qryList = null;
			
		         }
		return status;

	}
	
	public ArrayList editStockyardData(Object ob) {

		ArrayList searchlist = new ArrayList();
		StockyardMasterVo vo = (StockyardMasterVo)ob;
		ArrayList<StockyardMasterVo> caseTypeList = new ArrayList<StockyardMasterVo>();
		logger.info("ratioid in searchBenchMarkRatioEdit &***************************** = "+vo.getStockyard());

		try {
			

			StringBuilder bufInsSql = new StringBuilder();
			bufInsSql.append(" select  STOCKYARD_CODE,STOCKYARD_DESC,REC_STATUS  from REPO_STOCKYARD_DEF_M ");
			
			bufInsSql.append("  WHERE STOCKYARD_CODE='"+StringEscapeUtils.escapeSql(vo.getStockyard())+"'");
			logger.info("search Query...." + bufInsSql);

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			logger.info("IN editStockyardData() search query1 ### "+ bufInsSql.toString());
			logger.info("editStockyardData " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
				logger.info("editStockyardData " + searchlist.get(i).toString());
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					StockyardMasterVo stockyardMasterVo = new StockyardMasterVo();
					
					stockyardMasterVo.setLbxUserId(CommonFunction.checkNull(data.get(0)).toString());
					stockyardMasterVo.setStockyardDesc(CommonFunction.checkNull(data.get(1)).toString());					
					stockyardMasterVo.setRecStatus(CommonFunction.checkNull(data.get(2)).toString());
					caseTypeList.add(stockyardMasterVo);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			searchlist.clear();
			searchlist = null;
			
		         }
		return caseTypeList;
}
	
	
	
	
	public boolean updateStockyardData(Object ob) {
		StockyardMasterVo vo = (StockyardMasterVo) ob;
		String caseTypeCode = (String) vo.getLbxUserId();
		logger.info("getRecStatus():-" + vo.getRecStatus());
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList updatelist = new ArrayList();
		

		boolean status = false;
		String stat = "";

		try {
			
			if (vo.getRecStatus() != null
					&& vo.getRecStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";
			}
			StringBuilder bufInsSql = new StringBuilder();
			logger.info("In updateStockyardData..........inside ejb server file............Dao Impl");
			bufInsSql.append(" UPDATE REPO_STOCKYARD_DEF_M SET REC_STATUS=?");
			bufInsSql.append(" ,MAKER_ID=?,MAKER_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ");
			bufInsSql.append(" ,AUTHOR_ID=?,AUTHOR_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ");
			bufInsSql.append(" where STOCKYARD_CODE=?");

			
			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat);

			
			//----------------------------------
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
			//---------------------------------- 

			if (CommonFunction.checkNull(vo.getLbxUserId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getLbxUserId());

			insertPrepStmtObject.setSql(bufInsSql.toString());

			updatelist.add(insertPrepStmtObject);
			logger.info("In updateStockyardData" + bufInsSql.toString());
			
			logger.info("## In updateStockyardData() : update query (case tye master) "+insertPrepStmtObject.printQuery());
			
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);

		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		finally{
			updatelist.clear();
			updatelist = null;
			
		         }
		return status;
	}
	
	//Stockyard master end
	
	//Richa Work space end
	
	//Nazia workspace start
	
	public ArrayList searchRepoAsset(Object ob) {
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		String ProductSearchID = null;
		String Asset = null;
		
		ArrayList searchlist = new ArrayList();
		RepoAssetChecklistVo repoAssetMasterVo = (RepoAssetChecklistVo) ob;
		ArrayList<RepoAssetChecklistVo> detailList = new ArrayList<RepoAssetChecklistVo>();
		try {

			logger.info("In searchRepoAsset() ");
			
			ProductSearchID = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(repoAssetMasterVo.getLbxProductSearchID())).trim());
			Asset = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(repoAssetMasterVo.getAssetClass())).trim());
			
           	StringBuilder bufInsSql = new StringBuilder();
			StringBuilder bufInsSqlTempCount = new StringBuilder();
			bufInsSql.append("select CPM.PRODUCT_DESC, ASSET_ID,  case  RAPDM.REC_STATUS whEn 'A' THEN 'ACTIVE'WHEN 'X' THEN 'INACTIVE'  END AS REC,CPM.PRODUCT_ID from REPO_ASSET_PRODUCT_DEF_M RAPDM ");
			bufInsSql.append("INNER JOIN CR_PRODUCT_M CPM ON CPM.PRODUCT_ID = RAPDM.PRODUCT_ID");
			
			bufInsSqlTempCount.append(" select count(1)");
			bufInsSqlTempCount.append(" from REPO_ASSET_PRODUCT_DEF_M ");
			
			bufInsSql.append(" WHERE 5=5 ");
			bufInsSqlTempCount.append(" WHERE 5=5 ");
				
			
			if (!ProductSearchID.equals("")) {
					bufInsSql.append(" AND RAPDM.PRODUCT_ID = '" + ProductSearchID +  "' ");
					bufInsSqlTempCount.append(" AND PRODUCT_ID = '" + ProductSearchID +  "' ");
				}
				
				 if (!Asset.equals("")) {
					bufInsSql.append(" AND ASSET_ID='"+ Asset + "' ");
					bufInsSqlTempCount.append(" AND ASSET_ID='"+ Asset + "' ");
				}
			count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			logger.info("current PAge Link no .................... "+repoAssetMasterVo.getCurrentPageLink());
			
            
			count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
				
				if(repoAssetMasterVo.getCurrentPageLink()>1)
				{
					startRecordIndex = (repoAssetMasterVo.getCurrentPageLink()-1)*no;
					endRecordIndex = no;
					logger.info("startRecordIndex .................... "+startRecordIndex);
					logger.info("endRecordIndex .................... "+endRecordIndex);
				}
				
				bufInsSql.append(" ORDER BY RAPDM.PRODUCT_ID OFFSET ");
				bufInsSql.append(startRecordIndex);
				bufInsSql.append(" ROWS FETCH next ");
				bufInsSql.append(endRecordIndex);
				bufInsSql.append(" ROWS ONLY ");
							

				logger.info("## In searchRepoAsset(): select query for REPO_ASSET_PRODUCT_CHECKLIST_MX: ==>" +bufInsSql.toString());


			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			int size=0;
			if(searchlist!=null)
				size=searchlist.size();
			
			logger.info("searchRepoAsset " + size);

			for (int i = 0; i < size; i++) {
				
				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
			RepoAssetChecklistVo deptMVO = new RepoAssetChecklistVo();
					deptMVO.setProduct("<a href=RepoAssetChecklistDispatch.do?method=openEditRepoAsset&product="+CommonFunction.checkNull(data.get(3)).toString()+"&assetClass="
									+ CommonFunction.checkNull(data.get(1)).toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
					
					deptMVO.setAssetClass(CommonFunction.checkNull(data.get(1)).toString());
					deptMVO.setRecStatus(CommonFunction.checkNull(data.get(2)).toString());
					
					deptMVO.setTotalRecordSize(count);
					detailList.add(deptMVO);
				}

			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		finally
		{
			
			searchlist.clear();
			searchlist=null;
		}
		return detailList;
	}
		
	public boolean insertRepoAsset(Object ob,String [] checkList) {
		
		RepoAssetChecklistVo vo = (RepoAssetChecklistVo) ob;
		boolean status = false;
		
		logger.info("In insertRepoAsset.........inside ejb server file...........Dao Impl"
						+ vo.getRecStatus());
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		

		String query = "select PRODUCT_ID from REPO_ASSET_PRODUCT_DEF_M  where PRODUCT_ID='"
				+ StringEscapeUtils.escapeSql(vo.getLbxProductSearchID().trim()) + "' AND  ASSET_ID = '"+StringEscapeUtils.escapeSql(vo.getAssetClass().trim()) +"' ";
		
		boolean st = ConnectionDAOforEJB.checkStatus(query);
		try {
			logger.info("In insertRepoAsset.......inside ejb server file..........Dao Impl");
			if (!st) {
				if (vo.getRecStatus() != null
						&& vo.getRecStatus().equals("on")) {
					stat = "A";
				} else {
					stat = "X";
				}
					
				
				logger.info("In insert Repo Asset");
				StringBuilder bufInsSql = new StringBuilder();
				bufInsSql.append("insert into REPO_ASSET_PRODUCT_DEF_M (PRODUCT_ID,ASSET_ID,REC_STATUS,MAKER_ID,MAKER_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,"); //PRODUCT_ID
				bufInsSql.append(" ?,"); //ASSET_ID
				bufInsSql.append(" ?,"); //REC_STATUS
				bufInsSql.append(" ?,"); //makerId
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
			
			
				if (CommonFunction.checkNull(vo.getLbxProductSearchID()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxProductSearchID().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getAssetClass()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getAssetClass().toUpperCase().trim());
				
				
				
				if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(stat);
				
				

				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());
				
				
				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());
				
				

				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN insertRepoAsset() insert query1 ### "+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				
				int length=0;
				
				length = checkList.length;
				
				logger.info("IN insertRepoAsset(): length1: "+length);
				logger.info("IN insertRepoAsset(): length2: "+length);
					
				for (int i = 0; i < length; i++) {
					
					insertPrepStmtObject = null;
					bufInsSql = null;
					
					bufInsSql = new StringBuilder();
					insertPrepStmtObject = new PrepStmtObject();
					
					bufInsSql.append("INSERT INTO REPO_ASSET_PRODUCT_CHECKLIST_MX(PRODUCT,ASSET,CHECKLIST,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
					bufInsSql.append(" values ( ");
					bufInsSql.append(" ?,");//PRODUCT
					bufInsSql.append(" ?,");//ASSET
					bufInsSql.append(" ?,");//CHECKLIST
					bufInsSql.append(" ?,"); //makerId
					bufInsSql.append(dbo);//MAKER_DATE
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
					bufInsSql.append(" ?,");//AUTHER_ID
					bufInsSql.append(dbo);//AUTHER_DATE
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
					
					if (CommonFunction.checkNull(vo.getLbxProductSearchID())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getLbxProductSearchID()
								.toUpperCase());
					
					if (CommonFunction.checkNull(vo.getAssetClass())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getAssetClass()
								.toUpperCase());
					if (CommonFunction.checkNull(checkList[i])
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(checkList[i]
								.toUpperCase());

					if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
							""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getMakerId());
					
					
					if (CommonFunction.checkNull(vo.getMakerDate())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getMakerDate());
					
					
					if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
					""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getMakerId());
			
					if (CommonFunction.checkNull(vo.getMakerDate())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getMakerDate());
					

					insertPrepStmtObject.setSql(bufInsSql.toString());
					logger.info("IN insertRepoAsset() insert query1 ### "
							+ insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);
				}
				
				
				
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In insertRepoAsset......................"
						+ status);
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			
			qryList.clear();
			qryList=null;
		}

		return status;
		

		
	}
	
	
	public ArrayList<RepoAssetChecklistVo> openEditRepoAsset(Object ob) {
	
		ArrayList searchlist = new ArrayList();
		ArrayList<RepoAssetChecklistVo> dataList= new ArrayList<RepoAssetChecklistVo>();
		RepoAssetChecklistVo vo = (RepoAssetChecklistVo)ob;
		ArrayList<RepoAssetChecklistVo> repoAssetMasterVo = new ArrayList<RepoAssetChecklistVo>();
		logger.info("ratioid in searchBenchMarkRatioEdit &***************************** = "+vo.getLbxProductSearchID());

		try {
			logger.info("## In openEditRepoAsset(): ");

			StringBuilder bufInsSql = new StringBuilder();
			bufInsSql.append(" SELECT RAPDM.PRODUCT_ID,ASSET_ID,RAPDM.REC_STATUS,CPM.PRODUCT_DESC,GM.DESCRIPTION AS ASSETDESC,RAPDM.REC_STATUS FROM REPO_ASSET_PRODUCT_DEF_M RAPDM ");
			bufInsSql.append("  LEFT JOIN CR_PRODUCT_M CPM ON (CPM.PRODUCT_ID = RAPDM.PRODUCT_ID) ");
			bufInsSql.append(" LEFT JOIN GENERIC_MASTER GM ON (GM.VALUE = RAPDM.ASSET_ID AND GM.GENERIC_KEY = 'ASSET_CLASS') ");
			bufInsSql.append("  WHERE RAPDM.PRODUCT_ID='"+StringEscapeUtils.escapeSql(vo.getRepoProduct())+"'AND RAPDM.ASSET_ID='"+StringEscapeUtils.escapeSql(vo.getAssetClassId())+"' ");
			logger.info("## In openEditRepoAsset(): search Query...." + bufInsSql);

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			int size=0;
			if(searchlist!=null)
				size=searchlist.size();
			logger.info("IN openEditRepoAsset() search query1 ### "+ bufInsSql.toString());
			logger.info("openEditRepoAsset " + size);
			for (int i = 0; i < size; i++) {
				logger.info("openEditRepoAsset " + searchlist.get(i).toString());
				ArrayList data = (ArrayList) searchlist.get(i);
				
				if (data.size() > 0) {
			RepoAssetChecklistVo repoAssetChecklistVo = new RepoAssetChecklistVo();
			logger.info("size"+size);		
			repoAssetChecklistVo.setLbxProductSearchID(CommonFunction.checkNull(data.get(0)).toString());
			repoAssetChecklistVo.setAssetClassId(CommonFunction.checkNull(data.get(1)).toString());
			repoAssetChecklistVo.setRecStatus(CommonFunction.checkNull(data.get(2)).toString());
			repoAssetChecklistVo.setRepoProduct(CommonFunction.checkNull(data.get(3)).toString());
			repoAssetChecklistVo.setAssetClass(CommonFunction.checkNull(data.get(4)).toString());
			repoAssetChecklistVo.setRecStatus(CommonFunction.checkNull(data.get(5)).toString());
			dataList.add(repoAssetChecklistVo);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			
			searchlist.clear();
			searchlist=null;
		}
		return dataList;
	}
	
	
	
	
	public boolean updateRepoAsset(Object ob, String [] checkList) {
		RepoAssetChecklistVo vo = (RepoAssetChecklistVo) ob;
		String AssetClass = (String) vo.getAssetClass();
		logger.info("vo.getRecStatus():-" + vo.getRecStatus());
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList updatelist = new ArrayList();
		boolean status = false;
		String stat = "";
        
		try {
			logger.info("## In updateRepoAsset(): ");
			if (vo.getRecStatus() != null
					&& vo.getRecStatus().equals("on")) {
				stat = "A";
			} else {
				stat = "X";
			}
			
			
			StringBuilder bufInsSql = new StringBuilder();
			logger.info("In updateRepoAsset..........inside ejb server file............Dao Impl");
			bufInsSql.append(" UPDATE REPO_ASSET_PRODUCT_DEF_M set");
			bufInsSql.append(" REC_STATUS=?,MAKER_ID=?,MAKER_DATE=");
			bufInsSql.append(dbo);
			bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as varchar(30))),0,9) ");
			bufInsSql.append(" where PRODUCT_ID=? AND ASSET_ID=?");

			
			
			if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(stat);
			
			
			//----------------------------------
			if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerId());
			if (CommonFunction.checkNull(vo.getMakerDate())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerDate());
			//----------------------------------
			if (CommonFunction.checkNull(vo.getLbxProductSearchID()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getLbxProductSearchID());
			
			insertPrepStmtObject.setSql(bufInsSql.toString());

			if (CommonFunction.checkNull(vo.getAssetClass()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getAssetClass());
			
			insertPrepStmtObject.setSql(bufInsSql.toString());

			
			updatelist.add(insertPrepStmtObject);
			logger.info("In getListOfValues" + bufInsSql.toString());
			logger.info("## In updateRepoAsset() : updateRepoAsset: ==>> "+insertPrepStmtObject.printQuery());
			
			
			insertPrepStmtObject = null;
			bufInsSql = null;
			
			bufInsSql = new StringBuilder();
			insertPrepStmtObject = new PrepStmtObject();
			
			bufInsSql.append(" DELETE FROM REPO_ASSET_PRODUCT_CHECKLIST_MX ");
			bufInsSql.append("  WHERE PRODUCT ='"+StringEscapeUtils.escapeSql(vo.getLbxProductSearchID())+"'AND ASSET='"+StringEscapeUtils.escapeSql(vo.getAssetClassId())+"' ");
			
			
			insertPrepStmtObject.setSql(bufInsSql.toString());
			
			updatelist.add(insertPrepStmtObject);
			
			int length=0;
			
			length = checkList.length;
			
			logger.info("IN updateRepoAsset(): length1: "+length);
			
			
			
			logger.info("IN updateRepoAsset(): length2: "+length);

			for (int i = 0; i < length; i++) {
				
				insertPrepStmtObject = null;
				bufInsSql = null;
				
				bufInsSql = new StringBuilder();
				insertPrepStmtObject = new PrepStmtObject();
				
				bufInsSql.append("INSERT INTO REPO_ASSET_PRODUCT_CHECKLIST_MX(PRODUCT,ASSET,CHECKLIST,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");//PRODUCT
				bufInsSql.append(" ?,");//ASSET
				bufInsSql.append(" ?,");//CHECKLIST
				bufInsSql.append(" ?,"); //makerId
				bufInsSql.append(dbo);//MAKER_DATE
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
				bufInsSql.append(" ?,");//AUTHER_ID
				bufInsSql.append(dbo);//AUTHER_DATE
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
				
				if (CommonFunction.checkNull(vo.getLbxProductSearchID())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxProductSearchID()
							.toUpperCase());
				
				if (CommonFunction.checkNull(vo.getAssetClass())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getAssetClass()
							.toUpperCase());
				if (CommonFunction.checkNull(checkList[i])
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(checkList[i]
							.toUpperCase());

				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());
				
				
				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());
				
				
				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
				""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());
		
				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());
				

				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN insertRepoAsset() insert query1 ### "
						+ insertPrepStmtObject.printQuery());
				updatelist.add(insertPrepStmtObject);
			}
			
			
			
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);
			logger.info("In insertRepoAsset......................"
					+ status);
	

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		finally
		{
			
			updatelist.clear();
			updatelist=null;
		}
		return status;
	}
	
	public ArrayList getChecklistOnEdit(Object ob) {

		ArrayList list = new ArrayList();
		RepoAssetChecklistVo vo = (RepoAssetChecklistVo)ob;
		ArrayList<RepoAssetChecklistVo> checklist = new ArrayList<RepoAssetChecklistVo>();
		logger.info("ratioid in searchBenchMarkRatioEdit &***************************** = "+vo.getLbxProductSearchID());

		try {
			logger.info("## In getChecklistOnEdit():");

			StringBuilder bufInsSql = new StringBuilder();
			bufInsSql.append(" SELECT CHECKLIST FROM REPO_ASSET_PRODUCT_CHECKLIST_MX RAPC");
			bufInsSql.append(" where PRODUCT='" +vo.getRepoProduct()+"' AND ASSET='" +vo.getAssetClassId()+"'");
			//bufInsSql.append(" WHERE CPM.REC_STATUS='A' ");
			
			
			logger.info("IN getChecklist() search query1 ### "+ bufInsSql.toString());

			list = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			
			int size=0;
			if(list!=null)
				size= list.size();
			logger.info("getChecklist " +size);
			for (int i = 0; i < size; i++) {
				
				ArrayList data = (ArrayList) list.get(i);
				if (data.size() > 0) {
					RepoAssetChecklistVo repoAssetChecklistVo = new RepoAssetChecklistVo();
					
					repoAssetChecklistVo.setChecklist(CommonFunction.checkNull(data.get(0)).toString());
					

					checklist.add(repoAssetChecklistVo);

				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			
			list.clear();
			list=null;
		}
		return checklist;
	}
	
	public ArrayList getAssetClass() {

		ArrayList list = new ArrayList();
		ArrayList checkList = new ArrayList();
		
		

		try {
			logger.info("## In getChecklistOnEdit():");

			StringBuilder bufInsSql = new StringBuilder();
			
			bufInsSql.append(" SELECT VALUE,DESCRIPTION FROM GENERIC_MASTER WHERE GENERIC_KEY = 'ASSET_CLASS' AND REC_STATUS = 'A' ");
			
			logger.info("IN getAssetClass() query1 ### "+ bufInsSql.toString());

			list = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			
			int size=0;
			if(list!=null)
				size= list.size();
			logger.info("getChecklist " +size);
			for (int i = 0; i < size; i++) {
				//logger.info("getProduct " + list.get(i).toString());
				ArrayList data = (ArrayList) list.get(i);
				if (data.size() > 0) {
					RepoAssetChecklistVo repoAssetChecklistVo = new RepoAssetChecklistVo();
					
					repoAssetChecklistVo.setAssetClass(CommonFunction.checkNull(data.get(0)).toString());
					repoAssetChecklistVo.setAssetClassId(CommonFunction.checkNull(data.get(1)).toString());
					checkList.add(repoAssetChecklistVo);

				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return checkList;
	}
	
	//Nazia workspace end
	

		
}

