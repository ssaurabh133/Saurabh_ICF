package com.cm.daoImplMYSQL;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.cm.dao.DisbCancellationDAO;
import com.cm.vo.CancellationVO;
import com.cm.vo.ClosureAuthorVO;
import com.cm.vo.ClosureSearchVO;
import com.cm.vo.DisbCancellationVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;

public class DisbCancellationDAOImpl implements DisbCancellationDAO{
	
	private static final Logger logger = Logger.getLogger(DisbCancellationDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));

	public ArrayList<DisbCancellationVO> searchDisbCancellationData(Object ob, String status) {
	ArrayList<DisbCancellationVO> searchDataList = new ArrayList<DisbCancellationVO>();
	DisbCancellationVO vo = (DisbCancellationVO) ob;
	logger.info("In searchClosureData DAOImpl::::::::::::::::CancellationDAOImpl");
	
	ArrayList mainlist = new ArrayList();
	ArrayList subList = new ArrayList();
	StringBuilder bufInsSql=new StringBuilder();
	StringBuilder bufInsSqlTempCount = new StringBuilder();
//	String countStr="";
//	String userName="";
	
	StringBuilder countStr=new StringBuilder();
	StringBuilder userName=new StringBuilder();
	
	DisbCancellationVO cVo= null;
	logger.info("here userid++++++++++++ "+vo.getReportingToUserId());
	try{
		String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+vo.getReportingToUserId()+"'";
		userName.append(ConnectionDAO.singleReturn(userNameQ));
		logger.info("userNameQ: "+userNameQ+" userName: "+userName);
		
		userNameQ=null;
		userName=null;
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	try {
		int count = 0;
		int startRecordIndex=0;
		int endRecordIndex = no;
		
		bufInsSql.append("select distinct a.loan_no, b.customer_name, c.product_desc, "
					+ "d.scheme_desc,a.loan_loan_amount, DATE_FORMAT(a.loan_approval_date,'"+dateFormat+"'),a.loan_id from cr_loan_dtl a,gcd_customer_m b,"
					+ " cr_product_m c, cr_scheme_m d, cr_loan_disbursal_dtl e where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product"
					+ " and d.scheme_id = a.loan_scheme and a.loan_id=e.loan_id and e.CANCELLATION_FLAG='"
					+ status + "'  AND a.REC_STATUS='A' AND A.LOAN_BRANCH='"+vo.getBranchId()+"' ");
		bufInsSqlTempCount.append("select count(distinct e.loan_id) from cr_loan_dtl a,gcd_customer_m b,cr_product_m c, cr_scheme_m d, cr_loan_disbursal_dtl e" +
				" where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product"
				+ " and d.scheme_id = a.loan_scheme and a.loan_id=e.loan_id and e.CANCELLATION_FLAG='"
				+ status + "'   AND a.REC_STATUS='A' AND A.LOAN_BRANCH='"+vo.getBranchId()+"' ");
	
		logger.info("stage ----"+vo.getStage());		
		
		if ( vo.getStage().equalsIgnoreCase("F")) {
			bufInsSql.append(" AND E.CANCELLATION_MAKER_ID!='"
					+StringEscapeUtils.escapeSql(CommonFunction.checkNull( vo.getUserId()).trim())+ "' ");
			bufInsSqlTempCount.append(" AND E.CANCELLATION_MAKER_ID!='"
					+StringEscapeUtils.escapeSql(CommonFunction.checkNull( vo.getUserId()).trim())+ "' ");
		}	
		 		

		logger.info("In searchClosureData : " + bufInsSql.toString());
		mainlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
		countStr.append(CommonFunction.checkNull(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString())));
		logger.info("Count String: "+countStr);
		if(countStr.toString().equalsIgnoreCase(""))
			count=0;
		else
			count = Integer.parseInt(countStr.toString());
		
		// Logic for paging
		if((vo.getLbxLoanNoHID()==null && vo.getCustomerName()==null) || (vo.getLbxLoanNoHID().equalsIgnoreCase("") && vo.getCustomerName().equalsIgnoreCase(""))
				||( vo.getCurrentPageLink()>1))
		{
			logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
			if(vo.getCurrentPageLink()>1)
			{
				startRecordIndex = (vo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}
			
			bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
			
		}
		// Logic for paging ends
		bufInsSql= null;
		bufInsSqlTempCount = null;
		
		int size = mainlist.size();
		logger.info("In searchClosureData.....mainlist size: "+ size);
		for (int i = 0; i < size; i++) {

			subList = (ArrayList) mainlist.get(i);
			logger.info("In searchClosureData......sublist size: "
					+ subList.size());
			if (subList.size() > 0) {
				
				cVo= new DisbCancellationVO();
				
				
					logger.info("status in search method"+status);
				if(status.equalsIgnoreCase("P")){	
			   	cVo.setLoanAc("<a href=disbursalCancellation.do?method=openNewCancellationWithValues&loanId="
				      					+ (CommonFunction.checkNull(subList.get(6)).trim())+">"+ (CommonFunction.checkNull(subList.get(0)).trim()) + "</a>");
				}
			   	else
			   	{
			   		cVo.setLoanAc("<a href=disbursalCancellation.do?method=openCancellationAuthorWithValues&loanId="
	      					+ (CommonFunction.checkNull(subList.get(6)).trim())+">"+ (CommonFunction.checkNull(subList.get(0)).trim()) + "</a>");		
			   	}
					
				cVo.setCustomerName(CommonFunction.checkNull(subList.get(1)).trim());
				cVo.setProduct(CommonFunction.checkNull(subList.get(2)).trim());
				cVo.setScheme(CommonFunction.checkNull(subList.get(3)).trim());
				if(CommonFunction.checkNull(subList.get(4)).trim().equalsIgnoreCase(""))
					cVo.setLoanAmt("0.00");
				else
				{
					Number loanAmt= myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subList.get(4)).trim()));
					cVo.setLoanAmt(myFormatter.format(loanAmt));
				}
				cVo.setLoanDate((CommonFunction.checkNull(subList.get(5)).trim()));
				cVo.setLbxLoanNoHID((CommonFunction.checkNull(subList.get(6)).trim()));				
				cVo.setTotalRecordSize(count);
				searchDataList.add(cVo);
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		vo = null;
		mainlist= null;
		subList = null;
		
		countStr= null;
		cVo= null;
	}
	
	return searchDataList;
}
	public ArrayList<DisbCancellationVO> searchCancellationData(Object ob, String status) {
		ArrayList<DisbCancellationVO> searchDataList = new ArrayList<DisbCancellationVO>();
		DisbCancellationVO vo = (DisbCancellationVO) ob;
		logger.info("In searchCancellationData DAOImpl::::::::::::::::CancellationDAOImpl");
		
		ArrayList mainlist = new ArrayList();
		ArrayList subList = new ArrayList();
		StringBuilder bufInsSql=new StringBuilder();
		StringBuilder bufInsSqlTempCount = new StringBuilder();
//		String countStr="";
//		String userName="";
		
		StringBuilder countStr=new StringBuilder();
		StringBuilder userName=new StringBuilder();
		
		DisbCancellationVO cVo= null;
		logger.info("here userid++++++++++++ "+vo.getReportingToUserId());
		try{
			String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+vo.getReportingToUserId()+"'";
			userName.append(ConnectionDAO.singleReturn(userNameQ));
			logger.info("userNameQ: "+userNameQ+" userName: "+userName);
			
			userNameQ=null;
			userName=null;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		try {
			int count = 0;
			int startRecordIndex=0;
			int endRecordIndex = no;
			
			bufInsSql.append("select distinct a.loan_no, b.customer_name, c.product_desc, "
						+ "d.scheme_desc,a.loan_loan_amount, DATE_FORMAT(a.loan_approval_date,'"+dateFormat+"'),a.loan_id from cr_loan_dtl a,gcd_customer_m b,"
						+ " cr_product_m c, cr_scheme_m d, cr_loan_disbursal_dtl e where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product"
						+ " and d.scheme_id = a.loan_scheme and a.loan_id=e.loan_id and e.CANCELLATION_FLAG='"
						+ status + "'   AND A.LOAN_BRANCH='"+vo.getBranchId()+"' ");
			bufInsSqlTempCount.append("select count(distinct e.loan_id) from cr_loan_dtl a,gcd_customer_m b,cr_product_m c, cr_scheme_m d, cr_loan_disbursal_dtl e" +
					" where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product"
					+ " and d.scheme_id = a.loan_scheme and a.loan_id=e.loan_id and e.CANCELLATION_FLAG='"
					+ status + "'   AND A.LOAN_BRANCH='"+vo.getBranchId()+"' ");
		
			logger.info("stage ----"+vo.getStage());
			
			if (!(CommonFunction.checkNull(vo.getCustomerName()) == "")) {
				bufInsSql.append(" AND b.customer_name like '%"
						+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName()).trim())+ "%' ");
				bufInsSqlTempCount.append(" AND b.customer_name like '%"
						+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName()).trim())+ "%' ");
			}			
			else if (!(CommonFunction.checkNull(vo.getLbxLoanNoHID()) == "")) {
				bufInsSql.append(" AND e.loan_id='"
						+StringEscapeUtils.escapeSql(CommonFunction.checkNull( vo.getLbxLoanNoHID()).trim())+ "' ");
				bufInsSqlTempCount.append(" AND e.loan_id='"
						+StringEscapeUtils.escapeSql(CommonFunction.checkNull( vo.getLbxLoanNoHID()).trim())+ "' ");
			}
			
			else if (!(CommonFunction.checkNull(vo.getUserId()) == "")&& vo.getStage().equalsIgnoreCase("F")) {
				bufInsSql.append(" AND E.CANCELLATION_MAKER_ID='"
						+StringEscapeUtils.escapeSql(CommonFunction.checkNull( vo.getUserId()).trim())+ "' ");
				bufInsSqlTempCount.append(" AND E.CANCELLATION_MAKER_ID='"
						+StringEscapeUtils.escapeSql(CommonFunction.checkNull( vo.getUserId()).trim())+ "' ");
			}
			 
			else if (!(CommonFunction.checkNull(vo.getCustomerName()) == "")
					&& !(CommonFunction.checkNull(vo.getLbxLoanNoHID()) == "")) {
				bufInsSql.append(" AND e.loan_id='"
						+StringEscapeUtils.escapeSql(CommonFunction.checkNull( vo.getLbxLoanNoHID()).trim())
						+ "' and b.customer_name like '%"
						+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName()).trim())+ "%'");
				bufInsSqlTempCount.append(" AND e.loan_id='"
						+StringEscapeUtils.escapeSql(CommonFunction.checkNull( vo.getLbxLoanNoHID()).trim())
						+ "' and b.customer_name like '%"
						+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName()).trim())+ "%'");
			}

			logger.info("In searchCancellationData : " + bufInsSql.toString());
			mainlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			countStr.append(CommonFunction.checkNull(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString())));
			logger.info("Count String: "+countStr);
			if(countStr.toString().equalsIgnoreCase(""))
				count=0;
			else
				count = Integer.parseInt(countStr.toString());
			
			// Logic for paging
			if((vo.getLbxLoanNoHID()==null && vo.getCustomerName()==null) || (vo.getLbxLoanNoHID().equalsIgnoreCase("") && vo.getCustomerName().equalsIgnoreCase(""))
					||( vo.getCurrentPageLink()>1))
			{
				logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
				if(vo.getCurrentPageLink()>1)
				{
					startRecordIndex = (vo.getCurrentPageLink()-1)*no;
					endRecordIndex = no;
					logger.info("startRecordIndex .................... "+startRecordIndex);
					logger.info("endRecordIndex .................... "+endRecordIndex);
				}
				
				bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
				
			}
			// Logic for paging ends
			bufInsSql= null;
			bufInsSqlTempCount = null;
			
			int size = mainlist.size();
			logger.info("In searchClosureData.....mainlist size: "+ size);
			for (int i = 0; i < size; i++) {

				subList = (ArrayList) mainlist.get(i);
				logger.info("In searchClosureData......sublist size: "
						+ subList.size());
				if (subList.size() > 0) {
					
					cVo= new DisbCancellationVO();
					
					
						logger.info("status in search method"+status);
					if(status.equalsIgnoreCase("P")){	
				   	cVo.setLoanAc("<a href=disbursalCancellation.do?method=openNewCancellationWithValues&loanId="
					      					+ (CommonFunction.checkNull(subList.get(6)).trim())+">"+ (CommonFunction.checkNull(subList.get(0)).trim()) + "</a>");
					}
				   	else
				   	{
				   		cVo.setLoanAc("<a href=disbursalCancellation.do?method=openCancellationAuthorWithValues&loanId="
		      					+ (CommonFunction.checkNull(subList.get(6)).trim())+">"+ (CommonFunction.checkNull(subList.get(0)).trim()) + "</a>");		
				   	}
						
					cVo.setCustomerName(CommonFunction.checkNull(subList.get(1)).trim());
					cVo.setProduct(CommonFunction.checkNull(subList.get(2)).trim());
					cVo.setScheme(CommonFunction.checkNull(subList.get(3)).trim());
					if(CommonFunction.checkNull(subList.get(4)).trim().equalsIgnoreCase(""))
						cVo.setLoanAmt("0.00");
					else
					{
						Number loanAmt= myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subList.get(4)).trim()));
						cVo.setLoanAmt(myFormatter.format(loanAmt));
					}
					cVo.setLoanDate((CommonFunction.checkNull(subList.get(5)).trim()));
					cVo.setLbxLoanNoHID((CommonFunction.checkNull(subList.get(6)).trim()));				
					cVo.setTotalRecordSize(count);
					searchDataList.add(cVo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			vo = null;
			mainlist= null;
			subList = null;
			
			countStr= null;
			cVo= null;
		}
		
		return searchDataList;
	}
	
	public ArrayList<DisbCancellationVO> getCancellationValues(int lbxLoanNoHID) {
		ArrayList<DisbCancellationVO> loanDetailList = new ArrayList<DisbCancellationVO>();
		logger.info("In getCancellationValues Loan Id : " + lbxLoanNoHID);
		ArrayList mainlist = new ArrayList();
		ArrayList subList = new ArrayList();
		StringBuilder query = new StringBuilder();
		DisbCancellationVO loanvo = null;
//		String query1 = "";
		StringBuilder query1 =new StringBuilder();
		DisbCancellationVO loanvo1 = null;
		try {

			query.append("select distinct a.loan_no,a.loan_id, b.customer_name,");
			query.append(" DATE_FORMAT(a.loan_approval_date,'"+dateFormat+"'),a.loan_loan_amount,c.product_desc, ");
			query.append(" d.scheme_desc, ");
			  query.append(" if(a.LOAN_REPAYMENT_TYPE='I' AND e.BILL_FLAG='N','INB',IF(a.LOAN_REPAYMENT_TYPE='I' AND e.BILL_FLAG='Y','IYB','NNB'))billflag ");
			query.append(" from cr_loan_dtl a join gcd_customer_m b on b.customer_id = a.loan_customer_id ");
			query.append(" join  cr_product_m c on c.product_id = a.loan_product join cr_scheme_m d on d.scheme_id = a.loan_scheme "
					+ " left join cr_repaysch_dtl e on e.LOAN_ID=a.LOAN_ID and INSTL_NO=1 and e.LOAN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(Integer.valueOf(lbxLoanNoHID)).trim())+"' ");
				
		
			query.append(" where a.loan_id='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(lbxLoanNoHID).trim()) + "'");
			logger.info("In getLoanDetails : " + query);
			mainlist = ConnectionDAO.sqlSelect(query.toString());
			
			query = null;
			
			int size = mainlist.size();
			logger.info("In getCancellationValues.....mainlist size: "+ size);
			for (int i = 0; i < size; i++) {

				subList = (ArrayList) mainlist.get(i);
				if (subList.size() > 0) {
					loanvo = new DisbCancellationVO();
					loanvo.setLoanAc((CommonFunction.checkNull(subList.get(0)).trim()));
					loanvo.setLbxLoanNoHID((CommonFunction.checkNull(subList.get(1)).trim()));
					loanvo.setCustomerName((CommonFunction.checkNull(subList.get(2)).trim()));
					loanvo.setLoanDate((CommonFunction.checkNull(subList.get(3)).trim()));
					if((CommonFunction.checkNull(subList.get(4)).trim()).equalsIgnoreCase(""))
						loanvo.setLoanAmt("0.00");
					else
					{
						Number loanAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(4)).trim()));
						loanvo.setLoanAmt(myFormatter.format(loanAmt));
					}
					loanvo.setProduct((CommonFunction.checkNull(subList.get(5)).trim()));
					loanvo.setScheme((CommonFunction.checkNull(subList.get(6)).trim()));
					 loanvo.setBillFlagYesOrNo(CommonFunction.checkNull(subList.get(7)).trim());			
					loanDetailList.add(loanvo);
				}
			}
			
			
			size = mainlist.size();
			logger.info("In getCancellationValues.....mainlist size: "+size);
			for (int i = 0; i < size; i++) {

				subList = (ArrayList) mainlist.get(i);
				if (subList.size() > 0) {
					loanvo1 = new DisbCancellationVO();
					//loanvo1.setRemainingTenure((CommonFunction.checkNull(subList.get(0))).trim());
					loanDetailList.add(loanvo1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			mainlist = null;
			subList = null;
		
			loanvo = null;
			
			loanvo1 = null;
		}
		return loanDetailList;
	}	
	
	public ArrayList<DisbCancellationVO> getLoanDetails(int loanId) {
		ArrayList<DisbCancellationVO> getLoanDetails = new ArrayList<DisbCancellationVO>();
		logger.info("In getLoanDetails Loan Id : " + loanId);
		ArrayList mainlist = new ArrayList();
		ArrayList subList = new ArrayList();
		StringBuilder query = new StringBuilder();
		DisbCancellationVO loanvo = null;
//		String query1 = "";
		StringBuilder query1 =new StringBuilder();
		DisbCancellationVO loanvo1 = null;
		try {

			/*query.append("select distinct a.loan_no,a.loan_id, b.customer_name,");
			query.append(" DATE_FORMAT(a.loan_approval_date,'"+dateFormat+"'),a.loan_loan_amount,c.product_desc, ");
			query.append(" d.scheme_desc ");	
			query.append(" from cr_loan_dtl a,gcd_customer_m b,");
			query.append(" cr_product_m c, cr_scheme_m d where b.customer_id = a.loan_customer_id and "); 
			query.append(" c.product_id = a.loan_product");
			query.append(" and d.scheme_id = a.loan_scheme and a.loan_id='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim()) + "'");*/
			
			query.append(" select distinct a.loan_no,a.loan_id, b.customer_name, DATE_FORMAT(a.loan_approval_date,'%d-%m-%Y'),");
			       query.append(" a.loan_loan_amount,c.product_desc,d.scheme_desc, ");
			       query.append(" if(a.LOAN_REPAYMENT_TYPE='I' AND e.BILL_FLAG='N','INB',IF(a.LOAN_REPAYMENT_TYPE='I' AND e.BILL_FLAG='Y','IYB','NNB'))billflag ");
			       query.append(" from cr_loan_dtl a ");
			      query.append(" join gcd_customer_m b on b.customer_id = a.loan_customer_id  ");
			       query.append(" join  cr_product_m c on c.product_id = a.loan_product ");
			      query.append(" join cr_scheme_m d on d.scheme_id = a.loan_scheme ");
			       query.append(" left join cr_repaysch_dtl e on e.LOAN_ID=a.LOAN_ID and INSTL_NO=1 and e.LOAN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(Integer.valueOf(loanId)).trim())+"' ");
			      query.append(" where a.loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(Integer.valueOf(loanId)).trim())+"' ");
			
			logger.info("In getCancellationValues : " + query);
			mainlist = ConnectionDAO.sqlSelect(query.toString());
			
			query = null;
			
			int size = mainlist.size();
			logger.info("In getCancellationValues.....mainlist size: "+ size);
			for (int i = 0; i < size; i++) {

				subList = (ArrayList) mainlist.get(i);
				if (subList.size() > 0) {
					loanvo = new DisbCancellationVO();
					loanvo.setLoanAc((CommonFunction.checkNull(subList.get(0)).trim()));
					loanvo.setLbxLoanNoHID((CommonFunction.checkNull(subList.get(1)).trim()));
					loanvo.setCustomerName((CommonFunction.checkNull(subList.get(2)).trim()));
					loanvo.setLoanDate((CommonFunction.checkNull(subList.get(3)).trim()));
					if((CommonFunction.checkNull(subList.get(4)).trim()).equalsIgnoreCase(""))
						loanvo.setLoanAmt("0.00");
					else
					{
						Number loanAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(4)).trim()));
						loanvo.setLoanAmt(myFormatter.format(loanAmt));
					}
					loanvo.setProduct((CommonFunction.checkNull(subList.get(5)).trim()));
					loanvo.setScheme((CommonFunction.checkNull(subList.get(6)).trim()));
					 loanvo.setBillFlagYesOrNo(CommonFunction.checkNull(subList.get(7)).trim());
								
					getLoanDetails.add(loanvo);
				}
			}
			
		
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			mainlist = null;
			subList = null;
		
			loanvo = null;
			
			
		}
		return getLoanDetails;
	}
	
	
	
	public ArrayList<DisbCancellationVO> getDisbursalDetails(int loanId) {
		ArrayList<DisbCancellationVO> getDisbursalDetails = new ArrayList<DisbCancellationVO>();
		logger.info("In getDisbursalDetails Loan Id : " + loanId);
		ArrayList mainlist = new ArrayList();
		ArrayList subList = new ArrayList();
		StringBuilder query = new StringBuilder();
		DisbCancellationVO loanvo = null;
//		String query1 = "";
		
		
		try {
			
			query.append("select  A.LOAN_ID,a.disbursal_no,DATE_FORMAT(a.disbursal_date,'"+dateFormat+"'),a.disbursal_amount,");
			query.append("(select DESCRIPTION from generic_master where generic_key='BPTYPE' and value=DISBURSAL_TO)");
			query.append(" ,b.bp_name,CANCELLATION_REMARKS,ifnull(CANCELLATION_FLAG,'')='P',DATE_FORMAT(a.CANCELLATION_DATE,'"+dateFormat+"') ");	
			query.append(" ,a.LOAN_DISBURSAL_ID  from cr_loan_disbursal_dtl a");	
			query.append(" join business_partner_view b on a.LOAN_ID = b.LOAN_ID and b.bp_id=disbursal_to_id and disbursal_to = b.BP_TYPE ");
			query.append(" where a.loan_id='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim()) + "'");
			query.append(" AND A.REC_STATUS='A' "); 
			query.append(" order BY a.disbursal_no desc ");
			logger.info("In getLoanDetails : query" + query);
			mainlist = ConnectionDAO.sqlSelect(query.toString());
			
			query = null;
			
			int size = mainlist.size();
			logger.info("In getDisbursalDetails.....mainlist size: "+ size);
			for (int i = 0; i < size; i++) {

				subList = (ArrayList) mainlist.get(i);
				if (subList.size() > 0) {
					loanvo = new DisbCancellationVO();
					loanvo.setDisbursalNumber((CommonFunction.checkNull(subList.get(1)).trim()));					
					loanvo.setDisbursedDate((CommonFunction.checkNull(subList.get(2)).trim()));
					if((CommonFunction.checkNull(subList.get(3)).trim()).equalsIgnoreCase(""))
						loanvo.setDisbursedAmount("0.00");
					else
					{
						Number setDisbursedAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(3)).trim()));
						loanvo.setDisbursedAmount(myFormatter.format(setDisbursedAmount));
					}					
					loanvo.setDisbursedTo((CommonFunction.checkNull(subList.get(4)).trim()));					
					loanvo.setToName((CommonFunction.checkNull(subList.get(5)).trim()));
					loanvo.setCancellationRemarks((CommonFunction.checkNull(subList.get(6)).trim()));
					loanvo.setCancellationFlag((CommonFunction.checkNull(subList.get(7)).trim()));
					loanvo.setCancDate(CommonFunction.checkNull(subList.get(8)).trim());
					loanvo.setDisbursalIDNew(CommonFunction.checkNull(subList.get(9)).trim());
					getDisbursalDetails.add(loanvo);
				}
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			mainlist = null;
			subList = null;
		
			loanvo = null;			
			
		}
		return getDisbursalDetails;
	}
	public ArrayList<DisbCancellationVO> getDisbursalDetailsInAuthor(int loanId) {
		ArrayList<DisbCancellationVO> getDisbursalDetails = new ArrayList<DisbCancellationVO>();
		logger.info("In getDisbursalDetails Loan Id : " + loanId);
		ArrayList mainlist = new ArrayList();
		ArrayList subList = new ArrayList();
		StringBuilder query = new StringBuilder();
		DisbCancellationVO loanvo = null;
//		String query1 = "";
		
		
		try {
			
			query.append("select  A.LOAN_ID,a.disbursal_no,DATE_FORMAT(a.disbursal_date,'"+dateFormat+"'),a.disbursal_amount,");
			query.append("(select DESCRIPTION from generic_master where generic_key='BPTYPE' and value=DISBURSAL_TO)");
			query.append(" ,g.customer_name,CANCELLATION_REMARKS,ifnull(CANCELLATION_FLAG,'')='F',DATE_FORMAT(a.CANCELLATION_DATE,'"+dateFormat+"') ");	
			query.append(" ,a.LOAN_DISBURSAL_ID from cr_loan_disbursal_dtl a "
					+ " join gcd_customer_m g on g.customer_id=a.disbursal_to_id ");		
			query.append(" join  business_partner_view b on a.LOAN_ID = b.LOAN_ID and b.bp_id=disbursal_to_id and disbursal_to = b.BP_TYPE ");
			query.append(" where a.loan_id='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim()) + "'");
			query.append(" AND A.REC_STATUS='A' and a.CANCELLATION_FLAG='F' "); 
			query.append(" order BY a.disbursal_no desc ");
			logger.info("In getLoanDetails : query" + query);
			mainlist = ConnectionDAO.sqlSelect(query.toString());
			
			query = null;
			
			int size = mainlist.size();
			logger.info("In getCancellationValues.....mainlist size: "+ size);
			for (int i = 0; i < size; i++) {

				subList = (ArrayList) mainlist.get(i);
				if (subList.size() > 0) {
					loanvo = new DisbCancellationVO();
					loanvo.setDisbursalNumber((CommonFunction.checkNull(subList.get(1)).trim()));					
					loanvo.setDisbursedDate((CommonFunction.checkNull(subList.get(2)).trim()));
					if((CommonFunction.checkNull(subList.get(3)).trim()).equalsIgnoreCase(""))
						loanvo.setDisbursedAmount("0.00");
					else
					{
						Number setDisbursedAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(3)).trim()));
						loanvo.setDisbursedAmount(myFormatter.format(setDisbursedAmount));
					}
					if((CommonFunction.checkNull(subList.get(4)).trim()).equalsIgnoreCase("CS"))
						loanvo.setDisbursedTo("CUSTOMER");
					else 
						loanvo.setDisbursedTo((CommonFunction.checkNull(subList.get(4)).trim()));
					
					loanvo.setToName((CommonFunction.checkNull(subList.get(5)).trim()));
					loanvo.setCancellationRemarks((CommonFunction.checkNull(subList.get(6)).trim()));
					loanvo.setCancellationFlag((CommonFunction.checkNull(subList.get(7)).trim()));	
					loanvo.setCancDate((CommonFunction.checkNull(subList.get(8)).trim()));
					loanvo.setDisbursalIDNew(CommonFunction.checkNull(subList.get(9)).trim());
					getDisbursalDetails.add(loanvo);
				}
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			mainlist = null;
			subList = null;
		
			loanvo = null;			
			
		}
		return getDisbursalDetails;
	}
	
	public boolean saveCancellationData(DisbCancellationVO vo){
		logger.info("In updateCancellationData.....................................CancellationDAOImpl");
	boolean status = false;
	ArrayList queryList = new ArrayList();
	StringBuilder query = new StringBuilder();
	String [] chk=vo.getChk();
	String [] disbId=vo.getDisbursalID();
	logger.info("disbId len"+disbId.length);
	logger.info("chk len"+chk.length);
	String [] cancellatioDate=vo.getCancellationDate();
	logger.info(" cancellatioDate--"+cancellatioDate.length);
	String chkAllVal="";
	String cancellationDateAllVal="";
	PrepStmtObject insertPrepStmtObject =null;
	try {
		for(int i=0;i<chk.length;i++){
			 insertPrepStmtObject = new PrepStmtObject();	
			 query=new StringBuilder();
			 chkAllVal=chkAllVal+"'"+disbId[i]+"',";
			 //cancellationDateAllVal=cancellationDateAllVal+"'"+cancellatioDate[i]+"',";
			logger.info(" chkAllVal--"+chkAllVal);
		    query.append("update cr_loan_disbursal_dtl set CANCELLATION_FLAG='P' ,CANCELLATION_MAKER_ID=?,CANCELLATION_MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)");		
		    query.append(",CANCELLATION_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)");
		    query.append(" where loan_id=? and LOAN_DISBURSAL_ID =?");
		
		
			
		
		if(CommonFunction.checkNull(vo.getCancellationMakerID()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getCancellationMakerID()).trim()));
		
		if(CommonFunction.checkNull(vo.getCancellationMakerDate()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getCancellationMakerDate()).trim()));
		// Cancellation Date
		if(CommonFunction.checkNull(cancellatioDate[i]).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((CommonFunction.checkNull(cancellatioDate[i]).trim()));
		
		if(CommonFunction.checkNull(vo.getLbxLoanNoHID()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()));	
		
		
		if(CommonFunction.checkNull(disbId[i]).equalsIgnoreCase(""))
		{
			logger.info("disbId[i]"+i+"---"+CommonFunction.checkNull(disbId[i]));
			insertPrepStmtObject.addNull();
		}
		else{
			logger.info("disbId[i]"+i+"---"+CommonFunction.checkNull(disbId[i]));
			insertPrepStmtObject.addString((CommonFunction.checkNull(disbId[i]).trim()));
		}
		
		insertPrepStmtObject.setSql(query.toString());
		
	
	
	logger.info("IN insertUpdateClosureData() update query ### "+insertPrepStmtObject.printQuery());
		queryList.add(insertPrepStmtObject);
		
	}
	  String queryCheck="Select count(1) from cr_loan_disbursal_dtl where loan_id='"+vo.getLbxLoanNoHID()+"' and  LOAN_DISBURSAL_ID not in ("+chkAllVal.substring(0, chkAllVal.length()-1)+") ";
		logger.info("queryCheck:------------------"+queryCheck);
	  String queryCheckResult=ConnectionDAO.singleReturn(queryCheck);
		if(!CommonFunction.checkNull(queryCheckResult).equalsIgnoreCase("0")){
			insertPrepStmtObject = new PrepStmtObject();	
			 query=new StringBuilder();
			 query.append("update cr_loan_disbursal_dtl set CANCELLATION_FLAG='' where  loan_id='"+vo.getLbxLoanNoHID()+"' and  LOAN_DISBURSAL_ID not in ("+chkAllVal.substring(0, chkAllVal.length()-1)+")"  );
			 insertPrepStmtObject.setSql(query.toString());
			 logger.info("IN insertUpdateClosureData() update query for non check### "+insertPrepStmtObject.printQuery());
			 queryList.add(insertPrepStmtObject);
		}
	  status = ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
		logger.info("In insertUpdateClosureData.........update status: "+ status);
		
		query = null;
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		queryList = null;
	
		insertPrepStmtObject = null;
		vo = null;
	}
	return status;
}
	
	public boolean forwardCancellationData(DisbCancellationVO vo){
		logger.info("In updateCancellationData.....................................CancellationDAOImpl");
		boolean status = false;
		ArrayList queryList = new ArrayList();
		StringBuilder query = new StringBuilder();
		String [] chk=vo.getChk();
		logger.info("chk len"+chk.length);
		String [] disbId=vo.getDisbursalID();
		logger.info("disbId len"+disbId.length);
		String chkAllVal="";
		PrepStmtObject insertPrepStmtObject =null;
		try {
			for(int i=0;i<chk.length;i++){
				 insertPrepStmtObject = new PrepStmtObject();	
				 query=new StringBuilder();
				 chkAllVal=chkAllVal+"'"+disbId[i]+"',";
			query.append("update cr_loan_disbursal_dtl set CANCELLATION_FLAG='F' ,CANCELLATION_MAKER_ID=?,CANCELLATION_MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)");		
			query.append(" where loan_id=? and LOAN_DISBURSAL_ID=?");
			
			
				
			
			if(CommonFunction.checkNull(vo.getCancellationMakerID()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getCancellationMakerID()).trim()));
			
			if(CommonFunction.checkNull(vo.getCancellationMakerDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getCancellationMakerDate()).trim()));
			
			if(CommonFunction.checkNull(vo.getLbxLoanNoHID()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()));
			
			
			
			if(CommonFunction.checkNull(disbId[i]).equalsIgnoreCase(""))
			{
				logger.info("disbId[i]"+i+"---"+CommonFunction.checkNull(disbId[i]));
				insertPrepStmtObject.addNull();
			}
			else{
				logger.info("disbId[i]"+i+"---"+CommonFunction.checkNull(disbId[i]));
				insertPrepStmtObject.addString((CommonFunction.checkNull(disbId[i]).trim()));
			}
			
			insertPrepStmtObject.setSql(query.toString());
			
		
		
		logger.info("IN insertUpdateClosureData() update query ### "+insertPrepStmtObject.printQuery());
			queryList.add(insertPrepStmtObject);
			
		}
		  String queryCheck="Select count(1) from cr_loan_disbursal_dtl where loan_id='"+vo.getLbxLoanNoHID()+"' and  LOAN_DISBURSAL_ID not in ("+chkAllVal.substring(0, chkAllVal.length()-1)+") ";
			logger.info("queryCheck:------------------"+queryCheck);
		  String queryCheckResult=ConnectionDAO.singleReturn(queryCheck);
			if(!CommonFunction.checkNull(queryCheckResult).equalsIgnoreCase("0")){
				insertPrepStmtObject = new PrepStmtObject();	
				 query=new StringBuilder();
				 query.append("update cr_loan_disbursal_dtl set CANCELLATION_FLAG='' where  loan_id='"+vo.getLbxLoanNoHID()+"' and  LOAN_DISBURSAL_ID not in ("+chkAllVal.substring(0, chkAllVal.length()-1)+")"  );
				 insertPrepStmtObject.setSql(query.toString());
				 logger.info("IN insertUpdateClosureData() update query for non check### "+insertPrepStmtObject.printQuery());
				 queryList.add(insertPrepStmtObject);
			}
		  status = ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
			logger.info("In insertUpdateClosureData.........update status: "+ status);
			
			query = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			queryList = null;
		
			insertPrepStmtObject = null;
			vo = null;
		}
		return status;
}
	
	public String saveCancelLtionAuthor(Object ob) {
		logger.info("In saveCancelLtionAuthor");
		DisbCancellationVO vo = (DisbCancellationVO) ob;
		String status = "";
		logger.info("In saveCancelLtionAuthor: "+ vo.getDecision());
		String s1= "";
		String s2 = "";
		ArrayList<Object> in =new ArrayList<Object>();
		ArrayList<Object> out =new ArrayList<Object>();
		ArrayList outMessages = new ArrayList();
		logger.info("In PROCEDURE Loan_Cancellation_Author");
			try {
			   	in.add(vo.getCompanyId());
			   	in.add(Integer.parseInt(CommonFunction.checkNull (vo.getLbxLoanNoHID()).trim()));
				 String date=CommonFunction.changeFormat(CommonFunction.checkNull (vo.getAuthorDate()).trim());
	        	    if(date != null)
	        	    	in.add(date);
	        	    in.add(CommonFunction.checkNull (vo.getAuthorId()).trim());
	        	    in.add(CommonFunction.checkNull (vo.getDecision()).trim());
	        	    logger.info("Decision--------"+CommonFunction.checkNull (vo.getDecision()).trim());
	        	    in.add(CommonFunction.checkNull (vo.getComments()).trim());
			    out.add(s1);
	    	    out.add(s2);
	    	    outMessages=(ArrayList) ConnectionDAO.callSP("Disbursal_Cancellation_Author",in,out);
	    	    s1=CommonFunction.checkNull(outMessages.get(0));
	    	    s2=CommonFunction.checkNull(outMessages.get(1));

				if(s1!=null && s1.equalsIgnoreCase("S"))
				{
					status=s1;	
				}
				else if(s1!=null && s1.equalsIgnoreCase("E"))
				{
					status=s2;	
				}
				logger.info("status--------2: "+s1);
				logger.info("s2------------2: "+s2);
			} catch (Exception e) {			
				
				
				e.printStackTrace();
			}
			finally
			{
			
				s1=null;
				s2 =null;
			}
		
		return status;
	}
	public ArrayList<DisbCancellationVO> getAdviceDetails(DisbCancellationVO vo) {
		ArrayList<DisbCancellationVO> getAdviceDetails = new ArrayList<DisbCancellationVO>();
		logger.info("In getAdviceDetails Loan Id : " + vo.getLbxLoanNoHID());
		ArrayList mainlist = new ArrayList();
		ArrayList subList = new ArrayList();
		StringBuilder query = new StringBuilder();
		DisbCancellationVO loanvo = null;
//		String query1 = "";
		String [] chk=vo.getChk();
		String [] disbId=vo.getDisbursalID();
		String tempresult="";
		try {
			for(int i=0;i<chk.length;i++){
//				String inParameter1="'"+chk+"'";
//				String temp="+,+"+inParameter1;
//				tempresult=temp+inParameter1;	
				tempresult=tempresult+"'"+disbId[i]+"',";
				}
			
			logger.info("tempresult----------"+tempresult);
			logger.info("tempresulton:-------------"+tempresult.substring(0, tempresult.length()-1));
			tempresult=tempresult.substring(0, tempresult.length()-1);
			query.append("select if(sum(TXN_ADJUSTED_AMOUNT)>0 ,'Y','N'),if(sum(AMOUNT_IN_PROCESS)>0 ,'Y','N')");
			query.append(" from cr_txnadvice_dtl");					
			query.append(" where loan_id="+vo.getLbxLoanNoHID()+" and txn_id");
			query.append(" in ("+tempresult+") ");
			logger.info("In getLoanDetails : query" + query);
			mainlist = ConnectionDAO.sqlSelect(query.toString());
			
			query = null;
			
			int size = mainlist.size();
			logger.info("In getCancellationValues.....mainlist size: "+ size);
			for (int i = 0; i < size; i++) {

				subList = (ArrayList) mainlist.get(i);
				if (subList.size() > 0) {
					loanvo = new DisbCancellationVO();
					loanvo.setTxnAdjustedAmt(CommonFunction.checkNull(subList.get(0)).trim());
					loanvo.setAmtInProcess(CommonFunction.checkNull(subList.get(1)).trim());
					getAdviceDetails.add(loanvo);
				}
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			mainlist = null;
			subList = null;
		
			loanvo = null;			
			
		}
		return getAdviceDetails;
	}
	
	public ArrayList<DisbCancellationVO> getAdviceDetailsOnAuthor(DisbCancellationVO vo) {
		ArrayList<DisbCancellationVO> getAdviceDetails = new ArrayList<DisbCancellationVO>();
		logger.info("In getAdviceDetails Loan Id : " + vo.getLbxLoanNoHID());
		ArrayList mainlist = new ArrayList();
		ArrayList subList = new ArrayList();
		StringBuilder query = new StringBuilder();
		DisbCancellationVO loanvo = null;
//		String query1 = "";		
		try {
			
			query.append("select if(sum(TXN_ADJUSTED_AMOUNT)>0 ,'Y','N'),if(sum(AMOUNT_IN_PROCESS)>0 ,'Y','N')");
			query.append(" from cr_txnadvice_dtl where loan_id="+ vo.getLbxLoanNoHID()+" and txn_id in (select LOAN_DISBURSAL_ID ");				
			query.append("from cr_loan_disbursal_dtl where loan_id="+ vo.getLbxLoanNoHID()+" and CANCELLATION_FLAG='F') ");
			logger.info("In getLoanDetails : query" + query);
			mainlist = ConnectionDAO.sqlSelect(query.toString());
			
			query = null;
			
			int size = mainlist.size();
			logger.info("In getCancellationValues.....mainlist size: "+ size);
			for (int i = 0; i < size; i++) {

				subList = (ArrayList) mainlist.get(i);
				if (subList.size() > 0) {
					loanvo = new DisbCancellationVO();
					loanvo.setTxnAdjustedAmt(CommonFunction.checkNull(subList.get(0)).trim());
					loanvo.setAmtInProcess(CommonFunction.checkNull(subList.get(1)).trim());
					getAdviceDetails.add(loanvo);
				}
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			mainlist = null;
			subList = null;
		
			loanvo = null;			
			
		}
		return getAdviceDetails;
	}
	
	public ArrayList<DisbCancellationVO> getBillFlagDetails(DisbCancellationVO vo) {
		ArrayList<DisbCancellationVO> getBillFlagDetails = new ArrayList<DisbCancellationVO>();
		logger.info("In getAdviceDetails Loan Id : " + vo.getLbxLoanNoHID());
		ArrayList mainlist = new ArrayList();
		ArrayList subList = new ArrayList();
		StringBuilder query = new StringBuilder();
		DisbCancellationVO loanvo = null;
//		String query1 = "";		
		try {
			
			/*query.append("select IFNULL(BILL_FLAG,'N'),IFNULL(REC_TYPE,'')  from cr_repaysch_dtl");
			query.append("  where loan_id="+vo.getLbxLoanNoHID()+" and REPAYSCH_ID ");				
			query.append(" IN (SELECT  max(REPAYSCH_ID) FROM cr_repaysch_dtl where loan_id="+vo.getLbxLoanNoHID()+" ) ");*/
			
			query.append("SELECT  ifnull(bill_flag,'N'), if((select count(1) from cr_repaysch_dtl where loan_id='"+vo.getLbxLoanNoHID()+"' and rec_type='P' )>0,'P','') ");
			query.append(" FROM cr_repaysch_dtl where loan_id='"+vo.getLbxLoanNoHID()+"' and instl_no=1 ");
			
			logger.info("In getBillFlagDetails : query" + query);
			mainlist = ConnectionDAO.sqlSelect(query.toString());
			
			query = null;
			
			int size = mainlist.size();
			logger.info("In getCancellationValues.....mainlist size: "+ size);
			for (int i = 0; i < size; i++) {

				subList = (ArrayList) mainlist.get(i);
				if (subList.size() > 0) {
					loanvo = new DisbCancellationVO();
					String billFlag=CommonFunction.checkNull(subList.get(0)).trim();
					String recType=CommonFunction.checkNull(subList.get(1)).trim();
					if(billFlag.equalsIgnoreCase("")||billFlag==null){
					loanvo.setBillFlag("N");	
					}
					else
					{
						loanvo.setBillFlag(CommonFunction.checkNull(subList.get(0)).trim());		
					}
					if(recType.equalsIgnoreCase("")||recType==null){
						loanvo.setRecType("N");	
						}
						else
						{
							loanvo.setRecType(CommonFunction.checkNull(subList.get(1)).trim());		
						}
					getBillFlagDetails.add(loanvo);
				}
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			mainlist = null;
			subList = null;
		
			loanvo = null;			
			
		}
		return getBillFlagDetails;
	}
	public ArrayList<DisbCancellationVO> getTAFlagDetails(DisbCancellationVO vo) {
		ArrayList<DisbCancellationVO> getTAFlagDetails = new ArrayList<DisbCancellationVO>();
		logger.info("In getTAFlagDetails Loan Id : " + vo.getLbxLoanNoHID());		
		ArrayList mainlist = new ArrayList();
		ArrayList subList = new ArrayList();
		StringBuilder query = new StringBuilder();
		DisbCancellationVO loanvo = null;
//		String query1 = "";
		String [] chk=vo.getChk();
		String [] disbId=vo.getDisbursalID();
		String tempresult="";
		try {
			for(int i=0;i<chk.length;i++){
//				String inParameter1="'"+chk+"'";
//				String temp="+,+"+inParameter1;
//				tempresult=temp+inParameter1;	
				tempresult=tempresult+"'"+disbId[i]+"',";
				}
			
			logger.info("tempresult----------"+tempresult);
			logger.info("tempresulton:-------------"+tempresult.substring(0, tempresult.length()-1));
			tempresult=tempresult.substring(0, tempresult.length()-1);
			query.append("select TA_LOAN_ID,LOAN_DISBURSAL_ID ,(Select LOAN_NO from cr_loan_dtl where Loan_id=TA_LOAN_ID) ");
			query.append(" from cr_loan_disbursal_dtl  ");					
			query.append(" where loan_id="+vo.getLbxLoanNoHID()+" and TA_ADJUSTMENT_FLAG='Y'");
			query.append("  and  LOAN_DISBURSAL_ID  in ("+tempresult+") ");
			logger.info("In getTAFlagDetails : query" + query);
			mainlist = ConnectionDAO.sqlSelect(query.toString());
			
			query = null;
			
			int size = mainlist.size();
			logger.info("In getTAFlagDetails.....mainlist size: "+ size);
			for (int i = 0; i < size; i++) {

				subList = (ArrayList) mainlist.get(i);
				if (subList.size() > 0) {
					loanvo = new DisbCancellationVO();
					loanvo.setTaLoanID(CommonFunction.checkNull(subList.get(0)).trim());
					logger.info(" TA Loan ID--"+CommonFunction.checkNull(subList.get(0)).trim());
					loanvo.setTaLoanNo(CommonFunction.checkNull(subList.get(2)).trim());
					logger.info(" TA Loan No--"+CommonFunction.checkNull(subList.get(2)).trim());
					getTAFlagDetails.add(loanvo);
				}
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			mainlist = null;
			subList = null;
		
			loanvo = null;			
			
		}
		return getTAFlagDetails;
	}
	
	public ArrayList<DisbCancellationVO> getDisbursalFlag(DisbCancellationVO vo) {
		ArrayList<DisbCancellationVO> getDisbursalFlag = new ArrayList<DisbCancellationVO>();
		logger.info("In getDisbursalFlag Loan Id : " + vo.getLbxLoanNoHID());
		ArrayList mainlist = new ArrayList();
		ArrayList subList = new ArrayList();
		StringBuilder query = new StringBuilder();
		DisbCancellationVO loanvo = null;
//		String query1 = "";		
		try {
			
			query.append("select loan_repayment_type from cr_loan_dtl ");
			query.append("  where loan_id="+vo.getLbxLoanNoHID()+"  ");			
			logger.info("In getDisbursalFlag : query" + query);
			mainlist = ConnectionDAO.sqlSelect(query.toString());
			
			query = null;
			
			int size = mainlist.size();
			logger.info("In getDisbursalFlag.....mainlist size: "+ size);
			for (int i = 0; i < size; i++) {

				subList = (ArrayList) mainlist.get(i);
				if (subList.size() > 0) {
					loanvo = new DisbCancellationVO();
					String disbFlag=CommonFunction.checkNull(subList.get(0)).trim();
					logger.info("In getDisbursalFlag.....disbFlag: "+ disbFlag);
					if(disbFlag.equalsIgnoreCase("I")){
					loanvo.setDisbFlag("F");	
					}
					else
					{
						loanvo.setDisbFlag("N");	
						
					}
					getDisbursalFlag.add(loanvo);
				}
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			mainlist = null;
			subList = null;
		
			loanvo = null;			
			
		}
		return getDisbursalFlag;
	}
	
	public ArrayList<DisbCancellationVO> getTAFlagDetailsAuthor(DisbCancellationVO vo) {
		ArrayList<DisbCancellationVO> getTAFlagDetailsAuthor = new ArrayList<DisbCancellationVO>();
		logger.info("In getTAFlagDetailsAuthor Loan Id : " + vo.getLbxLoanNoHID());
		ArrayList mainlist = new ArrayList();
		ArrayList subList = new ArrayList();
		
		StringBuilder query = new StringBuilder();
		StringBuilder query2 = new StringBuilder();
		DisbCancellationVO loanvo = null;

		try {

			query.append("select TA_LOAN_ID,LOAN_DISBURSAL_ID ,(Select LOAN_NO from cr_loan_dtl where Loan_id=TA_LOAN_ID) ");
			query.append(" from cr_loan_disbursal_dtl  ");					
			query.append(" where loan_id="+vo.getLbxLoanNoHID()+" and TA_ADJUSTMENT_FLAG='Y'");
			query.append("  and  CANCELLATION_FLAG ='F' ");
			query.append("");
			logger.info("In getTAFlagDetailsAuthor : query" + query);
			mainlist = ConnectionDAO.sqlSelect(query.toString());
			
			query = null;
			
			int size = mainlist.size();
			logger.info("In getTAFlagDetailsAuthor.....mainlist size: "+ size);
			for (int i = 0; i < size; i++) {

				subList = (ArrayList) mainlist.get(i);
				if (subList.size() > 0) {
					loanvo = new DisbCancellationVO();	
					loanvo.setTaLoanID(CommonFunction.checkNull(subList.get(0)).trim());
					logger.info(" TA Loan ID--"+CommonFunction.checkNull(subList.get(0)).trim());
					loanvo.setTaLoanNo(CommonFunction.checkNull(subList.get(2)).trim());
					logger.info(" TA Loan No--"+CommonFunction.checkNull(subList.get(2)).trim());
					getTAFlagDetailsAuthor.add(loanvo);
				}
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			mainlist = null;
			subList = null;
		
			loanvo = null;			
			
		}
		return getTAFlagDetailsAuthor;
	}	
	public ArrayList<DisbCancellationVO> getAdviceTAFlag(DisbCancellationVO vo) {
		ArrayList<DisbCancellationVO> getAdviceTAFlag = new ArrayList<DisbCancellationVO>();
		logger.info("In getAdviceTAFlag Loan Id : " + vo.getLbxLoanNoHID());
		logger.info("In getAdviceTAFlag TA Loan Id : " + vo.getTaLoanID());
		logger.info("LOAN_DISBURSAL_ID "+CommonFunction.checkNull(vo.getDisbursalID()));
		ArrayList mainlist = new ArrayList();
		ArrayList subList = new ArrayList();
		StringBuilder query = new StringBuilder();
		DisbCancellationVO loanvo = null;
//		String query1 = "";
		String [] chk=vo.getChk();
		String [] disbId=vo.getDisbursalID();
		logger.info("disbId  "+disbId.length);
		String tempresult="";
		int counter=0;
		String instrumentNo="ADJ-R-";
		String tempDisbId="";
		try {
			for(int i=0;i<chk.length;i++){
//				String inParameter1="'"+chk+"'";
//				String temp="+,+"+inParameter1;
//				tempresult=temp+inParameter1;	
				//tempresult=tempresult+"'"+disbId[i]+"',";
				tempDisbId=disbId[i];
				String tempInstNo=instrumentNo.concat(tempDisbId);
				logger.info("tempInstNo  ----  "+tempInstNo);
				String queryCheck="Select count(1) from CR_INSTRUMENT_DTL  where TXNID='"+vo.getTaLoanID()+"' and INSTRUMENT_NO='"+tempInstNo+"' and ifnull(TXN_TYPE,'')='LIM' AND IFNULL(REC_STATUS,'A')<>'X' ";
				logger.info("queryCheck:------------------"+queryCheck);
			    String queryCheckResult=ConnectionDAO.singleReturn(queryCheck);
			    if(!CommonFunction.checkNull(queryCheckResult).equalsIgnoreCase("0"))
			    {
			    	counter=counter+1;
			    }
				
				}
			logger.info("counter----"+counter);
			loanvo = new DisbCancellationVO();
			if(counter>0){
				loanvo.setTaFlag("Y");	
			}
			else
			{
				loanvo.setTaFlag("N");	
			}
			getAdviceTAFlag.add(loanvo);		
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			//mainlist = null;
			//subList = null;			
			loanvo = null;			
			
		}
		return getAdviceTAFlag;
	}
	
	public ArrayList<DisbCancellationVO> getAdviceTAFlagOnAuthor(DisbCancellationVO vo) {
		ArrayList<DisbCancellationVO> getAdviceTAFlag = new ArrayList<DisbCancellationVO>();
		logger.info("In getAdviceTAFlag Loan Id : " + vo.getLbxLoanNoHID());
		logger.info("In getAdviceTAFlag TA Loan Id : " + vo.getTaLoanID());
		logger.info("LOAN_DISBURSAL_ID "+CommonFunction.checkNull(vo.getDisbursalID()));
		ArrayList mainlist = new ArrayList();
		ArrayList subList = new ArrayList();
		StringBuilder query = new StringBuilder();
		DisbCancellationVO loanvo = null;
//		String query1 = "";
		//String [] chk=vo.getChk();
		String [] disbId=vo.getDisbursalID();
		logger.info("disbId  "+disbId.length);
		String tempresult="";
		int counter=0;
		String instrumentNo="ADJ-R-";
		String tempDisbId="";
		try {
			for(int i=0;i<disbId.length;i++){
//				String inParameter1="'"+chk+"'";
//				String temp="+,+"+inParameter1;
//				tempresult=temp+inParameter1;	
				//tempresult=tempresult+"'"+disbId[i]+"',";
				tempDisbId=disbId[i];
				String tempInstNo=instrumentNo.concat(tempDisbId);
				logger.info("tempInstNo  ----  "+tempInstNo);
				String queryCheck="Select count(1) from CR_INSTRUMENT_DTL  where TXNID='"+vo.getTaLoanID()+"' and INSTRUMENT_NO='"+tempInstNo+"' and ifnull(TXN_TYPE,'')='LIM' AND IFNULL(REC_STATUS,'A')<>'X' ";
				logger.info("queryCheck:------------------"+queryCheck);
			    String queryCheckResult=ConnectionDAO.singleReturn(queryCheck);
			    if(!CommonFunction.checkNull(queryCheckResult).equalsIgnoreCase("0"))
			    {
			    	counter=counter+1;
			    }
				
				}
			logger.info("counter----"+counter);
			loanvo = new DisbCancellationVO();
			if(counter>0){
				loanvo.setTaFlag("Y");	
			}
			else
			{
				loanvo.setTaFlag("N");	
			}
			getAdviceTAFlag.add(loanvo);		
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			//mainlist = null;
			//subList = null;			
			loanvo = null;			
			
		}
		return getAdviceTAFlag;
	}
	
	
	
}
