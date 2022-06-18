package com.cm.daoImplMSSQL;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.apache.commons.lang.StringEscapeUtils;

import com.cm.dao.MaturityClosureDAO;
import com.cm.vo.ClosureSearchVO;
import com.cm.vo.ClosureVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;

public class MSSQLMaturityClosureDAOImpl implements MaturityClosureDAO{
	
	private static final Logger logger = Logger.getLogger(MSSQLMaturityClosureDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");
	//String selectFrom = resource.getString("lbl.selectFrom");
	
	
	public ArrayList<ClosureVO> selectClosureData(String loanId, String terminationId) {
	ArrayList<ClosureVO> closureData = new ArrayList<ClosureVO>();
	ClosureVO vo = null;
	logger.info("In selectClosureData Loan Id :::::::::::::::::::::MaturityClosureDAOImpl " + loanId);
	ArrayList mainlist = new ArrayList();
	ArrayList subList = new ArrayList();
	StringBuilder query = new StringBuilder();
	ClosureVO loanvo1 = null;
	StringBuilder query1 = new StringBuilder();
	try {
		query.append("select distinct a.loan_no, e.request_no,");
		query.append(dbo);
	    query.append("DATE_FORMAT(a.loan_approval_date,'"+dateFormat+"'), b.customer_name,c.product_desc, ");
		query.append(" d.scheme_desc, a.loan_repayment_freq,e.MAKER_REMARKS, e.remarks, ");
		query.append(dbo);
		query.append("DATE_FORMAT(e.termination_date,'"+dateFormat+"'), a.loan_tenure, ");
		query.append(dbo);
		query.append("DATE_FORMAT(a.loan_maturity_date,'"+dateFormat+"'),e.rec_principal_os, e.pay_sd_principal, ");
		query.append(" e.rec_installment_overdue, e.pay_sd_interest, e.interest_till_date, e.pay_sd_tds,  ");
		query.append(" e.rec_installment_unbilled, e.pay_sd_gap_interest, e.rec_fore_closure_charges, ");
		query.append(" e.pay_sd_gap_tds, e.rec_other_charges,  e.pay_other_charges, e.net_rec_pay, a.loan_id,e.termination_id,");
		query.append(" e.rec_lockin_period_interest,e.rec_od_amount,");
		query.append(" (select loan_no_of_installment-loan_billed_instl_num from cr_loan_dtl where loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+"'),");
		query.append(" e.rec_principal_overdue, e.waiveoff_amount,e.ADVANCE_EMI_REFUNDS");
		query.append(" from cr_loan_dtl a,gcd_customer_m b, cr_product_m c, cr_scheme_m d, cr_termination_dtl e ");
		query.append(" where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product");
		query.append(" and d.scheme_id = a.loan_scheme and e.loan_id=a.loan_id and e.loan_id=");
		query.append(" '"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+"' and e.termination_id=");
		query.append(" '"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(terminationId).trim())+"'");

		logger.info("In selectClosureData : " + query);
		mainlist = ConnectionDAO.sqlSelect(query.toString());
		
		query = null;
		
		int size = mainlist.size();
		logger.info("In selectClosureData.....mainlist size: "+ size);
		for (int i = 0; i < size; i++) {
			subList = (ArrayList) mainlist.get(i);
			if (subList.size() > 0) {
				vo = new ClosureVO();
			    vo.setLoanAc((CommonFunction.checkNull(subList.get(0)).trim()));
				vo.setRequestNumber((CommonFunction.checkNull(subList.get(1)).trim()));
				vo.setLoanDate((CommonFunction.checkNull(subList.get(2)).trim()));
				vo.setCustomerName((CommonFunction.checkNull(subList.get(3)).trim()));
				vo.setProduct((CommonFunction.checkNull(subList.get(4)).trim()));
				vo.setScheme((CommonFunction.checkNull(subList.get(5)).trim()));
				if((CommonFunction.checkNull(subList.get(6)).trim()).equalsIgnoreCase("M"))
					vo.setFrequency("Monthly");
				if((CommonFunction.checkNull(subList.get(6)).trim()).equalsIgnoreCase("B"))
					vo.setFrequency("Bimonthly");
				if((CommonFunction.checkNull(subList.get(6)).trim()).equalsIgnoreCase("Q"))
					vo.setFrequency("Quarterly");
				if((CommonFunction.checkNull(subList.get(6)).trim()).equalsIgnoreCase("H"))
					vo.setFrequency("Half Yearly");
				if((CommonFunction.checkNull(subList.get(6)).trim()).equalsIgnoreCase("Y"))
					vo.setFrequency("Yearly");
				
				vo.setReasonForClosure((CommonFunction.checkNull(subList.get(7)).trim()));
				vo.setAuthorRemarks((CommonFunction.checkNull(subList.get(8)).trim()));
				
				vo.setEffectiveDate((CommonFunction.checkNull(subList.get(9)).trim()));
				vo.setOriginalTenure((CommonFunction.checkNull(subList.get(10)).trim()));
				vo.setMaturityDate((CommonFunction.checkNull(subList.get(11)).trim()));
				
				
				if((CommonFunction.checkNull(subList.get(12)).trim()).equalsIgnoreCase(""))
					vo.setBalancePrincipal("0.00");
				else
				{
					Number balPrincipal = myFormatter.parse((CommonFunction.checkNull(subList.get(12)).trim()));
					vo.setBalancePrincipal(myFormatter.format(balPrincipal));
				}
				if((CommonFunction.checkNull(subList.get(13)).trim()).equalsIgnoreCase(""))
					vo.setSecureDeposit("0.00");
				else
				{
					Number secDeposit = myFormatter.parse((CommonFunction.checkNull(subList.get(13)).trim()));
					vo.setSecureDeposit(myFormatter.format(secDeposit));
				}
				if((CommonFunction.checkNull(subList.get(14)).trim()).equalsIgnoreCase(""))
					vo.setOverdueInstallments("0.00");
				else
				{
					Number overdueInst = myFormatter.parse((CommonFunction.checkNull(subList.get(14)).trim()));
					vo.setOverdueInstallments(myFormatter.format(overdueInst));
				}
				if((CommonFunction.checkNull(subList.get(15)).trim()).equalsIgnoreCase(""))
					vo.setSecureDepositInterest("0.00");
				else
				{
					Number secDepoInt = myFormatter.parse((CommonFunction.checkNull(subList.get(15)).trim()));
					vo.setSecureDepositInterest(myFormatter.format(secDepoInt));
				}
				if((CommonFunction.checkNull(subList.get(16)).trim()).equalsIgnoreCase(""))
					vo.setInterestTillDate("0.00");
				else
				{
					Number intTillDate = myFormatter.parse((CommonFunction.checkNull(subList.get(16)).trim()));
					vo.setInterestTillDate(myFormatter.format(intTillDate));
				}
				if((CommonFunction.checkNull(subList.get(17)).trim()).equalsIgnoreCase(""))
					vo.setSecureDepositTDS("0.00");
				else
				{
					Number secDepTDS = myFormatter.parse((CommonFunction.checkNull(subList.get(17)).trim()));
					vo.setSecureDepositTDS(myFormatter.format(secDepTDS));
				}
				if((CommonFunction.checkNull(subList.get(18)).trim()).equalsIgnoreCase(""))
					vo.setUnBilledInstallments("0.00");
				else
				{
					Number unbilledInst = myFormatter.parse((CommonFunction.checkNull(subList.get(18)).trim()));
					vo.setUnBilledInstallments(myFormatter.format(unbilledInst));
				}
				if((CommonFunction.checkNull(subList.get(19)).trim()).equalsIgnoreCase(""))
					vo.setGapSDInterest("0.00");
				else
				{
					Number gapSDInt = myFormatter.parse((CommonFunction.checkNull(subList.get(19)).trim()));
					vo.setGapSDInterest(myFormatter.format(gapSDInt));
				}
				if((CommonFunction.checkNull(subList.get(20)).trim()).equalsIgnoreCase(""))
					vo.setForeClosurePenalty("0.00");
				else
				{
					Number foreClosurePennalty = myFormatter.parse((CommonFunction.checkNull(subList.get(20)).trim()));
					vo.setForeClosurePenalty(myFormatter.format(foreClosurePennalty));
				}
				if((CommonFunction.checkNull(subList.get(21)).trim()).equalsIgnoreCase(""))
					vo.setGapSDTDS("0.00");
				else
				{
					Number gapSDtds = myFormatter.parse((CommonFunction.checkNull(subList.get(21)).trim()));
					vo.setGapSDTDS(myFormatter.format(gapSDtds));
				}
				if((CommonFunction.checkNull(subList.get(22)).trim()).equalsIgnoreCase(""))
					vo.setOtherDues("0.00");
				else
				{
					Number othDues = myFormatter.parse((CommonFunction.checkNull(subList.get(22)).trim()));
					vo.setOtherDues(myFormatter.format(othDues));
				}
				if((CommonFunction.checkNull(subList.get(23)).trim()).equalsIgnoreCase(""))
					vo.setOtherRefunds("0.00");
				else
				{
					Number othRef = myFormatter.parse((CommonFunction.checkNull(subList.get(23)).trim()));
					vo.setOtherRefunds(myFormatter.format(othRef));
				}
				if((CommonFunction.checkNull(subList.get(24)).trim()).equalsIgnoreCase(""))
					vo.setNetReceivablePayable("0.00");
				else
				{
					Number netTecPay = myFormatter.parse((CommonFunction.checkNull(subList.get(24)).trim()));
					vo.setNetReceivablePayable(myFormatter.format(netTecPay));
				}
				
				vo.setLbxLoanNoHID((CommonFunction.checkNull(subList.get(25)).trim()));
				vo.setTerminationId((CommonFunction.checkNull(subList.get(26)).trim()));
				if((CommonFunction.checkNull(subList.get(27)).trim()).equalsIgnoreCase(""))
					vo.setInterstTillLP("0.00");
				else
				{
					Number interstTillLP = myFormatter.parse((CommonFunction.checkNull(subList.get(27)).trim()));
					vo.setInterstTillLP(myFormatter.format(interstTillLP));
				}
				
				if((CommonFunction.checkNull(subList.get(28)).trim()).equalsIgnoreCase(""))
					vo.setLppAmount("0.00");
				else
				{
					Number lppAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(28)).trim()));
					vo.setLppAmount(myFormatter.format(lppAmount));
				}
				vo.setRemainingInstallments((CommonFunction.checkNull(subList.get(29)).trim()));
				
				if((CommonFunction.checkNull(subList.get(30)).trim()).equalsIgnoreCase(""))
					vo.setOverduePrincipal("0.00");
				else
				{
					Number overduePrincipal = myFormatter.parse((CommonFunction.checkNull(subList.get(30)).trim()));
					vo.setOverduePrincipal(myFormatter.format(overduePrincipal));
				}
				
				if((CommonFunction.checkNull(subList.get(31)).trim()).equalsIgnoreCase(""))
					vo.setWaiveOffAmount("0.00");
				else
				{
					Number waiveoffAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(31)).trim()));
					vo.setWaiveOffAmount(myFormatter.format(waiveoffAmount));
				}
				/*Change Of Advance EMI Refunds Starts Here*/
				if((CommonFunction.checkNull(subList.get(32)).trim()).equalsIgnoreCase(""))
					vo.setAdvanceEmiRefunds("0.00");
				else
				{
					Number advanceEmi = myFormatter.parse((CommonFunction.checkNull(subList.get(32)).trim()));
					vo.setAdvanceEmiRefunds(myFormatter.format(advanceEmi));
				}
				/*Change Of Advance EMI Refunds Ends Here*/
				closureData.add(vo);
			}
		}
		query1.append("SELECT period_diff(");
		query1.append(dbo);
		query1.append("date_format(a.loan_maturity_date,'%Y%m'),");
		query1.append(dbo);
		query1.append("date_format(b.termination_date,'%Y%m')) as remaining_tenure ");
		query1.append(" from cr_loan_dtl a, cr_termination_dtl b where a.loan_id='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim()) + "'");
		query1.append(" and b.termination_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(terminationId).trim())+"'");
		logger.info("In getClosureValues : " + query1);
		mainlist = ConnectionDAO.sqlSelect(query1.toString());
		
		query1 = null;
		
		size = mainlist.size();
		logger.info("In getClosureValues.....mainlist size: "
				+ size);
		for (int i = 0; i < size; i++) {

			subList = (ArrayList) mainlist.get(i);
			if (subList.size() > 0) {
				loanvo1 = new ClosureVO();
				loanvo1.setRemainingTenure((CommonFunction.checkNull(subList.get(0)).trim()));
				closureData.add(loanvo1);
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		vo = null;
		mainlist = null;
		subList = null;
		
		loanvo1 = null;
		
	}
	return closureData;
}

	
	public ArrayList<ClosureSearchVO> searchClosureData(Object ob, String status, String type) {
	ArrayList<ClosureSearchVO> searchDataList = new ArrayList<ClosureSearchVO>();
	ClosureSearchVO vo = (ClosureSearchVO) ob;
	logger.info("In searchClosureData DAOImpl::::::::::::::::::::MaturityClosureDAOImpl");
	
	ArrayList mainlist = new ArrayList();
	ArrayList subList = new ArrayList();
	StringBuilder bufInsSql=new StringBuilder();
	StringBuilder bufInsSqlTempCount = new StringBuilder();
//	String countStr="";
//	String userName="";
	
	StringBuilder countStr=new StringBuilder();
	StringBuilder userName=new StringBuilder();
	
	ClosureSearchVO cVo= null;
	logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+vo.getReportingToUserId());
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
		bufInsSql.append("select distinct a.loan_no, b.customer_name, c.product_desc,d.scheme_desc,a.loan_loan_amount,");
		bufInsSql.append(dbo);
		bufInsSql.append("DATE_FORMAT(a.loan_approval_date,'"+dateFormat+"'),a.loan_id, e.termination_id, (SELECT USER_NAME ");
		bufInsSql.append("FROM SEC_USER_M WHERE USER_ID=e.MAKER_ID) MAKER_ID from cr_loan_dtl a,gcd_customer_m b,"
					+ " cr_product_m c, cr_scheme_m d, cr_termination_dtl e where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product"
					+ " and d.scheme_id = a.loan_scheme and a.loan_id=e.loan_id and e.rec_status='"
					+ status + "' and e.termination_type='"+type+"' AND A.LOAN_BRANCH='"+vo.getBranchId()+"' ");
		bufInsSqlTempCount.append("select count(1) from cr_loan_dtl a,gcd_customer_m b,cr_product_m c, cr_scheme_m d, cr_termination_dtl e" +
				" where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product"
				+ " and d.scheme_id = a.loan_scheme and a.loan_id=e.loan_id and e.rec_status='"
				+ status + "' and e.termination_type='"+type+"' AND A.LOAN_BRANCH='"+vo.getBranchId()+"' ");
	
		if(vo.getStage()!=null && !vo.getStage().equalsIgnoreCase("F"))
		{
			bufInsSql.append(" AND E.MAKER_ID='"+vo.getReportingToUserId()+"' ");
			bufInsSqlTempCount.append(" AND E.MAKER_ID='"+vo.getReportingToUserId()+"' ");
		}
		
		if(vo.getStage()!=null && vo.getStage().equalsIgnoreCase("F"))
		{
			bufInsSql.append(" AND E.MAKER_ID!='"+vo.getUserId()+"' ");
			bufInsSqlTempCount.append(" AND E.MAKER_ID!='"+vo.getUserId()+"' ");
		}
		
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
		
		else if (!(CommonFunction.checkNull(vo.getLbxUserId()) == "")&& vo.getStage().equalsIgnoreCase("F")) {
			bufInsSql.append(" AND E.MAKER_ID='"
					+StringEscapeUtils.escapeSql(CommonFunction.checkNull( vo.getLbxUserId()).trim())+ "' ");
			bufInsSqlTempCount.append(" AND E.MAKER_ID='"
					+StringEscapeUtils.escapeSql(CommonFunction.checkNull( vo.getLbxUserId()).trim())+ "' ");
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
			//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));		
			bufInsSql.append(" ORDER BY USER_ID OFFSET ");
            bufInsSql.append(startRecordIndex);
            bufInsSql.append(" ROWS FETCH next ");
            bufInsSql.append(endRecordIndex);
            bufInsSql.append(" ROWS ONLY ");
			
		}
		
		bufInsSql= null;
		
		// Logic for paging ends
		int size = mainlist.size();
		logger.info("In searchClosureData.....mainlist size: "+ size);
		for (int i = 0; i < size; i++) {

			subList = (ArrayList) mainlist.get(i);
			logger.info("In searchClosureData......sublist size: "
					+ subList.size());
			if (subList.size() > 0) {
				
				cVo= new ClosureSearchVO();
				
				if(status.equalsIgnoreCase("P"))
				{
					if(type.equalsIgnoreCase("T"))
					{
						cVo.setLoanAc("<a href=earlyClosureBehind.do?method=openClosureValues&loanId="
		      					+ (CommonFunction.checkNull(subList.get(6)).trim())+ "&terminationId=" 
		      					+ (CommonFunction.checkNull(subList.get(7)).trim())
		      					+ "&type=T>"+ (CommonFunction.checkNull(subList.get(0)).trim()) + "</a>");
					}
					if(type.equalsIgnoreCase("C"))
					{
						cVo.setLoanAc("<a href=earlyClosureBehind.do?method=openClosureValues&loanId="
		      					+ (CommonFunction.checkNull(subList.get(6)).trim())+ "&terminationId=" 
		      					+ (CommonFunction.checkNull(subList.get(7)).trim())
		      					+ "&type=C>"+ (CommonFunction.checkNull(subList.get(0)).trim()) + "</a>");
					}
					if(type.equalsIgnoreCase("X"))
					{
						cVo.setLoanAc("<a href=earlyClosureBehind.do?method=openClosureValues&loanId="
		      					+ (CommonFunction.checkNull(subList.get(6)).trim())+ "&terminationId=" 
		      					+ (CommonFunction.checkNull(subList.get(7)).trim())
		      					+ "&type=X>"+ (CommonFunction.checkNull(subList.get(0)).trim()) + "</a>");
					}
				}
				
				if(status.equalsIgnoreCase("F"))
				{
					if(type.equalsIgnoreCase("T"))
					{
						cVo.setLoanAc("<a href=earlyClosureBehind.do?method=openEarlyClosureAuthor&loanId="
		      					+ (CommonFunction.checkNull(subList.get(6)).trim())+ "&terminationId=" 
		      					+ (CommonFunction.checkNull(subList.get(7)).trim())
		      					+ "&closureStatus=T>"+ (CommonFunction.checkNull(subList.get(0)).trim()) + "</a>");
					}
					if(type.equalsIgnoreCase("C"))
					{
						cVo.setLoanAc("<a href=earlyClosureBehind.do?method=openMaturityClosureAuthor&loanId="
		      					+ (CommonFunction.checkNull(subList.get(6)).trim())+ "&terminationId=" 
		      					+ (CommonFunction.checkNull(subList.get(7)).trim())
		      					+ "&closureStatus=C>"+ (CommonFunction.checkNull(subList.get(0)).trim()) + "</a>");
					}
					if(type.equalsIgnoreCase("X"))
					{
						cVo.setLoanAc("<a href=earlyClosureBehind.do?method=openCancellationAuthor&loanId="
		      					+ (CommonFunction.checkNull(subList.get(6)).trim())+ "&terminationId=" 
		      					+ (CommonFunction.checkNull(subList.get(7)).trim())
		      					+ "&closureStatus=X>"+ (CommonFunction.checkNull(subList.get(0)).trim()) + "</a>");
					}
				}
				
				cVo.setCustomerName(CommonFunction.checkNull(subList.get(1)).trim());
				cVo.setProduct(CommonFunction.checkNull(subList.get(2)).trim());
				cVo.setScheme(CommonFunction.checkNull(subList.get(3)).trim());
				if(CommonFunction.checkNull(subList.get(4)).trim().equalsIgnoreCase(""))
					cVo.setLoanAmount("0.00");
				else
				{
					Number loanAmt= myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(subList.get(4)).trim()));
					cVo.setLoanAmount(myFormatter.format(loanAmt));
				}
				cVo.setLoanDate((CommonFunction.checkNull(subList.get(5)).trim()));
				cVo.setLbxLoanNoHID((CommonFunction.checkNull(subList.get(6)).trim()));
				cVo.setTerminationId((CommonFunction.checkNull(subList.get(7)).trim()));
				cVo.setReportingToUserId((CommonFunction.checkNull(subList.get(8)).trim()));
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
		
		bufInsSqlTempCount = null;
		countStr= null;
		cVo= null;
		
	}
	
	return searchDataList;
}
	
}
