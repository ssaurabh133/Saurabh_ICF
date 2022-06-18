package com.cp.collateralVerification.dao;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.cp.collateralVerification.vo.CollateralCapturingVO;
import com.cp.vo.CommonDealVo;
import com.cp.vo.HeaderInfoVo;
import com.logger.LoggerMsg;

public class CollateralVerificationDAOImpl implements CollateralVerificationDAO {

	private static final Logger logger = Logger.getLogger(CollateralVerificationDAOImpl.class.getName());
	ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
	String dateFormat = resource.getString("lbl.dateInDao");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");


	public ArrayList<CommonDealVo> searchCollateralVerifiationCapturingData(CommonDealVo vo) {
		ArrayList<CommonDealVo> list = new ArrayList<CommonDealVo>();
		String userName=null;
		try {
			logger.info("Inside searchCollateralVerifiationCapturingData.....");
			ArrayList header = null;

			logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+vo.getUserId());
			 			String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+vo.getUserId()+"'";
			 		userName=ConnectionDAO.singleReturn(userNameQ);
			 			logger.info("userNameQ: "+userNameQ+" userName: "+userName);
			 			
			 		userNameQ=null;
			 			
			CommonDealVo fetchVo = (CommonDealVo) vo;
			boolean appendSQL = false;
			int count=0;
			int startRecordIndex=0;
			int endRecordIndex = 5;
			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			

			bufInsSql.append(" select distinct d.DEAL_ID,DEAL_NO, ");
			bufInsSql.append(dbo);
			bufInsSql.append("DATE_FORMAT(DEAL_DATE,'"+dateFormat+"'),");
			bufInsSql.append("deal.CUSTOMER_NAME,p.PRODUCT_DESC,s.SCHEME_DESC from cr_deal_dtl d, "
				+" cr_deal_loan_dtl n, cr_product_m p, cr_deal_customer_m deal,cr_scheme_m s, cr_deal_verification_dtl dv"
				+" left join com_agency_m ag on dv.external_appraiser=ag.AGENCY_CODE"  
				+" left join com_agency_user_mapping agm on ag.AGENCY_CODE=agm.AGENCY_CODE  "
				+" WHERE d.DEAL_ID=n.DEAL_ID and d.DEAL_ID=dv.DEAL_ID "
				+" and n.DEAL_PRODUCT=p.PRODUCT_ID and deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID "
				+" and n.DEAL_SCHEME=s.SCHEME_ID and dv.VERIFICATION_TYPE='C'"
				+" and (dv.internal_appraiser = '"+CommonFunction.checkNull(vo.getUserId())+"' or agm.USER_ID = '"+CommonFunction.checkNull(vo.getUserId())+"')");
			
			bufInsSqlTempCount.append(" select count(distinct d.DEAL_ID) from cr_deal_dtl d, "
					+" cr_deal_loan_dtl n, cr_product_m p, cr_deal_customer_m deal,cr_scheme_m s, cr_deal_verification_dtl dv"
					+" left join com_agency_m ag on dv.external_appraiser=ag.AGENCY_CODE"  
					+" left join com_agency_user_mapping agm on ag.AGENCY_CODE=agm.AGENCY_CODE  "
					+" WHERE d.DEAL_ID=n.DEAL_ID and d.DEAL_ID=dv.DEAL_ID "
					+" and n.DEAL_PRODUCT=p.PRODUCT_ID and deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID "
					+" and n.DEAL_SCHEME=s.SCHEME_ID and dv.VERIFICATION_TYPE='C'"
					+" and (dv.internal_appraiser = '"+CommonFunction.checkNull(vo.getUserId())+"' or agm.USER_ID = '"+CommonFunction.checkNull(vo.getUserId())+"')");
			
			if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo()).trim()).equalsIgnoreCase("")))
					&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInitiationDate())).trim().equalsIgnoreCase("")))
					&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomername()).trim()).equalsIgnoreCase("")))
					&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID()).trim()).equalsIgnoreCase("")))
					&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme()).trim()).equalsIgnoreCase("")))) {
				
				bufInsSql.append("and d.DEAL_BRANCH='"+ StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+ "' " +
						" and d.DEAL_ID='"+ StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+ "' AND " );
				bufInsSql.append(dbo);
				bufInsSql.append("date(d.DEAL_DATE) =" );
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE('"+ StringEscapeUtils.escapeSql(vo.getInitiationDate()).trim()+ "','"+ dateFormat+ "') AND" +
						" deal.CUSTOMER_NAME like'%"+ StringEscapeUtils.escapeSql(vo.getCustomername()).trim()+ "%' AND" +
						" n.DEAL_PRODUCT='"+ StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim() + "'AND" +
						" n.DEAL_SCHEME='"+ StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+ "' AND " +
						" d.Rec_status='F' and dv.rec_status='F'");
				
				bufInsSqlTempCount.append("and d.DEAL_BRANCH='"+ StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+ "' " +
						" and d.DEAL_ID='"+ StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+ "' AND " );
				bufInsSql.append(dbo);
				bufInsSqlTempCount.append("date(d.DEAL_DATE) =" );
				bufInsSqlTempCount.append(dbo);
				bufInsSqlTempCount.append("STR_TO_DATE('"+ StringEscapeUtils.escapeSql(vo.getInitiationDate()).trim()+ "','"+ dateFormat+ "') AND" +
						" deal.CUSTOMER_NAME like'%"+ StringEscapeUtils.escapeSql(vo.getCustomername()).trim()+ "%' AND" +
						" n.DEAL_PRODUCT='"+ StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim() + "'AND" +
						" n.DEAL_SCHEME='"+ StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+ "' AND " +
						" d.Rec_status='F' and dv.rec_status='F'");
			}

			if (((vo.getLbxDealNo().equalsIgnoreCase("")))
					|| ((vo.getInitiationDate().equalsIgnoreCase("")))
					|| ((vo.getCustomername().equalsIgnoreCase("")))
					|| ((vo.getLbxProductID().equalsIgnoreCase("")))
					|| ((vo.getLbxscheme().equalsIgnoreCase("")))) {
				appendSQL = true;
			}

			if (appendSQL) {
				logger.info("In Where Clause");
				bufInsSql.append(" and d.DEAL_BRANCH='"
						+ StringEscapeUtils.escapeSql(vo.getBranchId()).trim()
						+ "' and d.Rec_status='F' and dv.rec_status='F' ");
				bufInsSqlTempCount.append(" and d.DEAL_BRANCH='"
						+ StringEscapeUtils.escapeSql(vo.getBranchId()).trim()
						+ "' and d.Rec_status='F' and dv.rec_status='F' ");
			}

			if ((!(StringEscapeUtils.escapeSql(
					CommonFunction.checkNull(vo.getLbxDealNo())).trim()
					.equalsIgnoreCase("")))) {
				bufInsSql.append("AND d.DEAL_ID='"
						+ StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()
						+ "' ");
				bufInsSqlTempCount.append("AND d.DEAL_ID='"
						+ StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()
						+ "' ");
				appendSQL = true;

			}

			if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getInitiationDate())).trim().equalsIgnoreCase("")))) {
				bufInsSql.append("AND ");
				bufInsSql.append(dbo);
				bufInsSql.append("date(d.DEAL_DATE) =");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE('"	+ StringEscapeUtils.escapeSql(vo.getInitiationDate()).trim() + "','" + dateFormat + "')");
						
				bufInsSqlTempCount.append("AND ");
				bufInsSql.append(dbo);
				bufInsSql.append("date(d.DEAL_DATE) =" );
				bufInsSqlTempCount.append(dbo);
				bufInsSqlTempCount.append("STR_TO_DATE('"+ StringEscapeUtils.escapeSql(vo.getInitiationDate()).trim() + "','" + dateFormat + "') ");
				appendSQL = true;
			}

			if ((!(StringEscapeUtils.escapeSql(
					CommonFunction.checkNull(vo.getCustomername())).trim()
					.equalsIgnoreCase("")))) {
				bufInsSql.append("AND deal.CUSTOMER_NAME like'%"+ StringEscapeUtils.escapeSql(vo.getCustomername()).trim() + "%' ");
				bufInsSqlTempCount.append("AND deal.CUSTOMER_NAME like'%"+ StringEscapeUtils.escapeSql(vo.getCustomername()).trim() + "%' ");
				appendSQL = true;
			}
			if ((!(StringEscapeUtils.escapeSql(
					CommonFunction.checkNull(vo.getLbxProductID())).trim().equalsIgnoreCase("")))) {
				bufInsSql.append("AND n.DEAL_PRODUCT='"+ StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim() + "' ");
				bufInsSqlTempCount.append("AND n.DEAL_PRODUCT='"+ StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim() + "' ");
				appendSQL = true;
			}
			if ((!(StringEscapeUtils.escapeSql(	CommonFunction.checkNull(vo.getLbxscheme())).trim().equalsIgnoreCase("")))) {
				bufInsSql.append("AND n.DEAL_SCHEME='"+ StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+ "' ");
				bufInsSqlTempCount.append("AND n.DEAL_SCHEME='"+ StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+ "' ");
				appendSQL = true;
			}

			logger.info("count query bufInsSqlTempCount:::::::"+bufInsSqlTempCount);
			logger.info("count query bufInsSqlTempCount:::::::"+bufInsSqlTempCount.toString());
			
			if((CommonFunction.checkNull(vo.getLbxDealNo()).trim()==null && CommonFunction.checkNull(vo.getInitiationDate()).trim()==null && CommonFunction.checkNull(vo.getCustomername()).trim()==null && CommonFunction.checkNull(vo.getLbxProductID()).trim()==null && CommonFunction.checkNull(vo.getLbxscheme()).trim()==null) || (CommonFunction.checkNull(vo.getLbxDealNo()).trim().equalsIgnoreCase("") && CommonFunction.checkNull(vo.getInitiationDate()).trim().equalsIgnoreCase("") && CommonFunction.checkNull(vo.getCustomername()).trim().equalsIgnoreCase("") && CommonFunction.checkNull(vo.getLbxProductID()).trim().equalsIgnoreCase("") && CommonFunction.checkNull(vo.getLbxscheme()).trim().equalsIgnoreCase("")) || vo.getCurrentPageLink()>1)
			{
			
				logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
				if(vo.getCurrentPageLink()>1)
				{
					startRecordIndex = (vo.getCurrentPageLink()-1)*5;
					endRecordIndex = 5;
					logger.info("startRecordIndex .................... "+startRecordIndex);
					logger.info("endRecordIndex .................... "+endRecordIndex);
				}
			
				if(dbType.equalsIgnoreCase("MSSQL"))
				{
					bufInsSql.append(" ORDER BY d.DEAL_ID OFFSET ");
					bufInsSql.append(startRecordIndex);
					bufInsSql.append(" ROWS FETCH next ");
					bufInsSql.append(endRecordIndex);
					bufInsSql.append(" ROWS ONLY ");
					logger.info("Search searchGenericMasterDao query for SQL SERVER : " + bufInsSql.toString());
				}
				else
				{
					bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
				}
			
			}
			logger.info("count query bufInsSqlTempCount:::::::"+bufInsSqlTempCount);
			logger.info("count query bufInsSqlTempCount:::::::"+bufInsSqlTempCount.toString());
			header = ConnectionDAO.sqlSelect(bufInsSql.toString());
			count = Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
			
			bufInsSql=null;
			bufInsSqlTempCount=null;

			for (int i = 0; i < header.size(); i++) {
				logger.info("header: " + header.size());
				ArrayList header1 = (ArrayList) header.get(i);
				if (header1 != null && header1.size() > 0) {

					fetchVo = new CommonDealVo();
					fetchVo.setLbxDealNo((CommonFunction.checkNull(header1.get(0))).trim());
					fetchVo.setDealNo("<a href=collateralVerificationCapturingSearch.do?method=openCollateralCapturing&dealId="
	      					+ (CommonFunction.checkNull(header1.get(0)).trim())+">"
	      					+ (CommonFunction.checkNull(header1.get(1)).trim()) + "</a>");
					fetchVo.setInitiationDate((CommonFunction.checkNull(header1.get(2))).trim());
					fetchVo.setCustomername((CommonFunction.checkNull(header1.get(3))).trim());
					fetchVo.setProduct((CommonFunction.checkNull(header1.get(4))).trim());
					fetchVo.setScheme((CommonFunction.checkNull(header1.get(5))).trim());
					fetchVo.setReportingToUserId(userName);
					fetchVo.setTotalRecordSize(count);
					list.add(fetchVo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			userName=null;
		}

		LoggerMsg.info("Detail List when searchList is : " + list.size());

		return list;
	}

	public ArrayList<CollateralCapturingVO> getCollateralCapturingData(String dealId, String recStatus,String userId) {
		logger.info("Inside getCollateralClass method......DAOImpl");
		
	
		ArrayList<CollateralCapturingVO> collateralList = new ArrayList<CollateralCapturingVO>();
		try {
			  ArrayList searchlist=new ArrayList();
	 	      boolean appendSQL=false;
	 	      StringBuffer bufInsSql =	new StringBuffer();
			
	 	      
	 	      
			 bufInsSql.append("SELECT DISTINCT DV.DEAL_ID,DC.ASSETID,AC.ASSET_COLLATERAL_CLASS,AC.ASSET_COLLATERAL_DESC,AC.ASSET_COLLATERAL_VALUE, ");
			 bufInsSql.append("case  when DC.ASSETID not in (select collateral_id from cr_collatetral_check_dtl ccd) then 'Pending' ELSE 'In process' end as rec_status,DV.VERIFICATION_ID ");
			 bufInsSql.append("FROM cr_asset_collateral_m AC,cr_deal_collateral_m DC,cr_deal_verification_dtl DV ");
			 bufInsSql.append("left join com_agency_m ag on dv.external_appraiser=ag.AGENCY_CODE  left join com_agency_user_mapping agm on ag.AGENCY_CODE=agm.AGENCY_CODE ");
			 bufInsSql.append("WHERE DV.DEAL_ID=DC.DEAL_ID AND DC.ASSETID=AC.ASSET_ID AND DV.VERIFICATION_TYPE='C' AND DV.REC_STATUS='"+CommonFunction.checkNull(recStatus)+"' AND DV.VERIFICATION_ACTION='I' ");
			 bufInsSql.append("AND DC.REC_STATUS='F' AND DV.DEAL_ID='"+ StringEscapeUtils.escapeSql(dealId) + "' AND AC.ASSET_TYPE = 'COLLATERAL' ");
			 if(recStatus.equals("F"))
			 {
			 bufInsSql.append("and (DV.internal_appraiser = '"+CommonFunction.checkNull(userId)+"' or agm.USER_ID = '"+CommonFunction.checkNull(userId)+"') ");
			 }
			 bufInsSql.append("AND ((DV.VERIFICATION_SUBTYPE = 'O' AND AC.ASSET_COLLATERAL_CLASS IN ('OTHERS','SBLC', 'BG','SECURITIES', 'FD', 'DEBTOR')) ");
			 bufInsSql.append("OR (DV.VERIFICATION_SUBTYPE = 'C' AND AC.ASSET_COLLATERAL_CLASS = 'MACHINE') OR (DV.VERIFICATION_SUBTYPE = 'V' AND AC.ASSET_COLLATERAL_CLASS = 'VEHICLE') ");
			 bufInsSql.append("OR (DV.VERIFICATION_SUBTYPE = 'T' AND AC.ASSET_COLLATERAL_CLASS = 'STOCK') OR (DV.VERIFICATION_SUBTYPE = 'P' AND AC.ASSET_COLLATERAL_CLASS = 'PROPERTY')) GROUP BY VERIFICATION_ID ");
     
			 	LoggerMsg.info("query : "+bufInsSql);
				searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
				LoggerMsg.info("IN searchCMGrid() search query1 ### "+ bufInsSql.toString());
				LoggerMsg.info("searchCMGrid " + searchlist.size());
	 	      
	 	    bufInsSql=null;
				
			for (int i = 0; i < searchlist.size(); i++) {
				ArrayList subList = (ArrayList) searchlist.get(i);
				
				  if(subList.size()>0){
					  CollateralCapturingVO vo = new CollateralCapturingVO();
					  vo.setDealId((CommonFunction.checkNull(subList.get(0))).trim());
					  vo.setCollateralId((CommonFunction.checkNull(subList.get(1))).trim());
					  vo.setCollateralClass((CommonFunction.checkNull(subList.get(2))).trim());
					  vo.setCollateralDescription((CommonFunction.checkNull(subList.get(3))).trim());
					  
					  if ((CommonFunction.checkNull(subList.get(4))).trim().equalsIgnoreCase(""))
							vo.setCollateralValue("0");
						else {
							Number collateralValue = myFormatter.parse((CommonFunction.checkNull(subList.get(4))).trim());
							vo.setCollateralValue(myFormatter.format(collateralValue));
						}
					  vo.setCollateralStatus((CommonFunction.checkNull(subList.get(5))).trim());
					  vo.setVerificationId((CommonFunction.checkNull(subList.get(6))).trim()); 
				    collateralList.add(vo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return collateralList;
	}
	
	public ArrayList<CollateralCapturingVO> getCollateralCapturingMachineData(String dealId, String verificationId, String collateralId, String recStatus)
	{
		StringBuilder query=new StringBuilder();
		
	query.append("select REFERENCE_NO,APPRAISER_NAME,");
			query.append(dbo);
			query.append("date_format(APPRAISAL_DATE,'"+dateFormat+"')," +
					" VERIFICATION_MODE,PERSON_MET,RELATION,PHONE1,PHONE2,E_MAIL,MACHINE_MAKE,MACHINE_MODEL," +
				" MACHINE_TYPE,MACHINE_OWNER,MACHINE_MANUFACTURING_YEAR," +
				" MACHINE_IDENTIFICATION_NO,MACHINE_MARKET_PRICE," +
				" MACHINE_STATUS,MACHINE_RUNNING from cr_collatetral_check_dtl ccd"
				+ " where ccd.deal_id="+StringEscapeUtils.escapeSql(dealId)+" "
				+ " and ccd.verification_ID="+ StringEscapeUtils.escapeSql(verificationId) + ""
				+ " and ccd.collateral_id="+StringEscapeUtils.escapeSql(collateralId)+""
				+ " and ccd.rec_status='"+StringEscapeUtils.escapeSql(recStatus)+"'");
		logger.info("query::::::::::::::::;" + query);
		logger.info("query::::::::::::::::;" + query.toString());
		
		ArrayList<CollateralCapturingVO> collateralList = new ArrayList<CollateralCapturingVO>();
		try {
			ArrayList mainList = ConnectionDAO.sqlSelect(query.toString());
			
			int mainListSize = mainList.size();
			
			query=null;
			
			for (int i = 0; i < mainListSize; i++) {
				ArrayList subList = (ArrayList) mainList.get(i);
				if (subList != null && subList.size() > 0) {
					CollateralCapturingVO vo = new CollateralCapturingVO();
					vo.setRefrenceNo((CommonFunction.checkNull(subList.get(0))).trim());
					vo.setAppraiser((CommonFunction.checkNull(subList.get(1))).trim());
					vo.setAppraisalDate((CommonFunction.checkNull(subList.get(2))).trim());
					vo.setVerificationMode((CommonFunction.checkNull(subList.get(3))).trim());
					vo.setPersonMet((CommonFunction.checkNull(subList.get(4))).trim());
					vo.setRelation((CommonFunction.checkNull(subList.get(5))).trim());
					vo.setPhone1((CommonFunction.checkNull(subList.get(6))).trim());
					vo.setPhone2((CommonFunction.checkNull(subList.get(7))).trim());
					vo.setEmail((CommonFunction.checkNull(subList.get(8))).trim());
					vo.setMachineMake((CommonFunction.checkNull(subList.get(9))).trim());
					vo.setMachineModel((CommonFunction.checkNull(subList.get(10))).trim());
					vo.setMachineType((CommonFunction.checkNull(subList.get(11))).trim());
					vo.setMachineOwner((CommonFunction.checkNull(subList.get(12))).trim());
					vo.setMachineYearOfManufact((CommonFunction.checkNull(subList.get(13))).trim());
					vo.setMachineIdNo((CommonFunction.checkNull(subList.get(14))).trim());
					if ((CommonFunction.checkNull(subList.get(15))).trim().equalsIgnoreCase(""))
						vo.setMachinePrice("0");
					else {
						Number machinePrice = myFormatter.parse((CommonFunction.checkNull(subList.get(15))).trim());
						vo.setMachinePrice(myFormatter.format(machinePrice));
					}
					vo.setMachineStatus((CommonFunction.checkNull(subList.get(16))).trim());
					vo.setMachineRunning((CommonFunction.checkNull(subList.get(17))).trim());
					collateralList.add(vo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return collateralList;
	}

	public boolean saveMachineVerificationDetails(CollateralCapturingVO vo) {
		logger.info("Inside saveMachineVerificationDetails.....DAOImpl");
		boolean status = false;
		
		StringBuilder query=new StringBuilder();
		
		query.append("select count(deal_id) from cr_collatetral_check_dtl where deal_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDealId()))+"" +
				" and verification_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getVerificationId()))+"" +
				" and collateral_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCollateralId()))+"");
		int count = Integer.parseInt(ConnectionDAO.singleReturn(query.toString()));
		
		query=null;
		
		if(count==0)
		{
			try {
				StringBuffer bufInsSql = new StringBuffer();
				PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
				ArrayList qryList = new ArrayList();
				bufInsSql.append("insert into cr_collatetral_check_dtl (VERIFICATION_ID,DEAL_ID,REFERENCE_NO,"
					+ "APPRAISER_NAME,APPRAISAL_DATE,VERIFICATION_MODE,PERSON_MET,RELATION,PHONE1,PHONE2,E_MAIL,"
					+ "COLLATERAL_ID,COLLATERAL_CLASS,MACHINE_MAKE,MACHINE_MODEL,MACHINE_TYPE,MACHINE_OWNER,"
					+ "MACHINE_MANUFACTURING_YEAR,MACHINE_IDENTIFICATION_NO,MACHINE_MARKET_PRICE,MACHINE_STATUS,"
					+ "MACHINE_RUNNING,REC_STATUS,MAKER_ID,MAKER_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,"); //VERIFICATION_ID
				bufInsSql.append(" ?,"); //DEAL_ID
				bufInsSql.append(" ?,"); //REFERENCE_NO
				bufInsSql.append(" ?,"); //APPRAISER_NAME
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'" + dateFormat + "'),"); //APPRAISAL_DATE
				bufInsSql.append(" ?,"); //VERIFICATION_MODE
				bufInsSql.append(" ?,"); //PERSON_MET
				bufInsSql.append(" ?,"); //RELATION
				bufInsSql.append(" ?,"); //PHONE1
				bufInsSql.append(" ?,"); //PHONE2
				bufInsSql.append(" ?,"); //E_MAIL
				bufInsSql.append(" ?,"); //COLLATERAL_ID
				bufInsSql.append(" ?,"); //COLLATERAL_CLASS
				bufInsSql.append(" ?,"); //MACHINE_MAKE
				bufInsSql.append(" ?,"); //MACHINE_MODEL
				bufInsSql.append(" ?,"); //MACHINE_TYPE
				bufInsSql.append(" ?,"); //MACHINE_OWNER
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'" + dateFormat + "'),"); //MACHINE_MANUFACTURING_YEAR
				bufInsSql.append(" ?,"); //MACHINE_IDENTIFICATION_NO
				bufInsSql.append(" ?,"); //MACHINE_MARKET_PRICE
				bufInsSql.append(" ?,"); //MACHINE_STATUS
				bufInsSql.append(" ?,"); //MACHINE_RUNNING
				bufInsSql.append(" ?,"); //REC_STATUS
				bufInsSql.append(" ?,"); //MAKER_ID
//				bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND))"); //MAKER_DATE
				
				if(dbType.equalsIgnoreCase("MSSQL"))
				{
					bufInsSql.append(" dbo.STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");


				}
				else
				{
					bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND))"); //MAKER_DATE
				}

	
				if (CommonFunction.checkNull(vo.getVerificationId()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVerificationId()).trim()));
				
				if (CommonFunction.checkNull(vo.getDealId()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getDealId()).trim()));
				
				if ((CommonFunction.checkNull(vo.getRefrenceNo())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getRefrenceNo()).trim()));
				
				if ((CommonFunction.checkNull(vo.getAppraiser())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getAppraiser()).trim()));
				
				if ((CommonFunction.checkNull(vo.getAppraisalDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getAppraisalDate()).trim()));
				
				if ((CommonFunction.checkNull(vo.getVerificationMode())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVerificationMode()).trim()));
				
				if ((CommonFunction.checkNull(vo.getPersonMet())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPersonMet()).trim()));
	
				if ((CommonFunction.checkNull(vo.getRelation())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getRelation()).trim()));
				
				if ((CommonFunction.checkNull(vo.getPhone1())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPhone1()).trim()));
				
				if ((CommonFunction.checkNull(vo.getPhone2())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPhone2()).trim()));
				
				if ((CommonFunction.checkNull(vo.getEmail())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getEmail()).trim()));
	
				if ((CommonFunction.checkNull(vo.getCollateralId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getCollateralId()).trim()));
	
				if ((CommonFunction.checkNull(vo.getCollateralClass())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getCollateralClass()).trim()));
				
				if ((CommonFunction.checkNull(vo.getMachineMake())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMachineMake()).trim()));
				
				if ((CommonFunction.checkNull(vo.getMachineModel())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMachineModel()).trim()));
				
				if ((CommonFunction.checkNull(vo.getMachineType())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMachineType()).trim()));
	
				if ((CommonFunction.checkNull(vo.getMachineOwner())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMachineOwner()).trim()));
	
				if ((CommonFunction.checkNull(vo.getMachineYearOfManufact())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMachineYearOfManufact()).trim()));
	
				if ((CommonFunction.checkNull(vo.getMachineIdNo())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMachineIdNo()).trim()));
	
				if ((CommonFunction.checkNull(vo.getMachinePrice())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getMachinePrice()).trim())).toString());
				
				if ((CommonFunction.checkNull(vo.getMachineStatus())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMachineStatus()).trim()));
				
				if ((CommonFunction.checkNull(vo.getMachineRunning())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMachineRunning()).trim()));
				
				insertPrepStmtObject.addString("P");
	
				if ((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerId()).trim()));
	
				if ((CommonFunction.checkNull(vo.getMakerDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerDate()).trim()));
	
				insertPrepStmtObject.setSql(bufInsSql.toString());
	
				qryList.add(insertPrepStmtObject);
				logger.info("IN saveMachineVerificationDetails() insert query1: "+ insertPrepStmtObject.printQuery());
	
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In saveMachineVerificationDetails status: "+ status);
				
				bufInsSql=null;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(count>0)
		{
			StringBuilder updQuery=new StringBuilder();
			updQuery.append("update cr_collatetral_check_dtl set REFERENCE_NO=?,APPRAISER_NAME=?,APPRAISAL_DATE=");
			updQuery.append(dbo);
			updQuery.append("STR_TO_DATE(?,'"+dateFormat+"'),VERIFICATION_MODE=?,PERSON_MET=?,RELATION=?,PHONE1=?,PHONE2=?,E_MAIL=?,"
					+ "MACHINE_MAKE=?,MACHINE_MODEL=?,MACHINE_TYPE=?,MACHINE_OWNER=?,MACHINE_MANUFACTURING_YEAR=");
			updQuery.append(dbo);
			updQuery.append("STR_TO_DATE(?,'"+dateFormat+"'),MACHINE_IDENTIFICATION_NO=?,MACHINE_MARKET_PRICE=?,MACHINE_STATUS=?,MACHINE_RUNNING=?,MAKER_ID=?,MAKER_DATE=");
			
			if(dbType.equalsIgnoreCase("MSSQL"))
			{
				updQuery.append("dbo.STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
			}
			else
			{
				updQuery.append("DATE_ADD(STR_TO_DATE(?,'"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ");
			}

			
			updQuery.append("where deal_id=? and verification_id=? and collateral_id=?");
			
			PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
			ArrayList qryList = new ArrayList();
			
			try
			{
				if ((CommonFunction.checkNull(vo.getRefrenceNo())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getRefrenceNo()).trim()));
				
				if ((CommonFunction.checkNull(vo.getAppraiser())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getAppraiser()).trim()));
				
				if ((CommonFunction.checkNull(vo.getAppraisalDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getAppraisalDate()).trim()));
				
				if ((CommonFunction.checkNull(vo.getVerificationMode())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVerificationMode()).trim()));
				
				if ((CommonFunction.checkNull(vo.getPersonMet())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPersonMet()).trim()));
	
				if ((CommonFunction.checkNull(vo.getRelation())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getRelation()).trim()));
				
				if ((CommonFunction.checkNull(vo.getPhone1())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPhone1()).trim()));
				
				if ((CommonFunction.checkNull(vo.getPhone2())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPhone2()).trim()));
				
				if ((CommonFunction.checkNull(vo.getEmail())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getEmail()).trim()));
	
				if ((CommonFunction.checkNull(vo.getMachineMake())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMachineMake()).trim()));
				
				if ((CommonFunction.checkNull(vo.getMachineModel())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMachineModel()).trim()));
				
				if ((CommonFunction.checkNull(vo.getMachineType())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMachineType()).trim()));
	
				if ((CommonFunction.checkNull(vo.getMachineOwner())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMachineOwner()).trim()));
	
				if ((CommonFunction.checkNull(vo.getMachineYearOfManufact())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMachineYearOfManufact()).trim()));
	
				if ((CommonFunction.checkNull(vo.getMachineIdNo())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMachineIdNo()).trim()));
	
				if ((CommonFunction.checkNull(vo.getMachinePrice())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getMachinePrice()).trim())).toString());
				
				if ((CommonFunction.checkNull(vo.getMachineStatus())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMachineStatus()).trim()));
				
				if ((CommonFunction.checkNull(vo.getMachineRunning())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMachineRunning()).trim()));
	
				if ((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerId()).trim()));
	
				if ((CommonFunction.checkNull(vo.getMakerDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerDate()).trim()));
				
				if ((CommonFunction.checkNull(vo.getDealId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getDealId()).trim()));
				
				if ((CommonFunction.checkNull(vo.getVerificationId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVerificationId()).trim()));
				
				if ((CommonFunction.checkNull(vo.getCollateralId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getCollateralId()).trim()));
	
				insertPrepStmtObject.setSql(updQuery.toString());
	
				qryList.add(insertPrepStmtObject);
				logger.info("IN saveMachineVerificationDetails() update query1: "+ insertPrepStmtObject.printQuery());
	
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In saveMachineVerificationDetails status: "+ status);
			} catch (Exception e) {
				e.printStackTrace();
			}
			updQuery=null;
		}
		return status;
	}
	
	public ArrayList<CollateralCapturingVO> getCollateralCapturingPropertyData(String dealId, String verificationId, String collateralId, String recStatus)
	{
		StringBuilder query=new StringBuilder();
		
	 query.append("select REFERENCE_NO,APPRAISER_NAME,");
	 query.append(dbo);
	 query.append("date_format(APPRAISAL_DATE,'"+dateFormat+"')," +
				" VERIFICATION_MODE,PERSON_MET,RELATION,PHONE1,PHONE2,E_MAIL," +
				" PROPERTY_ADDRESS,PROPERTY_STATUS,PROPERTY_OWNERSHIP,PROPERTY_AREA,PROPERTY_ACCESSIBILITY," +
				" PROPERTY_AREA_TYPE,PROPERTY_POPULATION,PROPOERTY_MARKET_VALUE,CONSTRUCTION_AREA,PROPERTY_ADDRESS" +
				" from cr_collatetral_check_dtl ccd"
				+ " where ccd.deal_id="+StringEscapeUtils.escapeSql(dealId)+" "
				+ " and ccd.verification_ID="+ StringEscapeUtils.escapeSql(verificationId) + ""
				+ " and ccd.collateral_id="+StringEscapeUtils.escapeSql(collateralId)+""
				+ " and ccd.rec_status='"+StringEscapeUtils.escapeSql(recStatus)+"'");
		logger.info("query::::::::::::::;" + query);
		logger.info("query::::::::::::::;" + query.toString());
		ArrayList<CollateralCapturingVO> collateralList = new ArrayList<CollateralCapturingVO>();
		try {
			ArrayList mainList = ConnectionDAO.sqlSelect(query.toString());
			int mainListSize = mainList.size();
			
			query=null;
			
			for (int i = 0; i < mainListSize; i++) {
				ArrayList subList = (ArrayList) mainList.get(i);
				if (subList != null && subList.size() > 0) {
					CollateralCapturingVO vo = new CollateralCapturingVO();
					vo.setRefrenceNo((CommonFunction.checkNull(subList.get(0))).trim());
					vo.setAppraiser((CommonFunction.checkNull(subList.get(1))).trim());
					vo.setAppraisalDate((CommonFunction.checkNull(subList.get(2))).trim());
					vo.setVerificationMode((CommonFunction.checkNull(subList.get(3))).trim());
					vo.setPersonMet((CommonFunction.checkNull(subList.get(4))).trim());
					vo.setRelation((CommonFunction.checkNull(subList.get(5))).trim());
					vo.setPhone1((CommonFunction.checkNull(subList.get(6))).trim());
					vo.setPhone2((CommonFunction.checkNull(subList.get(7))).trim());
					vo.setEmail((CommonFunction.checkNull(subList.get(8))).trim());
					
					vo.setPropertyAddress((CommonFunction.checkNull(subList.get(9))).trim());
					vo.setPropertyStatus((CommonFunction.checkNull(subList.get(10))).trim());
					vo.setPropertyOwnership((CommonFunction.checkNull(subList.get(11))).trim());
					vo.setPropertyArea((CommonFunction.checkNull(subList.get(12))).trim());
					vo.setPropertyAccessibility((CommonFunction.checkNull(subList.get(13))).trim());
					vo.setPropertyAreaType((CommonFunction.checkNull(subList.get(14))).trim());
					vo.setPropertyPopulation((CommonFunction.checkNull(subList.get(15))).trim());
					if ((CommonFunction.checkNull(subList.get(16))).trim().equalsIgnoreCase(""))
						vo.setPropertyMarketValue("0");
					else {
						Number propertyPrice = myFormatter.parse((CommonFunction.checkNull(subList.get(16))).trim());
						vo.setPropertyMarketValue(myFormatter.format(propertyPrice));
					}
					vo.setConstructionArea((CommonFunction.checkNull(subList.get(17))).trim());
					vo.setPropertyAddress(CommonFunction.checkNull(subList.get(18)).trim());
					collateralList.add(vo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return collateralList;
	}
	
	public boolean savePropertyVerificationDetails(CollateralCapturingVO vo) {
		logger.info("Inside savePropertyVerificationDetails.....DAOImpl");
		boolean status = false;
		
		StringBuilder query=new StringBuilder();
		
	query.append("select count(deal_id) from cr_collatetral_check_dtl where deal_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDealId()))+"'" +
				" and verification_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getVerificationId()))+"'" +
				" and collateral_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCollateralId()))+"");
		int count = Integer.parseInt(ConnectionDAO.singleReturn(query.toString()));
		
		query=null;
		
		if(count==0)
		{
			try {
				StringBuffer bufInsSql = new StringBuffer();
				PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
				ArrayList qryList = new ArrayList();
				bufInsSql.append("insert into cr_collatetral_check_dtl (VERIFICATION_ID,DEAL_ID,REFERENCE_NO,"
					+ "APPRAISER_NAME,APPRAISAL_DATE,VERIFICATION_MODE,PERSON_MET,RELATION,PHONE1,PHONE2,E_MAIL,"
					+ "COLLATERAL_ID,COLLATERAL_CLASS,PROPERTY_ADDRESS,PROPERTY_STATUS,PROPERTY_OWNERSHIP,"
					+ "PROPERTY_AREA,PROPERTY_ACCESSIBILITY,PROPERTY_AREA_TYPE,PROPERTY_POPULATION,"
					+ "PROPOERTY_MARKET_VALUE,CONSTRUCTION_AREA,REC_STATUS,MAKER_ID,MAKER_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,"); //VERIFICATION_ID
				bufInsSql.append(" ?,"); //DEAL_ID
				bufInsSql.append(" ?,"); //REFERENCE_NO
				bufInsSql.append(" ?,"); //APPRAISER_NAME
				bufInsSql.append(dbo); 
				bufInsSql.append("STR_TO_DATE(?,'" + dateFormat + "'),"); //APPRAISAL_DATE
				bufInsSql.append(" ?,"); //VERIFICATION_MODE
				bufInsSql.append(" ?,"); //PERSON_MET
				bufInsSql.append(" ?,"); //RELATION
				bufInsSql.append(" ?,"); //PHONE1
				bufInsSql.append(" ?,"); //PHONE2
				bufInsSql.append(" ?,"); //E_MAIL
				bufInsSql.append(" ?,"); //COLLATERAL_ID
				bufInsSql.append(" ?,"); //COLLATERAL_CLASS
				bufInsSql.append(" ?,"); //PROPERTY_ADDRESS
				bufInsSql.append(" ?,"); //PROPERTY_STATUS
				bufInsSql.append(" ?,"); //PROPERTY_OWNERSHIP
				bufInsSql.append(" ?,"); //PROPERTY_AREA
				bufInsSql.append(" ?,"); //PROPERTY_ACCESSIBILITY
				bufInsSql.append(" ?,"); //PROPERTY_AREA_TYPE
				bufInsSql.append(" ?,"); //PROPERTY_POPULATION
				bufInsSql.append(" ?,"); //PROPOERTY_MARKET_VALUE
				bufInsSql.append(" ?,"); //CONSTRUCTION_AREA
				bufInsSql.append(" ?,"); //REC_STATUS
				bufInsSql.append(" ?,"); //MAKER_ID
				//bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND))"); //MAKER_DATE
				
				if(dbType.equalsIgnoreCase("MSSQL"))
				{
					bufInsSql.append(" dbo.STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
				}
				else
				{
					bufInsSql.append(" DATE_ADD(STR_TO_DATE(?,'"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) )");
				}

	
				if (CommonFunction.checkNull(vo.getVerificationId()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVerificationId()).trim()));
				
				if (CommonFunction.checkNull(vo.getDealId()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getDealId()).trim()));
				
				if ((CommonFunction.checkNull(vo.getRefrenceNo())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getRefrenceNo()).trim()));
				
				if ((CommonFunction.checkNull(vo.getAppraiser())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getAppraiser()).trim()));
				
				if ((CommonFunction.checkNull(vo.getAppraisalDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getAppraisalDate()).trim()));
				
				if ((CommonFunction.checkNull(vo.getVerificationMode())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVerificationMode()).trim()));
				
				if ((CommonFunction.checkNull(vo.getPersonMet())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPersonMet()).trim()));
	
				if ((CommonFunction.checkNull(vo.getRelation())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getRelation()).trim()));
				
				if ((CommonFunction.checkNull(vo.getPhone1())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPhone1()).trim()));
				
				if ((CommonFunction.checkNull(vo.getPhone2())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPhone2()).trim()));
				
				if ((CommonFunction.checkNull(vo.getEmail())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getEmail()).trim()));
	
				if ((CommonFunction.checkNull(vo.getCollateralId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getCollateralId()).trim()));
	
				if ((CommonFunction.checkNull(vo.getCollateralClass())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getCollateralClass()).trim()));
				
				if ((CommonFunction.checkNull(vo.getPropertyAddress())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPropertyAddress()).trim()));
				
				if ((CommonFunction.checkNull(vo.getPropertyStatus())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPropertyStatus()).trim()));
				
				if ((CommonFunction.checkNull(vo.getPropertyOwnership())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPropertyOwnership()).trim()));
	
				if ((CommonFunction.checkNull(vo.getPropertyArea())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPropertyArea()).trim()));
	
				if ((CommonFunction.checkNull(vo.getPropertyAccessibility())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPropertyAccessibility()).trim()));
	
				if ((CommonFunction.checkNull(vo.getPropertyAreaType())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPropertyAreaType()).trim()));
				
				if ((CommonFunction.checkNull(vo.getPropertyPopulation())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPropertyPopulation()).trim()));
	
				if ((CommonFunction.checkNull(vo.getPropertyMarketValue())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getPropertyMarketValue()).trim())).toString());
				
				if ((CommonFunction.checkNull(vo.getConstructionArea())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getConstructionArea()).trim()));
				
				insertPrepStmtObject.addString("P");
	
				if ((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerId()).trim()));
	
				if ((CommonFunction.checkNull(vo.getMakerDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerDate()).trim()));
	
				insertPrepStmtObject.setSql(bufInsSql.toString());
	
				qryList.add(insertPrepStmtObject);
				logger.info("IN savePropertyVerificationDetails() insert query1: "+ insertPrepStmtObject.printQuery());
	
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In savePropertyVerificationDetails status: "+ status);
				
				bufInsSql=null;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(count>0)
		{
			StringBuilder updQuery=new StringBuilder();
			
			updQuery.append("update cr_collatetral_check_dtl set REFERENCE_NO=?,APPRAISER_NAME=?,APPRAISAL_DATE=");
			updQuery.append(dbo);
			updQuery.append("STR_TO_DATE(?,'"+dateFormat+"'),VERIFICATION_MODE=?,PERSON_MET=?,RELATION=?,PHONE1=?,PHONE2=?,E_MAIL=?,"
					+ "PROPERTY_ADDRESS=?,PROPERTY_STATUS=?,PROPERTY_OWNERSHIP=?,PROPERTY_AREA=?,PROPERTY_ACCESSIBILITY=?,PROPERTY_AREA_TYPE=?," 
					+ "PROPERTY_POPULATION=?,PROPOERTY_MARKET_VALUE=?,MAKER_ID=?,MAKER_DATE=");
			
			if(dbType.equalsIgnoreCase("MSSQL"))
			{
				updQuery.append(" dbo.STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
			}
			else
			{
				updQuery.append("DATE_ADD(STR_TO_DATE(?,'"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ");
			}
			updQuery.append("where deal_id=? and verification_id=? and collateral_id=? ");
			logger.info("updQuery::::::::::::::::::::::::::::::::::::::::::"+updQuery);
			logger.info("updQuery::::::::::::::::::::::::::::::::::::::::::"+updQuery.toString());
			PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
			ArrayList qryList = new ArrayList();
			
			try
			{
				if ((CommonFunction.checkNull(vo.getRefrenceNo())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getRefrenceNo()).trim()));
				
				if ((CommonFunction.checkNull(vo.getAppraiser())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getAppraiser()).trim()));
				
				if ((CommonFunction.checkNull(vo.getAppraisalDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getAppraisalDate()).trim()));
				
				if ((CommonFunction.checkNull(vo.getVerificationMode())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVerificationMode()).trim()));
				
				if ((CommonFunction.checkNull(vo.getPersonMet())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPersonMet()).trim()));
	
				if ((CommonFunction.checkNull(vo.getRelation())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getRelation()).trim()));
				
				if ((CommonFunction.checkNull(vo.getPhone1())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPhone1()).trim()));
				
				if ((CommonFunction.checkNull(vo.getPhone2())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPhone2()).trim()));
				
				if ((CommonFunction.checkNull(vo.getEmail())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getEmail()).trim()));
	
				if ((CommonFunction.checkNull(vo.getPropertyAddress())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPropertyAddress()).trim()));
				
				if ((CommonFunction.checkNull(vo.getPropertyStatus())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPropertyStatus()).trim()));
				
				if ((CommonFunction.checkNull(vo.getPropertyOwnership())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPropertyOwnership()).trim()));
	
				if ((CommonFunction.checkNull(vo.getPropertyArea())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPropertyArea()).trim()));
	
				if ((CommonFunction.checkNull(vo.getPropertyAccessibility())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPropertyAccessibility()).trim()));
	
				if ((CommonFunction.checkNull(vo.getPropertyAreaType())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPropertyAreaType()).trim()));
				
				if ((CommonFunction.checkNull(vo.getPropertyPopulation())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPropertyPopulation()).trim()));
	
				if ((CommonFunction.checkNull(vo.getPropertyMarketValue())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getPropertyMarketValue()).trim())).toString());
	
				if ((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerId()).trim()));
	
				if ((CommonFunction.checkNull(vo.getMakerDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerDate()).trim()));
				
				if ((CommonFunction.checkNull(vo.getDealId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getDealId()).trim()));
				
				if ((CommonFunction.checkNull(vo.getVerificationId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVerificationId()).trim()));
				
				if ((CommonFunction.checkNull(vo.getCollateralId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getCollateralId()).trim()));
	
				insertPrepStmtObject.setSql(updQuery.toString());
	
				qryList.add(insertPrepStmtObject);
				logger.info("IN savePropertyVerificationDetails() update query1: "+ insertPrepStmtObject.printQuery());
	
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In savePropertyVerificationDetails status: "+ status);
				
			
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			updQuery=null;
		}
		return status;
	}
	
	public ArrayList<CollateralCapturingVO> getCollateralCapturingVehicleData(String dealId, String verificationId, String collateralId, String recStatus)
	{
		StringBuilder query=new StringBuilder();
	 query.append("select REFERENCE_NO,APPRAISER_NAME,");
	 query.append(dbo);
	 query.append("date_format(APPRAISAL_DATE,'"+dateFormat+"')," +
				" VERIFICATION_MODE,PERSON_MET,RELATION,PHONE1,PHONE2,E_MAIL," +
				" VEHICLE_MAKE,VEHICLE_MODEL,VEHICLE_TYPE,VEHICLE_OWNER,VEHICLE_MANUFACTURING_YEAR,VEHICLE_REGISTRATION_NO,");
				 query.append(dbo);
	 query.append("date_format(VEHICLE_REGISTRATION_DATE,'"+dateFormat+"'),VEHICLE_CHASIS_NUMBER,VEHICLE_INSURER,");
	 query.append(dbo);
	 query.append("date_format(VEHICLE_INSURED_DATE,'"+dateFormat+"'),VEHICLE_MARKET_PRICE,VEHICLE_CONDITION from cr_collatetral_check_dtl ccd"
				+ " where ccd.deal_id="+StringEscapeUtils.escapeSql(dealId)+" "
				+ " and ccd.verification_ID="+ StringEscapeUtils.escapeSql(verificationId) + ""
				+ " and ccd.collateral_id="+StringEscapeUtils.escapeSql(collateralId)+""
				+ " and ccd.rec_status='"+StringEscapeUtils.escapeSql(recStatus)+"'");
		logger.info("query::::::::::::::::::::::::::::::::::::::::" + query);
		logger.info("query::::::::::::::::::::::::::::::::::::::::" + query.toString());
		ArrayList<CollateralCapturingVO> collateralList = new ArrayList<CollateralCapturingVO>();
		try {
			ArrayList mainList = ConnectionDAO.sqlSelect(query.toString());
			int mainListSize = mainList.size();
			
			query=null;
			
			for (int i = 0; i < mainListSize; i++) {
				ArrayList subList = (ArrayList) mainList.get(i);
				if (subList != null && subList.size() > 0) {
					CollateralCapturingVO vo = new CollateralCapturingVO();
					vo.setRefrenceNo((CommonFunction.checkNull(subList.get(0))).trim());
					vo.setAppraiser((CommonFunction.checkNull(subList.get(1))).trim());
					vo.setAppraisalDate((CommonFunction.checkNull(subList.get(2))).trim());
					vo.setVerificationMode((CommonFunction.checkNull(subList.get(3))).trim());
					vo.setPersonMet((CommonFunction.checkNull(subList.get(4))).trim());
					vo.setRelation((CommonFunction.checkNull(subList.get(5))).trim());
					vo.setPhone1((CommonFunction.checkNull(subList.get(6))).trim());
					vo.setPhone2((CommonFunction.checkNull(subList.get(7))).trim());
					vo.setEmail((CommonFunction.checkNull(subList.get(8))).trim());
					
					vo.setVehicleMake((CommonFunction.checkNull(subList.get(9))).trim());
					vo.setVehicleModel((CommonFunction.checkNull(subList.get(10))).trim());
					vo.setVehicleType((CommonFunction.checkNull(subList.get(11))).trim());
					vo.setVehicleOwner((CommonFunction.checkNull(subList.get(12))).trim());
					vo.setVehicleYearOfManufact((CommonFunction.checkNull(subList.get(13))).trim());
					vo.setVehicleRegNo((CommonFunction.checkNull(subList.get(14))).trim());
					vo.setVehicleRegDate((CommonFunction.checkNull(subList.get(15))).trim());
					vo.setVehicleChesisNo((CommonFunction.checkNull(subList.get(16))).trim());
					vo.setVehicleInsurer((CommonFunction.checkNull(subList.get(17))).trim());
					vo.setVehicleInsureDate((CommonFunction.checkNull(subList.get(18))).trim());
					if ((CommonFunction.checkNull(subList.get(19))).trim().equalsIgnoreCase(""))
						vo.setVehicleMarketPrice("0");
					else {
						Number vehiclePrice = myFormatter.parse((CommonFunction.checkNull(subList.get(19))).trim());
						vo.setVehicleMarketPrice(myFormatter.format(vehiclePrice));
					}
					vo.setVehicleCondition((CommonFunction.checkNull(subList.get(20))).trim());
					collateralList.add(vo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return collateralList;
	}
	
	public boolean saveVehicleVerificationDetails(CollateralCapturingVO vo) {
		logger.info("Inside saveVehicleVerificationDetails.....DAOImpl");
		boolean status = false;
		StringBuilder query=new StringBuilder();
		query.append("select count(deal_id) from cr_collatetral_check_dtl where deal_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDealId()))+"'" +
				" and verification_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getVerificationId()))+"'" +
				" and collateral_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCollateralId()))+"");
		int count = Integer.parseInt(ConnectionDAO.singleReturn(query.toString()));
		
		query=null;
		
		if(count==0)
		{
			try {
				StringBuffer bufInsSql = new StringBuffer();
				PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
				ArrayList qryList = new ArrayList();
				bufInsSql.append("insert into cr_collatetral_check_dtl (VERIFICATION_ID,DEAL_ID,REFERENCE_NO,"
					+ "APPRAISER_NAME,APPRAISAL_DATE,VERIFICATION_MODE,PERSON_MET,RELATION,PHONE1,PHONE2,E_MAIL,"
					+ "COLLATERAL_ID,COLLATERAL_CLASS,VEHICLE_MAKE,VEHICLE_MODEL,VEHICLE_TYPE,"
					+ "VEHICLE_OWNER,VEHICLE_MANUFACTURING_YEAR,VEHICLE_REGISTRATION_NO,VEHICLE_REGISTRATION_DATE,"
					+ "VEHICLE_CHASIS_NUMBER,VEHICLE_INSURER,VEHICLE_INSURED_DATE,VEHICLE_MARKET_PRICE,"
					+ "VEHICLE_CONDITION,REC_STATUS,MAKER_ID,MAKER_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,"); //VERIFICATION_ID
				bufInsSql.append(" ?,"); //DEAL_ID
				bufInsSql.append(" ?,"); //REFERENCE_NO
				bufInsSql.append(" ?,"); //APPRAISER_NAME
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'" + dateFormat + "'),"); //APPRAISAL_DATE
				bufInsSql.append(" ?,"); //VERIFICATION_MODE
				bufInsSql.append(" ?,"); //PERSON_MET
				bufInsSql.append(" ?,"); //RELATION
				bufInsSql.append(" ?,"); //PHONE1
				bufInsSql.append(" ?,"); //PHONE2
				bufInsSql.append(" ?,"); //E_MAIL
				bufInsSql.append(" ?,"); //COLLATERAL_ID
				bufInsSql.append(" ?,"); //COLLATERAL_CLASS
				bufInsSql.append(" ?,"); //VEHICLE_MAKE
				bufInsSql.append(" ?,"); //VEHICLE_MODEL
				bufInsSql.append(" ?,"); //VEHICLE_TYPE
				bufInsSql.append(" ?,"); //VEHICLE_OWNER
				bufInsSql.append(" ?,"); //VEHICLE_MANUFACTURING_YEAR
				bufInsSql.append(" ?,"); //VEHICLE_REGISTRATION_NO
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"'),"); //VEHICLE_REGISTRATION_DATE
				bufInsSql.append(" ?,"); //VEHICLE_CHASIS_NUMBER
				bufInsSql.append(" ?,"); //VEHICLE_INSURER
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"'),"); //VEHICLE_INSURED_DATE
				bufInsSql.append(" ?,"); //VEHICLE_MARKET_PRICE
				bufInsSql.append(" ?,"); //VEHICLE_CONDITION
				bufInsSql.append(" ?,"); //REC_STATUS
				bufInsSql.append(" ?,"); //MAKER_ID
				if(dbType.equalsIgnoreCase("MSSQL"))
				{
					bufInsSql.append(" dbo.STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");}
				else
				{
					bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND))"); //MAKER_DATE
				}

				if (CommonFunction.checkNull(vo.getVerificationId()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVerificationId()).trim()));
				
				if (CommonFunction.checkNull(vo.getDealId()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getDealId()).trim()));
				
				if ((CommonFunction.checkNull(vo.getRefrenceNo())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getRefrenceNo()).trim()));
				
				if ((CommonFunction.checkNull(vo.getAppraiser())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getAppraiser()).trim()));
				
				if ((CommonFunction.checkNull(vo.getAppraisalDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getAppraisalDate()).trim()));
				
				if ((CommonFunction.checkNull(vo.getVerificationMode())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVerificationMode()).trim()));
				
				if ((CommonFunction.checkNull(vo.getPersonMet())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPersonMet()).trim()));
	
				if ((CommonFunction.checkNull(vo.getRelation())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getRelation()).trim()));
				
				if ((CommonFunction.checkNull(vo.getPhone1())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPhone1()).trim()));
				
				if ((CommonFunction.checkNull(vo.getPhone2())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPhone2()).trim()));
				
				if ((CommonFunction.checkNull(vo.getEmail())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getEmail()).trim()));
	
				if ((CommonFunction.checkNull(vo.getCollateralId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getCollateralId()).trim()));
	
				if ((CommonFunction.checkNull(vo.getCollateralClass())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getCollateralClass()).trim()));
				
				if ((CommonFunction.checkNull(vo.getVehicleMake())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVehicleMake()).trim()));
				
				if ((CommonFunction.checkNull(vo.getVehicleModel())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVehicleModel()).trim()));
				
				if ((CommonFunction.checkNull(vo.getVehicleType())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVehicleType()).trim()));
	
				if ((CommonFunction.checkNull(vo.getVehicleOwner())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVehicleOwner()).trim()));
	
				if ((CommonFunction.checkNull(vo.getVehicleYearOfManufact())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVehicleYearOfManufact()).trim()));
	
				if ((CommonFunction.checkNull(vo.getVehicleRegNo())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVehicleRegNo()).trim()));
				
				if ((CommonFunction.checkNull(vo.getVehicleRegDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVehicleRegDate()).trim()));
				
				if ((CommonFunction.checkNull(vo.getVehicleChesisNo())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVehicleChesisNo()).trim()));
				
				if ((CommonFunction.checkNull(vo.getVehicleInsurer())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVehicleInsurer()).trim()));
				
				if ((CommonFunction.checkNull(vo.getVehicleInsureDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVehicleInsureDate()).trim()));
	
				if ((CommonFunction.checkNull(vo.getVehicleMarketPrice())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getVehicleMarketPrice()).trim())).toString());
				
				if ((CommonFunction.checkNull(vo.getVehicleCondition())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVehicleCondition()).trim()));
				
				insertPrepStmtObject.addString("P");
	
				if ((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerId()).trim()));
	
				if ((CommonFunction.checkNull(vo.getMakerDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerDate()).trim()));
	
				insertPrepStmtObject.setSql(bufInsSql.toString());
	
				qryList.add(insertPrepStmtObject);
				logger.info("IN saveVehicleVerificationDetails() insert query1: "+ insertPrepStmtObject.printQuery());
	
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In saveVehicleVerificationDetails status: "+ status);
				
				bufInsSql=null;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(count>0)
		{
			StringBuilder updQuery=new StringBuilder();
			updQuery.append("update cr_collatetral_check_dtl set REFERENCE_NO=?,APPRAISER_NAME=?,APPRAISAL_DATE=");
			updQuery.append(dbo);
			updQuery.append("STR_TO_DATE(?,'"+dateFormat+"'),VERIFICATION_MODE=?,PERSON_MET=?,RELATION=?,PHONE1=?,PHONE2=?,E_MAIL=?,"
					+ "VEHICLE_MAKE=?,VEHICLE_MODEL=?,VEHICLE_TYPE=?,VEHICLE_OWNER=?,VEHICLE_MANUFACTURING_YEAR=?,VEHICLE_REGISTRATION_NO=?,VEHICLE_REGISTRATION_DATE=");
			updQuery.append(dbo);
			updQuery.append("STR_TO_DATE(?,'"+dateFormat+"'),VEHICLE_CHASIS_NUMBER=?,VEHICLE_INSURER=?,VEHICLE_INSURED_DATE=");
			updQuery.append(dbo);
			updQuery.append("STR_TO_DATE(?,'"+dateFormat+"'),VEHICLE_MARKET_PRICE=?,VEHICLE_CONDITION=?,MAKER_ID=?,MAKER_DATE=");
			if(dbType.equalsIgnoreCase("MSSQL"))
			{
				updQuery.append(" dbo.STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
			}
			else
			{
				updQuery.append(" DATE_ADD(STR_TO_DATE(?,'"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ");
			}

			updQuery.append("where deal_id=? and verification_id=? and collateral_id=?");
			PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
			ArrayList qryList = new ArrayList();
			
			try
			{
				if ((CommonFunction.checkNull(vo.getRefrenceNo())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getRefrenceNo()).trim()));
				
				if ((CommonFunction.checkNull(vo.getAppraiser())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getAppraiser()).trim()));
				
				if ((CommonFunction.checkNull(vo.getAppraisalDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getAppraisalDate()).trim()));
				
				if ((CommonFunction.checkNull(vo.getVerificationMode())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVerificationMode()).trim()));
				
				if ((CommonFunction.checkNull(vo.getPersonMet())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPersonMet()).trim()));
	
				if ((CommonFunction.checkNull(vo.getRelation())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getRelation()).trim()));
				
				if ((CommonFunction.checkNull(vo.getPhone1())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPhone1()).trim()));
				
				if ((CommonFunction.checkNull(vo.getPhone2())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPhone2()).trim()));
				
				if ((CommonFunction.checkNull(vo.getEmail())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getEmail()).trim()));
	
				if ((CommonFunction.checkNull(vo.getVehicleMake())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVehicleMake()).trim()));
				
				if ((CommonFunction.checkNull(vo.getVehicleModel())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVehicleModel()).trim()));
				
				if ((CommonFunction.checkNull(vo.getVehicleType())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVehicleType()).trim()));
	
				if ((CommonFunction.checkNull(vo.getVehicleOwner())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVehicleOwner()).trim()));
	
				if ((CommonFunction.checkNull(vo.getVehicleYearOfManufact())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVehicleYearOfManufact()).trim()));
	
				if ((CommonFunction.checkNull(vo.getVehicleRegNo())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVehicleRegNo()).trim()));
				
				if ((CommonFunction.checkNull(vo.getVehicleRegDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVehicleRegDate()).trim()));
				
				if ((CommonFunction.checkNull(vo.getVehicleChesisNo())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVehicleChesisNo()).trim()));
				
				if ((CommonFunction.checkNull(vo.getVehicleInsurer())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVehicleInsurer()).trim()));
				
				if ((CommonFunction.checkNull(vo.getVehicleInsureDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVehicleInsureDate()).trim()));
	
				if ((CommonFunction.checkNull(vo.getVehicleMarketPrice())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getVehicleMarketPrice()).trim())).toString());
				
				if ((CommonFunction.checkNull(vo.getVehicleCondition())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVehicleCondition()).trim()));
	
				if ((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerId()).trim()));
	
				if ((CommonFunction.checkNull(vo.getMakerDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerDate()).trim()));
				
				if ((CommonFunction.checkNull(vo.getDealId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getDealId()).trim()));
				
				if ((CommonFunction.checkNull(vo.getVerificationId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVerificationId()).trim()));
				
				if ((CommonFunction.checkNull(vo.getCollateralId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getCollateralId()).trim()));
	
				insertPrepStmtObject.setSql(updQuery.toString());
	
				qryList.add(insertPrepStmtObject);
				logger.info("IN saveVehicleVerificationDetails() update query1: "+ insertPrepStmtObject.printQuery());
	
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In saveVehicleVerificationDetails status: "+ status);
				
				updQuery=null;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return status;
	}
	
	public ArrayList<CollateralCapturingVO> getCollateralCapturingStockData(String dealId, String verificationId, String collateralId, String recStatus)
	{
		StringBuilder query=new StringBuilder();
		query.append("select REFERENCE_NO,APPRAISER_NAME,");
		query.append(dbo);
		query.append("date_format(APPRAISAL_DATE,'"+dateFormat+"'),VERIFICATION_MODE,PERSON_MET,RELATION,PHONE1,PHONE2,E_MAIL," +
				" STOCK_TYPE,STOCK_NATURE,STOCK_VALUE,STOCK_GODOWN_ADDRESS,STOCK_INVENTORY_CYCLE from cr_collatetral_check_dtl ccd"
				+ " where ccd.deal_id="+StringEscapeUtils.escapeSql(dealId)+" "
				+ " and ccd.verification_ID="+ StringEscapeUtils.escapeSql(verificationId) + ""
				+ " and ccd.collateral_id="+StringEscapeUtils.escapeSql(collateralId)+""
				+ " and ccd.rec_status='"+StringEscapeUtils.escapeSql(recStatus)+"'");
		logger.info("query:::::::::::::::::::::::::" + query);
		logger.info("query:::::::::::::::::::::::::" + query.toString());
		ArrayList<CollateralCapturingVO> collateralList = new ArrayList<CollateralCapturingVO>();
		try {
			ArrayList mainList = ConnectionDAO.sqlSelect(query.toString());
			int mainListSize = mainList.size();
			query=null;
			
			for (int i = 0; i < mainListSize; i++) {
				ArrayList subList = (ArrayList) mainList.get(i);
				if (subList != null && subList.size() > 0) {
					CollateralCapturingVO vo = new CollateralCapturingVO();
					vo.setRefrenceNo((CommonFunction.checkNull(subList.get(0))).trim());
					vo.setAppraiser((CommonFunction.checkNull(subList.get(1))).trim());
					vo.setAppraisalDate((CommonFunction.checkNull(subList.get(2))).trim());
					vo.setVerificationMode((CommonFunction.checkNull(subList.get(3))).trim());
					vo.setPersonMet((CommonFunction.checkNull(subList.get(4))).trim());
					vo.setRelation((CommonFunction.checkNull(subList.get(5))).trim());
					vo.setPhone1((CommonFunction.checkNull(subList.get(6))).trim());
					vo.setPhone2((CommonFunction.checkNull(subList.get(7))).trim());
					vo.setEmail((CommonFunction.checkNull(subList.get(8))).trim());
					
					vo.setStockType((CommonFunction.checkNull(subList.get(9))).trim());
					vo.setStockNature((CommonFunction.checkNull(subList.get(10))).trim());
					if ((CommonFunction.checkNull(subList.get(11))).trim().equalsIgnoreCase(""))
						vo.setStockValue("0");
					else {
						Number stockValue = myFormatter.parse((CommonFunction.checkNull(subList.get(11))).trim());
						vo.setStockValue(myFormatter.format(stockValue));
					}
					vo.setStockAddress((CommonFunction.checkNull(subList.get(12))).trim());
					vo.setStockCycle((CommonFunction.checkNull(subList.get(13))).trim());
					collateralList.add(vo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return collateralList;
	}
	
	public boolean saveStockVerificationDetails(CollateralCapturingVO vo) {
		logger.info("Inside saveStockVerificationDetails.....DAOImpl");
		boolean status = false;
		
		StringBuilder query=new StringBuilder();
		
		query.append("select count(deal_id) from cr_collatetral_check_dtl where deal_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDealId()))+"'" +
				" and verification_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getVerificationId()))+"'" +
				" and collateral_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCollateralId()))+"");
		int count = Integer.parseInt(ConnectionDAO.singleReturn(query.toString()));
		
		query=null;
		
		if(count==0)
		{
			try {
				StringBuffer bufInsSql = new StringBuffer();
				PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
				ArrayList qryList = new ArrayList();
				bufInsSql.append("insert into cr_collatetral_check_dtl (VERIFICATION_ID,DEAL_ID,REFERENCE_NO,"
					+ "APPRAISER_NAME,APPRAISAL_DATE,VERIFICATION_MODE,PERSON_MET,RELATION,PHONE1,PHONE2,E_MAIL,"
					+ "COLLATERAL_ID,COLLATERAL_CLASS,STOCK_TYPE,STOCK_NATURE,STOCK_VALUE,"
					+ "STOCK_GODOWN_ADDRESS,STOCK_INVENTORY_CYCLE,"
					+ "REC_STATUS,MAKER_ID,MAKER_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,"); //VERIFICATION_ID
				bufInsSql.append(" ?,"); //DEAL_ID
				bufInsSql.append(" ?,"); //REFERENCE_NO
				bufInsSql.append(" ?,"); //APPRAISER_NAME
				bufInsSql.append(dbo); 
				bufInsSql.append("STR_TO_DATE(?,'" + dateFormat + "'),"); //APPRAISAL_DATE
				bufInsSql.append(" ?,"); //VERIFICATION_MODE
				bufInsSql.append(" ?,"); //PERSON_MET
				bufInsSql.append(" ?,"); //RELATION
				bufInsSql.append(" ?,"); //PHONE1
				bufInsSql.append(" ?,"); //PHONE2
				bufInsSql.append(" ?,"); //E_MAIL
				bufInsSql.append(" ?,"); //COLLATERAL_ID
				bufInsSql.append(" ?,"); //COLLATERAL_CLASS
				bufInsSql.append(" ?,"); //STOCK_TYPE
				bufInsSql.append(" ?,"); //STOCK_NATURE
				bufInsSql.append(" ?,"); //STOCK_VALUE
				bufInsSql.append(" ?,"); //STOCK_GODOWN_ADDRESS
				bufInsSql.append(" ?,"); //STOCK_INVENTORY_CYCLE
				bufInsSql.append(" ?,"); //REC_STATUS
				bufInsSql.append(" ?,"); //MAKER_ID
				if(dbType.equalsIgnoreCase("MSSQL"))
				{
					bufInsSql.append(" dbo.STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
				}
				else
				{
					bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND))"); //MAKER_DATE
				}

	
				if (CommonFunction.checkNull(vo.getVerificationId()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVerificationId()).trim()));
				
				if (CommonFunction.checkNull(vo.getDealId()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getDealId()).trim()));
				
				if ((CommonFunction.checkNull(vo.getRefrenceNo())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getRefrenceNo()).trim()));
				
				if ((CommonFunction.checkNull(vo.getAppraiser())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getAppraiser()).trim()));
				
				if ((CommonFunction.checkNull(vo.getAppraisalDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getAppraisalDate()).trim()));
				
				if ((CommonFunction.checkNull(vo.getVerificationMode())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVerificationMode()).trim()));
				
				if ((CommonFunction.checkNull(vo.getPersonMet())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPersonMet()).trim()));
	
				if ((CommonFunction.checkNull(vo.getRelation())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getRelation()).trim()));
				
				if ((CommonFunction.checkNull(vo.getPhone1())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPhone1()).trim()));
				
				if ((CommonFunction.checkNull(vo.getPhone2())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPhone2()).trim()));
				
				if ((CommonFunction.checkNull(vo.getEmail())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getEmail()).trim()));
	
				if ((CommonFunction.checkNull(vo.getCollateralId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getCollateralId()).trim()));
	
				if ((CommonFunction.checkNull(vo.getCollateralClass())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getCollateralClass()).trim()));
				
				if ((CommonFunction.checkNull(vo.getStockType())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getStockType()).trim()));
				
				if ((CommonFunction.checkNull(vo.getStockNature())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getStockNature()).trim()));
	
				if ((CommonFunction.checkNull(vo.getStockValue())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getStockValue()).trim())).toString());
				
				if ((CommonFunction.checkNull(vo.getStockAddress())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getStockAddress()).trim()));
				
				if ((CommonFunction.checkNull(vo.getStockCycle())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getStockCycle()).trim()));
				
				insertPrepStmtObject.addString("P");
	
				if ((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerId()).trim()));
	
				if ((CommonFunction.checkNull(vo.getMakerDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerDate()).trim()));
	
				insertPrepStmtObject.setSql(bufInsSql.toString());
	
				qryList.add(insertPrepStmtObject);
				logger.info("IN saveStockVerificationDetails() insert query1: "+ insertPrepStmtObject.printQuery());
	
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In saveStockVerificationDetails status: "+ status);
				
				bufInsSql=null;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(count>0)
		{
			StringBuilder updQuery=new StringBuilder();
		 updQuery.append("update cr_collatetral_check_dtl set REFERENCE_NO=?,APPRAISER_NAME=?,APPRAISAL_DATE=");
		 updQuery.append(dbo); 
		 updQuery.append("STR_TO_DATE(?,'"+dateFormat+"'),VERIFICATION_MODE=?,PERSON_MET=?,RELATION=?,PHONE1=?,PHONE2=?,E_MAIL=?,"
					+ "STOCK_TYPE=?,STOCK_NATURE=?,STOCK_VALUE=?,STOCK_GODOWN_ADDRESS=?,STOCK_INVENTORY_CYCLE=?,MAKER_ID=?,MAKER_DATE=");
		 if(dbType.equalsIgnoreCase("MSSQL"))
		 {
			 updQuery.append(" dbo.STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
		 	}
		 else
		 {
			 updQuery.append(" DATE_ADD(STR_TO_DATE(?,'"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ");
		 }
		 
		 updQuery.append("where deal_id=? and verification_id=? and collateral_id=?");
		 logger.info("updQuery:::::::::::::::::::::::::::::::::::::::"+updQuery.toString());
			PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
			ArrayList qryList = new ArrayList();
			
			try
			{
				if ((CommonFunction.checkNull(vo.getRefrenceNo())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getRefrenceNo()).trim()));
				
				if ((CommonFunction.checkNull(vo.getAppraiser())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getAppraiser()).trim()));
				
				if ((CommonFunction.checkNull(vo.getAppraisalDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getAppraisalDate()).trim()));
				
				if ((CommonFunction.checkNull(vo.getVerificationMode())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVerificationMode()).trim()));
				
				if ((CommonFunction.checkNull(vo.getPersonMet())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPersonMet()).trim()));
	
				if ((CommonFunction.checkNull(vo.getRelation())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getRelation()).trim()));
				
				if ((CommonFunction.checkNull(vo.getPhone1())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPhone1()).trim()));
				
				if ((CommonFunction.checkNull(vo.getPhone2())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPhone2()).trim()));
				
				if ((CommonFunction.checkNull(vo.getEmail())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getEmail()).trim()));
	
				if ((CommonFunction.checkNull(vo.getStockType())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getStockType()).trim()));
				
				if ((CommonFunction.checkNull(vo.getStockNature())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getStockNature()).trim()));
	
				if ((CommonFunction.checkNull(vo.getStockValue())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getStockValue()).trim())).toString());
				
				if ((CommonFunction.checkNull(vo.getStockAddress())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getStockAddress()).trim()));
				
				if ((CommonFunction.checkNull(vo.getStockCycle())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getStockCycle()).trim()));
	
				if ((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerId()).trim()));
	
				if ((CommonFunction.checkNull(vo.getMakerDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerDate()).trim()));
				
				if ((CommonFunction.checkNull(vo.getDealId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getDealId()).trim()));
				
				if ((CommonFunction.checkNull(vo.getVerificationId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVerificationId()).trim()));
				
				if ((CommonFunction.checkNull(vo.getCollateralId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getCollateralId()).trim()));
	
				insertPrepStmtObject.setSql(updQuery.toString());
	
				qryList.add(insertPrepStmtObject);
				logger.info("IN saveStockVerificationDetails() update query1: "+ insertPrepStmtObject.printQuery());
	
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In saveStockVerificationDetails status: "+ status);
			} catch (Exception e) {
				e.printStackTrace();
			}
			updQuery=null;
		}
		return status;
	}
	
	public ArrayList<CollateralCapturingVO> getCollateralCapturingOtherData(String dealId, String verificationId, String collateralId, String recStatus)
	{
		StringBuilder query=new StringBuilder();
		 query.append("select REFERENCE_NO,APPRAISER_NAME,");
		 query.append(dbo);
		 query.append("date_format(APPRAISAL_DATE,'"+dateFormat+"'),VERIFICATION_MODE,PERSON_MET,RELATION,PHONE1,PHONE2,E_MAIL," +
				" OTHER_REMARKES from cr_collatetral_check_dtl ccd"
				+ " where ccd.deal_id="+StringEscapeUtils.escapeSql(dealId)+" "
				+ " and ccd.verification_ID="+ StringEscapeUtils.escapeSql(verificationId) + ""
				+ " and ccd.collateral_id="+StringEscapeUtils.escapeSql(collateralId)+""
				+ " and ccd.rec_status='"+StringEscapeUtils.escapeSql(recStatus)+"'");
		logger.info("query:::::::::::::::::::::::;" + query);
		logger.info("query:::::::::::::::::::::::;" + query.toString());
		ArrayList<CollateralCapturingVO> collateralList = new ArrayList<CollateralCapturingVO>();
		try {
			ArrayList mainList = ConnectionDAO.sqlSelect(query.toString());
			int mainListSize = mainList.size();
			
			query=null;
			
			for (int i = 0; i < mainListSize; i++) {
				ArrayList subList = (ArrayList) mainList.get(i);
				if (subList != null && subList.size() > 0) {
					CollateralCapturingVO vo = new CollateralCapturingVO();
					vo.setRefrenceNo((CommonFunction.checkNull(subList.get(0))).trim());
					vo.setAppraiser((CommonFunction.checkNull(subList.get(1))).trim());
					vo.setAppraisalDate((CommonFunction.checkNull(subList.get(2))).trim());
					vo.setVerificationMode((CommonFunction.checkNull(subList.get(3))).trim());
					vo.setPersonMet((CommonFunction.checkNull(subList.get(4))).trim());
					vo.setRelation((CommonFunction.checkNull(subList.get(5))).trim());
					vo.setPhone1((CommonFunction.checkNull(subList.get(6))).trim());
					vo.setPhone2((CommonFunction.checkNull(subList.get(7))).trim());
					vo.setEmail((CommonFunction.checkNull(subList.get(8))).trim());
					
					vo.setOtherCollateralDesc((CommonFunction.checkNull(subList.get(9))).trim());
					collateralList.add(vo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return collateralList;
	}
	
	public boolean saveOtherVerificationDetails(CollateralCapturingVO vo) {
		logger.info("Inside saveStockVerificationDetails.....DAOImpl");
		boolean status = false;
		StringBuilder query=new StringBuilder();
		
		 query.append("select count(deal_id) from cr_collatetral_check_dtl where deal_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getDealId()))+"'" +
				" and verification_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getVerificationId()))+"'" +
				" and collateral_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCollateralId()))+"");
		int count = Integer.parseInt(ConnectionDAO.singleReturn(query.toString()));
		
		query=null;
		
		if(count==0)
		{
			try {
				StringBuffer bufInsSql = new StringBuffer();
				PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
				ArrayList qryList = new ArrayList();
				bufInsSql.append("insert into cr_collatetral_check_dtl (VERIFICATION_ID,DEAL_ID,REFERENCE_NO,"
					+ "APPRAISER_NAME,APPRAISAL_DATE,VERIFICATION_MODE,PERSON_MET,RELATION,PHONE1,PHONE2,E_MAIL,"
					+ "COLLATERAL_ID,COLLATERAL_CLASS,OTHER_REMARKES,"
					+ "REC_STATUS,MAKER_ID,MAKER_DATE)");
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,"); //VERIFICATION_ID
				bufInsSql.append(" ?,"); //DEAL_ID
				bufInsSql.append(" ?,"); //REFERENCE_NO
				bufInsSql.append(" ?,"); //APPRAISER_NAME
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE(?,'" + dateFormat + "'),"); //APPRAISAL_DATE
				bufInsSql.append(" ?,"); //VERIFICATION_MODE
				bufInsSql.append(" ?,"); //PERSON_MET
				bufInsSql.append(" ?,"); //RELATION
				bufInsSql.append(" ?,"); //PHONE1
				bufInsSql.append(" ?,"); //PHONE2
				bufInsSql.append(" ?,"); //E_MAIL
				bufInsSql.append(" ?,"); //COLLATERAL_ID
				bufInsSql.append(" ?,"); //COLLATERAL_CLASS
				bufInsSql.append(" ?,"); //OTHER_REMARKES
				bufInsSql.append(" ?,"); //REC_STATUS
				bufInsSql.append(" ?,"); //MAKER_ID
				if(dbType.equalsIgnoreCase("MSSQL"))
				{
					bufInsSql.append(" dbo.STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9))");
					}
				else
				{
					bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND))"); //MAKER_DATE
				}
	
				if (CommonFunction.checkNull(vo.getVerificationId()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVerificationId()).trim()));
				
				if (CommonFunction.checkNull(vo.getDealId()).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getDealId()).trim()));
				
				if ((CommonFunction.checkNull(vo.getRefrenceNo())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getRefrenceNo()).trim()));
				
				if ((CommonFunction.checkNull(vo.getAppraiser())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getAppraiser()).trim()));
				
				if ((CommonFunction.checkNull(vo.getAppraisalDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getAppraisalDate()).trim()));
				
				if ((CommonFunction.checkNull(vo.getVerificationMode())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVerificationMode()).trim()));
				
				if ((CommonFunction.checkNull(vo.getPersonMet())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPersonMet()).trim()));
	
				if ((CommonFunction.checkNull(vo.getRelation())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getRelation()).trim()));
				
				if ((CommonFunction.checkNull(vo.getPhone1())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPhone1()).trim()));
				
				if ((CommonFunction.checkNull(vo.getPhone2())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPhone2()).trim()));
				
				if ((CommonFunction.checkNull(vo.getEmail())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getEmail()).trim()));
	
				if ((CommonFunction.checkNull(vo.getCollateralId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getCollateralId()).trim()));
	
				if ((CommonFunction.checkNull(vo.getCollateralClass())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getCollateralClass()).trim()));
				
				if ((CommonFunction.checkNull(vo.getOtherCollateralDesc())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getOtherCollateralDesc()).trim()));
				
				insertPrepStmtObject.addString("P");
	
				if ((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerId()).trim()));
	
				if ((CommonFunction.checkNull(vo.getMakerDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerDate()).trim()));
	
				insertPrepStmtObject.setSql(bufInsSql.toString());
	
				qryList.add(insertPrepStmtObject);
				logger.info("IN saveStockVerificationDetails() insert query1: "+ insertPrepStmtObject.printQuery());
	
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In saveStockVerificationDetails status: "+ status);
				
				bufInsSql=null;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(count>0)
		{
			StringBuilder updQuery=new StringBuilder();
			updQuery.append("update cr_collatetral_check_dtl set REFERENCE_NO=?,APPRAISER_NAME=?,APPRAISAL_DATE=");
			updQuery.append(dbo);
			updQuery.append("STR_TO_DATE(?,'"+dateFormat+"'),VERIFICATION_MODE=?,PERSON_MET=?,RELATION=?,PHONE1=?,PHONE2=?," 
					+ "E_MAIL=?,OTHER_REMARKES=?,MAKER_ID=?,MAKER_DATE=");
			if(dbType.equalsIgnoreCase("MSSQL"))
			{
				updQuery.append(" dbo.STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
				}
			else
			{
				updQuery.append(" DATE_ADD(STR_TO_DATE(?,'"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ");
			}
			updQuery.append("where deal_id=? and verification_id=? and collateral_id=?");
			logger.info("updQuery::::::::::::::::::::::::::::::"+updQuery.toString());
			PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
			ArrayList qryList = new ArrayList();
			
			try
			{
				if ((CommonFunction.checkNull(vo.getRefrenceNo())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getRefrenceNo()).trim()));
				
				if ((CommonFunction.checkNull(vo.getAppraiser())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getAppraiser()).trim()));
				
				if ((CommonFunction.checkNull(vo.getAppraisalDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getAppraisalDate()).trim()));
				
				if ((CommonFunction.checkNull(vo.getVerificationMode())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVerificationMode()).trim()));
				
				if ((CommonFunction.checkNull(vo.getPersonMet())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPersonMet()).trim()));
	
				if ((CommonFunction.checkNull(vo.getRelation())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getRelation()).trim()));
				
				if ((CommonFunction.checkNull(vo.getPhone1())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPhone1()).trim()));
				
				if ((CommonFunction.checkNull(vo.getPhone2())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getPhone2()).trim()));
				
				if ((CommonFunction.checkNull(vo.getEmail())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getEmail()).trim()));
	
				if ((CommonFunction.checkNull(vo.getOtherCollateralDesc())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getOtherCollateralDesc()).trim()));
	
				if ((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerId()).trim()));
	
				if ((CommonFunction.checkNull(vo.getMakerDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerDate()).trim()));
				
				if ((CommonFunction.checkNull(vo.getDealId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getDealId()).trim()));
				
				if ((CommonFunction.checkNull(vo.getVerificationId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getVerificationId()).trim()));
				
				if ((CommonFunction.checkNull(vo.getCollateralId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getCollateralId()).trim()));
	
				insertPrepStmtObject.setSql(updQuery.toString());
	
				qryList.add(insertPrepStmtObject);
				logger.info("IN saveStockVerificationDetails() update query1: "+ insertPrepStmtObject.printQuery());
	
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In saveStockVerificationDetails status: "+ status);
			} catch (Exception e) {
				e.printStackTrace();
			}
			updQuery=null;
			
		}
		return status;
	}

	public ArrayList<CollateralCapturingVO> getCollateralsCapturedData(String dealId, String userId) 
	{
	
		StringBuilder query=new StringBuilder();
		 query.append("SELECT ccd.VERIFICATION_ID FROM cr_collatetral_check_dtl ccd,cr_deal_verification_dtl dv " +
				"left join com_agency_m ag on dv.external_appraiser=ag.AGENCY_CODE left join com_agency_user_mapping agm on ag.AGENCY_CODE=agm.AGENCY_CODE " +
				"WHERE ccd.REC_STATUS='P'  and ccd.DEAL_ID=dv.DEAL_ID and ccd.VERIFICATION_ID=dv.VERIFICATION_ID " +
				"and (dv.internal_appraiser = '"+StringEscapeUtils.escapeSql(userId)+"' or agm.USER_ID = '"+StringEscapeUtils.escapeSql(userId)+"') AND ccd.DEAL_ID='"+StringEscapeUtils.escapeSql(dealId)+"' ");

		ArrayList<CollateralCapturingVO> collateralsCapturedList = new ArrayList<CollateralCapturingVO>();
		try {
			ArrayList mainList = ConnectionDAO.sqlSelect(query.toString());
			int mainListSize = mainList.size();
			query=null;
			for (int i = 0; i < mainListSize; i++) {
				ArrayList subList = (ArrayList) mainList.get(i);
				if (subList != null && subList.size() > 0) {
					CollateralCapturingVO vo = new CollateralCapturingVO();
					vo.setCollateralId((CommonFunction.checkNull(subList.get(0))).trim());
					collateralsCapturedList.add(vo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return collateralsCapturedList;
	}

	public boolean forwardCollateralCapturingData(String dealId,String verificationId) 
	{
		StringBuilder updQuery=new StringBuilder();
		 updQuery.append("update cr_collatetral_check_dtl ccd, cr_deal_verification_dtl dv " +
				"set ccd.rec_status=?, dv.rec_status=?" +
				" where dv.deal_id=ccd.deal_id and dv.VERIFICATION_ID=ccd.VERIFICATION_ID and dv.deal_id=?  and dv.VERIFICATION_ID=? and dv.VERIFICATION_TYPE='C' ");
		
	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	ArrayList qryList = new ArrayList();
	boolean status=false;
	try
	{
		insertPrepStmtObject.addString("F");
		insertPrepStmtObject.addString("R");
		
		if ((CommonFunction.checkNull(dealId)).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((CommonFunction.checkNull(dealId).trim()));
		
		if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(verificationId)).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(verificationId)));
		
		insertPrepStmtObject.setSql(updQuery.toString());

		qryList.add(insertPrepStmtObject);
		logger.info("IN forwardCollateralCapturingData() update query1: "+ insertPrepStmtObject.printQuery());

		status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		logger.info("In forwardCollateralCapturingData status: "+ status);
	} catch (Exception e) {
		e.printStackTrace();
	}
updQuery=null;
	return status;
	}
	
	public ArrayList<CommonDealVo> searchCollateralVerifiationCompletionData(
			CommonDealVo vo) {
		ArrayList<CommonDealVo> list = new ArrayList<CommonDealVo>();
		String userId=null;
		try {
			logger.info("Inside searchCollateralVerifiationCompletionData.....");
			ArrayList header = null;
			CommonDealVo fetchVo = (CommonDealVo) vo;
			 userId=fetchVo.getUserId();
			boolean appendSQL = false;
			int count=0;
			int startRecordIndex=0;
			int endRecordIndex = 5;

	 			
			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();

			bufInsSql.append(" select distinct d.DEAL_ID,DEAL_NO,");
			bufInsSql.append(dbo);
			bufInsSql.append("DATE_FORMAT(DEAL_DATE,'"+dateFormat+"'),"
				+" deal.CUSTOMER_NAME,p.PRODUCT_DESC,s.SCHEME_DESC from " 
				+" (SELECT COUNT(*) Collater_Count,TD.DEAL_ID FROM cr_collatetral_check_dtl TD WHERE TD.REC_STATUS='F' GROUP BY TD.DEAL_ID) AS F, " 
				+" (SELECT COUNT(*) D_COLL_COUNT,X.DEAL_ID FROM cr_deal_verification_dtl X WHERE X.VERIFICATION_TYPE='C' and X.VERIFICATION_ACTION='I' GROUP BY X.DEAL_ID) AS G,cr_deal_dtl d, "
				+" cr_deal_loan_dtl n, cr_collatetral_check_dtl ccd, cr_product_m p, cr_deal_customer_m deal,cr_scheme_m s"
				+" WHERE d.DEAL_ID=n.DEAL_ID and d.DEAL_ID=ccd.DEAL_ID "
				+" and n.DEAL_PRODUCT=p.PRODUCT_ID and deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID "
				+" and n.DEAL_SCHEME=s.SCHEME_ID"
				+" AND F.Collater_Count=G.D_COLL_COUNT AND F.DEAL_ID = G.DEAL_ID AND d.DEAL_ID=F.DEAL_ID AND d.DEAL_ID=G.DEAL_ID  and d.DEAL_ID not in(select  distinct DEAL_ID from cr_collatetral_check_dtl where rec_status='F' and MAKER_ID='"+userId+"')");
			
			bufInsSqlTempCount.append(" select count(distinct d.DEAL_ID) from "
					+" (SELECT COUNT(*) Collater_Count,TD.DEAL_ID FROM cr_collatetral_check_dtl TD WHERE TD.REC_STATUS='F' GROUP BY TD.DEAL_ID) AS F, " 
					+" (SELECT COUNT(*) D_COLL_COUNT,X.DEAL_ID FROM cr_deal_verification_dtl X WHERE X.VERIFICATION_TYPE='C' and X.VERIFICATION_ACTION='I' GROUP BY X.DEAL_ID) AS G,cr_deal_dtl d, "
					+" cr_deal_loan_dtl n, cr_collatetral_check_dtl ccd, cr_product_m p, cr_deal_customer_m deal,cr_scheme_m s"
					+" WHERE d.DEAL_ID=n.DEAL_ID and d.DEAL_ID=ccd.DEAL_ID "
					+" and n.DEAL_PRODUCT=p.PRODUCT_ID and deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID "
					+" and n.DEAL_SCHEME=s.SCHEME_ID "
					+" AND F.Collater_Count=G.D_COLL_COUNT AND F.DEAL_ID = G.DEAL_ID AND d.DEAL_ID=F.DEAL_ID AND d.DEAL_ID=G.DEAL_ID  and d.DEAL_ID not in(select  distinct DEAL_ID from cr_collatetral_check_dtl where rec_status='F' and MAKER_ID='"+userId+"')");
			
			if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(
					vo.getLbxDealNo()).trim()).equalsIgnoreCase("")))
					&& (!(StringEscapeUtils.escapeSql(
							CommonFunction.checkNull(vo.getInitiationDate()))
							.trim().equalsIgnoreCase("")))
					&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(
							vo.getCustomername()).trim()).equalsIgnoreCase("")))
					&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(
							vo.getLbxProductID()).trim()).equalsIgnoreCase("")))
					&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(
							vo.getLbxscheme()).trim()).equalsIgnoreCase("")))) {
				bufInsSql.append("and d.DEAL_BRANCH='"+ StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+ "' " +
						" and d.DEAL_ID='"+ StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+ "' AND");
				bufInsSql.append(dbo);
				bufInsSql.append("date(d.DEAL_DATE) =");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE('"+ StringEscapeUtils.escapeSql(vo.getInitiationDate()).trim()+ "','"+ dateFormat+ "') AND" +
						" deal.CUSTOMER_NAME like'%"+ StringEscapeUtils.escapeSql(vo.getCustomername()).trim()+ "%' AND" +
						" n.DEAL_PRODUCT='"+ StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim() + "'AND" +
						" n.DEAL_SCHEME='"+ StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+ "' AND " +
						" d.Rec_status='F' and ccd.rec_status='F'");
				bufInsSqlTempCount.append("and d.DEAL_BRANCH='"+ StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+ "' " +
						" and d.DEAL_ID='"+ StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()+ "' AND");
				bufInsSql.append(dbo);
				bufInsSql.append("date(d.DEAL_DATE) =");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE('"+ StringEscapeUtils.escapeSql(vo.getInitiationDate()).trim()+ "','"+ dateFormat+ "') AND" +
						" deal.CUSTOMER_NAME like'%"+ StringEscapeUtils.escapeSql(vo.getCustomername()).trim()+ "%' AND" +
						" n.DEAL_PRODUCT='"+ StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim() + "'AND" +
						" n.DEAL_SCHEME='"+ StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()+ "' AND " +
						" d.Rec_status='F' and ccd.rec_status='F'");
			}

			if (((vo.getLbxDealNo().equalsIgnoreCase("")))
					|| ((vo.getInitiationDate().equalsIgnoreCase("")))
					|| ((vo.getCustomername().equalsIgnoreCase("")))
					|| ((vo.getLbxProductID().equalsIgnoreCase("")))
					|| ((vo.getLbxscheme().equalsIgnoreCase("")))) {
				appendSQL = true;
			}

			if (appendSQL) {
				logger.info("In Where Clause");
				bufInsSql.append(" and d.DEAL_BRANCH='"
						+ StringEscapeUtils.escapeSql(vo.getBranchId()).trim()
						+ "' and d.Rec_status='F' and ccd.rec_status='F' ");
				bufInsSqlTempCount.append(" and d.DEAL_BRANCH='"
						+ StringEscapeUtils.escapeSql(vo.getBranchId()).trim()
						+ "' and d.Rec_status='F' and ccd.rec_status='F' ");
			}

			if ((!(StringEscapeUtils.escapeSql(
					CommonFunction.checkNull(vo.getLbxDealNo())).trim()
					.equalsIgnoreCase("")))) {
				bufInsSql.append("AND d.DEAL_ID='"
						+ StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()
						+ "' ");
				bufInsSqlTempCount.append("AND d.DEAL_ID='"
						+ StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()
						+ "' ");
				appendSQL = true;

			}

			if ((!(StringEscapeUtils.escapeSql(
					CommonFunction.checkNull(vo.getInitiationDate())).trim().equalsIgnoreCase("")))) {
				
				bufInsSql.append("AND ");
				bufInsSql.append(dbo);
				bufInsSql.append("date(d.DEAL_DATE) =");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE('"	+ StringEscapeUtils.escapeSql(vo.getInitiationDate()).trim() + "','" + dateFormat + "') ");
				bufInsSqlTempCount.append("AND ");
				bufInsSql.append(dbo);
				bufInsSql.append("date(d.DEAL_DATE) =");
				bufInsSql.append(dbo);
				bufInsSql.append("STR_TO_DATE('"+ StringEscapeUtils.escapeSql(vo.getInitiationDate()).trim() + "','" + dateFormat + "') ");
				appendSQL = true;
			}

			if ((!(StringEscapeUtils.escapeSql(
					CommonFunction.checkNull(vo.getCustomername())).trim()
					.equalsIgnoreCase("")))) {
				bufInsSql.append("AND deal.CUSTOMER_NAME like'%"
						+ StringEscapeUtils.escapeSql(vo.getCustomername())
								.trim() + "%' ");
				bufInsSqlTempCount.append("AND deal.CUSTOMER_NAME like'%"
						+ StringEscapeUtils.escapeSql(vo.getCustomername())
								.trim() + "%' ");
				appendSQL = true;
			}
			if ((!(StringEscapeUtils.escapeSql(
					CommonFunction.checkNull(vo.getLbxProductID())).trim()
					.equalsIgnoreCase("")))) {
				bufInsSql.append("AND n.DEAL_PRODUCT='"
						+ StringEscapeUtils.escapeSql(vo.getLbxProductID())
								.trim() + "' ");
				bufInsSqlTempCount.append("AND n.DEAL_PRODUCT='"
						+ StringEscapeUtils.escapeSql(vo.getLbxProductID())
								.trim() + "' ");
				appendSQL = true;
			}
			if ((!(StringEscapeUtils.escapeSql(
					CommonFunction.checkNull(vo.getLbxscheme())).trim()
					.equalsIgnoreCase("")))) {
				bufInsSql.append("AND n.DEAL_SCHEME='"
						+ StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()
						+ "' ");
				bufInsSqlTempCount.append("AND n.DEAL_SCHEME='"
						+ StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()
						+ "' ");
				appendSQL = true;
			}

			logger.info("query :::::::::::::::" + bufInsSql);
			logger.info("query :::::::::::::::" + bufInsSql.toString());
			logger.info("count query::::::::::::::::::::;"+bufInsSqlTempCount);
			logger.info("count query::::::::::::::::::::;"+bufInsSqlTempCount.toString());
			if((CommonFunction.checkNull(vo.getLbxDealNo()).trim()==null && CommonFunction.checkNull(vo.getInitiationDate()).trim()==null && CommonFunction.checkNull(vo.getCustomername()).trim()==null && CommonFunction.checkNull(vo.getLbxProductID()).trim()==null && CommonFunction.checkNull(vo.getLbxscheme()).trim()==null) || (CommonFunction.checkNull(vo.getLbxDealNo()).trim().equalsIgnoreCase("") && CommonFunction.checkNull(vo.getInitiationDate()).trim().equalsIgnoreCase("") && CommonFunction.checkNull(vo.getCustomername()).trim().equalsIgnoreCase("") && CommonFunction.checkNull(vo.getLbxProductID()).trim().equalsIgnoreCase("") && CommonFunction.checkNull(vo.getLbxscheme()).trim().equalsIgnoreCase("")) || vo.getCurrentPageLink()>1)
			{
			
				logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
				if(vo.getCurrentPageLink()>1)
				{
					startRecordIndex = (vo.getCurrentPageLink()-1)*5;
					endRecordIndex = 5;
					logger.info("startRecordIndex .................... "+startRecordIndex);
					logger.info("endRecordIndex .................... "+endRecordIndex);
				}
				
				if(dbType.equalsIgnoreCase("MSSQL"))
				{
					bufInsSql.append(" ORDER BY GENERIC_KEY OFFSET ");
					bufInsSql.append(startRecordIndex);
					bufInsSql.append(" ROWS FETCH next ");
					bufInsSql.append(endRecordIndex);
					bufInsSql.append(" ROWS ONLY ");
					logger.info("Search searchGenericMasterDao query for SQL SERVER : " + bufInsSql.toString());
				}
				else
				{
					bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
				}

			}
			header = ConnectionDAO.sqlSelect(bufInsSql.toString());
			count = Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
			
			bufInsSql=null;
			bufInsSqlTempCount=null;


			for (int i = 0; i < header.size(); i++) {
				logger.info("header: " + header.size());
				ArrayList header1 = (ArrayList) header.get(i);
				if (header1 != null && header1.size() > 0) {

					fetchVo = new CommonDealVo();
					fetchVo.setLbxDealNo((CommonFunction.checkNull(header1.get(0))).trim());
					fetchVo.setDealNo("<a href=collateralVerificationCompletionSearch.do?method=openCollateralCompletion&dealId="
	      					+ (CommonFunction.checkNull(header1.get(0)).trim())+">"
	      					+ (CommonFunction.checkNull(header1.get(1)).trim()) + "</a>");
					fetchVo.setInitiationDate((CommonFunction.checkNull(header1.get(2))).trim());
					fetchVo.setCustomername((CommonFunction.checkNull(header1.get(3))).trim());
					fetchVo.setProduct((CommonFunction.checkNull(header1.get(4))).trim());
					fetchVo.setScheme((CommonFunction.checkNull(header1.get(5))).trim());
					fetchVo.setTotalRecordSize(count);
					list.add(fetchVo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			userId=null;
		}

		LoggerMsg.info("Detail List when searchList is : " + list.size());

		return list;
	}
	
	public ArrayList getTradeHeader(String dealId) {
		ArrayList list=new ArrayList();
			try
			{
				StringBuilder query=new StringBuilder();
			query.append(" select ");
			if(dbType.equalsIgnoreCase("MSSQL"))
				{
				 query.append("TOP 1 ");
				}
			query.append("d.deal_id, deal_no,deal.CUSTOMER_NAME,");
			query.append(dbo);
			query.append("DATE_FORMAT(deal_date,'"+dateFormat+"'),p.PRODUCT_DESC,s.SCHEME_DESC,l.DEAL_PRODUCT_CATEGORY from cr_deal_dtl d "+
							    " left join cr_deal_loan_dtl l on d.DEAL_ID=l.DEAL_ID" +
							    " left join cr_product_m p on l.DEAL_PRODUCT=p.PRODUCT_ID"+
								" left join cr_scheme_m s on l.DEAL_SCHEME=s.SCHEME_ID"+
								" left join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID"+
								" where d.DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+" ");
			if(!dbType.equalsIgnoreCase("MSSQL") && dbo.equalsIgnoreCase(""))
			{
			 query.append(" limit 1");
			}
				
				logger.info("getDealHeader Query: "+query);
				
				HeaderInfoVo vo= null;
				ArrayList header = ConnectionDAO.sqlSelect(query.toString());
				
				query=null;
				
				for(int i=0;i<header.size();i++){
					logger.info("header"+header.size());
					ArrayList header1=(ArrayList)header.get(i);
					if(header1!=null && header1.size()>0)
					{
						vo = new HeaderInfoVo();
						vo.setDealId((CommonFunction.checkNull(header1.get(0))).trim());
						vo.setDealNo((CommonFunction.checkNull(header1.get(1))).trim());
						vo.setDealCustomerName((CommonFunction.checkNull(header1.get(2))).trim());
						vo.setDealDate((CommonFunction.checkNull(header1.get(3))).trim());
						vo.setDealProduct((CommonFunction.checkNull(header1.get(4))).trim());
						vo.setDealScheme((CommonFunction.checkNull(header1.get(5))).trim());
						vo.setDealProductCat((CommonFunction.checkNull(header1.get(6))).trim());
						list.add(vo);
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

			return list;
		}
	
	public boolean saveCollateralCompletionDecision(CollateralCapturingVO vo)
	{
		boolean status=false;
		logger.info("In saveCollateralCompletionDecision.....................................Dao Impl");
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		PrepStmtObject insertPrepStmtObject1 = new PrepStmtObject();
		PrepStmtObject insertPrepStmtObject2 = new PrepStmtObject();
		try{
			
		logger.info("In insert Approval Data part");
		
		if(!CommonFunction.checkNull(vo.getDecision()).equalsIgnoreCase("S"))
		 {
			StringBuffer bufInsSql =	new StringBuffer();
			bufInsSql.append("update cr_collatetral_check_dtl ccd, cr_deal_verification_dtl dv set dv.VERIFICATION_STATUS=?, dv.VERIFICATION_REMARKS=?," +
					" ccd.rec_status=?, dv.rec_status=? where ccd.deal_id=dv.deal_id and " +
					" ccd.deal_id=? and ccd.rec_status='F' and dv.rec_status='R' and dv.VERIFICATION_TYPE='C' ");
	
			if(CommonFunction.checkNull(vo.getDecision()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getDecision()).trim()));
			
			if(CommonFunction.checkNull(vo.getRemarks()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getRemarks()).trim()));
			
			insertPrepStmtObject.addString("A");
			insertPrepStmtObject.addString("C");
			
			if(CommonFunction.checkNull(vo.getDealId()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getDealId()).trim()));
			
			
			insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("IN saveCollateralCompletionDecision() insert query1 ### "+insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("In saveCollateralCompletionDecision......................"+status);
			
		 }
		else
		{
			StringBuffer bufInsSql =	new StringBuffer();
			bufInsSql.append("update cr_collatetral_check_dtl ccd, cr_deal_verification_dtl dv set dv.VERIFICATION_STATUS=?, dv.VERIFICATION_REMARKS=?," +
					" ccd.rec_status=?, dv.rec_status=? where ccd.deal_id=dv.deal_id and " +
					" ccd.deal_id=? and ccd.rec_status='F' and dv.rec_status='R' and dv.VERIFICATION_TYPE='C' ");
	
			if(CommonFunction.checkNull(vo.getDecision()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getDecision()).trim()));
			
			if(CommonFunction.checkNull(vo.getRemarks()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getRemarks()).trim()));
			
			insertPrepStmtObject.addString("P");
			insertPrepStmtObject.addString("F");
			
			if(CommonFunction.checkNull(vo.getDealId()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getDealId()).trim()));
			
			
			insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("IN saveCollateralCompletionDecision() insert query0 ### "+insertPrepStmtObject.printQuery());
			
			// UPDATE DEALMOVEMENT TABLE
			 StringBuffer bufInsSql1 = new StringBuffer();
			bufInsSql1.append("UPDATE CR_DEAL_MOVEMENT_DTL SET REC_STATUS = 'X' WHERE REC_STATUS = 'A' AND DEAL_STAGE_ID='CVC' AND DEAL_ID=?");
													
				if((CommonFunction.checkNull(vo.getDealId())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject1.addNull();
				else
					insertPrepStmtObject1.addString((vo.getDealId()).trim());

				
				insertPrepStmtObject1.setSql(bufInsSql1.toString());
				
				logger.info("IN saveCollateralCompletionDecision() UPDATE query1 ### "+ insertPrepStmtObject1.printQuery());
				
				
				
				// INSERT DEALMOVEMENT TABLE
				StringBuffer bufInsSql2 = new StringBuffer();
				bufInsSql2.append("INSERT INTO CR_DEAL_MOVEMENT_DTL(DEAL_ID,DEAL_STAGE_ID,DEAL_ACTION,DEAL_RECEIVED,DEAL_RECEIVED_USER,REC_STATUS)VALUES(?, 'CVC', 'I', ");
				if(dbType.equalsIgnoreCase("MSSQL"))
				{
					bufInsSql.append(" dbo.STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
					}
				else
				{
					bufInsSql.append(" DATE_ADD(STR_TO_DATE(?,'"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ,");
				}
				
				bufInsSql2.append(" ?,'A')");
														
					if((CommonFunction.checkNull(vo.getDealId())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject2.addNull();
					else
						insertPrepStmtObject2.addString((vo.getDealId()).trim());
					
					
					if((CommonFunction.checkNull(vo.getMakerDate())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject2.addNull();
					else
						insertPrepStmtObject2.addString((vo.getMakerDate()).trim());
					
					if((CommonFunction.checkNull(vo.getMakerId())).trim().equalsIgnoreCase(""))
						insertPrepStmtObject2.addNull();
					else
						insertPrepStmtObject2.addString((vo.getMakerId()).trim());

					
					
					
          			insertPrepStmtObject2.setSql(bufInsSql2.toString());
					
					logger.info("IN saveCollateralCompletionDecision() INSERT query2****"+ insertPrepStmtObject2.printQuery());
					
			
				qryList.add(insertPrepStmtObject);
				qryList.add(insertPrepStmtObject1);
				qryList.add(insertPrepStmtObject2);
			    status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			    
			logger.info("In saveCollateralCompletionDecision......................"+status);
		}
		
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return status;
	}

	public ArrayList<CommonDealVo> initialCollateralVerifiationCapturingData(CommonDealVo vo) {
		ArrayList<CommonDealVo> list = new ArrayList<CommonDealVo>();
		String userName=null;
		try {
			logger.info("Inside initialCollateralVerifiationCapturingData.....");
			ArrayList header = null;

			logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+vo.getUserId()+"Branch Id: "+vo.getBranchId());
			 			String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+vo.getUserId()+"'";
			 			 userName=ConnectionDAO.singleReturn(userNameQ);
			 			logger.info("userNameQ: "+userNameQ+" userName: "+userName);
			 			
			 			userNameQ=null;
			 			
			CommonDealVo fetchVo = (CommonDealVo) vo;
			int count=0;
			int startRecordIndex=0;
			int endRecordIndex = 5;
			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
		
			bufInsSql.append("select distinct d.DEAL_ID,DEAL_NO,");
			bufInsSql.append(dbo);
			bufInsSql.append("DATE_FORMAT(DEAL_DATE,'%d-%m-%Y'),deal.CUSTOMER_NAME,p.PRODUCT_DESC," +
					" s.SCHEME_DESC from cr_deal_dtl d,  cr_deal_loan_dtl n, cr_product_m p, cr_deal_customer_m deal," +
					" cr_scheme_m s, cr_deal_verification_dtl dv left join com_agency_m ag on dv.external_appraiser=ag.AGENCY_CODE " +  
					" left join com_agency_user_mapping agm on ag.AGENCY_CODE=agm.AGENCY_CODE " +
					" WHERE d.DEAL_ID=n.DEAL_ID and d.DEAL_ID=dv.DEAL_ID  and n.DEAL_PRODUCT=p.PRODUCT_ID and " +
					" deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID  and n.DEAL_SCHEME=s.SCHEME_ID and d.DEAL_BRANCH='"+vo.getBranchId()+"' and d.Rec_status='F'" +
					" and dv.rec_status='F' AND DV.VERIFICATION_TYPE='C' AND DV.VERIFICATION_ACTION='I'" +
					" and (dv.internal_appraiser = '"+CommonFunction.checkNull(vo.getUserId())+"' or agm.USER_ID = '"+CommonFunction.checkNull(vo.getUserId())+"')" );
			
			
			bufInsSqlTempCount.append("select count(distinct d.DEAL_ID) from cr_deal_dtl d,  cr_deal_loan_dtl n," +
					" cr_product_m p, cr_deal_customer_m deal,cr_scheme_m s, cr_deal_verification_dtl dv left join com_agency_m ag on dv.external_appraiser=ag.AGENCY_CODE " +  
					" left join com_agency_user_mapping agm on ag.AGENCY_CODE=agm.AGENCY_CODE " +
					" WHERE d.DEAL_ID=n.DEAL_ID and d.DEAL_ID=dv.DEAL_ID  and " +
					" n.DEAL_PRODUCT=p.PRODUCT_ID and deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID  and n.DEAL_SCHEME=s.SCHEME_ID and d.DEAL_BRANCH='"+vo.getBranchId()+"'" +
					" and d.Rec_status='F' and dv.rec_status='F' AND DV.VERIFICATION_TYPE='C' AND DV.VERIFICATION_ACTION='I'" +
					" and (dv.internal_appraiser = '"+CommonFunction.checkNull(vo.getUserId())+"' or agm.USER_ID = '"+CommonFunction.checkNull(vo.getUserId())+"')" );

			logger.info("query : " + bufInsSql);
			logger.info("count query: "+bufInsSqlTempCount);
			if((CommonFunction.checkNull(vo.getLbxDealNo()).trim()==null && CommonFunction.checkNull(vo.getInitiationDate()).trim()==null && CommonFunction.checkNull(vo.getCustomername()).trim()==null && CommonFunction.checkNull(vo.getLbxProductID()).trim()==null && CommonFunction.checkNull(vo.getLbxscheme()).trim()==null) || (CommonFunction.checkNull(vo.getLbxDealNo()).trim().equalsIgnoreCase("") && CommonFunction.checkNull(vo.getInitiationDate()).trim().equalsIgnoreCase("") && CommonFunction.checkNull(vo.getCustomername()).trim().equalsIgnoreCase("") && CommonFunction.checkNull(vo.getLbxProductID()).trim().equalsIgnoreCase("") && CommonFunction.checkNull(vo.getLbxscheme()).trim().equalsIgnoreCase("")) || vo.getCurrentPageLink()>1)
			{
			
				logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
				if(vo.getCurrentPageLink()>1)
				{
					startRecordIndex = (vo.getCurrentPageLink()-1)*5;
					endRecordIndex = 5;
					logger.info("startRecordIndex .................... "+startRecordIndex);
					logger.info("endRecordIndex .................... "+endRecordIndex);
				}
			
				if(dbType.equalsIgnoreCase("MSSQL"))
				{
					bufInsSql.append(" ORDER BY GENERIC_KEY OFFSET ");
					bufInsSql.append(startRecordIndex);
					bufInsSql.append(" ROWS FETCH next ");
					bufInsSql.append(endRecordIndex);
					bufInsSql.append(" ROWS ONLY ");
					logger.info("Search searchGenericMasterDao query for SQL SERVER ::::::::::" + bufInsSql.toString());
				}
				else
				{
					bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
				}
			
			}
			header = ConnectionDAO.sqlSelect(bufInsSql.toString());
			count = Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
			
			bufInsSql=null;
			bufInsSqlTempCount=null;

			

			for (int i = 0; i < header.size(); i++) {
				logger.info("header: " + header.size());
				ArrayList header1 = (ArrayList) header.get(i);
				if (header1 != null && header1.size() > 0) {

					fetchVo = new CommonDealVo();
					fetchVo.setLbxDealNo((CommonFunction.checkNull(header1.get(0))).trim());
					fetchVo.setDealNo("<a href=collateralVerificationCapturingSearch.do?method=openCollateralCapturing&dealId="
	      					+ (CommonFunction.checkNull(header1.get(0)).trim())+">"
	      					+ (CommonFunction.checkNull(header1.get(1)).trim()) + "</a>");
					fetchVo.setInitiationDate((CommonFunction.checkNull(header1.get(2))).trim());
					fetchVo.setCustomername((CommonFunction.checkNull(header1.get(3))).trim());
					fetchVo.setProduct((CommonFunction.checkNull(header1.get(4))).trim());
					fetchVo.setScheme((CommonFunction.checkNull(header1.get(5))).trim());
					fetchVo.setUserName(userName);
					fetchVo.setTotalRecordSize(count);
					list.add(fetchVo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			userName=null;
		}

		LoggerMsg.info("Detail List when searchList is in initialCollateralVerifiationCapturingData : " + list.size());

		return list;
	}

	public ArrayList<CommonDealVo> initialCollateralVerifiationCompletionData(CommonDealVo vo) {
		ArrayList<CommonDealVo> list = new ArrayList<CommonDealVo>();
		ArrayList header = new ArrayList();
		StringBuilder userId=new StringBuilder();
		try {
			logger.info("Inside initialCollateralVerifiationCompletionData.....");
			CommonDealVo fetchVo = (CommonDealVo) vo;
			 userId.append(fetchVo.getUserId());	
			boolean appendSQL = false;
			int count=0;
			int startRecordIndex=0;
			int endRecordIndex = no;

			
			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();

			bufInsSql.append(" select distinct d.DEAL_ID,DEAL_NO,");
			bufInsSql.append(dbo);
			bufInsSql.append("DATE_FORMAT(DEAL_DATE,'"+dateFormat+"'),deal.CUSTOMER_NAME,p.PRODUCT_DESC,s.SCHEME_DESC from (SELECT COUNT(*) Collater_Count,TD.DEAL_ID FROM cr_collatetral_check_dtl TD WHERE TD.REC_STATUS='F' GROUP BY TD.DEAL_ID) AS F,   (SELECT COUNT(*) D_COLL_COUNT,X.DEAL_ID FROM cr_deal_verification_dtl X WHERE X.VERIFICATION_TYPE='C' and X.VERIFICATION_ACTION='I' GROUP BY X.DEAL_ID) AS G, cr_deal_dtl d,  cr_deal_loan_dtl n, cr_collatetral_check_dtl ccd, cr_product_m p, cr_deal_customer_m deal,cr_scheme_m s WHERE d.DEAL_ID=n.DEAL_ID and d.DEAL_ID=ccd.DEAL_ID and n.DEAL_PRODUCT=p.PRODUCT_ID and deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID and n.DEAL_SCHEME=s.SCHEME_ID and ccd.rec_status = 'F' AND F.Collater_Count=G.D_COLL_COUNT AND F.DEAL_ID = G.DEAL_ID AND d.DEAL_ID=F.DEAL_ID AND d.DEAL_ID=G.DEAL_ID and d.DEAL_ID not in(select  distinct DEAL_ID from cr_collatetral_check_dtl where rec_status='F' and MAKER_ID='"+userId+"')");
			bufInsSqlTempCount.append(" select count(1) from (select distinct d.DEAL_ID,DEAL_NO,");
			bufInsSqlTempCount.append(dbo);
			bufInsSqlTempCount.append("DATE_FORMAT(DEAL_DATE,'"+dateFormat+"'),deal.CUSTOMER_NAME,p.PRODUCT_DESC,s.SCHEME_DESC from (SELECT COUNT(*) Collater_Count,TD.DEAL_ID FROM cr_collatetral_check_dtl TD WHERE TD.REC_STATUS='F' GROUP BY TD.DEAL_ID) AS F,   (SELECT COUNT(*) D_COLL_COUNT,X.DEAL_ID FROM cr_deal_verification_dtl X WHERE X.VERIFICATION_TYPE='C' and X.VERIFICATION_ACTION='I' GROUP BY X.DEAL_ID) AS G, cr_deal_dtl d,  cr_deal_loan_dtl n, cr_collatetral_check_dtl ccd, cr_product_m p, cr_deal_customer_m deal,cr_scheme_m s WHERE d.DEAL_ID=n.DEAL_ID and d.DEAL_ID=ccd.DEAL_ID and n.DEAL_PRODUCT=p.PRODUCT_ID and deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID and n.DEAL_SCHEME=s.SCHEME_ID and ccd.rec_status = 'F' AND F.Collater_Count=G.D_COLL_COUNT AND F.DEAL_ID = G.DEAL_ID AND d.DEAL_ID=F.DEAL_ID AND d.DEAL_ID=G.DEAL_ID and d.DEAL_ID not in(select  distinct DEAL_ID from cr_collatetral_check_dtl where rec_status='F' and MAKER_ID='"+userId+"'))as f");
			header = ConnectionDAO.sqlSelect(bufInsSql.toString());
			count = Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
			
			if((CommonFunction.checkNull(vo.getLbxDealNo()).trim()==null && CommonFunction.checkNull(vo.getInitiationDate()).trim()==null && CommonFunction.checkNull(vo.getCustomername()).trim()==null && CommonFunction.checkNull(vo.getLbxProductID()).trim()==null && CommonFunction.checkNull(vo.getLbxscheme()).trim()==null) || (CommonFunction.checkNull(vo.getLbxDealNo()).trim().equalsIgnoreCase("") && CommonFunction.checkNull(vo.getInitiationDate()).trim().equalsIgnoreCase("") && CommonFunction.checkNull(vo.getCustomername()).trim().equalsIgnoreCase("") && CommonFunction.checkNull(vo.getLbxProductID()).trim().equalsIgnoreCase("") && CommonFunction.checkNull(vo.getLbxscheme()).trim().equalsIgnoreCase("")) || vo.getCurrentPageLink()>1)
			{
			
				logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
				if(vo.getCurrentPageLink()>1)
				{
					startRecordIndex = (vo.getCurrentPageLink()-1)*no;
					endRecordIndex =no;
					logger.info("startRecordIndex .................... "+startRecordIndex);
					logger.info("endRecordIndex .................... "+endRecordIndex);
				}
				if(dbType.equalsIgnoreCase("MSSQL"))
				{
					bufInsSql.append(" ORDER BY GENERIC_KEY OFFSET ");
					bufInsSql.append(startRecordIndex);
					bufInsSql.append(" ROWS FETCH next ");
					bufInsSql.append(endRecordIndex);
					bufInsSql.append(" ROWS ONLY ");
					logger.info("Search searchGenericMasterDao query for SQL SERVER : " + bufInsSql.toString());
				}
				else
				{
					bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
				}
			
			}
			
			logger.info("query :::::::::::::::::::::::::" + bufInsSql.toString());
			logger.info("count query::::::::::::::::::::::;"+bufInsSqlTempCount.toString());
			bufInsSql=null;
			bufInsSqlTempCount=null;


			for (int i = 0; i < header.size(); i++) {
				logger.info("header: " + header.size());
				ArrayList header1 = (ArrayList) header.get(i);
				if (header1 != null && header1.size() > 0) {

					fetchVo = new CommonDealVo();
					fetchVo.setLbxDealNo((CommonFunction.checkNull(header1.get(0))).trim());
					fetchVo.setDealNo("<a href=collateralVerificationCompletionSearch.do?method=openCollateralCompletion&dealId="
	      					+ (CommonFunction.checkNull(header1.get(0)).trim())+">"
	      					+ (CommonFunction.checkNull(header1.get(1)).trim()) + "</a>");
					fetchVo.setInitiationDate((CommonFunction.checkNull(header1.get(2))).trim());
					fetchVo.setCustomername((CommonFunction.checkNull(header1.get(3))).trim());
					fetchVo.setProduct((CommonFunction.checkNull(header1.get(4))).trim());
					fetchVo.setScheme((CommonFunction.checkNull(header1.get(5))).trim());
					fetchVo.setTotalRecordSize(count);
					list.add(fetchVo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			userId=null;
		}

		LoggerMsg.info("Detail List when searchList is in initialCollateralVerifiationCompletionData : " + list.size());

		return list;
	}


	public ArrayList searchVerId(CollateralCapturingVO vo,String userId,String dealId) {
		ArrayList list=new ArrayList();
		try
		{
			StringBuilder query=new StringBuilder();
		query.append("select dv.VERIFICATION_ID from cr_deal_verification_dtl dv left join com_agency_m ag on dv.external_appraiser=ag.AGENCY_CODE left join com_agency_user_mapping agm on ag.AGENCY_CODE=agm.AGENCY_CODE where dv.DEAL_ID="+dealId+" and (dv.internal_appraiser = '"+userId+"' or agm.USER_ID = '"+userId+"') and dv.VERIFICATION_ACTION='I' and dv.VERIFICATION_TYPE='C' and dv.REC_STATUS='F'");
			
					logger.info("In CollateralVerificationdaoImpl------searchVerId() query is :-"+query);

					ArrayList list1 = ConnectionDAO.sqlSelect(query.toString());
					
					query=null;
										
					for(int i=0;i<list1.size();i++){
						logger.info("list1"+list1.size());
						ArrayList bSList=(ArrayList)list1.get(i);
						if(bSList!=null && bSList.size()>0)
						{
							vo = new CollateralCapturingVO();
						
							vo.setVerCheckId((CommonFunction.checkNull(bSList.get(0))).trim());
							list.add(vo);
						}
				}
			}
	
		catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	

}
