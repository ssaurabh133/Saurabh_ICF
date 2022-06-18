package com.cm.daoImplMYSQL;

import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.connect.CommonFunction;
import com.connect.ConnectionReportDumpsDAO;
import com.connect.PrepStmtObject;
import com.cm.dao.DumpDownLoadDAO;
import com.cm.vo.DumpDownLoadVO;
import com.connect.ConnectionDAO;

public class DumpDownLoadDAOImpl implements DumpDownLoadDAO {
	private static final Logger logger = Logger
			.getLogger(DumpDownLoadDAOImpl.class.getName());

	public ArrayList<DumpDownLoadVO> getGeneratFieldInformation(int recordID) {
		logger.info("in getGeneratFieldInformation() of DumpDownLoadDAOImpl.");
		ArrayList searchlist = new ArrayList();
		StringBuilder bufInsSql = new StringBuilder();
		ArrayList data = null;
		DumpDownLoadVO vo = null;

		ArrayList<DumpDownLoadVO> detailList = new ArrayList();
		try {
			bufInsSql
			.append("select RECORD_ID,RECORD_DESC,PARAM_KEY_ONE,PARAM_VALUE_ONE,PARAM_KEY_TWO,PARAM_VALUE_TWO,AS_ON_DATE,SUB_QUERY1,SUB_QUERY2,SUB_QUERY3,SUB_QUERY4,DATE_RANGE from cr_dump_download_dtl where RECORD_ID="
					+ recordID);
			logger.info("in getGeneratFieldInformation() of DumpDownLoadDAOImpl Query..."
					+ bufInsSql.toString());
			searchlist = ConnectionReportDumpsDAO.sqlSelect(bufInsSql
					.toString());
			int size = searchlist.size();
			logger.info("size: " + size);
			for (int i = 0; i < size; i++) {
				data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					vo = new DumpDownLoadVO();
					vo.setLbxRecordID((CommonFunction.checkNull(data.get(0))
							.trim()));
					vo.setRecordDesc((CommonFunction.checkNull(data.get(1))
							.trim()));
					vo.setParamKeyOne((CommonFunction.checkNull(data.get(2))
							.trim()));
					vo.setParamValueOne((CommonFunction.checkNull(data.get(3))
							.trim()));
					vo.setParamKeyTwo((CommonFunction.checkNull(data.get(4))
							.trim()));
					vo.setParamValueTwo((CommonFunction.checkNull(data.get(5))
							.trim()));
					vo.setAsOnDate((CommonFunction.checkNull(data.get(6))
							.trim()));
					vo.setQuery1((CommonFunction.checkNull(data.get(7)).trim()));
					vo.setQuery2((CommonFunction.checkNull(data.get(8)).trim()));
					vo.setQuery3((CommonFunction.checkNull(data.get(9)).trim()));
					vo.setQuery4((CommonFunction.checkNull(data.get(10)).trim()));
					vo.setDateRange((CommonFunction.checkNull(data.get(11))
							.trim()));
					detailList.add(vo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			searchlist = null;
			bufInsSql = null;
			data = null;
			vo = null;
		}
		return detailList;
	}

	public ArrayList<Object> reportGenerator(DumpDownLoadVO vo) {
		logger.info("in reportGenerator() of DumpDownLoadDAOImpl");
		ArrayList resultList = new ArrayList();
		String recordId = CommonFunction.checkNull(vo.getLbxRecordID()).trim();
		  String recorddesc = CommonFunction.checkNull(vo.getRecordDesc()).trim();
		String paramValOne = CommonFunction.checkNull(vo.getParamValueOne())
				.trim();
		String paramValTwo = CommonFunction.checkNull(vo.getParamValueTwo())
				.trim();
		String fromDate = CommonFunction.changeFormat(CommonFunction
				.checkNull(vo.getFromDate()));
		String toDate = CommonFunction.changeFormat(CommonFunction.checkNull(vo
				.getToDate()));
		String asOnDate = CommonFunction.changeFormat(CommonFunction
				.checkNull(vo.getAsOnDate()));
		String query1 = CommonFunction.checkNull(vo.getQuery1());
		String query2 = CommonFunction.checkNull(vo.getQuery2());
		String query3 = CommonFunction.checkNull(vo.getQuery3());
		String query4 = CommonFunction.checkNull(vo.getQuery4());
		logger.info("reportGenerator()   recordId      :  " + recordId);
		logger.info("reportGenerator()   paramValOne   :  " + paramValOne);
		logger.info("reportGenerator()   paramValTwo   :  " + paramValTwo);
		logger.info("reportGenerator()   fromDate      :  " + fromDate);
		logger.info("reportGenerator()   toDate        :  " + toDate);
		logger.info("reportGenerator()   asOnDate        :  " + asOnDate);
		logger.info(new StringBuilder().append("reportGenerator()   Record desc to be print -----------:  ").append(recorddesc).toString());
		String query = "select QUERY from cr_dump_download_dtl where RECORD_ID="
				+ recordId;
		String finalQuery = ConnectionReportDumpsDAO.singleReturn(query);
		// code added by aditi
		finalQuery = finalQuery.replace("#u", vo.getMaker_ID());
		// aditi space end
		finalQuery = finalQuery.replace("#p1", fromDate);
		finalQuery = finalQuery.replace("#p2", toDate);
		finalQuery = finalQuery.replace("#p3", asOnDate);
		finalQuery = finalQuery.replace("#p4", query1);
		finalQuery = finalQuery.replace("#p5", query2);
		finalQuery = finalQuery.replace("#p6", query3);
		finalQuery = finalQuery.replace("#p7", query4);
		if (!paramValOne.trim().equalsIgnoreCase("")) {
			String query12 = "select PARAM_VALUE_ONE from cr_dump_download_dtl where RECORD_ID="
					+ recordId;
			String paramLabOne = ConnectionReportDumpsDAO.singleReturn(query12);
			finalQuery = finalQuery + " and " + paramLabOne + "='"
					+ paramValOne + "'";
		}
		if (!paramValTwo.trim().equalsIgnoreCase("")) {
			String query12 = "select PARAM_VALUE_TWO from cr_dump_download_dtl where RECORD_ID="
					+ recordId;
			String paramLabTwo = ConnectionReportDumpsDAO.singleReturn(query12);
			finalQuery = finalQuery + " and " + paramLabTwo + "='"
					+ paramValTwo + "'";
		}
		finalQuery = finalQuery + ")";
		logger.info("final query for going to execute  :  " + finalQuery);
		try {
			//pooja code starts
			String BureauDumpId= ConnectionReportDumpsDAO.singleReturn("select RECORD_ID from cr_dump_download_dtl where record_desc='MULTI-BUREAU DATA EXPORT' ");
			 if(recordId.equalsIgnoreCase(BureauDumpId)){
					ArrayList<Object> in =new ArrayList<Object>();
					ArrayList<Object> out =new ArrayList<Object>();
					ArrayList outMessages = new ArrayList();
					ArrayList result = new ArrayList();
					String s1="";
					String s2="";
					String s3="";
					String error="";
					in.add(asOnDate);
					out.add(s1);
					out.add(s2);
					
					logger.info("MULTI_BUREAU_DATA_EXPORT ("+in.toString()+","+out.toString()+")");
					outMessages=(ArrayList) ConnectionDAO.callSP("MULTI_BUREAU_DATA_EXPORT",in,out);
					s1=CommonFunction.checkNull(outMessages.get(0));
					s2=CommonFunction.checkNull(outMessages.get(1));
					logger.info("MULTI_BUREAU_DATA_EXPORT  s1  : "+s1);
			        logger.info("MULTI_BUREAU_DATA_EXPORT  s2  : "+s2);
					
					String fd="SELECT 'LEAD NO','DEAL NO','PRODUCT','APPLICATION FORM NO','APPLICANT TYPE','SINGLE PAGE CUSTOMER ID','DEAL CUSTOMER ID','CUSTOMER NAME','CUSTOMER CATEGORY','CIBIL SCORE','HIGHMARK SCORE','EXPERIAN SCORE','REPORT NAME','CIBIL RESPONSE STATUS','HIGHMARK RESPONSE STATUS','RESPONSE DATE & TIME' union ( select ifnull(LEAD_NO,''),DEAL_NO,PRODUCT,APPLICATION_FORM_NO, case  APPLICANT_type when 'PRAPPL' then 'Applicant 'when 'COAPPL' then 'Co-Applicant' when 'GUARANTOR' then 'GUARANTOR' end as APPLICANT_TYPE,ifnull(SINGLE_PAGE_CUSTOMER_ID,''),DEAL_CUSTOMER_ID ,CUSTOMER_NAME ,case  CUSTOMER_CATEGORY WHEN 'I' THEN 'Individual' when 'C' then 'Corporate' end as  CUSTOMER_CATEGORY ,CIBIL_SCORE,HIGHMARK_SCORE,EXPERIAN_SCORE,REPORT_NAME,case  CIBIL_RESPONSE_STATUS when 'Y' then 'Done' when 'N' then 'Not Done' end as  CIBIL_RESPONSE_STATUS,case HIGHMARK_RESPONSE_STATUS when 'Y' then 'Done' when 'N' then 'Not Done' end as HIGHMARK_RESPONSE_STATUS,RESPONSE_DATE_TIME from multi_bureau_data_export_temp as u);";
					
					logger.info("final query for going to execute  :  "+fd);
					resultList = ConnectionReportDumpsDAO.sqlSelect(fd);
					 }else {
						 
						 
			resultList = ConnectionReportDumpsDAO.sqlSelect(finalQuery);
			if (recorddesc.trim().equalsIgnoreCase("QUATERLY CHANGE BASE RATE MIS")) {
		        logger.info("final query for going to execute for quaterly base rate-1 times :  ");
		        resultList = ConnectionReportDumpsDAO.sqlSelect(finalQuery);
		        logger.info("final query for going to execute for quaterly base rate-2  times :  ");
		        resultList = ConnectionReportDumpsDAO.sqlSelect(finalQuery);
		        logger.info("final query for going to execute for quaterly base rate-3  times :  ");
		        resultList = ConnectionReportDumpsDAO.sqlSelect(finalQuery);
		        logger.info("final query for going to execute for quaterly base rate-4  times :  ");
		        resultList = ConnectionReportDumpsDAO.sqlSelect(finalQuery);
		        logger.info("final query for going to execute for quaterly base rate-5  times :  ");
		        resultList = ConnectionReportDumpsDAO.sqlSelect(finalQuery);
		      }
					 }
			 //pooja code ends
			 
			//resultList = ConnectionReportDumpsDAO.sqlSelect(finalQuery);
			 if (recordId.equalsIgnoreCase("257")) {
			        logger.info("final query for going to execute for quaterly base rate-1 time :  ");
			        resultList = ConnectionReportDumpsDAO.sqlSelect(finalQuery);
			        resultList = ConnectionReportDumpsDAO.sqlSelect(finalQuery);
			        logger.info("final query for going to execute for quaterly base rate-2  time :  ");
			      }
			 if(recordId.equalsIgnoreCase("213")){
					logger.info("final query for going to execute for quaterly base rate-1 time :  ");
					resultList = ConnectionReportDumpsDAO.sqlSelect(finalQuery);
					resultList = ConnectionReportDumpsDAO.sqlSelect(finalQuery);
					logger.info("final query for going to execute for quaterly base rate-2  time :  ");
				}
			/*if(recordId.equalsIgnoreCase("213")){
				logger.info("final query for going to execute for quaterly base rate-1 time :  ");
				resultList = ConnectionReportDumpsDAO.sqlSelect(finalQuery);
				resultList = ConnectionReportDumpsDAO.sqlSelect(finalQuery);
				logger.info("final query for going to execute for quaterly base rate-2  time :  ");
			}*/
		    } catch (Exception e) {
			e.printStackTrace();
		}

		// IF I_PRM_VAL_1 <>'' THEN
		// select concat(@str,' and
		// ',V_PARAM_VALUE_ONE,'=','\'',I_PRM_VAL_1,'\'') into @str;
		// END IF;
		// IF I_PRM_VAL_2 <>'' THEN
		// select concat(@str,' and
		// ',V_PARAM_VALUE_TWO,'=','\'',I_PRM_VAL_2,'\'') into @str;

		/*
		 * ArrayList result=new ArrayList(); ArrayList<Object> in =new
		 * ArrayList<Object>(); ArrayList<Object> out =new ArrayList<Object>();
		 * ArrayList outMessages = new ArrayList(); String recordLocation="";
		 * String dbLocation=""; String s1=""; String s2=""; String status="ok";
		 * try { in.add(vo.getLbxRecordID()); String
		 * fDate=CommonFunction.changeFormat(vo.getFromDate()); in.add(fDate);
		 * String tDate=CommonFunction.changeFormat(vo.getToDate());
		 * in.add(tDate); in.add(vo.getParamValueOne());
		 * in.add(vo.getParamValueTwo()); in.add(vo.getMaker_ID());
		 * out.add(recordLocation); out.add(dbLocation); out.add(s1);
		 * out.add(s2);
		 * 
		 * 
		 * logger.info("CR_Dump_Creation ("+in.toString()+","+out.toString()+")")
		 * ; outMessages=(ArrayList)
		 * ConnectionReportDumpsDAO.callSP("CR_Dump_Creation",in,out);
		 * recordLocation=CommonFunction.checkNull(outMessages.get(0));
		 * dbLocation=CommonFunction.checkNull(outMessages.get(1));
		 * s1=CommonFunction.checkNull(outMessages.get(2));
		 * s2=CommonFunction.checkNull(outMessages.get(3));
		 * logger.info("downloadPath  :     "+recordLocation);
		 * logger.info("dbLocation  :     "+dbLocation);
		 * logger.info("s1            :     "+s1);
		 * logger.info("s2            :     "+s2); } catch (Exception e) {
		 * status="Error"; e.printStackTrace(); } result.add(status);
		 * result.add(s1); result.add(s2); result.add(recordLocation);
		 * result.add(dbLocation); return result;
		 */
		return resultList;
	}

	// sidharth space
	public ArrayList<Object> reportAdHoc(DumpDownLoadVO vo) {
		logger.info("in reportAdHoc() of DumpDownLoadDAOImpl");
		ArrayList resultList = new ArrayList();
		String query = CommonFunction.checkNull(vo.getQuery().trim());
		logger.info(query);

		try {
			resultList = ConnectionReportDumpsDAO.sqlColumnWithResult(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("size::::::" + resultList.size());
		return resultList;
	}

	// SIDHARTH ENDS
	public ArrayList getReportList(String query) {
		logger.info("in getReportList() of DumpDownLoadDAOImpl.");
		ArrayList searchlist = new ArrayList();
		StringBuilder bufInsSql = new StringBuilder();
		try {
			bufInsSql.append(query);
			logger.info("in getGeneratFieldInformation() of DumpDownLoadDAOImpl Query..."
					+ bufInsSql.toString());
			searchlist = ConnectionReportDumpsDAO.sqlSelect(bufInsSql
					.toString());
			int size = searchlist.size();
			logger.info("size: " + size);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bufInsSql = null;
		}
		return searchlist;
	}

	public void saveFunctionLogData(String userId, String moduleID,
			String functionId, String accessDate, String ipAddress,
			String sessionNo, String reportName, String reportParam) {
		logger.info("Inside ReportsDAOImpl.............saveFunctionLogData()");
		ResourceBundle resource = ResourceBundle
				.getBundle("com.yourcompany.struts.utill");
		String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		StringBuffer bufInsSql = new StringBuffer();

		ArrayList qryList = new ArrayList();
		try {
			String qry = "select curtime() ";
			String bTime = ConnectionReportDumpsDAO.singleReturn(qry);
			accessDate = accessDate.concat(" " + bTime);

			bufInsSql
			.append("insert into SEC_USER_FUNCTION_LOG(USER_ID,MODULE_ID,FUNCTION_ID,ACCESS_DATE,IP_ADDRESS,SESSION_NO,REPORT_NAME,REPORT_PARAM)");
			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?,"); // USER_ID
			bufInsSql.append(" ?,"); // MODULE_ID
			bufInsSql.append(" ?,"); // FUNCTION_ID
			bufInsSql.append(" STR_TO_DATE(?,'" + dateFormatWithTime + "'),"); // ACCESS_DATE
			bufInsSql.append(" ?,"); // IP_ADDRESS
			bufInsSql.append(" ?,"); // SESSION_NO
			bufInsSql.append(" ?,"); // REPORT_NAME
			bufInsSql.append(" ? )"); // REPORT_PARAM

			if ((CommonFunction.checkNull(userId)).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((CommonFunction
						.checkNull(userId).trim()));
			if ((CommonFunction.checkNull(moduleID).trim())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(moduleID.trim()); // MODULE_ID

			if ((CommonFunction.checkNull(functionId).trim())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((functionId).trim()); // FUNCTION_ID

			if ((CommonFunction.checkNull(accessDate).trim())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((accessDate).trim()); // ACCESS_DATE

			insertPrepStmtObject.addNull(); // IP_ADDRESS

			insertPrepStmtObject.addNull(); // SESSION_NO

			if ((CommonFunction.checkNull(reportName).trim())
					.equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((reportName).trim()); // REPORT_NAME

			insertPrepStmtObject.addString(reportParam); // REPORT_PARAM
			insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("IN saveUserFunctionLog() from reports insert query1 ### "
					+ insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			boolean status1 = ConnectionReportDumpsDAO
					.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("In saveUserFunctionLog from Reports......................"
					+ status1);

		} catch (Exception e) {
			logger.info("In saveUserFunctionLog......................" + e);
		} finally {
			insertPrepStmtObject = null;
			qryList = null;
			bufInsSql = null;
			dateFormatWithTime = null;
			moduleID = null;
		}
	}

	// Virender Hunter code for Fetching DealId ArrayList
	public ArrayList fetchDealID(String hunterCustTypeVar) {
		ArrayList list = new ArrayList();
		String query = "";
		try {
			if(hunterCustTypeVar.equals("C")){
				query ="select distinct(a.deal_id) from cr_deal_dtl a "
						+ " join cr_deal_customer_role b on a.deal_id=b.deal_id left join cr_deal_loan_dtl c on a.deal_id=c.deal_id "
						+ " where a.rec_status in ('F','A') and b.DEAL_CUSTOMER_TYPE='C' and a.deal_id not in (select deal_id from CR_HUNTER_MARKING_DTL)  "
						+ " and c.deal_product not in ('LAS','OPCO','OPCO1','OTHERS','RE','STL','STLR','WC') order by a.deal_id asc";
			}
			if(hunterCustTypeVar.equals("I")){
				query ="select distinct(a.deal_id) from cr_deal_dtl a  "+ 
						" left join cr_deal_customer_role b on a.deal_id=b.deal_id "+
						" left join cr_deal_loan_dtl c on a.deal_id=c.deal_id  "+ 
						" where a.rec_status in ('F','A') "
						+ " and a.deal_id not in(select distinct(c.deal_id) from cr_deal_dtl c left join cr_deal_customer_role d on c.deal_id=d.deal_id where rec_status in ('F','A') and d.DEAL_CUSTOMER_TYPE='C' order by c.deal_id asc) "
						+ " and b.DEAL_CUSTOMER_TYPE='I' "
						+ " and c.deal_product not in ('LAS','OPCO','OPCO1','OTHERS','RE','STL','STLR','WC')"
						+ " and a.deal_id not in (select deal_id from CR_HUNTER_MARKING_DTL ) order by a.deal_id asc ";
			}
			list = ConnectionReportDumpsDAO.sqlSelect(query);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			query = null;
		}
		return list;
	}

	// Virender Hunter code for Fetching CustomerId for DealId
	public ArrayList HunterCustCSV(String dealId) {
		ArrayList custlist = null;
		String query = "";
		try {
			query = "select deal_customer_id from cr_deal_customer_role where deal_id='"+ dealId + "'order by Deal_customer_Type , field(DEAL_CUSTOMER_ROLE_TYPE,'PRAPPL','COAPPL','GUARANTOR')";
			custlist = ConnectionReportDumpsDAO.sqlSelect(query);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			query = null;
		}
		return custlist;
	}
	
	// Virender Hunter code for Fetching Specific CustomerId for Corporate DealId
	public ArrayList CorpHunterCustCSV(String dealId) {
		ArrayList custlist = null;
		ArrayList CorpList = null;
		ArrayList IndiApplList = null;
		ArrayList IndiCoApplList = null;
		String query = "";
		try {
			custlist=new ArrayList();
			query = "select deal_customer_id from cr_deal_customer_role where deal_id='"+ dealId + "' and Deal_customer_type='C' "
					+ "order by field(DEAL_CUSTOMER_ROLE_TYPE,'PRAPPL','COAPPL','GUARANTOR'),DEAL_CUSTOMER_ROLE_ID asc limit 0,2";
			CorpList = ConnectionReportDumpsDAO.sqlSelect(query);
			if(CorpList.size()==1){
				custlist.add(CorpList);
				custlist.add("X");
			}
			else{
				custlist.addAll(CorpList);
			}

			query = "select deal_customer_id from cr_deal_customer_role where deal_id='"+ dealId + "' and Deal_customer_type='I' "
					+ "order by field(DEAL_CUSTOMER_ROLE_TYPE,'PRAPPL','COAPPL','GUARANTOR'),DEAL_CUSTOMER_ROLE_ID asc limit 0,5";
			IndiCoApplList = ConnectionReportDumpsDAO.sqlSelect(query);
			
			if(IndiCoApplList.size()>0){
				custlist.addAll(IndiCoApplList);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			query = null;
		}
		return custlist;
	}


	// Virender Hunter code for Fetching APPL HunterData (Corporate Type)
	public ArrayList fetchCorpHunterData(String dealId, String custId,int custFlag,String userId) {

		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		ArrayList HunterData = null;
		ArrayList tempHunterData = null;
		ArrayList qryList=null;
		ArrayList HunterTblRecord=null;
		ArrayList tempHunterTblRecord=null;
		String query = "";
		try {
			if(custFlag==0){
				query = " select IFNULL(concat(g.Deal_no,'##'),'##') as IDENTIFIER,"
						+ " IFNULL(CONCAT(if(a.deal_product_category='CV' or a.deal_product_category='PV','VL',a.deal_product),'_I_IND##'),'') as PRODUCT,"
						+ " 'ACCEPTED##' as CLASSIFICATION,"
						+ " CONCAT((select DATE_FORMAT(parameter_value,'%Y-%m-%d') from parameter_mst where Parameter_key ='BUSINESS_DATE'),'##')  as DATE,"
						+ " IFNULL(Concat(DATE_FORMAT(g.MAKER_DATE,'%Y-%m-%d'),'##'),'##') as APP_DTE,"
						+ " IFNULL(CONCAT(a.DEAL_TENURE,'##'),'##') as TERM,"
						+ " IFNULL(CONCAT(floor(a.DEAL_LOAN_AMOUNT),'##'),'##') as APP_VAL,"
						+ " '##'as ASS_ORIG_VAL,"
						+ " IF((c.CUSTMER_PAN ='' or c.CUSTMER_PAN is NULL)and(c.CUSTOMER_FNAME ='' or c.CUSTOMER_FNAME is NULL),'##','Clear##') as STATUS,"
						+ " IFNULL(CONCAT(c.CUSTMER_PAN,'##'),'##') as MA_PAN,"
						+ " IFNULL(CONCAT(c.CUSTOMER_FNAME,'##'),'##') as MA_FST_NME,"
						+ " IFNULL(CONCAT(c.CUSTOMER_MNAME,'##'),'##') as MA_MID_NME,"
						+ " IFNULL(CONCAT(c.CUSTOMER_LNAME,'##'),'##') as MA_LST_NME,"
						+ " IFNULL(CONCAT(DATE_FORMAT(c.CUSTOMER_DOB,'%Y-%m-%d'),'##'),'##') as MA_DOB,"
						+ " IF(d.ADDRESS_TYPE = 'REI', 'Clear##','##') as STATUS,"
						+ " IFNULL(IF(d.ADDRESS_TYPE = 'REI', CONCAT(IFNULL(d.ADDRESS_LINE1,''),'  ',IFNULL(d.ADDRESS_LINE2,''),'  ',IFNULL(d.ADDRESS_LINE3,''),'##'),'##'),'##') as MA_CA_ADD,"
						+ " IFNULL(IF(d.ADDRESS_TYPE = 'REI', CONCAT(j.DISTRICT_DESC,'##'),'##'),'##') as MA_CA_CTY,"
						+ " IFNULL(IF(d.ADDRESS_TYPE = 'REI', CONCAT(i.STATE_DESC,'##'), '##'),'##') as MA_CA_STE,"
						+ " IFNULL(IF(d.ADDRESS_TYPE = 'REI', CONCAT(h.COUNTRY_DESC,'##'),'##'),'##') as MA_CA_CTRY,"
						+ " IFNULL(IF(d.ADDRESS_TYPE = 'REI', CONCAT(d.PINCODE,'##'),'##'),'##') as MA_CA_PIN,"
						+ " IF(per.ADDRESS_TYPE = 'PERMANENT', 'Clear##','##') as STATUS,"
						+ " IFNULL(IF(per.ADDRESS_TYPE = 'PERMANENT', CONCAT(IFNULL(per.ADDRESS_LINE1,''),'  ',IFNULL(per.ADDRESS_LINE2,''),'  ',IFNULL(per.ADDRESS_LINE3,''),'##'),'##'),'##') as MA_PMA_ADD,"
						+ " IFNULL(IF(per.ADDRESS_TYPE = 'PERMANENT', CONCAT(jper.DISTRICT_DESC,'##'),'##'),'##') as MA_PMA_CTY,"
						+ " IFNULL(IF(per.ADDRESS_TYPE = 'PERMANENT', CONCAT(iper.STATE_DESC,'##'), '##'),'##') as MA_PMA_STE,"
						+ " IFNULL(IF(per.ADDRESS_TYPE = 'PERMANENT', CONCAT(hper.COUNTRY_DESC,'##'),'##'),'##') as MA_PMA_CTRY,"
						+ " IFNULL(IF(per.ADDRESS_TYPE = 'PERMANENT', CONCAT(per.PINCODE,'##'),'##'),'##') as MA_PMA_PIN,"
						+ " IF(d.ALTERNATE_PHONE =''or d.ALTERNATE_PHONE is null,'##','Clear##') as STATUS ,"
						+ " IFNULL(IF(d.ADDRESS_TYPE = 'REI', CONCAT(d.ALTERNATE_PHONE,'##'),'##'),'##') as MA_HT_TEL_NO,"
						+ " IF(d.PRIMARY_PHONE =''or d.PRIMARY_PHONE is null,'##','Clear##') as STATUS ,"
						+ " IFNULL(CONCAT(d.PRIMARY_PHONE,'##'),'##') as MA_MT_TEL_NO,"
						+ " IF(off.ALTERNATE_PHONE =''or off.ALTERNATE_PHONE is null,'##','Clear##') as STATUS ,"
						+ " IFNULL(IF(off.ADDRESS_TYPE = 'OFFICE', CONCAT(off.ALTERNATE_PHONE,'##'),'##'),'##') as MA_BT_TEL_NO,"
						+ " IF(CUSTOMER_EMAIL =''or CUSTOMER_EMAIL is null,'##','Clear##') as STATUS ,IFNULL(CONCAT(CUSTOMER_EMAIL,'##'),'##')as MA_EMA_EMA_ADD,"
						+ " IF(f.BANK_NAME IS NULL AND e.BANK_ACCOUNT IS NULL,'##','Clear##') as STATUS,"
						+ " IFNULL(CONCAT(f.BANK_NAME,'##'),'##') as MA_BNK_NM,IFNULL(CONCAT(\"'\",e.BANK_ACCOUNT,'##'),'##')as MA_BNK_ACC_NO,"
						+ " IF(c.VOTER_ID IS NULL ,'##','Clear##') as STATUS,IF(c.VOTER_ID IS NULL ,'##','VOTERS CARD##') as MA_ID_DOC_TYP,IFNULL(CONCAT(c.VOTER_ID,'##'),'##')as MA_ID_DOC_NO,"
						+ " IF(c.UID_NO IS NULL ,'##','Clear##') as STATUS,IF(c.UID_NO IS NULL ,'##','AADHAR ID##') as MA_ID_DOC_TYP,IFNULL(CONCAT(c.UID_NO,'##'),'##')as MA_ID_DOC_NO,"
						+ " IF(c.PASSPORT_NUMBER IS NULL ,'##','Clear##') as STATUS,IF(c.PASSPORT_NUMBER IS NULL ,'##','PASSPORT##') as MA_ID_DOC_TYP,IFNULL(CONCAT(c.PASSPORT_NUMBER,'##'),'##') as MA_ID_DOC_NO,"
						+ " IF(c.DRIVING_LICENSE IS NULL ,'##','Clear##') as STATUS,IF(c.DRIVING_LICENSE IS NULL ,'##','DRIVING LISENCE##') as MA_ID_DOC_TYP,IFNULL(CONCAT(c.DRIVING_LICENSE,'##'),'##') as MA_ID_DOC_NO,"
						+ " IF(c.OTHER_NO IS NULL ,'##','Clear##') as STATUS,IF(c.OTHER_NO IS NULL ,'##','OTHER##') as MA_ID_DOC_TYP,IFNULL(CONCAT(c.OTHER_NO,'##'),'##') as MA_ID_DOC_NO,"
						+ " '##' as STATUS,'##' as MA_EMP_ORG_NME,"
						+ " IF(off.ADDRESS_TYPE = 'OFFICE', 'Clear##','##') as STATUS,"
						+ " IFNULL(IF(off.ADDRESS_TYPE = 'OFFICE', CONCAT(IFNULL(off.ADDRESS_LINE1,''),'  ',IFNULL(off.ADDRESS_LINE2,''),'  ',IFNULL(off.ADDRESS_LINE3,''),'##'),'##'),'##') as MA_EMP_AD_ADD,"
						+ " IFNULL(IF(off.ADDRESS_TYPE = 'OFFICE', CONCAT(joff.DISTRICT_DESC,'##'),'##'),'##') as MA_EMP_AD_CTY,"
						+ " IFNULL(IF(off.ADDRESS_TYPE = 'OFFICE', CONCAT(ioff.STATE_DESC,'##'), '##'),'##') as MA_EMP_AD_STE,"
						+ " IFNULL(IF(off.ADDRESS_TYPE = 'OFFICE', CONCAT(hoff.COUNTRY_DESC,'##'),'##'),'##') as MA_EMP_AD_CTRY,"
						+ " IFNULL(IF(off.ADDRESS_TYPE = 'OFFICE', CONCAT(off.PINCODE,''),''),'') as MA_EMP_AD_PIN"
						+ " from cr_deal_dtl g"
						+ " left join cr_deal_loan_dtl a on g.deal_id=a.deal_id"
						+ " left join cr_deal_customer_role b on a.deal_id=b.deal_id"
						+ " left join cr_deal_customer_m c on b.DEAL_CUSTOMER_ID=c.CUSTOMER_ID "
						+ " left join cr_deal_address_m d on c.customer_id  = d.BPID and d.ADDRESS_TYPE = 'REI'"
						+ " left join cr_deal_address_m per on c.customer_id  = per.BPID and per.ADDRESS_TYPE = 'PERMANENT'"
						+ " left join cr_deal_address_m off on c.customer_id  = off.BPID and off.ADDRESS_TYPE = 'OFFICE'"
						+ " left join com_country_m h on h.country_id = d.country"
						+ " left join com_country_m hper on hper.country_id = per.country"
						+ " left join com_country_m hoff on hoff.country_id = off.country"
						+ " left join com_state_m i on i.state_id = d.state"
						+ " left join com_state_m iper on iper.state_id = per.state"
						+ " left join com_state_m ioff on ioff.state_id = off.state"
						+ " left join com_district_m j on j.district_id = d.district"
						+ " left join com_district_m jper on jper.district_id = per.district"
						+ " left join com_district_m joff on joff.district_id = off.district"
						+ " left join cust_bank_details_m e on d.BPID = e.CUSTOMER_ID"
						+ " left join COM_BANK_M f on e.BANK_ID=f.BANK_ID  "
						+ " where a.deal_id='"+ dealId+ "' and b.DEAL_CUSTOMER_ID='"+ custId + "' AND b.DEAL_CUSTOMER_TYPE = 'I' limit 0,1";
			}
			else if(custFlag==1){
				query = "select IF((c.CUSTMER_PAN ='' or c.CUSTMER_PAN is NULL)and(c.CUSTOMER_FNAME ='' or c.CUSTOMER_FNAME is NULL),'##','Clear##') as STATUS,"
						+ " IFNULL(CONCAT(c.CUSTMER_PAN,'##'),'##')as JA_PAN,"
						+ " IFNULL(CONCAT(c.CUSTOMER_FNAME,'##'),'##')as JA_FST_NME,"
						+ " IFNULL(CONCAT(c.CUSTOMER_MNAME,'##'),'##')as JA_MID_NME,"
						+ " IFNULL(concat(c.CUSTOMER_LNAME,'##'),'##')as JA_LST_NME,"
						+ " IFNULL(CONCAT(DATE_FORMAT(c.CUSTOMER_DOB, '%Y-%m-%d'),'##'),'##')as JA_DOB,"
						+ " IF(ADDRESS_TYPE = 'REI', 'Clear##','##') as STATUS,"
						+ " IFNULL(IF(ADDRESS_TYPE = 'REI', CONCAT(IFNULL(ADDRESS_LINE1,''),'  ',IFNULL(ADDRESS_LINE2,''),'  ',IFNULL(ADDRESS_LINE3,''),'##'),'##'),'##') as JA_CA_ADD,"
						+ " IFNULL(IF(ADDRESS_TYPE = 'REI', CONCAT(j.DISTRICT_DESC,'##'),'##'),'##') as JA_CA_CTY,"
						+ " IFNULL(IF(ADDRESS_TYPE = 'REI', CONCAT(i.STATE_DESC,'##'), '##'),'##') as JA_CA_STE,"
						+ " IFNULL(IF(ADDRESS_TYPE = 'REI', CONCAT(h.COUNTRY_DESC,'##'),'##'),'##') as JA_CA_CTRY,"
						+ " IFNULL(IF(ADDRESS_TYPE = 'REI', CONCAT(PINCODE,'##'),'##'),'##') as JA_CA_PIN,"
						+ " IF(c.VOTER_ID IS NULL ,'##','Clear##') as STATUS,"
						+ " IF(c.VOTER_ID IS NULL ,'##','VOTERS CARD##') as JA_ID_DOC_TYP,"
						+ " IFNULL(concat(c.VOTER_ID,'##'),'##')as JA_ID_DOC_NO,"
						+ " IF(c.UID_NO IS NULL ,'##','Clear##') as STATUS,"
						+ " IF(c.UID_NO IS NULL ,'##','AADHAR ID##') as JA_ID_DOC_TYP,"
						+ " IFNULL(concat(c.UID_NO,'##'),'##')as JA_ID_DOC_NO,"
						+ " IF(c.PASSPORT_NUMBER IS NULL ,'##','Clear##') as STATUS,"
						+ " IF(c.PASSPORT_NUMBER IS NULL ,'##','PASSPORT##') as JA_ID_DOC_TYP,"
						+ " IFNULL(concat(c.PASSPORT_NUMBER,'##'),'##')as JA_ID_DOC_NO,"
						+ " IF(c.DRIVING_LICENSE IS NULL ,'##','Clear##') as STATUS,"
						+ " IF(c.DRIVING_LICENSE IS NULL ,'##','DRIVING LISENCE##') as JA_ID_DOC_TYP,"
						+ " IFNULL(concat(c.DRIVING_LICENSE,'##'),'##')as JA_ID_DOC_NO,"
						+ " IF(c.OTHER_NO IS NULL ,'##','Clear##') as STATUS,"
						+ " IF(c.OTHER_NO IS NULL ,'##','OTHER##') as JA_ID_DOC_TYP,"
						+ " IFNULL(c.OTHER_NO,'') as JA_ID_DOC_NO"
						+ " from cr_deal_loan_dtl a left join cr_deal_customer_role b on a.deal_id=b.deal_id"
						+ "  left join cr_deal_customer_m c on b.DEAL_CUSTOMER_ID=c.CUSTOMER_ID "
						+ " left join cr_deal_address_m d on c.customer_id  = d.BPID and address_type='REI'"
						+ " left join com_country_m h on h.country_id = d.country"
						+ " left join com_state_m i on i.state_id = d.state"
						+ " left join com_district_m j on j.district_id = d.district"
						+ " where a.deal_id='"+ dealId+ "' and b.DEAL_CUSTOMER_ID='"+ custId + "' AND b.DEAL_CUSTOMER_TYPE = 'I' limit 0,1";
			}

		else if(custFlag==2){
				query = " select IFNULL(concat(g.Deal_no,'##'),'##') as IDENTIFIER,"
						+ " IFNULL(CONCAT(if(a.deal_product_category='CV' or a.deal_product_category='PV','VL',a.deal_product),'_NI_IND##'),'') as PRODUCT,"
						+ " 'ACCEPTED##' as CLASSIFICATION,"
						+ " CONCAT((select DATE_FORMAT(parameter_value,'%Y-%m-%d') from parameter_mst where Parameter_key ='BUSINESS_DATE'),'##')  as DATE,"
						+ " IFNULL(Concat(DATE_FORMAT(g.MAKER_DATE,'%Y-%m-%d'),'##'),'##') as APP_DTE,"
						+ " IFNULL(CONCAT(a.DEAL_TENURE,'##'),'##') as TERM,"
						+ " IFNULL(CONCAT(floor(a.DEAL_LOAN_AMOUNT),'##'),'##') as APP_VAL,"
						+ " '##' as ASS_ORIG_VAL,"
						+ " IF(c.CUSTOMER_NAME ='' or c.CUSTOMER_NAME is NULL,'##','Clear##') as STATUS,"
						+ " IFNULL(CONCAT(c.CUSTOMER_NAME, '##'), '##') as SME_ORG_NME,"
						+ " '##' as SME_TAN_NO,"
						+ " IF(d.ADDRESS_TYPE = 'OFFICE', 'Clear##','##') as STATUS,"
						+ " IFNULL(IF(d.ADDRESS_TYPE = 'OFFICE', CONCAT(IFNULL(d.ADDRESS_LINE1,''),'  ',IFNULL(d.ADDRESS_LINE2,''),'  ',IFNULL(d.ADDRESS_LINE3,''),'##'),'##'),'##') as MAC_ADD_ADD,"
						+ " IFNULL(IF(d.ADDRESS_TYPE = 'OFFICE', CONCAT(j.DISTRICT_DESC,'##'),'##'),'##') as MAC_ADD_CTY,"
						+ " IFNULL(IF(d.ADDRESS_TYPE = 'OFFICE', CONCAT(i.STATE_DESC,'##'), '##'),'##') as MAC_ADD_STE,"
						+ " IFNULL(IF(d.ADDRESS_TYPE = 'OFFICE', CONCAT(h.COUNTRY_DESC,'##'),'##'),'##') as MAC_ADD_CTRY,"
						+ " IFNULL(IF(d.ADDRESS_TYPE = 'OFFICE', CONCAT(d.PINCODE,'##'),'##'),'##') as MAC_ADD_PIN, "
						+ " IF(d.ALTERNATE_PHONE =''or d.ALTERNATE_PHONE is null,'##','Clear##') as STATUS ,"
						+ " IFNULL(IF(d.ADDRESS_TYPE = 'OFFICE', CONCAT(d.ALTERNATE_PHONE,'##'),'##'),'##') as MAC_TEL_TEL_NO,"
						+ " IF(f.BANK_NAME IS NULL AND e.BANK_ACCOUNT IS NULL,'##','Clear##') AS STATUS,"
						+ " IFNULL(CONCAT(f.BANK_NAME, '##'), '##') as MAC_BNK_NM,"
						+ " IFNULL(CONCAT(\"'\",e.BANK_ACCOUNT,'##'), '##') as MAC_BNK_ACC_NO,"
						+ " IF(CUSTOMER_EMAIL = '' or CUSTOMER_EMAIL IS NULL, '##', 'Clear##') AS STATUS,"
						+ " IFNULL(CUSTOMER_EMAIL,'') as MAC_EMA_EMA_ADD"
						+ " from cr_deal_dtl g "
						+ " left join cr_deal_loan_dtl a on g.deal_id=a.deal_id"
						+ " left join cr_deal_customer_role b on a.deal_id=b.deal_id"
						+ " left join cr_deal_customer_m c on b.DEAL_CUSTOMER_ID=c.CUSTOMER_ID "
						+ " left join cr_deal_address_m dbank on c.customer_id  = dbank.BPID"
						+ " left join cr_deal_address_m d on c.customer_id  = d.BPID and d.address_type='OFFICE'"
						+ " left join com_country_m h on h.country_id = d.country"
						+ " left join com_state_m i on i.state_id = d.state"
						+ " left join com_district_m j on j.district_id = d.district"
						+ "  left join cust_bank_details_m e on dbank.BPID = e.CUSTOMER_ID"
						+ " left join COM_BANK_M f on e.BANK_ID=f.BANK_ID"
						+ " WHERE a.deal_id = '"+dealId+"' AND b.DEAL_CUSTOMER_ID = '"+custId+"' AND b.DEAL_CUSTOMER_TYPE = 'C' limit 0,1";	
			}
			else if(custFlag==3){
					query = " select IF(c.CUSTOMER_NAME ='' or c.CUSTOMER_NAME is NULL,'##','Clear##') as STATUS,"
							+ " IFNULL(CONCAT(c.CUSTOMER_NAME, '##'), '##') as SME_ORG_NME,"
							+ " '##' as SME_TAN_NO,"
							+ " IF(d.ADDRESS_TYPE = 'OFFICE', 'Clear##','##') as STATUS,"
							+ " IFNULL(IF(d.ADDRESS_TYPE = 'OFFICE', CONCAT(IFNULL(d.ADDRESS_LINE1,''),'  ',IFNULL(d.ADDRESS_LINE2,''),'  ',IFNULL(d.ADDRESS_LINE3,''),'##'),'##'),'##') as MAC_ADD_ADD,"
							+ " IFNULL(IF(d.ADDRESS_TYPE = 'OFFICE', CONCAT(j.DISTRICT_DESC,'##'),'##'),'##') as MAC_ADD_CTY,"
							+ " IFNULL(IF(d.ADDRESS_TYPE = 'OFFICE', CONCAT(i.STATE_DESC,'##'), '##'),'##') as MAC_ADD_STE,"
							+ " IFNULL(IF(d.ADDRESS_TYPE = 'OFFICE', CONCAT(h.COUNTRY_DESC,'##'),'##'),'##') as MAC_ADD_CTRY,"
							+ " IFNULL(IF(d.ADDRESS_TYPE = 'OFFICE', CONCAT(d.PINCODE,'##'),'##'),'##') as MAC_ADD_PIN, "
							+ " IF(d.ALTERNATE_PHONE =''or d.ALTERNATE_PHONE is null,'##','Clear##') as STATUS ,"
							+ " IFNULL(IF(d.ADDRESS_TYPE = 'OFFICE', CONCAT(d.ALTERNATE_PHONE,'##'),'##'),'##') as MAC_TEL_TEL_NO,"
							+ " IF(f.BANK_NAME IS NULL AND e.BANK_ACCOUNT IS NULL,'##','Clear##') AS STATUS,"
							+ " IFNULL(CONCAT(f.BANK_NAME, '##'), '##') as MAC_BNK_NM,"
							+ " IFNULL(CONCAT(\"'\",e.BANK_ACCOUNT,'##'), '##') as MAC_BNK_ACC_NO,"
							+ " IF(CUSTOMER_EMAIL = '' or CUSTOMER_EMAIL IS NULL, '##', 'Clear##') AS STATUS,"
							+ " IFNULL(CUSTOMER_EMAIL,'') as MAC_EMA_EMA_ADD"
							+ " from cr_deal_dtl g "
							+ " left join cr_deal_loan_dtl a on g.deal_id=a.deal_id"
							+ " left join cr_deal_customer_role b on a.deal_id=b.deal_id"
							+ " left join cr_deal_customer_m c on b.DEAL_CUSTOMER_ID=c.CUSTOMER_ID "
							+ " left join cr_deal_address_m dbank on c.customer_id  = dbank.BPID"
							+ " left join cr_deal_address_m d on c.customer_id  = d.BPID and d.address_type='OFFICE'"
							+ " left join com_country_m h on h.country_id = d.country"
							+ " left join com_state_m i on i.state_id = d.state"
							+ " left join com_district_m j on j.district_id = d.district"
							+ "  left join cust_bank_details_m e on dbank.BPID = e.CUSTOMER_ID"
							+ " left join COM_BANK_M f on e.BANK_ID=f.BANK_ID"
							+ " WHERE a.deal_id = '"+dealId+"' AND b.DEAL_CUSTOMER_ID = '"+custId+"' AND b.DEAL_CUSTOMER_TYPE = 'C' limit 0,1";
			}
			else if(custFlag==4){
				query="select IF((c.CUSTMER_PAN ='' or c.CUSTMER_PAN is NULL)and(c.CUSTOMER_FNAME ='' or c.CUSTOMER_FNAME is NULL),'##','Clear##') as STATUS,"
						+ " IFNULL(CONCAT(c.CUSTMER_PAN,'##'),'##') as MP_PAN,"
						+ " IFNULL(CONCAT(c.CUSTOMER_FNAME,'##'),'##') as MP_FST_NME,"
						+ " IFNULL(CONCAT(c.CUSTOMER_MNAME,'##'),'##') as MP_MID_NME,"
						+ " IFNULL(CONCAT(c.CUSTOMER_LNAME,'##'),'##') as MP_LST_NME,"
						+ " IFNULL(CONCAT(DATE_FORMAT(c.CUSTOMER_DOB, '%Y-%m-%d'),'##'),'##') as MP_DOB,"
						+ " IF(d.ADDRESS_TYPE = 'REI', 'Clear##','##') as STATUS,"
						+ " IFNULL(IF(d.ADDRESS_TYPE = 'REI', CONCAT(IFNULL(d.ADDRESS_LINE1,''),'  ',IFNULL(d.ADDRESS_LINE2,''),'  ',IFNULL(d.ADDRESS_LINE3,''),'##'),'##'),'##') as MA_CA_ADD,"
						+ " IFNULL(IF(d.ADDRESS_TYPE = 'REI', CONCAT(j.DISTRICT_DESC,'##'),'##'),'##') as MA_CA_CTY,"
						+ " IFNULL(IF(d.ADDRESS_TYPE = 'REI', CONCAT(i.STATE_DESC,'##'), '##'),'##') as MA_CA_STE,"
						+ " IFNULL(IF(d.ADDRESS_TYPE = 'REI', CONCAT(h.COUNTRY_DESC,'##'),'##'),'##') as MA_CA_CTRY,"
						+ " IFNULL(IF(d.ADDRESS_TYPE = 'REI', CONCAT(d.PINCODE,'##'),'##'),'##') as MA_CA_PIN,"
						+ " IF(per.ADDRESS_TYPE = 'PERMANENT', 'Clear##','##') as STATUS,"
						+ " IFNULL(IF(per.ADDRESS_TYPE = 'PERMANENT', CONCAT(IFNULL(per.ADDRESS_LINE1,''),'  ',IFNULL(per.ADDRESS_LINE2,''),'  ',IFNULL(per.ADDRESS_LINE3,''),'##'),'##'),'##') as MA_PMA_ADD,"
						+ " IFNULL(IF(per.ADDRESS_TYPE = 'PERMANENT', CONCAT(jper.DISTRICT_DESC,'##'),'##'),'##') as MA_PMA_CTY,"
						+ " IFNULL(IF(per.ADDRESS_TYPE = 'PERMANENT', CONCAT(iper.STATE_DESC,'##'), '##'),'##') as MA_PMA_STE,"
						+ " IFNULL(IF(per.ADDRESS_TYPE = 'PERMANENT', CONCAT(hper.COUNTRY_DESC,'##'),'##'),'##') as MA_PMA_CTRY,"
						+ " IFNULL(IF(per.ADDRESS_TYPE = 'PERMANENT', CONCAT(per.PINCODE,'##'),'##'),'##') as MA_PMA_PIN,"
						+ " IF(d.ALTERNATE_PHONE =''or d.ALTERNATE_PHONE is null,'##','Clear##') as STATUS ,"
						+ " IFNULL(IF(d.ADDRESS_TYPE = 'REI', CONCAT(d.ALTERNATE_PHONE,'##'),'##'),'##') as MA_HT_TEL_NO,"
						+ " IF(d.PRIMARY_PHONE ='' or d.PRIMARY_PHONE is NULL,'##','Clear##') as STATUS ,"
						+ " IFNULL(CONCAT(d.PRIMARY_PHONE,'##'),'##') as MP_MT_TEL_NO,"
						+ " IF(CUSTOMER_EMAIL ='' or CUSTOMER_EMAIL IS NULL,'##','Clear##') as STATUS ,"
						+ " IFNULL(CONCAT(CUSTOMER_EMAIL,'##'),'##') as MP_EMA_EMA_ADD,"
						
						+ " IF(f.BANK_NAME IS NULL AND e.BANK_ACCOUNT IS NULL,'##','Clear##') as STATUS,"
						+ " IFNULL(CONCAT(f.BANK_NAME,'##'),'##') as MP_BNK_NM,"
						+ " IFNULL(CONCAT(\"'\",e.BANK_ACCOUNT,'##'),'##') as MP_BNK_ACC_NO,"
						+ "  IF(c.VOTER_ID IS NULL ,'##','Clear##') as STATUS,"
						+ " IF(c.VOTER_ID IS NULL ,'##','VOTERS CARD##') as MP_ID_DOC_TYP,"
						+ " IFNULL(CONCAT(c.VOTER_ID,'##'),'##') as MP_ID_DOC_NO,"
						+ " IF(c.UID_NO IS NULL ,'##','Clear##') as STATUS,"
						+ " IF(c.UID_NO IS NULL ,'##','AADHAR ID##') as MP_ID_DOC_TYP,"
						+ " IFNULL(CONCAT(c.UID_NO,'##'),'##') as MP_ID_DOC_NO,"
						+ " IF(c.PASSPORT_NUMBER IS NULL ,'##','Clear##') as STATUS,"
						+ " IF(c.PASSPORT_NUMBER IS NULL ,'##','PASSPORT##') as MP_ID_DOC_TYP,"
						+ " IFNULL(CONCAT(c.PASSPORT_NUMBER,'##'),'##') as MP_ID_DOC_NO,"
						+ " IF(c.DRIVING_LICENSE IS NULL ,'##','Clear##') as STATUS,"
						+ " IF(c.DRIVING_LICENSE IS NULL ,'##','DRIVING LISENCE##') as MP_ID_DOC_TYP,"
						+ " IFNULL(CONCAT(c.DRIVING_LICENSE,'##'),'##') as MP_ID_DOC_NO,"
						+ " IF(c.OTHER_NO IS NULL ,'##','Clear##') as STATUS,"
						+ " IF(c.OTHER_NO IS NULL ,'##','OTHER##') as MP_ID_DOC_TYP,"
						+ " IFNULL(c.OTHER_NO,'') as MP_ID_DOC_NO"
						+ " from cr_deal_loan_dtl a "
						+ " left join cr_deal_customer_role b on a.deal_id=b.deal_id"
						+ " left join cr_deal_customer_m c on b.DEAL_CUSTOMER_ID=c.CUSTOMER_ID "
						+ " left join cr_deal_address_m d on c.customer_id  = d.BPID and d.address_type='REI'"
						+ " left join cr_deal_address_m per on c.customer_id  = per.BPID and per.address_type='PERMANENT'"
						+ " left join com_country_m h on h.country_id = d.country"
						+ " left join com_country_m hper on hper.country_id = per.country"
						+ " left join com_state_m i on i.state_id = d.state"
						+ " left join com_state_m iper on iper.state_id = per.state"
						+ " left join com_district_m j on j.district_id = d.district"
						+ " left join com_district_m jper on jper.district_id = per.district"
						+ " left join cust_bank_details_m e on d.BPID = e.CUSTOMER_ID"
						+ " left join COM_BANK_M f on e.BANK_ID=f.BANK_ID"
						+ " WHERE a.deal_id = '"+dealId+"' AND b.DEAL_CUSTOMER_ID = '"+custId+"' AND b.DEAL_CUSTOMER_TYPE = 'I' limit 0,1";
			}
			else if(custFlag==5){
				query=" select IF((c.CUSTMER_PAN ='' or c.CUSTMER_PAN is NULL)and(c.CUSTOMER_FNAME ='' or c.CUSTOMER_FNAME is NULL),'##','Clear##') as STATUS,"
						+ " IFNULL(CONCAT(c.CUSTMER_PAN,'##'),'##') as CP_PAN,"
						+ " IFNULL(CONCAT(c.CUSTOMER_FNAME,'##'),'##') as CP_FST_NME,"
						+ " IFNULL(CONCAT(c.CUSTOMER_MNAME,'##'),'##') as CP_MID_NME,"
						+ " IFNULL(CONCAT(c.CUSTOMER_LNAME,'##'),'##') as CP_LST_NME,"
						+ " IFNULL(CONCAT(DATE_FORMAT(c.CUSTOMER_DOB, '%Y-%m-%d'),'##'),'##') as CP_DOB,"
						+ " IF(ADDRESS_TYPE = 'REI', 'Clear##','##') as STATUS,"
						+ " IFNULL(IF(ADDRESS_TYPE = 'REI', CONCAT(IFNULL(ADDRESS_LINE1,''),'  ',IFNULL(ADDRESS_LINE2,''),'  ',IFNULL(ADDRESS_LINE3,''),'##'),'##'),'##') as CP_CA_ADD,"
						+ " IFNULL(IF(ADDRESS_TYPE = 'REI', CONCAT(j.DISTRICT_DESC,'##'),'##'),'##') as CP_CA_CTY,"
						+ " IFNULL(IF(ADDRESS_TYPE = 'REI', CONCAT(i.STATE_DESC,'##'), '##'),'##') as CP_CA_STE,"
						+ " IFNULL(IF(ADDRESS_TYPE = 'REI', CONCAT(h.COUNTRY_DESC,'##'),'##'),'##') as CP_CA_CTRY,"
						+ " IFNULL(IF(ADDRESS_TYPE = 'REI', CONCAT(PINCODE,'##'),'##'),'##') as CP_CA_PIN,"
						+ " IF(c.VOTER_ID IS NULL ,'##','Clear##') as STATUS,"
						+ " IF(c.VOTER_ID IS NULL ,'##','VOTERS CARD##') as CP_ID_DOC_TYP,"
						+ " IFNULL(CONCAT(c.VOTER_ID,'##'),'##') as CP_ID_DOC_NO,"
						+ " IF(c.UID_NO IS NULL ,'##','Clear##') as STATUS,"
						+ " IF(c.UID_NO IS NULL ,'##','AADHAR ID##') as CP_ID_DOC_TYP,"
						+ " IFNULL(CONCAT(c.UID_NO,'##'),'##') as CP_ID_DOC_NO,"
						+ " IF(c.PASSPORT_NUMBER IS NULL ,'##','Clear##') as STATUS,"
						+ " IF(c.PASSPORT_NUMBER IS NULL ,'##','PASSPORT##') as CP_ID_DOC_TYP,"
						+ " IFNULL(CONCAT(c.PASSPORT_NUMBER,'##'),'##') as CP_ID_DOC_NO,"
						+ " IF(c.DRIVING_LICENSE IS NULL ,'##','Clear##') as STATUS,"
						+ " IF(c.DRIVING_LICENSE IS NULL ,'##','DRIVING LISENCE##') as CP_ID_DOC_TYP,"
						+ " IFNULL(CONCAT(c.DRIVING_LICENSE,'##'),'##') as CP_ID_DOC_NO,"
						+ " IF(c.OTHER_NO IS NULL ,'##','Clear##') as STATUS,"
						+ " IF(c.OTHER_NO IS NULL ,'##','OTHER##') as CP_ID_DOC_TYP,"
						+ " IFNULL(c.OTHER_NO,'') as CP_ID_DOC_NO"
						+ " from cr_deal_loan_dtl a left join cr_deal_customer_role b on a.deal_id=b.deal_id"
						+ " left join cr_deal_customer_m c on b.DEAL_CUSTOMER_ID=c.CUSTOMER_ID "
						+ " left join cr_deal_address_m d on c.customer_id  = d.BPID and address_type='REI'"
						+ " left join com_country_m h on h.country_id = d.country"
						+ " left join com_state_m i on i.state_id = d.state"
						+ " left join com_district_m j on j.district_id = d.district"
						+ " WHERE a.deal_id = '"+dealId+"' AND b.DEAL_CUSTOMER_ID = '"+custId+"' AND b.DEAL_CUSTOMER_TYPE = 'I' limit 0,1";
			}
			
			tempHunterData = ConnectionReportDumpsDAO.sqlSelect(query);

			DumpDownLoadDAOImpl ddlDAOImplObj=new DumpDownLoadDAOImpl();
			HunterData=ddlDAOImplObj.SplitArrayList(tempHunterData);

			String qry2="select concat(a.DEAL_ID,'##'),concat(a.DEAL_NO,'##'),concat(c.CUSTOMER_ID,'##'),concat(c.CUSTOMER_NAME,'##'),concat(b.DEAL_CUSTOMER_ROLE_TYPE,'##'),b.STATUS from cr_deal_dtl a"+
					" left join cr_deal_customer_role b on a.deal_id=b.deal_id left join cr_deal_customer_m c on b.DEAL_CUSTOMER_ID=c.CUSTOMER_ID "
					+ " where a.deal_id='"+ dealId+ "' and c.CUSTOMER_ID='"+ custId + "'";

			tempHunterTblRecord = ConnectionReportDumpsDAO.sqlSelect(qry2);

			HunterTblRecord=ddlDAOImplObj.SplitArrayList(tempHunterTblRecord);

			int HunterTblRecordSize=HunterTblRecord.size();

			StringBuffer bufInsSql =new StringBuffer();

			bufInsSql.append("insert into CR_HUNTER_MARKING_DTL(DEAL_ID,DEAL_NO,CUSTOMER_ID,CUSTOMER_NAME,CUSTOMER_ROLE_TYPE,STATUS,USER_ID)");
			bufInsSql.append(" values ( ");
			bufInsSql.append(" ?," ); //DEAL_ID
			bufInsSql.append(" ?," ); //DEAL_NO
			bufInsSql.append(" ?," ); //CUSTOMER_ID
			bufInsSql.append(" ?," ); //CUSTOMER_NAME
			bufInsSql.append(" ?," ); //CUSTOMER_ROLE_TYPE
			bufInsSql.append(" ?," ); //STATUS
			bufInsSql.append(" ? );" ); //USER_ID
			
			for(int i=0;i<=HunterTblRecordSize;i++){
				if(i!=HunterTblRecordSize){
				if((CommonFunction.checkNull(HunterTblRecord.get(i).toString())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((HunterTblRecord.get(i).toString()).trim());
				}
				else{
						insertPrepStmtObject.addString(userId);
				}
			}	
			
			
			insertPrepStmtObject.setSql(bufInsSql.toString());
			qryList=new ArrayList();
			qryList.add(insertPrepStmtObject);
			//boolean status=ConnectionReportDumpsDAO.sqlInsUpdDeletePrepStmt(qryList);
			ConnectionReportDumpsDAO.sqlInsUpdDeletePrepStmt(qryList);
			bufInsSql=null;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			query = null;
			qryList= null;
		}
		return HunterData;
	}

	// Virender Utility for splitting one arrayList to multiple
	public ArrayList SplitArrayList(ArrayList oldlist) {
		ArrayList newlist = new ArrayList();
		String temp = oldlist.get(0).toString();
		temp = temp.replace("[", "").replace("]", "");
		String[] temp1=temp.split("##,");
		int temp1Len=temp1.length;

		for (int i = 0; i < temp1Len; i++) {
			temp1[i]=temp1[i].trim();
			if(temp1[i].contains(",")){
				temp1[i]=temp1[i].replace(","," ");
			}
			newlist.add(temp1[i]);
		}
		temp = null;
		temp1 = null;
		oldlist = null;
		return newlist;
	}

}