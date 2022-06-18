package com.cm.daoImplMSSQL;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.cm.dao.CancellationDAO;
import com.cm.vo.CancellationVO;
import com.cm.vo.ClosureSearchVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;

public class MSSQLCancellationDAOImpl implements CancellationDAO{
	
	private static final Logger logger = Logger.getLogger(MSSQLCancellationDAOImpl.class.getName());
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

	public ArrayList<ClosureSearchVO> searchClosureData(Object ob, String status, String type) {
	ArrayList<ClosureSearchVO> searchDataList = new ArrayList<ClosureSearchVO>();
	ClosureSearchVO vo = (ClosureSearchVO) ob;
	logger.info("In searchClosureData DAOImpl::::::::::::::::CancellationDAOImpl");
	
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
		
		
		
		bufInsSql.append("select distinct a.loan_no, b.customer_name, c.product_desc, ");
		bufInsSql.append( "d.scheme_desc,a.loan_loan_amount, ");
		bufInsSql.append(dbo);
		bufInsSql.append("DATE_FORMAT(a.loan_approval_date,'"+dateFormat+"') AS loan_approval_date,a.loan_id, e.termination_id, (SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=e.MAKER_ID) MAKER_ID  ");
		bufInsSql.append(" from cr_loan_dtl a,gcd_customer_m b,");
		bufInsSql.append( " cr_product_m c, cr_scheme_m d, cr_termination_dtl e where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product");
		bufInsSql.append( " and d.scheme_id = a.loan_scheme and a.loan_id=e.loan_id and e.rec_status='");
		bufInsSql.append( status + "' and e.termination_type='"+type+"' AND A.LOAN_BRANCH='"+vo.getBranchId()+"' ");
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
//		if((vo.getLbxLoanNoHID()==null && vo.getCustomerName()==null) || (vo.getLbxLoanNoHID().equalsIgnoreCase("") && vo.getCustomerName().equalsIgnoreCase(""))
//				||( vo.getCurrentPageLink()>1))
//		{
			logger.info("current PAge Link no .................... "+vo.getCurrentPageLink());
			if(vo.getCurrentPageLink()>1)
			{
				startRecordIndex = (vo.getCurrentPageLink()-1)*no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "+startRecordIndex);
				logger.info("endRecordIndex .................... "+endRecordIndex);
			}

			bufInsSql.append(" ORDER BY a.loan_no OFFSET ");
			bufInsSql.append(startRecordIndex);
			bufInsSql.append(" ROWS FETCH next ");
			bufInsSql.append(endRecordIndex);
			bufInsSql.append(" ROWS ONLY ");
			
			
		//	bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));
			
			
			
//		}
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
		
		countStr= null;
		cVo= null;
	}
	
	return searchDataList;
}
	
	
	
	public boolean checkCancellationPaymentReceipt(String lbxLoanNoHID)
	{
		logger.info("In CancellationDAOImpl::::::::::::::::::::::::::::::");
	boolean status=false;
	String activePaymentStr = "";
	StringBuilder query = new StringBuilder();  
	query.append("select (sum(ISNULL(b.pmnt_amount,0))) as activePayment from cr_txnadvice_dtl a, cr_pmnt_dtl b,");
	query.append(" cr_instrument_dtl c where a.loan_id="+lbxLoanNoHID+" and a.rec_STATUS='A' and a.TXNADVICE_ID = b.TXNADVICEID");
	query.append(" and b.REC_STATUS <> 'X' AND B.INSTRUMENT_ID = C.INSTRUMENT_ID AND C.INSTRUMENT_TYPE <> 'K'");
	query.append(" AND C.REC_STATUS NOT IN('P','F','X')");
	logger.info("Query for check Active Payment: "+query.toString());
	try
	{
		activePaymentStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(query.toString()));
		//double activePayment = Double.parseDouble(activePaymentStr);
		
		query = null;
		
		double activePayment=0.0;
		if(activePaymentStr.equalsIgnoreCase(""))
		{
			activePayment=0.0;
		}
		else
		{
			activePayment = Double.parseDouble(activePaymentStr);
			logger.info("Active Payment: "+activePayment);
		}
		if(activePayment>0)
		{
			status=true;
		}
	}catch(Exception e)
	{
		e.printStackTrace();
	}
	finally
	{
		activePaymentStr = null;
		
	}
	return status;
}
	
	public String getMakerDate(String loanId) 
	{
		String makeDate="";
		StringBuilder query = new StringBuilder();
		query.append("select ");
		query.append(dbo);
		query.append("date_format(MAKER_DATE,'%d-%m-%Y') from cr_termination_dtl where LOAN_ID="+loanId.trim());
		logger.info("Query for getting Maker Date of early closure  :  "+query.toString());
		try
		{
			makeDate = ConnectionDAO.singleReturn(query.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return makeDate;
	}


	public String earlyClosureFlag() 
	{
		String flag="";
		StringBuilder query = new StringBuilder();
		query.append("select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='MAKER_AUTHOR_DATE_CHECK_FLAG'");
		logger.info("Query for getting EARLY_CLOSURE_DATE_FLAG from parameter_mst  : "+query.toString());
		try
		{
			flag = ConnectionDAO.singleReturn(query.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	
	public String insertCancellationData(CancellationVO vo){
	logger.info("In insertClosureData.....................................CancellationDAOImpl");
	String terminationId="";
	ArrayList<Object> in =new ArrayList<Object>();
	ArrayList<Object> out =new ArrayList<Object>();
	ArrayList outMessages = new ArrayList();

//	CallableStatement cst = null;
//	Connection con = ConnectionDAO.getConnection();
//	String s1= "";
//    String s2 ="";
//    String s3="";
	
	StringBuilder s1=new StringBuilder();
    StringBuilder s2 =new StringBuilder();
    StringBuilder s3=new StringBuilder();
    
	try {

	//	con.setAutoCommit(false);
		//cst = con.prepareCall("call Early_Closure_Save(?,?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?)");
		 in.add(CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim());//
		 in.add(CommonFunction.checkNull(vo.getRequestNumber()).trim());//
		 in.add(CommonFunction.checkNull(vo.getReasonForClosure()).trim());//
		 String date=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getMakerDate()).trim());
   	    if(date != null)
  	
  	     in.add(date);
//		 in.add(CommonFunction.checkNull(vo.getMakerDate()).trim());//
		 in.add(CommonFunction.checkNull(vo.getClosureType()).trim());//
		 in.add(0.0);
		 in.add(0.0);
		 in.add(0.0);
		 in.add(0.0);
		 in.add(0.0);
		 in.add(0.0);
		 in.add(0.0);
		 in.add(0.0);
		 in.add(0.0);
		 in.add(0.0);
		 in.add(0.0);
		 in.add(0.0);
		 in.add(0.0);
		 in.add(0.0);
		 in.add(0.0);
		 in.add(0.0);
		 in.add(0.0);
		 in.add(0.0);
		 in.add(CommonFunction.checkNull("P").trim());//
		 in.add(CommonFunction.checkNull(vo.getMakerId()).trim());//
		 String date1=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getMakerDate()).trim());
      	    if(date1 != null)
      	    	in.add(date1);
      	out.add(s3);
		out.add(s1);
 	    out.add(s2);
 	   
 	   outMessages=(ArrayList) ConnectionDAO.callSP("Early_Closure_Save",in,out);
	    s1.append(CommonFunction.checkNull(outMessages.get(1)));
	    s2.append(CommonFunction.checkNull(outMessages.get(2)));	  

        logger.info("s1: "+s1);
        logger.info("s2: "+s2);
        terminationId =CommonFunction.checkNull(outMessages.get(0));
//        if(s1.equalsIgnoreCase("S"))
//        {
//			con.commit();
//        }
//        else if(s1.equalsIgnoreCase("E"))
//        {
//        	con.rollback();
//        }
	}
	catch (Exception e) {
//		try {
//			con.rollback();
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
		e.printStackTrace();
	}
	finally
	{
//		try {
//			con.close();
//			cst.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	
//		con = null;
		s1= null;
        s2 = null;
        s3=null;
	}
	return terminationId;
}	
	
	
	
	public ArrayList<CancellationVO> selectCancellationData(String loanId, String terminationID){
		logger.info("In selectCancellationData.....................................CancellationDAOImpl");
	ArrayList<CancellationVO> cancellationData = new ArrayList<CancellationVO>();
	CancellationVO vo = null;
	ArrayList mainlist = null;
	ArrayList subList = null;
	StringBuilder query = new StringBuilder();
	CancellationVO loanvo = null;
	StringBuilder query1 = new StringBuilder();
	CancellationVO loanvo1 = null;
	try{
	query.append("select distinct a.loan_no,a.loan_id, b.customer_name,");
	query.append(dbo);
	query.append("DATE_FORMAT(a.loan_approval_date,'"+dateFormat+"'),a.loan_loan_amount,c.product_desc, ");
	query.append(" d.scheme_desc, a.loan_tenure,a.loan_repayment_freq, ");
	query.append(dbo);
	query.append("DATE_FORMAT(a.loan_maturity_date,'"+dateFormat+"'), a.LOAN_FINAL_RATE, a.LOAN_BILLED_PRINCIPAL,");
	query.append(" a.DISBURSAL_STATUS, (select sum(ISNULL(disbursal_amount,0)) from cr_loan_disbursal_dtl where");
	query.append(" loan_id='"+loanId+"' and rec_status='A' group by loan_id ) as disbursed_amount, a.LOAN_BALANCE_PRINCIPAL, "); 
	query.append(" a.LOAN_OVERDUE_PRINCIPAL, a.LOAN_BILLED_INSTL_NUM, a.LOAN_BILLED_INSTL_AMOUNT,");
	query.append(" a.LOAN_RECEIVED_INSTL_AMOUNT, e.request_no,e.remarks, e.termination_id,E.MAKER_REMARKS from cr_loan_dtl a,gcd_customer_m b,");
	query.append(" cr_product_m c, cr_scheme_m d,cr_termination_dtl e where b.customer_id = a.loan_customer_id and " );
	query.append(" c.product_id = a.loan_product and d.scheme_id = a.loan_scheme");
	query.append(" and a.loan_id=e.loan_id and e.loan_id='");
	query.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim()) + "' and e.termination_id="); 
	query.append("'"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(terminationID).trim())+"'");

	logger.info("In getCancellationValues : " + query.toString());
	mainlist = ConnectionDAO.sqlSelect(query.toString());
	
	query = null;
	
	int size = mainlist.size();
	logger.info("In getCancellationValues.....mainlist size: "+ size);
	for (int i = 0; i < size; i++) {
		subList = (ArrayList) mainlist.get(i);
		if (subList.size() > 0) {
			loanvo = new CancellationVO();
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
			loanvo.setOriginalTenure((CommonFunction.checkNull(subList.get(7)).trim()));
			if((CommonFunction.checkNull(subList.get(8)).trim()).equalsIgnoreCase("M"))
				loanvo.setFrequency("Monthly");
			if((CommonFunction.checkNull(subList.get(8)).trim()).equalsIgnoreCase("B"))
				loanvo.setFrequency("Bimonthly");
			if((CommonFunction.checkNull(subList.get(8)).trim()).equalsIgnoreCase("Q"))
				loanvo.setFrequency("Quarterly");
			if((CommonFunction.checkNull(subList.get(8)).trim()).equalsIgnoreCase("H"))
				loanvo.setFrequency("Half Yearly");
			if((CommonFunction.checkNull(subList.get(8)).trim()).equalsIgnoreCase("Y"))
				loanvo.setFrequency("Yearly");
			loanvo.setMaturityDate((CommonFunction.checkNull(subList.get(9)).trim()));
			
			if((CommonFunction.checkNull(subList.get(10)).trim()).equalsIgnoreCase(""))
				loanvo.setRate("0.00");
			else
			{
				Number rate = myFormatter.parse((CommonFunction.checkNull(subList.get(10)).trim()));
				loanvo.setRate(myFormatter.format(rate));
			}
			if((CommonFunction.checkNull(subList.get(11)).trim()).equalsIgnoreCase(""))
				loanvo.setBilledPrincipal("0.00");
			else
			{
				Number billedPrincipal = myFormatter.parse((CommonFunction.checkNull(subList.get(11)).trim()));
				loanvo.setBilledPrincipal(myFormatter.format(billedPrincipal));
			}
			if((CommonFunction.checkNull(subList.get(12)).trim()).equalsIgnoreCase(""))
				loanvo.setDisbursalStatus("");
			if((CommonFunction.checkNull(subList.get(12)).trim()).equalsIgnoreCase("P"))
				loanvo.setDisbursalStatus("Partial");
			if((CommonFunction.checkNull(subList.get(12)).trim()).equalsIgnoreCase("F"))
				loanvo.setDisbursalStatus("Final");
			if((CommonFunction.checkNull(subList.get(13)).trim()).equalsIgnoreCase(""))
				loanvo.setDisbursedAmount("0.00");
			else
			{
				Number disbursedAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(13)).trim()));
				loanvo.setDisbursedAmount(myFormatter.format(disbursedAmount));
			}
			if((CommonFunction.checkNull(subList.get(14)).trim()).equalsIgnoreCase(""))
				loanvo.setBalancePrincipal("0.00");
			else
			{
				Number balancePrincipal = myFormatter.parse((CommonFunction.checkNull(subList.get(14)).trim()));
				loanvo.setBalancePrincipal(myFormatter.format(balancePrincipal));
			}
			if((CommonFunction.checkNull(subList.get(15)).trim()).equalsIgnoreCase(""))
				loanvo.setOverduePrincipal("0.00");
			else
			{
				Number overduePrincipal = myFormatter.parse((CommonFunction.checkNull(subList.get(15)).trim()));
				loanvo.setOverduePrincipal(myFormatter.format(overduePrincipal));
			}
			if((CommonFunction.checkNull(subList.get(16)).trim()).equalsIgnoreCase(""))
				loanvo.setBilledInstallments("0.00");
			else
				loanvo.setBilledInstallments((CommonFunction.checkNull(subList.get(16)).trim()));
			if((CommonFunction.checkNull(subList.get(17)).trim()).equalsIgnoreCase(""))
				loanvo.setBilledInstallmentAmount("0.00");
			else
			{
				Number billedInstallmentAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(17)).trim()));
				loanvo.setBilledInstallmentAmount(myFormatter.format(billedInstallmentAmount));
			}
			if((CommonFunction.checkNull(subList.get(18)).trim()).equalsIgnoreCase(""))
				loanvo.setReceivedInstallmentAmount("0.00");
			else
			{
				Number receivedInstallmentAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(18)).trim()));
				loanvo.setReceivedInstallmentAmount(myFormatter.format(receivedInstallmentAmount));
			}
			loanvo.setRequestNumber((CommonFunction.checkNull(subList.get(19)).trim()));
			loanvo.setAuthorRemarks((CommonFunction.checkNull(subList.get(20)).trim()));
			loanvo.setTerminationId((CommonFunction.checkNull(subList.get(21)).trim()));
			loanvo.setReasonForClosure((CommonFunction.checkNull(subList.get(22)).trim()));
			cancellationData.add(loanvo);
		}
	}

	
	query1.append("SELECT DATEDIFF(MONTH,b.termination_date,loan_maturity_date)");
	query1.append("as remaining_tenure from cr_loan_dtl a, cr_termination_dtl b where ");
	query1.append("a.loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+"'");
	query1.append("and b.termination_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(terminationID).trim())+"'");
//	query1.append("SELECT period_diff(");
//	query1.append(dbo);
//	query1.append("date_format(loan_maturity_date,'%Y%m'),");
//	query1.append(dbo);
//	query1.append("date_format(b.termination_date,'%Y%m')) "); 
//	query1.append("as remaining_tenure from cr_loan_dtl a, cr_termination_dtl b where ");
//	query1.append("a.loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+"'");
//	query1.append("and b.termination_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(terminationID).trim())+"'");
	logger.info("In getCancellationValues : " + query1.toString());
	mainlist = ConnectionDAO.sqlSelect(query1.toString());
	
	query1 = null;
	
	size = mainlist.size();
	logger.info("In getCancellationValues.....mainlist size: "
			+ size);
	for (int i = 0; i <size; i++) {

		subList = (ArrayList) mainlist.get(i);
		if (subList.size() > 0) {
			loanvo1 = new CancellationVO();
			loanvo1.setRemainingTenure((CommonFunction.checkNull(subList.get(0))).trim());
			cancellationData.add(loanvo1);
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
	loanvo = null;
		
		loanvo1 = null;
	}

	return cancellationData;
}
	
	
	
	public boolean updateCancellationData(CancellationVO vo,String type){
		logger.info("In updateCancellationData.....................................CancellationDAOImpl");
	boolean status = false;
	ArrayList queryList = new ArrayList();
	StringBuilder query = new StringBuilder();
	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	try {
		query.append("update cr_termination_dtl set request_no=?, MAKER_REMARKS=?, ");
		query.append(" termination_date=");
		query.append(dbo);
		query.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
		query.append(" rec_status=?, maker_id=?, maker_date=");
		query.append(dbo);
		query.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9)");
		query.append(" where loan_id=? and termination_id=?");
		
		logger.info("IN..........updateCancellationData"+query.toString());
		if(CommonFunction.checkNull(vo.getRequestNumber()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else  
			insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getRequestNumber()).trim()));
		
		if(CommonFunction.checkNull(vo.getReasonForClosure()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getReasonForClosure()).trim()));
		
		if(CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerDate()).trim()));
		
		insertPrepStmtObject.addString(CommonFunction.checkNull(type));
		
		if(CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerId()).trim()));
		
		if(CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerDate()).trim()));
		
		if(CommonFunction.checkNull(vo.getLbxLoanNoHID()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()));
		
		if(CommonFunction.checkNull(vo.getTerminationId()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getTerminationId()).trim()));
		
		insertPrepStmtObject.setSql(query.toString());
		logger.info("IN insertUpdateClosureData() update query ### "+insertPrepStmtObject.printQuery());
		queryList.add(insertPrepStmtObject);
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
	
	public boolean deleteCancellationDetails(CancellationVO vo){
		logger.info("In deleteCancellationDetails.....................................CancellationDAOImpl");
	boolean status = false;
	ArrayList queryList = new ArrayList();
	StringBuilder query = new StringBuilder();
	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	try {
		
		query.append("delete from cr_termination_dtl where loan_id=? and termination_id=?");
		
		logger.info("IN..........deleteCancellationDetails"+query.toString());
		if(CommonFunction.checkNull(vo.getLbxLoanNoHID()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()));
		
		if(CommonFunction.checkNull(vo.getTerminationId()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getTerminationId()).trim()));
		
		insertPrepStmtObject.setSql(query.toString());
		logger.info("IN deleteCancellationDetails() delete query ### "+insertPrepStmtObject.printQuery());
		queryList.add(insertPrepStmtObject);
		status = ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
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
	public ArrayList<CancellationVO> getCancellationValues(int lbxLoanNoHID,String businessDate) {
		ArrayList<CancellationVO> loanDetailList = new ArrayList<CancellationVO>();
		logger.info("In getCancellationValues Loan Id : " + lbxLoanNoHID);
		ArrayList mainlist = new ArrayList();
		ArrayList subList = new ArrayList();
		StringBuilder query = new StringBuilder();
		CancellationVO loanvo = null;
//		String query1 = "";
		StringBuilder query1 =new StringBuilder();
		CancellationVO loanvo1 = null;
		try {

			query.append("select distinct a.loan_no,a.loan_id, b.customer_name,");
			query.append(dbo);
			query.append("DATE_FORMAT(a.loan_approval_date,'"+dateFormat+"'),a.loan_loan_amount,c.product_desc, ");
			query.append(" d.scheme_desc, a.loan_tenure,a.loan_repayment_freq, ");
			query.append(dbo);
			query.append("DATE_FORMAT(a.loan_maturity_date,'"+dateFormat+"'), a.LOAN_FINAL_RATE, a.LOAN_BILLED_PRINCIPAL,");
			query.append(" a.DISBURSAL_STATUS, (select sum(ISNULL(disbursal_amount,0)) from cr_loan_disbursal_dtl where");
			query.append(" loan_id='"+lbxLoanNoHID+"' and rec_status='A') as disbursed_amount, a.LOAN_BALANCE_PRINCIPAL, "); 
			query.append(" a.LOAN_OVERDUE_PRINCIPAL, a.LOAN_BILLED_INSTL_NUM, a.LOAN_BILLED_INSTL_AMOUNT," );
			query.append(" a.LOAN_RECEIVED_INSTL_AMOUNT from cr_loan_dtl a,gcd_customer_m b,");
			query.append(" cr_product_m c, cr_scheme_m d where b.customer_id = a.loan_customer_id and "); 
			query.append(" c.product_id = a.loan_product");
			query.append(" and d.scheme_id = a.loan_scheme and a.loan_id='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(lbxLoanNoHID).trim()) + "'");

			logger.info("In getCancellationValues : " + query.toString());
			mainlist = ConnectionDAO.sqlSelect(query.toString());
			
			query = null;
			
			int size = mainlist.size();
			logger.info("In getCancellationValues.....mainlist size: "+ size);
			for (int i = 0; i < size; i++) {

				subList = (ArrayList) mainlist.get(i);
				if (subList.size() > 0) {
					loanvo = new CancellationVO();
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
					loanvo.setOriginalTenure((CommonFunction.checkNull(subList.get(7)).trim()));
					if((CommonFunction.checkNull(subList.get(8)).trim()).equalsIgnoreCase("M"))
						loanvo.setFrequency("Monthly");
					if((CommonFunction.checkNull(subList.get(8)).trim()).equalsIgnoreCase("B"))
						loanvo.setFrequency("Bimonthly");
					if((CommonFunction.checkNull(subList.get(8)).trim()).equalsIgnoreCase("Q"))
						loanvo.setFrequency("Quarterly");
					if((CommonFunction.checkNull(subList.get(8)).trim()).equalsIgnoreCase("H"))
						loanvo.setFrequency("Half Yearly");
					if((CommonFunction.checkNull(subList.get(8)).trim()).equalsIgnoreCase("Y"))
						loanvo.setFrequency("Yearly");
					loanvo.setMaturityDate((CommonFunction.checkNull(subList.get(9)).trim()));
					
					if((CommonFunction.checkNull(subList.get(10)).trim()).equalsIgnoreCase(""))
						loanvo.setRate("0.00");
					else
					{
						Number billedPrincipal = myFormatter.parse((CommonFunction.checkNull(subList.get(10)).trim()));
						loanvo.setRate(myFormatter.format(billedPrincipal));
					}
					if((CommonFunction.checkNull(subList.get(11)).trim()).equalsIgnoreCase(""))
						loanvo.setBilledPrincipal("0.00");
					else
					{
						Number billedPrincipal = myFormatter.parse((CommonFunction.checkNull(subList.get(11)).trim()));
						loanvo.setBilledPrincipal(myFormatter.format(billedPrincipal));
					}
					if((CommonFunction.checkNull(subList.get(12)).trim()).equalsIgnoreCase(""))
						loanvo.setDisbursalStatus("");
					if((CommonFunction.checkNull(subList.get(12)).trim()).equalsIgnoreCase("P"))
						loanvo.setDisbursalStatus("Partial");
					if((CommonFunction.checkNull(subList.get(12)).trim()).equalsIgnoreCase("F"))
						loanvo.setDisbursalStatus("Final");
					if((CommonFunction.checkNull(subList.get(13)).trim()).equalsIgnoreCase(""))
						loanvo.setDisbursedAmount("0.00");
					else
					{
						Number disbursedAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(13)).trim()));
						loanvo.setDisbursedAmount(myFormatter.format(disbursedAmount));
					}
					if((CommonFunction.checkNull(subList.get(14)).trim()).equalsIgnoreCase(""))
						loanvo.setBalancePrincipal("0.00");
					else
					{
						Number balancePrincipal = myFormatter.parse((CommonFunction.checkNull(subList.get(14)).trim()));
						loanvo.setBalancePrincipal(myFormatter.format(balancePrincipal));
					}
					if((CommonFunction.checkNull(subList.get(15)).trim()).equalsIgnoreCase(""))
						loanvo.setOverduePrincipal("0.00");
					else
					{
						Number overduePrincipal = myFormatter.parse((CommonFunction.checkNull(subList.get(15)).trim()));
						loanvo.setOverduePrincipal(myFormatter.format(overduePrincipal));
					}
					if((CommonFunction.checkNull(subList.get(16)).trim()).equalsIgnoreCase(""))
						loanvo.setBilledInstallments("0.00");
					else
						loanvo.setBilledInstallments((CommonFunction.checkNull(subList.get(16)).trim()));
					if((CommonFunction.checkNull(subList.get(17)).trim()).equalsIgnoreCase(""))
						loanvo.setBilledInstallmentAmount("0.00");
					else
					{
						Number billedInstallmentAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(17)).trim()));
						loanvo.setBilledInstallmentAmount(myFormatter.format(billedInstallmentAmount));
					}
					if((CommonFunction.checkNull(subList.get(18)).trim()).equalsIgnoreCase(""))
						loanvo.setReceivedInstallmentAmount("0.00");
					else
					{
						Number receivedInstallmentAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(18)).trim()));
						loanvo.setReceivedInstallmentAmount(myFormatter.format(receivedInstallmentAmount));
					}
					loanDetailList.add(loanvo);
				}
			}
			
			query1.append("SELECT datediff(month,");
			query1.append(dbo);
			query1.append("STR_TO_DATE('"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(businessDate).trim())+"','"+dateFormat+"'),loan_maturity_date) as remaining_tenure from cr_loan_dtl where loan_id='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(lbxLoanNoHID).trim())+"'");
			
//			query1.append("SELECT period_diff(date_format(loan_maturity_date,'%Y%m'),");
//			query1.append(dbo);
//			query1.append("date_format(");
//			query1.append(dbo);
//			query1.append("STR_TO_DATE('"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(businessDate).trim())+"','"+dateFormat+"'),'%Y%m')) as remaining_tenure from cr_loan_dtl where loan_id='");
//			query1.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(lbxLoanNoHID).trim()) + "'");
			logger.info("In getCancellationValues : " + query1.toString());
			mainlist = ConnectionDAO.sqlSelect(query1.toString());
			
			query1 = null;
			
			size = mainlist.size();
			logger.info("In getCancellationValues.....mainlist size: "+size);
			for (int i = 0; i < size; i++) {

				subList = (ArrayList) mainlist.get(i);
				if (subList.size() > 0) {
					loanvo1 = new CancellationVO();
					loanvo1.setRemainingTenure((CommonFunction.checkNull(subList.get(0))).trim());
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
	
	
}
