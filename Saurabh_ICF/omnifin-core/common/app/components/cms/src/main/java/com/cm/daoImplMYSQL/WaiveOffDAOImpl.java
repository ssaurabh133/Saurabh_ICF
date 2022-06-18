package com.cm.daoImplMYSQL;

import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.cm.dao.WaiveOffDAO;
import com.cm.vo.PaymentMakerForCMVO;
import com.cm.vo.WaiveOffVO;
import com.cm.vo.waiveOffTempVo;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;

public class WaiveOffDAOImpl implements WaiveOffDAO {
	
	private static final Logger logger = Logger.getLogger(WaiveOffDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));
	
	public ArrayList searchwaiveOff(Object ob)
	{

		StringBuilder Loan_Id = new StringBuilder();
		StringBuilder Loan_No = new StringBuilder();
		StringBuilder Customer_Name = new StringBuilder();
		StringBuilder Bp_id = new StringBuilder();
		StringBuilder Bp_type = new StringBuilder();
		StringBuilder Bp_Name = new StringBuilder();
		StringBuilder ADVICE_AMOUNT = new StringBuilder();
		StringBuilder WAIVE_OFF_AMOUNT = new StringBuilder();
		
//		String Loan_Id = "";
//		String Loan_No = "";
//		String Customer_Name = "";
//		String Bp_id = "";
//		String Bp_type = "";
//		String Bp_Name = "";
//		String ADVICE_AMOUNT = "";
//		String WAIVE_OFF_AMOUNT = "";
//		String userName="";
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;

			
		ArrayList searchlist = new ArrayList();
		WaiveOffVO Vo = (WaiveOffVO) ob;
		ArrayList<WaiveOffVO> detailList = new ArrayList<WaiveOffVO>();

		logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+Vo.getReportingToUserId());
//			try{
//				String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+Vo.getReportingToUserId()+"'";
//				userName=ConnectionDAO.singleReturn(userNameQ);
//				logger.info("userNameQ: "+userNameQ+" userName: "+userName);
//			}
//			catch(Exception e)
//			{
//				e.printStackTrace();
//			}
		
		try {
			logger
					.info("In searchwaiveOff().....................................Dao Impl");

			Loan_Id.append(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(Vo.getLbxLoanNoHID().trim())));
			Loan_No.append(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(Vo.getLoanAccountNo().trim())));
			Customer_Name.append(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(Vo.getCustomerName().trim())));
			Bp_id.append(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(Vo.getLbxBusinessPartnearHID().trim())));
			Bp_type.append(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(Vo.getBusinessPartnerType().trim())));
			Bp_Name.append(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(Vo.getBusinessPartnerName().trim())));
			ADVICE_AMOUNT.append(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(Vo.getAdviceAmount().trim())));
			WAIVE_OFF_AMOUNT.append(StringEscapeUtils.escapeHtml(CommonFunction.checkNull(Vo.getWaivOffAmount().trim())));

			boolean appendSQL = false;
			StringBuffer sbAppendToSQLCount = new StringBuffer();
			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql.append("select distinct a.loan_id,a.LOAN_NO ,b.CUSTOMER_NAME,c.bp_id,c.BP_TYPE,c.BP_NAME,e.ADVICE_AMOUNT,e.WAIVE_OFF_AMOUNT,e.WAIVEOFF_ID, (SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=E.MAKER_ID) MAKER_ID,g.DESCRIPTION"
							+ " from cr_loan_dtl a,gcd_customer_m b,business_partner_view c, generic_master g,cr_waiveoff_dtl e"
							+ " where b.CUSTOMER_ID=a.LOAN_CUSTOMER_ID"
							+ " AND e.LOAN_ID=a.LOAN_ID"
							+ " and c.LOAN_ID=a.loan_id"
							+ " and e.BP_ID = c.BP_ID"
							+ " AND e.REC_STATUS='P' AND e.BP_TYPE = c.BP_TYPE AND g.VALUE=c.BP_TYPE AND A.LOAN_BRANCH='"+Vo.getBranchId()+"' AND E.MAKER_ID='"+Vo.getReportingToUserId()+"'");

			sbAppendToSQLCount.append("select distinct count(1)"
					+ " from cr_loan_dtl a,gcd_customer_m b,business_partner_view c,cr_waiveoff_dtl e"
					+ " where b.CUSTOMER_ID=a.LOAN_CUSTOMER_ID"
					+ " AND e.LOAN_ID=a.LOAN_ID"
					+ " and c.LOAN_ID=a.loan_id"
					+ " and e.BP_ID = c.BP_ID"
					+ " AND e.REC_STATUS='P' AND e.BP_TYPE = c.BP_TYPE AND A.LOAN_BRANCH='"+Vo.getBranchId()+"' AND E.MAKER_ID='"+Vo.getReportingToUserId()+"'");

			
			if ((!((Loan_Id.toString()).equalsIgnoreCase("")))
					&& (!((Loan_No.toString()).equalsIgnoreCase("")))
					&& (!((Customer_Name.toString()).equalsIgnoreCase("")))
					&& (!((Bp_id.toString()).equalsIgnoreCase("")))
					&& (!((Bp_type.toString()).equalsIgnoreCase("")))
					&& (!((Bp_Name.toString()).equalsIgnoreCase("")))
					&& (!((ADVICE_AMOUNT.toString()).equalsIgnoreCase("")))
					&& (!((WAIVE_OFF_AMOUNT.toString()).equalsIgnoreCase("")))) {
				bufInsSql.append(" AND a.Loan_Id=" + Loan_Id
						+ " AND a.Loan_No='" + Loan_No
						+ "' AND b.Customer_Name like'%" + Customer_Name
						+ "%' AND c.Bp_id=" + Bp_id + " AND c.Bp_type like'%"
						+ Bp_type + "%' AND c.Bp_Name ='" + Bp_Name
						+ "' AND e.Advice_Amount=" + ADVICE_AMOUNT
						+ " AND e.Waive_Off_Amount=" + WAIVE_OFF_AMOUNT);
				
				sbAppendToSQLCount.append(" AND a.Loan_Id=" + Loan_Id
						+ " AND a.Loan_No='" + Loan_No
						+ "' AND b.Customer_Name like'%" + Customer_Name
						+ "%' AND c.Bp_id=" + Bp_id + " AND c.Bp_type like'%"
						+ Bp_type + "%' AND c.Bp_Name ='" + Bp_Name
						+ "' AND e.Advice_Amount=" + ADVICE_AMOUNT
						+ " AND e.Waive_Off_Amount=" + WAIVE_OFF_AMOUNT);
			}
			if (!((Loan_Id.toString()).equalsIgnoreCase(""))
					|| !((Customer_Name.toString()).equalsIgnoreCase(""))
					|| !((Bp_id.toString()).equalsIgnoreCase(""))
					|| !((Bp_type.toString()).equalsIgnoreCase(""))
					|| !((Bp_Name.toString()).equalsIgnoreCase(""))
					|| !((ADVICE_AMOUNT.toString()).equalsIgnoreCase(""))
					|| !((WAIVE_OFF_AMOUNT.toString()).equalsIgnoreCase(""))) {
				appendSQL = true;
			}

//			if (appendSQL) {
//				bufInsSql.append(" AND ");
//			}

			if ((!((Loan_Id.toString()).equalsIgnoreCase("")))) {
				bufInsSql.append(" AND a.Loan_Id=" + Loan_Id + " ");
				sbAppendToSQLCount.append(" AND a.Loan_Id=" + Loan_Id + " ");
				appendSQL = true;

			}

			if ((!((Customer_Name.toString()).equalsIgnoreCase("")))) {
				bufInsSql.append("AND b.Customer_Name like'%" + Customer_Name+ "%' ");
				sbAppendToSQLCount.append("AND b.Customer_Name like'%" + Customer_Name+ "%' ");
				appendSQL = true;
			}

			if ((!((Bp_id.toString()).equalsIgnoreCase("")))) {
				bufInsSql.append("AND c.Bp_id=" + Bp_id + " ");
				sbAppendToSQLCount.append("AND c.Bp_id=" + Bp_id + " ");
				appendSQL = true;
			}

			if ((!((Bp_type.toString()).equalsIgnoreCase("")))) {
				bufInsSql.append("AND c.Bp_type like'" + Bp_type + "' ");
				sbAppendToSQLCount.append("AND c.Bp_type like'" + Bp_type + "' ");
				appendSQL = true;
			}

			if ((!((Bp_Name.toString()).equalsIgnoreCase("")))) {
				bufInsSql.append(" AND c.Bp_Name like '%" + Bp_Name + "%'");
				sbAppendToSQLCount.append(" AND c.Bp_Name like '%" + Bp_Name + "%'");
				appendSQL = true;
			}
			if ((!((ADVICE_AMOUNT.toString()).equalsIgnoreCase("")))) {
				bufInsSql.append(" AND e.ADVICE_AMOUNT=" + ADVICE_AMOUNT + " ");
				sbAppendToSQLCount.append(" AND e.ADVICE_AMOUNT=" + ADVICE_AMOUNT + " ");
				appendSQL = true;
			}
			if ((!((WAIVE_OFF_AMOUNT.toString()).equalsIgnoreCase("")))) {
				bufInsSql.append("AND e.WAIVE_OFF_AMOUNT=" + WAIVE_OFF_AMOUNT);
				sbAppendToSQLCount.append("AND e.WAIVE_OFF_AMOUNT=" + WAIVE_OFF_AMOUNT);
				appendSQL = true;
			}
			
			if (appendSQL) {
				String tmp = bufInsSql.toString();
				String tmp1 = sbAppendToSQLCount.toString();
				logger.info("In searchDisbursalData() ## tmp ## " + tmp);
				logger.info("In searchDisbursalData() ## tmp1 ## " + tmp1);
				logger.info("In appendSQL true----  in check index Of tmp"
						+ tmp.lastIndexOf("AND") + "------"
						+ (tmp.length() - 3));
				logger.info("In appendSQL true----  in check index Of tmp1"
						+ tmp1.lastIndexOf("AND") + "------"
						+ (tmp1.length() - 3));
				if (tmp.lastIndexOf("AND") == (tmp.length() - 3) && tmp1.lastIndexOf("AND") == (tmp1.length() - 3)) 
				{
					logger.info("In appendSQL true----  in check index Of");
					tmp = (tmp).substring(0, (tmp.length() - 4)).trim();
					tmp1 = (tmp1).substring(0, (tmp1.length() - 4)).trim();
					logger.info("search Query...tmp. " + tmp);
					searchlist = ConnectionDAO.sqlSelect(tmp);
					count =Integer.parseInt(ConnectionDAO.singleReturn(tmp1.toString()));
				} 
				else 
				{
					logger.info("search Query...tmp." + tmp);
					searchlist = ConnectionDAO.sqlSelect(tmp);
					count =Integer.parseInt(ConnectionDAO.singleReturn(tmp1.toString()));
				}
			} else {
				count =Integer.parseInt(ConnectionDAO.singleReturn(sbAppendToSQLCount.toString()));
				if((Loan_Id==null && Customer_Name==null && Bp_id==null && Bp_type==null & Bp_Name==null && ADVICE_AMOUNT==null && WAIVE_OFF_AMOUNT==null) 
						|| ((Loan_Id.toString()).equalsIgnoreCase("") && (Customer_Name.toString()).equalsIgnoreCase("") && (Bp_id.toString()).equalsIgnoreCase("")
								&& (Bp_type.toString()).equalsIgnoreCase("")&& (Bp_Name.toString()).equalsIgnoreCase("") && (ADVICE_AMOUNT.toString()).equalsIgnoreCase("")
								&& (WAIVE_OFF_AMOUNT.toString()).equalsIgnoreCase(""))
						|| Vo.getCurrentPageLink()>1)
				{
					  logger.info("current PAge Link no .................... "+Vo.getCurrentPageLink());
					  if(Vo.getCurrentPageLink()>1)
					  {
						  startRecordIndex = (Vo.getCurrentPageLink()-1)*no;
						  endRecordIndex = no;
							logger.info("startRecordIndex .................... "+startRecordIndex);
							logger.info("endRecordIndex .................... "+endRecordIndex);
					  }
					  bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
				  }
				logger.info("search Query...else-------." + bufInsSql);
				searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			}
			//bufInsSql.append(" limit 1");
			logger.info("In appendSQL true---- " + appendSQL);


			logger.info("searchWaiveOffData " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
				logger.info("searchWaiveOffDataList "
						+ searchlist.get(i).toString());
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					WaiveOffVO chargeMVo = new WaiveOffVO();
					chargeMVo.setLbxLoanNoHID((CommonFunction.checkNull(
							data.get(0)).trim()));
					
					
					chargeMVo.setLoanAccountNo("<a href=waiveOffMakerAction.do?method=waiveOffMakerCSE&loanId="+(CommonFunction.checkNull(data.get(0))).trim()+"" +
											"&bpType="+(CommonFunction.checkNull(data.get(4)))+"" +
											"&waveOffId="+(CommonFunction.checkNull(data.get(8)))+">"
											+(CommonFunction.checkNull(data.get(1)))+"</a>");

					chargeMVo.setCustomerName((CommonFunction.checkNull(
							data.get(2)).trim()));
					chargeMVo.setLbxBusinessPartnearHID((CommonFunction.checkNull(data.get(3)).trim()));
					chargeMVo.setBusinessPartnerType((CommonFunction.checkNull(
							data.get(4)).trim()));
					chargeMVo.setBusinessPartnerName((CommonFunction.checkNull(
							data.get(5))));
					chargeMVo.setAdviceAmount((CommonFunction.checkNull(
							data.get(6)).trim()));
					
					if(!CommonFunction.checkNull(data.get(7)).trim().equalsIgnoreCase(""))
    	    		{
	    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(data.get(7))).trim());  
	    	    		chargeMVo.setWaivOffAmount(myFormatter.format(reconNum));
    	    		}
					//chargeMVo.setWaivOffAmount((CommonFunction.checkNull(data.get(7)).trim()));
					chargeMVo.setWaveOffId((CommonFunction.checkNull(data.get(8)).trim()));
					chargeMVo.setTotalRecordSize(count);
					chargeMVo.setReportingToUserId((CommonFunction.checkNull(data.get(9)).trim()));
					chargeMVo.setBusinessPartnerDescription((CommonFunction.checkNull(data.get(10)).trim()));
					detailList.add(chargeMVo);
				}

			}

		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		finally
		{
			Loan_Id = null;
			Loan_No = null;
			Customer_Name = null;
			Bp_id = null;
			Bp_type = null; 
			Bp_Name = null;
			ADVICE_AMOUNT = null;
			WAIVE_OFF_AMOUNT = null;
		}

		return detailList;
	
	}
	public ArrayList searchWaiveOffAuthor(Object ob, HttpServletRequest request)
	{

		StringBuilder Loan_Id = new StringBuilder();
		StringBuilder Loan_No = new StringBuilder();
		StringBuilder Customer_Name = new StringBuilder();
		StringBuilder Bp_id = new StringBuilder();
		StringBuilder Bp_type = new StringBuilder();
		StringBuilder Bp_Name = new StringBuilder();
		StringBuilder ADVICE_AMOUNT = new StringBuilder();
		StringBuilder WAIVE_OFF_AMOUNT = new StringBuilder();

				
//		String Loan_Id = "";
//		String Loan_No = "";
//		String Customer_Name = "";
//		String Bp_id = "";
//		String Bp_type = "";
//		String Bp_Name = "";
//		String ADVICE_AMOUNT = "";
//		String WAIVE_OFF_AMOUNT = "";
//		String userName="";
		int count=0;
		int startRecordIndex=0;
		int endRecordIndex = no;

		ArrayList searchlist = new ArrayList();
		WaiveOffVO Vo = (WaiveOffVO) ob;
		ArrayList<WaiveOffVO> detailList = new ArrayList<WaiveOffVO>();

		logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+Vo.getReportingToUserId());
//			try{
//				String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+Vo.getReportingToUserId()+"'";
//				userName=ConnectionDAO.singleReturn(userNameQ);
//				logger.info("userNameQ: "+userNameQ+" userName: "+userName);
//			}
//			catch(Exception e)
//			{
//				e.printStackTrace();
//			}
		
		try {
			logger
					.info("In searchWaiveOffAuthor().....................................Dao Impl");

			Loan_Id.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxLoanNoHID()).trim()));
			Loan_No.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLoanAccountNo()).trim()));
			Customer_Name.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getCustomerName()).trim()));
			Bp_id.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getLbxBusinessPartnearHID()).trim()));
			Bp_type.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getBusinessPartnerType()).trim()));
			Bp_Name.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getBusinessPartnerName()).trim()));
			ADVICE_AMOUNT.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getAdviceAmount()).trim()));
			WAIVE_OFF_AMOUNT.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(Vo.getWaivOffAmount()).trim()));
			boolean appendSQL = false;
			StringBuffer sbAppendToSQLCount = new StringBuffer();
			StringBuffer bufInsSql = new StringBuffer();
			bufInsSql.append("select distinct a.loan_id,a.LOAN_NO ,b.CUSTOMER_NAME,c.bp_id,c.BP_TYPE,c.BP_NAME,e.ADVICE_AMOUNT,e.WAIVE_OFF_AMOUNT,e.WAIVEOFF_ID, (SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=E.MAKER_ID) MAKER_ID,g.DESCRIPTION"
							+ " from cr_loan_dtl a,gcd_customer_m b,business_partner_view c, generic_master g,cr_waiveoff_dtl e"
							+ " where b.CUSTOMER_ID=a.LOAN_CUSTOMER_ID"
							+ " AND e.LOAN_ID=a.LOAN_ID"
							+ " AND c.BP_ID = E.BP_ID AND C.BP_TYPE= E.BP_TYPE AND g.VALUE=c.BP_TYPE"
							+ " AND e.REC_STATUS='F' AND A.LOAN_BRANCH='"+Vo.getBranchId()+"' and E.MAKER_ID!='"+Vo.getUserId()+"'");
			
			sbAppendToSQLCount.append("select distinct count(1) from (select distinct a.loan_id,a.LOAN_NO ,b.CUSTOMER_NAME,c.bp_id,c.BP_TYPE,c.BP_NAME,e.ADVICE_AMOUNT,e.WAIVE_OFF_AMOUNT,e.WAIVEOFF_ID, (SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=E.MAKER_ID) MAKER_ID"
					+ " from cr_loan_dtl a,gcd_customer_m b,business_partner_view c,cr_waiveoff_dtl e"
					+ " where b.CUSTOMER_ID=a.LOAN_CUSTOMER_ID"
					+ " AND e.LOAN_ID=a.LOAN_ID"
					+ " AND c.BP_ID = E.BP_ID AND C.BP_TYPE= E.BP_TYPE"
					+ " AND e.REC_STATUS='F' AND A.LOAN_BRANCH='"+Vo.getBranchId()+"' and E.MAKER_ID!='"+Vo.getUserId()+"'");


			if ((!((Loan_Id.toString()).equalsIgnoreCase("")))
					&& (!((Loan_No.toString()).equalsIgnoreCase("")))
					&& (!((Customer_Name.toString()).equalsIgnoreCase("")))
					&& (!((Bp_id.toString()).equalsIgnoreCase("")))
					&& (!((Bp_type.toString()).equalsIgnoreCase("")))
					&& (!((Bp_Name.toString()).equalsIgnoreCase("")))
					&& (!((ADVICE_AMOUNT.toString()).equalsIgnoreCase("")))
					&& (!((WAIVE_OFF_AMOUNT.toString()).equalsIgnoreCase("")))) {
				bufInsSql.append(" AND  a.Loan_Id=" + Loan_Id
						+ " AND a.Loan_No='" + Loan_No
						+ "' AND b.Customer_Name like'%" + Customer_Name
						+ "%' AND c.Bp_id=" + Bp_id + " AND c.Bp_type='"
						+ Bp_type + "' AND c.Bp_Name like'%" + Bp_Name
						+ "%' AND e.Advice_Amount=" + ADVICE_AMOUNT
						+ " AND e.Waive_Off_Amount=" + WAIVE_OFF_AMOUNT);
				sbAppendToSQLCount.append(" AND  a.Loan_Id=" + Loan_Id
						+ " AND a.Loan_No='" + Loan_No
						+ "' AND b.Customer_Name like'%" + Customer_Name
						+ "%' AND c.Bp_id=" + Bp_id + " AND c.Bp_type='"
						+ Bp_type + "' AND c.Bp_Name like'%" + Bp_Name
						+ "%' AND e.Advice_Amount=" + ADVICE_AMOUNT
						+ " AND e.Waive_Off_Amount=" + WAIVE_OFF_AMOUNT);
			}
			if (!((Loan_Id.toString()).equalsIgnoreCase(""))
					|| !((Loan_No.toString()).equalsIgnoreCase(""))
					|| !((Customer_Name.toString()).equalsIgnoreCase(""))
					|| !((Bp_id.toString()).equalsIgnoreCase(""))
					|| !((Bp_type.toString()).equalsIgnoreCase(""))
					|| !((Bp_Name.toString()).equalsIgnoreCase(""))
					|| !((ADVICE_AMOUNT.toString()).equalsIgnoreCase(""))
					|| !((WAIVE_OFF_AMOUNT.toString()).equalsIgnoreCase(""))
					|| !((Vo.getLbxUserId()).equalsIgnoreCase(""))) {
				appendSQL = true;
			}

//			if (appendSQL) {
//				bufInsSql.append("  ");
//			}

			if ((!((Loan_Id.toString()).equalsIgnoreCase("")))) {
				bufInsSql.append(" AND a.Loan_Id=" + Loan_Id + " ");
				sbAppendToSQLCount.append(" AND a.Loan_Id=" + Loan_Id + " ");
				appendSQL = true;

			}

			if ((!((Loan_No.toString()).equalsIgnoreCase("")))) {
				bufInsSql.append("AND a.Loan_No='" + Loan_No + "' ");
				sbAppendToSQLCount.append("AND a.Loan_No='" + Loan_No + "' ");
				appendSQL = true;
			}

			if ((!((Customer_Name.toString()).equalsIgnoreCase("")))) {
				bufInsSql.append("AND b.Customer_Name like '%" + Customer_Name+ "%' ");
				sbAppendToSQLCount.append("AND b.Customer_Name like '%" + Customer_Name+ "%' ");
				appendSQL = true;
			}

			if ((!((Bp_id.toString()).equalsIgnoreCase("")))) {
				bufInsSql.append("AND c.Bp_id=" + Bp_id + " ");
				sbAppendToSQLCount.append("AND c.Bp_id=" + Bp_id + " ");
				appendSQL = true;
			}

			if ((!((Bp_type.toString()).equalsIgnoreCase("")))) {
				bufInsSql.append("AND c.Bp_type like '%" + Bp_type + "%' ");
				sbAppendToSQLCount.append("AND c.Bp_type like '%" + Bp_type + "%' ");
				appendSQL = true;
			}

			if ((!((Bp_Name.toString()).equalsIgnoreCase("")))) {
				bufInsSql.append("AND c.Bp_Name like '%" + Bp_Name + "%' ");
				sbAppendToSQLCount.append("AND c.Bp_Name like '%" + Bp_Name + "%' ");
				appendSQL = true;
			}
			if ((!((ADVICE_AMOUNT.toString()).equalsIgnoreCase("")))) {
				bufInsSql.append("AND e.ADVICE_AMOUNT=" + ADVICE_AMOUNT + " ");
				sbAppendToSQLCount.append("AND e.ADVICE_AMOUNT=" + ADVICE_AMOUNT + " ");
				appendSQL = true;
			}
			if ((!((WAIVE_OFF_AMOUNT.toString()).equalsIgnoreCase("")))) {
				bufInsSql.append("AND e.WAIVE_OFF_AMOUNT=" + WAIVE_OFF_AMOUNT);
				sbAppendToSQLCount.append("AND e.WAIVE_OFF_AMOUNT=" + WAIVE_OFF_AMOUNT);
				appendSQL = true;
			}
			if((!(CommonFunction.checkNull(Vo.getLbxUserId()).equalsIgnoreCase("")))) {
				bufInsSql.append(" AND E.MAKER_ID='"+StringEscapeUtils.escapeSql(Vo.getLbxUserId()).trim()+"' ");	
	      		sbAppendToSQLCount.append(" AND E.MAKER_ID='"+StringEscapeUtils.escapeSql(Vo.getLbxUserId()).trim()+"'");
				appendSQL = true;
		      }
			//bufInsSql.append("  limit 1");
			appendSQL = true;
			sbAppendToSQLCount.append(")as b");
			logger.info("In appendSQL true---- " + appendSQL);

			if (appendSQL) {
				String tmp = bufInsSql.toString();
				String tmp1 = sbAppendToSQLCount.toString();
				logger.info("In searchDisbursalData() ## tmp ## " + tmp);
				logger.info("In searchDisbursalData() ## tmp1 ## " + tmp1);
				logger.info("In appendSQL true----  in check index Of tmp"
						+ tmp.lastIndexOf("AND") + "------"
						+ (tmp.length() - 3));
				logger.info("In appendSQL true----  in check index Of tmp1"
						+ tmp1.lastIndexOf("AND") + "------"
						+ (tmp1.length() - 3));
				if (tmp.lastIndexOf("AND") == (tmp.length() - 3) && tmp1.lastIndexOf("AND") == (tmp1.length() - 3)) 
				{
					logger.info("In appendSQL true----  in check index Of");
					tmp = (tmp).substring(0, (tmp.length() - 4)).trim();
					tmp1 = (tmp1).substring(0, (tmp1.length() - 4)).trim();
					logger.info("search Query...tmp. " + tmp);
					searchlist = ConnectionDAO.sqlSelect(tmp);
					count =Integer.parseInt(ConnectionDAO.singleReturn(tmp1.toString()));
				} 
				else 
				{
					logger.info("search Query...tmp." + tmp);
					searchlist = ConnectionDAO.sqlSelect(tmp);
					count =Integer.parseInt(ConnectionDAO.singleReturn(tmp1.toString()));
				}
			} else {
				count =Integer.parseInt(ConnectionDAO.singleReturn(sbAppendToSQLCount.toString()));
				if((Loan_Id==null && Loan_No==null && Customer_Name==null && Bp_id==null && Bp_type==null & Bp_Name==null && ADVICE_AMOUNT==null && WAIVE_OFF_AMOUNT==null) 
						|| ((Loan_Id.toString()).equalsIgnoreCase("") && ((Loan_No.toString()).equalsIgnoreCase("")) && (Customer_Name.toString()).equalsIgnoreCase("") && (Bp_id.toString()).equalsIgnoreCase("")
								&& (Bp_type.toString()).equalsIgnoreCase("")&& (Bp_Name.toString()).equalsIgnoreCase("") && (ADVICE_AMOUNT.toString()).equalsIgnoreCase("")
								&& (WAIVE_OFF_AMOUNT.toString()).equalsIgnoreCase(""))
						|| Vo.getCurrentPageLink()>1)
				{
					  logger.info("current PAge Link no .................... "+Vo.getCurrentPageLink());
					  if(Vo.getCurrentPageLink()>1)
					  {
						  startRecordIndex = (Vo.getCurrentPageLink()-1)*no;
						  endRecordIndex = no;
							logger.info("startRecordIndex .................... "+startRecordIndex);
							logger.info("endRecordIndex .................... "+endRecordIndex);
					  }
					  bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
				  }
				logger.info("search Query...else-------." + bufInsSql);
				searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			}
			logger.info("searchWaiveOffAuthor " + searchlist.size());
			for (int i = 0; i < searchlist.size(); i++) {
				logger.info("searchWaiveOffAuthorDataList "
						+ searchlist.get(i).toString());
				ArrayList data = (ArrayList) searchlist.get(i);
				if (data.size() > 0) {
					
					StringBuilder loanid = new StringBuilder();
					StringBuilder bpid = new StringBuilder();
					StringBuilder waiveoffAmount = new StringBuilder();
					StringBuilder AdviceAmount = new StringBuilder();
					
					WaiveOffVO chargeMVo = new WaiveOffVO();
					chargeMVo.setLbxLoanNoHID((CommonFunction.checkNull(
							data.get(0)).trim()));
						
					chargeMVo.setLoanAccountNo("<a href=waiveOffAuthorAction.do?method=waiveOffAuthorFrame&loanId="
			  					+ (CommonFunction.checkNull(data.get(0)).trim())
			  					+ "&bpid="+(CommonFunction.checkNull(data.get(3)).trim())
			  					+ "&waveOffId="+(CommonFunction.checkNull(data.get(8)).trim())+">"
			  					+ (CommonFunction.checkNull(data.get(1)).trim()) + "</a>");

					chargeMVo.setCustomerName((CommonFunction.checkNull(
							data.get(2)).trim()));
					chargeMVo.setLbxBusinessPartnearHID((CommonFunction.checkNull(data.get(3)).trim()));
					chargeMVo.setBusinessPartnerType((CommonFunction.checkNull(
							data.get(4)).trim()));
					chargeMVo.setBusinessPartnerName((CommonFunction.checkNull(
							data.get(5)).trim()));
					chargeMVo.setAdviceAmount((CommonFunction.checkNull(
							data.get(6)).trim()));
					
					if(!CommonFunction.checkNull(data.get(7)).trim().equalsIgnoreCase(""))
    	    		{
	    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(data.get(7))).trim());  
	    	    		chargeMVo.setWaivOffAmount(myFormatter.format(reconNum));
    	    		}
					//chargeMVo.setWaivOffAmount((CommonFunction.checkNull(data.get(7)).trim()));
					
					chargeMVo.setWaveOffId((CommonFunction.checkNull(data.get(8)).trim()));
					chargeMVo.setTotalRecordSize(count);
					chargeMVo.setReportingToUserId((CommonFunction.checkNull(data.get(9)).trim()));
					chargeMVo.setBusinessPartnerDescription((CommonFunction.checkNull(data.get(10)).trim()));
					HttpSession session = request.getSession();
					loanid.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chargeMVo.getLbxLoanNoHID()).trim()));
					bpid.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chargeMVo.getLbxBusinessPartnearHID()).trim()));
					
					waiveoffAmount.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chargeMVo.getWaivOffAmount()).trim()));
					AdviceAmount.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(chargeMVo.getAdviceAmount()).trim()));
					session.setAttribute("SearchData", loanid + "," + bpid
							+ "," + waiveoffAmount + "," + AdviceAmount);

					detailList.add(chargeMVo);
					loanid = null;
					bpid = null;
					waiveoffAmount = null;
					AdviceAmount = null;
				}

			}

		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		finally
		{
			Loan_Id = null;
			Loan_No = null;
			Customer_Name = null;
			Bp_id = null;
			Bp_type = null; 
			Bp_Name = null;
			ADVICE_AMOUNT = null;
			WAIVE_OFF_AMOUNT = null;
		}
		logger.info("list size in dao impl ..................... "+detailList.size());
		return detailList;
	
	}
	
	public String saveWaiveOffAuthor(Object ob)
	{
		
		String status = "";
//		CallableStatement cst = null;
		int statusProc = 0;
//		Connection con=null;
		int waiveOffId =0;
		WaiveOffVO vo = (WaiveOffVO) ob;
//		con = ConnectionDAO.getConnection();
   	    ArrayList<Object> in =new ArrayList<Object>();
   		ArrayList<Object> out =new ArrayList<Object>();
   		ArrayList outMessages = new ArrayList();
   		StringBuilder s1 = new StringBuilder();
   		StringBuilder s2 = new StringBuilder();
  		StringBuilder date = new StringBuilder();
//   		 String s1="";
//   		String s2="";
		try {
		
//			con.setAutoCommit(false);
			waiveOffId=Integer.parseInt(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getWaveOffId()).trim()));
							
				in.add(Integer.parseInt((CommonFunction.checkNull(vo.getCompanyId()).trim())));
				in.add(waiveOffId );
				date.append(CommonFunction.changeFormat(CommonFunction.checkNull(vo.getAuthorDate()).trim()));
				  if(date.toString() != null)
				  in.add(date.toString());

//				cst.setString(3,(CommonFunction.checkNull(vo.getAuthorDate()).trim()));
			      in.add((CommonFunction.checkNull(vo.getAuthorID()).trim()));
			      in.add((CommonFunction.checkNull(vo.getDecision()).trim()));
			      in.add(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getRemarks()).trim()));
//				cst.registerOutParameter(7, Types.CHAR);
//				cst.registerOutParameter(8, Types.CHAR);
//				
//				statusProc = cst.executeUpdate();
//				logger.info("Status Proc: "+statusProc);
//				String s1 = cst.getString(7);
//				String s2 = cst.getString(8);
				out.add(s1.toString());
				 out.add(s2.toString());

				outMessages=(ArrayList) ConnectionDAO.callSP("Waive_Off_Author",in,out);
				s1.append(CommonFunction.checkNull(outMessages.get(0)));
				s2.append(CommonFunction.checkNull(outMessages.get(1)));
				logger.info("s1: " + s1);
				logger.info("s2: " + s2);
				
				if((s1.toString()).equalsIgnoreCase("S")){
					logger.info("After Proc inside If ");
					status="S";
//					con.commit();
				}else
				{
					
					status=(s2.toString()).toString();
//					con.rollback();
				}
				
		} catch (Exception e) {

			
			logger.info(e.getMessage());
		}
		finally
		{
			s1 = null;
			s2 = null;
			date = null;
			
		}
		
		logger.info("value of status ............................... "+status);
		return status;

	}
	public ArrayList selectWaiveOffAuthorData(String loanId, String bpid,String waveOffId)
	{

		ArrayList WaiveOffData = new ArrayList();
		StringBuilder query = new StringBuilder();
		logger.info("In selectWaiveOffData Loan Id : " + loanId);
		logger.info("In selectWaiveOffData BpId Id : " + bpid);
		try {

			ArrayList mainlist = new ArrayList();
			ArrayList subList = new ArrayList();
			
			query.append("select distinct a.LOAN_NO ,b.CUSTOMER_NAME,c.BP_TYPE,c.BP_NAME,d.CHARGE_CODE,d.CHARGE_DESC,f.ADVICE_AMOUNT," +
					"e.WAIVE_OFF_AMOUNT,e.REMARKS,e.WAIVEOFF_ID,f.CHARGE_AMOUNT,f.TAX_AMOUNT1,f.TAX_AMOUNT2,f.TXN_ADJUSTED_AMOUNT," +
					"e.WAIVE_OFF_TAX_AMOUNT1,e.WAIVE_OFF_TAX_AMOUNT2,e.MAKER_REMARKS,g.DESCRIPTION,f.amount_in_process,a.REC_STATUS,date_format(e.WAIVEOFF_DATE,'"+dateFormat+"') "
				+ " ,APPROVED_BY from cr_loan_dtl a,gcd_customer_m b,business_partner_view c,generic_master g,com_charge_code_m d,cr_waiveoff_dtl e,cr_txnadvice_dtl f "
				+ " where b.CUSTOMER_ID=a.LOAN_CUSTOMER_ID"
				+ " and d.CHARGE_CODE=e.CHARGE_CODE_ID"
				+ " and c.LOAN_ID=a.LOAN_ID and e.LOAN_ID=a.LOAN_ID and g.GENERIC_KEY='BPTYPE' and g.VALUE=c.BP_TYPE"
				+ " and c.BP_ID="+bpid+" and a.LOAN_ID="+loanId+" and WAIVEOFF_ID="+waveOffId+" " +
				" and e.TXN_ADVICE_ID=f.TXNADVICE_ID and e.bp_type=c.bp_type and e.loan_id=f.loan_id");

			
		logger.info("In selectWaiveOffData ****************************** " + query.toString());
		mainlist = ConnectionDAO.sqlSelect(query.toString());
		logger.info("In selectWaiveOffData.....mainlist size: "
				+ mainlist.size());
		for (int i = 0; i < mainlist.size(); i++) {

			subList = (ArrayList) mainlist.get(i);
			logger.info("In selectWaiveOffData......sublist size: "
					+ subList.size());
			if (subList.size() > 0) {
				
				WaiveOffVO vo = new WaiveOffVO();
				StringBuilder AdviceAmount = new StringBuilder();
				StringBuilder AdjustedAmount = new StringBuilder();
				StringBuilder AmountinProcess = new StringBuilder();
				StringBuilder newAdvAmt = new StringBuilder();
				
				//logger.info("setLoanAccountNo ...... "+(String) subList.get(0));
				vo.setLoanAccountNo((CommonFunction.checkNull( subList.get(0)).trim()));
				vo.setCustomerName((CommonFunction.checkNull(subList.get(1)).trim()));
				
				vo.setBusinessPartnerType((CommonFunction.checkNull(subList.get(2)).trim()));
			
				vo.setBusinessPartnerName((CommonFunction.checkNull(subList.get(3)).trim()));
			
				vo.setChargeType((CommonFunction.checkNull(subList.get(4)).trim()));
				
				vo.setChargeDescription((CommonFunction.checkNull( subList.get(5)).trim()));
				
				vo.setAdviceAmount(myFormatter.parse((CommonFunction.checkNull( subList.get(6)).trim())).toString());
				
				AdviceAmount.append(CommonFunction.checkNull(subList.get(6)).trim());
				
				AmountinProcess.append(CommonFunction.checkNull(subList.get(18)).trim());

				AdjustedAmount.append(CommonFunction.checkNull(subList.get(13)).trim());
				
				double Am_inprocess;

				Am_inprocess = Double.parseDouble(AmountinProcess.toString());
				
				Double AdviceAmt = Double.parseDouble(AdviceAmount.toString());
				Double AdjustedAmt = Double.parseDouble(AdjustedAmount.toString());
				
				
				//vo.setBalanceAmount(myFormatter.parse(CommonFunction.checkNull(BalanceAmount.toString())).toString());
				vo.setWaivOffAmount(myFormatter.parse((CommonFunction.checkNull(subList.get(7)).trim())).toString());
				vo.setAuthorRemarks((CommonFunction.checkNull(subList.get(8)).trim()));
				vo.setWaveOffId((CommonFunction.checkNull(subList.get(9)).trim()));
				vo.setChargeAmount(myFormatter.parse((CommonFunction.checkNull(subList.get(10)).trim())).toString());
				vo.setTaxAmount1(myFormatter.parse((CommonFunction.checkNull(subList.get(11)).trim())).toString());
				vo.setTaxAmount2(myFormatter.parse((CommonFunction.checkNull(subList.get(12)).trim())).toString());
				vo.setTxnAdjustedAmount(myFormatter.parse((CommonFunction.checkNull(subList.get(13)).trim())).toString());
				vo.setWaveAmountForTaxAmt1(myFormatter.parse((CommonFunction.checkNull(subList.get(14)).trim())).toString());
				vo.setWaveAmountForTaxAmt2(myFormatter.parse((CommonFunction.checkNull(subList.get(15)).trim())).toString());
				vo.setRemarks((CommonFunction.checkNull(subList.get(16)).trim()));
				vo.setBusinessPartnerDescription((CommonFunction.checkNull(subList.get(17)).trim()));
				double a =0;
				double b = 0;
				double c = 0;
				logger.info("waive off amount ))))))))))))) "+subList.get(7).toString());
				if(subList.get(7).toString().equalsIgnoreCase("") || subList.get(7).toString()==null)
				{
					a=0;
				}
				else
				{
					a = Double.parseDouble(subList.get(7).toString());
				}
				if(subList.get(14).toString().equalsIgnoreCase("") || subList.get(14).toString()==null)
				{
					b=0;
				}
				else
				{
					b = Double.parseDouble(subList.get(14).toString());
				}
				if(subList.get(15).toString().equalsIgnoreCase("") || subList.get(15).toString()==null)
				{
					c=0;
				}
				else
				{
					c = Double.parseDouble(subList.get(15).toString());
				}
				
				logger.info("setWaivOffAmount ............................... "+a);
				logger.info("setWaveAmountForTaxAmt1 ............................... "+b);
				logger.info("setWaveAmountForTaxAmt1 ............................... "+c);
				
				double tot = a+b+c;
				double amtInps = Am_inprocess - tot;
				double bam = AdviceAmt - AdjustedAmt - amtInps;
				Double BalanceAmount = new Double(Math.abs(bam));
			
				if(!CommonFunction.checkNull(BalanceAmount.toString()).equalsIgnoreCase(""))
	    		{
				Number reconNum =myFormatter.parse(BalanceAmount.toString());
				vo.setBalanceAmount(myFormatter.format(reconNum));
	    		}
				
				logger.info("total wave off ....................... "+tot);
				vo.setTotalWaiveOffAmt(myFormatter.parse(CommonFunction.checkNull(tot+"")).toString());
				

				vo.setNewChargeAmt(myFormatter.parse((Double.parseDouble(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getChargeAmount()).trim()))-Double.parseDouble(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getWaivOffAmount()).trim())))+"").toString());
				vo.setNewTaxAmt1(myFormatter.parse((Double.parseDouble(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getTaxAmount1()).trim()))-Double.parseDouble(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getWaveAmountForTaxAmt1()).trim())))+"").toString());
				vo.setNewTaxAmt2(myFormatter.parse((Double.parseDouble(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getTaxAmount2()).trim()))-Double.parseDouble(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getWaveAmountForTaxAmt2()).trim())))+"").toString());
				vo.setNewAdviceAmt(myFormatter.parse((Double.parseDouble(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAdviceAmount()).trim()))-Double.parseDouble(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getTotalWaiveOffAmt()).trim())))+"").toString());
				
				newAdvAmt.append(CommonFunction.checkNull(vo.getNewAdviceAmt()).trim());
				double newAdAmt;

				newAdAmt = Double.parseDouble(newAdvAmt.toString());
				double newbalamt = newAdAmt - AdjustedAmt - amtInps;
				Double newBalAmount = new Double(Math.abs(newbalamt));
				
				if(!CommonFunction.checkNull(newBalAmount.toString()).equalsIgnoreCase(""))
	    		{
				Number reconNum =myFormatter.parse(newBalAmount.toString());
				vo.setNewBalanceAmount(myFormatter.format(reconNum));
	    		}
				
				
				logger.info("setAmountInProcess ******************. "+CommonFunction.checkNull(amtInps+""));
				
				if(!CommonFunction.checkNull(amtInps+"").equalsIgnoreCase(""))
	    		{
				Number reconNum =myFormatter.parse(amtInps+"");
				vo.setAmountInProcess(myFormatter.format(reconNum));
	    		}
				
				//vo.setAmountInProcess(myFormatter.parse(CommonFunction.checkNull(amtInps+"")).toString());
				
				
//				vo.setNewChargeAmt((Double.parseDouble(vo.getChargeAmount())-Double.parseDouble(vo.getWaivOffAmount()))+"");
//				vo.setNewTaxAmt1((Double.parseDouble(vo.getTaxAmount1())-Double.parseDouble(vo.getWaveAmountForTaxAmt1()))+"");
//				vo.setNewTaxAmt2((Double.parseDouble(vo.getTaxAmount2())-Double.parseDouble(vo.getWaveAmountForTaxAmt2()))+"");
//				vo.setNewAdviceAmt((Double.parseDouble(vo.getAdviceAmount())-Double.parseDouble(vo.getTotalWaiveOffAmt()))+"");
				
				vo.setLoanRecStatus((CommonFunction.checkNull(subList.get(19)).trim()));
				vo.setValueDate((CommonFunction.checkNull(subList.get(20)).trim()));
				vo.setLbxapprovedBy((CommonFunction.checkNull(subList.get(21)).trim()));
				WaiveOffData.add(vo);
				AdviceAmount = null;
				AdjustedAmount = null;
				AmountinProcess=null;
			}
			}

		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		finally
		{
			query = null;

		}
		return WaiveOffData;

	
	}
	
	public ArrayList saveWaiveOffMaker(Object ob, HttpServletRequest request)
	{

		HttpSession sess = request.getSession();
		String flag1 = (String) sess.getAttribute("flag");
		String str="";
		flag1 = CommonFunction.checkNull(flag1);
		WaiveOffVO vo = (WaiveOffVO) ob;
		 ArrayList txnDetailList=new ArrayList();
		 ArrayList temp = new ArrayList();
		 ArrayList temp1 = new ArrayList();
		 ArrayList temp2 = new ArrayList();
		 ArrayList data = new ArrayList();
		 StringBuilder query = new StringBuilder();
		 StringBuilder tempCount = new StringBuilder();
		 StringBuilder maxId = new StringBuilder();
		 StringBuilder query3 = new StringBuilder();
		char flag = 'A';
		boolean status;
		waiveOffTempVo tempVo = new waiveOffTempVo();
		logger
				.info("In saveWaiveOffMaker.....................................Dao Impl....111");
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();

		query.append("select count(charge_code_id) from cr_waiveoff_dtl where charge_code_id='"+ vo.getLbxChargeCodeHID() + "' and LOAN_ID='"+vo.getLbxLoanNoHID()+"' and BP_TYPE='"+vo.getBusinessPartnerType()+"' and TXN_ADVICE_ID='"+vo.getTxnAdviceId()+"'  and REC_STATUS<>'X' and REC_STATUS<>'A'");
		logger.info("In saveWaiveOffMaker...........count query: " + query);
		tempCount.append(ConnectionDAO.singleReturn(query.toString()));
		int count = Integer.parseInt(tempCount.toString());
		logger.info("new new .......................................... "+vo.getN());
		logger.info("Count of rows from select query= " + count);
		if (count > 0 && ( vo.getN()!=null && vo.getN().equalsIgnoreCase("new")))
		{
			str="N";
			temp.add("N");
			  temp.add("");
		}
		else if (count <= 0)
		{
			
			try {
				logger.info("In insert cr_waiveoff_dtl");
				StringBuffer bufInsSql = new StringBuffer();
				StringBuffer bufInsSql1 = new StringBuffer();
				bufInsSql1.append("SELECT DEAL_ID,TXN_TYPE,TXN_ID,ADVICE_DATE,ADVICE_TYPE," +
						"TAX_RATE1,TAX_RATE2,TDS_RATE,ORG_CHARGE_AMOUNT,ORG_TAX_AMOUNT1,ORG_TAX_AMOUNT2,ORG_TDS_AMOUNT,ORG_ADVICE_AMOUNT," +
						"CHARGE_AMOUNT,TAX_AMOUNT1,TAX_AMOUNT2,TDS_AMOUNT,ADVICE_AMOUNT,TXN_ADJUSTED_AMOUNT,WAIVE_OFF_AMOUNT" +
						" From cr_txnadvice_dtl where TXNADVICE_ID="+vo.getTxnAdviceId()); 	
				logger.info("query :::: "+ bufInsSql1.toString());
				 txnDetailList = ConnectionDAO.sqlSelect(bufInsSql1.toString());
				  for(int i=0;i<txnDetailList.size();i++)
				  {
				       data=(ArrayList)txnDetailList.get(i);
				  }					
				bufInsSql.append("insert into cr_waiveoff_dtl(WAIVEOFF_DATE,TXN_ADVICE_ID,DEAL_ID,LOAN_ID,TXN_TYPE,TXN_ID,BP_TYPE,BP_ID," +
						"ADVICE_DATE,ADVICE_TYPE,CHARGE_CODE_ID,TAX_RATE1,TAX_RATE2,TDS_RATE,ORG_CHARGE_AMOUNT,ORG_TAX_AMOUNT1,ORG_TAX_AMOUNT2," +
						"ORG_TDS_AMOUNT,ORG_ADVICE_AMOUNT,CHARGE_AMOUNT,TAX_AMOUNT1,TAX_AMOUNT2,TDS_AMOUNT,ADVICE_AMOUNT,TXN_ADJUSTED_AMOUNT," +
						"WAIVE_OFF_AMOUNT,WAIVE_OFF_TAX_AMOUNT1,WAIVE_OFF_TAX_AMOUNT2,MAKER_REMARKS,REC_STATUS,MAKER_ID,MAKER_DATE,APPROVED_BY)");
				
				bufInsSql.append(" values ( ");
				bufInsSql.append(" STR_TO_DATE(?,'"+dateFormat+"'),");
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
				bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)  ");
				bufInsSql.append(" ,?");
				bufInsSql.append(" )");
				
				if (CommonFunction.checkNull(vo.getValueDate()) //WAIVEOFF_DATE
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getValueDate()).trim());


				if (CommonFunction.checkNull(vo.getTxnAdviceId()) //TXN_ADVICE_ID
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getTxnAdviceId()).trim());
	
				if (CommonFunction.checkNull((CommonFunction.checkNull(data.get(0)).trim())).equalsIgnoreCase(""))//DEAL_ID DEAL_ID
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(data.get(0)).trim()));
				
				if (CommonFunction.checkNull(vo.getLbxLoanNoHID())//LOAN_ID
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getLbxLoanNoHID()).trim());

				if (CommonFunction.checkNull((CommonFunction.checkNull(data.get(1)).trim())).equalsIgnoreCase(""))//,TXN_TYPE ,TXN_TYPE,
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(data.get(1)).trim()));
									
				if (CommonFunction.checkNull((CommonFunction.checkNull(data.get(2)).trim())).equalsIgnoreCase(""))//,TXN_ID,TXN_ID
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(data.get(2)).trim()));
				
				if (CommonFunction.checkNull(vo.getBusinessPartnerType())//BP_TYPE
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getBusinessPartnerType()).trim());
				if (CommonFunction.checkNull(vo.getLbxBusinessPartnearHID())//,BP_ID
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getLbxBusinessPartnearHID()).trim());
			
				if (CommonFunction.checkNull((CommonFunction.checkNull(data.get(3)).trim())).equalsIgnoreCase(""))//ADVICE_DATE ,ADVICE_DATE,
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(data.get(3)).trim()));
				
				if (CommonFunction.checkNull((CommonFunction.checkNull(data.get(4)).trim())).equalsIgnoreCase(""))//,ADVICE_TYPE ADVICE_TYPE
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(data.get(4)).trim()));
				if (CommonFunction.checkNull(vo.getLbxChargeCodeHID()) //,CHARGE_CODE_ID 
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((vo.getLbxChargeCodeHID()).trim());
		
				if (CommonFunction.checkNull((CommonFunction.checkNull(data.get(5)).trim())).equalsIgnoreCase(""))//,TAX_RATE1, TAX_RATE1
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(data.get(5)).trim())).toString());

				
				if (CommonFunction.checkNull((CommonFunction.checkNull(data.get(6)).trim())).equalsIgnoreCase(""))//TAX_RATE2 ,TAX_RATE2,
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(data.get(6)).trim())).toString());
				if (CommonFunction.checkNull((CommonFunction.checkNull(data.get(7)).trim())).equalsIgnoreCase(""))//,TDS_RATE TDS_RATE,
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(data.get(7)).trim())).toString());
				if (CommonFunction.checkNull((CommonFunction.checkNull(data.get(8)).trim())).equalsIgnoreCase(""))//,ORG_CHARGE_AMOUNT, ORG_CHARGE_AMOUNT
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(data.get(8)).trim())).toString());
				if (CommonFunction.checkNull((CommonFunction.checkNull(data.get(9)).trim())).equalsIgnoreCase(""))//ORG_TAX_AMOUNT1, ,ORG_TAX_AMOUNT1,
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(data.get(9)).trim())).toString());
				if (CommonFunction.checkNull((CommonFunction.checkNull(data.get(10)).trim())).equalsIgnoreCase(""))//ORG_TAX_AMOUNT2 ORG_TAX_AMOUNT2
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(data.get(10)).trim())).toString());
				if (CommonFunction.checkNull((CommonFunction.checkNull(data.get(11)).trim())).equalsIgnoreCase(""))//ORG_TDS_AMOUNT, ,ORG_TDS_AMOUNT,
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(data.get(11)).trim())).toString());
				
				
				if (CommonFunction.checkNull((CommonFunction.checkNull(data.get(12)).trim())).equalsIgnoreCase(""))//ORG_ADVICE_AMOUNT, ORG_ADVICE_AMOUNT
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(data.get(12)).trim())).toString());
				if (CommonFunction.checkNull((CommonFunction.checkNull(data.get(13)).trim())).equalsIgnoreCase(""))//CHARGE_AMOUNT CHARGE_AMOUNT,
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(data.get(13)).trim())).toString());
				if (CommonFunction.checkNull((CommonFunction.checkNull(data.get(14)).trim())).equalsIgnoreCase(""))//,TAX_AMOUNT1, TAX_AMOUNT1,
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(data.get(14)).trim())).toString());
				if (CommonFunction.checkNull((CommonFunction.checkNull(data.get(15)).trim())).equalsIgnoreCase(""))//TAX_AMOUNT2, TAX_AMOUNT2,
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(data.get(15)).trim())).toString());
				
				if (CommonFunction.checkNull((CommonFunction.checkNull(data.get(16)).trim())).equalsIgnoreCase(""))//TDS_AMOUNT TDS_AMOUNT,
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(data.get(16)).trim())).toString());
				
				if (CommonFunction.checkNull((CommonFunction.checkNull(data.get(17)).trim())).equalsIgnoreCase(""))//,ADVICE_AMOUNT, ADVICE_AMOUNT,
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(data.get(17)).trim())).toString());
				if (CommonFunction.checkNull((CommonFunction.checkNull(data.get(18)).trim())).equalsIgnoreCase(""))//TXN_ADJUSTED_AMOUNT TXN_ADJUSTED_AMOUNT
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(data.get(18)).trim())).toString());
									
				if (CommonFunction.checkNull(vo.getWaivOffAmount())//WAIVE_OFF_AMOUNT, ,WAIVE_OFF_AMOUNT
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getWaivOffAmount()).trim())).toString());
				
				if (CommonFunction.checkNull(vo.getWaveAmountForTaxAmt1())//WAIVE_OFF_TAX_AMOUNT1,
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addString("0");
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getWaveAmountForTaxAmt1()).trim())).toString());
				if (CommonFunction.checkNull(vo.getWaveAmountForTaxAmt2())//WAIVE_OFF_TAX_AMOUNT2
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addString("0");
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(vo.getWaveAmountForTaxAmt2()).trim())).toString());
				if (CommonFunction.checkNull(vo.getRemarks()).equalsIgnoreCase(""))//,REMARKS
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getRemarks()).trim()));
				
				insertPrepStmtObject.addString("P");//REC_STATUS

				if (CommonFunction.checkNull(vo.getMakerID()).equalsIgnoreCase(""))//,,MAKER_ID
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerID()).trim()));

				if (CommonFunction.checkNull(vo.getMakerDate())//,MAKER_DATE
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getMakerDate()).trim()));
				
				if (CommonFunction.checkNull(vo.getLbxapprovedBy())//,APPROVED_BY
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(vo.getLbxapprovedBy()).trim()));
				insertPrepStmtObject.setSql(bufInsSql.toString());

				logger.info("IN insertWaiveOff() insert query1 ### "
						+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				logger
						.info("In WaiveOffSave......................" + status);
				str="I";
				//flag = 'I';
				//sess.removeAttribute("flag");
				// String maxId="";
				 query3.append("Select max(WAIVEOFF_ID) from cr_waiveoff_dtl for update");
				  maxId.append(ConnectionDAO.singleReturn(query3.toString()));
				  //tempVo.setFlag("I");
				  //tempVo.setMaxId(maxId);
			//	  temp.removeAll(temp);
				  temp.add("I");
				  temp.add(maxId.toString());
			} catch (Exception e) {
				logger.info(e.getMessage());
			}

		} else {
			
			
			try {
				
//				logger.info("In saveWaiveOffMaker ........ Flag: " + flag1);
//				if (flag1.equals("N")) {
//					try {
//						str="P";
//						temp2.add("P");
//						temp2.add(vo.getWaveOffId());
//						//flag = 'P';
//						//sess.removeAttribute("flag");
//					} catch (Exception ex12) {
//						logger.info(ex12.getMessage());
//					}
//
//				} else {
					if(count>0)
					{
					StringBuilder query1 = new StringBuilder(); 
					query1.append("update cr_waiveoff_dtl set REC_STATUS='P', " +
							"WAIVE_OFF_AMOUNT="+myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getWaivOffAmount()).trim()).toString())+"," +
							" MAKER_REMARKS='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getRemarks()).trim())+"'," +
							"WAIVE_OFF_TAX_AMOUNT1='"+myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getWaveAmountForTaxAmt1()).trim()).toString())+"'," +
							"WAIVE_OFF_TAX_AMOUNT2='"+myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getWaveAmountForTaxAmt2()).trim()).toString())+"' ," +
							"MAKER_ID='"+CommonFunction.checkNull(vo.getUserId()).trim()+"'," +
							"MAKER_DATE=DATE_ADD(STR_TO_DATE('"+CommonFunction.checkNull(vo.getMakerDate()).trim()+"'," +
							" '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) " +
							",APPROVED_BY='"+CommonFunction.checkNull(vo.getLbxapprovedBy()).trim()+"' where WAIVEOFF_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getWaveOffId())).trim()+"'");
							//+ " BP_TYPE='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getBusinessPartnerType().trim()))
							//+ "' and CHARGE_CODE_ID='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxChargeCodeHID().trim()))
							//+ "' and "
							
					ArrayList queryList = new ArrayList();
					logger.info("In saveWaiveOffMaker ........ update query: "+ query1.toString());
					queryList.add(query1.toString());
									
					status = ConnectionDAO.sqlInsUpdDelete(queryList);
					logger
							.info("In saveWaiveOffMaker.........update status: "
									+ status);
					//flag = 'U';
					//sess.removeAttribute("flag");
					// tempVo.setFlag("U");
					// tempVo.setMaxId(vo.getWaveOffId());
					//temp.remove("I");
					//temp.remove(maxId);
					//temp.removeAll(temp);
					str="U";
					temp1.add("U");
					temp1.add(vo.getWaveOffId());
					query1=null;
				//}
					}
					else
					{
						str="P";
						temp2.add("P");
						temp2.add(vo.getWaveOffId());
					}
			} catch (Exception e) {
				logger.info(e.getMessage());
			}
			finally
			{
				query = null;
				tempCount = null;
				maxId = null;
				query3 = null;
			}
		}
		if(str.equalsIgnoreCase("I"))
		{
			return temp;
		}
		else if(str.equalsIgnoreCase("U"))
		{
			return temp1;
		}
		else if (str.equalsIgnoreCase("N"))
		{
				return temp;
		}
		else
		{
			return temp2;
		}
		
	}
	public ArrayList selectWaiveOffData(String loanId, String bpType,String waveOffId)
	{

		ArrayList WaiveOffData = new ArrayList();
		StringBuilder query = new StringBuilder();
		logger.info("In selectWaiveOffData Loan Id******************************* : " + loanId);
		//logger.info("In selectWaiveOffData BpId Id : " + bpid);
		try {

			ArrayList mainlist = new ArrayList();
			ArrayList subList = new ArrayList();

	      //String query = "select a.LOAN_NO ,b.CUSTOMER_NAME,c.BP_TYPE,c.BP_NAME,d.CHARGE_CODE,d.CHARGE_DESC,e.ADVICE_AMOUNT,e.TXN_ADJUSTED_AMOUNT,e.WAIVE_OFF_AMOUNT,e.REMARKS"
			query.append("select a.LOAN_NO ,b.CUSTOMER_NAME,c.BP_TYPE,c.BP_NAME,d.CHARGE_CODE,d.CHARGE_DESC,f.ADVICE_AMOUNT," +
					"e.WAIVE_OFF_AMOUNT,e.REMARKS,e.WAIVEOFF_ID,f.CHARGE_AMOUNT,f.TAX_AMOUNT1,f.TAX_AMOUNT2," +
					"f.TXN_ADJUSTED_AMOUNT,e.WAIVE_OFF_TAX_AMOUNT1,e.WAIVE_OFF_TAX_AMOUNT2,c.BP_ID,E.MAKER_REMARKS,a.LOAN_ID,e.TXN_ADVICE_ID,e.TAX_RATE1,e.TAX_RATE2,g.DESCRIPTION,f.amount_in_process,a.REC_STATUS,date_format(e.WAIVEOFF_DATE,'"+dateFormat+"') "
					+ " ,e.APPROVED_BY from cr_loan_dtl a,gcd_customer_m b,business_partner_view c,generic_master g,com_charge_code_m d,cr_waiveoff_dtl e,cr_txnadvice_dtl f "
					+ " where b.CUSTOMER_ID=a.LOAN_CUSTOMER_ID"
					+ " and d.CHARGE_CODE=e.CHARGE_CODE_ID "
					+ " and c.LOAN_ID=a.loan_id"
					
					+ " and e.LOAN_ID=a.LOAN_ID and g.GENERIC_KEY='BPTYPE' and g.VALUE=c.BP_TYPE" 
					+ " and c.BP_TYPE='"+bpType+"' "
					+ " and a.LOAN_ID="+loanId+" and WAIVEOFF_ID="+waveOffId+" and e.TXN_ADVICE_ID=f.TXNADVICE_ID and e.loan_id=f.loan_id");
			
			

//			select a.LOAN_NO ,b.CUSTOMER_NAME,c.BP_TYPE,c.BP_NAME,d.CHARGE_CODE,d.CHARGE_DESC,f.ADVICE_AMOUNT,e.WAIVE_OFF_AMOUNT,e.REMARKS,
//			e.WAIVEOFF_ID,f.CHARGE_AMOUNT,f.TAX_AMOUNT1,f.TAX_AMOUNT2,f.TXN_ADJUSTED_AMOUNT,e.WAIVE_OFF_TAX_AMOUNT1,e.WAIVE_OFF_TAX_AMOUNT2,
//			c.BP_ID,E.MAKER_REMARKS,a.LOAN_ID,e.TXN_ADVICE_ID,e.TAX_RATE1,e.TAX_RATE2,g.DESCRIPTION,f.amount_in_process  
//			from cr_loan_dtl a,gcd_customer_m b,business_partner_view c,generic_master g,com_charge_code_m d,cr_waiveoff_dtl e,cr_txnadvice_dtl f 
//			where b.CUSTOMER_ID=a.LOAN_CUSTOMER_ID 
//			and d.CHARGE_CODE=e.CHARGE_CODE_ID  
//			and c.LOAN_ID=a.loan_id 
//			and e.LOAN_ID=a.LOAN_ID and g.GENERIC_KEY='BPTYPE' 
//			and g.VALUE=c.BP_TYPE and c.BP_TYPE='CS'  
//			and a.LOAN_ID=1   and WAIVEOFF_ID=86
//			and e.TXN_ADVICE_ID=f.TXNADVICE_ID
//			and e.loan_id=f.loan_id
//			
			

			logger.info("In selectWaiveOffData : " + query);
			mainlist = ConnectionDAO.sqlSelect(query.toString());
			logger.info("In selectWaiveOffData.....mainlist size: "
					+ mainlist.size());
			for (int i = 0; i < mainlist.size(); i++) {

				subList = (ArrayList) mainlist.get(i);
				logger.info("In selectWaiveOffData......sublist size: "
						+ subList.size());
				if (subList.size() > 0) {
					
					StringBuilder AdviceAmount = new StringBuilder();
					StringBuilder AdjustedAmount = new StringBuilder();
					WaiveOffVO vo = new WaiveOffVO();
					logger.info("setLoanAccountNo ...... "+(String) subList.get(0));
					vo.setLoanAccountNo((CommonFunction.checkNull(subList.get(0)).trim()));
					vo.setLbxLoanNoHID((CommonFunction.checkNull(subList.get(18)).trim()));
					vo.setCustomerName((CommonFunction.checkNull(subList.get(1)).trim()));
					
					vo.setBusinessPartnerType((CommonFunction.checkNull(subList.get(2)).trim()));
				
					vo.setBusinessPartnerName((CommonFunction.checkNull(subList.get(3)).trim()));
				
					vo.setChargeType((CommonFunction.checkNull( subList.get(4)).trim()));
					
					vo.setChargeDescription((CommonFunction.checkNull( subList.get(5)).trim()));
					

					if((CommonFunction.checkNull(subList.get(6)).trim()).equalsIgnoreCase(""))
					{
						vo.setAdviceAmount("0");
					}
					else
					{
						Number adviceAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(6)).trim()));
						vo.setAdviceAmount(myFormatter.format(adviceAmount));
					}

					//vo.setAdviceAmount(myFormatter.parse((CommonFunction.checkNull(subList.get(6)).trim())).toString());

					
					//vo.setAdviceAmount(myFormatter.parse((CommonFunction.checkNull(subList.get(6)).trim())).toString());
					
					AdviceAmount.append(CommonFunction.checkNull(subList.get(6)).trim());
				
					AdjustedAmount.append(CommonFunction.checkNull(subList.get(13)).trim());
					Double AdviceAmt = Double.parseDouble(AdviceAmount.toString());
					Double AdjustedAmt = Double.parseDouble(AdjustedAmount.toString());
					Double BalanceAmount = AdviceAmt - AdjustedAmt;

					logger.info("AdviceAmt amt *************************************************** "+AdviceAmount);
					logger.info("AdjustedAmt amt *************************************************** "+AdjustedAmount);
					logger.info("BalanceAmount amt *************************************************** "+BalanceAmount);
					
					if(CommonFunction.checkNull(BalanceAmount.toString()).equalsIgnoreCase(""))
					{
						vo.setBalanceAmount("0");
					}
					else
					{
						
						Number balanceAmount = myFormatter.parse(CommonFunction.checkNull(BalanceAmount.toString()).trim().toString());
						vo.setBalanceAmount(myFormatter.format(balanceAmount));
					}
					//vo.setBalanceAmount(myFormatter.parse(CommonFunction.checkNull(BalanceAmount.toString())).toString());
					
					if((CommonFunction.checkNull(subList.get(7)).trim()).equalsIgnoreCase(""))
					{
						vo.setWaivOffAmount("0");
					}
					else
					{
						Number waivOffAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(7)).trim()));
						vo.setWaivOffAmount(myFormatter.format(waivOffAmount));
					}
											
					//vo.setWaivOffAmount(myFormatter.parse((CommonFunction.checkNull(subList.get(7)).trim())).toString());

					//vo.setBalanceAmount(myFormatter.parse(CommonFunction.checkNull(BalanceAmount.toString())).toString());
					//vo.setWaivOffAmount(myFormatter.parse((CommonFunction.checkNull(subList.get(7)).trim())).toString());

					vo.setAuthorRemarks((CommonFunction.checkNull(subList.get(8)).trim()));
					vo.setWaveOffId((CommonFunction.checkNull(subList.get(9)).trim()));

					
					if((CommonFunction.checkNull(subList.get(10)).trim()).equalsIgnoreCase(""))
					{
						vo.setChargeAmount("0");
					}
					else
					{
						Number chargeAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(10)).trim()));
						vo.setChargeAmount(myFormatter.format(chargeAmount));
					}
					//vo.setChargeAmount(myFormatter.parse((CommonFunction.checkNull(subList.get(10)).trim())).toString());
					if((CommonFunction.checkNull(subList.get(11)).trim()).equalsIgnoreCase(""))
					{
						vo.setTaxAmount1("0");
					}
					else
					{
						Number taxAmount1 = myFormatter.parse((CommonFunction.checkNull(subList.get(11)).trim()));
						vo.setTaxAmount1(myFormatter.format(taxAmount1));
					}
					//vo.setTaxAmount1(myFormatter.parse((CommonFunction.checkNull(subList.get(11)).trim())).toString());
					
					if((CommonFunction.checkNull(subList.get(12)).trim()).equalsIgnoreCase(""))
					{
						vo.setTaxAmount2("0");
					}
					else
					{
						Number taxAmount2 = myFormatter.parse((CommonFunction.checkNull(subList.get(12)).trim()));
						vo.setTaxAmount2(myFormatter.format(taxAmount2));
					}
					//vo.setTaxAmount2(myFormatter.parse((CommonFunction.checkNull(subList.get(12)).trim())).toString());
					if((CommonFunction.checkNull(subList.get(13)).trim()).equalsIgnoreCase(""))
					{
						vo.setTxnAdjustedAmount("0");
					}
					else
					{
						Number txnAdjustedAmount = myFormatter.parse((CommonFunction.checkNull(subList.get(13)).trim()));
						vo.setTxnAdjustedAmount(myFormatter.format(txnAdjustedAmount));
					}
					//vo.setTxnAdjustedAmount(myFormatter.parse((CommonFunction.checkNull(subList.get(13)).trim())).toString());
					if((CommonFunction.checkNull(subList.get(14)).trim()).equalsIgnoreCase(""))
					{
						vo.setWaveAmountForTaxAmt1("0");
					}
					else
					{
						Number waveAmountForTaxAmt1 = myFormatter.parse((CommonFunction.checkNull(subList.get(14)).trim()));
						vo.setWaveAmountForTaxAmt1(myFormatter.format(waveAmountForTaxAmt1));
					}
					//vo.setWaveAmountForTaxAmt1(myFormatter.parse((CommonFunction.checkNull(subList.get(14)).trim())).toString());
					if((CommonFunction.checkNull(subList.get(15)).trim()).equalsIgnoreCase(""))
					{
						vo.setWaveAmountForTaxAmt2("0");
					}
					else
					{
						Number waveAmountForTaxAmt2 = myFormatter.parse((CommonFunction.checkNull(subList.get(15)).trim()));
						vo.setWaveAmountForTaxAmt2(myFormatter.format(waveAmountForTaxAmt2));
					}
					//vo.setWaveAmountForTaxAmt2(myFormatter.parse((CommonFunction.checkNull(subList.get(15)).trim())).toString());
					

//					vo.setChargeAmount(myFormatter.parse((CommonFunction.checkNull(subList.get(10)).trim())).toString());
//					vo.setTaxAmount1(myFormatter.parse((CommonFunction.checkNull(subList.get(11)).trim())).toString());
//					vo.setTaxAmount2(myFormatter.parse((CommonFunction.checkNull(subList.get(12)).trim())).toString());
//					vo.setTxnAdjustedAmount(myFormatter.parse((CommonFunction.checkNull(subList.get(13)).trim())).toString());
//					vo.setWaveAmountForTaxAmt1(myFormatter.parse((CommonFunction.checkNull(subList.get(14)).trim())).toString());
//					vo.setWaveAmountForTaxAmt2(myFormatter.parse((CommonFunction.checkNull(subList.get(15)).trim())).toString());

					vo.setLbxBusinessPartnearHID((CommonFunction.checkNull(subList.get(16)).trim()));
					vo.setRemarks((CommonFunction.checkNull(subList.get(17)).trim()));
					//vo.setLbxBusinessPartnearHID(CommonFunction.checkNull(bpid));
					double a =0;
					double b = 0;
					double c = 0;
					logger.info("set WaivOff  Amount ***************. "+subList.get(7).toString());
					if(subList.get(7).toString().equalsIgnoreCase("") || subList.get(7).toString()==null)
					{
						a=0;
					}
					else
					{
						logger.info("set WaivOff  Amount ............................... "+subList.get(7).toString());
						a = Double.parseDouble(subList.get(7).toString());
					}
					if(subList.get(14).toString().equalsIgnoreCase("") || subList.get(14).toString()==null)
					{
						b=0;
					}
					else
					{
						logger.info("set tax  Amount 1............................... "+subList.get(14).toString());
						b = Double.parseDouble(subList.get(14).toString());
					}
					if(subList.get(15).toString().equalsIgnoreCase("") || subList.get(15).toString()==null)
					{
						c=0;
					}
					else
					{
						logger.info("set tax  Amount 2............................... "+subList.get(15).toString());
						c = Double.parseDouble(subList.get(15).toString());
					}
					
					logger.info("setWaivOffAmount ............................... "+a);
					logger.info("setWaveAmountForTaxAmt1 ............................... "+b);
					logger.info("setWaveAmountForTaxAmt1 ............................... "+c);
					
					double tot = a+b+c;
					logger.info("total wave off ....................... "+tot);
					

					if(CommonFunction.checkNull(tot+"").trim().equalsIgnoreCase(""))
					{
						vo.setTotalWaiveOffAmt("0");
					}
					else
					{
						Number totalWaiveOffAmt = myFormatter.parse(tot+"");
						vo.setTotalWaiveOffAmt(myFormatter.format(totalWaiveOffAmt));
					}
					//vo.setTotalWaiveOffAmt(myFormatter.parse(CommonFunction.checkNull(tot+"")).toString());

//					vo.setNewChargeAmt(myFormatter.parse((Double.parseDouble(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getChargeAmount()).trim()))-Double.parseDouble(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getWaivOffAmount()).trim())))+"").toString());
//					vo.setNewTaxAmt1(myFormatter.parse((Double.parseDouble(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getTaxAmount1()).trim()))-Double.parseDouble(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getWaveAmountForTaxAmt1()).trim())))+"").toString());
//					vo.setNewTaxAmt2(myFormatter.parse((Double.parseDouble(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getTaxAmount2()).trim()))-Double.parseDouble(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getWaveAmountForTaxAmt2()).trim())))+"").toString());
//					vo.setNewAdviceAmt(myFormatter.parse((Double.parseDouble(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAdviceAmount()).trim()))-Double.parseDouble(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getTotalWaiveOffAmt()).trim())))+"").toString());

					
					
					Double d1 = Double.parseDouble(myFormatter.parse((CommonFunction.checkNull(vo.getChargeAmount()).trim())).toString());
					Double d2 = Double.parseDouble(myFormatter.parse((CommonFunction.checkNull(vo.getWaivOffAmount()).trim())).toString());
					
					Double d3 = d1 - d2;
					logger.info("d1 "+d1);
					logger.info("d2 "+d2);
					logger.info("d3 "+d3);
					if(d3 <= 0)
					{
						logger.info("de--------"+d3);
						vo.setNewChargeAmt("0");
					}
					else
					{
						Number newChargeAmt = myFormatter.parse(d3+"");
						vo.setNewChargeAmt(myFormatter.format(newChargeAmt));
					}
					 d1 = Double.parseDouble(myFormatter.parse((CommonFunction.checkNull(vo.getTaxAmount1()).trim())).toString());
					 d2 = Double.parseDouble(myFormatter.parse((CommonFunction.checkNull(vo.getWaveAmountForTaxAmt1()))).toString());
					
					 d3 = d1 - d2;
					//vo.setNewChargeAmt(myFormatter.parse(((Double.parseDouble(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getChargeAmount()).trim()))-Double.parseDouble(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getWaivOffAmount()).trim())))+"").toString()));
					if(d3 <=0 )
					{
						vo.setNewTaxAmt1("0");
					}
					else
					{
						Number newTaxAmt1 = myFormatter.parse(d3+"");
						vo.setNewTaxAmt1(myFormatter.format(newTaxAmt1));
					}
					d1 = Double.parseDouble(myFormatter.parse((CommonFunction.checkNull(vo.getTaxAmount2()).trim())).toString());
					 d2 = Double.parseDouble(myFormatter.parse((CommonFunction.checkNull(vo.getWaveAmountForTaxAmt2()))).toString());
					
					 d3 = d1 - d2;
					if(d3 <= 0)
					{
						vo.setNewTaxAmt2("0");
					}
					else
					{
						Number newTaxAmt2 = myFormatter.parse(d3+"");
						vo.setNewTaxAmt2(myFormatter.format(newTaxAmt2));
					}
					//vo.setNewTaxAmt2((Double.parseDouble(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getTaxAmount2()).trim()))-Double.parseDouble(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getWaveAmountForTaxAmt2()).trim())))+"");
					
					d1 = Double.parseDouble(myFormatter.parse((CommonFunction.checkNull(vo.getAdviceAmount()).trim())).toString());
					 d2 = Double.parseDouble(myFormatter.parse((CommonFunction.checkNull(vo.getTotalWaiveOffAmt()))).toString());
					
					 d3 = d1 - d2;
					
					if(d3 <=0 )
					{
						vo.setNewAdviceAmt("0");
					}
					else
					{
						Number setNewAdviceAmt = myFormatter.parse(d3+"");
						vo.setNewAdviceAmt(myFormatter.format(setNewAdviceAmt));
					}
					logger.info("transaction advice id ********************************** "+(CommonFunction.checkNull( subList.get(19)).trim()));
					vo.setTxnAdviceId((CommonFunction.checkNull( subList.get(19)).trim()));			
					vo.setTaxRate1((CommonFunction.checkNull( subList.get(20)).trim()));			
					vo.setTaxRate2((CommonFunction.checkNull( subList.get(21)).trim()));			
					
					vo.setBusinessPartnerDescription((CommonFunction.checkNull(subList.get(22)).trim()));
					
					if(!CommonFunction.checkNull(subList.get(23)).trim().equalsIgnoreCase(""))
		    		{
						Number reconNum =myFormatter.parse(CommonFunction.checkNull(subList.get(23)).trim());
						vo.setAmountInProcess(myFormatter.format(reconNum));
		    		}
					
					
					vo.setLoanRecStatus((CommonFunction.checkNull(subList.get(24)).trim()));
					vo.setValueDate((CommonFunction.checkNull(subList.get(25)).trim()));
					vo.setLbxapprovedBy((CommonFunction.checkNull(subList.get(26)).trim()));
					WaiveOffData.add(vo);
					logger.info("WaiveOffData ...... "+WaiveOffData.toString());
					AdviceAmount = null;
					AdjustedAmount = null;
				}
			}

		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		finally
		{
			query = null;

		}
		logger.info("Size Of List in Dao Imp ....................... "+WaiveOffData.toString());
		return WaiveOffData;
	
	}
	public boolean saveAndForwordWaiveOffMaker(Object ob)
	{

		WaiveOffVO vo = (WaiveOffVO) ob;
		boolean status = false;
		StringBuilder queryAdvice = new StringBuilder();
		StringBuilder AmountInprocess = new StringBuilder();
		StringBuilder query = new StringBuilder();
		logger.info("In saveAndForwordWaiveOffMaker.....................................Dao Impl....111");
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();


			ArrayList queryList = new ArrayList();
			try {
				// update cr_waiveoff_dtl set REC_STATUS='f',
				// WAIVE_OFF_AMOUNT=1000 where LOAN_ID=101 and BP_TYPE='SU'
				queryAdvice.append("select AMOUNT_IN_PROCESS from cr_txnAdvice_dtl "
						+ " where bp_type='"
						+ vo.getBusinessPartnerType()
						+ "' " + " and CHARGE_CODE_ID=" + vo.getLbxChargeCodeHID()+" and LOAN_ID="+vo.getLbxLoanNoHID()+" and TXNADVICE_ID="+vo.getTxnAdviceId());
				
				AmountInprocess.append(ConnectionDAO.singleReturn(queryAdvice.toString()));
				logger.info("In saveAndForwordWaiveOffMaker.......AMOUNTINPROCESS............."+ AmountInprocess);
				Number totwaiveOffAmount = myFormatter.parse((vo.getTotalWaveOffAmt()).toString()) ;
				double Amount_In_process = Double.parseDouble(AmountInprocess.toString());
				double totwaive_Off_Amount = totwaiveOffAmount.doubleValue();
				double amtinProcess = Amount_In_process + totwaive_Off_Amount;
				logger.info("In saveAndForwordWaiveOffMaker.......AMOUNTINPROCESS.....after adjusted.........."
								+ amtinProcess);

				query.append("update cr_waiveoff_dtl w,cr_txnAdvice_dtl t set w.REC_STATUS='F', t.AMOUNT_IN_PROCESS="
						+ amtinProcess
						+ " ,w.WAIVE_OFF_AMOUNT="
						+ myFormatter.parse(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getWaivOffAmount().trim())).toString())
						+ ",  w.MAKER_REMARKS='"
						+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getRemarks().trim()))
						+ "' where w.BP_TYPE=t.BP_TYPE and w.CHARGE_CODE_ID=t.CHARGE_CODE_ID "
						+ "  and w.BP_TYPE='"
						+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getBusinessPartnerType().trim()))
						+ "' and  w.CHARGE_CODE_ID=" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxChargeCodeHID().trim()))
						+" and WAIVEOFF_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getWaveOffId().trim()))+"' and t.TXNADVICE_ID='"+vo.getTxnAdviceId()+"'");
				queryList.add(query.toString());
				logger
						.info("In saveAndForwordWaiveOffMaker ........ update query: "
								+ query);
				status = ConnectionDAO.sqlInsUpdDelete(queryList);
				logger
						.info("In saveAndForwordWaiveOffMaker.........update status: "
								+ status);
			} catch (Exception e) {
				logger.info(e.getMessage());
		}
			finally
			{
				queryAdvice = null;
				AmountInprocess = null;
				query = null;
			}
		return status;
	
	}
	public ArrayList getWaiveOffDetailScheme(String charge_code_id,	String loanid, String bp_type,String txn_Advice_Id)
	{

		int charge_code_id1 = Integer.parseInt(charge_code_id);
		int loanid1 = Integer.parseInt(loanid);
		WaiveOffVO vo = new WaiveOffVO();
		ArrayList<Object> list = new ArrayList<Object>();
		StringBuilder query = new StringBuilder();

		try {
				query.append("select Waive_off_amount,advice_amount,txn_adjusted_amount,amount_in_process,CHARGE_AMOUNT,TAX_AMOUNT1," +
					"TAX_AMOUNT2,TXNADVICE_ID,TAX_RATE1,TAX_RATE2 from cr_txnadvice_dtl where charge_code_id="
					+ charge_code_id1
					+ " and loan_id="
					+ loanid1
      				+ " and TXNADVICE_ID="
					+ txn_Advice_Id
					+ " and bp_type='" + bp_type + "'");

			logger.info("getWaiveOffDetailScheme  " + query);
			ArrayList schemedeatail = ConnectionDAO.sqlSelect(query.toString());
			logger.info("getLeadEntryList" + schemedeatail.size());

			for (int i = 0; i < schemedeatail.size(); i++) {

				ArrayList schemedeatail1 = (ArrayList) schemedeatail.get(i);
							
					StringBuilder AdjustedAmount = new StringBuilder();
					StringBuilder AmountinProcess = new StringBuilder();
					StringBuilder AdviceAmount = new StringBuilder();
					
					logger.info("getWaiveOffList" + schemedeatail1.size());
					logger.info("Waive_off_amount ............................. "+CommonFunction.checkNull(schemedeatail1.get(0)));
					vo.setWaiveOffAmountNotUsed((CommonFunction.checkNull(
							schemedeatail1.get(0)).trim()));
					logger.info("advice_amount ............................. "+CommonFunction.checkNull(schemedeatail1.get(1).toString()));
					
					if(!CommonFunction.checkNull(schemedeatail1.get(1)).equalsIgnoreCase(""))
    	    		{
	    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(schemedeatail1.get(1))).trim());
	    	    		vo.setAdviceAmount(myFormatter.format(reconNum));
    	    		}
					
					//vo.setAdviceAmount((CommonFunction.checkNull(schemedeatail1.get(1)).trim()));
					logger.info("txn_adjusted_amount ............................. "+CommonFunction.checkNull(schemedeatail1.get(2)));
					if(!CommonFunction.checkNull(schemedeatail1.get(2)).equalsIgnoreCase(""))
    	    		{
	    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(schemedeatail1.get(2))).trim());
	    	    		vo.setTxnAdjustedAmount(myFormatter.format(reconNum));
    	    		}
					//vo.setTxnAdjustedAmount((CommonFunction.checkNull(schemedeatail1.get(2)).trim()));
					AdjustedAmount.append(CommonFunction.checkNull(schemedeatail1.get(2)).trim());
					AmountinProcess.append(CommonFunction.checkNull(schemedeatail1.get(3)).trim());
					AdviceAmount.append(CommonFunction.checkNull(schemedeatail1.get(1)).trim());

					double Ad_Amount, Adj_Amount, Am_inprocess;

					Adj_Amount = Double.parseDouble(AdjustedAmount.toString());
					Am_inprocess = Double.parseDouble(AmountinProcess.toString());
					Ad_Amount = Double.parseDouble(AdviceAmount.toString());
					double bam = Ad_Amount - Adj_Amount - Am_inprocess;
					Double BalanceAmount = new Double(Math.abs(bam));
				
					if(!CommonFunction.checkNull(BalanceAmount.toString()).equalsIgnoreCase(""))
    	    		{
					Number reconNum =myFormatter.parse(BalanceAmount.toString());
    	    		vo.setBalanceAmount(myFormatter.format(reconNum));
    	    		}
					//vo.setBalanceAmount(BalanceAmount.toString());
					
					if(!CommonFunction.checkNull(schemedeatail1.get(4)).equalsIgnoreCase(""))
    	    		{
	    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(schemedeatail1.get(4))).trim());
	    	    		vo.setChargeAmount(myFormatter.format(reconNum));
    	    		}
					if(!CommonFunction.checkNull(schemedeatail1.get(5)).equalsIgnoreCase(""))
    	    		{
	    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(schemedeatail1.get(5))).trim());
	    	    		vo.setTaxAmount1(myFormatter.format(reconNum));
    	    		}
					if(!CommonFunction.checkNull(schemedeatail1.get(6)).equalsIgnoreCase(""))
    	    		{
	    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(schemedeatail1.get(6))).trim());
	    	    		vo.setTaxAmount2(myFormatter.format(reconNum));
    	    		}
					vo.setTxnAdviceId((CommonFunction.checkNull(schemedeatail1.get(7)).trim()));
//					if(!CommonFunction.checkNull(schemedeatail1.get(7)).equalsIgnoreCase(""))
//    	    		{
//	    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(schemedeatail1.get(7))).trim());
//	    	    		vo.setTxnAdviceId(myFormatter.format(reconNum));
//    	    		}
					if(!CommonFunction.checkNull(schemedeatail1.get(8)).equalsIgnoreCase(""))
    	    		{
	    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(schemedeatail1.get(8))).trim());
	    	    		vo.setTaxRate1(myFormatter.format(reconNum));
    	    		}
					if(!CommonFunction.checkNull(schemedeatail1.get(9)).equalsIgnoreCase(""))
    	    		{
	    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(schemedeatail1.get(9))).trim());
	    	    		vo.setTaxRate2(myFormatter.format(reconNum));
    	    		}
					logger.info("setAmountInProcess ............................. "+CommonFunction.checkNull(schemedeatail1.get(3).toString()));
					if(!CommonFunction.checkNull(schemedeatail1.get(3)).equalsIgnoreCase(""))
    	    		{
	    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(schemedeatail1.get(3))).trim());
	    	    		vo.setAmountInProcess(myFormatter.format(reconNum));
	    	    		logger.info("setAmountInProcess ............................. "+CommonFunction.checkNull(vo.getAmountInProcess()));
    	    		}
					//vo.setChargeAmount((CommonFunction.checkNull(schemedeatail1.get(4)).trim()));
					//vo.setTaxAmount1((CommonFunction.checkNull(schemedeatail1.get(5)).trim()));		
					//vo.setTaxAmount2((CommonFunction.checkNull(schemedeatail1.get(6)).trim()));
					//vo.setTxnAdviceId((CommonFunction.checkNull(schemedeatail1.get(7)).trim()));
					//vo.setTaxRate1((CommonFunction.checkNull(schemedeatail1.get(8)).trim()));
					//vo.setTaxRate2((CommonFunction.checkNull(schemedeatail1.get(9)).trim()));
				list.add(vo);
				AdjustedAmount = null;
				AmountinProcess = null;
				AdviceAmount = null;

			
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		finally
		{
			query = null;

		}
		return list;
	
	}
	public ArrayList<PaymentMakerForCMVO> viewReceivableForWaiveOff(int loanId,String bpType,int bpId)
	{


	 		ArrayList<PaymentMakerForCMVO> viewReceivabList=new ArrayList<PaymentMakerForCMVO>();
	 		StringBuilder query = new StringBuilder();
	 		try{
	 			ArrayList mainList=new ArrayList ();
	 			ArrayList subList =new ArrayList();
	 					
	 			logger.info(" In viewReceivableForWaiveOff....");	
	 				
	 		
	 			query.append("SELECT DATE_FORMAT(A.ADVICE_DATE,'"+dateFormat+"'),(Select CHARGE_DESC From com_charge_code_m "+
		 		" Where CHARGE_CODE=a.CHARGE_CODE_ID) CHARGE,A.ORG_ADVICE_AMOUNT,A.WAIVE_OFF_AMOUNT,A.TDS_AMOUNT," +
		 		" A.TXN_ADJUSTED_AMOUNT,A.AMOUNT_IN_PROCESS,"+
		        " (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS))BALANCE_AMOUNT,A.ADVICE_AMOUNT,G.DESCRIPTION"+
		 		" FROM cr_txnadvice_dtl A inner join generic_master G on A.BP_TYPE=G.VALUE and g.GENERIC_KEY='BPTYPE' WHERE A.REC_STATUS IN ('A', 'F') AND  " +
		 		" (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT))>0 AND"+
		        " A.ADVICE_TYPE='R' AND LOAN_ID='" +StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+ "' " +
		        " AND BP_TYPE='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(bpType).trim()) + "'" +
		       	" and A.BP_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(bpId).trim())+"' " +
		       	" ORDER BY A.ADVICE_DATE ASC");

	 			
	 			logger.info("In viewReceivableForWaiveOff"+query);	
	 			
	 			mainList=ConnectionDAO.sqlSelect(query.toString());
	 			for(int i=0;i<mainList.size();i++)
	 			{
	 				subList= (ArrayList)mainList.get(i);
	 				if(subList.size()>0){
	 				PaymentMakerForCMVO paymentVO = new PaymentMakerForCMVO();
					paymentVO.setPaymentDate((CommonFunction.checkNull(subList.get(0)).trim()));
 				paymentVO.setChargeDesc((CommonFunction.checkNull(subList.get(1)).trim()));
 				if((CommonFunction.checkNull(subList.get(2)).trim()).equalsIgnoreCase(""))
 					paymentVO.setOriginalAmount("0");
 				else
 				{
 					Number orgAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(2)).trim()));
 					paymentVO.setOriginalAmount(myFormatter.format(orgAmt));
 				}
 				
 				if((CommonFunction.checkNull(subList.get(3)).trim()).equalsIgnoreCase(""))
 					paymentVO.setWaiveOffAmount("0");
 				else
 				{
 					Number waivedOffAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(3)).trim()));
 					paymentVO.setWaiveOffAmount(myFormatter.format(waivedOffAmt));
 				}
 				
 				if((CommonFunction.checkNull(subList.get(4)).trim()).equalsIgnoreCase(""))
 					paymentVO.setTdsadviseAmount("0");
 				else
 				{
 					Number tdsAdviceAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(4)).trim()));
 					paymentVO.setTdsadviseAmount(myFormatter.format(tdsAdviceAmt));
 				}
 				
 				if((CommonFunction.checkNull(subList.get(5)).trim()).equalsIgnoreCase(""))
 					paymentVO.setAdjustedAmount("0");
 				else
 				{
 					Number adjustedAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(5)).trim()));
 					paymentVO.setAdjustedAmount(myFormatter.format(adjustedAmt));
 				}
 				
 				if((CommonFunction.checkNull(subList.get(6)).trim()).equalsIgnoreCase(""))
 					paymentVO.setAmountInProcess("0");
 				else
 				{
 					Number amtInProc = myFormatter.parse((CommonFunction.checkNull(subList.get(6)).trim()));
 					paymentVO.setAmountInProcess(myFormatter.format(amtInProc));
 				}
 				
 				if((CommonFunction.checkNull(subList.get(7)).trim()).equalsIgnoreCase(""))
 					paymentVO.setBalanceAmount("0");
 				else
 				{
 					Number balAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(7)).trim()));
 					paymentVO.setBalanceAmount(myFormatter.format(balAmt));
 				}
 				
 				if((CommonFunction.checkNull(subList.get(8)).trim()).equalsIgnoreCase(""))
 					paymentVO.setAdviceAmount("0");
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
	 				finally
	 				{
	 					query = null;
	 				}

	 				return viewReceivabList;
	 			
	}
	public  ArrayList<PaymentMakerForCMVO>viewPayableForWaiveOff(int loanId,String bpType,int bpId)
	{

		 	ArrayList<PaymentMakerForCMVO> getPayableList=new ArrayList<PaymentMakerForCMVO>();
		 	StringBuilder query= new StringBuilder();
		 	try{
		 		ArrayList mainList=new ArrayList ();
		 		ArrayList subList =new ArrayList();
		 		//CallableStatement cst=null;
		 		//Connection con=ConnectionDAO.getConnection();
		 				
		 		logger.info(" In viewPayableForWaiveOff....");	
		 			
		 	
		 		query.append("SELECT DATE_FORMAT(A.ADVICE_DATE,'"+dateFormat+"'),(Select CHARGE_DESC From com_charge_code_m "+
		 		" Where CHARGE_CODE=a.CHARGE_CODE_ID) CHARGE,A.ORG_ADVICE_AMOUNT,A.WAIVE_OFF_AMOUNT,A.TDS_AMOUNT," +
		 	    " A.TXN_ADJUSTED_AMOUNT,A.AMOUNT_IN_PROCESS,"+
		       " (ADVICE_AMOUNT-(TXN_ADJUSTED_AMOUNT+AMOUNT_IN_PROCESS))BALANCE_AMOUNT,A.ADVICE_AMOUNT"+
		 		"  FROM cr_txnadvice_dtl A   WHERE A.REC_STATUS IN ('A', 'F')  "+
		       "  AND  A.ADVICE_TYPE='P' AND LOAN_ID='" +StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+ "'" +
		       		" AND BP_TYPE='" + StringEscapeUtils.escapeSql(CommonFunction.checkNull(bpType).trim()) + "'" +
		       				"AND A.BP_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(bpId).trim())+"' ORDER BY A.ADVICE_DATE ASC");

		 		
		 		logger.info("In viewPayableForWaiveOff:-"+query);	
		 		
		 		mainList=ConnectionDAO.sqlSelect(query.toString());
		 		for(int i=0;i<mainList.size();i++)
		 		{
		 			subList= (ArrayList)mainList.get(i);
		 			if(subList.size()>0){
		 				
		 			PaymentMakerForCMVO paymentVO = new PaymentMakerForCMVO();
					paymentVO.setPaymentDate((CommonFunction.checkNull(subList.get(0)).trim()));
 				paymentVO.setChargeDesc((CommonFunction.checkNull(subList.get(1)).trim()));
 				if((CommonFunction.checkNull(subList.get(2)).trim()).equalsIgnoreCase(""))
 					paymentVO.setOriginalAmount("0");
 				else
 				{
 					Number orgAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(2)).trim()));
 					paymentVO.setOriginalAmount(myFormatter.format(orgAmt));
 				}
 				
 				if((CommonFunction.checkNull(subList.get(3)).trim()).equalsIgnoreCase(""))
 					paymentVO.setWaiveOffAmount("0");
 				else
 				{
 					Number waivedOffAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(3)).trim()));
 					paymentVO.setWaiveOffAmount(myFormatter.format(waivedOffAmt));
 				}
 				
 				if((CommonFunction.checkNull(subList.get(4)).trim()).equalsIgnoreCase(""))
 					paymentVO.setTdsadviseAmount("0");
 				else
 				{
 					Number tdsAdviceAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(4)).trim()));
 					paymentVO.setTdsadviseAmount(myFormatter.format(tdsAdviceAmt));
 				}
 				
 				if((CommonFunction.checkNull(subList.get(5)).trim()).equalsIgnoreCase(""))
 					paymentVO.setAdjustedAmount("0");
 				else
 				{
 					Number adjustedAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(5)).trim()));
 					paymentVO.setAdjustedAmount(myFormatter.format(adjustedAmt));
 				}
 				
 				if((CommonFunction.checkNull(subList.get(6)).trim()).equalsIgnoreCase(""))
 					paymentVO.setAmountInProcess("0");
 				else
 				{
 					Number amtInProc = myFormatter.parse((CommonFunction.checkNull(subList.get(6)).trim()));
 					paymentVO.setAmountInProcess(myFormatter.format(amtInProc));
 				}
 				
 				if((CommonFunction.checkNull(subList.get(7)).trim()).equalsIgnoreCase(""))
 					paymentVO.setBalanceAmount("0");
 				else
 				{
 					Number balAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(7)).trim()));
 					paymentVO.setBalanceAmount(myFormatter.format(balAmt));
 				}
 				
 				if((CommonFunction.checkNull(subList.get(8)).trim()).equalsIgnoreCase(""))
 					paymentVO.setAdviceAmount("0");
 				else
 				{
 					Number adviceAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(8)).trim()));
 					paymentVO.setAdviceAmount(myFormatter.format(adviceAmt));
 				}
		 			
		 			getPayableList.add(paymentVO);
		 			}
		 		}
		 			}catch(Exception e){
		 				e.printStackTrace();
		 			}
		 			finally
		 			{
		 				query = null;
		 			}

		 			return getPayableList;
		 		}
//Ritu
	public boolean deleteWaiveoff(String id) {
		
		boolean status=false;
		ArrayList list=new ArrayList();
		StringBuilder query=new StringBuilder();
		try{
		query.append ("delete from cr_waiveoff_dtl where WAIVEOFF_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id).trim())+"' ");
		list.add(query.toString());
		logger.info("deleteWaiveoff() query------------------" + query.toString());
		status =ConnectionDAO.sqlInsUpdDelete(list);
		}
	  catch (Exception e) {
			e.printStackTrace();
		}
	  return status;

	}


}
