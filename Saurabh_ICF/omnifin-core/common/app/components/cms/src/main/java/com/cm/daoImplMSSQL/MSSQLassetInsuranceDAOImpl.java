package com.cm.daoImplMSSQL;

import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;





import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;




import com.cm.dao.assetInsuranceDAO;
import com.cm.vo.AssetForCMVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.logger.LoggerMsg;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.upload.FormFile;


public class MSSQLassetInsuranceDAOImpl implements assetInsuranceDAO {
	
	private static final Logger logger = Logger.getLogger(MSSQLassetInsuranceDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	//change by sachin
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");
	//String selectFrom = resource.getString("lbl.selectFrom");
	//end by sachin
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	
	
		public String getSaveAssetMaker(Object ob) {
			AssetForCMVO assetMakervo =  (AssetForCMVO)ob; 
			ArrayList<AssetForCMVO> getDataList=new ArrayList<AssetForCMVO>();
//			PrepStmtObject insertPrepStmtObject = new PrepStmtObject();	
			boolean status=false;
			String result="";
			logger.info("In AssetInsurance...Dao Impl.");
			 logger.info("In getSaveAssetMaker......................");
			 ArrayList qryList = new ArrayList();

			
				try{
				
					String comboInsert=ConnectionDAO.singleReturn(" SELECT count(ASSET_INSURANCE_ID) from cr_asset_insurance_dtl where rec_status<>'X' and loan_id='"+CommonFunction.checkNull(assetMakervo.getLbxLoanNoHID())+"' and ENTITY_TYPE='"+CommonFunction.checkNull(assetMakervo.getEntityType())+"'  and (POLICY_NO='"+CommonFunction.checkNull(assetMakervo.getPolicyNo())+"' OR COVER_NOTE='"+CommonFunction.checkNull(assetMakervo.getCoverNoteNo())+"') and INSURANCE_CO_ID='"+CommonFunction.checkNull(assetMakervo.getLbxInsuranceAgency())+"' ");
					String insuranceCompanyCheck=ConnectionDAO.singleReturn(" SELECT count(ASSET_INSURANCE_ID) from cr_asset_insurance_dtl where rec_status<>'X' and loan_id='"+CommonFunction.checkNull(assetMakervo.getLbxLoanNoHID())+"' and ENTITY_ID='"+CommonFunction.checkNull(assetMakervo.getLbxEntity())+"' and INSURANCE_CO_ID='"+CommonFunction.checkNull(assetMakervo.getLbxInsuranceAgency())+"' and YEAR_NO='"+CommonFunction.checkNull(assetMakervo.getYearNo())+"' ");
					String sameYearWarning=ConnectionDAO.singleReturn(" SELECT count(ASSET_INSURANCE_ID) from cr_asset_insurance_dtl where rec_status<>'X' and loan_id='"+CommonFunction.checkNull(assetMakervo.getLbxLoanNoHID())+"' and ENTITY_ID='"+CommonFunction.checkNull(assetMakervo.getLbxEntity())+"' and YEAR_NO='"+CommonFunction.checkNull(assetMakervo.getYearNo())+"' ");
				    if(CommonFunction.checkNull(comboInsert).equalsIgnoreCase("0"))
				    {
				    	if(CommonFunction.checkNull(insuranceCompanyCheck).equalsIgnoreCase("0"))
					    {	
				    		
				    		if(CommonFunction.checkNull(assetMakervo.getAfterWarning()).equalsIgnoreCase("WARN") || CommonFunction.checkNull(sameYearWarning).equalsIgnoreCase("0"))
						    {	
					StringBuffer bufInsSql =	new StringBuffer();
				    PrepStmtObject insertPrepStmtObject = new PrepStmtObject();	
					bufInsSql.append("insert into cr_asset_insurance_dtl (LOAN_ID,COVER_NOTE,POLICY_NO,INSURANCE_CO_ID,INSURANCE_BY," +
					" SUM_ASSURED,PREMIUM_AMOUNT,START_DATE,END_DATE,REMARKS," +
		           " RAISE_ADVICE_FLAG,REC_STATUS,MAKER_ID,MAKER_DATE,INITIATION_DATE,YEAR_NO,ENTITY_TYPE,ENTITY_ID,SURRENDER_VALUE,PREMIUM_FREQUENCY,NOMINEE,RELATIONSHIP_WITH_NOMINEE )");
					bufInsSql.append(" values ( ");
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(dbo);
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"')," );
					bufInsSql.append(dbo);
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"')," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(dbo);
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
					bufInsSql.append(dbo);
					bufInsSql.append("STR_TO_DATE(?,'"+dateFormat+"')," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ?," );
					bufInsSql.append(" ? )" );
					
					if(CommonFunction.checkNull(assetMakervo.getLbxLoanNoHID()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((assetMakervo.getLbxLoanNoHID()).trim());
					
					if(CommonFunction.checkNull(assetMakervo.getCoverNoteNo()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((assetMakervo.getCoverNoteNo()).trim());

					if (CommonFunction.checkNull(assetMakervo.getPolicyNo()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((assetMakervo.getPolicyNo()).trim());
					
					if(CommonFunction.checkNull(assetMakervo.getLbxInsuranceAgency()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((assetMakervo.getLbxInsuranceAgency()).trim());
				
					if(CommonFunction.checkNull(assetMakervo.getInsuranceDoneBy()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((assetMakervo.getInsuranceDoneBy()).trim());
					
					if (CommonFunction.checkNull((assetMakervo.getSumAssured()).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(myFormatter.parse((assetMakervo.getSumAssured()).trim()).toString());
					
					if (CommonFunction.checkNull((assetMakervo.getPremiumAmnt()).trim()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(myFormatter.parse(assetMakervo.getPremiumAmnt().trim()).toString());
			
					if(CommonFunction.checkNull(assetMakervo.getStartDate()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((assetMakervo.getStartDate()).trim());
					if(CommonFunction.checkNull(assetMakervo.getEndDate()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((assetMakervo.getEndDate()).trim());
										
					if(CommonFunction.checkNull(assetMakervo.getRemarks()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((assetMakervo.getRemarks()).trim());					
				
					if(CommonFunction.checkNull(assetMakervo.getAdviseFlag()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((assetMakervo.getAdviseFlag()));	
					
						insertPrepStmtObject.addString("P");
					
					if(CommonFunction.checkNull(assetMakervo.getMakerId()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((assetMakervo.getMakerId()).trim());
					if(CommonFunction.checkNull(assetMakervo.getBusinessDate()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((assetMakervo.getBusinessDate()).trim());
					if(CommonFunction.checkNull(assetMakervo.getStartDate()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((assetMakervo.getStartDate()).trim());
					
					if(CommonFunction.checkNull(assetMakervo.getYearNo()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((assetMakervo.getYearNo()).trim());
					
					if(CommonFunction.checkNull(assetMakervo.getEntityType()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((assetMakervo.getEntityType()).trim());
					
					if(CommonFunction.checkNull(assetMakervo.getLbxEntity()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((assetMakervo.getLbxEntity()).trim());
					
					
					if(CommonFunction.checkNull(assetMakervo.getSurrenderValue()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(myFormatter.parse((assetMakervo.getSurrenderValue()).trim()).toString());
										
					if(CommonFunction.checkNull(assetMakervo.getPremiumFrequency()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((assetMakervo.getPremiumFrequency()).trim());
					
					if(CommonFunction.checkNull(assetMakervo.getNominee()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((assetMakervo.getNominee()).trim());					
					
					if(CommonFunction.checkNull(assetMakervo.getRelWithNominee()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((assetMakervo.getRelWithNominee()).trim());
																
						insertPrepStmtObject.setSql(bufInsSql.toString());
					
					qryList.add(insertPrepStmtObject);
					logger.info("IN SaveDatafor() insert cr_asset_insurance_dtl query1 ### "+insertPrepStmtObject.printQuery());
					status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
					logger.info("In saveData. cr_asset_insurance_dtl ....................."+status);
					if(status)
					{
						result="Y";
					}else{
						result="E";
					}
				}else{
					  result="W";
					}
			 }else{
			    	result="I";
				    }
			    }
			    	else{
						result="N";
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}			
				return result;
			}

		public String getAssetInsuranceId(){
			String instId="";
			StringBuilder assetInsuranceID= new StringBuilder();
			logger.info("In AssetInsurance...Dao Impl.");
			try{
				assetInsuranceID.append("SELECT max(ASSET_INSURANCE_ID) from cr_asset_insurance_dtl  WITH (ROWLOCK) ");
				
				instId=ConnectionDAO.singleReturn(assetInsuranceID.toString());
		  		logger.info("IN paymentId ### "+assetInsuranceID.toString());
		    }
		  	   catch(Exception e){
						e.printStackTrace();
					}
		  	   finally
		  	   {
		  		 assetInsuranceID = null;
		  	   }

					return instId;
				}

		public ArrayList <AssetForCMVO>searchAssetData(AssetForCMVO assetMakervo)
		{
		    ArrayList<AssetForCMVO> assetList=new ArrayList<AssetForCMVO>();
		    logger.info("In AssetInsurance...Dao Impl.");
		    
		try{
			  ArrayList searchlist=new ArrayList();
			  ArrayList data = new ArrayList();  
			  logger.info("In searchAssetData.................."+assetMakervo);
			  StringBuffer bufInsSql = new StringBuffer();	
			  String entityType=CommonFunction.checkNull(assetMakervo.getEntityType());
 				logger.info("assetMakervo.getEntityType()"+entityType);
					bufInsSql.append("Select distinct Top 1 CAI.COVER_NOTE,CAI.POLICY_NO,CAI.INSURANCE_CO_ID,A.AGENCY_NAME,CAI.INSURANCE_BY,"+
	  			" CAI.SUM_ASSURED,CAI.PREMIUM_AMOUNT,dbo.date_format(CAI.START_DATE,'"+dateFormat+"'), " +
	  			" dbo.date_format(CAI.END_DATE,'"+dateFormat+"'),CAI.APPROVAL_REMARKS, CAI.LOAN_ID,CLD.LOAN_NO,GCM.CUSTOMER_NAME, "+
	  			"  CAC.ASSET_ID,CAC.ASSET_COLLATERAL_DESC,CAC.ASSET_COLLATERAL_VALUE,CAI.RAISE_ADVICE_FLAG,CAI.ASSET_INSURANCE_ID,REMARKS,CAI.YEAR_NO, "+
	  			" CAI.ENTITY_TYPE,CAI.ENTITY_ID,CAI.SURRENDER_VALUE,CAI.PREMIUM_FREQUENCY,CAI.NOMINEE,CAI.RELATIONSHIP_WITH_NOMINEE, ");
					
				if(CommonFunction.checkNull(assetMakervo.getEntityType()).equals("ASSET"))
				{
				bufInsSql.append(" CAC.ASSET_COLLATERAL_DESC,  " +
	  			" CASE ASSET_NEW_OLD WHEN 'N' THEN 'NEW' WHEN 'O' THEN 'OLD' END AS ASSET_NEW_OLD,ISNULL(VEHICLE_MAKE,MACHINE_MAKE),ISNULL(VEHICLE_MODEL,VEHICLE_MODEL),  "+
	  			" DEALER_DESC,ISNULL(VEHICLE_ENGINE_NO,ENGINE_NUMBER),VEHICLE_CHASIS_NUMBER,VEHICLE_REGISTRATION_NO,  ");
				}
		  if(CommonFunction.checkNull(assetMakervo.getEntityType()).equals("ASSET") || CommonFunction.checkNull(assetMakervo.getEntityType()).equals("COLLATERAL"))
		  {
				bufInsSql.append(" m.ASSET_COLLATERAL_DESC ");
		  }else{
  				bufInsSql.append(" m.customer_name ");
		  	}
				bufInsSql.append("  from cr_asset_collateral_m CAC "+
	  			" JOIN cr_asset_insurance_dtl CAI ON CAC.ASSET_ID=CAI.ENTITY_ID AND CAI.REC_STATUS='P'  "+
	  			" JOIN cr_loan_dtl CLD on CAI.LOAN_ID=CLD.LOAN_ID "+
	 			" JOIN com_agency_m A on A.AGENCY_CODE=CAI.INSURANCE_CO_ID "+
	 			" LEFT JOIN CR_DSA_DEALER_M CDDM ON CAC.ASSET_SUPPLIER=CDDM.DEALER_ID "+
				" JOIN gcd_customer_m GCM  on CLD.LOAN_CUSTOMER_ID=GCM.CUSTOMER_ID ");
				
		  if(CommonFunction.checkNull(assetMakervo.getEntityType()).equals("ASSET") || CommonFunction.checkNull(assetMakervo.getEntityType()).equals("COLLATERAL"))
		  {
  			  bufInsSql.append(" JOIN (SELECT Top 1  LOAN_ID, ASSET_COLLATERAL_DESC FROM cr_asset_insurance_dtl "+
				 " JOIN cr_asset_collateral_m ON cr_asset_collateral_m.ASSET_ID=cr_asset_insurance_dtl.ENTITY_ID "+
				 " AND cr_asset_insurance_dtl.ENTITY_TYPE IN ('ASSET','COLLATERAL') "+
				 " WHERE cr_asset_insurance_dtl.ASSET_INSURANCE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(assetMakervo.getAssetInsuranceId()).trim())+"' ) m "+
				 " on m.loan_id=CAI.LOAN_ID ");
			}else{
  			bufInsSql.append(" JOIN (SELECT Top 1   gcd_customer_m.CUSTOMER_NAME,cr_asset_insurance_dtl.LOAN_ID from cr_asset_insurance_dtl "+
		  		" JOIN  cr_loan_customer_role on cr_loan_customer_role.GCD_ID=cr_asset_insurance_dtl.ENTITY_ID AND  LOAN_CUSTOMER_ROLE_TYPE='"+CommonFunction.checkNull(assetMakervo.getEntityType())+"' "+
		  		" JOIN gcd_customer_m on gcd_customer_m.CUSTOMER_ID=cr_loan_customer_role.GCD_ID where  "+
		  		" ASSET_INSURANCE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(assetMakervo.getAssetInsuranceId()).trim())+"'  ) m "+
		  		" on m.loan_id=CAI.LOAN_ID  ");
		  	}		
		  	bufInsSql.append(" where ASSET_INSURANCE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(assetMakervo.getAssetInsuranceId()).trim())+"'  ");
	
			  	logger.info("In searchAssetData......... query..........."+bufInsSql.toString());
			  	searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
		     	logger.info("searchAssetData size is...."+searchlist.size());

		      for(int i=0;i<searchlist.size();i++){
		    	  logger.info("searchAssetData List "+searchlist.get(i).toString());
		    	  data=(ArrayList)searchlist.get(i);

		if(data.size()>0){
			AssetForCMVO assetVO = new AssetForCMVO();
			
			assetVO.setCoverNoteNo((CommonFunction.checkNull(data.get(0)).trim()));
			  assetVO.setPolicyNo((CommonFunction.checkNull(data.get(1)).trim()));
			  assetVO.setLbxInsuranceAgency((CommonFunction.checkNull(data.get(2)).trim()));
			  
			  assetVO.setInsuranceAgency((CommonFunction.checkNull(data.get(3)).trim()));
			 
			  assetVO.setInsuranceDoneBy((CommonFunction.checkNull(data.get(4)).trim()));
			 
			  if(!CommonFunction.checkNull(data.get(5)).equalsIgnoreCase(""))	
				{
					Number SumAssured =myFormatter.parse((CommonFunction.checkNull(data.get(5))).trim());
					assetVO.setSumAssured(myFormatter.format(SumAssured));
				}
				  
			  if(!CommonFunction.checkNull(data.get(6)).equalsIgnoreCase(""))	
				{
					Number PremiumAmnt =myFormatter.parse((CommonFunction.checkNull(data.get(6))).trim());
					assetVO.setPremiumAmnt(myFormatter.format(PremiumAmnt));
				}
			  else {
				  assetVO.setPremiumAmnt("0");
			    }
			  assetVO.setStartDate((CommonFunction.checkNull(data.get(7)).trim()));
			  assetVO.setEndDate((CommonFunction.checkNull(data.get(8)).trim()));
			  assetVO.setAuthorRemarks((CommonFunction.checkNull(data.get(9)).trim()));
			  assetVO.setLbxLoanNoHID((CommonFunction.checkNull(data.get(10)).trim()));		   
		      assetVO.setLoanAccountNumber((CommonFunction.checkNull(data.get(11)).trim()));		      
		      assetVO.setCustomerName((CommonFunction.checkNull(data.get(12)).trim()));		     
			  assetVO.setAdviseFlag((CommonFunction.checkNull(data.get(16)).trim()));					 
			  assetVO.setRemarks((CommonFunction.checkNull(data.get(18)).trim()));
			  assetVO.setYearNo((CommonFunction.checkNull(data.get(19)).trim()));
			  assetVO.setEntityTypeDesc((CommonFunction.checkNull(data.get(20)).trim()));
			  assetVO.setLbxEntity((CommonFunction.checkNull(data.get(21)).trim()));			  
			  assetVO.setSurrenderValue((CommonFunction.checkNull(data.get(22)).trim()));
			  assetVO.setPremiumFrequency((CommonFunction.checkNull(data.get(23)).trim()));
			  assetVO.setNominee((CommonFunction.checkNull(data.get(24)).trim()));
			  assetVO.setRelWithNominee((CommonFunction.checkNull(data.get(25)).trim()));
			  if(CommonFunction.checkNull(assetMakervo.getEntityType()).equals("ASSET"))
				{
			  assetVO.setAssetDesc((CommonFunction.checkNull(data.get(26)).trim()));
			  assetVO.setAssetNature((CommonFunction.checkNull(data.get(27)).trim()));
			  assetVO.setAssetMake((CommonFunction.checkNull(data.get(28)).trim()));
			  assetVO.setAssetModel((CommonFunction.checkNull(data.get(29)).trim()));
			  assetVO.setDealerName((CommonFunction.checkNull(data.get(30)).trim()));
			  assetVO.setEngineNumber((CommonFunction.checkNull(data.get(31)).trim()));
			  assetVO.setChasisNumber((CommonFunction.checkNull(data.get(32)).trim()));
			  assetVO.setRegistrationNumber((CommonFunction.checkNull(data.get(33)).trim()));
			  assetVO.setEntity((CommonFunction.checkNull(data.get(34)).trim()));
		}else{
			 assetVO.setEntity((CommonFunction.checkNull(data.get(26)).trim()));
		   }
			  assetVO.setEntityType((CommonFunction.checkNull(data.get(20)).trim()));
			  assetList.add(assetVO);				
		 }

		}
		}catch(Exception e){
			e.printStackTrace();
				}
		return  assetList;	
		}

	public String saveForwardDataUpdate(AssetForCMVO assetMakervo){
			
			ArrayList<AssetForCMVO> getDataList=new ArrayList<AssetForCMVO>();
			logger.info("In AssetInsurance...Dao Impl.");
			boolean status=false;
			String result="";
			logger.info("In saveForwardDataUpdate,,,,,");
			 ArrayList queryList=new ArrayList();
			
			try{
				 String assetInsuranceId=assetMakervo.getAssetInsuranceId();
			 	 logger.info(" AssetInsuranceId:::"+ assetInsuranceId);
			 	 String recStatus =assetMakervo.getRecStatus();
			 	 logger.info(" recStatus:::"+ recStatus);					
			 	 StringBuffer sBUpdQry =	new StringBuffer();
			 	 PrepStmtObject updatePrepStmtObject = new PrepStmtObject();
			 	
			 	String comboInsert=ConnectionDAO.singleReturn(" SELECT count(ASSET_INSURANCE_ID) from cr_asset_insurance_dtl where loan_id='"+CommonFunction.checkNull(assetMakervo.getLbxLoanNoHID())+"' and ENTITY_TYPE='"+CommonFunction.checkNull(assetMakervo.getEntityType())+"'  and (POLICY_NO='"+CommonFunction.checkNull(assetMakervo.getPolicyNo())+"' or COVER_NOTE='"+CommonFunction.checkNull(assetMakervo.getCoverNoteNo())+"') and INSURANCE_CO_ID='"+CommonFunction.checkNull(assetMakervo.getInsuranceAgency())+"' AND ASSET_INSURANCE_ID<>'"+CommonFunction.checkNull(assetMakervo.getAssetInsuranceId())+"'");
			 	String insuranceCompanyCheck=ConnectionDAO.singleReturn(" SELECT count(ASSET_INSURANCE_ID) from cr_asset_insurance_dtl where loan_id='"+CommonFunction.checkNull(assetMakervo.getLbxLoanNoHID())+"' and ENTITY_ID='"+CommonFunction.checkNull(assetMakervo.getLbxEntity())+"' and INSURANCE_CO_ID='"+CommonFunction.checkNull(assetMakervo.getLbxInsuranceAgency())+"' and YEAR_NO='"+CommonFunction.checkNull(assetMakervo.getYearNo())+"' AND ASSET_INSURANCE_ID<>'"+CommonFunction.checkNull(assetMakervo.getAssetInsuranceId())+"' ");
				String sameYearWarning=ConnectionDAO.singleReturn(" SELECT count(ASSET_INSURANCE_ID) from cr_asset_insurance_dtl where loan_id='"+CommonFunction.checkNull(assetMakervo.getLbxLoanNoHID())+"' and ENTITY_ID='"+CommonFunction.checkNull(assetMakervo.getLbxEntity())+"' and YEAR_NO='"+CommonFunction.checkNull(assetMakervo.getYearNo())+"' AND ASSET_INSURANCE_ID<>'"+CommonFunction.checkNull(assetMakervo.getAssetInsuranceId())+"' ");
			    if(CommonFunction.checkNull(comboInsert).equalsIgnoreCase("0"))
			    {	
			    	if(CommonFunction.checkNull(insuranceCompanyCheck).equalsIgnoreCase("0"))
				    {
			    		if(CommonFunction.checkNull(assetMakervo.getAfterWarning()).equalsIgnoreCase("WARN") || CommonFunction.checkNull(sameYearWarning).equalsIgnoreCase("0"))
					    {
logger.info("EntityType"+assetMakervo.getEntityTypeDesc());
				 sBUpdQry.append(" UPDATE cr_asset_insurance_dtl SET ");
		         sBUpdQry.append(" LOAN_ID=?,COVER_NOTE=?,POLICY_NO=?, ");
		         sBUpdQry.append(" INSURANCE_CO_ID= ?,INSURANCE_BY =?,SUM_ASSURED= ?,");
		         sBUpdQry.append(" PREMIUM_AMOUNT= ?,START_DATE= ");
		         sBUpdQry.append(dbo);
		         sBUpdQry.append("STR_TO_DATE(?,'"+dateFormat+"'),END_DATE= ");
		         sBUpdQry.append(dbo);
		         sBUpdQry.append("STR_TO_DATE(?,'"+dateFormat+"'), ");
		         sBUpdQry.append(" REMARKS= ?,RAISE_ADVICE_FLAG=?,MAKER_ID=?,MAKER_DATE=");
		         sBUpdQry.append(dbo);
		         sBUpdQry.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ,");	
		         sBUpdQry.append("REC_STATUS='"+recStatus+"',INITIATION_DATE=");
		         sBUpdQry.append(dbo);
		         sBUpdQry.append("STR_TO_DATE(?,'"+dateFormat+"'), ");
		         sBUpdQry.append("YEAR_NO=?,ENTITY_TYPE=?,ENTITY_ID=?,SURRENDER_VALUE=?,PREMIUM_FREQUENCY=?,NOMINEE=?,RELATIONSHIP_WITH_NOMINEE=? WHERE ASSET_INSURANCE_ID="+assetInsuranceId+"   ");
		         
		         logger.info("In......saveForwardDataUpdate"+sBUpdQry.toString());
		       
		         if(CommonFunction.checkNull(assetMakervo.getLbxLoanNoHID()).equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((assetMakervo.getLbxLoanNoHID()).trim());
		         
		         if(CommonFunction.checkNull(assetMakervo.getCoverNoteNo()).equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((assetMakervo.getCoverNoteNo()).trim());

					if (CommonFunction.checkNull(assetMakervo.getPolicyNo()).equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((assetMakervo.getPolicyNo()).trim());
					
					if(CommonFunction.checkNull(assetMakervo.getLbxInsuranceAgency()).equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((assetMakervo.getLbxInsuranceAgency()).trim());
				
					if(CommonFunction.checkNull(assetMakervo.getInsuranceDoneBy()).equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((assetMakervo.getInsuranceDoneBy()).trim());
					
					if (CommonFunction.checkNull((assetMakervo.getSumAssured()).trim()).equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString(myFormatter.parse((assetMakervo.getSumAssured()).trim()).toString());
					
					if (CommonFunction.checkNull((assetMakervo.getPremiumAmnt()).trim()).equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString(myFormatter.parse(assetMakervo.getPremiumAmnt().trim()).toString());
					
					if(CommonFunction.checkNull(assetMakervo.getStartDate()).equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((assetMakervo.getStartDate()).trim());
					if(CommonFunction.checkNull(assetMakervo.getEndDate()).equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((assetMakervo.getEndDate()).trim());
					
					if(CommonFunction.checkNull(assetMakervo.getRemarks()).equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((assetMakervo.getRemarks()).trim());
					
					if(CommonFunction.checkNull(assetMakervo.getAdviseFlag()).trim().equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString(assetMakervo.getAdviseFlag().trim());	
					
					if ((CommonFunction.checkNull(assetMakervo.getMakerId())).trim().equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((assetMakervo.getMakerId()).trim());
					if ((CommonFunction.checkNull(assetMakervo.getBusinessDate()).trim()).equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString(CommonFunction.checkNull(assetMakervo.getBusinessDate()).trim());
					
					if(CommonFunction.checkNull(assetMakervo.getStartDate()).equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((assetMakervo.getStartDate()).trim());
					
					if(CommonFunction.checkNull(assetMakervo.getYearNo()).equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((assetMakervo.getYearNo()).trim());
					
					if(CommonFunction.checkNull(assetMakervo.getEntityTypeDesc()).equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((assetMakervo.getEntityTypeDesc()).trim());
				
					if(CommonFunction.checkNull(assetMakervo.getLbxEntity()).equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((assetMakervo.getLbxEntity()).trim());
					
					if(CommonFunction.checkNull(assetMakervo.getSurrenderValue()).equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((assetMakervo.getSurrenderValue()).trim());
										
					if(CommonFunction.checkNull(assetMakervo.getPremiumFrequency()).equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((assetMakervo.getPremiumFrequency()).trim());
					
					if(CommonFunction.checkNull(assetMakervo.getNominee()).equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((assetMakervo.getNominee()).trim());					
					
					if(CommonFunction.checkNull(assetMakervo.getRelWithNominee()).equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((assetMakervo.getRelWithNominee()).trim());
						
					updatePrepStmtObject.setSql(sBUpdQry.toString());
					
					queryList.add(updatePrepStmtObject);
					logger.info("IN updateOnSaveAsset query1 ### "+updatePrepStmtObject.printQuery());
			
					status =ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
					logger.info("In updateOnSaveAsset....................."+status);
					if(status)
					{
						result="Y";
					}else{
						result="E";
					}
				 }
			    else{
						result="W";
					}
				}
			     else{
						result="I";
					}
			    }else{
						result="N";
					}
			}catch(Exception e){
				e.printStackTrace();
			}
			return result;
		}	      

	   	public String approveByAuthor(AssetForCMVO assetMakervo,String uId,int companyId,String currDate,String assetInsuranceID,String comments,String status) {
    		boolean flag=false;
    		String result=""; 
    		logger.info("In AssetInsurance...Dao Impl.");
    	    ArrayList<Object> in =new ArrayList<Object>();
    		ArrayList<Object> out =new ArrayList<Object>();
    		ArrayList outMessages = new ArrayList();
    		StringBuilder s1= new StringBuilder();
    		StringBuilder s2= new StringBuilder();
    		StringBuilder date= new StringBuilder();
    		try{
    	    StringBuffer bufInsSql =	new StringBuffer();
    		ArrayList queryList=new ArrayList();
    		PrepStmtObject updatePrepStmtObject = new PrepStmtObject();    
    		int statusProc=0;

    		 in.add(companyId);
    		 in.add(Integer.parseInt(assetInsuranceID));
    		 date.append(CommonFunction.changeFormat(CommonFunction.checkNull(currDate)));
    		 if(date.toString() != null)
	    	 in.add(date.toString());
    		 in.add(uId);
    		 in.add(status);
    		 in.add(comments);
    		 out.add(s1.toString());
    		 out.add(s2.toString());

	    outMessages=(ArrayList) ConnectionDAO.callSP("Asset_Insurance_Author",in,out);
	    		s1.append(CommonFunction.checkNull(outMessages.get(0)));
			    s2.append(CommonFunction.checkNull(outMessages.get(1)));
   		logger.info("s1: "+s1);
   		logger.info("s2: "+s2);
   		
   		logger.info("After proc call....");	
   	
   		
   		if(!(s1.toString()).equalsIgnoreCase("S")){
   			logger.info("After Proc inside If "); 
   			flag=false;
   	
   			result=(s2.toString());
   		}else{
   			flag=true;  
   			result=(s1.toString());
   			 if((s1.toString()).equalsIgnoreCase("S"))  
   			 {
   					logger.info("After proc call..commit.error message."+s2);
   				}else{
   					logger.info("After proc call..rollback.error message."+s2);
   				}
    		}	
   		}catch(Exception e){
   				e.printStackTrace();
   			}
   		finally
   		{
   			s1 = null;
   			s2 = null;
   			date = null;
   		}
    	
   			return result;
   		}	

	   	public ArrayList<AssetForCMVO> assetAuthorGrid(AssetForCMVO assetMakervo)
	       	{
		   		String loanId=assetMakervo.getLbxLoanNoHID();
				String loanNo= assetMakervo.getLoanAccountNumber();
				String customerName= StringEscapeUtils.escapeSql(assetMakervo.getCustomerName());
				String coverNoteNo= assetMakervo.getCoverNoteNo();
				String policyNo= assetMakervo.getPolicyNo();
				String insuranceAgencyId= assetMakervo.getLbxInsuranceAgency();
				String insuranceAgency= assetMakervo.getInsuranceAgency();
				String startDate=assetMakervo.getStartDate();
				String endDate= assetMakervo.getEndDate();
			
	    	    logger.info("In AssetInsurance...Dao Impl.");
	    		int count=0;
	    		int startRecordIndex=0;
	    		int endRecordIndex = no;
	    	    ArrayList<AssetForCMVO> searchListGrid=new 	ArrayList<AssetForCMVO>();
	    		logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+assetMakervo.getReportingToUserId());

	    		  try{
	    			  ArrayList searchlist=new ArrayList();
	    	      logger.info("In assetMakerGrid....................");
	    	      boolean appendSQL=false;
	    	      StringBuffer bufInsSql =	new StringBuffer();
	    	      StringBuffer bufInsSqlTempCount = new StringBuffer();
	    	      
	    	      bufInsSql.append("Select distinct A.LOAN_ID,C.LOAN_NO,GCM.CUSTOMER_NAME,A.COVER_NOTE,A.POLICY_NO,A.INSURANCE_CO_ID,B.AGENCY_NAME, " );
	    	      bufInsSql.append(dbo);
	    	      bufInsSql.append("date_format(A.START_DATE,'"+dateFormat+"')AS START_DATE, ");
	    	      bufInsSql.append(dbo);
	    	      bufInsSql.append("date_format(A.END_DATE,'"+dateFormat+"')AS END_DATE ,(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=A.MAKER_ID) MAKER_ID,ASSET_INSURANCE_ID,ENTITY_TYPE " );
	    	      bufInsSql.append(" FROM cr_asset_insurance_dtl A,com_agency_m B,cr_loan_dtl C,gcd_customer_m GCM" );
	    	      bufInsSql.append(" WHERE B.AGENCY_CODE=A.INSURANCE_CO_ID AND C.LOAN_ID=A.LOAN_ID and c.LOAN_CUSTOMER_ID=GCM .CUSTOMER_ID AND A.REC_STATUS='F' AND A.MAKER_ID!='"+assetMakervo.getUserId()+"' ");
	    	    			  
	    	     bufInsSqlTempCount.append("Select distinct Count(1) FROM cr_asset_insurance_dtl A,com_agency_m B,cr_loan_dtl C,gcd_customer_m GCM " +
	    	    	      		"WHERE B.AGENCY_CODE=A.INSURANCE_CO_ID AND C.LOAN_ID=A.LOAN_ID and c.LOAN_CUSTOMER_ID=GCM .CUSTOMER_ID AND A.REC_STATUS='F' and A.MAKER_ID!='"+assetMakervo.getUserId()+"' ");
   	     
	    			  
	    	      if((!((loanId.toString()).trim().equalsIgnoreCase("")))&&(!((loanNo.toString()).trim().equalsIgnoreCase("")))&&(!((customerName.toString()).trim().equalsIgnoreCase("")))&&(!((coverNoteNo.toString()).trim().equalsIgnoreCase("")))&&(!((policyNo.toString()).trim().equalsIgnoreCase("")))&&(!((insuranceAgencyId.toString()).trim().equalsIgnoreCase("")))&&(!((insuranceAgency.toString()).trim().equalsIgnoreCase("")))&&(!((startDate.toString()).trim().equalsIgnoreCase("")))&&(!((endDate.toString()).trim().equalsIgnoreCase("")))) {
	    	    	  bufInsSql.append("  AND C.LOAN_NO='"+loanNo+"' AND GCM.CUSTOMER_NAME LIKE '%"+customerName+"%'AND A.COVER_NOTE='"+coverNoteNo+"' AND A.POLICY_NO='"+policyNo+"' AND A.INSURANCE_CO_ID='"+insuranceAgencyId+"' AND B.AGENCY_NAME LIKE '%"+insuranceAgency+"%' AND A.START_DATE='"+startDate+"' AND A.END_DATE='"+endDate+"'");
	    	    	  bufInsSqlTempCount.append("  AND C.LOAN_NO='"+loanNo+"' AND GCM.CUSTOMER_NAME LIKE '%"+customerName+"%'AND A.COVER_NOTE='"+coverNoteNo+"' AND A.POLICY_NO='"+policyNo+"' AND A.INSURANCE_CO_ID='"+insuranceAgencyId+"' AND B.AGENCY_NAME LIKE '%"+insuranceAgency+"%' AND A.START_DATE='"+startDate+"' AND A.END_DATE='"+endDate+"'");
	    	       }
	    		  if(!((loanId.toString()).trim().equalsIgnoreCase("")) || !((loanNo.toString()).trim().equalsIgnoreCase(""))|| !((customerName.toString()).trim().equalsIgnoreCase(""))|| !((coverNoteNo.toString()).trim().equalsIgnoreCase("")) || !((policyNo.toString()).trim().equalsIgnoreCase("")) || !((insuranceAgencyId.toString()).trim().equalsIgnoreCase("")) || !((insuranceAgency.toString()).trim().equalsIgnoreCase("")) || !((startDate.toString()).trim().equalsIgnoreCase("")) || !((endDate.toString()).trim().equalsIgnoreCase(""))){
	    				
	    		   }
	    		  appendSQL=true;
	    		  if(appendSQL){
	    				bufInsSql.append(" AND ");
	    				bufInsSqlTempCount.append(" AND ");
	    				
	                  }
	    		    if((!((loanNo.toString()).trim().equalsIgnoreCase("")))) {
	    	 	         bufInsSql.append(" C.LOAN_NO='"+loanNo+"' AND");
	    	 	        bufInsSqlTempCount.append(" C.LOAN_NO='"+loanNo+"' AND");
	    	 	    	 appendSQL=true;
	    	 	    	  
	    	 	      }
	    	  	    if((!((customerName.toString()).trim().equalsIgnoreCase("")))) {
	    		         bufInsSql.append(" GCM.CUSTOMER_NAME like '%"+customerName+"%' AND");
	    		         bufInsSqlTempCount.append(" C.LOAN_NO='"+loanNo+"' AND");
	    		    	 appendSQL=true;
	    		    	  
	    		      }
	    				 
	    		  if((!((coverNoteNo.toString()).trim().equalsIgnoreCase("")))) {
	    		    	  bufInsSql.append(" A.COVER_NOTE='"+coverNoteNo+"' AND");
	    		    	  bufInsSqlTempCount.append(" A.COVER_NOTE='"+coverNoteNo+"' AND");
	    		    	  appendSQL=true;
	    		      }
	    				
	    				if((!((policyNo.toString()).trim().equalsIgnoreCase("")))) {
	    		    	  bufInsSql.append(" A.POLICY_NO='"+policyNo+"' AND");
	    		    	  bufInsSqlTempCount.append(" A.POLICY_NO='"+policyNo+"' AND");
	    		    	  appendSQL=true;
	    		      }
	      	        if((!((insuranceAgencyId.toString()).trim().equalsIgnoreCase("")))) {
	    	         bufInsSql.append(" A.INSURANCE_CO_ID='"+insuranceAgencyId+"' AND");
	    	         bufInsSqlTempCount.append(" A.INSURANCE_CO_ID='"+insuranceAgencyId+"' AND");
	    	    	 appendSQL=true;
	    	    	  
	    	      }
	      	      if((!((insuranceAgency.toString()).trim().equalsIgnoreCase("")))) {
	     	         bufInsSql.append(" B.AGENCY_NAME LIKE '%"+insuranceAgency+"%' AND");
	     	        bufInsSqlTempCount.append(" B.AGENCY_NAME LIKE '%"+insuranceAgency+"%' AND");
	     	    	 appendSQL=true;
	     	    	  
	     	      }
	      	    if((!((startDate.toString()).trim().equalsIgnoreCase("")))) {
	    	         bufInsSql.append(" A.START_DATE=");
	    	         bufInsSql.append(dbo);
	    	         bufInsSql.append("STR_TO_DATE('"+startDate+"','"+dateFormatWithTime+"') AND");
	    	         bufInsSqlTempCount.append(" A.START_DATE=");
	    	         bufInsSqlTempCount.append(dbo);
	    	         bufInsSqlTempCount.append("STR_TO_DATE('"+startDate+"','"+dateFormatWithTime+"') AND");
	    	        
	    	    	 appendSQL=true;
	    	    	  
	    	      }
	      	   if((!((endDate.toString()).trim().equalsIgnoreCase("")))) {
	    	         bufInsSql.append(" A.END_DATE=");
	    	         bufInsSql.append(dbo);
	    	         bufInsSql.append("STR_TO_DATE('"+endDate+"','"+dateFormatWithTime+"') ");
	    	         bufInsSqlTempCount.append(" A.END_DATE=");
	    	         bufInsSqlTempCount.append(dbo);
	    	         bufInsSqlTempCount.append("STR_TO_DATE('"+endDate+"','"+dateFormatWithTime+"') ");
	    	    	 appendSQL=true;
	    	    	  
	    	      }	 	      	 
		      		bufInsSql.append("  'a'='a' ");	
					bufInsSqlTempCount.append(" 'a'='a' ");
					appendSQL = true;
				
	       				logger.info("In appendSQL true---- "+bufInsSql);
	       				logger.info("In appendSQL true---- "+bufInsSqlTempCount);
	       				 
	       				LoggerMsg.info("current PAge Link no .................... "+assetMakervo.getCurrentPageLink());
	       				if(assetMakervo.getCurrentPageLink()>1)
	       				{
	       					startRecordIndex = (assetMakervo.getCurrentPageLink()-1)*no;
	       					endRecordIndex = no;
	       					LoggerMsg.info("startRecordIndex .................... "+startRecordIndex);
	       					LoggerMsg.info("endRecordIndex .................... "+endRecordIndex);
	       				}  
	       			  searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
      				  count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));     			
      				
	       	      for(int i=0;i<searchlist.size();i++){
	       	      logger.info("assetMakerGrid search List "+searchlist.get(i).toString());
	       	      ArrayList data=(ArrayList)searchlist.get(i);

	       	      if(data.size()>0){
	       	    	  AssetForCMVO assetVO = new AssetForCMVO();
	       	
	       	    	assetVO.setLoanAccountNumber((CommonFunction.checkNull(data.get(1)).trim()));
		           	  assetVO.setCustomerName((CommonFunction.checkNull(data.get(2)).trim()));
		           	  assetVO.setCoverNoteNo((CommonFunction.checkNull(data.get(3)).trim()));

		           	  assetVO.setLoanAccountNumber("<a href=assetAuthorProcessAction.do?method=getDatatoApprove&loanId="
	     					+ CommonFunction.checkNull(data.get(0)).toString()+ "&entityType="+CommonFunction.checkNull(data.get(11)).toString()
	     					+"&assetInsuranceId="+CommonFunction.checkNull(data.get(10)).toString()+">"+ CommonFunction.checkNull(data.get(1)).toString() +"</a>");          	  
		           
		           	  assetVO.setPolicyNo((CommonFunction.checkNull(data.get(4)).trim()));
		           	  assetVO.setLbxInsuranceAgency((CommonFunction.checkNull(data.get(5)).trim()));
		           	  assetVO.setInsuranceAgency((CommonFunction.checkNull(data.get(6)).trim()));
		           	  assetVO.setStartDate((CommonFunction.checkNull(data.get(7)).trim()));
		           	  assetVO.setEndDate((CommonFunction.checkNull(data.get(8)).trim()));
		           	  assetVO.setTotalRecordSize(count);
		           	  assetVO.setReportingToUserId((CommonFunction.checkNull(data.get(9)).trim()));
		           	  assetVO.setAssetInsuranceId((CommonFunction.checkNull(data.get(10)).trim()));
		           	  assetVO.setEntityType((CommonFunction.checkNull(data.get(11)).trim()));
		           	  searchListGrid.add(assetVO);
	       	       }

	       		      }

	       		}catch(Exception e){
	       			e.printStackTrace();
	       				}
	       		finally
	       		{
	       			loanId = null;
	       			loanNo = null;
	       			customerName = null;
	       			coverNoteNo = null;
	       			policyNo = null;
	       			insuranceAgencyId = null;
	       			insuranceAgency = null;
	       			
	       			startDate = null;
	       			endDate = null;
	       		}
	       		return  searchListGrid;	
	       	}

	    public ArrayList <AssetForCMVO>savedDatatoApprove(AssetForCMVO assetMakervo)
	  	    {
	     	    ArrayList<AssetForCMVO> assetList=new ArrayList<AssetForCMVO>();
	     	   logger.info("In AssetInsurance...Dao Impl.......savedDatatoApprove");
	  	    try{
	  	    	 ArrayList searchlist=new ArrayList();
			  	    logger.info("In savedDatatoApprove.................."+assetMakervo);
			  	    StringBuffer bufInsSql =	new StringBuffer();
			  	    String entityType=CommonFunction.checkNull(assetMakervo.getEntityType());
				  
					bufInsSql.append("Select distinct Top 1 CAI.COVER_NOTE,CAI.POLICY_NO,CAI.INSURANCE_CO_ID,A.AGENCY_NAME,CASE CAI.INSURANCE_BY  WHEN 'B' THEN 'BORROWER' WHEN 'D' THEN 'DEALER' WHEN 'L' THEN 'LENDER' END AS INSURANCE_BY,"+
		  			" CAI.SUM_ASSURED,CAI.PREMIUM_AMOUNT,dbo.date_format(CAI.START_DATE,'"+dateFormat+"'), " +
		  			" dbo.date_format(CAI.END_DATE,'"+dateFormat+"'),CAI.APPROVAL_REMARKS, CAI.LOAN_ID,CLD.LOAN_NO,GCM.CUSTOMER_NAME, "+
		  			"  CAC.ASSET_ID,CAC.ASSET_COLLATERAL_DESC,CAC.ASSET_COLLATERAL_VALUE,CASE CAI.RAISE_ADVICE_FLAG WHEN 'Y' THEN 'YES' WHEN 'N' THEN 'NO' END AS RAISE_ADVICE_FLAG,CAI.ASSET_INSURANCE_ID,REMARKS,CAI.YEAR_NO, "+
		  			" CASE CAI.ENTITY_TYPE  WHEN 'PRAPPL' THEN 'APPLICANT' WHEN 'COAPPL' THEN 'CO-APPLICANT' WHEN 'GUARANTOR' THEN 'GUARANTOR' "+  
		  			" WHEN 'ASSET' THEN 'ASSET' WHEN 'COLLATERAL' THEN 'COLLATERAL' END AS ENTITY_TYPE,CAI.ENTITY_ID,CAI.SURRENDER_VALUE,CAI.PREMIUM_FREQUENCY,CAI.NOMINEE,CAI.RELATIONSHIP_WITH_NOMINEE, ");
					
					if(CommonFunction.checkNull(assetMakervo.getEntityType()).equals("ASSET"))
  					{
  					bufInsSql.append(" CAC.ASSET_COLLATERAL_DESC,  " +
		  			" CASE ASSET_NEW_OLD WHEN 'N' THEN 'NEW' WHEN 'O' THEN 'OLD' END AS ASSET_NEW_OLD,ISNULL(VEHICLE_MAKE,MACHINE_MAKE),ISNULL(VEHICLE_MODEL,VEHICLE_MODEL),  "+
		  			" DEALER_DESC,ISNULL(VEHICLE_ENGINE_NO,ENGINE_NUMBER),VEHICLE_CHASIS_NUMBER,VEHICLE_REGISTRATION_NO,  ");
  					}
			  if(CommonFunction.checkNull(assetMakervo.getEntityType()).equals("ASSET") || CommonFunction.checkNull(assetMakervo.getEntityType()).equals("COLLATERAL"))
			  {
					bufInsSql.append(" m.ASSET_COLLATERAL_DESC ");
			  }else{
					bufInsSql.append(" m.customer_name ");
			  	}
					bufInsSql.append("  from cr_asset_collateral_m CAC "+
		  			" JOIN cr_asset_insurance_dtl CAI  ON CAC.ASSET_ID=CAI.ENTITY_ID AND CAI.REC_STATUS='F'  "+
		  			" JOIN cr_loan_dtl CLD on CAI.LOAN_ID=CLD.LOAN_ID "+
		 			" JOIN com_agency_m A on A.AGENCY_CODE=CAI.INSURANCE_CO_ID "+
		 			" LEFT JOIN CR_DSA_DEALER_M CDDM ON CAC.ASSET_SUPPLIER=CDDM.DEALER_ID "+
					" JOIN gcd_customer_m GCM  on CLD.LOAN_CUSTOMER_ID=GCM.CUSTOMER_ID ");
				
			  if(CommonFunction.checkNull(assetMakervo.getEntityType()).equals("ASSET") || CommonFunction.checkNull(assetMakervo.getEntityType()).equals("COLLATERAL"))
			  {
				  bufInsSql.append(" JOIN (SELECT Top 1 LOAN_ID, ASSET_COLLATERAL_DESC FROM cr_asset_insurance_dtl "+
					 " JOIN cr_asset_collateral_m ON cr_asset_collateral_m.ASSET_ID=cr_asset_insurance_dtl.ENTITY_ID "+
					 " AND cr_asset_insurance_dtl.ENTITY_TYPE IN ('ASSET','COLLATERAL') "+
					 " WHERE cr_asset_insurance_dtl.ASSET_INSURANCE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(assetMakervo.getAssetInsuranceId()).trim())+"') m "+
					 " on m.loan_id=CAI.LOAN_ID ");
			}else{
				bufInsSql.append(" JOIN (SELECT Top 1 gcd_customer_m.CUSTOMER_NAME,cr_asset_insurance_dtl.LOAN_ID from cr_asset_insurance_dtl "+
			  		" JOIN  cr_loan_customer_role on cr_loan_customer_role.GCD_ID=cr_asset_insurance_dtl.ENTITY_ID AND  LOAN_CUSTOMER_ROLE_TYPE='"+CommonFunction.checkNull(assetMakervo.getEntityType())+"' "+
			  		" JOIN gcd_customer_m on gcd_customer_m.CUSTOMER_ID=cr_loan_customer_role.GCD_ID where  "+
			  		" ASSET_INSURANCE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(assetMakervo.getAssetInsuranceId()).trim())+"' ) m "+
			  		" on m.loan_id=CAI.LOAN_ID  ");
			  	}		
			  	bufInsSql.append(" where ASSET_INSURANCE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(assetMakervo.getAssetInsuranceId()).trim())+"'  ");
  			  
	  			  
	  			  logger.info("In searchAssetData......... query..........."+bufInsSql.toString());
	  			  searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
	  			  logger.info("searchAssetData size is...."+searchlist.size());

	  		      for(int i=0;i<searchlist.size();i++){
		  		logger.info("searchAssetData List "+searchlist.get(i).toString());
		  		ArrayList data=(ArrayList)searchlist.get(i);

	  		if(data.size()>0){
	  			AssetForCMVO assetVO = new AssetForCMVO();
	  			
	  			 assetVO.setCoverNoteNo((CommonFunction.checkNull(data.get(0)).trim()));
				  assetVO.setPolicyNo((CommonFunction.checkNull(data.get(1)).trim()));
				  assetVO.setLbxInsuranceAgency((CommonFunction.checkNull(data.get(2)).trim()));
				  
				  assetVO.setInsuranceAgency((CommonFunction.checkNull(data.get(3)).trim()));
				 
				  assetVO.setInsuranceDoneBy((CommonFunction.checkNull(data.get(4)).trim()));
				 
				  if(!CommonFunction.checkNull(data.get(5)).equalsIgnoreCase(""))	
					{
						Number SumAssured =myFormatter.parse((CommonFunction.checkNull(data.get(5))).trim());
						assetVO.setSumAssured(myFormatter.format(SumAssured));
					}
					  
				  if(!CommonFunction.checkNull(data.get(6)).equalsIgnoreCase(""))	
					{
						Number PremiumAmnt =myFormatter.parse((CommonFunction.checkNull(data.get(6))).trim());
						assetVO.setPremiumAmnt(myFormatter.format(PremiumAmnt));
					}
				  else {
					  assetVO.setPremiumAmnt("0");
				    }
				  assetVO.setStartDate((CommonFunction.checkNull(data.get(7)).trim()));
				  assetVO.setEndDate((CommonFunction.checkNull(data.get(8)).trim()));
				  assetVO.setAuthorRemarks((CommonFunction.checkNull(data.get(9)).trim()));
				  assetVO.setLbxLoanNoHID((CommonFunction.checkNull(data.get(10)).trim()));		   
			      assetVO.setLoanAccountNumber((CommonFunction.checkNull(data.get(11)).trim()));		      
			      assetVO.setCustomerName((CommonFunction.checkNull(data.get(12)).trim()));		     
				  assetVO.setAdviseFlag((CommonFunction.checkNull(data.get(16)).trim()));					 
				  assetVO.setRemarks((CommonFunction.checkNull(data.get(18)).trim()));
				  assetVO.setYearNo((CommonFunction.checkNull(data.get(19)).trim()));
				  assetVO.setEntityType((CommonFunction.checkNull(data.get(20)).trim()));
				  assetVO.setLbxEntity((CommonFunction.checkNull(data.get(21)).trim()));			  
				  assetVO.setSurrenderValue((CommonFunction.checkNull(data.get(22)).trim()));
				  assetVO.setPremiumFrequency((CommonFunction.checkNull(data.get(23)).trim()));
				  assetVO.setNominee((CommonFunction.checkNull(data.get(24)).trim()));
				  assetVO.setRelWithNominee((CommonFunction.checkNull(data.get(25)).trim()));
				  if(CommonFunction.checkNull(assetMakervo.getEntityType()).equals("ASSET"))
					{
				  assetVO.setAssetDesc((CommonFunction.checkNull(data.get(26)).trim()));
				  assetVO.setAssetNature((CommonFunction.checkNull(data.get(27)).trim()));
				  assetVO.setAssetMake((CommonFunction.checkNull(data.get(28)).trim()));
				  assetVO.setAssetModel((CommonFunction.checkNull(data.get(29)).trim()));
				  assetVO.setDealerName((CommonFunction.checkNull(data.get(30)).trim()));
				  assetVO.setEngineNumber((CommonFunction.checkNull(data.get(31)).trim()));
				  assetVO.setChasisNumber((CommonFunction.checkNull(data.get(32)).trim()));
				  assetVO.setRegistrationNumber((CommonFunction.checkNull(data.get(33)).trim()));
				  assetVO.setEntity((CommonFunction.checkNull(data.get(34)).trim()));
			}else{
				 assetVO.setEntity((CommonFunction.checkNull(data.get(26)).trim()));
			   }
				  assetList.add(assetVO);
	  		 }
	  		}
	  		}catch(Exception e){
	  			e.printStackTrace();
	  				}
	  		return  assetList;	
	  		}
	    
	    public boolean cancelAssets(AssetForCMVO assetMakervo)
		{
	    	ArrayList<AssetForCMVO> getDataList=new ArrayList<AssetForCMVO>();
			logger.info("In AssetInsurance...Dao Impl.");
			    boolean status=false;	
			     logger.info("In cancelAssets......................");
			    ArrayList qryList = new ArrayList();
				StringBuilder deleteCount= new StringBuilder();
			
				try{					 
					  deleteCount.append(ConnectionDAO.singleReturn("SELECT COUNT(1) FROM cr_asset_insurance_dtl WHERE ASSET_INSURANCE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(assetMakervo.getAssetInsuranceId()).trim())+"'"));
					  logger.info("In cancelAssets... deleteCount"+deleteCount);
					    PrepStmtObject insertPrepStmtObject=null;
						StringBuffer bufInsSql = null;
					   if(!(deleteCount.toString()).equalsIgnoreCase("0")){
						   insertPrepStmtObject =  new PrepStmtObject();
			                bufInsSql = new StringBuffer();	 
			              
		                bufInsSql.append(" DELETE FROM cr_asset_insurance_dtl WHERE ASSET_INSURANCE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(assetMakervo.getAssetInsuranceId()).trim())+"'");
		                logger.info("### bufInsSql #### "+bufInsSql.toString());
		                insertPrepStmtObject.setSql(bufInsSql.toString());
		                qryList.add(insertPrepStmtObject);  		           
						status =ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
						logger.info("In cancelAssets. cr_asset_insurance_dtl ....................."+status);
			      }
				}	 
				catch(Exception e){
					e.printStackTrace();
				}
				finally
				{
					deleteCount = null;
				}
				return status;
		}
	    public ArrayList<Object> getInsuranceDoneByList(){
	  	  
	  	  logger.info("in getInsuranceDoneByList() method***********************");    	
	  	ArrayList<Object>   list=new ArrayList<Object>();
	  	try{
	  		 String query=("select VALUE,DESCRIPTION  from GENERIC_MASTER WHERE GENERIC_KEY='INSURANC_DONE_BY' and REC_STATUS ='A' ");
	  		logger.info("query for InsuranceDoneByList:::::::::"+query);
	  		ArrayList doneBy = ConnectionDAO.sqlSelect(query);
	  		AssetForCMVO vo = null;
	  		logger.info("sizew of InsuranceDoneByList list:::::::"+doneBy.size());
	  		int size=doneBy.size();
	  	  		for(int i=0;i<size;i++)
	  	  		{
	  	  			ArrayList doneBy1=(ArrayList)doneBy.get(i);
	  	  			if(doneBy1.size()>0)
	  	  			{
	  	  				vo = new AssetForCMVO();
	  	  				vo.setDoneByCode((CommonFunction.checkNull(doneBy1.get(0)).toString()));
	  	  				vo.setDoneByDesc((CommonFunction.checkNull(doneBy1.get(1)).toString()));
	  	  				list.add(vo);	  				
	  	  			}    		
	  		
	  	  		}
	  	}
	  	catch(Exception e){
	  		e.printStackTrace();
	  		
	  	}
	  	return list;
	  }
	    public ArrayList<AssetForCMVO> assetMakerGrid(AssetForCMVO assetMakervo)
		{   
			
	    	String loanId=assetMakervo.getLbxLoanNoHID();
			String loanNo= assetMakervo.getLoanAccountNumber();
			String customerName= StringEscapeUtils.escapeSql(assetMakervo.getCustomerName());
			String coverNoteNo= assetMakervo.getCoverNoteNo();
			String policyNo= assetMakervo.getPolicyNo();
			String insuranceAgencyId= assetMakervo.getLbxInsuranceAgency();
			String insuranceAgency= assetMakervo.getInsuranceAgency();
			String startDate=assetMakervo.getStartDate();
			String endDate= assetMakervo.getEndDate();

		    String userName="";
			int count=0;
			int startRecordIndex=0;
			int endRecordIndex = no;
			logger.info("In AssetInsurance...Dao Impl.");
		
			logger.info("here userid++++++++++++++++++++++++++++ "+assetMakervo.getReportingToUserId());
				try{
					String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+assetMakervo.getReportingToUserId()+"'";
					userName=ConnectionDAO.singleReturn(userNameQ);
					logger.info("userNameQ: "+userNameQ+" userName: "+userName);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			
				ArrayList<AssetForCMVO> searchListGrid=new 	ArrayList<AssetForCMVO>();
			  try{
				  
				  ArrayList searchlist=new ArrayList();
		      logger.info("In assetMakerGrid....................");
		      boolean appendSQL=false;
		      StringBuffer bufInsSql =	new StringBuffer();
		      StringBuffer bufInsSqlTempCount = new StringBuffer();	      
		
		      bufInsSql.append("Select distinct A.LOAN_ID,C.LOAN_NO,GCM.CUSTOMER_NAME,A.COVER_NOTE,A.POLICY_NO,A.INSURANCE_CO_ID,B.AGENCY_NAME" +
			   " ,dbo.date_format(A.START_DATE,'"+dateFormat+"'), dbo.date_format(A.END_DATE,'"+dateFormat+"'),(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=A.MAKER_ID) MAKER_ID,ASSET_INSURANCE_ID,ENTITY_TYPE " +
				   " FROM cr_asset_insurance_dtl A,com_agency_m B,cr_loan_dtl C,gcd_customer_m GCM" +
				   " WHERE B.AGENCY_CODE=A.INSURANCE_CO_ID AND C.LOAN_ID=A.LOAN_ID and c.LOAN_CUSTOMER_ID=GCM .CUSTOMER_ID AND A.REC_STATUS='P' ");
				  
		          bufInsSqlTempCount.append("Select distinct Count(1) FROM cr_asset_insurance_dtl A,com_agency_m B,cr_loan_dtl C,gcd_customer_m GCM " +
		      		"WHERE B.AGENCY_CODE=A.INSURANCE_CO_ID AND C.LOAN_ID=A.LOAN_ID and c.LOAN_CUSTOMER_ID=GCM .CUSTOMER_ID AND A.REC_STATUS='P'  ");
				  
				  if((!((loanId.toString()).trim().equalsIgnoreCase("")))&&(!((loanNo.toString()).trim().equalsIgnoreCase("")))&&(!((customerName.toString()).trim().equalsIgnoreCase("")))&&(!((coverNoteNo.toString()).trim().equalsIgnoreCase("")))&&(!((policyNo.toString()).trim().equalsIgnoreCase("")))&&(!((insuranceAgencyId.toString()).trim().equalsIgnoreCase("")))&&(!((insuranceAgency.toString()).trim().equalsIgnoreCase("")))&&(!((startDate.toString()).trim().equalsIgnoreCase("")))&&(!((endDate.toString()).trim().equalsIgnoreCase("")))) {
			    	  bufInsSql.append("  AND C.LOAN_NO='"+loanNo+"' AND GCM.CUSTOMER_NAME LIKE '%"+customerName+"%'AND A.COVER_NOTE='"+coverNoteNo+"' AND A.POLICY_NO='"+policyNo+"' AND A.INSURANCE_CO_ID='"+insuranceAgencyId+"' AND B.AGENCY_NAME LIKE '%"+insuranceAgency+"%' AND A.START_DATE='"+startDate+"' AND A.END_DATE='"+endDate+"'");
			    	  bufInsSqlTempCount.append("  AND C.LOAN_NO='"+loanNo+"' AND GCM.CUSTOMER_NAME LIKE '%"+customerName+"%'AND A.COVER_NOTE='"+coverNoteNo+"' AND A.POLICY_NO='"+policyNo+"' AND A.INSURANCE_CO_ID='"+insuranceAgencyId+"' AND B.AGENCY_NAME LIKE '%"+insuranceAgency+"%' AND A.START_DATE='"+startDate+"' AND A.END_DATE='"+endDate+"'");
			       }
				  if(!((CommonFunction.checkNull(loanId).toString()).trim().equalsIgnoreCase("")) || !((CommonFunction.checkNull(loanNo).toString()).trim().equalsIgnoreCase(""))|| !((CommonFunction.checkNull(customerName).toString()).trim().equalsIgnoreCase(""))|| !((CommonFunction.checkNull(coverNoteNo).toString()).trim().equalsIgnoreCase("")) || !((CommonFunction.checkNull(policyNo).toString()).trim().equalsIgnoreCase("")) || !((CommonFunction.checkNull(insuranceAgencyId).toString()).trim().equalsIgnoreCase("")) || !((CommonFunction.checkNull(insuranceAgency).toString()).trim().equalsIgnoreCase("")) || !((CommonFunction.checkNull(startDate).toString()).trim().equalsIgnoreCase("")) || !((CommonFunction.checkNull(endDate).toString()).trim().equalsIgnoreCase("")) || (CommonFunction.checkNull(assetMakervo.getLbxUserId()).equalsIgnoreCase(""))){
						appendSQL=true;
				   }							
				  if(appendSQL){
						bufInsSql.append(" AND ");
						bufInsSqlTempCount.append(" AND ");
						
		              }
				    if((!((loanNo.toString()).trim().equalsIgnoreCase("")))) {
			 	         bufInsSql.append(" C.LOAN_NO='"+loanNo+"' AND");
			 	        bufInsSqlTempCount.append(" C.LOAN_NO='"+loanNo+"' AND");
			 	    	 appendSQL=true;
			 	    	  
			 	      }
			  	    if((!((customerName.toString()).trim().equalsIgnoreCase("")))) {
				         bufInsSql.append(" GCM.CUSTOMER_NAME like '%"+customerName+"%' AND");
				         bufInsSqlTempCount.append(" GCM.CUSTOMER_NAME like '%"+customerName+"%' AND");
				    	 appendSQL=true;
				    	  
				      }
						 
				  if((!((coverNoteNo.toString()).trim().equalsIgnoreCase("")))) {
				    	  bufInsSql.append(" A.COVER_NOTE='"+coverNoteNo+"' AND");
				    	  bufInsSqlTempCount.append(" A.COVER_NOTE='"+coverNoteNo+"' AND");
				    	  appendSQL=true;
				      }
						
						if((!((policyNo.toString()).trim().equalsIgnoreCase("")))) {
				    	  bufInsSql.append(" A.POLICY_NO='"+policyNo+"' AND");
				    	  bufInsSqlTempCount.append(" A.POLICY_NO='"+policyNo+"' AND");
				    	  appendSQL=true;
				      }
		  	        if((!((insuranceAgencyId.toString()).trim().equalsIgnoreCase("")))) {
			         bufInsSql.append(" A.INSURANCE_CO_ID='"+insuranceAgencyId+"' AND");
			         bufInsSqlTempCount.append(" A.INSURANCE_CO_ID='"+insuranceAgencyId+"' AND");
			    	 appendSQL=true;
			    	  
			      }
		  	      if((!((insuranceAgency.toString()).trim().equalsIgnoreCase("")))) {
		 	         bufInsSql.append(" B.AGENCY_NAME LIKE '%"+insuranceAgency+"%' AND");
		 	        bufInsSqlTempCount.append(" B.AGENCY_NAME LIKE '%"+insuranceAgency+"%' AND");
		 	    	 appendSQL=true;
		 	    	  
		 	      }
		  	    if((!((startDate.toString()).trim().equalsIgnoreCase("")))) {
			         bufInsSql.append(" A.START_DATE=STR_TO_DATE('"+startDate+"','"+dateFormatWithTime+"') AND");
			         bufInsSqlTempCount.append(" A.START_DATE=STR_TO_DATE('"+startDate+"','"+dateFormatWithTime+"') AND");
			        
			    	 appendSQL=true;
			    	  
			      }
		  	   if((!((endDate.toString()).trim().equalsIgnoreCase("")))) {
			         bufInsSql.append(" A.END_DATE=STR_TO_DATE('"+endDate+"','"+dateFormatWithTime+"') AND");
			         bufInsSqlTempCount.append(" A.END_DATE=STR_TO_DATE('"+endDate+"','"+dateFormatWithTime+"') AND");
			    	 appendSQL=true;
			    	  
			      }	
		  	 if (((StringEscapeUtils.escapeSql(CommonFunction.checkNull(assetMakervo.getLbxUserId())).trim().equalsIgnoreCase(""))))
				{
		  		 	bufInsSql.append(" A.MAKER_ID='"+StringEscapeUtils.escapeSql(assetMakervo.getUserId()).trim()+"' ");	
		  		 	bufInsSqlTempCount.append(" A.MAKER_ID='"+StringEscapeUtils.escapeSql(assetMakervo.getUserId()).trim()+"' ");
					appendSQL = true;			
				}
				else{
					bufInsSql.append(" A.MAKER_ID='"+StringEscapeUtils.escapeSql(assetMakervo.getReportingToUserId()).trim()+"' ");	
					bufInsSqlTempCount.append(" A.MAKER_ID='"+StringEscapeUtils.escapeSql(assetMakervo.getReportingToUserId()).trim()+"' ");
					appendSQL = true;
				    }				
					logger.info("In bufInsSql query---- "+bufInsSql);						
					count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));      	
					 
					  if(((loanNo.toString()).trim().equalsIgnoreCase("") && (customerName.toString()).trim()==null && (coverNoteNo.toString()).trim()==null && (policyNo.toString()).trim()==null && (insuranceAgencyId.toString()).trim()==null && (startDate.toString()).trim()==null && (endDate.toString()).trim()==null && (insuranceAgency.toString()).trim()==null) || ((loanNo.toString()).trim().equalsIgnoreCase("") && (customerName.toString()).trim().equalsIgnoreCase("") && (coverNoteNo.toString()).trim().equalsIgnoreCase("") && (policyNo.toString()).trim().equalsIgnoreCase("") && (insuranceAgencyId.toString()).trim().equalsIgnoreCase("") && (startDate.toString()).trim().equalsIgnoreCase("") && (endDate.toString()).trim().equalsIgnoreCase("") && (insuranceAgency.toString()).trim().equalsIgnoreCase("")) || assetMakervo.getCurrentPageLink()>1)
						{
						
						LoggerMsg.info("current PAge Link no .................... "+assetMakervo.getCurrentPageLink());
						if(assetMakervo.getCurrentPageLink()>1)
						{
							startRecordIndex = (assetMakervo.getCurrentPageLink()-1)*no;
							endRecordIndex = no;
							LoggerMsg.info("startRecordIndex .................... "+startRecordIndex);
							LoggerMsg.info("endRecordIndex .................... "+endRecordIndex);
						}						
						//bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);						
						}
					  searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
					
				      for(int i=0;i<searchlist.size();i++){
				      logger.info("assetMakerGrid search List "+searchlist.get(i).toString());
				      ArrayList data=(ArrayList)searchlist.get(i);
		
		      if(data.size()>0){
		    	  AssetForCMVO assetVO = new AssetForCMVO();
		    	  assetVO.setModifyNo((CommonFunction.checkNull(data.get(1)).trim()));
		    	  assetVO.setPolicyNo((CommonFunction.checkNull(data.get(4)).trim()));
		    	  assetVO.setCustomerName((CommonFunction.checkNull(data.get(2)).trim()));
		    	  assetVO.setCoverNoteNo((CommonFunction.checkNull(data.get(3)).trim()));
		    	  	    	
		    	  assetVO.setLoanAccountNumber("<a href=assetProcessAction.do?method=showAssetData&loanId="
							+ CommonFunction.checkNull(data.get(0)).toString()+ "&entityType="+CommonFunction.checkNull(data.get(11)).toString()
							+ "&assetInsuranceId="+CommonFunction.checkNull(data.get(10)).toString()+">"+ CommonFunction.checkNull(data.get(1)).toString() +"</a>");
		    	  
		    	  assetVO.setLbxInsuranceAgency((CommonFunction.checkNull(data.get(5)).trim()));
		    	  assetVO.setInsuranceAgency((CommonFunction.checkNull(data.get(6)).trim()));	    	
		    	  assetVO.setStartDate((CommonFunction.checkNull(data.get(7)).trim()));
		    	  assetVO.setEndDate((CommonFunction.checkNull(data.get(8)).trim()));
		    	  assetVO.setEntityType((CommonFunction.checkNull(data.get(11)).trim()));
		    	  assetVO.setTotalRecordSize(count);
		    	  assetVO.setReportingToUserId(userName);  	 
		    	  searchListGrid.add(assetVO);	
		       }
		
			      }
		
			}catch(Exception e){
				e.printStackTrace();
					}
			finally
			{
				loanId = null;
				loanNo = null;
				customerName = null;
				coverNoteNo = null;
				policyNo = null;
				insuranceAgencyId = null;
				insuranceAgency = null;
				startDate = null;
				endDate = null;
			}
			return  searchListGrid;	
		}
	    public ArrayList<AssetForCMVO> getAssetInsuranceViewer(AssetForCMVO assetMakervo)
		{
			    logger.info("in getAssetInsuranceViewer() method DaoImpl****"+CommonFunction.checkNull(assetMakervo.getLoanId()));    	
		    	ArrayList searchlist=new ArrayList();
		    	ArrayList<AssetForCMVO> assetList=new ArrayList<AssetForCMVO>();
		    	
		    try{
		    	String query=("Select distinct CAI.POLICY_NO,A.AGENCY_NAME, "+
		    				"  CASE CAI.INSURANCE_BY  WHEN 'B' THEN 'BORROWER' WHEN 'D' THEN 'DEALER' WHEN 'L' THEN 'LENDER' END AS INSURANCE_BY  , "+
		    				" GCM.CUSTOMER_NAME,CASE CAI.ENTITY_TYPE  WHEN 'PRAPPL' THEN 'APPLICANT' WHEN 'COAPPL' THEN 'CO-APPLICANT' WHEN 'GUARANTOR' THEN 'GUARANTOR' "+  
		    				" WHEN 'ASSET' THEN 'ASSET' WHEN 'COLLATERAL' THEN 'COLLATERAL' END AS ENTITY_TYPE, "+
			    			" CAI.PREMIUM_AMOUNT,dbo.date_format(CAI.START_DATE,'"+dateFormat+"'), "+
			    			" dbo.date_format(CAI.END_DATE,'"+dateFormat+"'),CAI.ASSET_INSURANCE_ID,CAI.LOAN_ID,ENTITY_TYPE "+
			    			" from cr_asset_collateral_m CAC "+
			    			" JOIN cr_asset_insurance_dtl CAI on  CAI.REC_STATUS='A' "+  
			    			" JOIN cr_loan_dtl CLD on CAI.LOAN_ID=CLD.LOAN_ID  "+
			    			" JOIN com_agency_m A on A.AGENCY_CODE=CAI.INSURANCE_CO_ID "+ 
			    			" JOIN gcd_customer_m GCM  on CLD.LOAN_CUSTOMER_ID=GCM.CUSTOMER_ID "+ 
			    			" where CAI.loan_id='"+CommonFunction.checkNull(assetMakervo.getLoanId())+"' ");
		    	   
		    			logger.info("query for AssetInsuranceViewer:::::::::"+query);
		    			searchlist = ConnectionDAO.sqlSelect(query.toString());
			  		    logger.info("searchAssetData size is...."+searchlist.size());

			  		   for(int i=0;i<searchlist.size();i++){
		  		    	  logger.info("searchAssetData List "+searchlist.get(i).toString());
		  		    	  ArrayList data=(ArrayList)searchlist.get(i);

			  		if(data.size()>0){
			  			AssetForCMVO assetVO = new AssetForCMVO();				  			
											    	  
			  			  assetVO.setAssetInsuranceId("<a href=\"#\" onclick=\"javascript:assetPolicyViewer('"+CommonFunction.checkNull(data.get(8).toString()).trim()+"','"+CommonFunction.checkNull(data.get(10).toString()).trim()+"')\">"+CommonFunction.checkNull(data.get(8)).toString().trim()+"</a>");
			  			  assetVO.setPolicyNo((CommonFunction.checkNull(data.get(0)).trim()));			 		  
						  assetVO.setInsuranceAgency((CommonFunction.checkNull(data.get(1)).trim()));			 
						  assetVO.setInsuranceDoneBy((CommonFunction.checkNull(data.get(2)).trim()));
						  assetVO.setCustomerName((CommonFunction.checkNull(data.get(3)).trim()));	
						  assetVO.setEntityType((CommonFunction.checkNull(data.get(4)).trim()));
				  if(!CommonFunction.checkNull(data.get(5)).equalsIgnoreCase(""))	
					{
					  Number PremiumAmnt =myFormatter.parse((CommonFunction.checkNull(data.get(5))).trim());
					  assetVO.setPremiumAmnt(myFormatter.format(PremiumAmnt));
					}					  
					  assetVO.setStartDate((CommonFunction.checkNull(data.get(6)).trim()));
					  assetVO.setEndDate((CommonFunction.checkNull(data.get(7)).trim()));				  
					  
			  		  assetList.add(assetVO);				
		  	  		} 
		  	  	}
		    }
		    	catch(Exception e){
		    		e.printStackTrace();	    		
		    	}
			return assetList;
		}
	    
		public ArrayList<AssetForCMVO> getAssetInsuranceViewerMaker(AssetForCMVO assetMakervo)
		{
			    logger.info("in getAssetInsuranceViewer() method DaoImpl****"+CommonFunction.checkNull(assetMakervo.getLoanId()));    	
		    	ArrayList searchlist=new ArrayList();
		    	ArrayList<AssetForCMVO> assetList=new ArrayList<AssetForCMVO>();
		    	
		    try{
		    	String query=("SELECT DISTINCT CAI.YEAR_NO,A.AGENCY_NAME,CASE CAI.ENTITY_TYPE  WHEN 'PRAPPL' THEN 'APPLICANT' WHEN 'COAPPL' THEN 'CO-APPLICANT' WHEN 'GUARANTOR' THEN 'GUARANTOR'  "+
	    				" WHEN 'ASSET' THEN 'ASSET' WHEN 'COLLATERAL' THEN 'COLLATERAL' END AS ENTITY_TYPE,GCM.CUSTOMER_NAME,dbo.date_format(CAI.START_DATE,'%D-%M-%Y'),  dbo.date_format(CAI.END_DATE,'%D-%M-%Y'), "+
	    				" CASE CAI.INSURANCE_BY  WHEN 'B' THEN 'BORROWER' WHEN 'D' THEN 'DEALER' WHEN 'L' THEN 'LENDER' END AS INSURANCE_BY, "+
	    				" CAI.POLICY_NO,CAI.COVER_NOTE,CAI.PREMIUM_AMOUNT,CAI.SUM_ASSURED,CAI.ENTITY_ID,CAI.ASSET_INSURANCE_ID,CAI.LOAN_ID,ENTITY_TYPE,CLD.LOAN_NO, CAC.ASSET_COLLATERAL_DESC,CAI.RAISE_ADVICE_FLAG,CAI.ENTITY_TYPE "+
	    				" FROM CR_ASSET_COLLATERAL_M CAC "+
	    				" JOIN CR_ASSET_INSURANCE_DTL CAI ON CAC.ASSET_ID=CAI.ENTITY_ID AND  CAI.REC_STATUS='A'  "+
	    				" JOIN CR_LOAN_DTL CLD ON CAI.LOAN_ID=CLD.LOAN_ID  "+
	    				" JOIN COM_AGENCY_M A ON A.AGENCY_CODE=CAI.INSURANCE_CO_ID "+
	    				" JOIN GCD_CUSTOMER_M GCM  ON CLD.LOAN_CUSTOMER_ID=GCM.CUSTOMER_ID "+
	    				" WHERE CAI.LOAN_ID='"+CommonFunction.checkNull(assetMakervo.getLoanId())+"' ");
		    	   
		    			logger.info("query for AssetInsuranceViewer:::::::::"+query);
		    			searchlist = ConnectionDAO.sqlSelect(query.toString());
			  		    logger.info("searchAssetData size is...."+searchlist.size());

			  		   for(int i=0;i<searchlist.size();i++){
		  		    	  logger.info("searchAssetData List "+searchlist.get(i).toString());
		  		    	  ArrayList data=(ArrayList)searchlist.get(i);

			  		if(data.size()>0){
			  			AssetForCMVO assetVO = new AssetForCMVO();  			
						
			  			assetVO.setYearNo("<a href=\"#\" onclick=\"clickAssetInsuranceMaker('"+CommonFunction.checkNull(data.get(13)).toString()+"','"+CommonFunction.checkNull(data.get(14)).toString()+"','"+CommonFunction.checkNull(data.get(12)).toString()+"')\" >"+ CommonFunction.checkNull(data.get(0)).toString() +"</a>");
				  	  assetVO.setInsuranceAgency((CommonFunction.checkNull(data.get(1)).trim()));
		  			  assetVO.setEntityType((CommonFunction.checkNull(data.get(2)).trim()));
		  			  assetVO.setCustomerName((CommonFunction.checkNull(data.get(3)).trim()));
		  			  assetVO.setStartDate((CommonFunction.checkNull(data.get(4)).trim()));
					  assetVO.setEndDate((CommonFunction.checkNull(data.get(5)).trim())); 						  			 
					  assetVO.setInsuranceDoneBy((CommonFunction.checkNull(data.get(6)).trim()));
					  assetVO.setPolicyNo((CommonFunction.checkNull(data.get(7)).trim()));
					  assetVO.setCoverNoteNo((CommonFunction.checkNull(data.get(8)).trim()));						 
					  if(!CommonFunction.checkNull(data.get(9)).equalsIgnoreCase(""))	
						{
							Number PremiumAmnt =myFormatter.parse((CommonFunction.checkNull(data.get(9))).trim());
							assetVO.setPremiumAmnt(myFormatter.format(PremiumAmnt));
						}
					  else {
						  assetVO.setPremiumAmnt("0");
					    }
					  if(!CommonFunction.checkNull(data.get(10)).equalsIgnoreCase(""))	
						{
							Number SumAssured =myFormatter.parse((CommonFunction.checkNull(data.get(10))).trim());
							assetVO.setSumAssured(myFormatter.format(SumAssured));
						}
			  		  assetVO.setLbxEntity((CommonFunction.checkNull(data.get(11)).trim()));	
			  		  assetVO.setLbxLoanNoHID((CommonFunction.checkNull(data.get(13)).trim()));	
			  		  assetVO.setLoanAccountNumber((CommonFunction.checkNull(data.get(15)).trim()));
			  		  assetVO.setEntity((CommonFunction.checkNull(data.get(16)).trim()));
					assetVO.setEntityTypeDesc((CommonFunction.checkNull(data.get(18)).trim()));
		  		      assetList.add(assetVO);				
		  	  		} 
		  	  	}
		    }
		    	catch(Exception e){
		    		e.printStackTrace();	    		
		    	}
			return assetList;
		}
		
		public ArrayList<AssetForCMVO> onInsurancePolicyViewer(AssetForCMVO assetMakervo)
		{
			 ArrayList<AssetForCMVO> assetPolicy=new ArrayList<AssetForCMVO>();
	     	   logger.info("In onInsurancePolicyViewer...Dao Impl......");
	  	    try{
	  	    	  ArrayList searchlist=new ArrayList();
		  	    logger.info("In onInsurancePolicyViewer.................."+assetMakervo);
		  	    StringBuffer bufInsSql =	new StringBuffer();
		  	  String entityType=CommonFunction.checkNull(assetMakervo.getEntityType());
			  
				bufInsSql.append("Select distinct Top 1 CAI.COVER_NOTE,CAI.POLICY_NO,CAI.INSURANCE_CO_ID,A.AGENCY_NAME,CAI.INSURANCE_BY ,"+
	  			" CAI.SUM_ASSURED,CAI.PREMIUM_AMOUNT,dbo.date_format(CAI.START_DATE,'"+dateFormat+"'), " +
	  			" dbo.date_format(CAI.END_DATE,'"+dateFormat+"'),CAI.APPROVAL_REMARKS, CAI.LOAN_ID,CLD.LOAN_NO,GCM.CUSTOMER_NAME, "+
	  			"  CAC.ASSET_ID,CAC.ASSET_COLLATERAL_DESC,CAC.ASSET_COLLATERAL_VALUE,CASE CAI.RAISE_ADVICE_FLAG WHEN 'Y' THEN 'YES' WHEN 'N' THEN 'NO' END AS RAISE_ADVICE_FLAG,CAI.ASSET_INSURANCE_ID,CAI.REMARKS,CAI.YEAR_NO, "+
	  			" CASE CAI.ENTITY_TYPE  WHEN 'PRAPPL' THEN 'APPLICANT' WHEN 'COAPPL' THEN 'CO-APPLICANT' WHEN 'GUARANTOR' THEN 'GUARANTOR' "+  
	  			" WHEN 'ASSET' THEN 'ASSET' WHEN 'COLLATERAL' THEN 'COLLATERAL' END AS ENTITY_TYPE,CAI.ENTITY_ID,CAI.SURRENDER_VALUE,CAI.PREMIUM_FREQUENCY,CAI.NOMINEE,CAI.RELATIONSHIP_WITH_NOMINEE, ");
				
				if(CommonFunction.checkNull(assetMakervo.getEntityType()).equals("ASSET"))
					{
					bufInsSql.append(" CAC.ASSET_COLLATERAL_DESC,  " +
		  			" CASE ASSET_NEW_OLD WHEN 'N' THEN 'NEW' WHEN 'O' THEN 'OLD' END AS ASSET_NEW_OLD,ISNULL(VEHICLE_MAKE,MACHINE_MAKE),ISNULL(VEHICLE_MODEL,VEHICLE_MODEL),  "+
		  			" DEALER_DESC,ISNULL(VEHICLE_ENGINE_NO,ENGINE_NUMBER),VEHICLE_CHASIS_NUMBER,VEHICLE_REGISTRATION_NO,  ");
					}
		  if(CommonFunction.checkNull(assetMakervo.getEntityType()).equals("ASSET") || CommonFunction.checkNull(assetMakervo.getEntityType()).equals("COLLATERAL"))
		  {
				bufInsSql.append(" m.ASSET_COLLATERAL_DESC ");
		  }else{
				bufInsSql.append(" m.customer_name ");
		  	}
				bufInsSql.append("  from cr_asset_collateral_m CAC "+
	  			" JOIN cr_asset_insurance_dtl CAI  ON CAC.ASSET_ID=CAI.ENTITY_ID AND CAI.REC_STATUS='A'  "+
	  			" JOIN cr_loan_dtl CLD on CAI.LOAN_ID=CLD.LOAN_ID "+
	 			" JOIN com_agency_m A on A.AGENCY_CODE=CAI.INSURANCE_CO_ID "+
	 			" JOIN CR_DSA_DEALER_M CDDM ON CAC.ASSET_SUPPLIER=CDDM.DEALER_ID "+
				" JOIN gcd_customer_m GCM  on CLD.LOAN_CUSTOMER_ID=GCM.CUSTOMER_ID ");
			
		  if(CommonFunction.checkNull(assetMakervo.getEntityType()).equals("ASSET") || CommonFunction.checkNull(assetMakervo.getEntityType()).equals("COLLATERAL"))
		  {
			  bufInsSql.append(" JOIN (SELECT Top 1 LOAN_ID, ASSET_COLLATERAL_DESC FROM cr_asset_insurance_dtl "+
				 " JOIN cr_asset_collateral_m ON cr_asset_collateral_m.ASSET_ID=cr_asset_insurance_dtl.ENTITY_ID "+
				 " AND cr_asset_insurance_dtl.ENTITY_TYPE IN ('ASSET','COLLATERAL') "+
				 " WHERE cr_asset_insurance_dtl.ASSET_INSURANCE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(assetMakervo.getAssetInsuranceId()).trim())+"' ) m "+
				 " on m.loan_id=CAI.LOAN_ID ");
		}else{
			bufInsSql.append(" JOIN (SELECT  Top 1 gcd_customer_m.CUSTOMER_NAME,cr_asset_insurance_dtl.LOAN_ID from cr_asset_insurance_dtl "+
		  		" JOIN  cr_loan_customer_role on cr_loan_customer_role.GCD_ID=cr_asset_insurance_dtl.ENTITY_ID AND  LOAN_CUSTOMER_ROLE_TYPE='"+CommonFunction.checkNull(assetMakervo.getEntityType())+"' "+
		  		" JOIN gcd_customer_m on gcd_customer_m.CUSTOMER_ID=cr_loan_customer_role.GCD_ID where  "+
		  		" ASSET_INSURANCE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(assetMakervo.getAssetInsuranceId()).trim())+"' ) m "+
		  		" on m.loan_id=CAI.LOAN_ID  ");
		  	}		
		  	bufInsSql.append(" where ASSET_INSURANCE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(assetMakervo.getAssetInsuranceId()).trim())+"' ");

	  			  	logger.info("In onInsurancePolicyViewer forward......... query..........."+bufInsSql.toString());
	  			  	searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
	  			  	logger.info("onInsurancePolicyViewer size is...."+searchlist.size());

	  		      for(int i=0;i<searchlist.size();i++){
		  		logger.info("onInsurancePolicyViewer List "+searchlist.get(i).toString());
		  		ArrayList data=(ArrayList)searchlist.get(i);

	  		if(data.size()>0){
	  			AssetForCMVO assetVO = new AssetForCMVO();
	  			
	  			  assetVO.setCoverNoteNo((CommonFunction.checkNull(data.get(0)).trim()));
				  assetVO.setPolicyNo((CommonFunction.checkNull(data.get(1)).trim()));
				  assetVO.setLbxInsuranceAgency((CommonFunction.checkNull(data.get(2)).trim()));
				  
				  assetVO.setInsuranceAgency((CommonFunction.checkNull(data.get(3)).trim()));
				 
				  assetVO.setInsuranceDoneBy((CommonFunction.checkNull(data.get(4)).trim()));
				 
				  if(!CommonFunction.checkNull(data.get(5)).equalsIgnoreCase(""))	
					{
						Number SumAssured =myFormatter.parse((CommonFunction.checkNull(data.get(5))).trim());
						assetVO.setSumAssured(myFormatter.format(SumAssured));
					}
					  
				  if(!CommonFunction.checkNull(data.get(6)).equalsIgnoreCase(""))	
					{
						Number PremiumAmnt =myFormatter.parse((CommonFunction.checkNull(data.get(6))).trim());
						assetVO.setPremiumAmnt(myFormatter.format(PremiumAmnt));
					}
				  else {
					  assetVO.setPremiumAmnt("0");
				    }
				  assetVO.setStartDate((CommonFunction.checkNull(data.get(7)).trim()));
				  assetVO.setEndDate((CommonFunction.checkNull(data.get(8)).trim()));
				  assetVO.setAuthorRemarks((CommonFunction.checkNull(data.get(9)).trim()));
				  assetVO.setLbxLoanNoHID((CommonFunction.checkNull(data.get(10)).trim()));		   
			      assetVO.setLoanAccountNumber((CommonFunction.checkNull(data.get(11)).trim()));		      
			      assetVO.setCustomerName((CommonFunction.checkNull(data.get(12)).trim()));		     
				  assetVO.setAdviseFlag((CommonFunction.checkNull(data.get(16)).trim()));					 
				  assetVO.setRemarks((CommonFunction.checkNull(data.get(18)).trim()));
				  assetVO.setYearNo((CommonFunction.checkNull(data.get(19)).trim()));
				  assetVO.setEntityType((CommonFunction.checkNull(data.get(20)).trim()));
				  assetVO.setLbxEntity((CommonFunction.checkNull(data.get(21)).trim()));			  
				  assetVO.setSurrenderValue((CommonFunction.checkNull(data.get(22)).trim()));
				  assetVO.setPremiumFrequency((CommonFunction.checkNull(data.get(23)).trim()));
				  assetVO.setNominee((CommonFunction.checkNull(data.get(24)).trim()));
				  assetVO.setRelWithNominee((CommonFunction.checkNull(data.get(25)).trim()));
				  if(CommonFunction.checkNull(assetMakervo.getEntityType()).equals("ASSET"))
					{
				  assetVO.setAssetDesc((CommonFunction.checkNull(data.get(26)).trim()));
				  assetVO.setAssetNature((CommonFunction.checkNull(data.get(27)).trim()));
				  assetVO.setAssetMake((CommonFunction.checkNull(data.get(28)).trim()));
				  assetVO.setAssetModel((CommonFunction.checkNull(data.get(29)).trim()));
				  assetVO.setDealerName((CommonFunction.checkNull(data.get(30)).trim()));
				  assetVO.setEngineNumber((CommonFunction.checkNull(data.get(31)).trim()));
				  assetVO.setChasisNumber((CommonFunction.checkNull(data.get(32)).trim()));
				  assetVO.setRegistrationNumber((CommonFunction.checkNull(data.get(33)).trim()));
				  assetVO.setEntity((CommonFunction.checkNull(data.get(34)).trim()));
			}else{
				 assetVO.setEntity((CommonFunction.checkNull(data.get(26)).trim()));
			   }
				  assetPolicy.add(assetVO);
	  		 }
	  		}
	  		}catch(Exception e){
	  			e.printStackTrace();
	  				}
	  		return  assetPolicy;	
	  		}
		public ArrayList<AssetForCMVO> assetVehicleDetails(AssetForCMVO assetMakervo, String lbxEntity)
		{
				   ArrayList<AssetForCMVO> assetVehicle=new ArrayList<AssetForCMVO>();
		     	   logger.info("In assetVehicleDetails...Dao Impl......");
		  	    try{
		  	    	  ArrayList searchlist=new ArrayList();
			  	      StringBuffer bufInsSql =	new StringBuffer();
			  	      			  
					  bufInsSql.append("SELECT DISTINCT ASSET_COLLATERAL_DESC,CASE ASSET_NEW_OLD WHEN 'N' THEN 'NEW' WHEN 'O' THEN 'OLD' END AS ASSET_NEW_OLD,ISNULL(VEHICLE_MAKE,MACHINE_MAKE),ISNULL(VEHICLE_MODEL,VEHICLE_MODEL), "+
								" DEALER_DESC,ISNULL(VEHICLE_ENGINE_NO,ENGINE_NUMBER),VEHICLE_CHASIS_NUMBER,VEHICLE_REGISTRATION_NO "+
								" FROM CR_ASSET_COLLATERAL_M "+
								" JOIN CR_DSA_DEALER_M ON CR_ASSET_COLLATERAL_M.ASSET_SUPPLIER=CR_DSA_DEALER_M.DEALER_ID "+
								" WHERE ASSET_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(lbxEntity).trim())+"' ");

		  			  	logger.info("In assetVehicleDetails forward......... query..........."+bufInsSql.toString());
		  			  	searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
		  			  	logger.info("assetVehicleDetails size is...."+searchlist.size());
		  			  	
		  		      for(int i=0;i<searchlist.size();i++){
			  		  logger.info("assetVehicleDetails List "+searchlist.get(i).toString());
			  		  ArrayList data=(ArrayList)searchlist.get(i);

		  		if(data.size()>0){
		  			AssetForCMVO assetVO = new AssetForCMVO();
		  			
		  			  assetVO.setAssetDesc((CommonFunction.checkNull(data.get(0)).trim()));
					  assetVO.setAssetNature((CommonFunction.checkNull(data.get(1)).trim()));
					  assetVO.setAssetMake((CommonFunction.checkNull(data.get(2)).trim()));				  
					  assetVO.setAssetModel((CommonFunction.checkNull(data.get(3)).trim()));				 
					  assetVO.setDealerName((CommonFunction.checkNull(data.get(4)).trim()));				 
					  assetVO.setEngineNumber((CommonFunction.checkNull(data.get(5)).trim()));
					  assetVO.setChasisNumber((CommonFunction.checkNull(data.get(6)).trim()));
					  assetVO.setRegistrationNumber((CommonFunction.checkNull(data.get(7)).trim()));
					  assetVehicle.add(assetVO);
		  		 }
		  		}
		  		}catch(Exception e){
		  			e.printStackTrace();
		  				}
		  		return  assetVehicle;	
		  		}
		public ArrayList <AssetForCMVO>showAssetDataInGrid(AssetForCMVO assetMakervo)
		{
		      ArrayList<AssetForCMVO> assetList=new ArrayList<AssetForCMVO>();
		      logger.info("In showAssetDataInGrid...Dao Impl.");
		    
		try{
			  ArrayList searchlist=new ArrayList();
			  ArrayList data = new ArrayList();  
			  logger.info("In searchAssetData.................."+assetMakervo);
			  StringBuffer bufInsSql = new StringBuffer();	
			  String entityType=CommonFunction.checkNull(assetMakervo.getEntityType());

				bufInsSql.append("Select distinct Top 1 CAI.COVER_NOTE,CAI.POLICY_NO,CAI.INSURANCE_CO_ID,A.AGENCY_NAME,CAI.INSURANCE_BY,"+
	  			" CAI.SUM_ASSURED,CAI.PREMIUM_AMOUNT,dbo.date_format(CAI.START_DATE,'"+dateFormat+"'), " +
	  			" dbo.date_format(CAI.END_DATE,'"+dateFormat+"'),CAI.APPROVAL_REMARKS, CAI.LOAN_ID,CLD.LOAN_NO,GCM.CUSTOMER_NAME, "+
	  			"  CAC.ASSET_ID,CAC.ASSET_COLLATERAL_DESC,CAC.ASSET_COLLATERAL_VALUE,CAI.RAISE_ADVICE_FLAG,CAI.ASSET_INSURANCE_ID,REMARKS,CAI.YEAR_NO, "+
	  			" CASE CAI.ENTITY_TYPE  WHEN 'PRAPPL' THEN 'APPLICANT' WHEN 'COAPPL' "+ 
	  			" THEN 'CO-APPLICANT' WHEN 'GUARANTOR' THEN 'GUARANTOR'   WHEN 'ASSET' THEN 'ASSET' WHEN 'COLLATERAL' THEN 'COLLATERAL' END AS ENTITY_TYPE,CAI.ENTITY_ID,CAI.SURRENDER_VALUE,CAI.PREMIUM_FREQUENCY,CAI.NOMINEE,CAI.RELATIONSHIP_WITH_NOMINEE,CAI.ENTITY_TYPE, ");
					
				if(CommonFunction.checkNull(assetMakervo.getEntityType()).equals("ASSET"))
				{
				bufInsSql.append(" CAC.ASSET_COLLATERAL_DESC,  " +
	  			" CASE ASSET_NEW_OLD WHEN 'N' THEN 'NEW' WHEN 'O' THEN 'OLD' END AS ASSET_NEW_OLD,ISNULL(VEHICLE_MAKE,MACHINE_MAKE),ISNULL(VEHICLE_MODEL,VEHICLE_MODEL),  "+
	  			" DEALER_DESC,ISNULL(VEHICLE_ENGINE_NO,ENGINE_NUMBER),VEHICLE_CHASIS_NUMBER,VEHICLE_REGISTRATION_NO,  ");
				}
		  if(CommonFunction.checkNull(assetMakervo.getEntityType()).equals("ASSET") || CommonFunction.checkNull(assetMakervo.getEntityType()).equals("COLLATERAL"))
		  {
				bufInsSql.append(" m.ASSET_COLLATERAL_DESC ");
		  }else{
  				bufInsSql.append(" m.customer_name ");
		  	}
				bufInsSql.append("  from cr_asset_collateral_m CAC "+
	  			" JOIN cr_asset_insurance_dtl CAI ON CAC.ASSET_ID=CAI.ENTITY_ID  "+
	  			" JOIN cr_loan_dtl CLD on CAI.LOAN_ID=CLD.LOAN_ID "+
	 			" JOIN com_agency_m A on A.AGENCY_CODE=CAI.INSURANCE_CO_ID "+
	 			" JOIN CR_DSA_DEALER_M CDDM ON CAC.ASSET_SUPPLIER=CDDM.DEALER_ID "+
				" JOIN gcd_customer_m GCM  on CLD.LOAN_CUSTOMER_ID=GCM.CUSTOMER_ID ");
				
		  if(CommonFunction.checkNull(assetMakervo.getEntityType()).equals("ASSET") || CommonFunction.checkNull(assetMakervo.getEntityType()).equals("COLLATERAL"))
		  {
  			  bufInsSql.append(" JOIN (SELECT Top 1  LOAN_ID, ASSET_COLLATERAL_DESC FROM cr_asset_insurance_dtl "+
				 " JOIN cr_asset_collateral_m ON cr_asset_collateral_m.ASSET_ID=cr_asset_insurance_dtl.ENTITY_ID "+
				 " AND cr_asset_insurance_dtl.ENTITY_TYPE IN ('ASSET','COLLATERAL') "+
				 " WHERE cr_asset_insurance_dtl.ASSET_INSURANCE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(assetMakervo.getAssetInsuranceId()).trim())+"' ) m "+
				 " on m.loan_id=CAI.LOAN_ID ");
			}else{
  			bufInsSql.append(" JOIN (SELECT Top 1   gcd_customer_m.CUSTOMER_NAME,cr_asset_insurance_dtl.LOAN_ID from cr_asset_insurance_dtl "+
		  		" JOIN  cr_loan_customer_role on cr_loan_customer_role.GCD_ID=cr_asset_insurance_dtl.ENTITY_ID AND  LOAN_CUSTOMER_ROLE_TYPE='"+CommonFunction.checkNull(assetMakervo.getEntityType())+"' "+
		  		" JOIN gcd_customer_m on gcd_customer_m.CUSTOMER_ID=cr_loan_customer_role.GCD_ID where  "+
		  		" ASSET_INSURANCE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(assetMakervo.getAssetInsuranceId()).trim())+"'  ) m "+
		  		" on m.loan_id=CAI.LOAN_ID  ");
		  	}		
		  	bufInsSql.append(" where ASSET_INSURANCE_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(assetMakervo.getAssetInsuranceId()).trim())+"'  ");
	
			  	logger.info("In showAssetDataInGrid......... query..........."+bufInsSql.toString());
			  	searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
		     	logger.info("showAssetDataInGrid size is...."+searchlist.size());

		      for(int i=0;i<searchlist.size();i++){
		    	  logger.info("showAssetDataInGrid List "+searchlist.get(i).toString());
		    	  data=(ArrayList)searchlist.get(i);

		if(data.size()>0){
			AssetForCMVO assetVO = new AssetForCMVO();
			
			assetVO.setCoverNoteNo((CommonFunction.checkNull(data.get(0)).trim()));
			  assetVO.setPolicyNo((CommonFunction.checkNull(data.get(1)).trim()));
			  assetVO.setLbxInsuranceAgency((CommonFunction.checkNull(data.get(2)).trim()));
			  
			  assetVO.setInsuranceAgency((CommonFunction.checkNull(data.get(3)).trim()));
			 
			  assetVO.setInsuranceDoneBy((CommonFunction.checkNull(data.get(4)).trim()));
			 
			  if(!CommonFunction.checkNull(data.get(5)).equalsIgnoreCase(""))	
				{
					Number SumAssured =myFormatter.parse((CommonFunction.checkNull(data.get(5))).trim());
					assetVO.setSumAssured(myFormatter.format(SumAssured));
				}
				  
			  if(!CommonFunction.checkNull(data.get(6)).equalsIgnoreCase(""))	
				{
					Number PremiumAmnt =myFormatter.parse((CommonFunction.checkNull(data.get(6))).trim());
					assetVO.setPremiumAmnt(myFormatter.format(PremiumAmnt));
				}
			  else {
				  assetVO.setPremiumAmnt("0");
			    }
			  assetVO.setStartDate((CommonFunction.checkNull(data.get(7)).trim()));
			  assetVO.setEndDate((CommonFunction.checkNull(data.get(8)).trim()));
			  assetVO.setAuthorRemarks((CommonFunction.checkNull(data.get(9)).trim()));
			  assetVO.setLbxLoanNoHID((CommonFunction.checkNull(data.get(10)).trim()));		   
		      assetVO.setLoanAccountNumber((CommonFunction.checkNull(data.get(11)).trim()));		      
		      assetVO.setCustomerName((CommonFunction.checkNull(data.get(12)).trim()));		     
			  assetVO.setAdviseFlag((CommonFunction.checkNull(data.get(16)).trim()));					 
			  assetVO.setRemarks((CommonFunction.checkNull(data.get(18)).trim()));
			  assetVO.setYearNo((CommonFunction.checkNull(data.get(19)).trim()));
			  assetVO.setEntityType((CommonFunction.checkNull(data.get(20)).trim()));
			  assetVO.setLbxEntity((CommonFunction.checkNull(data.get(21)).trim()));			  
			  assetVO.setSurrenderValue((CommonFunction.checkNull(data.get(22)).trim()));
			  assetVO.setPremiumFrequency((CommonFunction.checkNull(data.get(23)).trim()));
			  assetVO.setNominee((CommonFunction.checkNull(data.get(24)).trim()));
			  assetVO.setRelWithNominee((CommonFunction.checkNull(data.get(25)).trim()));
			  assetVO.setEntityTypeDesc((CommonFunction.checkNull(data.get(26)).trim()));
			  if(CommonFunction.checkNull(assetMakervo.getEntityType()).equals("ASSET"))
				{
			  assetVO.setAssetDesc((CommonFunction.checkNull(data.get(27)).trim()));
			  assetVO.setAssetNature((CommonFunction.checkNull(data.get(28)).trim()));
			  assetVO.setAssetMake((CommonFunction.checkNull(data.get(29)).trim()));
			  assetVO.setAssetModel((CommonFunction.checkNull(data.get(30)).trim()));
			  assetVO.setDealerName((CommonFunction.checkNull(data.get(31)).trim()));
			  assetVO.setEngineNumber((CommonFunction.checkNull(data.get(32)).trim()));
			  assetVO.setChasisNumber((CommonFunction.checkNull(data.get(33)).trim()));
			  assetVO.setRegistrationNumber((CommonFunction.checkNull(data.get(34)).trim()));
			  assetVO.setEntity((CommonFunction.checkNull(data.get(35)).trim()));
		}else{
			 assetVO.setEntity((CommonFunction.checkNull(data.get(27)).trim()));
		   }
			  assetList.add(assetVO);				
		 }

		}
		}catch(Exception e){
			e.printStackTrace();
				}
		return  assetList;	
		}
		public ArrayList<AssetForCMVO> getMaxYearNo(String loanId)
	{
		AssetForCMVO assetMakervo = new AssetForCMVO();
	    logger.info("in getAssetInsuranceViewerMaker() method DaoImpl****"+CommonFunction.checkNull(assetMakervo.getLoanId()));    	
    	ArrayList searchlist=new ArrayList();
    	ArrayList<AssetForCMVO> assetList=new ArrayList<AssetForCMVO>();
    	
    try{
    	String query=("select iif(MAX(CAI.YEAR_NO) IS NOT NULL ,max(CAI.YEAR_NO),0)  FROM CR_ASSET_COLLATERAL_M CAC JOIN CR_ASSET_INSURANCE_DTL CAI ON CAC.ASSET_ID=CAI.ENTITY_ID AND  CAI.REC_STATUS='A'JOIN CR_LOAN_DTL CLD ON CAI.LOAN_ID=CLD.LOAN_ID  JOIN COM_AGENCY_M A ON A.AGENCY_CODE=CAI.INSURANCE_CO_ID  JOIN GCD_CUSTOMER_M GCM  ON CLD.LOAN_CUSTOMER_ID=GCM.CUSTOMER_ID WHERE CAI.LOAN_ID='"+loanId+"' ");
    	   
    			logger.info("query for AssetInsuranceViewerMaker:::::::::"+query);
    			searchlist = ConnectionDAO.sqlSelect(query.toString());
	  		    logger.info("searchAssetData size is...."+searchlist.size());
	  		    	int size=searchlist.size();
	  		    	for(int i=0;i<size;i++){
  		    	  logger.info("searchAssetData List "+searchlist.get(i).toString());
  		    	  ArrayList data=(ArrayList)searchlist.get(i);

	  		if(data.size()>0){
	  			AssetForCMVO assetVO = new AssetForCMVO();				  			
	  		
	  			  assetVO.setYearDesc((CommonFunction.checkNull(data.get(0)).trim()));
	  			 assetList.add(assetVO);				
  	  		} 
  	  	}
    }
    	catch(Exception e){
    		e.printStackTrace();	    		
    	}
    	finally{
    		
    		loanId=null;
    		assetMakervo=null;
    	}
	return assetList;
}
		
		@Override
		public boolean docUploadExcel(HttpServletRequest request,
				FormFile myFile) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean uploadCersaiReport(String vFileName, String UserId) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
