package com.cp.daoImplMYSQL;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.cp.dao.DeviationApprovalDAO;
import com.cp.vo.CommonDealVo;
import com.cp.vo.DeviationApprovalVo;
import com.cp.vo.HeaderInfoVo;
import com.logger.LoggerMsg;

public class DeviationApprovalDAOImpl implements DeviationApprovalDAO {
	private static final Logger logger = Logger
			.getLogger(DeviationApprovalDAOImpl.class.getName());
	ResourceBundle resource = ResourceBundle
			.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime = resource.getString("lbl.dateWithTimeInDao");
	String dateFormat = resource.getString("lbl.dateInDao");

	DecimalFormat myFormatter = new DecimalFormat("###,###.####");
	int no = Integer.parseInt(resource.getString("msg.pageSizeForMaster"));

	public ArrayList<CommonDealVo> deviationApprovalAllDetails(CommonDealVo vo) {
		ArrayList list = new ArrayList();
		int count = 0;

		int startRecordIndex = 0;
		int endRecordIndex = no;

		logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "
				+ vo.getReportingToUserId());
		String userNameQ = "select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"
				+ vo.getReportingToUserId() + "'";
		String userName = ConnectionDAO.singleReturn(userNameQ);
		logger.info("userNameQ: " + userNameQ + " userName: " + userName);
		ArrayList header = new ArrayList();

		try {
			logger.info("Inside deviationApprovalAllDetails.....");

			CommonDealVo fetchVo = (CommonDealVo) vo;
			boolean appendSQL = false;
			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();

			bufInsSql
					.append(" select distinct d.DEAL_ID,DEAL_NO,deal.CUSTOMER_NAME,p.PRODUCT_DESC,s.SCHEME_DESC,d.DEAL_DATE,n.DEAL_TENURE,n.DEAL_LOAN_AMOUNT from cr_deal_dtl d");
			bufInsSqlTempCount
					.append("SELECT COUNT(1) FROM (Select distinct d.DEAL_ID,DEAL_NO,deal.CUSTOMER_NAME,p.PRODUCT_DESC,s.SCHEME_DESC,d.DEAL_DATE,n.DEAL_TENURE,n.DEAL_LOAN_AMOUNT FROM cr_deal_dtl d ");

			bufInsSql
					.append(" inner join cr_deal_loan_dtl n on d.DEAL_ID=n.DEAL_ID  ");
			bufInsSqlTempCount
					.append(" inner join cr_deal_loan_dtl n on d.DEAL_ID=n.DEAL_ID  ");

			bufInsSql
					.append(" inner join cr_product_m p on n.DEAL_PRODUCT=p.PRODUCT_ID  ");
			bufInsSqlTempCount
					.append(" inner join cr_product_m p on n.DEAL_PRODUCT=p.PRODUCT_ID  ");

			bufInsSql
					.append(" inner join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID ");
			bufInsSqlTempCount
					.append(" inner join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID ");

			bufInsSql
					.append(" inner join cr_scheme_m s on n.DEAL_SCHEME=s.SCHEME_ID ");
			bufInsSqlTempCount
					.append(" inner join cr_scheme_m s on n.DEAL_SCHEME=s.SCHEME_ID ");

			/*
			 * bufInsSql.append(
			 * " INNER JOIN cr_deal_movement_dtl M ON M.DEAL_ID=D.DEAL_ID AND M.DEAL_RECEIVED<>'0000-00-00 00:00:00' AND M.DEAL_FORWARDED='0000-00-00 00:00:00' AND M.DEAL_STAGE_ID='POC' AND M.REC_STATUS='A' "
			 * ); bufInsSqlTempCount.append(
			 * " INNER JOIN cr_deal_movement_dtl M ON M.DEAL_ID=D.DEAL_ID AND M.DEAL_RECEIVED<>'0000-00-00 00:00:00' AND M.DEAL_FORWARDED='0000-00-00 00:00:00' AND M.DEAL_STAGE_ID='POC' AND M.REC_STATUS='A' "
			 * );
			 */
			bufInsSql
					.append("  join cr_policy_decision md  on d.DEAL_ID=md.DEAL_ID and RULE_RESULT='T' and d.DEAL_ID  in ( select distinct md.deal_id from cr_policy_decision md where md.REC_STATUS in('I','P') and md.DEVIATION_TYPE='A') ");
			bufInsSqlTempCount
					.append("  join cr_policy_decision md  on d.DEAL_ID=md.DEAL_ID and RULE_RESULT='T' and d.DEAL_ID  in ( select distinct md.deal_id from cr_policy_decision md where md.REC_STATUS in('I','P') and md.DEVIATION_TYPE='A') ");

			bufInsSql.append("  ,cr_user_approval_m ua ");
			bufInsSqlTempCount.append(" ,cr_user_approval_m ua ");

			// bufInsSqlTempCount.append("SELECT COUNT(1) FROM cr_deal_dtl d ");

			if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(
					vo.getLbxDealNo()).trim()).equalsIgnoreCase("")))
					&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(
							vo.getCustomername()).trim()).equalsIgnoreCase("")))
					&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(
							vo.getLbxProductID()).trim()).equalsIgnoreCase("")))
					&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(
							vo.getLbxscheme()).trim()).equalsIgnoreCase("")))) {
				bufInsSql.append("WHERE d.MAKER_ID!='"
						+ StringEscapeUtils
								.escapeSql(vo.getReportingToUserId()).trim()
						+ "' AND md.MAKER_ID!='"
						+ StringEscapeUtils
								.escapeSql(vo.getReportingToUserId()).trim()
						+ "' AND d.DEAL_BRANCH='"
						+ StringEscapeUtils.escapeSql(vo.getBranchId()).trim()
						+ "' and d.DEAL_ID='"
						+ StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()
						+ "' AND deal.CUSTOMER_NAME like'%"
						+ StringEscapeUtils.escapeSql(vo.getCustomername())
								.trim()
						+ "%' AND n.DEAL_PRODUCT='"
						+ StringEscapeUtils.escapeSql(vo.getLbxProductID())
								.trim() + "'AND n.DEAL_SCHEME='"
						+ StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()
						+ "' AND d.Rec_status='F'");
				bufInsSqlTempCount.append("WHERE d.MAKER_ID!='"
						+ StringEscapeUtils
								.escapeSql(vo.getReportingToUserId()).trim()
						+ "' AND md.MAKER_ID!='"
						+ StringEscapeUtils
								.escapeSql(vo.getReportingToUserId()).trim()
						+ "' AND d.DEAL_BRANCH='"
						+ StringEscapeUtils.escapeSql(vo.getBranchId()).trim()
						+ "' and d.DEAL_ID='"
						+ StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()
						+ "' AND deal.CUSTOMER_NAME like'%"
						+ StringEscapeUtils.escapeSql(vo.getCustomername())
								.trim()
						+ "%' AND n.DEAL_PRODUCT='"
						+ StringEscapeUtils.escapeSql(vo.getLbxProductID())
								.trim() + "'AND n.DEAL_SCHEME='"
						+ StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()
						+ "' AND d.Rec_status='F'");
			}

			if (((vo.getLbxDealNo().equalsIgnoreCase(""))
					|| (vo.getCustomername().equalsIgnoreCase(""))
					|| (vo.getLbxProductID().equalsIgnoreCase("")) || (vo
						.getLbxscheme().equalsIgnoreCase("")))) {
				appendSQL = true;
			}

			if (appendSQL) {
				logger.info("In Where Clause");
				// bufInsSql.append(" WHERE d.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and d.Rec_status='F' ");
				// bufInsSqlTempCount.append(" WHERE d.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and d.Rec_status='F' ");

				// bufInsSql.append(" WHERE d.MAKER_ID!='"+StringEscapeUtils.escapeSql(vo.getReportingToUserId()).trim()+"' AND ua.USER_ID='"+StringEscapeUtils.escapeSql(vo.getReportingToUserId()).trim()+"' and n.DEAL_LOAN_AMOUNT>=ua.AMOUNT_FROM and n.DEAL_LOAN_AMOUNT<=ua.AMOUNT_TO and ua.USER_ROLE='P' and  ua.LEVEL=md.APPROVAL_LEVEL ");
				// bufInsSqlTempCount.append(" WHERE d.MAKER_ID!='"+StringEscapeUtils.escapeSql(vo.getReportingToUserId()).trim()+"' AND ua.USER_ID='"+StringEscapeUtils.escapeSql(vo.getReportingToUserId()).trim()+"' and n.DEAL_LOAN_AMOUNT>=ua.AMOUNT_FROM and n.DEAL_LOAN_AMOUNT<=ua.AMOUNT_TO and ua.USER_ROLE='P' and  ua.LEVEL=md.APPROVAL_LEVEL ");
				if (CommonFunction.checkNull(vo.getReportingToUserId())
						.equalsIgnoreCase(vo.getUserId())) {
					bufInsSql
							.append(" WHERE d.MAKER_ID!='"
									+ StringEscapeUtils.escapeSql(
											vo.getReportingToUserId()).trim()
									+ "' AND md.MAKER_ID!='"
									+ StringEscapeUtils.escapeSql(
											vo.getReportingToUserId()).trim()
									+ "' AND ua.USER_ID='"
									+ StringEscapeUtils.escapeSql(
											vo.getReportingToUserId()).trim()
									+ "' and ua.REC_STATUS='A' and ua.USER_ROLE='P' AND d.DEAL_BRANCH='"
									+ StringEscapeUtils.escapeSql(
											vo.getBranchId()).trim()
									+ "' and d.Rec_status='F'   ");
					bufInsSql
							.append("and ua.SUB_RULE_TYPE=(select SUB_RULE_TYPE from cr_user_approval_m where USER_ROLE='P' and USER_ID='"
									+ StringEscapeUtils.escapeSql(
											vo.getReportingToUserId()).trim()
									+ "')");
					bufInsSqlTempCount
							.append(" WHERE d.MAKER_ID!='"
									+ StringEscapeUtils.escapeSql(
											vo.getReportingToUserId()).trim()
									+ "' AND md.MAKER_ID!='"
									+ StringEscapeUtils.escapeSql(
											vo.getReportingToUserId()).trim()
									+ "' AND ua.USER_ID='"
									+ StringEscapeUtils.escapeSql(
											vo.getReportingToUserId()).trim()
									+ "' and ua.REC_STATUS='A' and ua.USER_ROLE='P' AND d.DEAL_BRANCH='"
									+ StringEscapeUtils.escapeSql(
											vo.getBranchId()).trim()
									+ "' and d.Rec_status='F' ");
					bufInsSqlTempCount
							.append("and ua.SUB_RULE_TYPE=(select SUB_RULE_TYPE from cr_user_approval_m where USER_ROLE='P' and USER_ID='"
									+ StringEscapeUtils.escapeSql(
											vo.getReportingToUserId()).trim()
									+ "')");
				} else {
					bufInsSql
							.append(" WHERE d.MAKER_ID!='"
									+ StringEscapeUtils.escapeSql(
											vo.getReportingToUserId()).trim()
									+ "' AND md.MAKER_ID!='"
									+ StringEscapeUtils.escapeSql(
											vo.getReportingToUserId()).trim()
									+ "' AND ua.USER_ID='"
									+ StringEscapeUtils.escapeSql(
											vo.getReportingToUserId()).trim()
									+ "' and ua.REC_STATUS='A' and ua.USER_ROLE='P' AND d.DEAL_BRANCH='"
									+ StringEscapeUtils.escapeSql(
											vo.getBranchId()).trim()
									+ "' and d.Rec_status='F'  ");
					bufInsSql
							.append("and ua.SUB_RULE_TYPE=(select SUB_RULE_TYPE from cr_user_approval_m where USER_ROLE='P' and USER_ID='"
									+ StringEscapeUtils.escapeSql(
											vo.getReportingToUserId()).trim()
									+ "')");
					bufInsSqlTempCount
							.append(" WHERE d.MAKER_ID!='"
									+ StringEscapeUtils.escapeSql(
											vo.getReportingToUserId()).trim()
									+ "' AND md.MAKER_ID!='"
									+ StringEscapeUtils.escapeSql(
											vo.getReportingToUserId()).trim()
									+ "' AND ua.USER_ID='"
									+ StringEscapeUtils.escapeSql(
											vo.getReportingToUserId()).trim()
									+ "' and ua.REC_STATUS='A' and ua.USER_ROLE='P' AND d.DEAL_BRANCH='"
									+ StringEscapeUtils.escapeSql(
											vo.getBranchId()).trim()
									+ "' and d.Rec_status='F'   ");
					bufInsSqlTempCount
							.append("and ua.SUB_RULE_TYPE=(select SUB_RULE_TYPE from cr_user_approval_m where USER_ROLE='P' and USER_ID='"
									+ StringEscapeUtils.escapeSql(
											vo.getReportingToUserId()).trim()
									+ "')");
				}

			}

			if ((!(StringEscapeUtils.escapeSql(
					CommonFunction.checkNull(vo.getLbxDealNo())).trim()
					.equalsIgnoreCase("")))) {
				bufInsSql.append("AND d.DEAL_ID='"
						+ StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()
						+ "' ");
				bufInsSqlTempCount.append("AND d.DEAL_ID='"
						+ StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()
						+ "' ");
				appendSQL = true;

			}

			if ((!(StringEscapeUtils.escapeSql(
					CommonFunction.checkNull(vo.getCustomername())).trim()
					.equalsIgnoreCase("")))) {
				bufInsSql.append("AND deal.CUSTOMER_NAME like'%"
						+ StringEscapeUtils.escapeSql(vo.getCustomername())
								.trim() + "%' ");
				bufInsSqlTempCount.append("AND deal.CUSTOMER_NAME like'%"
						+ StringEscapeUtils.escapeSql(vo.getCustomername())
								.trim() + "%' ");
				appendSQL = true;
			}
			if ((!(StringEscapeUtils.escapeSql(
					CommonFunction.checkNull(vo.getLbxProductID())).trim()
					.equalsIgnoreCase("")))) {
				bufInsSql.append("AND n.DEAL_PRODUCT='"
						+ StringEscapeUtils.escapeSql(vo.getLbxProductID())
								.trim() + "' ");
				bufInsSqlTempCount.append("AND n.DEAL_PRODUCT='"
						+ StringEscapeUtils.escapeSql(vo.getLbxProductID())
								.trim() + "' ");
				appendSQL = true;
			}
			if ((!(StringEscapeUtils.escapeSql(
					CommonFunction.checkNull(vo.getLbxscheme())).trim()
					.equalsIgnoreCase("")))) {
				bufInsSql.append("AND n.DEAL_SCHEME='"
						+ StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()
						+ "' ");
				bufInsSqlTempCount.append("AND n.DEAL_SCHEME='"
						+ StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()
						+ "' ");
				appendSQL = true;
			}
			bufInsSqlTempCount.append(") tab");

			/*
			 * if((StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()==null
			 * &&
			 * StringEscapeUtils.escapeSql(vo.getCustomername()).trim()==null)
			 * || (StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim().
			 * equalsIgnoreCase("") &&
			 * StringEscapeUtils.escapeSql(vo.getCustomername
			 * ()).trim().equalsIgnoreCase("")) ||
			 * (StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()==null
			 * && StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim().
			 * equalsIgnoreCase("")) || (
			 * StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()==null &&
			 * StringEscapeUtils
			 * .escapeSql(vo.getLbxscheme()).trim().equalsIgnoreCase("")) ||
			 * vo.getCurrentPageLink()>1) {
			 * 
			 * logger.info("current PAge Link no .................... "+vo.
			 * getCurrentPageLink()); if(vo.getCurrentPageLink()>1) {
			 * startRecordIndex = (vo.getCurrentPageLink()-1)*no; endRecordIndex
			 * = no;
			 * logger.info("startRecordIndex .................... "+startRecordIndex
			 * );
			 * logger.info("endRecordIndex .................... "+endRecordIndex
			 * ); }
			 * 
			 * bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
			 * 
			 * }
			 */

			// /////////////////////////////////////////////////////
			appendSQL = false;
			StringBuffer bufInsNewSql = new StringBuffer();
			StringBuffer bufInsSqlNewTempCount = new StringBuffer();
			startRecordIndex = 0;
			endRecordIndex = no;

			bufInsNewSql
					.append(" select distinct  d.DEAL_ID,DEAL_NO,deal.CUSTOMER_NAME,p.PRODUCT_DESC,s.SCHEME_DESC,d.DEAL_DATE,n.DEAL_TENURE,n.DEAL_LOAN_AMOUNT from cr_deal_dtl d  ");
			bufInsSqlNewTempCount
					.append(" select COUNT(1) FROM (SELECT distinct  d.DEAL_ID,DEAL_NO,deal.CUSTOMER_NAME,p.PRODUCT_DESC,s.SCHEME_DESC,d.DEAL_DATE,n.DEAL_TENURE,n.DEAL_LOAN_AMOUNT from cr_deal_dtl d ");

			bufInsNewSql
					.append(" inner join cr_deal_loan_dtl n on d.DEAL_ID=n.DEAL_ID  ");
			bufInsSqlNewTempCount
					.append(" inner join cr_deal_loan_dtl n on d.DEAL_ID=n.DEAL_ID  ");

			bufInsNewSql
					.append("  inner join cr_product_m p on n.DEAL_PRODUCT=p.PRODUCT_ID   ");
			bufInsSqlNewTempCount
					.append("  inner join cr_product_m p on n.DEAL_PRODUCT=p.PRODUCT_ID   ");

			bufInsNewSql
					.append(" inner join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID ");
			bufInsSqlNewTempCount
					.append(" left join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID ");

			bufInsNewSql
					.append(" inner join cr_scheme_m s on n.DEAL_SCHEME=s.SCHEME_ID  ");
			bufInsSqlNewTempCount
					.append(" left join cr_scheme_m s on n.DEAL_SCHEME=s.SCHEME_ID  ");

			bufInsNewSql
					.append("  join cr_policy_decision md  on d.DEAL_ID=md.DEAL_ID and md.REC_STATUS in('I','P')    ");
			bufInsSqlNewTempCount
					.append("  join cr_policy_decision md  on d.DEAL_ID=md.DEAL_ID and md.REC_STATUS in('I','P')  ");

			bufInsNewSql.append("  ,cr_user_approval_m ua ");
			bufInsSqlNewTempCount.append(" ,cr_user_approval_m ua ");

			// bufInsSqlTempCount.append("SELECT COUNT(1) FROM cr_deal_dtl d ");

			if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(
					vo.getLbxDealNo()).trim()).equalsIgnoreCase("")))
					&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(
							vo.getCustomername()).trim()).equalsIgnoreCase("")))
					&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(
							vo.getLbxProductID()).trim()).equalsIgnoreCase("")))
					&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(
							vo.getLbxscheme()).trim()).equalsIgnoreCase("")))) {
				bufInsNewSql.append("WHERE  d.DEAL_BRANCH='"
						+ StringEscapeUtils.escapeSql(vo.getBranchId()).trim()
						+ "' and d.DEAL_ID='"
						+ StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()
						+ "' AND deal.CUSTOMER_NAME like'%"
						+ StringEscapeUtils.escapeSql(vo.getCustomername())
								.trim()
						+ "%' AND n.DEAL_PRODUCT='"
						+ StringEscapeUtils.escapeSql(vo.getLbxProductID())
								.trim() + "'AND n.DEAL_SCHEME='"
						+ StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()
						+ "' AND d.Rec_status='F'");
				bufInsSqlNewTempCount.append("WHERE  d.DEAL_BRANCH='"
						+ StringEscapeUtils.escapeSql(vo.getBranchId()).trim()
						+ "' and d.DEAL_ID='"
						+ StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()
						+ "' AND deal.CUSTOMER_NAME like'%"
						+ StringEscapeUtils.escapeSql(vo.getCustomername())
								.trim()
						+ "%' AND n.DEAL_PRODUCT='"
						+ StringEscapeUtils.escapeSql(vo.getLbxProductID())
								.trim() + "'AND n.DEAL_SCHEME='"
						+ StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()
						+ "' AND d.Rec_status='F'");
			}

			if (((vo.getLbxDealNo().equalsIgnoreCase(""))
					|| (vo.getCustomername().equalsIgnoreCase(""))
					|| (vo.getLbxProductID().equalsIgnoreCase("")) || (vo
						.getLbxscheme().equalsIgnoreCase("")))) {
				appendSQL = true;
			}

			if (appendSQL) {
				logger.info("In Where Clause");
				// bufInsSql.append(" WHERE d.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and d.Rec_status='F' ");
				// bufInsSqlTempCount.append(" WHERE d.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and d.Rec_status='F' ");
				if (CommonFunction.checkNull(vo.getReportingToUserId())
						.equalsIgnoreCase(vo.getUserId())) {
					bufInsNewSql
							.append(" WHERE ua.USER_ID='"
									+ StringEscapeUtils.escapeSql(
											vo.getReportingToUserId()).trim()
									+ "' AND md.MAKER_ID!='"
									+ StringEscapeUtils.escapeSql(
											vo.getReportingToUserId()).trim()
									+ "'  and ua.USER_ROLE='P'  and d.Rec_status='F' and ua.REC_STATUS='A' and d.DEAL_BRANCH='"
									+ StringEscapeUtils.escapeSql(
											vo.getBranchId()).trim() + "' ");
					bufInsSqlNewTempCount
							.append(" WHERE  ua.USER_ID='"
									+ StringEscapeUtils.escapeSql(
											vo.getReportingToUserId()).trim()
									+ "' AND md.MAKER_ID!='"
									+ StringEscapeUtils.escapeSql(
											vo.getReportingToUserId()).trim()
									+ "'   and ua.USER_ROLE='P'  and d.Rec_status='F' and ua.REC_STATUS='A' and d.DEAL_BRANCH='"
									+ StringEscapeUtils.escapeSql(
											vo.getBranchId()).trim() + "' ");
				} else {
					bufInsNewSql
							.append(" WHERE ua.USER_ID='"
									+ StringEscapeUtils.escapeSql(
											vo.getReportingToUserId()).trim()
									+ "' AND md.MAKER_ID!='"
									+ StringEscapeUtils.escapeSql(
											vo.getReportingToUserId()).trim()
									+ "'   and ua.USER_ROLE='P'  and d.Rec_status='F' and ua.REC_STATUS='A' and d.DEAL_BRANCH='"
									+ StringEscapeUtils.escapeSql(
											vo.getBranchId()).trim() + "' ");
					bufInsSqlNewTempCount
							.append(" WHERE  ua.USER_ID='"
									+ StringEscapeUtils.escapeSql(
											vo.getReportingToUserId()).trim()
									+ "' AND md.MAKER_ID!='"
									+ StringEscapeUtils.escapeSql(
											vo.getReportingToUserId()).trim()
									+ "'   and ua.USER_ROLE='P'  and d.Rec_status='F' and ua.REC_STATUS='A' and d.DEAL_BRANCH='"
									+ StringEscapeUtils.escapeSql(
											vo.getBranchId()).trim() + "' ");

				}

			}

			if ((!(StringEscapeUtils.escapeSql(
					CommonFunction.checkNull(vo.getLbxDealNo())).trim()
					.equalsIgnoreCase("")))) {
				bufInsNewSql.append("AND d.DEAL_ID='"
						+ StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()
						+ "' ");
				bufInsSqlNewTempCount.append("AND d.DEAL_ID='"
						+ StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()
						+ "' ");
				appendSQL = true;

			}

			if ((!(StringEscapeUtils.escapeSql(
					CommonFunction.checkNull(vo.getCustomername())).trim()
					.equalsIgnoreCase("")))) {
				bufInsNewSql.append("AND deal.CUSTOMER_NAME like'%"
						+ StringEscapeUtils.escapeSql(vo.getCustomername())
								.trim() + "%' ");
				bufInsSqlNewTempCount.append("AND deal.CUSTOMER_NAME like'%"
						+ StringEscapeUtils.escapeSql(vo.getCustomername())
								.trim() + "%' ");
				appendSQL = true;
			}
			if ((!(StringEscapeUtils.escapeSql(
					CommonFunction.checkNull(vo.getLbxProductID())).trim()
					.equalsIgnoreCase("")))) {
				bufInsNewSql.append("AND n.DEAL_PRODUCT='"
						+ StringEscapeUtils.escapeSql(vo.getLbxProductID())
								.trim() + "' ");
				bufInsSqlNewTempCount.append("AND n.DEAL_PRODUCT='"
						+ StringEscapeUtils.escapeSql(vo.getLbxProductID())
								.trim() + "' ");
				appendSQL = true;
			}
			if ((!(StringEscapeUtils.escapeSql(
					CommonFunction.checkNull(vo.getLbxscheme())).trim()
					.equalsIgnoreCase("")))) {
				bufInsNewSql.append("AND n.DEAL_SCHEME='"
						+ StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()
						+ "' ");
				bufInsSqlNewTempCount.append("AND n.DEAL_SCHEME='"
						+ StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()
						+ "' ");
				appendSQL = true;
			}
			bufInsSqlNewTempCount.append(") tab");
			logger.info("query : " + bufInsNewSql);
			logger.info("bufInsSqlNewTempCount **************************** : "
					+ bufInsSqlNewTempCount.toString());

			// if((StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()==null
			// &&
			// StringEscapeUtils.escapeSql(vo.getCustomername()).trim()==null)
			// ||
			// (StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim().equalsIgnoreCase("")
			// &&
			// StringEscapeUtils.escapeSql(vo.getCustomername()).trim().equalsIgnoreCase(""))
			// ||
			// (StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim()==null
			// &&
			// StringEscapeUtils.escapeSql(vo.getLbxProductID()).trim().equalsIgnoreCase(""))
			// || ( StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()==null
			// &&
			// StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim().equalsIgnoreCase(""))
			// || vo.getCurrentPageLink()>1)
			// {

			logger.info("current PAge Link no .................... "
					+ vo.getCurrentPageLink());
			if (vo.getCurrentPageLink() > 1) {

				startRecordIndex = (vo.getCurrentPageLink() - 1) * no;
				endRecordIndex = no;
				logger.info("startRecordIndex .................... "
						+ startRecordIndex);
				logger.info("endRecordIndex .................... "
						+ endRecordIndex);
			}

			bufInsNewSql.append(" limit " + startRecordIndex + ","
					+ endRecordIndex);

			// }

			// //////////////////////////////////////////////////////

			logger.info("bufInsSqlTempCount -SEARCH QUERY-----------: "
					+ bufInsSqlTempCount);
			count = Integer.parseInt(ConnectionDAO
					.singleReturn(bufInsSqlTempCount.toString()));
			logger.info("count  .................... " + count);

			logger.info("bufInsSqlNewTempCount -SEARCH QUERY-----------: "
					+ bufInsSqlNewTempCount);
			int count1 = Integer.parseInt(ConnectionDAO
					.singleReturn(bufInsSqlNewTempCount.toString()));
			logger.info("count  .................... " + count1);

			count = count + count1;
			logger.info("Total Count.................... " + count);
			String newString = bufInsSql.toString() + " UNION "
					+ bufInsNewSql.toString();
			logger.info("SEARCH QUERY ------------: " + newString);
			header = ConnectionDAO.sqlSelect(newString);
			for (int i = 0; i < header.size(); i++) {

				logger.info("header: " + header.size());
				ArrayList header1 = (ArrayList) header.get(i);
				if (header1 != null && header1.size() > 0) {

					fetchVo = new CommonDealVo();

					fetchVo.setLbxDealNo((CommonFunction
							.checkNull(CommonFunction.checkNull(header1.get(0))))
							.trim());

					// dealCapturing.do?method=leadEntryCapturing&dealId="+val+"&status=UWA

					fetchVo.setDealNo("<a href=\"#\" onclick=\"searchDeal('"
							+ CommonFunction.checkNull(header1.get(0))
									.toString()
							+ "');\">"
							+ CommonFunction.checkNull(header1.get(1))
									.toString() + "</a>");
					fetchVo.setCustomername((CommonFunction.checkNull(header1
							.get(2))).trim());
					fetchVo.setProduct((CommonFunction.checkNull(header1.get(3)))
							.trim());
					fetchVo.setScheme((CommonFunction.checkNull(header1.get(4)))
							.trim());
					fetchVo.setReportingToUserId(userName);
					fetchVo.setTotalRecordSize(count);
					list.add(fetchVo);
				}
			}

			/*
			 * headerNew = ConnectionDAO.sqlSelect(bufInsNewSql.toString());
			 * for(int i=0;i<headerNew.size();i++){
			 * 
			 * logger.info("headerNew: "+headerNew.size()); ArrayList
			 * headerNew1=(ArrayList)headerNew.get(i); if(headerNew1!=null &&
			 * headerNew1.size()>0) {
			 * 
			 * fetchVo = new CommonDealVo();
			 * 
			 * fetchVo.setLbxDealNo((CommonFunction.checkNull(CommonFunction.
			 * checkNull(headerNew1.get(0)))).trim());
			 * 
			 * //dealCapturing.do?method=leadEntryCapturing&dealId="+val+"&status
			 * =UWA
			 * 
			 * fetchVo.setDealNo("<a href=\"#\" onclick=\"searchDeal('"+
			 * CommonFunction.checkNull(headerNew1.get(0)).toString()+ "');\">"+
			 * CommonFunction.checkNull(headerNew1.get(1)).toString() + "</a>");
			 * fetchVo
			 * .setCustomername((CommonFunction.checkNull(headerNew1.get(2
			 * ))).trim());
			 * fetchVo.setProduct((CommonFunction.checkNull(headerNew1
			 * .get(3))).trim());
			 * fetchVo.setScheme((CommonFunction.checkNull(headerNew1
			 * .get(4))).trim()); fetchVo.setReportingToUserId(userName);
			 * fetchVo.setTotalRecordSize(count); list.add(fetchVo); } }
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
		// if(header.size()==0)
		// {
		// CommonDealVo fetchVo = new CommonDealVo();
		// fetchVo.setTotalRecordSize(count);
		// list.add(fetchVo);
		// request.setAttribute("flag","yes");
		// logger.info("Detail List when searchList is null: "+list.size());
		// }
		// LoggerMsg.info("Detail List when searchList is : "+list.size());

		return list;
	}

	public ArrayList<CommonDealVo> deviationApprovalManualDetails(
			CommonDealVo vo) {
		ArrayList list = new ArrayList();
		int count = 0;
		int startRecordIndex = 0;
		int endRecordIndex = no;

		logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "
				+ vo.getReportingToUserId());
		String userNameQ = "select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"
				+ vo.getReportingToUserId() + "'";
		String userName = ConnectionDAO.singleReturn(userNameQ);
		logger.info("userNameQ: " + userNameQ + " userName: " + userName);
		ArrayList header = new ArrayList();
		try {
			logger.info("Inside deviationApprovalAllDetails.....");

			CommonDealVo fetchVo = (CommonDealVo) vo;
			boolean appendSQL = false;
			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();

			bufInsSql
					.append(" select distinct  d.DEAL_ID,DEAL_NO,deal.CUSTOMER_NAME,p.PRODUCT_DESC,s.SCHEME_DESC,d.DEAL_DATE,n.DEAL_TENURE,n.DEAL_LOAN_AMOUNT from cr_deal_dtl d  ");
			bufInsSqlTempCount
					.append(" select distinct  d.DEAL_ID,DEAL_NO,deal.CUSTOMER_NAME,p.PRODUCT_DESC,s.SCHEME_DESC,d.DEAL_DATE,n.DEAL_TENURE,n.DEAL_LOAN_AMOUNT from cr_deal_dtl d ");

			bufInsSql
					.append(" left join cr_deal_loan_dtl n on d.DEAL_ID=n.DEAL_ID  ");
			bufInsSqlTempCount
					.append(" left join cr_deal_loan_dtl n on d.DEAL_ID=n.DEAL_ID  ");

			bufInsSql
					.append("  left join cr_product_m p on n.DEAL_PRODUCT=p.PRODUCT_ID   ");
			bufInsSqlTempCount
					.append("  left join cr_product_m p on n.DEAL_PRODUCT=p.PRODUCT_ID   ");

			bufInsSql
					.append(" left join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID ");
			bufInsSqlTempCount
					.append(" left join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID ");

			bufInsSql
					.append(" left join cr_scheme_m s on n.DEAL_SCHEME=s.SCHEME_ID  ");
			bufInsSqlTempCount
					.append(" left join cr_scheme_m s on n.DEAL_SCHEME=s.SCHEME_ID  ");

			bufInsSql
					.append("  join cr_policy_decision md  on d.DEAL_ID=md.DEAL_ID and md.REC_STATUS ='I' and d.DEAL_BRANCH='1' and d.Rec_status='F' and  ");
			bufInsSqlTempCount
					.append("  join cr_policy_decision md  on d.DEAL_ID=md.DEAL_ID and md.REC_STATUS ='I' and d.DEAL_BRANCH='1' and d.Rec_status='F' and ");

			bufInsSql
					.append("  d.DEAL_ID in ( select distinct md.deal_id from cr_policy_decision md where md.REC_STATUS ='I') ");
			bufInsSqlTempCount
					.append("  d.DEAL_ID in ( select distinct md.deal_id from cr_policy_decision md where md.REC_STATUS ='I') ");

			// bufInsSqlTempCount.append("SELECT COUNT(1) FROM cr_deal_dtl d ");

			if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(
					vo.getLbxDealNo()).trim()).equalsIgnoreCase("")))
					&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(
							vo.getCustomername()).trim()).equalsIgnoreCase("")))
					&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(
							vo.getLbxProductID()).trim()).equalsIgnoreCase("")))
					&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(
							vo.getLbxscheme()).trim()).equalsIgnoreCase("")))) {
				bufInsSql.append("WHERE d.MAKER_ID!='"
						+ StringEscapeUtils
								.escapeSql(vo.getReportingToUserId()).trim()
						+ "' AND d.DEAL_BRANCH='"
						+ StringEscapeUtils.escapeSql(vo.getBranchId()).trim()
						+ "' and d.DEAL_ID='"
						+ StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()
						+ "' AND deal.CUSTOMER_NAME like'%"
						+ StringEscapeUtils.escapeSql(vo.getCustomername())
								.trim()
						+ "%' AND n.DEAL_PRODUCT='"
						+ StringEscapeUtils.escapeSql(vo.getLbxProductID())
								.trim() + "'AND n.DEAL_SCHEME='"
						+ StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()
						+ "' AND d.Rec_status='F'");
				bufInsSqlTempCount.append("WHERE d.MAKER_ID!='"
						+ StringEscapeUtils
								.escapeSql(vo.getReportingToUserId()).trim()
						+ "' AND d.DEAL_BRANCH='"
						+ StringEscapeUtils.escapeSql(vo.getBranchId()).trim()
						+ "' and d.DEAL_ID='"
						+ StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()
						+ "' AND deal.CUSTOMER_NAME like'%"
						+ StringEscapeUtils.escapeSql(vo.getCustomername())
								.trim()
						+ "%' AND n.DEAL_PRODUCT='"
						+ StringEscapeUtils.escapeSql(vo.getLbxProductID())
								.trim() + "'AND n.DEAL_SCHEME='"
						+ StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()
						+ "' AND d.Rec_status='F'");
			}

			if (((vo.getLbxDealNo().equalsIgnoreCase(""))
					|| (vo.getCustomername().equalsIgnoreCase(""))
					|| (vo.getLbxProductID().equalsIgnoreCase("")) || (vo
						.getLbxscheme().equalsIgnoreCase("")))) {
				appendSQL = true;
			}

			if (appendSQL) {
				logger.info("In Where Clause");
				// bufInsSql.append(" WHERE d.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and d.Rec_status='F' ");
				// bufInsSqlTempCount.append(" WHERE d.DEAL_BRANCH='"+StringEscapeUtils.escapeSql(vo.getBranchId()).trim()+"' and d.Rec_status='F' ");

				bufInsSql.append(" WHERE d.MAKER_ID!='"
						+ StringEscapeUtils
								.escapeSql(vo.getReportingToUserId()).trim()
						+ "'   ");
				bufInsSqlTempCount.append(" WHERE d.MAKER_ID!='"
						+ StringEscapeUtils
								.escapeSql(vo.getReportingToUserId()).trim()
						+ "'  ");

			}

			if ((!(StringEscapeUtils.escapeSql(
					CommonFunction.checkNull(vo.getLbxDealNo())).trim()
					.equalsIgnoreCase("")))) {
				bufInsSql.append("AND d.DEAL_ID='"
						+ StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()
						+ "' ");
				bufInsSqlTempCount.append("AND d.DEAL_ID='"
						+ StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()
						+ "' ");
				appendSQL = true;

			}

			if ((!(StringEscapeUtils.escapeSql(
					CommonFunction.checkNull(vo.getCustomername())).trim()
					.equalsIgnoreCase("")))) {
				bufInsSql.append("AND deal.CUSTOMER_NAME like'%"
						+ StringEscapeUtils.escapeSql(vo.getCustomername())
								.trim() + "%' ");
				bufInsSqlTempCount.append("AND deal.CUSTOMER_NAME like'%"
						+ StringEscapeUtils.escapeSql(vo.getCustomername())
								.trim() + "%' ");
				appendSQL = true;
			}
			if ((!(StringEscapeUtils.escapeSql(
					CommonFunction.checkNull(vo.getLbxProductID())).trim()
					.equalsIgnoreCase("")))) {
				bufInsSql.append("AND n.DEAL_PRODUCT='"
						+ StringEscapeUtils.escapeSql(vo.getLbxProductID())
								.trim() + "' ");
				bufInsSqlTempCount.append("AND n.DEAL_PRODUCT='"
						+ StringEscapeUtils.escapeSql(vo.getLbxProductID())
								.trim() + "' ");
				appendSQL = true;
			}
			if ((!(StringEscapeUtils.escapeSql(
					CommonFunction.checkNull(vo.getLbxscheme())).trim()
					.equalsIgnoreCase("")))) {
				bufInsSql.append("AND n.DEAL_SCHEME='"
						+ StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()
						+ "' ");
				bufInsSqlTempCount.append("AND n.DEAL_SCHEME='"
						+ StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()
						+ "' ");
				appendSQL = true;
			}
			LoggerMsg.info("query : " + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "
					+ bufInsSqlTempCount.toString());
			count = Integer.parseInt(ConnectionDAO
					.singleReturn(bufInsSqlTempCount.toString()));

			if ((StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim() == null && StringEscapeUtils
					.escapeSql(vo.getCustomername()).trim() == null)
					|| (StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()
							.equalsIgnoreCase("") && StringEscapeUtils
							.escapeSql(vo.getCustomername()).trim()
							.equalsIgnoreCase(""))
					|| (StringEscapeUtils.escapeSql(vo.getLbxProductID())
							.trim() == null && StringEscapeUtils
							.escapeSql(vo.getLbxProductID()).trim()
							.equalsIgnoreCase(""))
					|| (StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim() == null && StringEscapeUtils
							.escapeSql(vo.getLbxscheme()).trim()
							.equalsIgnoreCase(""))
					|| vo.getCurrentPageLink() > 1) {

				logger.info("current PAge Link no .................... "
						+ vo.getCurrentPageLink());
				if (vo.getCurrentPageLink() > 1) {
					startRecordIndex = (vo.getCurrentPageLink() - 1) * no;
					endRecordIndex = no;
					logger.info("startRecordIndex .................... "
							+ startRecordIndex);
					logger.info("endRecordIndex .................... "
							+ endRecordIndex);
				}

				bufInsSql.append(" limit " + startRecordIndex + ","
						+ endRecordIndex);

			}
			logger.info("query : " + bufInsSql);

			header = ConnectionDAO.sqlSelect(bufInsSql.toString());

			for (int i = 0; i < header.size(); i++) {

				logger.info("header: " + header.size());
				ArrayList header1 = (ArrayList) header.get(i);
				if (header1 != null && header1.size() > 0) {
					logger.info("header list size ....................."
							+ header.size());
					fetchVo = new CommonDealVo();

					fetchVo.setLbxDealNo((CommonFunction
							.checkNull(CommonFunction.checkNull(header1.get(0))))
							.trim());

					// dealCapturing.do?method=leadEntryCapturing&dealId="+val+"&status=UWA

					fetchVo.setDealNo("<a href=\"#\" onclick=\"searchDeal('"
							+ CommonFunction.checkNull(header1.get(0))
									.toString()
							+ "');\">"
							+ CommonFunction.checkNull(header1.get(1))
									.toString() + "</a>");
					fetchVo.setCustomername((CommonFunction.checkNull(header1
							.get(2))).trim());
					fetchVo.setProduct((CommonFunction.checkNull(header1.get(3)))
							.trim());
					fetchVo.setScheme((CommonFunction.checkNull(header1.get(4)))
							.trim());
					fetchVo.setReportingToUserId(userName);
					fetchVo.setTotalRecordSize(count);
					list.add(fetchVo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// if(header.size()==0)
		// {
		// CommonDealVo fetchVo = new CommonDealVo();
		// fetchVo.setTotalRecordSize(count);
		// list.add(fetchVo);
		// request.setAttribute("flag","yes");
		// logger.info("Detail List when searchList is null: "+list.size());
		// }
		// LoggerMsg.info("Detail List when searchList is : "+list.size());

		return list;
	}

	public ArrayList getDeviationApprovalDetails(DeviationApprovalVo Vo,
			String dealId) {
		ArrayList list = new ArrayList();
		try {
			logger.info("In getDeviationApprovalDetails..........................DAOImpl");

			StringBuilder query = new StringBuilder();

			query.append("select POLICY_DECISION_ID,DEAL_ID,");
			if (CommonFunction.checkNull(Vo.getCheckAuthorIdFlag())
					.equalsIgnoreCase("Y")) {
				query.append(" STAGE_ID, ");
			} else {
				query.append(" st.STAGE_ID, ");
			}

			query.append(" cp.RULE_CODE,replace(cp.RULE_EXPRESSION,'$',''),crm.RULE_DESCRIPTION,RULE_RESULT,RULE_ACTION,APPROVAL_LEVEL,");
			query.append(" case  cp.REC_STATUS when 'A' THEN 'AP' WHEN 'X' THEN 'R' else cp.REC_STATUS end as REC_STATUS,REMARKS from cr_policy_decision cp join cr_rule_m crm  on crm.RULE_CODE=cp.RULE_CODE ");
			if (!CommonFunction.checkNull(Vo.getCheckAuthorIdFlag())
					.equalsIgnoreCase("Y")) {
				query.append(" left outer join cr_stage_m st on cp.STAGE_ID=st.STAGE_ID ");
				// query.append(" join cr_user_approval_m ua on cp.APPROVAL_LEVEL=ua.LEVEL and ua.USER_ID='"+StringEscapeUtils.escapeSql(Vo.getMakerId()).trim()+"' AND ua.USER_ROLE='P' ");
			}
			query.append(" where DEAL_ID='"
					+ dealId
					+ "' and RULE_RESULT='T' and  ifNull(DEVIATION_TYPE,'A')<>'M'   ");
			if (CommonFunction.checkNull(Vo.getCheckAuthorIdFlag())
					.equalsIgnoreCase("Y")) {
				query.append("and cp.REC_STATUS in('I','P','A','X','AP','R')  order by cp.APPROVAL_LEVEL ");
			} else {
				query.append("and cp.REC_STATUS in('I','P','AP','R','A','X')  order by cp.APPROVAL_LEVEL ");
			}
			int level = 0;
			int count = Integer
					.parseInt(ConnectionDAO
							.singleReturn("SELECT count(1) FROM CR_USER_APPROVAL_M WHERE USER_ID='"
									+ Vo.getMakerId() + "' "));
			if (count > 0) {
				String q1 = "SELECT LEVEL FROM CR_USER_APPROVAL_M WHERE USER_ID='"
						+ Vo.getMakerId() + "' ";
				level = Integer.parseInt(ConnectionDAO.singleReturn(q1));
			}
			// String query =
			// "select POLICY_DECISION_ID,DEAL_ID,STAGE_ID,RULE_CODE,RULE_EXPRESSION,RULE_RESULT,RULE_ACTION,APPROVAL_LEVEL,"
			// +
			// "REC_STATUS,REMARKS from cr_policy_decision where DEAL_ID='"+dealId+"'";
			logger.info("query: " + query);
			DeviationApprovalVo vo = null;
			ArrayList dataList = ConnectionDAO.sqlSelect(query.toString());
			logger.info("dataList " + dataList.size());
			for (int i = 0; i < dataList.size(); i++) {
				logger.info("getNoteCode "
						+ CommonFunction.checkNull(dataList.get(i)).toString());
				ArrayList data = (ArrayList) dataList.get(i);
				if (data.size() > 0) {
					vo = new DeviationApprovalVo();

					vo.setPolicyDecisionId((CommonFunction.checkNull(data
							.get(0))).trim());
					vo.setDealId((CommonFunction.checkNull(data.get(1))).trim());
					vo.setStageId((CommonFunction.checkNull(data.get(2)))
							.trim());
					vo.setRuleCode((CommonFunction.checkNull(data.get(3)))
							.trim());
					vo.setRuleParamValues((CommonFunction.checkNull(data.get(4)))
							.trim());
					vo.setRuleDesc((CommonFunction.checkNull(data.get(5)))
							.trim());
					if ((CommonFunction.checkNull(data.get(6))).trim()
							.equalsIgnoreCase("T")) {
						vo.setRuleResult("TRUE");
					} else {
						vo.setRuleResult("FALSE");
					}

					if ((CommonFunction.checkNull(data.get(7))).trim()
							.equalsIgnoreCase("S")) {
						vo.setRuleAction("STOP");
					} else if ((CommonFunction.checkNull(data.get(7))).trim()
							.equalsIgnoreCase("D")) {
						vo.setRuleAction("DEVIATION");
					} else {
						vo.setRuleAction("PASS");
					}

					vo.setApprovalLevel((CommonFunction.checkNull(data.get(8)))
							.trim());
					int devationLevel = Integer.parseInt((CommonFunction
							.checkNull(data.get(8))).trim());
					vo.setRecStatus((CommonFunction.checkNull(data.get(9)))
							.trim());
					vo.setRemark((CommonFunction.checkNull(data.get(10)))
							.trim());
					if (devationLevel <= level) {
						vo.setRecordStatus("YES");
					} else {
						vo.setRecordStatus("NO");
					}
					list.add(vo);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public boolean forwardDeviationApproval(Object ob) {
		DeviationApprovalVo vo = (DeviationApprovalVo) ob;
		ArrayList updatelist = new ArrayList();

		String policyDecisionIds[] = vo.getPolicyDecisionIds();
		logger.info("In saveDeviationApproval...policyDecisionIds.length."
				+ policyDecisionIds.length);
		String manualPolicyId[] = vo.getManualPolicyId();
		logger.info("In saveDeviationApproval...manualPolicyId.length."
				+ manualPolicyId.length);

		String remarks[] = vo.getRemarks();
		String manualRemark[] = vo.getManualRemark();
		logger.info("In saveDeviationApproval...manualRemark.length."
				+ manualRemark.length);
		String recStatuses[] = vo.getRecStatuses();
		String manRecStatus[] = vo.getManRecStatus();
		String recStatus[] = vo.getRecStatusId();
		logger.info("In saveDeviationApproval...remark.length."
				+ recStatus.length);

		String autoRecStatus[] = vo.getAutoRecStatusId();
		logger.info("In saveDeviationApproval...autoRecStatus."
				+ autoRecStatus.length);

		boolean status = false;
		String stat = "";
		try {
			PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
			StringBuffer bufInsSql = new StringBuffer();
			int size = remarks.length;

			if (size != 0) {
				logger.info("In forwardDeviationApproval.....................................Dao Impl");

				bufInsSql
						.append("update cr_policy_decision set REMARKS=?, REC_STATUS=?,AUTHOR_ID=?,AUTHOR_DATE=DATE_ADD(STR_TO_DATE(?, '"
								+ dateFormatWithTime
								+ "'),INTERVAL CURTIME() HOUR_SECOND) "
								+ " where POLICY_DECISION_ID=? and DEVIATION_TYPE=? ");

				for (int i = 0; i < remarks.length; i++) {
					insertPrepStmtObject = null;
					insertPrepStmtObject = new PrepStmtObject();

					String query = "Select REC_STATUS from cr_policy_decision where POLICY_DECISION_ID='"
							+ CommonFunction.checkNull(policyDecisionIds[i])
							+ "' ";
					String previsousStatus = ConnectionDAO.singleReturn(query);
					if (CommonFunction.checkNull(remarks[i]).equalsIgnoreCase(
							""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(remarks[i]);
					// logger.info("i........................................... "+i);
					// logger.info("recstatus ........................................... "+recStatuses[i].toString());
					// if(CommonFunction.checkNull(recStatuses[i]).trim().equalsIgnoreCase(""))
					// insertPrepStmtObject.addString("A");
					// else
					// insertPrepStmtObject.addString("A");
					if (CommonFunction.checkNull(autoRecStatus[i])
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addString("I");
					else if (CommonFunction.checkNull(autoRecStatus[i])
							.equalsIgnoreCase("AP"))
						insertPrepStmtObject.addString("A");
					else if (CommonFunction.checkNull(autoRecStatus[i])
							.equalsIgnoreCase("R"))
						insertPrepStmtObject.addString("X");
					else if (CommonFunction.checkNull(autoRecStatus[i])
							.equalsIgnoreCase("S"))
						insertPrepStmtObject.addString("P");
					else if (CommonFunction.checkNull(autoRecStatus[i])
							.equalsIgnoreCase("A"))
						insertPrepStmtObject.addString("A");
					else if (CommonFunction.checkNull(autoRecStatus[i])
							.equalsIgnoreCase("X"))
						insertPrepStmtObject.addString("X");

					if (CommonFunction.checkNull(previsousStatus)
							.equalsIgnoreCase(autoRecStatus[i])) {
						String q1 = "Select AUTHOR_ID from cr_policy_decision where POLICY_DECISION_ID='"
								+ CommonFunction
										.checkNull(policyDecisionIds[i]) + "' ";
						String makerId = ConnectionDAO.singleReturn(q1);

						String q2 = "Select date_format(AUTHOR_DATE,'%d-%m-%Y') from cr_policy_decision where POLICY_DECISION_ID='"
								+ CommonFunction
										.checkNull(policyDecisionIds[i]) + "' ";
						String makerdate = ConnectionDAO.singleReturn(q2);
						if ((CommonFunction.checkNull(makerId).trim())
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((makerId).trim());

						if ((CommonFunction.checkNull(makerdate).trim())
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((makerdate).trim()); // maker_date
					} else {
						if ((CommonFunction.checkNull(vo.getMakerId()).trim())
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((vo.getMakerId())
									.trim());

						if ((CommonFunction.checkNull(vo.getMakerDate()).trim())
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((vo.getMakerDate())
									.trim()); // maker_date
					}
					if (CommonFunction.checkNull(policyDecisionIds[i])
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(policyDecisionIds[i]);
					if (CommonFunction.checkNull("A").equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else {
						insertPrepStmtObject.addString("A");
					}

					insertPrepStmtObject.setSql(bufInsSql.toString());
					logger.info(insertPrepStmtObject.printQuery());
					logger.info("In forward DeviationApproval for auto dev"
							+ bufInsSql.toString());
					updatelist.add(insertPrepStmtObject);

				}
			}
			bufInsSql = null;
			bufInsSql = new StringBuffer();
			size = manualRemark.length;
			if (size != 0) {
				bufInsSql
						.append("update cr_policy_decision set REMARKS=?, REC_STATUS=?,AUTHOR_ID=?,AUTHOR_DATE=DATE_ADD(STR_TO_DATE(?, '"
								+ dateFormatWithTime
								+ "'),INTERVAL CURTIME() HOUR_SECOND) "
								+ " where POLICY_DECISION_ID=?  and DEVIATION_TYPE=? ");

				for (int i = 0; i < manualRemark.length; i++) {
					insertPrepStmtObject = null;
					insertPrepStmtObject = new PrepStmtObject();
					String query = "Select REC_STATUS from cr_policy_decision where POLICY_DECISION_ID='"
							+ CommonFunction.checkNull(manualPolicyId[i])
							+ "' ";
					String previsousStatus = ConnectionDAO.singleReturn(query);
					if (CommonFunction.checkNull(manualRemark[i])
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(manualRemark[i]);
					// if(CommonFunction.checkNull(manRecStatus[i]).trim().equalsIgnoreCase(""))
					// insertPrepStmtObject.addString("A");
					// else
					// insertPrepStmtObject.addString("A");

					if (CommonFunction.checkNull(manRecStatus[i])
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addString("I");
					else if (CommonFunction.checkNull(manRecStatus[i])
							.equalsIgnoreCase("AP"))
						insertPrepStmtObject.addString("A");
					else if (CommonFunction.checkNull(manRecStatus[i])
							.equalsIgnoreCase("R"))
						insertPrepStmtObject.addString("X");
					else if (CommonFunction.checkNull(manRecStatus[i])
							.equalsIgnoreCase("S"))
						insertPrepStmtObject.addString("P");
					else if (CommonFunction.checkNull(manRecStatus[i])
							.equalsIgnoreCase("A"))
						insertPrepStmtObject.addString("A");
					else if (CommonFunction.checkNull(manRecStatus[i])
							.equalsIgnoreCase("X"))
						insertPrepStmtObject.addString("X");
					else
						insertPrepStmtObject.addString(manRecStatus[i]);

					if (CommonFunction.checkNull(previsousStatus)
							.equalsIgnoreCase(manRecStatus[i])) {
						String q1 = "Select AUTHOR_ID from cr_policy_decision where POLICY_DECISION_ID='"
								+ CommonFunction.checkNull(manualPolicyId[i])
								+ "' ";
						String makerId = ConnectionDAO.singleReturn(q1);

						String q2 = "Select date_format(AUTHOR_DATE,'%d-%m-%Y') from cr_policy_decision where POLICY_DECISION_ID='"
								+ CommonFunction.checkNull(manualPolicyId[i])
								+ "' ";
						String makerdate = ConnectionDAO.singleReturn(q2);
						if ((CommonFunction.checkNull(makerId).trim())
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((makerId).trim());

						if ((CommonFunction.checkNull(makerdate).trim())
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((makerdate).trim()); // maker_date
					} else {
						if ((CommonFunction.checkNull(vo.getMakerId()).trim())
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((vo.getMakerId())
									.trim());

						if ((CommonFunction.checkNull(vo.getMakerDate()).trim())
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((vo.getMakerDate())
									.trim()); // maker_date
					}
					if (CommonFunction.checkNull(manualPolicyId[i])
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(manualPolicyId[i]);
					if (CommonFunction.checkNull("M").equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else {
						insertPrepStmtObject.addString("M");
					}

					insertPrepStmtObject.setSql(bufInsSql.toString());
					logger.info(insertPrepStmtObject.printQuery());
					logger.info("In forward DeviationApproval for maual dev"
							+ bufInsSql.toString());
					updatelist.add(insertPrepStmtObject);

				}
			}
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(updatelist);
			// }
			// else {
			// status = false;
			// }

		} catch (Exception e) {
			e.printStackTrace();

		}
		return status;
	}

	// Manisha Manual Deviation Maker Search

	public ArrayList<CommonDealVo> manualDeviationMakerSearch(CommonDealVo vo) {
		ArrayList list = new ArrayList();
		int count = 0;
		int startRecordIndex = 0;
		int endRecordIndex = no;

		ArrayList header = new ArrayList();
		try {
			logger.info("Inside manualDeviationMakerSearch.....");
			logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "
					+ vo.getReportingToUserId());
			String userNameQ = "select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"
					+ vo.getReportingToUserId() + "'";
			String userName = ConnectionDAO.singleReturn(userNameQ);
			logger.info("userNameQ: " + userNameQ + " userName: " + userName);

			CommonDealVo fetchVo = (CommonDealVo) vo;
			boolean appendSQL = false;
			StringBuffer bufInsSql = new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();

			bufInsSql
					.append(" select distinct d.DEAL_ID,DEAL_NO,deal.CUSTOMER_NAME,p.PRODUCT_DESC,s.SCHEME_DESC,date(d.DEAL_DATE),n.DEAL_TENURE,n.DEAL_LOAN_AMOUNT from cr_deal_dtl d");
			bufInsSqlTempCount
					.append("SELECT COUNT(1) FROM (select distinct d.DEAL_ID,DEAL_NO,deal.CUSTOMER_NAME,p.PRODUCT_DESC,s.SCHEME_DESC,date(d.DEAL_DATE),n.DEAL_TENURE,n.DEAL_LOAN_AMOUNT from cr_deal_dtl d ");

			bufInsSql
					.append(" left join cr_deal_loan_dtl n on d.DEAL_ID=n.DEAL_ID  ");
			bufInsSqlTempCount
					.append(" left join cr_deal_loan_dtl n on d.DEAL_ID=n.DEAL_ID  ");

			bufInsSql
					.append(" left join cr_product_m p on n.DEAL_PRODUCT=p.PRODUCT_ID  ");
			bufInsSqlTempCount
					.append(" left join cr_product_m p on n.DEAL_PRODUCT=p.PRODUCT_ID  ");

			bufInsSql
					.append(" left join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID ");
			bufInsSqlTempCount
					.append(" left join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID ");

			bufInsSql
					.append(" left join cr_scheme_m s on n.DEAL_SCHEME=s.SCHEME_ID ");
			bufInsSqlTempCount
					.append(" left join cr_scheme_m s on n.DEAL_SCHEME=s.SCHEME_ID ");

			/*
			 * bufInsSql.append(
			 * " LEFT JOIN cr_stage_workflow_m SW ON n.DEAL_PRODUCT_CATEGORY=SW.PRODUCT_ID "
			 * ); bufInsSqlTempCount.append(
			 * " LEFT JOIN cr_stage_workflow_m SW ON n.DEAL_PRODUCT_CATEGORY=SW.PRODUCT_ID "
			 * );
			 */

			// bufInsSql.append(" INNER JOIN cr_deal_movement_dtl M ON M.DEAL_ID=D.DEAL_ID AND M.DEAL_RECEIVED<>'0000-00-00 00:00:00' AND M.DEAL_FORWARDED='0000-00-00 00:00:00' AND M.DEAL_STAGE_ID='POC' AND M.REC_STATUS='A' ");
			// bufInsSqlTempCount.append(" INNER JOIN cr_deal_movement_dtl M ON M.DEAL_ID=D.DEAL_ID AND M.DEAL_RECEIVED<>'0000-00-00 00:00:00' AND M.DEAL_FORWARDED='0000-00-00 00:00:00' AND M.DEAL_STAGE_ID='POC' AND M.REC_STATUS='A' ");

			// bufInsSql.append(" left outer join cr_manual_deviation_dtl md on d.DEAL_ID=md.DEAL_ID AND d.DEAL_ID in (select distinct md.DEAL_ID from cr_manual_deviation_dtl md where md.REC_STATUS<>'F' AND MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getReportingToUserId()).trim()+"')  ");
			// bufInsSqlTempCount.append(" left outer join cr_manual_deviation_dtl md on d.DEAL_ID=md.DEAL_ID AND d.DEAL_ID in (select distinct md.DEAL_ID from cr_manual_deviation_dtl md where md.REC_STATUS<>'F' AND MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getReportingToUserId()).trim()+"')  ");

			bufInsSql
					.append(" JOIN cr_manual_deviation_m MAS ON MAS.PRODUCT_ID=N.DEAL_PRODUCT AND (MAS.SCHEME_ID=N.DEAL_SCHEME OR ifnull(MAS.SCHEME_ID,'')='')  ");
			bufInsSqlTempCount
					.append("  JOIN cr_manual_deviation_m MAS ON MAS.PRODUCT_ID=N.DEAL_PRODUCT AND (MAS.SCHEME_ID=N.DEAL_SCHEME OR ifnull(MAS.SCHEME_ID,'')='') ");

			bufInsSql
					.append(" left outer join cr_manual_deviation_dtl md on d.DEAL_ID=md.DEAL_ID AND MAS.MANUAL_DEVIATION_ID=MD.MANUAL_DEVIATION_ID AND md.REC_STATUS='P' and MD.maker_id<>'"
							+ StringEscapeUtils.escapeSql(
									vo.getReportingToUserId()).trim() + "' ");
			bufInsSqlTempCount
					.append(" left outer join cr_manual_deviation_dtl md on d.DEAL_ID=md.DEAL_ID AND MAS.MANUAL_DEVIATION_ID=MD.MANUAL_DEVIATION_ID AND md.REC_STATUS='P' and MD.maker_id<>'"
							+ StringEscapeUtils.escapeSql(
									vo.getReportingToUserId()).trim() + "' ");

			if ((!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(
					vo.getLbxDealNo()).trim()).equalsIgnoreCase("")))
					&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(
							vo.getCustomername()).trim()).equalsIgnoreCase("")))
					&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(
							vo.getLbxProductID()).trim()).equalsIgnoreCase("")))
					&& (!(StringEscapeUtils.escapeSql(CommonFunction.checkNull(
							vo.getLbxscheme()).trim()).equalsIgnoreCase("")))) {
				bufInsSql.append("WHERE d.DEAL_BRANCH='"
						+ StringEscapeUtils.escapeSql(vo.getBranchId()).trim()
						+ "' and d.DEAL_ID='"
						+ StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()
						+ "' AND deal.CUSTOMER_NAME like'%"
						+ StringEscapeUtils.escapeSql(vo.getCustomername())
								.trim()
						+ "%' AND n.DEAL_PRODUCT='"
						+ StringEscapeUtils.escapeSql(vo.getLbxProductID())
								.trim() + "'AND n.DEAL_SCHEME='"
						+ StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()
						+ "' AND d.Rec_status='F'");
				bufInsSqlTempCount.append("WHERE d.DEAL_BRANCH='"
						+ StringEscapeUtils.escapeSql(vo.getBranchId()).trim()
						+ "' and d.DEAL_ID='"
						+ StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()
						+ "' AND deal.CUSTOMER_NAME like'%"
						+ StringEscapeUtils.escapeSql(vo.getCustomername())
								.trim()
						+ "%' AND n.DEAL_PRODUCT='"
						+ StringEscapeUtils.escapeSql(vo.getLbxProductID())
								.trim() + "'AND n.DEAL_SCHEME='"
						+ StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()
						+ "' AND d.Rec_status='F'");
			}
			if (((StringEscapeUtils.escapeSql(CommonFunction.checkNull(
					vo.getLbxDealNo()).trim()).equalsIgnoreCase("")))
					|| ((StringEscapeUtils.escapeSql(CommonFunction.checkNull(
							vo.getCustomername()).trim()).equalsIgnoreCase("")))
					|| ((StringEscapeUtils.escapeSql(CommonFunction.checkNull(
							vo.getLbxProductID()).trim()).equalsIgnoreCase("")))
					|| ((StringEscapeUtils.escapeSql(CommonFunction.checkNull(
							vo.getLbxscheme()).trim()).equalsIgnoreCase(""))))
			// if(((vo.getLbxDealNo().equalsIgnoreCase(""))
			// ||(vo.getCustomername().equalsIgnoreCase(""))||(vo.getLbxProductID().equalsIgnoreCase(""))||(vo.getLbxscheme().equalsIgnoreCase(""))))
			{
				appendSQL = true;
			}

			if (appendSQL) {
				logger.info("In Where Clause");
				bufInsSql.append(" WHERE d.DEAL_BRANCH='"
						+ StringEscapeUtils.escapeSql(vo.getBranchId()).trim()
						+ "'  ");
				bufInsSqlTempCount.append(" WHERE d.DEAL_BRANCH='"
						+ StringEscapeUtils.escapeSql(vo.getBranchId()).trim()
						+ "'  ");

				// bufInsSql.append(" AND d.DEAL_ID  in (SELECT DISTINCT DEAL_ID FROM cr_manual_deviation_dtl WHERE REC_STATUS='P' ) ");
				// bufInsSqlTempCount.append(" AND d.DEAL_ID  in (SELECT DISTINCT DEAL_ID FROM cr_manual_deviation_dtl WHERE REC_STATUS='P' ) ");

				/*bufInsSql
						.append("  AND (d.DEAL_ID in (select distinct cp.DEAL_ID from cr_policy_decision cp join cr_manual_deviation_m k on cp.manual_deviation_id <> k.manual_deviation_id and cp.rec_status = 'A' and cp.DEVIATION_TYPE='M' ) or "
								+ " d.deal_id not in (select distinct deal_id from cr_policy_decision where MANUAL_DEVIATION_ID is not null )) ");
				bufInsSqlTempCount
						.append(" AND (d.DEAL_ID in (select distinct cp.DEAL_ID from cr_policy_decision cp join cr_manual_deviation_m k on cp.manual_deviation_id <> k.manual_deviation_id and cp.rec_status = 'A' and cp.DEVIATION_TYPE='M' ) or "
								+ " d.deal_id not in (select distinct deal_id from cr_policy_decision where MANUAL_DEVIATION_ID is not null ))  ");
*/
				/*
				 * bufInsSql.append(
				 * " AND SW.STAGE_ID='POC' AND MAS.REC_STATUS='A' AND d.DEAL_ID  in (select DISTINCT DEAL_ID from cr_deal_movement_dtl where DEAL_FORWARDED='00-00-0000 00:00' and ifnull(DEAL_FORWARD_USER,'')='' AND DEAL_STAGE_ID='POC')"
				 * ); bufInsSqlTempCount.append(
				 * " AND SW.STAGE_ID='POC' AND MAS.REC_STATUS='A' AND d.DEAL_ID  in (select DISTINCT DEAL_ID from cr_deal_movement_dtl where DEAL_FORWARDED='00-00-0000 00:00' and ifnull(DEAL_FORWARD_USER,'')=''AND DEAL_STAGE_ID='POC')"
				 * );
				 */

				bufInsSql
						.append(" AND MAS.REC_STATUS='A' and date(d.DEAL_DATE) <> '0000-00-00' and d.REC_STATUS in ('P','F') ");
				bufInsSqlTempCount
						.append("  AND MAS.REC_STATUS='A' and date(d.DEAL_DATE) <> '0000-00-00'  and d.REC_STATUS in ('P','F') ");
			}

			if ((!(StringEscapeUtils.escapeSql(
					CommonFunction.checkNull(vo.getLbxDealNo())).trim()
					.equalsIgnoreCase("")))) {
				bufInsSql.append("AND d.DEAL_ID='"
						+ StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()
						+ "' ");
				bufInsSqlTempCount.append("AND d.DEAL_ID='"
						+ StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()
						+ "' ");
				appendSQL = true;

			}

			if ((!(StringEscapeUtils.escapeSql(
					CommonFunction.checkNull(vo.getCustomername())).trim()
					.equalsIgnoreCase("")))) {
				bufInsSql.append("AND deal.CUSTOMER_NAME like'%"
						+ StringEscapeUtils.escapeSql(vo.getCustomername())
								.trim() + "%' ");
				bufInsSqlTempCount.append("AND deal.CUSTOMER_NAME like'%"
						+ StringEscapeUtils.escapeSql(vo.getCustomername())
								.trim() + "%' ");
				appendSQL = true;
			}
			if ((!(StringEscapeUtils.escapeSql(
					CommonFunction.checkNull(vo.getLbxProductID())).trim()
					.equalsIgnoreCase("")))) {
				bufInsSql.append("AND n.DEAL_PRODUCT='"
						+ StringEscapeUtils.escapeSql(vo.getLbxProductID())
								.trim() + "' ");
				bufInsSqlTempCount.append("AND n.DEAL_PRODUCT='"
						+ StringEscapeUtils.escapeSql(vo.getLbxProductID())
								.trim() + "' ");
				appendSQL = true;
			}
			if ((!(StringEscapeUtils.escapeSql(
					CommonFunction.checkNull(vo.getLbxscheme())).trim()
					.equalsIgnoreCase("")))) {
				bufInsSql.append("AND n.DEAL_SCHEME='"
						+ StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()
						+ "' ");
				bufInsSqlTempCount.append("AND n.DEAL_SCHEME='"
						+ StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim()
						+ "' ");
				appendSQL = true;
			}
			bufInsSqlTempCount.append(") tab");
			LoggerMsg.info("query : " + bufInsSql);
			logger.info("bufInsSqlTempCount **************************** : "
					+ bufInsSqlTempCount.toString());
			count = Integer.parseInt(ConnectionDAO
					.singleReturn(bufInsSqlTempCount.toString()));

			if ((StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim() == null && StringEscapeUtils
					.escapeSql(vo.getCustomername()).trim() == null)
					|| (StringEscapeUtils.escapeSql(vo.getLbxDealNo()).trim()
							.equalsIgnoreCase("") && StringEscapeUtils
							.escapeSql(vo.getCustomername()).trim()
							.equalsIgnoreCase(""))
					|| (StringEscapeUtils.escapeSql(vo.getLbxProductID())
							.trim() == null && StringEscapeUtils
							.escapeSql(vo.getLbxProductID()).trim()
							.equalsIgnoreCase(""))
					|| (StringEscapeUtils.escapeSql(vo.getLbxscheme()).trim() == null && StringEscapeUtils
							.escapeSql(vo.getLbxscheme()).trim()
							.equalsIgnoreCase(""))
					|| vo.getCurrentPageLink() > 1) {

				logger.info("current PAge Link no .................... "
						+ vo.getCurrentPageLink());
				if (vo.getCurrentPageLink() > 1) {
					startRecordIndex = (vo.getCurrentPageLink() - 1) * no;
					endRecordIndex = no;
					logger.info("startRecordIndex .................... "
							+ startRecordIndex);
					logger.info("endRecordIndex .................... "
							+ endRecordIndex);
				}

				bufInsSql.append(" limit " + startRecordIndex + ","
						+ endRecordIndex);

			}
			logger.info("query : " + bufInsSql);

			header = ConnectionDAO.sqlSelect(bufInsSql.toString());

			for (int i = 0; i < header.size(); i++) {

				logger.info("header: " + header.size());
				ArrayList header1 = (ArrayList) header.get(i);
				if (header1 != null && header1.size() > 0) {
					logger.info("header list size ....................."
							+ header.size());
					fetchVo = new CommonDealVo();

					fetchVo.setLbxDealNo((CommonFunction
							.checkNull(CommonFunction.checkNull(header1.get(0))))
							.trim());

					// dealCapturing.do?method=leadEntryCapturing&dealId="+val+"&status=UWA

					fetchVo.setDealNo("<a href=\"#\" onclick=\"searchDeviationMakerDeals('"
							+ CommonFunction.checkNull(header1.get(0))
									.toString()
							+ "');\">"
							+ CommonFunction.checkNull(header1.get(1))
									.toString() + "</a>");
					fetchVo.setCustomername((CommonFunction.checkNull(header1
							.get(2))).trim());
					fetchVo.setProduct((CommonFunction.checkNull(header1.get(3)))
							.trim());
					fetchVo.setScheme((CommonFunction.checkNull(header1.get(4)))
							.trim());
					fetchVo.setReportingToUserId(userName);
					fetchVo.setTotalRecordSize(count);
					list.add(fetchVo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList getDealHeader(String dealId) {
		ArrayList list = new ArrayList();
		try {
			String query = " select d.deal_id, deal_no,deal.CUSTOMER_NAME,DATE_FORMAT(deal_date,'"
					+ dateFormat
					+ "'),p.PRODUCT_DESC,s.SCHEME_DESC,l.DEAL_PRODUCT_CATEGORY from cr_deal_dtl d "
					+ " left join cr_deal_loan_dtl l on d.DEAL_ID=l.DEAL_ID"
					+ " left join cr_product_m p on l.DEAL_PRODUCT=p.PRODUCT_ID"
					+ " left join cr_scheme_m s on l.DEAL_SCHEME=s.SCHEME_ID"
					+ " left join cr_deal_customer_m deal on deal.CUSTOMER_ID=d.DEAL_CUSTOMER_ID"
					+ " where d.DEAL_ID=" + dealId.trim();
			logger.info("Manual Dev getDealHeader()........................ query : "
					+ query);
			HeaderInfoVo vo = null;
			ArrayList header = ConnectionDAO.sqlSelect(query);
			for (int i = 0; i < header.size(); i++) {
				logger.info("header" + header.size());
				ArrayList header1 = (ArrayList) header.get(i);
				if (header1 != null && header1.size() > 0) {
					vo = new HeaderInfoVo();
					vo.setDealId((CommonFunction.checkNull(header1.get(0)))
							.trim());
					vo.setDealNo((CommonFunction.checkNull(header1.get(1)))
							.trim());
					vo.setDealCustomerName((CommonFunction.checkNull(header1
							.get(2))).trim());
					vo.setDealDate((CommonFunction.checkNull(header1.get(3)))
							.trim());
					vo.setDealProduct((CommonFunction.checkNull(header1.get(4)))
							.trim());
					vo.setDealScheme((CommonFunction.checkNull(header1.get(5)))
							.trim());
					vo.setDealProductCat((CommonFunction.checkNull(header1
							.get(6))).trim());
					list.add(vo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList manualDeviationM(DeviationApprovalVo Vo, String dealId) {
		ArrayList list = new ArrayList();
		try {
			logger.info("In manualDeviationM..........................DAOImpl");
			String query = "";
			if (CommonFunction.checkNull(Vo.getFunctionId()).equalsIgnoreCase(
					"3000206")) {
				query = " select distinct cdm.MANUAL_DEVIATION_ID,st.STAGE_DESC,m.RULE_DESC,cdm.RULE_RESULT,cdm.RULE_ACTION,cdm.APPROVAL_LEVEL,md.REMARKS,cdm.REMARKS,cdm.DEAL_ID,"
						+ " case  cdm.REC_STATUS when 'A' THEN 'AP' WHEN 'X' THEN 'R' else cdm.REC_STATUS end as REC_STATUS,cdm.POLICY_DECISION_ID from cr_policy_decision cdm  "
						+ " left outer join cr_stage_m st on cdm.STAGE_ID=st.STAGE_ID"
						+ " left join cr_manual_deviation_m m on m.MANUAL_DEVIATION_ID=cdm.MANUAL_DEVIATION_ID"
						+ " left join cr_manual_deviation_dtl md on md.MANUAL_DEVIATION_ID=cdm.MANUAL_DEVIATION_ID and md.DEAL_ID=cdm.DEAL_ID"
						+ " join cr_user_approval_m ua on  ua.USER_ROLE='P' "
						+ " where cdm.DEAL_ID='"
						+ dealId
						+ "' and RULE_RESULT='T' and cdm.DEVIATION_TYPE='M' order by cdm.APPROVAL_LEVEL ";
			} else if (CommonFunction.checkNull(Vo.getFunctionId())
					.equalsIgnoreCase("500000123")) {
				query = " select distinct cdm.MANUAL_DEVIATION_ID,st.STAGE_DESC,m.RULE_DESC,cdm.RULE_RESULT,cdm.RULE_ACTION,cdm.APPROVAL_LEVEL,md.REMARKS,cdm.REMARKS,cdm.DEAL_ID,"
						+ " case  cdm.REC_STATUS when 'A' THEN 'AP' WHEN 'X' THEN 'R' else cdm.REC_STATUS end as REC_STATUS,cdm.POLICY_DECISION_ID from cr_policy_decision cdm  "
						+ " left outer join cr_stage_m st on cdm.STAGE_ID=st.STAGE_ID"
						+ " left join cr_manual_deviation_m m on m.MANUAL_DEVIATION_ID=cdm.MANUAL_DEVIATION_ID"
						+ " left join cr_manual_deviation_dtl md on md.MANUAL_DEVIATION_ID=cdm.MANUAL_DEVIATION_ID and md.DEAL_ID=cdm.DEAL_ID"
						+ " join cr_user_approval_m ua on  ua.USER_ROLE='P' "
						+ " where cdm.REC_STATUS = 'A' and cdm.DEAL_ID='"
						+ dealId
						+ "' and RULE_RESULT='T' and cdm.DEVIATION_TYPE='M' order by cdm.APPROVAL_LEVEL ";
			} else {
				query = " select distinct cdm.MANUAL_DEVIATION_ID,st.STAGE_DESC,m.RULE_DESC,cdm.RULE_RESULT,cdm.RULE_ACTION,cdm.APPROVAL_LEVEL,md.REMARKS,cdm.REMARKS,cdm.DEAL_ID,"
						+ "case  cdm.REC_STATUS when 'A' THEN 'AP' WHEN 'X' THEN 'R' else cdm.REC_STATUS end as REC_STATUS,cdm.POLICY_DECISION_ID from cr_policy_decision cdm  "
						+ " left outer join cr_stage_m st on cdm.STAGE_ID=st.STAGE_ID"
						+ " left join cr_manual_deviation_m m on m.MANUAL_DEVIATION_ID=cdm.MANUAL_DEVIATION_ID"
						+ " left join cr_manual_deviation_dtl md on md.MANUAL_DEVIATION_ID=cdm.MANUAL_DEVIATION_ID and md.DEAL_ID=cdm.DEAL_ID"
						+ " join cr_user_approval_m ua on ua.USER_ROLE='P' "
						+ " and ua.USER_ID='"
						+ StringEscapeUtils.escapeSql(Vo.getMakerId()).trim()
						+ "'"
						+ " where cdm.REC_STATUS  in('I','P','AP','R','A','X') and cdm.DEAL_ID='"
						+ dealId
						+ "' and RULE_RESULT='T' and cdm.DEVIATION_TYPE='M' order by cdm.APPROVAL_LEVEL ";
			}
			int level = 0;
			int count = Integer
					.parseInt(ConnectionDAO
							.singleReturn("SELECT count(1) FROM CR_USER_APPROVAL_M WHERE USER_ID='"
									+ Vo.getMakerId() + "' and rec_status='A' and user_role='P' "));
			if (count > 0) {
				String q1 = "SELECT LEVEL FROM CR_USER_APPROVAL_M WHERE USER_ID='"
						+ Vo.getMakerId() + "' and rec_status='A' and user_role='P' ";
				level = Integer.parseInt(ConnectionDAO.singleReturn(q1));
			}
			logger.info("query: " + query);
			DeviationApprovalVo vo = null;
			ArrayList dataList = ConnectionDAO.sqlSelect(query);
			logger.info("dataList " + dataList.size());
			for (int i = 0; i < dataList.size(); i++) {
				logger.info("manualDeviationM "
						+ CommonFunction.checkNull(dataList.get(i)).toString());
				ArrayList data = (ArrayList) dataList.get(i);
				if (data.size() > 0) {
					vo = new DeviationApprovalVo();
					vo.setManualDevId((CommonFunction.checkNull(data.get(0)))
							.trim());
					vo.setStage((CommonFunction.checkNull(data.get(1))).trim());
					vo.setRuleDesc((CommonFunction.checkNull(data.get(2)))
							.trim());
					if ((CommonFunction.checkNull(data.get(3))).trim()
							.equalsIgnoreCase("T")) {
						vo.setRuleResult("TRUE");
					} else {
						vo.setRuleResult("FALSE");
					}
					vo.setRuleAction((CommonFunction.checkNull(data.get(4)))
							.trim());
					vo.setApprovalLevel((CommonFunction.checkNull(data.get(5)))
							.trim());
					int devationLevel = Integer.parseInt((CommonFunction
							.checkNull(data.get(5))).trim());
					vo.setRemark((CommonFunction.checkNull(data.get(6))).trim());
					vo.setAuthorRemark((CommonFunction.checkNull(data.get(7)))
							.trim());
					vo.setDealId((CommonFunction.checkNull(data.get(8))).trim());
					vo.setManRecStat((CommonFunction.checkNull(data.get(9)))
							.trim());
					vo.setManualId((CommonFunction.checkNull(data.get(10)))
							.trim());
					if (devationLevel <= level) {
						vo.setRecordStatus("YES");
					} else {
						vo.setRecordStatus("NO");
					}
					list.add(vo);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// //////////////////////////////////

	public String saveManualDeviation(DeviationApprovalVo vo,
			ArrayList<String> manualId, ArrayList<String> remarks,
			ArrayList<String> approvalLevel) {
		boolean status = false;
		logger.info("In saveManualDeviation..........");
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		StringBuilder bufInsSql = new StringBuilder();
		StringBuilder bufInsSqlTxn = new StringBuilder();
		String resultStr = "";
		String num = "";
		try {
			int size = manualId.size();

			String query = "select DEAL_ID from cr_manual_deviation_dtl where DEAL_ID='"
					+ CommonFunction.checkNull(vo.getDealId()) + "'";
			logger.info("In saveManualDeviation.....................................Dao Impl"
					+ query);
			boolean st = ConnectionDAO.checkStatus(query);
			if (st) {
				insertPrepStmtObject = null;
				insertPrepStmtObject = new PrepStmtObject();
				String qry = "delete from cr_manual_deviation_dtl where DEAL_ID='"
						+ CommonFunction.checkNull(vo.getDealId())
						+ "' and REC_STATUS<>'F' ";
				insertPrepStmtObject.setSql(qry);
				logger.info("IN (saveManualDeviation)delete query1 ### "
						+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
			}

			for (int i = 0; i < size; i++) {

				bufInsSql = null;
				bufInsSql = new StringBuilder();

				bufInsSql
						.append(" insert into cr_manual_deviation_dtl(MANUAL_DEVIATION_ID,DEAL_ID,RULE_ACTION,APPROVAL_LEVEL,REMARKS,REC_STATUS,MAKER_ID,MAKER_DATE )values(");
				bufInsSql.append(" ?,");// MANUAL_ID
				bufInsSql.append(" ?,");// DEAL_ID
				bufInsSql.append(" 'D',");// RULE_ACTION
				bufInsSql.append(" ?,");// APPROVAL_LEVEL
				bufInsSql.append(" ?,");// REMARKS
				bufInsSql.append(" 'P',");// REC_STATUS
				bufInsSql.append(" ?,");// MAKER_ID
				bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"
						+ dateFormatWithTime
						+ "'),INTERVAL CURTIME() HOUR_SECOND))");// MAKER_DATE

				insertPrepStmtObject = null;
				insertPrepStmtObject = new PrepStmtObject();
				if (CommonFunction.checkNull(manualId.get(i)).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(manualId.get(i).trim());
				if (CommonFunction.checkNull(vo.getDealId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getDealId());

				if (CommonFunction.checkNull(approvalLevel.get(i))
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(approvalLevel.get(i).trim());

				if (CommonFunction.checkNull(remarks.get(i)).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(remarks.get(i).trim());

				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());

				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());

				insertPrepStmtObject.setSql(bufInsSql.toString());

				logger.info("IN saveManualDeviation() insert query1 ### "
						+ insertPrepStmtObject.printQuery());

				qryList.add(insertPrepStmtObject);

			}

			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("In saveManualDeviation......................" + status);
			if (status) {
				resultStr = "Saved";

			} else {
				resultStr = "notSaved";
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			qryList = null;
			insertPrepStmtObject = null;
			stat = null;
			bufInsSql = null;

		}
		return resultStr;
	}

	public ArrayList manualDevList(String dealId) {
		ArrayList list = new ArrayList();
		try {
			logger.info("In manualDevList..........................DAOImpl");

			String query = " select m.MANUAL_DEVIATION_ID,p.PRODUCT_DESC,s.SCHEME_DESC,st.STAGE_DESC,m.RULE_DESC,m.APPROVAL_LEVEL,md.REMARKS"
					+ " from cr_manual_deviation_m m  "
					+ " left join cr_product_m p on m.PRODUCT_ID=p.PRODUCT_ID"
					+ " left join cr_scheme_m s on m.SCHEME_ID=s.SCHEME_ID"
					+ " left join cr_manual_deviation_dtl md on m.MANUAL_DEVIATION_ID=md.MANUAL_DEVIATION_ID and md.REC_STATUS='P'  and DEAL_ID='"
					+ dealId
					+ "'"
					+ " left outer join cr_stage_m st on m.STAGE_ID=st.STAGE_ID"
					+ " where m.REC_STATUS='A' and st.STAGE_ID='DC' "
					+ " and m.MANUAL_DEVIATION_ID not in (Select mdn.MANUAL_DEVIATION_ID from CR_POLICY_DECISION mdn where mdn.REC_STATUS in('X','F','A','R','AP') AND DEVIATION_TYPE='M' "
					+ " and mdn.DEAL_ID='"
					+ dealId
					+ "')  and m.PRODUCT_ID=(select DEAL_PRODUCT from cr_deal_loan_dtl where DEAL_ID='"
					+ dealId
					+ "') "
					+ " and (m.SCHEME_ID=(select DEAL_SCHEME from cr_deal_loan_dtl where DEAL_ID='"
					+ dealId + "')"
					+ "OR ifnull(m.SCHEME_ID,'')='')";

			logger.info("query: " + query);
			DeviationApprovalVo vo = null;
			ArrayList dataList = ConnectionDAO.sqlSelect(query);
			logger.info("dataList " + dataList.size());
			for (int i = 0; i < dataList.size(); i++) {
				logger.info("manualDevList "
						+ CommonFunction.checkNull(dataList.get(i)).toString());
				ArrayList data = (ArrayList) dataList.get(i);
				if (data.size() > 0) {
					vo = new DeviationApprovalVo();
					vo.setManualId((CommonFunction.checkNull(data.get(0)))
							.trim());
					vo.setProductDesc((CommonFunction.checkNull(data.get(1)))
							.trim());
					vo.setScheme((CommonFunction.checkNull(data.get(2))).trim());
					vo.setStage((CommonFunction.checkNull(data.get(3))).trim());
					vo.setRuleDesc((CommonFunction.checkNull(data.get(4)))
							.trim());
					vo.setApprovalLevel((CommonFunction.checkNull(data.get(5)))
							.trim());

					if (!(CommonFunction.checkNull(data.get(6)))
							.equalsIgnoreCase("")) {
						vo.setRemark((CommonFunction.checkNull(data.get(6)))
								.trim());
					}
					list.add(vo);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public String forwardManualDeviation(DeviationApprovalVo vo,
			ArrayList<String> manualId, ArrayList<String> remarks,
			ArrayList<String> approvalLevel) {
		boolean status = false;
		logger.info("In forwardManualDeviation..........");
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		StringBuilder bufInsSql = new StringBuilder();
		StringBuilder bufInsSqlTxn = new StringBuilder();
		String resultStr = "";
		String num = "";

		try {

			int size = manualId.size();
			for (int i = 0; i < size; i++) {
				insertPrepStmtObject = null;
				insertPrepStmtObject = new PrepStmtObject();

				String querry = "select DEAL_ID from cr_manual_deviation_dtl where DEAL_ID='"
						+ CommonFunction.checkNull(vo.getDealId()) + "'";
				logger.info("In Manual dev maker update.....................................Dao Impl"
						+ querry);
				boolean stt = ConnectionDAO.checkStatus(querry);
				if (stt) {
					String qry = "delete from cr_manual_deviation_dtl where DEAL_ID='"
							+ CommonFunction.checkNull(vo.getDealId())
							+ "' and REC_STATUS<>'F' and  MANUAL_DEVIATION_ID='"
							+ CommonFunction.checkNull(manualId.get(i)) + "'";
					insertPrepStmtObject.setSql(qry);
					qryList.add(insertPrepStmtObject);
				}

				bufInsSqlTxn = null;
				bufInsSqlTxn = new StringBuilder();

				bufInsSqlTxn
						.append(" insert into cr_manual_deviation_dtl(MANUAL_DEVIATION_ID,DEAL_ID,RULE_ACTION,APPROVAL_LEVEL,REMARKS,REC_STATUS,MAKER_ID,MAKER_DATE )values(");
				bufInsSqlTxn.append(" ?,");// MANUAL_ID
				bufInsSqlTxn.append(" ?,");// DEAL_ID
				bufInsSqlTxn.append(" 'D',");// RULE_ACTION
				bufInsSqlTxn.append(" ?,");// APPROVAL_LEVEL
				bufInsSqlTxn.append(" ?,");// REMARKS
				bufInsSqlTxn.append(" 'F',");// REC_STATUS
				bufInsSqlTxn.append(" ?,");// MAKER_ID
				bufInsSqlTxn.append(" DATE_ADD(STR_TO_DATE(?, '"
						+ dateFormatWithTime
						+ "'),INTERVAL CURTIME() HOUR_SECOND))");// MAKER_DATE

				insertPrepStmtObject = null;
				insertPrepStmtObject = new PrepStmtObject();
				if (CommonFunction.checkNull(manualId.get(i)).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(manualId.get(i).trim());
				if (CommonFunction.checkNull(vo.getDealId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getDealId());

				if (CommonFunction.checkNull(approvalLevel.get(i))
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(approvalLevel.get(i).trim());

				if (CommonFunction.checkNull(remarks.get(i)).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(remarks.get(i).trim());

				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());

				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());

				insertPrepStmtObject.setSql(bufInsSqlTxn.toString());

				logger.info("IN Manual dev maker update insert query1 ### "
						+ insertPrepStmtObject.printQuery());

				qryList.add(insertPrepStmtObject);
			}

			size = manualId.size();
			bufInsSql = null;
			bufInsSql = new StringBuilder();

			bufInsSql
					.append(" insert into cr_policy_decision(MANUAL_DEVIATION_ID,DEAL_ID,DEVIATION_TYPE,RULE_RESULT,RULE_ACTION,APPROVAL_LEVEL,REC_STATUS,MAKER_ID,MAKER_DATE,PRODUCT_ID,SCHEME_ID,STAGE_ID )");
			bufInsSql.append(" Select ");
			bufInsSql.append(" ?,");// MANUAL_DEVIATION_ID
			bufInsSql.append(" ?,");// DEAL_ID

			bufInsSql.append(" 'M',");// DEVIATION_TYPE
			bufInsSql.append(" 'T',");// RULE_RESULT
			bufInsSql.append(" 'D',");// RULE_ACTION
			bufInsSql.append(" ?,");// APPROVAL_LEVEL
			bufInsSql.append(" 'I',");// REC_STATUS
			bufInsSql.append(" ?,");// MAKER_ID
			bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '" + dateFormatWithTime
					+ "'),INTERVAL CURTIME() HOUR_SECOND)");// MAKER_DATE
			bufInsSql
					.append(" ,PRODUCT_ID,SCHEME_ID,STAGE_ID from  cr_manual_deviation_m  where MANUAL_DEVIATION_ID=?");// PRODUCT_ID,SCHEME_ID,STAGE_ID

			for (int i = 0; i < size; i++) {
				String query = "select DEAL_ID from cr_policy_decision where DEAL_ID='"
						+ CommonFunction.checkNull(vo.getDealId()) + "'";
				logger.info("In forwardManualDeviation.....................................Dao Impl"
						+ query);
				boolean st = ConnectionDAO.checkStatus(query);
				if (st) {
					insertPrepStmtObject = null;
					insertPrepStmtObject = new PrepStmtObject();
					String qry = "delete from cr_policy_decision where DEAL_ID='"
							+ CommonFunction.checkNull(vo.getDealId())
							+ "' and REC_STATUS<>'F' and DEVIATION_TYPE='M' and MANUAL_DEVIATION_ID='"
							+ CommonFunction.checkNull(manualId.get(i)) + "'";
					insertPrepStmtObject.setSql(qry);
					qryList.add(insertPrepStmtObject);
				}
				insertPrepStmtObject = null;
				insertPrepStmtObject = new PrepStmtObject();
				if (CommonFunction.checkNull(manualId.get(i)).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(manualId.get(i).trim());
				if (CommonFunction.checkNull(vo.getDealId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getDealId());

				if (CommonFunction.checkNull(approvalLevel.get(i))
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(approvalLevel.get(i).trim());

				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());

				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());

				if (CommonFunction.checkNull(manualId.get(i)).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(manualId.get(i).trim());

				insertPrepStmtObject.setSql(bufInsSql.toString());

				logger.info("IN forwardManualDeviation() insert query1 ### "
						+ insertPrepStmtObject.printQuery());

				qryList.add(insertPrepStmtObject);

			}

			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("In forwardManualDeviation......................"
					+ status);
			if (status) {
				resultStr = "Forwarded";

			} else {
				resultStr = "notForwarded";
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			qryList = null;
			insertPrepStmtObject = null;
			stat = null;
			bufInsSql = null;

		}
		return resultStr;
	}

	public String authorManualDeviation(DeviationApprovalVo vo) {
		boolean status = false;
		logger.info("In authorManualDeviation..........");
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		String stat = "X";
		StringBuilder bufInsSql = new StringBuilder();
		StringBuilder bufInsSqlTxn = new StringBuilder();
		String resultStr = "";
		String num = "";

		String remark[] = vo.getRemarks();
		logger.info("In authorManualDeviation...remark.length." + remark.length);
		String recStatus[] = vo.getRecStatuses();
		logger.info("In authorManualDeviation...recStatus.length."
				+ recStatus.length);
		String manualDeviId[] = vo.getManualDeviId();
		logger.info("In authorManualDeviation...manualDeviId.length."
				+ manualDeviId.length);

		try {

			int size = manualDeviId.length;

			bufInsSqlTxn
					.append("update cr_manual_deviation_dtl  set AUTHOR_REMARKS=?,REC_STATUS=?,AUTHOR_ID=?,AUTHOR_DATE=DATE_ADD(STR_TO_DATE(?, '"
							+ dateFormatWithTime
							+ "'),INTERVAL CURTIME() HOUR_SECOND) where MANUAL_ID=?");

			for (int i = 0; i < size; i++) {
				insertPrepStmtObject = null;
				insertPrepStmtObject = new PrepStmtObject();

				if (CommonFunction.checkNull(remark[i]).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(remark[i].trim());
				if (CommonFunction.checkNull(recStatus[i]).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(recStatus[i].trim());

				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerId());

				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getMakerDate());
				if (CommonFunction.checkNull(manualDeviId[i]).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(manualDeviId[i].trim());

				insertPrepStmtObject.setSql(bufInsSqlTxn.toString());

				logger.info("IN authorManualDeviation() update query1 ### "
						+ insertPrepStmtObject.printQuery());

				qryList.add(insertPrepStmtObject);

			}

			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("In authorManualDeviation......................"
					+ status);
			if (status) {
				resultStr = "Saved";

			} else {
				resultStr = "NotSaved";
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			qryList = null;
			insertPrepStmtObject = null;
			stat = null;
			bufInsSql = null;

		}
		return resultStr;
	}

	public String saveDeviationApproval(DeviationApprovalVo vo) {
		boolean status = false;
		logger.info("In saveDeviationApproval.. impl........");
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		StringBuilder bufInsSql = new StringBuilder();
		StringBuilder bufInsSqlTxn = new StringBuilder();
		String resultStr = "";

		String manualRemark[] = vo.getManualRemark();
		logger.info("In saveDeviationApproval...manualRemark.length."
				+ manualRemark.length);
		String manualDeviId[] = vo.getManualDeviId();
		logger.info("In saveDeviationApproval...manualDeviId.length."
				+ manualDeviId.length);
		String dealDevId[] = vo.getDealDevId();
		logger.info("In saveDeviationApproval...dealId.length."
				+ dealDevId.length);
		String remark[] = vo.getRemarks();
		logger.info("In saveDeviationApproval...remark.length." + remark.length);
		String recStatus[] = vo.getRecStatusId();
		logger.info("In saveDeviationApproval...recStatus.length."
				+ recStatus.length);

		String autoRecStatus[] = vo.getAutoRecStatusId();
		logger.info("In saveDeviationApproval...autoRecStatus."
				+ autoRecStatus.length);
		String policyDecisionIds[] = vo.getPolicyDecisionIds();
		logger.info("In saveDeviationApproval...autoRecStatus."
				+ autoRecStatus.length);

		try {

			int size = manualRemark.length;

			if (size != 0) {

				logger.info("In saveDeviationApproval...inside 1st this loop");
				bufInsSqlTxn
						.append("update cr_policy_decision  set REMARKS=?,AUTHOR_ID=?,REC_STATUS=?,AUTHOR_DATE=DATE_ADD(STR_TO_DATE(?, '"
								+ dateFormatWithTime
								+ "'),INTERVAL CURTIME() HOUR_SECOND) where MANUAL_DEVIATION_ID=? and DEAL_ID=? ");

				for (int i = 0; i < size; i++) {
					insertPrepStmtObject = null;
					insertPrepStmtObject = new PrepStmtObject();

					if (CommonFunction.checkNull(manualRemark[i])
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(manualRemark[i].trim());

					if (CommonFunction.checkNull(vo.getMakerId())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getMakerId());

					if (CommonFunction.checkNull(recStatus[i])
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addString("I");
					else
						insertPrepStmtObject.addString(recStatus[i].trim());

					if (CommonFunction.checkNull(vo.getMakerDate())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getMakerDate());
					if (CommonFunction.checkNull(manualDeviId[i])
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(manualDeviId[i].trim());

					if (CommonFunction.checkNull(dealDevId[i])
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(dealDevId[i].trim());

					insertPrepStmtObject.setSql(bufInsSqlTxn.toString());

					logger.info("IN saveDeviationApproval() update cr_policy_decision for manual devi ### "
							+ insertPrepStmtObject.printQuery());

					qryList.add(insertPrepStmtObject);

				}
			}

			size = remark.length;
			// bufInsSqlTxn=null;
			StringBuilder bufInsSqlTxn1 = new StringBuilder();

			if (size != 0) {

				logger.info("In saveDeviationApproval...inside 2nd this IF");

				bufInsSqlTxn1
						.append("update cr_policy_decision  set  REC_STATUS=?, REMARKS=?,AUTHOR_ID=?,AUTHOR_DATE=DATE_ADD(STR_TO_DATE(?, '"
								+ dateFormatWithTime
								+ "'),INTERVAL CURTIME() HOUR_SECOND) where DEAL_ID=? and ifNull(DEVIATION_TYPE,'A')<>'M' and RULE_RESULT='T' and POLICY_DECISION_ID=?");

				for (int i = 0; i < size; i++) {
					logger.info("In saveDeviationApproval...inside 2nd this loop");
					insertPrepStmtObject = null;
					insertPrepStmtObject = new PrepStmtObject();

					if (CommonFunction.checkNull(autoRecStatus[i])
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addString("I");
					else
						insertPrepStmtObject.addString(autoRecStatus[i].trim());

					if (CommonFunction.checkNull(remark[i])
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(remark[i].trim());

					if (CommonFunction.checkNull(vo.getMakerId())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getMakerId());

					if (CommonFunction.checkNull(vo.getMakerDate())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getMakerDate());

					if (CommonFunction.checkNull(dealDevId[i])
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(dealDevId[i].trim());

					if (CommonFunction.checkNull(policyDecisionIds[i])
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(policyDecisionIds[i]
								.trim());

					insertPrepStmtObject.setSql(bufInsSqlTxn1.toString());

					logger.info("IN saveDeviationApproval() update cr_policy_decision for auto devi  ### "
							+ insertPrepStmtObject.printQuery());

					qryList.add(insertPrepStmtObject);

				}
			}
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("In saveDeviationApproval......................"
					+ status);
			if (status) {
				resultStr = "Saved";

			} else {
				resultStr = "NotSaved";
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			qryList = null;
			insertPrepStmtObject = null;

			bufInsSql = null;

		}
		return resultStr;
	}

	public ArrayList<DeviationApprovalVo> getLoanAllDeviation(String loanId,
			String userId, String functionId) {
		ArrayList list = new ArrayList();

		try {
			StringBuilder sqlQuery = new StringBuilder();
			sqlQuery.append(" SELECT DISTINCT D.MANUAL_DEVIATION_ID,D.STAGE_ID,IFNULL(M.RULE_DESC,R.RULE_DESCRIPTION),D.DEVIATION_TYPE,D.RULE_ACTION,D.APPROVAL_LEVEL,D.REMARKS,D.LOAN_DEVIATION_ID,AUTHOR_REMARKS,D.rec_status FROM cr_loan_deviation_dtl D "
					+ " LEFT JOIN cr_manual_deviation_m M ON M.MANUAL_DEVIATION_ID=D.MANUAL_DEVIATION_ID AND M.REC_STATUS='A' "
					+ " LEFT JOIN cr_rule_m R ON R.RULE_CODE=D.RULE_CODE AND R.REC_STATUS='A' ");
			if (!userId.equalsIgnoreCase("")) {
				sqlQuery.append(" INNER JOIN cr_user_approval_m ua on  ua.USER_ID='"
						+ userId
						+ "' and ua.USER_ROLE='P' and ua.rec_status='A'");
				sqlQuery.append(" WHERE D.LOAN_ID='" + loanId
						+ "' AND  D.APPROVAL_LEVEL=ua.LEVEL ");
			} else {
				sqlQuery.append(" WHERE D.LOAN_ID='" + loanId + "' ");
			}
			if (CommonFunction.checkNull(functionId).trim()
					.equalsIgnoreCase("4000109"))
				sqlQuery.append(" and D.rec_status='F' ");
			logger.info("sqlQuery: " + sqlQuery);
			DeviationApprovalVo sqlVo = null;
			ArrayList sqlDataList = ConnectionDAO
					.sqlSelect(sqlQuery.toString());
			logger.info("sqlDataList " + sqlDataList.size());
			for (int i = 0; i < sqlDataList.size(); i++) {
				logger.info("loanManualDeviationWithMaster "
						+ CommonFunction.checkNull(sqlDataList.get(i))
								.toString());
				ArrayList data = (ArrayList) sqlDataList.get(i);
				if (data.size() > 0) {
					sqlVo = new DeviationApprovalVo();
					sqlVo.setManualDevId((CommonFunction.checkNull(data.get(0)))
							.trim());
					sqlVo.setStage((CommonFunction.checkNull(data.get(1)))
							.trim());
					sqlVo.setRuleDesc((CommonFunction.checkNull(data.get(2)))
							.trim());
					sqlVo.setDeviationType((CommonFunction.checkNull(data
							.get(3))).trim());
					sqlVo.setRuleAction((CommonFunction.checkNull(data.get(4)))
							.trim());
					sqlVo.setApprovalLevel((CommonFunction.checkNull(data
							.get(5))).trim());
					sqlVo.setRemark((CommonFunction.checkNull(data.get(6)))
							.trim());
					sqlVo.setLoanDevId((CommonFunction.checkNull(data.get(7)))
							.trim());
					sqlVo.setAuthorRemark((CommonFunction.checkNull(data.get(8)))
							.trim());
					sqlVo.setRecStatus((CommonFunction.checkNull(data.get(9)))
							.trim());
					list.add(sqlVo);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public String saveLoanDeviation(DeviationApprovalVo vo) {
		boolean status = false;
		logger.info("In saveManualDeviation..........");
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = null;
		PrepStmtObject updatePrepStmtObject = null;
		StringBuilder bufInsSql = null;
		StringBuilder bufUpdSql = null;
		String resultStr = "";
		try {
			String qquery = "";
			PrepStmtObject stmt = null;
			ArrayList list = new ArrayList();
			try {
				qquery = "update cr_loan_deviation_dtl set rec_status='P',REMARKS=null,MAKER_ID=null,MAKER_DATE=null where LOAN_ID='"
						+ CommonFunction.checkNull(vo.getLoanId()).trim()
						+ "' ";
				stmt = new PrepStmtObject();
				stmt.setSql(qquery);
				logger.info("IN deleteUserOldRecord() Delete query :  " + stmt);
				list.add(stmt);
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(list);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				qquery = null;
				stmt = null;
				list.clear();
			}
			String loanDeviationId[] = vo.getLoanDeviationId();
			String remarks[] = vo.getManualRemark();
			/*
			 * String checkQuery=
			 * "SELECT COUNT(*) FROM cr_loan_deviation_dtl WHERE LOAN_ID="
			 * +vo.getLoanId(); String
			 * check=ConnectionDAO.singleReturn(checkQuery);
			 * if(CommonFunction.checkNull(check).equalsIgnoreCase("0")) {
			 * for(int i=0;i<manualId.length;i++) {
			 * 
			 * bufInsSql = new StringBuilder(); bufInsSql.append(
			 * " insert into cr_loan_deviation_dtl(MANUAL_DEVIATION_ID,LOAN_ID,RULE_ACTION,APPROVAL_LEVEL,REMARKS,REC_STATUS,MAKER_ID,MAKER_DATE )values("
			 * ); bufInsSql.append(" ?,");// MANUAL_ID
			 * bufInsSql.append(" ?,");// LOAN_ID bufInsSql.append(" 'D',");//
			 * RULE_ACTION bufInsSql.append(" ?,");// APPROVAL_LEVEL
			 * bufInsSql.append(" ?,");// REMARKS bufInsSql.append(" 'P',");//
			 * REC_STATUS bufInsSql.append(" ?,");// MAKER_ID
			 * bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"
			 * +dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND))"
			 * );//MAKER_DATE
			 * 
			 * 
			 * insertPrepStmtObject=null; insertPrepStmtObject = new
			 * PrepStmtObject(); if
			 * (CommonFunction.checkNull(manualId[i]).equalsIgnoreCase(""))
			 * insertPrepStmtObject.addNull(); else
			 * insertPrepStmtObject.addString(manualId[i].trim()); if
			 * (CommonFunction.checkNull(vo.getLoanId()).equalsIgnoreCase(""))
			 * insertPrepStmtObject.addNull(); else
			 * insertPrepStmtObject.addString(vo.getLoanId());
			 * 
			 * if (CommonFunction.checkNull(approv[i]).equalsIgnoreCase(""))
			 * insertPrepStmtObject.addNull(); else
			 * insertPrepStmtObject.addString(approv[i].trim());
			 * 
			 * 
			 * if (CommonFunction.checkNull(remarks[i]).equalsIgnoreCase(""))
			 * insertPrepStmtObject.addNull(); else
			 * insertPrepStmtObject.addString(remarks[i].trim());
			 * 
			 * 
			 * if
			 * (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
			 * insertPrepStmtObject.addNull(); else
			 * insertPrepStmtObject.addString(vo.getMakerId());
			 * 
			 * if
			 * (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""
			 * )) insertPrepStmtObject.addNull(); else
			 * insertPrepStmtObject.addString(vo.getMakerDate());
			 * 
			 * insertPrepStmtObject.setSql(bufInsSql.toString());
			 * 
			 * logger.info("IN saveManualDeviation() insert query1 ### " +
			 * insertPrepStmtObject.printQuery());
			 * 
			 * qryList.add(insertPrepStmtObject);
			 * 
			 * } } else {
			 */
			for (int i = 0; i < loanDeviationId.length; i++) {
				bufUpdSql = new StringBuilder();
				bufUpdSql
						.append("update cr_loan_deviation_dtl  set rec_status='S',REMARKS=?,MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"
								+ dateFormatWithTime
								+ "'),INTERVAL CURTIME() HOUR_SECOND) where LOAN_DEVIATION_ID=?");
				updatePrepStmtObject = new PrepStmtObject();

				if (CommonFunction.checkNull(remarks[i]).equalsIgnoreCase(""))
					updatePrepStmtObject.addNull();
				else
					updatePrepStmtObject.addString(remarks[i].trim());

				if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(
						""))
					updatePrepStmtObject.addNull();
				else
					updatePrepStmtObject.addString(vo.getMakerId());

				if (CommonFunction.checkNull(vo.getMakerDate())
						.equalsIgnoreCase(""))
					updatePrepStmtObject.addNull();
				else
					updatePrepStmtObject.addString(vo.getMakerDate());

				if (CommonFunction.checkNull(loanDeviationId[i])
						.equalsIgnoreCase(""))
					updatePrepStmtObject.addNull();
				else
					updatePrepStmtObject.addString(loanDeviationId[i].trim());

				updatePrepStmtObject.setSql(bufUpdSql.toString());

				logger.info("IN saveManualDeviation() update query1 ### "
						+ updatePrepStmtObject.printQuery());

				qryList.add(updatePrepStmtObject);
			}

			// }
			if (loanDeviationId.length > 0) {
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("Inside IF Block saveManualDeviation......................"
						+ status);
			}
			logger.info("Outside IF Block saveManualDeviation......................"
					+ status);
			if (status) {
				resultStr = "Saved";

			} else {
				resultStr = "notSaved";
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			qryList = null;
			insertPrepStmtObject = null;
			bufInsSql = null;

		}
		return resultStr;
	}

	public boolean loanForwardDeviationApproval(Object ob) {
		DeviationApprovalVo vo = (DeviationApprovalVo) ob;
		ArrayList updatelist = new ArrayList();

		String manualRemark[] = vo.getManualRemark();
		logger.info("In saveDeviationApproval...manualRemark.length."
				+ manualRemark.length);
		String recStatuses[] = vo.getRecStatuses();
		String loanDeviationId[] = vo.getLoanDeviationId();

		boolean status = false;
		String stat = "";
		try {
			PrepStmtObject insertPrepStmtObject = null;
			StringBuffer bufInsSql = null;
			int size = loanDeviationId.length;
			logger.info("In forwardDeviationApproval.....................................Dao Impl "
					+ size);
			if (size != 0) {

				for (int i = 0; i < loanDeviationId.length; i++) {
					bufInsSql = new StringBuffer();

					bufInsSql
							.append("update cr_loan_deviation_dtl set AUTHOR_REMARKS=?, REC_STATUS='A',AUTHOR_ID=?,AUTHOR_DATE=DATE_ADD(STR_TO_DATE(?, '"
									+ dateFormatWithTime
									+ "'),INTERVAL CURTIME() HOUR_SECOND) "
									+ " where LOAN_DEVIATION_ID=? ");

					insertPrepStmtObject = new PrepStmtObject();

					if (CommonFunction.checkNull(manualRemark[i])
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(manualRemark[i]);

					if ((CommonFunction.checkNull(vo.getMakerId()).trim())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject
								.addString((vo.getMakerId()).trim());

					if ((CommonFunction.checkNull(vo.getMakerDate()).trim())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getMakerDate())
								.trim()); // maker_date

					if (CommonFunction.checkNull(loanDeviationId[i])
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(loanDeviationId[i]);

					insertPrepStmtObject.setSql(bufInsSql.toString());
					logger.info(insertPrepStmtObject.printQuery());
					logger.info("In forward DeviationApproval for auto dev"
							+ bufInsSql.toString());
					updatelist.add(insertPrepStmtObject);

				}
			}

			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(updatelist);
			// }
			// else {
			// status = false;
			// }

		} catch (Exception e) {
			e.printStackTrace();

		}
		return status;
	}

	@Override
	public boolean saveLoanManualDeviationFromMaster(String loanId,
			String userId, String bDate) {

		boolean status = false;
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = null;
		StringBuilder bufInsSql = null;
		try {

			logger.info("In loanManualDeviationWithMaster..........................DAOImpl");
			String checkQuery = "SELECT COUNT(*) FROM cr_loan_deviation_dtl WHERE MANUAL_DEVIATION_ID IS NOT NULL AND LOAN_ID="
					+ loanId;
			String check = ConnectionDAO.singleReturn(checkQuery);
			if (CommonFunction.checkNull(check).equalsIgnoreCase("0")) {
				String query = " SELECT DISTINCT M.MANUAL_DEVIATION_ID,M.STAGE_ID,M.DEVIATION_TYPE,M.RULE_ACTION,M.APPROVAL_LEVEL FROM cr_manual_deviation_m M "
						+ " LEFT JOIN CR_LOAN_DTL L ON M.PRODUCT_ID=L.LOAN_PRODUCT AND M.SCHEME_ID=L.LOAN_SCHEME "
						+ " WHERE M.REC_STATUS='A' AND STAGE_ID='LIM' AND L.LOAN_ID="
						+ loanId;

				logger.info("query: " + query);
				ArrayList dataList = ConnectionDAO.sqlSelect(query);
				logger.info("dataList " + dataList.size());
				for (int i = 0; i < dataList.size(); i++) {
					logger.info("loanManualDeviationWithMaster "
							+ CommonFunction.checkNull(dataList.get(i))
									.toString());
					ArrayList data = (ArrayList) dataList.get(i);
					if (data.size() > 0) {

						bufInsSql = new StringBuilder();
						bufInsSql
								.append(" insert into cr_loan_deviation_dtl(MANUAL_DEVIATION_ID,LOAN_ID,RULE_ACTION,APPROVAL_LEVEL,REMARKS,STAGE_ID,DEVIATION_TYPE,REC_STATUS,MAKER_ID,MAKER_DATE )values(");
						bufInsSql.append(" ?,");// MANUAL_ID
						bufInsSql.append(" ?,");// LOAN_ID
						bufInsSql.append(" ?,");// RULE_ACTION
						bufInsSql.append(" ?,");// APPROVAL_LEVEL
						bufInsSql.append(" ?,");// REMARKS
						bufInsSql.append(" ?,");// STAGE_ID
						bufInsSql.append(" ?,");// DEVIATION_TYPE
						bufInsSql.append(" ?,");// REC_STATUS
						bufInsSql.append(" ?,");// MAKER_ID
						bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"
								+ dateFormatWithTime
								+ "'),INTERVAL CURTIME() HOUR_SECOND))");// MAKER_DATE

						insertPrepStmtObject = null;
						insertPrepStmtObject = new PrepStmtObject();
						if (CommonFunction.checkNull(
								(CommonFunction.checkNull(data.get(0))).trim())
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction
									.checkNull(data.get(0))).trim());
						if (CommonFunction.checkNull(loanId).equalsIgnoreCase(
								""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(loanId);

						if (CommonFunction.checkNull(
								(CommonFunction.checkNull(data.get(3))).trim())
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction
									.checkNull(data.get(3))).trim());

						if (CommonFunction.checkNull(
								(CommonFunction.checkNull(data.get(4))).trim())
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction
									.checkNull(data.get(4))).trim());

						insertPrepStmtObject.addNull();

						if (CommonFunction.checkNull(
								(CommonFunction.checkNull(data.get(1))).trim())
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction
									.checkNull(data.get(1))).trim());

						if (CommonFunction.checkNull(
								(CommonFunction.checkNull(data.get(2))).trim())
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString((CommonFunction
									.checkNull(data.get(2))).trim());

						insertPrepStmtObject.addString("P");

						if (CommonFunction.checkNull(userId).equalsIgnoreCase(
								""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(userId);

						if (CommonFunction.checkNull(bDate)
								.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(bDate);

						insertPrepStmtObject.setSql(bufInsSql.toString());

						logger.info("IN saveManualDeviation() insert query1 ### "
								+ insertPrepStmtObject.printQuery());

						qryList.add(insertPrepStmtObject);
						status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
						logger.info("In saveManualDeviation......................"
								+ status);

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public ArrayList manualDeviationUND(DeviationApprovalVo Vo, String dealId) {
		ArrayList list = new ArrayList();
		try {
			logger.info("In manualDeviationUND..........................DAOImpl");
			String productQry="select deal_product from cr_deal_loan_dtl where deal_id='"+dealId+"' limit 1";
			String product=ConnectionDAO.singleReturn(productQry);
			String schemeQuery="select deal_scheme from cr_deal_loan_dtl where deal_id='"+dealId+"' limit 1";
			String scheme=ConnectionDAO.singleReturn(schemeQuery);
			String amountQuery="select SUM(DEAL_LOAN_AMOUNT) from cr_deal_loan_dtl where deal_id='"+dealId+"' ";
			String amount=ConnectionDAO.singleReturn(amountQuery);
			String qr1="SELECT ifnull(FINAL_APPROVAL_LEVEL,0) FROM CR_APPROVAL_LEVEL_M WHERE PRODUCT_ID='"+product+"' AND SCHEME_ID='"+scheme+"' AND AMOUNT_FROM<='"+amount+"' AND '"+amount+"'<=AMOUNT_TO  AND REC_STATUS='A' limit 1";
			int finalLevel=Integer.parseInt(ConnectionDAO.singleReturn(qr1));		
			String query = " select distinct cdm.MANUAL_DEVIATION_ID,st.STAGE_ID,m.RULE_DESC,cdm.RULE_RESULT,cdm.RULE_ACTION,cdm.APPROVAL_LEVEL,md.REMARKS,cdm.REMARKS,cdm.DEAL_ID,"
					+ " case  cdm.REC_STATUS when 'AP' THEN 'A' WHEN 'R' THEN 'X' else cdm.REC_STATUS end as REC_STATUS,cdm.POLICY_DECISION_ID from cr_policy_decision cdm  "
					+ " left outer join cr_stage_m st on cdm.STAGE_ID=st.STAGE_ID"
					+ " left join cr_manual_deviation_m m on m.MANUAL_DEVIATION_ID=cdm.MANUAL_DEVIATION_ID"
					+ " left join cr_manual_deviation_dtl md on md.MANUAL_DEVIATION_ID=cdm.MANUAL_DEVIATION_ID and md.DEAL_ID=cdm.DEAL_ID"
					+ " join cr_user_approval_m ua on cdm.APPROVAL_LEVEL=ua.LEVEL"
					+ " where cdm.DEAL_ID='"
					+ dealId
					+ "' and st.STAGE_ID='DC' and cdm.deviation_type <> 'A' order by cdm.APPROVAL_LEVEL ";

			logger.info("query: " + query);
			int level = 0;
			int count = Integer
					.parseInt(ConnectionDAO
							.singleReturn("SELECT count(1) FROM CR_USER_APPROVAL_M WHERE USER_ID='"
									+ Vo.getMakerId() + "' and rec_status='A' and user_role='P' "));
			if (count > 0) {
				String q1 = "SELECT LEVEL FROM CR_USER_APPROVAL_M WHERE USER_ID='"
						+ Vo.getMakerId() + "' and rec_status='A' and user_role='P' ";
				level = Integer.parseInt(ConnectionDAO.singleReturn(q1));
			}
			DeviationApprovalVo vo = null;
			ArrayList dataList = ConnectionDAO.sqlSelect(query);
			logger.info("dataList " + dataList.size());
			for (int i = 0; i < dataList.size(); i++) {
				logger.info("manualDeviationUND "
						+ CommonFunction.checkNull(dataList.get(i)).toString());
				ArrayList data = (ArrayList) dataList.get(i);
				if (data.size() > 0) {
					vo = new DeviationApprovalVo();
					vo.setManualDevId((CommonFunction.checkNull(data.get(0)))
							.trim());
					vo.setStage((CommonFunction.checkNull(data.get(1))).trim());
					vo.setRuleDesc((CommonFunction.checkNull(data.get(2)))
							.trim());
					if ((CommonFunction.checkNull(data.get(3))).trim()
							.equalsIgnoreCase("T")) {
						vo.setRuleResult("TRUE");
					} else {
						vo.setRuleResult("FALSE");
					}
					vo.setRuleAction((CommonFunction.checkNull(data.get(4)))
							.trim());
					vo.setApprovalLevel((CommonFunction.checkNull(data.get(5)))
							.trim());
					int devationLevel = Integer.parseInt((CommonFunction
							.checkNull(data.get(5))).trim());
					vo.setRemark((CommonFunction.checkNull(data.get(6))).trim());
					vo.setAuthorRemark((CommonFunction.checkNull(data.get(7)))
							.trim());
					vo.setDealId((CommonFunction.checkNull(data.get(8))).trim());
					vo.setManRecStat((CommonFunction.checkNull(data.get(9)))
							.trim());
					vo.setManualId((CommonFunction.checkNull(data.get(10)))
							.trim());
					/*if(finalLevel<=level){
						vo.setRecordStatus("YES");
					}else{*/
					if (devationLevel <= level) {
						vo.setRecordStatus("YES");
					} else {
						vo.setRecordStatus("NO");
					}
					/*}*/

					list.add(vo);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public boolean loansendBackDeviation(Object ob) {
		DeviationApprovalVo vo = (DeviationApprovalVo) ob;
		ArrayList updatelist = new ArrayList();

		String manualRemark[] = vo.getManualRemark();
		logger.info("In loansendBackDeviation...manualRemark.length."
				+ manualRemark.length);
		String recStatuses[] = vo.getRecStatuses();
		String loanDeviationId[] = vo.getLoanDeviationId();

		boolean status = false;
		String stat = "";
		try {
			PrepStmtObject insertPrepStmtObject = null;
			StringBuffer bufInsSql = null;
			int size = loanDeviationId.length;
			logger.info("In loansendBackDeviation.....................................Dao Impl "
					+ size);
			if (size != 0) {

				ArrayList qryList = new ArrayList();
				logger.info("In loansendBackDeviation.......Update mode "
						+ vo.getLoanId());

				String query1 = "update cr_loan_dtl set REC_STATUS='P' where LOAN_ID="
						+ vo.getLoanId();

				String query2 = "update cr_loan_collateral_m set REC_STATUS='P' where LOAN_ID="
						+ vo.getLoanId();

				String query3 = "update cr_txncharges_dtl set REC_STATUS='P' where TXN_ID="
						+ vo.getLoanId();

				String query4 = "update cr_sd_m set REC_STATUS='P' where TXN_TYPE='LIM'and TXN_ID="
						+ vo.getLoanId();

				String query5 = "update cr_document_dtl set REC_STATUS='P' where TXNID="
						+ vo.getLoanId();

				qryList.add(query1);
				qryList.add(query2);
				qryList.add(query3);
				qryList.add(query4);
				qryList.add(query5);

				boolean status1 = false;

				try {
					status = ConnectionDAO.sqlInsUpdDelete(qryList);
					logger.info("In loansendBackDeviation......................status= "
							+ status1);
				} catch (SQLException e) {

					e.printStackTrace();
				}

				for (int i = 0; i < loanDeviationId.length; i++) {
					bufInsSql = new StringBuffer();

					bufInsSql
							.append("update cr_loan_deviation_dtl set AUTHOR_REMARKS=?, REC_STATUS='P',AUTHOR_ID=?,AUTHOR_DATE=DATE_ADD(STR_TO_DATE(?, '"
									+ dateFormatWithTime
									+ "'),INTERVAL CURTIME() HOUR_SECOND) "
									+ " where LOAN_DEVIATION_ID=? ");

					insertPrepStmtObject = new PrepStmtObject();

					if (CommonFunction.checkNull(manualRemark[i])
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(manualRemark[i]);

					if ((CommonFunction.checkNull(vo.getMakerId()).trim())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject
								.addString((vo.getMakerId()).trim());

					if ((CommonFunction.checkNull(vo.getMakerDate()).trim())
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString((vo.getMakerDate())
								.trim()); // maker_date

					if (CommonFunction.checkNull(loanDeviationId[i])
							.equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(loanDeviationId[i]);

					insertPrepStmtObject.setSql(bufInsSql.toString());
					logger.info(insertPrepStmtObject.printQuery());
					logger.info("In forward DeviationApproval for auto dev"
							+ bufInsSql.toString());
					updatelist.add(insertPrepStmtObject);

				}
			}

			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(updatelist);
			// }
			// else {
			// status = false;
			// }

		} catch (Exception e) {
			e.printStackTrace();

		}
		return status;
	}

	@Override
	public String countApproveDeviation(String dealId) {
		logger.info("In countApproveDeviation");
		String result = "";
		try {
			StringBuilder query = new StringBuilder();
			query.append("select COUNT(1) from CR_POLICY_DECISION where REC_STATUS IN('I','P') AND DEAL_ID='"
					+ dealId + "'");
			logger.info("In countApproveDeviation" + query.toString());
			result = (String) ConnectionDAO.singleReturn(query.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Method used to delete any deviation from UI.
	 */
	public String deleteDeviationUND(DeviationApprovalVo vo,
			String[] deleteIdFin) {
		boolean status = false;
		logger.info("In deleteDeviationApproval.. impl........");
		ArrayList qryList = new ArrayList();
		PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		PrepStmtObject insertPrepStmtObject1 = new PrepStmtObject();
		StringBuilder bufInsSql = new StringBuilder();
		StringBuilder bufInsSqlTxn = new StringBuilder();
		String resultStr = "";
		String[] manualDeviId = vo.getManualDeviId();
		logger.info(new StringBuilder()
				.append("In deleteDeviationApproval...manualDeviId.length.") 
				.append(manualDeviId.length).toString());
		try {

			logger.info("In deleteDeviationApproval...inside 1st this loop");
			for (int i = 0; i < deleteIdFin.length; i++) {
				String q1="select count(1) from cr_policy_decision where MANUAL_DEVIATION_ID='"+deleteIdFin[i]+"' and DEAL_ID='"+vo.getDealId()+"' and rec_status='I' ";
				int count=Integer.parseInt(ConnectionDAO.singleReturn(q1));
				if(count>0){
					bufInsSqlTxn=new StringBuilder();
					bufInsSql=new StringBuilder();
			bufInsSqlTxn
					.append(new StringBuilder()
							.append("delete from cr_policy_decision where MANUAL_DEVIATION_ID=? and DEAL_ID=? ")
							.toString());
			bufInsSql
					.append(new StringBuilder()
							.append("delete from cr_manual_deviation_dtl where MANUAL_DEVIATION_ID=? ")
							.toString());

			
				insertPrepStmtObject = null;
				insertPrepStmtObject = new PrepStmtObject();
				insertPrepStmtObject1 = null;
				insertPrepStmtObject1 = new PrepStmtObject();
				qryList=new ArrayList();
				if (CommonFunction.checkNull(deleteIdFin[i]).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else {
					insertPrepStmtObject.addString(deleteIdFin[i].trim());
				}

				if (CommonFunction.checkNull(deleteIdFin[i]).equalsIgnoreCase(
						""))
					insertPrepStmtObject1.addNull();
				else {
					insertPrepStmtObject1.addString(deleteIdFin[i].trim());
				}
				if (CommonFunction.checkNull(vo.getDealId()).equalsIgnoreCase(
						""))
					insertPrepStmtObject.addNull();
				else {
					insertPrepStmtObject.addString(vo.getDealId());
				}

				insertPrepStmtObject.setSql(bufInsSqlTxn.toString());
				insertPrepStmtObject1.setSql(bufInsSql.toString());

				logger.info(new StringBuilder()
						.append("IN deleteDeviationApproval delete cr_policy_decision for manual devi ### ")
						.append(insertPrepStmtObject.printQuery()).toString());
				logger.info(new StringBuilder()
						.append("IN deleteDeviationApproval delete cr_manual_deviation_dtl for manual devi ### ")
						.append(insertPrepStmtObject1.printQuery()).toString());

				qryList.add(insertPrepStmtObject);
				qryList.add(insertPrepStmtObject1);
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				}
			}

			

			logger.info(new StringBuilder()
					.append("In deleteDeviationApproval......................")
					.append(status).toString());
			if (status) {
				resultStr = "Saved";
			} else {
				resultStr = "NotSaved";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			qryList = null;
			insertPrepStmtObject = null;
			bufInsSql = null;
		}

		return resultStr;
	}

}



