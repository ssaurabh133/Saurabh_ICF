package com.cp.daoImplMYSQL;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.VO.CustomerSaveVo;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.ConnectionDAOforEJB;
import com.connect.PrepStmtObject;
import com.cp.dao.FileUplaodDao;
import com.cp.util.ValidationUtility;
import com.cp.vo.BankDetailsVO;
import com.cp.vo.CibilObligationVo;
import com.cp.vo.CommonXlsVo;
import com.cp.vo.UnderwritingDocUploadVo;

public class FileUploadDaoImpl implements FileUplaodDao {
	private static final Logger logger = Logger
			.getLogger(FileUploadDaoImpl.class.getName());
	ResourceBundle resource = ResourceBundle
			.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
	String dateFormat = resource.getString("lbl.dateInDao");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no = Integer.parseInt(resource.getString("msg.pageSizeForMaster"));

	public boolean saveBankingDetailsPdf(Object ob, ArrayList hearderDetails, ArrayList valueDetails) 
	{
		UnderwritingDocUploadVo vo = (UnderwritingDocUploadVo) ob;
		ArrayList qryList = new ArrayList();
		boolean status=false;

		try 
		{
		String qryLimit= "select max(document_id) from cr_uploaded_documents for update" ;
		String documentId = ConnectionDAO.singleReturn(qryLimit.toString());
		
		logger.info("query................."+qryLimit.toString());
		
		vo.setDocId(documentId);
		
		/*for (Map.Entry<String, Map<String, String>> entry : map.entrySet()) 
		{
			qryList = bankingDetailsData(qryList, map.get(entry.getKey()),vo);
		}
		*/
		status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
		logger.info("In saveBankingDetails......................"
					+ status);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public ArrayList profitAndLossSheetData1(ArrayList list, Object ob,
			String paramCode, String paramValue) {
		CustomerSaveVo vo = (CustomerSaveVo) ob;
		boolean status = false;
/*
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String source = vo.getSource();
		String customerId = "";
		try {
			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql
					.append("insert into cr_financial_data_temp(deal_ID,SOURCE_TYPE,PARAM_NAME,FINANCIAL_YEAR,FINANCIAL_MONTH,PARAM_VALUE,deal_CUSTOMER_ROLE_TYPE,deal_CUSTOMER_ID,VERIFICATION_METHOD,INCOME_SOURCE_TYPE,REMARKS,REC_STATUS,FORMAT_TYPE,DOCUMENT_ID,MAKER_ID,MAKER_DATE)");
			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?  ");
			bufInsSql.append(")");

			if (CommonFunction.checkNull(vo.getAddr_type()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getAddr_type()).trim());

			insertPrepStmtObject.addString(("P").trim());

			if (CommonFunction.checkNull(paramCode).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addString((paramCode).trim());
			else
				insertPrepStmtObject.addString((paramCode).trim());

			if (CommonFunction.checkNull(vo.getAddr1()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getAddr1()).trim());

			if (CommonFunction.checkNull(vo.getAddr3()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getAddr3()).trim());

			if (CommonFunction.checkNull(paramValue).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((paramValue).trim());

			if (CommonFunction.checkNull(vo.getTxtCountryCode()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getTxtCountryCode()).trim());

			if (CommonFunction.checkNull(vo.getTxtStateCode()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getTxtStateCode()).trim());

			if (CommonFunction.checkNull(vo.getTxtDistCode()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getTxtDistCode()).trim());

			if (CommonFunction.checkNull(vo.getPincode()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getPincode()).trim());

			if (CommonFunction.checkNull(vo.getPrimaryPhoneNo()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getPrimaryPhoneNo()).trim());

			if (CommonFunction.checkNull(vo.getAlternatePhoneNo()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getAlternatePhoneNo())
						.trim());

			if (CommonFunction.checkNull(vo.getTollfreeNo()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getTollfreeNo()).trim());

			if (CommonFunction.checkNull(vo.getFaxNo()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getFaxNo()).trim());
			
			

			// ---------------------------------------------------------

			insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("IN profitAndLossSheetData()   insert query1 ### "
					+ insertPrepStmtObject.printQuery());
			list.add(insertPrepStmtObject);

		} catch (Exception e) {
			e.printStackTrace();
		}*/

		return list;
	}

	public ArrayList bankingDetailsPdfData(ArrayList list, Map<String, String> map,Object ob) {
		com.cp.vo.UnderwritingDocUploadVo vo = (UnderwritingDocUploadVo) ob;
		boolean status = false;
		vo.setFormDate("2015-10-10");
		boolean flag = false;
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		try {
			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql
					.append("insert into cr_bank_stmnt_dtl_temp(deal_ID,DOCUMENT_ID,deal_no,ENTITY_TYPE,ENTITY_ID,BANK_NAME,BANK_BRANCH,ACCOUNT_NO,ACCOUNT_TYPE,"
							+ "STATEMENT_YEAR,STATEMENT_MONTH,STATMENT_DAY,TRANSFER_INWARD_OUTWARD,BOUNCING_INWARD_OUTWARD,IGNORE_FLAG,REJECT_REASON,MAKER_ID,MAKER_DATE,LAST_UPDATED_BY,LAST_UPDATE_DATE,"
							+ "DOCUMENT_SEQ_ID,STATMENT_DATE,TYPE,NARRATION,TOTAL_DR,"
							+ "TOTAL_CR,BALANCE_AMOUNT,BRANCH_CODE,CHEQUE_NO,TRANSACTION_ID,VALUE_DATE,Transaction_Amount,AUTO_SWEEP,REVERSE_SWEEP)");
			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?,");//deal_id
			bufInsSql.append(" ?,");//DOCUMENT_ID
			
			bufInsSql.append(" ?,");//deal_no
			
			bufInsSql.append(" ?,");//ENTITY_TYPE
			bufInsSql.append(" ?,");//ENTITY_ID
			bufInsSql.append(" ?,");//BANK_NAME
			bufInsSql.append(" ?,");//BANK_BRANCH
			bufInsSql.append(" ?,");//ACCOUNT_NO
			bufInsSql.append(" ?,");//ACCOUNT_TYPE
			bufInsSql.append(" ?,");//STATEMENT_YEAR
			bufInsSql.append(" ?,");//STATEMENT_MONTH
			bufInsSql.append(" ?,");//STATMENT_DAY
			
			
			
			bufInsSql.append(" ?,");//TRANSFER_INWARD_OUTWARD
			bufInsSql.append(" ?,");//BOUNCING_INWARD_OUTWARD
			bufInsSql.append(" ?,");//IGNORE_FLAG
			bufInsSql.append(" ?,");//REMARKS
			bufInsSql.append(" ?,");//MAKER_ID
			bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '" + dateFormatWithTime
					+ "'),INTERVAL CURTIME() HOUR_SECOND) , ");	 //MAKER_DATE
			bufInsSql.append(" ?,");//last_update_by
			bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '" + dateFormatWithTime
					+ "'),INTERVAL CURTIME() HOUR_SECOND),  ");//last_update_DATE
			
			bufInsSql.append(" ?,");//DOCUMENT_SEQ_ID
			bufInsSql.append(" ?,  "); //STATMENT_DATE
			
			bufInsSql.append(" ?,");//type
			bufInsSql.append(" ?,");//NARRATION
			bufInsSql.append(" ?,");//TOTAL_DR
			bufInsSql.append(" ?,");//TOTAL_CR
			
			bufInsSql.append(" ?,");//BALANCE_AMOUNT
			
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,"); //AUTO_SWEEP
			bufInsSql.append(" ?"); //REVERSE_SWEEP
			
			bufInsSql.append(")"); 
			
			if (CommonFunction.checkNull(vo.getCaseId()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getCaseId()).trim());
			
			if (CommonFunction.checkNull(vo.getDocId()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getDocId()).trim());
			
			if (CommonFunction.checkNull(vo.getRefId()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getRefId()).trim());


			if (CommonFunction.checkNull(vo.getDocEntity()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getDocEntity()).trim());

			if (CommonFunction.checkNull(vo.getEntityCustomerId()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();  // entity id
			else
				insertPrepStmtObject.addString((vo.getEntityCustomerId()).trim());

			if (CommonFunction.checkNull(vo.getBankId()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getBankId()).trim());

			if (CommonFunction.checkNull(vo.getBankBranch()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getBankBranch()).trim());

			if (CommonFunction.checkNull(vo.getAccountNo()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull(); // ACCOUNT_NO
			else
				insertPrepStmtObject.addString((vo.getAccountNo()).trim());

			if (CommonFunction.checkNull(vo.getAccountType()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getAccountType()).trim());

			if (CommonFunction.checkNull(vo.getFormDate().substring(0,4)).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getFormDate().substring(0,4)).trim());

			if (CommonFunction.checkNull(vo.getFormDate().substring(5,7)).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getFormDate().substring(5,7)).trim());

			if (CommonFunction.checkNull(vo.getFormDate().substring(8,10)).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getFormDate().substring(8,10)).trim());

			if (CommonFunction.checkNull(vo.getTransferInwardOutward()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getTransferInwardOutward())
						.trim());

			if (CommonFunction.checkNull(vo.getBouncingInwardOutward()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getBouncingInwardOutward()).trim());

			if (CommonFunction.checkNull(vo.getIgnoreFlag()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getIgnoreFlag()).trim());
			
			if (CommonFunction.checkNull(vo.getRemarks()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getRemarks()).trim());
			
			if (CommonFunction.checkNull(vo.getMakerId()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getMakerId()).trim());
			
			if (CommonFunction.checkNull(vo.getMakerDate()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getMakerDate()).trim());
			
			if (CommonFunction.checkNull(vo.getLastUpdatedBY()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getLastUpdatedBY()).trim());
			
			if (CommonFunction.checkNull(vo.getLastUpdateDate()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getLastUpdateDate()).trim());
			
			
			System.out.println(map);
			
			
				String commonValue ="";
				flag = false;
				
				
				if(map.get("SEQ_ID")!=null)
				{
					insertPrepStmtObject.addString(CommonFunction.checkNull(map.get("SEQ_ID")));
				}
				else
				{
					insertPrepStmtObject.addNull();
				}
				
				
				if(map.get("TRANSACTION_DATE")!=null)
				{
					insertPrepStmtObject.addString(CommonFunction.checkNull(map.get("TRANSACTION_DATE")));
				}
				else
				{
					insertPrepStmtObject.addNull();
				}
				
				if(map.get("TYPE")!=null)
				{
					insertPrepStmtObject.addString(CommonFunction.checkNull(map.get("TYPE")));
				}
				else
				{
					insertPrepStmtObject.addNull();
				}
				
				if(map.get("NARRATION")!=null)
				{
					insertPrepStmtObject.addString(CommonFunction.checkNull(map.get("NARRATION")));
				}
				else
				{
					insertPrepStmtObject.addNull();
				}
				
				 if(map.get("TOTAL_DR")!=null)
				{
					 if(CommonFunction.checkNull(map.get("TOTAL_DR")).trim().equalsIgnoreCase("DR"))
						{
							System.out.println("TOTAL_CR1kjh----"+map.get("TOTAL_DR")+"-----"+map.get("Transaction_Amount"));
							insertPrepStmtObject.addString(CommonFunction.checkNull(map.get("Transaction_Amount")).trim());
							insertPrepStmtObject.addNull();
						}
					 else if(CommonFunction.checkNull(map.get("TOTAL_DR")).trim().equalsIgnoreCase("CR"))
						{
							System.out.println("TOTAL_CR1kjh----"+map.get("TOTAL_DR")+"-----"+map.get("Transaction_Amount"));
							insertPrepStmtObject.addNull();
							insertPrepStmtObject.addString(CommonFunction.checkNull(map.get("Transaction_Amount")).trim());
						}
					 else if(map.get("TOTAL_DR")!=null && map.get("TOTAL_CR")==null)
					 {
						if(map.get("TOTAL_DR").indexOf("-")==0)
						{
							insertPrepStmtObject.addString(CommonFunction.checkNull(map.get("TOTAL_DR")).trim());
							insertPrepStmtObject.addNull();
						}
						else
						{
							insertPrepStmtObject.addNull();
							insertPrepStmtObject.addString(CommonFunction.checkNull(map.get("TOTAL_DR")).trim());
							
						}
					 }
					 else
						{
						 System.out.println("TOTAL_CR1kfsdsfjh----"+map.get("TOTAL_DR")+"-----");
							insertPrepStmtObject.addString(CommonFunction.checkNull(map.get("TOTAL_DR")));
						}
					
				}
				 else
					 insertPrepStmtObject.addNull();
				
					
				 if(map.get("TOTAL_CR")!=null)
					{
						 if(CommonFunction.checkNull(map.get("TOTAL_CR")).trim().equalsIgnoreCase("CR"))
							{
								insertPrepStmtObject.addString(CommonFunction.checkNull(map.get("Transaction_Amount")).trim());
							}
						 else if(CommonFunction.checkNull(map.get("TOTAL_DR")).trim().equalsIgnoreCase("DR"))
							{
								insertPrepStmtObject.addNull();
							}
						 else
							{
								insertPrepStmtObject.addString(CommonFunction.checkNull(map.get("TOTAL_CR")));
							}
						
					}
					
				
				if(map.get("BALANCE_AMOUNT")!=null)
				{
					insertPrepStmtObject.addString(CommonFunction.checkNull(map.get("BALANCE_AMOUNT")));
				}else
				{insertPrepStmtObject.addNull();}
				
				
				
				
				if(map.get("BRANCH_CODE")!=null)
				{
					insertPrepStmtObject.addString(CommonFunction.checkNull(map.get("BRANCH_CODE")));
				}else
				{insertPrepStmtObject.addNull();}
				
				if(map.get("CHEQUE_NO")!=null)
				{
					insertPrepStmtObject.addString(CommonFunction.checkNull(map.get("CHEQUE_NO")));
				}else
				{insertPrepStmtObject.addNull();}
				
				
				if(map.get("TRANSACTION_ID")!=null)
				{
					insertPrepStmtObject.addString(CommonFunction.checkNull(map.get("TRANSACTION_ID")));
				}else
				{insertPrepStmtObject.addNull();}
				
				if(map.get("VALUE_DATE")!=null)
				{
					insertPrepStmtObject.addString(CommonFunction.checkNull(map.get("VALUE_DATE")));
				}else
				{insertPrepStmtObject.addNull();}
				if(map.get("Transaction_Amount")!=null)
				{
					insertPrepStmtObject.addString(CommonFunction.checkNull(map.get("Transaction_Amount")));
				}else
				{insertPrepStmtObject.addNull();}
				
				if(map.get("AUTO_SWEEP")!=null)
				{
					insertPrepStmtObject.addString(CommonFunction.checkNull(map.get("AUTO_SWEEP")));
				}else
				{insertPrepStmtObject.addNull();}
				
				if(map.get("REVERSE_SWEEP")!=null)
				{
					insertPrepStmtObject.addString(CommonFunction.checkNull(map.get("REVERSE_SWEEP")));
				}else
				{insertPrepStmtObject.addNull();}
				flag=true;
				
			/*}*/
			
		
				
			

			// ---------------------------------------------------------

			if (flag)
			{
			insertPrepStmtObject.setSql(bufInsSql.toString());
			/*logger.info("IN bankingDetailsData()   insert query1 ### "
					+ insertPrepStmtObject.printQuery());*/
			list.add(insertPrepStmtObject);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public boolean saveBalanceSheet(Object ob, ArrayList<CommonXlsVo> list) {
		UnderwritingDocUploadVo vo = (UnderwritingDocUploadVo)ob;
		System.out.println("size "+list.size());
		Iterator<CommonXlsVo> iterator = list.iterator();
		ArrayList qryList = new ArrayList();
		ArrayList outMessages = new ArrayList();
		String s1=null;
		String s2=null;
		//CustomerSaveVo vo = new CustomerSaveVo();// temporary vo
		logger.info("vo.getCustomer_id==="+vo.getCustomerId()+" vo.getCustomerRoletType "+vo.getCustomerRoleType());
		int counter = 0;
		boolean status = false;
		while (iterator.hasNext()) {
			CommonXlsVo commonXlsVo2 = (CommonXlsVo) iterator.next();
			
			counter++;
			balanceSheetData(qryList, vo,commonXlsVo2);
			
				
			
		}

		// start the code db insertion
		try {
			 status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("In saveBalanceSheet......................"
					+ status);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
		
	}

	@Override
	public boolean saveProfitAndLossSheet(Object ob, ArrayList<CommonXlsVo> list) {
		UnderwritingDocUploadVo Vo = (UnderwritingDocUploadVo)ob;
		Iterator<CommonXlsVo> iterator = list.iterator();
		ArrayList qryList = new ArrayList();
		CustomerSaveVo vo = new CustomerSaveVo();// temporary vo
		int counter = 0;
		boolean status = false;
		/*while (iterator.hasNext()) {
			CommonXlsVo commonXlsVo2 = (CommonXlsVo) iterator.next();
			String[] profitSheet = commonXlsVo2.getProfitSheet();
			String[] lossSheet = commonXlsVo2.getLossSheet();
			counter++;
			if (counter > 3) {
				if (profitSheet != null) {

					// System.out.println("Profit Sheet Data"+profitSheet[0]+"=="+profitSheet[1]);
					profitAndLossSheetData(qryList, vo, profitSheet[0],
							profitSheet[1]);
				}
				if (lossSheet != null) {

					// System.out.println("Loss Sheet Data "+lossSheet[0]+"=="+lossSheet[1]);
					profitAndLossSheetData(qryList, vo, lossSheet[0],
							lossSheet[1]);
				}
			}
		}

		// start the code db insertion
		try {
			 status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("In saveProfitAndLossSheet......................"
					+ status);
		} catch (Exception e) {
			e.printStackTrace();
		}
*/
		return status;
	}

	@Override
	public boolean saveBankingDetails(Object ob, Map<String, Map<String, String>> map) 
	{
		UnderwritingDocUploadVo vo = (UnderwritingDocUploadVo) ob;
		ArrayList qryList = new ArrayList();
		boolean status=false;

		try 
		{
		String qryLimit= "select max(document_id) from cr_uploaded_documents for update" ;
		String documentId = ConnectionDAO.singleReturn(qryLimit.toString());
		
		logger.info("query................."+qryLimit.toString());
		
		vo.setDocId(documentId);
		
		for (Map.Entry<String, Map<String, String>> entry : map.entrySet()) 
		{
			qryList = bankingDetailsData(qryList, map.get(entry.getKey()),vo);
		}
		
		status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
		logger.info("In saveBankingDetails......................"
					+ status);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public ArrayList profitAndLossSheetData(ArrayList list, Object ob,
			String paramCode, String paramValue) {
		CustomerSaveVo vo = (CustomerSaveVo) ob;
		boolean status = false;
/*
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String source = vo.getSource();
		String customerId = "";
		try {
			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql
					.append("insert into cr_financial_data_temp(deal_ID,SOURCE_TYPE,PARAM_NAME,FINANCIAL_YEAR,FINANCIAL_MONTH,PARAM_VALUE,deal_CUSTOMER_ROLE_TYPE,deal_CUSTOMER_ID,VERIFICATION_METHOD,INCOME_SOURCE_TYPE,REMARKS,REC_STATUS,FORMAT_TYPE,DOCUMENT_ID,MAKER_ID,MAKER_DATE)");
			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?  ");
			bufInsSql.append(")");

			if (CommonFunction.checkNull(vo.getAddr_type()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getAddr_type()).trim());

			insertPrepStmtObject.addString(("P").trim());

			if (CommonFunction.checkNull(paramCode).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addString((paramCode).trim());
			else
				insertPrepStmtObject.addString((paramCode).trim());

			if (CommonFunction.checkNull(vo.getAddr1()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getAddr1()).trim());

			if (CommonFunction.checkNull(vo.getAddr3()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getAddr3()).trim());

			if (CommonFunction.checkNull(paramValue).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((paramValue).trim());

			if (CommonFunction.checkNull(vo.getTxtCountryCode()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getTxtCountryCode()).trim());

			if (CommonFunction.checkNull(vo.getTxtStateCode()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getTxtStateCode()).trim());

			if (CommonFunction.checkNull(vo.getTxtDistCode()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getTxtDistCode()).trim());

			if (CommonFunction.checkNull(vo.getPincode()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getPincode()).trim());

			if (CommonFunction.checkNull(vo.getPrimaryPhoneNo()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getPrimaryPhoneNo()).trim());

			if (CommonFunction.checkNull(vo.getAlternatePhoneNo()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getAlternatePhoneNo())
						.trim());

			if (CommonFunction.checkNull(vo.getTollfreeNo()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getTollfreeNo()).trim());

			if (CommonFunction.checkNull(vo.getFaxNo()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getFaxNo()).trim());
			
			

			// ---------------------------------------------------------

			insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("IN profitAndLossSheetData()   insert query1 ### "
					+ insertPrepStmtObject.printQuery());
			list.add(insertPrepStmtObject);

		} catch (Exception e) {
			e.printStackTrace();
		}*/

		return list;
	}

	public ArrayList bankingDetailsData(ArrayList list, Map<String, String> map,Object ob) {
		com.cp.vo.UnderwritingDocUploadVo vo = (UnderwritingDocUploadVo) ob;
		boolean status = false;
		vo.setFormDate("2015-10-10");
		boolean flag = false;
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		try {
			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql
					.append("insert into cr_bank_stmnt_dtl_temp(deal_ID,DOCUMENT_ID,deal_no,ENTITY_TYPE,ENTITY_ID,BANK_NAME,BANK_BRANCH,ACCOUNT_NO,ACCOUNT_TYPE,"
							+ "STATEMENT_YEAR,STATEMENT_MONTH,STATMENT_DAY,TRANSFER_INWARD_OUTWARD,BOUNCING_INWARD_OUTWARD,IGNORE_FLAG,REJECT_REASON,MAKER_ID,MAKER_DATE,LAST_UPDATED_BY,LAST_UPDATE_DATE,"
							+ "DOCUMENT_SEQ_ID,STATMENT_DATE,TYPE,NARRATION,TOTAL_DR,"
							+ "TOTAL_CR,BALANCE_AMOUNT,BRANCH_CODE,CHEQUE_NO,TRANSACTION_ID,VALUE_DATE,Transaction_Amount,AUTO_SWEEP,REVERSE_SWEEP)");
			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?,");//deal_id
			bufInsSql.append(" ?,");//DOCUMENT_ID
			
			bufInsSql.append(" ?,");//deal_no
			
			bufInsSql.append(" ?,");//ENTITY_TYPE
			bufInsSql.append(" ?,");//ENTITY_ID
			bufInsSql.append(" ?,");//BANK_NAME
			bufInsSql.append(" ?,");//BANK_BRANCH
			bufInsSql.append(" ?,");//ACCOUNT_NO
			bufInsSql.append(" ?,");//ACCOUNT_TYPE
			bufInsSql.append(" ?,");//STATEMENT_YEAR
			bufInsSql.append(" ?,");//STATEMENT_MONTH
			bufInsSql.append(" ?,");//STATMENT_DAY
			
			
			
			bufInsSql.append(" ?,");//TRANSFER_INWARD_OUTWARD
			bufInsSql.append(" ?,");//BOUNCING_INWARD_OUTWARD
			bufInsSql.append(" ?,");//IGNORE_FLAG
			bufInsSql.append(" ?,");//REMARKS
			bufInsSql.append(" ?,");//MAKER_ID
			bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '" + dateFormatWithTime
					+ "'),INTERVAL CURTIME() HOUR_SECOND) , ");	 //MAKER_DATE
			bufInsSql.append(" ?,");//last_update_by
			bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '" + dateFormatWithTime
					+ "'),INTERVAL CURTIME() HOUR_SECOND),  ");//last_update_DATE
			
			bufInsSql.append(" ?,");//DOCUMENT_SEQ_ID
			bufInsSql.append(" ?,  "); //STATMENT_DATE
			
			bufInsSql.append(" ?,");//type
			bufInsSql.append(" ?,");//NARRATION
			bufInsSql.append(" ?,");//TOTAL_DR
			bufInsSql.append(" ?,");//TOTAL_CR
			
			bufInsSql.append(" ?,");//BALANCE_AMOUNT
			
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,"); //AUTO_SWEEP
			bufInsSql.append(" ?"); //REVERSE_SWEEP
			
			bufInsSql.append(")"); 
			
			if (CommonFunction.checkNull(vo.getCaseId()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getCaseId()).trim());
			
			if (CommonFunction.checkNull(vo.getDocId()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getDocId()).trim());
			
			if (CommonFunction.checkNull(vo.getRefId()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getRefId()).trim());


			if (CommonFunction.checkNull(vo.getDocEntity()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getDocEntity()).trim());

			if (CommonFunction.checkNull(vo.getEntityCustomerId()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();  // entity id
			else
				insertPrepStmtObject.addString((vo.getEntityCustomerId()).trim());

			if (CommonFunction.checkNull(vo.getBankId()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getBankId()).trim());

			if (CommonFunction.checkNull(vo.getBankBranch()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getBankBranch()).trim());

			if (CommonFunction.checkNull(vo.getAccountNo()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull(); // ACCOUNT_NO
			else
				insertPrepStmtObject.addString((vo.getAccountNo()).trim());

			if (CommonFunction.checkNull(vo.getAccountType()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getAccountType()).trim());

			if (CommonFunction.checkNull(vo.getFormDate().substring(0,4)).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getFormDate().substring(0,4)).trim());

			if (CommonFunction.checkNull(vo.getFormDate().substring(5,7)).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getFormDate().substring(5,7)).trim());

			if (CommonFunction.checkNull(vo.getFormDate().substring(8,10)).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getFormDate().substring(8,10)).trim());

			if (CommonFunction.checkNull(vo.getTransferInwardOutward()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getTransferInwardOutward())
						.trim());

			if (CommonFunction.checkNull(vo.getBouncingInwardOutward()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getBouncingInwardOutward()).trim());

			if (CommonFunction.checkNull(vo.getIgnoreFlag()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getIgnoreFlag()).trim());
			
			if (CommonFunction.checkNull(vo.getRemarks()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getRemarks()).trim());
			
			if (CommonFunction.checkNull(vo.getMakerId()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getMakerId()).trim());
			
			if (CommonFunction.checkNull(vo.getMakerDate()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getMakerDate()).trim());
			
			if (CommonFunction.checkNull(vo.getLastUpdatedBY()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getLastUpdatedBY()).trim());
			
			if (CommonFunction.checkNull(vo.getLastUpdateDate()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getLastUpdateDate()).trim());
			
			
			System.out.println(map);
			
			
				String commonValue ="";
				flag = false;
				
				
				if(map.get("SEQ_ID")!=null)
				{
					insertPrepStmtObject.addString(CommonFunction.checkNull(map.get("SEQ_ID")));
				}
				else
				{
					insertPrepStmtObject.addNull();
				}
				
				
				if(map.get("TRANSACTION_DATE")!=null)
				{
					insertPrepStmtObject.addString(CommonFunction.checkNull(map.get("TRANSACTION_DATE")));
				}
				else
				{
					insertPrepStmtObject.addNull();
				}
				
				if(map.get("TYPE")!=null)
				{
					insertPrepStmtObject.addString(CommonFunction.checkNull(map.get("TYPE")));
				}
				else
				{
					insertPrepStmtObject.addNull();
				}
				
				if(map.get("NARRATION")!=null)
				{
					insertPrepStmtObject.addString(CommonFunction.checkNull(map.get("NARRATION")));
				}
				else
				{
					insertPrepStmtObject.addNull();
				}
				
				 if(map.get("TOTAL_DR")!=null)
				{
					 if(CommonFunction.checkNull(map.get("TOTAL_DR")).trim().equalsIgnoreCase("DR"))
						{
							System.out.println("TOTAL_CR1kjh----"+map.get("TOTAL_DR")+"-----"+map.get("Transaction_Amount"));
							insertPrepStmtObject.addString(CommonFunction.checkNull(map.get("Transaction_Amount")).trim());
							insertPrepStmtObject.addNull();
						}
					 else if(CommonFunction.checkNull(map.get("TOTAL_DR")).trim().equalsIgnoreCase("CR"))
						{
							System.out.println("TOTAL_CR1kjh----"+map.get("TOTAL_DR")+"-----"+map.get("Transaction_Amount"));
							insertPrepStmtObject.addNull();
							insertPrepStmtObject.addString(CommonFunction.checkNull(map.get("Transaction_Amount")).trim());
						}
					 else if(map.get("TOTAL_DR")!=null && map.get("TOTAL_CR")==null)
					 {
						if(map.get("TOTAL_DR").indexOf("-")==0)
						{
							insertPrepStmtObject.addString(CommonFunction.checkNull(map.get("TOTAL_DR")).trim());
							insertPrepStmtObject.addNull();
						}
						else
						{
							insertPrepStmtObject.addNull();
							insertPrepStmtObject.addString(CommonFunction.checkNull(map.get("TOTAL_DR")).trim());
							
						}
					 }
					 else
						{
						 System.out.println("TOTAL_CR1kfsdsfjh----"+map.get("TOTAL_DR")+"-----");
							insertPrepStmtObject.addString(CommonFunction.checkNull(map.get("TOTAL_DR")));
						}
					
				}
				 else
					 insertPrepStmtObject.addNull();
				
					
				 if(map.get("TOTAL_CR")!=null)
					{
						 if(CommonFunction.checkNull(map.get("TOTAL_CR")).trim().equalsIgnoreCase("CR"))
							{
								insertPrepStmtObject.addString(CommonFunction.checkNull(map.get("Transaction_Amount")).trim());
							}
						 else if(CommonFunction.checkNull(map.get("TOTAL_DR")).trim().equalsIgnoreCase("DR"))
							{
								insertPrepStmtObject.addNull();
							}
						 else
							{
								insertPrepStmtObject.addString(CommonFunction.checkNull(map.get("TOTAL_CR")));
							}
						
					}
					
				
				if(map.get("BALANCE_AMOUNT")!=null)
				{
					insertPrepStmtObject.addString(CommonFunction.checkNull(map.get("BALANCE_AMOUNT")));
				}else
				{insertPrepStmtObject.addNull();}
				
				
				
				
				if(map.get("BRANCH_CODE")!=null)
				{
					insertPrepStmtObject.addString(CommonFunction.checkNull(map.get("BRANCH_CODE")));
				}else
				{insertPrepStmtObject.addNull();}
				
				if(map.get("CHEQUE_NO")!=null)
				{
					insertPrepStmtObject.addString(CommonFunction.checkNull(map.get("CHEQUE_NO")));
				}else
				{insertPrepStmtObject.addNull();}
				
				
				if(map.get("TRANSACTION_ID")!=null)
				{
					insertPrepStmtObject.addString(CommonFunction.checkNull(map.get("TRANSACTION_ID")));
				}else
				{insertPrepStmtObject.addNull();}
				
				if(map.get("VALUE_DATE")!=null)
				{
					insertPrepStmtObject.addString(CommonFunction.checkNull(map.get("VALUE_DATE")));
				}else
				{insertPrepStmtObject.addNull();}
				if(map.get("Transaction_Amount")!=null)
				{
					insertPrepStmtObject.addString(CommonFunction.checkNull(map.get("Transaction_Amount")));
				}else
				{insertPrepStmtObject.addNull();}
				
				if(map.get("AUTO_SWEEP")!=null)
				{
					insertPrepStmtObject.addString(CommonFunction.checkNull(map.get("AUTO_SWEEP")));
				}else
				{insertPrepStmtObject.addNull();}
				
				if(map.get("REVERSE_SWEEP")!=null)
				{
					insertPrepStmtObject.addString(CommonFunction.checkNull(map.get("REVERSE_SWEEP")));
				}else
				{insertPrepStmtObject.addNull();}
				flag=true;
				
			/*}*/
			
		
				
			

			// ---------------------------------------------------------

			if (flag)
			{
			insertPrepStmtObject.setSql(bufInsSql.toString());
			/*logger.info("IN bankingDetailsData()   insert query1 ### "
					+ insertPrepStmtObject.printQuery());*/
			list.add(insertPrepStmtObject);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList balanceSheetData(ArrayList list, Object ob,CommonXlsVo cVo) {
		UnderwritingDocUploadVo vo = (UnderwritingDocUploadVo) ob;
		boolean status = false;

		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		
		//String source = vo.getSource();
		String customerId = "";
		try {
			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql
					.append("insert into cr_financial_data_temp(deal_ID,SOURCE_TYPE,PARAM_NAME,FINANCIAL_YEAR,FINANCIAL_MONTH,PARAM_VALUE,deal_CUSTOMER_ROLE_TYPE,"
							+ "deal_CUSTOMER_ID,VERIFICATION_METHOD,INCOME_SOURCE_TYPE,REJECT_REASON,MAKER_ID,MAKER_DATE,NOTE,DOCUMENT_ID,FORMAT_TYPE)");
			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?,");
			bufInsSql.append(" ?");
			bufInsSql.append(")");

			if (CommonFunction.checkNull(vo.getDealId()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getDealId()).trim());

			insertPrepStmtObject.addString((vo.getSourceType()).trim());

			if (CommonFunction.checkNull(cVo.getParticulars()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((cVo.getParticulars()).trim());

			if (CommonFunction.checkNull(cVo.getYear()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((cVo.getYear()).trim());

			if (CommonFunction.checkNull(cVo.getMonth()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((cVo.getMonth()).trim());

			if (CommonFunction.checkNull(cVo.getReportingAmount()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((cVo.getReportingAmount()).trim());

			if (CommonFunction.checkNull(vo.getCustomerRoleType()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getCustomerRoleType()).trim());

			if (CommonFunction.checkNull(vo.getCustomerId()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getCustomerId()).trim());

			if (CommonFunction.checkNull(vo.getFieldVerificationUniqueId()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getFieldVerificationUniqueId()).trim());

			if (CommonFunction.checkNull(vo.getIncomeSouceType()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getIncomeSouceType()).trim());

			if (CommonFunction.checkNull(vo.getRemarks()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getRemarks()).trim());

			if (CommonFunction.checkNull(vo.getMakerId()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getMakerId())
						.trim());

			if (CommonFunction.checkNull(vo.getMakerDate()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getMakerDate()).trim());

			if (CommonFunction.checkNull(cVo.getNotes()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((cVo.getNotes()).trim());
			
			if (CommonFunction.checkNull(vo.getDocId()).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getDocId()).trim());
			if (CommonFunction.checkNull(vo.getFormatType() ).trim()
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((vo.getFormatType()).trim());
			
			// ---------------------------------------------------------

			insertPrepStmtObject.setSql(bufInsSql.toString());
			
			logger.info("IN balanceSheet data()   insert query1 ### "
					+ insertPrepStmtObject.printQuery());
			list.add(insertPrepStmtObject);
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public boolean updateWorkFlowRecords(Object ob,String queueName,String bgStatus, String stageId) {
		UnderwritingDocUploadVo vo = (UnderwritingDocUploadVo)ob;
		logger.info("in updateWorkFlowRecords ");
		String docId = vo.getDocId()==null?"":vo.getDealId();
		String qry = "update cr_deal_movement_dtl set BACK_GROUND_ACTIVITY = 1, queue_name='"+queueName+"', rec_status= '"+bgStatus+"' where deal_id = '"+vo.getCaseId()+"' and deal_stage_id='"+stageId+"'" ;
		ArrayList qryList = new ArrayList();
		PrepStmtObject prepStmtObject = new PrepStmtObject();
		prepStmtObject.setSql(qry);
		qryList.add(prepStmtObject);
		logger.info("update query "+qry);
		boolean status =false;
		try {
			status = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public ArrayList getBankingDetailsData(UnderwritingDocUploadVo uwDocVo) {
		logger.info("getBankingDetailsData");
		BankDetailsVO bankDetailsVO = null;
		  ArrayList<BankDetailsVO> list = new ArrayList<BankDetailsVO>();
		try {
			
			String qry = "SELECT STMNT_ENTRY_ID,DOCUMENT_ID,DOCUMENT_SEQ_ID,deal_no,ENTITY_TYPE,ENTITY_ID,BANK_NAME,BANK_BRANCH,"
				+ "ACCOUNT_NO,ACCOUNT_TYPE,STATEMENT_YEAR,STATEMENT_MONTH,STATMENT_DAY,STATMENT_DATE,TOTAL_CR,TOTAL_DR,"
				+ "BALANCE_AMOUNT,NARRATION,REJECT_REASON,UPLOAD_FLAG,MAKER_ID,"
				+ "MAKER_DATE,LAST_UPDATED_BY,LAST_UPDATE_DATE,type,SOL,BRANCH_CODE,CHEQUE_NO,TRANSACTION_ID,VALUE_DATE,"
				+ "TRANSACTION_AMOUNT,deal_ID,ERROR_FLAG_PDF,AUTO_SWEEP,REVERSE_SWEEP"
				+ " from "
				+ "cr_bank_stmnt_dtl_temp where DOCUMENT_ID='"+uwDocVo.getDocId()+"' and deal_no = '"+uwDocVo.getRefId()+"'";
				
					if(uwDocVo.getDateIncrementalOrder().equalsIgnoreCase("D"))
					{
						logger.info("uwDocVo.getDateIncrementalOrder()-----"+uwDocVo.getDateIncrementalOrder());
						qry = qry+" order by STMNT_ENTRY_ID desc "; 
					}
		  ArrayList bdetails = ConnectionDAO.sqlSelect(qry);
		  logger.info("getBankingDetailsData Query: "+qry);
		  logger.info("getBankingDetailsData: "+bdetails.size());
  	    
  	  qry=null;
  	    
	  	    for(int i=0;i<bdetails.size();i++)
	  	    {
	  	    	
	  	    	ArrayList bdetails1=(ArrayList)bdetails.get(i);
	  	    	if(bdetails1.size()>0)
	  			{
	  	    		bankDetailsVO =  new BankDetailsVO();
	  	    			bankDetailsVO.	  setStmntEntryid(CommonFunction.checkNull(bdetails1.get(0)));	
		  	    		bankDetailsVO.	  setDocumentId(CommonFunction.checkNull(bdetails1.get(1)));	
		  	    		bankDetailsVO.	  setDocumentSeqId(CommonFunction.checkNull(bdetails1.get(2)));	
		  	    		bankDetailsVO.	  setRefId(CommonFunction.checkNull(bdetails1.get(3)));	
		  	    		bankDetailsVO.	  setEntityType(CommonFunction.checkNull(bdetails1.get(4)));	
		  	    		bankDetailsVO.	  setEntityId(CommonFunction.checkNull(bdetails1.get(5)));	
		  	    		bankDetailsVO.	  setBankName(CommonFunction.checkNull(bdetails1.get(6)));	
		  	    		bankDetailsVO.	  setBankBranch(CommonFunction.checkNull(bdetails1.get(7)));	
		  	    		bankDetailsVO.	  setAccountNo(CommonFunction.checkNull(bdetails1.get(8)));	
		  	    		bankDetailsVO.	  setAccountType(CommonFunction.checkNull(bdetails1.get(9)));	
		  	    		bankDetailsVO.	  setStatementYear(CommonFunction.checkNull(bdetails1.get(10)));	
		  	    		bankDetailsVO.	  setStatementMonth(CommonFunction.checkNull(bdetails1.get(11)));	
		  	    		bankDetailsVO.	  setStatmentDay(CommonFunction.checkNull(bdetails1.get(12)));	
		  	    		bankDetailsVO.	  setStatmentDate(CommonFunction.checkNull(bdetails1.get(13)));	
		  	    		bankDetailsVO.	  setTotalCr(ValidationUtility.removeComma(CommonFunction.checkNull(bdetails1.get(14))));	
		  	    		bankDetailsVO.	  setTotalDr(ValidationUtility.removeComma(CommonFunction.checkNull(bdetails1.get(15))));	
		  	    		bankDetailsVO.	  setBalanceAmount(ValidationUtility.removeComma(CommonFunction.checkNull(bdetails1.get(16))));	
		  	    		bankDetailsVO.	  setNarration(CommonFunction.checkNull(bdetails1.get(17)));		
		  	    		bankDetailsVO.	  setRemarks(CommonFunction.checkNull(bdetails1.get(18)));
		  	    		bankDetailsVO.	  setUploadFlag(CommonFunction.checkNull(bdetails1.get(19)));
		  	    		bankDetailsVO.	  setMakerId(CommonFunction.checkNull(bdetails1.get(20)));	
		  	    		bankDetailsVO.	  setMakerDate(CommonFunction.checkNull(bdetails1.get(21)));	
		  	    		bankDetailsVO.	  setLastUpdatedBy(CommonFunction.checkNull(bdetails1.get(22)));	
		  	    		bankDetailsVO.	  setLastUpdateDate(CommonFunction.checkNull(bdetails1.get(23)));	
		  	    		bankDetailsVO.	  setType(CommonFunction.checkNull(bdetails1.get(24)));	
		  	    		bankDetailsVO.	  setSol(CommonFunction.checkNull(bdetails1.get(25)));	
		  	    		bankDetailsVO.	  setBranchCode(CommonFunction.checkNull(bdetails1.get(26)));	
		  	    		bankDetailsVO.	  setChequeNo(CommonFunction.checkNull(bdetails1.get(27)));	
		  	    		bankDetailsVO.	  setTransactionId(CommonFunction.checkNull(bdetails1.get(28)));	
		  	    		bankDetailsVO.	  setValueDate(CommonFunction.checkNull(bdetails1.get(29)));	
		  	    		bankDetailsVO.	  setTransactionAmount(CommonFunction.checkNull(bdetails1.get(30)));
		  	    		bankDetailsVO.	  setCaseId(CommonFunction.checkNull(bdetails1.get(31)));	
		  	    		bankDetailsVO.	  setErrorFlag(CommonFunction.checkNull(bdetails1.get(32)));
		  	    		bankDetailsVO.	  setAutoSweep(CommonFunction.checkNull(bdetails1.get(33)));
		  	    		bankDetailsVO.	  setReverseSweep(CommonFunction.checkNull(bdetails1.get(34)));

	  	    		
	  	    		
	  			}
	  	    	bdetails1.clear();
	  	    	bdetails1=null;
	  			list.add(bankDetailsVO);
			
	  	    }
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	@Override
	public boolean updateBankingDetailsTemp(ArrayList list) {
		logger.info("updateBankingDetailsTemp");
		ArrayList qryList = new ArrayList();
		boolean returnFlag = false;
		String query = "update cr_bank_stmnt_dtl_temp set UPLOAD_FLAG =?,REJECT_REASON=? where document_id=? and STMNT_ENTRY_ID=?";
		
		PrepStmtObject object = null;
		
		for(int i =0; i<list.size();i++)
		{
			BankDetailsVO vo = (BankDetailsVO)list.get(i);
			object = new PrepStmtObject();
			
			object.addString(vo.getUploadFlag());
			object.addString(vo.getRemarks());
			object.addString(vo.getDocumentId());
			object.addString(vo.getStmntEntryid());
			
			object.setSql(query);
			logger.info(object.printQuery());
			qryList.add(object);
			object=null;
		}
		try {
			returnFlag=  ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("result   "+returnFlag);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnFlag;
	}

	@Override
	public boolean saveBankingDetailsInMain(ArrayList list) {
		logger.info("saveBankingDetailsInMain");
		StringBuffer buffer =  new StringBuffer();
		buffer.append("INSERT  into cr_bank_stmnt_dtl(DOCUMENT_ID,DOCUMENT_SEQ_ID,deal_no,"
				+ "ENTITY_TYPE,ENTITY_ID,BANK_NAME,BANK_BRANCH,ACCOUNT_NO,ACCOUNT_TYPE,STATEMENT_YEAR,"
				+ "STATEMENT_MONTH,STATMENT_DAY,STATMENT_DATE,TOTAL_CR,TOTAL_DR,BALANCE_AMOUNT,NARRATION,"
				+ "MAKER_ID,MAKER_DATE,LAST_UPDATED_BY,LAST_UPDATE_DATE,type,SOL,BRANCH_CODE,"
				+ "CHEQUE_NO,TRANSACTION_ID,VALUE_DATE,TRANSACTION_AMOUNT,deal_ID,ERROR_FLAG_PDF,AUTO_SWEEP,REVERSE_SWEEP ) ");
		buffer.append(" values (");
		buffer.append(" ?,");		//	DOCUMENT_ID
		buffer.append(" ?,");		//	DOCUMENT_SEQ_ID
		buffer.append(" ?,");		//	deal_no
		buffer.append(" ?,");		//	ENTITY_TYPE
		buffer.append(" ?,");		//	ENTITY_ID
		buffer.append(" ?,");		//	BANK_NAME
		buffer.append(" ?,");		//	BANK_BRANCH
		buffer.append(" ?,");		//	ACCOUNT_NO
		buffer.append(" ?,");		//	ACCOUNT_TYPE
		buffer.append(" ?,");		//	STATEMENT_YEAR
		buffer.append(" ?,");		//	STATEMENT_MONTH
		buffer.append(" ?,");		//	STATMENT_DAY
		buffer.append(" ?,");		//	STATMENT_DATE
		buffer.append(" ?,");		//	TOTAL_CR
		buffer.append(" ?,");		//	TOTAL_DR
		buffer.append(" ?,");		//	BALANCE_AMOUNT
		buffer.append(" ?,");		//	NARRATION
		//buffer.append(" ?,");		//	REMARKS
		buffer.append(" ?,");		//	MAKER_ID
		buffer.append(" ?,");		//	MAKER_DATE
		buffer.append(" ?,");		//	LAST_UPDATED_BY
		buffer.append(" ?,");		//	LAST_UPDATE_DATE
		buffer.append(" ?,");		//	type
		buffer.append(" ?,");		//	SOL
		buffer.append(" ?,");		//	BRANCH_CODE
		buffer.append(" ?,");		//	CHEQUE_NO
		buffer.append(" ?,");		//	TRANSACTION_ID
		buffer.append(" ?,");		//	VALUE_DATE
		buffer.append(" ?,");		//	TRANSACTION_AMOUNT
		buffer.append(" ?,");		//	Case ID
		buffer.append(" ?,");		//	Error_flag_pdf
		buffer.append(" ?,");		//AUTO_SWEEP
		buffer.append(" ?");		//REVERSE_SWEEP


		buffer.append(" )");
		ArrayList qryList = new ArrayList();
		PrepStmtObject object = null;
		boolean returnFlag = false;
		for(int i =0; i<list.size();i++)
		{
			BankDetailsVO vo = (BankDetailsVO)list.get(i);
			object = new PrepStmtObject();
			
			if(!CommonFunction.checkNull(vo.getDocumentId()).equalsIgnoreCase(""))
				object.addString(vo.getDocumentId().trim());
			else 
				object.addNull();
			
			

				if(!CommonFunction.checkNull(vo.getDocumentSeqId()).equalsIgnoreCase(""))
					object.addString(vo.getDocumentSeqId().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getRefId()).equalsIgnoreCase(""))
					object.addString(vo.getRefId().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getEntityType()).equalsIgnoreCase(""))
					object.addString(vo.getEntityType().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getEntityId()).equalsIgnoreCase(""))
					object.addString(vo.getEntityId().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getBankName()).equalsIgnoreCase(""))
					object.addString(vo.getBankName().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getBankBranch()).equalsIgnoreCase(""))
					object.addString(vo.getBankBranch().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getAccountNo()).equalsIgnoreCase(""))
					object.addString(vo.getAccountNo().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getAccountType()).equalsIgnoreCase(""))
					object.addString(vo.getAccountType().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getStatementYear()).equalsIgnoreCase(""))
					object.addString(vo.getStatementYear().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getStatementMonth()).equalsIgnoreCase(""))
					object.addString(vo.getStatementMonth().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getStatmentDay()).equalsIgnoreCase(""))
					object.addString(vo.getStatmentDay().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getStatmentDate()).equalsIgnoreCase(""))
					object.addString(vo.getStatmentDate().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getTotalCr()).equalsIgnoreCase(""))
					object.addString(vo.getTotalCr().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getTotalDr()).equalsIgnoreCase(""))
					object.addString(vo.getTotalDr().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getBalanceAmount()).equalsIgnoreCase(""))
					object.addString(vo.getBalanceAmount().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getNarration()).equalsIgnoreCase(""))
					object.addString(vo.getNarration().trim());
				else 
					object.addNull();
				/*if(!CommonFunction.checkNull(vo.getRemarks()).equalsIgnoreCase(""))
					object.addString(vo.getRemarks().trim());
				else 
					object.addNull();*/
				if(!CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
					object.addString(vo.getMakerId().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
					object.addString(vo.getMakerDate().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getLastUpdatedBy()).equalsIgnoreCase(""))
					object.addString(vo.getLastUpdatedBy().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getLastUpdateDate()).equalsIgnoreCase(""))
					object.addString(vo.getLastUpdateDate().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getType()).equalsIgnoreCase(""))
					object.addString(vo.getType().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getSol()).equalsIgnoreCase(""))
					object.addString(vo.getSol().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getBranchCode()).equalsIgnoreCase(""))
					object.addString(vo.getBranchCode().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getChequeNo()).equalsIgnoreCase(""))
					object.addString(vo.getChequeNo().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getTransactionId()).equalsIgnoreCase(""))
					object.addString(vo.getTransactionId().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getValueDate()).equalsIgnoreCase(""))
					object.addString(vo.getValueDate().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getTransactionAmount()).equalsIgnoreCase(""))
					object.addString(vo.getTransactionAmount().trim());
				else 
					object.addNull();
				
				if(!CommonFunction.checkNull(vo.getCaseId()).equalsIgnoreCase(""))
					object.addString(vo.getCaseId().trim());
				else 
					object.addNull();
				
				if(!CommonFunction.checkNull(vo.getErrorFlag()).equalsIgnoreCase(""))
					object.addString(vo.getErrorFlag().trim());
				else 
					object.addNull();
				
				if(!CommonFunction.checkNull(vo.getAutoSweep()).equalsIgnoreCase(""))
					object.addString(vo.getAutoSweep().trim());
				else 
					object.addNull();
				
				if(!CommonFunction.checkNull(vo.getReverseSweep()).equalsIgnoreCase(""))
					object.addString(vo.getReverseSweep().trim());
				else 
					object.addNull();

			
			object.setSql(buffer.toString());
			logger.info(object.printQuery());
			qryList.add(object);
		}
		try {
			returnFlag=  ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("result----   "+returnFlag);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnFlag;
	}

	@Override
	public boolean checkBankDataisValidation(UnderwritingDocUploadVo uwDocVo) {
		logger.info("checkBankDataisValidation");
		boolean returnFlag=false;
		try {
			String qry ="select count(1) from cr_bank_stmnt_dtl_temp where document_id = '"+uwDocVo.getDocId()+"' and upload_flag = 'N' " ;
		returnFlag = ConnectionDAO.checkStatus(qry);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnFlag;
	}

	@Override
	public boolean saveErrorRecords(int row, int col,UnderwritingDocUploadVo uwDocVo) {
		logger.info("saveErrorRecords");
		boolean returnFlag = false;
		StringBuffer buffer =  new StringBuffer();
		buffer.append("insert into cr_upload_document_error_dtl (document_id,error_msg,error_flag,file_row,file_col)");
		buffer.append(" values (");
		buffer.append("?,");
		buffer.append("?,");
		buffer.append("?,");
		buffer.append("?,");
		buffer.append("?");
		buffer.append(")");
		ArrayList qryList = new ArrayList();
		PrepStmtObject object = new PrepStmtObject();
		if(!CommonFunction.checkNull(uwDocVo.getDocId()).equalsIgnoreCase(""))
		{
			object.addString(uwDocVo.getDocId());
		}
		else
		     object.addNull();
		
			object.addString(CommonFunction.checkNull(uwDocVo.getErrorMsg()));
			object.addString("E");
			object.addString(row+"");
			object.addString(col+"");
			object.setSql(buffer.toString());
			logger.info(object.printQuery());
			qryList.add(object);
			try {
				returnFlag = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
			} catch (Exception e) {
				// TODO: handle exception
			}
		return returnFlag; 
	}

	@Override
	public boolean deleteBankDetailsData(String refId, String makerId, String Doc) {
		logger.info("deleteBankDetailsData");
		boolean returnFlag = false;
		logger.info("Doc_id ================ "+Doc);
		String deleteTempQuery = "delete from cr_bank_stmnt_dtl_temp where deal_no='"+refId+"'  and DOCUMENT_ID = '"+Doc+"' ";
		String deleteQuery = "delete from cr_bank_stmnt_dtl where deal_no='"+refId+"'  and DOCUMENT_ID = '"+Doc+"' ";
		ArrayList deleteList = new ArrayList();
		deleteList.add(deleteTempQuery);
		deleteList.add(deleteQuery);
		try {
			returnFlag = ConnectionDAO.sqlInsUpdDelete(deleteList);
			if(returnFlag)
			{
				deleteList = null;
				deleteList = new ArrayList();
				String bankAnalysis = "delete from CR_BANK_ANALYSIS_DTL where  DOCUMENT_ID = '"+Doc+"' ";
				String obligationAnalysis = "delete from cr_obligation_analysis_dtl where  DOCUMENT_ID = '"+Doc+"' and source_type = 'BS'"; 
				deleteList.add(bankAnalysis);
				deleteList.add(obligationAnalysis);
				returnFlag = ConnectionDAO.sqlInsUpdDelete(deleteList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("deleteStatus------"+returnFlag);
		return returnFlag;
	}

	@Override
	public boolean deleteFinancialDetailsData(String caseId, String sourceType,	String makerId, String Doc) {
		logger.info("deleteFinancialDetailsData");
		boolean returnFlag = false;
		logger.info("Doc_id ================ "+Doc);
		String deleteTempQuery = "delete from cr_financial_data_temp where deal_id='"+caseId+"' and source_type='"+sourceType+"' and document_id = '"+Doc+"' ";
		String deleteQuery = "delete from cr_financial_data_dtl where deal_id='"+caseId+"' and source_type='"+sourceType+"' and document_id = '"+Doc+"' ";
		ArrayList deleteList = new ArrayList();
		deleteList.add(deleteTempQuery);
		deleteList.add(deleteQuery);
		try {
			returnFlag = ConnectionDAO.sqlInsUpdDelete(deleteList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("deleteStatus------"+returnFlag);
		return returnFlag;
	}

	@Override
	public boolean getVerificationAccountNumberUpload(String caseId,
			String customerId) {
		logger.info("getVerificationAccountNumberUpload------");
		String countQuery="Select count(1) from cr_classification_account_no_dtl where deal_id = '"+caseId+"' ";
		logger.info("countQuery----------"+countQuery);
		int countValue = Integer.parseInt(ConnectionDAO.singleReturn(countQuery));
		return countValue==0?false:true;
	}

	@Override
	public boolean saveRecordCibilObligation(ArrayList<CibilObligationVo> list) {
		logger.info("saveRecordCibilObligation----------");
		StringBuffer buffer = new StringBuffer();
		buffer.append("insert into CR_CIBIL_OBLIGATION_DTL_TEMP (deal_ID,deal_CUSTOMER_ID,DOCUMENT_ID,ENTITY_TYPE,"
				+ " CUST_ID_N,CUST_DETAIL,SCORE,MEMBER,ACC_TYPE,ACCNO,DPD,OWN_INDI,DATE_OPEN,DATE_LAST_PAY,"
				+ " DATE_REPORTED,DATE_CLOSED,SEN_AMT,CURR_BAL,AMT_OVERDUE,WRIT_OFF_STATUS,TENURE,COLLATERAL,COLLATERAL_TYPE,"
				+ " CREDIT_LIMIT,CASH_LIMIT,INTEREST_RATE,EMI_AMT,WRIT_OFF_PRIN_AMT,WRIT_OFF_TOTAL_AMT,SETTLEMENT_AMT,"
				+ " PAYMT_FREQUENCY,ACT_PAYMT_AMT,ERR_CODE,ERR_CODE_ENTRY_DATE,CBL_REM_ENTRY_DATE,CBL_REM_CODE,"
				+ " DISPUTE_RMK_ENTRY_DATE,DISPUTE_RMK1,DISPUTE_RMK2,SUITFILD,MAKER_ID,MAKER_DATE)");
		buffer.append(" values (");
		buffer.append(" ?,");
		buffer.append(" ?,");
		buffer.append(" ?,");
		buffer.append(" ?,");
		buffer.append(" ?,");
		buffer.append(" ?,");
		buffer.append(" ?,");
		buffer.append(" ?,");
		buffer.append(" ?,");
		buffer.append(" ?,");
		buffer.append(" ?,");
		buffer.append(" ?,");
		buffer.append(" ?,");
		buffer.append(" ?,");
		buffer.append(" ?,");
		buffer.append(" ?,");
		buffer.append(" ?,");
		buffer.append(" ?,");
		buffer.append(" ?,");
		buffer.append(" ?,");
		buffer.append(" ?,");
		buffer.append(" ?,");
		buffer.append(" ?,");
		buffer.append(" ?,");
		buffer.append(" ?,");
		buffer.append(" ?,");
		buffer.append(" ?,");
		buffer.append(" ?,");
		buffer.append(" ?,");
		buffer.append(" ?,");
		buffer.append(" ?,");
		buffer.append(" ?,");
		buffer.append(" ?,");
		buffer.append(" ?,");
		buffer.append(" ?,");
		buffer.append(" ?,");
		buffer.append(" ?,");
		buffer.append(" ?,");
		buffer.append(" ?,");
		buffer.append(" ?,");
		buffer.append(" ?,");
		buffer.append(" ?");

		buffer.append(" )");
		ArrayList qryList = new ArrayList();
		PrepStmtObject object = null;
		boolean returnFlag = false;
		String caseId="";
		for(int i =0; i<list.size();i++)
		{
			
			CibilObligationVo vo = list.get(i);
			caseId=vo.getCaseId();
			object = new PrepStmtObject();
			
				if(!CommonFunction.checkNull(vo.getCaseId()).equalsIgnoreCase(""))//getCaseId
					object.addString(vo.getCaseId().trim());
				else 
					object.addNull();
			
				if(!CommonFunction.checkNull(vo.getCaseCustomerId()).equalsIgnoreCase(""))//getCaseCustomerId
					object.addString(vo.getCaseCustomerId().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getDocumentId()).equalsIgnoreCase(""))//getDocumentId
					object.addString(vo.getDocumentId().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getEntityType()).equalsIgnoreCase(""))//ENTITY_TYPE
					object.addString(vo.getEntityType().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getCustIdN()).equalsIgnoreCase(""))//getCustIdN
					object.addString(vo.getCustIdN().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getCustDetail()).equalsIgnoreCase(""))//getCustDetail
					object.addString(vo.getCustDetail().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getScore()).equalsIgnoreCase(""))//getScore
					object.addString(vo.getScore().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getMember()).equalsIgnoreCase(""))//getMember
					object.addString(vo.getMember().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getAccType()).equalsIgnoreCase(""))//getAccType
					object.addString(vo.getAccType().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getAccno()).equalsIgnoreCase(""))//getAccno
					object.addString(vo.getAccno().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getDpd()).equalsIgnoreCase(""))//getDpd
					object.addString(vo.getDpd().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getOwnIndi()).equalsIgnoreCase(""))//getOwnIndi
					object.addString(vo.getOwnIndi().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getDateOpen()).equalsIgnoreCase(""))//getDateOpen
					object.addString(vo.getDateOpen().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getDateLastPay()).equalsIgnoreCase(""))//getDateLastPay
					object.addString(vo.getDateLastPay().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getDateReported()).equalsIgnoreCase(""))//getDateReported
					object.addString(vo.getDateReported().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getDateClosed()).equalsIgnoreCase(""))//getDateClosed
					object.addString(vo.getDateClosed().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getSenAmt()).equalsIgnoreCase(""))//getSenAmt
					object.addString(vo.getSenAmt().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getCurrBal()).equalsIgnoreCase(""))//getCurrBal
					object.addString(vo.getCurrBal().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getAmtOverdue()).equalsIgnoreCase(""))//getAmtOverdue
					object.addString(vo.getAmtOverdue().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getWritOffStatus()).equalsIgnoreCase(""))//getWritOffStatus
					object.addString(vo.getWritOffStatus().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getTenure()).equalsIgnoreCase(""))//getTenure
					object.addString(vo.getTenure().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getCollateral()).equalsIgnoreCase(""))//getCollateral
					object.addString(vo.getCollateral().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getCollateralType()).equalsIgnoreCase(""))//getCollateralType
					object.addString(vo.getCollateralType().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getCreditLimit()).equalsIgnoreCase(""))//getCreditLimit
					object.addString(vo.getCreditLimit().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getCashLimit()).equalsIgnoreCase(""))//getCashLimit
					object.addString(vo.getCashLimit().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getInterestRate()).equalsIgnoreCase(""))//getInterestRate
					object.addString(vo.getInterestRate().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getEmiAmt()).equalsIgnoreCase(""))//getEmiAmt
					object.addString(vo.getEmiAmt().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getWritOffPrinAmt()).equalsIgnoreCase(""))//getWritOffPrinAmt
					object.addString(vo.getWritOffPrinAmt().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getWritOffTotalAmt()).equalsIgnoreCase(""))//getWritOffTotalAmt
					object.addString(vo.getWritOffTotalAmt().trim());
				else 
					object.addNull();
				
				if(!CommonFunction.checkNull(vo.getSettlementAmt()).equalsIgnoreCase(""))//getSettlementAmt
					object.addString(vo.getSettlementAmt().trim());
				else 
					object.addNull();
				
				if(!CommonFunction.checkNull(vo.getPaymtFrequency()).equalsIgnoreCase(""))//getPaymtFrequency
					object.addString(vo.getPaymtFrequency().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getActPaymtAmt()).equalsIgnoreCase(""))//getActPaymtAmt
					object.addString(vo.getActPaymtAmt().trim());
				else 
					object.addNull();
				
				if(!CommonFunction.checkNull(vo.getErrCode()).equalsIgnoreCase(""))//getErrCode
					object.addString(vo.getErrCode().trim());
				else 
					object.addNull();
				
				if(!CommonFunction.checkNull(vo.getErrCodeEntryDate()).equalsIgnoreCase(""))//getErrCodeEntryDate
					object.addString(vo.getErrCodeEntryDate().trim());
				else 
					object.addNull();
				
				if(!CommonFunction.checkNull(vo.getCblRemEntryDate()).equalsIgnoreCase(""))//getCblRemEntryDate
					object.addString(vo.getCblRemEntryDate().trim());
				else 
					object.addNull();
				
				if(!CommonFunction.checkNull(vo.getCblRemCode()).equalsIgnoreCase(""))//getCblRemCode
					object.addString(vo.getCblRemCode().trim());
				else 
					object.addNull();
				
				if(!CommonFunction.checkNull(vo.getDisputeRmkEntryDate()).equalsIgnoreCase(""))//getDisputeRmkEntryDate
					object.addString(vo.getDisputeRmkEntryDate().trim());
				else 
					object.addNull();
				
				if(!CommonFunction.checkNull(vo.getDisputeRmk1()).equalsIgnoreCase(""))//getDisputeRmk1
					object.addString(vo.getDisputeRmk1().trim());
				else 
					object.addNull();
				
				if(!CommonFunction.checkNull(vo.getDisputeRmk2()).equalsIgnoreCase(""))//getDisputeRmk2
					object.addString(vo.getDisputeRmk2().trim());
				else 
					object.addNull();
				
				if(!CommonFunction.checkNull(vo.getSuitfild()).equalsIgnoreCase(""))//getSuitfild
					object.addString(vo.getSuitfild().trim());
				else 
					object.addNull();
				
				if(!CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))//getMakerId
					object.addString(vo.getMakerId().trim());
				else 
					object.addNull();
				
				if(!CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))//getMakerDate
					object.addString(vo.getMakerDate().trim());
				else 
					object.addNull();


			
			object.setSql(buffer.toString());
			logger.info(object.printQuery());
			qryList.add(object);
		}
		try {
			returnFlag=  ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("result----   "+returnFlag);
			if(returnFlag)
			{
			String insertObligationDtl = "insert into cr_obligation_analysis_dtl(deal_ID,SOURCE_TYPE,DOCUMENT_ID,deal_CUSTOMER_ID,deal_CUSTOMER_ROLE_TYPE,INSTITUTION_NAME,LOAN_NO,LOAN_TYPE,EMI,CLOSURE_DATE,FINANCED_AMOUNT,REC_STATUS,MAKER_ID,MAKER_DATE)"
					+ " select  distinct deal_ID,'CIBIL',DOCUMENT_ID,deal_CUSTOMER_ID,ENTITY_TYPE,MEMBER,ACCNO,ACC_TYPE,EMI_AMT,STR_TO_DATE(DATE_CLOSED,'%d/%m/%Y'),SEN_AMT ,'P',MAKER_ID, STR_TO_DATE(MAKER_DATE,'%d-%m-%Y')"
					+ "	from cr_cibil_obligation_dtl_temp where deal_id = '"+caseId+"'";
			qryList = null;
			qryList=new ArrayList();
			qryList.add(insertObligationDtl);
			returnFlag = ConnectionDAOforEJB.sqlInsUpdDelete(qryList);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnFlag;
	}

	@Override
	public boolean deleteCibilObligationRecord(String caseId, String documentId) {
		logger.info("deleteCibilObligationRecord");
		boolean flag=false;
		String deleteQuery = "delete from CR_CIBIL_OBLIGATION_DTL_TEMP where deal_id = '"+caseId+"' and document_id = '"+documentId+"'";
		String deleteQueryFromObligation = "delete from cr_obligation_analysis_dtl where deal_id = '"+caseId+"' and source_type = 'CIBIL'";
		ArrayList list = new ArrayList();
		list.add(deleteQuery);
		list.add(deleteQueryFromObligation);
		try {
		flag = 	ConnectionDAO.sqlInsUpdDelete(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return flag;
	}
	
	@Override
	public boolean deleteAccountLoanNumberRecord(String caseId) {
		logger.info("deleteAccountLoanNumberRecord");
		boolean flag=false;
		String deleteQuery = "delete from cr_classification_account_no_dtl where deal_id = '"+caseId+"' ";
		ArrayList list = new ArrayList();
		list.add(deleteQuery);
		try {
		flag = 	ConnectionDAO.sqlInsUpdDelete(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return flag;
	}
	public boolean saveBankingDetailsDataForPDF(ArrayList list, Map<String, String> map,Object ob) {
		logger.info("saveBankingDetailsInMain");
		StringBuffer buffer =  new StringBuffer();
		buffer
		.append("insert into cr_bank_stmnt_dtl_temp(deal_ID,DOCUMENT_ID,deal_no,ENTITY_TYPE,ENTITY_ID,BANK_NAME,BANK_BRANCH,ACCOUNT_NO,ACCOUNT_TYPE,"
				+ "STATEMENT_YEAR,STATEMENT_MONTH,STATMENT_DAY,TRANSFER_INWARD_OUTWARD,BOUNCING_INWARD_OUTWARD,IGNORE_FLAG,REJECT_REASON,MAKER_ID,MAKER_DATE,LAST_UPDATED_BY,LAST_UPDATE_DATE,"
				+ "DOCUMENT_SEQ_ID,STATMENT_DATE,TYPE,NARRATION,TOTAL_DR,"
				+ "TOTAL_CR,BALANCE_AMOUNT,BRANCH_CODE,CHEQUE_NO,TRANSACTION_ID,VALUE_DATE,Transaction_Amount,ERROR_FLAG_PDF,AUTO_SWEEP,REVERSE_SWEEP)");
		buffer.append(" values (");
		buffer.append(" ?,");		//	deal_ID
		buffer.append(" ?,");		//	DOCUMENT_ID
		buffer.append(" ?,");		//	deal_no
		buffer.append(" ?,");		//	ENTITY_TYPE
		buffer.append(" ?,");		//	ENTITY_ID
		buffer.append(" ?,");		//	BANK_NAME
		buffer.append(" ?,");		//	BANK_BRANCH
		buffer.append(" ?,");		//	ACCOUNT_NO
		buffer.append(" ?,");		//	ACCOUNT_TYPE
		buffer.append(" ?,");		//	STATEMENT_YEAR
		buffer.append(" ?,");		//	STATEMENT_MONTH
		buffer.append(" ?,");		//	STATMENT_DAY
		//buffer.append(" ?,");		//	STATMENT_DATE
		buffer.append(" ?,");		//	TRANSFER_INWARD_OUTWARD
		buffer.append(" ?,");		//	BOUNCING_INWARD_OUTWARD
		buffer.append(" ?,");		//	IGNORE_FLAG
		buffer.append(" ?,");		//	REJECT_REASON
		//buffer.append(" ?,");		//	REMARKS
		buffer.append(" ?,");		//	MAKER_ID
		buffer.append(" ?,");		//	MAKER_DATE
		buffer.append(" ?,");		//	LAST_UPDATED_BY
		buffer.append(" ?,");		//	LAST_UPDATE_DATE
		buffer.append(" ?,");		//	DOCUMENT_SEQ_ID
		buffer.append(" ?,");		//	STATMENT_DATE
		buffer.append(" ?,");		//	TYPE
		buffer.append(" ?,");		//	NARRATION
		buffer.append(" ?,");		//	TOTAL_DR
		buffer.append(" ?,");		//	TOTAL_CR
		buffer.append(" ?,");		//	BALANCE_AMOUNT
		buffer.append(" ?,");		//	BRANCH_CODE ID
		buffer.append(" ?,");	//CHEQUE_NO
		buffer.append(" ?,");	//TRANSACTION_ID
		buffer.append(" ?,");	//VALUE_DATE
		buffer.append(" ?,");	//Transaction_Amount
		buffer.append(" ?,");	//	Error_falg_pdf
		buffer.append(" ?,");	//AUTO_SWEEP
		buffer.append(" ?");	//REVERSE_SWEEP
		buffer.append(" )");
		ArrayList qryList = new ArrayList();
		PrepStmtObject object = null;
		boolean returnFlag = false;
		for(int i =0; i<list.size();i++)
		{
			BankDetailsVO vo = (BankDetailsVO)list.get(i);
			object = new PrepStmtObject();
			
			if(!CommonFunction.checkNull(vo.getCaseId()).equalsIgnoreCase(""))
				object.addString(vo.getCaseId().trim());
			else 
				object.addNull();
			
			if(!CommonFunction.checkNull(vo.getDocumentId()).equalsIgnoreCase(""))
				object.addString(vo.getDocumentId().trim());
			else 
				object.addNull();
			
		
				if(!CommonFunction.checkNull(vo.getRefId()).equalsIgnoreCase(""))
					object.addString(vo.getRefId().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getEntityType()).equalsIgnoreCase(""))
					object.addString(vo.getEntityType().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getEntityId()).equalsIgnoreCase(""))
					object.addString(vo.getEntityId().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getBankName()).equalsIgnoreCase(""))
					object.addString(vo.getBankName().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getBankBranch()).equalsIgnoreCase(""))
					object.addString(vo.getBankBranch().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getAccountNo()).equalsIgnoreCase(""))
					object.addString(vo.getAccountNo().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getAccountType()).equalsIgnoreCase(""))
					object.addString(vo.getAccountType().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getStatementYear()).equalsIgnoreCase(""))
					object.addString(vo.getStatementYear().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getStatementMonth()).equalsIgnoreCase(""))
					object.addString(vo.getStatementMonth().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getStatmentDay()).equalsIgnoreCase(""))
					object.addString(vo.getStatmentDay().trim());
				else 
					object.addNull();
				
				if(!CommonFunction.checkNull(vo.getTransferInwardOutward()).equalsIgnoreCase(""))
					object.addString(vo.getTransferInwardOutward().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getBouncingInwardOutward()).equalsIgnoreCase(""))
					object.addString(vo.getBouncingInwardOutward().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getIgnoreFlag()).equalsIgnoreCase(""))
					object.addString(vo.getIgnoreFlag().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getRemarks()).equalsIgnoreCase(""))
					object.addString(vo.getRemarks().trim());
				else 
					object.addNull();
				/*if(!CommonFunction.checkNull(vo.getRemarks()).equalsIgnoreCase(""))
					object.addString(vo.getRemarks().trim());
				else 
					object.addNull();*/
				if(!CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
					object.addString(vo.getMakerId().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
					object.addString(vo.getMakerDate().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getLastUpdatedBy()).equalsIgnoreCase(""))
					object.addString(vo.getLastUpdatedBy().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getLastUpdateDate()).equalsIgnoreCase(""))
					object.addString(vo.getLastUpdateDate().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getDocumentSeqId()).equalsIgnoreCase(""))
					object.addString(vo.getDocumentSeqId().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getStatmentDate()).equalsIgnoreCase(""))
				object.addString(vo.getStatmentDate().trim());
				else 
				object.addNull();
				if(!CommonFunction.checkNull(vo.getType()).equalsIgnoreCase(""))
					object.addString(vo.getType().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getNarration()).equalsIgnoreCase(""))
					object.addString(vo.getNarration().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getTotalDr()).equalsIgnoreCase(""))
					object.addString(vo.getTotalDr().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getTotalCr()).equalsIgnoreCase(""))
					object.addString(vo.getTotalCr().trim());
				else 
					object.addNull();
				
				if(!CommonFunction.checkNull(vo.getBalanceAmount()).equalsIgnoreCase(""))
					object.addString(vo.getBalanceAmount().trim());
				else 
					object.addNull();
				
			/*	if(!CommonFunction.checkNull(vo.getSol()).equalsIgnoreCase(""))
					object.addString(vo.getSol().trim());
				else 
					object.addNull();*/
				if(!CommonFunction.checkNull(vo.getBranchCode()).equalsIgnoreCase(""))
					object.addString(vo.getBranchCode().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getChequeNo()).equalsIgnoreCase(""))
					object.addString(vo.getChequeNo().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getTransactionId()).equalsIgnoreCase(""))
					object.addString(vo.getTransactionId().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getValueDate()).equalsIgnoreCase(""))
					object.addString(vo.getValueDate().trim());
				else 
					object.addNull();
				if(!CommonFunction.checkNull(vo.getTransactionAmount()).equalsIgnoreCase(""))
					object.addString(vo.getTransactionAmount().trim());
				else 
					object.addNull();
				
				if(!CommonFunction.checkNull(vo.getErrorFlag()).equalsIgnoreCase(""))
					object.addString(vo.getErrorFlag().trim());
				else 
					object.addNull();
				
				if(!CommonFunction.checkNull(vo.getAutoSweep()).equalsIgnoreCase(""))
					object.addString(vo.getAutoSweep().trim());
				else 
					object.addNull();
				
				if(!CommonFunction.checkNull(vo.getReverseSweep()).equalsIgnoreCase(""))
					object.addString(vo.getReverseSweep().trim());
				else 
					object.addNull();
				
				

			
			object.setSql(buffer.toString());
			logger.info(object.printQuery());
			qryList.add(object);
		}
		try {
			returnFlag=  ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("result----   "+returnFlag);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnFlag;
	}
	
}
