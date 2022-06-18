package com.repo.daoImplMYSQL;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.connect.CommonFunction;
import com.connect.ConnectionDAOforEJB;
import com.connect.PrepStmtObject;
import com.repo.dao.RepoTransactionDAO;
import com.repo.vo.RepoConfirmationtVo;
import com.repo.vo.RepoDetailStockyardVo;
import com.repo.vo.RepoDetailVo;
import com.repo.vo.RepoMarkingMakerVo;


public class RepoTransactionDaoImpl implements RepoTransactionDAO{

	private static final Logger logger = Logger.getLogger(RepoTransactionDaoImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
	String dateFormat = resource.getString("lbl.dateInDao");
	String dbo=resource.getString("lbl.dbPrefix");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	
	
	public ArrayList searchRepoDetailbyAgency(Object ob) {
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		String repoId = "";
		String loanId = "";
		ArrayList searchlist = new ArrayList();
		RepoDetailVo repoVo = (RepoDetailVo) ob;
		ArrayList<RepoDetailVo> detailList = new ArrayList<RepoDetailVo>();
		try {

			logger.info("## In searchRepoDetailbyAgency () :");
			
			repoId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(repoVo.getSearchRepoId())).trim());
			loanId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(repoVo.getLbxLoanId())).trim());
			
			
			logger.info("## In searchRepoDetailbyAgency () : repoId : ==>> "+repoId);
			logger.info("## In searchRepoDetailbyAgency () : loanId : ==>> "+loanId);
			
           	StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			
			bufInsSql.append(" SELECT REPO_ID ,RCD.LOAN_ID,CLD.LOAN_NO,RCD.CUSTOMER_ID,GCM.CUSTOMER_NAME,RCD.PRODUCT_ID,CPM.PRODUCT_DESC,RCD.ASSET_CLASS,GM.VALUE AS ASSET_CALSS_DESC ");
			bufInsSql.append(" FROM REPO_CASE_DTL RCD ");
			bufInsSql.append(" JOIN CR_LOAN_DTL  CLD ON (CLD.LOAN_ID = RCD.LOAN_ID) ");
			bufInsSql.append(" LEFT JOIN GCD_CUSTOMER_M GCM ON (GCM.CUSTOMER_ID = RCD.CUSTOMER_ID) ");
			bufInsSql.append(" LEFT JOIN CR_PRODUCT_M CPM ON (CPM.PRODUCT_ID = RCD.PRODUCT_ID) ");
			bufInsSql.append(" LEFT JOIN GENERIC_MASTER GM ON (GM.VALUE = RCD.ASSET_CLASS AND GM.GENERIC_KEY = 'ASSET_CLASS') ");
			bufInsSql.append(" WHERE AGENCY_FLAG = 'N' AND AGENCY_NAME IN (SELECT AGENCY_CODE FROM COM_AGENCY_USER_MAPPING WHERE USER_ID = '"+CommonFunction.checkNull(repoVo.getMakerId())+"' ) AND RCD.BRANCH_ID='"+CommonFunction.checkNull(repoVo.getBranchId())+"' ");
			
			
			bufInsSqlTempCount.append(" SELECT COUNT(1) ");
			bufInsSqlTempCount.append(" FROM REPO_CASE_DTL RCD ");
			bufInsSqlTempCount.append(" JOIN CR_LOAN_DTL  CLD ON (CLD.LOAN_ID = RCD.LOAN_ID) ");
			bufInsSqlTempCount.append(" LEFT JOIN GCD_CUSTOMER_M GCM ON (GCM.CUSTOMER_ID = RCD.CUSTOMER_ID) ");
			bufInsSqlTempCount.append(" LEFT JOIN CR_PRODUCT_M CPM ON (CPM.PRODUCT_ID = RCD.PRODUCT_ID) ");
			bufInsSqlTempCount.append(" LEFT JOIN GENERIC_MASTER GM ON (GM.VALUE = RCD.ASSET_CLASS AND GM.GENERIC_KEY = 'ASSET_CLASS')  ");
			bufInsSqlTempCount.append(" WHERE AGENCY_FLAG = 'N' AND AGENCY_NAME IN (SELECT AGENCY_CODE FROM COM_AGENCY_USER_MAPPING WHERE USER_ID = '"+CommonFunction.checkNull(repoVo.getMakerId())+"' ) AND RCD.BRANCH_ID='"+CommonFunction.checkNull(repoVo.getBranchId())+"' ");
			
			if(!repoId.equals("")) {
				bufInsSql.append(" AND RCD.REPO_ID = '" + repoId + "' ");
				bufInsSqlTempCount.append(" AND RCD.REPO_ID = '" + repoId + "' ");
			}
			if(!loanId.equals("")) {
				bufInsSql.append(" AND RCD.LOAN_ID = '" + loanId + "' ");
				bufInsSqlTempCount.append(" AND RCD.LOAN_ID = '" + loanId + "' ");
			}
			
			logger.info("## In searchRepoDetailbyAgency () : search query : ==>> "+bufInsSql.toString());
			logger.info("## In searchRepoDetailbyAgency () : count query : ==>> "+bufInsSqlTempCount.toString());
            
			
			bufInsSql.append(" ORDER BY REPO_ID ");
			
			count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));
			
			logger.info("## In searchRepoDetailbyAgency () : repoVo.getCurrentPageLink() : ==>> "+repoVo.getCurrentPageLink());
			
			if(repoVo.getCurrentPageLink()>1)
			{
				startRecordIndex = (repoVo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				
				logger.info("startRecordIndex .................."+startRecordIndex);
				logger.info("endRecordIndex ...................."+endRecordIndex);
			}
			
			bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
			
			/*bufInsSql.append(" ORDER BY REPO_ID OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");*/
							

			logger.info("## In searchRepoDetailbyAgency () : search query : ==>> "+bufInsSql.toString());

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			
			int searchListSize =  searchlist.size();
			
		
			logger.info("## In searchRepoDetailbyAgency () : searchListSize : ==>> "+searchListSize);
			
			

			for (int i = 0; i < searchListSize; i++) {

				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					RepoDetailVo vo = new RepoDetailVo();
					
					vo.setRepoId("<a href=repoDetailbyAgencyDispatchAction.do?method=openEditRepoDetailbyAgency&repoId="
									+ CommonFunction.checkNull(data.get(0)).toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
					vo.setLoanNo(CommonFunction.checkNull(data.get(2)).toString());
					vo.setCustomerName(CommonFunction.checkNull(data.get(4)).toString());
					vo.setProductDesc(CommonFunction.checkNull(data.get(6)).toString());
					vo.setAssetDesc(CommonFunction.checkNull(data.get(8)).toString());
					vo.setTotalRecordSize(count);
					detailList.add(vo);
				}

			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		return detailList;
	}
	
	public ArrayList getRepoDetailbyAgencyForEdit(Object ob) {

		ArrayList queryList = new ArrayList();
		
		RepoDetailVo vo = (RepoDetailVo)ob;
		
		ArrayList<RepoDetailVo> repoAgencyList = new ArrayList<RepoDetailVo>();
		

		try {
			
			logger.info("## In getRepoDetailbyAgencyForEdit() : ");
			
			
			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql.append(" SELECT RCD.REPO_ID,RCD.LOAN_ID,CLD.LOAN_NO,RCD.CUSTOMER_ID,GCM.CUSTOMER_NAME,CPM.PRODUCT_ID,CPM.PRODUCT_DESC,RCD.SCHEME_ID,CSM.SCHEME_DESC,RCD.ASSET_CLASS,RCD.ASSET_DESCRIPTION,RCD.ASSET_COST, " );
			bufInsSql.append(" REPOSSESSED_BY,RCD.AGENCY_NAME AGENCY_ID,CAM.AGENCY_NAME AS AGENCY_DESC,REPO_ADDRESS1,REPO_ADDRESS2,RCD.STATE,SM.STATE_DESC,RCD.DISTRICT,CDM.DISTRICT_DESC,REPO_FROM,RELATION_TO_CUSTOMER, ");
			
			bufInsSql.append("DATE_FORMAT(RCD.REPO_DATE,'"+ dateFormat +"'), ");			
			bufInsSql.append("RCD.REPO_TIME,RCD.STOCKYARD AS STOCKYARD_ID,SUM1.USER_NAME AS STOCKYARD_NAME,AGENCY_REMARKS FROM REPO_CASE_DTL RCD ");
			bufInsSql.append(" JOIN CR_LOAN_DTL CLD ON (CLD.LOAN_ID = RCD.LOAN_ID) ");
			bufInsSql.append(" LEFT JOIN GCD_CUSTOMER_M GCM ON (GCM.CUSTOMER_ID = RCD.CUSTOMER_ID) ");
			bufInsSql.append(" LEFT JOIN CR_PRODUCT_M CPM ON (CPM.PRODUCT_ID = RCD.PRODUCT_ID) ");
			bufInsSql.append(" LEFT JOIN CR_SCHEME_M CSM ON (CSM.SCHEME_ID = RCD.SCHEME_ID) ");
			bufInsSql.append(" LEFT JOIN COM_AGENCY_M CAM ON (CAM.AGENCY_CODE = RCD.AGENCY_NAME) ");
			bufInsSql.append(" LEFT JOIN COM_STATE_M SM ON (SM.STATE_ID = RCD.STATE) ");
			bufInsSql.append(" LEFT JOIN COM_DISTRICT_M CDM ON (CDM.DISTRICT_ID = RCD.DISTRICT) ");
			bufInsSql.append(" LEFT JOIN SEC_USER_M SUM1 ON (SUM1.USER_ID = RCD.STOCKYARD) ");
			bufInsSql.append(" WHERE REPO_ID ='"+StringEscapeUtils.escapeSql(vo.getRepoId())+"'");
			
			logger.info("## In getRepoDetailbyAgencyForEdit() : Select query for Repo detail by agency : ==>> "+bufInsSql.toString());

			queryList = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			
			int queryListSize = queryList.size();
			
			logger.info("## In getRepoDetailbyAgencyForEdit() : queryListSize : ==>> "+queryListSize);
			
			for (int i = 0; i < queryListSize; i++) {
				
				ArrayList data = (ArrayList) queryList.get(i);
				
				if (data.size() > 0) {
					
					RepoDetailVo repoVo = new RepoDetailVo();
					
					repoVo.setRepoId(CommonFunction.checkNull(data.get(0)).toString());
					repoVo.setLoanId(CommonFunction.checkNull(data.get(1)).toString());
					repoVo.setLoanNo(CommonFunction.checkNull(data.get(2)).toString());
					repoVo.setCustomerId(CommonFunction.checkNull(data.get(3)).toString());	
					repoVo.setCustomerName(CommonFunction.checkNull(data.get(4)).toString());	
					repoVo.setProductId(CommonFunction.checkNull(data.get(5)).toString());
					repoVo.setProductDesc(CommonFunction.checkNull(data.get(6)).toString());
					repoVo.setSchemeDesc(CommonFunction.checkNull(data.get(8)).toString());
					repoVo.setAssetClass(CommonFunction.checkNull(data.get(9)).toString());
					repoVo.setAssetDesc(CommonFunction.checkNull(data.get(10)).toString());
					if(CommonFunction.checkNull(data.get(11)).trim().equalsIgnoreCase(""))
					{
						repoVo.setAssetCost("0");
					}
					else
					{
						repoVo.setAssetCost(myFormatter.format(myFormatter.parse(CommonFunction.checkNull(data.get(11)))));
					}
					//repoVo.setAssetCost(CommonFunction.checkNull(data.get(11)).toString());
					repoVo.setRepossessedBy(CommonFunction.checkNull(data.get(12)).toString());
					repoVo.setAgencyId(CommonFunction.checkNull(data.get(13)).toString());
					repoVo.setAgencyName(CommonFunction.checkNull(data.get(14)).toString());
					repoVo.setRepoAdd1(CommonFunction.checkNull(data.get(15)).toString());
					repoVo.setRepoAdd2(CommonFunction.checkNull(data.get(16)).toString());
					repoVo.setLbxState(CommonFunction.checkNull(data.get(17)).toString());
					repoVo.setStateDesc(CommonFunction.checkNull(data.get(18)).toString());
					repoVo.setLbxDistrict(CommonFunction.checkNull(data.get(19)).toString());
					repoVo.setDistrictDesc(CommonFunction.checkNull(data.get(20)).toString());
					repoVo.setRepoFrom(CommonFunction.checkNull(data.get(21)).toString());
					repoVo.setRelationToCustomer(CommonFunction.checkNull(data.get(22)).toString());
					repoVo.setRepoDate(CommonFunction.checkNull(data.get(23)).toString());
					repoVo.setRepoTime(CommonFunction.checkNull(data.get(24)).toString());
					repoVo.setLbxStockYard(CommonFunction.checkNull(data.get(25)).toString());
					repoVo.setStockYardDesc(CommonFunction.checkNull(data.get(26)).toString());
					repoVo.setAgencyRemarks(CommonFunction.checkNull(data.get(27)).toString());
					repoAgencyList.add(repoVo);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return repoAgencyList;
   }
	
	public ArrayList getCheckListDetail(Object ob) {

		ArrayList queryList = new ArrayList();
		
		RepoDetailVo vo = (RepoDetailVo)ob;
		
		ArrayList<RepoDetailVo> repoCheckList = new ArrayList<RepoDetailVo>();
		

		try {
			
			logger.info("## In getCheckListDetail() : ");
			
			
			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql.append(" SELECT RAPCM.CHECKLIST,RAACD.CHECKLIST_STATUS,RAACD.REMARKS FROM REPO_ASSET_PRODUCT_CHECKLIST_MX RAPCM " );
			bufInsSql.append(" JOIN REPO_CASE_DTL RCD ON (RCD.PRODUCT_ID = RAPCM.PRODUCT AND RCD.ASSET_CLASS = RAPCM.ASSET) ");
			bufInsSql.append(" LEFT JOIN REPO_AGENCY_ASSET_CHECKLIST_DTL RAACD ON (RAACD.ASSET = RAPCM.ASSET AND RAACD.PRODUCT = RAPCM.PRODUCT AND RAACD.ASSET_CHECKLIST = RAPCM.CHECKLIST AND  RAACD.REPO_ID = RCD.REPO_ID) ");
			bufInsSql.append(" WHERE RCD.REPO_ID ='"+StringEscapeUtils.escapeSql(vo.getRepoId())+"'");
			
			logger.info("## In getCheckListDetail() : Select query for check list detail : ==>> "+bufInsSql.toString());

			queryList = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			
			int queryListSize = queryList.size();
			
			logger.info("## In getCheckListDetail() : queryListSize : ==>> "+queryListSize);
			
			for (int i = 0; i < queryListSize; i++) {
				
				ArrayList data = (ArrayList) queryList.get(i);
				
				if (data.size() > 0) {
					
					RepoDetailVo repoVo = new RepoDetailVo();
					
					repoVo.setCheckList(CommonFunction.checkNull(data.get(0)).toString());
					repoVo.setCheckListStatus(CommonFunction.checkNull(data.get(1)).toString());
					repoVo.setCheckListRemarks(CommonFunction.checkNull(data.get(2)).toString());
					
					repoCheckList.add(repoVo);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return repoCheckList;
	}
	   
    public boolean saveRepoDetailsByAgency(Object ob,String [] checkList,String [] statusList,String [] remarkList) {
    	
    	RepoDetailVo vo = (RepoDetailVo) ob;
    	String repoId = (String) vo.getRepoId();
    	ArrayList updatelist = new ArrayList();
    	boolean status = false;
    	String saveForwardFlag = CommonFunction.checkNull(vo.getSaveForwardFlag());
    	
    	logger.info("## IN saveRepoDetailsByAgency(): saveForwardFlag :==>> "+saveForwardFlag); 
    	

    	try {
    		
    		StringBuilder bufInsSql = new StringBuilder();
    		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
    		
    		bufInsSql.append(" UPDATE REPO_CASE_DTL SET REPOSSESSED_BY=?,REPO_ADDRESS1=?,REPO_ADDRESS2=?,STATE=?,DISTRICT=?, " );
    		bufInsSql.append(" REPO_FROM=?,RELATION_TO_CUSTOMER=?,REPO_DATE= ");
			bufInsSql.append("DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND), ");
    		bufInsSql.append(" REPO_TIME=?,STOCKYARD=?,AGENCY_REMARKS=?,AGENCY_FLAG=?,AGENCY_MAKER_ID=?,AGENCY_DATE= ");
			bufInsSql.append("DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ");
    		bufInsSql.append(" where REPO_ID=?");

    		if (CommonFunction.checkNull(vo.getRepossessedBy())
    				.equalsIgnoreCase(""))
    			insertPrepStmtObject.addNull();
    		else
    			insertPrepStmtObject.addString(vo.getRepossessedBy()
    					.toUpperCase().trim());
    		
    		if (CommonFunction.checkNull(vo.getRepoAdd1())
    				.equalsIgnoreCase(""))
    			insertPrepStmtObject.addNull();
    		else
    			insertPrepStmtObject.addString(vo.getRepoAdd1()
    					.toUpperCase().trim());
    		
    		
    		if (CommonFunction.checkNull(vo.getRepoAdd2())
    				.equalsIgnoreCase(""))
    			insertPrepStmtObject.addNull();
    		else
    			insertPrepStmtObject.addString(vo.getRepoAdd2()
    					.toUpperCase().trim());
    		
    		if (CommonFunction.checkNull(vo.getLbxState())
    				.equalsIgnoreCase(""))
    			insertPrepStmtObject.addNull();
    		else
    			insertPrepStmtObject.addString(vo.getLbxState()
    					.toUpperCase().trim());
    		
    		if (CommonFunction.checkNull(vo.getLbxDistrict())
    				.equalsIgnoreCase(""))
    			insertPrepStmtObject.addNull();
    		else
    			insertPrepStmtObject.addString(vo.getLbxDistrict()
    					.toUpperCase().trim());
    		
    		if (CommonFunction.checkNull(vo.getRepoFrom())
    				.equalsIgnoreCase(""))
    			insertPrepStmtObject.addNull();
    		else
    			insertPrepStmtObject.addString(vo.getRepoFrom()
    					.toUpperCase().trim());
    		
     		if (CommonFunction.checkNull(vo.getRelationToCustomer())
    				.equalsIgnoreCase(""))
    			insertPrepStmtObject.addNull();
    		else
    			insertPrepStmtObject.addString(vo.getRelationToCustomer()
    					.toUpperCase().trim());
     		
     		if (CommonFunction.checkNull(vo.getRepoDate())
    				.equalsIgnoreCase(""))
    			insertPrepStmtObject.addNull();
    		else
    			insertPrepStmtObject.addString(vo.getRepoDate()
    					.toUpperCase().trim());
    		
    		if (CommonFunction.checkNull(vo.getRepoTime())
    				.equalsIgnoreCase(""))
    			insertPrepStmtObject.addNull();
    		else
    			insertPrepStmtObject.addString(vo.getRepoTime()
    					.toUpperCase().trim());
    		
    		if (CommonFunction.checkNull(vo.getLbxStockYard())
    				.equalsIgnoreCase(""))
    			insertPrepStmtObject.addNull();
    		else
    			insertPrepStmtObject.addString(vo.getLbxStockYard()
    					.toUpperCase().trim());
    		
    		if (CommonFunction.checkNull(vo.getAgencyRemarks())
    				.equalsIgnoreCase(""))
    			insertPrepStmtObject.addNull();
    		else
    			insertPrepStmtObject.addString(vo.getAgencyRemarks()
    					.toUpperCase().trim());
    		
    		if(saveForwardFlag.equalsIgnoreCase("F"))
    			insertPrepStmtObject.addString("Y");
    		else	
    			insertPrepStmtObject.addString("N");
    		
    		
    		if (CommonFunction.checkNull(vo.getMakerId())
    				.equalsIgnoreCase(""))
    			insertPrepStmtObject.addNull();
    		else
    			insertPrepStmtObject.addString(vo.getMakerId()
    					.toUpperCase().trim());
    		
    		if (CommonFunction.checkNull(vo.getMakerDate())
    				.equalsIgnoreCase(""))
    			insertPrepStmtObject.addNull();
    		else
    			insertPrepStmtObject.addString(vo.getMakerDate()
    					.toUpperCase().trim());
    		
    		if (CommonFunction.checkNull(vo.getRepoId())
    				.equalsIgnoreCase(""))
    			insertPrepStmtObject.addNull();
    		else
    			insertPrepStmtObject.addString(vo.getRepoId());

    		insertPrepStmtObject.setSql(bufInsSql.toString());
    		
    		logger.info("## In saveRepoDetailsByAgency() : update query (REPO DETAIL BY AGENCY) "+insertPrepStmtObject.printQuery());
    		
    		updatelist.add(insertPrepStmtObject);
    		
    		bufInsSql = null;
    		insertPrepStmtObject = null;
    		
    		bufInsSql = new StringBuilder();
			insertPrepStmtObject = new PrepStmtObject();
    		
			bufInsSql.append("DELETE FROM REPO_AGENCY_ASSET_CHECKLIST_DTL WHERE REPO_ID='"+vo.getRepoId()+"'");
			
			insertPrepStmtObject.setSql(bufInsSql.toString());
			
			logger.info("## In saveRepoDetailsByAgency () : Delete query : ==>> "+insertPrepStmtObject.printQuery());
			
			updatelist.add(insertPrepStmtObject);
    		
    		int listLength = checkList.length;
    		
    		for (int i = 0; i < listLength; i++) {
				
    			bufInsSql = null;
				insertPrepStmtObject = null;
    			
				bufInsSql = new StringBuilder();
				insertPrepStmtObject = new PrepStmtObject();
				
				bufInsSql.append("INSERT INTO REPO_AGENCY_ASSET_CHECKLIST_DTL (REPO_ID,LOAN_ID,PRODUCT,ASSET,ASSET_CHECKLIST,CHECKLIST_STATUS,REMARKS,MAKER_ID,MAKER_DATE) ");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");//REPO_ID
				bufInsSql.append(" ?,");//LOAN_ID
				bufInsSql.append(" ?,");//PRODUCT
				bufInsSql.append(" ?,");//ASSET
				bufInsSql.append(" ?,");//ASSET_CHECKLIST
				bufInsSql.append(" ?,");//CHECKLIST_STATUS
				bufInsSql.append(" ?,"); //REMARKS
				bufInsSql.append(" ?,");//MAKER_ID
				bufInsSql.append("DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND))");
				
				if (CommonFunction.checkNull(vo.getRepoId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getRepoId()
							.toUpperCase());
				
				if (CommonFunction.checkNull(vo.getLoanId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLoanId()
							.toUpperCase());
				
				if (CommonFunction.checkNull(vo.getProductId())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getProductId()
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
					insertPrepStmtObject.addString(checkList[i]);
				
				if (CommonFunction.checkNull(statusList[i])
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(statusList[i]);
				
				if (CommonFunction.checkNull(remarkList[i])
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(remarkList[i]);
				
				if(CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
				""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());
				
				if(CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(
				""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());

				insertPrepStmtObject.setSql(bufInsSql.toString());
				
				logger.info("## In saveRepoDetailsByAgency () : Insert query of REPO_AGENCY_ASSET_CHECKLIST_DTL : ==>> "+insertPrepStmtObject.printQuery());
				
				updatelist.add(insertPrepStmtObject);
			}
    		
    		status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);

    	
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return status;
    }
    
    
    //Richa Workspace start
    
    	//Repo Detail By Stockyard
    
 // Repo Details By Stockyard Starts
	public ArrayList searchRepoDetailByStockyard(Object ob) {
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		String loanId = null;
		String repoId = null;
		ArrayList searchlist = new ArrayList();
		RepoDetailStockyardVo repoDetailStockyardVo = (RepoDetailStockyardVo) ob;
		ArrayList<RepoDetailStockyardVo> detailList = new ArrayList<RepoDetailStockyardVo>();
		try {

			logger.info("In searchRepoDetailByStockyard()..............inside ejb server file.......................Dao Impl");

			repoId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(repoDetailStockyardVo.getSearchRepoId())).trim());
			loanId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(repoDetailStockyardVo.getLbxLoanIdSearch())).trim());
			
           	StringBuilder bufInsSql = new StringBuilder();
			StringBuilder bufInsSqlTempCount = new StringBuilder();
			
			bufInsSql.append(" SELECT REPO_ID ,RCD.LOAN_ID,CLD.LOAN_NO,RCD.CUSTOMER_ID,GCM.CUSTOMER_NAME,RCD.PRODUCT_ID,CPM.PRODUCT_DESC,RCD.ASSET_CLASS,GM.VALUE AS ASSET_CALSS_DESC ");
			bufInsSql.append(" FROM REPO_CASE_DTL RCD ");
			bufInsSql.append(" JOIN CR_LOAN_DTL  CLD ON (CLD.LOAN_ID = RCD.LOAN_ID) ");
			bufInsSql.append(" LEFT JOIN GCD_CUSTOMER_M GCM ON (GCM.CUSTOMER_ID = RCD.CUSTOMER_ID) ");
			bufInsSql.append(" LEFT JOIN CR_PRODUCT_M CPM ON (CPM.PRODUCT_ID = RCD.PRODUCT_ID) ");
			bufInsSql.append(" LEFT JOIN GENERIC_MASTER GM ON (GM.VALUE = RCD.ASSET_CLASS AND GM.GENERIC_KEY = 'ASSET_CLASS') ");
			bufInsSql.append(" WHERE AGENCY_FLAG = 'Y' AND STOCKYARD_FLAG = 'N' AND RCD.STOCKYARD = '"+CommonFunction.checkNull(repoDetailStockyardVo.getMakerId())+"'  AND RCD.BRANCH_ID='"+CommonFunction.checkNull(repoDetailStockyardVo.getBranchId())+"'");
			
			
			bufInsSqlTempCount.append(" SELECT COUNT(1) ");
			bufInsSqlTempCount.append(" FROM REPO_CASE_DTL RCD ");
			bufInsSqlTempCount.append(" JOIN CR_LOAN_DTL  CLD ON (CLD.LOAN_ID = RCD.LOAN_ID) ");
			bufInsSqlTempCount.append(" LEFT JOIN GCD_CUSTOMER_M GCM ON (GCM.CUSTOMER_ID = RCD.CUSTOMER_ID) ");
			bufInsSqlTempCount.append(" LEFT JOIN CR_PRODUCT_M CPM ON (CPM.PRODUCT_ID = RCD.PRODUCT_ID) ");
			bufInsSqlTempCount.append(" LEFT JOIN GENERIC_MASTER GM ON (GM.VALUE = RCD.ASSET_CLASS AND GM.GENERIC_KEY = 'ASSET_CLASS')  ");
			bufInsSqlTempCount.append(" WHERE AGENCY_FLAG = 'Y' AND STOCKYARD_FLAG = 'N' AND RCD.STOCKYARD = '"+CommonFunction.checkNull(repoDetailStockyardVo.getMakerId())+"' AND RCD.BRANCH_ID='"+CommonFunction.checkNull(repoDetailStockyardVo.getBranchId())+"'");
			
			if(!repoId.equals("")) {
				bufInsSql.append(" AND RCD.REPO_ID = '" + repoId + "' ");
				bufInsSqlTempCount.append(" AND RCD.REPO_ID = '" + repoId + "' ");
			}
			if(!loanId.equals("")) {
				bufInsSql.append(" AND RCD.LOAN_ID = '" + loanId + "' ");
				bufInsSqlTempCount.append(" AND RCD.LOAN_ID = '" + loanId + "' ");
			}

			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
			
			bufInsSql.append(" ORDER BY LOAN_NO ");
			
			count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));


				logger.info("current PAge Link no .................... "+repoDetailStockyardVo.getCurrentPageLink());
				if(repoDetailStockyardVo.getCurrentPageLink()>1)
				{
					startRecordIndex = (repoDetailStockyardVo.getCurrentPageLink()-1)*no;
					endRecordIndex = no;
					logger.info("startRecordIndex .................... "+startRecordIndex);
					logger.info("endRecordIndex .................... "+endRecordIndex);
				}
				
				bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
				
				//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
				/*bufInsSql.append(" ORDER BY LOAN_NO OFFSET ");
				bufInsSql.append(startRecordIndex);
				bufInsSql.append(" ROWS FETCH next ");
				bufInsSql.append(endRecordIndex);
				bufInsSql.append(" ROWS ONLY ");*/


			logger.info("query : "+bufInsSql);

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("IN searchRepoDetailByStockyard() search query1 ### "+ bufInsSql.toString());

			logger.info("searchRepoDetailByStockyard " + searchlist.size());

			for (int i = 0; i < searchlist.size(); i++) {
				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					RepoDetailStockyardVo deptMVO = new RepoDetailStockyardVo();
					deptMVO.setRepoId("<a href=repoDetailByStockyardDispatch.do?method=openEditRepoDetailByStockyard&repoId="
									+ CommonFunction.checkNull(data.get(0)).toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
					deptMVO.setLoanNo(CommonFunction.checkNull(data.get(2)).toString());
					deptMVO.setCustomer(CommonFunction.checkNull(data.get(4)).toString());
					deptMVO.setProduct(CommonFunction.checkNull(data.get(6)).toString());
					deptMVO.setAssetDescription(CommonFunction.checkNull(data.get(8)).toString());
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


	public ArrayList openEditRepoDetailByStockyard(Object ob) {
		ArrayList searchlist = new ArrayList();
		RepoDetailStockyardVo vo = (RepoDetailStockyardVo)ob;
		ArrayList<RepoDetailStockyardVo> list = new ArrayList<RepoDetailStockyardVo>();
		logger.info("ratioid in searchBenchMarkRatioEdit &***************************** = "+vo.getLoanNo());

		try {


			StringBuffer bufInsSql = new StringBuffer();
			
			bufInsSql.append(" SELECT RCD.REPO_ID,RCD.LOAN_ID,CLD.LOAN_NO,RCD.CUSTOMER_ID,GCM.CUSTOMER_NAME,CPM.PRODUCT_ID,CPM.PRODUCT_DESC,RCD.SCHEME_ID,CSM.SCHEME_DESC,RCD.ASSET_CLASS,RCD.ASSET_DESCRIPTION,RCD.ASSET_COST, " );
			bufInsSql.append(" REPOSSESSED_BY,RCD.AGENCY_NAME AGENCY_ID,CAM.AGENCY_NAME AS AGENCY_DESC,REPO_ADDRESS1,REPO_ADDRESS2,RCD.STATE,SM.STATE_DESC,RCD.DISTRICT,CDM.DISTRICT_DESC,REPO_FROM,RELATION_TO_CUSTOMER, ");
			
			bufInsSql.append("DATE_FORMAT(RCD.REPO_DATE,'"+ dateFormat +"'), ");			
			bufInsSql.append("RCD.REPO_TIME,RCD.STOCKYARD AS STOCKYARD_ID,SUM1.USER_NAME AS STOCKYARD_NAME,RCD.STOCKYARD_MANAGER_NAME,RCD.STOCKYARD_REMARKS,RCD.STOCKYARD_ENTRY_TIME, ");
			
			bufInsSql.append("DATE_FORMAT(RCD.STOCKYARD_ENTRY_DATE,'"+ dateFormat +"'), RCD.ASSET_CLASS ");
			bufInsSql.append(" FROM REPO_CASE_DTL RCD ");
			bufInsSql.append(" JOIN CR_LOAN_DTL CLD ON (CLD.LOAN_ID = RCD.LOAN_ID) ");
			bufInsSql.append(" JOIN GCD_CUSTOMER_M GCM ON (GCM.CUSTOMER_ID = RCD.CUSTOMER_ID) ");
			bufInsSql.append(" JOIN CR_PRODUCT_M CPM ON (CPM.PRODUCT_ID = RCD.PRODUCT_ID) ");
			bufInsSql.append(" JOIN CR_SCHEME_M CSM ON (CSM.SCHEME_ID = RCD.SCHEME_ID) ");
			bufInsSql.append(" LEFT JOIN COM_AGENCY_M CAM ON (CAM.AGENCY_CODE = RCD.AGENCY_NAME) ");
			bufInsSql.append(" LEFT JOIN COM_STATE_M SM ON (SM.STATE_ID = RCD.STATE) ");
			bufInsSql.append(" LEFT JOIN COM_DISTRICT_M CDM ON (CDM.DISTRICT_ID = RCD.DISTRICT) ");
			bufInsSql.append(" LEFT JOIN SEC_USER_M SUM1 ON (SUM1.USER_ID = RCD.STOCKYARD) ");
			bufInsSql.append(" WHERE REPO_ID ='"+StringEscapeUtils.escapeSql(vo.getRepoId())+"'");

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			logger.info("IN openEditRepoDetailByStockyard() search query1 ### "+ bufInsSql.toString());
			logger.info("openEditRepoDetailByStockyard " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
				logger.info("openEditRepoDetailByStockyard " + searchlist.get(i).toString());
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					RepoDetailStockyardVo caseInitMakerVo = new RepoDetailStockyardVo();
				
					caseInitMakerVo.setRepoId(CommonFunction.checkNull(data.get(0)).toString());
					caseInitMakerVo.setLoanId(CommonFunction.checkNull(data.get(1)).toString());
					caseInitMakerVo.setLoanNo(CommonFunction.checkNull(data.get(2)).toString());
					caseInitMakerVo.setCustomer(CommonFunction.checkNull(data.get(4)).toString());
					caseInitMakerVo.setProductId(CommonFunction.checkNull(data.get(5)).toString());
					caseInitMakerVo.setProduct(CommonFunction.checkNull(data.get(6)).toString());
					caseInitMakerVo.setScheme(CommonFunction.checkNull(data.get(8)).toString());
					caseInitMakerVo.setAssetDescription(CommonFunction.checkNull(data.get(10)).toString());
					if(CommonFunction.checkNull(data.get(11)).trim().equalsIgnoreCase(""))
					{
						caseInitMakerVo.setAssetCost("0");
					}
					else
					{
						caseInitMakerVo.setAssetCost(myFormatter.format(myFormatter.parse(CommonFunction.checkNull(data.get(11)))));
					}
					caseInitMakerVo.setRepossesedBy(CommonFunction.checkNull(data.get(12)).toString());
					caseInitMakerVo.setAgencyName(CommonFunction.checkNull(data.get(14)).toString());
					caseInitMakerVo.setRepoAddress1(CommonFunction.checkNull(data.get(15)).toString());
					caseInitMakerVo.setRepoAddress2(CommonFunction.checkNull(data.get(16)).toString());
					//caseInitMakerVo.setLbxState(CommonFunction.checkNull(data.get(17)).toString());
					caseInitMakerVo.setState(CommonFunction.checkNull(data.get(18)).toString());
					//repoVo.setLbxDistrict(CommonFunction.checkNull(data.get(19)).toString());
					caseInitMakerVo.setDistrict(CommonFunction.checkNull(data.get(20)).toString());
					caseInitMakerVo.setRepoFrom(CommonFunction.checkNull(data.get(21)).toString());
					caseInitMakerVo.setRelationToCustomer(CommonFunction.checkNull(data.get(22)).toString());
					caseInitMakerVo.setRepoDate(CommonFunction.checkNull(data.get(23)).toString());
					caseInitMakerVo.setRepoTime(CommonFunction.checkNull(data.get(24)).toString());
					//caseInitMakerVo.setLbxStockYard(CommonFunction.checkNull(data.get(25)).toString());
					caseInitMakerVo.setStockyard(CommonFunction.checkNull(data.get(26)).toString());
					caseInitMakerVo.setStockyardManager(CommonFunction.checkNull(data.get(27)).toString());
					caseInitMakerVo.setStockyardRemarks(CommonFunction.checkNull(data.get(28)).toString());
					caseInitMakerVo.setStockyardEntryTime(CommonFunction.checkNull(data.get(29)).toString());
					caseInitMakerVo.setStockyardEntryDate(CommonFunction.checkNull(data.get(30)).toString());
					caseInitMakerVo.setAssetId(CommonFunction.checkNull(data.get(31)).toString());

					list.add(caseInitMakerVo);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList getCheckListForStockyard(Object ob) {
		ArrayList searchlist = new ArrayList();
		RepoDetailStockyardVo vo = (RepoDetailStockyardVo)ob;
		ArrayList<RepoDetailStockyardVo> list = new ArrayList<RepoDetailStockyardVo>();
		logger.info("ratioid in searchBenchMarkRatioEdit &***************************** = "+vo.getLoanNo());

		try {


			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql.append(" SELECT CHECKLIST,RSACD.CHECKLIST_STATUS,RSACD.REMARKS FROM REPO_ASSET_PRODUCT_CHECKLIST_MX RAPCM " );
			bufInsSql.append(" JOIN REPO_CASE_DTL RCD ON (RCD.ASSET_CLASS = RAPCM.ASSET AND RCD.PRODUCT_ID = RAPCM.PRODUCT ) ");
			bufInsSql.append(" LEFT JOIN REPO_STOCKYARD_ASSET_CHECKLIST_DTL RSACD ON (RSACD.ASSET = RAPCM.ASSET AND RSACD.PRODUCT = RAPCM.PRODUCT AND RSACD.ASSET_CHECKLIST = RAPCM.CHECKLIST AND RSACD.REPO_ID = RCD.REPO_ID)   ");
			bufInsSql.append(" WHERE RCD.REPO_ID ='"+StringEscapeUtils.escapeSql(vo.getRepoId())+"' ");
			logger.info("search Query...." + bufInsSql);

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			logger.info("IN openEditRepoDetailByStockyard() search query1 ### "+ bufInsSql.toString());
			logger.info("openEditRepoDetailByStockyard " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
				logger.info("openEditRepoDetailByStockyard " + searchlist.get(i).toString());
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					RepoDetailStockyardVo caseInitMakerVo = new RepoDetailStockyardVo();

					caseInitMakerVo.setAssetChecklist(CommonFunction.checkNull(data.get(0)).toString());
					caseInitMakerVo.setCheckListStatus(CommonFunction.checkNull(data.get(1)).toString());
					caseInitMakerVo.setCheckListRemarks(CommonFunction.checkNull(data.get(2)).toString());

					list.add(caseInitMakerVo);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public boolean insertRepoDetailByStockyard(Object ob,String [] checkList,String [] checkListStatusList,String [] checkListRemarksList) {
		RepoDetailStockyardVo vo = (RepoDetailStockyardVo) ob;
	logger.info("## In insertRepoDetailByStockyard() : of daoimpls");
	logger.info("repoID:-1---"+vo.getRepoId());
		ArrayList qryList = new ArrayList();
		logger.info("repoID:--2--"+vo.getRepoId());
		PrepStmtObject insertPrepStmtObject = null;


		boolean status = false;
		String updateForwardFlag = CommonFunction.checkNull(vo.getUpdateForwardFlag());

		logger.info("## IN insertRepoDetailByStockyard(): updateForwardFlag :==>> "+updateForwardFlag);


		try {


				insertPrepStmtObject = new PrepStmtObject();
				logger.info("In insertRepoDetailByStockyard");
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append(" UPDATE REPO_CASE_DTL SET STOCKYARD_MANAGER_NAME=?,STOCKYARD_REMARKS=?,STOCKYARD_FLAG=?,STOCKYARD_ENTRY_DATE=");
				bufInsSql.append("DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND), ");
				bufInsSql.append(" STOCKYARD_ENTRY_TIME=?,REC_STATUS=?");
				bufInsSql.append(" where REPO_ID=?");


				if (CommonFunction.checkNull(vo.getStockyardManager()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getStockyardManager().toUpperCase().trim());
				
				if (CommonFunction.checkNull(vo.getStockyardRemarks()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getStockyardRemarks().toUpperCase().trim());
				
				if(CommonFunction.checkNull(vo.getUpdateForwardFlag()).equalsIgnoreCase("F"))
					insertPrepStmtObject.addString("Y");
				else
					insertPrepStmtObject.addString("N");


				if (CommonFunction.checkNull(vo.getStockyardEntryDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getStockyardEntryDate());

				if (CommonFunction.checkNull(vo.getStockyardEntryTime()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getStockyardEntryTime().toUpperCase().trim());

				insertPrepStmtObject.addString("A");
				
			if (CommonFunction.checkNull(vo.getRepoId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getRepoId());

				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN insertRepoDetailByStockyard() insert query1 ### "
						+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);

				//new

				insertPrepStmtObject = null;
				bufInsSql = null;

				bufInsSql = new StringBuffer();
				insertPrepStmtObject = new PrepStmtObject();

				bufInsSql.append(" DELETE FROM REPO_STOCKYARD_ASSET_CHECKLIST_DTL WHERE REPO_ID='"+vo.getRepoId()+"'");

				insertPrepStmtObject.setSql(bufInsSql.toString());

				qryList.add(insertPrepStmtObject);
				logger.info("IN insertRepoDetailByStockyard()delete query1 ### "
						+ insertPrepStmtObject.printQuery());
				int length=0;
				if(((checkList!=null)));
				length= checkList.length;



				logger.info("IN insertStageTypeMaster(): length1: "+length);


				for (int i = 0; i < length; i++) {

					insertPrepStmtObject = null;
					bufInsSql = null;

					bufInsSql = new StringBuffer();
					insertPrepStmtObject = new PrepStmtObject();

					bufInsSql.append("INSERT INTO REPO_STOCKYARD_ASSET_CHECKLIST_DTL(REPO_ID,LOAN_ID,PRODUCT,ASSET,ASSET_CHECKLIST,CHECKLIST_STATUS,REMARKS,MAKER_ID,MAKER_DATE)");
					bufInsSql.append(" values ( ");
					bufInsSql.append(" ?,");//REPO_ID
					bufInsSql.append(" ?,");//LOAN_ID
					bufInsSql.append(" ?,");//PRODUCT
					bufInsSql.append(" ?,");//ASSET
					bufInsSql.append(" ?,");//ASSET_CHECKLIST
					bufInsSql.append(" ?,");//CHECKLIST_STATUS
					bufInsSql.append(" ?,"); //REMARKS
					bufInsSql.append(" ?,"); //makerId
					bufInsSql.append("DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND))");

					if (CommonFunction.checkNull(vo.getRepoId())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getRepoId()
								.toUpperCase());

					if (CommonFunction.checkNull(vo.getLoanId())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getLoanId()
								.toUpperCase());

					if (CommonFunction.checkNull(vo.getProductId())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getProductId()
								.toUpperCase());


					if (CommonFunction.checkNull(vo.getAssetId())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getAssetId()
								.toUpperCase());


					if (CommonFunction.checkNull(checkList[i])
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(checkList[i]);

					if (CommonFunction.checkNull(checkListStatusList[i])
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(checkListStatusList[i]);

					if (CommonFunction.checkNull(checkListRemarksList[i])
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(checkListRemarksList[i]);



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
					logger.info("IN insertUserMaster() insert query1 ::::::::::: "+ insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);
				}
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);


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


    
    	//Repo Detil By Stockyard
	
	//Repo Marking Maker Start
	
	public ArrayList searchRepoMarkingMaker(Object ob) {
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		String loanId = null;
		String productId = null;
		String stockyardDesc = null;
		ArrayList searchlist = new ArrayList();
		RepoMarkingMakerVo repoMarkingMakerVo = (RepoMarkingMakerVo) ob;
		ArrayList<RepoMarkingMakerVo> detailList = new ArrayList<RepoMarkingMakerVo>();
		try {

			logger.info("In searchStockyardMasterData()..............inside ejb server file.......................Dao Impl");

			loanId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(repoMarkingMakerVo.getLbxLoanId())).trim());
			productId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(repoMarkingMakerVo.getLbxProductSearchID())).trim());
			
           	StringBuilder bufInsSql = new StringBuilder();
			StringBuilder bufInsSqlTempCount = new StringBuilder();
			bufInsSql.append(" SELECT RMD.LOAN_ID,CLT.LOAN_NO, CPM.PRODUCT_DESC,CSM.SCHEME_DESC,CBM.BRANCH_DESC,RMD.LOAN_AMOUNT from REPO_MARKING_DTL RMD ");
			bufInsSql.append(" INNER JOIN CR_SCHEME_M CSM ON CSM.SCHEME_ID= RMD.SCHEME_ID ");
			bufInsSql.append(" INNER JOIN CR_PRODUCT_M CPM ON CPM.PRODUCT_ID= RMD.PRODUCT_ID  ");
			bufInsSql.append(" INNER JOIN COM_BRANCH_M CBM On CBM.BRANCH_ID= RMD.BRANCH_ID  ");
			bufInsSql.append(" INNER JOIN CR_LOAN_DTL CLT On CLT.LOAN_ID= RMD.LOAN_ID  ");
			bufInsSql.append(" where RMD.REC_STATUS='P' AND  RMD.BRANCH_ID='" + repoMarkingMakerVo.getBranch() + "' ");
			
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM REPO_MARKING_DTL RMD ");
			bufInsSqlTempCount.append(" INNER JOIN CR_SCHEME_M CSM ON CSM.SCHEME_ID= RMD.SCHEME_ID ");
			bufInsSqlTempCount.append(" INNER JOIN CR_PRODUCT_M CPM ON CPM.PRODUCT_ID= RMD.PRODUCT_ID  ");
			bufInsSqlTempCount.append(" INNER JOIN COM_BRANCH_M CBM On CBM.BRANCH_ID= RMD.BRANCH_ID  ");
			bufInsSqlTempCount.append(" INNER JOIN CR_LOAN_DTL CLT On CLT.LOAN_ID= RMD.LOAN_ID  ");
			bufInsSqlTempCount.append(" where RMD.REC_STATUS='P' AND  RMD.BRANCH_ID='" + repoMarkingMakerVo.getBranch() + "' ");



			 if (!loanId.equals("")) {
				bufInsSql.append(" AND RMD.LOAN_ID = '" + loanId + "' ");
				bufInsSqlTempCount.append(" AND RMD.LOAN_ID = '" + loanId + "' ");
			}
			 
			 if (!productId.equals("")) {
					bufInsSql.append(" AND RMD.PRODUCT_ID = '" + productId + "' ");
					bufInsSqlTempCount.append(" AND RMD.PRODUCT_ID = '" + productId + "' ");
			}

			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
			
			bufInsSql.append(" ORDER BY LOAN_NO ");
			
			count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));


				logger.info("current PAge Link no .................... "+repoMarkingMakerVo.getCurrentPageLink());
				if(repoMarkingMakerVo.getCurrentPageLink()>1)
				{
					startRecordIndex = (repoMarkingMakerVo.getCurrentPageLink()-1)*no;
					endRecordIndex = no;
					logger.info("startRecordIndex .................... "+startRecordIndex);
					logger.info("endRecordIndex .................... "+endRecordIndex);
				}
				
				bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
				
				//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
				/*bufInsSql.append(" ORDER BY LOAN_NO OFFSET ");
				bufInsSql.append(startRecordIndex);
				bufInsSql.append(" ROWS FETCH next ");
				bufInsSql.append(endRecordIndex);
				bufInsSql.append(" ROWS ONLY ");*/


			logger.info("query : "+bufInsSql);

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("IN searchStockyardMasterData() search query1 ### "+ bufInsSql.toString());

			logger.info("searchStockyardMasterData " + searchlist.size());

			for (int i = 0; i < searchlist.size(); i++) {
				// logger.info("searchCaseTypeMasterDataList "+searchlist.get(i).toString());

				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					RepoMarkingMakerVo deptMVO = new RepoMarkingMakerVo();
					deptMVO.setLoanNo("<a href=repoMarkingMakerDispatch.do?method=openRepoMarkingMaker&loanId="
							+ CommonFunction.checkNull(data.get(0)).toString()
							+ ">"
							+ CommonFunction.checkNull(data.get(1)).toString() + "</a>");
					deptMVO.setProduct(CommonFunction.checkNull(data.get(2)).toString());
					deptMVO.setScheme(CommonFunction.checkNull(data.get(3)).toString());
					deptMVO.setBranch(CommonFunction.checkNull(data.get(4)).toString());
					if(CommonFunction.checkNull(data.get(5)).trim().equalsIgnoreCase(""))
					{
						deptMVO.setLoanAmount("0");
					}
					else
					{
						deptMVO.setLoanAmount(myFormatter.format(myFormatter.parse(CommonFunction.checkNull(data.get(5)))));
					}
					//deptMVO.setLoanAmount(CommonFunction.checkNull(data.get(5)).toString());
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


	public boolean insertRepoMarkingMaker(Object ob) {
		RepoMarkingMakerVo vo = (RepoMarkingMakerVo) ob;
		boolean status = false;

		logger.info("In insertRepoMarkingMaker.........inside ejb server file...........Dao Impl"
						+ vo.getRecStatus());
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();

		String query = "select LOAN_ID from REPO_MARKING_DTL where  LOAN_ID='"
				+ StringEscapeUtils.escapeSql(vo.getLoanNo().trim()) + "' AND REC_STATUS <> 'X'";
		logger.info("In insertRepoMarkingMaker.......inside ejb server file..........Dao Impl"
						+ query);
		boolean st = ConnectionDAOforEJB.checkStatus(query);
		try {

			if (!st) {

				logger.info("In insertRepoMarkingMaker");
				logger.info("In insertRepoMarkingMaker  value of agency "+ vo.getLbxAgencyId());
				StringBuffer bufInsSql = new StringBuffer();
				bufInsSql.append(" INSERT INTO REPO_MARKING_DTL(LOAN_ID,BRANCH_ID,PRODUCT_ID,SCHEME_ID,DPD, " );
				bufInsSql.append(" LOAN_AMOUNT, " );
				bufInsSql.append(" BALANCE_PRINCIPAL, " );
				bufInsSql.append(" OVER_DUE_PRINCIPAL, " );
				bufInsSql.append(" OVER_DUE_AMOUNT,REASON, ");
				bufInsSql.append(" AGENCY,MAKER_REMARKS, ");
				bufInsSql.append(" MAKER_ID,MAKER_DATE, " );
				bufInsSql.append(" REC_STATUS) " );
				bufInsSql.append(" SELECT LOAN_ID,LOAN_BRANCH,LOAN_PRODUCT,LOAN_SCHEME,LOAN_DPD,LOAN_LOAN_AMOUNT,LOAN_BALANCE_PRINCIPAL,LOAN_OVERDUE_PRINCIPAL,LOAN_OVERDUE_AMOUNT , ");
				bufInsSql.append(" '"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxReasonId()))+"','"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxAgencyId()))+"', ");
				bufInsSql.append(" '"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getRemarks()))+"',");
				bufInsSql.append(" '"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerId()))+"', ");
				bufInsSql.append("DATE_ADD(STR_TO_DATE('"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getMakerDate()))+"', '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),");
				bufInsSql.append("'P'");
				bufInsSql.append(" FROM CR_LOAN_DTL CLD WHERE CLD.LOAN_ID = '"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLoanId()))+"' ");


				insertPrepStmtObject.setSql(bufInsSql.toString());
				logger.info("IN insertRepoMarkingMaker() insert query1 ### "
						+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In saveCountryData......................"
						+ status);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;

	}


	public ArrayList openEditRepoMarkingMaker(Object ob) {
		ArrayList searchlist = new ArrayList();
		RepoMarkingMakerVo vo = (RepoMarkingMakerVo)ob;
		ArrayList<RepoMarkingMakerVo> repoMarkingMakerList = new ArrayList<RepoMarkingMakerVo>();
		logger.info("ratioid in searchBenchMarkRatioEdit &***************************** = "+vo.getLoanNo());

		try {


			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql.append(" SELECT RMD.LOAN_ID,CLD.LOAN_NO,RMD.BRANCH_ID, CBM.BRANCH_DESC,RMD.PRODUCT_ID,CPM.PRODUCT_DESC, " );
			bufInsSql.append(" RMD.SCHEME_ID,CSM.SCHEME_DESC, RMD.LOAN_AMOUNT,RMD.BALANCE_PRINCIPAL,RMD.OVER_DUE_PRINCIPAL,");
			bufInsSql.append(" RMD.OVER_DUE_AMOUNT,RMD.DPD,RMD.REASON,CRM.REASON_DESC,RMD.MAKER_REMARKS,RMD.AGENCY,CAM.AGENCY_NAME  ");
			bufInsSql.append(" FROM REPO_MARKING_DTL RMD ");
			bufInsSql.append(" JOIN CR_LOAN_DTL CLD ON (CLD.LOAN_ID = RMD.LOAN_ID) ");
			bufInsSql.append(" LEFT JOIN COM_BRANCH_M CBM ON (CBM.BRANCH_ID = RMD.BRANCH_ID) ");
			bufInsSql.append(" LEFT JOIN CR_PRODUCT_M CPM ON (CPM.PRODUCT_ID = RMD.PRODUCT_ID) ");
			bufInsSql.append(" LEFT JOIN CR_SCHEME_M CSM ON (CSM.SCHEME_ID = RMD.SCHEME_ID) ");
			bufInsSql.append(" LEFT JOIN COM_REASON_M CRM ON (CRM.REASON_ID = RMD.REASON) ");
			bufInsSql.append(" LEFT JOIN COM_AGENCY_M CAM ON (CAM.AGENCY_CODE = RMD.AGENCY) ");
			bufInsSql.append(" WHERE RMD.LOAN_ID ='"+StringEscapeUtils.escapeSql(vo.getLoanId())+"' AND RMD.REC_STATUS <> 'X' ");
			
			logger.info("search Query...." + bufInsSql);

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			logger.info("IN openEditRepoMarkingMaker() search query1 ### "+ bufInsSql.toString());
			logger.info("openEditRepoMarkingMaker " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
				logger.info("openEditRepoMarkingMaker " + searchlist.get(i).toString());
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					RepoMarkingMakerVo repoMarkingvo = new RepoMarkingMakerVo();
					
					
					repoMarkingvo.setLoanId(CommonFunction.checkNull(data.get(0)).toString());
					repoMarkingvo.setLoanNo(CommonFunction.checkNull(data.get(1)).toString());
					repoMarkingvo.setLbxBranch(CommonFunction.checkNull(data.get(2)).toString());
					repoMarkingvo.setBranch(CommonFunction.checkNull(data.get(3)).toString());
					repoMarkingvo.setLbxProduct(CommonFunction.checkNull(data.get(4)).toString());
					repoMarkingvo.setProduct(CommonFunction.checkNull(data.get(5)).toString());
					repoMarkingvo.setLbxScheme(CommonFunction.checkNull(data.get(6)).toString());
					repoMarkingvo.setScheme(CommonFunction.checkNull(data.get(7)).toString());
					if(CommonFunction.checkNull(data.get(8)).trim().equalsIgnoreCase(""))
					{
						repoMarkingvo.setLoanAmount("0");
					}
					else
					{
						repoMarkingvo.setLoanAmount(myFormatter.format(myFormatter.parse(CommonFunction.checkNull(data.get(8)))));
					}
					//repoMarkingvo.setLoanAmount(CommonFunction.checkNull(data.get(8)).toString());
					if(CommonFunction.checkNull(data.get(9)).trim().equalsIgnoreCase(""))
					{
						repoMarkingvo.setBalancePrincipal("0");
					}
					else
					{
						repoMarkingvo.setBalancePrincipal(myFormatter.format(myFormatter.parse(CommonFunction.checkNull(data.get(9)))));
					}
					//repoMarkingvo.setBalancePrincipal(CommonFunction.checkNull(data.get(9)).toString());
					if(CommonFunction.checkNull(data.get(10)).trim().equalsIgnoreCase(""))
					{
						repoMarkingvo.setOverduePrincipal("0");
					}
					else
					{
						repoMarkingvo.setOverduePrincipal(myFormatter.format(myFormatter.parse(CommonFunction.checkNull(data.get(10)))));
					}
					//repoMarkingvo.setOverduePrincipal(CommonFunction.checkNull(data.get(10)).toString());
					if(CommonFunction.checkNull(data.get(11)).trim().equalsIgnoreCase(""))
					{
						repoMarkingvo.setOverdueAmount("0");
					}
					else
					{
						repoMarkingvo.setOverdueAmount(myFormatter.format(myFormatter.parse(CommonFunction.checkNull(data.get(11)))));
					}
					//repoMarkingvo.setOverdueAmount(CommonFunction.checkNull(data.get(11)).toString());
					repoMarkingvo.setDpd(CommonFunction.checkNull(data.get(12)).toString());
					repoMarkingvo.setLbxReasonId(CommonFunction.checkNull(data.get(13)).toString());
					repoMarkingvo.setReasonDesc(CommonFunction.checkNull(data.get(14)).toString());
					repoMarkingvo.setRemarks(CommonFunction.checkNull(data.get(15)).toString());
					repoMarkingvo.setLbxAgencyId(CommonFunction.checkNull(data.get(16)).toString());
					repoMarkingvo.setAgency(CommonFunction.checkNull(data.get(17)).toString());

					repoMarkingMakerList.add(repoMarkingvo);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return repoMarkingMakerList;
	}


	public ArrayList getDetailofLoanForRepoMarkingMaker(Object ob) {
		ArrayList searchlist = new ArrayList();
		RepoMarkingMakerVo vo = (RepoMarkingMakerVo)ob;
		ArrayList<RepoMarkingMakerVo> repoMarkingMakerList = new ArrayList<RepoMarkingMakerVo>();
		logger.info("ratioid in searchBenchMarkRatioEdit &***************************** = "+vo.getLoanNo());

		try {


			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql.append(" SELECT  LOAN_NO,  " );
			bufInsSql.append(" (SELECT DISTINCT BRANCH_DESC FROM COM_BRANCH_M WHERE BRANCH_ID = CLT.LOAN_BRANCH) BRANCH,(SELECT DISTINCT PRODUCT_DESC FROM CR_PRODUCT_M WHERE PRODUCT_ID = CLT.LOAN_PRODUCT) PRODUCT,");
			bufInsSql.append(" (SELECT DISTINCT SCHEME_DESC FROM CR_SCHEME_M WHERE SCHEME_ID = CLT.LOAN_SCHEME) SCHEME,LOAN_DPD,LOAN_LOAN_AMOUNT  ,  LOAN_BALANCE_PRINCIPAL, LOAN_OVERDUE_PRINCIPAL ,LOAN_OVERDUE_AMOUNT  ");
			bufInsSql.append(" FROM CR_LOAN_DTL CLT WHERE LOAN_ID='"+StringEscapeUtils.escapeSql(vo.getLoanId())+"' ");

			logger.info("search Query...." + bufInsSql);

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			logger.info("IN openEditRepoMarkingMaker() search query1 ### "+ bufInsSql.toString());
			logger.info("openEditRepoMarkingMaker " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
				logger.info("openEditRepoMarkingMaker " + searchlist.get(i).toString());
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					RepoMarkingMakerVo repoMarkingVo = new RepoMarkingMakerVo();

					repoMarkingVo.setLoanNo(CommonFunction.checkNull(data.get(0)).toString());
					repoMarkingVo.setBranch(CommonFunction.checkNull(data.get(1)).toString());
					repoMarkingVo.setProduct(CommonFunction.checkNull(data.get(2)).toString());
					repoMarkingVo.setScheme(CommonFunction.checkNull(data.get(3)).toString());
					repoMarkingVo.setDpd(CommonFunction.checkNull(data.get(4)).toString());
					if(CommonFunction.checkNull(data.get(5)).trim().equalsIgnoreCase(""))
					{
						repoMarkingVo.setLoanAmount("0");
					}
					else
					{
						repoMarkingVo.setLoanAmount(myFormatter.format(myFormatter.parse(CommonFunction.checkNull(data.get(5)))));
					}
					//repoMarkingVo.setLoanAmount(CommonFunction.checkNull(data.get(5)).toString());
					if(CommonFunction.checkNull(data.get(6)).trim().equalsIgnoreCase(""))
					{
						repoMarkingVo.setBalancePrincipal("0");
					}
					else
					{
						repoMarkingVo.setBalancePrincipal(myFormatter.format(myFormatter.parse(CommonFunction.checkNull(data.get(6)))));
					}
					//repoMarkingVo.setBalancePrincipal(CommonFunction.checkNull(data.get(6)).toString());
					if(CommonFunction.checkNull(data.get(7)).trim().equalsIgnoreCase(""))
					{
						repoMarkingVo.setOverduePrincipal("0");
					}
					else
					{
						repoMarkingVo.setOverduePrincipal(myFormatter.format(myFormatter.parse(CommonFunction.checkNull(data.get(7)))));
					}
					//repoMarkingVo.setOverduePrincipal(CommonFunction.checkNull(data.get(7)).toString());
					if(CommonFunction.checkNull(data.get(8)).trim().equalsIgnoreCase(""))
					{
						repoMarkingVo.setOverdueAmount("0");
					}
					else
					{
						repoMarkingVo.setOverdueAmount(myFormatter.format(myFormatter.parse(CommonFunction.checkNull(data.get(8)))));
					}
					//repoMarkingVo.setOverdueAmount(CommonFunction.checkNull(data.get(8)).toString());


					repoMarkingMakerList.add(repoMarkingVo);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return repoMarkingMakerList;
	}


	public boolean updateRepoMarkingMaker(Object ob) {
		RepoMarkingMakerVo vo = (RepoMarkingMakerVo) ob;
		String loanNo = (String) vo.getLoanNo();
		logger.info("getRecStatus():-" + vo.getRecStatus());
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList updatelist = new ArrayList();


		boolean status = false;
		String updateForwardFlag = CommonFunction.checkNull(vo.getUpdateForwardFlag());

		logger.info("## IN updateRepoMarkingMaker(): updateForwardFlag :==>> "+updateForwardFlag);


		try {

			StringBuffer bufInsSql = new StringBuffer();
			logger.info("In updateRepoMarkingMaker..........inside ejb server file............Dao Impl");
			bufInsSql.append(" UPDATE REPO_MARKING_DTL SET LOAN_ID=?,PRODUCT_ID=?,SCHEME_ID=?,BRANCH_ID=?,");
			bufInsSql.append(" LOAN_AMOUNT=?,BALANCE_PRINCIPAL=?,OVER_DUE_PRINCIPAL=?,");
			bufInsSql.append(" OVER_DUE_AMOUNT=?,DPD=?,REASON=?,MAKER_REMARKS=?,AGENCY=?,REC_STATUS=?, ");
			bufInsSql.append(" MAKER_ID=?,MAKER_DATE=");
			bufInsSql.append("DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ");
			bufInsSql.append(" where LOAN_ID=? AND REC_STATUS <> 'X'");

			if (CommonFunction.checkNull(vo.getLoanId())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getLoanId()

						.toUpperCase().trim());

			if (CommonFunction.checkNull(vo.getLbxProduct())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getLbxProduct()
						.toUpperCase().trim());

			if (CommonFunction.checkNull(vo.getLbxScheme())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getLbxScheme()
						.toUpperCase().trim());

			if (CommonFunction.checkNull(vo.getLbxBranch())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getLbxBranch()
						.toUpperCase().trim());

			if (CommonFunction.checkNull(vo.getLoanAmount())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse(vo.getLoanAmount()).toString());

			if (CommonFunction.checkNull(vo.getBalancePrincipal())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse(vo.getBalancePrincipal()).toString());

			if (CommonFunction.checkNull(vo.getOverduePrincipal())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse(vo.getOverduePrincipal()).toString());

			if (CommonFunction.checkNull(vo.getOverdueAmount())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(myFormatter.parse(vo.getOverdueAmount()).toString());

			if (CommonFunction.checkNull(vo.getDpd())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getDpd()
						.toUpperCase().trim());

			if (CommonFunction.checkNull(vo.getLbxReasonId())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getLbxReasonId()
						.toUpperCase().trim());
			if (CommonFunction.checkNull(vo.getRemarks())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getRemarks()
						.toUpperCase().trim());
			if (CommonFunction.checkNull(vo.getLbxAgencyId())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getLbxAgencyId()
						.toUpperCase().trim());

		if(updateForwardFlag.equalsIgnoreCase("U"))
		{
			insertPrepStmtObject.addString("P");
		}
		else if(updateForwardFlag.equalsIgnoreCase("F"))
		{
			insertPrepStmtObject.addString("F");
		}

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

			if (CommonFunction.checkNull(vo.getLoanId())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getLoanId());

			insertPrepStmtObject.setSql(bufInsSql.toString());

			updatelist.add(insertPrepStmtObject);
			logger.info("In getListOfValues" + bufInsSql.toString());

			logger.info("## In updateRepoMarkingMaker() : update query (case tye master) "+insertPrepStmtObject.printQuery());

			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);


		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	//Repo Marking Maker end
	
	//Repo Marking Author Start
	
	public ArrayList searchRepoMarkingAuthor(Object ob) {
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		String loanId = null;
		String productId = null;
		ArrayList searchlist = new ArrayList();
		RepoMarkingMakerVo repoMarkingMakerVo = (RepoMarkingMakerVo) ob;
		ArrayList<RepoMarkingMakerVo> detailList = new ArrayList<RepoMarkingMakerVo>();
		try {

			logger.info("In searchStockyardMasterData()..............inside ejb server file.......................Dao Impl");

			loanId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(repoMarkingMakerVo.getLbxLoanId())).trim());
			productId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(repoMarkingMakerVo.getLbxProductSearchID())).trim());
			
           	StringBuilder bufInsSql = new StringBuilder();
			StringBuilder bufInsSqlTempCount = new StringBuilder();
			
			bufInsSql.append(" SELECT RMD.LOAN_ID,CLD.LOAN_NO,CPM.PRODUCT_DESC,CSM.SCHEME_DESC,CBM.BRANCH_DESC,RMD.LOAN_AMOUNT FROM REPO_MARKING_DTL RMD ");
			bufInsSql.append(" JOIN CR_LOAN_DTL CLD ON (CLD.LOAN_ID = RMD.LOAN_ID) ");
			bufInsSql.append(" LEFT JOIN CR_PRODUCT_M CPM ON (CPM.PRODUCT_ID = RMD.PRODUCT_ID)  ");
			bufInsSql.append(" LEFT JOIN CR_SCHEME_M CSM ON (CSM.SCHEME_ID = RMD.SCHEME_ID) ");
			bufInsSql.append(" LEFT JOIN COM_BRANCH_M CBM ON (CBM.BRANCH_ID = RMD.BRANCH_ID) ");
			bufInsSql.append(" WHERE RMD.REC_STATUS = 'F' AND  RMD.BRANCH_ID='" + repoMarkingMakerVo.getBranch() + "' ");
			
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM REPO_MARKING_DTL RMD");
			bufInsSqlTempCount.append(" JOIN CR_LOAN_DTL CLD ON (CLD.LOAN_ID = RMD.LOAN_ID) ");
			bufInsSqlTempCount.append(" LEFT JOIN CR_PRODUCT_M CPM ON (CPM.PRODUCT_ID = RMD.PRODUCT_ID)  ");
			bufInsSqlTempCount.append(" LEFT JOIN CR_SCHEME_M CSM ON (CSM.SCHEME_ID = RMD.SCHEME_ID) ");
			bufInsSqlTempCount.append(" LEFT JOIN COM_BRANCH_M CBM ON (CBM.BRANCH_ID = RMD.BRANCH_ID) ");
			bufInsSqlTempCount.append(" WHERE RMD.REC_STATUS = 'F' and  RMD.BRANCH_ID='" + repoMarkingMakerVo.getBranch() + "' ");



			 if (!loanId.equals("")) {
				bufInsSql.append(" AND RMD.LOAN_ID = '" + loanId + "' ");
				bufInsSqlTempCount.append(" AND RMD.LOAN_ID = '" + loanId + "' ");
			}
			 
			 if (!productId.equals("")) {
					bufInsSql.append(" AND RMD.PRODUCT_ID = '" + productId + "' ");
					bufInsSqlTempCount.append(" AND RMD.PRODUCT_ID = '" + productId + "' ");
				}

			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
			
			bufInsSql.append(" ORDER BY LOAN_NO ");
			
			count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));


				logger.info("current PAge Link no .................... "+repoMarkingMakerVo.getCurrentPageLink());
				if(repoMarkingMakerVo.getCurrentPageLink()>1)
				{
					startRecordIndex = (repoMarkingMakerVo.getCurrentPageLink()-1)*no;
					endRecordIndex = no;
					logger.info("startRecordIndex .................... "+startRecordIndex);
					logger.info("endRecordIndex .................... "+endRecordIndex);
				}
				
				bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
				
				//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
				/*bufInsSql.append(" ORDER BY LOAN_NO OFFSET ");
				bufInsSql.append(startRecordIndex);
				bufInsSql.append(" ROWS FETCH next ");
				bufInsSql.append(endRecordIndex);
				bufInsSql.append(" ROWS ONLY ");*/


			logger.info("query : "+bufInsSql);

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("IN searchStockyardMasterData() search query1 ### "+ bufInsSql.toString());

			logger.info("searchStockyardMasterData " + searchlist.size());

			for (int i = 0; i < searchlist.size(); i++) {
				// logger.info("searchCaseTypeMasterDataList "+searchlist.get(i).toString());

				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					RepoMarkingMakerVo deptMVO = new RepoMarkingMakerVo();
					deptMVO.setLoanNo("<a href=repoMarkingAuthorDispatch.do?method=openEditRepoMarkingAuthorCase&loanId="
							+ CommonFunction.checkNull(data.get(0)).toString()
							+ ">"
							+ CommonFunction.checkNull(data.get(1)).toString() + "</a>");
					deptMVO.setProduct(CommonFunction.checkNull(data.get(2)).toString());
					deptMVO.setScheme(CommonFunction.checkNull(data.get(3)).toString());
					deptMVO.setBranch(CommonFunction.checkNull(data.get(4)).toString());
					if(CommonFunction.checkNull(data.get(5)).trim().equalsIgnoreCase(""))
					{
						deptMVO.setLoanAmount("0");
					}
					else
					{
						deptMVO.setLoanAmount(myFormatter.format(myFormatter.parse(CommonFunction.checkNull(data.get(5)))));
					}
					//deptMVO.setLoanAmount(CommonFunction.checkNull(data.get(5)).toString());
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


	public ArrayList openEditRepoMarkingAuthorCase(Object ob) {
		ArrayList searchlist = new ArrayList();
		RepoMarkingMakerVo vo = (RepoMarkingMakerVo)ob;
		ArrayList<RepoMarkingMakerVo> repoMarkingMakerList = new ArrayList<RepoMarkingMakerVo>();
		logger.info("ratioid in searchBenchMarkRatioEdit &***************************** = "+vo.getLoanNo());

		try {


			StringBuffer bufInsSql = new StringBuffer();
			
			bufInsSql.append("  SELECT CLD.LOAN_NO,CBM.BRANCH_DESC,CPM.PRODUCT_DESC,CSM.SCHEME_DESC,RMD.DPD, " );
			bufInsSql.append("  RMD.LOAN_AMOUNT,RMD.BALANCE_PRINCIPAL,RMD.OVER_DUE_PRINCIPAL,RMD.OVER_DUE_AMOUNT,RMD.LOAN_ID, ");
			bufInsSql.append(" CAM.AGENCY_NAME,CRM.REASON_DESC,RMD.MAKER_REMARKS,RMD.BRANCH_ID,RMD.PRODUCT_ID,RMD.SCHEME_ID,RMD.REASON ");
			bufInsSql.append(" FROM REPO_MARKING_DTL RMD ");
			bufInsSql.append(" JOIN CR_LOAN_DTL CLD ON (CLD.LOAN_ID = RMD.LOAN_ID) ");
			bufInsSql.append(" LEFT JOIN COM_BRANCH_M CBM ON (RMD.BRANCH_ID = CBM.BRANCH_ID) ");
			bufInsSql.append(" LEFT JOIN CR_PRODUCT_M CPM ON (CPM.PRODUCT_ID = RMD.PRODUCT_ID) ");
			bufInsSql.append(" LEFT JOIN CR_SCHEME_M CSM ON (CSM.SCHEME_ID = RMD.SCHEME_ID) ");
			bufInsSql.append(" LEFT JOIN COM_AGENCY_M CAM ON (CAM.AGENCY_CODE = RMD.AGENCY) ");
			bufInsSql.append(" LEFT JOIN COM_REASON_M CRM ON (CRM.REASON_ID = RMD.REASON) ");
			bufInsSql.append("  WHERE RMD.LOAN_ID ='"+StringEscapeUtils.escapeSql(vo.getLoanId())+"' AND RMD.REC_STATUS <> 'X' ");
			
			logger.info("search Query...." + bufInsSql);

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			logger.info("IN openEditRepoMarkingMaker() search query1 ### "+ bufInsSql.toString());
			logger.info("openEditRepoMarkingMaker " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
				logger.info("openEditRepoMarkingMaker " + searchlist.get(i).toString());
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					RepoMarkingMakerVo repoMarkingVo = new RepoMarkingMakerVo();

					repoMarkingVo.setLoanNo(CommonFunction.checkNull(data.get(0)).toString());
					repoMarkingVo.setBranch(CommonFunction.checkNull(data.get(1)).toString());
					repoMarkingVo.setProduct(CommonFunction.checkNull(data.get(2)).toString());
					repoMarkingVo.setScheme(CommonFunction.checkNull(data.get(3)).toString());
					repoMarkingVo.setDpd(CommonFunction.checkNull(data.get(4)).toString());
					if(CommonFunction.checkNull(data.get(5)).trim().equalsIgnoreCase(""))
					{
						repoMarkingVo.setLoanAmount("0");
					}
					else
					{
						repoMarkingVo.setLoanAmount(myFormatter.format(myFormatter.parse(CommonFunction.checkNull(data.get(5)))));
					}
					//repoMarkingVo.setLoanAmount(CommonFunction.checkNull(data.get(5)).toString());
					if(CommonFunction.checkNull(data.get(6)).trim().equalsIgnoreCase(""))
					{
						repoMarkingVo.setBalancePrincipal("0");
					}
					else
					{
						repoMarkingVo.setBalancePrincipal(myFormatter.format(myFormatter.parse(CommonFunction.checkNull(data.get(6)))));
					}
					//repoMarkingVo.setBalancePrincipal(CommonFunction.checkNull(data.get(6)).toString());
					if(CommonFunction.checkNull(data.get(7)).trim().equalsIgnoreCase(""))
					{
						repoMarkingVo.setOverduePrincipal("0");
					}
					else
					{
						repoMarkingVo.setOverduePrincipal(myFormatter.format(myFormatter.parse(CommonFunction.checkNull(data.get(7)))));
					}
					//repoMarkingVo.setOverduePrincipal(CommonFunction.checkNull(data.get(7)).toString());
					if(CommonFunction.checkNull(data.get(8)).trim().equalsIgnoreCase(""))
					{
						repoMarkingVo.setOverdueAmount("0");
					}
					else
					{
						repoMarkingVo.setOverdueAmount(myFormatter.format(myFormatter.parse(CommonFunction.checkNull(data.get(8)))));
					}
					//repoMarkingVo.setOverdueAmount(CommonFunction.checkNull(data.get(8)).toString());
					repoMarkingVo.setLoanId(CommonFunction.checkNull(data.get(9)).toString());
					repoMarkingVo.setAgency(CommonFunction.checkNull(data.get(10)).toString());
					repoMarkingVo.setReasonDesc(CommonFunction.checkNull(data.get(11)).toString());
					repoMarkingVo.setRemarks(CommonFunction.checkNull(data.get(12)).toString());

					repoMarkingMakerList.add(repoMarkingVo);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return repoMarkingMakerList;
	}


	public boolean insertRepoMarkingAuthorCase(Object ob) {

		logger.info("## in insertAssignRejectCase()");

		RepoMarkingMakerVo vo = (RepoMarkingMakerVo) ob;
		String loanId = (String) vo.getLoanId();
		logger.info("getRecStatus():-" + vo.getRecStatus());
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList updatelist = new ArrayList();


		boolean status = false;
		String updateForwardFlag = CommonFunction.checkNull(vo.getUpdateForwardFlag());
		logger.info("## IN insertAssignRejectCase(): updateForwardFlag :==>> "+updateForwardFlag);

		logger.info("## IN insertAssignRejectCase(): noticeId :==>> "+loanId);


		try {

			String stat = "";



				if (vo.getRecStatus() != null
						&& vo.getRecStatus().equals("F")) {
					stat = "F";
				} else {
					stat = "A";
				}

			StringBuffer bufInsSql = new StringBuffer();
			logger.info("In updateCountryData..........inside ejb server file............Dao Impl");
			bufInsSql.append(" UPDATE REPO_MARKING_DTL SET AUTHER_REMARKS=?,REC_STATUS=?,");
			bufInsSql.append("AUTHOR_ID=?,AUTHOR_DATE=");
			bufInsSql.append("DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ");
			bufInsSql.append(" where LOAN_ID=? AND REC_STATUS <> 'X'");


			if (CommonFunction.checkNull(vo.getComments()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getComments().toUpperCase().trim());

			if (CommonFunction.checkNull(vo.getDecision()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getDecision().toUpperCase().trim());


		 if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerId());

			if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerDate());


			if (CommonFunction.checkNull(vo.getLoanId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getLoanId());

			insertPrepStmtObject.setSql(bufInsSql.toString());

			updatelist.add(insertPrepStmtObject);
			logger.info("In getListOfValues" + bufInsSql.toString());

			logger.info("## In updateCaseTypeData() : update query (case tye master) "+insertPrepStmtObject.printQuery());

			if(CommonFunction.checkNull(vo.getDecision()).equalsIgnoreCase("A"))
			{
			bufInsSql = null;
			insertPrepStmtObject = null;

			bufInsSql = new StringBuffer();
			insertPrepStmtObject = new PrepStmtObject();

			bufInsSql.append(" INSERT INTO REPO_CASE_DTL(LOAN_ID,CUSTOMER_ID,BRANCH_ID,PRODUCT_ID,SCHEME_ID,ASSET_COST, ASSET_CLASS, ASSET_DESCRIPTION, " );
			bufInsSql.append(" AGENCY_NAME, " );
			bufInsSql.append(" LOAN_STATUS) " );
			bufInsSql.append(" SELECT CLT.LOAN_ID,CLT.LOAN_CUSTOMER_ID,CLT.LOAN_BRANCH,CLT.LOAN_PRODUCT,CLT.LOAN_SCHEME,CLT.LOAN_ASSET_COST,CACM.ASSET_COLLATERAL_CLASS,CACM.ASSET_COLLATERAL_DESC, ");
			bufInsSql.append(" RMD.AGENCY,CLT.REC_STATUS FROM REPO_MARKING_DTL RMD  ");
			bufInsSql.append(" inner join CR_LOAN_DTL CLT ON CLT.LOAN_ID= RMD.LOAN_ID ");
			bufInsSql.append(" JOIN CR_LOAN_COLLATERAL_M CLCM ON (CLCM.LOAN_ID = RMD.LOAN_ID) ");
			bufInsSql.append(" JOIN CR_ASSET_COLLATERAL_M CACM ON  (CACM.ASSET_ID = CLCM.ASSETID AND CACM.ASSET_TYPE = 'ASSET') ");
			bufInsSql.append(" WHERE RMD.LOAN_ID = '"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLoanId()))+"' AND RMD.REC_STATUS <> 'X' ");
			insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("IN insertRepoMarkingMaker() insert query1 ### "
					+ insertPrepStmtObject.printQuery());

			updatelist.add(insertPrepStmtObject);

			}

			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	//Repo Marking Author end
    
    
    //Richa Workspace end
	
	//Nazia work space start
	
// nazia workspace starts for  REPO CONFIRMATION ------>>>>>
	
	public ArrayList searchRepoConfirmation(Object ob) {
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		String loanNo = null;
		String repoId = null;
		ArrayList searchlist = new ArrayList();
		logger.info("searchRepoConfirmation---->>>");
		RepoConfirmationtVo repoVo = (RepoConfirmationtVo) ob;
		ArrayList<RepoConfirmationtVo> detailList = new ArrayList<RepoConfirmationtVo>();
		try {

			logger.info("In searchRepoConfirmation()..............inside ejb server file.......................Dao Impl");
			
			loanNo = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(repoVo.getLbxLoanIdSearch())).trim());
			repoId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(repoVo.getSearchRepoId())).trim());
           	StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			bufInsSql.append("SELECT REPO_ID, LOAN_NO, GCM.CUSTOMER_NAME , ASSET_CLASS,CPM.PRODUCT_DESC ");
			bufInsSql.append(" FROM REPO_CASE_DTL RCD ");
			bufInsSql.append(" JOIN CR_LOAN_DTL CLD ON CLD.LOAN_ID = RCD.LOAN_ID");
			bufInsSql.append(" LEFT JOIN CR_PRODUCT_M CPM ON CPM.PRODUCT_ID= RCD.PRODUCT_ID");
			bufInsSql.append(" LEFT JOIN GCD_CUSTOMER_M GCM ON RCD.CUSTOMER_ID= GCM.CUSTOMER_ID");
			bufInsSql.append(" WHERE RCD.AGENCY_FLAG='Y' AND RCD.STOCKYARD_FLAG = 'Y' AND IFNULL(RCD.REPO_CONFIRMATION_FLAG,'N')='N' AND RCD.BRANCH_ID='"+CommonFunction.checkNull(repoVo.getBranchId())+"'  ");
			//bufInsSql.append(" AND AGENCY_NAME NOT IN (SELECT AGENCY_CODE FROM COM_AGENCY_USER_MAPPING WHERE USER_ID = '"+CommonFunction.checkNull(repoVo.getAutherId())+"' AND RCD.STOCKYARD <> '"+CommonFunction.checkNull(repoVo.getAutherId())+"' ");
			
			bufInsSqlTempCount.append("SELECT COUNT(1) FROM REPO_CASE_DTL RCD WHERE RCD.AGENCY_FLAG='Y' AND RCD.STOCKYARD_FLAG = 'Y' AND IFNULL(RCD.REPO_CONFIRMATION_FLAG,'N')='N'  AND RCD.BRANCH_ID='"+CommonFunction.checkNull(repoVo.getBranchId())+"'  ");
			//bufInsSqlTempCount.append(" AND AGENCY_NAME NOT IN (SELECT AGENCY_CODE FROM COM_AGENCY_USER_MAPPING WHERE USER_ID = '"+CommonFunction.checkNull(repoVo.getAutherId())+"' AND RCD.STOCKYARD <> '"+CommonFunction.checkNull(repoVo.getAutherId())+"' ");
		
			


			if (!loanNo.equals("")) {
				bufInsSql.append("AND RCD.LOAN_ID = '" + loanNo + "' ");
				bufInsSqlTempCount.append(" AND RCD.LOAN_ID = '" + loanNo + "' ");
			}
			 if (!repoId.equals("")) {
				bufInsSql.append(" AND RCD.REPO_ID = '" + repoId + "' ");
				bufInsSqlTempCount.append(" AND RCD.REPO_ID = '" + repoId + "' ");
			}
			logger.info("search Query...." + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
            
			bufInsSql.append(" ORDER BY REPO_ID ");
			
			count =Integer.parseInt(ConnectionDAOforEJB.singleReturn(bufInsSqlTempCount.toString()));

			
				logger.info("current PAge Link no .................... "+repoVo.getCurrentPageLink());
				if(repoVo.getCurrentPageLink()>1)
				{
					startRecordIndex = (repoVo.getCurrentPageLink()-1)*no;
					endRecordIndex = no;
					logger.info("startRecordIndex .................... "+startRecordIndex);
					logger.info("endRecordIndex .................... "+endRecordIndex);
				}
				
				bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
				
				/*bufInsSql.append(" ORDER BY REPO_ID OFFSET ");
				bufInsSql.append(startRecordIndex);
				bufInsSql.append(" ROWS FETCH next ");
				bufInsSql.append(endRecordIndex);
				bufInsSql.append(" ROWS ONLY ");*/
							

			logger.info("query : "+bufInsSql);

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());

			logger.info("IN searchRepoConfirmation() search query1 ### "+ bufInsSql.toString());
			logger.info("In searchRepoConfirmation.....................................Dao Impl");
			logger.info("searchRepoConfirmation " + searchlist.size());

			for (int i = 0; i < searchlist.size(); i++) {
				

				ArrayList data = (ArrayList) searchlist.get(i);

				if (data.size() > 0) {
					RepoConfirmationtVo vo = new RepoConfirmationtVo();
					vo.setRepoId("<a href=RepoConfirmationDispatch.do?method=openEditRepoConfirmation&repoId="
									+ CommonFunction.checkNull(data.get(0)).toString()
									+ ">"
									+ CommonFunction.checkNull(data.get(0)).toString() + "</a>");
					
					vo.setLoanNo(CommonFunction.checkNull(data.get(1)).toString());
					vo.setCustomerName(CommonFunction.checkNull(data.get(2)).toString());
					vo.setAssetDescription(CommonFunction.checkNull(data.get(3)).toString());
					vo.setProductDescription(CommonFunction.checkNull(data.get(4)).toString());
					detailList.add(vo);
				}

			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		return detailList;
	}
	
	
	
	public ArrayList openEditRepoConfirmation(Object ob) {

		ArrayList searchlist = new ArrayList();
		RepoConfirmationtVo vo = (RepoConfirmationtVo)ob;
		ArrayList<RepoConfirmationtVo> repoTypeList = new ArrayList<RepoConfirmationtVo>();
		logger.info("ratioid in openEditRepoConfirmation &***************************** = "+vo.getRepoId());

		try {
			

			StringBuffer bufInsSql = new StringBuffer();
			
			bufInsSql.append(" SELECT RCD.REPO_ID,RCD.LOAN_ID,CLD.LOAN_NO,RCD.CUSTOMER_ID,GCM.CUSTOMER_NAME,CPM.PRODUCT_ID,CPM.PRODUCT_DESC,RCD.SCHEME_ID,CSM.SCHEME_DESC,RCD.ASSET_CLASS,RCD.ASSET_DESCRIPTION,RCD.ASSET_COST, " );
			bufInsSql.append(" REPOSSESSED_BY,RCD.AGENCY_NAME AGENCY_ID,CAM.AGENCY_NAME AS AGENCY_DESC,REPO_ADDRESS1,REPO_ADDRESS2,RCD.STATE,SM.STATE_DESC,RCD.DISTRICT,CDM.DISTRICT_DESC,REPO_FROM,RELATION_TO_CUSTOMER, ");
			
			bufInsSql.append("DATE_FORMAT(RCD.REPO_DATE,'"+ dateFormat +"'), ");			
			bufInsSql.append("RCD.REPO_TIME,RCD.STOCKYARD AS STOCKYARD_ID,SUM1.USER_NAME AS STOCKYARD_NAME,RCD.STOCKYARD_MANAGER_NAME,RCD.AGENCY_REMARKS,RCD.STOCKYARD_REMARKS,RCD.STOCKYARD_ENTRY_TIME, ");
			
			bufInsSql.append("DATE_FORMAT(RCD.STOCKYARD_ENTRY_DATE,'"+ dateFormat +"') ");
			bufInsSql.append(" FROM REPO_CASE_DTL RCD ");
			bufInsSql.append(" left JOIN CR_LOAN_DTL CLD ON (CLD.LOAN_ID = RCD.LOAN_ID) ");
			bufInsSql.append(" left JOIN GCD_CUSTOMER_M GCM ON (GCM.CUSTOMER_ID = RCD.CUSTOMER_ID) ");
			bufInsSql.append(" JOIN CR_PRODUCT_M CPM ON (CPM.PRODUCT_ID = RCD.PRODUCT_ID) ");
			bufInsSql.append(" JOIN CR_SCHEME_M CSM ON (CSM.SCHEME_ID = RCD.SCHEME_ID) ");
			bufInsSql.append(" LEFT JOIN COM_AGENCY_M CAM ON (CAM.AGENCY_CODE = RCD.AGENCY_NAME) ");
			bufInsSql.append(" LEFT JOIN COM_STATE_M SM ON (SM.STATE_ID = RCD.STATE) ");
			bufInsSql.append(" LEFT JOIN COM_DISTRICT_M CDM ON (CDM.DISTRICT_ID = RCD.DISTRICT) ");
			bufInsSql.append(" LEFT JOIN SEC_USER_M SUM1 ON (SUM1.USER_ID = RCD.STOCKYARD) ");
			bufInsSql.append(" WHERE REPO_ID ='"+StringEscapeUtils.escapeSql(vo.getRepoId())+"'");
			
			logger.info("search Query...." + bufInsSql);

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			logger.info("IN openEditRepoConfirmation() search query1 ### "+ bufInsSql.toString());
			logger.info("openEditRepoConfirmation " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
				logger.info("openEditRepoConfirmation " + searchlist.get(i).toString());
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					RepoConfirmationtVo Rvo = new RepoConfirmationtVo();
					
					/*Rvo.setRepoId(CommonFunction.checkNull(data.get(0)).toString());
					Rvo.setLoanId(CommonFunction.checkNull(data.get(1)).toString());*/
					Rvo.setLoanNo(CommonFunction.checkNull(data.get(2)).toString());
					Rvo.setCustomerName(CommonFunction.checkNull(data.get(4)).toString());
					Rvo.setProductId(CommonFunction.checkNull(data.get(5)).toString());
					Rvo.setProductDescription(CommonFunction.checkNull(data.get(6)).toString());
					Rvo.setScheme(CommonFunction.checkNull(data.get(8)).toString());
					Rvo.setAssetDescription(CommonFunction.checkNull(data.get(10)).toString());
					if(CommonFunction.checkNull(data.get(11)).trim().equalsIgnoreCase(""))
					{
						Rvo.setAssetCost("0");
					}
					else
					{
						Rvo.setAssetCost(myFormatter.format(myFormatter.parse(CommonFunction.checkNull(data.get(11)))));
					}
					Rvo.setRepossessedBy(CommonFunction.checkNull(data.get(12)).toString());
					Rvo.setAgencyName(CommonFunction.checkNull(data.get(14)).toString());
					Rvo.setRepoAddress1(CommonFunction.checkNull(data.get(15)).toString());
					Rvo.setRepoAddress2(CommonFunction.checkNull(data.get(16)).toString());
					//Rvo.setLbxState(CommonFunction.checkNull(data.get(17)).toString());
					Rvo.setState(CommonFunction.checkNull(data.get(18)).toString());
					//repoVo.setLbxDistrict(CommonFunction.checkNull(data.get(19)).toString());
					Rvo.setDistrict(CommonFunction.checkNull(data.get(20)).toString());
					Rvo.setRepoFrom(CommonFunction.checkNull(data.get(21)).toString());
					Rvo.setRelationToCustomer(CommonFunction.checkNull(data.get(22)).toString());
					Rvo.setRepoDate(CommonFunction.checkNull(data.get(23)).toString());
					Rvo.setRepoTime(CommonFunction.checkNull(data.get(24)).toString());
					//Rvo.setLbxStockYard(CommonFunction.checkNull(data.get(25)).toString());
					Rvo.setStockyard(CommonFunction.checkNull(data.get(26)).toString());
					Rvo.setStockyardManagerName(CommonFunction.checkNull(data.get(27)).toString());
					Rvo.setAgencyRemarks(CommonFunction.checkNull(data.get(28)).toString());
					Rvo.setStockyardRemarks(CommonFunction.checkNull(data.get(29)).toString());
					Rvo.setStockyardEntryTime(CommonFunction.checkNull(data.get(30)).toString());
					Rvo.setStockyardEntryDate(CommonFunction.checkNull(data.get(31)).toString());
					
					repoTypeList.add(Rvo);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return repoTypeList;
}

	public ArrayList getCheckListOfAgency(Object ob) {
		
		ArrayList searchlist = new ArrayList();
		RepoConfirmationtVo vo = (RepoConfirmationtVo)ob;
		ArrayList<RepoConfirmationtVo> list = new ArrayList<RepoConfirmationtVo>();
		logger.info("ratioid in searchBenchMarkRatioEdit &***************************** = "+vo.getLoanNo());

		try {
			
			logger.info("## In getCheckListForStockyard() : ");
			StringBuffer bufInsSql = new StringBuffer();
			
			bufInsSql.append("  SELECT ASSET_CHECKLIST,CHECKLIST_STATUS,REMARKS FROM REPO_AGENCY_ASSET_CHECKLIST_DTL WHERE REPO_ID ='"+StringEscapeUtils.escapeSql(vo.getRepoId())+"' ");
			
			logger.info("search Query...." + bufInsSql);

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			logger.info("IN openEditRepoDetailByStockyard() search query1 ### "+ bufInsSql.toString());
			logger.info("openEditRepoDetailByStockyard " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
				logger.info("openEditRepoDetailByStockyard " + searchlist.get(i).toString());
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					RepoConfirmationtVo caseInitMakerVo = new RepoConfirmationtVo();
					
					caseInitMakerVo.setAssetChecklist(CommonFunction.checkNull(data.get(0)).toString());
					caseInitMakerVo.setCheckListStatus(CommonFunction.checkNull(data.get(1)).toString());
					caseInitMakerVo.setCheckListRemarks(CommonFunction.checkNull(data.get(2)).toString());
					
					list.add(caseInitMakerVo);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
public ArrayList getCheckListOfStockyard(Object ob) {
		
		ArrayList searchlist = new ArrayList();
		RepoConfirmationtVo vo = (RepoConfirmationtVo)ob;
		ArrayList<RepoConfirmationtVo> list = new ArrayList<RepoConfirmationtVo>();
		logger.info("ratioid in searchBenchMarkRatioEdit &***************************** = "+vo.getLoanNo());

		try {
			
			logger.info("## In getCheckListForStockyard() : ");
			StringBuffer bufInsSql = new StringBuffer();
			
			/*bufInsSql.append("  SELECT RSACD.ASSET_CHECKLIST,RSACD.CHECKLIST_STATUS,RSACD.REMARKS,RCD.REPO_ID,CLD.LOAN_NO,CPM.PRODUCT_DESC FROM REPO_CASE_DTL RCD LEFT JOIN REPO_STOCKYARD_ASSET_CHECKLIST_DTL RSACD ON RCD.REPO_ID=RSACD.REPO_ID LEFT JOIN CR_LOAN_DTL CLD ON CLD.LOAN_ID = RCD.LOAN_ID LEFT JOIN CR_PRODUCT_M CPM ON CPM.PRODUCT_ID= RCD.PRODUCT_ID WHERE RCD.REPO_ID ='"+StringEscapeUtils.escapeSql(vo.getRepoId())+"' ");*/
			bufInsSql.append("  SELECT ASSET_CHECKLIST,CHECKLIST_STATUS,REMARKS FROM REPO_STOCKYARD_ASSET_CHECKLIST_DTL WHERE REPO_ID ='"+StringEscapeUtils.escapeSql(vo.getRepoId())+"' ");
			logger.info("search Query...." + bufInsSql);

			searchlist = ConnectionDAOforEJB.sqlSelect(bufInsSql.toString());
			logger.info("IN openEditRepoDetailByStockyard() search query1 ### "+ bufInsSql.toString());
			logger.info("openEditRepoDetailByStockyard " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
				logger.info("openEditRepoDetailByStockyard " + searchlist.get(i).toString());
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					RepoConfirmationtVo caseInitMakerVo = new RepoConfirmationtVo();
					
					/*caseInitMakerVo.setAssetChecklist(CommonFunction.checkNull(data.get(0)).toString());
					caseInitMakerVo.setCheckListStatus(CommonFunction.checkNull(data.get(1)).toString());
					caseInitMakerVo.setCheckListRemarks(CommonFunction.checkNull(data.get(2)).toString());
					caseInitMakerVo.setRepoId(CommonFunction.checkNull(data.get(3)).toString());
					caseInitMakerVo.setLoanNo(CommonFunction.checkNull(data.get(4)).toString());
					caseInitMakerVo.setProductDescription(CommonFunction.checkNull(data.get(5)).toString());*/
					
					caseInitMakerVo.setAssetChecklist(CommonFunction.checkNull(data.get(0)).toString());
					           caseInitMakerVo.setCheckListStatus(CommonFunction.checkNull(data.get(1)).toString());
					           caseInitMakerVo.setCheckListRemarks(CommonFunction.checkNull(data.get(2)).toString());
					
					list.add(caseInitMakerVo);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

public boolean saveRepoConfirmationDetail(Object ob) {
	RepoConfirmationtVo vo = (RepoConfirmationtVo) ob;
	String repoId = (String) vo.getRepoId();

	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	
	ArrayList updatelist = new ArrayList();
	
	boolean status = false;
	


	try {
		
		StringBuffer bufInsSql = new StringBuffer();
		
		logger.info("In saveRepoConfirmationDetail..........inside ejb server file............Dao Impl");
		
		bufInsSql.append(" UPDATE REPO_CASE_DTL SET AGENCY_FLAG=?,STOCKYARD_FLAG=?,REPO_CONFIRMATION_FLAG = ?, REPO_CONFIRMATION_REMARKS = ?, " );
		bufInsSql.append("REPO_CONFIRMED_BY=?,REPO_CONFIRMATION_DATE=");
		bufInsSql.append("DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ");
		bufInsSql.append(" where REPO_ID=?");

		if(CommonFunction.checkNull(vo.getDecision()).equalsIgnoreCase("Y"))
		{
			insertPrepStmtObject.addString("Y");
		
			insertPrepStmtObject.addString("Y");
			
			insertPrepStmtObject.addString("Y");
		}
		else if(CommonFunction.checkNull(vo.getDecision()).equalsIgnoreCase("N"))
		{
			insertPrepStmtObject.addString("N");
			
			insertPrepStmtObject.addString("N");
			
			insertPrepStmtObject.addString("N");
		}
		else if(CommonFunction.checkNull(vo.getDecision()).equalsIgnoreCase("X"))
		{
			insertPrepStmtObject.addString("X");
			
			insertPrepStmtObject.addString("X");
			
			insertPrepStmtObject.addString("X");
		}
		
		if (CommonFunction.checkNull(vo.getComments())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getComments()
					.toUpperCase().trim());
		
		if (CommonFunction.checkNull(vo.getAutherId())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getAutherId()
					.toUpperCase().trim());
		
		if (CommonFunction.checkNull(vo.getAutherDate())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getAutherDate()
					.toUpperCase().trim());
		
		if (CommonFunction.checkNull(vo.getRepoId())
				.equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(vo.getRepoId());

		insertPrepStmtObject.setSql(bufInsSql.toString());

		updatelist.add(insertPrepStmtObject);
		
		logger.info("In getListOfValues" + bufInsSql.toString());
		
		logger.info("## In saveRepoConfirmationDetail() : update query (Reassign Case) "+insertPrepStmtObject.printQuery());
		
		status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(updatelist);

	
	} catch (Exception e) {
		e.printStackTrace();
	}
	return status;
}
	
	
	//Nazia work space end
	
	

	
	
		
}
