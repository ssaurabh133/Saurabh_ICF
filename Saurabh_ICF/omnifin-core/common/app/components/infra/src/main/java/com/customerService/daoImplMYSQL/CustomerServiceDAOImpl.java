package com.customerService.daoImplMYSQL;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.cp.vo.CreditProcessingNotepadVo;
import com.customerService.dao.CustomerServiceDAO;
import com.customerService.vo.CustomerServiceVo;



public class CustomerServiceDAOImpl  implements CustomerServiceDAO{
	
	private static final Logger logger = Logger.getLogger(CustomerServiceDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));

	@Override
	public ArrayList<CustomerServiceVo> caseMarkingDetail(CustomerServiceVo ob) {
		
		
		logger.info("In caseMarkingDetail() loan id: "+ob.getLoanId());
		CustomerServiceVo vo = null;
		ArrayList list = new ArrayList();
		StringBuilder query = new StringBuilder(); 
		ArrayList outerList = new ArrayList();
		ArrayList innerList = new ArrayList();
		try {
				query.append(" SELECT DISTINCT G.DESCRIPTION,IF(M.CASE_MARKING_FLAG='Y','YES','NO') AS CASE_MARKING_FLAG,IF(M.CASE_MARKING_FLAG='Y',DATE_FORMAT(M.CASE_MARKING_DATE,'"+dateFormat+"'),'') MARKED_DATE,A.AGENCY_NAME,M.OTHER_DETAILS,M.REMARKS,IF(M.CASE_MARKING_FLAG='N',DATE_FORMAT(HIST.CASE_MARKING_DATE,'"+dateFormat+"'),'') UNMARKED_DATE " );
				query.append(" FROM CR_CASE_MARKING_DTL M ");
				query.append(" INNER JOIN GENERIC_MASTER G ON GENERIC_KEY='CASE_MARKING_FLAG' AND G.VALUE=M.CASE_STATUS ");
				query.append(" LEFT JOIN COM_AGENCY_M A ON A.AGENCY_CODE=M.AGENCY ");
				query.append(" LEFT JOIN (  ");
				query.append(" SELECT H.LOAN_ID,MAX(H.CASE_MARKING_DATE)AS CASE_MARKING_DATE FROM CR_CASE_MARKING_HST_DTL H WHERE H.LOAN_ID='"+CommonFunction.checkNull(ob.getLoanId())+"' GROUP BY H.LOAN_ID ");
				query.append(" )HIST ON HIST.LOAN_ID=M.LOAN_ID   ");
				query.append(" WHERE M.LOAN_ID='"+CommonFunction.checkNull(ob.getLoanId())+"' ");
		
		        logger.info(" caseMarkingDetail query  :  " + query.toString());
		
		
		    outerList = ConnectionDAO.sqlSelect(query.toString());
			int size = outerList.size();
		
			for (int i = 0; i < size; i++) {
				
				innerList = (ArrayList) outerList.get(i);
				if (innerList.size() > 0) {
					
					vo = new CustomerServiceVo();
					
					vo.setCaseMarkingStatus((CommonFunction.checkNull(innerList.get(0)).trim()));
					vo.setCaseMarkingFlag((CommonFunction.checkNull(innerList.get(1)).trim()));
					vo.setCaseMarkingDate((CommonFunction.checkNull(innerList.get(2)).trim()));
					vo.setCaseMarkingAgencyName((CommonFunction.checkNull(innerList.get(3)).trim()));
					vo.setCaseMarkingOtherDetails((CommonFunction.checkNull(innerList.get(4)).trim()));
					vo.setCaseMarkingRemarks((CommonFunction.checkNull(innerList.get(5)).trim()));
					vo.setCaseUnMarkingDate((CommonFunction.checkNull(innerList.get(6)).trim()));
					list.add(vo);
					
					
				}

				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			vo=null;
			query = null; 
			innerList=null;
			outerList=null;
			
		}
		return list;

	}

	@Override
	public ArrayList<CustomerServiceVo> closureDetail(CustomerServiceVo ob) {
		
		logger.info("In closureDetail() loan id: "+ob.getLoanId());
		CustomerServiceVo vo = null;
		ArrayList list = new ArrayList();
		StringBuilder query = new StringBuilder(); 
		ArrayList outerList = new ArrayList();
		ArrayList innerList = new ArrayList();
		try {
				query.append(" SELECT DISTINCT if(T.TERMINATION_TYPE='X','CANCEL', if(T.TERMINATION_TYPE='T','TERMINATION',if(T.TERMINATION_TYPE='C','CLOSURE','NA'   ))) AS TERMINATION_TYPE ,  " );
				query.append(" DATE_FORMAT(T.TERMINATION_DATE,'"+dateFormat+"') AS TERMINATION_DATE,M.USER_NAME,MAKER_REMARKS,REMARKS,A.USER_NAME   ");
				query.append(" FROM CR_TERMINATION_DTL T    ");
				query.append(" INNER JOIN SEC_USER_M M ON M.USER_ID=T.MAKER_ID ");
				query.append(" LEFT JOIN SEC_USER_M A ON A.USER_ID=T.AUTHOR_ID  ");
				query.append(" WHERE LOAN_ID='"+CommonFunction.checkNull(ob.getLoanId())+"' ");
		
		        logger.info(" closureDetail query  :  " + query.toString());
		
		
		    outerList = ConnectionDAO.sqlSelect(query.toString());
			int size = outerList.size();
		
			for (int i = 0; i < size; i++) {
				
				innerList = (ArrayList) outerList.get(i);
				if (innerList.size() > 0) {
					
					vo = new CustomerServiceVo();
					
					vo.setClosureType((CommonFunction.checkNull(innerList.get(0)).trim()));
					vo.setClosureDate((CommonFunction.checkNull(innerList.get(1)).trim()));
					vo.setClosureMakerName((CommonFunction.checkNull(innerList.get(2)).trim()));
					vo.setClosureMakerRemarks((CommonFunction.checkNull(innerList.get(3)).trim()));
					vo.setClosureAuthoremarks((CommonFunction.checkNull(innerList.get(4)).trim()));
					vo.setClosureAuthorName((CommonFunction.checkNull(innerList.get(5)).trim()));
					list.add(vo);
					
				
				}

				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			vo=null;
			query = null; 
			innerList=null;
			outerList=null;
			
		}
		return list;
	}

	@Override
	public ArrayList<CustomerServiceVo> loanSummaryViewer(CustomerServiceVo ob) {
		
		logger.info("In loanSummaryViewer() loan id: "+ob.getLoanId());
		CustomerServiceVo vo = null;
		ArrayList list = new ArrayList();
		StringBuilder query = new StringBuilder(); 
		ArrayList outerList = new ArrayList();
		ArrayList innerList = new ArrayList();
		try {
		query.append("SELECT DISTINCT LOAN_NO,C.CUSTOMER_NAME,if(CLD.REC_STATUS='P','Pending' ,if(CLD.REC_STATUS='A','Active', if(CLD.REC_STATUS='L','Cancelled', if(CLD.REC_STATUS='X','Rejected',     if(CLD.REC_STATUS='C','Closed', 'NA'))))) AS REC_STATUS," );
		query.append(" if(CLD.DISBURSAL_STATUS='N','Not Disbursed', if(CLD.DISBURSAL_STATUS='P','Partially Disbursed',if(CLD.DISBURSAL_STATUS='F','Fully Disbursed','NA'   ))) AS DISBURSAL_STATUS,  " );
		query.append(" if(CLD.FILE_STATUS='SO','Send To OPS', if(CLD.FILE_STATUS='RO','Received By OPS',if(CLD.FILE_STATUS='RU','Received By User',if(CLD.FILE_STATUS='H','Hold',if(CLD.FILE_STATUS='C','Clear',if(CLD.FILE_STATUS='SS','Send To Store',if(CLD.FILE_STATUS='RS','Received By Store','NA'   )    )   )   )   ))) AS FILE_STATUS , " );
		query.append(" DATE_FORMAT(LOAN_APPROVAL_DATE,'"+dateFormat+"'), ");
		query.append(" if(CLD.LOAN_REPAYMENT_MODE='O','OTC', if(CLD.LOAN_REPAYMENT_MODE='P','PDC',if(CLD.LOAN_REPAYMENT_MODE='E','ECS',if(CLD.LOAN_REPAYMENT_MODE='N','NPDC',if(CLD.LOAN_REPAYMENT_MODE='R','RPDC',if(CLD.LOAN_REPAYMENT_MODE='D','DIRECT DEBIT',if(CLD.LOAN_REPAYMENT_MODE='S','ESCROW','NA'   )   )   )   )   ))) AS LOAN_REPAYMENT_MODE ,LOAN_FLAT_RATE,LOAN_EFF_RATE, ");
		query.append(" GI.DESCRIPTION,if(CLD.LOAN_INSTALLMENT_MODE='A','ADVANCE', if(CLD.LOAN_INSTALLMENT_MODE='R','ARREAR','NA')) AS LOAN_INSTALLMENT_MODE ,G.DESCRIPTION,NPA_FLAG,LOAN_DPD,LOAN_DPD_STRING,LOAN_TENURE,DATE_FORMAT(LOAN_MATURITY_DATE,'"+dateFormat+"')  ");
		query.append(" FROM CR_LOAN_DTL CLD ");
		query.append(" INNER JOIN gcd_customer_m C ON CLD.LOAN_CUSTOMER_ID=C.CUSTOMER_ID ");
		query.append(" LEFT JOIN generic_master G ON G.GENERIC_KEY='SECTOR_TYPE' AND G.VALUE=CLD.LOAN_SECTOR_TYPE ");
		query.append(" LEFT JOIN generic_master GI ON GI.GENERIC_KEY='INSTALLMENT_TYPE' AND GI.VALUE=CLD.LOAN_INSTALLMENT_TYPE ");
		query.append(" WHERE LOAN_ID='"+CommonFunction.checkNull(ob.getLoanId())+"' ");
		
		logger.info(" loanSummaryViewer query  :  " + query.toString());
		
		
		    outerList = ConnectionDAO.sqlSelect(query.toString());
			int size = outerList.size();
		
			for (int i = 0; i < size; i++) {
				
				innerList = (ArrayList) outerList.get(i);
				if (innerList.size() > 0) {
					
					vo = new CustomerServiceVo();
					
					vo.setLoanNo((CommonFunction.checkNull(innerList.get(0)).trim()));
					vo.setCustomerName((CommonFunction.checkNull(innerList.get(1)).trim()));
					vo.setLoanStatus((CommonFunction.checkNull(innerList.get(2)).trim()));
					vo.setDisbursalStatus((CommonFunction.checkNull(innerList.get(3)).trim()));
					vo.setFileStatus((CommonFunction.checkNull(innerList.get(4)).trim()));
					vo.setLoanApprovalDate((CommonFunction.checkNull(innerList.get(5)).trim()));
					vo.setLoanRepaymentMode((CommonFunction.checkNull(innerList.get(6)).trim()));
					vo.setLoanFlatRate((CommonFunction.checkNull(innerList.get(7)).trim()));
					vo.setLoanEffRate((CommonFunction.checkNull(innerList.get(8)).trim()));
					vo.setLoanInstallmentType((CommonFunction.checkNull(innerList.get(9)).trim()));
					vo.setLoanInstallmentMode((CommonFunction.checkNull(innerList.get(10)).trim()));
					vo.setSectorType((CommonFunction.checkNull(innerList.get(11)).trim()));
					vo.setNpaFlag((CommonFunction.checkNull(innerList.get(12)).trim()));
					vo.setLoanDpd((CommonFunction.checkNull(innerList.get(13)).trim()));
					vo.setLoanDpdString((CommonFunction.checkNull(innerList.get(14)).trim()));
					vo.setLoanTenure((CommonFunction.checkNull(innerList.get(15)).trim()));
					vo.setLoanMaturityDate((CommonFunction.checkNull(innerList.get(16)).trim()));
					list.add(vo);
					
					
				}

				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			vo=null;
			query = null; 
			innerList=null;
			outerList=null;
			
		}
		return list;
	}

	@Override
	public ArrayList<CustomerServiceVo> reshcedulingDetail(CustomerServiceVo ob) {
		
		logger.info("In reshcedulingDetail() loan id: "+ob.getLoanId());
		CustomerServiceVo vo = null;
		ArrayList list = new ArrayList();
		StringBuilder query = new StringBuilder(); 
		ArrayList outerList = new ArrayList();
		ArrayList innerList = new ArrayList();
		try {
				query.append(" SELECT DISTINCT if(R.RESCH_TYPE='R','Repricing', if(R.RESCH_TYPE='P','Part Payment',if(R.RESCH_TYPE='D','EMI Deferral',if(R.RESCH_TYPE='U','Due date change',if(R.RESCH_TYPE='A','Additional Disbursal','NA'   )   )   ))) AS RESCH_TYPE ,  " );
				query.append(" DATE_FORMAT(R.RESCH_DATE,'"+dateFormat+"') AS RESCH_DATE,M.USER_NAME,RESCH_REASON,REMARKS,A.USER_NAME  ");
				query.append(" FROM CR_RESCH_DTL R   ");
				query.append(" INNER JOIN SEC_USER_M M ON M.USER_ID=R.MAKER_ID ");
				query.append(" LEFT JOIN SEC_USER_M A ON A.USER_ID=R.AUTHOR_ID  ");
				query.append(" WHERE LOAN_ID='"+CommonFunction.checkNull(ob.getLoanId())+"' ");
		
		        logger.info(" reshcedulingDetail query  :  " + query.toString());
		
		
		    outerList = ConnectionDAO.sqlSelect(query.toString());
			int size = outerList.size();
		
			for (int i = 0; i < size; i++) {
				
				innerList = (ArrayList) outerList.get(i);
				if (innerList.size() > 0) {
					
					vo = new CustomerServiceVo();
					
					vo.setReschType((CommonFunction.checkNull(innerList.get(0)).trim()));
					vo.setReschDate((CommonFunction.checkNull(innerList.get(1)).trim()));
					vo.setReschMakerName((CommonFunction.checkNull(innerList.get(2)).trim()));
					vo.setReschMakerRemarks((CommonFunction.checkNull(innerList.get(3)).trim()));
					vo.setReschAuthoremarks((CommonFunction.checkNull(innerList.get(4)).trim()));
					vo.setReschAuthorName((CommonFunction.checkNull(innerList.get(5)).trim()));
					list.add(vo);
					
					
				}

				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			vo=null;
			query = null; 
			innerList=null;
			outerList=null;
			
		}
		return list;
	}

	@Override
	public ArrayList<CustomerServiceVo> secuitizationDetail(CustomerServiceVo ob) {
		
		logger.info("In secuitizationDetail() loan id: "+ob.getLoanId());
		CustomerServiceVo vo = null;
		ArrayList list = new ArrayList();
		StringBuilder query = new StringBuilder(); 
		ArrayList outerList = new ArrayList();
		ArrayList innerList = new ArrayList();
		try {
				query.append(" SELECT DISTINCT PD.POOL_ID,PM.POOL_NAME,IF(PM.POOL_TYPE='S','Securitized','Re-finance') AS POOL_TYPE,DATE_FORMAT(PM.POOL_CREATION_DATE,'"+dateFormat+"') AS POOL_CREATION_DATE,A.AGENCY_NAME  " );
				query.append(" FROM CR_SECURITIZATION_POOL_DTL PD  ");
				query.append(" INNER JOIN CR_SECURITIZATION_POOL_M PM ON PM.POOL_ID=PD.POOL_ID ");
				query.append(" INNER JOIN COM_AGENCY_M A ON  AGENCY_TYPE='SEC' AND PM.POOL_INSTITUTION_ID=A.AGENCY_CODE  ");
				query.append(" WHERE LOAN_ID='"+CommonFunction.checkNull(ob.getLoanId())+"' ");
		
		        logger.info(" secuitizationDetail query  :  " + query.toString());
		
		
		    outerList = ConnectionDAO.sqlSelect(query.toString());
			int size = outerList.size();
		
			for (int i = 0; i < size; i++) {
				
				innerList = (ArrayList) outerList.get(i);
				if (innerList.size() > 0) {
					
					vo = new CustomerServiceVo();
					
					vo.setSecPoolNo((CommonFunction.checkNull(innerList.get(0)).trim()));
					vo.setSecPoolName((CommonFunction.checkNull(innerList.get(1)).trim()));
					vo.setSecPoolType((CommonFunction.checkNull(innerList.get(2)).trim()));
					vo.setSecPoolMarkingDate((CommonFunction.checkNull(innerList.get(3)).trim()));
					vo.setSecAgencyName((CommonFunction.checkNull(innerList.get(4)).trim()));
					list.add(vo);
					
					
				}

				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			vo=null;
			query = null; 
			innerList=null;
			outerList=null;
			
		}
		return list;

	}

	@Override
	public ArrayList<CustomerServiceVo> customerExposureListViewer(
			CustomerServiceVo ob) {
		
		logger.info("In customerExposureListViewer() loan id: "+ob.getTxnId());
		CustomerServiceVo vo = null;
		ArrayList list = new ArrayList();
		StringBuilder query = new StringBuilder(); 
		ArrayList outerList = new ArrayList();
		ArrayList innerList = new ArrayList();
		try {
				query.append(" SELECT DISTINCT L.LOAN_NO,IF(L.REC_STATUS='A','Approved',IF(L.REC_STATUS='X','Rejected',IF(L.REC_STATUS='P','Pending',IF(L.REC_STATUS='F','Forward',IF(L.REC_STATUS='C','Closed',IF(L.REC_STATUS='L','Canceled','NA'))))))AS 'LOAN STATUS',  " );
				query.append(" IF(L.DISBURSAL_STATUS='P','Partially Disbursed',IF(L.DISBURSAL_STATUS='F','Full Disbursed',IF(L.DISBURSAL_STATUS='N','Not Disbursed','NA')))AS 'DISBURSAL STATUS' ,DATE_FORMAT(L.NEXT_DUE_DATE,'"+dateFormat+"') AS 'FIRST EMI' ,DATE_FORMAT(DATE_ADD(L.NEXT_DUE_DATE, INTERVAL LOAN_NO_OF_INSTALLMENT MONTH),'"+dateFormat+"') AS 'LAST EMI' ,  " );
				query.append(" IF(R.LOAN_CUSTOMER_ROLE_TYPE='PRAPPL','Applicant',IF(R.LOAN_CUSTOMER_ROLE_TYPE='COAPPL','Co-Applicant',IF(R.LOAN_CUSTOMER_ROLE_TYPE='GUARANTOR','Guarantor','NA'))) AS 'CUSTOMER ROLE',IFNULL(LOAN_BALANCE_PRINCIPAL,0),IFNULL(LOAN_OVERDUE_AMOUNT,0),(SUM(IFNULL(ADVICE_AMOUNT,0))-SUM(IFNULL(TXN_ADJUSTED_AMOUNT,0))) AS 'OVERDUE CHARGES',LOAN_DPD,  " );
				query.append(" NPA_FLAG,IF(L.REPO_FLAG='A','YES',IF(L.REPO_FLAG='X','NO','NA')) AS 'REPO FLAG',IF(L.LEGAL_FLAG='A','YES',IF(L.LEGAL_FLAG='X','NO','NA')) AS 'LEGAL FLAG','SOA LINK',L.LOAN_ID,G.CUSTOMER_NAME  " );
				query.append(" FROM CR_LOAN_DTL L  ");
				query.append(" INNER JOIN  CR_LOAN_CUSTOMER_ROLE R  ON L.LOAN_ID=R.LOAN_ID AND R.GCD_ID IN  " );
				if(CommonFunction.checkNull(ob.getTxnType()).equalsIgnoreCase("DC"))
				{
					query.append(" (SELECT IFNULL(A.GCD_ID,0)  FROM CR_DEAL_CUSTOMER_ROLE A WHERE A.DEAL_ID='"+CommonFunction.checkNull(ob.getTxnId())+"')  ");
				}
				else
				{
					query.append(" (SELECT IFNULL(A.GCD_ID,0) FROM CR_LOAN_CUSTOMER_ROLE A WHERE A.LOAN_ID='"+CommonFunction.checkNull(ob.getTxnId())+"')  ");
				}
				query.append(" INNER JOIN GCD_CUSTOMER_M G ON G.CUSTOMER_ID=R.GCD_ID  ");
				query.append(" LEFT JOIN CR_TXNADVICE_DTL T ON T.LOAN_ID=L.LOAN_ID AND ADVICE_TYPE='R' ");
				query.append(" GROUP BY L.LOAN_ID  ");
		
		     logger.info(" customerExposureListViewer query  :  " + query.toString());
		
		
		    outerList = ConnectionDAO.sqlSelect(query.toString());
			int size = outerList.size();
		
			for (int i = 0; i < size; i++) {
				
				innerList = (ArrayList) outerList.get(i);
				if (innerList.size() > 0) {
					
					vo = new CustomerServiceVo();
					
					vo.setLoanNo((CommonFunction.checkNull(innerList.get(0)).trim()));
					vo.setLoanStatus((CommonFunction.checkNull(innerList.get(1)).trim()));
					vo.setDisbursalStatus((CommonFunction.checkNull(innerList.get(2)).trim()));
					
					vo.setFirstEmi((CommonFunction.checkNull(innerList.get(3)).trim()));
					
					vo.setLastEmi((CommonFunction.checkNull(innerList.get(4)).trim()));
					
					vo.setCustomerRole((CommonFunction.checkNull(innerList.get(5)).trim()));
					vo.setLoanBalancePrincipal((CommonFunction.checkNull(innerList.get(6)).trim()));
					vo.setLoanOverdueAmount((CommonFunction.checkNull(innerList.get(7)).trim()));
					vo.setOverdueCharges((CommonFunction.checkNull(innerList.get(8)).trim()));
					vo.setLoanDpd((CommonFunction.checkNull(innerList.get(9)).trim()));
					vo.setNpaFlag((CommonFunction.checkNull(innerList.get(10)).trim()));
					vo.setRepoFlag((CommonFunction.checkNull(innerList.get(11)).trim()));
					vo.setLegalFlag((CommonFunction.checkNull(innerList.get(12)).trim()));
					vo.setSoaLink("<a href='#' onclick=statementOfAccountAtCustomerExposure('"+(CommonFunction.checkNull(innerList.get(14)).trim())+"')>"+(CommonFunction.checkNull(innerList.get(13)))+"</a>");
					vo.setLoanId((CommonFunction.checkNull(innerList.get(14)).trim()));
					vo.setCustomerName((CommonFunction.checkNull(innerList.get(15)).trim()));
					list.add(vo);
					
				}

				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			vo=null;
			query = null; 
			innerList=null;
			outerList=null;
			
		}
		return list;
	}
	
	//Nishant space starts
	public ArrayList<CustomerServiceVo> vehicleDetails(CustomerServiceVo ob) {
		
		logger.info("In vehicleDetails() loan id: "+ob.getLoanId());
		CustomerServiceVo vo = null;
		ArrayList list = new ArrayList();
		StringBuilder query = new StringBuilder(); 
		ArrayList outerList = new ArrayList();
		ArrayList innerList = new ArrayList();
		try {
		query.append("SELECT CASE WHEN C.ASSET_NEW_OLD='N' THEN 'NEW ' WHEN C.ASSET_NEW_OLD='O' THEN 'OLD ' ELSE '' END AS ASSET_NATURE,C.VEHICLE_MAKE, C.VEHICLE_MODEL,C.ASSET_MANUFATURER_DESC,C.ASSET_SUPPLIER_DESC,  C.VEHICLE_REGISTRATION_NO, C.VEHICLE_CHASIS_NUMBER,C.ENGINE_NUMBER,C.VEHICLE_MANUFACTURING_YEAR " );
		query.append(" FROM CR_LOAN_DTL A ");
		query.append(" JOIN CR_LOAN_COLLATERAL_M B ON A.LOAN_ID=B.LOAN_ID ");
		query.append(" JOIN CR_ASSET_COLLATERAL_M C ON C.ASSET_ID=B.ASSETID AND C.ASSET_COLLATERAL_CLASS='VEHICLE' ");
		query.append(" WHERE A.LOAN_ID='"+CommonFunction.checkNull(ob.getLoanId())+"' ");
		
		logger.info(" vehicleDetails query  :  " + query.toString());
		
		
		    outerList = ConnectionDAO.sqlSelect(query.toString());
			int size = outerList.size();
		
			for (int i = 0; i < size; i++) {
				
				innerList = (ArrayList) outerList.get(i);
				if (innerList.size() > 0) {
					
					vo = new CustomerServiceVo();
					
					vo.setAssetNature((CommonFunction.checkNull(innerList.get(0)).trim()));
					vo.setVehicleMake((CommonFunction.checkNull(innerList.get(1)).trim()));
					vo.setVehicleModel((CommonFunction.checkNull(innerList.get(2)).trim()));
					vo.setManufact((CommonFunction.checkNull(innerList.get(3)).trim()));
					vo.setSupplier((CommonFunction.checkNull(innerList.get(4)).trim()));
					vo.setRegistrationNumber((CommonFunction.checkNull(innerList.get(5)).trim()));
					vo.setChasisNumber((CommonFunction.checkNull(innerList.get(6)).trim()));
					vo.setEngineNumber((CommonFunction.checkNull(innerList.get(7)).trim()));
					vo.setYearofManufacture((CommonFunction.checkNull(innerList.get(8)).trim()));
					list.add(vo);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			vo=null;
			query = null; 
			innerList=null;
			outerList=null;
			
		}
		return list;
	}
	//Nishant space end

	@Override
	public ArrayList<CustomerServiceVo> notePadListLoanSummaryDetail(CustomerServiceVo ob) {
		
		ArrayList list=new ArrayList();
		CreditProcessingNotepadVo noteVO=null;
		try{
		
		  String dealIdQuery="SELECT LOAN_DEAL_ID FROM CR_LOAN_DTL WHERE LOAN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getLoanId()))+"'";
		  String dealId=ConnectionDAO.singleReturn(dealIdQuery);
		  dealIdQuery=null;
		  String leadIdQuery="SELECT LEAD_ID FROM CR_DEAL_DTL WHERE DEAL_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId))+"'";
		  String leadId=ConnectionDAO.singleReturn(leadIdQuery);
		  leadIdQuery=null;
		  StringBuilder query=new StringBuilder();
			
		  query.append("select distinct b.description ,DATE_FORMAT(notepad_date,'"+dateFormatWithTime+"'),person_met,meeting_location,if(followup_reqd='N','NO','YES') as followup_reqd,"+
						" DATE_FORMAT(followup_date,'"+dateFormatWithTime+"'),followup_location,notepad_comments,followup_remarks,FOLLOWUP_PERSON, " +
						" s.USER_NAME,DATE_FORMAT(a.maker_date,'"+dateFormatWithTime+"'),'LOAN INITIATION MAKER' from cr_notepad_dtl a,generic_master b,sec_user_m s"
						+" where notepad_code=b.value and " +
						"txn_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(ob.getLoanId())).trim()+"' and TXN_TYPE='LIM'" +
						" and a.MAKER_ID=s.USER_ID ");
		  query.append(" UNION ALL ");
		  query.append("select distinct b.description ,DATE_FORMAT(notepad_date,'"+dateFormatWithTime+"'),person_met,meeting_location,if(followup_reqd='N','NO','YES') as followup_reqd,"+
					" DATE_FORMAT(followup_date,'"+dateFormatWithTime+"'),followup_location,notepad_comments,followup_remarks,FOLLOWUP_PERSON, " +
					" s.USER_NAME,DATE_FORMAT(a.maker_date,'"+dateFormatWithTime+"'),'DEAL CAPTURE' from cr_notepad_dtl a,generic_master b,sec_user_m s"
					+" where notepad_code=b.value and " +
					"txn_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).trim()+"' and TXN_TYPE='DC'" +
					" and a.MAKER_ID=s.USER_ID ");
		  query.append(" UNION ALL ");
		  query.append("select distinct b.description ,DATE_FORMAT(notepad_date,'"+dateFormatWithTime+"'),person_met,meeting_location,if(followup_reqd='N','NO','YES') as followup_reqd,"+
					" DATE_FORMAT(followup_date,'"+dateFormatWithTime+"'),followup_location,notepad_comments,followup_remarks,FOLLOWUP_PERSON, " +
					" s.USER_NAME,DATE_FORMAT(a.maker_date,'"+dateFormatWithTime+"'),'LEAD TRACKING' from cr_notepad_dtl a,generic_master b,sec_user_m s"
					+" where notepad_code=b.value and " +
					"txn_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(leadId)).trim()+"' and TXN_TYPE='LT' " +
					" and a.MAKER_ID=s.USER_ID ");
		  query.append(" UNION ALL ");
		  query.append("select distinct b.description ,DATE_FORMAT(notepad_date,'"+dateFormatWithTime+"'),person_met,meeting_location,if(followup_reqd='N','NO','YES') as followup_reqd,"+
					" DATE_FORMAT(followup_date,'"+dateFormatWithTime+"'),followup_location,notepad_comments,followup_remarks,FOLLOWUP_PERSON, " +
					" s.USER_NAME,DATE_FORMAT(a.maker_date,'"+dateFormatWithTime+"'),'LEAD PROCESSING' from cr_notepad_dtl a,generic_master b,sec_user_m s"
					+" where notepad_code=b.value and " +
					"txn_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(leadId)).trim()+"' and TXN_TYPE ='LP'" +
					" and a.MAKER_ID=s.USER_ID ");
		
		logger.info("In getNotepadData"+query.toString());
		
		
		ArrayList product = ConnectionDAO.sqlSelect(query.toString());			
	
		query=null;
		dealId=null;
		leadId=null;
		
		for(int i=0;i<product.size();i++){
		
			ArrayList data=(ArrayList)product.get(i);
			if(data.size()>0){
				noteVO = new CreditProcessingNotepadVo();
				noteVO.setNoteCodeDescription((CommonFunction.checkNull(data.get(0))).trim());
				noteVO.setMeetingDate((CommonFunction.checkNull(data.get(1))).trim());
				noteVO.setPersonMet((CommonFunction.checkNull(data.get(2))).trim());
				noteVO.setMeetingLocation((CommonFunction.checkNull(data.get(3))).trim());
				noteVO.setFollowUp((CommonFunction.checkNull(data.get(4))).trim());
				noteVO.setFollowupDate((CommonFunction.checkNull(data.get(5))).trim());
				noteVO.setFollowUpLocation((CommonFunction.checkNull(data.get(6))).trim());
				noteVO.setMeetingRemarks((CommonFunction.checkNull(data.get(7))).trim());
				noteVO.setFollowupRemarks((CommonFunction.checkNull(data.get(8))).trim());
				noteVO.setFollowUpPerson((CommonFunction.checkNull(data.get(9))).trim());
				noteVO.setUserName((CommonFunction.checkNull(data.get(10))).trim());
				noteVO.setCreationDate((CommonFunction.checkNull(data.get(11))).trim());
				noteVO.setTxnType((CommonFunction.checkNull(data.get(12))).trim());
				
				list.add(noteVO);
			}
			data.clear();
			data=null;
		}
		product=null;
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			ob=null;
			noteVO=null;
		}
	   return list;
	}

}
