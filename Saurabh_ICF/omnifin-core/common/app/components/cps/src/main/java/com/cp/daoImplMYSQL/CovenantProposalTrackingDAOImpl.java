/*   Author Name-      Abhishek Mathur 
     Date of creation -17/04/2017
     Purpose-         Convenant Tracking
 */
package com.cp.daoImplMYSQL;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import org.apache.commons.lang.StringEscapeUtils;

import com.cm.vo.LoanDetailForCMVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.cp.dao.CovenantProposalTrackingDAO;
import com.cp.vo.CovenantProposalTrackingVO;
import com.cp.vo.HeaderInfoVo;
import com.cp.vo.UnderwriterApprovalVo;

public class CovenantProposalTrackingDAOImpl implements CovenantProposalTrackingDAO {

	ResourceBundle				resource				= ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String						dateFormatWithTime		= resource.getString("lbl.dateWithTimeInDao");
	String						dateFormat				= resource.getString("lbl.dateInDao");
	int							no						= Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	private static final Logger	logger					= Logger.getLogger(CovenantProposalTrackingDAOImpl.class.getName());
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	
	@Override
	public ArrayList getCovenantTrackingData(CovenantProposalTrackingVO covTrackVo) {
		StringBuilder query = new StringBuilder();
		ArrayList List = new ArrayList();
		ArrayList finalList = new ArrayList();
		String chk = "";
		query.append("SELECT CT.COVENANT_ID,CT.COVENANT_DESC,date_format(CT.FIRST_DUE_DATE,'%d-%m-%Y'),CT.FREQUENCY,CT.TYPE,CT.COMMUNICATION_TYPE,CT.BENEFICIARIE,CT.GRACE_DAYS,"
				+ " CT.charge_code, "
				+ "	CT.REC_STATUS,CT.MAKER_ID,CT.MAKER_DATE,CT.AUTHOR_ID,CT.AUTHOR_DATE,CT.AUTHOR_REMARKS,ld.deal_no,gcd.customer_name,ct.deal_id, "
				+ " case CT.FREQUENCY when 'O' then 'ONE TIME CONDITION' when 'M' then 'MONTHLY' when 'B' then 'BIMONTHLY' when 'Q' then 'QUARTERLY' when 'H' then 'HALFYERALY' when 'Y' then 'YEARLY' end as FREQUENCY, "
				+ " case CT.TYPE when 'CS' then 'Condition Subsequent' when 'CP' then 'Condition Precedent' end as TYPE, case CT.COMMUNICATION_TYPE when 'E' then 'E-Mail' when 'S' then 'SMS' when 'B' then 'Both' end as COMMUNICATION_TYPE,CT.NON_COMPLIANCE_CHARGES,CAM.AGENCY_NAME,CT.SECURITY_COVER,CT.PRODUCT_ID,CPM.product_desc,CT.SUB_TYPE,CT.CATEGORY,date_format(CT.FACILITY_MATURITY_DATE,'%d-%m-%Y'),CT.COMMUNICATION_TYPE,CT.COV_AGENCY "
				+ " from cr_covenant_conditions_dtl CT "
				+ " join cr_deal_dtl ld on ld.deal_id = ct.deal_id "
				+ " join cr_Deal_customer_m gcd on gcd.customer_id = ld.deal_customer_id  "
				+" left join cr_product_m CPM on CPM.product_id=CT.product_id "
				+ " left join COM_AGENCY_M CAM on CAM.AGENCY_CODE=CT.COV_AGENCY "
				+ " where ct.rec_status = 'P' and ct.branch_id = '"+covTrackVo.getBranchId()+"' and ct.deal_id = '"+covTrackVo.getDealId()+"'");
		if(!CommonFunction.checkNull(covTrackVo.getId()).equalsIgnoreCase(""))
			query.append(" and ct.COVENANT_ID = '"+covTrackVo.getId()+"' ");
			
		logger.info("getCovenantTrackingData : "+query.toString());	
		
		try {
			List = ConnectionDAO.sqlSelect(query.toString());
			
			for (int i = 0; i < List.size(); i++) {
				ArrayList data = (ArrayList) List.get(i);
				if (data.size() > 0) {
					CovenantProposalTrackingVO vo = new CovenantProposalTrackingVO();
					chk = (CommonFunction.checkNull(data.get(0))).trim();	
					vo.setChk(chk);					
					vo.setCovDesc("<a href=covenantProposalTrackingAction.do?method=searchTrackingAction&id="+chk+"&dealId="+covTrackVo.getDealId()+"  >"
							+(CommonFunction.checkNull(data.get(1))).trim()+"</a>");				
					vo.setCoDesc((CommonFunction.checkNull(data.get(1))).trim());
					vo.setFirstDueDate((CommonFunction.checkNull(data.get(2))).trim());
					vo.setFrequency((CommonFunction.checkNull(data.get(3))).trim());
					vo.setType((CommonFunction.checkNull(data.get(4))).trim());
					vo.setComType((CommonFunction.checkNull(data.get(5))).trim());
					vo.setBeneficiaries((CommonFunction.checkNull(data.get(6))).trim());
					vo.setGraceDay((CommonFunction.checkNull(data.get(7))).trim());
					vo.setNonComplianceCharge((CommonFunction.checkNull(data.get(8))).trim());
					vo.setRecStatus((CommonFunction.checkNull(data.get(9))).trim());
					vo.setMakerId((CommonFunction.checkNull(data.get(10))).trim());
					vo.setMakerDate((CommonFunction.checkNull(data.get(11))).trim());
					vo.setAuthorId((CommonFunction.checkNull(data.get(12))).trim());
					vo.setAuthorDate((CommonFunction.checkNull(data.get(13))).trim());
					vo.setAthorRemarks((CommonFunction.checkNull(data.get(14))).trim());
					vo.setDealNo((CommonFunction.checkNull(data.get(15))).trim());
					vo.setCustomerName((CommonFunction.checkNull(data.get(16))).trim());
					vo.setDealId((CommonFunction.checkNull(data.get(17))).trim());
					if(!CommonFunction.checkNull(covTrackVo.getId()).equalsIgnoreCase(""))
						vo.setId((CommonFunction.checkNull(data.get(0))).trim());
					vo.setFrequencyDesc((CommonFunction.checkNull(data.get(18))).trim());
					vo.setTypeDesc((CommonFunction.checkNull(data.get(19))).trim());
					vo.setComTypeDesc((CommonFunction.checkNull(data.get(20))).trim());
					vo.setNonComplianceChargeDesc((CommonFunction.checkNull(data.get(21))).trim());
					vo.setLbxDealNoHID((CommonFunction.checkNull(data.get(17))).trim());
					vo.setCovAllUser((CommonFunction.checkNull(data.get(22))).trim());
					vo.setSecurityCover((CommonFunction.checkNull(data.get(23))).trim());
					vo.setLbxProduct((CommonFunction.checkNull(data.get(24))).trim());
					vo.setProduct((CommonFunction.checkNull(data.get(25))).trim());
					vo.setSubType((CommonFunction.checkNull(data.get(26))).trim());
					vo.setCategory((CommonFunction.checkNull(data.get(27))).trim());
					vo.setFacilityMaturityDate((CommonFunction.checkNull(data.get(28))).trim());
					vo.setCommunication((CommonFunction.checkNull(data.get(29))).trim());	
					vo.setLbxUserId((CommonFunction.checkNull(data.get(30))).trim());
					vo.setTotalRecordSize(List.size());
					finalList.add(vo);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			query = null;
			covTrackVo = null;
		}

		return finalList;
	}

	@Override
	public boolean saveCovenantTracking(CovenantProposalTrackingVO covTrackVo) {
		boolean status = false;
		ArrayList qryList = new ArrayList();
		ArrayList searchlist = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		try {
			
			
			String customerId = ConnectionDAO.singleReturn("select Deal_CUSTOMER_ID from cr_deal_dtl where deal_id = '"+covTrackVo.getDealId()+"' ");
			if(covTrackVo.getAllSelection().equalsIgnoreCase("S")){
			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql.append("insert into cr_covenant_conditions_dtl (DEAL_ID,LOAN_ID,CUSTOMER_ID,COVENANT_DESC,FIRST_DUE_DATE,NEXT_DUE_DATE,FREQUENCY,TYPE,"
					+ " BENEFICIARIE,GRACE_DAYS,NON_COMPLIANCE_CHARGES,REC_STATUS,BRANCH_ID,MAKER_DATE,MAKER_ID,COV_AGENCY,SECURITY_COVER,PRODUCT_ID,SUB_TYPE,CATEGORY,FACILITY_MATURITY_DATE,COMMUNICATION_TYPE,charge_code)");
			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?," );//DEAL_ID
			bufInsSql.append(" ?," );//LOAN_ID
			bufInsSql.append(" ?," );//CUSTOMER_ID
			bufInsSql.append(" ?," );//COVENANT_DESC
			bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND), " );//FIRST_DUE_DATE
			bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND), " );//NEXT_DUE_DATE
			bufInsSql.append(" ?," );//FREQUENCY
			bufInsSql.append(" ?," );//TYPE
			bufInsSql.append(" ?," );//BENEFICIARIE
			bufInsSql.append(" ?," );//GRACE_DAYS
			bufInsSql.append(" ?," );//NON_COMPLIANCE_CHARGES
			bufInsSql.append(" ?," );//REC_STATUS
			bufInsSql.append(" ?," );//BRANCH_ID
			bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND), " );//MAKER_DATE
			bufInsSql.append(" ?," );//MAKER_ID
			bufInsSql.append(" ?," );//COV_AGENCY
			bufInsSql.append(" ?," );//SECURITY_COVER
			bufInsSql.append(" ?," );//product_id
			bufInsSql.append(" ?," );//subType
			bufInsSql.append(" ?," );//CATEGORY
			bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND), " );//FACILITY MATURITY DATE
			bufInsSql.append(" ?," );//communication
			bufInsSql.append(" ?" );//charge_code
			bufInsSql.append("  )" );
			
			insertPrepStmtObject = new PrepStmtObject();

			if ((CommonFunction.checkNull(covTrackVo.getDealId())).trim().equalsIgnoreCase("")) // DEAL_ID
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((covTrackVo.getDealId()).trim()); // DEAL_ID			
		
				insertPrepStmtObject.addNull(); // LOAN_ID
			
			if ((CommonFunction.checkNull(customerId)).trim().equalsIgnoreCase("")) // customerId
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((customerId).trim()); // customerId
			
			if ((CommonFunction.checkNull(covTrackVo.getCovDesc())).trim().equalsIgnoreCase("")) // COVENANT_DESC
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((covTrackVo.getCovDesc()).trim()); // COVENANT_DESC
			
			if ((CommonFunction.checkNull(covTrackVo.getFirstDueDate())).trim().equalsIgnoreCase("")) // First_Due_Date
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((covTrackVo.getFirstDueDate()).trim()); // First_Due_Date
			
			if ((CommonFunction.checkNull(covTrackVo.getFirstDueDate())).trim().equalsIgnoreCase("")) // NEXT_Due_Date
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((covTrackVo.getFirstDueDate()).trim()); // NEXT_Due_Date

			if ((CommonFunction.checkNull(covTrackVo.getFrequency())).trim().equalsIgnoreCase("")) // FREQUENCY
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((covTrackVo.getFrequency()).trim()); // FREQUENCY
		
			if ((CommonFunction.checkNull(covTrackVo.getType())).trim().equalsIgnoreCase("")) // TYPE
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((covTrackVo.getType()).trim()); // TYPE
			
			
			
			if ((CommonFunction.checkNull(covTrackVo.getBeneficiaries())).trim().equalsIgnoreCase("")) // BENEFICIARIE
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((covTrackVo.getBeneficiaries()).trim()); // BENEFICIARIE
			
			if ((CommonFunction.checkNull(covTrackVo.getGraceDay())).trim().equalsIgnoreCase("")) // GRACE_DAYS
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addInt(Integer.parseInt(covTrackVo.getGraceDay())); // GRACE_DAYS
			
			if ((CommonFunction.checkNull(covTrackVo.getNonComplianceCharge())).trim().equalsIgnoreCase("")) // compliance charge
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(covTrackVo.getNonComplianceCharge()); // compliance charge
			
			insertPrepStmtObject.addString("P");// Rec_Status
			
			if ((CommonFunction.checkNull(covTrackVo.getBranchId())).trim().equalsIgnoreCase("")) // Branch_id
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((covTrackVo.getBranchId()).trim()); // Branch_id
			
			if ((CommonFunction.checkNull(covTrackVo.getbDate())).trim().equalsIgnoreCase("")) // MAKER_DATE
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((covTrackVo.getbDate()).trim()); // MAKER_DATE
		
			if ((CommonFunction.checkNull(covTrackVo.getMakerId())).trim().equalsIgnoreCase("")) // MAKER_ID
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((covTrackVo.getMakerId()).trim()); // MAKER_ID
			
			if ((CommonFunction.checkNull(covTrackVo.getLbxUserId())).trim().equalsIgnoreCase("")) // MAKER_ID
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((covTrackVo.getLbxUserId()).trim()); // MAKER_ID
			
			if ((CommonFunction.checkNull(covTrackVo.getSecurityCover())).trim().equalsIgnoreCase("")) // MAKER_ID
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((covTrackVo.getSecurityCover()).trim()); // MAKER_ID
			
			if ((CommonFunction.checkNull(covTrackVo.getLbxProduct())).trim().equalsIgnoreCase("")) // PRODUCT_ID
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((covTrackVo.getLbxProduct()).trim()); // PRODUCT_ID
			
			if ((CommonFunction.checkNull(covTrackVo.getSubType())).trim().equalsIgnoreCase("")) // SUB_TYPE
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((covTrackVo.getSubType()).trim()); // SUB_TYPE
			
			if ((CommonFunction.checkNull(covTrackVo.getCategory())).trim().equalsIgnoreCase("")) // category
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((covTrackVo.getCategory()).trim()); // category
			
			if ((CommonFunction.checkNull(covTrackVo.getFacilityMaturityDate())).trim().equalsIgnoreCase("")) // facility maturity date
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((covTrackVo.getFacilityMaturityDate()).trim()); // facility maturity date
			
			if ((CommonFunction.checkNull(covTrackVo.getCommunication())).trim().equalsIgnoreCase("")) // Communication
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((covTrackVo.getCommunication()).trim()); // Communication
			
			insertPrepStmtObject.addString("450"); // charge_code

						

			insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("IN SaveLoan() insert query1 ### " + insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("In saveCovenantTracking......................" + status);
			
			    if(status){
				status=saveCovenantUserMapping(covTrackVo);
			    }
			
			}else{
				
				StringBuffer sb=new StringBuffer();
				sb.append("select product_id from cr_product_m, cr_deal_loan_dtl where cr_product_m.product_id=cr_deal_loan_dtl.deal_product and deal_id='"+covTrackVo.getDealId()+"'");
				logger.info("Query: for getting products for deal is  "+sb.toString());
				searchlist = ConnectionDAO.sqlSelect(sb.toString());
				for (int i = 0; i < searchlist.size(); i++) {
					ArrayList data = (ArrayList) searchlist.get(i);
					if (data.size() > 0) {
						
						StringBuffer bufInsSql = new StringBuffer();
						bufInsSql.append("insert into cr_covenant_conditions_dtl (DEAL_ID,LOAN_ID,CUSTOMER_ID,COVENANT_DESC,FIRST_DUE_DATE,NEXT_DUE_DATE,FREQUENCY,TYPE,"
								+ " BENEFICIARIE,GRACE_DAYS,NON_COMPLIANCE_CHARGES,REC_STATUS,BRANCH_ID,MAKER_DATE,MAKER_ID,COV_AGENCY,SECURITY_COVER,PRODUCT_ID,SUB_TYPE,CATEGORY,FACILITY_MATURITY_DATE,COMMUNICATION_TYPE,charge_code)");
						bufInsSql.append(" values ( ");
						bufInsSql.append(" ?," );//DEAL_ID
						bufInsSql.append(" ?," );//LOAN_ID
						bufInsSql.append(" ?," );//CUSTOMER_ID
						bufInsSql.append(" ?," );//COVENANT_DESC
						bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND), " );//FIRST_DUE_DATE
						bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND), " );//NEXT_DUE_DATE
						bufInsSql.append(" ?," );//FREQUENCY
						bufInsSql.append(" ?," );//TYPE
						bufInsSql.append(" ?," );//BENEFICIARIE
						bufInsSql.append(" ?," );//GRACE_DAYS
						bufInsSql.append(" ?," );//NON_COMPLIANCE_CHARGES
						bufInsSql.append(" ?," );//REC_STATUS
						bufInsSql.append(" ?," );//BRANCH_ID
						bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND), " );//MAKER_DATE
						bufInsSql.append(" ?," );//MAKER_ID
						bufInsSql.append(" ?," );//COV_AGENCY
						bufInsSql.append(" ?," );//SECURITY_COVER
						bufInsSql.append(" ?," );//product_id
						bufInsSql.append(" ?," );//subType
						bufInsSql.append(" ?," );//CATEGORY
						bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND), " );//FACILITY MATURITY DATE
						bufInsSql.append(" ?," );//communication
						bufInsSql.append(" ?" );//charge_code

						bufInsSql.append("  )" );
						
						insertPrepStmtObject = new PrepStmtObject();

						if ((CommonFunction.checkNull(covTrackVo.getDealId())).trim().equalsIgnoreCase("")) // DEAL_ID
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((covTrackVo.getDealId()).trim()); // DEAL_ID			
					
							insertPrepStmtObject.addNull(); // LOAN_ID
						
						if ((CommonFunction.checkNull(customerId)).trim().equalsIgnoreCase("")) // customerId
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((customerId).trim()); // customerId
						
						if ((CommonFunction.checkNull(covTrackVo.getCovDesc())).trim().equalsIgnoreCase("")) // COVENANT_DESC
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((covTrackVo.getCovDesc()).trim()); // COVENANT_DESC
						
						if ((CommonFunction.checkNull(covTrackVo.getFirstDueDate())).trim().equalsIgnoreCase("")) // First_Due_Date
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((covTrackVo.getFirstDueDate()).trim()); // First_Due_Date
						
						if ((CommonFunction.checkNull(covTrackVo.getFirstDueDate())).trim().equalsIgnoreCase("")) // NEXT_Due_Date
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((covTrackVo.getFirstDueDate()).trim()); // NEXT_Due_Date

						if ((CommonFunction.checkNull(covTrackVo.getFrequency())).trim().equalsIgnoreCase("")) // FREQUENCY
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((covTrackVo.getFrequency()).trim()); // FREQUENCY
					
						if ((CommonFunction.checkNull(covTrackVo.getType())).trim().equalsIgnoreCase("")) // TYPE
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((covTrackVo.getType()).trim()); // TYPE
						
						
						
						if ((CommonFunction.checkNull(covTrackVo.getBeneficiaries())).trim().equalsIgnoreCase("")) // BENEFICIARIE
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((covTrackVo.getBeneficiaries()).trim()); // BENEFICIARIE
						
						if ((CommonFunction.checkNull(covTrackVo.getGraceDay())).trim().equalsIgnoreCase("")) // GRACE_DAYS
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addInt(Integer.parseInt(covTrackVo.getGraceDay())); // GRACE_DAYS
						
						if ((CommonFunction.checkNull(covTrackVo.getNonComplianceCharge())).trim().equalsIgnoreCase("")) // NonComplianceCharge
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(covTrackVo.getNonComplianceCharge()); // NonComplianceCharge
						
						insertPrepStmtObject.addString("P");// Rec_Status
						
						if ((CommonFunction.checkNull(covTrackVo.getBranchId())).trim().equalsIgnoreCase("")) // Branch_id
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((covTrackVo.getBranchId()).trim()); // Branch_id
						
						if ((CommonFunction.checkNull(covTrackVo.getbDate())).trim().equalsIgnoreCase("")) // MAKER_DATE
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((covTrackVo.getbDate()).trim()); // MAKER_DATE
					
						if ((CommonFunction.checkNull(covTrackVo.getMakerId())).trim().equalsIgnoreCase("")) // MAKER_ID
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((covTrackVo.getMakerId()).trim()); // MAKER_ID
						
						if ((CommonFunction.checkNull(covTrackVo.getLbxUserId())).trim().equalsIgnoreCase("")) // MAKER_ID
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((covTrackVo.getLbxUserId()).trim()); // MAKER_ID
						
						if ((CommonFunction.checkNull(covTrackVo.getSecurityCover())).trim().equalsIgnoreCase("")) // MAKER_ID
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((covTrackVo.getSecurityCover()).trim()); // MAKER_ID
						
						if (CommonFunction.checkNull(data.get(0)).toString().equalsIgnoreCase("")) // PRODUCT_ID
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(CommonFunction.checkNull(data.get(0)).toString()); // PRODUCT_ID
						
						if ((CommonFunction.checkNull(covTrackVo.getSubType())).trim().equalsIgnoreCase("")) // SUB_TYPE
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((covTrackVo.getSubType()).trim()); // SUB_TYPE
						
						if ((CommonFunction.checkNull(covTrackVo.getCategory())).trim().equalsIgnoreCase("")) // category
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((covTrackVo.getCategory()).trim()); // category
						
						if ((CommonFunction.checkNull(covTrackVo.getFacilityMaturityDate())).trim().equalsIgnoreCase("")) // facility maturity date
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((covTrackVo.getFacilityMaturityDate()).trim()); // facility maturity date
						
						if ((CommonFunction.checkNull(covTrackVo.getCommunication())).trim().equalsIgnoreCase("")) // Communication
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((covTrackVo.getCommunication()).trim()); // Communication
						
						insertPrepStmtObject.addString("450"); // charge_code

									
						insertPrepStmtObject.setSql(bufInsSql.toString());
						logger.info("IN SaveLoan() insert query1 ### " + insertPrepStmtObject.printQuery());
						qryList.add(insertPrepStmtObject);
						status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
						logger.info("In saveCovenantTracking......................" + status);
						        if(status){
								status=saveCovenantUserMapping(covTrackVo);
							    }
		
					}
					
				}
				
			}
			return status;
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			qryList = null;
			insertPrepStmtObject = null;
		}

		return false;
	}

	@Override
	public boolean updateCovenantTracking(CovenantProposalTrackingVO covTrackVo) {
		boolean status = false;
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		StringBuilder query = new StringBuilder();
		
		query.append("update cr_covenant_conditions_dtl set COVENANT_DESC=?, "
				+ " FIRST_DUE_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),NEXT_DUE_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND), FREQUENCY=?, TYPE=?, COMMUNICATION_TYPE=?, "
				+ " BENEFICIARIE=?,GRACE_DAYS=?,NON_COMPLIANCE_CHARGES=?,MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),COV_AGENCY=?,SECURITY_COVER=?,PRODUCT_ID=?,SUB_TYPE=?,CATEGORY=?,FACILITY_MATURITY_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),COMMUNICATION_TYPE=? "
						+ " where COVENANT_ID=? ");
		
		if ((CommonFunction.checkNull(covTrackVo.getCovDesc())).trim().equalsIgnoreCase("")) // COVENANT_DESC
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((covTrackVo.getCovDesc()).trim()); // COVENANT_DESC

		if ((CommonFunction.checkNull(covTrackVo.getFirstDueDate())).trim().equalsIgnoreCase("")) // FIRST_DUE_DATE
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((covTrackVo.getFirstDueDate()).trim()); // FIRST_DUE_DATE
		
		if ((CommonFunction.checkNull(covTrackVo.getFirstDueDate())).trim().equalsIgnoreCase("")) // Next_DUE_DATE
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((covTrackVo.getFirstDueDate()).trim()); // Next_DUE_DATE
		
		if ((CommonFunction.checkNull(covTrackVo.getFrequency())).trim().equalsIgnoreCase("")) // FREQUENCY
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((covTrackVo.getFrequency()).trim()); // FREQUENCY

		if ((CommonFunction.checkNull(covTrackVo.getType())).trim().equalsIgnoreCase("")) // TYPE
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((covTrackVo.getType()).trim()); // TYPE

		if ((CommonFunction.checkNull(covTrackVo.getComType())).trim().equalsIgnoreCase("")) // COMMUNICATION_TYPE
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((covTrackVo.getComType()).trim()); // COMMUNICATION_TYPE

		if ((CommonFunction.checkNull(covTrackVo.getBeneficiaries())).trim().equalsIgnoreCase("")) // BENEFICIARIE
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((covTrackVo.getBeneficiaries()).trim()); // BENEFICIARIE

		if ((CommonFunction.checkNull(covTrackVo.getGraceDay())).trim().equalsIgnoreCase("")) // GRACE_DAY
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addInt(Integer.parseInt(covTrackVo.getGraceDay())); // GRACE_DAY

		if ((CommonFunction.checkNull(covTrackVo.getNonComplianceCharge())).trim().equalsIgnoreCase("")) // NON_COMPLIANCE_CHARGES
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(covTrackVo.getNonComplianceCharge()); // NON_COMPLIANCE_CHARGES

		if ((CommonFunction.checkNull(covTrackVo.getMakerId())).trim().equalsIgnoreCase("")) // MAKER_ID
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((covTrackVo.getMakerId()).trim()); // MAKER_ID

		if ((CommonFunction.checkNull(covTrackVo.getbDate())).trim().equalsIgnoreCase("")) // MAKER_DATE
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((covTrackVo.getbDate()).trim()); // MAKER_DATE
		
		if ((CommonFunction.checkNull(covTrackVo.getLbxUserId())).trim().equalsIgnoreCase("")) // COV_AGENCY
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((covTrackVo.getLbxUserId()).trim()); // COV_AGENCY
		
		if ((CommonFunction.checkNull(covTrackVo.getSecurityCover())).trim().equalsIgnoreCase("")) // SECURITY_COVER
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((covTrackVo.getSecurityCover()).trim()); // SECURITY_COVER
		
		if ((CommonFunction.checkNull(covTrackVo.getLbxProduct())).trim().equalsIgnoreCase("")) // PRODUCT_ID
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((covTrackVo.getLbxProduct()).trim()); // PRODUCT_ID
		
		if ((CommonFunction.checkNull(covTrackVo.getSubType())).trim().equalsIgnoreCase("")) // SUB_TYPE
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((covTrackVo.getSubType()).trim()); // SUB_TYPE
		
		if ((CommonFunction.checkNull(covTrackVo.getCategory())).trim().equalsIgnoreCase("")) // CATEGORY
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((covTrackVo.getCategory()).trim()); // CATEGORY
		
		if ((CommonFunction.checkNull(covTrackVo.getFacilityMaturityDate())).trim().equalsIgnoreCase("")) // facility maturity date
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((covTrackVo.getFacilityMaturityDate()).trim()); // facility maturity date
		
		if ((CommonFunction.checkNull(covTrackVo.getCommunication())).trim().equalsIgnoreCase("")) // communication
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((covTrackVo.getCommunication()).trim()); // communication
		
		if ((CommonFunction.checkNull(covTrackVo.getId())).trim().equalsIgnoreCase("")) // ID
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((covTrackVo.getId()).trim()); // ID

		try {
			insertPrepStmtObject.setSql(query.toString());
			logger.info("IN updateCovenantTracking() insert query1 ### " + insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("In updateCovenantTracking......................" + status);
			
			    if(status){
			    	covTrackVo.setCovenantId(covTrackVo.getId());
				status=saveCovenantUserMapping(covTrackVo);
			    }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			qryList = null;
			insertPrepStmtObject = null;
		}

		return status;
	}

	@Override
	public ArrayList getCovenantTrackingDataSearch(CovenantProposalTrackingVO covTrackVo) {
		StringBuilder query = new StringBuilder();
		ArrayList List = new ArrayList();
		ArrayList finalList = new ArrayList();
		String dealId = "";
		query.append(" SELECT DISTINCT ld.deal_id,ld.deal_no,gcd.customer_name,b.branch_desc, "
				+ "case C.rec_status when 'A' then 'APPROVED' when 'X' then 'REJECTED' when 'F' then 'FORWARDED' when 'P' then 'PENDING' end as REC_STATUS "
				+ " FROM cr_covenant_conditions_dtl c "
				+ " JOIN cr_deal_dtl ld on ld.deal_id = c.deal_id "
				+ " join cr_deal_customer_m gcd on gcd.customer_id = ld.deal_customer_id   "				
				+ " join com_branch_m b on b.branch_id = ld.deal_branch  "
				+ " where ld.rec_status = 'F' and c.branch_id = '"+covTrackVo.getBranchId()+"'  and C.MAKER_ID = '"+covTrackVo.getMakerId()+"'  and c.rec_status = 'P' "); 
		if(!CommonFunction.checkNull(covTrackVo.getDealId()).equalsIgnoreCase(""))
			query.append(" and c.deal_ID = '"+covTrackVo.getDealId()+"' ");
		
		logger.info("query Of Covenant Tracking : "+query.toString());
			
		try {
			List = ConnectionDAO.sqlSelect(query.toString());
			logger.info("getBranchList " + List.size());
			for (int i = 0; i < List.size(); i++) {
				ArrayList data = (ArrayList) List.get(i);
				if (data.size() > 0) {
					CovenantProposalTrackingVO vo = new CovenantProposalTrackingVO();
					dealId = CommonFunction.checkNull(data.get(0)).trim();
					vo.setDealNo("<a href=covenantProposalTrackingAction.do?method=OpenCovenantTracking&dealId="+dealId+"  >"
							+(CommonFunction.checkNull(data.get(1))).trim()+"</a>");
					vo.setCustomerName((CommonFunction.checkNull(data.get(2))).trim());					
					vo.setDealBranch((CommonFunction.checkNull(data.get(3))).trim());				
					vo.setDealStatus((CommonFunction.checkNull(data.get(4))).trim());
					vo.setTotalRecordSize(List.size());
					finalList.add(vo);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			query = null;
			covTrackVo = null;
		}

		return finalList;
	}

	@Override
	public boolean deleteCovenantTrackingData(String recordId,String dealId) {
		boolean status=false;
		logger.info("deleteViabilityDtl");
		StringBuilder query=new StringBuilder();
		query.append("delete from cr_covenant_conditions_dtl where deal_id='"+dealId+"' and COVENANT_ID = '"+recordId+"' ");
		ArrayList list=new ArrayList();
		list.add(query);
		try {
			status=ConnectionDAO.sqlInsUpdDelete(list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			e=null;
		}
		finally
		{
			query=null;
			list.clear();
			list=null;
		}
		return status;
	}

	@Override
	public boolean forwardCovenantTrackingData(CovenantProposalTrackingVO covTrackVo) {
		StringBuilder queryUpdate=new StringBuilder();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList qryList = new ArrayList();
		boolean status = false;
		queryUpdate.append("UPDATE cr_covenant_conditions_dtl SET rec_status=? WHERE deal_id=? and rec_status =?");
		
		insertPrepStmtObject.addString("F");
		
		if((CommonFunction.checkNull(covTrackVo.getDealId())).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((covTrackVo.getDealId()).trim());
		
		insertPrepStmtObject.addString("P");
		
		insertPrepStmtObject.setSql(queryUpdate.toString());
		logger.info("IN forwardCovenantTrackingData() update query2 ### "+insertPrepStmtObject.printQuery());
		qryList.add(insertPrepStmtObject);
		try{
				status=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		}
		catch(Exception e){
			e.printStackTrace();
			e=null;
		}
		logger.info("In forwardCovenantTrackingData.........insert status:"+status);
		return status;
	}

	@Override
	public ArrayList getCovenantTrackingSearchAuthor(CovenantProposalTrackingVO covTrackVo) {
		StringBuilder query = new StringBuilder();
		ArrayList listSearch = null;
		ArrayList finalList = new ArrayList();
		String dealId = "";
		query.append(" SELECT DISTINCT ld.deal_id,ld.deal_no,gcd.customer_name,b.branch_desc, "
				+ "case c.rec_status when 'P' then 'PENDING' when 'X' then 'REJECTED' when 'F' then 'FORWARDED' when 'A' then 'APPROVED' end as REC_STATUS "
				+ " FROM cr_covenant_conditions_dtl c "
				+ " JOIN cr_deal_dtl ld on ld.deal_id = c.deal_id "
				+ " join cr_deal_customer_m gcd on gcd.customer_id = ld.deal_customer_id   "				
				+ " join com_branch_m b on b.branch_id = ld.deal_branch  "
				+ " where ld.rec_status = 'F' and c.branch_id = '"+covTrackVo.getBranchId()+"' and C.MAKER_ID <> '"+covTrackVo.getMakerId()+"' and c.rec_status = 'F' ");  
		if(!CommonFunction.checkNull(covTrackVo.getDealId()).equalsIgnoreCase(""))
			query.append(" and c.deal_ID = '"+covTrackVo.getDealId()+"' ");
		logger.info("getCovenantTrackingSearchAuthor : "+query.toString());	
			
		try {
			listSearch = ConnectionDAO.sqlSelect(query.toString());
			logger.info("getBranchList " + listSearch.size());
			for (int i = 0; i < listSearch.size(); i++) {
				ArrayList data = (ArrayList) listSearch.get(i);
				if (data.size() > 0) {
					CovenantProposalTrackingVO vo = new CovenantProposalTrackingVO();
					dealId = CommonFunction.checkNull(data.get(0)).trim();
					vo.setDealNo("<a href=covenantProposalTrackingAction.do?method=OpenCovenantTrackingTabFram&dealId="+dealId+"  >"
							+(CommonFunction.checkNull(data.get(1))).trim()+"</a>");
					vo.setCustomerName((CommonFunction.checkNull(data.get(2))).trim());					
					vo.setDealBranch((CommonFunction.checkNull(data.get(3))).trim());				
					vo.setDealStatus((CommonFunction.checkNull(data.get(4))).trim());
					vo.setTotalRecordSize(listSearch.size());
					finalList.add(vo);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			query = null;
			covTrackVo = null;
		}

		return finalList;
	}

	@Override
	public ArrayList getCovenantTrackingDataSearchAuthor(CovenantProposalTrackingVO covTrackVo) {
		StringBuilder query = new StringBuilder();
		ArrayList List = new ArrayList();
		ArrayList finalList = new ArrayList();
		String dealId = "";
		query.append(" SELECT DISTINCT ld.deal_id,ld.deal_no,gcd.customer_name,b.branch_desc, "
				+ "case c.rec_status when 'P' then 'PENDING' when 'X' then 'REJECTED' when 'F' then 'FORWARDED' when 'A' then 'APPROVED' end as REC_STATUS "
				+ " FROM cr_covenant_conditions_dtl c "
				+ " JOIN cr_deal_dtl ld on ld.deal_id = c.deal_id "
				+ " join cr_deal_customer_m gcd on gcd.customer_id = ld.deal_customer_id   "				
				+ " join com_branch_m b on b.branch_id = ld.deal_branch  "
				
				+ " where ld.rec_status = 'F' and c.branch_id = '"+covTrackVo.getBranchId()+"' and C.MAKER_ID <> '"+covTrackVo.getMakerId()+"' and c.rec_status = 'F' "); 
		if(!CommonFunction.checkNull(covTrackVo.getDealId()).equalsIgnoreCase(""))
			query.append(" and c.deal_ID = '"+covTrackVo.getDealId()+"' ");
		logger.info("getCovenantTrackingDataSearchAuthor : "+query.toString());	
		try {
			List = ConnectionDAO.sqlSelect(query.toString());
			logger.info("getBranchList " + List.size());
			for (int i = 0; i < List.size(); i++) {
				ArrayList data = (ArrayList) List.get(i);
				if (data.size() > 0) {					
					CovenantProposalTrackingVO vo = new CovenantProposalTrackingVO();
					dealId = CommonFunction.checkNull(data.get(0)).trim();
					vo.setDealNo("<a href=covenantProposalTrackingAction.do?method=OpenCovenantTrackingTabFram&dealId="+dealId+"  >"
							+(CommonFunction.checkNull(data.get(1))).trim()+"</a>");
					vo.setCustomerName((CommonFunction.checkNull(data.get(2))).trim());					
					vo.setDealBranch((CommonFunction.checkNull(data.get(3))).trim());				
					vo.setDealStatus((CommonFunction.checkNull(data.get(4))).trim());
					vo.setTotalRecordSize(List.size());
					finalList.add(vo);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			query = null;
			covTrackVo = null;
		}

		return finalList;
	}

	@Override
	public boolean getCovTrackDecision(CovenantProposalTrackingVO covTrackVo) {
		boolean status = false;
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		StringBuilder query = new StringBuilder();

		query.append("update cr_covenant_conditions_dtl set REC_STATUS=?, "
				+ " AUTHOR_ID=?,AUTHOR_REMARKS=?,AUTHOR_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) "
						+ " where deal_ID=? ");
		
		if ((CommonFunction.checkNull(covTrackVo.getDecision())).trim().equalsIgnoreCase("")) // REC_STATUS
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((covTrackVo.getDecision()).trim()); // REC_STATUS

		if ((CommonFunction.checkNull(covTrackVo.getMakerId())).trim().equalsIgnoreCase("")) // AUTHOR_ID
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((covTrackVo.getMakerId()).trim()); // AUTHOR_ID
		
		if ((CommonFunction.checkNull(covTrackVo.getComments())).trim().equalsIgnoreCase("")) // AUTHOR_REMARKS
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((covTrackVo.getComments()).trim()); // AUTHOR_REMARKS
		
		if ((CommonFunction.checkNull(covTrackVo.getbDate())).trim().equalsIgnoreCase("")) // AUTHOR_DATE
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((covTrackVo.getbDate()).trim()); // AUTHOR_DATE

		if ((CommonFunction.checkNull(covTrackVo.getDealId())).trim().equalsIgnoreCase("")) // LOAN_ID
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((covTrackVo.getDealId()).trim()); // LOAN_ID

		try {
			insertPrepStmtObject.setSql(query.toString());
			logger.info("IN updateCovenantTracking() insert query1 ### " + insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("In updateCovenantTracking......................" + status);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			qryList = null;
			insertPrepStmtObject = null;
		}

		return status;
	}

	@Override
	public ArrayList getCovenantTrackingDataAuthor(CovenantProposalTrackingVO covTrackVo) {
		StringBuilder query = new StringBuilder();
		ArrayList List = new ArrayList();
		ArrayList finalList = new ArrayList();
		String chk = "";
		query.append("SELECT CT.COVENANT_ID,CT.COVENANT_DESC,date_format(CT.FIRST_DUE_DATE,'%d-%m-%Y'),CT.FREQUENCY,CT.TYPE,CT.COMMUNICATION_TYPE,CT.BENEFICIARIE,CT.GRACE_DAYS,"
				+ " CT.CHARGE_CODE, "
				+ "	CT.REC_STATUS,CT.MAKER_ID,CT.MAKER_DATE,CT.AUTHOR_ID,CT.AUTHOR_DATE,CT.AUTHOR_REMARKS,ld.deal_no,gcd.customer_name,ct.deal_id, "
				+ " case CT.FREQUENCY when 'O' then 'ONE TIME CONDITION' when 'M' then 'MONTHLY' when 'B' then 'BIMONTHLY' when 'Q' then 'QUARTERLY' when 'H' then 'HALFYERALY' when 'Y' then 'YEARLY' end as FREQUENCY, "
				+ " case CT.TYPE when 'CS' then 'Condition Subsequent' when 'CP' then 'Condition Precedent' end as TYPE, case CT.COMMUNICATION_TYPE when 'E' then 'E-Mail' when 'S' then 'SMS' when 'B' then 'Both' end as COMMUNICATION_TYPE,CT.NON_COMPLIANCE_CHARGES,CAM.AGENCY_NAME,CT.SECURITY_COVER,CT.PRODUCT_ID,CPM.product_desc,CT.SUB_TYPE,CT.CATEGORY,date_format(CT.FACILITY_MATURITY_DATE,'%d-%m-%Y'),CT.COMMUNICATION_TYPE "//COV_AGENCY added by Virender
				+ " from cr_covenant_conditions_dtl CT "
				+ " join cr_deal_dtl ld on ld.deal_id = ct.deal_id "
				+ " join cr_deal_customer_m gcd on gcd.customer_id = ld.deal_customer_id  "
				+ " left join com_charges_m CM on CM.CHARGE_ID = CT.CHARGE_CODE"
				+"  left join cr_product_m CPM on CPM.product_id=CT.product_id "
				+ " left join COM_AGENCY_M CAM on CAM.AGENCY_CODE=CT.COV_AGENCY "
				+ " where ct.rec_status = 'F' and ct.branch_id = '"+covTrackVo.getBranchId()+"' and ct.deal_id = '"+covTrackVo.getDealId()+"'");
		if(!CommonFunction.checkNull(covTrackVo.getId()).equalsIgnoreCase(""))
			query.append(" and ct.COVENANT_ID = '"+covTrackVo.getId()+"' ");
		logger.info("getCovenantTrackingDataAuthor : "+query.toString());		
			
		try {
			List = ConnectionDAO.sqlSelect(query.toString());
			logger.info("getBranchList " + List.size());
			for (int i = 0; i < List.size(); i++) {
				ArrayList data = (ArrayList) List.get(i);
				if (data.size() > 0) {
					CovenantProposalTrackingVO vo = new CovenantProposalTrackingVO();
					chk = (CommonFunction.checkNull(data.get(0))).trim();
					vo.setChk("<input type=\"radio\" id=\"chk"+i+"\" name=\"chk\" value=\""+chk+"\" />");
					vo.setCovDesc("<a href=covenantProposalTrackingAction.do?method=getConvTrackMakerDetail&id="+ chk+"&dealId="+covTrackVo.getDealId()+"  >"
							+(CommonFunction.checkNull(data.get(1))).trim()+"</a>");
					vo.setCoDesc((CommonFunction.checkNull(data.get(1))).trim());
					vo.setFirstDueDate((CommonFunction.checkNull(data.get(2))).trim());
					vo.setFrequency((CommonFunction.checkNull(data.get(3))).trim());
					vo.setType((CommonFunction.checkNull(data.get(4))).trim());
					vo.setComType((CommonFunction.checkNull(data.get(5))).trim());
					vo.setBeneficiaries((CommonFunction.checkNull(data.get(6))).trim());
					vo.setGraceDay((CommonFunction.checkNull(data.get(7))).trim());
					vo.setNonComplianceCharge((CommonFunction.checkNull(data.get(8))).trim());
					vo.setRecStatus((CommonFunction.checkNull(data.get(9))).trim());
					vo.setMakerId((CommonFunction.checkNull(data.get(10))).trim());
					vo.setMakerDate((CommonFunction.checkNull(data.get(11))).trim());
					vo.setAuthorId((CommonFunction.checkNull(data.get(12))).trim());
					vo.setAuthorDate((CommonFunction.checkNull(data.get(13))).trim());
					vo.setAthorRemarks((CommonFunction.checkNull(data.get(14))).trim());
					vo.setDealNo((CommonFunction.checkNull(data.get(15))).trim());
					vo.setCustomerName((CommonFunction.checkNull(data.get(16))).trim());
					vo.setDealId((CommonFunction.checkNull(data.get(17))).trim());
					if(!CommonFunction.checkNull(covTrackVo.getId()).equalsIgnoreCase(""))
						vo.setId((CommonFunction.checkNull(data.get(0))).trim());
					vo.setFrequencyDesc((CommonFunction.checkNull(data.get(18))).trim());
					vo.setTypeDesc((CommonFunction.checkNull(data.get(19))).trim());
					vo.setComTypeDesc((CommonFunction.checkNull(data.get(20))).trim());
					vo.setNonComplianceCharge((CommonFunction.checkNull(data.get(21))).trim());
					vo.setCovAllUser((CommonFunction.checkNull(data.get(22))).trim());
					vo.setSecurityCover((CommonFunction.checkNull(data.get(23))).trim());
					vo.setLbxProduct((CommonFunction.checkNull(data.get(24))).trim());
					vo.setProduct((CommonFunction.checkNull(data.get(25))).trim());
					vo.setSubType((CommonFunction.checkNull(data.get(26))).trim());
					vo.setCategory((CommonFunction.checkNull(data.get(27))).trim());
					vo.setFacilityMaturityDate((CommonFunction.checkNull(data.get(28))).trim());
					vo.setCommunication((CommonFunction.checkNull(data.get(29))).trim());
					vo.setTotalRecordSize(List.size());
					finalList.add(vo);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			query = null;
			covTrackVo = null;
		}

		return finalList;
	}

	@Override
	public ArrayList getCovenantTrackingSearchDetails(CovenantProposalTrackingVO covTrackVo) {
		StringBuilder query = new StringBuilder();
		ArrayList List = new ArrayList();
		ArrayList finalList = new ArrayList();
		String dealId = "";
		query.append(" SELECT distinct ld.deal_no,gcd.customer_name,c.deal_id,"
				+ " case ld.rec_status when 'A' then 'APPROVED' when 'X' then 'REJECTED' when 'F' then 'FORWARDED' end as REC_STATUS,CB.branch_desc "
				+ " FROM cr_covenant_conditions_dtl c "
				+ " JOIN cr_deal_dtl ld on ld.deal_id = c.deal_id "
				+ " join cr_deal_customer_m gcd on gcd.customer_id = ld.deal_customer_id "
				+ " JOIN cr_covenant_trk_dtl ctr ON CTR.COVENANT_ID = C.COVENANT_ID "
				+ " JOIN COM_BRANCH_M CB ON CB.BRANCH_ID = LD.deal_BRANCH "
				+"  join cr_covenant_user_mapping ccum on ccum.covenent_id=c.covenant_id and ccum.user_id='"+covTrackVo.getMakerId()+"'  "
				+ " where ld.rec_status = 'F' and c.branch_id = '"+covTrackVo.getBranchId()+"' and c.rec_status = 'A' AND CTR.STATUS is  NULL  " );
		

		if(!CommonFunction.checkNull(covTrackVo.getDealId()).equalsIgnoreCase(""))
			query.append(" and c.deal_ID = '"+covTrackVo.getDealId()+"' ");
		logger.info("getCovenantTrackingSearchDetails : "+query.toString());		

			
		try {
			List = ConnectionDAO.sqlSelect(query.toString());
			logger.info("getBranchList " + List.size());
			for (int i = 0; i < List.size(); i++) {
				ArrayList data = (ArrayList) List.get(i);
				if (data.size() > 0) {
					CovenantProposalTrackingVO vo = new CovenantProposalTrackingVO();
					dealId = CommonFunction.checkNull(data.get(2)).trim();
					vo.setDealNo("<a href=covenantProposalTrackingAction.do?method=OpenCovenantTrackingDetails&dealId="+dealId+"  >"
							+(CommonFunction.checkNull(data.get(0))).trim()+"</a>");
					vo.setCustomerName((CommonFunction.checkNull(data.get(1))).trim());
					vo.setDealStatus((CommonFunction.checkNull(data.get(3))).trim());
					vo.setDealBranch((CommonFunction.checkNull(data.get(4))).trim());
					vo.setTotalRecordSize(List.size());
					finalList.add(vo);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			query = null;
			covTrackVo = null;
		}

		return finalList;
	}

	@Override
	public ArrayList getCovenantTrackingDetailData(CovenantProposalTrackingVO covTrackVo) {
		StringBuilder query = new StringBuilder();
		ArrayList List = new ArrayList();
		ArrayList finalList = new ArrayList();
		String chk = "";
		
		/*
		query.append("SELECT CT.COVENANT_ID,CT.COVENANT_DESC,date_format(CT.FIRST_DUE_DATE,'%d-%m-%Y'),CT.FREQUENCY,CT.TYPE,CT.COMMUNICATION_TYPE,CT.BENEFICIARIE,CT.GRACE_DAYS,"
				+ " CT.CHARGE_CODE, "
				+ "	CT.REC_STATUS,CT.MAKER_ID,CT.MAKER_DATE,CT.AUTHOR_ID,CT.AUTHOR_DATE,CT.AUTHOR_REMARKS,ld.loan_no,gcd.customer_name,ct.loan_id, "
				+ " case CT.FREQUENCY when 'M' then 'MONTHLY' when 'B' then 'BIMONTHLY' when 'Q' then 'QUARTERLY' when 'H' then 'HALFYERALY' when 'Y' then 'YEARLY' end as FREQUENCY, "
				+ " case CT.TYPE when 'CS' then 'Condition Subsequent' when 'CP' then 'Condition Precedent' end as TYPE, case CT.COMMUNICATION_TYPE when 'E' then 'E-Mail' when 'S' then 'SMS' when 'B' then 'Both' end as COMMUNICATION_TYPE,"
				+ " COVENANT_REMARKS , COVENANT_DECISION,CCM.CHARGE_DESC, "
				+ " case CT.COVENANT_DECISION when 'P' then 'Pending' when 'R' then 'Received' when 'D' then 'Defferred' when 'W' then 'Waived' end as COVENANT_DECISION, CT.COVENANT_REMARKS "
				+ " from cr_covenant_conditions_dtl CT "
				+ " join cr_loan_dtl ld on ld.loan_id = ct.loan_id "
				+ " join gcd_customer_m gcd on gcd.customer_id = ld.loan_customer_id  "
				+ " left join com_charges_m CM on CM.CHARGE_ID = CT.CHARGE_CODE"
				+ " left join com_charge_code_m CCM on CCM.CHARGE_CODE = CT.CHARGE_CODE"
				+ " where ct.rec_status = 'A' and ct.COVENANT_STATUS = 'P' and ct.branch_id = '"+covTrackVo.getBranchId()+"' and ct.loan_id = '"+covTrackVo.getLoanId()+"'");
		*/
		query.append(" SELECT CT.COVENANT_ID,CT.COVENANT_DESC,date_format(CT.FIRST_DUE_DATE,'%d-%m-%Y'),CT.FREQUENCY,CT.TYPE,CT.COMMUNICATION_TYPE, "
						+ " CT.BENEFICIARIE,CT.GRACE_DAYS, CT.NON_COMPLIANCE_CHARGES, CT.REC_STATUS,CT.MAKER_ID,CT.MAKER_DATE,CT.AUTHOR_ID,CT.AUTHOR_DATE,CT.AUTHOR_REMARKS,"
						+ " ld.deal_no,gcd.customer_name,ct.deal_id, "
						+ " case CT.FREQUENCY when 'M' then 'MONTHLY' when 'B' then 'BIMONTHLY' when 'Q' then 'QUARTERLY' when 'H' then 'HALFYERALY'  when 'Y' then 'YEARLY' end as FREQUENCY, "
						+ " case CT.TYPE when 'CS' then 'Condition Subsequent' when 'CP' then 'Condition Precedent' end as TYPE, "
						+ " case CT.COMMUNICATION_TYPE when 'E' then 'E-Mail' when 'S' then 'SMS' when 'B' then 'Both' end as COMMUNICATION_TYPE, "
						+ " CTR.REMARKS , CTR.STATUS,CCM.CHARGE_DESC, "
						+ " case CTR.STATUS when 'P' then 'Pending' when 'R' then 'Received' when 'D' then 'Defferred' when 'W' then 'Waived' end as COVENANT_DECISION, CTR.REMARKS, ctr.cov_trk_id,CT.SECURITY_COVER,CT.PRODUCT_ID,CPM.product_desc,CT.CATEGORY,CT.SUB_TYPE,date_format(CT.FACILITY_MATURITY_DATE,'%d-%m-%Y'),CAM.AGENCY_NAME  "
						+ " from cr_covenant_conditions_dtl CT "
						+ " join cr_deal_dtl ld on ld.deal_id = ct.deal_id  "
						+ " join cr_deal_customer_m gcd on gcd.customer_id = ld.deal_customer_id "
						+ " left join com_charges_m CM on CM.CHARGE_ID = CT.CHARGE_CODE "
						+ " left join com_charge_code_m CCM on CCM.CHARGE_CODE = CT.CHARGE_CODE "
						+ " JOIN cr_covenant_trk_dtl ctr ON CTR.COVENANT_ID = CT.COVENANT_ID "
						+" left join cr_product_m CPM on CPM.product_id=CT.product_id "
						+ " left join COM_AGENCY_M CAM on CAM.AGENCY_CODE=CT.COV_AGENCY "
						+ " where ct.rec_status = 'A' and ct.branch_id = '"+covTrackVo.getBranchId()+"' and ct.deal_id = '"+covTrackVo.getDealId()+"' and CT.COVENANT_ID='"+covTrackVo.getId()+"' ");
		

		
				if(!CommonFunction.checkNull(covTrackVo.getCovenantTrackId()).equalsIgnoreCase(""))
					query.append(" and ctr.cov_trk_id = '"+covTrackVo.getCovenantTrackId()+"' ");
		
				logger.info("getCovenantTrackingDetailData : "+query.toString());		

			
		try {
			List = ConnectionDAO.sqlSelect(query.toString());
			logger.info("getBranchList " + List.size());
			for (int i = 0; i < List.size(); i++) {
				ArrayList data = (ArrayList) List.get(i);
				if (data.size() > 0) {
					CovenantProposalTrackingVO vo = new CovenantProposalTrackingVO();
					chk = (CommonFunction.checkNull(data.get(0))).trim();
					vo.setChk("<input type=\"radio\" id=\"chk"+i+"\" name=\"chk\" value=\""+chk+"\" />");
					vo.setCovDesc("<a href=covenantProposalTrackingAction.do?method=OpenCovenantTrackingDetails&id="+ chk+"&dealId="+covTrackVo.getDealId()+"  >"
							+(CommonFunction.checkNull(data.get(1))).trim()+"</a>");
					vo.setCoDesc((CommonFunction.checkNull(data.get(1))).trim());
					vo.setFirstDueDate((CommonFunction.checkNull(data.get(2))).trim());
					vo.setFrequency((CommonFunction.checkNull(data.get(3))).trim());
					vo.setType((CommonFunction.checkNull(data.get(4))).trim());
					vo.setCommunication((CommonFunction.checkNull(data.get(5))).trim());
					vo.setBeneficiaries((CommonFunction.checkNull(data.get(6))).trim());
					vo.setGraceDay((CommonFunction.checkNull(data.get(7))).trim());
					vo.setNonComplianceCharge((CommonFunction.checkNull(data.get(8))).trim());
					vo.setRecStatus((CommonFunction.checkNull(data.get(9))).trim());
					vo.setMakerId((CommonFunction.checkNull(data.get(10))).trim());
					vo.setMakerDate((CommonFunction.checkNull(data.get(11))).trim());
					vo.setAuthorId((CommonFunction.checkNull(data.get(12))).trim());
					vo.setAuthorDate((CommonFunction.checkNull(data.get(13))).trim());
					vo.setAthorRemarks((CommonFunction.checkNull(data.get(14))).trim());
					vo.setDealNo((CommonFunction.checkNull(data.get(15))).trim());
					vo.setCustomerName((CommonFunction.checkNull(data.get(16))).trim());
					vo.setDealId((CommonFunction.checkNull(data.get(17))).trim());
					if(!CommonFunction.checkNull(covTrackVo.getId()).equalsIgnoreCase(""))
						vo.setId((CommonFunction.checkNull(data.get(26))).trim());
					vo.setFrequencyDesc((CommonFunction.checkNull(data.get(18))).trim());
					vo.setComTypeDesc((CommonFunction.checkNull(data.get(20))).trim());
					vo.setFinalRmrks((CommonFunction.checkNull(data.get(21))).trim());
					vo.setCovDcsn((CommonFunction.checkNull(data.get(22))).trim());
					vo.setNonComplianceCharge((CommonFunction.checkNull(data.get(23))).trim());
					vo.setCovDcsn((CommonFunction.checkNull(data.get(24))).trim());
					vo.setFinalRmrks((CommonFunction.checkNull(data.get(25))).trim());
					vo.setSecurityCover((CommonFunction.checkNull(data.get(27))).trim());
					vo.setLbxProduct((CommonFunction.checkNull(data.get(28))).trim());
					vo.setProduct((CommonFunction.checkNull(data.get(29))).trim());
					vo.setCategory((CommonFunction.checkNull(data.get(30))).trim());
					vo.setSubType((CommonFunction.checkNull(data.get(31))).trim());
					vo.setFacilityMaturityDate((CommonFunction.checkNull(data.get(32))).trim());
					vo.setCovAllUser((CommonFunction.checkNull(data.get(33))).trim());
					vo.setTotalRecordSize(List.size());
					finalList.add(vo);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			query = null;
			covTrackVo = null;
		}

		return finalList;
	}

	@Override
	public boolean saveCovenantDetails(CovenantProposalTrackingVO covTrackVo) {
		logger.info("covTrackVo.getCovTrkId() BasuDeo :"+covTrackVo.getCovTrkId());
		boolean status = false;
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		StringBuilder query = new StringBuilder();

		query.append("update cr_covenant_trk_dtl set STATUS=?, REMARKS=?,COMPLIED_BY=?,COMPLIED_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'), INTERVAL CURTIME() HOUR_SECOND) "
				+ " where deal_ID=? and COV_TRK_ID=?");
		
		if ((CommonFunction.checkNull(covTrackVo.getCovDcsn())).trim().equalsIgnoreCase("")) // COVENANT_DECISION
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((covTrackVo.getCovDcsn()).trim()); 

		if ((CommonFunction.checkNull(covTrackVo.getFinalRmrks())).trim().equalsIgnoreCase("")) // COVENANT_REMARKS
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((covTrackVo.getFinalRmrks()).trim()); 
		
		if ((CommonFunction.checkNull(covTrackVo.getMakerId())).trim().equalsIgnoreCase("")) // COMPLIED_USER_ID
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((covTrackVo.getMakerId()).trim()); 
		
		if ((CommonFunction.checkNull(covTrackVo.getbDate())).trim().equalsIgnoreCase("")) // COMPLIED_DATE
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((covTrackVo.getbDate()).trim());
		
		if ((CommonFunction.checkNull(covTrackVo.getDealId())).trim().equalsIgnoreCase("")) // LOAN_ID
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((covTrackVo.getDealId()).trim());
		
		if ((CommonFunction.checkNull(covTrackVo.getId())).trim().equalsIgnoreCase("")) // ID
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((covTrackVo.getId()).trim()); // ID
	
		try {
			insertPrepStmtObject.setSql(query.toString());
			logger.info("IN saveCovenantDetails() insert query1 ### " + insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("In saveCovenantDetails......................" + status);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			qryList = null;
			insertPrepStmtObject = null;
		}

		return status;
	}

	@Override
	public ArrayList getCovenantTrackingDetailDataOpen(CovenantProposalTrackingVO covTrackVo) {
		StringBuilder query = new StringBuilder();
		ArrayList List = new ArrayList();
		ArrayList finalList = new ArrayList();
		String chk = "";
		String covID = "";
		/*
		query.append("SELECT CT.COVENANT_ID,CT.COVENANT_DESC,date_format(CT.FIRST_DUE_DATE,'%d-%m-%Y'),CT.FREQUENCY,CT.TYPE,CT.COMMUNICATION_TYPE,CT.BENEFICIARIE,CT.GRACE_DAYS,"
				+ " CT.CHARGE_CODE, "
				+ "	CT.REC_STATUS,CT.MAKER_ID,CT.MAKER_DATE,CT.AUTHOR_ID,CT.AUTHOR_DATE,CT.AUTHOR_REMARKS,ld.loan_no,gcd.customer_name,ct.loan_id, "
				+ " case CT.FREQUENCY when 'M' then 'MONTHLY' when 'B' then 'BIMONTHLY' when 'Q' then 'QUARTERLY' when 'H' then 'HALFYERALY' when 'Y' then 'YEARLY' end as FREQUENCY, "
				+ " case CT.TYPE when 'CS' then 'Condition Subsequent' when 'CP' then 'Condition Precedent' end as TYPE, case CT.COMMUNICATION_TYPE when 'E' then 'E-Mail' when 'S' then 'SMS' when 'B' then 'Both' end as COMMUNICATION_TYPE,"
				+ " COVENANT_REMARKS , COVENANT_DECISION,CCM.CHARGE_DESC, "
				+ " case CT.COVENANT_DECISION when 'P' then 'Pending' when 'R' then 'Received' when 'D' then 'Defferred' when 'W' then 'Waived' end as COVENANT_DECISION, CT.COVENANT_REMARKS, date_format(CT.maker_date,'%d-%m-%Y') "
				+ " from cr_covenant_conditions_dtl CT "
				+ " join cr_loan_dtl ld on ld.loan_id = ct.loan_id "
				+ " join gcd_customer_m gcd on gcd.customer_id = ld.loan_customer_id  "
				+ " left join com_charges_m CM on CM.CHARGE_ID = CT.CHARGE_CODE"
				+ " left join com_charge_code_m CCM on CCM.CHARGE_CODE = CT.CHARGE_CODE"
				+ " where ct.rec_status = 'A' and ct.COVENANT_STATUS = 'P' AND ct.COVENANT_DECISION is NULL AND ct.branch_id = '"+covTrackVo.getBranchId()+"' and ct.loan_id = '"+covTrackVo.getLoanId()+"'");
		
		*/
		
		query.append(" SELECT distinct CT.COVENANT_ID,CT.COVENANT_DESC,date_format(CT.FIRST_DUE_DATE,'%d-%m-%Y'),CT.FREQUENCY,CT.TYPE,CT.COMMUNICATION_TYPE,CT.BENEFICIARIE,CT.GRACE_DAYS, "
					+" CT.CHARGE_CODE, "
					+" CT.REC_STATUS,CT.MAKER_ID,CT.MAKER_DATE,CT.AUTHOR_ID,CT.AUTHOR_DATE,CT.AUTHOR_REMARKS,ld.deal_no,gcd.customer_name,ct.deal_id, " 
					+" case CT.FREQUENCY when 'O' then 'ONE TIME CONDITION' when 'M' then 'MONTHLY' when 'B' then 'BIMONTHLY' when 'Q' then 'QUARTERLY' when 'H' then 'HALFYERALY' when 'Y' then 'YEARLY' end as FREQUENCY, " 
					+" case CT.TYPE when 'CS' then 'Condition Subsequent' when 'CP' then 'Condition Precedent' end as TYPE, case CT.COMMUNICATION_TYPE when 'E' then 'E-Mail' when 'S' then 'SMS' when 'B' then 'Both' end as COMMUNICATION_TYPE, "
					+" CTR.REMARKS , CTR.STATUS,CCM.CHARGE_DESC,  " 
					+" case CTR.STATUS when 'C' then 'Complied' when 'W' then 'Waived' end as COVENANT_DECISION, CTR.REMARKS, date_format(CTR.RAISED_ON,'%d-%m-%Y'), ctr.COV_TRK_ID,CT.SECURITY_COVER "
					+" from cr_covenant_conditions_dtl CT  "
					+" join cr_deal_Dtl ld on ld.deal_id = ct.deal_id " 
					+" join cr_deal_customer_m gcd on gcd.customer_id = ld.deal_customer_id "  
					+" left join com_charges_m CM on CM.CHARGE_ID = CT.CHARGE_CODE "
					+" left join com_charge_code_m CCM on CCM.CHARGE_CODE = CT.CHARGE_CODE "
					+" JOIN cr_covenant_trk_dtl ctr ON CTR.COVENANT_ID = CT.COVENANT_ID "
					+" JOIN COM_AGENCY_M AM ON AM.AGENCY_CODE = CT.COV_AGENCY "
					+" JOIN COM_AGENCY_USER_MAPPING AUM ON AUM.AGENCY_CODE = AM.AGENCY_CODE "
					+"  join cr_covenant_user_mapping ccum on ccum.covenent_id=ct.covenant_id and ccum.user_id='"+covTrackVo.getMakerId()+"'  "
					+" where ct.rec_status = 'A' AND CTR.STATUS is NULL AND ct.branch_id = '"+covTrackVo.getBranchId()+"' and ct.deal_id = '"+covTrackVo.getDealId()+"' " );
		
		
		if(!CommonFunction.checkNull(covTrackVo.getId()).equalsIgnoreCase(""))
			query.append(" and ctr.COV_TRK_ID = '"+covTrackVo.getId()+"' ");
		
		logger.info("getCovenantTrackingDetailDataOpen : "+query.toString());		
		try {
			List = ConnectionDAO.sqlSelect(query.toString());
			logger.info("getBranchList " + List.size());
			for (int i = 0; i < List.size(); i++) {
				ArrayList data = (ArrayList) List.get(i);
				if (data.size() > 0) {
					CovenantProposalTrackingVO vo = new CovenantProposalTrackingVO();
					chk = (CommonFunction.checkNull(data.get(27))).trim();
					covID  = (CommonFunction.checkNull(data.get(0))).trim();
					vo.setChk("<input type=\"radio\" id=\"chk"+i+"\" name=\"chk\" value=\""+chk+"\" />");
					vo.setCovDesc("<a href=covenantProposalTrackingAction.do?method=OpenCovenantTrackingDetails&id="+ chk+"&dealId="+covTrackVo.getDealId()+"&covID="+covID+"  >"
							+(CommonFunction.checkNull(data.get(1))).trim()+"</a>");
					vo.setCoDesc((CommonFunction.checkNull(data.get(1))).trim());
					vo.setFirstDueDate((CommonFunction.checkNull(data.get(2))).trim());
					vo.setFrequency((CommonFunction.checkNull(data.get(3))).trim());
					vo.setType((CommonFunction.checkNull(data.get(4))).trim());
					vo.setComType((CommonFunction.checkNull(data.get(5))).trim());
					vo.setBeneficiaries((CommonFunction.checkNull(data.get(6))).trim());
					vo.setGraceDay((CommonFunction.checkNull(data.get(7))).trim());
					vo.setNonComplianceCharge((CommonFunction.checkNull(data.get(8))).trim());
					vo.setRecStatus((CommonFunction.checkNull(data.get(9))).trim());
					vo.setMakerId((CommonFunction.checkNull(data.get(10))).trim());
					vo.setMakerDate((CommonFunction.checkNull(data.get(11))).trim());
					vo.setAuthorId((CommonFunction.checkNull(data.get(12))).trim());
					vo.setAuthorDate((CommonFunction.checkNull(data.get(13))).trim());
					vo.setAthorRemarks((CommonFunction.checkNull(data.get(14))).trim());
					vo.setDealNo((CommonFunction.checkNull(data.get(15))).trim());
					vo.setCustomerName((CommonFunction.checkNull(data.get(16))).trim());
					vo.setDealId((CommonFunction.checkNull(data.get(17))).trim());
					if(!CommonFunction.checkNull(covTrackVo.getId()).equalsIgnoreCase(""))
						vo.setId((CommonFunction.checkNull(data.get(27))).trim());
					vo.setFrequencyDesc((CommonFunction.checkNull(data.get(18))).trim());
					vo.setComTypeDesc((CommonFunction.checkNull(data.get(20))).trim());
					vo.setFinalRmrks((CommonFunction.checkNull(data.get(21))).trim());
					vo.setCovDcsn((CommonFunction.checkNull(data.get(22))).trim());
					vo.setNonComplianceCharge((CommonFunction.checkNull(data.get(23))).trim());
					vo.setCovDcsn((CommonFunction.checkNull(data.get(24))).trim());
					vo.setFinalRmrks((CommonFunction.checkNull(data.get(25))).trim());
					vo.setRaisedDate((CommonFunction.checkNull(data.get(26))).trim());
					vo.setSecurityCover((CommonFunction.checkNull(data.get(28))).trim());
					vo.setTotalRecordSize(List.size());
					finalList.add(vo);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			query = null;
			covTrackVo = null;
		}

		return finalList;
	}

	@Override
	public ArrayList getCovenantTrackingDetailDataComplied(CovenantProposalTrackingVO covTrackVo) {
		StringBuilder query = new StringBuilder();
		ArrayList List = new ArrayList();
		ArrayList finalList = new ArrayList();
		String chk = "";
		String covID = "";
		/*
		query.append("SELECT CT.COVENANT_ID,CT.COVENANT_DESC,date_format(CT.FIRST_DUE_DATE,'%d-%m-%Y'),CT.FREQUENCY,CT.TYPE,CT.COMMUNICATION_TYPE,CT.BENEFICIARIE,CT.GRACE_DAYS,"
				+ " CT.CHARGE_CODE, "
				+ "	CT.REC_STATUS,CT.MAKER_ID,CT.MAKER_DATE,CT.AUTHOR_ID,CT.AUTHOR_DATE,CT.AUTHOR_REMARKS,ld.loan_no,gcd.customer_name,ct.loan_id, "
				+ " case CT.FREQUENCY when 'M' then 'MONTHLY' when 'B' then 'BIMONTHLY' when 'Q' then 'QUARTERLY' when 'H' then 'HALFYERALY' when 'Y' then 'YEARLY' end as FREQUENCY, "
				+ " case CT.TYPE when 'CS' then 'Condition Subsequent' when 'CP' then 'Condition Precedent' end as TYPE, case CT.COMMUNICATION_TYPE when 'E' then 'E-Mail' when 'S' then 'SMS' when 'B' then 'Both' end as COMMUNICATION_TYPE,"
				+ " COVENANT_REMARKS , COVENANT_DECISION,CCM.CHARGE_DESC, "
				+ " case CT.COVENANT_DECISION when 'P' then 'Pending' when 'R' then 'Received' when 'D' then 'Defferred' when 'W' then 'Waived' end as COVENANT_DECISION, CT.COVENANT_REMARKS, "
				+ " CT.COMPLIED_USER_ID,CT.COMPLIED_DATE,date_format(CT.maker_date,'%d-%m-%Y') "
				+ " from cr_covenant_conditions_dtl CT "
				+ " join cr_loan_dtl ld on ld.loan_id = ct.loan_id "
				+ " join gcd_customer_m gcd on gcd.customer_id = ld.loan_customer_id  "
				+ " left join com_charges_m CM on CM.CHARGE_ID = CT.CHARGE_CODE"
				+ " left join com_charge_code_m CCM on CCM.CHARGE_CODE = CT.CHARGE_CODE"
				+ " where ct.rec_status = 'A' and ct.COVENANT_STATUS = 'P' AND ct.COVENANT_DECISION is not NULL AND ct.branch_id = '"+covTrackVo.getBranchId()+"' and ct.loan_id = '"+covTrackVo.getLoanId()+"'");
				*/
		query.append(" SELECT distinct CT.COVENANT_ID,CT.COVENANT_DESC,date_format(CT.FIRST_DUE_DATE,'%d-%m-%Y'),CT.FREQUENCY,CT.TYPE,CT.COMMUNICATION_TYPE,CT.BENEFICIARIE,CT.GRACE_DAYS, "
				+ " CT.CHARGE_CODE,  "
				+ " CT.REC_STATUS,CT.MAKER_ID,CT.MAKER_DATE,CT.AUTHOR_ID,CT.AUTHOR_DATE,CT.AUTHOR_REMARKS,ld.deal_no,gcd.customer_name,ct.deal_id, " 
				+ " case CT.FREQUENCY when 'O' then 'ONE TIME CONDITION' when 'M' then 'MONTHLY' when 'B' then 'BIMONTHLY' when 'Q' then 'QUARTERLY' when 'H' then 'HALFYERALY' when 'Y' then 'YEARLY' end as FREQUENCY, " 
				+ " case CT.TYPE when 'CS' then 'Condition Subsequent' when 'CP' then 'Condition Precedent' end as TYPE, case CT.COMMUNICATION_TYPE when 'E' then 'E-Mail' when 'S' then 'SMS' when 'B' then 'Both' end as COMMUNICATION_TYPE, "
				+ " CTR.REMARKS , CTR.STATUS,CCM.CHARGE_DESC,  "
				+ " case CTR.STATUS when 'C' then 'Complied' when 'W' then 'Waived' end as STATUS, CTR.REMARKS, " 
				+ " CTR.COMPLIED_BY,CTR.MAKER_DATE,date_format(CTR.RAISED_ON,'%d-%m-%Y'),ctr.COV_TRK_ID,CT.SECURITY_COVER "
				+ " from cr_covenant_conditions_dtl CT  "
				+ " join cr_deal_dtl ld on ld.deal_id = ct.deal_id " 
				+ " join cr_deal_customer_m gcd on gcd.customer_id = ld.deal_customer_id "  
				+ " left join com_charges_m CM on CM.CHARGE_ID = CT.CHARGE_CODE "
				+ " left join com_charge_code_m CCM on CCM.CHARGE_CODE = CT.CHARGE_CODE "
				+ " JOIN cr_covenant_trk_dtl ctr ON CTR.COVENANT_ID = CT.COVENANT_ID "
				+ " JOIN COM_AGENCY_M AM ON AM.AGENCY_CODE = CT.COV_AGENCY "
				+ " JOIN COM_AGENCY_USER_MAPPING AUM ON AUM.AGENCY_CODE = AM.AGENCY_CODE "
				+ " where ct.rec_status = 'A'  AND ctr.STATUS is not NULL AND ct.branch_id = '"+covTrackVo.getBranchId()+"' and ct.deal_id = '"+covTrackVo.getDealId()+"' "
				+ " AND CTR.COMPLIED_BY = '"+covTrackVo.getMakerId()+"' ");
		
		if(!CommonFunction.checkNull(covTrackVo.getCovenantTrackId()).equalsIgnoreCase(""))//Virender
			query.append(" and ctr.COV_TRK_ID = '"+covTrackVo.getCovenantTrackId()+"' ");//Virender
		
		logger.info("getCovenantTrackingDetailDataComplied : "+query.toString());		
			
		try {
			List = ConnectionDAO.sqlSelect(query.toString());
			logger.info("getBranchList " + List.size());
			for (int i = 0; i < List.size(); i++) {
				ArrayList data = (ArrayList) List.get(i);
				if (data.size() > 0) {
					CovenantProposalTrackingVO vo = new CovenantProposalTrackingVO();
					chk = (CommonFunction.checkNull(data.get(29))).trim();
					covID  = (CommonFunction.checkNull(data.get(0))).trim();
					vo.setChk("<input type=\"radio\" id=\"chk"+i+"\" name=\"chk\" value=\""+chk+"\" />");
					vo.setCovDesc("<a href=covenantProposalTrackingAction.do?method=OpenCovenantTrackingDetails&id="+ chk+"&dealId="+covTrackVo.getDealId()+"&covID="+covID+"  >"
							+(CommonFunction.checkNull(data.get(1))).trim()+"</a>");
					vo.setCoDesc((CommonFunction.checkNull(data.get(1))).trim());
					vo.setFirstDueDate((CommonFunction.checkNull(data.get(2))).trim());
					vo.setFrequency((CommonFunction.checkNull(data.get(3))).trim());
					vo.setType((CommonFunction.checkNull(data.get(4))).trim());
					vo.setComType((CommonFunction.checkNull(data.get(5))).trim());
					vo.setBeneficiaries((CommonFunction.checkNull(data.get(6))).trim());
					vo.setGraceDay((CommonFunction.checkNull(data.get(7))).trim());
					vo.setNonComplianceCharge((CommonFunction.checkNull(data.get(8))).trim());
					vo.setRecStatus((CommonFunction.checkNull(data.get(9))).trim());
					vo.setMakerId((CommonFunction.checkNull(data.get(10))).trim());
					vo.setMakerDate((CommonFunction.checkNull(data.get(11))).trim());
					vo.setAuthorId((CommonFunction.checkNull(data.get(12))).trim());
					vo.setAuthorDate((CommonFunction.checkNull(data.get(13))).trim());
					vo.setAthorRemarks((CommonFunction.checkNull(data.get(14))).trim());
					vo.setDealNo((CommonFunction.checkNull(data.get(15))).trim());
					vo.setCustomerName((CommonFunction.checkNull(data.get(16))).trim());
					vo.setDealId((CommonFunction.checkNull(data.get(17))).trim());
					if(!CommonFunction.checkNull(covTrackVo.getId()).equalsIgnoreCase(""))
						vo.setId((CommonFunction.checkNull(data.get(29))).trim());
					vo.setFrequencyDesc((CommonFunction.checkNull(data.get(18))).trim());
					vo.setComTypeDesc((CommonFunction.checkNull(data.get(20))).trim());
					vo.setFinalRmrks((CommonFunction.checkNull(data.get(21))).trim());
					vo.setCovDcsn((CommonFunction.checkNull(data.get(22))).trim());
					vo.setNonComplianceCharge((CommonFunction.checkNull(data.get(23))).trim());
					vo.setCovDcsnGrid((CommonFunction.checkNull(data.get(24))).trim());
					vo.setFinalRmrks((CommonFunction.checkNull(data.get(25))).trim());
					vo.setCompliedUser((CommonFunction.checkNull(data.get(26))).trim());
					vo.setCompliedDate((CommonFunction.checkNull(data.get(27))).trim());
					vo.setRaisedDate((CommonFunction.checkNull(data.get(28))).trim());
					vo.setSecurityCover((CommonFunction.checkNull(data.get(30))).trim());
					vo.setTotalRecordSize(List.size());
					finalList.add(vo);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			query = null;
			covTrackVo = null;
		}

		return finalList;
	}

	@Override
	public boolean saveCovenantUserMapping(CovenantProposalTrackingVO covTrackVo) {
		
		PrepStmtObject insertPrepMappObject= null;
		ArrayList updatelist = new ArrayList();
        boolean status=false;
		if(CommonFunction.checkNull(covTrackVo.getCovenantId()).equalsIgnoreCase("") ){
			String covenentId = ConnectionDAO.singleReturn("select max(covenant_id) from cr_covenant_conditions_dtl");
			covTrackVo.setCovenantId(covenentId);
		}else{
			deleteCovenantUserMappingData(covTrackVo.getCovenantId(),covTrackVo.getDealId());
		}
		
		for (int i = 0; i < covTrackVo.getUserMapping().length; i++) {
		StringBuffer bufInsSql = new StringBuffer();
		insertPrepMappObject=new PrepStmtObject();
		bufInsSql.append(" INSERT INTO CR_COVENANT_USER_MAPPING(COVENENT_ID,USER_ID,DEAL_ID,REC_STATUS,MAKER_ID ,MAKER_DATE) ");
		bufInsSql.append(" values ( ");
		bufInsSql.append(" ?," );//COVENENT_ID
		bufInsSql.append(" ?," );//USER_ID
		bufInsSql.append(" ?," );//DEAL_ID
		bufInsSql.append(" ?," );//REC_STATUS
		bufInsSql.append(" ?," );//MAKER_ID
		bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) " );//MAKER_DATE
		bufInsSql.append("  )" );

		
		if (CommonFunction.checkNull(covTrackVo.getCovenantId()).equalsIgnoreCase(""))
			insertPrepMappObject.addNull();
		else
			insertPrepMappObject.addString(covTrackVo.getCovenantId().trim());
		
		if (CommonFunction.checkNull(covTrackVo.getUserMapping()[i]).equalsIgnoreCase(""))
			insertPrepMappObject.addNull();
		else
			insertPrepMappObject.addString(covTrackVo.getUserMapping()[i].trim());
		
		if (CommonFunction.checkNull(covTrackVo.getDealId()).equalsIgnoreCase(""))
			insertPrepMappObject.addNull();
		else
			insertPrepMappObject.addString(covTrackVo.getDealId().trim());
		
		insertPrepMappObject.addString("A");

		if (CommonFunction.checkNull(covTrackVo.getMakerId()).equalsIgnoreCase(""))
			insertPrepMappObject.addNull();
		else
			insertPrepMappObject.addString(covTrackVo.getMakerId().trim());
		
		if (CommonFunction.checkNull(covTrackVo.getbDate()).equalsIgnoreCase(""))
			insertPrepMappObject.addNull();
		else
			insertPrepMappObject.addString(covTrackVo.getbDate().trim());
		
		insertPrepMappObject.setSql(bufInsSql.toString());
		updatelist.add(insertPrepMappObject);
		
		logger.info(insertPrepMappObject.printQuery());
		logger.info("In saveCovenantUserMapping" + bufInsSql.toString());
		try{
		status = ConnectionDAO.sqlInsUpdDeletePrepStmt(updatelist);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		insertPrepMappObject=null;
		bufInsSql=null;
		
		
		}
		
		return status;
	}




@Override
public boolean deleteCovenantUserMappingData(String covenantId,String deal) {
	boolean status=false;
	logger.info("deleteViabilityDtl");
	StringBuilder query=new StringBuilder();
	query.append("delete from CR_COVENANT_USER_MAPPING where deal_id='"+deal+"' and COVENENT_ID = '"+covenantId+"' ");
	ArrayList list=new ArrayList();
	list.add(query);
	try {
		status=ConnectionDAO.sqlInsUpdDelete(list);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		e=null;
	}
	finally
	{
		query=null;
		list.clear();
		list=null;
	}
	return status;
}


public ArrayList<CovenantProposalTrackingVO> searchCovenentUserMappingData(String  covenentId){
	
	ArrayList searchlist = new ArrayList();
	ArrayList<CovenantProposalTrackingVO> detailList = new ArrayList<CovenantProposalTrackingVO>();
	try {

		StringBuffer bufInsSql = new StringBuffer();
		
		logger.info("In searchAgencyDataMapping()...........");
		bufInsSql.append("select A.User_id,B.User_name from CR_COVENANT_USER_MAPPING A join SEC_USER_M B on A.USER_ID=B.USER_ID WHERE A.COVENENT_ID='"+covenentId+"'");

		searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
		
		logger.info("searchAgencyDataMapping list size" + searchlist.size());
		for (int i = 0; i < searchlist.size(); i++) {
			logger.info("searchAgencyDataMapping data  "	+ searchlist.get(i).toString());
			ArrayList data = (ArrayList) searchlist.get(i);

			if (data.size() > 0) {
				CovenantProposalTrackingVO mVo = new CovenantProposalTrackingVO();
				mVo.setLbxBeneficiaryId(CommonFunction.checkNull(data.get(0)).toString());
				mVo.setBeneficiaryDesc(CommonFunction.checkNull(data.get(1)).toString());
				detailList.add(mVo);
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}

	return detailList;

}

public boolean saveDefaultValue(String dealId,String userId,String businessDate, String businessType){
	StringBuilder query=new StringBuilder();
	PrepStmtObject insertPrepStmtObject=null;
	StringBuilder bufInsSql = null;
	ArrayList qryList = new ArrayList();
	String partner_id="";
	String percentage="";
	String rate="";
	boolean status=false;
	String countQuery="select count(1) from cr_business_partner_dtl where deal_id='"+dealId+"' and self_flag='Y'"; 
	
	
	
	logger.info("The count query is...."+countQuery);
	int count = Integer.parseInt(ConnectionDAO.singleReturn(countQuery));
	logger.info("The Value count is...."+count);
	if(count==0){
		
		String dealAmountQuery="select deal_loan_amount from cr_deal_loan_dtl where deal_id='"+dealId+"' limit 1" ;
		
		double dealAmount = Double.parseDouble((ConnectionDAO.singleReturn(dealAmountQuery)).trim());
		
		String selQuery="select partner_id, amount, rate from com_partner_details_m where SELF_FLAG='Y'";
		try{
			ArrayList list = ConnectionDAO.sqlSelect(selQuery);
			for(int i=0;i<list.size();i++){
				logger.info("list"+list.size());
				ArrayList list1=(ArrayList)list.get(i);
				if(list1.size()>0)
				{
					partner_id = CommonFunction.checkNull(list1.get(0)).trim();
					percentage = CommonFunction.checkNull(list1.get(1)).trim();
					rate = CommonFunction.checkNull(list1.get(2)).trim();
				}
			}
			double partnerAmount = dealAmount*Double.parseDouble(percentage)/100;
			
			bufInsSql = new StringBuilder();
			insertPrepStmtObject = new PrepStmtObject();
			insertPrepStmtObject = new PrepStmtObject();
			bufInsSql.append("insert into cr_business_partner_dtl(PARTNER_ID, DEAL_ID, EFFECTIVE_RATE, PERCENTAGE, MAKER_ID, MAKER_DATE, PARTNER_TYPE, SERVICE_PARTNER_FLAG, SELF_FLAG, LOAN_AMOUNT,LEAD_PARTNER_FLAG,RATE_TYPE)");
			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?,"); //PARTNER_ID
			bufInsSql.append(" ?,"); //LOAN_ID
			bufInsSql.append(" ?,"); //RATE
			bufInsSql.append(" ?,"); //PERCENTAGE
			bufInsSql.append(" ?,"); //MAKER_ID
			bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ,"); //MAKER_DATE
			bufInsSql.append(" ?,"); //PARTNER_TYPE
			bufInsSql.append(" ?,"); //SERVICE_PARTNER_FLAG
			bufInsSql.append(" ?,"); //SELF_FLAG
			bufInsSql.append(" ?,"); //LOAN_AMOUNT
			bufInsSql.append(" ?,"); //LEAD_PARTNER_FLAG
			bufInsSql.append(" 'E')"); //rate_type
			
			insertPrepStmtObject.addString(partner_id);
			insertPrepStmtObject.addString(dealId);
			insertPrepStmtObject.addString(rate);
			insertPrepStmtObject.addString(percentage);
			insertPrepStmtObject.addString(userId);
			insertPrepStmtObject.addString(businessDate);
			insertPrepStmtObject.addString(businessType);
			insertPrepStmtObject.addString("N");
			insertPrepStmtObject.addString("Y");
			insertPrepStmtObject.addFloat(partnerAmount);
			insertPrepStmtObject.addString("N");
			insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("The default partner insert query is...."+bufInsSql.toString());
			logger.info("IN insertCaseTypeMaster() insert query1 ### "+ insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			
		}catch (Exception e) {
			
		}
	}else{
		
	}
	return status;
}

public ArrayList getLoanHeader(String dealId) {
	ArrayList list=new ArrayList();
	try
	{
		StringBuilder query =new StringBuilder();
		 query.append(" select L.DEAL_ID, DEAL.DEAL_NO,DEAL.DEAL_NO,DEAL.GCD_CUSTOMER_ID,G.CUSTOMER_NAME,DATE_FORMAT(L.MAKER_DATE,'"+dateFormat+"'),p.PRODUCT_DESC,s.SCHEME_DESC,L.DEAL_PRODUCT_CATEGORY,L.DEAL_LOAN_AMOUNT from CR_DEAL_LOAN_DTL L  "+
				 		" JOIN CR_DEAL_DTL DEAL ON L.DEAL_ID = DEAL.DEAL_ID"+
				 		" left join cr_product_m p on L.DEAL_PRODUCT=p.PRODUCT_ID"+
						" left join cr_scheme_m s on L.DEAL_SCHEME=s.SCHEME_ID"+
						" left join cr_deal_customer_m G on G.CUSTOMER_ID=DEAL.GCD_CUSTOMER_ID"+
						" where L.DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+" limit 1");
		
		logger.info("getLoanHeader Query::::::::::::::::::::::::::::::::::::"+query);
		
		HeaderInfoVo vo= null;
		ArrayList header = ConnectionDAO.sqlSelect(query.toString());
		for(int i=0;i<header.size();i++){
			logger.info("header"+header.size());
			ArrayList header1=(ArrayList)header.get(i);
			if(header1!=null && header1.size()>0)
			{
				vo = new HeaderInfoVo();
				vo.setLoanId((CommonFunction.checkNull(header1.get(0))).trim());
				vo.setLoanNo((CommonFunction.checkNull(header1.get(1))).trim());
				vo.setDealLoanNo((CommonFunction.checkNull(header1.get(2))).trim());
				vo.setDealCustomerId((CommonFunction.checkNull(header1.get(3))).trim());
				vo.setLoanCustomerName((CommonFunction.checkNull(header1.get(4))).trim());
				vo.setLoanDate((CommonFunction.checkNull(header1.get(5))).trim());
				vo.setLoanProduct((CommonFunction.checkNull(header1.get(6))).trim());
				vo.setLoanScheme((CommonFunction.checkNull(header1.get(7))).trim());
				vo.setLoanProductCat((CommonFunction.checkNull(header1.get(8))).trim());
				vo.setLoanAmount((CommonFunction.checkNull(header1.get(9))).trim());
				list.add(vo);
				vo=null;
			}
		}
		query=null;
	}
	catch(Exception e)
	{
		e.printStackTrace();
		e=null;
	}finally{
		dealId=null;			
	}

	return list;

}

public ArrayList<LoanDetailForCMVO> getPartnerDetailsforPopUp(
		LoanDetailForCMVO loanDetailForCMVO, String dealId,
		String businessType) {
	ArrayList list=new ArrayList();
	String partnerType="";
	PrepStmtObject insertPrepStmtObject=null;
	StringBuilder bufInsSql = null;
	logger.info("businessType "+businessType);
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	
	try
	{
		StringBuilder query =new StringBuilder();
	    query.append(" select a.BP_ID,a.partner_ID,B.DESCRIPTION,a.deal_id,c.DEAL_NO,a.RATE ,round(ifnull(a.PERCENTAGE,0),2),case a.service_partner_flag when 'Y' then 'Yes' when 'N' then 'No' end as service_partner_flag,round(ifnull(loan_amount,0),2),case a.lead_partner_flag when 'Y' then 'Yes' when 'N' then 'No' end as leadflag,a.EFFECTIVE_RATE,a.RATE_TYPE,case ifnull(a.RATE_TYPE,'E') when 'E' then 'Effective' when 'F' then 'Flat' end as rate_type_desc  from cr_business_partner_dtl a "
	    		+ " join generic_master b on a.DEAL_PARTNER_ID=b.VALUE  left join cr_deal_dtl c on c.deal_id=a.deal_id "+
						" where a.DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+" and a.PARTNER_TYPE='"+businessType+"' ");
		
		logger.info("getLoanHeader Query::::::::::::::::::::::::::::::::::::"+query);
		
		LoanDetailForCMVO vo= null;
		ArrayList header = ConnectionDAO.sqlSelect(query.toString());
		for(int i=0;i<header.size();i++){
			logger.info("header"+header.size());
			ArrayList header1=(ArrayList)header.get(i);
			if(header1!=null && header1.size()>0)
			{
				vo = new LoanDetailForCMVO();
				vo.setId((CommonFunction.checkNull(header1.get(0))).trim());
				vo.setLbxpartnerId((CommonFunction.checkNull(header1.get(1))).trim());
				vo.setPartnerName((CommonFunction.checkNull(header1.get(2))).trim());
				vo.setLoanId((CommonFunction.checkNull(header1.get(3))).trim());
				vo.setLoanNo((CommonFunction.checkNull(header1.get(4))).trim());
				vo.setPartnerRate((CommonFunction.checkNull(header1.get(5))).trim());
				vo.setPartnerPercentage((CommonFunction.checkNull(header1.get(6))).trim());
				vo.setServicingPartnerFlag((CommonFunction.checkNull(header1.get(7))).trim());
				vo.setPartnerAmount((CommonFunction.checkNull(header1.get(8))).trim());
				vo.setLeadPartnerFlag((CommonFunction.checkNull(header1.get(9))).trim());
				if(!CommonFunction.checkNull(header1.get(10)).equalsIgnoreCase(""))	
				{
					Number reconNum =myFormatter.parse((CommonFunction.checkNull(header1.get(10))).trim());
					vo.setEffectiveRate(myFormatter.format(reconNum));
				}
				vo.setRateType((CommonFunction.checkNull(header1.get(11))).trim());
				vo.setRateTypeDesc((CommonFunction.checkNull(header1.get(12))).trim());
				list.add(vo);
				vo=null;
			}
		}
		query=null;
	}
	catch(Exception e)
	{
		e.printStackTrace();
		e=null;
	}finally{
		dealId=null;			
	}

	return list;

}

@Override
  public String savePartnerDetails(Object ob,String id,String businessType) {
	LoanDetailForCMVO vo = (LoanDetailForCMVO) ob;
	boolean status = false;
	String qry="";
	String qry1="";
	String qry2="";
	String qry3="";
	String qry4="";
	String qry5="";
	String result="";
	String flag="";
	String qry6="";
	String qry7="";
	logger.info("In savePartnerDetails.........inside ..Dao Impl");
	ArrayList qryList = new ArrayList();
	ArrayList rspQryList = new ArrayList();
	String stat = "X";
	int prevent = 0;
	int exist_id=0;
	int exist_id1=0;
	String loanRate="";
//	String partnerType="";
	PrepStmtObject insertPrepStmtObject=null;
	StringBuilder bufInsSql = null;
	//String businessType=vo.getBusinessType();
	logger.info("in savePartnerDetails businessType is :: "+businessType);
	//String query=" select FUNDING_TYPE from cr_loan_dtl where loan_id ='"+vo.getLoanId()+"'  ";
   	
			try {
			//partnerType=ConnectionDAO.singleReturn(query);	
			//logger.info("dealllll Query::::::::::::::::::::::::::::::::::::"+query);
			qry1="SELECT count(1) FROM cr_business_partner_dtl where DEAL_PARTNER_ID='"+vo.getPartnerName()+"' and  deal_id='"+vo.getLoanId()+"' and RATE='"+vo.getEffectiveRate()+"'and PERCENTAGE='"+vo.getPartnerPercentage()+"' and partner_type='"+businessType+"' and service_partner_flag = '"+vo.getServicingPartnerFlag()+"' and loan_amount='"+vo.getPartnerAmount()+"' and IFNULL(lead_partner_flag,'N') ='"+vo.getLeadPartnerFlag()+"' ";
			exist_id1=Integer.parseInt(ConnectionDAO.singleReturn(qry1));
			logger.info("userNameQ1::::"+exist_id1);
			logger.info("qry1 amanddd:::"+qry1);
			if(exist_id1>0){
				result="EX";
				return result;
			}
			if (CommonFunction.checkNull(vo.getLeadPartnerFlag()).equalsIgnoreCase("Y"))
			{
			qry7="SELECT count(1) FROM cr_business_partner_dtl where  deal_id='"+vo.getLoanId()+"'  and IFNULL(lead_partner_flag,'N') ='Y' and partner_type='"+businessType+"' and  DEAL_PARTNER_ID <>'"+vo.getPartnerName()+"' ";
			exist_id1=Integer.parseInt(ConnectionDAO.singleReturn(qry7));
			logger.info("userNameQ1::::"+exist_id1);
			logger.info("qry7:::"+qry7);
			if(exist_id1>0){
				result="leadPartnerExist";
				return result;
			}
			}
			qry3="SELECT ifnull(sum(percentage),0) FROM cr_business_partner_dtl where  deal_id='"+vo.getLoanId()+"' and partner_type='"+businessType+"'";
			 logger.info("the qry3 of count is : "+qry3);
			float sum =Float.parseFloat(ConnectionDAO.singleReturn(qry3));
			// add by saorabh
			/*qry2="SELECT count(1) FROM cr_business_partner_dtl where  LOAN_ID='"+vo.getLoanId()+"' and partner_type='"+businessType+"' and service_partner_flag='Y'";
			 logger.info("the qry2 of count is : "+qry2);
			prevent=Integer.parseInt(ConnectionDAO.singleReturn(qry2));
					logger.info("Prevent Service Partner::::"+prevent);
					if(prevent>0 && vo.getServicingPartnerFlag().equalsIgnoreCase("Y"))
					{
						return "prevent";
					}*/
					//parvez starts
					
					qry6="SELECT self_flag FROM com_partner_details_m where   DEAL_PARTNER_ID='"+vo.getPartnerName()+"' ";
					 logger.info("the self flag : "+qry6);
					flag =ConnectionDAO.singleReturn(qry6);
					
					//parvez ends
					
			//end by saorabh
			
			
			qry="SELECT count(1) FROM cr_business_partner_dtl where DEAL_PARTNER_ID='"+vo.getPartnerName()+"' and deal_id='"+vo.getLoanId()+"' and partner_type='"+businessType+"' ";
			 logger.info("the qry of count is : "+qry);
			exist_id=Integer.parseInt(ConnectionDAO.singleReturn(qry));
			
					logger.info("userNameQ1::::"+exist_id);
					logger.info("id for partner qry dtl::::"+id);
				//amandeep adds	
					if(vo.getServicingPartnerFlag().equalsIgnoreCase("Y"))
					{
						qry5="SELECT ifnull(deal_EFF_RATE,0) FROM cr_deal_loan_dtl where  deal_id='"+vo.getLoanId()+"' ";
						 logger.info("the qry5 of count is : "+qry5);
						 loanRate =ConnectionDAO.singleReturn(qry5);
					}
					//amandeep ends
						if(exist_id==0){
							
							qry2="SELECT count(1) FROM cr_business_partner_dtl where  deal_id='"+vo.getLoanId()+"' and partner_type='"+businessType+"' and service_partner_flag='Y'";
							 logger.info("the qry2 of count is : "+qry2);
							prevent=Integer.parseInt(ConnectionDAO.singleReturn(qry2));
									logger.info("Prevent Service Partner::::"+prevent);
						if(prevent>0 && vo.getServicingPartnerFlag().equalsIgnoreCase("Y"))
						{
							return "prevent";
						}
				sum = sum+Float.parseFloat(vo.getPartnerPercentage());
				if(sum>100.00)
				{
					return "contributionAmount";
				}
			
				
				
			bufInsSql = new StringBuilder();
			insertPrepStmtObject = new PrepStmtObject();
			bufInsSql.append("insert into cr_business_partner_dtl(DEAL_PARTNER_ID,deal_id,EFFECTIVE_RATE,PERCENTAGE,PARTNER_TYPE,MAKER_ID,LOAN_AMOUNT,self_flag,MAKER_DATE,SERVICE_PARTNER_FLAG,LEAD_PARTNER_FLAG,RATE_TYPE,RATE)");
			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?,"); //partner_ID
			bufInsSql.append(" ?,"); //LOAN_ID
			bufInsSql.append(" ?,"); //RATE
			bufInsSql.append(" ?,"); //AMOUNT
			bufInsSql.append(" ?,"); //partner_type
			bufInsSql.append(" ?,"); //makerId
			bufInsSql.append(" ?,"); //Loan_Amount
			bufInsSql.append(" ?,"); //self_flag
			bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?)");
			if (CommonFunction.checkNull(vo.getPartnerName()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getPartnerName());
			
			if (CommonFunction.checkNull(vo.getLoanId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getLoanId().trim());
			
			if ((CommonFunction.checkNull(vo.getEffectiveRate()).equalsIgnoreCase("")) || (CommonFunction.checkNull(vo.getLeadPartnerFlag()).equalsIgnoreCase("Y") && CommonFunction.checkNull(vo.getRateType()).equalsIgnoreCase("F")))
					insertPrepStmtObject.addNull();
			else if(vo.getServicingPartnerFlag().equalsIgnoreCase("Y"))
				{
				if (CommonFunction.checkNull(loanRate).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
				else
						insertPrepStmtObject.addString(loanRate);
				}
			else
				insertPrepStmtObject.addString(vo.getEffectiveRate().trim());
			
			if (CommonFunction.checkNull(vo.getPartnerPercentage()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getPartnerPercentage().trim());
			
			if (CommonFunction.checkNull(businessType).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(businessType.trim());
			

			if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerId());
			
			
			if (CommonFunction.checkNull(vo.getPartnerAmount()).equalsIgnoreCase(""))
				insertPrepStmtObject.addFloat(0.00);
			else
				insertPrepStmtObject.addString(CommonFunction.decimalNumberConvert((vo.getPartnerAmount()).trim()).toString());
			
			if (CommonFunction.checkNull(flag).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(flag.trim());
			
			
			if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getMakerDate());
			// add by saorabh
			if (CommonFunction.checkNull(vo.getServicingPartnerFlag()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getServicingPartnerFlag().trim());
			//end by saorabh
			
			if (CommonFunction.checkNull(vo.getLeadPartnerFlag()).equalsIgnoreCase(""))
				insertPrepStmtObject.addString("N");
			else
				insertPrepStmtObject.addString(vo.getLeadPartnerFlag().trim());
			
			logger.info("getRateType"+vo.getRateType());
			
			logger.info("getRateType"+vo.getServicingPartnerFlag());
			
			logger.info("getRateType"+vo.getLeadPartnerFlag());
			

			if ((CommonFunction.checkNull(vo.getRateType()).equalsIgnoreCase("") && CommonFunction.checkNull(vo.getServicingPartnerFlag()).equalsIgnoreCase("Y")) || (CommonFunction.checkNull(vo.getServicingPartnerFlag()).equalsIgnoreCase("N") && CommonFunction.checkNull(vo.getLeadPartnerFlag()).equalsIgnoreCase("N")) ||  (CommonFunction.checkNull(vo.getServicingPartnerFlag()).equalsIgnoreCase("Y") && CommonFunction.checkNull(vo.getLeadPartnerFlag()).equalsIgnoreCase("N")))
				insertPrepStmtObject.addString("E");
				else
					insertPrepStmtObject.addString(vo.getRateType().trim());
				
				if (CommonFunction.checkNull(vo.getPartnerRate()).equalsIgnoreCase("") && CommonFunction.checkNull(vo.getLeadPartnerFlag()).equalsIgnoreCase("N"))
					insertPrepStmtObject.addNull();
				else if (CommonFunction.checkNull(vo.getPartnerRate()).equalsIgnoreCase("") && CommonFunction.checkNull(vo.getLeadPartnerFlag()).equalsIgnoreCase("Y") && CommonFunction.checkNull(vo.getRateType()).equalsIgnoreCase("E") ) 
					insertPrepStmtObject.addNull();
				else	
					insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getPartnerRate()).trim());
			
			
			
			
			
			insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("IN business partner dtl() insert query1 ### "+ insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			if(status)
				result="S";
			else 
				result="NS";
			}
			else
			{
				insertPrepStmtObject = new PrepStmtObject();
				bufInsSql = new StringBuilder();
				bufInsSql.append("UPDATE cr_business_partner_dtl SET service_partner_flag='N'"); 
				bufInsSql.append(" where DEAL_PARTNER_ID=? and deal_id=?" );
				
				if (CommonFunction.checkNull(vo.getPartnerName()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getPartnerName());
				if (CommonFunction.checkNull(vo.getLoanId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLoanId().trim());
				insertPrepStmtObject.setSql(bufInsSql.toString());
				
				qryList.add(insertPrepStmtObject);
				
				
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				
				
				
				qry2="SELECT count(1) FROM cr_business_partner_dtl where  deal_id='"+vo.getLoanId()+"' and partner_type='"+businessType+"' and service_partner_flag='Y' ";
				 logger.info("the qry2 of count is : "+qry2);
				prevent=Integer.parseInt(ConnectionDAO.singleReturn(qry2));
						logger.info("Prevent Service Partner::::"+prevent);
						if(prevent>0 && vo.getServicingPartnerFlag().equalsIgnoreCase("Y"))
						{
							return "prevent";
						}
			
				
				
				qry4="SELECT ifnull(percentage,0) FROM cr_business_partner_dtl where DEAL_PARTNER_ID='"+vo.getPartnerName()+"' and deal_id='"+vo.getLoanId()+"' and partner_type='"+businessType+"'";
				 logger.info("the qry4 of count is : "+qry4);
				float  value =Float.parseFloat(ConnectionDAO.singleReturn(qry4));
				sum=(sum-value)+Float.parseFloat(vo.getPartnerPercentage());
				if(sum>100.00)
				{
					return "contributionAmount";
				}
				insertPrepStmtObject = new PrepStmtObject();
				bufInsSql = new StringBuilder();
				bufInsSql.append("UPDATE cr_business_partner_dtl SET DEAL_PARTNER_ID=?,deal_id=?,EFFECTIVE_RATE=?,percentage=?,partner_type=?,MAKER_ID=?,LOAN_AMOUNT=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),service_partner_flag=?,self_flag=?,LEAD_PARTNER_FLAG=?,RATE_TYPE=?,RATE=?  "); 
				bufInsSql.append(" where DEAL_PARTNER_ID=? and deal_id=? and partner_type=? " );
				
				
				
				if (CommonFunction.checkNull(vo.getPartnerName()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getPartnerName());
				
				if (CommonFunction.checkNull(vo.getLoanId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLoanId().trim());
				
				
				
				if ((CommonFunction.checkNull(vo.getEffectiveRate()).equalsIgnoreCase("")) || (CommonFunction.checkNull(vo.getLeadPartnerFlag()).equalsIgnoreCase("Y") && CommonFunction.checkNull(vo.getRateType()).equalsIgnoreCase("F")))
					insertPrepStmtObject.addNull();
			else if(vo.getServicingPartnerFlag().equalsIgnoreCase("Y"))
				{
				if (CommonFunction.checkNull(loanRate).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
				else
						insertPrepStmtObject.addString(loanRate);
				}
			else
				insertPrepStmtObject.addString(vo.getEffectiveRate().trim());
				
				
				
				if (CommonFunction.checkNull(vo.getPartnerPercentage()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getPartnerPercentage().trim());
				
				if (CommonFunction.checkNull(businessType).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(businessType.trim());
				

				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());
				
				
				if (CommonFunction.checkNull(vo.getPartnerAmount()).equalsIgnoreCase(""))
					insertPrepStmtObject.addFloat(0.00);
				else
					insertPrepStmtObject.addString(CommonFunction.decimalNumberConvert((vo.getPartnerAmount()).trim()).toString());
				
				
				if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());
				// add by saorabh
				if (CommonFunction.checkNull(vo.getServicingPartnerFlag()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getServicingPartnerFlag().trim());
				
				
				//end by saorabh
				
				if (CommonFunction.checkNull(flag).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(flag.trim());
				
				if (CommonFunction.checkNull(vo.getLeadPartnerFlag()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLeadPartnerFlag().trim());
				
				if ((CommonFunction.checkNull(vo.getRateType()).equalsIgnoreCase("") && CommonFunction.checkNull(vo.getServicingPartnerFlag()).equalsIgnoreCase("Y")) || (CommonFunction.checkNull(vo.getServicingPartnerFlag()).equalsIgnoreCase("N") && CommonFunction.checkNull(vo.getLeadPartnerFlag()).equalsIgnoreCase("N")) ||  (CommonFunction.checkNull(vo.getServicingPartnerFlag()).equalsIgnoreCase("Y") && CommonFunction.checkNull(vo.getLeadPartnerFlag()).equalsIgnoreCase("N")))
					insertPrepStmtObject.addString("E");
				else
					insertPrepStmtObject.addString(vo.getRateType().trim());
				
				if (CommonFunction.checkNull(vo.getPartnerRate()).equalsIgnoreCase("") && CommonFunction.checkNull(vo.getLeadPartnerFlag()).equalsIgnoreCase("N"))
					insertPrepStmtObject.addNull();
				else if (CommonFunction.checkNull(vo.getPartnerRate()).equalsIgnoreCase("") && CommonFunction.checkNull(vo.getLeadPartnerFlag()).equalsIgnoreCase("Y") && CommonFunction.checkNull(vo.getRateType()).equalsIgnoreCase("E") ) 
					insertPrepStmtObject.addNull();
				else	
					insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getPartnerRate()).trim());
				
				
				if (CommonFunction.checkNull(vo.getPartnerName()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getPartnerName());
				
				if (CommonFunction.checkNull(vo.getLoanId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLoanId().trim());
				if (CommonFunction.checkNull(businessType).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(businessType.trim());
				
				insertPrepStmtObject.setSql(bufInsSql.toString());
				
				logger.info("IN updateBusiness partner() insert query1 ### "+ insertPrepStmtObject.printQuery());
				
				qryList.add(insertPrepStmtObject);
							
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				
				if(status)
					result="M";
				else
					result="NM";
			 
			}
			logger.info("In updateBusiness partner......................"+ status);
			/*if(CommonFunction.checkNull(result).equalsIgnoreCase("S") || CommonFunction.checkNull(result).equalsIgnoreCase("M"))
			{
			insertPrepStmtObject=null;
			bufInsSql=null;
				
			insertPrepStmtObject = new PrepStmtObject();
			bufInsSql = new StringBuilder();
			bufInsSql.append("UPDATE cr_deal_dtl SET REFRESH_FLAG=CONCAT(left(REFRESH_FLAG,3-1),'Y',right(REFRESH_FLAG,lenGTH(REFRESH_FLAG)-3)) WHERE deal_id=?"); 
			
			if (CommonFunction.checkNull(vo.getLoanId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getLoanId().trim());
			insertPrepStmtObject.setSql(bufInsSql.toString());
			
			rspQryList.add(insertPrepStmtObject);
			
			
			ConnectionDAO.sqlInsUpdDeletePrepStmt(rspQryList);
				
			}	*/

			} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				qryList.clear();
				qryList = null;
				
			         }
			return result;
		
	}
	@Override
	public ArrayList<LoanDetailForCMVO> getPartnerDetails(LoanDetailForCMVO loanDetailForCMVO, String loanId,String businessType) {
	ArrayList list=new ArrayList();
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
		try
			{
			StringBuilder query =new StringBuilder();
		    query.append(" select a.BP_ID,a.partner_ID,B.DESCRIPTION,a.deal_id,c.DEAL_NO,a.RATE,round(ifnull(a.percentage,0),2),case a.service_partner_flag when 'Y' then 'Yes' when 'N' then 'No' end as service_partner_flag, round(ifnull(loan_amount,0),2),case a.lead_partner_flag when 'Y' then 'Yes' when 'N' then 'No' end as leadflag ,a.RATE_TYPE,a.EFFECTIVE_RATE,case ifnull(a.RATE_TYPE,'E') when 'E' then 'Effective' when 'F' then 'Flat' end as rate_type_desc from cr_business_partner_dtl a "
		    		+ " join generic_master b on a.DEAL_PARTNER_ID=b.VALUE  left join cr_deal_dtl c on c.deal_id=a.deal_id "+
			" where a.deal_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()+" and a.partner_type='"+businessType+"' ");
					
			logger.info("getLoanHeader Query::::::::::::::::::::::::::::::::::::"+query);
					
		LoanDetailForCMVO vo= null;
	    ArrayList header = ConnectionDAO.sqlSelect(query.toString());
	        for(int i=0;i<header.size();i++){
		logger.info("header"+header.size());
		ArrayList header1=(ArrayList)header.get(i);
						if(header1!=null && header1.size()>0)
						{
							vo = new LoanDetailForCMVO();
							vo.setId((CommonFunction.checkNull(header1.get(0))).trim());
							vo.setLbxpartnerId((CommonFunction.checkNull(header1.get(1))).trim());
							vo.setPartnerName((CommonFunction.checkNull(header1.get(2))).trim());
							vo.setLoanId((CommonFunction.checkNull(header1.get(3))).trim());
							vo.setLoanNo((CommonFunction.checkNull(header1.get(4))).trim());
							vo.setPartnerRate((CommonFunction.checkNull(header1.get(5))).trim());
							vo.setPartnerPercentage((CommonFunction.checkNull(header1.get(6))).trim());
							vo.setServicingPartnerFlag((CommonFunction.checkNull(header1.get(7))).trim());
							vo.setPartnerAmount((CommonFunction.checkNull(header1.get(8))).trim());
							vo.setLeadPartnerFlag((CommonFunction.checkNull(header1.get(9))).trim());
							vo.setRateType((CommonFunction.checkNull(header1.get(10))).trim());
							if(!CommonFunction.checkNull(header1.get(11)).equalsIgnoreCase(""))	
							{
								Number reconNum =myFormatter.parse((CommonFunction.checkNull(header1.get(11))).trim());
								vo.setEffectiveRate(myFormatter.format(reconNum));
							}
							vo.setRateTypeDesc((CommonFunction.checkNull(header1.get(12))).trim());
			
							list.add(vo);
							vo=null;
						}
					}
					query=null;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					e=null;
				}finally{
					loanId=null;			
				}
			
				return list;
			
			}
	
//pooja code starts
	@Override
	public ArrayList<LoanDetailForCMVO> getPartnerBusDetails(LoanDetailForCMVO loanDetailForCMVO,
			String id, String loanId,String businessType) {
		ArrayList list=new ArrayList();
		try
		{
			StringBuilder query =new StringBuilder();
			 query.append(" select a.BP_ID,a.DEAL_PARTNER_ID,a.deal_id,c.DEAL_NO,a.RATE ,round(ifnull(A.PERCENTAGE,0),2),case a.service_partner_flag when 'Y' then 'Yes' when 'N' then 'No' end as service_partner_flag,round(ifnull(loan_amount,0),2),case a.lead_partner_flag when 'Y' then 'Yes' when 'N' then 'No' end as leadflag,a.RATE_TYPE,a.EFFECTIVE_RATE,case ifnull(a.RATE_TYPE,'E') when 'E' then 'Effective' when 'F' then 'Flat' end as rate_type_desc from cr_business_partner_dtl a "
			 		+ " join generic_master b on a.DEAL_PARTNER_ID=b.VALUE  left join cr_deal_dtl c on c.deal_id=a.deal_id  "+
							" where a.DEAL_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()+" and a.BP_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id)).trim()+" and a.partner_type='"+businessType+"' ");
			
			logger.info("getLoanHeader Query::::::::::::::::::::::::::::::::::::"+query);
			
			LoanDetailForCMVO vo= null;
			ArrayList header = ConnectionDAO.sqlSelect(query.toString());
			for(int i=0;i<header.size();i++){
				logger.info("header"+header.size());
				ArrayList header1=(ArrayList)header.get(i);
				if(header1!=null && header1.size()>0)
				{
					vo = new LoanDetailForCMVO();
					vo.setId((CommonFunction.checkNull(header1.get(0))).trim());
					vo.setPartnerName((CommonFunction.checkNull(header1.get(1))).trim());
					//vo.setPartnerName((CommonFunction.checkNull(header1.get(2))).trim());
					vo.setLoanId((CommonFunction.checkNull(header1.get(2))).trim());
					vo.setLoanNo((CommonFunction.checkNull(header1.get(3))).trim());
					vo.setPartnerRate((CommonFunction.checkNull(header1.get(4))).trim());
					vo.setPartnerPercentage((CommonFunction.checkNull(header1.get(5))).trim());
					vo.setServicingPartnerFlag((CommonFunction.checkNull(header1.get(6))).trim());
					vo.setPartnerAmount((CommonFunction.checkNull(header1.get(7))).trim());
					vo.setLeadPartnerFlag((CommonFunction.checkNull(header1.get(8))).trim());
					vo.setRateType((CommonFunction.checkNull(header1.get(9))).trim());
					if(!CommonFunction.checkNull(header1.get(10)).equalsIgnoreCase(""))	
					{
						Number reconNum =myFormatter.parse((CommonFunction.checkNull(header1.get(10))).trim());
						vo.setEffectiveRate(myFormatter.format(reconNum));
					}
					vo.setRateTypeDesc((CommonFunction.checkNull(header1.get(11))).trim());

					list.add(vo);
					vo=null;
				}
			}
			query=null;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			e=null;
		}finally{
			loanId=null;			
		}

		return list;

	}
	@Override
	public int deletePartnerDtl(String partnerDtl, String loanId,String businessType) {
		
        int status=0;
        boolean qryStatus = false;
       
        ArrayList qryList = new ArrayList();
		
	try{
		String query="delete from cr_business_partner_dtl where BP_ID=? and partner_type=? ";
		PrepStmtObject insPrepStmtObject = new PrepStmtObject();
		if(CommonFunction.checkNull(partnerDtl).trim().equalsIgnoreCase(""))
			insPrepStmtObject.addNull();
		else
			insPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(partnerDtl)).trim());
		if(CommonFunction.checkNull(businessType).trim().equalsIgnoreCase(""))
			insPrepStmtObject.addNull();
		else
			insPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(businessType)).trim());
		
		insPrepStmtObject.setSql(query.toString());
        logger.info("IN deletePartnerDtl() delete query ### "+insPrepStmtObject.printQuery());
		qryList.add(insPrepStmtObject);
		qryStatus=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
		logger.info("Deletion Status :."+qryStatus);
		}catch(Exception e){
			e.printStackTrace();
		}
		if(qryStatus)
		{
			status=1;
		}
		return status;
		}
	public boolean insertcolndngDtl(UnderwriterApprovalVo cr){
		boolean status = false;
		ArrayList qryList = new ArrayList();
		ArrayList searchlist = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		try {
			String customerId = ConnectionDAO.singleReturn("select IFNULL(GCD_CUSTOMER_ID,DEAL_CUSTOMER_ID) from cr_deal_dtl where deal_id = '"+cr.getDealId()+"' ");
			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql.append("insert into CR_DEAL_COLANDING_APPROVAL_DTL (DEAL_ID,CUSTOMER_ID,REC_STATUS,MAKER_ID,MAKER_DATE,STAGE ");
			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?," );//DEAL_ID
			bufInsSql.append(" ?," );//CUSTOMER_ID
			bufInsSql.append(" ?," );//REC_STATUS
			bufInsSql.append(" ?," );//MAKER_ID
			bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND), " );//MAKER_DATE
			bufInsSql.append(" ?" );//charge_code
			bufInsSql.append("  )" );
			//insertPrepStmtObject = new PrepStmtObject();

			if ((CommonFunction.checkNull(cr.getDealId())).trim().equalsIgnoreCase("")) 
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((cr.getDealId()).trim()); // DEAL_ID	
			
			if ((CommonFunction.checkNull(customerId)).trim().equalsIgnoreCase("")) 
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((customerId).trim()); // CUSTOMER_ID
			if(cr.getFunctionId().equalsIgnoreCase("3000296")){
				insertPrepStmtObject.addString(("A").trim()); // REC_STATUS
			}else if(cr.getFunctionId().equalsIgnoreCase("500000123")){
				insertPrepStmtObject.addString(("P").trim());
			}
			
			if ((CommonFunction.checkNull(cr.getUserId())).trim().equalsIgnoreCase("")) 
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((cr.getUserId()).trim()); // MAKER_ID
				
			if ((CommonFunction.checkNull(cr.getMakerDate())).trim().equalsIgnoreCase("")) 
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((cr.getMakerDate()).trim()); // MAKER_DATE
			
			if(cr.getFunctionId().equalsIgnoreCase("3000296")){
				insertPrepStmtObject.addString(("UNC").trim()); // STAGE
			}else if(cr.getFunctionId().equalsIgnoreCase("500000123")){
				insertPrepStmtObject.addString(("COUNC").trim());
			}
			
			insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("IN SaveLoan() insert query1 ### " + insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("In saveCovenantTracking......................" + status);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			qryList = null;
			insertPrepStmtObject = null;
		}
		return status;
	}
	
	public String updateApprovalData(Object ob) {
		
		UnderwriterApprovalVo vo = (UnderwriterApprovalVo)ob;
		if(CommonFunction.checkNull(vo.getLbxReason()).equalsIgnoreCase(""))
		{
			vo.setLbxReason("0");
		}
		logger.info("In updateApprovalData......DealId:  "+vo.getDealId()+"UserId:  "+vo.getUserId()+"Approval decision:  "+vo.getDecision()+"Reverse Stage:  "+vo.getReversingStage()+"Reprocessing flag:  "+vo.getRpStageFlag()+" Reason Id: "+vo.getLbxReason());
		CallableStatement cst=null;
		String statusProc="";
		
		int x=0;
		boolean status=false;
		
		//Connection con=ConnectionDAO.getConnection();
		try 
		{
			ArrayList<Object> in =new ArrayList<Object>();
			ArrayList<Object> out =new ArrayList<Object>();
			ArrayList outMessages = new ArrayList();
			String s1="";
			String s2="";
			in.add(vo.getDealId());
			in.add(vo.getUserId());
			String date=CommonFunction.changeFormat(vo.getMakerDate());
			in.add(date);
			in.add(vo.getDecision());
			in.add(vo.getReversingStage());
			in.add(vo.getRpStageFlag());
			in.add(vo.getRemarks());
			in.add(vo.getLbxReason());
			out.add(s1);
			out.add(s2);
			
			logger.info("COLANDING_UNDERWRITER_APPROVAL ("+in.toString()+","+out.toString()+")");
			outMessages=(ArrayList) ConnectionDAO.callSP("COLANDING_UNDERWRITER_APPROVAL",in,out);
			s1=CommonFunction.checkNull(outMessages.get(0));
			s2=CommonFunction.checkNull(outMessages.get(1));
		    logger.info("s1  : "+s1);
		    logger.info("s2  : "+s2);	
		    if(s1.equalsIgnoreCase("S"))
			{
				statusProc=s1;
				status=true;
				logger.info("Procedure Error Message----"+s2);
			}
			else
			{
				statusProc=s2;
				logger.info("Procedure Error Message----"+s2);
			}		
		     in.clear();
		     in=null;
		     out.clear();
		     out=null;
		} catch (Exception e)
		{e.printStackTrace();}
		finally{
			vo=null;
			ob=null;
		}
		return statusProc;
	}
//pooja code end	
			
			
			}
			
			
