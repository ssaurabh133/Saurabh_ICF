package com.cm.daoImplMSSQL;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import org.apache.commons.lang.StringEscapeUtils;

import com.cm.dao.EarlyClosureDAO;
import com.cm.vo.CancellationVO;
import com.cm.vo.ClosureAuthorVO;
import com.cm.vo.ClosureSearchVO;
import com.cm.vo.ClosureVO;
import com.cm.vo.PaymentMakerForCMVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.cp.vo.RepayScheduleVo;

public class MSSQLEarlyClosureDAOImpl implements EarlyClosureDAO{
	
	
	private static final Logger logger = Logger.getLogger(MSSQLEarlyClosureDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	String dbType=resource.getString("lbl.dbType");
	String dbo=resource.getString("lbl.dbPrefix");
	//String selectFrom = resource.getString("lbl.selectFrom");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	
	
	
	public ArrayList<ClosureSearchVO> searchClosureData(Object ob, String status, String type) {
		ArrayList<ClosureSearchVO> searchDataList = new ArrayList<ClosureSearchVO>();
		ClosureSearchVO vo = (ClosureSearchVO) ob;
		logger.info("In searchClosureData ..................EarlyClosureDAOImpl::::::::::::::::In EarlyClosureDAOImpl");
		
		ArrayList mainlist = new ArrayList();
		ArrayList subList = new ArrayList();
		StringBuilder bufInsSql=new StringBuilder();
		StringBuilder bufInsSqlTempCount = new StringBuilder();
		String countStr="";
		String userName="";
		ClosureSearchVO cVo= null;
		logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+vo.getReportingToUserId());
		try{
			String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+vo.getReportingToUserId()+"'";
			userName=ConnectionDAO.singleReturn(userNameQ);
			logger.info("userNameQ: "+userNameQ+" userName: "+userName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		try {
			int count = 0;
			int startRecordIndex=0;
			int endRecordIndex = no;
			
			 String branch="";
			 if(!type.equalsIgnoreCase("T"))
			 {
				 branch=vo.getBranchId();
			 }else{
				   if(vo.getAllBranches().equalsIgnoreCase("on")|| status.equalsIgnoreCase("P"))
				   {
					   String branchMappedToUserQuery= "SELECT STUFF((SELECT ',' + convert(varchar(10),BRANCH_ID) FROM sec_user_branch_dtl WHERE USER_ID='"+CommonFunction.checkNull(vo.getUserId()).trim()+"' AND REC_STATUS='A' FOR XML PATH ('')), 1, 1, '')";
					  // String branchMappedToUserQuery="SELECT GROUP_CONCAT(BRANCH_ID) FROM sec_user_branch_dtl WHERE USER_ID='"+CommonFunction.checkNull(vo.getUserId()).trim()+"' AND REC_STATUS='A'";
					   branch=ConnectionDAO.singleReturn(branchMappedToUserQuery);
					   logger.info("branchMappedToUserQuery: "+branchMappedToUserQuery+"      branches maped to user:   "+branch);
				   }
				   else if(!(vo.getLbxBranchId().equalsIgnoreCase("")))
				   {
					   branch=vo.getLbxBranchId();
				   }
				   else
				   {
					   branch=vo.getBranchId();
				   }
			 }
			   
			bufInsSql.append("select distinct a.loan_no, b.customer_name, c.product_desc, "
						+ "d.scheme_desc,a.loan_loan_amount, ");
			bufInsSql.append(dbo);
			bufInsSql.append("DATE_FORMAT(a.loan_approval_date,'"+dateFormat+"') as loan_approval_date,a.loan_id, e.termination_id, (SELECT USER_NAME ");
			bufInsSql.append("FROM SEC_USER_M WHERE USER_ID=e.MAKER_ID) MAKER_ID ");
			
			bufInsSql.append(" from cr_loan_dtl a,gcd_customer_m b,"
						+ " cr_product_m c, cr_scheme_m d, cr_termination_dtl e where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product"
						+ " and d.scheme_id = a.loan_scheme and a.loan_id=e.loan_id and e.rec_status='"
						+ status + "' and e.termination_type='"+type+"' AND A.LOAN_BRANCH IN("+StringEscapeUtils.escapeSql(branch).trim()+") ");
			bufInsSqlTempCount.append("select count(1) from cr_loan_dtl a,gcd_customer_m b,cr_product_m c, cr_scheme_m d, cr_termination_dtl e" +
					" where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product"
					+ " and d.scheme_id = a.loan_scheme and a.loan_id=e.loan_id and e.rec_status='"
					+ status + "' and e.termination_type='"+type+"' AND A.LOAN_BRANCH IN("+StringEscapeUtils.escapeSql(branch).trim()+") ");
		
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
			countStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
			logger.info("Count String: "+countStr);
			if(countStr.equalsIgnoreCase(""))
				count=0;
			else
				count = Integer.parseInt(countStr);
			
			// Logic for paging
//			if((vo.getLbxLoanNoHID()==null && vo.getCustomerName()==null) || (vo.getLbxLoanNoHID().equalsIgnoreCase("") && vo.getCustomerName().equalsIgnoreCase(""))
//					||( vo.getCurrentPageLink()>1))
//			{
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
				//bufInsSql.append(CommonFunction.betweenStartEnd(startRecordIndex, endRecordIndex));		
//			}
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
			bufInsSql= null;
			bufInsSqlTempCount = null;
			countStr= null;
			cVo= null;
		}
		
		return searchDataList;
	}
	
	
	
	public String insertClosureData(ClosureVO vo) 
	{
	logger.info("In insertClosureData ..................EarlyClosureDAOImpl::::::::::::::::In EarlyClosureDAOImpl");
	String terminationId="";
	ArrayList<Object> in =new ArrayList<Object>();
	ArrayList<Object> out =new ArrayList<Object>();
	ArrayList outMessages = new ArrayList();
	String s1="";
	String s2="";
	String s3="";
//	CallableStatement cst = null;
//	Connection con = ConnectionDAO.getConnection();

	try {

//		con.setAutoCommit(false);
		//cst = con.prepareCall("call Early_Closure_Save(?,?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?)");
		in.add(CommonFunction.checkNull(vo.getLbxLoanNoHID().trim()));//
		in.add(CommonFunction.checkNull(vo.getRequestNumber().trim()));//
		in.add(CommonFunction.checkNull(vo.getReasonForClosure().trim()));//
		String date=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getEffectiveDate().trim()));
		if(date != null)
		in.add(date);
	//	cst.setString(4, CommonFunction.checkNull(vo.getEffectiveDate().trim()));//
		in.add(CommonFunction.checkNull(vo.getClosureType().trim()));
		if((CommonFunction.checkNull(vo.getBalancePrincipal().trim()).equalsIgnoreCase("")))
			in.add(0.00);
		else
		{
			String amt=CommonFunction.checkNull(vo.getBalancePrincipal().trim());
			amt=amt.replace(",","");
			in.add(amt);
		}
		
		if((CommonFunction.checkNull(vo.getSecureDeposit().trim()).equalsIgnoreCase("")))
			in.add(0.00);
		else
		{
			String amt=CommonFunction.checkNull(vo.getSecureDeposit().trim());
			amt=amt.replace(",","");
			in.add(amt);
		}
		if((CommonFunction.checkNull(vo.getOverdueInstallments().trim()).equalsIgnoreCase("")))
			in.add(0.00);
		else
		{
			String amt=CommonFunction.checkNull(vo.getOverdueInstallments().trim());
			amt=amt.replace(",","");
			in.add(amt);
		}
			
		if((CommonFunction.checkNull(vo.getSecureDepositInterest().trim()).equalsIgnoreCase("")))
			in.add(0.00);
		else
		{
			String amt=CommonFunction.checkNull(vo.getSecureDepositInterest().trim());
			amt=amt.replace(",","");
			in.add(amt);
		}
			
		if((CommonFunction.checkNull(vo.getInterestTillDate().trim()).equalsIgnoreCase("")))
			in.add(0.00);
		else
		{
			String amt=CommonFunction.checkNull(vo.getInterestTillDate().trim());
			amt=amt.replace(",","");
			in.add(amt);
		}
			
		if((CommonFunction.checkNull(vo.getSecureDepositTDS().trim()).equalsIgnoreCase("")))
			in.add(0.00);
		else
		{
			String amt=CommonFunction.checkNull(vo.getSecureDepositTDS().trim());
			amt=amt.replace(",","");
			in.add(amt);
		}
				
		if((CommonFunction.checkNull(vo.getUnBilledInstallments().trim()).equalsIgnoreCase("")))
			in.add(0.00);
		else
		{
			String amt=CommonFunction.checkNull(vo.getUnBilledInstallments().trim());
			amt=amt.replace(",","");
			in.add(amt);
		}
				
		if((CommonFunction.checkNull(vo.getGapSDInterest().trim()).equalsIgnoreCase("")))
			in.add(0.00);
		else
		{
			String amt=CommonFunction.checkNull(vo.getGapSDInterest().trim());
			amt=amt.replace(",","");
			in.add(amt);
		}
				
		if((CommonFunction.checkNull(vo.getForeClosurePenalty().trim()).equalsIgnoreCase("")))
			in.add(0.00);
		else
		{
			String amt=CommonFunction.checkNull(vo.getForeClosurePenalty().trim());
			amt=amt.replace(",","");
			in.add(amt);
		}
					
		if((CommonFunction.checkNull(vo.getInterstTillLP().trim()).equalsIgnoreCase("")))
			in.add(0.00);
		else
		{
			String amt=CommonFunction.checkNull(vo.getInterstTillLP().trim());
			amt=amt.replace(",","");
			in.add(amt);
		}
				
		if((CommonFunction.checkNull(vo.getLppAmount().trim()).equalsIgnoreCase("")))
			in.add(0.00);
		else
		{
			String amt=CommonFunction.checkNull(vo.getLppAmount().trim());
			amt=amt.replace(",","");
			in.add(amt);
		}
				
		if((CommonFunction.checkNull(vo.getGapSDTDS().trim()).equalsIgnoreCase("")))
			in.add(0.00);
		else
		{
			String amt=CommonFunction.checkNull(vo.getGapSDTDS().trim());
			amt=amt.replace(",","");
			in.add(amt);
		}
					
		if((CommonFunction.checkNull(vo.getOtherDues().trim()).equalsIgnoreCase("")))
			in.add(0.00);
		else
		{
			String amt=CommonFunction.checkNull(vo.getOtherDues().trim());
			amt=amt.replace(",","");
			in.add(amt);
		}
			
		if((CommonFunction.checkNull(vo.getOtherRefunds().trim()).equalsIgnoreCase("")))
			in.add(0.00);
		else
		{
			String amt=CommonFunction.checkNull(vo.getOtherRefunds().trim());
			amt=amt.replace(",","");
			in.add(amt);
		}
			
		if((CommonFunction.checkNull(vo.getNetReceivablePayable().trim()).equalsIgnoreCase("")))
			in.add(0.00);
		else
		{
			String amt=CommonFunction.checkNull(vo.getNetReceivablePayable().trim());
			amt=amt.replace(",","");
			in.add(amt);
		}
				
		if((CommonFunction.checkNull(vo.getOverduePrincipal().trim()).equalsIgnoreCase("")))
			in.add(0.00);
		else
		{
			String amt=CommonFunction.checkNull(vo.getOverduePrincipal().trim());
			amt=amt.replace(",","");
			in.add(amt);
		}
			
		if((CommonFunction.checkNull(vo.getWaiveOffAmount().trim()).equalsIgnoreCase("")))
			in.add(0.00);
		else
		{
			String amt=CommonFunction.checkNull(vo.getWaiveOffAmount().trim());
			amt=amt.replace(",","");
			in.add(amt);
		}
		//added by  arun for AdvanceEmiRefunds  Starts 
		if((CommonFunction.checkNull(vo.getAdvanceEmiRefunds().trim()).equalsIgnoreCase("")))
			in.add(0.00);
		else
		{
			String amt=CommonFunction.checkNull(vo.getAdvanceEmiRefunds().trim());
			amt=amt.replace(",","");
			in.add(amt);
		}
		//added by  arun for AdvanceEmiRefunds Ends Here
		in.add(CommonFunction.checkNull("P").trim());//
		in.add(CommonFunction.checkNull(vo.getMakerId()).trim());//
		String date1=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getMakerDate()).trim());
		if(date1 != null)
		in.add(date1);
	//	in.add(CommonFunction.checkNull(vo.getMakerDate()).trim());//
		out.add(s3);
		out.add(s1);
		out.add(s2);
		
//		cst.registerOutParameter(27, Types.CHAR);//
//		cst.registerOutParameter(28, Types.CHAR);//
//		cst.executeUpdate();
		 outMessages=(ArrayList) ConnectionDAO.callSP("Early_Closure_Save",in,out);
 	    s1=CommonFunction.checkNull(outMessages.get(1));
 	    s2=CommonFunction.checkNull(outMessages.get(2));
 	    s3=CommonFunction.checkNull(outMessages.get(0));
//		s1= cst.getString(26);
//        s2 = cst.getString(27);
        logger.info("s1: "+s1);
        logger.info("s2: "+s2);
        terminationId =CommonFunction.checkNull(outMessages.get(0));;
        if(s1.equalsIgnoreCase("S"))
        {
			//con.commit();
        }
        else if(s1.equalsIgnoreCase("E"))
        {
        	//con.rollback();
        }
	}
	catch (Exception e) {
		
		e.printStackTrace();
	}
	finally
	{
	
		s1= null;
        s2 = null;
	}
	return terminationId;
}
	
	public String getMakerDate(String loanId) 
	{
		String makeDate="";
		StringBuilder query = new StringBuilder();
		query.append("select ");
		query.append(dbo);
		query.append("date_format(MAKER_DATE,'%d-%m-%Y') from cr_termination_dtl where LOAN_ID="+loanId.trim()+" and rec_status !='X'");
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
	
	public String earlyClosureRealizeFlag() 
	{
		String flag="";
		StringBuilder query = new StringBuilder();
		query.append("select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='REALIZE_FLAG'");
		logger.info("Query for getting EARLY_CLOSURE_RealizeFlag from parameter_mst  : "+query.toString());
		try
		{
			flag = ConnectionDAO.singleReturn(query.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	
	public ArrayList<ClosureVO> selectClosureData(String loanId, String terminationId) {
	ArrayList<ClosureVO> closureData = new ArrayList<ClosureVO>();
	ClosureVO vo = null;
	logger.info("In selectClosureData Loan Id in EarlyClosureDAOImpl::::::::::::::::::In EarlyClosureDAOImpl " + loanId);
	ArrayList mainlist = new ArrayList();
	ArrayList subList = new ArrayList();
	StringBuilder query = new StringBuilder();
	ClosureVO loanvo1 = null;
	StringBuilder query1 = new StringBuilder();
	try {
		query.append("select distinct a.loan_no, e.request_no, ");
		query.append(dbo);
		query.append(" DATE_FORMAT(a.loan_approval_date,'"+dateFormat+"'), b.customer_name,c.product_desc, ");
		query.append(" d.scheme_desc, a.loan_repayment_freq,e.MAKER_REMARKS, e.remarks, ");
		query.append(dbo);
		query.append(" DATE_FORMAT(e.termination_date,'"+dateFormat+"'), a.loan_tenure, ");
		query.append(dbo);
		query.append(" DATE_FORMAT(a.loan_maturity_date,'"+dateFormat+"'),e.rec_principal_os, e.pay_sd_principal, ");
		query.append(" e.rec_installment_overdue, e.pay_sd_interest, e.interest_till_date, e.pay_sd_tds,  ");
		query.append(" e.rec_installment_unbilled, e.pay_sd_gap_interest, e.rec_fore_closure_charges, ");
		query.append(" e.pay_sd_gap_tds, e.rec_other_charges,  e.pay_other_charges, e.net_rec_pay, a.loan_id,e.termination_id,");
		query.append(" e.rec_lockin_period_interest,e.rec_od_amount,");
		query.append(" (select loan_no_of_installment-loan_billed_instl_num from cr_loan_dtl where loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+"'),");
		query.append(" e.rec_principal_overdue, e.waiveoff_amount,e.ADVANCE_EMI_REFUNDS ");
//		Amit Changes Starts
		query.append(",e.rec_status,e.termination_type ");
//		Amit Changes Ends
		query.append(" from cr_loan_dtl a,gcd_customer_m b, cr_product_m c, cr_scheme_m d, cr_termination_dtl e ");
		query.append(" where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product");
		query.append(" and d.scheme_id = a.loan_scheme and e.loan_id=a.loan_id and e.loan_id=");
		query.append(" '"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+"' and e.termination_id=");
		query.append(" '"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(terminationId).trim())+"'");
//		Amit Changes Starts
		query.append(" and e.rec_status<>'X'");
//		Amit Changes Ends
		logger.info("In selectClosureData : " + query);
		mainlist = ConnectionDAO.sqlSelect(query.toString());
		int size = mainlist.size();
		logger.info("In selectClosureData.....mainlist size: "+ size);
		
		StringBuilder realize = new StringBuilder();
		realize.append("select * from cr_instrument_dtl where TXNID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+"' and isnull(TXN_TYPE,'')='LIM' and rec_status ='D'");
		String real = ConnectionDAO.singleReturn(realize.toString());
		logger.info("real status:::::::: "+ real);
		
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
				/*Advance Emi Refund */
				if((CommonFunction.checkNull(subList.get(32)).trim()).equalsIgnoreCase(""))
					vo.setAdvanceEmiRefunds("0.00");
				else
				{
					Number advanceEmiRefund= myFormatter.parse((CommonFunction.checkNull(subList.get(32)).trim()));
					vo.setAdvanceEmiRefunds(myFormatter.format(advanceEmiRefund));
				}
//				Amit Changes Starts
				vo.setRecStatus((CommonFunction.checkNull(subList.get(33)).trim()));
				vo.setClosureType((CommonFunction.checkNull(subList.get(34)).trim()));
//				Amit Changes Ends
				
				/*Advance Emi Refund*/
				double balancePrincipal=0.00;
				double overdueInstallments=0.00;
				double interestTillDate=0.00;
				double unBilledInstallments=0.00;
				double foreClosurePenalty=0.00;
				double otherDues=0.00;
				double interstTillLP=0.00;
				double lppAmount=0.00;
				double totalRec=0.00;
				double advanceEmiRefunds=0.00;
				double otherRefunnds=0.00;
				double totalpayable=0.00;
				
				if(!(CommonFunction.checkNull(subList.get(12)).trim()).equalsIgnoreCase(""))
					balancePrincipal=Double.parseDouble((String)subList.get(12));
			    if(!(CommonFunction.checkNull(subList.get(14)).trim()).equalsIgnoreCase(""))
			    	overdueInstallments=Double.parseDouble((String)subList.get(14));
			    if(!(CommonFunction.checkNull(subList.get(16)).trim()).equalsIgnoreCase(""))
			    	interestTillDate=Double.parseDouble((String)subList.get(16));
			    if(!(CommonFunction.checkNull(subList.get(18)).trim()).equalsIgnoreCase(""))
			    	unBilledInstallments=Double.parseDouble((String)subList.get(18));
			    if(!(CommonFunction.checkNull(subList.get(20)).trim()).equalsIgnoreCase(""))
			    	foreClosurePenalty=Double.parseDouble((String)subList.get(20));
			    if(!(CommonFunction.checkNull(subList.get(22)).trim()).equalsIgnoreCase(""))
			    	otherDues=Double.parseDouble((String)subList.get(22));
			    if(!(CommonFunction.checkNull(subList.get(27)).trim()).equalsIgnoreCase(""))
			    	interstTillLP=Double.parseDouble((String)subList.get(27));
			    if(!(CommonFunction.checkNull(subList.get(28)).trim()).equalsIgnoreCase(""))
			    	lppAmount=Double.parseDouble((String)subList.get(28));
			    if(!(CommonFunction.checkNull(subList.get(32)).trim()).equalsIgnoreCase(""))
			    	advanceEmiRefunds=Double.parseDouble((String)subList.get(32));
			    if(!(CommonFunction.checkNull(subList.get(23)).trim()).equalsIgnoreCase(""))
			    	otherRefunnds=Double.parseDouble((String)subList.get(23));
				totalRec = balancePrincipal+overdueInstallments+interestTillDate+unBilledInstallments+foreClosurePenalty+otherDues+interstTillLP+lppAmount;
				totalpayable=otherRefunnds+advanceEmiRefunds;
				logger.info("Total Recevable : "+totalRec);
				logger.info("Total totalpayable : "+totalpayable);
				String val=totalRec+"";
				Number totalRecevable = myFormatter.parse(val);
				Number totalpay=myFormatter.parse(totalpayable+"");
				vo.setTotalRecevable(myFormatter.format(totalRecevable));
				vo.setTotalPayable(myFormatter.format(totalpay));
				vo.setRealize(real);
				
				closureData.add(vo);
			}
		}
		
		 query1.append("SELECT DATEDIFF(MONTH,b.termination_date,a.loan_maturity_date)");
		 query1.append(" as remaining_tenure ");
		 query1.append(" from cr_loan_dtl a, cr_termination_dtl b where a.loan_id='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim()) + "'");
		 query1.append(" and b.termination_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(terminationId).trim())+"'");
		
//		 query1.append("SELECT period_diff(");
//		 query1.append(dbo);
//		 query1.append("date_format(a.loan_maturity_date,'%Y%m'),");
//		 query1.append(dbo);
//		 query1.append("date_format(b.termination_date,'%Y%m')) as remaining_tenure ");
//		query1.append(" from cr_loan_dtl a, cr_termination_dtl b where a.loan_id='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim()) + "'");
//		query1.append(" and b.termination_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(terminationId).trim())+"'");
		logger.info("In selectClosureData getClosureValues : " + query1);
		mainlist = ConnectionDAO.sqlSelect(query1.toString());
		size = mainlist.size();
		logger.info("In selectClosureData getClosureValues.....mainlist size: "
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
		query = null;
		loanvo1 = null;
		query1 = null;
	}
	return closureData;
}	
	
	

	
	public String checkClosureAmtInProces(String lbxLoanNoHID)
	{
	logger.info("In checkClosureAmtInProces ........ DAOImpl::::::::::::::::In EarlyClosureDAOImpl");
	String status="";
	StringBuilder query =new StringBuilder();
	query.append("select sum(ISNULL(AMOUNT_IN_PROCESS,0)) from cr_txnadvice_dtl where loan_id='"+lbxLoanNoHID+"' and rec_STATUS='A'");
	logger.info("query checkClosureAmtInProces: "+query.toString());
	String amtInProcStr = "";
	try
	{
		amtInProcStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(query.toString()));
		logger.info("checkClosureAmtInProces amtInProcStr: "+amtInProcStr);
		double amtInProc=0.0;
		if(amtInProcStr.equalsIgnoreCase(""))
		{
			amtInProc=0.0;
		}
		else
		{
			amtInProc = Double.parseDouble(amtInProcStr);
		}
		if(amtInProc>0)
		{
			status="AmtInProcess";
		}
	}catch(Exception e)
	{
		e.printStackTrace();
	}
	finally
	{
		query = null;
		amtInProcStr = null;
	}
	return status;
}
	
	

	
	
	public boolean insertUpdateClosureData(Object ob,String type) {
		logger.info("::::::::::::::::In EarlyClosureDAOImpl");
	ClosureVO vo = (ClosureVO) ob;
	boolean status = false;
	ArrayList queryList = new ArrayList();
	StringBuilder query = new StringBuilder();
	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	try {
		logger.info(" insertUpdateClosureData() Before query;;;;;;;;");
		
		query.append("update cr_termination_dtl set request_no=?,MAKER_REMARKS=?, termination_date=");
		//query.append("DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),");
		//Saurabh space starts
		query.append(dbo);
		query.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9),");
		//Saurabh space ends

		query.append(" rec_principal_os=?, pay_sd_principal=?, rec_installment_overdue=?, pay_sd_interest=?, interest_till_date=?,");
		query.append(" rec_lockin_period_interest=?, rec_od_amount=?, pay_sd_tds=?,");
		query.append(" rec_installment_unbilled=?, pay_sd_gap_interest=?, rec_fore_closure_charges=?, pay_sd_gap_tds=?, rec_other_charges=?, pay_other_charges=?, ");
		query.append(" net_rec_pay=?, rec_principal_overdue=?, waiveoff_amount=?, ADVANCE_EMI_REFUNDS=?,rec_status=?, maker_id=?, maker_date=");
		//query.append("DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)");
		//Saurabh space starts
		query.append(dbo);
		query.append("STR_TO_DATE(?,'"+dateFormatWithTime+"') + ' '+ substring((cast(CONVERT(time,getdate()) as nvarchar(30))),0,9) ");
		
		//Saurabh space ends

		query.append(" where loan_id=? and termination_id=?");
		
		// logger.info("going to fill query;;;;;;;;");
		if(CommonFunction.checkNull(vo.getRequestNumber()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else  
			insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getRequestNumber()).trim()));
		
		if(CommonFunction.checkNull(vo.getReasonForClosure()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getReasonForClosure()).trim()));
		
		if(CommonFunction.checkNull(vo.getEffectiveDate()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getEffectiveDate()).trim()));
		
		if(CommonFunction.checkNull(vo.getBalancePrincipal()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getBalancePrincipal()).trim())).toString());
		
		if(CommonFunction.checkNull(vo.getSecureDeposit()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getSecureDeposit()).trim())).toString());
		
		if(CommonFunction.checkNull(vo.getOverdueInstallments()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getOverdueInstallments()).trim())).toString());
		
		if(CommonFunction.checkNull(vo.getSecureDepositInterest()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getSecureDepositInterest()).trim())).toString());
		
		if(CommonFunction.checkNull(vo.getInterestTillDate()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getInterestTillDate()).trim())).toString());
		
		if(CommonFunction.checkNull(vo.getInterstTillLP()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getInterstTillLP()).trim())).toString());
		
		if(CommonFunction.checkNull(vo.getLppAmount()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getLppAmount()).trim())).toString());
		
		if(CommonFunction.checkNull(vo.getSecureDepositTDS()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getSecureDepositTDS()).trim())).toString());
		
		if(CommonFunction.checkNull(vo.getUnBilledInstallments()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getUnBilledInstallments()).trim())).toString());
		
		if(CommonFunction.checkNull(vo.getGapSDInterest()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getGapSDInterest()).trim())).toString());
		
		if(CommonFunction.checkNull(vo.getForeClosurePenalty()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getForeClosurePenalty()).trim())).toString());
		
		if(CommonFunction.checkNull(vo.getGapSDTDS()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getGapSDTDS()).trim())).toString());
		
		if(CommonFunction.checkNull(vo.getOtherDues()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getOtherDues()).trim())).toString());
		
		if(CommonFunction.checkNull(vo.getOtherRefunds()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getOtherRefunds()).trim())).toString());
		
		if(CommonFunction.checkNull(vo.getNetReceivablePayable()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getNetReceivablePayable()).trim())).toString());
		
		if(CommonFunction.checkNull(vo.getOverduePrincipal()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getOverduePrincipal()).trim())).toString());
		
		if(CommonFunction.checkNull(vo.getWaiveOffAmount()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getWaiveOffAmount()).trim())).toString());
		
		/* Added by Arun For Advance EMI Refunds Starts here*/
		if(CommonFunction.checkNull(vo.getAdvanceEmiRefunds()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getAdvanceEmiRefunds()).trim())).toString());
		
		/*Added by Arun For Advance EMI Refunds Ends Here*/
		
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
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		vo = null;
		queryList = null;
		query = null;
		insertPrepStmtObject = null;
	}
	return status;
}

	public boolean deleteClosureData(Object ob) {
		logger.info("::::::::::::::::In EarlyClosureDAOImpl");
	ClosureVO vo = (ClosureVO) ob;
	boolean status = false;
	ArrayList queryList = new ArrayList();
	StringBuilder query = new StringBuilder();
	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	StringBuilder fcDeleteQuery = new StringBuilder();
	PrepStmtObject insertPrepStmtObject1 = new PrepStmtObject();
	ArrayList deleteList = new ArrayList();
	try {
		logger.info(" deleteClosureData() Before query;;;;;;;;");
		
		query.append("delete from cr_termination_dtl where loan_id=? and termination_id=?");

		if(CommonFunction.checkNull(vo.getLbxLoanNoHID()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim()));
		
		if(CommonFunction.checkNull(vo.getTerminationId()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getTerminationId()).trim()));
		
		insertPrepStmtObject.setSql(query.toString());
		logger.info("IN deleteClosureData()  query ### "+insertPrepStmtObject.printQuery());
		queryList.add(insertPrepStmtObject);
		status = ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
		logger.info("In deleteClosureData......... status: "+ status);
		if(status)
		{
			fcDeleteQuery.append("delete from cr_fc_waiveoff_dtl where loan_id='"+CommonFunction.checkNull(vo.getLbxLoanNoHID())+"' and termination_id='"+CommonFunction.checkNull(vo.getTerminationId())+"'");
			insertPrepStmtObject1.setSql(fcDeleteQuery.toString());
			logger.info("IN delete cr_fc_waiveoff_dtl query ### "+insertPrepStmtObject1.printQuery());
			deleteList.add(insertPrepStmtObject1);
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(deleteList);
			logger.info("In delete cr_fc_waiveoff_dtl......... status: "+ status);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		vo = null;
		queryList = null;
		query = null;
		insertPrepStmtObject = null;
		fcDeleteQuery = null;
		deleteList.clear();
		deleteList = null;
		insertPrepStmtObject1 = null;
	}
	return status;
}

	
	public ArrayList<CancellationVO> selectCancellationData(String loanId, String terminationID) {
	ArrayList<CancellationVO> cancellationData = new ArrayList<CancellationVO>();
	CancellationVO vo = null;
	ArrayList mainlist = null;
	ArrayList subList = null;
	StringBuilder query = new StringBuilder();
	CancellationVO loanvo = null;
	StringBuilder query1 = new StringBuilder();
	CancellationVO loanvo1 = null;
	logger.info("::::::::::::::::In EarlyClosureDAOImpl");
	try{
	query.append("select distinct a.loan_no,a.loan_id, b.customer_name,");
	query.append(dbo);
	query.append(" DATE_FORMAT(a.loan_approval_date,'"+dateFormat+"'),a.loan_loan_amount,c.product_desc, ");
	query.append(" d.scheme_desc, a.loan_tenure,a.loan_repayment_freq, ");
	query.append(dbo);
	query.append(" DATE_FORMAT(a.loan_maturity_date,'"+dateFormat+"'), a.LOAN_FINAL_RATE, a.LOAN_BILLED_PRINCIPAL,");
	query.append(" a.DISBURSAL_STATUS, (select sum(ISNULL(disbursal_amount,0)) from cr_loan_disbursal_dtl where");
	query.append(" loan_id='"+loanId+"' and rec_status='A' group by loan_id) as disbursed_amount, a.LOAN_BALANCE_PRINCIPAL, "); 
	query.append(" a.LOAN_OVERDUE_PRINCIPAL, a.LOAN_BILLED_INSTL_NUM, a.LOAN_BILLED_INSTL_AMOUNT,");
	query.append(" a.LOAN_RECEIVED_INSTL_AMOUNT, e.request_no,e.remarks, e.termination_id,E.MAKER_REMARKS from cr_loan_dtl a,gcd_customer_m b,");
	query.append(" cr_product_m c, cr_scheme_m d,cr_termination_dtl e where b.customer_id = a.loan_customer_id and " );
	query.append(" c.product_id = a.loan_product and d.scheme_id = a.loan_scheme");
	query.append(" and a.loan_id=e.loan_id and e.loan_id='");
	query.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim()) + "' and e.termination_id="); 
	query.append("'"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(terminationID).trim())+"'");

	logger.info("In getCancellationValues : " + query);
	mainlist = ConnectionDAO.sqlSelect(query.toString());
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
	logger.info("In getCancellationValues : " + query1);
	mainlist = ConnectionDAO.sqlSelect(query1.toString());
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
		query = null;
		loanvo = null;
		query1 = null;
		loanvo1 = null;
	}

	return cancellationData;
}
	
	
	public ArrayList<PaymentMakerForCMVO> viewPayableForEarlyClousre(int loanId){
		logger.info("::::::::::::::::In EarlyClosureDAOImpl");
		ArrayList<PaymentMakerForCMVO> viewReceivabList=new ArrayList<PaymentMakerForCMVO>();
	
	try{
		ArrayList mainList=new ArrayList ();
		ArrayList subList =new ArrayList();	 					
		logger.info(" In viewPayableForEarlyClousre....");	
		StringBuilder query=new StringBuilder();
		query.append("SELECT ");
		query.append(dbo);
		query.append("DATE_FORMAT(ADVICE_DATE,'"+dateFormat+"'),(Select CHARGE_DESC From com_charge_code_m "+
		" Where CHARGE_CODE=CHARGE_CODE_ID) CHARGE,ORG_ADVICE_AMOUNT,WAIVE_OFF_AMOUNT,TDS_AMOUNT," +
		" TXN_ADJUSTED_AMOUNT,AMOUNT_IN_PROCESS,"+
    " (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS))BALANCE_AMOUNT,ADVICE_AMOUNT"+
		" FROM cr_txnadvice_dtl  WHERE REC_STATUS in('A','F') " +
		" AND  (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS))>0 "+
    " AND ADVICE_TYPE='P' AND LOAN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+"'" +
   	" AND BP_TYPE in(select bp_type from business_partner_view where LOAN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+"') " +
   	" ORDER BY ADVICE_DATE ASC");

		
		logger.info("In viewReceivableForDisbursal: "+query.toString());	
		
		mainList=ConnectionDAO.sqlSelect(query.toString());
		for(int i=0;i<mainList.size();i++)
		{
			subList= (ArrayList)mainList.get(i);
			if(subList.size()>0){
				PaymentMakerForCMVO paymentVO = new PaymentMakerForCMVO();
				paymentVO.setPaymentDate((CommonFunction.checkNull(subList.get(0)).trim()));
				paymentVO.setChargeDesc((CommonFunction.checkNull(subList.get(1)).trim()));
				if((CommonFunction.checkNull(subList.get(2)).trim()).equalsIgnoreCase(""))
					paymentVO.setOriginalAmount("0.00");
				else
				{
					Number orgAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(2)).trim()));
					paymentVO.setOriginalAmount(myFormatter.format(orgAmt));
				}
				
				if((CommonFunction.checkNull(subList.get(3)).trim()).equalsIgnoreCase(""))
					paymentVO.setWaiveOffAmount("0.00");
				else
				{
					Number waivedOffAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(3)).trim()));
					paymentVO.setWaiveOffAmount(myFormatter.format(waivedOffAmt));
				}
				
				if((CommonFunction.checkNull(subList.get(4)).trim()).equalsIgnoreCase(""))
					paymentVO.setTdsadviseAmount("0.00");
				else
				{
					Number tdsAdviceAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(4)).trim()));
					paymentVO.setTdsadviseAmount(myFormatter.format(tdsAdviceAmt));
				}
				
				if((CommonFunction.checkNull(subList.get(5)).trim()).equalsIgnoreCase(""))
					paymentVO.setAdjustedAmount("0.00");
				else
				{
					Number adjustedAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(5)).trim()));
					paymentVO.setAdjustedAmount(myFormatter.format(adjustedAmt));
				}
				
				if((CommonFunction.checkNull(subList.get(6)).trim()).equalsIgnoreCase(""))
					paymentVO.setAmountInProcess("0.00");
				else
				{
					Number amtInProc = myFormatter.parse((CommonFunction.checkNull(subList.get(6)).trim()));
					paymentVO.setAmountInProcess(myFormatter.format(amtInProc));
				}
				
				if((CommonFunction.checkNull(subList.get(7)).trim()).equalsIgnoreCase(""))
					paymentVO.setBalanceAmount("0.00");
				else
				{
					Number balAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(7)).trim()));
					paymentVO.setBalanceAmount(myFormatter.format(balAmt));
				}
				
				if((CommonFunction.checkNull(subList.get(8)).trim()).equalsIgnoreCase(""))
					paymentVO.setAdviceAmount("0.00");
				else
				{
					Number adviceAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(8)).trim()));
					paymentVO.setAdviceAmount(myFormatter.format(adviceAmt));
				}
				viewReceivabList.add(paymentVO);
			}
		}
			}catch(Exception e){
				e.printStackTrace();
			}


			return viewReceivabList;
	
}
	
	

	public ArrayList<PaymentMakerForCMVO> viewReceivableForEarlyClousre(int loanId)
	{
		logger.info("::::::::::::::::In EarlyClosureDAOImpl");
		ArrayList<PaymentMakerForCMVO> viewReceivabList=new ArrayList<PaymentMakerForCMVO>();
		
		try{
			ArrayList mainList=new ArrayList ();
			ArrayList subList =new ArrayList();	 					
			logger.info(" In viewReceivableForDisbursal....");	
			StringBuilder query=new StringBuilder();
			 query.append("SELECT ");
			 query.append(dbo);
			 query.append("DATE_FORMAT(ADVICE_DATE,'"+dateFormat+"'),(Select CHARGE_DESC From com_charge_code_m "+
 		" Where CHARGE_CODE=CHARGE_CODE_ID) CHARGE,ORG_ADVICE_AMOUNT,WAIVE_OFF_AMOUNT,TDS_AMOUNT," +
 		" TXN_ADJUSTED_AMOUNT,AMOUNT_IN_PROCESS,"+
        " (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS))BALANCE_AMOUNT,ADVICE_AMOUNT,G.DESCRIPTION "+
 		" FROM cr_txnadvice_dtl A inner join generic_master G on A.BP_TYPE=G.VALUE AND G.GENERIC_KEY='BPTYPE' WHERE A.REC_STATUS in('A','F') " +
 		" AND  (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT))>0 "+
        " AND ADVICE_TYPE='R' AND LOAN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+"'" +
       	" AND BP_TYPE in(select bp_type from business_partner_view where LOAN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+"') " +
       	" ORDER BY ADVICE_DATE ASC");

			
			logger.info("In viewReceivableForDisbursal: "+query.toString());	
			
			mainList=ConnectionDAO.sqlSelect(query.toString());
			for(int i=0;i<mainList.size();i++)
			{
				subList= (ArrayList)mainList.get(i);
				if(subList.size()>0){
					PaymentMakerForCMVO paymentVO = new PaymentMakerForCMVO();
					paymentVO.setPaymentDate((CommonFunction.checkNull(subList.get(0)).trim()));
 				paymentVO.setChargeDesc((CommonFunction.checkNull(subList.get(1)).trim()));
 				if((CommonFunction.checkNull(subList.get(2)).trim()).equalsIgnoreCase(""))
 					paymentVO.setOriginalAmount("0.00");
 				else
 				{
 					Number orgAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(2)).trim()));
 					paymentVO.setOriginalAmount(myFormatter.format(orgAmt));
 				}
 				
 				if((CommonFunction.checkNull(subList.get(3)).trim()).equalsIgnoreCase(""))
 					paymentVO.setWaiveOffAmount("0.00");
 				else
 				{
 					Number waivedOffAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(3)).trim()));
 					paymentVO.setWaiveOffAmount(myFormatter.format(waivedOffAmt));
 				}
 				
 				if((CommonFunction.checkNull(subList.get(4)).trim()).equalsIgnoreCase(""))
 					paymentVO.setTdsadviseAmount("0.00");
 				else
 				{
 					Number tdsAdviceAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(4)).trim()));
 					paymentVO.setTdsadviseAmount(myFormatter.format(tdsAdviceAmt));
 				}
 				
 				if((CommonFunction.checkNull(subList.get(5)).trim()).equalsIgnoreCase(""))
 					paymentVO.setAdjustedAmount("0.00");
 				else
 				{
 					Number adjustedAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(5)).trim()));
 					paymentVO.setAdjustedAmount(myFormatter.format(adjustedAmt));
 				}
 				
 				if((CommonFunction.checkNull(subList.get(6)).trim()).equalsIgnoreCase(""))
 					paymentVO.setAmountInProcess("0.00");
 				else
 				{
 					Number amtInProc = myFormatter.parse((CommonFunction.checkNull(subList.get(6)).trim()));
 					paymentVO.setAmountInProcess(myFormatter.format(amtInProc));
 				}
 				
 				if((CommonFunction.checkNull(subList.get(7)).trim()).equalsIgnoreCase(""))
 					paymentVO.setBalanceAmount("0.00");
 				else
 				{
 					Number balAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(7)).trim()));
 					paymentVO.setBalanceAmount(myFormatter.format(balAmt));
 				}
 				
 				if((CommonFunction.checkNull(subList.get(8)).trim()).equalsIgnoreCase(""))
 					paymentVO.setAdviceAmount("0.00");
 				else
 				{
 					Number adviceAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(8)).trim()));
 					paymentVO.setAdviceAmount(myFormatter.format(adviceAmt));
 				}
 				paymentVO.setBusinessPartnerType((CommonFunction.checkNull(subList.get(9)).trim()));
 				viewReceivabList.add(paymentVO);
				}
			}
				}catch(Exception e){
					e.printStackTrace();
				}


				return viewReceivabList;
}
	

	public ArrayList<RepayScheduleVo> getRepayScheduleDisbursal(String loanId) {
	ArrayList<RepayScheduleVo> list = new ArrayList<RepayScheduleVo>();
	logger.info("::::::::::::::::In EarlyClosureDAOImpl");
	logger.info("In getRepayScheduleDisbursal():------> ");
	
	ArrayList mainlist = new ArrayList();
	ArrayList subList = new ArrayList();
	StringBuilder query= new StringBuilder();
	RepayScheduleVo repvo = null;
	try {

		query.append("select INSTL_NO,");
		query.append(dbo);
		query.append("DATE_FORMAT(INSTL_DATE,'"+dateFormat+"'),INSTL_AMOUNT,PRIN_COMP,INT_COMP,EXCESS_INT," +
				" CASE WHEN ADV_FLAG='Y' THEN 'YES' ELSE 'NO'END,PRIN_OS,INSTL_AMOUNT_RECD,CASE WHEN BILL_FLAG='Y'THEN 'YES' ELSE 'NO' END as bill,OTHER_CHARGES from cr_repaysch_dtl where LOAN_ID="+loanId+" and  ");
		query.append("ISNULL(REC_STATUS,'A') <>'X' order by SEQ_NO ");
		
		logger.info("Query in getRepaySched-----" + query);
		mainlist = ConnectionDAO.sqlSelect(query.toString());
		int size = mainlist.size();
		for (int i = 0; i <size; i++) {
			subList = (ArrayList) mainlist.get(i);
			repvo = new RepayScheduleVo();
			if (subList.size() > 0) {

				repvo.setInstNo((CommonFunction.checkNull(subList.get(0)).trim()));
				repvo.setDueDate((CommonFunction.checkNull(subList.get(1)).trim()));
				
				if(!CommonFunction.checkNull(subList.get(2)).trim().equalsIgnoreCase(""))
	    		{
    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());  
    	    		repvo.setInstAmount(myFormatter.format(reconNum));
	    		}
				else
					repvo.setInstAmount("0.00");
				//repvo.setInstAmount((CommonFunction.checkNull(subList.get(2)).trim()));
				if(!CommonFunction.checkNull(subList.get(3)).trim().equalsIgnoreCase(""))
	    		{
    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(3))).trim());  
    	    		repvo.setPrinciple(myFormatter.format(reconNum));
	    		}
				else
					repvo.setPrinciple("0.00");
				//repvo.setPrinciple((CommonFunction.checkNull(subList.get(3)).trim()));
				if(!CommonFunction.checkNull(subList.get(4)).trim().equalsIgnoreCase(""))
	    		{
    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(4))).trim());  
    	    		repvo.setInstCom(myFormatter.format(reconNum));
	    		}
				else
					repvo.setInstCom("0.00");
				//repvo.setInstCom((CommonFunction.checkNull(subList.get(4)).trim()));
				if(!CommonFunction.checkNull(subList.get(5)).trim().equalsIgnoreCase(""))
	    		{
    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(5))).trim());  
    	    		repvo.setExcess(myFormatter.format(reconNum));
	    		}
				else
					repvo.setExcess("0.00");
				//repvo.setExcess((CommonFunction.checkNull(subList.get(5)).trim()));
				
				repvo.setAdvFlag((CommonFunction.checkNull(subList.get(6)).trim()));
				if(!CommonFunction.checkNull(subList.get(7)).trim().equalsIgnoreCase(""))
	    		{
    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(7))).trim());  
    	    		repvo.setPrinOS(myFormatter.format(reconNum));
	    		}
				else
					repvo.setPrinOS("0.00");
				if(!CommonFunction.checkNull(subList.get(8)).trim().equalsIgnoreCase(""))
	    		{
    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(8))).trim());  
    	    		repvo.setInstAmountRec(myFormatter.format(reconNum));
	    		}
				else
					repvo.setInstAmountRec("0.00");
				repvo.setBillFlag((CommonFunction.checkNull(subList.get(9)).trim()));
				//repvo.setPrinOS((CommonFunction.checkNull(subList.get(7)).trim()));
				if(!CommonFunction.checkNull(subList.get(10)).trim().equalsIgnoreCase(""))
	    		{
    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(10))).trim());  
    	    		repvo.setOtherCharges(myFormatter.format(reconNum));
	    		}
				else
					repvo.setOtherCharges("0.00");
				
			}
			list.add(repvo);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		mainlist = null;
		subList = null;
		query= null;
		repvo = null;
	}
	return list;
}
	
	
	public ArrayList<RepayScheduleVo> getRepaySchFieldsDetail(String loanId) {
		logger.info("::::::::::::::::In EarlyClosureDAOImpl");
	logger.info("Inside getRepaySchFieldsDetail.....DAOImpl");
	ArrayList<RepayScheduleVo> list = new ArrayList();
	
	ArrayList mainlist = new ArrayList();
	ArrayList subList = new ArrayList();
	StringBuilder query= new StringBuilder();
	StringBuilder bussIrrQ= new StringBuilder();
	RepayScheduleVo repvo = null;
	
	try {
		query.append("select ld.LOAN_RATE_METHOD,ld.LOAN_FLAT_RATE,ld.LOAN_EFF_RATE,ld.LOAN_IRR1,ld.LOAN_IRR2,UPFRONT_ROUNDING_AMOUNT " +
				" from cr_loan_dtl ld where ld.LOAN_ID="+loanId);
		logger.info("Query in getRepaySchFieldsDetail-----" + query.toString());
		bussIrrQ.append("select deal_irr2 from cr_deal_loan_dtl where DEAL_ID=(select LOAN_DEAL_ID from cr_loan_dtl where LOAN_ID="+loanId+")");
		logger.info("Query in getRepaySchFieldsDetail--DEAL_IRR2---" + bussIrrQ.toString());
		String bussIrr=ConnectionDAO.singleReturn(bussIrrQ.toString());
		mainlist = ConnectionDAO.sqlSelect(query.toString());
		int size = mainlist.size();
		for (int i = 0; i < size; i++) {
			
			subList = (ArrayList) mainlist.get(i);
			repvo = new RepayScheduleVo();
			if (subList.size()> 0) {
				
				repvo.setLoanRateMethod((CommonFunction.checkNull(subList.get(0)).trim()));
				if(!CommonFunction.checkNull(subList.get(1)).trim().equalsIgnoreCase(""))
	    		{
    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(1))).trim());  
    	    		repvo.setFinalRate(myFormatter.format(reconNum));
	    		}
				else
					repvo.setFinalRate("0.00");
				//repvo.setFinalRate((CommonFunction.checkNull(subList.get(1)).trim()));
				//logger.info("Final Rate: "+CommonFunction.checkNull(subList.get(1)));
				if(!CommonFunction.checkNull(subList.get(2)).trim().equalsIgnoreCase(""))
	    		{
    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());  
    	    		repvo.setEffectiveRate(myFormatter.format(reconNum));
	    		}
				else
					repvo.setEffectiveRate("0.00");
				//repvo.setEffectiveRate((CommonFunction.checkNull(subList.get(2)).trim()));
				if(!CommonFunction.checkNull(subList.get(3)).trim().equalsIgnoreCase(""))
	    		{
    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(3))).trim());  
    	    		repvo.setMktIRR1(myFormatter.format(reconNum));
	    		}
				else
					repvo.setMktIRR1("0.00");
				//repvo.setMktIRR1((CommonFunction.checkNull(subList.get(3)).trim()));
				if(!CommonFunction.checkNull(subList.get(4)).trim().equalsIgnoreCase(""))
	    		{
    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(4))).trim());  
    	    		repvo.setMktIRR2(myFormatter.format(reconNum));
	    		}
				else
					repvo.setMktIRR2("0.00");
				// add by saorabh
				if(!CommonFunction.checkNull(subList.get(5)).trim().equalsIgnoreCase(""))
	    		{
    	    		Number UPFRONT_ROUNDING_AMOUNT =myFormatter.parse((CommonFunction.checkNull(subList.get(5))).trim());  
    	    		repvo.setUpfrontRoundingAmount(myFormatter.format(UPFRONT_ROUNDING_AMOUNT));
	    		}
				else 
					repvo.setUpfrontRoundingAmount("0.00");
				// end by saorabh
				//repvo.setMktIRR2((CommonFunction.checkNull(subList.get(4)).trim()));
				if((CommonFunction.checkNull(subList.get(0)).trim()).equalsIgnoreCase("E"))
                {
					repvo.setFinalRate("");
                }
				repvo.setDealIRR2(bussIrr);
			
			}
			list.add(repvo);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		mainlist= null;
		subList = null;
		query= null;
		bussIrrQ= null;
		repvo = null;
	}
	return list;
}
	

	
	public ArrayList<ClosureVO> getDuesRefundsList(String companyId, String loanId, String effectiveDate, String closureType,String source) throws SQLException{
		logger.info("::::::::::::::::In EarlyClosureDAOImpl");
		logger.info("In getDuesRefundsList.............Dao Impl");
	logger.info("Loan id: " + loanId);
	logger.info("Effective Date: " + effectiveDate);
//	CallableStatement cst = null;
	ClosureVO closureVo = new ClosureVO();
	ArrayList<ClosureVO> duesRefundsList = new ArrayList<ClosureVO>();
//	Connection con = ConnectionDAO.getConnection();
	BigDecimal balancePrincipal = null;
	//BigDecimal overduePrincipal = null;
	BigDecimal overdueInstallments = null;
	BigDecimal interestTillDate = null;
	BigDecimal unbilledInstallments = null;
	BigDecimal foreClosurePenalty = null;
	BigDecimal otherDues = null;
	BigDecimal otherRefunds = null;
	BigDecimal lpInterest = null;
	BigDecimal lppAmt = null;
	BigDecimal netRecPay = null;
	BigDecimal totalRecevable = null;
	BigDecimal emiRefundsBD = null;
	BigDecimal totalPayable = null;
	StringBuilder query =new StringBuilder();
	String repayEffDateStr = "";
	String queryScheme ="";
	SimpleDateFormat dateFormat1 = null;
	Calendar cal = null;
	ArrayList<Object> in =new ArrayList<Object>();
	ArrayList<Object> out =new ArrayList<Object>();
	ArrayList outMessages = new ArrayList();
	String s1 = "";
	String s2 = "";
	String bprincipal="";
	String ovprincipal="";
	String ovinstallments="";  
	String ubinstallments="";
		String otherdues="";
 	String gapintrst="";  
 	String repaypenalty="";
	String lpintrst="";
	String oldamt="";  
   	String otherpayable="";  
 	String securitydeposit="";
	String securitydepositintrst="";
	String securitydeposittds=""; 
   	String securitydepositgapintrst="";  
 	String securitydepositgaptds="";
 	String emiRefunds="";
	try {
//		cst = con.prepareCall("call EARLY_CLOSURE_DETAIL(?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		in.add((CommonFunction.checkNull(companyId).trim()));//I_COMPANY_ID
		in.add((CommonFunction.checkNull(loanId).trim()));//I_LOAN_ID
		String date=CommonFunction.changeFormat(CommonFunction.checkNull(effectiveDate).trim());
		         	    if(date != null)
		         	    	in.add(date);
	   if(source.trim().equalsIgnoreCase("cs"))
		   in.add("CS");
 	    out.add( bprincipal);//O_BALANCE_PRINCIPAL--4
	    out.add( ovprincipal);//O_OVERDUE_PRINCIPAL--5
	    out.add(ovinstallments);//O_OVERDUE_INSTALLMENTS--6
	    out.add(ubinstallments);//O_UNBILLED_INSTALLMENTS--7
	    out.add( otherdues);//O_OTHER_DUES--8
	    out.add(gapintrst);//O_GAP_INTEREST--9
	    out.add(repaypenalty);//O_PREPAY_PENALTY--10
		out.add(lpintrst);//LP Interest or O_LOCKIN_PERIOD_INTEREST--11
		out.add(oldamt);//OD Amount or LPP Amount or O_OD_AMOUNT--12
		out.add( otherpayable);//O_OTHER_PAYABLES--13
		out.add(securitydeposit);//O_SECURE_DEPOSIT--14
		out.add(securitydepositintrst);//O_SECURE_DEPOSIT_INTEREST--15
		out.add( securitydeposittds);//O_SECURE_DEPOSIT_TDS--16
		out.add(securitydepositgapintrst);//O_SECURE_DEPOSIT_GAP_INTEREST--17
		out.add(securitydepositgaptds);//O_SECURE_DEPOSIT_GAP_TDS--18
		out.add(emiRefunds);//O_ADVANCE_EMI_REFUNDS--19
	    out.add(s1);//O_ERRORFLAG--20
	    out.add(s2);//O_ERRORMSG--21
	    if(source.trim().equalsIgnoreCase("cs"))
	    {
	    	logger.info("Before procedure call Early_Closure_Detail_Main("+in.toString()+","+out.toString()+")");
	    	outMessages=(ArrayList) ConnectionDAO.callSP("Early_Closure_Detail_Main",in,out);
	    }
	    else if(source.trim().equalsIgnoreCase("closure"))
	    {
	    	logger.info("Before procedure call EARLY_CLOSURE_DETAIL("+in.toString()+","+out.toString()+")");
	    	outMessages=(ArrayList) ConnectionDAO.callSP("EARLY_CLOSURE_DETAIL",in,out);
	    }
		Number balPrincipal=0.0;
		Number overduePrinci=0.0;
		Number overdueInst=0.0;
		Number unbilledInst=0.0;
		Number othDues=0.0;
		Number intTillDate=0.0;
		Number foreClosurePennalty=0.0;
		Number othRef=0.0;
		Number secDeposit=0.0;
		Number secDepoInt=0.0;
		Number secDepTDS=0.0;
		Number gapSDInt=0.0;
		Number gapSDtds=0.0;
		Number interestTillLP=0.0;
		Number lppAmount=0.0;
		Number advanceEmiRefunds=0.0;
		
		//logger.info("After procedure call EARLY_CLOSURE_DETAIL....."+CommonFunction.checkNull(outMessages.get(0)).trim());
		//logger.info("After procedure call EARLY_CLOSURE_DETAIL....."+outMessages);
		if((CommonFunction.checkNull(outMessages.get(0)).trim()).equalsIgnoreCase(""))
		{
			closureVo.setBalancePrincipal("0.00");
		}
		else
		{
			balPrincipal = myFormatter.parse((CommonFunction.checkNull(outMessages.get(0)).trim()));
			closureVo.setBalancePrincipal(myFormatter.format(balPrincipal));
		}
		
		if((CommonFunction.checkNull(outMessages.get(1)).trim()).equalsIgnoreCase(""))
		{
			closureVo.setOverduePrincipal("0.00");
		}
		else
		{
			overduePrinci = myFormatter.parse((CommonFunction.checkNull(outMessages.get(1)).trim()));
			closureVo.setOverduePrincipal(myFormatter.format(overduePrinci));
		}
		
		if((CommonFunction.checkNull(outMessages.get(2)).trim()).equalsIgnoreCase(""))
			closureVo.setOverdueInstallments("0.00");
		else
		{
			overdueInst = myFormatter.parse((CommonFunction.checkNull(outMessages.get(2)).trim()));
			closureVo.setOverdueInstallments(myFormatter.format(overdueInst));
		}
		
		if((CommonFunction.checkNull(outMessages.get(3)).trim()).equalsIgnoreCase(""))
			closureVo.setUnBilledInstallments("0.00");
		else
		{
			unbilledInst = myFormatter.parse((CommonFunction.checkNull(outMessages.get(3)).trim()));
			closureVo.setUnBilledInstallments(myFormatter.format(unbilledInst));
		}
		if((CommonFunction.checkNull(outMessages.get(4)).trim()).equalsIgnoreCase(""))
			closureVo.setOtherDues("0.00");
		else
		{
			othDues = myFormatter.parse((CommonFunction.checkNull(outMessages.get(4)).trim()));
			closureVo.setOtherDues(myFormatter.format(othDues));
		}
		if((CommonFunction.checkNull(outMessages.get(5)).trim()).equalsIgnoreCase(""))
			closureVo.setInterestTillDate("0.00");
		else
		{
			intTillDate = myFormatter.parse((CommonFunction.checkNull(outMessages.get(5)).trim()));
			closureVo.setInterestTillDate(myFormatter.format(intTillDate));
		}
		if(closureType.equalsIgnoreCase("T"))
		{
			if((CommonFunction.checkNull(outMessages.get(6)).trim()).equalsIgnoreCase(""))
				closureVo.setForeClosurePenalty("0.00");
			else
			{
				foreClosurePennalty = myFormatter.parse((CommonFunction.checkNull(outMessages.get(6)).trim()));
				closureVo.setForeClosurePenalty(myFormatter.format(foreClosurePennalty));
			}
		}
		if(closureType.equalsIgnoreCase("C"))
		{
			closureVo.setForeClosurePenalty("0.00");
		}
		if((CommonFunction.checkNull(outMessages.get(7)).trim()).equalsIgnoreCase(""))
			closureVo.setInterstTillLP("0.00");
		else
		{
			interestTillLP = myFormatter.parse((CommonFunction.checkNull(outMessages.get(7)).trim()));
			closureVo.setInterstTillLP(myFormatter.format(interestTillLP));
		}
		if((CommonFunction.checkNull(outMessages.get(8)).trim()).equalsIgnoreCase(""))
			closureVo.setLppAmount("0.00");
		else
		{
			lppAmount = myFormatter.parse((CommonFunction.checkNull(outMessages.get(8)).trim()));
			closureVo.setLppAmount(myFormatter.format(lppAmount));
		}
		if((CommonFunction.checkNull(outMessages.get(9)).trim()).equalsIgnoreCase(""))
			closureVo.setOtherRefunds("0.00");
		else
		{
			othRef = myFormatter.parse((CommonFunction.checkNull(outMessages.get(9)).trim()));
			closureVo.setOtherRefunds(myFormatter.format(othRef));
		}
		if((CommonFunction.checkNull(outMessages.get(10)).trim()).equalsIgnoreCase(""))
			closureVo.setSecureDeposit("0.00");
		else
		{
			secDeposit = myFormatter.parse((CommonFunction.checkNull(outMessages.get(10)).trim()));
			closureVo.setSecureDeposit(myFormatter.format(secDeposit));
		}
		if((CommonFunction.checkNull(outMessages.get(11)).trim()).equalsIgnoreCase(""))
			closureVo.setSecureDepositInterest("0.00");
		else
		{
			secDepoInt = myFormatter.parse((CommonFunction.checkNull(outMessages.get(11)).trim()));
			closureVo.setSecureDepositInterest(myFormatter.format(secDepoInt));
		}
		if((CommonFunction.checkNull(outMessages.get(12)).trim()).equalsIgnoreCase(""))
			closureVo.setSecureDepositTDS("0.00");
		else
		{
			secDepTDS = myFormatter.parse((CommonFunction.checkNull(outMessages.get(12)).trim()));
			closureVo.setSecureDepositTDS(myFormatter.format(secDepTDS));
		}
		if((CommonFunction.checkNull(outMessages.get(13)).trim()).equalsIgnoreCase(""))
			closureVo.setGapSDInterest("0.00");
		else
		{
			gapSDInt = myFormatter.parse((CommonFunction.checkNull(outMessages.get(13)).trim()));
			closureVo.setGapSDInterest(myFormatter.format(gapSDInt));
		}
		if((CommonFunction.checkNull(outMessages.get(14)).trim()).equalsIgnoreCase(""))
			closureVo.setGapSDTDS("0.00");
		else
		{
			gapSDtds = myFormatter.parse((CommonFunction.checkNull(outMessages.get(14)).trim()));
			closureVo.setGapSDTDS(myFormatter.format(gapSDtds));
		}
		//Change By Arunn
		if((CommonFunction.checkNull(outMessages.get(15)).trim()).equalsIgnoreCase(""))
			closureVo.setAdvanceEmiRefunds("0.00");
		else
		{
			advanceEmiRefunds = myFormatter.parse((CommonFunction.checkNull(outMessages.get(15)).trim()));
			closureVo.setAdvanceEmiRefunds(myFormatter.format(advanceEmiRefunds));
		}
		//Change By Arunn
		balancePrincipal = new BigDecimal(balPrincipal.toString());
		//overduePrincipal = new BigDecimal(overduePrinci.toString());
		overdueInstallments = new BigDecimal(overdueInst.toString());
		interestTillDate = new BigDecimal(intTillDate.toString());
		unbilledInstallments = new BigDecimal(unbilledInst.toString());
		foreClosurePenalty = new BigDecimal(foreClosurePennalty.toString());
		otherDues = new BigDecimal(othDues.toString());
		otherRefunds = new BigDecimal(othRef.toString());
		lpInterest = new BigDecimal(interestTillLP.toString());
		lppAmt = new BigDecimal(lppAmount.toString());
		totalPayable=new BigDecimal("0");
		logger.info("Values from Procedure Set in VO+advanceEmiRefunds:----"+advanceEmiRefunds);
		emiRefundsBD= new BigDecimal(advanceEmiRefunds.toString());
		totalRecevable=balancePrincipal.add(overdueInstallments.add(interestTillDate.add(unbilledInstallments.add(foreClosurePenalty.add(otherDues.add(lpInterest.add(lppAmt)))))));
		netRecPay = balancePrincipal.add(overdueInstallments.add(interestTillDate.add(unbilledInstallments.add(foreClosurePenalty.add(otherDues.add(lpInterest.add(lppAmt))))))).subtract(otherRefunds.add(emiRefundsBD));
		System.out.println("\n\nnetRecPay  : "+netRecPay);
		System.out.println("\n\notherRefunds   : "+otherRefunds+"\n\n");
		totalPayable=totalPayable.add(otherRefunds).add(emiRefundsBD);
		logger.info("Values from Procedure Set in VO+totalPayable:----"+totalPayable);
		closureVo.setTotalRecevable(myFormatter.format(myFormatter.parse(totalRecevable.toString())));
		closureVo.setNetReceivablePayable(myFormatter.format(myFormatter.parse(netRecPay.toString())));
		closureVo.setTotalPayable(myFormatter.format(myFormatter.parse(totalPayable.toString())));
		logger.info("Values from Procedure Set in VO");
		s1 = (CommonFunction.checkNull(outMessages.get(16)).trim());
		s2 = (CommonFunction.checkNull(outMessages.get(17)).trim());
		logger.info("s1: " + s1);
		logger.info("s2: " + s2);
		logger.info("Size of List: " + duesRefundsList.size());
		
		query.append("select ");
		query.append(dbo);
		query.append("DATE_FORMAT(d.LOAN_REPAY_EFF_DATE,'"+dateFormat+"') from cr_loan_dtl d where d.LOAN_ID='"+StringEscapeUtils.escapeSql(loanId)+"'");
		repayEffDateStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(query.toString()));
		
		queryScheme = "select s.TERMINATION_LOCKIN_PERIOD from cr_loan_dtl d,cr_scheme_m s where d.LOAN_SCHEME=s.SCHEME_ID and d.loan_id='"+StringEscapeUtils.escapeSql(loanId)+"'";
		String schemeResult=ConnectionDAO.singleReturn(queryScheme);
		int lockinPeriodStr=0;
		 if(source.trim().equalsIgnoreCase("closure"))
		   {	
				if(!CommonFunction.checkNull(schemeResult).equalsIgnoreCase(""))
				{
					lockinPeriodStr = Integer.parseInt(schemeResult);
				}
		  
		
		dateFormat1 = new SimpleDateFormat(dateForDisbursal);
		cal = Calendar.getInstance();
		try {
			  Date repayEffDate = dateFormat1.parse(repayEffDateStr);
			  logger.info("repayEffDate: "+repayEffDate);
			  cal.setTime(repayEffDate);
			  cal.add(Calendar.MONTH, lockinPeriodStr);
			  Date repayEffDateNew = cal.getTime();
			  logger.info("repayEffDateNew: "+repayEffDateNew);
			  Date businessDate = dateFormat1.parse(effectiveDate);
			  logger.info("businessDate: "+businessDate);
			  if(repayEffDateNew.after(businessDate) || repayEffDateNew.equals(businessDate))
			  {
				  closureVo.setEarlyClosureWarn("T");
				  logger.info("Lockin period Valid");
			  }
			  else
			  {
				  closureVo.setEarlyClosureWarn("F");
				  logger.info("Lockin period Invalid");
			  }
			  
		  }catch(Exception e){
			  e.printStackTrace();
		  }
	 }
		  if(s1.equalsIgnoreCase("E"))
			  closureVo.setProcWarning(s2);
		  duesRefundsList.add(closureVo);
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		
		
		closureVo = null;
		
		balancePrincipal = null;
		overdueInstallments = null;
		interestTillDate = null;
		unbilledInstallments = null;
		foreClosurePenalty = null;
		otherDues = null;
		otherRefunds = null;
		lpInterest = null;
		lppAmt = null;
		netRecPay = null;
		query = null;
		repayEffDateStr = null;
		queryScheme = null;
		queryScheme = null;
		dateFormat1 = null;
		cal = null;
		s1 = null;
		s2 = null;
	}
	return duesRefundsList;
}
	
	
	public String saveClosureAuthor(Object ob) {
	logger.info("In saveClosureAuthor ........ EarlyClosureDAOImpl");
	ClosureAuthorVO vo = (ClosureAuthorVO) ob;
	String status = "";
	logger.info("In saveClosureAuthor ........Decision: "+ vo.getDecision());
	//CallableStatement cst=null;
	//Connection con=ConnectionDAO.getConnection();
	String s1= "";
	String s2 = "";
	ArrayList<Object> in =new ArrayList<Object>();
	ArrayList<Object> out =new ArrayList<Object>();
	ArrayList outMessages = new ArrayList();

	// For Early Closure & Maturity Closure Author
	
	if(vo.getClosureStatus().equalsIgnoreCase("T") || vo.getClosureStatus().equalsIgnoreCase("C"))
	{
		logger.info("In PROCEDURE early_closure_author");
		try {

			//con.setAutoCommit(false);
			//cst=con.prepareCall("call early_closure_author(?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?,?)");
			  in.add(vo.getCompanyId());
			  in.add(Integer.parseInt(CommonFunction.checkNull (vo.getLoanId()).trim()));
			  String date=CommonFunction.changeFormat(CommonFunction.checkNull (vo.getAuthorDate()).trim());
        	    if(date != null)
        	    	in.add(date);
//			cst.setString(3,CommonFunction.checkNull (vo.getAuthorDate()).trim());
        	    in.add(CommonFunction.checkNull (vo.getAuthorId()).trim());
        	    in.add(CommonFunction.checkNull (vo.getDecision()).trim());
        	    in.add(CommonFunction.checkNull (vo.getComments()).trim());
        	    out.add(s1);
        	    out.add(s2);
		
//			cst.executeUpdate();
			 outMessages=(ArrayList) ConnectionDAO.callSP("early_closure_author",in,out);
			    s1=CommonFunction.checkNull(outMessages.get(0));
        	    s2=CommonFunction.checkNull(outMessages.get(1));

			if(s1!=null && s1.equalsIgnoreCase("S"))
			{
			//	con.commit();
				status=s1;	
			}
			else if(s1!=null && s1.equalsIgnoreCase("E"))
			{
			//	con.rollback();
				status=s2;	
			}
			logger.info("status-------------1: "+s1);
			logger.info("s2-----------------1: "+s2);
		} catch (Exception e) {
				
			
			
			e.printStackTrace();
		}
		finally
		{
			
			s1=null;
			s2 =null;
		}
	}
	
	// For Cancellation Author
	
	if(vo.getClosureStatus().equalsIgnoreCase("X"))
	{	logger.info("In PROCEDURE Loan_Cancellation_Author");
		try {

		//	con.setAutoCommit(false);
//			cst=con.prepareCall("call Loan_Cancellation_Author(?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?,?)");
		   	in.add(vo.getCompanyId());
		   	in.add(Integer.parseInt(CommonFunction.checkNull (vo.getLoanId()).trim()));
			 String date=CommonFunction.changeFormat(CommonFunction.checkNull (vo.getAuthorDate()).trim());
        	    if(date != null)
        	    	in.add(date);
//			cst.setString(3,CommonFunction.checkNull (vo.getAuthorDate()).trim());
        	    in.add(CommonFunction.checkNull (vo.getAuthorId()).trim());
        	    in.add(CommonFunction.checkNull (vo.getDecision()).trim());
        	    in.add(CommonFunction.checkNull (vo.getComments()).trim());
		    out.add(s1);
    	    out.add(s2);
    	    outMessages=(ArrayList) ConnectionDAO.callSP("Loan_Cancellation_Author",in,out);
    	    s1=CommonFunction.checkNull(outMessages.get(0));
    	    s2=CommonFunction.checkNull(outMessages.get(1));

			if(s1!=null && s1.equalsIgnoreCase("S"))
			{
				//con.commit();
				status=s1;	
			}
			else if(s1!=null && s1.equalsIgnoreCase("E"))
			{
				//con.rollback();
				status=s2;	
			}
			logger.info("status--------2: "+s1);
			logger.info("s2------------2: "+s2);
		} catch (Exception e) {
				
			
			
			e.printStackTrace();
		}
		finally
		{
//			try {
//				con.close();
//				cst.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}			
			s1=null;
			s2 =null;
		}
	}
	return status;
}
	public ArrayList<ClosureVO> getClosureValues(int lbxLoanNoHID,String businessDate) {
		// TODO Auto-generated method stub
		ArrayList<ClosureVO> loanDetailList = new ArrayList<ClosureVO>();
		logger.info("In getClosureValues Loan Id : " + lbxLoanNoHID);
		ArrayList mainlist = new ArrayList();
		ArrayList subList = new ArrayList();
		StringBuilder query = new StringBuilder();
		ClosureVO loanvo = null;
		StringBuilder query1 =new StringBuilder();
		ClosureVO loanvo1 = null;
		try {
			 query.append(" select distinct a.loan_no,");
			 query.append(dbo);
			 query.append("DATE_FORMAT(a.loan_approval_date,'"+dateFormat+"'), b.customer_name,c.product_desc, d.scheme_desc,");
			 query.append(" a.loan_repayment_freq, a.loan_tenure, ");
			 query.append(dbo);
			 query.append("DATE_FORMAT(a.loan_maturity_date,'"+dateFormat+"'),a.loan_id,");
			 query.append(" (a.loan_no_of_installment-a.loan_billed_instl_num)  as remaining_installments ");
			 query.append(" from cr_loan_dtl a,gcd_customer_m b,");
			 query.append(" cr_product_m c, cr_scheme_m d where b.customer_id = a.loan_customer_id and c.product_id = a.loan_product");
			 query.append(" and d.scheme_id = a.loan_scheme and a.loan_id='"+ lbxLoanNoHID + "'");

			logger.info("In getClosureValues : " + query.toString());
			mainlist = ConnectionDAO.sqlSelect(query.toString());
			int size = mainlist.size();
			logger.info("In getClosureValues.....mainlist size: "+ size);
			for (int i = 0; i < size; i++) {

				subList = (ArrayList) mainlist.get(i);
				if (subList.size() > 0) {
					loanvo = new ClosureVO();
					loanvo.setLoanAc((CommonFunction.checkNull(subList.get(0)).trim()));
					loanvo.setLoanDate((CommonFunction.checkNull(subList.get(1)).trim()));
					loanvo.setCustomerName((CommonFunction.checkNull(subList.get(2)).trim()));
					loanvo.setProduct((CommonFunction.checkNull(subList.get(3)).trim()));
					loanvo.setScheme((CommonFunction.checkNull(subList.get(4)).trim()));
					if((CommonFunction.checkNull(subList.get(5)).trim()).equalsIgnoreCase("M"))
						loanvo.setFrequency("Monthly");
					if((CommonFunction.checkNull(subList.get(5)).trim()).equalsIgnoreCase("B"))
						loanvo.setFrequency("Bimonthly");
					if((CommonFunction.checkNull(subList.get(5)).trim()).equalsIgnoreCase("Q"))
						loanvo.setFrequency("Quarterly");
					if((CommonFunction.checkNull(subList.get(5)).trim()).equalsIgnoreCase("H"))
						loanvo.setFrequency("Half Yearly");
					if((CommonFunction.checkNull(subList.get(5)).trim()).equalsIgnoreCase("Y"))
						loanvo.setFrequency("Yearly");
					loanvo.setOriginalTenure((CommonFunction.checkNull(subList.get(6)).trim()));
					loanvo.setMaturityDate((CommonFunction.checkNull(subList.get(7)).trim()));
					loanvo.setLbxLoanNoHID((CommonFunction.checkNull(subList.get(8)).trim()));
					loanvo.setRemainingInstallments((CommonFunction.checkNull(subList.get(9)).trim()));
					loanDetailList.add(loanvo);
				}
			}
		
			query1.append("SELECT datediff(month,");
			query1.append(dbo);
			query1.append("STR_TO_DATE('"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(businessDate).trim())+"','"+dateFormat+"'),loan_maturity_date) as remaining_tenure from cr_loan_dtl where loan_id='"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(lbxLoanNoHID).trim())+"'");			
						
//			query1.append("SELECT period_diff(");
//			query1.append(dbo);
//			query1.append("date_format(loan_maturity_date,'%Y%m'),");
//			query1.append(dbo);
//			query1.append("date_format(");
//			query1.append(dbo);
//			query1.append("STR_TO_DATE('"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(businessDate).trim())+"','"+dateFormat+"'),'%Y%m')) as remaining_tenure from cr_loan_dtl where loan_id='"
//					+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(lbxLoanNoHID).trim()) + "'");
			logger.info("In getClosureValues : " + query1.toString());
			mainlist = ConnectionDAO.sqlSelect(query1.toString());
			size = mainlist.size();
			logger.info("In getClosureValues.....mainlist size: "+ size);
			for (int i = 0; i < size; i++) {

				subList = (ArrayList) mainlist.get(i);
				if (subList.size() > 0) {
					loanvo1 = new ClosureVO();
					loanvo1.setRemainingTenure((String) subList.get(0));
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
			query = null;
			loanvo = null;
			query1 = null;
			loanvo1 = null;
		}
		return loanDetailList;

	}
	//method added by neeraj tripathi
	public ArrayList getWaiveOffList(ArrayList<String> inList) 
	{
		logger.info("in getWaiveOffList()");
		ArrayList<Object> result =new ArrayList<Object>();
		ArrayList<Object> in =new ArrayList<Object>();
		ArrayList<Object> out =new ArrayList<Object>();
		ArrayList outMessages = new ArrayList();
				
		String operation=inList.get(0);
		String terminationId=inList.get(1);
		String userId=inList.get(2);
		String businessDate=inList.get(3);
		businessDate=CommonFunction.changeFormat(businessDate);
		String s1="";
		String s2="";
		in.add(operation);
		in.add(terminationId);
		in.add(userId);
		in.add(businessDate);		
		out.add(s1);
		out.add(s2);
			
		try
		{
			logger.info("WAIVE_OFF_ALLOCATOR ("+in.toString()+","+out.toString());
			outMessages=(ArrayList) ConnectionDAO.callSP("WAIVE_OFF_ALLOCATOR",in,out);
			s1=CommonFunction.checkNull(outMessages.get(0));
			s2=CommonFunction.checkNull(outMessages.get(1));
	        logger.info("s1  : "+s1);
	        logger.info("s2  : "+s2);	
	    }
		catch (Exception e) 
		{
			ClosureVO vo=new ClosureVO();
			vo.setErrorFlag("E");
			vo.setErrorMsg("Some Error Occur,Contact System Administrator.");
			result.add(vo);
			e.printStackTrace();
			return result;
	    }			
		if(s1.trim().equalsIgnoreCase("E")||s1.trim().equalsIgnoreCase(""))
		{
			ClosureVO vo=new ClosureVO();
			vo.setErrorFlag("E");
			vo.setErrorMsg(s2);
			result.add(vo);
			return result;
		}
		else
		{
			ArrayList searchlist=new ArrayList();
			StringBuilder bufInsSql =	new StringBuilder();
			ArrayList data= null;
			ClosureVO vo= null;
			bufInsSql.append(" SELECT CHARGE_CODE,MIN(CHARGES) CHARGES,SUM(ISNULL(ADVICE_AMOUNT,0))ADVICE_AMOUNT,SUM(ISNULL(ADJUSTED_AMOUNT,0))ADJUSTED_AMOUNT,SUM(ISNULL(AMOUNT_DUE,0))AMOUNT_DUE,SUM(ISNULL(WAIVE_OFF_AMOUNT,0))WAIVE_OFF_AMOUNT,SUM(ISNULL(BALANCE_AMOUNT,0))BALANCE_AMOUNT   " );
			bufInsSql.append(" FROM CR_FC_WAIVEOFF_DTL WHERE TERMINATION_ID='"+CommonFunction.checkNull(terminationId)+"'");
			bufInsSql.append(" GROUP BY CHARGE_CODE ");
			logger.info("In getWaiveOffList() Query  :  "+bufInsSql.toString());
			try
			{
				searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			}
			catch (Exception e) 
			{
				vo=new ClosureVO();
				vo.setErrorFlag("E");
				vo.setErrorMsg("Some Error Occur,Contact System Administrator.");
				result.add(vo);
				e.printStackTrace();
				return result;
			}
			int size = searchlist.size();
            logger.info("size  :   "+size);
            for(int i=0;i<size;i++)
            {
               data=(ArrayList)searchlist.get(i);
               if(data.size()>0)
               {
            	 try
            	 {
            		 vo= new ClosureVO(); 
            		 vo.setErrorFlag("S");
     				 vo.setErrorMsg("");
            	
                	 vo.setChargeCode((CommonFunction.checkNull(data.get(0)).trim()));
                	 vo.setCharges((CommonFunction.checkNull(data.get(1)).trim()));
                	 String advAmt=CommonFunction.checkNull(data.get(2)).trim().toString();
            		 if(advAmt.trim().equalsIgnoreCase(""))
            			 advAmt="0";
            		 String adjstAmt=CommonFunction.checkNull(data.get(3)).trim().toString();
            		 if(adjstAmt.trim().equalsIgnoreCase(""))
            			 adjstAmt="0";
                	 String amtDue=CommonFunction.checkNull(data.get(4)).trim().toString();
            		 if(amtDue.trim().equalsIgnoreCase(""))
            			 amtDue="0";
            		 String waivAmt=CommonFunction.checkNull(data.get(5)).trim().toString();
            		 if(waivAmt.trim().equalsIgnoreCase(""))
            			 waivAmt="0";
            		 String blsAmt=CommonFunction.checkNull(data.get(6)).trim().toString();
            		 if(blsAmt.trim().equalsIgnoreCase(""))
            			 blsAmt="0";
            		 vo.setAdviceAmt(myFormatter.format(myFormatter.parse(advAmt)));
            		 vo.setAdjustedAmt(myFormatter.format(myFormatter.parse(adjstAmt)));
                	 vo.setAmountDue(myFormatter.format(myFormatter.parse(amtDue)));
                	 vo.setWaiveOffAmount(myFormatter.format(myFormatter.parse(waivAmt)));
                	 vo.setBalsAmt(myFormatter.format(myFormatter.parse(blsAmt)));
                	 result.add(vo);
            	 }
            	 catch (Exception e) 
            	 {
            		vo=new ClosureVO();
     				vo.setErrorFlag("E");
     				vo.setErrorMsg("Some Error Occur,Contact System Administrator.");
     				result.add(vo);
     				e.printStackTrace();
     				return result;
     			 }            	
             }
		   }
        }	
	    return result;
	}
// method added by neeraj tripathi
	public ArrayList getTotalList(String terminationId) 
	{
		logger.info("in getWaiveOffList()");
		ArrayList<Object> result =new ArrayList<Object>();
		ArrayList searchlist=new ArrayList();
		StringBuilder bufInsSql =	new StringBuilder();
		ArrayList data= null;
		ClosureVO vo= null;
		bufInsSql.append("select sum(ADVICE_AMOUNT),sum(ADJUSTED_AMOUNT),sum(AMOUNT_DUE),sum(WAIVE_OFF_AMOUNT),sum(BALANCE_AMOUNT) from cr_fc_waiveoff_dtl where TERMINATION_ID='"+terminationId.trim()+"'");
		logger.info("in getTotalList() Query  :  "+bufInsSql.toString());
		try
		{
			searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
		}
		catch (Exception e) 
		{e.printStackTrace();}
		int size = searchlist.size();
        logger.info("size  :   "+size);
        for(int i=0;i<size;i++)
        {
           data=(ArrayList)searchlist.get(i);
           if(data.size()>0)
           {
        	 try
        	 {
        		 vo= new ClosureVO();
        		 String advAmt=CommonFunction.checkNull(data.get(0)).trim().toString();
        		 if(advAmt.trim().equalsIgnoreCase(""))
        			 advAmt="0";
        		 String adjstAmt=CommonFunction.checkNull(data.get(1)).trim().toString();
        		 if(adjstAmt.trim().equalsIgnoreCase(""))
        			 adjstAmt="0";
        		 String amtDue=CommonFunction.checkNull(data.get(2)).trim().toString();
        		 if(amtDue.trim().equalsIgnoreCase(""))
        			 amtDue="0";        		 
        		 String waivAmt=CommonFunction.checkNull(data.get(3)).trim().toString();
        		 if(waivAmt.trim().equalsIgnoreCase(""))
        			 waivAmt="0";
        		 String blsAmt=CommonFunction.checkNull(data.get(4)).trim().toString();
        		 if(blsAmt.trim().equalsIgnoreCase(""))
        			 blsAmt="0";
        		 vo.setTotalAdviceAmount(myFormatter.format(myFormatter.parse(advAmt)));
        		 vo.setTotalAdjustedAmount(myFormatter.format(myFormatter.parse(adjstAmt)));
            	 vo.setTotalAmountDue(myFormatter.format(myFormatter.parse(amtDue)));
            	 vo.setTotalWaiveOffAmount(myFormatter.format(myFormatter.parse(waivAmt)));
            	 vo.setTotalBalsAmt(myFormatter.format(myFormatter.parse(blsAmt)));
      		   	 result.add(vo);
        	 }
        	 catch (Exception e) 
        	 {e.printStackTrace();}        	
          }
	   }
       return result;
    }
	
// method added by neeraj
	public ArrayList saveWaiveOffData(String terminationId,String chargeList, String waiveAmtList,String balAmtList,String recordList,String loanId, String userId,String businessDate,String approvedBY) 
	{
		logger.info("In saveWaiveOffData().....................................Dao Impl"+chargeList);	
		ArrayList result =new ArrayList();
		ArrayList<Object> in =new ArrayList<Object>();
		ArrayList<Object> out =new ArrayList<Object>();
		ArrayList outMessages = new ArrayList();
		businessDate=CommonFunction.changeFormat(businessDate);
		String s1="";
		String s2="";
		in.add(terminationId);
		in.add(chargeList+",");
		in.add(waiveAmtList+",");
		in.add(approvedBY);
		in.add(userId);
		in.add(businessDate);		
		out.add(s1);
		out.add(s2);
			
		try
		{
			logger.info("WAIVE_OFF_CONSOLIDATION_ALLOCATOR ("+in.toString()+","+out.toString());
			outMessages=(ArrayList) ConnectionDAO.callSP("WAIVE_OFF_CONSOLIDATION_ALLOCATOR",in,out);
			s1=CommonFunction.checkNull(outMessages.get(0));
			s2=CommonFunction.checkNull(outMessages.get(1));
	        logger.info("s1  : "+s1);
	        logger.info("s2  : "+s2);	
	    }
		catch (Exception e) 
		{
			ClosureVO vo=new ClosureVO();
			vo.setErrorFlag("E");
			vo.setErrorMsg("Some Error Occur,Contact System Administrator.");
			result.add(vo);
			e.printStackTrace();
			
	    }
		if(CommonFunction.checkNull(s1).equalsIgnoreCase("S"))
		{
			ClosureVO vo=new ClosureVO();
			vo.setErrorFlag("S");
			vo.setErrorMsg("");
			result.add(vo);
		}
		else
		{
			ClosureVO vo=new ClosureVO();
			vo.setErrorFlag("E");
			vo.setErrorMsg("Some Error Occur,Contact System Administrator.");
			result.add(vo);
		
		}
		s1=null;
		s2=null;
		out=null;
		in=null;
		outMessages=null;
		terminationId=null;
		chargeList=null;
		waiveAmtList=null;
		balAmtList=null;
		approvedBY=null;
		userId=null;
		businessDate=null;
		return result;
	}	
	public String getApprovedBY(String loanId,String terminationId) 
	{
		String approvedBY="";
		StringBuilder query = new StringBuilder();
		query.append("  select DISTINCT(B.USER_NAME)");		
		query.append("  from cr_fc_waiveoff_dtl A INNER JOIN  SEC_USER_M B ON A.APPROVED_BY=B.USER_ID where LOAN_ID="+loanId.trim()+" and TERMINATION_ID="+terminationId.trim()+"");
		logger.info("Query for getting approvedBY of early closure  :  "+query.toString());
		try
		{
			approvedBY = ConnectionDAO.singleReturn(CommonFunction.checkNull(query.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return approvedBY;
	}
	
	public String getLbxapprovedBy(String loanId,String terminationId) 
	{
		String approvedBY="";
		StringBuilder query = new StringBuilder();
		query.append("select DISTINCT(APPROVED_BY)");		
		query.append("  from cr_fc_waiveoff_dtl where LOAN_ID="+loanId.trim()+" and TERMINATION_ID="+terminationId.trim()+"");
		logger.info("Query for getting approvedBY of early closure  :  "+query.toString());
		try
		{
			approvedBY = ConnectionDAO.singleReturn(query.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return approvedBY;
	}
	
	public String netReceivablePayableFlag() 
	{
		String flag="";
		StringBuilder query = new StringBuilder();
		query.append("select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='NET_RECEIVABLE_PAYABLE_FLAG'");
		logger.info("Query for getting NET_RECEIVABLE_PAYABLE_FLAG from parameter_mst  : "+query.toString());
		try
		{
			flag = ConnectionDAO.singleReturn(query.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public ArrayList<Object> getClosureTypeList(){
		//dummy implementation
		return null;
	}
	
}