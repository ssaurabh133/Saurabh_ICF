package com.cm.daoImplMYSQL;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.cm.dao.LoanInitiationDAO;
import com.cm.dao.ReportsDAO;
import com.cm.vo.CommonLoanVo;
import com.cm.vo.CompoundFrequencyVO;
import com.cm.vo.InstallmentPlanForCMVO;
import com.cm.vo.LoanDetailForCMVO;
import com.cm.vo.LoanInitAuthorVo;
import com.cm.vo.NoOfDisbursalVo;
import com.cm.vo.SecurityDepositVO;
import com.cm.vo.TenureAmountVo;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.connect.PrepStmtObject;
import com.cp.vo.ChargeVo;
import com.cp.vo.CodeDescVo;
import com.cp.vo.CollateralVo;
import com.cp.vo.CreditProcessingCustomerEntryVo;
import com.cp.vo.HeaderInfoVo;
import com.cp.vo.LoanDetailVo;
import com.cp.vo.RepayScheduleVo;
import com.cp.vo.UnderwriterApprovalVo;
import com.logger.LoggerMsg;
import com.tabDependencyCheck.RefreshFlagValueInsert;
import com.tabDependencyCheck.RefreshFlagVo;

import java.io.BufferedReader;
import java.io.DataOutputStream;import java.net.MalformedURLException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.map.HashedMap;

import com.cm.vo.CmInsuranceVo;

import java.net.MalformedURLException;

public class LoanInitiationDAOImpl implements LoanInitiationDAO {
	
	private static final Logger logger = Logger.getLogger(LoanInitiationDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));


	public boolean saveDefaultValue(String loanId,String userId,String businessDate, String businessType){
		StringBuilder query=new StringBuilder();
		PrepStmtObject insertPrepStmtObject=null;
		StringBuilder bufInsSql = null;
		ArrayList qryList = new ArrayList();
		String partner_id="";
		String percentage="";
		String rate="";
		boolean status=false;
		String countQuery="select count(1) from cr_business_partner_dtl where loan_id='"+loanId+"' and self_flag='Y'"; 
		
		
		
		logger.info("The count query is...."+countQuery);
		int count = Integer.parseInt(ConnectionDAO.singleReturn(countQuery));
		logger.info("The Value count is...."+count);
		if(count==0){
			
			String loanAmountQuery="select loan_loan_amount from cr_loan_dtl where loan_id='"+loanId+"'" ;
			double loanAmount = Double.parseDouble((ConnectionDAO.singleReturn(loanAmountQuery)).trim());
			
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
				double partnerAmount = loanAmount*Double.parseDouble(percentage)/100;
				
				bufInsSql = new StringBuilder();
				insertPrepStmtObject = new PrepStmtObject();
				insertPrepStmtObject = new PrepStmtObject();
				bufInsSql.append("insert into cr_business_partner_dtl(PARTNER_ID, LOAN_ID, EFFECTIVE_RATE, PERCENTAGE, MAKER_ID, MAKER_DATE, PARTNER_TYPE, SERVICE_PARTNER_FLAG, SELF_FLAG, LOAN_AMOUNT,LEAD_PARTNER_FLAG,RATE_TYPE)");
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
				insertPrepStmtObject.addString(loanId);
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
	
	public ArrayList<Object> fetchLoanDetail(CommonLoanVo vo, HttpServletRequest request) {
	    ArrayList list = new ArrayList();
	    int count = 0;
	    int startRecordIndex = 0;
	    int endRecordIndex = this.no;

	    StringBuilder userName = new StringBuilder();

	    logger.info(new StringBuilder().append("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ ").append(vo.getReportingToUserId()).toString());
	    try {
	      StringBuilder userNameQ = new StringBuilder();
	      userNameQ.append(new StringBuilder().append("select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='").append(vo.getReportingToUserId()).append("'").toString());
	      userName.append(ConnectionDAO.singleReturn(userNameQ.toString()));
	      logger.info(new StringBuilder().append("userNameQ: ").append(userNameQ).append(" userName: ").append(userName).toString());
	      userNameQ = null;
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }

	    StringBuilder dealId = new StringBuilder();
	    StringBuilder lonaNo = new StringBuilder();
	    StringBuilder aDate = new StringBuilder();
	    StringBuilder custName = new StringBuilder();
	    StringBuilder productId = new StringBuilder();
	    StringBuilder scheme = new StringBuilder();

	    dealId.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo())).trim());
	    lonaNo.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID())).trim());
	    aDate.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAgrementDate())).trim());
	    custName.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName())).trim());
	    productId.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID())).trim());
	    scheme.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme())).trim());
	    ArrayList pQuery = new ArrayList();
	    StringBuilder pFlag = new StringBuilder();
	    pFlag.append("select ifnull(PARAMETER_VALUE,'N') from parameter_mst where parameter_key = 'LOAN_INITIATION_MAKER_CHECK' ");
		logger.info("Query to check  flag of maker Id-- "+pFlag.toString());
	    pQuery.add(pFlag);
	    String pValue = ConnectionDAO.singleReturn(pFlag.toString());
		logger.info("pValue ********** "+pValue);
		
	    try
	    {
	      ArrayList header = null;
	      CommonLoanVo fetchVo = null;
	      boolean appendSQL = false;
	      StringBuffer bufInsSql = new StringBuffer();
	      StringBuffer bufInsSqlTempCount = new StringBuffer();

	      bufInsSql.append(new StringBuilder().append(" select distinct d.LOAN_ID,d.LOAN_NO,DATE_FORMAT(d.LOAN_AGREEMENT_DATE,'").append(this.dateFormat).append("'),g.CUSTOMER_NAME,p.PRODUCT_DESC, s.SCHEME_DESC, DATE_FORMAT(dl.DEAL_SANCTION_VALID_TILL,'").append(this.dateFormat).append("'),(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=D.MAKER_ID) MAKER_ID,LOAN_DEAL_ID from cr_loan_dtl d").toString());
	      bufInsSqlTempCount.append("SELECT distinct COUNT(1) FROM cr_loan_dtl d ");

	      bufInsSql.append(" left join gcd_customer_m g on g.CUSTOMER_ID=d.LOAN_CUSTOMER_ID ");
	      bufInsSqlTempCount.append(" left join gcd_customer_m g on g.CUSTOMER_ID=d.LOAN_CUSTOMER_ID ");

	      bufInsSql.append(" left join cr_product_m p on d.LOAN_PRODUCT=p.PRODUCT_ID  ");
	      bufInsSqlTempCount.append(" left join cr_product_m p on d.LOAN_PRODUCT=p.PRODUCT_ID  ");

	      bufInsSql.append(" left join cr_scheme_m s on d.LOAN_SCHEME=s.SCHEME_ID ");
	      bufInsSqlTempCount.append(" left join cr_scheme_m s on d.LOAN_SCHEME=s.SCHEME_ID ");

			bufInsSql.append(" left join cr_deal_loan_dtl dl on d.LOAN_DEAL_ID=dl.DEAL_ID  AND dl.deal_loan_id=d.loan_deal_loan_id");
			bufInsSqlTempCount.append(" left join cr_deal_loan_dtl dl on d.LOAN_DEAL_ID=dl.DEAL_ID  AND dl.deal_loan_id=d.loan_deal_loan_id");

	      if ((vo.getStage() != null) && (!vo.getStage().equalsIgnoreCase("F")))
	      {
	        if ((!vo.getLbxDealNo().equalsIgnoreCase("")) && (!vo.getLbxLoanNoHID().equalsIgnoreCase("")) && (!vo.getAgrementDate().equalsIgnoreCase("")) && (!vo.getCustomerName().equalsIgnoreCase("")) && (!vo.getLbxProductID().equalsIgnoreCase("")) && (!vo.getLbxscheme().equalsIgnoreCase("")))
	        {
	        	bufInsSql.append("WHERE (D.MAKER_ID='"+vo.getReportingToUserId()+"' or d.maker_id is null ) AND D.LOAN_BRANCH='"+vo.getBranchId()+"' AND d.LOAN_DEAL_ID='"+vo.getLbxDealNo()+"' AND d.LOAN_ID='"+vo.getLbxLoanNoHID()+"'AND d.LOAN_AGREEMENT_DATE=STR_TO_DATE('"+vo.getAgrementDate()+"','"+dateFormat+"') AND g.CUSTOMER_NAME like'%"+vo.getCustomerName()+"%' AND d.LOAN_PRODUCT='"+vo.getLbxProductID()+"'AND d.LOAN_SCHEME='"+vo.getLbxscheme()+"' AND d.REC_STATUS='"+vo.getStage()+"'");
			   	  bufInsSqlTempCount.append("WHERE (D.MAKER_ID='"+vo.getReportingToUserId()+"' or d.maker_id is null ) AND D.LOAN_BRANCH='"+vo.getBranchId()+"' AND d.LOAN_DEAL_ID='"+vo.getLbxDealNo()+"' AND d.LOAN_ID='"+vo.getLbxLoanNoHID()+"'AND d.LOAN_AGREEMENT_DATE=STR_TO_DATE('"+vo.getAgrementDate()+"','"+dateFormat+"') AND g.CUSTOMER_NAME like'%"+vo.getCustomerName()+"%' AND d.LOAN_PRODUCT='"+vo.getLbxProductID()+"'AND d.LOAN_SCHEME='"+vo.getLbxscheme()+"' AND d.REC_STATUS='"+vo.getStage()+"'");
	        }

	        if ((vo.getLbxDealNo().equalsIgnoreCase("")) || (vo.getLbxLoanNoHID().equalsIgnoreCase("")) || (vo.getAgrementDate().equalsIgnoreCase("")) || (vo.getCustomerName().equalsIgnoreCase("")) || (vo.getLbxProductID().equalsIgnoreCase("")) || (vo.getLbxscheme().equalsIgnoreCase("")) || (vo.getStage().equalsIgnoreCase(""))) {
	          appendSQL = true;
	        }

	        if (appendSQL) {
				logger.info("In Where Clause");
				//Saurabh Code Start
				if(pValue.equalsIgnoreCase("Y")){
				bufInsSql.append(" WHERE (D.MAKER_ID='"+vo.getReportingToUserId()+"' or d.maker_id is null ) AND D.LOAN_BRANCH='"+vo.getBranchId()+"' AND d.REC_STATUS='"+vo.getStage()+"'");
				bufInsSqlTempCount.append(" WHERE (D.MAKER_ID='"+vo.getReportingToUserId()+"' or d.maker_id is null ) AND D.LOAN_BRANCH='"+vo.getBranchId()+"' AND d.REC_STATUS='"+vo.getStage()+"'");
	          }
	          else
	          {
	            logger.info("In N Where Clause");
						bufInsSql.append(" WHERE D.LOAN_BRANCH='"+vo.getBranchId()+"' AND IFNULL(d.AUTHOR_ID,'')<>'"+vo.getReportingToUserId()+"' AND d.REC_STATUS='"+vo.getStage()+"'");
						bufInsSqlTempCount.append(" WHERE D.LOAN_BRANCH='"+vo.getBranchId()+"' AND IFNULL(d.AUTHOR_ID,'')<>'"+vo.getReportingToUserId()+"' AND d.REC_STATUS='"+vo.getStage()+"'");
	            logger.info("In Author Block");

	        }
			//Saurab Code Ends
		}

	      }
	      else if ((vo.getStage() != null) && (vo.getStage().equalsIgnoreCase("F")))
	      {
	        String branch = "";
	        if (vo.getAllBranches().equalsIgnoreCase("on"))
	        {
	          String branchMappedToUserQuery = new StringBuilder().append("SELECT GROUP_CONCAT(BRANCH_ID) FROM sec_user_branch_dtl WHERE USER_ID='").append(CommonFunction.checkNull(vo.getReportingToUserId()).trim()).append("' AND REC_STATUS='A'").toString();
	          branch = ConnectionDAO.singleReturn(branchMappedToUserQuery);
	          logger.info(new StringBuilder().append("branchMappedToUserQuery: ").append(branchMappedToUserQuery).append("      branches maped to user:   ").append(branch).toString());
	        }
	        else if (!vo.getLbxBranchId().equalsIgnoreCase(""))
	        {
	          branch = vo.getLbxBranchId();
	        }
	        else
	        {
	          branch = vo.getBranchId();
	        }

	        if ((!vo.getLbxDealNo().equalsIgnoreCase("")) && (!vo.getLbxLoanNoHID().equalsIgnoreCase("")) && (!vo.getAgrementDate().equalsIgnoreCase("")) && (!vo.getCustomerName().equalsIgnoreCase("")) && (!vo.getLbxProductID().equalsIgnoreCase("")) && (!vo.getLbxscheme().equalsIgnoreCase("")))
	        {
	          bufInsSql.append(new StringBuilder().append("WHERE  D.LOAN_ID NOT IN(SELECT deviation.LOAN_ID FROM cr_loan_deviation_dtl deviation WHERE deviation.REC_STATUS NOT IN ('A')  and rule_result='T') AND D.MAKER_ID!='").append(vo.getReportingToUserId()).append("' AND D.LOAN_BRANCH IN (").append(StringEscapeUtils.escapeSql(branch).trim()).append(") AND d.LOAN_DEAL_ID='").append(vo.getLbxDealNo()).append("' AND d.LOAN_ID='").append(vo.getLbxLoanNoHID()).append("' AND d.LOAN_AGREEMENT_DATE=STR_TO_DATE('").append(vo.getAgrementDate()).append("','").append(this.dateFormat).append("') AND g.CUSTOMER_NAME like'%").append(vo.getCustomerName()).append("%' AND d.LOAN_PRODUCT='").append(vo.getLbxProductID()).append("'AND d.LOAN_SCHEME='").append(vo.getLbxscheme()).append("' AND d.REC_STATUS='").append(vo.getStage()).append("'").toString());
	          bufInsSqlTempCount.append(new StringBuilder().append("WHERE  D.LOAN_ID NOT IN(SELECT deviation.LOAN_ID FROM cr_loan_deviation_dtl deviation WHERE deviation.REC_STATUS NOT IN ('A')  and rule_result='T') AND D.MAKER_ID!='").append(vo.getReportingToUserId()).append("' AND D.LOAN_BRANCH IN (").append(StringEscapeUtils.escapeSql(branch).trim()).append(")  AND d.LOAN_DEAL_ID='").append(vo.getLbxDealNo()).append("' AND d.LOAN_ID='").append(vo.getLbxLoanNoHID()).append("' AND d.LOAN_AGREEMENT_DATE=STR_TO_DATE('").append(vo.getAgrementDate()).append("','").append(this.dateFormat).append("') AND g.CUSTOMER_NAME like'%").append(vo.getCustomerName()).append("%' AND d.LOAN_PRODUCT='").append(vo.getLbxProductID()).append("'AND d.LOAN_SCHEME='").append(vo.getLbxscheme()).append("' AND d.REC_STATUS='").append(vo.getStage()).append("'").toString());
	        }

	        if ((vo.getLbxDealNo().equalsIgnoreCase("")) || (vo.getLbxLoanNoHID().equalsIgnoreCase("")) || (vo.getAgrementDate().equalsIgnoreCase("")) || (vo.getCustomerName().equalsIgnoreCase("")) || (vo.getLbxProductID().equalsIgnoreCase("")) || (vo.getLbxscheme().equalsIgnoreCase("")) || (vo.getStage().equalsIgnoreCase(""))) {
	          appendSQL = true;
	        }

	        if (appendSQL) {
	          logger.info("In Where Clause");
	          bufInsSql.append(" WHERE (D.MAKER_ID!='"+vo.getUserId()+"' or d.maker_id is null ) AND D.LOAN_BRANCH IN ("+StringEscapeUtils.escapeSql(branch).trim()+") AND d.REC_STATUS='"+vo.getStage()+"' AND D.LOAN_ID NOT IN(SELECT deviation.LOAN_ID FROM cr_loan_deviation_dtl deviation WHERE deviation.REC_STATUS NOT IN ('A','P')  and rule_result='T') ");
				bufInsSqlTempCount.append(" WHERE (D.MAKER_ID!='"+vo.getUserId()+"' or d.maker_id is null ) AND D.LOAN_BRANCH IN ("+StringEscapeUtils.escapeSql(branch).trim()+")  AND d.REC_STATUS='"+vo.getStage()+"' AND D.LOAN_ID NOT IN(SELECT deviation.LOAN_ID FROM cr_loan_deviation_dtl deviation WHERE deviation.REC_STATUS NOT IN ('A','P')  and rule_result='T') ");
	        }
	      }

	      if (!vo.getLbxDealNo().equalsIgnoreCase("")) {
	        bufInsSql.append(new StringBuilder().append(" and d.LOAN_DEAL_ID='").append(vo.getLbxDealNo()).append("'").toString());
	        bufInsSqlTempCount.append(new StringBuilder().append(" and d.LOAN_DEAL_ID='").append(vo.getLbxDealNo()).append("'").toString());
	        appendSQL = true;
	      }

	      if (!vo.getLbxLoanNoHID().equalsIgnoreCase("")) {
	        bufInsSql.append(new StringBuilder().append(" and  d.LOAN_ID='").append(vo.getLbxLoanNoHID()).append("'").toString());
	        bufInsSqlTempCount.append(new StringBuilder().append(" and  d.LOAN_ID='").append(vo.getLbxLoanNoHID()).append("'").toString());
	        appendSQL = true;
	      }

	      if (!vo.getAgrementDate().equalsIgnoreCase("")) {
	        bufInsSql.append(new StringBuilder().append("and  d.LOAN_AGREEMENT_DATE =STR_TO_DATE('").append(vo.getAgrementDate()).append("','").append(this.dateFormatWithTime).append("') ").toString());
	        bufInsSqlTempCount.append(new StringBuilder().append("and  d.LOAN_AGREEMENT_DATE =STR_TO_DATE('").append(vo.getAgrementDate()).append("','").append(this.dateFormatWithTime).append("') ").toString());
	        appendSQL = true;
	      }

	      if (!vo.getCustomerName().equalsIgnoreCase("")) {
	        bufInsSql.append(new StringBuilder().append("and  g.CUSTOMER_NAME like'%").append(vo.getCustomerName()).append("%' ").toString());
	        bufInsSqlTempCount.append(new StringBuilder().append("and  g.CUSTOMER_NAME like'%").append(vo.getCustomerName()).append("%'").toString());
	        appendSQL = true;
	      }
	      if (!vo.getLbxProductID().equalsIgnoreCase("")) {
	        bufInsSql.append(new StringBuilder().append("and  d.LOAN_PRODUCT='").append(vo.getLbxProductID()).append("'").toString());
	        bufInsSqlTempCount.append(new StringBuilder().append("and  d.LOAN_PRODUCT='").append(vo.getLbxProductID()).append("'").toString());
	        appendSQL = true;
	      }
	      if (!vo.getLbxscheme().equalsIgnoreCase("")) {
	        bufInsSql.append(new StringBuilder().append("and  d.LOAN_SCHEME='").append(vo.getLbxscheme()).append("'").toString());
	        bufInsSqlTempCount.append(new StringBuilder().append("and  d.LOAN_SCHEME='").append(vo.getLbxscheme()).append("'").toString());
	        appendSQL = true;
	      }
	      if ((!vo.getLbxUserId().equalsIgnoreCase("")) && (vo.getStage().equalsIgnoreCase("F"))) {
	    	  bufInsSql.append(" AND (d.MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getLbxUserId()).trim()+"' or d.maker_id is null ) ");	
				bufInsSqlTempCount.append(" AND (d.MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getLbxUserId()).trim()+"' or d.maker_id is null ) ");
	        appendSQL = true;
	      }
	      logger.info(new StringBuilder().append("In appendSQL true---- ").append(appendSQL).toString());

	      if (appendSQL) {
	        logger.info("In appendSQL true---- ");
	        String tmp = bufInsSql.toString();
	        String tmp1 = bufInsSqlTempCount.toString();

	        logger.info(new StringBuilder().append("In fetchLoanDetail() ## tmp ## ").append(tmp).toString());
	        logger.info(new StringBuilder().append("In appendSQL true----  in check index Of").append(tmp.lastIndexOf("AND")).append("------").append(tmp.length() - 3).toString());

	        if ((tmp.lastIndexOf("AND") == tmp.length() - 3) || (tmp1.lastIndexOf("AND") == tmp1.length() - 3))
	        {
	          logger.info("In appendSQL true----  in check index Of");
	          tmp = tmp.substring(0, tmp.length() - 4);
	          tmp1 = tmp1.substring(0, tmp1.length() - 4);
	          logger.info(new StringBuilder().append("fetchLoanDetail Query...tmp.").append(tmp).toString());
	          header = ConnectionDAO.sqlSelect(tmp);
	          count = Integer.parseInt(ConnectionDAO.singleReturn(tmp1.toString()));
	        }
	        else
	        {
	          logger.info(new StringBuilder().append("fetchLoanDetail Query...tmp.").append(tmp).toString());
	          header = ConnectionDAO.sqlSelect(tmp);
	          count = Integer.parseInt(ConnectionDAO.singleReturn(tmp1.toString()));
	        }
	      }
	      else {
	        LoggerMsg.info(new StringBuilder().append("search Query...else-------.").append(bufInsSql).toString());
	        LoggerMsg.info(new StringBuilder().append("bufInsSqlTempCount **************************** : ").append(bufInsSqlTempCount.toString()).toString());

	        count = Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));

	        if (((dealId.toString().trim() == null) && (lonaNo.toString().trim() == null) && (aDate.toString().trim() == null) && (custName.toString().trim() == null) && (productId.toString().trim() == null) && (scheme.toString().trim() == null)) || ((dealId.toString().trim().equalsIgnoreCase("")) && (lonaNo.toString().trim().equalsIgnoreCase("")) && (aDate.toString().trim().equalsIgnoreCase("")) && (custName.toString().trim().equalsIgnoreCase("")) && (productId.toString().trim().equalsIgnoreCase("")) && (scheme.toString().trim().equalsIgnoreCase(""))) || (vo.getCurrentPageLink() > 1))
	        {
	          LoggerMsg.info(new StringBuilder().append("current PAge Link no .................... ").append(vo.getCurrentPageLink()).toString());
	          if (vo.getCurrentPageLink() > 1)
	          {
	            startRecordIndex = (vo.getCurrentPageLink() - 1) * this.no;
	            endRecordIndex = this.no;
	            LoggerMsg.info(new StringBuilder().append("startRecordIndex .................... ").append(startRecordIndex).toString());
	            LoggerMsg.info(new StringBuilder().append("endRecordIndex .................... ").append(endRecordIndex).toString());
	          }

	          bufInsSql.append(new StringBuilder().append(" limit ").append(startRecordIndex).append(",").append(endRecordIndex).toString());
	        }

	        LoggerMsg.info(new StringBuilder().append("query : ").append(bufInsSql).toString());

	        header = ConnectionDAO.sqlSelect(bufInsSql.toString());
	      }

	      for (int i = 0; i < header.size(); i++) {
	        logger.info(new StringBuilder().append("header").append(header.size()).toString());
	        ArrayList header1 = (ArrayList)header.get(i);
	        if (header1.size() > 0)
	        {
	          fetchVo = new CommonLoanVo();
	          fetchVo.setModifyNo(new StringBuilder().append("<a href=loanInitBehindAction.do?method=loanInitMakerAuthor&loanId=").append(CommonFunction.checkNull(header1.get(0)).trim().toString()).append("+&loanStatus=&dealId=").append(CommonFunction.checkNull(header1.get(8)).trim().toString()).append(">").append(CommonFunction.checkNull(header1.get(1)).trim().toString()).append("</a>").toString());

	          fetchVo.setLbxLoanNoHID(CommonFunction.checkNull(header1.get(0)).trim());
	          fetchVo.setLoanNo(CommonFunction.checkNull(header1.get(1)).trim());
	          fetchVo.setAgrementDate(CommonFunction.checkNull(header1.get(2)).trim());
	          fetchVo.setCustomerName(CommonFunction.checkNull(header1.get(3)).trim());
	          fetchVo.setProduct(CommonFunction.checkNull(header1.get(4)).trim());
	          fetchVo.setScheme(CommonFunction.checkNull(header1.get(5)).trim());
	          fetchVo.setValidSancTill(CommonFunction.checkNull(header1.get(6)).trim());
	          fetchVo.setReportingToUserId(CommonFunction.checkNull(header1.get(7)).trim());
	          fetchVo.setTotalRecordSize(count);
	          list.add(fetchVo);
	        }

	      }

	      bufInsSql = null;
	      bufInsSqlTempCount = null;
	      fetchVo = null;
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	    finally
	    {
	      dealId = null;
	      lonaNo = null;
	      custName = null;
	      productId = null;
	      scheme = null;
	      aDate = null;
	      userName = null;
	      vo = null;
	    }
	    return list;
	  }
	
	
	public boolean checkLoanSanctionVaildTill(String loanId,String businessDate,int functionId)
	{
		logger.info("Inside checkLoanSanctionVaildTill.......DAOImpl");
//		String recStatus = "";
		StringBuilder recStatus =new StringBuilder();
		if(functionId==4000106)
		{
			recStatus.append("P");
		}
		else if(functionId==4000111 || functionId==4000109)
		{
			recStatus.append("F");
		}
		boolean sanctionValid = false;
		StringBuilder query = new StringBuilder();
		query.append("select DATE_FORMAT(a.DEAL_SANCTION_VALID_TILL,'"+dateFormat+"') from cr_deal_loan_dtl a");
		query.append(" where a.DEAL_LOAN_ID=(select loan_deal_loan_id from cr_loan_dtl where ");
		query.append(" loan_id="+StringEscapeUtils.escapeSql(loanId)+" and rec_status='"+StringEscapeUtils.escapeSql(recStatus.toString())+"')");
		String sanctionDateStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(query.toString()));
		SimpleDateFormat dateFormat1 = new SimpleDateFormat(dateForDisbursal);
		try 
		{
			if(!sanctionDateStr.equalsIgnoreCase(""))
			{
				Date sanctionDate = dateFormat1.parse(sanctionDateStr); 
				Date actDate = dateFormat1.parse(businessDate);
				logger.info("sanctionDate: "+sanctionDate);
				logger.info("actDate: "+actDate);
				if(sanctionDate.after(actDate) || sanctionDate.equals(actDate))
				{
					sanctionValid=true;
				}
				else
					sanctionValid=false;
			}
		}catch(Exception e){
			 e.printStackTrace();
			}
		finally
		{
			recStatus = null;
			query = null;
			sanctionDateStr = null;
			dateFormat1 = null;
			loanId=null;
			businessDate=null;
		}
		logger.info("sanctionValid: "+sanctionValid);
		return sanctionValid;
	}
	public ArrayList getDate(String loanId)
	{
		ArrayList<LoanDetailForCMVO> DateList = new ArrayList<LoanDetailForCMVO>();
		logger.info("In getDate: " + loanId);		
		ArrayList mainlist = new ArrayList();
		ArrayList subList = new ArrayList();
		StringBuilder query = new StringBuilder(); 
		LoanDetailForCMVO loanvo = null;
		try {
			
			query.append("select DATE_FORMAT(c.LOAN_REPAY_EFF_DATE,'"+dateFormat+"'),DATE_FORMAT(C.NEXT_DUE_DATE,'"+dateFormat+"') from cr_loan_dtl c where LOAN_ID='"+loanId.trim()+"'");
			logger.info("In getDate    :    " + query.toString());
			mainlist = ConnectionDAO.sqlSelect(query.toString());
			int size = mainlist.size();
			for (int i = 0; i < size; i++) 
			{
				subList = (ArrayList) mainlist.get(i);
				if (subList.size() > 0) 
				{ 
					loanvo = new LoanDetailForCMVO();
					loanvo.setRepayEffectiveDate((CommonFunction.checkNull(subList.get(0)).trim()));
					loanvo.setNextDueDate((CommonFunction.checkNull(subList.get(1)).trim()));
					DateList.add(loanvo);
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
			loanId=null;
		}
		return DateList;
	}
	

	public ArrayList getloanListInLoan(String loanId)
	  {
	    ArrayList loanDetailList = new ArrayList();
	    logger.info(new StringBuilder().append("In getloanListInLoan: ").append(loanId).toString());

	    ArrayList mainlist = new ArrayList();
	    ArrayList subList = new ArrayList();
	    StringBuilder query = new StringBuilder();
	    logger.info(new StringBuilder().append("StringBuilder: Capacity: ").append(query.capacity()).toString());
	    LoanDetailForCMVO loanvo = null;
	    try
	    {
	    	query.append("select distinct LOAN_ID,LOAN_NO,G.CUSTOMER_NAME,LOAN_PRODUCT,p.PRODUCT_DESC,LOAN_SCHEME,s.SCHEME_DESC,DATE_FORMAT(d.DEAL_SANCTION_VALID_TILL,'"+dateFormat+"'),DATE_FORMAT(LOAN_AGREEMENT_DATE,'"+dateFormat+"'),");
			query.append(" DATE_FORMAT(LOAN_REPAY_EFF_DATE,'"+dateFormat+"'),LOAN_ASSET_COST,LOAN_MARGIN_RATE,LOAN_MARGIN_AMOUNT,LOAN_LOAN_AMOUNT,LOAN_TENURE,LOAN_REPAYMENT_FREQ,LOAN_RATE_TYPE,LOAN_RATE_METHOD,");
	      query.append(" LOAN_BASE_RATE_TYPE,LOAN_BASE_RATE,LOAN_MARKUP,LOAN_FINAL_RATE,LOAN_REPAYMENT_TYPE,LOAN_INSTALLMENT_TYPE,LOAN_INSTALLMENT_MODE,");
			query.append(" LOAN_REPAYMENT_MODE,LOAN_NO_OF_INSTALLMENT,LOAN_NUMBER_DISBURSAL,LOAN_ADVANCE_INSTL,LOAN_DUE_DAY, "
					 + " d.DEAL_SANCTION_AMOUNT, "
					+ " d.DEAL_UTILIZED_AMOUNT,deal.DEAL_ID,deal.DEAL_NO,DATE_FORMAT(LOAN_MATURITY_DATE,'"+dateFormat+"'),m.DESCRIPTION,LOAN_MAKER_REMARKS,loan_remarks,PO_DO_FLAG,LOAN_DUE_DAY,d.DEAL_IRR2,DATE_FORMAT(max(A.APPROVAL_DATE),'"+dateFormat+"'),INSUARENCE_DONE_BY,");
			query.append(" p.ONE_DEAL_ONE_LOAN,DATE_FORMAT(c.NEXT_DUE_DATE,'"+dateFormat+"'),c.REC_STATUS,s.MIN_FLAT_RATE,s.MAX_FLAT_RATE,s.MIN_EFF_RATE,s.MAX_EFF_RATE,s.MIN_TENURE,s.MAX_TENURE,c.AREA_CODE," +
					"(SELECT B.AREA_CODE_NAME FROM com_areacode_m B WHERE B.AREA_CODE=c.AREA_CODE)AS areaCodeName,p.ASSET_FLAG,c.LOAN_INT_CALC_FROM,i.DESCRIPTION,c.NET_LTV,c.TENURE_IN_DAY,p.DAYS_BASIS,(select DESCRIPTION from generic_master where GENERIC_KEY='INSTALLMENT_TYPE' AND REC_STATUS='A' AND VALUE=LOAN_INSTALLMENT_TYPE)AS INSTALLMENT_TYPE_DESC,c.LOAN_FLOATING_FIXED_PERIOD,c.LOAN_SECTOR_TYPE,ifnull(c.NO_OF_ASSET,0),LOAN_REFERENCE_NO,LOAN_DEAL_ID,c.LOAN_CLASSIFICATION ,(select DESCRIPTION from generic_master where GENERIC_KEY='repayment_mode' AND REC_STATUS='A' AND VALUE=LOAN_REPAYMENT_MODE)AS LOAN_REPAYMENT_DESC,c.FUNDING_TYPE,c.VAT_AMOUNT,ifnull(c.loan_credit_period,0),"
					+ " c.INT_COMP_FREQ,c.INT_METHOD,c.INT_FREQ,DATE_FORMAT(c.INTEREST_DUE_DATE,'"+dateFormat+"'),ifnull(round(c.insurance_premium,2),0)as insurance_premium,ifnull(c.DEAL_LOAN_AMOUNT_NEW,loan_loan_amount)as req_amount ,c.EDIT_DUE_DATE,DATE_FORMAT(c.FIRST_INTEREST_DUE_DATE,'"+dateFormat+"'),IFNULL(c.GROSS_BLOCK,''),IFNULL(c.NET_BLOCK,'') from cr_loan_dtl c");
	      query.append(" left join cr_product_m p on c.LOAN_PRODUCT=p.PRODUCT_ID");
	      query.append(" left join cr_scheme_m s on c.LOAN_SCHEME=s.SCHEME_ID");
			query.append(" left join cr_deal_loan_dtl d on d.DEAL_ID=c.LOAN_DEAL_ID and d.deal_loan_id=c.loan_deal_loan_id");
	      query.append(" left join cr_deal_dtl deal on deal.DEAL_ID=c.LOAN_DEAL_ID");
	      query.append(" left join gcd_customer_m G on G.CUSTOMER_ID=c.LOAN_CUSTOMER_ID");
	      query.append(" left JOIN generic_master m on LOAN_SECTOR_TYPE=m.VALUE and GENERIC_KEY='SECTOR_TYPE'");
			query.append(" left JOIN generic_master i on DEAL_INT_CALC_FROM=i.VALUE and i.GENERIC_KEY='INTERST_CAL_FROM' ");//Surendra..
			query.append(" left join cr_deal_approval_dtl A on A.DEAL_ID=c.LOAN_DEAL_ID ");
			query.append(" where LOAN_ID='"+loanId+"'");

	      logger.info(new StringBuilder().append("In getloanListInLoan    :    ").append(query.toString()).toString());
	      mainlist = ConnectionDAO.sqlSelect(query.toString());
	      int size = mainlist.size();
	      logger.info(new StringBuilder().append("In getloanListInLoan....").append(size).toString());
	      for (int i = 0; i < size; i++)
	      {
	        subList = (ArrayList)mainlist.get(i);
	        logger.info(new StringBuilder().append("In getloanListInLoan...").append(subList.size()).toString());
	        if (subList.size() > 0)
	        {
	          loanvo = new LoanDetailForCMVO();
	          loanvo.setLoanId(CommonFunction.checkNull(subList.get(0)).trim());
	          loanvo.setLoanNo(CommonFunction.checkNull(subList.get(1)).trim());
	          loanvo.setCustomerName(CommonFunction.checkNull(subList.get(2)).trim());
	          loanvo.setProduct(CommonFunction.checkNull(subList.get(3)).trim());
	          loanvo.setShowProduct(CommonFunction.checkNull(subList.get(4)).trim());
	          loanvo.setScheme(CommonFunction.checkNull(subList.get(5)).trim());
	          loanvo.setShowScheme(CommonFunction.checkNull(subList.get(6)).trim());
	          loanvo.setSanctionedValidtill(CommonFunction.checkNull(subList.get(7)).trim());
	          loanvo.setAgreementDate(CommonFunction.checkNull(subList.get(8)).trim());
	          loanvo.setRepayEffectiveDate(CommonFunction.checkNull(subList.get(9)).trim());

	          if (!CommonFunction.checkNull(subList.get(10)).equalsIgnoreCase(""))
	          {
	            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subList.get(10)).trim());
	            loanvo.setAssetCost(this.myFormatter.format(reconNum));
	          }

	          if (!CommonFunction.checkNull(subList.get(11)).equalsIgnoreCase(""))
	          {
	            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subList.get(11)).trim());
	            loanvo.setMargin(this.myFormatter.format(reconNum));
	            loanvo.setLtvPerc(this.myFormatter.format(100.0D - reconNum.doubleValue()));
	          }

	          if (!CommonFunction.checkNull(subList.get(12)).equalsIgnoreCase(""))
	          {
	            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subList.get(12)).trim());
	            loanvo.setMarginAmount(this.myFormatter.format(reconNum));
	          }
	          if (!CommonFunction.checkNull(subList.get(13)).equalsIgnoreCase(""))
	          {
	            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subList.get(13)).trim());
	            loanvo.setLoanAmount(this.myFormatter.format(reconNum));
	          }
	          loanvo.setTenureMonths(CommonFunction.checkNull(subList.get(14)).trim());

	          if (CommonFunction.checkNull(subList.get(15)).equalsIgnoreCase("M"))
	          {
	            loanvo.setFrequency(CommonFunction.checkNull(subList.get(15)).trim());
	            loanvo.setShowFrequency("MONTHLY");
	          }
	          else if (CommonFunction.checkNull(subList.get(15)).equalsIgnoreCase("B"))
	          {
	            loanvo.setFrequency(CommonFunction.checkNull(subList.get(15)).trim());
	            loanvo.setShowFrequency("BIMONTHLY");
	          }
	          else if (CommonFunction.checkNull(subList.get(15)).equalsIgnoreCase("Q"))
	          {
	            loanvo.setFrequency(CommonFunction.checkNull(subList.get(15)).trim());
	            loanvo.setShowFrequency("QUARTERLY");
	          }
	          else if (CommonFunction.checkNull(subList.get(15)).equalsIgnoreCase("H"))
	          {
	            loanvo.setFrequency(CommonFunction.checkNull(subList.get(15)).trim());
	            loanvo.setShowFrequency("HALFYERALY");
	          }
	          else if (CommonFunction.checkNull(subList.get(15)).equalsIgnoreCase("Y"))
	          {
	            loanvo.setFrequency(CommonFunction.checkNull(subList.get(15)).trim());
	            loanvo.setShowFrequency("YEARLY");
	          }

	          if (CommonFunction.checkNull(subList.get(16)).equalsIgnoreCase("F"))
	          {
	            loanvo.setRateType(CommonFunction.checkNull(subList.get(16)).trim());
	            loanvo.setShowEffectiveRate("FLAT RATE");
	          }
	          else if (CommonFunction.checkNull(subList.get(16)).equalsIgnoreCase("E"))
	          {
	            loanvo.setRateType(CommonFunction.checkNull(subList.get(16)).trim());
	            loanvo.setShowEffectiveRate("EFFECTIVE RATE");
	          }

	          loanvo.setDealRateMethod(CommonFunction.checkNull(subList.get(17)).trim());
	          loanvo.setBaseRateType(CommonFunction.checkNull(subList.get(18)).trim());

	          if (!CommonFunction.checkNull(subList.get(19)).equalsIgnoreCase(""))
	          {
	            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subList.get(19)).trim());
	            loanvo.setBaseRate(this.myFormatter.format(reconNum));
	          }
	          if (!CommonFunction.checkNull(subList.get(20)).equalsIgnoreCase(""))
	          {
	            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subList.get(20)).trim());
	            loanvo.setMarkUp(this.myFormatter.format(reconNum));
	          }
	          if (!CommonFunction.checkNull(subList.get(21)).equalsIgnoreCase(""))
	          {
	            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subList.get(21)).trim());
	            loanvo.setEffectiveRate(this.myFormatter.format(reconNum));
	          }

	          if (CommonFunction.checkNull(subList.get(22)).equalsIgnoreCase("I"))
	          {
	            loanvo.setRepaymentType(CommonFunction.checkNull(subList.get(22)).trim());
	            loanvo.setShowRepayment("INSTALLMENT");
	          }
	          else if (CommonFunction.checkNull(subList.get(22)).equalsIgnoreCase("N"))
	          {
	            loanvo.setRepaymentType(CommonFunction.checkNull(subList.get(22)).trim());
	            loanvo.setShowRepayment("NON-INSTALLMENT");
	          }

	          loanvo.setInstallmentType(CommonFunction.checkNull(subList.get(23)).trim());

	          if (CommonFunction.checkNull(subList.get(24)).equalsIgnoreCase("A"))
	          {
	            loanvo.setDealInstallmentMode(CommonFunction.checkNull(subList.get(24)).trim());
	            loanvo.setShowInstMode("ADVANCE");
	          }
	          else if (CommonFunction.checkNull(subList.get(24)).equalsIgnoreCase("R"))
	          {
	            loanvo.setDealInstallmentMode(CommonFunction.checkNull(subList.get(24)).trim());
	            loanvo.setShowInstMode("ARREAR");
	          }

	          loanvo.setRepaymentMode(CommonFunction.checkNull(subList.get(25)).trim());

	          loanvo.setLoanNoofInstall(CommonFunction.checkNull(subList.get(26)).trim());

	          loanvo.setNoOfDisbursal(CommonFunction.checkNull(subList.get(27)).trim());

	          if (!loanvo.getNoOfDisbursal().equalsIgnoreCase("1"))
	          {
	            loanvo.setTypeOfDisbursal("M");
	          }
	          else if (loanvo.getNoOfDisbursal().equalsIgnoreCase("1"))
	          {
	            loanvo.setTypeOfDisbursal("S");
	          }

	          loanvo.setInstallments(CommonFunction.checkNull(subList.get(28)).trim());

	          loanvo.setLoanDayDue(CommonFunction.checkNull(subList.get(29)).trim());

	          if (!CommonFunction.checkNull(subList.get(30)).equalsIgnoreCase(""))
	          {
	            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subList.get(30)).trim());
	            loanvo.setSanctionedLoanAmount(this.myFormatter.format(reconNum));
	          }
	          if (!CommonFunction.checkNull(subList.get(31)).equalsIgnoreCase(""))
	          {
	            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subList.get(31)).trim());
	            loanvo.setUtilizeLoanAmount(this.myFormatter.format(reconNum));
	          }

	          loanvo.setLbxDealNo(CommonFunction.checkNull(subList.get(32)).trim());
	          loanvo.setDealNo(CommonFunction.checkNull(subList.get(33)).trim());
	          loanvo.setMaturityDate(CommonFunction.checkNull(subList.get(34)).trim());

	          loanvo.setShowSectorType(CommonFunction.checkNull(subList.get(35)).trim());
	          loanvo.setRemarks(CommonFunction.checkNull(subList.get(36)).trim());
	          logger.info(new StringBuilder().append("Remarks: ").append(CommonFunction.checkNull(subList.get(36))).toString());
	          logger.info(new StringBuilder().append("Author Remarks: ").append(CommonFunction.checkNull(subList.get(37))).toString());
	          loanvo.setAuthorRemarks(CommonFunction.checkNull(subList.get(37)).trim());
	          loanvo.setPodoFlag(CommonFunction.checkNull(subList.get(38)).trim());
	          loanvo.setDueDay(CommonFunction.checkNull(subList.get(39)).trim());
	          if (!CommonFunction.checkNull(subList.get(40)).equalsIgnoreCase(""))
	          {
	            Number reconNum = this.myFormatter.parse(CommonFunction.checkNull(subList.get(40)).trim());
	            loanvo.setDealIrr2(this.myFormatter.format(reconNum));
	          }
	          loanvo.setSanctionDate(CommonFunction.checkNull(subList.get(41)).trim());
	          loanvo.setInsuranceDoneBy(CommonFunction.checkNull(subList.get(42)).trim());
	          loanvo.setOneDealOneLoan(CommonFunction.checkNull(subList.get(43)).trim());
	          loanvo.setNextDueDate(CommonFunction.checkNull(subList.get(44)).trim());
	          loanvo.setRecStatus(CommonFunction.checkNull(subList.get(45)).trim());
	          loanvo.setMinFlatRate(CommonFunction.checkNull(subList.get(46)).trim());
	          loanvo.setMaxFlatRate(CommonFunction.checkNull(subList.get(47)).trim());
	          loanvo.setMinEffRate(CommonFunction.checkNull(subList.get(48)).trim());
	          loanvo.setMaxEffRate(CommonFunction.checkNull(subList.get(49)).trim());
	          loanvo.setMinTenure(CommonFunction.checkNull(subList.get(50)).trim());
	          loanvo.setMaxTenure(CommonFunction.checkNull(subList.get(51)).trim());
	          loanvo.setLbxareaCodeVal(CommonFunction.checkNull(subList.get(52)).trim());
	          loanvo.setAreaCodename(CommonFunction.checkNull(subList.get(53)).trim());

	          loanvo.setAssetFlag(CommonFunction.checkNull(subList.get(54)).trim());

	          loanvo.setInterestCalc(CommonFunction.checkNull(subList.get(55)).trim());
	          loanvo.setShowInterestCalc(CommonFunction.checkNull(subList.get(56)).trim());

	          loanvo.setNetLtv(CommonFunction.checkNull(subList.get(57)).trim());
	          loanvo.setTenureInDay(CommonFunction.checkNull(subList.get(58)).trim());
	          loanvo.setDaysBasis(CommonFunction.checkNull(subList.get(59)).trim());
	          loanvo.setShowInstallment(CommonFunction.checkNull(subList.get(60)).trim());
	          loanvo.setFixPriod(CommonFunction.checkNull(subList.get(61)).trim());
	          loanvo.setSectorType(CommonFunction.checkNull(subList.get(62)).trim());
	          loanvo.setNoOfAsset(CommonFunction.checkNull(subList.get(63)).trim());
	          loanvo.setFormNo(CommonFunction.checkNull(subList.get(64)).trim());
	          loanvo.setLoanDealId(CommonFunction.checkNull(subList.get(65)).trim());
	          loanvo.setLbxLoanClassification(CommonFunction.checkNull(subList.get(66)).trim());
	          loanvo.setShowRepayMode(CommonFunction.checkNull(subList.get(67)).trim());
	          loanvo.setBusinessType(CommonFunction.checkNull(subList.get(68)).trim());
	          loanvo.setTotalVatAmt(CommonFunction.checkNull(subList.get(69)).trim());
	          loanvo.setCreditPeriod(CommonFunction.checkNull(subList.get(70)).trim());
	          loanvo.setInterestCompoundingFrequency(CommonFunction.checkNull(subList.get(71)).trim());
	          loanvo.setInterestCalculationMethod(CommonFunction.checkNull(subList.get(72)).trim());
	          loanvo.setInterestFrequency(CommonFunction.checkNull(subList.get(73)).trim());
	          loanvo.setInterestDueDate(CommonFunction.checkNull(subList.get(74)).trim());
	          loanvo.setInsurancePremium(new StringBuilder().append(CommonFunction.checkNull(subList.get(75)).trim()).append("").toString());
	          if (!CommonFunction.checkNull(subList.get(76)).equalsIgnoreCase(""))
	          {
	            Number reconNumReqAmt = this.myFormatter.parse(CommonFunction.checkNull(subList.get(76)).trim());
	            loanvo.setRequestedLoamt(this.myFormatter.format(reconNumReqAmt));
	          }

	          loanvo.setEditDueDate(CommonFunction.checkNull(subList.get(77)).trim());

	          loanvo.setFirstInterestDueDate(CommonFunction.checkNull(subList.get(78)).trim());

	          loanvo.setGrossBlock(CommonFunction.checkNull(subList.get(79)).trim());
	          loanvo.setNetBlock(CommonFunction.checkNull(subList.get(80)).trim());
	          loanDetailList.add(loanvo);
	        }
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    finally
	    {
	      mainlist.clear();
	      mainlist = null;
	      subList.clear();
	      subList = null;
	      query = null;
	      loanvo = null;
	      loanId = null;
	    }
	    return loanDetailList;
	  }
	public ArrayList getLoanHeader(String loanId) {
		ArrayList list=new ArrayList();
		try
		{
			StringBuilder query =new StringBuilder();
			 query.append(" select L.LOAN_ID, LOAN_NO,D.DEAL_NO,L.LOAN_CUSTOMER_ID,G.CUSTOMER_NAME,DATE_FORMAT(L.MAKER_DATE,'"+dateFormat+"'),p.PRODUCT_DESC,s.SCHEME_DESC,L.LOAN_PRODUCT_CATEGORY,L.Loan_Loan_Amount from cr_loan_dtl L "+
						   " left join cr_product_m p on L.LOAN_PRODUCT=p.PRODUCT_ID"+
							" left join cr_scheme_m s on L.LOAN_SCHEME=s.SCHEME_ID"+
							" left join gcd_customer_m G on G.CUSTOMER_ID=L.LOAN_CUSTOMER_ID"+
							 " left join cr_deal_dtl D on D.DEAL_ID=L.LOAN_DEAL_ID"+
							" where L.LOAN_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()+" limit 1");
			
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
			loanId=null;			
		}

		return list;

	}
   
	public String getOneDealOneLoanFlag(String laonId){
//		String oneDealOneFlag="";
		StringBuilder oneDealOneFlag= new StringBuilder();
		StringBuilder query = new StringBuilder();
	 try{
			query.append("select  p.ONE_DEAL_ONE_LOAN  from cr_loan_dtl c");
			query.append(" left join cr_product_m p on c.LOAN_PRODUCT=p.PRODUCT_ID");
			query.append(" where LOAN_ID='" + laonId + "'");
			oneDealOneFlag.append(ConnectionDAO.singleReturn(query.toString()));
	}catch (Exception e) {
		e.printStackTrace();
		e=null;
	}
	finally
	{
		//queryList = null;
		query = null;
		laonId=null;
	}
	return oneDealOneFlag.toString();
	}
	public boolean checkOldNewTenureAreEqual(String loanId,String reschId){
		boolean oldNewTenureEqlFlag=false;
		StringBuilder query = new StringBuilder();
		ArrayList list=new ArrayList();
		ArrayList sublist=new ArrayList();
//		String oldTenure=null;
//		String newTenure=null;
		
		String oldTenure=null;
		String newTenure=null;
		
	 try{
			query.append("Select D.TO_INSTL_NO,R.NEW_INSTL_NO from cr_resch_dtl R ,cr_resch_installment_plan D");
			query.append(" where  R.LOAN_ID=D.LOAN_ID and R.LOAN_ID="+CommonFunction.checkNull(loanId)+" and  R.resch_id="+CommonFunction.checkNull(reschId)+"");
			logger.info("checkOldNewTenureAreEqual query:-"+query);
			list=ConnectionDAO.sqlSelect(query.toString());
			int size=list.size();
			if(size>0){
				sublist=(ArrayList)list.get(0);
				oldTenure=CommonFunction.checkNull(sublist.get(0));
				newTenure=CommonFunction.checkNull(sublist.get(1));
				if(oldTenure.equalsIgnoreCase(newTenure)){
					oldNewTenureEqlFlag=true;	
				}
				
			}
	}catch (Exception e) {
		e.printStackTrace();
		e=null;
	}
	finally
	{
		list.clear();
		list = null;
		sublist.clear();
		sublist=null;
		query = null;
		oldTenure=null;
		newTenure=null;
		loanId=null;
		reschId=null;
	}
	return oldNewTenureEqlFlag;	
	}
	

	
public ArrayList refreshchargesDetailInCM(String loanStage, String loanId) 
{
		
		int dId=0;
		ArrayList list =new ArrayList();
		try 
		{
			if(!loanId.trim().equalsIgnoreCase(""))
			dId = Integer.parseInt(loanId.trim());
			StringBuilder selectQuery=new StringBuilder();
			selectQuery.append("select LOAN_CUSTOMER_ID,LOAN_PRODUCT,LOAN_SCHEME,LOAN_LOAN_AMOUNT,LOAN_MARGIN_AMOUNT from cr_loan_dtl where LOAN_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim());
			ArrayList productList=ConnectionDAO.sqlSelect(selectQuery.toString());
			logger.info("refreshchargesDetailInCM: "+selectQuery.toString());			
			selectQuery=null;			
			StringBuilder supllierIdQ=new StringBuilder();
			StringBuilder suplId=new StringBuilder();
			StringBuilder manfId=new StringBuilder();
			StringBuilder exactManfId=new StringBuilder();
			StringBuilder exactSuplId=new StringBuilder();
			StringBuilder query1=new StringBuilder();
			String rsp="";
			suplId.append("SU;");
			manfId.append("MF;");
					
			
			query1.append("SELECT IFNULL(PARTNER_ID,'') FROM cr_business_partner_dtl WHERE LOAN_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()+" AND IFNULL(SELF_FLAG,'')='N'");
			String val=ConnectionDAO.singleReturn(query1.toString());
			if(!CommonFunction.checkNull(val).equalsIgnoreCase(""))
			{
				rsp="CP;"+val+"|";
			}
			
			supllierIdQ.append("select distinct ASSET_SUPPLIER from cr_loan_collateral_m L,cr_asset_collateral_m a "+ 
								" where L.ASSETID=a.ASSET_ID and a.ASSET_TYPE='ASSET'  and  LOAN_ID="+loanId);
			 
			 logger.info("refreshchargesDetailInCM supllierIdQ:  "+supllierIdQ.toString());		
			 ArrayList supllierIdList=ConnectionDAO.sqlSelect(supllierIdQ.toString());
			 supllierIdQ=null;
			 if(supllierIdList.size()>0)
			 {
				 for(int i=0;i<supllierIdList.size();i++)
				 {
					 ArrayList subsupllierIdList=(ArrayList)supllierIdList.get(i);
					 if(subsupllierIdList.size()>0)
					 	 suplId.append(CommonFunction.checkNull(subsupllierIdList.get(0))+"|SU;");
				}
			 }
			 if(suplId.lastIndexOf("SU;") == (suplId.length()-3))
					  exactSuplId.append((suplId).substring(0,(suplId.length()-3)));
			 StringBuilder manufactIdQ=new StringBuilder();
			 manufactIdQ.append("select distinct ASSET_MANUFATURER from cr_loan_collateral_m L,cr_asset_collateral_m a "+ 
			                   " where L.ASSETID=a.ASSET_ID and a.ASSET_TYPE='ASSET'  and  LOAN_ID="+loanId);
			 
			 logger.info("refreshchargesDetailInCM manufactIdQ:  "+manufactIdQ.toString());
			 ArrayList manufactIdList=ConnectionDAO.sqlSelect(manufactIdQ.toString());
			 manufactIdQ=null;
			 if(manufactIdList.size()>0)
			 {
				for(int i=0;i<manufactIdList.size();i++)
				{
					  ArrayList submanufactIdList=(ArrayList)manufactIdList.get(i);
					  if(submanufactIdList.size()>0)
					  	  manfId.append(CommonFunction.checkNull(submanufactIdList.get(0))+"|MF;");;
				}
			 }
			 if(manfId.lastIndexOf("MF;") == (manfId.length()-3))
				 exactManfId.append((manfId).substring(0,(manfId.length()-3)));
			     
			  for(int i=0;i<productList.size();i++)
			  {
				  ArrayList subproductList=(ArrayList)productList.get(i);
				  if(subproductList.size()>0)
				  {
					  String chargeStr="2;"+CommonFunction.checkNull(subproductList.get(3))+"|3;"+CommonFunction.checkNull(subproductList.get(4))+"|";
					  String bpStr="CS;"+CommonFunction.checkNull(subproductList.get(0))+"|"+exactSuplId+exactManfId+rsp;
					  logger.info("refreshchargesDetailInCM STAGE INFO: "+loanStage+ " Total Charges: "+chargeStr+ " customer Id:  "+CommonFunction.checkNull(subproductList.get(0))+ " Product: "+CommonFunction.checkNull(subproductList.get(1))+ " Scheme: "+CommonFunction.checkNull(subproductList.get(2))+"bpStr: "+bpStr);
					  
					  ArrayList<Object> in =new ArrayList<Object>();
					  ArrayList<Object> out =new ArrayList<Object>();
					  ArrayList outMessages = new ArrayList();
						ReportsDAO reportdao = (ReportsDAO)DaoImplInstanceFactory.getDaoImplInstance(ReportsDAO.IDENTITY);
			    		logger.info("Implementation class: "+reportdao.getClass());
					  String endDate=reportdao.getEndDate(); 
					  String s1=null;
					  String s2=null;
					  in.add(loanStage);
					  in.add(dId);
					  in.add(CommonFunction.checkNull(subproductList.get(1)));
					  in.add(CommonFunction.checkNull(subproductList.get(2)));
					  in.add(chargeStr);
					  in.add(bpStr);
					  out.add(s1);
					  out.add(s2);  
									
					  logger.info("Get_charges ("+in.toString()+","+out.toString());
					  outMessages=(ArrayList) ConnectionDAO.callSP("Get_charges",in,out);
					  s1=CommonFunction.checkNull(outMessages.get(0));
					  s2=CommonFunction.checkNull(outMessages.get(1));
					  logger.info("s1  : "+s1);
					  logger.info("s2  : "+s2);
					  in.clear();
					  in=null;
					  out.clear();
					  out=null;
					  outMessages.clear();
					  outMessages=null;
					  s1=null;
					  s2=null;
				  }				  
			  }
			  ChargeVo vo =null;
			  StringBuilder query=new StringBuilder();
			query.append("select DEAL_CHARGE_DTL_ID,DEAL_CHARGE_TYPE,DEAL_CHARGE_CODE,CHARGE_DESC," );
			query.append(" GM.DESCRIPTION,v.DEALER_DESC,DEAL_CHARGE_CALCULATED_AMOUNT," );
			query.append(" DEAL_CHARGE_FINAL_AMOUNT,g.CUSTOMER_NAME,L.LOAN_LOAN_AMOUNT," );
			query.append(" (IFNULL(L.LOAN_ASSET_COST,0)-IFNULL(L.LOAN_LOAN_AMOUNT,0)) AS 'MARGIN AMOUNT',DEAL_CHARGE_CALCULATED_ON,if(DEAL_CHARGE_METHOD='P','PERCENTAGE','FLAT')," );
			query.append(" if(DEAL_CHARGE_TAX_INCLUSIVE='N','NO','YES'),DEAL_CHARGE_TAX_RATE1," );
			query.append(" DEAL_CHARGE_TAX_RATE2,DEAL_CHARGE_BP_TYPE, if(DEAL_MIN_CHARGE_METHOD='P','PERCENTAGE','FLAT'),DEAL_CHARGE_MIN_CHARGE_AMOUNT," );
			query.append(" DEAL_CHARGE_TAX_APPLICABLE,DEAL_CHARGE_TDS_APPLICABLE,DEAL_CHARGE_TAX_AMOUNT1,DEAL_CHARGE_TAX_AMOUNT2,DEAL_CHARGE_MIN_CHARGE_AMOUNT,DEAL_CHARGE_TDS_RATE,DEAL_CHARGE_TDS_AMOUNT,DEAL_CHARGE_NET_AMOUNT,DEAL_CHARGE_APPLICATION_STAGE, " );
			query.append(" (SELECT sum(DEAL_CHARGE_CALCULATED_AMOUNT) from  cr_txncharges_dtl where TXN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()+"' and DEAL_CHARGE_BP_TYPE='CS' and DEAL_CHARGE_TYPE='R') as CUST_CHARGE_AMOUNT, ");
			query.append(" (SELECT sum(DEAL_CHARGE_FINAL_AMOUNT) from  cr_txncharges_dtl where TXN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()+"' and DEAL_CHARGE_BP_TYPE='CS' and DEAL_CHARGE_TYPE='R') as CUST_FINAL_CHARGE_AMOUNT, ");
			query.append(" if(ifnull(DEAL_CHARGE_BP_TYPE,'')='CP',(select  name  from com_partner_details_m where partner_id=DEAL_CHARGE_BP_ID),GM.DESCRIPTION)'rspname' ");
			query.append(" from cr_txncharges_dtl t " );
			query.append(" left join com_charge_code_m c on t.DEAL_CHARGE_CODE=c.CHARGE_CODE " );
			query.append(" left join cr_dsa_dealer_m v on v.DEALER_ID=t.DEAL_CHARGE_BP_ID and v.BP_TYPE=t.DEAL_CHARGE_BP_TYPE and v.REC_STATUS='A' " );
			query.append(" left join gcd_customer_m g on g.CUSTOMER_ID=t.DEAL_CHARGE_BP_ID " );
			query.append(" left join cr_loan_dtl L on L.LOAN_ID=TXN_ID " ); 
			query.append(" left join generic_master GM on GM.VALUE=DEAL_CHARGE_BP_TYPE and GM.GENERIC_KEY='BPTYPE' " );
			query.append(" where TXN_TYPE='LIM' and TXN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()+"' ");
			  logger.info("refreshchargesDetailInCM query   :  "+query.toString());
			  ArrayList charges = ConnectionDAO.sqlSelect(query.toString());
			  logger.info("refreshchargesDetailInCM size "+charges.size());
			  query=null;
			  for(int i=0;i<charges.size();i++)
			  {
				  ArrayList subcharges=(ArrayList)charges.get(i);
				  if(subcharges.size()>0)	
				  {
					  vo = new ChargeVo();
					  vo.setChargeId((CommonFunction.checkNull(subcharges.get(0))).trim());
					  if((CommonFunction.checkNull(subcharges.get(1)).trim()).equals("R"))
					  		  vo.setChargeType("Receivable");
					  else if((CommonFunction.checkNull(subcharges.get(1)).trim()).equals("P"))
							 vo.setChargeType("Payable");
					  vo.setChargeCode((CommonFunction.checkNull(subcharges.get(2))).trim());
					  vo.setChargeDesc((CommonFunction.checkNull(subcharges.get(3))).trim());
					  vo.setChargeBPType((CommonFunction.checkNull(subcharges.get(4)).trim()));
					  if((CommonFunction.checkNull(subcharges.get(16)).trim()).equals("CS"))
					  		vo.setChargeBPId((CommonFunction.checkNull(subcharges.get(8))).trim());
					  else if((CommonFunction.checkNull(subcharges.get(16)).trim()).equals("CP"))
					  		vo.setChargeBPId((CommonFunction.checkNull(subcharges.get(30))).trim());
					  else
							vo.setChargeBPId((CommonFunction.checkNull(subcharges.get(5))).trim());
					  if(!CommonFunction.checkNull(subcharges.get(6)).equalsIgnoreCase(""))
    	    		  {
	    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subcharges.get(6))).trim());
	    	    		vo.setChargeCal(myFormatter.format(reconNum));
    	    		  }
					  if(!CommonFunction.checkNull(subcharges.get(7)).equalsIgnoreCase(""))
    	    		  {
	    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subcharges.get(7))).trim());
	    	    		vo.setChargeFinal(myFormatter.format(reconNum));
    	    		  }				
					  vo.setLoanAmount((CommonFunction.checkNull(subcharges.get(9))).trim());
					  vo.setMarginAmount((CommonFunction.checkNull(subcharges.get(10))).trim());
					  vo.setChargeCalculatedOn((CommonFunction.checkNull(subcharges.get(11))).trim());
					  vo.setChargeMethod((CommonFunction.checkNull(subcharges.get(12))).trim());
					  vo.setTaxsInclusive((CommonFunction.checkNull(subcharges.get(13))).trim());
					  if(!CommonFunction.checkNull(subcharges.get(14)).equalsIgnoreCase(""))
					  {
						  Number reconNum =myFormatter.parse((CommonFunction.checkNull(subcharges.get(14))).trim());
						  vo.setTaxtRat1(myFormatter.format(reconNum));
					  }
					  if(!CommonFunction.checkNull(subcharges.get(15)).equalsIgnoreCase(""))
					  {
						  Number reconNum =myFormatter.parse((CommonFunction.checkNull(subcharges.get(15))).trim());
						  logger.info("setTaxtRat2: "+reconNum);
						  vo.setTaxtRat2(myFormatter.format(reconNum));
					  }
					  vo.setMinChargeMethod((CommonFunction.checkNull(subcharges.get(17))).trim());
					  vo.setMinChargeCalculatedOn((CommonFunction.checkNull(subcharges.get(18))).trim());
					  vo.setDealChargeTaxApp((CommonFunction.checkNull(subcharges.get(19))).trim());
					  vo.setDealChargeTdsApp((CommonFunction.checkNull(subcharges.get(20))).trim());
					  if(!CommonFunction.checkNull(subcharges.get(21)).equalsIgnoreCase(""))
					  {
						  Number reconNum =myFormatter.parse((CommonFunction.checkNull(subcharges.get(21))).trim());
						  vo.setDealChargeTaxAmount1(myFormatter.format(reconNum));
					  }
					  if(!CommonFunction.checkNull(subcharges.get(22)).equalsIgnoreCase(""))
					  {
						  Number reconNum =myFormatter.parse((CommonFunction.checkNull(subcharges.get(22))).trim());
						  vo.setDealChargeTaxAmount2(myFormatter.format(reconNum));
					  }
					  if(!CommonFunction.checkNull(subcharges.get(23)).equalsIgnoreCase(""))
					  {
						  Number reconNum =myFormatter.parse((CommonFunction.checkNull(subcharges.get(23))).trim());
						  vo.setDealChargeMinChargeAmount(myFormatter.format(reconNum));
					  }
					  if(!CommonFunction.checkNull(subcharges.get(24)).equalsIgnoreCase(""))
					  {
						  Number reconNum =myFormatter.parse((CommonFunction.checkNull(subcharges.get(24))).trim());
						  vo.setDealChargeTdsRate(myFormatter.format(reconNum));
					  }
					  if(!CommonFunction.checkNull(subcharges.get(25)).equalsIgnoreCase(""))
					  {
						  Number reconNum =myFormatter.parse((CommonFunction.checkNull(subcharges.get(25))).trim());
						  vo.setDealChargeTdsAmount(myFormatter.format(reconNum));
					  }
					  if(!CommonFunction.checkNull(subcharges.get(26)).equalsIgnoreCase(""))
					  {
						  Number reconNum =myFormatter.parse((CommonFunction.checkNull(subcharges.get(26))).trim());
						  vo.setDealChargeNetAmount(myFormatter.format(reconNum));
					  }
					  vo.setApplStage((CommonFunction.checkNull(subcharges.get(27))).trim());
					 if(!CommonFunction.checkNull(subcharges.get(28)).equalsIgnoreCase(""))
			    	 {
			   	    	Number reconNum =myFormatter.parse((CommonFunction.checkNull(subcharges.get(28))).trim());
			   	    	vo.setCustomerCharge(myFormatter.format(reconNum));
			   	    		
			    	 }
					 if(!CommonFunction.checkNull(subcharges.get(29)).equalsIgnoreCase(""))
			    	 {
			   	    	Number reconNum =myFormatter.parse((CommonFunction.checkNull(subcharges.get(29))).trim());
			   	    	vo.setCustomerFinalCharge(myFormatter.format(reconNum));
			   	    		
			    	 }
          			  list.add(vo);
          			  vo=null;
		           }
			}
		}
		catch (Exception e) 
		{e.printStackTrace();}
		finally{
			loanStage=null;
			loanId=null;
		}
		return list;
	}
	
	
	
	public ArrayList getTenureAmount(String loanId) {

		
		ArrayList list=new ArrayList();
		
		try
		{
		  ArrayList searchlist=new ArrayList();
		  logger.info("In getTenureAmount....................");
		  
		  StringBuffer bufInsSql =	new StringBuffer();

		  bufInsSql.append("select LOAN_TENURE,DEAL_CHARGE_FINAL_AMOUNT from  cr_loan_dtl L,cr_txncharges_dtl C where L.LOAN_ID="+loanId+" and L.LOAN_ID=C.TXN_ID and c.DEAL_CHARGE_CODE=103 and TXN_TYPE='LIM' limit 1");
			  
			  logger.info("In getTenureAmount......... query..........."+bufInsSql.toString());
		      searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
		
		  for(int i=0;i<searchlist.size();i++){
		  logger.info("getTenureAmount List "+searchlist.get(i).toString());
		  ArrayList data=(ArrayList)searchlist.get(i);
		  if(data.size()>0)
		  {
			 TenureAmountVo receiptvo = new TenureAmountVo();
			 receiptvo.setTenure((CommonFunction.checkNull(data.get(0)).trim()));
			 
			 if(!CommonFunction.checkNull(data.get(1)).trim().equalsIgnoreCase(""))
	    		{
    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(data.get(1))).trim());  
    	    		receiptvo.setSecurityAmount(myFormatter.format(reconNum));
	    		}
			 
			 //receiptvo.setSecurityAmount((CommonFunction.checkNull(data.get(1)).trim()));
			 list.add(receiptvo);	
			 receiptvo=null;
		   }
		  }
		  bufInsSql=null;
		  
		}catch(Exception e){
			e.printStackTrace();
			e=null;
				}
		finally{
			loanId=null;
		}
		return  list;	
	}


	public ArrayList<CompoundFrequencyVO> getCompFrequencyList() {
		ArrayList<CompoundFrequencyVO> list = new ArrayList<CompoundFrequencyVO>();
		
		CompoundFrequencyVO appl = null;
		StringBuilder query = new StringBuilder();
		ArrayList freqdetails = new ArrayList();
		ArrayList freqdetails1 = new ArrayList();
		try {

			query.append("SELECT VALUE,DESCRIPTION FROM generic_master  ");
			query.append("where GENERIC_KEY='COMPOUND_FREQUENCY' and REC_STATUS='A'");
			freqdetails = ConnectionDAO.sqlSelect(query.toString());
			int size = freqdetails.size();
			logger.info("getfrequencyDetails" + size);
			for (int i = 0; i < size; i++) {
				logger.info("getfrequencyDetails"+ freqdetails.get(i).toString());
				freqdetails1 = (ArrayList) freqdetails.get(i);
				if(freqdetails1.size()>0){
					appl = new CompoundFrequencyVO();
					appl.setFrequency_code((CommonFunction.checkNull(freqdetails1.get(0)).trim()));
					appl.setFrequency_desc((CommonFunction.checkNull(freqdetails1.get(1)).trim()));

				}

				list.add(appl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			appl = null;
			query = null;
			freqdetails = null;
			freqdetails1 = null;
		}
		logger.info("Value of List is in dao is=" + list);
		return list;

	}


	public ArrayList<Object> getSecurityDetailAll(String loanId) {
		ArrayList<Object> list = new ArrayList<Object>();
		
		SecurityDepositVO appl = null;
		StringBuilder query = new StringBuilder();
		ArrayList appdetails = new ArrayList();
		ArrayList adetails1 = new ArrayList();

		try {
			logger.info("IN getSecurityDetailAll()" + loanId);
			query.append("select TXN_TYPE,TXN_ID,SD_AMOUNT,SD_INTEREST_TYPE,SD_INTEREST_RATE,SD_COMPOUNDING_FREQ,SD_TENURE,SD_INTEREST,SD_ADJUSTMENT");
			query.append(" from cr_sd_m S where TXN_ID="+loanId+"");

			appdetails = ConnectionDAO.sqlSelect(query.toString());
			int size = appdetails.size();
			logger.info("IN getSecurityDetailAll()" + size);
			for (int i = 0; i < size; i++) {

				adetails1 = (ArrayList) appdetails.get(i);
				if(adetails1.size()>0){

					appl = new SecurityDepositVO();
					appl.setTxnType((CommonFunction.checkNull(adetails1.get(0)).trim()));
					appl.setTxnId((CommonFunction.checkNull(adetails1.get(1)).trim()));
					
					if(!CommonFunction.checkNull(adetails1.get(2)).trim().equalsIgnoreCase(""))
    	    		{
	    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(adetails1.get(2))).trim());  
	    	    		appl.setSecurityAmount(myFormatter.format(reconNum));
    	    		}
					
					//appl.setSecurityAmount((CommonFunction.checkNull(adetails1.get(2)).trim()));
					logger.info("IN getSecurityDetailAll()----adetails1.get(3)" + adetails1.get(3));
					if((CommonFunction.checkNull(adetails1.get(3)).trim()).equalsIgnoreCase("N"))
					{
						appl.setInterestType("NO");
					}
					else 	if((CommonFunction.checkNull(adetails1.get(3)).trim()).equalsIgnoreCase("S"))
					{
						appl.setInterestType("SIMPLE");
					}
					else 	if((CommonFunction.checkNull(adetails1.get(3)).trim()).equalsIgnoreCase("C"))
					{
						appl.setInterestType("COMPOUND");
					}
				
					if(!CommonFunction.checkNull(adetails1.get(4)).trim().equalsIgnoreCase(""))
    	    		{
	    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(adetails1.get(4))).trim());  
	    	    		appl.setInterestRate(myFormatter.format(reconNum));
    	    		}
					//appl.setInterestRate((CommonFunction.checkNull(adetails1.get(4)).trim()));
					appl.setCompoundFrequency((CommonFunction.checkNull(adetails1.get(5)).trim()));
					appl.setTenure((CommonFunction.checkNull(adetails1.get(6)).trim()));
					if(!CommonFunction.checkNull(adetails1.get(7)).trim().equalsIgnoreCase(""))
    	    		{
	    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(adetails1.get(7))).trim());  
	    	    		appl.setRelatedInterest(myFormatter.format(reconNum));
    	    		}
					appl.setSdAdjust((CommonFunction.checkNull(adetails1.get(8)).trim()));

				}

				list.add(appl);
			}
		} catch (Exception e) {
			e.printStackTrace();
			e=null;
		}
		finally
		{
			appl = null;
			query = null;
			appdetails = null;
			adetails1 = null;
			loanId=null;
		}
		logger.info("Value of List is in dao is=" + list);
		return list;

	}

	  public ArrayList<InstallmentPlanForCMVO> getInstallType(String loanId) {
          ArrayList<InstallmentPlanForCMVO> list=new ArrayList<InstallmentPlanForCMVO>();
          ArrayList mainList=new ArrayList ();
			ArrayList subList =new ArrayList();
      		
      		try{
      			StringBuilder query=new StringBuilder();
               	 query.append(" select distinct FROM_INSTL_NO,TO_INSTL_NO,(CASE WHEN ((IFNULL(LOAN_INSTALLMENT_TYPE,'')='E' OR IFNULL(LOAN_INSTALLMENT_TYPE,'')='P') AND L.EDIT_DUE_DATE='N') THEN 100 ELSE RECOVERY_PERCENT END ) AS RECOVERY_PERCENT1,LOAN_INSTALLMENT_TYPE,LOAN_NO_OF_INSTALLMENT,PRINCIPAL_AMOUNT,INSTALLMENT_AMOUNT,LOAN_RATE_TYPE,LOAN_LOAN_AMOUNT,RECOVERY_TYPE,DATE_FORMAT(loan_repay_eff_date,'"+dateFormat+"'),DATE_FORMAT(L.LOAN_MATURITY_DATE,'"+dateFormat+"'),DATE_FORMAT(d.due_date,'"+dateFormat+"'),L.EDIT_DUE_DATE,DATE_FORMAT(L.NEXT_DUE_DATE,'"+dateFormat+"') "+ // Query changed for Edit Due Date| Rahul papneja |290102018 // Brijesh * (DATE_FORMAT(L.LOAN_MATURITY_DATE,'"+dateFormat+"'))
               	  	" ,LOAN_REPAYMENT_FREQ "+
               	    "  from  cr_loan_dtl L   left JOIN cr_installment_plan D on D.LOAN_ID=L.LOAN_ID "+
               	  	"  where L.LOAN_ID="+loanId);
      			
      		logger.info("getInstallType Queryl: "+query);
      		
      		mainList=ConnectionDAO.sqlSelect(query.toString());
			for(int i=0;i<mainList.size();i++)
			{ 
				subList= (ArrayList)mainList.get(i);
				if(subList.size()>0){
					InstallmentPlanForCMVO ipVo= new InstallmentPlanForCMVO();
      				ipVo = new InstallmentPlanForCMVO();  
      				if((CommonFunction.checkNull(subList.get(3)).equalsIgnoreCase("E")||CommonFunction.checkNull(subList.get(3)).equalsIgnoreCase("P"))&& CommonFunction.checkNull(subList.get(13)).equalsIgnoreCase("N"))
      				{
      					if(CommonFunction.checkNull(subList.get(0)).equalsIgnoreCase("") || CommonFunction.checkNull(subList.get(1)).equalsIgnoreCase("")){
      						ipVo.setFromInstallment("1");
      						ipVo.setToInstallment((CommonFunction.checkNull(subList.get(4))).trim());
      					}else{
      						ipVo.setFromInstallment((CommonFunction.checkNull(subList.get(0))).trim());
      						ipVo.setToInstallment((CommonFunction.checkNull(subList.get(1))).trim());
      					}
      					
      					 if(!CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase("")){
          				     Number RecoveryPercen = myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());
   	 			   
          				     logger.info("RecoveryPercen: "+RecoveryPercen);			
   	 			             ipVo.setRecoveryPercen(myFormatter.format(RecoveryPercen));
          				      }else{
          				     ipVo.setRecoveryPercen("0");
          				          }
      				    ipVo.setInstallmentType((CommonFunction.checkNull(subList.get(3))).trim());
       			  	ipVo.setTotalInstallment((CommonFunction.checkNull(subList.get(4))).trim());
       			
       			      //ipVo.setRecoveryType("P");
      				}
      				else{
      				    ipVo.setFromInstallment((CommonFunction.checkNull(subList.get(0))).trim());
      				    ipVo.setToInstallment((CommonFunction.checkNull(subList.get(1))).trim());
      				    if(!CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase("")){
      				     Number RecoveryPercen = myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());
	 			   
      				     logger.info("RecoveryPercen: "+RecoveryPercen);			
	 			             ipVo.setRecoveryPercen(myFormatter.format(RecoveryPercen));
      				      }else{
      				     ipVo.setRecoveryPercen("0");
      				          }
      				    ipVo.setInstallmentType((CommonFunction.checkNull(subList.get(3))).trim());
      				    ipVo.setTotalInstallment((CommonFunction.checkNull(subList.get(4))).trim());
      				 
           				
      				    }
      				ipVo.setRateType((CommonFunction.checkNull(subList.get(7))).trim());
     				
      				  if(!CommonFunction.checkNull(subList.get(5)).equalsIgnoreCase("")){
       				     Number princam = myFormatter.parse((CommonFunction.checkNull(subList.get(5))).trim());
   	 			   
       				     logger.info("princ amount: "+princam);			
   	 			         ipVo.setPrinAm(myFormatter.format(princam));
       				      }else{
       				     ipVo.setPrinAm("0.00");
       				          }
        				if(!CommonFunction.checkNull(subList.get(6)).equalsIgnoreCase("")){
       				     Number instam = myFormatter.parse((CommonFunction.checkNull(subList.get(6))).trim());
   	 			   
       				     logger.info("instamount: "+instam);			
   	 			         ipVo.setInstalAm(myFormatter.format(instam));
       				      }else{
       				     ipVo.setInstalAm("0.00");
       				          }
     				
     				if(!CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("")){
       				     Number instam = myFormatter.parse((CommonFunction.checkNull(subList.get(8))).trim());
   	 			   
       				     logger.info("setLoanAmount: "+instam);			
   	 			         ipVo.setLoanAmount(myFormatter.format(instam));
       				      }else{
       				     ipVo.setLoanAmount("0.00");
       				          }
     				ipVo.setRecoveryType((CommonFunction.checkNull(subList.get(9))).trim());
     				//Richa space starts
     				ipVo.setRepayeffdate((CommonFunction.checkNull(subList.get(10))).trim());
     				ipVo.setMaxDate((CommonFunction.checkNull(subList.get(11))).trim());
     				ipVo.setDueDatee((CommonFunction.checkNull(subList.get(12))).trim());
     				//Richa space ends
     				//COde added for Edit Due Date| Rahul papneja |29012018
     				// Vishal changes start
     				ipVo.setEditDueDate((CommonFunction.checkNull(subList.get(13))).trim());
     				ipVo.setInsNextDueDate((CommonFunction.checkNull(subList.get(14))).trim());
     				// Vishal changes end
     			    // added one filed for auto fill due date in installment plan|DEAL_REPAYMENT_FREQ|Vijendra Singh	
     				ipVo.setFrequency((CommonFunction.checkNull(subList.get(15))).trim());     	     				
     				//End Code Vijendra Singh
     				// Ends Here
      				   list.add(ipVo);
     				     ipVo=null;
      			     }
      		  }
			query=null;
      		}catch(Exception e){
      			e.printStackTrace();
      			e=null;
      		} finally{
      			loanId=null;
      		}
      		return list;
      	}

	
		public ArrayList getNoOfDisb(String loanId) {
			
			ArrayList<Object> list=new ArrayList<Object>();
			try{
				StringBuilder query=new StringBuilder();
	        query.append("select distinct DISBURSAL_NO,DISBURSAL_STAGE,DISBURSAL_DESCRIPTION,DATE_FORMAT(PROPOSED_DISBURSAL_DATE,'"+dateFormat+"'),PROPOSED_DISBURSAL_AMOUNT from cr_loan_disbursalsch_dtl D where D.LOAN_ID='"+loanId+"'");
				
			logger.info("getNoOfDisb Queryl: "+query);
			
			NoOfDisbursalVo nVo = null;
			ArrayList disbdeatail = ConnectionDAO.sqlSelect(query.toString());
			logger.info("getNoOfDisb OUTER ARRAYLIST SIZE: "+disbdeatail.size());
			for(int i=0;i<disbdeatail.size();i++){

				logger.info("getDocumentsDetail"+disbdeatail.get(i).toString());
				ArrayList disbdeatail1=(ArrayList)disbdeatail.get(i);
				logger.info("getNoOfDisb INNNER ARRAYLIST SIZE: "+disbdeatail1.size());
				if(disbdeatail1.size()>0)
				{
					nVo = new NoOfDisbursalVo();
					
					nVo.setNoOfDisb((CommonFunction.checkNull(disbdeatail1.get(0))).trim());
					nVo.setSatge((CommonFunction.checkNull(disbdeatail1.get(1))).trim());
					nVo.setDescOfDisb((CommonFunction.checkNull(disbdeatail1.get(2))).trim());
					nVo.setDateOfDisb((CommonFunction.checkNull(disbdeatail1.get(3))).trim());
					
					if(!CommonFunction.checkNull(disbdeatail1.get(4)).equalsIgnoreCase(""))	
		 			{
						Number Amount = myFormatter.parse((CommonFunction.checkNull(disbdeatail1.get(4))).trim());
						logger.info("LoanAmount: "+Amount);			
						nVo.setAmountOfDisb(myFormatter.format(Amount));	
		 			}
					else
					{
						nVo.setAmountOfDisb("");	
					}
					
					list.add(nVo);
					nVo=null;
				}
			}
			query=null;
			}catch(Exception e){
				e.printStackTrace();
				e=null;
			} finally{
				loanId=null;
			}
			return list;
		}


		public ArrayList<Object> getCustomerInCMList(String loanId,String source) {
			logger.info("id in getCustomerInCMList");
			ArrayList<Object> list=new ArrayList<Object>();
			logger.info("In, getCustomerInCMList() *************************************    :"+loanId);
			String roleTable="cr_loan_customer_role";
			String gcdTable="gcd_customer_m";
			if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
			{
				roleTable="cr_loan_customer_role_edit";
				gcdTable="gcd_customer_m_edit";
			}
			try
			{
				StringBuilder query=new StringBuilder();
				query.append("select GCD_ID,LOAN_CUSTOMER_ROLE_TYPE,LOAN_CUSTOMER_TYPE,CUSTOMER_NAME,GUARANTEE_AMOUNT,DESCRIPTION  " +
				" from "+roleTable+" r,"+gcdTable+" g, generic_master m   "+ 
				" where  LOAN_ID="+(CommonFunction.checkNull(loanId).trim())+"  and r.GCD_ID=g.CUSTOMER_ID " +
				" and r.LOAN_CUSTOMER_ROLE_TYPE=m.VALUE and m.GENERIC_KEY='CUST_ROLE'") ;
				logger.info("getCustomerInCMList :Query "+query);		
				CreditProcessingCustomerEntryVo vo=null;
				ArrayList roleList = ConnectionDAO.sqlSelect(query.toString());
				logger.info("getCustomerInCMList "+roleList.size());
				for(int i=0;i<roleList.size();i++)
				{
					ArrayList data=(ArrayList)roleList.get(i);
					if(data.size()>0)
					{
					vo =new CreditProcessingCustomerEntryVo();
					//vo.setRoleId((CommonFunction.checkNull(data.get(0))));
					vo.setCustomerId((CommonFunction.checkNull(data.get(0))));
					vo.setApplicantType((CommonFunction.checkNull(data.get(1))));
					if((CommonFunction.checkNull(data.get(2))).equalsIgnoreCase("C"))
					{
						vo.setApplicantCategory("CORPORATE");
					}
					else if((CommonFunction.checkNull(data.get(2))).equalsIgnoreCase("I"))
					{
						vo.setApplicantCategory("INDIVIDUAL");
					}
											
					vo.setCustomerName((CommonFunction.checkNull(data.get(3)).toString()));
					
					
					if(!(CommonFunction.checkNull(data.get(4)).toString()).equalsIgnoreCase(""))
		    		{
		    			Number reconNum =myFormatter.parse((CommonFunction.checkNull(data.get(4))).trim());
		    			vo.setGuaranteeAmount(myFormatter.format(reconNum));
		    		}	
					
					vo.setApplicantDesc((CommonFunction.checkNull(data.get(5)).toString()));		
					list.add(vo);
					vo=null;
				}
			}
			query=null;
			}catch(Exception e){
				e.printStackTrace();
				e=null;
			}
			finally{
				loanId=null;
				source=null;
				roleTable=null;
				gcdTable=null;
			}
			return list;
		}
	

		public ArrayList<LoanDetailForCMVO> getListOfValues(int dealLoanId,String businessDate) {
			ArrayList<LoanDetailForCMVO> loanDetailList = new ArrayList<LoanDetailForCMVO>();
			logger.info("In getListOfValues: " + dealLoanId);
			ArrayList mainlist = new ArrayList();
			ArrayList subList = new ArrayList();
			StringBuilder query = new StringBuilder();
			LoanDetailForCMVO loanvo = null;
			try {
				query.append(" select DEAL_LOAN_ID,c.DEAL_ID,DEAL_SCHEME,DEAL_PRODUCT,DEAL_PRODUCT_TYPE,c.DEAL_SANCTION_AMOUNT,");
				query.append(" DEAL_TENURE,DATE_FORMAT(DEAL_SANCTION_VALID_TILL,'"+dateFormat+"'),c.DEAL_ASSET_COST,DEAL_MARGIN_RATE,");
				 query.append(" DEAL_MARGIN_AMOUNT,c.DEAL_SANCTION_AMOUNT,DEAL_RATE_TYPE,DEAL_RATE_METHOD,DEAL_BASE_RATE_TYPE,DEAL_BASE_RATE,");
				query.append(" DEAL_MARKUP,DEAL_FINAL_RATE,DEAL_REPAYMENT_TYPE,DEAL_INSTALLMENT_TYPE,DEAL_REPAYMENT_FREQ,DEAL_REPAYMENT_MODE,");
				query.append(" DEAL_INSTALLMENT_MODE,DEAL_PROMO_SCHEME,d.DEAL_BRANCH,d.DEAL_INDUSTRY_ID,d.DEAL_SUB_INDUSTRY_ID,d.GCD_CUSTOMER_ID,");
				query.append(" d.DEAL_CUSTOMER_TYPE,d.DEAL_EXISTING_CUSTOMER,p.PRODUCT_DESC,s.SCHEME_DESC,c.DEAL_PRODUCT_CATEGORY,");
				query.append(" c.DEAL_NO_OF_INSTALLMENT,c.DEAL_UTILIZED_AMOUNT,DATE_FORMAT(DEAL_SANCTION_DATE,'"+dateFormat+"'),DEAL_SECTOR_TYPE,m.DESCRIPTION,"); 
				query.append(" c.DEAL_ADVANCE_INSTL,c.DEAL_DUE_DAY,c.DEAL_IRR1,c.DEAL_IRR2,c.DEAL_EFF_RATE,DATE_FORMAT(max(A.APPROVAL_DATE),'"+dateFormat+"'),");
				query.append(" c.DEAL_FLAT_RATE,p.ONE_DEAL_ONE_LOAN,DATE_FORMAT(c.DEAL_REPAY_EFF_DATE,'"+dateFormat+"'),DATE_FORMAT(c.DEAL_MATURITY_DATE,'"+dateFormat+"'),DATE_FORMAT(C.NEXT_DUE_DATE,'"+dateFormat+"'),s.MIN_FLAT_RATE," +
						"s.MAX_FLAT_RATE,s.MIN_EFF_RATE,s.MAX_EFF_RATE,s.MIN_TENURE,s.MAX_TENURE,d.AREA_CODE,(SELECT B.AREA_CODE_NAME FROM com_areacode_m B WHERE B.AREA_CODE=d.AREA_CODE)AS areaCodeName,p.ASSET_FLAG,DEAL_INT_CALC_FROM,i.DESCRIPTION,p.DAYS_BASIS,c.TENURE_IN_DAY,(select DESCRIPTION from generic_master where GENERIC_KEY='INSTALLMENT_TYPE' AND REC_STATUS='A' AND VALUE=DEAL_INSTALLMENT_TYPE)AS INSTALLMENT_TYPE_DESC,c.NET_LTV,c.DEAL_FLOATING_FIXED_PERIOD,ifnull(c.NO_OF_ASSET,0),d.DEAL_APPLICATION_FORM_NO,");//Richa HP VAT TAX
				query.append("IFNULL(C.VAT_AMOUNT,'')VAT_AMOUNT,IFNULL(C.VAT_PERCENT,0.00)VAT_PERCENT,IFNULL(C.service_Tax,0.00)service_tax ,IFNULL(C.service_amount,'')service_amount,ifnull(c.deal_credit_period,0), ");// add by saorabh
				query.append("c.INT_COMP_FREQ,c.INT_METHOD,c.INT_FREQ ");// add by saorabh
				query.append(",DATE_FORMAT(C.INTEREST_DUE_DATE,'%d-%m-%Y'),ifnull(c.INSURANCE_PREMIUM,0)as insurance_premuim,ifnull(c.deal_loan_amount_new,c.deal_loan_amount)as req_amt,c.EDIT_DUE_DATE,IFNULL(c.BUSINESS_TYPE,'OF') ");// add by ajay | Query Updated for Edit Due Date| Rahul Papneja |29012018
				 
				query.append(" from cr_deal_loan_dtl c");
				query.append(" left join cr_deal_dtl d on c.DEAL_ID=d.DEAL_ID");
				query.append(" left join cr_product_m p on c.DEAL_PRODUCT=p.PRODUCT_ID");
				query.append(" left join cr_scheme_m s on c.DEAL_SCHEME=s.SCHEME_ID");
				query.append(" left join gcd_customer_m g on g.CUSTOMER_ID=d.GCD_CUSTOMER_ID");
				query.append(" left JOIN generic_master m on DEAL_SECTOR_TYPE=m.VALUE and m.GENERIC_KEY='SECTOR_TYPE' ");
				query.append(" left join cr_deal_approval_dtl A on A.DEAL_ID=c.DEAL_ID");
				query.append(" left JOIN generic_master i on DEAL_INT_CALC_FROM=i.VALUE and i.GENERIC_KEY='INTERST_CAL_FROM' ");//Surendra..
				//query.append(" left join cr_deal_collateral_m cdcm on cdcm.DEAL_ID=c.DEAL_ID ");//Richa..HP VAT TAX
				//query.append(" left join cr_asset_collateral_m cacm on cacm.ASSET_ID=cdcm.ASSETID and cacm.ASSET_COLLATERAL_CLASS='VEHICLE' ");//Richa.HP VAT TAX.
				query.append(" where DEAL_LOAN_ID='" + dealLoanId + "'");

				logger.info("In getListOfValues  Query  :  " + query.toString());
				mainlist = ConnectionDAO.sqlSelect(query.toString());
				int size = mainlist.size();
				logger.info("In getListOfValues,,,,," + size);
				for (int i = 0; i < size; i++) {

					subList = (ArrayList) mainlist.get(i);
					logger.info("In getListOfValues..." + subList.size());
					if (subList.size() > 0) {

						loanvo = new LoanDetailForCMVO();
						loanvo.setLoanId((CommonFunction.checkNull(subList.get(0))).trim());
						loanvo.setDealNo((CommonFunction.checkNull(subList.get(1))).trim());
						loanvo.setScheme((CommonFunction.checkNull(subList.get(2))).trim());
						loanvo.setProduct((CommonFunction.checkNull(subList.get(3))).trim());
						loanvo.setProductType((CommonFunction.checkNull(subList.get(4))).trim());
						if(!CommonFunction.checkNull(subList.get(5)).trim().equalsIgnoreCase(""))
	    	    		{
		    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(5))).trim());  
		    	    		loanvo.setLoanAmount(myFormatter.format(reconNum));
	    	    		}
						//loanvo.setLoanAmount((CommonFunction.checkNull(subList.get(5))).trim());
						loanvo.setTenureMonths((CommonFunction.checkNull(subList.get(6))).trim());
						loanvo.setSanctionedValidtill((CommonFunction.checkNull(subList.get(7))).trim());
						if(!CommonFunction.checkNull(subList.get(8)).trim().equalsIgnoreCase(""))
	    	    		{
		    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(8))).trim());  
		    	    		loanvo.setAssetCost(myFormatter.format(reconNum));
	    	    		}
						//loanvo.setAssetCost((CommonFunction.checkNull(subList.get(8))).trim());
						if(!CommonFunction.checkNull(subList.get(9)).trim().equalsIgnoreCase(""))
	    	    		{
		    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(9))).trim());  
		    	    		loanvo.setMargin(myFormatter.format(reconNum));
		    	    		loanvo.setLtvPerc(myFormatter.format(100-reconNum.doubleValue()));
	    	    		}
						//loanvo.setMargin((CommonFunction.checkNull(subList.get(9))).trim());
						
						if(!CommonFunction.checkNull(subList.get(10)).trim().equalsIgnoreCase(""))
	    	    		{
		    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(10))).trim());  
		    	    		loanvo.setMarginAmount(myFormatter.format(reconNum));
	    	    		}
						
						//loanvo.setMarginAmount((CommonFunction.checkNull(subList.get(10))).trim());
						
						if(!CommonFunction.checkNull(subList.get(11)).trim().equalsIgnoreCase(""))
	    	    		{
		    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(11))).trim());  
		    	    		loanvo.setSanctionedLoanAmount(myFormatter.format(reconNum));
	    	    		}
						//loanvo.setSanctionedLoanAmount((CommonFunction.checkNull(subList.get(11))).trim());
					
						loanvo.setRateType((CommonFunction.checkNull(subList.get(12))).trim());
						loanvo.setDealRateMethod((CommonFunction.checkNull(subList.get(13))).trim());
						loanvo.setBaseRateType((CommonFunction.checkNull(subList.get(14))).trim());
						loanvo.setBaseRate((CommonFunction.checkNull(subList.get(15))).trim());
						if(!CommonFunction.checkNull(subList.get(16)).trim().equalsIgnoreCase(""))
	    	    		{
		    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(16))).trim());  
		    	    		loanvo.setMarkUp(myFormatter.format(reconNum));
	    	    		}
						//loanvo.setMarkUp((CommonFunction.checkNull(subList.get(16))).trim());
						
						if(!CommonFunction.checkNull(subList.get(17)).trim().equalsIgnoreCase(""))
	    	    		{
		    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(17))).trim());  
		    	    		loanvo.setEffectiveRate(myFormatter.format(reconNum));
	    	    		}
						
						//loanvo.setEffectiveRate((CommonFunction.checkNull(subList.get(17))).trim());
						loanvo.setRepaymentType((CommonFunction.checkNull(subList.get(18))).trim());
						loanvo.setInstallmentType((CommonFunction.checkNull(subList.get(19))).trim());
						loanvo.setFrequency((CommonFunction.checkNull(subList.get(20))).trim());
						loanvo.setRepaymentMode((CommonFunction.checkNull(subList.get(21))).trim());
						loanvo.setDealInstallmentMode((CommonFunction.checkNull(subList.get(22))).trim());

						loanvo.setDealPromo((CommonFunction.checkNull(subList.get(23))).trim());
						loanvo.setLoanBranch((CommonFunction.checkNull(subList.get(24))).trim());
						loanvo.setLoanIndustry((CommonFunction.checkNull(subList.get(25))).trim());
						loanvo.setLoanSubIndustry((CommonFunction.checkNull(subList.get(26))).trim());
						loanvo.setLoanCustomerId((CommonFunction.checkNull(subList.get(27))).trim());
						loanvo.setLoanCustomerType((CommonFunction.checkNull(subList.get(28))).trim());
						loanvo.setLoanCustomerExisting((CommonFunction.checkNull(subList.get(29))).trim());
						loanvo.setShowProduct((CommonFunction.checkNull(subList.get(30))).trim());
						loanvo.setShowScheme((CommonFunction.checkNull(subList.get(31))).trim());
						
						loanvo.setProductCat((CommonFunction.checkNull(subList.get(32))).trim());
						loanvo.setLoanNoofInstall((CommonFunction.checkNull(subList.get(33))).trim());
						if(!CommonFunction.checkNull(subList.get(34)).trim().equalsIgnoreCase(""))
	    	    		{
		    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(34))).trim());  
		    	    		loanvo.setUtilizeLoanAmount(myFormatter.format(reconNum));
	    	    		}
						//loanvo.setUtilizeLoanAmount((CommonFunction.checkNull(subList.get(34))).trim());
						loanvo.setSanctionedDate((CommonFunction.checkNull(subList.get(35))).trim());
						loanvo.setSectorType((CommonFunction.checkNull(subList.get(36))).trim());
						loanvo.setShowSectorType((CommonFunction.checkNull(subList.get(37))).trim());
						loanvo.setInstallments((CommonFunction.checkNull(subList.get(38))).trim());
						loanvo.setDueDay((CommonFunction.checkNull(subList.get(39))).trim());
						
						if(!CommonFunction.checkNull(subList.get(40)).trim().equalsIgnoreCase(""))
	    	    		{
		    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(40))).trim());  
		    	    		loanvo.setDealIrr1(myFormatter.format(reconNum));
	    	    		}
						
						//loanvo.setDealIrr1((CommonFunction.checkNull(subList.get(40))).trim());
						if(!CommonFunction.checkNull(subList.get(41)).trim().equalsIgnoreCase(""))
	    	    		{
		    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(41))).trim());  
		    	    		loanvo.setDealIrr2(myFormatter.format(reconNum));
		    	    		logger.info("DealIrr2: "+loanvo.getDealIrr2());
	    	    		}
					//	loanvo.setDealIrr2((CommonFunction.checkNull(subList.get(41))).trim());
						if(!CommonFunction.checkNull(subList.get(42)).trim().equalsIgnoreCase(""))
	    	    		{
		    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(42))).trim());  
		    	    		loanvo.setDealEffectiveRate(myFormatter.format(reconNum));
	    	    		}
						loanvo.setSanctionDate((CommonFunction.checkNull(subList.get(43))).trim());
						
						if(!CommonFunction.checkNull(subList.get(44)).trim().equalsIgnoreCase(""))
	    	    		{
		    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(44))).trim());  
		    	    		loanvo.setDealFlatRate(myFormatter.format(reconNum));
	    	    		}
						loanvo.setOneDealOneLoan((CommonFunction.checkNull(subList.get(45))).trim());
						loanvo.setMaturityDate((CommonFunction.checkNull(subList.get(47))).trim());
						loanvo.setRepayEffectiveDate((CommonFunction.checkNull(subList.get(46))).trim());
						loanvo.setNextDueDate((CommonFunction.checkNull(subList.get(48))).trim());
						loanvo.setAgreementDate(businessDate);
						//code added by neeraj tripathi
						loanvo.setMinFlatRate((CommonFunction.checkNull(subList.get(49))).trim());
						loanvo.setMaxFlatRate((CommonFunction.checkNull(subList.get(50))).trim());
						loanvo.setMinEffRate((CommonFunction.checkNull(subList.get(51))).trim());
						loanvo.setMaxEffRate((CommonFunction.checkNull(subList.get(52))).trim());
						loanvo.setMinTenure((CommonFunction.checkNull(subList.get(53))).trim());
						loanvo.setMaxTenure((CommonFunction.checkNull(subList.get(54))).trim());
						
						//neeraj tripathi's space end
						//KANIKA CODE
						loanvo.setLbxareaCodeVal((CommonFunction.checkNull(subList.get(55))).trim());
						loanvo.setAreaCodename((CommonFunction.checkNull(subList.get(56))).trim());
//Prashant
						loanvo.setAssetFlag((CommonFunction.checkNull(subList.get(57))).trim());
						//KANIKA CODE END HERE
						//@Surendra..
						loanvo.setInterestCalc((CommonFunction.checkNull(subList.get(58))).trim());
						loanvo.setShowInterestCalc((CommonFunction.checkNull(subList.get(59))).trim());
						loanvo.setDaysBasis((CommonFunction.checkNull(subList.get(60))).trim());
						loanvo.setTenureInDay((CommonFunction.checkNull(subList.get(61))).trim());
						loanvo.setShowInstallment ((CommonFunction.checkNull(subList.get(62)).trim()));
						loanvo.setNetLtv((CommonFunction.checkNull(subList.get(63)).trim()));
						loanvo.setFixPriod((CommonFunction.checkNull(subList.get(64)).trim()));
						loanvo.setNoOfAsset((CommonFunction.checkNull(subList.get(65)).trim()));
						loanvo.setFormNo((CommonFunction.checkNull(subList.get(66)).trim()));
						loanvo.setTotalVatAmt((CommonFunction.checkNull(subList.get(67)).trim())); //Richa HP VAT TAX
						loanvo.setVatPercent((CommonFunction.checkNull(subList.get(68)).trim())); //add by saorabh HP VAT TAX
						loanvo.setServiceTax((CommonFunction.checkNull(subList.get(69)).trim())); //Richa HP VAT TAX
						loanvo.setServiceAmount((CommonFunction.checkNull(subList.get(70)).trim())); //add by saorabh HP VAT TAX
						loanvo.setCreditPeriod((CommonFunction.checkNull(subList.get(71)).trim())); //add by Richa
						loanvo.setInterestCompoundingFrequency((CommonFunction.checkNull(subList.get(72)).trim()));
						loanvo.setInterestCalculationMethod((CommonFunction.checkNull(subList.get(73)).trim()));
						loanvo.setInterestFrequency((CommonFunction.checkNull(subList.get(74)).trim()));
						//@Surendra..
						loanvo.setInterestDueDate((CommonFunction.checkNull(subList.get(75)).trim()));//add by Ajay 
						loanvo.setInsurancePremium((CommonFunction.checkNull(subList.get(76)).trim()));
						if(!CommonFunction.checkNull(subList.get(77)).trim().equalsIgnoreCase(""))
	    	    		{
		    	    		Number reconNumreqAmt =myFormatter.parse((CommonFunction.checkNull(subList.get(77))).trim());  
		    	    		loanvo.setRequestedLoamt(myFormatter.format(reconNumreqAmt));
	    	    		}else{
	    	    			loanvo.setRequestedLoamt("0");
	    	    		
	    	    		}
						// Code Added for Edit Due Date| Rahul papneja| 29012018
						loanvo.setEditDueDate((CommonFunction.checkNull(subList.get(78)).trim()));//add by Vishal
						loanvo.setBusinessType((CommonFunction.checkNull(subList.get(79)).trim()));//add by Rohit Sharma
						// Ends Here| Rahul Papneja
						loanDetailList.add(loanvo);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				e=null;
			}
			finally
			{
				mainlist = null;
				subList = null;
				query = null;
				loanvo = null;
				businessDate=null;
			}
			return loanDetailList;
		}


public ArrayList updateListOfvalue(LoanDetailForCMVO loanDetailForCMVO) 
{

	logger.info("In updateListOfvalue()");
	ArrayList result=new ArrayList();
	int maxId = 0;
	boolean status = false;
	ArrayList qryList = new ArrayList();
	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	String q = "select LOAN_ID from cr_loan_dtl where LOAN_ID='"+loanDetailForCMVO.getLoanId()+"'";
	logger.info("In CreditManagementDAOImpl getListOfValues query:- "+q);
	boolean check = ConnectionDAO.checkStatus(q);
	ArrayList deleteArl = new ArrayList();
	String instalQuery ="";
	String instalStatus ="";
	String deleteInst= "";
	ArrayList qryListUp = new ArrayList();
	PrepStmtObject updatePrepStmtObject = new PrepStmtObject();
	StringBuilder queryUpdate = new StringBuilder();
	StringBuilder bufInsSql = new StringBuilder();
	StringBuilder query3 =new StringBuilder();
	StringBuilder id =new StringBuilder();
	try 
	{
		//sachin
		String nextdueDate ="";
		if (!CommonFunction.checkNull(loanDetailForCMVO.getNextDueDate()).equalsIgnoreCase(""))
		{
			nextdueDate = loanDetailForCMVO.getNextDueDate().trim();
		    nextdueDate=nextdueDate.substring(0,10);
		    logger.info("nextdueDate"+nextdueDate);
		}
		
		//sachin
		
		//KK
		BigDecimal loanMarginRate=new BigDecimal(0);
		if ((!CommonFunction.checkNull(loanDetailForCMVO.getAssetCost()).equalsIgnoreCase(""))&& (!CommonFunction.checkNull(loanDetailForCMVO.getLoanAmount()).equalsIgnoreCase("")))
		{
			String assetCost=myFormatter.parse(loanDetailForCMVO.getAssetCost().trim()).toString();
			String loanAmount=myFormatter.parse(loanDetailForCMVO.getLoanAmount().trim()).toString();						
			BigDecimal loanAssetCost= new BigDecimal(assetCost);
			BigDecimal loanLoanAmount= new BigDecimal(loanAmount);
			loanMarginRate=((loanAssetCost.subtract(loanLoanAmount)).multiply(new BigDecimal(100)).divide(loanAssetCost,4,BigDecimal.ROUND_HALF_UP));
			logger.info("loanMarginRate:::::::::"+loanMarginRate);
		}	
		//KK
		
		logger.info("  check :  "+check);
		if (check) 
		{
		
			instalQuery = "select LOAN_ID from cr_loan_dtl where  LOAN_ID="+loanDetailForCMVO.getLoanId().trim()+" and LOAN_LOAN_AMOUNT="+myFormatter.parse(StringEscapeUtils.escapeSql(loanDetailForCMVO.getLoanAmount()).trim()).toString()+" and DATE_FORMAT(LOAN_REPAY_EFF_DATE,'"+dateFormat+"')='"+loanDetailForCMVO.getRepayEffectiveDate()+"'";
			logger.info("In LoanDetailProcessAction(saveLoan) Q "+ instalQuery);
			instalStatus = ConnectionDAO.singleReturn(instalQuery);
			logger.info("instalStatus:  " + instalStatus);
			if(CommonFunction.checkNull(instalStatus).equalsIgnoreCase(""))
			{
				deleteInst="DELETE from cr_installment_plan where LOAN_ID="+loanDetailForCMVO.getLoanId().trim()+" ";
				logger.info("Deletion is Q ="+deleteInst);
				deleteArl.add(deleteInst);
				status=ConnectionDAO.sqlInsUpdDelete(deleteArl);
				logger.info("Status of Deletion is ="+status);
			}			
			logger.info("In Update CreditManagementDAOImpl ");
			queryUpdate.append(" update cr_loan_dtl set FUNDING_TYPE=?,LOAN_NUMBER_DISBURSAL=?,LOAN_AGREEMENT_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND), "); 
			queryUpdate.append(" LOAN_REPAY_EFF_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),");
			queryUpdate.append(" LOAN_ADVANCE_INSTL=?,LOAN_LOAN_AMOUNT=?,LOAN_MATURITY_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),");
			queryUpdate.append(" LOAN_MAKER_REMARKS=?,PO_DO_FLAG=?, LOAN_DUE_DAY=?,NEXT_DUE_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),INSUARENCE_DONE_BY=?,MAKER_ID=?,");
			queryUpdate.append(" MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),LOAN_ASSET_COST=?,LOAN_MARGIN_RATE=?,LOAN_MARGIN_AMOUNT=?,LOAN_TENURE=?,LOAN_MARKUP=?,LOAN_FINAL_RATE=?,LOAN_NO_OF_INSTALLMENT=?,AREA_CODE=?,TENURE_IN_DAY=?,LOAN_INSTALLMENT_MODE=?,LOAN_ADVANCE_INSTL=?,LOAN_INSTALLMENT_TYPE=?," );
			queryUpdate.append(" LOAN_REPAYMENT_MODE=?,LOAN_FLOATING_FIXED_PERIOD=?,LOAN_SECTOR_TYPE=?,NO_OF_ASSET=?,LOAN_REFERENCE_NO=?,LOAN_RATE_TYPE=?,LOAN_BASE_RATE_TYPE=?,LOAN_BASE_RATE=?,LOAN_RATE_METHOD=?,LOAN_REPAYMENT_FREQ=? ,LOAN_CLASSIFICATION=?,loan_credit_period=?,"
					+ " INT_COMP_FREQ=?, INT_METHOD=?,INT_FREQ=?, INTEREST_DUE_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) ,insurance_premium=?,DEAL_LOAN_AMOUNT_NEW=?,EDIT_DUE_DATE=?,FIRST_INTEREST_DUE_DATE=STR_TO_DATE(?, '"+dateFormat+"'), GROSS_BLOCK=?,NET_BLOCK=?,LOAN_EFF_RATE=? where LOAN_ID=?");

			if (CommonFunction.checkNull(loanDetailForCMVO.getBusinessType()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getBusinessType())).trim());
			if (CommonFunction.checkNull(loanDetailForCMVO.getNoOfDisbursal()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getNoOfDisbursal())).trim());
			
			if(CommonFunction.checkNull(loanDetailForCMVO.getAgreementDate()).equals(""))
				updatePrepStmtObject.addNull();
		    else
		    	updatePrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getAgreementDate()).trim()));
			
			if(CommonFunction.checkNull(loanDetailForCMVO.getRepayEffectiveDate()).equals(""))
				updatePrepStmtObject.addNull();
		    else
		    	updatePrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getRepayEffectiveDate()).trim()));
						
			if (CommonFunction.checkNull(loanDetailForCMVO.getInstallments()).equalsIgnoreCase(""))
				updatePrepStmtObject.addString("0");
			else
				updatePrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getInstallments()).trim()));			
			
			if (CommonFunction.checkNull(loanDetailForCMVO.getLoanAmount()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(loanDetailForCMVO.getLoanAmount()).trim())).toString());
			
			if (CommonFunction.checkNull(loanDetailForCMVO.getMaturityDate()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getMaturityDate()).trim()));
			
			if (CommonFunction.checkNull(loanDetailForCMVO.getRemarks()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getRemarks()).trim()));
			
			if (CommonFunction.checkNull(loanDetailForCMVO.getPodoFlag()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getPodoFlag()).trim()));
			
			if (CommonFunction.checkNull(loanDetailForCMVO.getDueDay()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getDueDay()).trim()));
			
			if ((CommonFunction.checkNull(nextdueDate).trim()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(CommonFunction.checkNull(nextdueDate).trim());			
			
			if (CommonFunction.checkNull(loanDetailForCMVO.getInsuranceDoneBy()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getInsuranceDoneBy()).trim()));
		
			if ((CommonFunction.checkNull(loanDetailForCMVO.getUserId())).trim().equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((loanDetailForCMVO.getUserId()).trim());
			
			if ((CommonFunction.checkNull(loanDetailForCMVO.getBussinessDate()).trim()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(CommonFunction.checkNull(loanDetailForCMVO.getBussinessDate()).trim());
			
			if (CommonFunction.checkNull(loanDetailForCMVO.getAssetCost()).equalsIgnoreCase(""))
				updatePrepStmtObject.addFloat(0.0000);
			else
				updatePrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(loanDetailForCMVO.getAssetCost()).trim())).toString());
			
//			if (CommonFunction.checkNull(loanDetailForCMVO.getMargin()).equalsIgnoreCase(""))
//				updatePrepStmtObject.addNull();
//			else
//				updatePrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(loanDetailForCMVO.getMargin()).trim())).toString());
			
    		if(CommonFunction.checkNull(loanMarginRate).trim().equalsIgnoreCase("0"))
    			updatePrepStmtObject.addNull();
	    	else
	    		updatePrepStmtObject.addString(StringEscapeUtils.escapeSql(loanMarginRate.toString()).toString());
			
			if (CommonFunction.checkNull(loanDetailForCMVO.getMarginAmount()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(loanDetailForCMVO.getMarginAmount()).trim())).toString());
			
			if (CommonFunction.checkNull(loanDetailForCMVO.getTenureMonths()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(loanDetailForCMVO.getTenureMonths()).trim())).toString());
			
			if (CommonFunction.checkNull(loanDetailForCMVO.getMarkUp()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(loanDetailForCMVO.getMarkUp()).trim())).toString());
			
			if (CommonFunction.checkNull(loanDetailForCMVO.getEffectiveRate()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(loanDetailForCMVO.getEffectiveRate()).trim())).toString());
			
			if (CommonFunction.checkNull(loanDetailForCMVO.getLoanNoofInstall()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(CommonFunction.checkNull(loanDetailForCMVO.getLoanNoofInstall()).trim());
			
			if (loanDetailForCMVO.getLbxareaCodeVal().equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getLbxareaCodeVal()).trim()));
			
			if (CommonFunction.checkNull(loanDetailForCMVO.getTenureInDay()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(CommonFunction.checkNull(loanDetailForCMVO.getTenureInDay()).trim());
			
			//code added by neeraj
			if (CommonFunction.checkNull(loanDetailForCMVO.getDealInstallmentMode()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(CommonFunction.checkNull(loanDetailForCMVO.getDealInstallmentMode()).trim());
			
			if (CommonFunction.checkNull(loanDetailForCMVO.getInstallments()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(CommonFunction.checkNull(loanDetailForCMVO.getInstallments()).trim());
			
			if (CommonFunction.checkNull(loanDetailForCMVO.getInstallmentType()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(CommonFunction.checkNull(loanDetailForCMVO.getInstallmentType()).trim());
			
			if (CommonFunction.checkNull(loanDetailForCMVO.getRepaymentMode()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(CommonFunction.checkNull(loanDetailForCMVO.getRepaymentMode()).trim());
			//neeraj space end
			if (CommonFunction.checkNull(loanDetailForCMVO.getFixPriod()).equalsIgnoreCase(""))
				updatePrepStmtObject.addInt(0);
			else
				updatePrepStmtObject.addString(CommonFunction.checkNull(loanDetailForCMVO.getFixPriod()).trim());
			
			if (CommonFunction.checkNull(loanDetailForCMVO.getSectorType()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getSectorType()).trim()));//sector Type
			
			if (CommonFunction.checkNull(loanDetailForCMVO.getNoOfAsset()).equalsIgnoreCase(""))
				updatePrepStmtObject.addInt(0);
			else
				updatePrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getNoOfAsset()).trim()));//NO_OF_ASSET
			
			if (CommonFunction.checkNull(loanDetailForCMVO.getFormNo()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getFormNo()).trim()));//LOAN_REFERENCE_NO			
		//Nishant Space starts
			if (CommonFunction.checkNull(loanDetailForCMVO.getRateType()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getRateType()).trim()));//LOAN_RATE_TYPE
			
			if (CommonFunction.checkNull(loanDetailForCMVO.getBaseRateType()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getBaseRateType()).trim()));//LOAN_BASE_RATE_TYPE
			
			if (CommonFunction.checkNull(loanDetailForCMVO.getBaseRate()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getBaseRate()).trim()));//LOAN_BASE_RATE
						
			if (CommonFunction.checkNull(loanDetailForCMVO.getDealRateMethod()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getDealRateMethod()).trim()));//LOAN_RATE_METHOD
			if (CommonFunction.checkNull(loanDetailForCMVO.getFrequency()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getFrequency()).trim()));//LOAN_REPAYMENT_FREQ
		//Nishant Space ends
			if (CommonFunction.checkNull(loanDetailForCMVO.getLbxLoanClassification()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getLbxLoanClassification()).trim()));//LOAN_REFERENCE_NO			
		//added by Richa
			if (CommonFunction.checkNull(loanDetailForCMVO.getCreditPeriod()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getCreditPeriod()).trim()));
			//ended by richa
			
			if (CommonFunction.checkNull((loanDetailForCMVO.getInterestCompoundingFrequency())).trim().equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((loanDetailForCMVO.getInterestCompoundingFrequency()).trim());	
			
			if (CommonFunction.checkNull((loanDetailForCMVO.getInterestCalculationMethod())).trim().equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((loanDetailForCMVO.getInterestCalculationMethod()).trim());	
			
			if (CommonFunction.checkNull((loanDetailForCMVO.getInterestFrequency())).trim().equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((loanDetailForCMVO.getInterestFrequency()).trim());	
			
			if (CommonFunction.checkNull((loanDetailForCMVO.getInterestDueDate())).trim().equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((loanDetailForCMVO.getInterestDueDate()).trim());	
			 if (CommonFunction.checkNull(loanDetailForCMVO.getInsurancePremium()).equalsIgnoreCase(""))
					updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getInsurancePremium()).trim()));//insurance premium			
						
			if (CommonFunction.checkNull(loanDetailForCMVO.getRequestedLoamt()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(loanDetailForCMVO.getRequestedLoamt()).trim())).toString()); //deal_loan_amount_new
			
			// Code Added for Edit Due Date| Rahul papneja| 29012018
			// Vishal changes start
						if (CommonFunction.checkNull(loanDetailForCMVO.getEditDueDate()).equalsIgnoreCase(""))//EDIT_DUE_DATE
							updatePrepStmtObject.addNull();
						else
							updatePrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getEditDueDate()).trim()));
						// Vishal changes end
			// Ends Here| Rahul papneja
						
						if (CommonFunction.checkNull(loanDetailForCMVO.getFirstInterestDueDate()).equalsIgnoreCase(""))//EDIT_DUE_DATE
							updatePrepStmtObject.addNull();
						else
							updatePrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getFirstInterestDueDate()).trim()));
						
			
						if (CommonFunction.checkNull(loanDetailForCMVO.getGrossBlock()).equalsIgnoreCase(""))
							updatePrepStmtObject.addNull();
						else
							updatePrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getGrossBlock()).trim()));	
						
						if (CommonFunction.checkNull(loanDetailForCMVO.getNetBlock()).equalsIgnoreCase(""))
							updatePrepStmtObject.addNull();
						else
							updatePrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getNetBlock()).trim()));
						

				if (CommonFunction.checkNull(loanDetailForCMVO.getEffectiveRate()).equalsIgnoreCase(""))
					updatePrepStmtObject.addNull();
				else
					updatePrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(loanDetailForCMVO.getEffectiveRate()).trim())).toString());
									
						
			if (CommonFunction.checkNull(loanDetailForCMVO.getLoanId()).equalsIgnoreCase(""))
				updatePrepStmtObject.addNull();
			else
				updatePrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getLoanId()).trim()));
			
			updatePrepStmtObject.setSql(queryUpdate.toString());
			logger.info("IN updateListOfvalue() insert query1 ### "+ updatePrepStmtObject.printQuery());
			qryListUp.add(updatePrepStmtObject);
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryListUp);
							
			if (status) 
			{
				maxId = Integer.parseInt(loanDetailForCMVO.getLoanId());
				// add by vijendra singh				
					deleteInst="DELETE from cr_installment_plan where LOAN_ID="+loanDetailForCMVO.getLoanId().trim()+" ";
					logger.info("Deletion is Q ="+deleteInst);
					deleteArl.add(deleteInst);
					status=ConnectionDAO.sqlInsUpdDelete(deleteArl);
					logger.info("Status of Deletion is ="+status);				
				//end code by vijendra singh
		    	
				String seqQuery="update cr_sd_m s set SD_AMOUNT=(select DEAL_CHARGE_FINAL_AMOUNT from  cr_loan_dtl L,cr_txncharges_dtl C where L.LOAN_ID="+maxId+" and L.LOAN_ID=C.TXN_ID and c.DEAL_CHARGE_CODE=103 and TXN_TYPE='LIM' limit 1) where s.TXN_ID="+maxId+" and TXN_TYPE='LIM'";
		    	logger.info("seqQuery: "+seqQuery);
		    	ArrayList al=new ArrayList();
		    	al.add(seqQuery);
		    	boolean st = ConnectionDAO.sqlInsUpdDelete(al);
		    	logger.info("status: "+st);
		    	String fetchQ="select SD_COMPOUNDING_FREQ,SD_INTEREST_RATE,SD_INTEREST_TYPE,SD_TENURE,SD_AMOUNT from  cr_sd_m where TXN_ID="+maxId+" and TXN_TYPE='LIM'";
		    	logger.info("fetchQ "+fetchQ);
		    	ArrayList fetchRecord=ConnectionDAO.sqlSelect(fetchQ);
		    	if(fetchRecord!=null && fetchRecord.size()>0 && st)
		    	{
		    		 double totalInt=0;
		    		 int freqMonth=0;
		    		 int reqFreq=0;
		    		 ArrayList dataSd=(ArrayList)fetchRecord.get(0);
		    		 String freq=CommonFunction.checkNull(dataSd.get(0)).trim();
		    		 String rate=CommonFunction.checkNull(dataSd.get(1)).trim();
		    		 String tenure=CommonFunction.checkNull(dataSd.get(3)).trim();
		    		 String sdAmount=CommonFunction.checkNull(dataSd.get(4)).trim();
		    		 logger.info("Fetch Record: SD_COMPOUNDING_FREQ: "+freq+" SD_INTEREST_RATE: "+rate+" SD_INTEREST_TYPE: "+dataSd.get(2)+" SD_TENURE: "+tenure+" SD_AMOUNT: "+!sdAmount.equalsIgnoreCase(""));
		    		 if(CommonFunction.checkNull(dataSd.get(2)).equalsIgnoreCase("S"))
		    		 {
		    			 logger.info("sdAmount: "+sdAmount);
		    			 if(!sdAmount.equalsIgnoreCase("") && !tenure.equalsIgnoreCase("") && !rate.equalsIgnoreCase(""))
		    			 totalInt=(Double.parseDouble(sdAmount)*Double.parseDouble(rate)*Double.parseDouble(tenure))/(12*100);
		    			 logger.info("totalInt: "+totalInt);
		    			 String QuerSimple="update cr_sd_m set SD_INTEREST='"+totalInt+"' where TXN_ID="+maxId+" and TXN_TYPE='LIM'";
		    	   		 logger.info("upQuerSimple: "+QuerSimple);
		    	   		 ArrayList al2=new ArrayList();
		    	   		 al2.add(QuerSimple);
		    	   		 boolean st1 = ConnectionDAO.sqlInsUpdDelete(al2);
		    	   		 logger.info("upQuerSimple status: "+st1);
		    		 }
		    		 if(CommonFunction.checkNull(dataSd.get(2)).equalsIgnoreCase("C"))
		    		 {
		    			 logger.info("In Coupound part: "+dataSd.get(2)); 
		    			 if(freq.equalsIgnoreCase("M"))
		    	    	 		freqMonth=1;
		    			 else if(freq.equalsIgnoreCase("B"))
		    			 		freqMonth=2;
		    			 else if(freq.equalsIgnoreCase("Q"))
		    					freqMonth=3;
		    			 else if(freq.equalsIgnoreCase("H"))
		    					freqMonth=6;
		    			 else if(freq.equalsIgnoreCase("Y"))
		    					freqMonth=12;
		    			 if(freqMonth!=0)
		    					reqFreq=(int)12/freqMonth;
		    			 if(!sdAmount.equalsIgnoreCase("") && !tenure.equalsIgnoreCase("") && !rate.equalsIgnoreCase(""))
		    					totalInt=(Double.parseDouble(sdAmount) * Math.pow(1+(Double.parseDouble(rate)/(reqFreq*100)),(Double.parseDouble(tenure)*reqFreq/12))) - Double.parseDouble(sdAmount);
		    			 logger.info("totalInt: "+totalInt);
		    			 String upQuerSimple="update cr_sd_m set SD_INTEREST='"+totalInt+"' where TXN_ID="+maxId+" and TXN_TYPE='LIM'";
		    		     logger.info("upQuerCo: "+upQuerSimple);
		    		     ArrayList al2=new ArrayList();
		    		     al2.add(upQuerSimple);
		    		     boolean st1 = ConnectionDAO.sqlInsUpdDelete(al2);
		    		     logger.info("upQuerCO status: "+st1);
		    		 }
		    	}
		    	//REFRESHING FLAG LOGIC
				String repayType=loanDetailForCMVO.getRepaymentType();
				logger.info("Repayment Type  :  "+repayType);
				RefreshFlagVo vo = new RefreshFlagVo();
				vo.setRecordId(maxId);
				vo.setModuleName("CM");
				vo.setTabIndex(1);
				vo.setNonInstallment(repayType);
				RefreshFlagValueInsert.updateRefreshFlag(vo);
			}
			logger.info("In updateListOfvalue......................"	+ status);
			if(status)
			{
				result.add("S");
				result.add(loanDetailForCMVO.getLoanId());
			}
			else
			{
				result.add("E");
				result.add("Some Error Occur,Please contact Administrator.");
			}
		} 
		else 
		{ 
			logger.info("In insert CreditManagementDAOImpl ");	
			
			String deleteQuery="DELETE from cr_loan_dtl_temp where MAKER_ID='"+CommonFunction.checkNull(loanDetailForCMVO.getUserId()).trim()+"' ";
			logger.info("updateListOfvalue() of LoanInitiationDAOImpl Delete Query :  "+deleteQuery); 
			ArrayList list=new ArrayList();			
			list.add(deleteQuery);
			status=ConnectionDAO.sqlInsUpdDelete(list);
			logger.info("updateListOfvalue() of LoanInitiationDAOImpl Delete Status :"+status);
			if(status)
			{			
				bufInsSql.append("insert into cr_loan_dtl_temp(FUNDING_TYPE,LOAN_DEAL_ID,LOAN_DEAL_LOAN_ID,LOAN_PRODUCT_CATEGORY,LOAN_PRODUCT,LOAN_SCHEME,LOAN_PROMO_SCHEME,LOAN_PRODUCT_TYPE,LOAN_BRANCH,LOAN_INDUSTRY_ID,LOAN_SUB_INDUSTRY_ID,LOAN_CUSTOMER_ID,LOAN_CUSTOMER_TYPE,LOAN_EXISTING_CUSTOMER,LOAN_INITIATION_DATE,LOAN_AGREEMENT_DATE,LOAN_REPAY_EFF_DATE,LOAN_ASSET_COST,LOAN_MARGIN_RATE, ");
				bufInsSql.append(" LOAN_MARGIN_AMOUNT,LOAN_LOAN_AMOUNT,LOAN_TENURE,LOAN_REPAYMENT_FREQ,LOAN_RATE_TYPE,LOAN_RATE_METHOD,LOAN_BASE_RATE_TYPE,LOAN_BASE_RATE,LOAN_MARKUP,LOAN_FINAL_RATE,LOAN_REPAYMENT_TYPE,LOAN_INSTALLMENT_TYPE,LOAN_INSTALLMENT_MODE,LOAN_REPAYMENT_MODE,LOAN_NO_OF_INSTALLMENT,LOAN_NUMBER_DISBURSAL,LOAN_ADVANCE_INSTL,LOAN_DUE_DAY,NEXT_DUE_DATE," +
						"LOAN_MATURITY_DATE,LOAN_SECTOR_TYPE,REC_STATUS,LOAN_MAKER_REMARKS,PO_DO_FLAG,LOAN_EFF_RATE,LOAN_IRR1,LOAN_IRR2,INSUARENCE_DONE_BY,LOAN_FLAT_RATE,MAKER_ID,AREA_CODE,REFRESH_FLAG,MAKER_DATE,LOAN_INT_CALC_FROM,TENURE_IN_DAY,LOAN_FLOATING_FIXED_PERIOD,NO_OF_ASSET,LOAN_REFERENCE_NO,vat_percent,vat_amount,service_tax,service_amount,loan_credit_period,INT_COMP_FREQ,INT_METHOD,INT_FREQ,INTEREST_DUE_DATE ,insurance_premium,DEAL_LOAN_AMOUNT_NEW,EDIT_DUE_DATE,FIRST_INTEREST_DUE_DATE,GROSS_BLOCK,NET_BLOCK )");// add by saorabh vat and service tax| Code added for Edit Due Date | Rahul papneja | 29012018
				bufInsSql.append(" values ( ");
				bufInsSql.append(" ?,");//FUNDING_TYPE
				bufInsSql.append(" ?,");//LOAN_DEAL_ID
				bufInsSql.append(" ?,");//LOAN_DEAL_LOAN_ID
				bufInsSql.append(" ?,");//LOAN_PRODUCT_CATEGORY
				bufInsSql.append(" ?,");//LOAN_PRODUCT
				bufInsSql.append(" ?,");//LOAN_SCHEME
				bufInsSql.append(" ?,");//LOAN_PROMO_SCHEME
				bufInsSql.append(" ?,");//LOAN_PRODUCT_TYPE
				bufInsSql.append(" ?,");//LOAN_BRANCH
				bufInsSql.append(" ?,");//LOAN_INDUSTRY_ID
				bufInsSql.append(" ?,");//LOAN_SUB_INDUSTRY_ID
				bufInsSql.append(" ?,");//LOAN_CUSTOMER_ID
				bufInsSql.append(" ?,");//LOAN_CUSTOMER_TYPE
				bufInsSql.append(" ?,");//LOAN_EXISTING_CUSTOMER
				bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"'),");//LOAN_INITIATION_DATE
				bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"'),");//LOAN_AGREEMENT_DATE  
				bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"'),");//LOAN_REPAY_EFF_DATE
				bufInsSql.append(" ?,");//LOAN_ASSET_COST
				bufInsSql.append(" ?,");//LOAN_MARGIN_RATE
				bufInsSql.append(" ?,");//LOAN_MARGIN_AMOUNT
				bufInsSql.append(" ?,");//LOAN_LOAN_AMOUNT
				bufInsSql.append(" ?,");//LOAN_TENURE
				bufInsSql.append(" ?,");//LOAN_REPAYMENT_FREQ
				bufInsSql.append(" ?,");//LOAN_RATE_TYPE
				bufInsSql.append(" ?,");//LOAN_RATE_METHOD
				bufInsSql.append(" ?,");//LOAN_BASE_RATE_TYPE
				bufInsSql.append(" ?,");//LOAN_BASE_RATE
				bufInsSql.append(" ?,");//LOAN_MARKUP
				bufInsSql.append(" ?,");//LOAN_FINAL_RATE
				bufInsSql.append(" ?,");//LOAN_REPAYMENT_TYPE
				bufInsSql.append(" ?,");//LOAN_INSTALLMENT_TYPE
				bufInsSql.append(" ?,");//LOAN_INSTALLMENT_MODE
				bufInsSql.append(" ?,");//LOAN_REPAYMENT_MODE
				bufInsSql.append(" ?,");//LOAN_NO_OF_INSTALLMENT
				bufInsSql.append(" ?,");//LOAN_NUMBER_DISBURSAL
				bufInsSql.append(" ?,");//LOAN_ADVANCE_INSTL
				bufInsSql.append(" ?,");//LOAN_DUE_DAY
				bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"'),");//NEXT_DUE_DATE
				bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"'),");//LOAN_MATURITY_DATE
				bufInsSql.append(" ?,");//LOAN_SECTOR_TYPE
	 			bufInsSql.append(" ?,");//REC_STATUS
	 			bufInsSql.append(" ?,");//LOAN_MAKER_REMARKS
	 			bufInsSql.append(" ?,");//PO_DO_FLAG 			
	 			bufInsSql.append(" ?,");//LOAN_EFF_RATE
	 			bufInsSql.append(" ?,");//LOAN_IRR1
	 			bufInsSql.append(" ?,");//LOAN_IRR2
	 			bufInsSql.append(" ?,");//INSUARENCE_DONE_BY
	 			bufInsSql.append(" ?,");//LOAN_FLAT_RATE
				bufInsSql.append(" ?,");//MAKER_ID
				bufInsSql.append(" ?,");//AREA_CODE
				bufInsSql.append("'NNNNNNNNNNNNNNN',");//REFRESH_FLAG					
				bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"'),");//MAKER_DATE
				bufInsSql.append(" ?,");//LOAN_INT_CALC_FROM
				bufInsSql.append(" ?,");//TENURE_IN_DAY 
				bufInsSql.append(" ?, ");//LOAN_FLOATING_FIXED_PERIOD 
				bufInsSql.append(" ?, ");//NO_OF_ASSET	
				bufInsSql.append(" ?, ");//LOAN_REFERENCE_NO	
				//add by saorabh
				bufInsSql.append(" ?,");//vatPercent 
				bufInsSql.append(" ?, ");//vatamount 
				bufInsSql.append(" ?, ");//serviceTax	
				bufInsSql.append(" ?, ");//serviceAmount	
				bufInsSql.append(" ?, ");//loan_credit_period	
				bufInsSql.append(" ?, " );//INT_COMP_FREQ
				bufInsSql.append(" ?, " );//INT_METHOD
				bufInsSql.append(" ?, " );//INT_FREQ
				bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"'), ");//INTEREST_DUE_DATE
				//end by saorabh
				bufInsSql.append(" ?, ");//insurance_premium				
				bufInsSql.append(" ?, ");//DEAL_LOAN_AMOUNT_NEW		
				bufInsSql.append(" ?, ");//EDIT_DUE_DATE | Rahul papneja |29012018
				bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"'),");//FIRST_INTEREST_DUE_DATE
				bufInsSql.append(" ?, ");//GROSS_BLOCK
				bufInsSql.append(" ? ");//NET_BLOCK
				bufInsSql.append(")");//END
				
				if (CommonFunction.checkNull(loanDetailForCMVO.getBusinessType()).equalsIgnoreCase(""))//FUNDING_TYPE
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getBusinessType()).trim()));
				
				if (CommonFunction.checkNull(loanDetailForCMVO.getLbxDealNo()).equalsIgnoreCase(""))//LOAN_DEAL_ID
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getLbxDealNo()).trim()));
				
				if (CommonFunction.checkNull(loanDetailForCMVO.getLoanDealId()).equalsIgnoreCase(""))//LOAN_DEAL_LOAN_ID
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getLoanDealId()).trim()));
				
				if (CommonFunction.checkNull(loanDetailForCMVO.getProductCat()).equalsIgnoreCase(""))//LOAN_PRODUCT_CATEGORY
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getProductCat()).trim()));
				
				if (CommonFunction.checkNull(loanDetailForCMVO.getProduct()).equalsIgnoreCase(""))//LOAN_PRODUCT
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getProduct()).trim()));
				
				if (CommonFunction.checkNull(loanDetailForCMVO.getScheme()).equalsIgnoreCase(""))//LOAN_SCHEME
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getScheme()).trim()));
				
				insertPrepStmtObject.addNull();  //LOAN_PROMO_SCHEME
			
				if(CommonFunction.checkNull(loanDetailForCMVO.getProductType()).equalsIgnoreCase(""))//LOAN_PRODUCT_TYPE
				     insertPrepStmtObject.addNull();
				 else
				   insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getProductType()).trim()));
				
				if (CommonFunction.checkNull(loanDetailForCMVO.getLoanBranch()).equalsIgnoreCase(""))//LOAN_BRANCH
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getLoanBranch()).trim()));
				if (CommonFunction.checkNull(loanDetailForCMVO.getLoanIndustry()).equalsIgnoreCase(""))//LOAN_INDUSTRY_ID
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getLoanIndustry()).trim()));
				if (CommonFunction.checkNull(loanDetailForCMVO.getLoanSubIndustry()).equalsIgnoreCase(""))//LOAN_Sub_INDUSTRY_ID
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getLoanSubIndustry()).trim()));
				if (CommonFunction.checkNull(loanDetailForCMVO.getLoanCustomerId()).equalsIgnoreCase(""))//LOAN_CUSTOMER_ID
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getLoanCustomerId()).trim()));
				
				if (CommonFunction.checkNull(loanDetailForCMVO.getLoanCustomerType()).equalsIgnoreCase(""))//LOAN_CUSTOMER_TYPE
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getLoanCustomerType()).trim()));
				
				if (CommonFunction.checkNull(loanDetailForCMVO.getLoanCustomerExisting()).equalsIgnoreCase(""))//LOAN_EXISTING_CUSTOMER
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getLoanCustomerExisting()).trim()));
	
				if (CommonFunction.checkNull(loanDetailForCMVO.getBussinessDate()).equalsIgnoreCase(""))//LOAN_INITIATION_DATE
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getBussinessDate()).trim()));
	
				if(CommonFunction.checkNull(loanDetailForCMVO.getAgreementDate()).equals(""))//LOAN_Agreement_Date
					insertPrepStmtObject.addNull();
			    else
			    	insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getAgreementDate()).trim()));
				
				if(CommonFunction.checkNull(loanDetailForCMVO.getRepayEffectiveDate()).equals(""))//LOAN_RepayEffective_Date
					insertPrepStmtObject.addNull();
			    else
			    	insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getRepayEffectiveDate()).trim()));
	
				if (CommonFunction.checkNull(loanDetailForCMVO.getAssetCost()).equalsIgnoreCase(""))//LOAN_ASSET_COST
					insertPrepStmtObject.addFloat(0.0000);
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(loanDetailForCMVO.getAssetCost()).trim())).toString());
	
//				if (CommonFunction.checkNull(loanDetailForCMVO.getMargin()).equalsIgnoreCase(""))
//					insertPrepStmtObject.addNull();
//				else
//					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(loanDetailForCMVO.getMargin()).trim())).toString());
				
	    		if(CommonFunction.checkNull(loanMarginRate).trim().equalsIgnoreCase("0"))//LOAN_MARGIN_RATE
		    		insertPrepStmtObject.addNull();
		    	else
		    		insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(loanMarginRate.toString()).toString());
	    		
				if (CommonFunction.checkNull(loanDetailForCMVO.getMarginAmount()).equalsIgnoreCase(""))//LOAN_MARGIN_AMOUNT
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(loanDetailForCMVO.getMarginAmount()).trim())).toString());
	
				if (CommonFunction.checkNull(loanDetailForCMVO.getLoanAmount()).equalsIgnoreCase(""))//LOAN_LOAN_AMOUNT
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(loanDetailForCMVO.getLoanAmount()).trim())).toString());
	
				if (CommonFunction.checkNull(loanDetailForCMVO.getTenureMonths()).equalsIgnoreCase(""))//LOAN_TENURE
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getTenureMonths()).trim()));
	
				if (CommonFunction.checkNull(loanDetailForCMVO.getFrequency()).equalsIgnoreCase(""))//LOAN_REPAYMENT_FREQ
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getFrequency()).trim()));
	
				if (CommonFunction.checkNull(loanDetailForCMVO.getRateType()).equalsIgnoreCase(""))//LOAN_RATE_TYPE
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getRateType()).trim()));
	
				if (CommonFunction.checkNull(loanDetailForCMVO.getDealRateMethod()).equalsIgnoreCase(""))//LOAN_RATE_METHOD
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getDealRateMethod()).trim()));
	
				if (CommonFunction.checkNull(loanDetailForCMVO.getBaseRateType()).equalsIgnoreCase(""))//LOAN_BASE_RATE_TYPE
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getBaseRateType()).trim()));
	
				if (CommonFunction.checkNull(loanDetailForCMVO.getBaseRate()).equalsIgnoreCase(""))//LOAN_BASE_RATE
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(loanDetailForCMVO.getBaseRate()).trim())).toString());
	
				if (CommonFunction.checkNull(loanDetailForCMVO.getMarkUp()).equalsIgnoreCase(""))//LOAN_MARKUP
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(loanDetailForCMVO.getMarkUp()).trim())).toString());
	
				
				if (CommonFunction.checkNull(loanDetailForCMVO.getEffectiveRate()).equalsIgnoreCase(""))//LOAN_FINAL_RATE 
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(loanDetailForCMVO.getEffectiveRate()).trim())).toString());
	
				if (CommonFunction.checkNull(loanDetailForCMVO.getRepaymentType()).equalsIgnoreCase(""))//LOAN_REPAYMENT_TYPE
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getRepaymentType()).trim()));
	
				if (CommonFunction.checkNull(loanDetailForCMVO.getInstallmentType()).equalsIgnoreCase(""))//LOAN_Installment_TYPE
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getInstallmentType()).trim()));
				
				if (CommonFunction.checkNull(loanDetailForCMVO.getDealInstallmentMode()).equalsIgnoreCase(""))//LOAN_INSTALLMENT_MODE
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getDealInstallmentMode()).trim()));
	
				if (CommonFunction.checkNull(loanDetailForCMVO.getRepaymentMode()).equalsIgnoreCase(""))//LOAN_Repayment_MODE
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getRepaymentMode()).trim()));
				
				
				if (CommonFunction.checkNull(loanDetailForCMVO.getLoanNoofInstall()).equalsIgnoreCase(""))//LOAN_NO_OF_INSTALLMENT
					insertPrepStmtObject.addNull();//No of installment
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getLoanNoofInstall()).trim()));
	
				if (CommonFunction.checkNull(loanDetailForCMVO.getNoOfDisbursal()).equalsIgnoreCase(""))//LOAN_NO_Disbursal
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getNoOfDisbursal()).trim()));
	
				if (CommonFunction.checkNull(loanDetailForCMVO.getInstallments()).equalsIgnoreCase(""))//LOAN_ADVANCE_INSTL
					insertPrepStmtObject.addString("0");
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getInstallments()).trim()));
				
				if (CommonFunction.checkNull(loanDetailForCMVO.getDueDay()).equalsIgnoreCase(""))//LOAN_DueDate
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getDueDay()).trim()));
				
				if ((CommonFunction.checkNull(nextdueDate).trim()).equalsIgnoreCase(""))//LOAN_NEXT_Date
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(CommonFunction.checkNull(nextdueDate).trim());
				
		
				if (CommonFunction.checkNull(loanDetailForCMVO.getMaturityDate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getMaturityDate()).trim()));//loanIntiatindate + tenure
					
				if (CommonFunction.checkNull(loanDetailForCMVO.getSectorType()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getSectorType()).trim()));//sector Type
				
				insertPrepStmtObject.addString("P");//REC_STATUS
	
				if (CommonFunction.checkNull(loanDetailForCMVO.getRemarks()).equalsIgnoreCase(""))//LOAN_MAKER_REMARKS
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getRemarks()).trim()));
				
				if (CommonFunction.checkNull(loanDetailForCMVO.getPodoFlag()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getPodoFlag()).trim()));
				
				
				if (CommonFunction.checkNull(loanDetailForCMVO.getDealEffectiveRate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getDealEffectiveRate()).trim()));
				
				
				if (CommonFunction.checkNull(loanDetailForCMVO.getDealIrr1()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getDealIrr1()).trim()));
				
				if (CommonFunction.checkNull(loanDetailForCMVO.getDealIrr2()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getDealIrr2()).trim()));
				
				if (CommonFunction.checkNull(loanDetailForCMVO.getInsuranceDoneBy()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getInsuranceDoneBy()).trim()));
				
				if (CommonFunction.checkNull(loanDetailForCMVO.getDealFlatRate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getDealFlatRate()).trim()));
				
				if (loanDetailForCMVO.getUserId().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getUserId()).trim()));
				if (loanDetailForCMVO.getLbxareaCodeVal().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getLbxareaCodeVal()).trim()));
	
				if (loanDetailForCMVO.getBussinessDate().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getBussinessDate()).trim()));
				if (loanDetailForCMVO.getInterestCalc().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getInterestCalc()).trim()));
				
				if (CommonFunction.checkNull(loanDetailForCMVO.getTenureInDay()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getTenureInDay()).trim()));
				
				if (CommonFunction.checkNull(loanDetailForCMVO.getFixPriod()).equalsIgnoreCase(""))
					insertPrepStmtObject.addInt(0);
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getFixPriod()).trim()));
				
				if (CommonFunction.checkNull(loanDetailForCMVO.getNoOfAsset()).equalsIgnoreCase(""))
					insertPrepStmtObject.addInt(0);
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getNoOfAsset()).trim()));//NO_OF_ASSET
				
				if (CommonFunction.checkNull(loanDetailForCMVO.getFormNo()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getFormNo()).trim()));//LOAN_REFERENCE_NO
				//add by saorabh
				if (CommonFunction.checkNull(loanDetailForCMVO.getVatPercent()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getVatPercent()).trim()));//vatPercent
				if (CommonFunction.checkNull(loanDetailForCMVO.getTotalVatAmt()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getTotalVatAmt()).trim()));//vatamount 
				if (CommonFunction.checkNull(loanDetailForCMVO.getServiceTax()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getServiceTax()).trim()));//servicetax 
				if (CommonFunction.checkNull(loanDetailForCMVO.getServiceAmount()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getServiceAmount()).trim()));//serviceamount
				//end by saorabh
				//added by Richa
				if (CommonFunction.checkNull(loanDetailForCMVO.getCreditPeriod()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getCreditPeriod()).trim()));//credit period
				//ended by Richa
				
				if (CommonFunction.checkNull((loanDetailForCMVO.getInterestCompoundingFrequency())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((loanDetailForCMVO.getInterestCompoundingFrequency()).trim());	
				
				if (CommonFunction.checkNull((loanDetailForCMVO.getInterestCalculationMethod())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((loanDetailForCMVO.getInterestCalculationMethod()).trim());	
				
				if (CommonFunction.checkNull((loanDetailForCMVO.getInterestFrequency())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((loanDetailForCMVO.getInterestFrequency()).trim());	
				
				if (CommonFunction.checkNull((loanDetailForCMVO.getInterestDueDate())).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((loanDetailForCMVO.getInterestDueDate()).trim());
				
				if (CommonFunction.checkNull(loanDetailForCMVO.getInsurancePremium()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getInsurancePremium()).trim()));//Insurance premium
				
				
				if (CommonFunction.checkNull(loanDetailForCMVO.getRequestedLoamt()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(loanDetailForCMVO.getRequestedLoamt()).trim())).toString()); //DEAL_LOAN_AMOUNT_NEW
			
				// Code added for Edit Due Date| Rahul papneja| 29012018
				if (CommonFunction.checkNull(loanDetailForCMVO.getEditDueDate()).equalsIgnoreCase(""))//Edit_Due_Date
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getEditDueDate()).trim()));
				
				// Ends Here| Rahul papneja
				
				
				
				
				// Code added for First Interest Due Date| Rahul papneja| 02022018
				if (CommonFunction.checkNull(loanDetailForCMVO.getFirstInterestDueDate()).equalsIgnoreCase(""))//Edit_Due_Date
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getFirstInterestDueDate()).trim()));
				
				if (CommonFunction.checkNull(loanDetailForCMVO.getGrossBlock()).equalsIgnoreCase(""))//Edit_Due_Date
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getGrossBlock()).trim()));
				
				if (CommonFunction.checkNull(loanDetailForCMVO.getNetBlock()).equalsIgnoreCase(""))//Edit_Due_Date
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getNetBlock()).trim()));
				
				/*if(CommonFunction.checkNull(loanDetailForCMVO.getRepayEffectiveDate()).equals(""))//LOAN_RepayEffective_Date
					insertPrepStmtObject.addNull();
			    else
			    	insertPrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getRepayEffectiveDate()).trim()));
				*/
				
				// Ends Here| Rahul papneja
				insertPrepStmtObject.setSql(bufInsSql.toString());
	
				logger.info("IN updateListOfvalue() insert query1 ### "+ insertPrepStmtObject.printQuery());
				qryList.add(insertPrepStmtObject);
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				
				logger.info("In updateListOfvalue......................"+ status);
				if (status) 
				{
					String loanId=null;
					ArrayList<Object> in =new ArrayList<Object>();
					ArrayList<Object> out =new ArrayList<Object>();
					ArrayList outMessages = new ArrayList();
					String s1=null;
					String s2=null;
					in.add(loanDetailForCMVO.getLbxDealNo());
					in.add(loanDetailForCMVO.getRepaymentType());
					in.add(loanDetailForCMVO.getUserId());
					String makerDate=CommonFunction.checkNull(loanDetailForCMVO.getBussinessDate()).trim();
					makerDate=CommonFunction.changeFormat(makerDate);
					in.add(makerDate);
					out.add(loanId);
					out.add(s1);
					out.add(s2);  									
					logger.info("LOAN_DEATAIL_INSERT ("+in.toString()+","+out.toString());
					outMessages=(ArrayList) ConnectionDAO.callSP("LOAN_DEATAIL_INSERT",in,out);
					loanId=CommonFunction.checkNull(outMessages.get(0));
					s1=CommonFunction.checkNull(outMessages.get(1));
					s2=CommonFunction.checkNull(outMessages.get(2));
					logger.info("LOAN_DEATAIL_INSERT  s1  : "+s1);
					logger.info("LOAN_DEATAIL_INSERT  s2  : "+s2);
					result.add(CommonFunction.checkNull(s1).trim());
					if(CommonFunction.checkNull(s1).trim().equalsIgnoreCase("S"))
						result.add(CommonFunction.checkNull(loanId).trim());
					else
						result.add(CommonFunction.checkNull(s2).trim());
						
					in.clear();
					in=null;
					out.clear();
					out=null;
					outMessages.clear();
					outMessages=null;
					s1=null;
					s2=null;
					loanId=null;
//					boolean assetStatus=false;
//					boolean chargeStatus=false;
//					boolean sdStatus=false;
//					boolean instPlanStatus=false;
//					boolean rePayStatus=false;
//					boolean documentStatus=false;
//					boolean disbursalStatus=false;
//					LoanInitiationDAO dao = new LoanInitiationDAOImpl();
//					logger.info("loanDetailForCMVO.getUserId()(MAKER_ID):::::::: "+loanDetailForCMVO.getUserId());
//					query3.append("Select max(LOAN_ID) from cr_loan_dtl where MAKER_ID='"+CommonFunction.checkNull(loanDetailForCMVO.getUserId())+"'");
//					id.append(ConnectionDAO.singleReturn(query3.toString()));
//					maxId = Integer.parseInt(id.toString());
//					logger.info("maxId : " + maxId);
//					loanDetailForCMVO.setLoanId(""+maxId);
//					assetStatus=dao.saveAssetCollateralFrmLoanInit(loanDetailForCMVO);// Refresh the AssetCollateral
//					if(assetStatus)
//					{
//						ArrayList list=new ArrayList();
//						PrepStmtObject stmt = new PrepStmtObject();
//						String upQuery="update cr_asset_collateral_m join cr_loan_collateral_m on ASSETID=ASSET_ID set PROPERTY_OWNER_GCD_ID=PROPERTY_OWNER where loan_id="+maxId;
//						stmt.setSql(upQuery);
//						logger.info("Copy data for loan  :   "+ stmt);
//						list.add(stmt);
//						assetStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(list);
//					}
//					int c=0;
//					String chReq="SELECT COUNT(1) FROM CR_DEAL_TXNCHARGES_DTL WHERE DEAL_CHARGE_APPLICATION_STAGE <> 'DC' AND DEAL_ID =(select loan_deal_id from cr_loan_dtl where loan_id="+maxId+")";				
//					c=Integer.parseInt(ConnectionDAO.singleReturn(chReq));
//					if(c==0)
//						chargeStatus=true;
//					else
//					{
//						ArrayList charges = dao.refreshchargesDetailInCM("LIM",""+maxId);// Refresh the charges	
//						if(charges.size()>0)
//							chargeStatus=true;
//						else
//							chargeStatus=false;
//					}
//					int sc=0;
//					String securityQuery="select count(1) from cr_deal_sd_m where deal_id=(select LOAN_DEAL_ID from cr_loan_dtl where loan_id='"+maxId+"')";
//					logger.info("Security check query  :  "+securityQuery);
//					sc=Integer.parseInt(ConnectionDAO.singleReturn(securityQuery));
//					if(sc==0)
//						sdStatus=true;
//					else
//					{
//						ArrayList tenureAmount=dao.getTenureAmount(""+maxId);
//						logger.info("Size of tenureAmount: "+tenureAmount.size());			
//						if(tenureAmount!=null && tenureAmount.size()!=0)
//							sdStatus = CommonFunction.insertsecurity(""+maxId);// Refresh the Security Deposit	
//						else
//							sdStatus=true;
//					}
//					String repymeType=loanDetailForCMVO.getRepaymentType();	
//					logger.info("Loan Repayment Type  :  "+repymeType);
//			    	if(CommonFunction.checkNull(repymeType).trim().equalsIgnoreCase("I"))
//			    	{
//			    		instPlanStatus=CommonFunction.insertInstallment(maxId);// Refresh the Installment Plan
//			    		DisbursalInitiationDAO detail=(DisbursalInitiationDAO)DaoImplInstanceFactory.getDaoImplInstance(DisbursalInitiationDAO.IDENTITY);
//			    		logger.info("Implementation class: "+detail.getClass()); 
//			    	   //	DisbursalInitiationDAO detail = new DisbursalInitiationDAOImpl();		
//			    	   	String resultproc=detail.generateRepySchedule(""+maxId,loanDetailForCMVO.getUserId());// Refresh the Repayment Schedule	
//			    	   	if(CommonFunction.checkNull(resultproc).trim().equalsIgnoreCase("S"))
//			    	   		rePayStatus=true;
//			    	   	else
//			    	   		rePayStatus=false;
//			    	}
//			    	else
//			    	{
//			    		instPlanStatus=true;
//			    		rePayStatus=true;
//			    		disbursalStatus=true;
//			    	}
//			    	CreditProcessingDAO service=(CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance(CreditProcessingDAO.IDENTITY);
//					logger.info("Implementation class: "+dao.getClass()); 
//			       	//CreditProcessingDAO service = new CreditProcessingDAOImpl();				
//					String status1=service.collectDocuments("LIM",""+maxId,"PRD","NE");	
//					if(CommonFunction.checkNull(status1).trim().equalsIgnoreCase("S"))
//						documentStatus=true;
//	    	   		else
//	    	   			documentStatus=false;
//					
//					
//					String AC="Y",CH="Y",SD="Y",IP="Y",RP="Y",DC="Y",DB="Y";				
//					if(assetStatus)
//						AC="N";
//					if(chargeStatus)
//						CH="N";
//					if(sdStatus)
//						SD="N";
//					if(instPlanStatus)
//						IP="N";
//					if(rePayStatus)
//						RP="N";
//					if(documentStatus)
//						DC="N";
//					if(disbursalStatus)
//						DB="N";
//					
//					String refresFlag="N"+AC+CH+SD+IP+RP+DC+DB+"NNNNNNN";
//					logger.info("REFRESHIN FLAG  :  " + refresFlag);
//					ArrayList list = new ArrayList();
//					String loanUpdateQuery="update cr_loan_dtl set REFRESH_FLAG='"+refresFlag+"' where loan_id="+maxId;
//					logger.info("loanUpdateQuery Query  :  " + loanUpdateQuery);
//					list.add(loanUpdateQuery);
//					boolean restatus = ConnectionDAO.sqlInsUpdDelete(list);
//					logger.info("REFRESHIN FLAG  update Status   :  " + restatus);
//					CorporateDAO crdao=(CorporateDAO)DaoImplInstanceFactory.getDaoImplInstance(CorporateDAO.IDENTITY); 
//					crdao.saveCustomerFromDeal(""+maxId,"NE"); 
		 			 			
				}
				else
				{
					result.add("E");
					result.add("Some Error Occur,Please contact Administrator.");
				}
			}
			else
			{
				result.add("E");
				result.add("Some Error Occur,Please contact Administrator.");
			}
		}
	}
    catch (Exception e) 
    {	
    	result.add("E");
		result.add("Some Error Occur,Please contact Administrator.");
    	e.printStackTrace();
    }
	finally
	{
		qryList = null;
		insertPrepStmtObject = null;
		q = null;
		deleteArl = null;
		instalQuery = null;
		instalStatus = null;
		deleteInst= null;
		qryListUp= null;
		updatePrepStmtObject = null;
		queryUpdate = null;
		bufInsSql = null;
		query3 = null;
		id = null;
	}
	return result;
}

		public boolean saveAssetCollateralFrmLoanInit(LoanDetailForCMVO loanDetailForCMVO){
			boolean status = false;
			ArrayList queryList = new ArrayList();
			StringBuilder query = new StringBuilder();
			try {

				query.append("Insert into cr_loan_collateral_m (LOAN_ID,ASSETID,REC_STATUS,MAKER_ID,MAKER_DATE) ");
				query.append(" Select '"+loanDetailForCMVO.getLoanId()+"', ASSETID,'A','"+loanDetailForCMVO.getUserId()+"', ");
				query.append(" str_to_date('"+loanDetailForCMVO.getBussinessDate()+"','"+dateFormatWithTime+"') ");
				query.append(" from cr_deal_collateral_m where DEAL_ID='"+loanDetailForCMVO.getLbxDealNo()+"' AND REC_STATUS='A'");

				queryList.add(query.toString());
				logger.info("In saveAssetCollateralFrmLoanInit:-" + query.toString());
				status = ConnectionDAO.sqlInsUpdDelete(queryList);
				logger.info("In saveAssetCollateralFrmLoanInit,,,,," + status);

			} catch (Exception e) {
				e.printStackTrace();
				e=null;
			}
			finally
			{
				queryList.clear();
				queryList = null;
				query = null;
				loanDetailForCMVO=null;
			}
			return status;
		}

		

		public String checkStageInLoanInit(String loanId)
		  {
		    String status = "F";
		    StringBuilder tenure = new StringBuilder();
		    StringBuilder sdAmount = new StringBuilder();
		    StringBuilder utilAmount = new StringBuilder();
		    StringBuilder q1 = new StringBuilder();
		    StringBuilder q2 = new StringBuilder();
		    StringBuilder q3 = new StringBuilder();
		    StringBuilder q4 = new StringBuilder();
		    StringBuilder q5 = new StringBuilder();
		    StringBuilder q9 = new StringBuilder();
		    StringBuilder q10 = new StringBuilder();
		    StringBuilder q11 = new StringBuilder();
		    StringBuilder q12 = new StringBuilder();
		    StringBuilder q13 = new StringBuilder();
		    StringBuilder checkUtilizedAm = new StringBuilder();
		    StringBuilder q6 = new StringBuilder();
		    StringBuilder q7 = new StringBuilder();
		    StringBuilder q8 = new StringBuilder();
		    StringBuilder q14 = new StringBuilder();
		    StringBuilder q15 = new StringBuilder();
		    ArrayList docs = null;
		    ArrayList docs1 = null;
		    ArrayList docs2 = null;
		    try
		    {
		      q1.append(new StringBuilder().append("select COUNT(1) from cr_loan_collateral_m where LOAN_ID=").append(StringEscapeUtils.escapeSql(loanId).trim()).append("").toString());
		      q2.append(new StringBuilder().append("select STRCMP(d.SD_AMOUNT,t.DEAL_CHARGE_FINAL_AMOUNT) from cr_sd_m d,cr_txncharges_dtl t where d.TXN_TYPE='LIM' and d.TXN_TYPE=t.TXN_TYPE and d.TXN_ID=").append(StringEscapeUtils.escapeSql(loanId).trim()).append(" and d.TXN_ID=t.TXN_ID and DEAL_CHARGE_CODE=103").toString());
		      q3.append(new StringBuilder().append("select count(1) from cr_txncharges_dtl where TXN_TYPE='LIM' AND  TXN_ID=").append(StringEscapeUtils.escapeSql(loanId).trim()).append("").toString());
		      q4.append(new StringBuilder().append("select DOC_STATUS from cr_document_dtl where STAGE_ID='PRD' and DOC_MANDATORY='Y' AND TXN_TYPE='LIM' and ENTITY_ID=").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).append(" AND TXNID=").append(StringEscapeUtils.escapeSql(loanId).trim()).append("").toString());
		      q5.append(new StringBuilder().append("select STRCMP(L.LOAN_LOAN_AMOUNT,(SELECT SUM(D.PROPOSED_DISBURSAL_AMOUNT) FROM cr_loan_disbursalsch_dtl D,cr_loan_dtl L WHERE L.LOAN_ID= D.LOAN_ID AND  L.LOAN_ID=").append(StringEscapeUtils.escapeSql(loanId).trim()).append("))").toString());
		      q5.append(new StringBuilder().append(" from cr_loan_dtl L where L.LOAN_ID=").append(StringEscapeUtils.escapeSql(loanId).trim()).append("").toString());
		      q6.append(new StringBuilder().append("select COUNT(1) from cr_document_dtl where  TXN_TYPE='LIM' AND TXNID=").append(StringEscapeUtils.escapeSql(loanId).trim()).append("").toString());
		      q9.append(new StringBuilder().append("select DOC_STATUS from cr_document_dtl where TXNID=").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).append(" and ENTITY_ID in (select GCD_ID from cr_deal_customer_role where DEAL_ID in ( select loan_deal_id from cr_loan_dtl where loan_id=").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).append(")) and DOC_MANDATORY='Y' and STAGE_ID='PRD' AND TXN_TYPE='LIM'").toString());
		      q10.append(new StringBuilder().append("select DOC_STATUS from cr_document_dtl where TXNID=").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).append(" and ENTITY_ID in (select ASSETID from cr_loan_collateral_m where loan_ID=").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).append(")  and DOC_MANDATORY='Y' and STAGE_ID='PRD' AND TXN_TYPE='LIM'").toString());
		      q11.append(new StringBuilder().append("select LOAN_REPAYMENT_TYPE from cr_loan_dtl where LOAN_ID=").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).append("").toString());
		      q12.append("select s.SD_TENURE from cr_sd_m s,cr_loan_dtl d where d.LOAN_ID=s.TXN_ID and s.TXN_TYPE='LIM' and s.SD_TENURE<=d.LOAN_TENURE");
		      q12.append(new StringBuilder().append(" and d.LOAN_ID=").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).append("").toString());
		      q13.append(new StringBuilder().append("SELECT SD_AMOUNT FROM cr_sd_m WHERE TXN_TYPE='LIM' and TXN_ID=").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).append("").toString());
		      checkUtilizedAm.append("select L.LOAN_ID from cr_loan_dtl L,cr_deal_loan_dtl DL where L.LOAN_DEAL_LOAN_ID=DL.DEAL_LOAN_ID AND L.LOAN_DEAL_ID=DL.DEAL_ID ");
		      checkUtilizedAm.append(new StringBuilder().append(" AND L.LOAN_ID=").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).append(" AND DL.DEAL_SANCTION_AMOUNT>ifnull(DL.DEAL_UTILIZED_AMOUNT, 0)").toString());
		      String checkDeviationQuery = new StringBuilder().append("SELECT count( DISTINCT MANUAL_DEVIATION_ID) FROM cr_manual_deviation_m M  INNER JOIN CR_LOAN_DTL L ON M.PRODUCT_ID=L.LOAN_PRODUCT AND M.SCHEME_ID=L.LOAN_SCHEME  WHERE M.REC_STATUS='A' AND STAGE_ID='LIM' AND L.LOAN_ID='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).append("'  AND MANUAL_DEVIATION_ID NOT IN(SELECT MANUAL_DEVIATION_ID FROM cr_loan_deviation_dtl WHERE LOAN_ID='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).append("') ").toString();

		      logger.info(new StringBuilder().append("AssetCollateral  : ").append(q1.toString()).toString());
		      int asset = Integer.parseInt(ConnectionDAO.singleReturn(q1.toString()));
		      String str1;
		      if (asset == 0)
		      {
		        String prodFlagQ = new StringBuilder().append("select count(*) from cr_loan_dtl l,cr_product_m p where l.LOAN_PRODUCT=p.PRODUCT_ID and p.ASSET_FLAG='N' and  l.LOAN_ID='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).append("'").toString();
		        String prodFlag = ConnectionDAO.singleReturn(prodFlagQ);
		        logger.info(new StringBuilder().append("prodFlagQ: ").append(prodFlagQ).append("status ").append(prodFlag).toString());
		        if (CommonFunction.checkNull(prodFlag).equalsIgnoreCase("0"))
		        {
		          status = "AC";
		          return status;
		        }
		      }
		      String repayType = "N";
		      logger.info(new StringBuilder().append("Repay Type Query: ").append(q11.toString()).toString());
		      repayType = ConnectionDAO.singleReturn(q11.toString());
		      logger.info(new StringBuilder().append(" Repay Type Data: ").append(repayType).toString());
		      logger.info(new StringBuilder().append("SD Amount check query  : ").append(q2.toString()).toString());
		      String sd = ConnectionDAO.singleReturn(q2.toString());
		      if ((!CommonFunction.checkNull(sd).equalsIgnoreCase("0")) && (repayType != null) && (repayType.trim().equalsIgnoreCase("Y")))
		      {
		        status = "SD";
		        return status;
		      }
		      int c1 = 0;
		      int c2 = 0;
		      q7.append(new StringBuilder().append("select count(*) from cr_loan_dtl l,com_charges_m p where l.LOAN_PRODUCT=p.PRODUCT_ID and l.loan_scheme=p.scheme_id and DEF_STAGE_ID='LIM'  and p.rec_status='A' and  l.LOAN_ID='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).append("'").toString());
		      logger.info(new StringBuilder().append(" Query To find charges defined on product scheme combination or not : ").append(q7.toString()).toString());
		      c1 = Integer.parseInt(ConnectionDAO.singleReturn(q7.toString()));
		      if (c1 == 0)
		      {
		        q8.append(new StringBuilder().append("select count(*) from cr_loan_dtl l,com_charges_m p where l.LOAN_PRODUCT=p.PRODUCT_ID  and p.rec_status='A' AND DEF_STAGE_ID='LIM' and  l.LOAN_ID='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).append("'").toString());
		        logger.info(new StringBuilder().append(" Query To find charges defined on product  : ").append(q8.toString()).toString());
		        c2 = Integer.parseInt(ConnectionDAO.singleReturn(q8.toString()));
		      }
		      if ((c1 != 0) || (c2 != 0))
		      {
		        logger.info(new StringBuilder().append("Charge query : ").append(q3.toString()).toString());
		        int charge = Integer.parseInt(ConnectionDAO.singleReturn(q3.toString()));
		        if (charge == 0)
		        {
		          status = "CH";
		          return status;
		        }

		      }

		      logger.info(new StringBuilder().append("Application Document query : ").append(q4.toString()).toString());
		      docs = ConnectionDAO.sqlSelect(q4.toString());
		      ArrayList dataList;
		      if ((docs != null) || (CommonFunction.checkNull(docs).equals("")))
		      {
		        int size = docs.size();
		        for (int j = 0; j < size; j++)
		        {
		          dataList = (ArrayList)docs.get(j);
		          if (dataList.size() > 0)
		          {
		            String docMode = dataList.get(0).toString();
		            if ((docMode.equalsIgnoreCase("P")) || (docMode.equalsIgnoreCase("W")))
		            {
		              logger.info("In if in for");
		              status = "DC";
		              return status;
		            }
		          }
		        }
		      }
		      logger.info(new StringBuilder().append("Disbursal loan amount  :").append(q5.toString()).toString());
		      String loanDisbAmount = ConnectionDAO.singleReturn(q5.toString());
		      if ((!CommonFunction.checkNull(loanDisbAmount).equalsIgnoreCase("0")) && (repayType != null) && (repayType.trim().equalsIgnoreCase("Y")))
		      {
		        logger.info(new StringBuilder().append("loan disb: ").append(q5.toString()).toString());
		        status = "LD";
		        return status;
		      }
		      logger.info(new StringBuilder().append("Customer Document Query: ").append(q9.toString()).toString());
		      docs1 = ConnectionDAO.sqlSelect(q9.toString());
		      String str4;
		      if ((docs1 != null) || (CommonFunction.checkNull(docs1).equals("")))
		      {
		        int size = docs1.size();
		        for (int j = 0; j < size; j++)
		        {
		        	 dataList = (ArrayList)docs1.get(j);
		          if (dataList.size() > 0)
		          {
		            String docMode = dataList.get(0).toString();
		            if ((docMode.equalsIgnoreCase("P")) || (docMode.equalsIgnoreCase("W")))
		            {
		              status = "DC";
		              return status;
		            }
		          }
		        }
		      }
		      logger.info(new StringBuilder().append("Asset Document Query: ").append(q10.toString()).toString());
		      docs2 = ConnectionDAO.sqlSelect(q10.toString());
		      String docMode;
		      if ((docs2 != null) || (CommonFunction.checkNull(docs2).equals("")))
		      {
		        int size = docs2.size();
		        for (int j = 0; j < size; j++)
		        {
		        	 dataList = (ArrayList)docs2.get(j);
		          if (dataList.size() > 0)
		          {
		            docMode = dataList.get(0).toString();
		            if ((docMode.equalsIgnoreCase("P")) || (docMode.equalsIgnoreCase("W")))
		            {
		              status = "DC";
		              return status;
		            }
		          }
		        }
		      }

		      logger.info(new StringBuilder().append("SD Amount : ").append(q13.toString()).toString());
		      sdAmount.append(ConnectionDAO.singleReturn(q13.toString()));

		      if ((!CommonFunction.checkNull(sdAmount).equalsIgnoreCase("")) && (!CommonFunction.checkNull(sdAmount).equalsIgnoreCase("0.0000")))
		      {
		        logger.info(new StringBuilder().append("Tenure Query  : ").append(q12.toString()).toString());
		        tenure.append(ConnectionDAO.singleReturn(q12.toString()));
		        if (CommonFunction.checkNull(tenure).equalsIgnoreCase(""))
		        {
		          status = "TE";
		          return status;
		        }
		      }
		      logger.info(new StringBuilder().append("Check checkUtilizedAm query  : ").append(checkUtilizedAm.toString()).toString());
		      utilAmount.append(ConnectionDAO.singleReturn(checkUtilizedAm.toString()));
		      if (CommonFunction.checkNull(utilAmount).equalsIgnoreCase(""))
		      {
		        status = "UT";
		        return status;
		      }
		      String marginQuery = new StringBuilder().append("select (ifnull(LOAN_ASSET_COST,0)-ifnull(LOAN_LOAN_AMOUNT,0)) as 'Margin Amount' from cr_loan_dtl where LOAN_ID='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).append("'").toString();
		      logger.info(new StringBuilder().append("marginQuery: ").append(marginQuery).toString());
		      String marginAmount = ConnectionDAO.singleReturn(marginQuery);
		      logger.info(new StringBuilder().append("marginAmount: ").append(marginAmount).toString());
		      double marAmt = 0.0D;
		      if (!CommonFunction.checkNull(marginAmount).equalsIgnoreCase(""))
		      {
		        marAmt = Double.parseDouble(marginAmount);
		      }
		      String query101 = new StringBuilder().append("select  c.DEAL_CHARGE_FINAL_AMOUNT from cr_txncharges_dtl c where TXN_TYPE='LIM' AND DEAL_CHARGE_CODE=101 and TXN_ID='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).append("'").toString();
		      logger.info(new StringBuilder().append("query101: ").append(query101).toString());
		      String query101Amount = ConnectionDAO.singleReturn(query101);
		      double query101Amt = 0.0D;
		      if (!CommonFunction.checkNull(query101Amount).equalsIgnoreCase(""))
		      {
		        query101Amt = Double.parseDouble(query101Amount);
		      }
		      logger.info(new StringBuilder().append("query101Amount: ").append(query101Amount).toString());
		      String query102 = new StringBuilder().append("select  c.DEAL_CHARGE_FINAL_AMOUNT from cr_txncharges_dtl c where  TXN_TYPE='LIM' AND DEAL_CHARGE_CODE=102 and TXN_ID='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).append("'").toString();
		      logger.info(new StringBuilder().append("query102: ").append(query102).toString());
		      String query102Amount = ConnectionDAO.singleReturn(query102);
		      double query102Amt = 0.0D;
		      if (!CommonFunction.checkNull(query102Amount).equalsIgnoreCase(""))
		      {
		        query102Amt = Double.parseDouble(query102Amount);
		      }
		      logger.info(new StringBuilder().append("query102Amount: ").append(query102Amount).toString());
		      logger.info(new StringBuilder().append("checkDeviationQuery: ").append(checkDeviationQuery).toString());
		      String checkDeviation = ConnectionDAO.singleReturn(checkDeviationQuery);
		      logger.info(new StringBuilder().append("checkDeviation: ").append(checkDeviation).toString());
		      if (!CommonFunction.checkNull(checkDeviation).equalsIgnoreCase("0"))
		      {
		        status = "UPDATEDEVIATION";
		        return status;
		      }
		      StringBuilder q71 = new StringBuilder();
		      StringBuilder q81 = new StringBuilder();
		      StringBuilder q131 = new StringBuilder();
		      StringBuilder q151 = new StringBuilder();
		      StringBuilder commAddr = new StringBuilder();
		      StringBuilder gAddr = new StringBuilder();
		      ArrayList gCheckAddr = new ArrayList();
		      StringBuilder coAddr = new StringBuilder();
		      ArrayList coCheckAddr = new ArrayList();
		      StringBuilder guram = new StringBuilder();
		      q71.append(new StringBuilder().append("select count(*) FROM com_address_m where BPID=(SELECT LOAN_CUSTOMER_ID FROM cr_loan_dtl WHERE LOAN_ID=").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).append(") and BPTYPE='CS' and COMMUNICATION_ADDRESS='Y'").toString());
		      logger.info(new StringBuilder().append("Applicant Communication Address check query : ").append(q71.toString()).toString());
		      commAddr.append(ConnectionDAO.singleReturn(q71.toString()));
		      if (!commAddr.toString().equalsIgnoreCase("1"))
		      {
		        status = "CA";
		        return status;
		      }
		      q131.append(new StringBuilder().append("SELECT GCD_ID FROM cr_loan_customer_role where LOAN_CUSTOMER_ROLE_TYPE='GUARANTOR' AND LOAN_ID=").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).toString());
		      logger.info(new StringBuilder().append("Gurantor ID List check query : ").append(q131.toString()).toString());
		      gCheckAddr = ConnectionDAO.sqlSelect(q131.toString());
		      String str7;
		      if ((!CommonFunction.checkNull(gCheckAddr).equalsIgnoreCase("")) && (gCheckAddr.size() > 0))
		      {
		        int ff = gCheckAddr.size();
		        for (int j = 0; j < ff; j++)
		        {
		          ArrayList data = (ArrayList)gCheckAddr.get(j);
		          int flag = data.size();

		          if (flag > 0)
		          {
		            StringBuilder q141 = new StringBuilder();
		            gAddr = new StringBuilder();
		            q141.append(new StringBuilder().append("select count(*) FROM com_address_m where BPID='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(data.get(0))).trim()).append("' and BPTYPE='CS' and COMMUNICATION_ADDRESS='Y'").toString());
		            logger.info(new StringBuilder().append("Gurantor communication address check query  : ").append(q141.toString()).toString());
		            gAddr.append(ConnectionDAO.singleReturn(q141.toString()));
		            q141 = null;
		          }
		          if (gAddr.toString().equalsIgnoreCase("0"))
		          {
		            status = "CA";
		            return status;
		          }
		        }
		      }
		      q151.append(new StringBuilder().append("SELECT GCD_ID FROM cr_loan_customer_role where LOAN_CUSTOMER_ROLE_TYPE='COAPPL' AND LOAN_ID=").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).toString());
		      logger.info(new StringBuilder().append("Coapplicant ID List check query : ").append(q151.toString()).toString());
		      coCheckAddr = ConnectionDAO.sqlSelect(q151.toString());
		      StringBuilder q16;
		      if ((!CommonFunction.checkNull(coCheckAddr).equalsIgnoreCase("")) && (coCheckAddr.size() > 0))
		      {
		        int ff = coCheckAddr.size();
		        for (int j = 0; j < ff; j++)
		        {
		          ArrayList data = (ArrayList)coCheckAddr.get(j);
		          int flag = data.size();

		          if (flag > 0)
		          {
		            q16 = new StringBuilder();
		            coAddr = new StringBuilder();
		            q16.append(new StringBuilder().append("select count(*) FROM com_address_m where BPID = '").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(data.get(0))).trim()).append("' and BPTYPE='CS' and COMMUNICATION_ADDRESS='Y'").toString());
		            logger.info(new StringBuilder().append("CO Applicant communication address check query  : ").append(q16.toString()).toString());
		            coAddr.append(ConnectionDAO.singleReturn(q16.toString()));
		            q16 = null;
		          }
		          if (coAddr.toString().equalsIgnoreCase("0"))
		          {
		            status = "CA";
		            return status;
		          }
		        }
		      }
		      q81.append(new StringBuilder().append("SELECT GUARANTEE_AMOUNT FROM cr_loan_customer_role WHERE LOAN_CUSTOMER_ROLE_TYPE='GUARANTOR' AND LOAN_ID=").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).toString());
		      if ((!CommonFunction.checkNull(gCheckAddr).equalsIgnoreCase("")) && (gCheckAddr.size() > 0))
		      {
		        String guaraneeAmountQuery = "select PARAMETER_VALUE from parameter_mst where PARAMETER_KEY='GUARANTEE_AMOUNT'";
		        String guaraneeAmountPara = ConnectionDAO.singleReturn(guaraneeAmountQuery);
		        logger.info(new StringBuilder().append("guaraneeAmountPara:::::::::::::::").append(guaraneeAmountPara).toString());
		        if (CommonFunction.checkNull(guaraneeAmountPara).equalsIgnoreCase("Y"))
		        {
		          logger.info(new StringBuilder().append("Gurantor Amount Query : ").append(q81.toString()).toString());
		          guram.append(ConnectionDAO.singleReturn(q81.toString()));
		          logger.info(new StringBuilder().append("guram       :").append(guram.toString()).toString());
		          if ((CommonFunction.checkNull(guram).trim().equalsIgnoreCase("")) || (guram.toString().equalsIgnoreCase("0.0000")))
		          {
		            status = "GA";
		            return status;
		          }
		        }
		      }
		      String assetFlagQry = new StringBuilder().append("select ASSET_FLAG from cr_loan_dtl l,cr_product_m p where l.LOAN_PRODUCT=p.PRODUCT_ID and  l.LOAN_ID='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).append("'").toString();
		      String assetFlag = ConnectionDAO.singleReturn(assetFlagQry);
		      if (CommonFunction.checkNull(assetFlag).trim().equalsIgnoreCase("A"))
		      {
		        if ((!CommonFunction.checkNull(query101Amount).equalsIgnoreCase("")) && (query101Amt > marAmt))
		        {
		          status = "charge101";
		          return status;
		        }

		        if ((!CommonFunction.checkNull(query102Amount).equalsIgnoreCase("")) && (query102Amt > marAmt))
		        {
		          status = "charge102";
		          return status;
		        }

		        if ((!CommonFunction.checkNull(query101Amount).equalsIgnoreCase("")) && (!CommonFunction.checkNull(query102Amount).equalsIgnoreCase("")))
		        {
		          if (!CommonFunction.checkNull(query102Amount).equalsIgnoreCase(CommonFunction.checkNull(query101Amount)))
		          {
		            status = "bothcharge";
		            return status;
		          }
		        }
		        else if (((query101Amt > 0.0D) && (!CommonFunction.checkNull(query101Amount).equalsIgnoreCase(""))) || ((query102Amt > 0.0D) && (!CommonFunction.checkNull(query102Amount).equalsIgnoreCase(""))))
		        {
		          status = "bothNotExist";
		          return status;
		        }

		        StringBuilder query = new StringBuilder();
		        query.append(new StringBuilder().append("select count(1) from cr_LOAN_collateral_m a join cr_asset_collateral_m b on(b.ASSET_ID=a.ASSETID) where b.ASSET_TYPE='ASSET' and a.LOAN_ID='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).append("'").toString());
		        logger.info(new StringBuilder().append("In execute()  query   :   ").append(query.toString()).toString());
		        String ct = ConnectionDAO.singleReturn(query.toString());
		        logger.info(new StringBuilder().append("In execute()  count   :   ").append(ct).toString());
		        int value = 0;
		        if (ct == null)
		          value = 0;
		        else
		          value = Integer.parseInt(ct);
		        logger.info(new StringBuilder().append("value      :    ").append(value).toString());
		        String str9;
		        if (value > 0)
		        {
		          StringBuilder queryAsset = new StringBuilder();
		          queryAsset.append(new StringBuilder().append("select if(a.LOAN_ASSET_COST=sum(c.ASSET_COLLATERAL_VALUE),0,1) from cr_loan_dtl a join cr_loan_collateral_m b on a.LOAN_ID=b.LOAN_ID and a.loan_id='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).append("' join cr_asset_collateral_m c on b.ASSETID=c.ASSET_ID and c.ASSET_TYPE='ASSET' and a.loan_id='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).append("'").toString());
		          logger.info(new StringBuilder().append("In execute()  query   :   ").append(queryAsset.toString()).toString());
		          String ctAsset = ConnectionDAO.singleReturn(queryAsset.toString());
		          logger.info(new StringBuilder().append("In execute()  count   :   ").append(ctAsset).toString());
		          int valueAsset = 0;
		          if (ctAsset == null)
		            valueAsset = 0;
		          else
		            valueAsset = Integer.parseInt(ctAsset);
		          if (valueAsset > 0) {
		            status = new StringBuilder().append(valueAsset).append("").toString();
		            return status;
		          }
		          StringBuilder queryMarginAmt = new StringBuilder();
		          queryMarginAmt.append(new StringBuilder().append("select if(LOAN_ASSET_COST=LOAN_MARGIN_AMOUNT+LOAN_LOAN_AMOUNT,0,1) from cr_loan_dtl where loan_id='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).append("'").toString());
		          logger.info(new StringBuilder().append("In execute()  query   :   ").append(queryMarginAmt.toString()).toString());
		          String ctMarginAmt = ConnectionDAO.singleReturn(queryMarginAmt.toString());
		          logger.info(new StringBuilder().append("In execute()  count   :   ").append(ctMarginAmt).toString());
		          int valueMarginAmt = 0;
		          if (ctMarginAmt == null)
		            valueMarginAmt = 0;
		          else {
		            valueMarginAmt = Integer.parseInt(ctMarginAmt);
		          }

		          logger.info(new StringBuilder().append("valueMarginAmt    :    ").append(valueMarginAmt).toString());
		          if (valueMarginAmt > 0)
		          {
		            status = "LNASTNTMATMAR";
		            return status;
		          }

		        }

		        StringBuilder noOfAssetLoanQry = new StringBuilder();
		        noOfAssetLoanQry.append(new StringBuilder().append("select NO_OF_ASSET from cr_loan_dtl where loan_id='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).append("' ").toString());
		        String noOfAssetLoan = ConnectionDAO.singleReturn(noOfAssetLoanQry.toString());
		        if (CommonFunction.checkNull(noOfAssetLoan).equalsIgnoreCase(""))
		          noOfAssetLoan = "0";
		        StringBuilder noOfAssetQry = new StringBuilder();
		        noOfAssetQry.append(new StringBuilder().append("select count(1) from cr_loan_collateral_m a join cr_asset_collateral_m b on(b.ASSET_ID=a.ASSETID) where b.ASSET_TYPE='ASSET'  and a.loan_id='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).append("' ").toString());
		        String noOfAsset = ConnectionDAO.singleReturn(noOfAssetQry.toString());
		        if (CommonFunction.checkNull(noOfAsset).equalsIgnoreCase(""))
		          noOfAsset = "0";
		        int loanAsstCt = Integer.parseInt(noOfAssetLoan);
		        int asstCt = Integer.parseInt(noOfAsset);
		        /*if (loanAsstCt < 1) {
		          status = "PLZASSETCAP";
		          return status;
		        }*/
		        if (loanAsstCt > asstCt)
		        {
		          status = "ASSETCT";
		          return status;
		        }
		      }

		      StringBuilder queryToCheckVehicle = new StringBuilder();
		      StringBuilder queryToCheckTotalVehicle = new StringBuilder();
		      queryToCheckVehicle.append("select count(1) from cr_loan_collateral_m a join cr_asset_collateral_m b on(b.ASSET_ID=a.ASSETID) ");
		      queryToCheckVehicle.append("left join cr_make_model_master c on (b.MAKE_MODEL_ID=c.MAKE_MODEL_ID) left join cr_loan_dtl d on (d.LOAN_ID=a.LOAN_ID) ");
		      queryToCheckVehicle.append(new StringBuilder().append(" where b.ASSET_COLLATERAL_CLASS='VEHICLE' and a.LOAN_ID='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).append("' ").toString());
		      queryToCheckVehicle.append(" AND c.PRODUCT_ID=d.loan_product AND ((IFNULL(c.SCHEME_ID,'')<>'' and c.SCHEME_ID=d.loan_scheme) OR (IFNULL(c.SCHEME_ID,'')='' and 'a'='a'))");

		      queryToCheckTotalVehicle.append("select count(1) from cr_loan_collateral_m a join cr_asset_collateral_m b on(b.ASSET_ID=a.ASSETID) ");
		      queryToCheckTotalVehicle.append(new StringBuilder().append(" where b.ASSET_COLLATERAL_CLASS='VEHICLE' and a.LOAN_ID='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).append("' ").toString());

		      String checkVehicleCount = ConnectionDAO.singleReturn(queryToCheckVehicle.toString());
		      String checkTotalVehicleCount = ConnectionDAO.singleReturn(queryToCheckTotalVehicle.toString());
		      logger.info(new StringBuilder().append("checkVehicleCount At loan:: ").append(checkVehicleCount).toString());
		      logger.info(new StringBuilder().append("checkTotalVehicleCount At loan :: ").append(checkTotalVehicleCount).toString());
		      if (!CommonFunction.checkNull(checkVehicleCount).equals(checkTotalVehicleCount))
		      {
		        status = "VEHICLECT";
		      }
		      queryToCheckVehicle = null;
		      queryToCheckTotalVehicle = null;
		      checkVehicleCount = null;
		      checkTotalVehicleCount = null;

		      StringBuilder queryToCheckAssetProduct = new StringBuilder();
		      queryToCheckAssetProduct.append(new StringBuilder().append("select asset_flag from cr_product_m where product_id=(select loan_product from cr_loan_dtl where loan_id='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).append("' ) ").toString());
		      String assetFlagValue = ConnectionDAO.singleReturn(queryToCheckAssetProduct.toString());
		      StringBuilder queryToCheckAssetCost = new StringBuilder();

		      queryToCheckAssetCost.append(new StringBuilder().append("select sum(asset_collateral_value)asset_collateral_value from cr_loan_collateral_m A  left join cr_asset_collateral_m B on b.asset_id=A.assetid where A.loan_id='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).append("'  ").toString());
		      String ToCheckAssetCost = ConnectionDAO.singleReturn(queryToCheckAssetCost.toString());
		      if (ToCheckAssetCost == "")
		      {
		        ToCheckAssetCost = "0.0000";
		      }
		      StringBuilder queryToCheckLoanAssetCost = new StringBuilder();

		      queryToCheckLoanAssetCost.append(new StringBuilder().append("select loan_asset_cost from cr_loan_dtl where loan_id='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).append("'  ").toString());
		      String ToCheckLoanAssetCost = ConnectionDAO.singleReturn(queryToCheckLoanAssetCost.toString());
		      if (CommonFunction.checkNull(assetFlagValue).equalsIgnoreCase("A"))
		      {
		        if (!CommonFunction.checkNull(ToCheckLoanAssetCost).equals(ToCheckAssetCost))
		        {
		          status = "assetCost";
		        }

		      }
				/*String qry="SELECT COUNT(1) FROM CR_LOAN_DTL WHERE LOAN_ID='"+loanId+"' and loan_scheme in (select VALUE from generic_master where generic_key='SIBDI_SCHEME' and REC_STATUS='A')";
				int cnt=Integer.parseInt(ConnectionDAO.singleReturn(qry));
				if(cnt>0){
					String qry11="select round(Sum(ifnull(SIBDI,0))) from cr_loan_collateral_m a join cr_asset_collateral_m b on a.assetid=b.asset_id WHERE a.LOAN_ID='"+loanId+"'";
					String cnt11=ConnectionDAO.singleReturn(qry11); 
					if(CommonFunction.checkNull(cnt11).equalsIgnoreCase("0")){
						status = "sidbi";
						 return status; 
					}
				}*/
/*		      StringBuilder queryToCheckLoanLoan = new StringBuilder();

		      queryToCheckLoanLoan.append(new StringBuilder().append("select count(a.LOAN_ID) from cr_loan_collateral_m b  join cr_loan_dtl a on a.LOAN_ID=b.loan_id where assetid in (select A.assetid from cr_loan_collateral_m A left join cr_loan_dtl C on C.loan_id=A.loan_id join cr_asset_collateral_m B on b.asset_id=A.assetid and B.asset_type='ASSET'  where A.loan_id='").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).append("') and a.REC_STATUS<>'L' and a.LOAN_ID<> '").append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()).append("'  ").toString());

		      String ToCheckLoanLoan = ConnectionDAO.singleReturn(queryToCheckLoanLoan.toString());
		      int counterAsset = Integer.parseInt(ToCheckLoanLoan);
		      logger.info(new StringBuilder().append("queryToCheckLoanLoan::").append(queryToCheckLoanLoan).toString());
		      logger.info(new StringBuilder().append("ToCheckLoanLoan:::").append(ToCheckLoanLoan).toString());

		      if (counterAsset > 0)
		      {
		        status = "assetMapped";
		      }*/

		    }
		    catch (Exception e)
		    {
		      e.printStackTrace();
		      e = null;
		    }
		    finally
		    {
		      tenure = null;
		      sdAmount = null;
		      utilAmount = null;
		      q1 = null;
		      q2 = null;
		      q3 = null;
		      q4 = null;
		      q5 = null;
		      q9 = null;
		      q10 = null;
		      q11 = null;
		      q12 = null;
		      q13 = null;
		      checkUtilizedAm = null;
		      docs = null;
		      docs1 = null;
		      docs2 = null;
		      loanId = null;
		    }
		    logger.info(new StringBuilder().append("In after status ").append(status).toString());
		    return status;
		  }

		

		public boolean stageForwardInLoanInit(String loanId,String makerId,String businessDate) {
			
			ArrayList qryList = new ArrayList();
			logger.info("In stageForward.......Update mode " + loanId);

			String query1 = "update cr_loan_dtl set REC_STATUS='F',MAKER_ID='"+makerId+"',MAKER_DATE=DATE_ADD(STR_TO_DATE('"+businessDate+"','"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) where LOAN_ID="+ loanId;
			
			String query2 = "update cr_loan_collateral_m set REC_STATUS='F' where LOAN_ID="+ loanId;

			String query3 = "update cr_txncharges_dtl set REC_STATUS='F' where TXN_ID="+ loanId;

			String query4 = "update cr_sd_m set REC_STATUS='F' where TXN_TYPE='LIM'and TXN_ID="+ loanId;

			String query5 = "update cr_document_dtl set REC_STATUS='F' where TXNID="+ loanId;
			
			String query6 = "update cr_loan_deviation_dtl set REC_STATUS='F' where REC_STATUS='S' and LOAN_ID="+ loanId;

			qryList.add(query1);
			qryList.add(query2);
			qryList.add(query3);
			
			qryList.add(query4);
			qryList.add(query5);
			qryList.add(query6);
			
			boolean status = false;

			try {
				status = ConnectionDAO.sqlInsUpdDelete(qryList);
				logger
						.info("In stageForwardInLoanInit......................status= "
								+ status);
			} catch (SQLException e) {
			
				e.printStackTrace();
				e=null;
			}
			finally
			{
				query1 = null;
				query2 = null;
				query3 = null;
				query4 = null;
				query5 = null;
				qryList = null;
				makerId=null;
				businessDate=null;
				loanId=null;
			}
			return status;
		}
		
		
		//sachin
		
   public boolean getCountForDoc(String loanId) {
   			String count=null;
   			boolean doc=false;
   			ArrayList mainList=new ArrayList ();
			ArrayList subList =new ArrayList();   				
			StringBuilder query=new StringBuilder();
   			
   			try{
   				
   				query.append(" select count(1) from cr_document_dtl where STAGE_ID='PRS' AND TXN_TYPE='DC' AND DOC_MANDATORY='Y' AND DOC_STATUS='D' and txnid=(select loan_deal_id from cr_loan_dtl where loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+"') ");
   	          
   				logger.info("In getCountForDoc:-"+query);	
   				
   				mainList=ConnectionDAO.sqlSelect(query.toString());
   				for(int i=0;i<mainList.size();i++)
   				{
   					subList= (ArrayList)mainList.get(i);
   					if(subList.size()>0){
   						
   					count=CommonFunction.checkNull(subList.get(0)).trim();
   					
   					}
   				}
   				if(count.equalsIgnoreCase("0")){
						doc=true;
					}
					else{
						doc=false;
					}
   					query=null;
   					
   					}catch(Exception e){
   						e.printStackTrace();
   						e=null;
   					} finally{
   						loanId=null;
   						count=null;
   						mainList.clear();
   						mainList=null;
   						subList.clear();
   						subList=null;
   					}

   					logger.info("status in doc:"+doc);
   					return doc;
   				}

		
		//sachin
		
        public ArrayList<CollateralVo> getcolletralDetail(CollateralVo collateralVo,String loanId) {
   			

   			ArrayList<CollateralVo> getcolletralList=new ArrayList<CollateralVo>();
   			
   			try{
   				
   			    ArrayList mainList=new ArrayList ();
   				ArrayList subList =new ArrayList();
   				
   				StringBuilder query=new StringBuilder();
   				
   				query.append(" Select ASSET_ID,ASSET_TYPE,ASSET_COLLATERAL_DESC,ASSET_COLLATERAL_CLASS,ASSET_COLLATERAL_VALUE "+
   	           "  from cr_asset_collateral_m AC,cr_DEAL_collateral_m DC, CR_LOAN_DTL LD, cr_deal_loan_dtl DD  "+
   	           " where AC.ASSET_ID=DC.ASSETID AND DC.DEAL_ID=DD.DEAL_ID AND LD.LOAN_DEAL_ID=DD.DEAL_ID AND LD.LOAN_DEAL_LOAN_ID=DD.DEAL_LOAN_ID and " +
   	           " AC.ASSET_ID not in (select ASSETID from cr_loan_collateral_m where  REC_STATUS='A' and LOAN_ID in (select loan_id from CR_LOAN_DTL where LOAN_DEAL_ID=(select LOAN_DEAL_ID from cr_loan_dtl where LOAN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+"') )) "+
   	           " AND LD.LOAN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+"'");
   	                    				
   				logger.info("In getcolletralList:-"+query);	
   				
   				mainList=ConnectionDAO.sqlSelect(query.toString());
   				for(int i=0;i<mainList.size();i++)
   				{
   					subList= (ArrayList)mainList.get(i);
   					if(subList.size()>0){
   						CollateralVo VO = new CollateralVo();
   						VO.setAssetsId((CommonFunction.checkNull(subList.get(0)).trim()));
   					    VO.setColltype1((CommonFunction.checkNull(subList.get(1)).trim()));
   						VO.setAssetsCollateralDesc((CommonFunction.checkNull(subList.get(2)).trim()));
   						VO.setAssetNature((CommonFunction.checkNull(subList.get(3)).trim()));
   						VO.setAssetsCollateralValue((CommonFunction.checkNull(subList.get(4)).trim()));
   						getcolletralList.add(VO);
   						VO=null;
   					}
   				}
   				query=null;
   					}catch(Exception e){
   						e.printStackTrace();
   						e=null;
   					}
   					finally{
   						loanId=null;
   						collateralVo=null;
   					}

   					return getcolletralList;
   				}


        public boolean addassetCollateralInCM(String[] assetId, String loanId,String makerId,String makerDate) {
        		
        		boolean status = false;
        		
        		ArrayList qryList = new ArrayList();
        		ArrayList subList = new ArrayList();
        		
        		try
        		{
        			boolean st=false;
        			for (int i = 0; i<assetId.length ; i++) 
        			{
        			   
        				String checkQ="select ASSETID from cr_loan_collateral_m where LOAN_ID="+loanId+" and ASSETID="+assetId[i]+"";
        				logger.info("checkQ: "+checkQ);
        				st=ConnectionDAO.checkStatus(checkQ);
        				logger.info("status "+st);
        				checkQ=null;
        			}
        			
        			
        		if(!st)
        		{		
        			for (int i = 0; i<assetId.length ; i++) 
        			{
        			   
        				
        		
        		
        			StringBuffer bufInsSql = new StringBuffer();
        			PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
        			bufInsSql.append("insert into cr_loan_collateral_m(LOAN_ID,ASSETID,REC_STATUS,MAKER_ID,MAKER_DATE)");
        			bufInsSql.append(" values (");
        			bufInsSql.append(" ?,"); // LOAN_ID
        			bufInsSql.append(" ?,"); // ASSETID
        			bufInsSql.append(" ?,"); // REC_STATUS
        			bufInsSql.append(" ?,"); // MAKER_ID
        			bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND))"); // maker_date
        		
        					if ((CommonFunction.checkNull(loanId).trim()).equalsIgnoreCase(""))
        						insertPrepStmtObject.addNull();
        					else  
        						insertPrepStmtObject.addString((CommonFunction.checkNull(loanId).trim()));
        					
        					if ((CommonFunction.checkNull(assetId[i]).trim()).equalsIgnoreCase(""))
        						insertPrepStmtObject.addNull();
        					else  
        						insertPrepStmtObject.addString((CommonFunction.checkNull(assetId[i]).trim()));
        					
        						insertPrepStmtObject.addString("P");
        						
        					if ((CommonFunction.checkNull(makerId).trim()).equalsIgnoreCase(""))
        						insertPrepStmtObject.addNull();
        					else  
        						insertPrepStmtObject.addString((CommonFunction.checkNull(makerId).trim()));
        					
        					if ((CommonFunction.checkNull(makerDate).trim()).equalsIgnoreCase(""))
        						insertPrepStmtObject.addNull();
        					else  
        						insertPrepStmtObject.addString((CommonFunction.checkNull(makerDate).trim()));
        										
        						insertPrepStmtObject.setSql(bufInsSql.toString());
        						logger.info("insert query1 ### "+ insertPrepStmtObject.printQuery());
        						qryList.add(insertPrepStmtObject);
        						insertPrepStmtObject=null;
        						bufInsSql=null;
        						
        					}
        				
        			 status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
        			logger.info("status......................"+ status);
        			
        			}
        			else
        			{
        				status=false;
        			}
        			
        			
        			
        		}	
        		
        		catch(Exception e)
        		{
        			e.printStackTrace();
        			e=null;
        		}
        		finally{
        			assetId=null;
        			loanId=null;
        			makerId=null;
        			makerDate=null;
        			qryList.clear();
        			qryList=null;
        			subList.clear();
        			subList=null;
        		}
        		return status;
        	}


        public boolean saveInstallPlan(InstallmentPlanForCMVO ipvo) {
          	 
        	logger.info("In saveInstallPlan");
			String FromInstallment[] = ipvo.getFromInstall();
			String ToInstallment[] = ipvo.getToInstall();
			String RecoveryPercen[] = ipvo.getRecoveryPer();
			String prinAmount[] = ipvo.getPrincipalAmount();
			String instalAmount[] = ipvo.getInstallmentAmount();
			String InstallmentType= ipvo.getInstallmentType();
			String TotalInstallment=ipvo.getTotalInstallment();
			String toInstallment = ipvo.getToInstallment();	
			String dueDate[] = ipvo.getDueDate();
			// Code Added for Edit Due date| Rahul papneja |29012018
			String editDueDate = ipvo.getEditDueDate();
			// Ends Here| Rahul papneja
			
			ArrayList qryList=new ArrayList();
			boolean status=false;
			try {
				 PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
				 StringBuilder bufInsSql =	new StringBuilder();
				 
				 StringBuilder checkQ=new StringBuilder();
				  checkQ.append("select count(*) from cr_installment_plan where LOAN_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(ipvo.getLoanId()).trim())+"");
				  
				  String count=ConnectionDAO.singleReturn(checkQ.toString());
				   
				   if(!count.equalsIgnoreCase("0"))
					  { 
					  insertPrepStmtObject = new PrepStmtObject();
					 String qry="DELETE FROM cr_installment_plan WHERE LOAN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(ipvo.getLoanId()).trim())+ "'";

				     insertPrepStmtObject.setSql(qry);
					 qryList.add(insertPrepStmtObject);
					// ConnectionDAO.sqlInsUpdDelete(qryList);
					  
					  }
			
				 for(int k=0;k<FromInstallment.length;k++)  
				{
					bufInsSql =	new StringBuilder();
			     insertPrepStmtObject = new PrepStmtObject();
				  
				
				  insertPrepStmtObject = new PrepStmtObject();
				  // Code added for Edit Due Date| Rahul papneja |29012018
				  if(CommonFunction.checkNull(editDueDate).equalsIgnoreCase("Y"))
				  {
					  bufInsSql.append("insert into cr_installment_plan (LOAN_ID,FROM_INSTL_NO,TO_INSTL_NO,RECOVERY_PERCENT,PRINCIPAL_AMOUNT,INSTALLMENT_AMOUNT,REC_STATUS,SEQ_NO,RECOVERY_TYPE,MAKER_ID,MAKER_DATE,DUE_DATE) " );
					  bufInsSql.append("values(?,?,?,?,?,?,?,?,?,?,DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)");
					  bufInsSql.append(")");
				  }
				  else
				  {
					  // Ends Here| Rahul papneja
				 
					bufInsSql.append("insert into cr_installment_plan (LOAN_ID,FROM_INSTL_NO,TO_INSTL_NO,RECOVERY_PERCENT,PRINCIPAL_AMOUNT,INSTALLMENT_AMOUNT,REC_STATUS,SEQ_NO,RECOVERY_TYPE,MAKER_ID,MAKER_DATE" );
					if(CommonFunction.checkNull(InstallmentType).equalsIgnoreCase("I")||CommonFunction.checkNull(InstallmentType).equalsIgnoreCase("J")) // Code changed for Edit Due Date| Rahul papneja|29012018
						bufInsSql.append(",DUE_DATE)");
						else
							bufInsSql.append(")");
					bufInsSql.append("values(?,?,?,?,?,?,?,?,?,?,DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)");
					if(CommonFunction.checkNull(InstallmentType).equalsIgnoreCase("I")||CommonFunction.checkNull(InstallmentType).equalsIgnoreCase("J")) // Code changed for Edit Due date| Rahul papneja|29012018
					bufInsSql.append(",DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND))");
					else
						bufInsSql.append(")");
				  }
				  
					if(CommonFunction.checkNull(ipvo.getLoanId()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(ipvo.getLoanId().trim());   // loan Id
					
					if(CommonFunction.checkNull(FromInstallment[k]).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(FromInstallment[k].trim()); //From Installment
					
			    	if(CommonFunction.checkNull(ToInstallment[k]).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
					insertPrepStmtObject.addString(ToInstallment[k].trim());// To Installment 
			    	
			    	 if((CommonFunction.checkNull(RecoveryPercen[k])).trim().equalsIgnoreCase(""))
			        		insertPrepStmtObject.addString("0.00");
					else
					 insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(RecoveryPercen[k]).trim())).toString());
			    	 
			    	 
			    	 if((CommonFunction.checkNull(prinAmount[k])).trim().equalsIgnoreCase(""))
			        		insertPrepStmtObject.addString("0.00");
					else
					 insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(prinAmount[k]).trim())).toString());// PRINCIPAL_AMOUNT   	
			    	if((CommonFunction.checkNull(instalAmount[k])).trim().equalsIgnoreCase(""))
		        		insertPrepStmtObject.addString("0.00");
				     else
				    insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(instalAmount[k]).trim())).toString());// INSTALLMENT_AMOUNT	
			    	insertPrepStmtObject.addString("P");// REC_STATUS   	
									
			    	insertPrepStmtObject.addString(""+(k+1));// SEQ_NO    
			    	
			    	if(CommonFunction.checkNull(InstallmentType).equalsIgnoreCase("I"))
			    	{
			    		insertPrepStmtObject.addString("F");
			    	}
			    	else
			    	{
			    	if(CommonFunction.checkNull(ipvo.getRecoveryType()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
					insertPrepStmtObject.addString(ipvo.getRecoveryType().trim());// RECOVERY_TYPE
			    	}

			 	//---------------------------------------------------------
			 	if ((CommonFunction.checkNull(ipvo.getMakerId())).trim().equalsIgnoreCase(""))
			 		insertPrepStmtObject.addNull();
			 	else
			 		insertPrepStmtObject.addString((ipvo.getMakerId()).trim());
			 	if ((CommonFunction.checkNull(ipvo.getMakerDate()).trim()).equalsIgnoreCase(""))
			 		insertPrepStmtObject.addNull();
			 	else
			 		insertPrepStmtObject.addString(CommonFunction.checkNull(ipvo.getMakerDate()).trim());
			 	//---------------------------------------------------------
			 	// Code added for Edit Due Date| Rahul papneja| 30012018
			 // Vishal changes start
				if(CommonFunction.checkNull(editDueDate).equalsIgnoreCase("Y"))
				{
					if(CommonFunction.checkNull(dueDate[k]).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
					insertPrepStmtObject.addString(dueDate[k].trim());// Due Date  	
				}
				// Vishal changes end
				else
				{
			 	if(CommonFunction.checkNull(InstallmentType).equalsIgnoreCase("I")||CommonFunction.checkNull(InstallmentType).equalsIgnoreCase("J"))
			 	// Ends Here| Rahul papneja
			 	
			 	{
			 	if(CommonFunction.checkNull(dueDate[k]).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
				insertPrepStmtObject.addString(dueDate[k].trim());// To duedate 
			 	}
				  
				}
				 	insertPrepStmtObject.setSql(bufInsSql.toString());
					logger.info("IN saveInstallPlan() insert query1 ### "+insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);
				  
				}
				
				
					status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
					checkQ=null;
					bufInsSql=null;
					count=null;
				} catch (Exception e) {
					e.printStackTrace();
					e=null;
				}
				finally{
					ipvo=null;
					FromInstallment = null;
					ToInstallment = null;
					RecoveryPercen = null;
					prinAmount = null;
					instalAmount= null;
					InstallmentType= null;
					TotalInstallment= null;
					toInstallment = null;	
				}
			    logger.info("In saveInstallPlan......................"+status);
			return status;
		}

    	public boolean saveNoOfDisb(NoOfDisbursalVo vo) {
    		
    		
    		logger.info("In saveNoOfDisb mod"+vo.getNoOfDisbursal().length);
    		 String noDisb[] = vo.getNoOfDisbursal();
    		 String disbDate[] = vo.getDateOfDisbursal();
    		 String disbDesc[] = vo.getDescOfDisbursal();
    		 String amount[]=vo.getAmountOfDisbursal();
    		
    		logger.info("In .saveNoOfDisb mod"+vo.getNoOfDisbursal().length);
    		ArrayList qryList=new ArrayList();
    		ArrayList qryList1=new ArrayList();
    		boolean status=false;
    		try {
    			StringBuilder	bufInsSql=null;
    			
    			 
    			 StringBuilder checkQ=null;
    			 StringBuilder count =	new StringBuilder();
    			 logger.info("Doc Id in saveNoOfDisb: "+noDisb.length);
    			int disLength= noDisb.length;
    		for(int k=0;k<disLength; k++)
    		{
    			checkQ=new StringBuilder();
    			PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
    			 
//    			 StringBuffer bufInsSql =	new StringBuffer();
    			 logger.info("Doc Id in saveNoOfDisb: "+noDisb[k]);
    			
    			   checkQ.append("select count(*) from cr_loan_disbursalsch_dtl where LOAN_ID="+vo.getLoanId()+" and DISBURSAL_NO="+CommonFunction.checkNull(noDisb[k]));
    			   count.append(ConnectionDAO.singleReturn(checkQ.toString()));
    			   
    			   if(!count.toString().equalsIgnoreCase("0"))
    				  { 
    				   StringBuilder qry=new StringBuilder();
    				 qry.append("DELETE FROM cr_loan_disbursalsch_dtl WHERE LOAN_ID='"+(CommonFunction.checkNull(vo.getLoanId()).trim())+ "'");
    				 qryList1.add(qry);
    				 ConnectionDAO.sqlInsUpdDelete(qryList1);
    				 qry=null;
    				  }
    			 // if(count.equalsIgnoreCase("0"))
    			 // {
    			    //logger.info("Doc Id in saveDocumentPOD: "+dId[k]);
    			   bufInsSql =	new StringBuilder();
    				bufInsSql.append("insert into cr_loan_disbursalsch_dtl(LOAN_ID,DISBURSAL_NO,DISBURSAL_STAGE,DISBURSAL_DESCRIPTION,PROPOSED_DISBURSAL_DATE,PROPOSED_DISBURSAL_AMOUNT,DISBURSAL_FLAG,REC_STATUS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE) values(?,?,?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,STR_TO_DATE(?,'"+dateFormat+"'))");
    				
    				if(CommonFunction.checkNull(vo.getLoanId()).trim().equalsIgnoreCase(""))
    					insertPrepStmtObject.addNull();
    				else
    					insertPrepStmtObject.addString(vo.getLoanId().trim());   // loan Id
    				
    				if(CommonFunction.checkNull(noDisb[k]).trim().equalsIgnoreCase(""))
    					insertPrepStmtObject.addNull();
    				else
    					insertPrepStmtObject.addString(noDisb[k].trim()); //disb no
    				
    		
    				     insertPrepStmtObject.addString("MM");// Disbursal stage
    					
    					
    				if(CommonFunction.checkNull(disbDesc[k]).trim().equalsIgnoreCase(""))
    					insertPrepStmtObject.addNull();
    				else
    					insertPrepStmtObject.addString(disbDesc[k].trim());// Description
    				
    				if(CommonFunction.checkNull(disbDate[k]).trim().equalsIgnoreCase(""))
    					insertPrepStmtObject.addNull();
    				else
    					insertPrepStmtObject.addString(disbDate[k].trim());          // disb date			
    				
    					
    				if((CommonFunction.checkNull(amount[k])).equalsIgnoreCase(""))
    					insertPrepStmtObject.addString("0.00");
    					else
    						insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(amount[k]).trim())).toString());
    					
    				
    				    insertPrepStmtObject.addNull();//Flag
    				    
    				    if(CommonFunction.checkNull(vo.getRecStatus()).trim().equalsIgnoreCase(""))
        					insertPrepStmtObject.addNull();
        				else
        					insertPrepStmtObject.addString(vo.getRecStatus());    
    				    
    				    if(CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
        					insertPrepStmtObject.addNull();
        				else
        					insertPrepStmtObject.addString(vo.getMakerId());   
    				    
    				    if(CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
        					insertPrepStmtObject.addNull();
        				else
        					insertPrepStmtObject.addString(vo.getMakerDate());   
    				    
    				    if(CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
        					insertPrepStmtObject.addNull();
        				else
        					insertPrepStmtObject.addString(vo.getMakerId());   
    				    
    				    if(CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
        					insertPrepStmtObject.addNull();
        				else
        					insertPrepStmtObject.addString(vo.getMakerDate());   
    		
    		
    		
    		insertPrepStmtObject.setSql(bufInsSql.toString());
    		logger.info("IN saveNoOfDisb()del insert query1 ### "+insertPrepStmtObject.printQuery());
    		qryList.add(insertPrepStmtObject);
    		checkQ=null;
    		bufInsSql=null;
    }
    	
    	
    		status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
    		
    		checkQ=null;
    		bufInsSql=null;
    		count=null;
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		e=null;
    	}
    	finally{
    		 amount=null;
    		 noDisb=null;
    		 disbDate=null;
    		 disbDesc=null;
    		 qryList.clear();
    		 qryList=null;
    		 qryList1.clear();
    		 qryList1=null;
    		 vo=null;
    	}
        logger.info("In saveNoOfDisb......................"+status);
        return status;
    } 

		public boolean deleteNoOfDisb(String loanId, String disNo) {
			
			// TODO Auto-generated method stub
			return false;
		}


		public boolean deleteSecurityDeposit(String[] id) {

			logger.info("In deleteSecurityDeposit ......." + id);
			boolean status = false;
			ArrayList queryList = new ArrayList();
			StringBuilder bufInsSql = null;
			int length = id.length;
			try {
				for (int k = 0; k < length; k++) {
					bufInsSql = new StringBuilder();
					bufInsSql.append("DELETE FROM cr_sd_m WHERE TXN_ID=" + id[k]
							+ " and TXN_TYPE='LIM'");
					logger.info("### bufInsSql #### " + bufInsSql.toString());
					queryList.add(bufInsSql.toString());
					bufInsSql = null;
				}
				status = ConnectionDAO.sqlInsUpdDelete(queryList);
			} catch (Exception e) {
				e.printStackTrace();
				e=null;
			}
			finally
			{
				queryList.clear();
				queryList = null;
				bufInsSql = null;
				id=null;
			}
			return status;

		}


		public String checkDealSanctionVaildTill(String dealId,String businessDate)
		{
			logger.info("Inside checkDealSanctionVaildTill.......DAOImpl");
			
//			String sanctionValid = "";
			String sanctionValid =null;
			StringBuilder query = new StringBuilder();
			
			query.append("select DATE_FORMAT(a.DEAL_SANCTION_VALID_TILL,'"+dateFormat+"') from cr_deal_loan_dtl a");
			query.append(" where a.DEAL_ID="+StringEscapeUtils.escapeSql(dealId)+" and a.rec_status='A'");
			String sanctionDateStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(query.toString()));
			SimpleDateFormat dateFormat1 = new SimpleDateFormat(dateForDisbursal);
			try 
			{
				Date sanctionDate = dateFormat1.parse(sanctionDateStr); 
				Date actDate = dateFormat1.parse(businessDate);
				//logger.info("sanctionDate: "+sanctionDate);
				//logger.info("actDate: "+actDate);
				if(sanctionDate.after(actDate) || sanctionDate.equals(actDate))
				{
					sanctionValid="T";
				}
				else
					sanctionValid="F";
			}catch(Exception e){
				 e.printStackTrace();
				 e=null;
				}
			finally
			{
				sanctionDateStr = null;
				query = null;
				dateFormat1 = null;
				dealId=null;
				businessDate=null;
			}
			//logger.info("sanctionValid: "+sanctionValid);
			return sanctionValid;
		}


		public ArrayList getRevertDataInLoanInit(String loanId) {
			ArrayList list=new ArrayList();
//			String roleName="";
			try{
				logger.info("In getRevertDataInLoanInit..........................DAOImpl"+loanId);

				String query = "select REC_STATUS, LOAN_REMARKS from cr_loan_dtl where LOAN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()+"' and REC_STATUS='F' limit 1";
				//logger.info("In getRevertDataInLoanInit...............query...........DAOImpl"+query);
				UnderwriterApprovalVo noteVO=null;
				ArrayList product = ConnectionDAO.sqlSelect(query);
				query=null;
				//logger.info("getApprovalData "+product.size());
				for(int i=0;i<product.size();i++){
				//	logger.info("getRevertDataInLoanInit...FOR loop "+CommonFunction.checkNull(product.get(i)).toString());
					ArrayList data=(ArrayList)product.get(i);
					if(data.size()>0)	{
						noteVO = new UnderwriterApprovalVo();
						//noteVO.setUserName((CommonFunction.checkNull(data.get(0))).trim());
						//noteVO.setApprovalLevel((roleName));
						noteVO.setDecision((CommonFunction.checkNull(data.get(0))).trim());
						noteVO.setRemarks((CommonFunction.checkNull(data.get(1))).trim());
						list.add(noteVO);
						noteVO=null;						
					}
				}
			}
			catch(Exception e){
				e.printStackTrace();
				e=null;
			}
			finally{
				loanId=null;
			}
			return list;
		}


		public String insertLoanAuthor(LoanInitAuthorVo vo,String source) {
			
			logger.info("In insertLoanAuthor ........ DAOImpl");
			String status=null;
			StringBuilder s1=new StringBuilder();
			StringBuilder s2=new StringBuilder();
		    ArrayList<Object> in =new ArrayList<Object>();
			ArrayList<Object> out =new ArrayList<Object>();
			ArrayList outMessages = new ArrayList();
			try 
			{
				in.add(CommonFunction.checkNull( vo.getCompanyId()).trim());
				in.add(CommonFunction.checkNull(vo.getLoanId()).trim());
				String date=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getBussinessDate()).trim());
				if(date != null)
			    	in.add(date);
			    in.add(CommonFunction.checkNull(vo.getUserId()).trim());
			    in.add(CommonFunction.checkNull(vo.getDecision()).trim());
			    in.add(CommonFunction.checkNull(vo.getComments()).trim());
				out.add(s1);
			    out.add(s2);
			    if(CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
			    {
			    	 logger.info("Edit_Loan_Authorization "+in.toString()+out.toString());
			    	 outMessages=(ArrayList) ConnectionDAO.callSP("Edit_Loan_Authorization",in,out);
			    }
			    else
			    {
			    	logger.info("Loan_Authorization "+in.toString()+out.toString());
			    	outMessages=(ArrayList) ConnectionDAO.callSP("Loan_Authorization",in,out);
			    }
			    s1.append(CommonFunction.checkNull(outMessages.get(0)));
			    s2.append(CommonFunction.checkNull(outMessages.get(1)));
				if(s1!=null && s1.toString().equalsIgnoreCase("S"))
				{
					status=s1.toString();
				}
				else
				{
					status=s2.toString();
				}
				logger.info("status: "+s1);
				logger.info("s2: "+s2);
			}
			catch (Exception e) 
			{e.printStackTrace();}
			finally{
				source=null;
				vo=null;
				s1=null;
				s2=null;
				in.clear();
				in=null;
				out.clear();
				out=null;
				outMessages.clear();
				outMessages=null;
				
			}
			
			/*if(!CommonFunction.checkNull(source).trim().equalsIgnoreCase("ED"))
			{
				try
				{
					if(s1!=null && s1.toString().equalsIgnoreCase("S") && vo.getDecision().equalsIgnoreCase("A"))
					{
						logger.info("Loan Id: "+vo.getLoanId());
						ArrayList al =new ArrayList();
						String updateLoanDtlTable="update cr_deal_loan_dtl set DEAL_UTILIZED_AMOUNT=ifnull(DEAL_UTILIZED_AMOUNT,0)+(select LOAN_LOAN_AMOUNT from cr_loan_dtl where LOAN_ID="+((CommonFunction.checkNull(vo.getLoanId()).trim()))+") "+ 
												  " where DEAL_LOAN_ID=(select LOAN_DEAL_LOAN_ID from cr_loan_dtl where LOAN_ID="+((CommonFunction.checkNull(vo.getLoanId()).trim()))+")";
						
						logger.info("Query: "+updateLoanDtlTable);
						al.add(updateLoanDtlTable);
						boolean checkUp=ConnectionDAO.sqlInsUpdDelete(al);
						logger.info("checkUp insertLoanAuthor updateLoanDtlTable : "+checkUp);
						
						
					}
					
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}*/
			return status;
		 }
		

		public boolean saveSecurityDeposit(SecurityDepositVO vo) {
			SecurityDepositVO cv = (SecurityDepositVO) vo;
			ArrayList qryList = new ArrayList();
			StringBuilder bufInsSql = new StringBuilder();
			PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
			boolean status = false;
//			String q1="";
//			String status1="";
			
			StringBuilder q1=new StringBuilder();
			StringBuilder status1=new StringBuilder();
			
			logger.info("In saveSecurityDeposit.TXNID(dealId/LoanID)"+vo.getLoanId()+" and TXN_TYPE "+vo.getTxnType());
			try {

				q1.append("select count(*) from cr_sd_m where TXN_ID="+vo.getLoanId()+" and TXN_TYPE='"+vo.getTxnType()+"'");
				logger.info("query "+q1);
				status1.append(ConnectionDAO.singleReturn(q1.toString()));
			if(status1.toString().equalsIgnoreCase("0"))
			{
				bufInsSql.append("Insert into cr_sd_m(TXN_TYPE,TXN_ID,SD_AMOUNT,SD_INTEREST_TYPE,SD_INTEREST_RATE,SD_COMPOUNDING_FREQ,SD_TENURE,SD_INTEREST,SD_ADJUSTMENT,REC_STATUS,MAKER_ID,MAKER_DATE)");

				bufInsSql.append(" values ( ");
				bufInsSql.append(" '"+vo.getTxnType()+"',");//TXN_TYPE
				bufInsSql.append(" ?,");//TXN_ID
				bufInsSql.append(" ?,");//SD_AMOUNT
				bufInsSql.append(" ?,");//SD_INTEREST_TYPE
				bufInsSql.append(" ?,");//SD_INTEREST_RATE
				bufInsSql.append(" ?,");//SD_COMPOUNDING_FREQ
				bufInsSql.append(" ?,");//SD_TENURE
				bufInsSql.append(" ?,");//SD_INTEREST
				bufInsSql.append(" ?,");//SD_ADJUSTMENT
				bufInsSql.append(" 'P',");//REC_STATUS
				bufInsSql.append(" ?,");//MAKER_ID
				bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) )");//MAKER_DATE

				if (CommonFunction.checkNull(cv.getLoanId()).equalsIgnoreCase("")) //TXN_ID
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(cv.getLoanId()).trim()));

				
				if (CommonFunction.checkNull(cv.getSecurityAmount()).equalsIgnoreCase(""))//SD_AMOUNT
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(cv.getSecurityAmount()).trim())).toString());
				
				
				if (CommonFunction.checkNull(cv.getInterestType()).equalsIgnoreCase("")) //SD_INTEREST_TYPE
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(cv.getInterestType()).trim()));
				
				if (CommonFunction.checkNull(cv.getInterestRate()).equalsIgnoreCase("")) //SD_INTEREST_RATE
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(cv.getInterestRate()).trim())).toString());
							
				if (CommonFunction.checkNull(cv.getCompoundFrequency()).equalsIgnoreCase("")) //SD_COMPOUNDING_FREQ
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(cv.getCompoundFrequency()).trim()));
				if (CommonFunction.checkNull(cv.getTenure()).equalsIgnoreCase("")) //SD_TENURE
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(cv.getTenure()).trim()));

				
				
				if (CommonFunction.checkNull(cv.getRelatedInterest()).equalsIgnoreCase("")) //SD_INTEREST
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(cv.getRelatedInterest()).trim())).toString());

				if (CommonFunction.checkNull(cv.getSdAdjust()).equalsIgnoreCase("")) //SD_ADJUSTMENT
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(cv.getSdAdjust()).trim()));

				
				
				if (CommonFunction.checkNull(cv.getUserId()).equalsIgnoreCase(""))//MAKER_ID
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(cv.getUserId()).trim()));

				if (CommonFunction.checkNull(cv.getBussinessDate()).equalsIgnoreCase(""))//MAKER_DATE
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(cv.getBussinessDate()).trim()));

				logger.info("ibufInsUpdSql.toString()" + bufInsSql.toString());
				insertPrepStmtObject.setSql(bufInsSql.toString());

				logger.info("IN security deposit insert query1 ### "
						+ insertPrepStmtObject.printQuery());

				qryList.add(insertPrepStmtObject);
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In security deposit......................" + status);
			 }
			else
			{
				bufInsSql.append("update cr_sd_m set SD_INTEREST_TYPE=?,SD_INTEREST_RATE=?,SD_COMPOUNDING_FREQ=?,SD_INTEREST=?,SD_AMOUNT=?,SD_ADJUSTMENT=?  where TXN_ID=? and TXN_TYPE='"+vo.getTxnType()+"' ");
				
				if (CommonFunction.checkNull(cv.getInterestType()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(cv.getInterestType()).trim()));
				if (CommonFunction.checkNull(cv.getInterestRate()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(cv.getInterestRate()).trim())).toString());
				if (CommonFunction.checkNull(cv.getCompoundFrequency()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(cv.getCompoundFrequency()).trim()));
				
				if (CommonFunction.checkNull(cv.getRelatedInterest()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(cv.getRelatedInterest()).trim())).toString());
				
				if (CommonFunction.checkNull(cv.getSecurityAmount()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(cv.getSecurityAmount()).trim())).toString());
				
				if (CommonFunction.checkNull(cv.getSdAdjust()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(cv.getSdAdjust()).trim()));

				
				if (CommonFunction.checkNull(cv.getLoanId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString((CommonFunction.checkNull(cv.getLoanId()).trim()));


				logger.info("ibufInsUpdSql.toString()" + bufInsSql.toString());
				insertPrepStmtObject.setSql(bufInsSql.toString());

				logger.info("IN security Deposit update query1 ### "
						+ insertPrepStmtObject.printQuery());

				qryList.add(insertPrepStmtObject);
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				logger.info("In security Deposit......................" + status);
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally
			{
				cv = null;
				qryList = null;
				bufInsSql = null;
				insertPrepStmtObject = null;
				q1= null;
				status1= null;
			}
			return status;
		}



		public boolean insertAsset(String[] assetId, String dealOrLoanId, String userId,
				String bDate, String fromDealOrLoan) {

			ArrayList qryList = new ArrayList();
			StringBuilder bufInsSql = null;
			PrepStmtObject insertPrepStmtObject = null;
			StringBuilder q1 =new StringBuilder();
			boolean check = false;
			boolean status = false;

			try {

				if (fromDealOrLoan.equalsIgnoreCase("D")) {
					logger
							.info("In insertAsset...used Table cr_deal_collateral_m");
					int length = assetId.length;
					for (int k = 0; k < length; k++) {

						q1.append("select DEAL_ID,ASSETID from cr_deal_collateral_m where DEAL_ID='"
								+ dealOrLoanId + "' and ASSETID=" + assetId[k]);

						check = ConnectionDAO.checkStatus(q1.toString());

						bufInsSql = new StringBuilder();
						insertPrepStmtObject = new PrepStmtObject();
						bufInsSql.append("Insert into cr_deal_collateral_m(DEAL_ID,ASSETID,REC_STATUS,MAKER_ID,MAKER_DATE)");
						bufInsSql.append(" values ( ");
						bufInsSql.append(" ?,");
						bufInsSql.append(" ?,");
						bufInsSql.append(" ?,");
						bufInsSql.append(" ?,");
						bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) )");

						if (dealOrLoanId.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(dealOrLoanId);

						if (assetId[k].equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(assetId[k]);

						insertPrepStmtObject.addString("P");
						if (userId.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(userId);
						if (bDate.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(bDate);

						insertPrepStmtObject.setSql(bufInsSql.toString());

						qryList.add(insertPrepStmtObject);

						logger
								.info("IN cr_deal_collateral_m insertSelectAsset insert query1 ### "
										+ insertPrepStmtObject.printQuery());

					}
				} else if(fromDealOrLoan.equalsIgnoreCase("L")){
					logger
							.info("In insertAsset...used Table cr_loan_collateral_m");
					int length = assetId.length;
					for (int k = 0; k < length; k++) {

						q1.append("select LOAN_ID,ASSETID from cr_loan_collateral_m where LOAN_ID='"
								+ dealOrLoanId + "' and ASSETID=" + assetId[k]);

						check = ConnectionDAO.checkStatus(q1.toString());

						bufInsSql = new StringBuilder();
						insertPrepStmtObject = new PrepStmtObject();
						bufInsSql
								.append("Insert into cr_loan_collateral_m(LOAN_ID,ASSETID,REC_STATUS,MAKER_ID,MAKER_DATE)");
						bufInsSql.append(" values ( ");
						bufInsSql.append(" ?,");
						bufInsSql.append(" ?,");
						bufInsSql.append(" ?,");
						bufInsSql.append(" ?,");
						bufInsSql.append(" STR_TO_DATE(?,'"+dateFormatWithTime+"') )");

						if (dealOrLoanId.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(dealOrLoanId);

						if (assetId[k].equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(assetId[k]);

						insertPrepStmtObject.addString("P");
						if (userId.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(userId);
						if (bDate.equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(bDate);

						insertPrepStmtObject.setSql(bufInsSql.toString());

						qryList.add(insertPrepStmtObject);

						logger
								.info("IN cr_loan_collateral_m insertSelectAsset insert query1 ### "
										+ insertPrepStmtObject.printQuery());

					}
				}

				if (!check) {
					status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				}
				logger.info("In insertSelectAsset......................"
						+ status);

			} catch (Exception e) {
				e.printStackTrace();
			}
			finally
			{
				qryList = null;
				bufInsSql = null;
				insertPrepStmtObject = null;
				q1 = null;
				assetId=null;
				dealOrLoanId=null;
				userId=null;
				bDate=null;
				fromDealOrLoan=null;
				
			}
			return status;
		}


//		public ArrayList<RepayScheduleVo> getNewRepayScheduleRepricing(RepayScheduleVo vo, String loanId, String reschId) 
//		{
//			logger.info("In getNewRepayScheduleRepricing DAOImpl");
//			String status="";
////			CallableStatement cst=null;
//		    Connection con=ConnectionDAO.getConnection();
//			ArrayList<RepayScheduleVo> list = new ArrayList<RepayScheduleVo>();
//			ArrayList mainlist = new ArrayList();
//			ArrayList subList = new ArrayList();
//		    String s1="";
//			String s2="";
//			String s3="";	 
//		    String s4="";
//			String s5="";
//			String s6="";	 
//		    ArrayList<Object> in =new ArrayList<Object>();
//			ArrayList<Object> out =new ArrayList<Object>();
//			ArrayList outMessages = new ArrayList();
////			String chkQuery = "select count(INSTALLMENT_PLAN_ID) from cr_resch_installment_plan where " +
////			" resch_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(reschId))+" and" +
////			" loan_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId))+"";
////			String chkStatusStr = ConnectionDAO.singleReturn(chkQuery);
////			int chkStatus = Integer.parseInt(chkStatusStr);
////			logger.info("chkStatus: "+chkStatus);
////			if(chkStatus>0)
////			{
//				try
//				{
////					con.setAutoCommit(false);
////			        cst=con.prepareCall("call Repricing_Author(?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?,?,?,?,?,?)");
//					in.add(Integer.parseInt((CommonFunction.checkNull(vo.getCompanyId()).trim())));
//					in.add(Integer.parseInt((CommonFunction.checkNull(reschId).trim())));
////			        cst.setString(3, (CommonFunction.checkNull(vo.getAuthorDate()).trim()));
//			        String date=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getAuthorDate()).trim());
//				    if(date != null)
//				    	in.add(date);
//				    in.add((CommonFunction.checkNull(vo.getAuthorId()).trim()));
//				    in.add("T");
//				    in.add("");
////			        cst.registerOutParameter(7, Types.CHAR );
////			        cst.registerOutParameter(8, Types.CHAR );
////			        cst.registerOutParameter(9, Types.CHAR );
////			        cst.registerOutParameter(10, Types.CHAR );
////			        cst.registerOutParameter(11, Types.CHAR );
////			        cst.registerOutParameter(12, Types.CHAR );
////			        cst.executeUpdate();
////			        String s1= cst.getString(11);
////			        String s2 = cst.getString(12);
//			        outMessages=(ArrayList) ConnectionDAO.callSP("Repricing_Author",in,out);
//				    s3=CommonFunction.checkNull(outMessages.get(0));
//				    s4=CommonFunction.checkNull(outMessages.get(1));
//				    s5=CommonFunction.checkNull(outMessages.get(2));
//				    s6=CommonFunction.checkNull(outMessages.get(3));
//				    s1=CommonFunction.checkNull(outMessages.get(4));
//				    s2=CommonFunction.checkNull(outMessages.get(5));
//			        logger.info("s1: "+s1);
//			        logger.info("s2: "+s2);
//			        if(s1.equalsIgnoreCase("S"))
//			        {
//			        	status=s1;
//			        	
//						String bussIrrQ="select deal_irr2 from cr_deal_loan_dtl where DEAL_ID=(select LOAN_DEAL_ID from cr_loan_dtl where LOAN_ID="+loanId+")";
//						logger.info("Query in getRepaySchFieldsDetail--DEAL_IRR2---" + bussIrrQ);
//						String bussIrr = ConnectionDAO.singleReturn(bussIrrQ);
//						
//						String query="select R_Seq_No,DATE_FORMAT(R_Due_Date,'"+dateFormat+"'),R_Instl_Amount,R_Prin_Comp,R_Int_Comp,R_EXCESS_Int_Comp," +
//						" if(R_ADV_FLAG='Y','YES','NO'),R_Prin_OS from Repay_Temp where R_LOAN_ID="+loanId+" order by R_DUE_DATE,R_ORG_SEQ_NO";
//						logger.info("Query in getNewRepayScheduleRepricing-----" + query);
//						int j=1;
//						Statement st = con.createStatement();
//						ResultSet rs = st.executeQuery(query);
//			
//			            //getting the no of fields selected
//			            int noOfFields = (rs.getMetaData()).getColumnCount();
//			
//			            //counter which will be incremented for the no of fields
//			            //check whether the records have been returned
//			            
//			
//			            while (rs.next()) {
//			                int counter = 1; //this will restart the count every time from 1
//			
//			                //change made ..arraylist to beinitialized within the rs.next()
//			                ArrayList records = new ArrayList();
//			
//			                while (counter <= noOfFields) {
//			                    //adding the column values in the arraylist
//			                    records.add(rs.getString(counter));
//			                    counter++;
//			                }
//			
//			                //adding the arraylist to the vector
//			                mainlist.add(records);
//			                
//			            } //end of rs.next()
//						logger.info("mainlist size: "+mainlist.size());
//						
//						for (int i = 0; i < mainlist.size(); i++) 
//						{
//							
//							subList = (ArrayList) mainlist.get(i);
//							
//							RepayScheduleVo repvo = new RepayScheduleVo();
//							if (subList.size() > 0)
//							{
//								repvo.setInstNo((CommonFunction.checkNull(subList.get(0)).trim()));
//								repvo.setDueDate((CommonFunction.checkNull(subList.get(1)).trim()));
//								
//								if(!CommonFunction.checkNull(subList.get(2)).trim().equalsIgnoreCase(""))
//					    		{
//						    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());  
//						    		repvo.setInstAmount(myFormatter.format(reconNum));
//					    		}
//								
//								if(!CommonFunction.checkNull(subList.get(3)).trim().equalsIgnoreCase(""))
//					    		{
//						    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(3))).trim());  
//						    		repvo.setPrinciple(myFormatter.format(reconNum));
//					    		}
//					
//								if(!CommonFunction.checkNull(subList.get(4)).trim().equalsIgnoreCase(""))
//					    		{
//						    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(4))).trim());  
//						    		repvo.setInstCom(myFormatter.format(reconNum));
//					    		}
//					
//								if(!CommonFunction.checkNull(subList.get(5)).trim().equalsIgnoreCase(""))
//					    		{
//						    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(5))).trim());  
//						    		repvo.setExcess(myFormatter.format(reconNum));
//					    		}
//							}
//								repvo.setAdvFlag((CommonFunction.checkNull(subList.get(6)).trim()));
//								if(!CommonFunction.checkNull(subList.get(7)).trim().equalsIgnoreCase(""))
//					    		{
//						    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(7))).trim());  
//						    		repvo.setPrinOS(myFormatter.format(reconNum));
//					    		}
//								repvo.setDealIRR2(bussIrr);
//								
//								
//								if(!CommonFunction.checkNull(s3).trim().equalsIgnoreCase(""))
//			    	    		{
//				    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(s3)).trim());  
//				    	    		repvo.setFinalRate(myFormatter.format(reconNum));
//			    	    		}
//								
//								if(!CommonFunction.checkNull(s4).trim().equalsIgnoreCase(""))
//			    	    		{
//				    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(s4)).trim());  
//				    	    		repvo.setEffectiveRate(myFormatter.format(reconNum));
//			    	    		}
//								
//								if(!CommonFunction.checkNull(s5).trim().equalsIgnoreCase(""))
//			    	    		{
//				    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(s5)).trim());  
//				    	    		repvo.setMktIRR1(myFormatter.format(reconNum));
//			    	    		}
//								
//								if(!CommonFunction.checkNull(s6).trim().equalsIgnoreCase(""))
//			    	    		{
//				    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(s6)).trim());  
//				    	    		repvo.setMktIRR2(myFormatter.format(reconNum));
//			    	    		}
//								
//							list.add(repvo);
//						}
//						con.commit();
//			        }
//			        else if(s1.equalsIgnoreCase("E"))
//			        {
//			        	con.rollback();
//			        	status = s2;
//			        }
//			        
//					
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				finally
//				{
//					try {
//						con.close();
////						cst.close();
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}
//					
//				}
////			}
//
//			return list;
//		}
		
		
		
//neeraj
public ArrayList<RepayScheduleVo> getNewRepayScheduleRepricing(RepayScheduleVo vo, String loanId, String reschId) 
{
	logger.info("In getNewRepayScheduleRepricing DAOImpl");
	ArrayList mainlist=new ArrayList();
	ArrayList list=new ArrayList();
	ArrayList subList=null;
	try
	{
		StringBuilder bussIrrQ=new StringBuilder();
		 bussIrrQ.append("select deal_irr2 from cr_deal_loan_dtl where DEAL_LOAN_ID=(select LOAN_DEAL_LOAN_ID from cr_loan_dtl where LOAN_ID="+loanId+")");
		logger.info("Query in getRepaySchFieldsDetail--DEAL_IRR2---" + bussIrrQ);
		String bussIrr = ConnectionDAO.singleReturn(bussIrrQ.toString());
		
		StringBuilder query=new StringBuilder();
		 query.append("select R_Seq_No,DATE_FORMAT(R_Due_Date,'"+dateFormat+"'),R_Instl_Amount,R_Prin_Comp,R_Int_Comp,R_EXCESS_Int_Comp," +
				" if(R_ADV_FLAG='Y','YES','NO'),R_Prin_OS from Repay_Temp where R_LOAN_ID="+loanId+" order by R_DUE_DATE,R_ORG_SEQ_NO");
		logger.info("Query in getNewRepayScheduleRepricing-----" + query);
		mainlist = ConnectionDAO.sqlSelect(query.toString());
		logger.info("mainlist size: "+mainlist.size());
				
		for (int i = 0; i < mainlist.size(); i++) 
		{
			subList = (ArrayList) mainlist.get(i);
			RepayScheduleVo repvo = new RepayScheduleVo();
			if (subList.size() > 0)
			{
				repvo.setInstNo((CommonFunction.checkNull(subList.get(0)).trim()));
				repvo.setDueDate((CommonFunction.checkNull(subList.get(1)).trim()));
				
				if(!CommonFunction.checkNull(subList.get(2)).trim().equalsIgnoreCase(""))
	    		{
		    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());  
		    		repvo.setInstAmount(myFormatter.format(reconNum));
	    		}
				
				if(!CommonFunction.checkNull(subList.get(3)).trim().equalsIgnoreCase(""))
	    		{
		    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(3))).trim());  
		    		repvo.setPrinciple(myFormatter.format(reconNum));
	    		}
	
				if(!CommonFunction.checkNull(subList.get(4)).trim().equalsIgnoreCase(""))
	    		{
		    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(4))).trim());  
		    		repvo.setInstCom(myFormatter.format(reconNum));
	    		}
	
				if(!CommonFunction.checkNull(subList.get(5)).trim().equalsIgnoreCase(""))
	    		{
		    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(5))).trim());  
		    		repvo.setExcess(myFormatter.format(reconNum));
	    		}
			}
			repvo.setAdvFlag((CommonFunction.checkNull(subList.get(6)).trim()));
			if(!CommonFunction.checkNull(subList.get(7)).trim().equalsIgnoreCase(""))
	    	{
		    	Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(7))).trim());  
		    	repvo.setPrinOS(myFormatter.format(reconNum));
	    	}
			repvo.setDealIRR2(bussIrr);
			list.add(repvo);
		}
		bussIrrQ=null;
		query=null;
		
	} 
	catch (Exception e) 
	{e.printStackTrace();}
	finally{
		loanId=null;
		reschId=null;
		vo=null;
		mainlist.clear();
		mainlist=null;
		subList.clear();
		subList=null;
	}
	return list;
}
		
		public ArrayList<LoanDetailForCMVO> getLoanListOfProduct(int dealno) {

			ArrayList<LoanDetailForCMVO> loanDetailList = new ArrayList<LoanDetailForCMVO>();
			
			ArrayList mainlist = new ArrayList();
			ArrayList subList = new ArrayList();
			StringBuilder query = new StringBuilder(); 
			LoanDetailForCMVO loanvo = null;
			try {

				
				query.append("select DEAL_ID,s.SCHEME_DESC,p.PRODUCT_DESC,DEAL_PRODUCT_CATEGORY,DEAL_SANCTION_AMOUNT, ");
				query.append("DEAL_TENURE,DATE_FORMAT(DEAL_SANCTION_VALID_TILL,'"+dateFormat+"'),DEAL_LOAN_ID,DEAL_UTILIZED_AMOUNT,ifnull(d.deal_credit_period,0)");
				query.append(",DATE_FORMAT(INTEREST_DUE_DATE,'%d-%m-%Y')");
				query.append(" from cr_deal_loan_dtl d, cr_product_m p,cr_scheme_m s where d.DEAL_PRODUCT=p.PRODUCT_ID and d.DEAL_SCHEME=s.SCHEME_ID ");
				query.append("and  DEAL_ID='"+ dealno + "'");
				logger.info("In getLoanListOfProduct query "+query);
				mainlist = ConnectionDAO.sqlSelect(query.toString());
				int size = mainlist.size();
				for (int i = 0; i < size; i++) {
					subList = (ArrayList) mainlist.get(i);
					if (subList.size() > 0) {
						loanvo = new LoanDetailForCMVO();
						loanvo.setDealNo((CommonFunction.checkNull(subList.get(0))).trim());
						loanvo.setScheme((CommonFunction.checkNull(subList.get(1))).trim());
						loanvo.setProduct((CommonFunction.checkNull(subList.get(2))).trim());
						loanvo.setProductType((CommonFunction.checkNull(subList.get(3))).trim());
						
						if(!CommonFunction.checkNull(subList.get(4)).trim().equalsIgnoreCase(""))
	    	    		{
		    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(4))).trim());  
		    	    		loanvo.setSanctionedLoanAmount(myFormatter.format(reconNum));
	    	    		}
										
						loanvo.setSanctionedValidtill((CommonFunction.checkNull(subList.get(6))).trim());
						loanvo.setLoanId((CommonFunction.checkNull(subList.get(7))).trim());
						if(!CommonFunction.checkNull(subList.get(8)).trim().equalsIgnoreCase(""))
	    	    		{
		    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(8))).trim());  
		    	    		loanvo.setUtilizeLoanAmount(myFormatter.format(reconNum));
	    	    		}
						loanvo.setCreditPeriod((CommonFunction.checkNull(subList.get(9))).trim());
						loanvo.setInterestDueDate((CommonFunction.checkNull(subList.get(10))).trim());//add by ajay
						loanDetailList.add(loanvo);
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
			}
			return loanDetailList;
		}
		public ArrayList getloanListForNotePad(CommonLoanVo vo,HttpServletRequest request) {
			{
				ArrayList list=new ArrayList();
				int count=0;
				int startRecordIndex=0;
				int endRecordIndex = no;
//				String userName="";
				//mradul changes
				
				
				StringBuilder dealId = new StringBuilder(); 
				StringBuilder lonaNo = new StringBuilder(); 
				StringBuilder aDate = new StringBuilder(); 
				StringBuilder productId = new StringBuilder(); 
				StringBuilder custName = new StringBuilder(); 
				StringBuilder scheme = new StringBuilder(); 
//				String dealId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo())).trim());
//				String lonaNo = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID())).trim());
//				String aDate = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAgrementDate())).trim());
//				String custName = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName())).trim());
//				String productId = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID())).trim());
//				String scheme = (StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme())).trim());
				
				
				dealId.append((StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo())).trim()));
				lonaNo.append((StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID())).trim()));
				aDate.append((StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAgrementDate())).trim()));
				custName.append((StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName())).trim()));
				productId.append((StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID())).trim()));
				 scheme.append((StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme())).trim()));
				
				
//				logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+vo.getReportingToUserId());
//				try{
//					String userNameQ="select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+vo.getReportingToUserId()+"'";
//					userName=ConnectionDAO.singleReturn(userNameQ);
//					logger.info("userNameQ: "+userNameQ+" userName: "+userName);
//				}
//				catch(Exception e)
//				{
//					e.printStackTrace();
//				}

				
				try
				{
					ArrayList header=null;
					CommonLoanVo fetchVo= null;
					boolean appendSQL=false;
					StringBuilder bufInsSql=new StringBuilder();
					StringBuilder bufInsSqlTempCount = new StringBuilder();

					bufInsSql.append(" select distinct d.LOAN_ID,d.LOAN_NO,DATE_FORMAT(d.LOAN_AGREEMENT_DATE,'"+dateFormat+"'),g.CUSTOMER_NAME,p.PRODUCT_DESC, s.SCHEME_DESC, DATE_FORMAT(dl.DEAL_SANCTION_VALID_TILL,'"+dateFormat+"') from cr_loan_dtl d");
					bufInsSqlTempCount.append("SELECT distinct COUNT(1) FROM cr_loan_dtl d ");
					
					bufInsSql.append(" left join gcd_customer_m g on g.CUSTOMER_ID=d.LOAN_CUSTOMER_ID ");
					bufInsSqlTempCount.append(" left join gcd_customer_m g on g.CUSTOMER_ID=d.LOAN_CUSTOMER_ID ");
					bufInsSql.append(" left join cr_product_m p on d.LOAN_PRODUCT=p.PRODUCT_ID  ");
					bufInsSqlTempCount.append(" left join cr_product_m p on d.LOAN_PRODUCT=p.PRODUCT_ID  ");
					bufInsSql.append(" left join cr_scheme_m s on d.LOAN_SCHEME=s.SCHEME_ID ");
					bufInsSqlTempCount.append(" left join cr_scheme_m s on d.LOAN_SCHEME=s.SCHEME_ID ");
					bufInsSql.append(" left join cr_deal_loan_dtl dl on d.LOAN_DEAL_ID=dl.DEAL_ID AND dl.deal_loan_id=d.loan_deal_loan_id ");
					bufInsSqlTempCount.append(" left join cr_deal_loan_dtl dl on d.LOAN_DEAL_ID=dl.DEAL_ID AND dl.deal_loan_id=d.loan_deal_loan_id ");
					
					
						if((!(vo.getLbxDealNo().equalsIgnoreCase("")))&&(!(vo.getLbxLoanNoHID().equalsIgnoreCase("")))&&(!(vo.getAgrementDate().equalsIgnoreCase("")))&&(!(vo.getCustomerName().equalsIgnoreCase("")))&&(!(vo.getLbxProductID().equalsIgnoreCase("")))&&(!(vo.getLbxscheme().equalsIgnoreCase(""))))
						{
					   	  bufInsSql.append("WHERE  D.LOAN_BRANCH='"+vo.getBranchId()+"' AND d.LOAN_DEAL_ID='"+vo.getLbxDealNo()+"' AND d.LOAN_ID='"+vo.getLbxLoanNoHID()+"'AND d.LOAN_AGREEMENT_DATE=STR_TO_DATE('"+vo.getAgrementDate()+"','"+dateFormat+"') AND g.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomerName())+"%' AND d.LOAN_PRODUCT='"+vo.getLbxProductID()+"'AND d.LOAN_SCHEME='"+vo.getLbxscheme()+"' AND");
					   	  bufInsSqlTempCount.append("WHERE  D.LOAN_BRANCH='"+vo.getBranchId()+"' AND d.LOAN_DEAL_ID='"+vo.getLbxDealNo()+"' AND d.LOAN_ID='"+vo.getLbxLoanNoHID()+"'AND d.LOAN_AGREEMENT_DATE=STR_TO_DATE('"+vo.getAgrementDate()+"','"+dateFormat+"') AND g.CUSTOMER_NAME like'%"+vo.getCustomerName()+"%' AND d.LOAN_PRODUCT='"+vo.getLbxProductID()+"'AND d.LOAN_SCHEME='"+vo.getLbxscheme()+"' AND");
						}
					   		
						if(((vo.getLbxDealNo().equalsIgnoreCase("")))||((vo.getLbxLoanNoHID().equalsIgnoreCase("")))||((vo.getAgrementDate().equalsIgnoreCase("")))||((vo.getCustomerName().equalsIgnoreCase("")))||((vo.getLbxProductID().equalsIgnoreCase("")))||((vo.getLbxscheme().equalsIgnoreCase("")))||((vo.getStage().equalsIgnoreCase("")))){
							appendSQL=true;
						}
						
						if(appendSQL){
							logger.info("In Where Clause");
							bufInsSql.append(" WHERE  D.LOAN_BRANCH='"+vo.getBranchId()+"' AND");
							bufInsSqlTempCount.append(" WHERE  D.LOAN_BRANCH='"+vo.getBranchId()+"' AND");
						}
											
					if((!(vo.getLbxDealNo().equalsIgnoreCase("")))) {
				        bufInsSql.append(" d.LOAN_DEAL_ID='"+vo.getLbxDealNo()+"' AND");
				        bufInsSqlTempCount.append(" d.LOAN_DEAL_ID='"+vo.getLbxDealNo()+"' AND");
				   	 appendSQL=true;
				   	  
				     }
					
					 if((!(vo.getLbxLoanNoHID().equalsIgnoreCase("")))) {
				        bufInsSql.append(" d.LOAN_ID='"+vo.getLbxLoanNoHID()+"' AND");
				        bufInsSqlTempCount.append(" d.LOAN_ID='"+vo.getLbxLoanNoHID()+"' AND");
				   	 appendSQL=true;
				   	  
				     }
					 
								
					if((!(vo.getAgrementDate().equalsIgnoreCase("")))) {
				   	  bufInsSql.append(" d.LOAN_AGREEMENT_DATE =STR_TO_DATE('"+vo.getAgrementDate()+"','"+dateFormatWithTime+"') AND");
				   	  bufInsSqlTempCount.append(" d.LOAN_AGREEMENT_DATE =STR_TO_DATE('"+vo.getAgrementDate()+"','"+dateFormatWithTime+"') AND");
				   	  appendSQL=true;
				     }
					
					if((!(vo.getCustomerName().equalsIgnoreCase("")))) {
				   	  bufInsSql.append(" g.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomerName())+"%' AND");
				   	  bufInsSqlTempCount.append(" g.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomerName())+"%' AND");
				   	  appendSQL=true;
				     }
					if((!(vo.getLbxProductID().equalsIgnoreCase("")))) {
					   	  bufInsSql.append(" d.LOAN_PRODUCT='"+vo.getLbxProductID()+"' AND");
					   	  bufInsSqlTempCount.append(" d.LOAN_PRODUCT='"+vo.getLbxProductID()+"' AND");
					   	  appendSQL=true;
					     }
					if((!(vo.getLbxscheme().equalsIgnoreCase("")))) {
					   	  bufInsSql.append(" d.LOAN_SCHEME='"+vo.getLbxscheme()+"' AND");
					   	  bufInsSqlTempCount.append(" d.LOAN_SCHEME='"+vo.getLbxscheme()+"' AND");
					   	  appendSQL=true;
					     }
					
					logger.info("In appendSQL true---- "+appendSQL);
							
					if(appendSQL){
						logger.info("In appendSQL true---- ");
						String  tmp = bufInsSql.toString();
						String tmp1 = bufInsSqlTempCount.toString();
						
				  //   logger.info("In getloanListForNotePad() ## tmp ## "+tmp);
				   //  logger.info("In appendSQL true----  in check index Of"+tmp.lastIndexOf("AND") +"------"+(tmp.length()-3));
				   
				     if(tmp.lastIndexOf("AND")== (tmp.length()-3) || tmp1.lastIndexOf("AND")==(tmp1.length()-3)){
				   //  logger.info("In appendSQL true----  in check index Of");
				     tmp = (tmp).substring(0,(tmp.length()-4));
				     tmp1 = (tmp1).substring(0,(tmp1.length()-4));
				//     logger.info("getloanListForNotePad Query...tmp."+tmp);
				     header = ConnectionDAO.sqlSelect(tmp);
				     count =Integer.parseInt(ConnectionDAO.singleReturn(tmp1.toString()));
				     
				      }else
				      {
				   //  	  logger.info("getloanListForNotePad Query...tmp."+tmp);
				     	 header = ConnectionDAO.sqlSelect(tmp); 
				     	 count =Integer.parseInt(ConnectionDAO.singleReturn(tmp1.toString()));
				       }
					 }else {
				    	  
							LoggerMsg.info("search Query...else-------." + bufInsSql);
							LoggerMsg.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
				            
							count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
							
							
							if((dealId.toString().trim()==null && lonaNo.toString().trim()==null && aDate.toString().trim()==null && custName.toString().trim()==null && productId.toString().trim()==null && scheme.toString().trim()==null) || (dealId.toString().trim().equalsIgnoreCase("") && lonaNo.toString().trim().equalsIgnoreCase("") && aDate.toString().trim().equalsIgnoreCase("") && custName.toString().trim().equalsIgnoreCase("") && productId.toString().trim().equalsIgnoreCase("") && scheme.toString().trim().equalsIgnoreCase("")) || vo.getCurrentPageLink()>1)
							{
							
							LoggerMsg.info("current PAge Link no .................... "+vo.getCurrentPageLink());
							if(vo.getCurrentPageLink()>1)
							{
								startRecordIndex = (vo.getCurrentPageLink()-1)*no;
								endRecordIndex = no;
								LoggerMsg.info("startRecordIndex .................... "+startRecordIndex);
								LoggerMsg.info("endRecordIndex .................... "+endRecordIndex);
							}
							
							bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
							
							}
							LoggerMsg.info("query : "+bufInsSql);
							
							header = ConnectionDAO.sqlSelect(bufInsSql.toString());
						}

								
					for(int i=0;i<header.size();i++){
						logger.info("header"+header.size());
						ArrayList header1=(ArrayList)header.get(i);
						if(header1.size()>0)
						{
							fetchVo = new CommonLoanVo();
							fetchVo.setModifyNo("<a href=notePadeBehindInCMAction.do?loanId="
							+ CommonFunction.checkNull(header1.get(0)).trim().toString()+ "+&loanStatus=" 
							+ ">"
							+ CommonFunction.checkNull(header1.get(1)).trim().toString() + "</a>");
							
							fetchVo.setLbxLoanNoHID((CommonFunction.checkNull(header1.get(0)).trim()));
							fetchVo.setLoanNo((CommonFunction.checkNull(header1.get(1)).trim()));
							fetchVo.setAgrementDate((CommonFunction.checkNull(header1.get(2)).trim()));
							fetchVo.setCustomerName((CommonFunction.checkNull(header1.get(3)).trim()));
							fetchVo.setProduct((CommonFunction.checkNull(header1.get(4)).trim()));
							fetchVo.setScheme((CommonFunction.checkNull(header1.get(5)).trim()));
							fetchVo.setValidSancTill((CommonFunction.checkNull(header1.get(6)).trim()));
							fetchVo.setTotalRecordSize(count);
//							fetchVo.setReportingToUserId(userName);
							list.add(fetchVo);
						}
					}
					/*if( header.size()==0)
					{
						CommonLoanVo fetchMVo = new CommonLoanVo();
						fetchMVo.setTotalRecordSize(count);
						list.add(fetchMVo);
						request.setAttribute("flag","yes");
						//LoggerMsg.info("getTotalRecordSize : "+fetchMVo.getTotalRecordSize());
					}*/
					bufInsSql=null;
					bufInsSqlTempCount=null;
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
					dealId=null;
					lonaNo=null;
					aDate=null;
					productId=null;
					custName=null;
					scheme=null;
					
				}

				return list;
			}
		}
		
		
		public boolean insertUpdateSupManfCust(LoanDetailForCMVO loanDetailForCMVO){
			boolean status = false;
			ArrayList queryList1 = new ArrayList();
			ArrayList queryList7 = new ArrayList();
			ArrayList queryList6 = new ArrayList();
			
			StringBuilder query1 = new StringBuilder();
			StringBuilder query2 = new StringBuilder();
			
			
			StringBuilder query5 = new StringBuilder();
			StringBuilder query6 = new StringBuilder();
			StringBuilder query7 = new StringBuilder();
			String count="";
			try {
				logger.info("insertUpdateSupManfCust in DAO: ");
				logger.info("loan id  dao:::::::::::::::::::::: "+loanDetailForCMVO.getLoanId());
				String loanCustomerId="";
				String q="SELECT LOAN_CUSTOMER_ID FROM CR_LOAN_DTL A, GCD_CUSTOMER_M B WHERE A.REC_STATUS='P' AND A.LOAN_ID = "+CommonFunction.checkNull(loanDetailForCMVO.getLoanId()).trim()+" AND A.LOAN_CUSTOMER_ID = B.CUSTOMER_ID";
				logger.info("Query  to get CustomerID  :  "+q);
				loanCustomerId =ConnectionDAO.singleReturn(q);
				
				logger.info("loanCustomerId:::::::"+loanCustomerId+":::::loan_id::::"+CommonFunction.checkNull(loanDetailForCMVO.getLoanId()).trim());
					

				
				query1.append("SELECT COUNT(1) FROM BUSINESS_PARTNER_VIEW WHERE LOAN_ID = "+CommonFunction.checkNull(loanDetailForCMVO.getLoanId()).trim()+" AND BP_TYPE = 'CS' AND BP_ID = "+CommonFunction.checkNull(loanCustomerId).trim()+"");
				count = ConnectionDAO.singleReturn(query1.toString());
				
				if(Integer.parseInt(count)==0)
				{
					query5.append("INSERT INTO BUSINESS_PARTNER_VIEW (DEAL_ID, LOAN_ID, BP_TYPE, BP_ID, BP_NAME) VALUES("+CommonFunction.checkNull(loanDetailForCMVO.getLbxDealNo()).trim()+", "+CommonFunction.checkNull(loanDetailForCMVO.getLoanId()).trim()+", 'CS', "+CommonFunction.checkNull(loanCustomerId).trim()+", '"+CommonFunction.checkNull(loanDetailForCMVO.getCustomerName()).trim()+"')");
					queryList1.add(query5.toString());
					logger.info("In insertUpdateSupManfCust:-" + query5);
					status = ConnectionDAO.sqlInsUpdDelete(queryList1);
				}
				
				
				String checkAdvice="select count(*) FROM cr_txnadvice_dtl WHERE loan_id="+CommonFunction.checkNull(loanDetailForCMVO.getLoanId()).trim()+" AND ADVICE_AMOUNT>0 AND TXN_ADJUSTED_AMOUNT>0";
			    logger.info("checkAdvice Query: "+checkAdvice);
			    String checkAdviceCount=ConnectionDAO.singleReturn(checkAdvice);
				logger.info("checkAdviceCount: "+checkAdviceCount);
				
				
			if( Integer.parseInt(checkAdviceCount) <= 0)
			{
				
				StringBuilder bufInsSql1 = new StringBuilder();
				bufInsSql1.append("DELETE FROM BUSINESS_PARTNER_VIEW WHERE LOAN_ID = "+CommonFunction.checkNull(loanDetailForCMVO.getLoanId()).trim()+" AND BP_TYPE IN ('SU','MF') ");
						
				logger.info("### bufInsSql1 #### " + bufInsSql1.toString());
				queryList6.add(bufInsSql1.toString());
				status = ConnectionDAO.sqlInsUpdDelete(queryList6);
				bufInsSql1 = null;
				
			}
				ArrayList header=null;
				String assetManufacturer="";
				String suppler="";
				query2.append("SELECT A.ASSET_ID, A.ASSET_MANUFATURER, A.ASSET_SUPPLIER " +
						"FROM CR_ASSET_COLLATERAL_M A, CR_LOAN_COLLATERAL_M B " +
						"WHERE B.LOAN_ID = "+CommonFunction.checkNull(loanDetailForCMVO.getLoanId()).trim()+" AND A.ASSET_ID = B.ASSETID AND A.ASSET_TYPE='ASSET'");
				logger.info("In insertUpdateSupManfCust:query2-" + query2);
				header = ConnectionDAO.sqlSelect(query2.toString());
				
				String dealerDescription="";
				String dealerDesc="";
				
				for(int i=0;i<header.size();i++){
					logger.info("header"+header.size());
					ArrayList header1=(ArrayList)header.get(i);
					if(header1.size()>0)
					{
						
						assetManufacturer = (CommonFunction.checkNull(header1.get(1)).trim());
						suppler = (CommonFunction.checkNull(header1.get(2)).trim());
						StringBuilder query3 = new StringBuilder();
						StringBuilder query4 = new StringBuilder();
						ArrayList queryList4 = new ArrayList();
						ArrayList queryList2 = new ArrayList();
						ArrayList queryList3 = new ArrayList();
						
						ArrayList queryList5 = new ArrayList();
						
						if(!assetManufacturer.equalsIgnoreCase(""))
						{
							dealerDesc="SELECT DEALER_DESC FROM CR_DSA_DEALER_M WHERE DEALER_ID = "+CommonFunction.checkNull(assetManufacturer).trim()+" AND BP_TYPE = 'MF' AND REC_STATUS = 'A'";
						    logger.info("dealerDesc Query: "+dealerDesc);
						    dealerDescription=ConnectionDAO.singleReturn(dealerDesc);
							logger.info("dealerDescription for manufacture: "+dealerDescription);
								
								String manufacExistQuery="SELECT COUNT(*) FROM BUSINESS_PARTNER_VIEW WHERE LOAN_ID = "+CommonFunction.checkNull(loanDetailForCMVO.getLoanId()).trim()+" AND BP_TYPE = 'MF' AND BP_ID = "+CommonFunction.checkNull(assetManufacturer).trim()+"";
							    logger.info("manufacExistQuery Query: "+manufacExistQuery);
							    String manufacExistCount=ConnectionDAO.singleReturn(manufacExistQuery);
								logger.info("manufacExistCount: "+dealerDescription);
								int manuFacExt =Integer.parseInt(manufacExistCount);
								
							if( manuFacExt <= 0)
							{
								logger.info("loanDetailForCMVO.getLbxDealNo() " + loanDetailForCMVO.getLbxDealNo());
								logger.info("loanDetailForCMVO.getLoanId() " + loanDetailForCMVO.getLoanId());
								logger.info("assetManufacturer " + assetManufacturer);
								logger.info("dealerDescription " + dealerDescription);
								
								query3.append(" INSERT INTO BUSINESS_PARTNER_VIEW (DEAL_ID, LOAN_ID, BP_TYPE, BP_ID, BP_NAME) VALUES('"+CommonFunction.checkNull(loanDetailForCMVO.getLbxDealNo()).trim()+"', '"+CommonFunction.checkNull(loanDetailForCMVO.getLoanId()).trim()+"', 'MF', '"+CommonFunction.checkNull(assetManufacturer).trim()+"', '"+CommonFunction.checkNull(dealerDescription).trim()+"') ");
								queryList2.add(query3.toString());
								logger.info("In insertUpdateSupManfCust:-" + query3);
								status = ConnectionDAO.sqlInsUpdDelete(queryList2);
								query3=null;
							}
						}
						
						if(!suppler.equalsIgnoreCase(""))
						{
						    dealerDesc="SELECT DEALER_DESC FROM CR_DSA_DEALER_M WHERE DEALER_ID = "+CommonFunction.checkNull(suppler).trim()+" AND BP_TYPE = 'SU' AND REC_STATUS = 'A'";
						    logger.info("dealerDesc Query: "+dealerDesc);
						    dealerDescription=ConnectionDAO.singleReturn(dealerDesc);
							logger.info("dealerDescription: "+dealerDescription);
							

							String suppExistQuery="SELECT COUNT(*) FROM BUSINESS_PARTNER_VIEW WHERE LOAN_ID = "+CommonFunction.checkNull(loanDetailForCMVO.getLoanId()).trim()+" AND BP_TYPE = 'SU' AND BP_ID = "+CommonFunction.checkNull(suppler).trim()+"";
						    logger.info("manufacExistQuery Query: "+suppExistQuery);
						    String suppExistCount=ConnectionDAO.singleReturn(suppExistQuery);
							logger.info("suppExistQuery: "+dealerDescription);
							
						if(Integer.parseInt(suppExistCount)<=0)
						{
							query4.append("INSERT INTO BUSINESS_PARTNER_VIEW (DEAL_ID, LOAN_ID, BP_TYPE, BP_ID, BP_NAME) VALUES("+CommonFunction.checkNull(loanDetailForCMVO.getLbxDealNo()).trim()+", "+CommonFunction.checkNull(loanDetailForCMVO.getLoanId()).trim()+", 'SU', "+CommonFunction.checkNull(suppler).trim()+", '"+CommonFunction.checkNull(dealerDescription).trim()+"')");
							queryList3.add(query4.toString());
							logger.info("In insertUpdateSupManfCust:-" + query4);
							status = ConnectionDAO.sqlInsUpdDelete(queryList3);
							query4=null;
						}
						
						}
						
						queryList2 = null;
						 queryList3 = null;
						 queryList4 = null;
						 queryList5 = null;
						 query3 = null;
						 query4 = null;
					}
				}
				query6.append("SELECT COUNT(*) FROM BUSINESS_PARTNER_VIEW WHERE LOAN_ID = "+CommonFunction.checkNull(loanDetailForCMVO.getLoanId()).trim()+" AND BP_TYPE = 'OTH' ");
				count = ConnectionDAO.singleReturn(query6.toString());
				
				if(Integer.parseInt(count)<=0)
				{
					query7.append("INSERT INTO BUSINESS_PARTNER_VIEW (DEAL_ID, LOAN_ID, BP_TYPE, BP_ID, BP_NAME) VALUES("+CommonFunction.checkNull(loanDetailForCMVO.getLbxDealNo()).trim()+", "+CommonFunction.checkNull(loanDetailForCMVO.getLoanId()).trim()+", 'OTH', "+CommonFunction.checkNull(loanDetailForCMVO.getLoanId()).trim()+", NULL )");
					queryList7.add(query7.toString());
					logger.info("In insertUpdateSupManfCustForOTH:-" + query7);
					status = ConnectionDAO.sqlInsUpdDelete(queryList7);
				}
			} 
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			finally
			{
				 queryList7 = null;
				 
				
				 query1 = null;
				 query2 = null;
				
				 query5 = null;
			}
			return status;
		}
		
		public ArrayList<Object> fetchLoanDetailForDeviation(CommonLoanVo vo) {
			ArrayList list=new ArrayList();
			int count=0;
			int startRecordIndex=0;
			int endRecordIndex = no;
//			String userName="";
			StringBuilder userName=new StringBuilder();
			
			logger.info("here fetchLoanDetailForDeviation userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+vo.getReportingToUserId());
			try{
				StringBuilder userNameQ=new StringBuilder();
				 userNameQ.append("select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+vo.getReportingToUserId()+"'");
				userName.append(ConnectionDAO.singleReturn(userNameQ.toString()));
				logger.info("userNameQ: "+userNameQ+" userName: "+userName);
				userNameQ=null;
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			

			StringBuilder dealId=new StringBuilder();
			StringBuilder lonaNo=new StringBuilder();
			StringBuilder aDate=new StringBuilder();
			StringBuilder custName=new StringBuilder();
			StringBuilder productId=new StringBuilder();
			StringBuilder scheme=new StringBuilder();
			
			dealId.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo())).trim());
		    lonaNo.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID())).trim());
			aDate.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAgrementDate())).trim());
		custName.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName())).trim());
			 productId.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID())).trim());
			 scheme.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme())).trim());
		
			 
			try
			{
							    
				ArrayList header=null;
				CommonLoanVo fetchVo= null;
				boolean appendSQL=false;
				StringBuffer bufInsSql=new StringBuffer();
				StringBuffer bufInsSqlTempCount = new StringBuffer();
		 
				bufInsSql.append(" select distinct d.LOAN_ID,d.LOAN_NO,DATE_FORMAT(d.LOAN_AGREEMENT_DATE,'"+dateFormat+"'),g.CUSTOMER_NAME,p.PRODUCT_DESC, s.SCHEME_DESC, DATE_FORMAT(dl.DEAL_SANCTION_VALID_TILL,'"+dateFormat+"'),(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=D.MAKER_ID) MAKER_ID from cr_loan_dtl d");
				bufInsSqlTempCount.append("SELECT distinct COUNT(1) FROM cr_loan_dtl d ");
				
				bufInsSql.append(" left join gcd_customer_m g on g.CUSTOMER_ID=d.LOAN_CUSTOMER_ID ");
				bufInsSqlTempCount.append(" left join gcd_customer_m g on g.CUSTOMER_ID=d.LOAN_CUSTOMER_ID ");
				
				bufInsSql.append(" left join cr_product_m p on d.LOAN_PRODUCT=p.PRODUCT_ID  ");
				bufInsSqlTempCount.append(" left join cr_product_m p on d.LOAN_PRODUCT=p.PRODUCT_ID  ");
				
				bufInsSql.append(" left join cr_scheme_m s on d.LOAN_SCHEME=s.SCHEME_ID ");
				bufInsSqlTempCount.append(" left join cr_scheme_m s on d.LOAN_SCHEME=s.SCHEME_ID ");
				
				bufInsSql.append(" left join cr_deal_loan_dtl dl on d.LOAN_DEAL_ID=dl.DEAL_ID AND dl.deal_loan_id=d.loan_deal_loan_id ");
				bufInsSqlTempCount.append(" left join cr_deal_loan_dtl dl on d.LOAN_DEAL_ID=dl.DEAL_ID  AND dl.deal_loan_id=d.loan_deal_loan_id ");
				
				bufInsSql.append(" inner join cr_loan_deviation_dtl deviation on deviation.LOAN_ID=d.LOAN_ID AND deviation.REC_STATUS='F'");
				bufInsSqlTempCount.append(" inner join cr_loan_deviation_dtl deviation on deviation.LOAN_ID=d.LOAN_ID AND deviation.REC_STATUS='F' ");
				
				bufInsSql.append(" inner join cr_user_approval_m ua on  ua.USER_ID='"+vo.getReportingToUserId()+"' and ua.USER_ROLE='P' and ua.rec_status='A'");
				bufInsSqlTempCount.append(" inner join cr_user_approval_m ua on  ua.USER_ID='"+vo.getReportingToUserId()+"' and ua.USER_ROLE='P' and ua.rec_status='A' ");
				
			
					if((!(vo.getLbxDealNo().equalsIgnoreCase("")))&&(!(vo.getLbxLoanNoHID().equalsIgnoreCase("")))&&(!(vo.getAgrementDate().equalsIgnoreCase("")))&&(!(vo.getCustomerName().equalsIgnoreCase("")))&&(!(vo.getLbxProductID().equalsIgnoreCase("")))&&(!(vo.getLbxscheme().equalsIgnoreCase(""))))
					{
				   	  bufInsSql.append("WHERE  D.LOAN_BRANCH='"+vo.getBranchId()+"' AND d.LOAN_DEAL_ID='"+vo.getLbxDealNo()+"' AND d.LOAN_ID='"+vo.getLbxLoanNoHID()+"' AND  deviation.APPROVAL_LEVEL=ua.LEVEL and d.MAKER_ID<>'"+vo.getUserId()+"' AND d.LOAN_AGREEMENT_DATE=STR_TO_DATE('"+vo.getAgrementDate()+"','"+dateFormat+"') AND g.CUSTOMER_NAME like'%"+vo.getCustomerName()+"%' AND d.LOAN_PRODUCT='"+vo.getLbxProductID()+"'AND d.LOAN_SCHEME='"+vo.getLbxscheme()+"' AND d.REC_STATUS='"+vo.getStage()+"'");
				   	  bufInsSqlTempCount.append("WHERE  D.LOAN_BRANCH='"+vo.getBranchId()+"' AND d.LOAN_DEAL_ID='"+vo.getLbxDealNo()+"' AND d.LOAN_ID='"+vo.getLbxLoanNoHID()+"' AND  deviation.APPROVAL_LEVEL=ua.LEVEL and d.MAKER_ID<>'"+vo.getUserId()+"' AND d.LOAN_AGREEMENT_DATE=STR_TO_DATE('"+vo.getAgrementDate()+"','"+dateFormat+"') AND g.CUSTOMER_NAME like'%"+vo.getCustomerName()+"%' AND d.LOAN_PRODUCT='"+vo.getLbxProductID()+"'AND d.LOAN_SCHEME='"+vo.getLbxscheme()+"' AND d.REC_STATUS='"+vo.getStage()+"'");
					}
					
					if(((vo.getLbxDealNo().equalsIgnoreCase("")))||((vo.getLbxLoanNoHID().equalsIgnoreCase("")))||((vo.getAgrementDate().equalsIgnoreCase("")))||((vo.getCustomerName().equalsIgnoreCase("")))||((vo.getLbxProductID().equalsIgnoreCase("")))||((vo.getLbxscheme().equalsIgnoreCase("")))||((vo.getStage().equalsIgnoreCase("")))){
						appendSQL=true;
					}
					
					if(appendSQL){
						logger.info("In Where Clause");
						bufInsSql.append(" WHERE  D.LOAN_BRANCH='"+vo.getBranchId()+"' AND d.REC_STATUS='"+vo.getStage()+"' AND  deviation.APPROVAL_LEVEL=ua.LEVEL and d.MAKER_ID<>'"+vo.getUserId()+"'");
						bufInsSqlTempCount.append(" WHERE  D.LOAN_BRANCH='"+vo.getBranchId()+"' AND d.REC_STATUS='"+vo.getStage()+"' AND  deviation.APPROVAL_LEVEL=ua.LEVEL and d.MAKER_ID<>'"+vo.getUserId()+"'");
					}
				
				if((!(vo.getLbxDealNo().equalsIgnoreCase("")))) {
			        bufInsSql.append(" and d.LOAN_DEAL_ID='"+vo.getLbxDealNo()+"'");
			        bufInsSqlTempCount.append(" and d.LOAN_DEAL_ID='"+vo.getLbxDealNo()+"'");
			   	 appendSQL=true;
			   	  
			     }
				
				 if((!(vo.getLbxLoanNoHID().equalsIgnoreCase("")))) {
			        bufInsSql.append("and  d.LOAN_ID='"+vo.getLbxLoanNoHID()+"'");
			        bufInsSqlTempCount.append("and  d.LOAN_ID='"+vo.getLbxLoanNoHID()+"'");
			   	 appendSQL=true;
			   	  
			     }
				 
							
				if((!(vo.getAgrementDate().equalsIgnoreCase("")))) {
			   	  bufInsSql.append("and  d.LOAN_AGREEMENT_DATE =STR_TO_DATE('"+vo.getAgrementDate()+"','"+dateFormatWithTime+"') ");
			   	  bufInsSqlTempCount.append("and  d.LOAN_AGREEMENT_DATE =STR_TO_DATE('"+vo.getAgrementDate()+"','"+dateFormatWithTime+"') ");
			   	  appendSQL=true;
			     }
				
				if((!(vo.getCustomerName().equalsIgnoreCase("")))) {
			   	  bufInsSql.append("and  g.CUSTOMER_NAME like'%"+vo.getCustomerName()+"%'");
			   	  bufInsSqlTempCount.append("and  g.CUSTOMER_NAME like'%"+vo.getCustomerName()+"%'");
			   	  appendSQL=true;
			     }
				if((!(vo.getLbxProductID().equalsIgnoreCase("")))) {
				   	  bufInsSql.append("and  d.LOAN_PRODUCT='"+vo.getLbxProductID()+"'");
				   	  bufInsSqlTempCount.append("and  d.LOAN_PRODUCT='"+vo.getLbxProductID()+"'");
				   	  appendSQL=true;
				     }
				if((!(vo.getLbxscheme().equalsIgnoreCase("")))) {
				   	  bufInsSql.append("and  d.LOAN_SCHEME='"+vo.getLbxscheme()+"'");
				   	  bufInsSqlTempCount.append("and  d.LOAN_SCHEME='"+vo.getLbxscheme()+"'");
				   	  appendSQL=true;
				     }

				logger.info("In appendSQL true---- "+appendSQL);
				
				if(appendSQL){
					logger.info("In appendSQL true---- ");
					String  tmp = bufInsSql.toString();
					String tmp1 = bufInsSqlTempCount.toString();
					
			     logger.info("In fetchLoanDetailForDeviation() ## tmp ## "+tmp);
			     logger.info("In appendSQL true----  in check index Of"+tmp.lastIndexOf("AND") +"------"+(tmp.length()-3));
			   
			     if(tmp.lastIndexOf("AND")== (tmp.length()-3) || tmp1.lastIndexOf("AND")==(tmp1.length()-3))
			     {
			     logger.info("In appendSQL true----  in check index Of");
			     tmp = (tmp).substring(0,(tmp.length()-4));
			     tmp1 = (tmp1).substring(0,(tmp1.length()-4));
			     logger.info("fetchLoanDetailForDeviation Query...tmp."+tmp);
			     header = ConnectionDAO.sqlSelect(tmp);
			     count =Integer.parseInt(ConnectionDAO.singleReturn(tmp1.toString()));
			     
			      }else
			      {
			     	  logger.info("fetchLoanDetailForDeviation Query...tmp."+tmp);
			     	 header = ConnectionDAO.sqlSelect(tmp); 
			     	 count =Integer.parseInt(ConnectionDAO.singleReturn(tmp1.toString()));
			       }
				 }else {
			    	  
						LoggerMsg.info("search Query...else-------." + bufInsSql);
						LoggerMsg.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
			            
						count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
						
						
						if((dealId.toString().trim()==null && lonaNo.toString().trim()==null && aDate.toString().trim()==null && custName.toString().trim()==null && productId.toString().trim()==null && scheme.toString().trim()==null) || (dealId.toString().trim().equalsIgnoreCase("") && lonaNo.toString().trim().equalsIgnoreCase("") && aDate.toString().trim().equalsIgnoreCase("") && custName.toString().trim().equalsIgnoreCase("") && productId.toString().trim().equalsIgnoreCase("") && scheme.toString().trim().equalsIgnoreCase("")) || vo.getCurrentPageLink()>1)
						{
						
						LoggerMsg.info("current PAge Link no .................... "+vo.getCurrentPageLink());
						if(vo.getCurrentPageLink()>1)
						{
							startRecordIndex = (vo.getCurrentPageLink()-1)*no;
							endRecordIndex = no;
							LoggerMsg.info("startRecordIndex .................... "+startRecordIndex);
							LoggerMsg.info("endRecordIndex .................... "+endRecordIndex);
						}
						
						bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
						
						}
						LoggerMsg.info("query : "+bufInsSql);
						
						header = ConnectionDAO.sqlSelect(bufInsSql.toString());
					}

							
				for(int i=0;i<header.size();i++){
					logger.info("header"+header.size());
					ArrayList header1=(ArrayList)header.get(i);
					if(header1.size()>0)
					{
						fetchVo = new CommonLoanVo();
						fetchVo.setModifyNo("<a href=loanInitBehindAction.do?method=loanInitMakerAuthor&loanId="
						+ CommonFunction.checkNull(header1.get(0)).trim().toString()+ "+&loanStatus=" 
						+ ">"
						+ CommonFunction.checkNull(header1.get(1)).trim().toString() + "</a>");
						
						fetchVo.setLbxLoanNoHID((CommonFunction.checkNull(header1.get(0)).trim()));
						fetchVo.setLoanNo((CommonFunction.checkNull(header1.get(1)).trim()));
						fetchVo.setAgrementDate((CommonFunction.checkNull(header1.get(2)).trim()));
						fetchVo.setCustomerName((CommonFunction.checkNull(header1.get(3)).trim()));
						fetchVo.setProduct((CommonFunction.checkNull(header1.get(4)).trim()));
						fetchVo.setScheme((CommonFunction.checkNull(header1.get(5)).trim()));
						fetchVo.setValidSancTill((CommonFunction.checkNull(header1.get(6)).trim()));
						fetchVo.setReportingToUserId((CommonFunction.checkNull(header1.get(7)).trim()));
						fetchVo.setTotalRecordSize(count);
						list.add(fetchVo);
					}
				}

				bufInsSql=null;
				bufInsSqlTempCount=null;
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	finally
	{
		dealId=null;
		 lonaNo=null;
		 custName=null;
		 productId=null;
		 scheme=null;
		 aDate=null;
		 userName=null;
	}
			return list;
		}

//mradul code for loan init.
		@Override
		public boolean saveDisbursalScheduleFromDeal(String loanId) {
			
			logger.info("In saveDisbursalScheduleFromDeal mod"+loanId);			
			
			ArrayList qryList=new ArrayList();
			ArrayList qryList1=new ArrayList();
			boolean status=false;
			try {
				  StringBuilder	bufInsSql=null;
				  StringBuilder checkQ=null;
				  StringBuilder count =	new StringBuilder();				
		
				  checkQ=new StringBuilder();
				  PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	
					StringBuilder qry=new StringBuilder();
					 qry.append("DELETE FROM cr_loan_disbursalsch_dtl WHERE LOAN_ID='"+(CommonFunction.checkNull(loanId).trim())+ "'");
					 qryList1.add(qry);
					 ConnectionDAO.sqlInsUpdDelete(qryList1);
					 qry=null;
				
				    bufInsSql =	new StringBuilder();
					bufInsSql.append("insert into cr_loan_disbursalsch_dtl(LOAN_ID,DISBURSAL_NO,DISBURSAL_STAGE,DISBURSAL_DESCRIPTION,PROPOSED_DISBURSAL_DATE,PROPOSED_DISBURSAL_AMOUNT,DISBURSAL_FLAG,REC_STATUS,AUTHOR_REMARKS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
					bufInsSql.append("SELECT "+loanId+",DISBURSAL_NO,DISBURSAL_STAGE,DISBURSAL_DESCRIPTION,PROPOSED_DISBURSAL_DATE,PROPOSED_DISBURSAL_AMOUNT,DISBURSAL_FLAG,REC_STATUS,AUTHOR_REMARKS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE FROM cr_deal_disbursalsch_dtl WHERE DEAL_ID=(SELECT LOAN_DEAL_ID FROM CR_LOAN_DTL WHERE LOAN_ID='"+(CommonFunction.checkNull(loanId).trim())+ "' LIMIT 1)");
					
			insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("IN saveDisbursalScheduleFromDeal()del insert query1 ### "+insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			checkQ=null;
			bufInsSql=null;
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			
			checkQ=null;
			bufInsSql=null;
			count=null;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			loanId=null;
			qryList.clear();
			qryList=null;
			qryList1.clear();
			qryList1=null;
		}
		logger.info("In saveDisbursalScheduleFromDeal......................"+status);
		return status;
		}


		public ArrayList getLoanDetailForDisb(String loanId) {
			
			ArrayList<Object> list=new ArrayList<Object>();
			try{
				StringBuilder query=new StringBuilder();
	        query.append("select distinct DATE_FORMAT(LOAN_AGREEMENT_DATE,'"+dateFormat+"'),DATE_FORMAT(LOAN_REPAY_EFF_DATE,'"+dateFormat+"'),LOAN_NUMBER_DISBURSAL,LOAN_LOAN_AMOUNT from cr_loan_dtl L where L.LOAN_ID='"+loanId+"'");
				
			logger.info("getLoanDetailForDisb Queryl: "+query);
			
			NoOfDisbursalVo nVo = null;
			ArrayList disbdeatail = ConnectionDAO.sqlSelect(query.toString());
			//mradul done starts here 
		//	logger.info("getLoanDetailForDisb OUTER ARRAYLIST SIZE: "+disbdeatail.size());
			for(int i=0;i<disbdeatail.size();i++){

			//	logger.info("getLoanDetailForDisb"+disbdeatail.get(i).toString());
				ArrayList disbdeatail1=(ArrayList)disbdeatail.get(i);
				//logger.info("getLoanDetailForDisb INNNER ARRAYLIST SIZE: "+disbdeatail1.size());
				if(disbdeatail1.size()>0)
				{
					nVo = new NoOfDisbursalVo();
					nVo.setLoanAgrementDate((CommonFunction.checkNull(disbdeatail1.get(0))).trim());
					nVo.setRepayEffectiveDate((CommonFunction.checkNull(disbdeatail1.get(1))).trim());
					nVo.setDisbNoInLoan((CommonFunction.checkNull(disbdeatail1.get(2))).trim());
					if(!CommonFunction.checkNull(disbdeatail1.get(3)).equalsIgnoreCase(""))	
		 			{
		 				Number LoanAmount =myFormatter.parse((CommonFunction.checkNull(disbdeatail1.get(3))).trim());
		 				nVo.setLoanAmount(myFormatter.format(LoanAmount));
		 			}
					list.add(nVo);
				}
			}
			nVo=null;
			query=null;
			}catch(Exception e){
				e.printStackTrace();
			}
			finally{
				loanId=null;
			}
			return list;
		}

		public boolean updateDisbTypeAndDisbNo(String loanId, String count) {
			
			
			String updateDisbTypeAndDisbNoQuery="update cr_loan_dtl set LOAN_NUMBER_DISBURSAL='"+count+"' where LOAN_ID='"+loanId+"' ";
	    	logger.info("updateDisbTypeAndDisbNoQuery: "+updateDisbTypeAndDisbNoQuery);
	    	//mradul done changes for loan init.
	    	ArrayList al=new ArrayList();
	    	al.add(updateDisbTypeAndDisbNoQuery);
	    	boolean st=false;
			try {
				st = ConnectionDAO.sqlInsUpdDelete(al);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally{
				loanId=null;
				count=null;
				updateDisbTypeAndDisbNoQuery=null;
				al.clear();
				al=null;
			}
	    	logger.info("updateDisbTypeAndDisbNoQuery status: "+st);
			return st;
		}


		@Override
		public boolean saveDisbursalScheduleDefault(String loanId) {
			
          logger.info("In saveDisbursalScheduleDefault mod"+loanId);
			
			
			ArrayList qryList=new ArrayList();
			ArrayList qryList1=new ArrayList();
			boolean status=false;
			try {
				  StringBuilder	bufInsSql=null;
				
				 
				  StringBuilder checkQ=null;
				  StringBuilder count =	new StringBuilder();
				
		
				checkQ=new StringBuilder();
				PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	
				
					    StringBuilder qry=new StringBuilder();
						 qry.append("DELETE FROM cr_loan_disbursalsch_dtl WHERE LOAN_ID='"+(CommonFunction.checkNull(loanId).trim())+ "'");
						 qryList1.add(qry);
						 ConnectionDAO.sqlInsUpdDelete(qryList1);
						 qry=null;
				
				    bufInsSql =	new StringBuilder();
					bufInsSql.append("insert into cr_loan_disbursalsch_dtl(LOAN_ID,DISBURSAL_NO,DISBURSAL_STAGE,DISBURSAL_DESCRIPTION,PROPOSED_DISBURSAL_DATE,PROPOSED_DISBURSAL_AMOUNT,DISBURSAL_FLAG,REC_STATUS,AUTHOR_REMARKS,MAKER_ID,MAKER_DATE,AUTHOR_ID,AUTHOR_DATE)");
					bufInsSql.append(" VALUES("+loanId+",'1','MM',NULL,(SELECT LOAN_REPAY_EFF_DATE FROM cr_loan_dtl WHERE LOAN_ID='"+(CommonFunction.checkNull(loanId).trim())+ "' LIMIT 1 ),(SELECT LOAN_LOAN_AMOUNT FROM cr_loan_dtl WHERE LOAN_ID='"+(CommonFunction.checkNull(loanId).trim())+ "' LIMIT 1),NULL,'A',NULL,NULL,NULL,NULL,NULL )");
					
			insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("IN saveDisbursalScheduleDefault()del insert query1 ### "+insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			checkQ=null;
			bufInsSql=null;
			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			
			checkQ=null;
			bufInsSql=null;
			count=null;
			
		} catch (Exception e) {
			e.printStackTrace();
			e=null;
		}
		finally{
			loanId=null;
			qryList.clear();
			qryList=null;
			qryList1.clear();
			qryList1=null;
		}
		logger.info("In saveDisbursalScheduleDefault......................"+status);
		return status;
		}
		
		public ArrayList<Object> fetchLoanDetailForDisbursalPlanMaker(CommonLoanVo vo) {
			ArrayList list=new ArrayList();
			int count=0;
			int startRecordIndex=0;
			int endRecordIndex = no;
//			String userName="";
			StringBuilder userName=new StringBuilder();
			
			logger.info("here fetchLoanDetailForDisbursalPlanMaker userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+vo.getReportingToUserId());
			try{
				StringBuilder userNameQ=new StringBuilder();
				 userNameQ.append("select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+vo.getReportingToUserId()+"'");
				userName.append(ConnectionDAO.singleReturn(userNameQ.toString()));
				logger.info("userNameQ: "+userNameQ+" userName: "+userName);
				userNameQ=null;
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			

			StringBuilder dealId=new StringBuilder();
			StringBuilder lonaNo=new StringBuilder();
			StringBuilder aDate=new StringBuilder();
			StringBuilder custName=new StringBuilder();
			StringBuilder productId=new StringBuilder();
			StringBuilder scheme=new StringBuilder();
			
			dealId.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo())).trim());
		    lonaNo.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID())).trim());
			aDate.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAgrementDate())).trim());
		    custName.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName())).trim());
			 productId.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID())).trim());
			 scheme.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme())).trim());
		
			 
			try
			{
							    
				ArrayList header=null;
				CommonLoanVo fetchVo= null;
				boolean appendSQL=false;
				StringBuffer bufInsSql=new StringBuffer();
				StringBuffer bufInsSqlTempCount = new StringBuffer();
		 
				bufInsSql.append(" select distinct d.LOAN_ID,d.LOAN_NO,DATE_FORMAT(d.LOAN_AGREEMENT_DATE,'"+dateFormat+"'),g.CUSTOMER_NAME,p.PRODUCT_DESC, s.SCHEME_DESC, DATE_FORMAT(dl.DEAL_SANCTION_VALID_TILL,'"+dateFormat+"'),(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=D.MAKER_ID) MAKER_ID from cr_loan_dtl d");
				bufInsSqlTempCount.append("SELECT COUNT(1) FROM ( select distinct d.LOAN_ID,d.LOAN_NO,DATE_FORMAT(d.LOAN_AGREEMENT_DATE,'"+dateFormat+"'),g.CUSTOMER_NAME,p.PRODUCT_DESC, s.SCHEME_DESC, DATE_FORMAT(dl.DEAL_SANCTION_VALID_TILL,'"+dateFormat+"'),(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=D.MAKER_ID) MAKER_ID from cr_loan_dtl d ");
				
				bufInsSql.append(" inner join gcd_customer_m g on g.CUSTOMER_ID=d.LOAN_CUSTOMER_ID ");
				bufInsSqlTempCount.append(" inner join gcd_customer_m g on g.CUSTOMER_ID=d.LOAN_CUSTOMER_ID ");
				
				bufInsSql.append(" inner join cr_product_m p on d.LOAN_PRODUCT=p.PRODUCT_ID  ");
				bufInsSqlTempCount.append(" inner join cr_product_m p on d.LOAN_PRODUCT=p.PRODUCT_ID  ");
				
				bufInsSql.append(" inner join cr_scheme_m s on d.LOAN_SCHEME=s.SCHEME_ID ");
				bufInsSqlTempCount.append(" inner join cr_scheme_m s on d.LOAN_SCHEME=s.SCHEME_ID ");
				
				bufInsSql.append(" inner join cr_deal_loan_dtl dl on d.LOAN_DEAL_ID=dl.DEAL_ID  AND dl.deal_loan_id=d.loan_deal_loan_id ");
				bufInsSqlTempCount.append(" inner join cr_deal_loan_dtl dl on d.LOAN_DEAL_ID=dl.DEAL_ID  AND dl.deal_loan_id=d.loan_deal_loan_id ");
				
				//bufInsSql.append(" inner join cr_loan_disbursalsch_dtl disb on disb.LOAN_ID=d.LOAN_ID ");
				//bufInsSqlTempCount.append(" inner join cr_loan_disbursalsch_dtl disb on disb.LOAN_ID=d.LOAN_ID ");
				
				bufInsSql.append(" inner join cr_loan_disbursalsch_temp_dtl temp on temp.LOAN_ID=d.LOAN_ID AND  IFNULL(temp.REC_STATUS,'P') NOT IN ('F')  ");
				bufInsSqlTempCount.append(" inner join cr_loan_disbursalsch_temp_dtl temp on temp.LOAN_ID=d.LOAN_ID AND  IFNULL(temp.REC_STATUS,'P') NOT IN ('F')  ");
				
				
				//bufInsSql.append(" inner join cr_user_approval_m ua on  ua.USER_ID='"+vo.getReportingToUserId()+"' and d.LOAN_LOAN_AMOUNT>=ua.AMOUNT_FROM and d.LOAN_LOAN_AMOUNT<=ua.AMOUNT_TO and ua.USER_ROLE='P' and ua.rec_status='A'");
				//bufInsSqlTempCount.append(" inner join cr_user_approval_m ua on  ua.USER_ID='"+vo.getReportingToUserId()+"' and d.LOAN_LOAN_AMOUNT>=ua.AMOUNT_FROM and d.LOAN_LOAN_AMOUNT<=ua.AMOUNT_TO and ua.USER_ROLE='P' and ua.rec_status='A' ");
				
			
					if((!(vo.getLbxDealNo().equalsIgnoreCase("")))&&(!(vo.getLbxLoanNoHID().equalsIgnoreCase("")))&&(!(vo.getAgrementDate().equalsIgnoreCase("")))&&(!(vo.getCustomerName().equalsIgnoreCase("")))&&(!(vo.getLbxProductID().equalsIgnoreCase("")))&&(!(vo.getLbxscheme().equalsIgnoreCase(""))))
					{
				   	  bufInsSql.append("WHERE  D.LOAN_BRANCH='"+vo.getBranchId()+"' AND d.LOAN_DEAL_ID='"+vo.getLbxDealNo()+"' AND d.LOAN_ID='"+vo.getLbxLoanNoHID()+"' AND d.LOAN_AGREEMENT_DATE=STR_TO_DATE('"+vo.getAgrementDate()+"','"+dateFormat+"') AND g.CUSTOMER_NAME like'%"+vo.getCustomerName()+"%' AND d.LOAN_PRODUCT='"+vo.getLbxProductID()+"'AND d.LOAN_SCHEME='"+vo.getLbxscheme()+"' AND d.REC_STATUS  IN ('F','A') AND d.DISBURSAL_STATUS IN ('N','P') AND D.LOAN_REPAYMENT_TYPE='I' ");
				   	  bufInsSqlTempCount.append("WHERE  D.LOAN_BRANCH='"+vo.getBranchId()+"' AND d.LOAN_DEAL_ID='"+vo.getLbxDealNo()+"' AND d.LOAN_ID='"+vo.getLbxLoanNoHID()+"' AND d.LOAN_AGREEMENT_DATE=STR_TO_DATE('"+vo.getAgrementDate()+"','"+dateFormat+"') AND g.CUSTOMER_NAME like'%"+vo.getCustomerName()+"%' AND d.LOAN_PRODUCT='"+vo.getLbxProductID()+"'AND d.LOAN_SCHEME='"+vo.getLbxscheme()+"' AND d.REC_STATUS  IN ('F','A') AND d.DISBURSAL_STATUS IN ('N','P') AND D.LOAN_REPAYMENT_TYPE='I'");
					}
					
					if(((vo.getLbxDealNo().equalsIgnoreCase("")))||((vo.getLbxLoanNoHID().equalsIgnoreCase("")))||((vo.getAgrementDate().equalsIgnoreCase("")))||((vo.getCustomerName().equalsIgnoreCase("")))||((vo.getLbxProductID().equalsIgnoreCase("")))||((vo.getLbxscheme().equalsIgnoreCase("")))||((vo.getStage().equalsIgnoreCase("")))){
						appendSQL=true;
					}
					
					if(appendSQL){
						logger.info("In Where Clause");
						bufInsSql.append(" WHERE  D.LOAN_BRANCH='"+vo.getBranchId()+"' AND d.REC_STATUS IN ('F','A') AND d.DISBURSAL_STATUS IN ('N','P') AND D.LOAN_REPAYMENT_TYPE='I'");
						bufInsSqlTempCount.append(" WHERE  D.LOAN_BRANCH='"+vo.getBranchId()+"' AND d.REC_STATUS IN ('F','A') AND d.DISBURSAL_STATUS IN ('N','P') AND D.LOAN_REPAYMENT_TYPE='I'");
					}
				
				if((!(vo.getLbxDealNo().equalsIgnoreCase("")))) {
			        bufInsSql.append(" and d.LOAN_DEAL_ID='"+vo.getLbxDealNo()+"'");
			        bufInsSqlTempCount.append(" and d.LOAN_DEAL_ID='"+vo.getLbxDealNo()+"'");
			   	 appendSQL=true;
			   	  
			     }
				
				 if((!(vo.getLbxLoanNoHID().equalsIgnoreCase("")))) {
			        bufInsSql.append("and  d.LOAN_ID='"+vo.getLbxLoanNoHID()+"'");
			        bufInsSqlTempCount.append("and  d.LOAN_ID='"+vo.getLbxLoanNoHID()+"'");
			   	 appendSQL=true;
			   	  
			     }
				 
							
				if((!(vo.getAgrementDate().equalsIgnoreCase("")))) {
			   	  bufInsSql.append("and  d.LOAN_AGREEMENT_DATE =STR_TO_DATE('"+vo.getAgrementDate()+"','"+dateFormatWithTime+"') ");
			   	  bufInsSqlTempCount.append("and  d.LOAN_AGREEMENT_DATE =STR_TO_DATE('"+vo.getAgrementDate()+"','"+dateFormatWithTime+"') ");
			   	  appendSQL=true;
			     }
				
				if((!(vo.getCustomerName().equalsIgnoreCase("")))) {
			   	  bufInsSql.append("and  g.CUSTOMER_NAME like'%"+vo.getCustomerName()+"%' ");
			   	  bufInsSqlTempCount.append("and  g.CUSTOMER_NAME like'%"+vo.getCustomerName()+"%'");
			   	  appendSQL=true;
			     }
				if((!(vo.getLbxProductID().equalsIgnoreCase("")))) {
				   	  bufInsSql.append("and  d.LOAN_PRODUCT='"+vo.getLbxProductID()+"'");
				   	  bufInsSqlTempCount.append("and  d.LOAN_PRODUCT='"+vo.getLbxProductID()+"'");
				   	  appendSQL=true;
				     }
				if((!(vo.getLbxscheme().equalsIgnoreCase("")))) {
				   	  bufInsSql.append("and  d.LOAN_SCHEME='"+vo.getLbxscheme()+"'");
				   	  bufInsSqlTempCount.append("and  d.LOAN_SCHEME='"+vo.getLbxscheme()+"'");
				   	  appendSQL=true;
				     }
                bufInsSqlTempCount.append(")b");
				logger.info("In appendSQL true---- "+appendSQL);
				
				if(appendSQL){
					logger.info("In appendSQL true---- ");
					String  tmp = bufInsSql.toString();
					String tmp1 = bufInsSqlTempCount.toString();
					
			     logger.info("In fetchLoanDetailForDisbursalPlanMaker() ## tmp ## "+tmp);
			     logger.info("In appendSQL true----  in check index Of"+tmp.lastIndexOf("AND") +"------"+(tmp.length()-3));
			   
			     if(tmp.lastIndexOf("AND")== (tmp.length()-3) || tmp1.lastIndexOf("AND")==(tmp1.length()-3))
			     {
			     logger.info("In appendSQL true----  in check index Of");
			     tmp = (tmp).substring(0,(tmp.length()-4));
			     tmp1 = (tmp1).substring(0,(tmp1.length()-4));
			     logger.info("fetchLoanDetailForDisbursalPlanMaker Query...tmp."+tmp);
			     header = ConnectionDAO.sqlSelect(tmp);
			     count =Integer.parseInt(ConnectionDAO.singleReturn(tmp1.toString()));
			     
			      }else
			      {
			     	  logger.info("fetchLoanDetailForDisbursalPlanMaker Query...tmp."+tmp);
			     	 header = ConnectionDAO.sqlSelect(tmp); 
			     	 count =Integer.parseInt(ConnectionDAO.singleReturn(tmp1.toString()));
			       }
				 }else {
			    	  
						LoggerMsg.info("search Query...else-------." + bufInsSql);
						LoggerMsg.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
			            
						count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
						
						
						if((dealId.toString().trim()==null && lonaNo.toString().trim()==null && aDate.toString().trim()==null && custName.toString().trim()==null && productId.toString().trim()==null && scheme.toString().trim()==null) || (dealId.toString().trim().equalsIgnoreCase("") && lonaNo.toString().trim().equalsIgnoreCase("") && aDate.toString().trim().equalsIgnoreCase("") && custName.toString().trim().equalsIgnoreCase("") && productId.toString().trim().equalsIgnoreCase("") && scheme.toString().trim().equalsIgnoreCase("")) || vo.getCurrentPageLink()>1)
						{
						
						LoggerMsg.info("current PAge Link no .................... "+vo.getCurrentPageLink());
						if(vo.getCurrentPageLink()>1)
						{
							startRecordIndex = (vo.getCurrentPageLink()-1)*no;
							endRecordIndex = no;
							LoggerMsg.info("startRecordIndex .................... "+startRecordIndex);
							LoggerMsg.info("endRecordIndex .................... "+endRecordIndex);
						}
						
						bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
						
						}
						LoggerMsg.info("query : "+bufInsSql);
						
						header = ConnectionDAO.sqlSelect(bufInsSql.toString());
					}

							
				for(int i=0;i<header.size();i++){
					logger.info("header"+header.size());
					ArrayList header1=(ArrayList)header.get(i);
					if(header1.size()>0)
					{
						fetchVo = new CommonLoanVo();
						fetchVo.setModifyNo("<a href=disbursalPlanSearchMaker.do?method=openDisbPlanMaker&loanId="
						+ CommonFunction.checkNull(header1.get(0)).trim().toString()+ "+&loanStatus=" 
						+ ">"
						+ CommonFunction.checkNull(header1.get(1)).trim().toString() + "</a>");
						
						fetchVo.setLbxLoanNoHID((CommonFunction.checkNull(header1.get(0)).trim()));
						fetchVo.setLoanNo((CommonFunction.checkNull(header1.get(1)).trim()));
						fetchVo.setAgrementDate((CommonFunction.checkNull(header1.get(2)).trim()));
						fetchVo.setCustomerName((CommonFunction.checkNull(header1.get(3)).trim()));
						fetchVo.setProduct((CommonFunction.checkNull(header1.get(4)).trim()));
						fetchVo.setScheme((CommonFunction.checkNull(header1.get(5)).trim()));
						fetchVo.setValidSancTill((CommonFunction.checkNull(header1.get(6)).trim()));
						fetchVo.setReportingToUserId((CommonFunction.checkNull(header1.get(7)).trim()));
						fetchVo.setTotalRecordSize(count);
						list.add(fetchVo);
					}
				}

				bufInsSql=null;
				bufInsSqlTempCount=null;
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	finally
	{
		dealId=null;
		 lonaNo=null;
		 custName=null;
		 productId=null;
		 scheme=null;
		 aDate=null;
		 userName=null;
	}
			return list;
		}


		@Override
		public ArrayList<Object> fetchLoanDetailForDisbursalPlanAuthor(
				CommonLoanVo vo) {
			
			ArrayList list=new ArrayList();
			int count=0;
			int startRecordIndex=0;
			int endRecordIndex = no;
			StringBuilder userName=new StringBuilder();
			
			logger.info("In fetchLoanDetailForDisbursalPlanAuthor... ");
			try{
				StringBuilder userNameQ=new StringBuilder();
				userNameQ.append("select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+vo.getReportingToUserId()+"'");
				logger.info("fetchLoanDetailForDisbursalPlanAuthor userNameQ: "+userNameQ.toString());
				userName.append(ConnectionDAO.singleReturn(userNameQ.toString()));
				userNameQ=null;
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			

			StringBuilder dealId=new StringBuilder();
			StringBuilder lonaNo=new StringBuilder();
			StringBuilder aDate=new StringBuilder();
			StringBuilder custName=new StringBuilder();
			StringBuilder productId=new StringBuilder();
			StringBuilder scheme=new StringBuilder();
			
			dealId.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxDealNo())).trim());
		    lonaNo.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID())).trim());
			aDate.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAgrementDate())).trim());
		    custName.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName())).trim());
			 productId.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID())).trim());
			 scheme.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme())).trim());
		
			 
			try
			{
				String countResult=null;			    
				ArrayList header=null;
				CommonLoanVo fetchVo= null;
				boolean appendSQL=false;
				StringBuffer bufInsSql=new StringBuffer();
				StringBuffer bufInsSqlTempCount = new StringBuffer();
		 
				bufInsSql.append(" select distinct d.LOAN_ID,d.LOAN_NO,DATE_FORMAT(d.LOAN_AGREEMENT_DATE,'"+dateFormat+"'),g.CUSTOMER_NAME,p.PRODUCT_DESC, s.SCHEME_DESC, DATE_FORMAT(dl.DEAL_SANCTION_VALID_TILL,'"+dateFormat+"'),(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=D.MAKER_ID) MAKER_ID from cr_loan_dtl d");
				bufInsSqlTempCount.append("SELECT distinct COUNT(1) FROM cr_loan_dtl d ");
				
				bufInsSql.append(" inner join gcd_customer_m g on g.CUSTOMER_ID=d.LOAN_CUSTOMER_ID ");
				bufInsSqlTempCount.append(" inner join gcd_customer_m g on g.CUSTOMER_ID=d.LOAN_CUSTOMER_ID ");
				
				bufInsSql.append(" inner join cr_product_m p on d.LOAN_PRODUCT=p.PRODUCT_ID  ");
				bufInsSqlTempCount.append(" inner join cr_product_m p on d.LOAN_PRODUCT=p.PRODUCT_ID  ");
				
				bufInsSql.append(" inner join cr_scheme_m s on d.LOAN_SCHEME=s.SCHEME_ID ");
				bufInsSqlTempCount.append(" inner join cr_scheme_m s on d.LOAN_SCHEME=s.SCHEME_ID ");
				
				bufInsSql.append(" inner join cr_deal_loan_dtl dl on d.LOAN_DEAL_ID=dl.DEAL_ID  AND dl.deal_loan_id=d.loan_deal_loan_id ");
				bufInsSqlTempCount.append(" inner join cr_deal_loan_dtl dl on d.LOAN_DEAL_ID=dl.DEAL_ID  AND dl.deal_loan_id=d.loan_deal_loan_id ");
				
				bufInsSql.append(" inner join cr_loan_disbursalsch_temp_dtl disb on disb.LOAN_ID=d.LOAN_ID AND  disb.REC_STATUS='F' AND disb.MAKER_ID<>'"+vo.getReportingToUserId()+"' ");
				bufInsSqlTempCount.append(" inner join cr_loan_disbursalsch_temp_dtl disb on disb.LOAN_ID=d.LOAN_ID AND  disb.REC_STATUS='F' AND disb.MAKER_ID<>'"+vo.getReportingToUserId()+"' ");
				
				
				bufInsSql.append(" inner join cr_user_approval_m ua on  ua.USER_ID='"+vo.getReportingToUserId()+"' and d.LOAN_LOAN_AMOUNT>=ua.AMOUNT_FROM and d.LOAN_LOAN_AMOUNT<=ua.AMOUNT_TO and ua.USER_ROLE='U' and ua.rec_status='A'");
				bufInsSqlTempCount.append(" inner join cr_user_approval_m ua on  ua.USER_ID='"+vo.getReportingToUserId()+"' and d.LOAN_LOAN_AMOUNT>=ua.AMOUNT_FROM and d.LOAN_LOAN_AMOUNT<=ua.AMOUNT_TO and ua.USER_ROLE='U' and ua.rec_status='A' ");
				
			
					if((!(vo.getLbxDealNo().equalsIgnoreCase("")))&&(!(vo.getLbxLoanNoHID().equalsIgnoreCase("")))&&(!(vo.getAgrementDate().equalsIgnoreCase("")))&&(!(vo.getCustomerName().equalsIgnoreCase("")))&&(!(vo.getLbxProductID().equalsIgnoreCase("")))&&(!(vo.getLbxscheme().equalsIgnoreCase(""))))
					{
				   	  bufInsSql.append("WHERE  D.LOAN_BRANCH='"+vo.getBranchId()+"' AND d.LOAN_DEAL_ID='"+vo.getLbxDealNo()+"' AND d.LOAN_ID='"+vo.getLbxLoanNoHID()+"' AND d.LOAN_AGREEMENT_DATE=STR_TO_DATE('"+vo.getAgrementDate()+"','"+dateFormat+"') AND g.CUSTOMER_NAME like'%"+vo.getCustomerName()+"%' AND d.LOAN_PRODUCT='"+vo.getLbxProductID()+"'AND d.LOAN_SCHEME='"+vo.getLbxscheme()+"' AND d.REC_STATUS IN ('F','A') AND d.DISBURSAL_STATUS IN ('N','P') AND D.LOAN_REPAYMENT_TYPE='I' ");
				   	  bufInsSqlTempCount.append("WHERE  D.LOAN_BRANCH='"+vo.getBranchId()+"' AND d.LOAN_DEAL_ID='"+vo.getLbxDealNo()+"' AND d.LOAN_ID='"+vo.getLbxLoanNoHID()+"' AND d.LOAN_AGREEMENT_DATE=STR_TO_DATE('"+vo.getAgrementDate()+"','"+dateFormat+"') AND g.CUSTOMER_NAME like'%"+vo.getCustomerName()+"%' AND d.LOAN_PRODUCT='"+vo.getLbxProductID()+"'AND d.LOAN_SCHEME='"+vo.getLbxscheme()+"' AND d.REC_STATUS IN ('F','A') AND d.DISBURSAL_STATUS IN ('N','P') AND D.LOAN_REPAYMENT_TYPE='I' ");
					}
					
					if(((vo.getLbxDealNo().equalsIgnoreCase("")))||((vo.getLbxLoanNoHID().equalsIgnoreCase("")))||((vo.getAgrementDate().equalsIgnoreCase("")))||((vo.getCustomerName().equalsIgnoreCase("")))||((vo.getLbxProductID().equalsIgnoreCase("")))||((vo.getLbxscheme().equalsIgnoreCase("")))||((vo.getStage().equalsIgnoreCase("")))){
						appendSQL=true;
					}
					
					if(appendSQL){
						logger.info("In Where Clause");
						bufInsSql.append(" WHERE  D.LOAN_BRANCH='"+vo.getBranchId()+"' AND d.REC_STATUS IN ('F','A') AND d.DISBURSAL_STATUS IN ('N','P') AND D.LOAN_REPAYMENT_TYPE='I'");
						bufInsSqlTempCount.append(" WHERE  D.LOAN_BRANCH='"+vo.getBranchId()+"' AND d.REC_STATUS IN ('F','A') AND d.DISBURSAL_STATUS IN ('N','P') AND D.LOAN_REPAYMENT_TYPE='I'");
					}
				
				if((!(vo.getLbxDealNo().equalsIgnoreCase("")))) {
			        bufInsSql.append(" and d.LOAN_DEAL_ID='"+vo.getLbxDealNo()+"'");
			        bufInsSqlTempCount.append(" and d.LOAN_DEAL_ID='"+vo.getLbxDealNo()+"'");
			   	 appendSQL=true;
			   	  
			     }
				
				 if((!(vo.getLbxLoanNoHID().equalsIgnoreCase("")))) {
			        bufInsSql.append("and  d.LOAN_ID='"+vo.getLbxLoanNoHID()+"'");
			        bufInsSqlTempCount.append("and  d.LOAN_ID='"+vo.getLbxLoanNoHID()+"'");
			   	 appendSQL=true;
			   	  
			     }
				 
							
				if((!(vo.getAgrementDate().equalsIgnoreCase("")))) {
			   	  bufInsSql.append("and  d.LOAN_AGREEMENT_DATE =STR_TO_DATE('"+vo.getAgrementDate()+"','"+dateFormatWithTime+"') ");
			   	  bufInsSqlTempCount.append("and  d.LOAN_AGREEMENT_DATE =STR_TO_DATE('"+vo.getAgrementDate()+"','"+dateFormatWithTime+"') ");
			   	  appendSQL=true;
			     }
				
				if((!(vo.getCustomerName().equalsIgnoreCase("")))) {
			   	  bufInsSql.append("and  g.CUSTOMER_NAME like'%"+vo.getCustomerName()+"%' ");
			   	  bufInsSqlTempCount.append("and  g.CUSTOMER_NAME like'%"+vo.getCustomerName()+"%'");
			   	  appendSQL=true;
			     }
				if((!(vo.getLbxProductID().equalsIgnoreCase("")))) {
				   	  bufInsSql.append("and  d.LOAN_PRODUCT='"+vo.getLbxProductID()+"'");
				   	  bufInsSqlTempCount.append("and  d.LOAN_PRODUCT='"+vo.getLbxProductID()+"'");
				   	  appendSQL=true;
				     }
				if((!(vo.getLbxscheme().equalsIgnoreCase("")))) {
				   	  bufInsSql.append("and  d.LOAN_SCHEME='"+vo.getLbxscheme()+"'");
				   	  bufInsSqlTempCount.append("and  d.LOAN_SCHEME='"+vo.getLbxscheme()+"'");
				   	  appendSQL=true;
				     }

				
				
				if(appendSQL){
					logger.info("In appendSQL true---- ");
					String  tmp = bufInsSql.toString();
					String tmp1 = bufInsSqlTempCount.toString();
					
			   
			     if(tmp.lastIndexOf("AND")== (tmp.length()-3) || tmp1.lastIndexOf("AND")==(tmp1.length()-3))
			     {
			     tmp = (tmp).substring(0,(tmp.length()-4));
			     tmp1 = (tmp1).substring(0,(tmp1.length()-4));
			     logger.info("If block (fetchLoanDetailForDisbursalPlanAuthor temp) : "+tmp);
		     	 header = ConnectionDAO.sqlSelect(tmp); 
		     	 tmp=null;
		     	logger.info("If block (fetchLoanDetailForDisbursalPlanAuthor temp1) : "+tmp1);
		     	countResult=ConnectionDAO.singleReturn(tmp1);
				if(!CommonFunction.checkNull(countResult).equalsIgnoreCase(""))
				{
					count =Integer.parseInt(countResult);
					countResult=null;
				}
		      	 tmp1=null;
			     
			      }else
			      {
			    	 logger.info(" Else block (fetchLoanDetailForDisbursalPlanAuthor temp) : "+tmp);
			     	 header = ConnectionDAO.sqlSelect(tmp); 
			     	 tmp=null;
			     	logger.info(" Else block (fetchLoanDetailForDisbursalPlanAuthor temp1) : "+tmp1);
					countResult=ConnectionDAO.singleReturn(tmp1);
					if(!CommonFunction.checkNull(countResult).equalsIgnoreCase(""))
					{
						count =Integer.parseInt(countResult);
						countResult=null;
					}
			      	 tmp1=null;
			       }
				 }else {
			    	  
						logger.info("fetchLoanDetailForDisbursalPlanAuthor bufInsSqlTempCount : "+bufInsSqlTempCount.toString());
						 countResult=ConnectionDAO.singleReturn(bufInsSqlTempCount.toString());
						if(!CommonFunction.checkNull(countResult).equalsIgnoreCase(""))
						{
							count =Integer.parseInt(countResult);
							countResult=null;
						}
						
						bufInsSqlTempCount=null;
						
											
						if(vo.getCurrentPageLink()>1)
						{
							startRecordIndex = (vo.getCurrentPageLink()-1)*no;
							endRecordIndex = no;
							
						}
						
						bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
				    	logger.info("fetchLoanDetailForDisbursalPlanAuthor query : "+bufInsSql.toString());
						
						header = ConnectionDAO.sqlSelect(bufInsSql.toString());
						bufInsSql=null;
					}

					int size=header.size();
				for(int i=0;i<size;i++){
				
					ArrayList header1=(ArrayList)header.get(i);
					if(header1.size()>0)
					{
						fetchVo = new CommonLoanVo();
						fetchVo.setModifyNo("<a href=disbursalPlanSearchAuthor.do?method=openDisbPlanAuthor&loanId="
						+ CommonFunction.checkNull(header1.get(0)).trim().toString()+ "+&loanStatus=" 
						+ ">"
						+ CommonFunction.checkNull(header1.get(1)).trim().toString() + "</a>");
						
						fetchVo.setLbxLoanNoHID((CommonFunction.checkNull(header1.get(0)).trim()));
						fetchVo.setLoanNo((CommonFunction.checkNull(header1.get(1)).trim()));
						fetchVo.setAgrementDate((CommonFunction.checkNull(header1.get(2)).trim()));
						fetchVo.setCustomerName((CommonFunction.checkNull(header1.get(3)).trim()));
						fetchVo.setProduct((CommonFunction.checkNull(header1.get(4)).trim()));
						fetchVo.setScheme((CommonFunction.checkNull(header1.get(5)).trim()));
						fetchVo.setValidSancTill((CommonFunction.checkNull(header1.get(6)).trim()));
						fetchVo.setReportingToUserId((CommonFunction.checkNull(header1.get(7)).trim()));
						fetchVo.setTotalRecordSize(count);
						list.add(fetchVo);
						fetchVo=null;
					}
					header1.clear();
					header1=null;
				}
				header.clear();
				header=null;
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	finally
	{
		dealId=null;
		 lonaNo=null;
		 custName=null;
		 productId=null;
		 scheme=null;
		 aDate=null;
		 userName=null;
	}
			return list;
		}


		@Override
		public ArrayList getNoOfDisbPlanMaker(String loanId) {
			
			ArrayList<Object> list=new ArrayList<Object>();
			try{
				StringBuilder query=new StringBuilder();
	        query.append("select distinct S.DISBURSAL_NO,S.DISBURSAL_STAGE,S.DISBURSAL_DESCRIPTION,DATE_FORMAT(S.PROPOSED_DISBURSAL_DATE,'%d-%m-%Y'),S.PROPOSED_DISBURSAL_AMOUNT , ");
	        query.append(" CASE WHEN (D.DISBURSAL_AMOUNT=S.PROPOSED_DISBURSAL_AMOUNT AND D.DISBURSAL_DATE=S.PROPOSED_DISBURSAL_DATE AND D.DISBURSAL_NO=S.DISBURSAL_NO) THEN 'Y' END AS 'DISB_STATUS' ");
	        query.append(" from cr_loan_disbursalsch_temp_dtl S  ");
	        query.append(" left join cr_loan_disbursal_dtl D on D.LOAN_ID=S.LOAN_ID AND D.REC_STATUS='A' ");
	        query.append(" where S.LOAN_ID='"+loanId+"' ");
	        
			logger.info("getNoOfDisbPlanMaker Queryl: "+query);
			
			NoOfDisbursalVo nVo = null;
			ArrayList disbdeatail = ConnectionDAO.sqlSelect(query.toString());
			logger.info("getNoOfDisbPlanMaker OUTER ARRAYLIST SIZE: "+disbdeatail.size());
			for(int i=0;i<disbdeatail.size();i++){

				logger.info("getNoOfDisbPlanMaker"+disbdeatail.get(i).toString());
				ArrayList disbdeatail1=(ArrayList)disbdeatail.get(i);
				logger.info("getNoOfDisbPlanMaker INNNER ARRAYLIST SIZE: "+disbdeatail1.size());
				if(disbdeatail1.size()>0)
				{
					nVo = new NoOfDisbursalVo();
					
					nVo.setNoOfDisb((CommonFunction.checkNull(disbdeatail1.get(0))).trim());
					nVo.setSatge((CommonFunction.checkNull(disbdeatail1.get(1))).trim());
					nVo.setDescOfDisb((CommonFunction.checkNull(disbdeatail1.get(2))).trim());
					nVo.setDateOfDisb((CommonFunction.checkNull(disbdeatail1.get(3))).trim());
					
					if(!CommonFunction.checkNull(disbdeatail1.get(4)).equalsIgnoreCase(""))	
		 			{
						Number Amount = myFormatter.parse((CommonFunction.checkNull(disbdeatail1.get(4))).trim());
						logger.info("LoanAmount: "+Amount);			
						nVo.setAmountOfDisb(myFormatter.format(Amount));	
		 			}
					else
					{
						nVo.setAmountOfDisb("");	
					}
					nVo.setDisbStatus((CommonFunction.checkNull(disbdeatail1.get(5))).trim());
					
					list.add(nVo);
				}
			}
			query=null;
			}catch(Exception e){
				e.printStackTrace();
			}
			return list;
		}

		@Override
		public boolean approveNoOfDisb(NoOfDisbursalVo nvo) {
			
        logger.info("In approveNoOfDisb mod"+nvo.getLoanId()+" decision: "+nvo.getDecision());
			
			
			ArrayList qryList=new ArrayList();
			
			  boolean status=false;
			  StringBuilder	bufInsSql=null;
			  StringBuilder	bufInsSql1=null;
			  StringBuilder bufInsSql2=null;
			  StringBuilder deleteQuery =null;
			  
			  PrepStmtObject insertPrepStmtObject=null;
			  PrepStmtObject insertPrepStmtObject1=null;
			  PrepStmtObject insertPrepStmtObject2=null;
			  PrepStmtObject deletePrepStmtObject=null;
			try {

				  if(CommonFunction.checkNull(nvo.getDecision()).equalsIgnoreCase("A"))
				  {
				    insertPrepStmtObject = new PrepStmtObject();
				    bufInsSql =	new StringBuilder();
					bufInsSql.append("insert into cr_loan_disbursalsch_hst_dtl(LOAN_ID,DISBURSAL_NO,DISBURSAL_STAGE,DISBURSAL_DESCRIPTION,PROPOSED_DISBURSAL_DATE,PROPOSED_DISBURSAL_AMOUNT,DISBURSAL_FLAG,REC_STATUS,AUTHOR_REMARKS,MAKER_ID,MAKER_DATE)");
					bufInsSql.append(" SELECT LOAN_ID,DISBURSAL_NO,DISBURSAL_STAGE,DISBURSAL_DESCRIPTION,PROPOSED_DISBURSAL_DATE,PROPOSED_DISBURSAL_AMOUNT,DISBURSAL_FLAG,IFNULL(REC_STATUS,'P'),AUTHOR_REMARKS,MAKER_ID,MAKER_DATE FROM cr_loan_disbursalsch_dtl WHERE LOAN_ID='"+(CommonFunction.checkNull(nvo.getLoanId()).trim())+ "'");
					insertPrepStmtObject.setSql(bufInsSql.toString());
					logger.info("IN approveNoOfDisb()del insert query1 ### "+insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);
					deleteQuery=new StringBuilder();
					deletePrepStmtObject=new PrepStmtObject();
					deleteQuery.append("DELETE FROM cr_loan_disbursalsch_dtl WHERE LOAN_ID='"+(CommonFunction.checkNull(nvo.getLoanId()).trim())+ "'");
					deletePrepStmtObject.setSql(deleteQuery.toString());	 
					logger.info("approveNoOfDisb Delete query(cr_loan_disbursalsch_dtl): "+deletePrepStmtObject.toString());
					qryList.add(deletePrepStmtObject);
						 
					insertPrepStmtObject2 = new PrepStmtObject();
					bufInsSql2 =new StringBuilder();
					bufInsSql2.append("update cr_loan_disbursalsch_temp_dtl SET REC_STATUS='"+nvo.getDecision()+"',AUTHOR_REMARKS='"+nvo.getComments()+"', AUTHOR_ID='"+nvo.getMakerId()+"',AUTHOR_DATE=STR_TO_DATE('"+nvo.getMakerDate()+"','"+dateFormatWithTime+"')");
					
					bufInsSql2.append(" WHERE LOAN_ID='"+(CommonFunction.checkNull(nvo.getLoanId()).trim())+ "'");
					insertPrepStmtObject2.setSql(bufInsSql2.toString());
					logger.info("IN approveNoOfDisb()update cr_loan_disbursalsch_temp_dtl query3 ### "+insertPrepStmtObject2.printQuery());
					qryList.add(insertPrepStmtObject2);	
						 
				  insertPrepStmtObject1 = new PrepStmtObject();
				  bufInsSql1 =	new StringBuilder();
				  bufInsSql1.append("insert into cr_loan_disbursalsch_dtl(LOAN_ID,DISBURSAL_NO,DISBURSAL_STAGE,DISBURSAL_DESCRIPTION,PROPOSED_DISBURSAL_DATE,PROPOSED_DISBURSAL_AMOUNT,DISBURSAL_FLAG,REC_STATUS,AUTHOR_REMARKS,MAKER_ID,MAKER_DATE)");
				  bufInsSql1.append("SELECT LOAN_ID,DISBURSAL_NO,DISBURSAL_STAGE,DISBURSAL_DESCRIPTION,PROPOSED_DISBURSAL_DATE,PROPOSED_DISBURSAL_AMOUNT,DISBURSAL_FLAG,IFNULL(REC_STATUS,'P'),AUTHOR_REMARKS,MAKER_ID,MAKER_DATE FROM cr_loan_disbursalsch_temp_dtl WHERE LOAN_ID='"+(CommonFunction.checkNull(nvo.getLoanId()).trim())+ "'");
				  insertPrepStmtObject1.setSql(bufInsSql1.toString());
				  logger.info("IN approveNoOfDisb()del insert query3 ### "+insertPrepStmtObject1.printQuery());
				  qryList.add(insertPrepStmtObject1);	
				  
				  }
				  else
				  {
                        deletePrepStmtObject = new PrepStmtObject();
					    StringBuilder delsSql =	new StringBuilder();
					    delsSql.append("delete from cr_loan_disbursalsch_temp_dtl where LOAN_ID='"+(CommonFunction.checkNull(nvo.getLoanId()).trim())+ "'");
					    deletePrepStmtObject.setSql(delsSql.toString());
						logger.info("IN approveNoOfDisb() delete query1 ### "+deletePrepStmtObject.printQuery());
						qryList.add(deletePrepStmtObject);
					    insertPrepStmtObject = new PrepStmtObject();
					    bufInsSql =	new StringBuilder();
						bufInsSql.append("insert into cr_loan_disbursalsch_temp_dtl(LOAN_ID,DISBURSAL_NO,DISBURSAL_STAGE,DISBURSAL_DESCRIPTION,PROPOSED_DISBURSAL_DATE,PROPOSED_DISBURSAL_AMOUNT,DISBURSAL_FLAG,REC_STATUS,AUTHOR_REMARKS,MAKER_ID,MAKER_DATE)");
						bufInsSql.append(" SELECT LOAN_ID,DISBURSAL_NO,DISBURSAL_STAGE,DISBURSAL_DESCRIPTION,PROPOSED_DISBURSAL_DATE,PROPOSED_DISBURSAL_AMOUNT,DISBURSAL_FLAG,IFNULL(REC_STATUS,'P'),AUTHOR_REMARKS,MAKER_ID,MAKER_DATE FROM cr_loan_disbursalsch_dtl WHERE LOAN_ID='"+(CommonFunction.checkNull(nvo.getLoanId()).trim())+ "'");
						insertPrepStmtObject.setSql(bufInsSql.toString());
						logger.info("IN approveNoOfDisb()del insert query1 ### "+insertPrepStmtObject.printQuery());
						qryList.add(insertPrepStmtObject);	
				  }
				  status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			
			bufInsSql=null;
			bufInsSql1=null;
			bufInsSql2=null;
			insertPrepStmtObject=null;
			insertPrepStmtObject1=null;
			insertPrepStmtObject2=null;
		}
		logger.info("In approveNoOfDisb......................"+status);
		return status;
		
		}


		@Override
		public boolean saveDisbursalScheduleFromLoanInTemp(String loanId) {
			
         logger.info("In saveDisbursalScheduleFromLoanInTemp mod"+loanId);
			
			ArrayList qryList=new ArrayList();
			ArrayList qryList1=new ArrayList();
			boolean status=false;
			try {
				  StringBuilder	bufInsSql=null;
				
				 
				  StringBuilder checkQ=null;
				  StringBuilder count =	new StringBuilder();
				
		
				checkQ=new StringBuilder();
				PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	
				
					    StringBuilder qry=new StringBuilder();
						 qry.append("DELETE FROM cr_loan_disbursalsch_temp_dtl WHERE LOAN_ID='"+(CommonFunction.checkNull(loanId).trim())+ "'");
						 logger.info("saveDisbursalScheduleFromLoanInTemp Delete query(cr_loan_disbursalsch_temp_dtl): "+qry.toString());
						 qryList1.add(qry);
						 ConnectionDAO.sqlInsUpdDelete(qryList1);
						 qry=null;
				
				    bufInsSql =	new StringBuilder();
					bufInsSql.append("insert into cr_loan_disbursalsch_temp_dtl(LOAN_ID,DISBURSAL_NO,DISBURSAL_STAGE,DISBURSAL_DESCRIPTION,PROPOSED_DISBURSAL_DATE,PROPOSED_DISBURSAL_AMOUNT,DISBURSAL_FLAG,REC_STATUS,MAKER_ID,MAKER_DATE)");
					bufInsSql.append("SELECT LOAN_ID,DISBURSAL_NO,DISBURSAL_STAGE,DISBURSAL_DESCRIPTION,PROPOSED_DISBURSAL_DATE,PROPOSED_DISBURSAL_AMOUNT,DISBURSAL_FLAG,IFNULL(REC_STATUS,'P'),MAKER_ID,MAKER_DATE FROM cr_loan_disbursalsch_dtl WHERE LOAN_ID='"+(CommonFunction.checkNull(loanId).trim())+ "'");
					
			insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("IN saveDisbursalScheduleFromLoanInTemp()del insert query1 ### "+insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
			checkQ=null;
			bufInsSql=null;
		


			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			
			checkQ=null;
			bufInsSql=null;
			count=null;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("In saveDisbursalScheduleFromLoanInTemp......................"+status);
		return status;
		}


		@Override
		public boolean saveNoOfDisbPlanMenu(NoOfDisbursalVo vo) {
			
			logger.info("In saveNoOfDisbPlanMenu mod"+vo.getNoOfDisbursal().length);
    		String noDisb[] = vo.getNoOfDisbursal();
    		String disbDate[] = vo.getDateOfDisbursal();
    		String disbDesc[] = vo.getDescOfDisbursal();
    		String amount[]=vo.getAmountOfDisbursal();
    		
    		logger.info("In .saveNoOfDisbPlanMenu mod"+vo.getNoOfDisbursal().length);
    		
    	
    		
    		ArrayList qryList=new ArrayList();
    		ArrayList qryList1=new ArrayList();
    		boolean status=false;
    		try {
    			StringBuilder	bufInsSql=null;
    			
    			 
    			 StringBuilder checkQ=null;
    			 StringBuilder count =	new StringBuilder();
    			 logger.info("Doc Id in saveNoOfDisbPlanMenu: "+noDisb.length);
    			int disLength= noDisb.length;
    		for(int k=0;k<disLength; k++)
    		{
    			checkQ=new StringBuilder();
    			PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
    			 
//    			 StringBuffer bufInsSql =	new StringBuffer();
    			 logger.info("Doc Id in saveNoOfDisbPlanMenu: "+noDisb[k]);
    			
    			   checkQ.append("select count(*) from cr_loan_disbursalsch_temp_dtl where LOAN_ID="+vo.getLoanId()+" and DISBURSAL_NO="+CommonFunction.checkNull(noDisb[k]));
    			   count.append(ConnectionDAO.singleReturn(checkQ.toString()));
    			   
    			   if(!count.toString().equalsIgnoreCase("0"))
    				  { 
    				   StringBuilder qry=new StringBuilder();
    				 qry.append("DELETE FROM cr_loan_disbursalsch_temp_dtl WHERE LOAN_ID='"+(CommonFunction.checkNull(vo.getLoanId()).trim())+ "'");
    				 qryList1.add(qry);
    				 ConnectionDAO.sqlInsUpdDelete(qryList1);
    				 qry=null;
    				  }
    			 // if(count.equalsIgnoreCase("0"))
    			 // {
    			    //logger.info("Doc Id in saveDocumentPOD: "+dId[k]);
    			   bufInsSql =	new StringBuilder();
    				bufInsSql.append("insert into cr_loan_disbursalsch_temp_dtl(LOAN_ID,DISBURSAL_NO,DISBURSAL_STAGE,DISBURSAL_DESCRIPTION,PROPOSED_DISBURSAL_DATE,PROPOSED_DISBURSAL_AMOUNT,DISBURSAL_FLAG,REC_STATUS,MAKER_ID,MAKER_DATE) values(?,?,?,?,STR_TO_DATE(?,'"+dateFormat+"'),?,?,?,?,STR_TO_DATE(?,'"+dateFormat+"'))");
    				
    				if(CommonFunction.checkNull(vo.getLoanId()).trim().equalsIgnoreCase(""))
    					insertPrepStmtObject.addNull();
    				else
    					insertPrepStmtObject.addString(vo.getLoanId().trim());   // loan Id
    				
    				if(CommonFunction.checkNull(noDisb[k]).trim().equalsIgnoreCase(""))
    					insertPrepStmtObject.addNull();
    				else
    					insertPrepStmtObject.addString(noDisb[k].trim()); //disb no
    				
    		
    				     insertPrepStmtObject.addString("MM");// Disbursal stage
    					
    					
    				if(CommonFunction.checkNull(disbDesc[k]).trim().equalsIgnoreCase(""))
    					insertPrepStmtObject.addNull();
    				else
    					insertPrepStmtObject.addString(disbDesc[k].trim());// Description
    				
    				if(CommonFunction.checkNull(disbDate[k]).trim().equalsIgnoreCase(""))
    					insertPrepStmtObject.addNull();
    				else
    					insertPrepStmtObject.addString(disbDate[k].trim());          // disb date			
    				
    					
    				if((CommonFunction.checkNull(amount[k])).equalsIgnoreCase(""))
    					insertPrepStmtObject.addString("0.00");
    					else
    						insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(amount[k]).trim())).toString());
    					
    				
    				    insertPrepStmtObject.addNull();//Flag
    				    
    				    if(CommonFunction.checkNull(vo.getRecStatus()).trim().equalsIgnoreCase(""))
        					insertPrepStmtObject.addNull();
        				else
        					insertPrepStmtObject.addString(vo.getRecStatus());    
    				    
    				    if(CommonFunction.checkNull(vo.getMakerId()).trim().equalsIgnoreCase(""))
        					insertPrepStmtObject.addNull();
        				else
        					insertPrepStmtObject.addString(vo.getMakerId());   
    				    
    				    if(CommonFunction.checkNull(vo.getMakerDate()).trim().equalsIgnoreCase(""))
        					insertPrepStmtObject.addNull();
        				else
        					insertPrepStmtObject.addString(vo.getMakerDate());   
    		
    		
    		insertPrepStmtObject.setSql(bufInsSql.toString());
    		logger.info("IN saveNoOfDisbPlanMenu() insert query1 ### "+insertPrepStmtObject.printQuery());
    		qryList.add(insertPrepStmtObject);
    		checkQ=null;
    		bufInsSql=null;
    }
    	
    	
    		status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
    		
    		checkQ=null;
    		bufInsSql=null;
    		count=null;
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	finally{
    		
    		 noDisb=null;
    		 disbDate=null;
    		 disbDesc=null;
    		 amount=null;
    		 vo=null;
    	}
        logger.info("In saveNoOfDisbPlanMenu......................"+status);
        return status;
		}


		//Method added by neeraj
		public ArrayList<Object> forwardedEditLoan(CommonLoanVo vo) 
		{
			logger.info("In forwardedEditLoan() of LoanInitiationDAOImpl");
			ArrayList list=new ArrayList();
			int count=0;
			int startRecordIndex=0;
			int endRecordIndex = no;
			ArrayList header=null;
			CommonLoanVo fetchVo= null;
			boolean appendSQL=false;
			StringBuffer bufInsSql=new StringBuffer();
			StringBuffer bufInsSqlTempCount = new StringBuffer();
			try
			{
				bufInsSql.append(" select d.LOAN_ID,d.LOAN_NO,DATE_FORMAT(d.LOAN_AGREEMENT_DATE,'"+dateFormat+"'),g.CUSTOMER_NAME,p.PRODUCT_DESC, s.SCHEME_DESC," +
						" DATE_FORMAT(dl.DEAL_SANCTION_VALID_TILL,'"+dateFormat+"'),u.USER_NAME MAKER_ID " +
						" from cr_loan_dtl d " +
						" join gcd_customer_m g on g.CUSTOMER_ID=d.LOAN_CUSTOMER_ID " +
						" join cr_product_m p on d.LOAN_PRODUCT=p.PRODUCT_ID  " +
						" join cr_scheme_m s on d.LOAN_SCHEME=s.SCHEME_ID " +
						" join cr_deal_loan_dtl dl on d.LOAN_DEAL_ID=dl.DEAL_ID  AND dl.deal_loan_id=d.loan_deal_loan_id " +
						" join sec_user_m u on(u.USER_ID=D.MAKER_ID) " +
						" where d.rec_status='A' ");
				if(CommonFunction.checkNull(vo.getEditData()).trim().equalsIgnoreCase("O") && CommonFunction.checkNull(vo.getEditStatus()).trim().equalsIgnoreCase("P"))
					bufInsSql.append(" and ifnull(d.EDIT_REC_STATUS,'N')='P' and  ifnull(d.EDIT_MAKER_ID,'XYZ')='"+CommonFunction.checkNull(vo.getUserId())+"' ");
				if(CommonFunction.checkNull(vo.getEditData()).trim().equalsIgnoreCase("O") && CommonFunction.checkNull(vo.getEditStatus()).trim().equalsIgnoreCase("F"))
					bufInsSql.append(" and ifnull(d.EDIT_REC_STATUS,'N')='F' and  ifnull(d.EDIT_MAKER_ID,'XYZ')!='"+CommonFunction.checkNull(vo.getUserId())+"' ");
				if(CommonFunction.checkNull(vo.getEditData()).trim().equalsIgnoreCase("N"))
					bufInsSql.append(" and ifnull(d.EDIT_REC_STATUS,'N')NOT IN('P','F')");
				
				bufInsSqlTempCount.append(" select count(1) from (select d.LOAN_ID,d.LOAN_NO,DATE_FORMAT(d.LOAN_AGREEMENT_DATE,'"+dateFormat+"'),g.CUSTOMER_NAME,p.PRODUCT_DESC, s.SCHEME_DESC," +
						" DATE_FORMAT(dl.DEAL_SANCTION_VALID_TILL,'"+dateFormat+"'),u.USER_NAME MAKER_ID " +
						" from cr_loan_dtl d " +
						" join gcd_customer_m g on g.CUSTOMER_ID=d.LOAN_CUSTOMER_ID " +
						" join cr_product_m p on d.LOAN_PRODUCT=p.PRODUCT_ID  " +
						" join cr_scheme_m s on d.LOAN_SCHEME=s.SCHEME_ID " +
						" join cr_deal_loan_dtl dl on d.LOAN_DEAL_ID=dl.DEAL_ID  AND dl.deal_loan_id=d.loan_deal_loan_id " +
						" join sec_user_m u on(u.USER_ID=D.MAKER_ID) " +
						" where d.rec_status='A' ");
				if(CommonFunction.checkNull(vo.getEditData()).trim().equalsIgnoreCase("O") && CommonFunction.checkNull(vo.getEditStatus()).trim().equalsIgnoreCase("P"))
					bufInsSqlTempCount.append(" and d.EDIT_REC_STATUS='P' and  d.EDIT_MAKER_ID='"+CommonFunction.checkNull(vo.getUserId())+"' ");
				if(CommonFunction.checkNull(vo.getEditData()).trim().equalsIgnoreCase("O") && CommonFunction.checkNull(vo.getEditStatus()).trim().equalsIgnoreCase("F"))
					bufInsSqlTempCount.append(" and d.EDIT_REC_STATUS='F' and  d.EDIT_MAKER_ID !='"+CommonFunction.checkNull(vo.getUserId())+"' ");
				if(CommonFunction.checkNull(vo.getEditData()).trim().equalsIgnoreCase("N"))
					bufInsSqlTempCount.append(" and ifnull(d.EDIT_REC_STATUS,'N') NOT IN('P','F')");
				
				if(!CommonFunction.checkNull(vo.getLbxLoanNoHID()).trim().equalsIgnoreCase(""))
				{
					bufInsSql.append(" AND d.LOAN_ID='"+vo.getLbxLoanNoHID()+"'");
					bufInsSqlTempCount.append(" AND d.LOAN_ID='"+vo.getLbxLoanNoHID()+"'");
				}
				if(!CommonFunction.checkNull(vo.getCustomerName()).trim().equalsIgnoreCase(""))
				{
					bufInsSql.append(" AND g.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomerName())+"%' ");
					bufInsSqlTempCount.append(" AND g.CUSTOMER_NAME like'%"+StringEscapeUtils.escapeSql(vo.getCustomerName())+"%' ");
				}
				if(!CommonFunction.checkNull(vo.getLbxProductID()).trim().equalsIgnoreCase(""))
				{
					bufInsSql.append(" AND d.LOAN_PRODUCT='"+vo.getLbxProductID()+"' ");
					bufInsSqlTempCount.append(" AND d.LOAN_PRODUCT='"+vo.getLbxProductID()+"' ");
				}
				if(!CommonFunction.checkNull(vo.getLbxscheme()).trim().equalsIgnoreCase(""))
				{
					bufInsSql.append(" AND d.LOAN_SCHEME='"+vo.getLbxscheme()+" ");
					bufInsSqlTempCount.append(" AND d.LOAN_SCHEME='"+vo.getLbxscheme()+" ");
				}
				bufInsSqlTempCount.append(" )b ");
				logger.info("In forwardedEditLoan() of LoanInitiationDAOImpl  Count Query  :  "+bufInsSqlTempCount.toString());
				count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
				
				
				if(vo.getCurrentPageLink()>1)
				{
					startRecordIndex = (vo.getCurrentPageLink()-1)*no;
					endRecordIndex = no;
					logger.info("startRecordIndex .................... "+startRecordIndex);
					logger.info("endRecordIndex .................... "+endRecordIndex);
				}
				bufInsSql.append(" ORDER BY d.LOAN_ID ");
				bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
				
				logger.info("In forwardedEditLoan() of LoanInitiationDAOImpl  Search Query  :  "+bufInsSql.toString());
				header = ConnectionDAO.sqlSelect(bufInsSql.toString());

				for(int i=0;i<header.size();i++)
				{
					ArrayList header1=(ArrayList)header.get(i);
					if(header1.size()>0)
					{
						fetchVo = new CommonLoanVo();    
						fetchVo.setModifyNo("<a href=loanInitBehindAction.do?method=loanInitMakerAuthor&loanId="+ CommonFunction.checkNull(header1.get(0)).trim().toString()+ "+&loanStatus=A&caseType="+vo.getCaseType()+">"+ CommonFunction.checkNull(header1.get(1)).trim().toString() + "</a>");
						fetchVo.setLbxLoanNoHID((CommonFunction.checkNull(header1.get(0)).trim()));
						fetchVo.setLoanNo((CommonFunction.checkNull(header1.get(1)).trim()));
						fetchVo.setAgrementDate((CommonFunction.checkNull(header1.get(2)).trim()));
						fetchVo.setCustomerName((CommonFunction.checkNull(header1.get(3)).trim()));
						fetchVo.setProduct((CommonFunction.checkNull(header1.get(4)).trim()));
						fetchVo.setScheme((CommonFunction.checkNull(header1.get(5)).trim()));
						fetchVo.setValidSancTill((CommonFunction.checkNull(header1.get(6)).trim()));
						fetchVo.setReportingToUserId((CommonFunction.checkNull(header1.get(7)).trim()));
						fetchVo.setTotalRecordSize(count);
						list.add(fetchVo);
					}
				}
				bufInsSql=null;
				bufInsSqlTempCount=null;
			}
			catch(Exception e)
			{e.printStackTrace();}
			finally
			{
				header=null;
				fetchVo= null;
				bufInsSql=null;
				bufInsSqlTempCount=null;
			}
			return list;
		}
		//method added by neeraj
		public ArrayList<Object> forwardEditLoan(CommonLoanVo vo) 
		{
			ArrayList<Object> result =new ArrayList<Object>();
			ArrayList<Object> in =new ArrayList<Object>();
			ArrayList<Object> out =new ArrayList<Object>();
			ArrayList outMessages = new ArrayList();
			String s1=null;
			String s2=null;  	
			try
			{
				in.add(vo.getLbxLoanNoHID());
				in.add(vo.getUserId());
				in.add(CommonFunction.changeFormat(vo.getbDate()));
				out.add(s1);
				out.add(s2);
				logger.info("Edit_Loan_Forwarded ("+vo.getLbxLoanNoHID()+","+vo.getUserId()+","+CommonFunction.changeFormat(vo.getbDate())+","+s1+","+s2+")");
				outMessages=(ArrayList) ConnectionDAO.callSP("Edit_Loan_Forwarded",in,out);
				s1=CommonFunction.checkNull(outMessages.get(0));
				s2=CommonFunction.checkNull(outMessages.get(1));
		        logger.info("s1  : "+s1);
		        logger.info("s2  : "+s2);	
		        result.add(s1);
		        result.add(s2);
		       
		    }
			catch (Exception e) 
			{
				result.add("E");
		        result.add("Some Exception occur Please contact Administrator... ");
			e.printStackTrace();
			}	
			finally
			{
				in.clear();
				in=null;
				out.clear();
				out=null;
				outMessages.clear();
				outMessages=null;
				s1=null;
				s2=null;
			}
			return result;

		}
		//code added by neeraj
		public ArrayList copyData(String loanId, String userId, String businessDate) 
		{
			ArrayList<Object> result =new ArrayList<Object>();
			ArrayList<Object> in =new ArrayList<Object>();
			ArrayList<Object> out =new ArrayList<Object>();
			ArrayList outMessages = new ArrayList();
			String s1=null;
			String s2=null;
			businessDate=CommonFunction.changeFormat(businessDate);
			try
			{
				in.add(loanId);
				in.add(businessDate);
				in.add(userId);
				out.add(s1);
				out.add(s2);
				logger.info("Edit_Loan_Maker("+loanId+","+businessDate+","+userId+","+s1+","+s2+") ");
				outMessages=(ArrayList) ConnectionDAO.callSP("Edit_Loan_Maker",in,out);
				s1=CommonFunction.checkNull(outMessages.get(0));
				s2=CommonFunction.checkNull(outMessages.get(1));
		        logger.info("s1  : "+s1);
		        logger.info("s2  : "+s2);
		        result.add(s1);
		        result.add(s2);
		    }	
			catch (Exception e)
			{
				result.add("E");
				result.add("Error! Some Exception occur,Please contact Administrator... ");
				e.printStackTrace();
			}
			finally
			{
				in.clear();
				in=null;
				out.clear();
				out=null;
				outMessages.clear();
				outMessages=null;
				s1=null;
				s2=null;
			}
			return result;

		}		
		public ArrayList getPayeeName(String loanId) 
		{
			logger.info("in  getPayeeName().");
			ArrayList header=new ArrayList();
			ArrayList list=new ArrayList();	
			ArrayList header1=null;
			try
			{
				String query="select BP_ID,BP_NAME from business_partner_view WHERE LOAN_ID="+CommonFunction.checkNull(loanId).trim()+" AND BP_TYPE='CS' limit 1";
				logger.info("Query for getPayeeName :  "+query);			
				header = ConnectionDAO.sqlSelect(query);				
				for(int i=0;i<header.size();i++)
				{
					header1=(ArrayList)header.get(i);
					if(header1.size()>0)
					{
						list.add((CommonFunction.checkNull(header1.get(0)).trim()));
						list.add((CommonFunction.checkNull(header1.get(1)).trim()));
					}
				}
			}
			catch(Exception e)
			{e.printStackTrace();}	
			finally
			{
				header.clear();
				header=null;
				header1=null;
			}
			return list;			
		}
		public String validateFormNo(String loanDealId,String formNo) 
		{
			logger.info("in  validateFormNo().");
			String countStr=null;;
			String validate="N";
			int count=0;
			try
			{
				String query="select count(1) from cr_loan_dtl where loan_reference_no='"+CommonFunction.checkNull(formNo).trim()+"' and LOAN_DEAL_ID!='"+CommonFunction.checkNull(loanDealId).trim()+"';";
				logger.info("Query for validateFormNo :  "+query);			
				countStr=ConnectionDAO.singleReturn(query);	
				if(CommonFunction.checkNull(formNo).trim().equalsIgnoreCase(""))
					countStr="0";
				count=Integer.parseInt(countStr);
				if(count==0)
					validate="Y";
				query=null;		
				countStr=null;
			}
			catch(Exception e)
			{e.printStackTrace();}	
			finally{
				countStr=null;
				loanDealId=null;
				formNo=null;
			}
			return validate;	
		}
		
		public boolean saveNewInstallPlan(InstallmentPlanForCMVO ipvo) {
	       	 

			logger.info("In saveInstallPlan");
			String FromInstallment[] = ipvo.getFromInstall();
			String ToInstallment[] = ipvo.getToInstall();
			String RecoveryPercen[] = ipvo.getRecoveryPer();
			String prinAmount[] = ipvo.getPrincipalAmount();
			String instalAmount[] = ipvo.getInstallmentAmount();
			String InstallmentType= ipvo.getInstallmentType();
			String TotalInstallment=ipvo.getTotalInstallment();
			String toInstallment = ipvo.getToInstallment();
			String dueDate[] = ipvo.getDueDate();
			
			// Code Added for Edit Due date| Rahul papneja |29012018
		String editDueDate = ipvo.getEditDueDate();
		logger.info("editDueDate: "+ editDueDate);
		// Ends Here| Rahul papneja

			ArrayList qryList=new ArrayList();
			boolean status=false;
			try {
				 PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
				 StringBuilder bufInsSql =	new StringBuilder();
				 

				 StringBuilder checkQ=new StringBuilder();
				  checkQ.append("select count(*) from cr_resch_installment_plan  where LOAN_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(ipvo.getLoanId()).trim())+"");
				  
				  String count=ConnectionDAO.singleReturn(checkQ.toString());
				   
				   if(!count.equalsIgnoreCase("0"))
					  { 
					  insertPrepStmtObject = new PrepStmtObject();
					 String qry="DELETE FROM cr_resch_installment_plan WHERE LOAN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(ipvo.getLoanId()).trim())+ "'";
					 logger.info("");
				     insertPrepStmtObject.setSql(qry);
					 qryList.add(insertPrepStmtObject);
					// ConnectionDAO.sqlInsUpdDelete(qryList);						  
					  }

			
				 for(int k=0;k<FromInstallment.length;k++)  
				{
					bufInsSql =	new StringBuilder();
			     insertPrepStmtObject = new PrepStmtObject();
				  
				
				  insertPrepStmtObject = new PrepStmtObject();
if(CommonFunction.checkNull(editDueDate).equalsIgnoreCase("Y"))
			  {
					bufInsSql.append("insert into cr_resch_installment_plan (LOAN_DISBURSAL_ID_TEMP,LOAN_ID,FROM_INSTL_NO,TO_INSTL_NO,RECOVERY_PERCENT,PRINCIPAL_AMOUNT,INSTALLMENT_AMOUNT,REC_STATUS,SEQ_NO,RECOVERY_TYPE,MAKER_ID,MAKER_DATE,DUE_DATE) ");
					
					bufInsSql.append("values(?,?,?,?,?,?,?,?,?,?,?,DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)");
				
bufInsSql.append(")");

}
					else
					{					
					
					bufInsSql.append("insert into cr_resch_installment_plan (LOAN_DISBURSAL_ID_TEMP,LOAN_ID,FROM_INSTL_NO,TO_INSTL_NO,RECOVERY_PERCENT,PRINCIPAL_AMOUNT,INSTALLMENT_AMOUNT,REC_STATUS,SEQ_NO,RECOVERY_TYPE,MAKER_ID,MAKER_DATE ");
					if(CommonFunction.checkNull(InstallmentType).equalsIgnoreCase("I") ||CommonFunction.checkNull(InstallmentType).equalsIgnoreCase("J"))
						bufInsSql.append(",DUE_DATE)");
						else
							bufInsSql.append(")");
					bufInsSql.append("values(?,?,?,?,?,?,?,?,?,?,?,DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)");
					if(CommonFunction.checkNull(InstallmentType).equalsIgnoreCase("I") ||CommonFunction.checkNull(InstallmentType).equalsIgnoreCase("J"))
					bufInsSql.append(",DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND))");
else
					bufInsSql.append(")");
					}
											

					if(CommonFunction.checkNull(ipvo.getDisbursalId()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(ipvo.getDisbursalId().trim());   // LOAN_DISBURSAL_ID_TEMP
					if(CommonFunction.checkNull(ipvo.getLoanId()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(ipvo.getLoanId().trim());   // loan Id
					

					if(CommonFunction.checkNull(FromInstallment[k]).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(FromInstallment[k].trim()); //From Installment
					

			    	if(CommonFunction.checkNull(ToInstallment[k]).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
					insertPrepStmtObject.addString(ToInstallment[k].trim());// To Installment 
			    	

			    	 if((CommonFunction.checkNull(RecoveryPercen[k])).trim().equalsIgnoreCase(""))
			        		insertPrepStmtObject.addString("0.00");
					else
					 insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(RecoveryPercen[k]).trim())).toString());
			    	 
			    	 
			    	 if((CommonFunction.checkNull(prinAmount[k])).trim().equalsIgnoreCase(""))
			        		insertPrepStmtObject.addString("0.00");
					else
					 insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(prinAmount[k]).trim())).toString());// PRINCIPAL_AMOUNT   	
			    	if((CommonFunction.checkNull(instalAmount[k])).trim().equalsIgnoreCase(""))
		        		insertPrepStmtObject.addString("0.00");
				     else







				    insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(instalAmount[k]).trim())).toString());// INSTALLMENT_AMOUNT	




			    	insertPrepStmtObject.addString("P");// REC_STATUS   	
									

			    	insertPrepStmtObject.addString(""+(k+1));// SEQ_NO    
			    	

			    	if(CommonFunction.checkNull(InstallmentType).equalsIgnoreCase("I"))
			    	{

			    		insertPrepStmtObject.addString("F");
			    	}
			    	else
			    	{



			    	if(CommonFunction.checkNull(ipvo.getRecoveryType()).trim().equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
					insertPrepStmtObject.addString(ipvo.getRecoveryType().trim());// RECOVERY_TYPE
			    	}


			 	//---------------------------------------------------------
			 	if ((CommonFunction.checkNull(ipvo.getMakerId())).trim().equalsIgnoreCase(""))
			 		insertPrepStmtObject.addNull();
			 	else
			 		insertPrepStmtObject.addString((ipvo.getMakerId()).trim());
			 	if ((CommonFunction.checkNull(ipvo.getMakerDate()).trim()).equalsIgnoreCase(""))
			 		insertPrepStmtObject.addNull();
			 	else
			 		insertPrepStmtObject.addString(CommonFunction.checkNull(ipvo.getMakerDate()).trim());
			 	//---------------------------------------------------------
			 	//added by Richa

				 	// Code added for Edit Due Date| Rahul papneja| 30012018
		 // Vishal changes start
			if(CommonFunction.checkNull(editDueDate).equalsIgnoreCase("Y"))

			{
				if(CommonFunction.checkNull(dueDate[k]).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
				insertPrepStmtObject.addString(dueDate[k].trim());// Due Date  	
			}
				
				else{
				
				

				if(CommonFunction.checkNull(InstallmentType).equalsIgnoreCase("I") ||CommonFunction.checkNull(InstallmentType).equalsIgnoreCase("J"))
				{

				if(CommonFunction.checkNull(dueDate[k]).trim().equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
				insertPrepStmtObject.addString(dueDate[k].trim());// Due Date   
			}
			}
				//Ends by Richa
				  
		 	insertPrepStmtObject.setSql(bufInsSql.toString());
					logger.info("IN saveNewInstallPlan() insert query1 ### "+insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);
				  
				}
				
				




					status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
					checkQ=null;
					bufInsSql=null;
					count=null;
				} catch (Exception e) {
					e.printStackTrace();
				}


				finally{
					ipvo=null;
					FromInstallment=null;
					ToInstallment=null;
					RecoveryPercen=null;
					prinAmount=null;
					instalAmount=null;
					InstallmentType=null;
					TotalInstallment=null;
					toInstallment=null;
					qryList=null;
				}

			    logger.info("In saveNewInstallPlan......................"+status);
			return status;
		}		


		
		public ArrayList<InstallmentPlanForCMVO> getNewInstallType(String loanId) {
	          ArrayList<InstallmentPlanForCMVO> list=new ArrayList<InstallmentPlanForCMVO>();
	          ArrayList mainList=new ArrayList ();
				ArrayList subList =new ArrayList();


	      		
	      		try{
	      			StringBuilder query=new StringBuilder();
	               	 query.append(" select distinct FROM_INSTL_NO,TO_INSTL_NO,RECOVERY_PERCENT,LOAN_INSTALLMENT_TYPE,LOAN_NO_OF_INSTALLMENT,PRINCIPAL_AMOUNT,INSTALLMENT_AMOUNT,LOAN_RATE_TYPE,LOAN_LOAN_AMOUNT,RECOVERY_TYPE,DATE_FORMAT(loan_repay_eff_date,'"+dateFormat+"'),DATE_FORMAT(L.LOAN_MATURITY_DATE,'"+dateFormat+"'),DATE_FORMAT(d.due_date,'"+dateFormat+"'),L.EDIT_DUE_DATE,DATE_FORMAT(C.NEXT_DUE_DATE,'"+dateFormat+"') "+  // Query changed for Edit Due Date| Rahul papneja |08032018
	               			" ,LOAN_REPAYMENT_FREQ "+
	      				"  from cr_loan_dtl L left JOIN cr_resch_installment_plan D on D.LOAN_ID=L.LOAN_ID "+  
	               			"JOIN CR_LOAN_DISBURSAL_DTL_TEMP C on C.LOAN_ID=L.LOAN_ID"+
	      				" where L.LOAN_ID="+loanId);

	      			
	      		logger.info("getNewInstallType Queryl: "+query);

	      		
	      		mainList=ConnectionDAO.sqlSelect(query.toString());
				for(int i=0;i<mainList.size();i++)

				{
					subList= (ArrayList)mainList.get(i);
					if(subList.size()>0){
						InstallmentPlanForCMVO ipVo= new InstallmentPlanForCMVO();
	      				ipVo = new InstallmentPlanForCMVO();  
	      				if((CommonFunction.checkNull(subList.get(3)).equalsIgnoreCase("E")||CommonFunction.checkNull(subList.get(3)).equalsIgnoreCase("P"))&& CommonFunction.checkNull(subList.get(13)).equalsIgnoreCase("N"))

	      				{
	      					if(CommonFunction.checkNull(subList.get(0)).equalsIgnoreCase("") || CommonFunction.checkNull(subList.get(1)).equalsIgnoreCase("")){
	      						ipVo.setFromInstallment("1");
	      						ipVo.setToInstallment((CommonFunction.checkNull(subList.get(4))).trim());
	      					}else{
	      						ipVo.setFromInstallment((CommonFunction.checkNull(subList.get(0))).trim());
	      						ipVo.setToInstallment((CommonFunction.checkNull(subList.get(1))).trim());


	      					}
	      					
	      					 if(!CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase("")){
	          				     Number RecoveryPercen = myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());

	   	 			   
	          				     logger.info("RecoveryPercen: "+RecoveryPercen);			
	   	 			             ipVo.setRecoveryPercen(myFormatter.format(RecoveryPercen));

	          				      }else{
	          				     ipVo.setRecoveryPercen("0");

	          				          }
	      				    ipVo.setInstallmentType((CommonFunction.checkNull(subList.get(3))).trim());
	       			  	ipVo.setTotalInstallment((CommonFunction.checkNull(subList.get(4))).trim());

	       			
	       			      //ipVo.setRecoveryType("P");


	      				}
	      				else{
	      				    ipVo.setFromInstallment((CommonFunction.checkNull(subList.get(0))).trim());
	      				    ipVo.setToInstallment((CommonFunction.checkNull(subList.get(1))).trim());
	      				    if(!CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase("")){
	      				     Number RecoveryPercen = myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());

		 			   
	      				     logger.info("RecoveryPercen: "+RecoveryPercen);			
		 			             ipVo.setRecoveryPercen(myFormatter.format(RecoveryPercen));

	      				      }else{
	      				     ipVo.setRecoveryPercen("0");

	      				          }
	      				    ipVo.setInstallmentType((CommonFunction.checkNull(subList.get(3))).trim());
	      				    ipVo.setTotalInstallment((CommonFunction.checkNull(subList.get(4))).trim());



	      				 
	           				
	      				    }
	      				ipVo.setRateType((CommonFunction.checkNull(subList.get(7))).trim());

	     				
	      				  if(!CommonFunction.checkNull(subList.get(5)).equalsIgnoreCase("")){
	       				     Number princam = myFormatter.parse((CommonFunction.checkNull(subList.get(5))).trim());

	   	 			   
	       				     logger.info("princ amount: "+princam);			
	   	 			         ipVo.setPrinAm(myFormatter.format(princam));

	       				      }else{
	       				     ipVo.setPrinAm("0.00");

	       				          }
	        				if(!CommonFunction.checkNull(subList.get(6)).equalsIgnoreCase("")){
	       				     Number instam = myFormatter.parse((CommonFunction.checkNull(subList.get(6))).trim());

	   	 			   
	       				     logger.info("instamount: "+instam);			
	   	 			         ipVo.setInstalAm(myFormatter.format(instam));

	       				      }else{
	       				     ipVo.setInstalAm("0.00");


	       				          }
	     				
	     				if(!CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("")){
	       				     Number instam = myFormatter.parse((CommonFunction.checkNull(subList.get(8))).trim());

	   	 			   
	       				     logger.info("setLoanAmount: "+instam);			
	   	 			         ipVo.setLoanAmount(myFormatter.format(instam));

	       				      }else{
	       				     ipVo.setLoanAmount("0.00");

	       				          }
	     				ipVo.setRecoveryType((CommonFunction.checkNull(subList.get(9))).trim());
	     				//Richa space starts
	     				ipVo.setRepayeffdate((CommonFunction.checkNull(subList.get(10))).trim());
	     				ipVo.setMaxDate((CommonFunction.checkNull(subList.get(11))).trim());
	     				ipVo.setDueDatee((CommonFunction.checkNull(subList.get(12))).trim());
	     				//Richa space ends

	//COde added for Edit Due Date| Rahul papneja |29012018
   				// Vishal changes start
   				ipVo.setEditDueDate((CommonFunction.checkNull(subList.get(13))).trim());
   				ipVo.setInsNextDueDate((CommonFunction.checkNull(subList.get(14))).trim());
   				// Vishal changes end
   			    // added one filed for auto fill due date in installment plan|DEAL_REPAYMENT_FREQ|Vijendra Singh	
   				ipVo.setFrequency((CommonFunction.checkNull(subList.get(15))).trim());     	     				
   				//End Code Vijendra Singh
   				// Ends Here
   					list.add(ipVo);
	      				 ipVo=null;


	      			     }
	      		  }
				query=null;
	      		}catch(Exception e){
	      			e.printStackTrace();

	      		}
	      		finally{
	      			loanId=null;

	      			mainList.clear();
	      			mainList=null;
	      			subList.clear();
	      			subList=null;
	      		}
	      		return list;


	      	}
		
		// Code Added for Handling Stage for New Installment Type| Rahul papneja
		public ArrayList<InstallmentPlanForCMVO> getNewInstallmentType(String loanId,String stage) {
	          ArrayList<InstallmentPlanForCMVO> list=new ArrayList<InstallmentPlanForCMVO>();
	          ArrayList mainList=new ArrayList ();
				ArrayList subList =new ArrayList();


	      		
	      		try{
	      			StringBuilder query=new StringBuilder();
	      			logger.info("Stage:::::"+stage);
	      			if(stage.equalsIgnoreCase("DIM")){
	               	 query.append(" select distinct FROM_INSTL_NO,TO_INSTL_NO,RECOVERY_PERCENT,LOAN_INSTALLMENT_TYPE,LOAN_NO_OF_INSTALLMENT,PRINCIPAL_AMOUNT,INSTALLMENT_AMOUNT,LOAN_RATE_TYPE,LOAN_LOAN_AMOUNT,RECOVERY_TYPE,DATE_FORMAT(loan_repay_eff_date,'"+dateFormat+"'),IF(L.LOAN_INSTALLMENT_TYPE='S' AND  IFNULL(C.LOAN_MATURITY_DATE,'0000-00-00')<>'0000-00-00',DATE_FORMAT(C.LOAN_MATURITY_DATE,'"+dateFormat+"'),(select DATE_FORMAT(date_add(repay_eff_date,INTERVAL tenure_in_day DAY),'"+dateFormat+"'))),DATE_FORMAT(d.due_date,'"+dateFormat+"'),L.EDIT_DUE_DATE,DATE_FORMAT(C.NEXT_DUE_DATE,'"+dateFormat+"') "+  // Query changed for Edit Due Date| Rahul papneja |08032018
	               			" ,LOAN_REPAYMENT_FREQ "+
	      				"  from cr_loan_dtl L left JOIN cr_resch_installment_plan D on D.LOAN_ID=L.LOAN_ID "+  
	               			"JOIN CR_LOAN_DISBURSAL_DTL_TEMP C on C.LOAN_ID=L.LOAN_ID"+
	      				" where L.LOAN_ID="+loanId);
	      			}else
	      			{
	      			 	 query.append(" select distinct FROM_INSTL_NO,TO_INSTL_NO,RECOVERY_PERCENT,LOAN_INSTALLMENT_TYPE,LOAN_NO_OF_INSTALLMENT,PRINCIPAL_AMOUNT,INSTALLMENT_AMOUNT,LOAN_RATE_TYPE,LOAN_LOAN_AMOUNT,RECOVERY_TYPE,DATE_FORMAT(loan_repay_eff_date,'"+dateFormat+"'),DATE_FORMAT(L.LOAN_MATURITY_DATE,'"+dateFormat+"'),DATE_FORMAT(d.due_date,'"+dateFormat+"'),L.EDIT_DUE_DATE,DATE_FORMAT(C.NEXT_DUE_DATE,'"+dateFormat+"') "+  // Query changed for Edit Due Date| Rahul papneja |08032018
	 	               			" ,LOAN_REPAYMENT_FREQ "+
	 	      				"  from cr_loan_dtl L left JOIN cr_resch_installment_plan D on D.LOAN_ID=L.LOAN_ID "+  
	 	               			"JOIN CR_LOAN_DISBURSAL_DTL C on C.LOAN_ID=L.LOAN_ID"+
	 	      				" where L.LOAN_ID="+loanId);
	      			}
	      			
	      		logger.info("getNewInstallType Queryl: "+query);

	      		
	      		mainList=ConnectionDAO.sqlSelect(query.toString());
				for(int i=0;i<mainList.size();i++)

				{
					subList= (ArrayList)mainList.get(i);
					if(subList.size()>0){
						InstallmentPlanForCMVO ipVo= new InstallmentPlanForCMVO();
	      				ipVo = new InstallmentPlanForCMVO();  
	      				if((CommonFunction.checkNull(subList.get(3)).equalsIgnoreCase("E")||CommonFunction.checkNull(subList.get(3)).equalsIgnoreCase("P"))&& CommonFunction.checkNull(subList.get(13)).equalsIgnoreCase("N"))

	      				{
	      					if(CommonFunction.checkNull(subList.get(0)).equalsIgnoreCase("") || CommonFunction.checkNull(subList.get(1)).equalsIgnoreCase("")){
	      						ipVo.setFromInstallment("1");
	      						ipVo.setToInstallment((CommonFunction.checkNull(subList.get(4))).trim());
	      					}else{
	      						ipVo.setFromInstallment((CommonFunction.checkNull(subList.get(0))).trim());
	      						ipVo.setToInstallment((CommonFunction.checkNull(subList.get(1))).trim());


	      					}
	      					
	      					 if(!CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase("")){
	          				     Number RecoveryPercen = myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());

	   	 			   
	          				     logger.info("RecoveryPercen: "+RecoveryPercen);			
	   	 			             ipVo.setRecoveryPercen(myFormatter.format(RecoveryPercen));

	          				      }else{
	          				     ipVo.setRecoveryPercen("0");

	          				          }
	      				    ipVo.setInstallmentType((CommonFunction.checkNull(subList.get(3))).trim());
	       			  	ipVo.setTotalInstallment((CommonFunction.checkNull(subList.get(4))).trim());

	       			
	       			      //ipVo.setRecoveryType("P");


	      				}
	      				else{
	      				    ipVo.setFromInstallment((CommonFunction.checkNull(subList.get(0))).trim());
	      				    ipVo.setToInstallment((CommonFunction.checkNull(subList.get(1))).trim());
	      				    if(!CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase("")){
	      				     Number RecoveryPercen = myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());

		 			   
	      				     logger.info("RecoveryPercen: "+RecoveryPercen);			
		 			             ipVo.setRecoveryPercen(myFormatter.format(RecoveryPercen));

	      				      }else{
	      				     ipVo.setRecoveryPercen("0");

	      				          }
	      				    ipVo.setInstallmentType((CommonFunction.checkNull(subList.get(3))).trim());
	      				    ipVo.setTotalInstallment((CommonFunction.checkNull(subList.get(4))).trim());



	      				 
	           				
	      				    }
	      				ipVo.setRateType((CommonFunction.checkNull(subList.get(7))).trim());

	     				
	      				  if(!CommonFunction.checkNull(subList.get(5)).equalsIgnoreCase("")){
	       				     Number princam = myFormatter.parse((CommonFunction.checkNull(subList.get(5))).trim());

	   	 			   
	       				     logger.info("princ amount: "+princam);			
	   	 			         ipVo.setPrinAm(myFormatter.format(princam));

	       				      }else{
	       				     ipVo.setPrinAm("0.00");

	       				          }
	        				if(!CommonFunction.checkNull(subList.get(6)).equalsIgnoreCase("")){
	       				     Number instam = myFormatter.parse((CommonFunction.checkNull(subList.get(6))).trim());

	   	 			   
	       				     logger.info("instamount: "+instam);			
	   	 			         ipVo.setInstalAm(myFormatter.format(instam));

	       				      }else{
	       				     ipVo.setInstalAm("0.00");


	       				          }
	     				
	     				if(!CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("")){
	       				     Number instam = myFormatter.parse((CommonFunction.checkNull(subList.get(8))).trim());

	   	 			   
	       				     logger.info("setLoanAmount: "+instam);			
	   	 			         ipVo.setLoanAmount(myFormatter.format(instam));

	       				      }else{
	       				     ipVo.setLoanAmount("0.00");

	       				          }
	     				ipVo.setRecoveryType((CommonFunction.checkNull(subList.get(9))).trim());
	     				//Richa space starts
	     				ipVo.setRepayeffdate((CommonFunction.checkNull(subList.get(10))).trim());
	     				ipVo.setMaxDate((CommonFunction.checkNull(subList.get(11))).trim());
	     				ipVo.setDueDatee((CommonFunction.checkNull(subList.get(12))).trim());
	     				//Richa space ends

	//COde added for Edit Due Date| Rahul papneja |29012018
 				// Vishal changes start
 				ipVo.setEditDueDate((CommonFunction.checkNull(subList.get(13))).trim());
 				ipVo.setInsNextDueDate((CommonFunction.checkNull(subList.get(14))).trim());
 				// Vishal changes end
 			    // added one filed for auto fill due date in installment plan|DEAL_REPAYMENT_FREQ|Vijendra Singh	
 				ipVo.setFrequency((CommonFunction.checkNull(subList.get(15))).trim());     	     				
 				//End Code Vijendra Singh
 				// Ends Here
 					list.add(ipVo);
	      				 ipVo=null;


	      			     }
	      		  }
				query=null;
	      		}catch(Exception e){
	      			e.printStackTrace();

	      		}
	      		finally{
	      			loanId=null;

	      			mainList.clear();
	      			mainList=null;
	      			subList.clear();
	      			subList=null;
	      		}
	      		return list;


	      	}
		
		
		
		
		public String getdisbrsedAmt(String loanId) 
		   {
		   	String disbrsalAmt="0";
		   	String query="select SUM(IFNULL(disbursal_amount,0)) FROM CR_LOAN_DISBURSAL_DTL WHERE LOAN_ID = '"+loanId+"' AND REC_STATUS='A'";
		   	disbrsalAmt=ConnectionDAO.singleReturn(query);	
		   	if(CommonFunction.checkNull(disbrsalAmt).trim().equalsIgnoreCase(""))
		   		disbrsalAmt="0.00";
		   	query=null;
		   	loanId=null;
		   	return disbrsalAmt;
		   } 
		public String getdisbrsalAmt(String loanId) 
		   {
		   	String disbrsalAmt="0";
		   	String query=" select sum(disbursal_amount)disbursal_amount from cr_loan_disbursal_dtl_temp where loan_id='"+CommonFunction.checkNull(loanId).trim()+"'";
		   	disbrsalAmt=ConnectionDAO.singleReturn(query);	
		   	if(CommonFunction.checkNull(disbrsalAmt).trim().equalsIgnoreCase(""))
		   		disbrsalAmt="0.00";
		   	query=null;
		   	loanId=null;
		   	return disbrsalAmt;
		   }
		
		public ArrayList getresultForLoan(String loanId) {
			 
			ArrayList searchlist = new ArrayList();
			
			ArrayList<LoanDetailVo> resultList = new ArrayList<LoanDetailVo>();

			try {
				logger.info("In getresultForOPS().....................................Dao Impl....."+loanId);

				StringBuilder bufInsSql = new StringBuilder();
				LoanDetailVo vo = new LoanDetailVo();
				
				bufInsSql.append(" select  VALUE,DESCRIPTION  from generic_master a join cr_loan_dtl b  on( (instr(concat('|',LOAN_CLASSIFICATION,'|'),concat('|',a.VALUE,'|'))>0)) where generic_key='LOAN_CLASSIFICATION' and loan_id ='"+ loanId+ "' " );
				searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
				logger.info("IN searchUserBranchEdit() search query1 ### "+ bufInsSql.toString());
				int size=searchlist.size();
				for (int i = 0; i <size ; i++) {
					ArrayList data = (ArrayList) searchlist.get(i);
					if (data.size() > 0) {
						vo = new LoanDetailVo();
						vo.setLoanClassificationId(CommonFunction.checkNull(data.get(0)));
						vo.setLoanClassificationLabel(CommonFunction.checkNull(data.get(1)));
						resultList.add(vo);
						vo=null;
					}
					data=null;
				}
				bufInsSql=null;
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally
			{
				
				searchlist.clear();
				searchlist=null;
			}
			return resultList;
		}
		
		
		public ArrayList<Object> getPaymentModes() {
	    	logger.info("in getPaymentModes()  ");  		
			ArrayList<Object> list=new ArrayList<Object>();
		  	try
		  	{	  		
		  		String query="select value,description from generic_master where generic_key='repayment_mode' and rec_status='A'";
		  		logger.info("in getPaymentModes()  :  "+query);
		  		LoanDetailForCMVO loanVo = null; 
		  		ArrayList source = ConnectionDAO.sqlSelect(query);
		  		int size=source.size();
		  		for(int i=0;i<size;i++)
		  		{
		  			ArrayList subEduDetail=(ArrayList)source.get(i);
		  			if(subEduDetail.size()>0)
		  			{
		  				loanVo = new LoanDetailForCMVO();
		  				loanVo.setPaymentModeId((CommonFunction.checkNull(subEduDetail.get(0)).toString()));
		  				loanVo.setRepaymentMode((CommonFunction.checkNull(subEduDetail.get(1)).toString()));
		  				
		  				list.add(loanVo);	  				
		  			}
		  		}
		  		}catch(Exception e){
		  			e.printStackTrace();
		  		}
		  		return list;
	}


		public boolean saveSectorTypeDetails(Object ob,String dealId,String loanId) {
			LoanDetailForCMVO vo = (LoanDetailForCMVO) ob;
			boolean status = false;
			
			logger.info("In saveSectorTypeDetails.........inside ejb server file...........Dao Impl");
			ArrayList qryList = new ArrayList();
			String stat = "X";
			int exist_loan_id=0;
			PrepStmtObject insertPrepStmtObject=null;
			StringBuilder bufInsSql = null;
/*
			String query = "select CASE_TYPE_CODE from LEG_CASE_TYPE_DEF_M where CASE_TYPE_CODE='"
					+ StringEscapeUtils.escapeSql(vo.getCaseTypeCode().trim()) + "'";
			logger.info("In insertCaseTypeMaster.......inside ejb server file..........Dao Impl"
							+ query);
			boolean st = ConnectionDAO.checkStatus(query);
			*/
			try {
				
				exist_loan_id=Integer.parseInt(ConnectionDAO.singleReturn("select count(1) from cr_loan_sector_type_dtl where DEAL_ID='"+dealId+"' AND LOAN_ID='"+loanId+"'"));
				logger.info("userNameQ1::::"+exist_loan_id);
				logger.info("userNameQ1::::"+dealId);
					if (vo.getRecStatus() != null
							&& vo.getRecStatus().equals("on")) {
						stat = "A";
					} else {
						stat = "X";

					}

					logger.info("In insert Case Type master");
					if(exist_loan_id==0){
					bufInsSql = new StringBuilder();
					insertPrepStmtObject = new PrepStmtObject();
					bufInsSql.append("insert into cr_loan_sector_type_dtl(deal_id,agri_doc,agri_land,name_on_agri_doc,relation_with_hirer,REC_STATUS,MAKER_ID,MAKER_DATE,LOAN_ID)");
					bufInsSql.append(" values ( ");
					bufInsSql.append(" ?,"); //deal_id
					bufInsSql.append(" ?,"); //agri_doc
					bufInsSql.append(" ?,"); //agri_land
					bufInsSql.append(" ?,"); //name_on_agri_land
					bufInsSql.append(" ?,"); //relation_with_hirere
					bufInsSql.append(" ?,"); //rec_status
					bufInsSql.append(" ?,"); //makerId
					bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND), ");
					bufInsSql.append(" ?)"); //makerId
				
					if (CommonFunction.checkNull(dealId).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(dealId);
					
					if (CommonFunction.checkNull(vo.getAgriDocs()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getAgriDocs().toUpperCase().trim());
					
					if (CommonFunction.checkNull(vo.getAgriLand()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getAgriLand().toUpperCase().trim());
					
					if (CommonFunction.checkNull(vo.getNameAgriDoc()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getNameAgriDoc().toUpperCase().trim());
					
					if (CommonFunction.checkNull(vo.getRelationWithHirer()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getRelationWithHirer().toUpperCase().trim());
					

					if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(stat);
					

					if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getMakerId());
					
					
					if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getMakerDate());
					if (CommonFunction.checkNull(loanId).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(loanId);
					
					/*
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
					
					*/

					insertPrepStmtObject.setSql(bufInsSql.toString());
					logger.info("IN insertCaseTypeMaster() insert query1 ### "+ insertPrepStmtObject.printQuery());
					qryList.add(insertPrepStmtObject);
					status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
					}
					else
					{
						
						insertPrepStmtObject = new PrepStmtObject();
						bufInsSql = new StringBuilder();
						
						bufInsSql.append("UPDATE cr_loan_sector_type_dtl SET agri_doc=?,agri_land=?,name_on_agri_doc=?,relation_with_hirer=?,REC_STATUS=?,MAKER_ID=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND)"); 
						bufInsSql.append(" where DEAL_ID=? AND LOAN_ID=?" );
						
						
						
						if (CommonFunction.checkNull(vo.getAgriDocs()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(vo.getAgriDocs().toUpperCase().trim());
						
						if (CommonFunction.checkNull(vo.getAgriLand()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(vo.getAgriLand().toUpperCase().trim());
						
						if (CommonFunction.checkNull(vo.getNameAgriDoc()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(vo.getNameAgriDoc().toUpperCase().trim());
						
						if (CommonFunction.checkNull(vo.getRelationWithHirer()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(vo.getRelationWithHirer().toUpperCase().trim());
						

						if (CommonFunction.checkNull(stat).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(stat);
						

						if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(vo.getMakerId());
						
						
						if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(vo.getMakerDate());
						if (CommonFunction.checkNull(dealId).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(dealId);
						
						if (CommonFunction.checkNull(loanId).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(loanId);
						
						insertPrepStmtObject.setSql(bufInsSql.toString());
						
						logger.info("IN updateCaseMarkingMaker() insert query1 ### "+ insertPrepStmtObject.printQuery());
						
						qryList.add(insertPrepStmtObject);
									
						status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
					 
					}
					logger.info("In saveCountryData......................"+ status);
				

			} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				qryList.clear();
				qryList = null;
				
			         }
			return status;

		}
		
		public ArrayList editSectorTypeDetails(Object ob) {

			ArrayList searchlist = new ArrayList();
			LoanDetailForCMVO vo = (LoanDetailForCMVO)ob;
			ArrayList<LoanDetailForCMVO> sectorTypeList = new ArrayList<LoanDetailForCMVO>();
			logger.info("ratioid in searchBenchMarkRatioEdit &***************************** = "+vo.getLoanDealId());

			try {
				

				StringBuilder bufInsSql = new StringBuilder();
				bufInsSql.append(" select  DEAL_ID,AGRI_DOC,AGRI_LAND,NAME_ON_AGRI_DOC,RELATION_WITH_HIRER,REC_STATUS,loan_id  from cr_loan_sector_type_dtl ");
				
				bufInsSql.append("  WHERE DEAL_ID='"+StringEscapeUtils.escapeSql(vo.getLoanDealId())+"' and loan_id='"+StringEscapeUtils.escapeSql(vo.getLoanId())+"'");
				logger.info("search Query...." + bufInsSql);

				searchlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
				logger.info("IN editSectorTypeDetails() search query1 ### "+ bufInsSql.toString());
				logger.info("editSectorTypeDetails " + searchlist.size());
				for (int i = 0; i < searchlist.size(); i++) {
					logger.info("editSectorTypeDetails " + searchlist.get(i).toString());
					ArrayList data = (ArrayList) searchlist.get(i);
					if (data.size() > 0) {
						LoanDetailForCMVO caseTypeDataVo = new LoanDetailForCMVO();
						
						caseTypeDataVo.setLoanDealId(CommonFunction.checkNull(data.get(0)).toString());
						caseTypeDataVo.setAgriDocs(CommonFunction.checkNull(data.get(1)).toString());	
						caseTypeDataVo.setAgriLand(CommonFunction.checkNull(data.get(2)).toString());
						caseTypeDataVo.setNameAgriDoc(CommonFunction.checkNull(data.get(3)).toString());
						caseTypeDataVo.setRelationWithHirer(CommonFunction.checkNull(data.get(4)).toString());
						caseTypeDataVo.setRecStatus(CommonFunction.checkNull(data.get(5)).toString());
						caseTypeDataVo.setLoanId(CommonFunction.checkNull(data.get(6)).toString());
						sectorTypeList.add(caseTypeDataVo);

					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				searchlist.clear();
				searchlist = null;
				
			         }
			return sectorTypeList;
	}
		
		//Richa
		public ArrayList<Object> getAgriDocsList() {
			
			ArrayList list=new ArrayList();
			try{
				StringBuffer query=new StringBuffer();
			 query.append("SELECT VALUE,DESCRIPTION FROM generic_master  "
		        +"where GENERIC_KEY='AGRI_DOC' and REC_STATUS='A'");
			logger.info("In creditProcessingDaoI getMortageList"+query.toString());
			
			ArrayList agriDoc = ConnectionDAO.sqlSelect(query.toString());
			query=null;
			for(int i=0;i<agriDoc.size();i++){
				ArrayList sublist = (ArrayList) agriDoc.get(i);
				if(sublist.size()>0){
					CodeDescVo av=new CodeDescVo();
				    av.setId((CommonFunction.checkNull(sublist.get

		(0))).trim());
					av.setName((CommonFunction.checkNull

		(sublist.get(1))).trim());
					list.add(av);
				}
			}
			}catch(Exception e){
				e.printStackTrace();
			}
			return list;
		}
		
		
		
		public String getDeal(String loanId) 
		   {
			logger.info("loan_id::"+loanId);
			String dealId="";
		   	String query=" select loan_deal_id from cr_loan_dtl where loan_id= '"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()+"'  ";
		   	dealId=ConnectionDAO.singleReturn(query);	
			logger.info("dealllll Query::::::::::::::::::::::::::::::::::::"+query);
		   	query=null;
		   	
		   	return dealId;
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
		qry1="SELECT count(1) FROM cr_business_partner_dtl where partner_ID='"+id+"' and LOAN_ID='"+vo.getLoanId()+"' and RATE='"+vo.getEffectiveRate()+"'and PERCENTAGE='"+vo.getPartnerPercentage()+"' and partner_type='"+businessType+"' and service_partner_flag = '"+vo.getServicingPartnerFlag()+"' and loan_amount='"+vo.getPartnerAmount()+"' and IFNULL(lead_partner_flag,'N') ='"+vo.getLeadPartnerFlag()+"' ";
		exist_id1=Integer.parseInt(ConnectionDAO.singleReturn(qry1));
		logger.info("userNameQ1::::"+exist_id1);
		logger.info("qry1 amanddd:::"+qry1);
		if(exist_id1>0){
			result="EX";
			return result;
		}
		if (CommonFunction.checkNull(vo.getLeadPartnerFlag()).equalsIgnoreCase("Y"))
		{
		qry7="SELECT count(1) FROM cr_business_partner_dtl where  LOAN_ID='"+vo.getLoanId()+"'  and IFNULL(lead_partner_flag,'N') ='Y' and partner_type='"+businessType+"' and  partner_ID <>'"+id+"' ";
		exist_id1=Integer.parseInt(ConnectionDAO.singleReturn(qry7));
		logger.info("userNameQ1::::"+exist_id1);
		logger.info("qry7:::"+qry7);
		if(exist_id1>0){
			result="leadPartnerExist";
			return result;
		}
		}
		qry3="SELECT ifnull(sum(percentage),0) FROM cr_business_partner_dtl where  LOAN_ID='"+vo.getLoanId()+"' and partner_type='"+businessType+"'";
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
				
				qry6="SELECT self_flag FROM com_partner_details_m where   partner_ID='"+id+"' ";
				 logger.info("the self flag : "+qry6);
				flag =ConnectionDAO.singleReturn(qry6);
				
				//parvez ends
				
		//end by saorabh
		
		
		qry="SELECT count(1) FROM cr_business_partner_dtl where partner_ID='"+id+"' and LOAN_ID='"+vo.getLoanId()+"' and partner_type='"+businessType+"' ";
		 logger.info("the qry of count is : "+qry);
		exist_id=Integer.parseInt(ConnectionDAO.singleReturn(qry));
		
				logger.info("userNameQ1::::"+exist_id);
				logger.info("id for partner qry dtl::::"+id);
			//amandeep adds	
				if(vo.getServicingPartnerFlag().equalsIgnoreCase("Y"))
				{
					qry5="SELECT ifnull(LOAN_EFF_RATE,0) FROM cr_loan_dtl where  LOAN_ID='"+vo.getLoanId()+"' ";
					 logger.info("the qry5 of count is : "+qry5);
					 loanRate =ConnectionDAO.singleReturn(qry5);
				}
				//amandeep ends
					if(exist_id==0){
						
						qry2="SELECT count(1) FROM cr_business_partner_dtl where  LOAN_ID='"+vo.getLoanId()+"' and partner_type='"+businessType+"' and service_partner_flag='Y'";
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
					bufInsSql.append("insert into cr_business_partner_dtl(partner_ID,LOAN_ID,EFFECTIVE_RATE,PERCENTAGE,PARTNER_TYPE,MAKER_ID,LOAN_AMOUNT,self_flag,MAKER_DATE,SERVICE_PARTNER_FLAG,LEAD_PARTNER_FLAG,RATE_TYPE,RATE)");
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
					if (CommonFunction.checkNull(vo.getLbxpartnerId()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getLbxpartnerId());
					
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
						bufInsSql.append(" where partner_ID=? and loan_id=?" );
						
						if (CommonFunction.checkNull(vo.getLbxpartnerId()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(vo.getLbxpartnerId());
						if (CommonFunction.checkNull(vo.getLoanId()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(vo.getLoanId().trim());
						insertPrepStmtObject.setSql(bufInsSql.toString());
						
						qryList.add(insertPrepStmtObject);
						
						
						status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
						
						
						
						qry2="SELECT count(1) FROM cr_business_partner_dtl where  LOAN_ID='"+vo.getLoanId()+"' and partner_type='"+businessType+"' and service_partner_flag='Y' ";
						 logger.info("the qry2 of count is : "+qry2);
						prevent=Integer.parseInt(ConnectionDAO.singleReturn(qry2));
								logger.info("Prevent Service Partner::::"+prevent);
								if(prevent>0 && vo.getServicingPartnerFlag().equalsIgnoreCase("Y"))
								{
									return "prevent";
								}
					
						
						
						qry4="SELECT ifnull(percentage,0) FROM cr_business_partner_dtl where partner_ID='"+id+"' and LOAN_ID='"+vo.getLoanId()+"' and partner_type='"+businessType+"'";
						 logger.info("the qry4 of count is : "+qry4);
						float  value =Float.parseFloat(ConnectionDAO.singleReturn(qry4));
						sum=(sum-value)+Float.parseFloat(vo.getPartnerPercentage());
						if(sum>100.00)
						{
							return "contributionAmount";
						}
						insertPrepStmtObject = new PrepStmtObject();
						bufInsSql = new StringBuilder();
						bufInsSql.append("UPDATE cr_business_partner_dtl SET partner_ID=?,LOAN_ID=?,EFFECTIVE_RATE=?,percentage=?,partner_type=?,MAKER_ID=?,LOAN_AMOUNT=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),service_partner_flag=?,self_flag=?,LEAD_PARTNER_FLAG=?,RATE_TYPE=?,RATE=?  "); 
						bufInsSql.append(" where partner_ID=? and loan_id=? and partner_type=? " );
						
						
						
						if (CommonFunction.checkNull(vo.getLbxpartnerId()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(vo.getLbxpartnerId());
						
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
						
						
						if (CommonFunction.checkNull(vo.getLbxpartnerId()).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(vo.getLbxpartnerId());
						
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
					if(CommonFunction.checkNull(result).equalsIgnoreCase("S") || CommonFunction.checkNull(result).equalsIgnoreCase("M"))
					{
					insertPrepStmtObject=null;
					bufInsSql=null;
						
					insertPrepStmtObject = new PrepStmtObject();
					bufInsSql = new StringBuilder();
					bufInsSql.append("UPDATE CR_LOAN_DTL SET REFRESH_FLAG=CONCAT(left(REFRESH_FLAG,3-1),'Y',right(REFRESH_FLAG,lenGTH(REFRESH_FLAG)-3)) WHERE LOAN_ID=?"); 
					
					if (CommonFunction.checkNull(vo.getLoanId()).equalsIgnoreCase(""))
						insertPrepStmtObject.addNull();
					else
						insertPrepStmtObject.addString(vo.getLoanId().trim());
					insertPrepStmtObject.setSql(bufInsSql.toString());
					
					rspQryList.add(insertPrepStmtObject);
					
					
					ConnectionDAO.sqlInsUpdDeletePrepStmt(rspQryList);
						
					}	

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
			try
			{
				StringBuilder query =new StringBuilder();
				 query.append(" select a.BP_ID,a.partner_ID,b.name,a.LOAN_ID,c.loan_no,a.RATE,round(ifnull(a.percentage,0),2),case a.service_partner_flag when 'Y' then 'Yes' when 'N' then 'No' end as service_partner_flag, round(ifnull(loan_amount,0),2),case a.lead_partner_flag when 'Y' then 'Yes' when 'N' then 'No' end as leadflag ,a.RATE_TYPE,a.EFFECTIVE_RATE,case ifnull(a.RATE_TYPE,'E') when 'E' then 'Effective' when 'F' then 'Flat' end as rate_type_desc from cr_business_partner_dtl a join com_partner_details_m b on a.partner_ID=b.partner_ID left join cr_loan_dtl c on c.loan_id=a.loan_id "+
								" where a.LOAN_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()+" and a.partner_type='"+businessType+"' ");
				
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


		@Override
		public ArrayList<LoanDetailForCMVO> getPartnerBusDetails(LoanDetailForCMVO loanDetailForCMVO,
				String id, String loanId,String businessType) {
			ArrayList list=new ArrayList();
			try
			{
				StringBuilder query =new StringBuilder();
				 query.append(" select a.BP_ID,a.partner_ID,b.name,a.LOAN_ID,c.loan_no,a.RATE ,round(ifnull(A.PERCENTAGE,0),2),case a.service_partner_flag when 'Y' then 'Yes' when 'N' then 'No' end as service_partner_flag,round(ifnull(loan_amount,0),2),case a.lead_partner_flag when 'Y' then 'Yes' when 'N' then 'No' end as leadflag,a.RATE_TYPE,a.EFFECTIVE_RATE,case ifnull(a.RATE_TYPE,'E') when 'E' then 'Effective' when 'F' then 'Flat' end as rate_type_desc from cr_business_partner_dtl a join com_partner_details_m b on a.partner_ID=b.partner_ID left join cr_loan_dtl c on c.loan_id=a.loan_id "+
								" where a.LOAN_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()+" and a.BP_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id)).trim()+" and a.partner_type='"+businessType+"' ");
				
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
			return status;}


		@Override
		public ArrayList<LoanDetailForCMVO> getPartnerDetailsforPopUp(
				LoanDetailForCMVO loanDetailForCMVO, String loanId,
				String businessType) {
			ArrayList list=new ArrayList();
			String partnerType="";
			PrepStmtObject insertPrepStmtObject=null;
			StringBuilder bufInsSql = null;
			logger.info("businessType "+businessType);
			//String query1=" select FUNDING_TYPE from cr_loan_dtl where loan_id ='"+loanId+"'  ";
		   	
			try
			{
			//	partnerType=ConnectionDAO.singleReturn(query1);	
				StringBuilder query =new StringBuilder();
				 query.append(" select a.BP_ID,a.partner_ID,b.name,a.LOAN_ID,c.loan_no,a.RATE ,round(ifnull(a.PERCENTAGE,0),2),case a.service_partner_flag when 'Y' then 'Yes' when 'N' then 'No' end as service_partner_flag,round(ifnull(loan_amount,0),2),case a.lead_partner_flag when 'Y' then 'Yes' when 'N' then 'No' end as leadflag,a.EFFECTIVE_RATE,a.RATE_TYPE,case ifnull(a.RATE_TYPE,'E') when 'E' then 'Effective' when 'F' then 'Flat' end as rate_type_desc  from cr_business_partner_dtl a join com_partner_details_m b on a.partner_ID=b.partner_ID left join cr_loan_dtl c on c.loan_id=a.loan_id "+
								" where a.LOAN_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()+" and a.PARTNER_TYPE='"+businessType+"' ");
				
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
				loanId=null;			
			}

			return list;

		}
		
		// add by saorabh on 6 sep 2014
		public String  getvalidateBusinessPartnerRule(String loanId,String businessId,String businessType)
		{
			String countQuery = "select count(1) from cr_business_partner_dtl where loan_id = '"+loanId+"' and partner_type = '"+businessType+"'";
			logger.info("countQuery ------------"+countQuery);
			String countStr = ConnectionDAO.singleReturn(countQuery);
			String countPartnerFlag = "";
			int count = 0;
			countQuery = null;
			if(!CommonFunction.checkNull(countStr).equalsIgnoreCase(""))
			{
				count = Integer.parseInt(countStr);
				if(count<2)
				{
					return "count";
				}
				else
				{
					countQuery="select count(1) from cr_business_partner_dtl where loan_id = '"+loanId+"' and partner_type = '"+businessType+"' and service_partner_flag = 'Y'";
					countPartnerFlag = ConnectionDAO.singleReturn(countQuery);
					if(CommonFunction.checkNull(countPartnerFlag).equalsIgnoreCase("0"))
					{
						/*count = Integer.parseInt(countPartnerFlag);*/
						return "al";
					}
					/*else
						return "al"; //al = atleast capture on servicing partner
*/				}
					
			}
			else
				return "pt"; // pt = Please capture two business partner
			
		//amandeep	comments
		/*	String eff_count = "select count(1) from cr_business_partner_dtl a left join cr_loan_dtl l on l.loan_id=a.loan_id where a.loan_id = '"+loanId+"' and a.service_partner_flag = 'N' and a.rate>l.loan_eff_rate";
			logger.info("eff_count Query ------------"+eff_count);
			int effCount = Integer.parseInt(ConnectionDAO.singleReturn(eff_count));
			if(effCount>0)
			return "rlee";//rate<=loan_eff_rate
*/			
			//amandeep
			String query = "select a.rate,a.service_partner_flag,l.loan_eff_rate from cr_business_partner_dtl a left join cr_loan_dtl l on l.loan_id=a.loan_id where a.loan_id = '"+loanId+"' and a.service_partner_flag = 'Y'";
			logger.info("select Query ------------"+query);
			ArrayList list = null;
			String rate = "";
			String partnerFlag="";
			String loanEffRate="";
			
			try {
				 list = ConnectionDAO.sqlSelect(query);
				 
				 if(list!=null && list.size()>0){
						logger.info("header"+list.size());
						ArrayList header1=(ArrayList)list.get(0);
						if(header1!=null && header1.size()>0)
						{
							rate = CommonFunction.checkNull(header1.get(0));
							partnerFlag = CommonFunction.checkNull(header1.get(1));
							loanEffRate = CommonFunction.checkNull(header1.get(2));
						}
					}
				 //amandeep comments
				/* if(partnerFlag.equalsIgnoreCase("Y"))
				 {
					double cRate = Double.parseDouble(rate); 
					double cloanEffRate = Double.parseDouble(loanEffRate); 
					BigDecimal bigDecimal = new BigDecimal(rate);
					BigDecimal bigDecimal1 = new BigDecimal(loanEffRate);
					int compareValue = bigDecimal.compareTo(bigDecimal1);
					if(compareValue!=0)
						return "rel";//rel = rate and loan_eff_rate
				 }*/
				 //amandeep 
				 /*else if(partnerFlag.equalsIgnoreCase("N"))
				 {
					 
				 }*/
				 
				 
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return "success";
		}
		// end by saorabh
		
public Boolean deleteBusinessPartnr(String loanId,String businessId,String businessType) {
			
			
            boolean qryStatus = false;
           
            ArrayList qryList = new ArrayList();
            String query ="";
   		
		try{
			if(businessType.equalsIgnoreCase("OF"))
			 query="delete from cr_business_partner_dtl where loan_id='"+loanId+"' ";
			else
			query="delete from cr_business_partner_dtl where loan_id='"+loanId+"' and partner_type <>'"+businessType+"'";
	        logger.info("IN deletePartnerDtl() delete query ### "+query);
	        PrepStmtObject insPrepStmtObject = new PrepStmtObject();
	        insPrepStmtObject.setSql(query);
	        qryList.add(insPrepStmtObject);
			qryStatus=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("Deletion Status :."+qryStatus);
			query=null;
			query = "update cr_loan_dtl set funding_type = '"+businessType+"' where loan_id = '"+loanId+"'" ;
			insPrepStmtObject= null;
			insPrepStmtObject = new PrepStmtObject();
			insPrepStmtObject.setSql(query);
			qryList=null;
			qryList = new ArrayList();
			qryList.add(insPrepStmtObject);
			qryStatus=ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			logger.info("Update Status :."+qryStatus);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			return qryStatus;
			}
// add by saorabh
public boolean validationOnVatPercent(String loanId)
{
		boolean status = true;
		String selectQuery = "select ifnull(vat_percent,0.00) vat_percent from cr_asset_collateral_m where asset_id in (select assetId from cr_loan_collateral_m where loan_id = '"
				+ loanId + "')";
		try {
			ArrayList arrayList = ConnectionDAO.sqlSelect(selectQuery);
			String checkVatPercent = "";
			String checkVatPercent1 = "";
			if (arrayList.size() > 1) {
				checkVatPercent = CommonFunction.checkNull(arrayList.get(0))
						.trim();
				logger.info("checkVatPercent ----------" + checkVatPercent);
				for (int i = 1; i < arrayList.size(); i++) {
					checkVatPercent1 = CommonFunction.checkNull(
							arrayList.get(i)).trim();
					if (!checkVatPercent.equalsIgnoreCase(checkVatPercent1)) {
						status = false;
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
}
//end by saorabh

@Override
public int checkParticipationAmt(String loanId,String partnerType) {
	StringBuilder query = new StringBuilder();
	StringBuilder query1 = new StringBuilder();
	StringBuilder queryResult = new StringBuilder();
	StringBuilder checkSumOfParticipationAmt = new StringBuilder();
	int result=0;
 try{  
	 if(!CommonFunction.checkNull(partnerType).equalsIgnoreCase("OF")) {
	 queryResult.append(" select count(1) from cr_txncharges_dtl where deal_charge_code=283 and txn_type='LIM' and txn_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()+" ");
	 result=Integer.parseInt(ConnectionDAO.singleReturn(queryResult.toString()));
		
	 
	 if(result>0)
	 {
		query.append(" select count(1) from cr_business_partner_dtl a ");
		query.append(" join cr_txncharges_dtl b on a.loan_id=b.txn_id and b.txn_type='LIM' and a.self_flag='N' ");  
		query.append(" and round(a.loan_amount,2)=round(b.deal_charge_final_amount,2) and b.deal_charge_code=283 and a.loan_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()+" ");
		result=	Integer.parseInt(ConnectionDAO.singleReturn(query.toString()));
		
	if(result>0)
		 {
			 query1.append(" select count(1) from cr_business_partner_dtl a ");
			 query1.append(" join cr_loan_dtl b on a.loan_id=b.loan_id and a.service_partner_flag='Y' ");  
			 query1.append(" and round(IFNULL(a.EFFECTIVE_RATE,''),4)=round(b.LOAN_EFF_RATE,4) and a.loan_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()+" ");
			 result=	Integer.parseInt(ConnectionDAO.singleReturn(query1.toString())); 
			 if(result==0)
			 {
				 result=3; 
			 }
			 else{
				 checkSumOfParticipationAmt.append("  select if(b.loan_loan_amount=sum(a.loan_amount),1,4) from cr_business_partner_dtl a  ");
				 checkSumOfParticipationAmt.append(" join cr_loan_dtl b on a.loan_id=b.loan_id and a.loan_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()+" ");  
				 result=Integer.parseInt(ConnectionDAO.singleReturn(checkSumOfParticipationAmt.toString())); 
			 }
		 }	
		
		
	 }
	 else{
		 result=1;
	 }
	
	 
	 }
	 else{
		 queryResult.append(" select count(1) from cr_txncharges_dtl where deal_charge_code=283 and txn_type='LIM' and txn_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()+" ");
		 result=Integer.parseInt(ConnectionDAO.singleReturn(queryResult.toString()));
			
		 
		 if(result>0)
		 {
			query.append(" select count(1) from cr_business_partner_dtl a ");
			query.append(" join cr_txncharges_dtl b on a.loan_id=b.txn_id and b.txn_type='LIM' and a.self_flag='N' ");  
			query.append(" and round(a.loan_amount,2)=round(b.deal_charge_final_amount,2) and b.deal_charge_code=283 and a.loan_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()+" ");
			result=	Integer.parseInt(ConnectionDAO.singleReturn(query.toString()));
			if(result>0){
				result=1;
			}
		 }
		 else{
			 result=1;
		 }
	 }

}catch (Exception e) {
	e.printStackTrace();
	e=null;
}
finally
{
	query = null;
	query1=null;
	loanId=null;
	queryResult=null;
	checkSumOfParticipationAmt=null;
}
return result;
}

public ArrayList<Object> fetchLoanDetailForRepaymentService(CommonLoanVo vo,HttpServletRequest request) {
	ArrayList list=new ArrayList();
	int count=0;
	int startRecordIndex=0;
	int endRecordIndex = no;
//	String userName="";
	StringBuilder userName=new StringBuilder();
		
	logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+vo.getReportingToUserId());
	try{
		StringBuilder userNameQ=new StringBuilder();
		 userNameQ.append("select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+vo.getReportingToUserId()+"'");
		userName.append(ConnectionDAO.singleReturn(userNameQ.toString()));
		logger.info("userNameQ: "+userNameQ+" userName: "+userName);
		userNameQ=null;
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}		

	StringBuilder lonaNo=new StringBuilder();
	StringBuilder aDate=new StringBuilder();
	StringBuilder custName=new StringBuilder();
	StringBuilder productId=new StringBuilder();
	StringBuilder scheme=new StringBuilder();
	
    lonaNo.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID())).trim());
	aDate.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAgrementDate())).trim());
	custName.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName())).trim());
	productId.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID())).trim());
	scheme.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme())).trim());

	 
	try
	{
		
	    
		ArrayList header=null;
		CommonLoanVo fetchVo= null;
		boolean appendSQL=false;
		StringBuffer bufInsSql=new StringBuffer();
		StringBuffer bufInsSqlTempCount = new StringBuffer();
 
		

		bufInsSql.append(" select distinct d.LOAN_ID,d.LOAN_NO,DATE_FORMAT(d.LOAN_AGREEMENT_DATE,'"+dateFormat+"'),g.CUSTOMER_NAME,p.PRODUCT_DESC, s.SCHEME_DESC, DATE_FORMAT(dl.DEAL_SANCTION_VALID_TILL,'"+dateFormat+"'),(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=D.MAKER_ID) MAKER_ID,LOAN_DEAL_ID,z.PARTNER_ID,y.NAME,IF(IFNULL(Z.partner_type,'')='',IFNULL(D.funding_type,''), IFNULL(Z.partner_type,'')) from cr_loan_dtl d");
		bufInsSqlTempCount.append("SELECT distinct COUNT(1) FROM cr_loan_dtl d ");

		//Rohit Changes Start for RSP search.....
		
		bufInsSql.append(" LEFT JOIN CR_BUSINESS_PARTNER_DTL Z ON Z.LOAN_ID=D.LOAN_ID AND  IFNULL(Z.REC_STATUS,'')='P' AND Z.SERVICE_PARTNER_FLAG='N' ");
		bufInsSqlTempCount.append(" LEFT JOIN CR_BUSINESS_PARTNER_DTL Z ON Z.LOAN_ID=D.LOAN_ID AND  IFNULL(Z.REC_STATUS,'')='P' AND Z.SERVICE_PARTNER_FLAG='N' ");
		
		bufInsSql.append("	JOIN cr_repaysch_dtl R ON R.LOAN_ID=D.LOAN_ID AND INSTL_NO=1 AND BILL_FLAG='N' ");
		bufInsSqlTempCount.append("	JOIN cr_repaysch_dtl R ON R.LOAN_ID=D.LOAN_ID AND INSTL_NO=1 AND BILL_FLAG='N'	");
		
		//bufInsSql.append(" left join cr_business_partner_dtl z on d.loan_id=z.LOAN_ID and  z.REC_STATUS='P'  and z.service_partner_flag='N'  and  z.partner_type in('CP','CB')   ");
	//	bufInsSqlTempCount.append(" left join cr_business_partner_dtl z on d.loan_id=z.LOAN_ID and  z.REC_STATUS='P'  and z.service_partner_flag='N'  and  z.partner_type in('CP','CB')  ");
		
		//Rohit Changes end for RSP search.....
		
		bufInsSql.append(" left join com_partner_details_m y on z.partner_ID=y.PARTNER_ID  ");
		bufInsSqlTempCount.append(" left join com_partner_details_m y on z.partner_ID=y.PARTNER_ID  ");
		
		bufInsSql.append(" left join gcd_customer_m g on g.CUSTOMER_ID=d.LOAN_CUSTOMER_ID ");
		bufInsSqlTempCount.append(" left join gcd_customer_m g on g.CUSTOMER_ID=d.LOAN_CUSTOMER_ID ");
		
		bufInsSql.append(" left join cr_product_m p on d.LOAN_PRODUCT=p.PRODUCT_ID  ");
		bufInsSqlTempCount.append(" left join cr_product_m p on d.LOAN_PRODUCT=p.PRODUCT_ID  ");
		
		bufInsSql.append(" left join cr_scheme_m s on d.LOAN_SCHEME=s.SCHEME_ID ");
		bufInsSqlTempCount.append(" left join cr_scheme_m s on d.LOAN_SCHEME=s.SCHEME_ID ");
		
		bufInsSql.append(" left join cr_deal_loan_dtl dl on d.LOAN_DEAL_ID=dl.DEAL_ID  AND dl.deal_loan_id=d.loan_deal_loan_id ");
		bufInsSqlTempCount.append(" left join cr_deal_loan_dtl dl on d.LOAN_DEAL_ID=dl.DEAL_ID  AND dl.deal_loan_id=d.loan_deal_loan_id ");
				
		 
		

			//Nishant Space starts
			   String branch="";
			   if(vo.getAllBranches().equalsIgnoreCase("on"))
			   {
				   String branchMappedToUserQuery="SELECT GROUP_CONCAT(BRANCH_ID) FROM sec_user_branch_dtl WHERE USER_ID='"+CommonFunction.checkNull(vo.getReportingToUserId()).trim()+"' AND REC_STATUS='A'";
				   branch=ConnectionDAO.singleReturn(branchMappedToUserQuery);
				   logger.info("branchMappedToUserQuery: "+branchMappedToUserQuery+"      branches maped to user:   "+branch);
			   }
			   else if(!(vo.getLbxBranchId().equalsIgnoreCase("")))
			   {
				   branch=vo.getLbxBranchId();
				   logger.info("i am in");
			   }

			//Nishant Space End
			if((!(vo.getLbxDealNo().equalsIgnoreCase("")))&&(!(vo.getLbxLoanNoHID().equalsIgnoreCase("")))&&(!(vo.getAgrementDate().equalsIgnoreCase("")))&&(!(vo.getCustomerName().equalsIgnoreCase("")))&&(!(vo.getLbxProductID().equalsIgnoreCase("")))&&(!(vo.getLbxscheme().equalsIgnoreCase(""))))
			{
		   	  bufInsSql.append("WHERE   D.LOAN_BRANCH IN ("+StringEscapeUtils.escapeSql(branch).trim()+")  AND d.LOAN_ID='"+vo.getLbxLoanNoHID()+"' AND g.CUSTOMER_NAME like'%"+vo.getCustomerName()+"%' AND d.LOAN_PRODUCT='"+vo.getLbxProductID()+"'AND d.LOAN_SCHEME='"+vo.getLbxscheme()+"' and ifnull(d.funding_type,'')='OF' and ifnull(d.DISBURSAL_STATUS,'')='F' and ifnull(d.rec_status,'')='A' and d.LOAN_REPAYMENT_TYPE='I' ");
		   	  bufInsSqlTempCount.append("WHERE   D.LOAN_BRANCH IN ("+StringEscapeUtils.escapeSql(branch).trim()+")  AND d.LOAN_ID='"+vo.getLbxLoanNoHID()+"' AND g.CUSTOMER_NAME like'%"+vo.getCustomerName()+"%' AND d.LOAN_PRODUCT='"+vo.getLbxProductID()+"'AND d.LOAN_SCHEME='"+vo.getLbxscheme()+"' and ifnull(d.funding_type,'')='OF' and ifnull(d.DISBURSAL_STATUS,'')='F' and ifnull(d.rec_status,'')='A'  and d.LOAN_REPAYMENT_TYPE='I' ");
		   	  
		   	bufInsSql.append(" and  d.loan_id not in(select loan_id from cr_business_partner_dtl where ifnull(lead_partner_flag,'')='Y' and ifnull(rec_status,'')='F' ) ");
		   	bufInsSql.append("	and d.loan_id not in (select distinct loan_id from cr_repaysch_dtl where bill_flag='Y') ");
		   	bufInsSqlTempCount.append(" and  d.loan_id not in(select loan_id from cr_business_partner_dtl where ifnull(lead_partner_flag,'')='Y' and ifnull(rec_status,'')='F' ) ");
		   	bufInsSqlTempCount.append("	and d.loan_id not in (select distinct loan_id from cr_repaysch_dtl where bill_flag='Y') ");
			}
			
			if(((vo.getLbxDealNo().equalsIgnoreCase("")))||((vo.getLbxLoanNoHID().equalsIgnoreCase("")))||((vo.getAgrementDate().equalsIgnoreCase("")))||((vo.getCustomerName().equalsIgnoreCase("")))||((vo.getLbxProductID().equalsIgnoreCase("")))||((vo.getLbxscheme().equalsIgnoreCase("")))||((vo.getStage().equalsIgnoreCase("")))){
				appendSQL=true;
			}
			
			if(appendSQL){
				logger.info("In Where Clause");
				//Rohit Changes Start for RSP search.....
				bufInsSql.append(" WHERE   D.REC_STATUS='A' AND IFNULL(D.FUNDING_TYPE,'')='OF' AND IFNULL(D.DISBURSAL_STATUS,'')='F' AND D.LOAN_REPAYMENT_TYPE='I' ");
				bufInsSqlTempCount.append(" WHERE  D.REC_STATUS='A' AND IFNULL(D.FUNDING_TYPE,'')='OF' AND IFNULL(D.DISBURSAL_STATUS,'')='F' AND D.LOAN_REPAYMENT_TYPE='I'  ");
			
			//	bufInsSql.append(" WHERE  'a'='a' and ifnull(d.funding_type,'')='OF' and ifnull(d.DISBURSAL_STATUS,'')='F' and ifnull(d.rec_status,'')='A' and d.LOAN_REPAYMENT_TYPE='I' ");
			//	bufInsSqlTempCount.append(" WHERE  'a'='a' and ifnull(d.funding_type,'')='OF' and ifnull(d.DISBURSAL_STATUS,'')='F' and ifnull(d.rec_status,'')='A' and d.LOAN_REPAYMENT_TYPE='I'  ");
			 //  	bufInsSql.append(" and  d.loan_id not in(select loan_id from cr_business_partner_dtl where ifnull(lead_partner_flag,'')='Y' and ifnull(rec_status,'')='F' ) ");
			 //  	bufInsSql.append("	and d.loan_id not in (select distinct loan_id from cr_repaysch_dtl where bill_flag='Y') ");
			 //  	bufInsSqlTempCount.append(" and  d.loan_id not in(select loan_id from cr_business_partner_dtl where ifnull(lead_partner_flag,'')='Y' and ifnull(rec_status,'')='F' ) ");
			//   	bufInsSqlTempCount.append("	and d.loan_id not in (select distinct loan_id from cr_repaysch_dtl where bill_flag='Y') ");
			
				//Rohit Changes end for RSP Search......
			}
		
		
			if(!CommonFunction.checkNull(branch).equalsIgnoreCase("")) {
				bufInsSql.append(" AND  D.LOAN_BRANCH IN ("+StringEscapeUtils.escapeSql(branch).trim()+")  ");
				bufInsSqlTempCount.append(" AND   D.LOAN_BRANCH IN ("+StringEscapeUtils.escapeSql(branch).trim()+")    ");
		   	 appendSQL=true;
		
		     }	
		
		
		 if((!(vo.getLbxLoanNoHID().equalsIgnoreCase("")))) {
	        bufInsSql.append(" and  d.LOAN_ID='"+vo.getLbxLoanNoHID()+"'");
	        bufInsSqlTempCount.append(" and  d.LOAN_ID='"+vo.getLbxLoanNoHID()+"'");
	   	 appendSQL=true;
	   	  
	     }
		 
			
		 if((!(vo.getCustomerRSPId().equalsIgnoreCase("")))) {
	        bufInsSql.append(" and  z.PARTNER_ID='"+vo.getCustomerRSPId()+"'");
	        bufInsSqlTempCount.append(" and  z.PARTNER_ID='"+vo.getCustomerRSPId()+"'");
	   	 appendSQL=true;
	   	  
	     }
		 
					
		
		if((!(vo.getCustomerName().equalsIgnoreCase("")))) {
	   	  bufInsSql.append("and  g.CUSTOMER_NAME like'%"+vo.getCustomerName()+"%' ");
	   	  bufInsSqlTempCount.append("and  g.CUSTOMER_NAME like'%"+vo.getCustomerName()+"%'");
	   	  appendSQL=true;
	     }
		if((!(vo.getLbxProductID().equalsIgnoreCase("")))) {
		   	  bufInsSql.append("and  d.LOAN_PRODUCT='"+vo.getLbxProductID()+"'");
		   	  bufInsSqlTempCount.append("and  d.LOAN_PRODUCT='"+vo.getLbxProductID()+"'");
		   	  appendSQL=true;
		     }
		if((!(vo.getLbxscheme().equalsIgnoreCase("")))) {
		   	  bufInsSql.append("and  d.LOAN_SCHEME='"+vo.getLbxscheme()+"'");
		   	  bufInsSqlTempCount.append("and  d.LOAN_SCHEME='"+vo.getLbxscheme()+"'");
		   	  appendSQL=true;
		     }
		if((!(vo.getLbxUserId().equalsIgnoreCase("")))) {
		bufInsSql.append(" AND d.MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getLbxUserId()).trim()+"' ");	
		bufInsSqlTempCount.append(" AND d.MAKER_ID='"+StringEscapeUtils.escapeSql(vo.getLbxUserId()).trim()+"'");
		appendSQL=true;
		}
		logger.info("In appendSQL true---- "+appendSQL);
		
		if(appendSQL){
			logger.info("In appendSQL true---- ");
			String  tmp = bufInsSql.toString();
			String tmp1 = bufInsSqlTempCount.toString();
			
	     logger.info("In fetchLoanDetail() ## tmp ## "+tmp);
	     logger.info("In appendSQL true----  in check index Of"+tmp.lastIndexOf("AND") +"------"+(tmp.length()-3));
	   
	     if(tmp.lastIndexOf("AND")== (tmp.length()-3) || tmp1.lastIndexOf("AND")==(tmp1.length()-3))
	     {
	     logger.info("In appendSQL true----  in check index Of");
	     tmp = (tmp).substring(0,(tmp.length()-4));
	     tmp1 = (tmp1).substring(0,(tmp1.length()-4));
	     logger.info("fetchLoanDetail Query...tmp."+tmp);
	     header = ConnectionDAO.sqlSelect(tmp);
	     count =Integer.parseInt(ConnectionDAO.singleReturn(tmp1.toString()));
	     
	      }else
	      {
	     	  logger.info("fetchLoanDetail Query...tmp."+tmp);
	     	 header = ConnectionDAO.sqlSelect(tmp); 
	     	 count =Integer.parseInt(ConnectionDAO.singleReturn(tmp1.toString()));
	       }
		 }else {
	    	  
				LoggerMsg.info("search Query...else-------." + bufInsSql);
				LoggerMsg.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
	            
				count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
				
				
				if(( lonaNo.toString().trim()==null && aDate.toString().trim()==null && custName.toString().trim()==null && productId.toString().trim()==null && scheme.toString().trim()==null) || ( lonaNo.toString().trim().equalsIgnoreCase("") && aDate.toString().trim().equalsIgnoreCase("") && custName.toString().trim().equalsIgnoreCase("") && productId.toString().trim().equalsIgnoreCase("") && scheme.toString().trim().equalsIgnoreCase("")) || vo.getCurrentPageLink()>1)
				{
				
				LoggerMsg.info("current PAge Link no .................... "+vo.getCurrentPageLink());
				if(vo.getCurrentPageLink()>1)
				{
					startRecordIndex = (vo.getCurrentPageLink()-1)*no;
					endRecordIndex = no;
					LoggerMsg.info("startRecordIndex .................... "+startRecordIndex);
					LoggerMsg.info("endRecordIndex .................... "+endRecordIndex);
				}
				
				bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
				
				}
				LoggerMsg.info("query : "+bufInsSql);
				
				header = ConnectionDAO.sqlSelect(bufInsSql.toString());
			}

					
		for(int i=0;i<header.size();i++){
			logger.info("header"+header.size());
			ArrayList header1=(ArrayList)header.get(i);
			if(header1.size()>0)
			{
				fetchVo = new CommonLoanVo();
				fetchVo.setModifyNo("<a href=repaymentServiceAction.do?method=openPartnerDetailsForRsp&loanId="
			+ CommonFunction.checkNull(header1.get(0)).trim().toString() + "&businessType="+ CommonFunction.checkNull(header1.get(11)).trim().toString() +"&customerRSPId="+ CommonFunction.checkNull(header1.get(9)).trim().toString()+ " >"
				+ CommonFunction.checkNull(header1.get(1)).trim().toString() + "</a>");
				
				fetchVo.setLbxLoanNoHID((CommonFunction.checkNull(header1.get(0)).trim()));
				fetchVo.setLoanNo((CommonFunction.checkNull(header1.get(1)).trim()));
				fetchVo.setAgrementDate((CommonFunction.checkNull(header1.get(2)).trim()));
				fetchVo.setCustomerName((CommonFunction.checkNull(header1.get(3)).trim()));
				fetchVo.setProduct((CommonFunction.checkNull(header1.get(4)).trim()));
				fetchVo.setScheme((CommonFunction.checkNull(header1.get(5)).trim()));
				fetchVo.setValidSancTill((CommonFunction.checkNull(header1.get(6)).trim()));
				fetchVo.setReportingToUserId((CommonFunction.checkNull(header1.get(7)).trim()));
				fetchVo.setCustomerRSPName((CommonFunction.checkNull(header1.get(10)).trim()));
				fetchVo.setTotalRecordSize(count);
				list.add(fetchVo);
				
			}
		}
//		if( header.size()==0)
//		{
//			CommonLoanVo fetchMVo = new CommonLoanVo();
//			fetchMVo.setTotalRecordSize(count);
//			list.add(fetchMVo);
//			request.setAttribute("flag","yes");
//			//LoggerMsg.info("getTotalRecordSize : "+fetchMVo.getTotalRecordSize());
//		}
		bufInsSql=null;
		bufInsSqlTempCount=null;
		fetchVo=null;			
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
finally
{
 lonaNo=null;
 custName=null;
 productId=null;
 scheme=null;
 aDate=null;
 userName=null;
 vo=null;	 
}
	return list;
}

public ArrayList<InstallmentPlanForCMVO> getRspInstallType(String loanId,String customerRSPId) {
    ArrayList<InstallmentPlanForCMVO> list=new ArrayList<InstallmentPlanForCMVO>();
    ArrayList mainList=new ArrayList ();
		ArrayList subList =new ArrayList();
		
		try{
			StringBuilder query=new StringBuilder();
			
			 query.append(" select distinct FROM_INSTL_NO,TO_INSTL_NO,RECOVERY_PERCENT,LOAN_INSTALLMENT_TYPE, ");
			query.append(" LOAN_NO_OF_INSTALLMENT,PRINCIPAL_AMOUNT,INSTALLMENT_AMOUNT,LOAN_RATE_TYPE,  ");
			query.append(" a.LOAN_AMOUNT,RECOVERY_TYPE,DATE_FORMAT(loan_repay_eff_date,'%d-%m-%Y'),  ");
			 query.append("  DATE_FORMAT(L.loan_maturity_date,'%d-%m-%Y'),DATE_FORMAT(d.due_date,'%d-%m-%Y')   ");
			 query.append(" from cr_loan_dtl L   ");
			query.append(" join cr_business_partner_dtl a on l.loan_id=a.loan_id and a.service_partner_flag='N'  ");
			query.append(" left JOIN cr_rsp_installment_plan D on D.LOAN_ID=L.LOAN_ID  where L.LOAN_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+" " );
			

			
		logger.info("getRspInstallType Queryl: "+query);
		
		mainList=ConnectionDAO.sqlSelect(query.toString());
		for(int i=0;i<mainList.size();i++)
		{ 
			subList= (ArrayList)mainList.get(i);
			if(subList.size()>0){
				InstallmentPlanForCMVO ipVo= new InstallmentPlanForCMVO();
				ipVo = new InstallmentPlanForCMVO();  
				if(CommonFunction.checkNull(subList.get(3)).equalsIgnoreCase("E")||CommonFunction.checkNull(subList.get(3)).equalsIgnoreCase("P"))
				{
					if(CommonFunction.checkNull(subList.get(0)).equalsIgnoreCase("") || CommonFunction.checkNull(subList.get(1)).equalsIgnoreCase("")){
						ipVo.setFromInstallment("1");
						ipVo.setToInstallment((CommonFunction.checkNull(subList.get(4))).trim());
					}else{
						ipVo.setFromInstallment((CommonFunction.checkNull(subList.get(0))).trim());
						ipVo.setToInstallment((CommonFunction.checkNull(subList.get(1))).trim());
					}
					
					 if(!CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase("")){
    				     Number RecoveryPercen = myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());
	 			   
    				     logger.info("RecoveryPercen: "+RecoveryPercen);			
	 			             ipVo.setRecoveryPercen(myFormatter.format(RecoveryPercen));
    				      }else{
    				     ipVo.setRecoveryPercen("0");
    				          }
				    ipVo.setInstallmentType((CommonFunction.checkNull(subList.get(3))).trim());
 			  	ipVo.setTotalInstallment((CommonFunction.checkNull(subList.get(4))).trim());
 			
 			      //ipVo.setRecoveryType("P");
				}
				else{
				    ipVo.setFromInstallment((CommonFunction.checkNull(subList.get(0))).trim());
				    ipVo.setToInstallment((CommonFunction.checkNull(subList.get(1))).trim());
				    if(!CommonFunction.checkNull(subList.get(2)).equalsIgnoreCase("")){
				     Number RecoveryPercen = myFormatter.parse((CommonFunction.checkNull(subList.get(2))).trim());
			   
				     logger.info("RecoveryPercen: "+RecoveryPercen);			
			             ipVo.setRecoveryPercen(myFormatter.format(RecoveryPercen));
				      }else{
				     ipVo.setRecoveryPercen("0");
				          }
				    ipVo.setInstallmentType((CommonFunction.checkNull(subList.get(3))).trim());
				    ipVo.setTotalInstallment((CommonFunction.checkNull(subList.get(4))).trim());
				 
     				
				    }
				ipVo.setRateType((CommonFunction.checkNull(subList.get(7))).trim());
				
				  if(!CommonFunction.checkNull(subList.get(5)).equalsIgnoreCase("")){
 				     Number princam = myFormatter.parse((CommonFunction.checkNull(subList.get(5))).trim());
	 			   
 				     logger.info("princ amount: "+princam);			
	 			         ipVo.setPrinAm(myFormatter.format(princam));
 				      }else{
 				     ipVo.setPrinAm("0.00");
 				          }
  				if(!CommonFunction.checkNull(subList.get(6)).equalsIgnoreCase("")){
 				     Number instam = myFormatter.parse((CommonFunction.checkNull(subList.get(6))).trim());
	 			   
 				     logger.info("instamount: "+instam);			
	 			         ipVo.setInstalAm(myFormatter.format(instam));
 				      }else{
 				     ipVo.setInstalAm("0.00");
 				          }
				
				if(!CommonFunction.checkNull(subList.get(8)).equalsIgnoreCase("")){
 				     Number instam = myFormatter.parse((CommonFunction.checkNull(subList.get(8))).trim());
	 			   
 				     logger.info("setLoanAmount: "+instam);			
	 			         ipVo.setLoanAmount(myFormatter.format(instam));
 				      }else{
 				     ipVo.setLoanAmount("0.00");
 				          }
				ipVo.setRecoveryType((CommonFunction.checkNull(subList.get(9))).trim());
				//Richa space starts
				ipVo.setRepayeffdate((CommonFunction.checkNull(subList.get(10))).trim());
				ipVo.setMaxDate((CommonFunction.checkNull(subList.get(11))).trim());
				ipVo.setDueDatee((CommonFunction.checkNull(subList.get(12))).trim());
				//Richa space ends
				   list.add(ipVo);
				     ipVo=null;
			     }
		  }
		query=null;
		}catch(Exception e){
			e.printStackTrace();
			e=null;
		} finally{
			loanId=null;
		}
		return list;
	}


public String saveRSPInstallPlan(InstallmentPlanForCMVO ipvo) {
  	 
	logger.info("In saveRSPInstallPlan");
	String FromInstallment[] = ipvo.getFromInstall();
	String ToInstallment[] = ipvo.getToInstall();
	String RecoveryPercen[] = ipvo.getRecoveryPer();
	String prinAmount[] = ipvo.getPrincipalAmount();
	String instalAmount[] = ipvo.getInstallmentAmount();
	String InstallmentType= ipvo.getInstallmentType();
	String TotalInstallment=ipvo.getTotalInstallment();
	String toInstallment = ipvo.getToInstallment();	
	String dueDate[] = ipvo.getDueDate();
	
	ArrayList qryList=new ArrayList();
	ArrayList delList=new ArrayList();
	ArrayList updateList=new ArrayList();
	String status="";

		
		
		 PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
		 StringBuilder bufInsSql =	new StringBuilder();
		 String countQuery="";
		 int countFlag=0;
		 boolean finalStatus=false;
			try {
		 countQuery=" SELECT COUNT(1) FROM cr_business_partner_dtl WHERE LOAN_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(ipvo.getLoanId()).trim())+"  AND IFNULL(LEAD_PARTNER_FLAG,'N')='Y' ";
		 countFlag=Integer.parseInt(ConnectionDAO.singleReturn(countQuery));
			logger.info("countQuery:::::::::::"+countQuery);
			logger.info("countFlag:::::::::::"+countFlag);
		 if(countFlag==0 || countFlag>1)
			{
				status="ONEMUSTLEADPARTNER";
		 
				 String qry="DELETE FROM cr_rsp_installment_plan WHERE LOAN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(ipvo.getLoanId()).trim())+ "'" ;

			     delList.add(qry);
			     ConnectionDAO.sqlInsUpdDelete(delList);
				
				
			}
		
		if(CommonFunction.checkNull(status).equalsIgnoreCase("")){
		 
		 StringBuilder checkQ=new StringBuilder();
		  checkQ.append("select count(1) from cr_rsp_installment_plan where LOAN_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(ipvo.getLoanId()).trim())+" and ifnull(rec_status,'')='P' ");
		  
		  String count=ConnectionDAO.singleReturn(checkQ.toString());
		   
		   if(!count.equalsIgnoreCase("0"))
			  { 
			  insertPrepStmtObject = new PrepStmtObject();
			 String qry="DELETE FROM cr_rsp_installment_plan WHERE LOAN_ID='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(ipvo.getLoanId()).trim())+ "'  " ;

		     insertPrepStmtObject.setSql(qry);
		     logger.info("qry::::::::::"+qry);
			 qryList.add(insertPrepStmtObject);
			 ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);			 
			// ConnectionDAO.sqlInsUpdDelete(qryList);
			  
			  }
	
		 for(int k=0;k<FromInstallment.length;k++)  
		{
			bufInsSql =	new StringBuilder();
	     insertPrepStmtObject = new PrepStmtObject();
		  
		
		  insertPrepStmtObject = new PrepStmtObject();
			bufInsSql.append("insert into cr_rsp_installment_plan (LOAN_ID,FROM_INSTL_NO,TO_INSTL_NO,RECOVERY_PERCENT,PRINCIPAL_AMOUNT,INSTALLMENT_AMOUNT,REC_STATUS,SEQ_NO,RECOVERY_TYPE,MAKER_ID,MAKER_DATE,RSP_CUSTOMER_ID" );
			if(CommonFunction.checkNull(InstallmentType).equalsIgnoreCase("I"))
				bufInsSql.append(",DUE_DATE)");
				else
					bufInsSql.append(")");
			bufInsSql.append("values(?,?,?,?,?,?,?,?,?,?,DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),?");
			if(CommonFunction.checkNull(InstallmentType).equalsIgnoreCase("I"))
			bufInsSql.append(",DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND))");
			else
				bufInsSql.append(")");
			if(CommonFunction.checkNull(ipvo.getLoanId()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(ipvo.getLoanId().trim());   // loan Id
			
			if(CommonFunction.checkNull(FromInstallment[k]).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(FromInstallment[k].trim()); //From Installment
			
	    	if(CommonFunction.checkNull(ToInstallment[k]).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
			insertPrepStmtObject.addString(ToInstallment[k].trim());// To Installment 
	    	
	    	 if((CommonFunction.checkNull(RecoveryPercen[k])).trim().equalsIgnoreCase(""))
	        		insertPrepStmtObject.addString("0.00");
			else
			 insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(RecoveryPercen[k]).trim())).toString());
	    	 
	    	 
	    	 if((CommonFunction.checkNull(prinAmount[k])).trim().equalsIgnoreCase(""))
	        		insertPrepStmtObject.addString("0.00");
			else
			 insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(prinAmount[k]).trim())).toString());// PRINCIPAL_AMOUNT   	
	    	if((CommonFunction.checkNull(instalAmount[k])).trim().equalsIgnoreCase(""))
        		insertPrepStmtObject.addString("0.00");
		     else
		    insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(instalAmount[k]).trim())).toString());// INSTALLMENT_AMOUNT	
	    	insertPrepStmtObject.addString("P");// REC_STATUS   	
							
	    	insertPrepStmtObject.addString(""+(k+1));// SEQ_NO    
	    	
	    	if(CommonFunction.checkNull(InstallmentType).equalsIgnoreCase("I"))
	    	{
	    		insertPrepStmtObject.addString("F");
	    	}
	    	else
	    	{
	    	if(CommonFunction.checkNull(ipvo.getRecoveryType()).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
			insertPrepStmtObject.addString(ipvo.getRecoveryType().trim());// RECOVERY_TYPE
	    	}

	 	//---------------------------------------------------------
	 	if ((CommonFunction.checkNull(ipvo.getMakerId())).trim().equalsIgnoreCase(""))
	 		insertPrepStmtObject.addNull();
	 	else
	 		insertPrepStmtObject.addString((ipvo.getMakerId()).trim());
	 	if ((CommonFunction.checkNull(ipvo.getMakerDate()).trim()).equalsIgnoreCase(""))
	 		insertPrepStmtObject.addNull();
	 	else
	 		insertPrepStmtObject.addString(CommonFunction.checkNull(ipvo.getMakerDate()).trim());
	 	//---------------------------------------------------------
	 	if(CommonFunction.checkNull(ipvo.getCustomerRSPId()).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
		insertPrepStmtObject.addString(ipvo.getCustomerRSPId().trim());// CustomerRSPId
	 	
	 	if(CommonFunction.checkNull(InstallmentType).equalsIgnoreCase("I"))
	 	{
	 	if(CommonFunction.checkNull(dueDate[k]).trim().equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
		insertPrepStmtObject.addString(dueDate[k].trim());// To duedate 
	 	}
	 	
	 	
    	
		  
		  
		 	insertPrepStmtObject.setSql(bufInsSql.toString());
			logger.info("IN saveRSPInstallPlan() insert query1 ### "+insertPrepStmtObject.printQuery());
			qryList.add(insertPrepStmtObject);
		  
		}
		
		
			finalStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			if(finalStatus){ 
				
			String updateQuery = "update cr_business_partner_dtl set REFRESH_FLAG='N' where LOAN_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(ipvo.getLoanId()).trim())+"";

			updateList.add(updateQuery);


			finalStatus = ConnectionDAO.sqlInsUpdDelete(updateList);
			if(finalStatus){
				status="S";					
			}
			else{
				status="E";
			}
			}
			
			checkQ=null;
			bufInsSql=null;
			count=null;
		}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			e=null;
		}
		finally{
			ipvo=null;
			FromInstallment = null;
			ToInstallment = null;
			RecoveryPercen = null;
			prinAmount = null;
			instalAmount= null;
			InstallmentType= null;
			TotalInstallment= null;
			toInstallment = null;	
		}
	    logger.info("In saveInstallPlan......................"+status);
	return status;
}

//	public ArrayList getLoanDetailForRepaymenTSchedule(String loanId,String cpID) {
//    ArrayList list=new ArrayList();
//    ArrayList mainList=new ArrayList ();
//		ArrayList subList =new ArrayList();
//		RSPRepaymentScheduleVo cpVo=null;
//		
//		try{
//			StringBuilder query=new StringBuilder();
//			
//			
//			query.append(" select D.INSTL_NO,D.INSTL_DATE,D.SEQ_NO,A.LOAN_ID,B.LOAN_AMOUNT, ");
//			query.append(" B.RATE,A.LOAN_RATE_TYPE,DAYS_PER_YEAR,INT_ROUND_TYPE, INT_ROUNDING,  ");
//			query.append(" if(D.SEQ_NO=1,GET_DAYS_BETWEEN(A.LOAN_REPAY_EFF_DATE, A.NEXT_DUE_DATE, C.DAYS_BASIS), GET_DAYS_BETWEEN(D.INSTL_DATE,date_add(D.INSTL_DATE, interval 1 month), C.DAYS_BASIS))diff,e.INSTALLMENT_AMOUNT  ");
//			query.append(" , B.PARTNER_ID  from  cr_loan_dtl a ");
//			query.append(" join cr_business_partner_dtl b on a.LOAN_ID=b.LOAN_ID  AND A.LOAN_ID= "+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+" and B.PARTNER_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(cpID).trim())+"");
//			query.append(" join CR_PRODUCT_M c on a.LOAN_PRODUCT=c.PRODUCT_ID ");
//			query.append(" join cr_repaysch_dtl d on d.loan_id=a.loan_id ");
//			query.append(" join cr_rsp_installment_plan e on e.LOAN_ID=a.LOAN_ID  ");
//			
//		
//			
//			
//			
//		logger.info("getLoanDetailForRepaymenTSchedule Query: "+query);
//		
//		mainList=ConnectionDAO.sqlSelect(query.toString());
//		for(int i=0;i<mainList.size();i++)
//		{ 
//			subList= (ArrayList)mainList.get(i);
//			if(subList.size()>0){
//				cpVo = new InstallmentPlanForCMVO(); 
//				
//				cpVo.setInstNo((CommonFunction.checkNull(subList.get(0))).trim());	
//			    
//			    cpVo.setDueDate((CommonFunction.checkNull(subList.get(1))).trim());
//			    
//			    cpVo.setSeqNo((CommonFunction.checkNull(subList.get(2))).trim());
//			    
//			    cpVo.setLoanId((CommonFunction.checkNull(subList.get(3))).trim());
//			    
//			    cpVo.setLoanAmount((CommonFunction.checkNull(subList.get(4))).trim());
//			    
//			    cpVo.setEffectiveRate((CommonFunction.checkNull(subList.get(5))).trim());
//			    
//			    cpVo.setRateType((CommonFunction.checkNull(subList.get(6))).trim());
//			    
//			    cpVo.setDayPerYear((CommonFunction.checkNull(subList.get(7))).trim());
//			    
//			    cpVo.setIntrestRoundType((CommonFunction.checkNull(subList.get(8))).trim());
//			    
//			    cpVo.setInterestRounding((CommonFunction.checkNull(subList.get(9))).trim());
//			    
//			    cpVo.setDayDiffence((CommonFunction.checkNull(subList.get(10))).trim());
//			    
//			    cpVo.setInstAmount((CommonFunction.checkNull(subList.get(11))).trim());
//			    
//			    cpVo.setPartnerId((CommonFunction.checkNull(subList.get(12))).trim());
//				
//				
//				
//
//				   list.add(cpVo);
//				   cpVo=null;
//			     }
//		  }
//		query=null;
//		}catch(Exception e){
//			e.printStackTrace();
//			e=null;
//		} finally{
//			loanId=null;
//		}
//		return list;
//	}
//
//
//	
//	
//	public ArrayList GenerateRepaymenTScheduleForRSP(ArrayList vo) {
//	    ArrayList list=vo;
//	    ArrayList mainList=new ArrayList();
//	    BigDecimal pos= new BigDecimal(0);
//		
//	    
//	    for(int i=0;i<list.size();i++)
//	    {
//	    	
//	    	RSPRepaymentScheduleVo cpVo=(RSPRepaymentScheduleVo)list.get(i);
//	    	
//	    if(i==0)
//	    {
//			BigDecimal effectiveRate= new BigDecimal(cpVo.getEffectiveRate());
//			BigDecimal loanAmt= new BigDecimal(cpVo.getLoanAmount());
//			BigDecimal day=new BigDecimal(cpVo.getDayDiffence());
//			BigDecimal divideBy=new BigDecimal(36500);
//			BigDecimal installmentAmt=new BigDecimal(cpVo.getInstAmount());
//			BigDecimal interest=loanAmt.multiply(effectiveRate).multiply(day).divide(divideBy);
//			BigDecimal principal=installmentAmt.subtract(interest);
//			pos=loanAmt.subtract(principal);
//			cpVo.setPrinOS(pos);
//			cpVo.setPrinciple(principal);
//			cpVO.setInstCom(interest);
//			mainList.add(cpVo);
//			
//	    }
//	    else{
//	    	
//	    	BigDecimal effectiveRate= new BigDecimal(cpVo.getEffectiveRate());
//			BigDecimal day=new BigDecimal(cpVo.getDayDiffence());
//			BigDecimal divideBy=new BigDecimal(36500);
//			BigDecimal installmentAmt=new BigDecimal(cpVo.getInstAmount());
//			BigDecimal interest=pos.multiply(effectiveRate).multiply(day).divide(divideBy);
//			BigDecimal principal=installmentAmt.subtract(interest);
//			pos=loanAmt.subtract(principal);
//			cpVo.setPrinOS(pos);
//			cpVo.setPrinciple(principal);
//			cpVO.setInstCom(interest);
//			mainList.add(cpVo);
//	    	
//	    }
//			
//			
//	    	
//	    	
//	    }
//
//			return mainList;
//		}
//	
//	
//public boolean saveRSPRepaymentSchdule(ArrayList vo)
//{
//	
//    ArrayList list=vo;
//    
//    RSPRepaymentScheduleVo cpVo=(RSPRepaymentScheduleVo)list.get(0);
//	ArrayList qryList=new ArrayList();
//	boolean status=false;
//	try {
//		 PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
//		 StringBuilder bufInsSql =	new StringBuilder();
//		 
//		 StringBuilder checkQ=new StringBuilder();
//		  checkQ.append("select count(*) from consortium_repayment_dtl_RSP where LOAN_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(cpVo.getLoanId()).trim())+"  and PARTNER_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(cpVo.getPartnerId()).trim())+"");
//		  
//		  String count=ConnectionDAO.singleReturn(checkQ.toString());
//		   
//		   if(!count.equalsIgnoreCase("0"))
//			  { 
//			  insertPrepStmtObject = new PrepStmtObject();
//			 String qry="DELETE from consortium_repayment_dtl_RSP where LOAN_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(cpVo.getLoanId()).trim())+"  and PARTNER_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(cpVo.getPartnerId()).trim())+"" ;
//
//		     insertPrepStmtObject.setSql(qry);
//			 qryList.add(insertPrepStmtObject);
//			// ConnectionDAO.sqlInsUpdDelete(qryList);
//			  
//			  }
//	
//		 for(int k=0;k<list.size();k++)  
//		{
//			bufInsSql =	new StringBuilder();
//	     insertPrepStmtObject = new PrepStmtObject();
//	    	RSPRepaymentScheduleVo cpVo=(RSPRepaymentScheduleVo)list.get(k);
//		
//		  insertPrepStmtObject = new PrepStmtObject();
//			bufInsSql.append("insert into consortium_repayment_dtl_RSP(PARTNER_ID,LOAN_ID,INSTL_AMOUNT,PRIN_COMP,INT_COMP,POS,INSTL_NO,INSTL_DATE,REC_TYPE,TOTAL_DAYS) values (?,?,?,?,?,?,?,?,?,?)" );
//			
//			
//			if(CommonFunction.checkNull(cpVo.getPartnerId()).trim().equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(cpVo.getPartnerId().trim());   // loan Id
//			
//			
//			if(CommonFunction.checkNull(cpVo.getLoanId()).trim().equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(ipvo.getLoanId().trim());   // loan Id
//			
//			if (CommonFunction.checkNull(cpVo.getInstAmount()).equalsIgnoreCase(""))//LOAN_LOAN_AMOUNT
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(loanDetailForCMVO.getInstAmount()).trim())).toString());
//			
//			if (CommonFunction.checkNull(cpVo.getPrinciple()).equalsIgnoreCase(""))//LOAN_LOAN_AMOUNT
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(loanDetailForCMVO.getInstAmount()).trim())).toString());
//			
//			if (CommonFunction.checkNull(cpVo.getInstAmount()).equalsIgnoreCase(""))//LOAN_LOAN_AMOUNT
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(loanDetailForCMVO.getInstAmount()).trim())).toString());
//			
//			if (CommonFunction.checkNull(cpVo.getInstAmount()).equalsIgnoreCase(""))//LOAN_LOAN_AMOUNT
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(loanDetailForCMVO.getInstAmount()).trim())).toString());
//			
//			if(CommonFunction.checkNull(FromInstallment[k]).trim().equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(FromInstallment[k].trim()); //From Installment
//			
//	    	if(CommonFunction.checkNull(ToInstallment[k]).trim().equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//			insertPrepStmtObject.addString(ToInstallment[k].trim());// To Installment 
//	    	
//	    	 if((CommonFunction.checkNull(RecoveryPercen[k])).trim().equalsIgnoreCase(""))
//	        		insertPrepStmtObject.addString("0.00");
//			else
//			 insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(RecoveryPercen[k]).trim())).toString());
//	    	 
//	    	 
//	    	 if((CommonFunction.checkNull(prinAmount[k])).trim().equalsIgnoreCase(""))
//	        		insertPrepStmtObject.addString("0.00");
//			else
//			 insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(prinAmount[k]).trim())).toString());// PRINCIPAL_AMOUNT   	
//	    	if((CommonFunction.checkNull(instalAmount[k])).trim().equalsIgnoreCase(""))
//       		insertPrepStmtObject.addString("0.00");
//		     else
//		    insertPrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(instalAmount[k]).trim())).toString());// INSTALLMENT_AMOUNT	
//	    	insertPrepStmtObject.addString("P");// REC_STATUS   	
//							
//	    	insertPrepStmtObject.addString(""+(k+1));// SEQ_NO    
//	    	
//	    	if(CommonFunction.checkNull(InstallmentType).equalsIgnoreCase("I"))
//	    	{
//	    		insertPrepStmtObject.addString("F");
//	    	}
//	    	else
//	    	{
//	    	if(CommonFunction.checkNull(ipvo.getRecoveryType()).trim().equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//			insertPrepStmtObject.addString(ipvo.getRecoveryType().trim());// RECOVERY_TYPE
//	    	}
//
//	 	//---------------------------------------------------------
//	 	if ((CommonFunction.checkNull(ipvo.getMakerId())).trim().equalsIgnoreCase(""))
//	 		insertPrepStmtObject.addNull();
//	 	else
//	 		insertPrepStmtObject.addString((ipvo.getMakerId()).trim());
//	 	if ((CommonFunction.checkNull(ipvo.getMakerDate()).trim()).equalsIgnoreCase(""))
//	 		insertPrepStmtObject.addNull();
//	 	else
//	 		insertPrepStmtObject.addString(CommonFunction.checkNull(ipvo.getMakerDate()).trim());
//	 	//---------------------------------------------------------
//	 	if(CommonFunction.checkNull(InstallmentType).equalsIgnoreCase("I"))
//	 	{
//	 	if(CommonFunction.checkNull(dueDate[k]).trim().equalsIgnoreCase(""))
//			insertPrepStmtObject.addNull();
//		else
//		insertPrepStmtObject.addString(dueDate[k].trim());// To duedate 
//	 	}
//	 	
//	 	if(CommonFunction.checkNull(ipvo.getCustomerRSPId()).trim().equalsIgnoreCase(""))
//			insertPrepStmtObject.addNull();
//		else
//		insertPrepStmtObject.addString(ipvo.getCustomerRSPId().trim());// CustomerRSPId
//   	
//		  
//		  
//		 	insertPrepStmtObject.setSql(bufInsSql.toString());
//			logger.info("IN saveRSPInstallPlan() insert query1 ### "+insertPrepStmtObject.printQuery());
//			qryList.add(insertPrepStmtObject);
//		  
//		}
//		
//		
//			status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
//			checkQ=null;
//			bufInsSql=null;
//			count=null;
//		} catch (Exception e) {
//			e.printStackTrace();
//			e=null;
//		}
//		
//	    logger.info("In saveInstallPlan......................"+status);
//	return status;
//	
//}
	
	
public String generateRSPRepySchedule(String loanId,String partnerId,String makerId) {
	boolean status=false;
	//CallableStatement cst=null;
	//Connection con=ConnectionDAO.getConnection();
	ArrayList<Object> in =new ArrayList<Object>();
	ArrayList<Object> out =new ArrayList<Object>();
	ArrayList outMessages = new ArrayList();
	ArrayList updateList= new ArrayList();
	String s1="";
	String s2="";
	String procval="";
	int countFlag;
	String countQuery="";
	try {
//		con.setAutoCommit(false);
		logger.info("In generateRSPRepySchedule Procedure: ");

		 countQuery=" SELECT COUNT(1) FROM cr_business_partner_dtl WHERE LOAN_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+"  AND IFNULL(LEAD_PARTNER_FLAG,'N')='Y' ";
		 countFlag=Integer.parseInt(ConnectionDAO.singleReturn(countQuery));
			logger.info("countQuery:::::::::::"+countQuery);
			logger.info("countFlag:::::::::::"+countFlag);
		 if(countFlag==0 || countFlag>1)
			{
			 procval="ONEMUSTLEADPARTNER";
			}
		if(CommonFunction.checkNull(procval).equalsIgnoreCase(""))
		{ 
			 countQuery=" SELECT COUNT(1) FROM cr_rsp_installment_plan WHERE LOAN_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+" ";
			 countFlag=Integer.parseInt(ConnectionDAO.singleReturn(countQuery));
				logger.info("countQuery:::::::::::"+countQuery);
				logger.info("countFlag:::::::::::"+countFlag);
			 if(countFlag==0)
				{
				 procval="ONEMUSTROW";
				}	
		}
		if(CommonFunction.checkNull(procval).equalsIgnoreCase(""))
		{
		in.add("CPM");
 	    in.add(loanId);

 	    out.add(s1);
 	    out.add(s2);
 	//    out.add(s3);
 	    outMessages=(ArrayList) ConnectionDAO.callSP("GENERATE_REPAYMENT_SCHEDULE",in,out);
 	    s1=CommonFunction.checkNull(outMessages.get(0));
 	    s2=CommonFunction.checkNull(outMessages.get(1));
 

		
		if(s1!=null && s1.equalsIgnoreCase("S"))
		{
			status=true;
			procval=s1;	
			String updateQuery = "update consortium_repayment_dtl set REC_STATUS='F' where LOAN_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+"";
			String updateQuery1 = "update cr_business_partner_dtl set REFRESH_FLAG='Y' where LOAN_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+"";
			updateList.add(updateQuery);
			updateList.add(updateQuery1);
			status = ConnectionDAO.sqlInsUpdDelete(updateList);		
		}else{
			procval=s2;
		}
		}
		logger.info("status: "+status);
		logger.info("s2: "+s2);
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		
		s1=null;
		s2=null;
		in.clear();
		in=null;
		out.clear();
		out=null;
		outMessages.clear();
		outMessages=null;
	}
	return procval;
}


public ArrayList getRSPRepaySched(String loanId, String partnerId) {
	
	ArrayList list = new ArrayList();
	logger.info("In getRepaySched: ");
	ArrayList mainlist = new ArrayList();
	ArrayList subList = new ArrayList();
	StringBuilder query= new StringBuilder();
	RepayScheduleVo repvo = null;
	try {

		query.append("select INSTL_NO,DATE_FORMAT(INSTL_DATE,'"+dateFormat+"'),INSTL_AMOUNT,PRIN_COMP,INT_COMP,EXCESS_INT,");
		query.append(" 'NO' ,ifnull(PRIN_OS,0),0 AS OTHER_CHARGES, 0 AS vat_amount,0 AS service_amount,IFNULL(INTEREST_RATE,0) from CONSORTIUM_REPAYMENT_DTL where LOAN_ID="+loanId+" and SEQ_NO IS NOT NULL");
		
		logger.info("Query in getRSPRepaySched-----" + query.toString());
		mainlist = ConnectionDAO.sqlSelect(query.toString());
		int size = mainlist.size();
		for (int i = 0; i < size; i++) {
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
				
				//repvo.setInstAmount((CommonFunction.checkNull(subList.get(2)).trim()));
				if(!CommonFunction.checkNull(subList.get(3)).trim().equalsIgnoreCase(""))
	    		{
    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(3))).trim());  
    	    		repvo.setPrinciple(myFormatter.format(reconNum));
	    		}
				//repvo.setPrinciple((CommonFunction.checkNull(subList.get(3)).trim()));
				if(!CommonFunction.checkNull(subList.get(4)).trim().equalsIgnoreCase(""))
	    		{
    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(4))).trim());  
    	    		repvo.setInstCom(myFormatter.format(reconNum));
	    		}
				//repvo.setInstCom((CommonFunction.checkNull(subList.get(4)).trim()));
				if(!CommonFunction.checkNull(subList.get(5)).trim().equalsIgnoreCase(""))
	    		{
    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(5))).trim());  
    	    		repvo.setExcess(myFormatter.format(reconNum));
	    		}
				
				//repvo.setExcess((CommonFunction.checkNull(subList.get(5)).trim()));
				repvo.setAdvFlag((CommonFunction.checkNull(subList.get(6)).trim()));
				if(!CommonFunction.checkNull(subList.get(7)).trim().equalsIgnoreCase(""))
	    		{
    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(7))).trim());  
    	    		repvo.setPrinOS(myFormatter.format(reconNum));
	    		}
				//repvo.setPrinOS((CommonFunction.checkNull(subList.get(7)).trim()));
				if(!CommonFunction.checkNull(subList.get(8)).trim().equalsIgnoreCase(""))
	    		{
    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(8))).trim());  
    	    		repvo.setOtherCharges(myFormatter.format(reconNum));
	    		}
				else
				{
					repvo.setOtherCharges("0.00");
				}
				// add by saorabh
				if(!CommonFunction.checkNull(subList.get(9)).trim().equalsIgnoreCase(""))
	    		{
    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(9))).trim());  
    	    		repvo.setVatAmount(myFormatter.format(reconNum));
	    		}
				else
				{
					repvo.setVatAmount("0.00");
				}
				if(!CommonFunction.checkNull(subList.get(10)).trim().equalsIgnoreCase(""))
	    		{
    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(10))).trim());  
    	    		repvo.setServiceAmount(myFormatter.format(reconNum));
	    		}
				else
				{
					repvo.setServiceAmount("0.00");
				}
				//end by saorabh
				
				if(!CommonFunction.checkNull(subList.get(11)).trim().equalsIgnoreCase(""))
	    		{
    	    		Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(11))).trim());  
    	    		repvo.setEffectiveRate(myFormatter.format(reconNum));
	    		}
				else
				{
					repvo.setEffectiveRate("0.00");
				}
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

@Override
public int checkLeadPartner(String loanId) {
	StringBuilder queryResult = new StringBuilder();
	int result=0;
 try{  
	 queryResult.append(" SELECT COUNT(1) FROM cr_business_partner_dtl WHERE LOAN_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+"  AND IFNULL(LEAD_PARTNER_FLAG,'N')='Y'  ");
	 result=Integer.parseInt(ConnectionDAO.singleReturn(queryResult.toString()));

}catch (Exception e) {
	e.printStackTrace();
	e=null;
}
finally
{
	loanId=null;
	queryResult=null;
}
return result;
}

@Override
public String fetchRspId(String loanId) {
	StringBuilder queryResult = new StringBuilder();
	String result="";
 try{  
	 queryResult.append(" SELECT IFNULL(partner_id,'') FROM cr_business_partner_dtl WHERE LOAN_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+"  AND IFNULL(LEAD_PARTNER_FLAG,'N')='Y'  ");
	 result=ConnectionDAO.singleReturn(queryResult.toString());

}catch (Exception e) {
	e.printStackTrace();
	e=null;
}
finally
{
	loanId=null;
	queryResult=null;
}
return result;
}

@Override
public String savePartnerDetailsForRsp(Object ob,String id,String businessType) {
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
qry1="SELECT count(1) FROM cr_business_partner_dtl where partner_ID='"+id+"' and LOAN_ID='"+vo.getLoanId()+"' and RATE='"+vo.getEffectiveRate()+"'and PERCENTAGE='"+vo.getPartnerPercentage()+"'  and service_partner_flag = '"+vo.getServicingPartnerFlag()+"' and loan_amount='"+vo.getPartnerAmount()+"' and IFNULL(lead_partner_flag,'N') ='"+vo.getLeadPartnerFlag()+"' ";
exist_id1=Integer.parseInt(ConnectionDAO.singleReturn(qry1));
logger.info("userNameQ1::::"+exist_id1);
logger.info("qry1 amanddd:::"+qry1);
if(exist_id1>0){
	result="EX";
	return result;
}
if (CommonFunction.checkNull(vo.getLeadPartnerFlag()).equalsIgnoreCase("Y"))
{
qry7="SELECT count(1) FROM cr_business_partner_dtl where  LOAN_ID='"+vo.getLoanId()+"'  and IFNULL(lead_partner_flag,'N') ='Y'  and  partner_ID <>'"+id+"' ";
exist_id1=Integer.parseInt(ConnectionDAO.singleReturn(qry7));
logger.info("userNameQ1::::"+exist_id1);
logger.info("qry7:::"+qry7);
if(exist_id1>0){
	result="leadPartnerExist";
	return result;
}
}
qry3="SELECT ifnull(sum(percentage),0) FROM cr_business_partner_dtl where  LOAN_ID='"+vo.getLoanId()+"'";
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

		qry6="SELECT self_flag FROM com_partner_details_m where   partner_ID='"+id+"' ";
		 logger.info("the self flag : "+qry6);
		flag =ConnectionDAO.singleReturn(qry6);
		
		//parvez ends
		
//end by saorabh


qry="SELECT count(1) FROM cr_business_partner_dtl where partner_ID='"+id+"' and LOAN_ID='"+vo.getLoanId()+"'  ";
 logger.info("the qry of count is : "+qry);
exist_id=Integer.parseInt(ConnectionDAO.singleReturn(qry));

		logger.info("userNameQ1::::"+exist_id);
		logger.info("id for partner qry dtl::::"+id);
	//amandeep adds	
		if(vo.getServicingPartnerFlag().equalsIgnoreCase("Y"))
		{
			qry5="SELECT ifnull(LOAN_EFF_RATE,0) FROM cr_loan_dtl where  LOAN_ID='"+vo.getLoanId()+"' ";
			 logger.info("the qry5 of count is : "+qry5);
			 loanRate =ConnectionDAO.singleReturn(qry5);
		}
		//amandeep ends
			if(exist_id==0){
				
				qry2="SELECT count(1) FROM cr_business_partner_dtl where  LOAN_ID='"+vo.getLoanId()+"'  and service_partner_flag='Y'";
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
			bufInsSql.append("insert into cr_business_partner_dtl(partner_ID,LOAN_ID,EFFECTIVE_RATE,PERCENTAGE,PARTNER_TYPE,MAKER_ID,LOAN_AMOUNT,self_flag,MAKER_DATE,SERVICE_PARTNER_FLAG,LEAD_PARTNER_FLAG,RATE_TYPE,RATE,REC_STATUS,REFRESH_FLAG)");
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
			bufInsSql.append(" ?,");
			bufInsSql.append(" 'P', ");
			bufInsSql.append(" 'N' )");
			if (CommonFunction.checkNull(vo.getLbxpartnerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(vo.getLbxpartnerId());
			
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
			else if(CommonFunction.checkNull(vo.getLeadPartnerFlag()).equalsIgnoreCase("Y"))
				insertPrepStmtObject.addString(businessType.trim());
			else
				insertPrepStmtObject.addNull();

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
				bufInsSql.append(" where partner_ID=? and loan_id=?" );
				
				if (CommonFunction.checkNull(vo.getLbxpartnerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxpartnerId());
				if (CommonFunction.checkNull(vo.getLoanId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLoanId().trim());
				insertPrepStmtObject.setSql(bufInsSql.toString());
				
				qryList.add(insertPrepStmtObject);
				
				
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				
				
				
				qry2="SELECT count(1) FROM cr_business_partner_dtl where  LOAN_ID='"+vo.getLoanId()+"' and service_partner_flag='Y' ";
				 logger.info("the qry2 of count is : "+qry2);
				prevent=Integer.parseInt(ConnectionDAO.singleReturn(qry2));
						logger.info("Prevent Service Partner::::"+prevent);
						if(prevent>0 && vo.getServicingPartnerFlag().equalsIgnoreCase("Y"))
						{
							return "prevent";
						}
			
				
				
				qry4="SELECT ifnull(percentage,0) FROM cr_business_partner_dtl where partner_ID='"+id+"' and LOAN_ID='"+vo.getLoanId()+"' ";
				 logger.info("the qry4 of count is : "+qry4);
				float  value =Float.parseFloat(ConnectionDAO.singleReturn(qry4));
				sum=(sum-value)+Float.parseFloat(vo.getPartnerPercentage());
				if(sum>100.00)
				{
					return "contributionAmount";
				}
				insertPrepStmtObject = new PrepStmtObject();
				bufInsSql = new StringBuilder();
				bufInsSql.append("UPDATE cr_business_partner_dtl SET partner_ID=?,LOAN_ID=?,EFFECTIVE_RATE=?,percentage=?,partner_type=?,MAKER_ID=?,LOAN_AMOUNT=?,MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),service_partner_flag=?,self_flag=?,LEAD_PARTNER_FLAG=?,RATE_TYPE=?,RATE=?,rec_status='P',REFRESH_FLAG='N'  "); 
				bufInsSql.append(" where partner_ID=? and loan_id=?  " );
				
				
				
				if (CommonFunction.checkNull(vo.getLbxpartnerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxpartnerId());
				
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
				else if(CommonFunction.checkNull(vo.getLeadPartnerFlag()).equalsIgnoreCase("Y"))
					insertPrepStmtObject.addString(businessType.trim());
				else
					insertPrepStmtObject.addNull();
				

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
				
				
				if (CommonFunction.checkNull(vo.getLbxpartnerId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLbxpartnerId());
				
				if (CommonFunction.checkNull(vo.getLoanId()).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(vo.getLoanId().trim());
				
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
//			if(CommonFunction.checkNull(result).equalsIgnoreCase("S") || CommonFunction.checkNull(result).equalsIgnoreCase("M"))
//			{
//			insertPrepStmtObject=null;
//			bufInsSql=null;
//			
//			insertPrepStmtObject = new PrepStmtObject();
//			bufInsSql = new StringBuilder();
//			bufInsSql.append("UPDATE CR_LOAN_DTL SET REFRESH_FLAG=CONCAT(left(REFRESH_FLAG,3-1),'Y',right(REFRESH_FLAG,lenGTH(REFRESH_FLAG)-3)) WHERE LOAN_ID=?"); 
//		
//			if (CommonFunction.checkNull(vo.getLoanId()).equalsIgnoreCase(""))
//				insertPrepStmtObject.addNull();
//			else
//				insertPrepStmtObject.addString(vo.getLoanId().trim());
//			insertPrepStmtObject.setSql(bufInsSql.toString());
//			
//			rspQryList.add(insertPrepStmtObject);
//			
//			
//			ConnectionDAO.sqlInsUpdDeletePrepStmt(rspQryList);
//				
//			}	

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
public ArrayList<LoanDetailForCMVO> getPartnerDetailsForRsp(LoanDetailForCMVO loanDetailForCMVO, String loanId,String businessType) {
	ArrayList list=new ArrayList();
	try
	{
		StringBuilder query =new StringBuilder();
		 query.append(" select a.BP_ID,a.partner_ID,b.name,a.LOAN_ID,c.loan_no,a.RATE,round(ifnull(a.percentage,0),2),case a.service_partner_flag when 'Y' then 'Yes' when 'N' then 'No' end as service_partner_flag, round(ifnull(loan_amount,0),2),case a.lead_partner_flag when 'Y' then 'Yes' when 'N' then 'No' end as leadflag ,a.RATE_TYPE,a.EFFECTIVE_RATE,case ifnull(a.RATE_TYPE,'E') when 'E' then 'Effective' when 'F' then 'Flat' end as rate_type_desc from cr_business_partner_dtl a join com_partner_details_m b on a.partner_ID=b.partner_ID left join cr_loan_dtl c on c.loan_id=a.loan_id "+
						" where a.LOAN_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()+"  ");
		
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


@Override
public ArrayList<LoanDetailForCMVO> getPartnerBusDetailsForRsp(LoanDetailForCMVO loanDetailForCMVO,
		String id, String loanId,String businessType) {
	ArrayList list=new ArrayList();
	try
	{
		StringBuilder query =new StringBuilder();
		 query.append(" select a.BP_ID,a.partner_ID,b.name,a.LOAN_ID,c.loan_no,a.RATE ,round(ifnull(A.PERCENTAGE,0),2),case a.service_partner_flag when 'Y' then 'Yes' when 'N' then 'No' end as service_partner_flag,round(ifnull(loan_amount,0),2),case a.lead_partner_flag when 'Y' then 'Yes' when 'N' then 'No' end as leadflag,a.RATE_TYPE,a.EFFECTIVE_RATE,case ifnull(a.RATE_TYPE,'E') when 'E' then 'Effective' when 'F' then 'Flat' end as rate_type_desc from cr_business_partner_dtl a join com_partner_details_m b on a.partner_ID=b.partner_ID left join cr_loan_dtl c on c.loan_id=a.loan_id "+
						" where a.LOAN_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()+" and a.BP_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(id)).trim()+"");
		
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


@Override
public int deletePartnerDtlForRsp(String partnerDtl, String loanId,String businessType) {
	
    int status=0;
    boolean qryStatus = false;
   
    ArrayList qryList = new ArrayList();
	
try{
	String query="delete from cr_business_partner_dtl where BP_ID=? ";
	PrepStmtObject insPrepStmtObject = new PrepStmtObject();
	if(CommonFunction.checkNull(partnerDtl).trim().equalsIgnoreCase(""))
		insPrepStmtObject.addNull();
	else
		insPrepStmtObject.addString(StringEscapeUtils.escapeSql(CommonFunction.checkNull(partnerDtl)).trim());
	
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
	return status;}


@Override
public ArrayList<LoanDetailForCMVO> getPartnerDetailsforPopUpForRsp(
		LoanDetailForCMVO loanDetailForCMVO, String loanId,
		String businessType) {
	ArrayList list=new ArrayList();
	String partnerType="";
	PrepStmtObject insertPrepStmtObject=null;
	StringBuilder bufInsSql = null;
	logger.info("businessType "+businessType);
	//String query1=" select FUNDING_TYPE from cr_loan_dtl where loan_id ='"+loanId+"'  ";
   	
	try
	{
	//	partnerType=ConnectionDAO.singleReturn(query1);	
		StringBuilder query =new StringBuilder();
		 query.append(" select a.BP_ID,a.partner_ID,b.name,a.LOAN_ID,c.loan_no,a.RATE ,round(ifnull(a.PERCENTAGE,0),2),case a.service_partner_flag when 'Y' then 'Yes' when 'N' then 'No' end as service_partner_flag,round(ifnull(loan_amount,0),2),case a.lead_partner_flag when 'Y' then 'Yes' when 'N' then 'No' end as leadflag,a.EFFECTIVE_RATE,a.RATE_TYPE,case ifnull(a.RATE_TYPE,'E') when 'E' then 'Effective' when 'F' then 'Flat' end as rate_type_desc  from cr_business_partner_dtl a join com_partner_details_m b on a.partner_ID=b.partner_ID left join cr_loan_dtl c on c.loan_id=a.loan_id "+
						" where a.LOAN_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()+" ");
		
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
		loanId=null;			
	}

	return list;

}

public String forwardPartnerDetailsForRsp(String loanId) {
	
	logger.info("In forwardPartnerDetailsForRsp....loanId " + loanId);
	
	ArrayList qryList = new ArrayList();
	
	String status = "";
	String countQuery="";
	int countFlag=0;
	boolean finalStatus=false;
	try {
		
	countQuery="select count(1) from cr_business_partner_dtl where loan_id ="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+" ";
	countFlag = Integer.parseInt(ConnectionDAO.singleReturn(countQuery));
	logger.info("countQuery:::::::::::"+countQuery);
	logger.info("countFlag:::::::::::"+countFlag);
	if(countFlag==0)
	{
		status="saveOneRow";
	}	
	if(CommonFunction.checkNull(status).equalsIgnoreCase(""))
	{	
	countQuery="select count(1) from cr_business_partner_dtl where loan_id ="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+" and service_partner_flag = 'Y'";
	countFlag = Integer.parseInt(ConnectionDAO.singleReturn(countQuery));
	logger.info("countQuery:::::::::::"+countQuery);
	logger.info("countFlag:::::::::::"+countFlag);
		if(countFlag==0 || countFlag>1)
		{
		status="al";
		}
	}
	if(CommonFunction.checkNull(status).equalsIgnoreCase(""))
	{
	 countQuery=" SELECT COUNT(1) FROM cr_business_partner_dtl WHERE LOAN_ID="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId).trim())+"  AND IFNULL(LEAD_PARTNER_FLAG,'N')='Y' ";
	 countFlag=Integer.parseInt(ConnectionDAO.singleReturn(countQuery));
		logger.info("countQuery:::::::::::"+countQuery);
		logger.info("countFlag:::::::::::"+countFlag);
	 if(countFlag==0 || countFlag>1)
		{
			status="ONEMUSTLEADPARTNER";
		}
	}
	
	if(CommonFunction.checkNull(status).equalsIgnoreCase(""))
	{
	countQuery=" select count(1) from cr_business_partner_dtl a "+
			" join cr_loan_dtl b on a.loan_id=b.loan_id and a.service_partner_flag='Y' "+
			" and round(IFNULL(a.EFFECTIVE_RATE,''),4)=round(b.LOAN_EFF_RATE,4) and a.loan_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()+" ";
	countFlag=	Integer.parseInt(ConnectionDAO.singleReturn(countQuery)); 
	logger.info("countQuery:::::::::::"+countQuery);
	logger.info("countFlag:::::::::::"+countFlag);
	 if(countFlag==0)
	 {
		 status="RATENOTMATCH"; 
	 }
	}
	
	if(CommonFunction.checkNull(status).equalsIgnoreCase(""))
	{
		countQuery="  select if(b.loan_loan_amount=sum(a.loan_amount),1,0) from cr_business_partner_dtl a  "+
		 " join cr_loan_dtl b on a.loan_id=b.loan_id and a.loan_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()+" ";  
		 countFlag=Integer.parseInt(ConnectionDAO.singleReturn(countQuery)); 
			logger.info("countQuery:::::::::::"+countQuery);
			logger.info("countFlag:::::::::::"+countFlag);
		 if(countFlag==0)
		 {
			 status="checkAllParticipationAmt"; 
		 }
	}
	
	
	if(CommonFunction.checkNull(status).equalsIgnoreCase(""))
	{
		countQuery="select if((select count(1) from  cr_business_partner_dtl where loan_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()+")=(select count(1) from  cr_business_partner_dtl where loan_id="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()+" and ifnull(REFRESH_FLAG,'N')='Y'),'1','0')flag";
		 
		countFlag=Integer.parseInt(ConnectionDAO.singleReturn(countQuery)); 
		logger.info("countQuery:::::::::::"+countQuery);
		logger.info("countFlag:::::::::::"+countFlag);
		 if(countFlag==0)
		 {
			 status="SAVEALLSTAGE"; 
		 }
	}
	
	if(CommonFunction.checkNull(status).equalsIgnoreCase(""))
	{
	String queryBussDtl = "update cr_business_partner_dtl set REC_STATUS='F' where LOAN_ID="+ loanId;
	String queryConDtl = "update consortium_repayment_dtl set REC_STATUS='F' where LOAN_ID="+ loanId;
	String queryInsDtl = "update cr_rsp_installment_plan set REC_STATUS='F' where LOAN_ID="+ loanId;

	qryList.add(queryBussDtl);
	qryList.add(queryConDtl);
	qryList.add(queryInsDtl);


		finalStatus = ConnectionDAO.sqlInsUpdDelete(qryList);
		if(finalStatus){
			status="S";
		}
		else{
			status="E";
		}
		logger.info("In forwardPartnerDetailsForRsp......................status= "
						+ status);
	}
	} catch (SQLException e) {
	
		e.printStackTrace();
		e=null;
	}
	finally
	{
		loanId=null;
		countQuery=null;
	}
	return status;
}

public String rspApproval(LoanDetailForCMVO loanDetailForCMVO){
	logger.info("In rspApproval....... ");
	//CallableStatement cst=null;
	//Connection con=ConnectionDAO.getConnection();
	LoanDetailForCMVO cv = (LoanDetailForCMVO) loanDetailForCMVO;
	ArrayList<Object> in =new ArrayList<Object>();
	ArrayList<Object> out =new ArrayList<Object>();
	ArrayList outMessages = new ArrayList();
	String s1="";
	String s2="";
	String procval="";

	try {

		
		in.add("1");
 	    in.add(cv.getLoanId());
		String makerDate=CommonFunction.checkNull(cv.getMakerDate()).trim();
		makerDate=CommonFunction.changeFormat(makerDate);
 	    in.add(makerDate);
 	    in.add(cv.getMakerId());
 	    in.add(cv.getRecStatus());
 	    in.add(cv.getRemarks());

 	    out.add(s1);
 	    out.add(s2);
 	//    out.add(s3);
 	    outMessages=(ArrayList) ConnectionDAO.callSP("MANUAL_CONSORTIUM_REPAYMENT_SCHEDULE",in,out);
 	    s1=CommonFunction.checkNull(outMessages.get(0));
 	    s2=CommonFunction.checkNull(outMessages.get(1));
 		
		if(s1!=null && s1.equalsIgnoreCase("S"))
		{

			procval=CommonFunction.checkNull(cv.getRecStatus()).trim();		
		}else{
			procval=s2;
		}
		logger.info("s2: "+s2);
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		
		s1=null;
		s2=null;
		in.clear();
		in=null;
		out.clear();
		out=null;
		outMessages.clear();
		outMessages=null;
	}
	return procval;	
}



public ArrayList<Object> fetchLoanDetailForRepaymentServiceForAuthor(CommonLoanVo vo,HttpServletRequest request) {
	ArrayList list=new ArrayList();
	int count=0;
	int startRecordIndex=0;
	int endRecordIndex = no;
//	String userName="";
	StringBuilder userName=new StringBuilder();
	
	logger.info("here userid++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+vo.getReportingToUserId());
	try{
		StringBuilder userNameQ=new StringBuilder();
		 userNameQ.append("select USER_NAME from sec_user_m where REC_STATUS='A' and USER_ID='"+vo.getReportingToUserId()+"'");
		userName.append(ConnectionDAO.singleReturn(userNameQ.toString()));
		logger.info("userNameQ: "+userNameQ+" userName: "+userName);
		userNameQ=null;
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}		

	StringBuilder lonaNo=new StringBuilder();
	StringBuilder aDate=new StringBuilder();
	StringBuilder custName=new StringBuilder();
	StringBuilder productId=new StringBuilder();
	StringBuilder scheme=new StringBuilder();
	
    lonaNo.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxLoanNoHID())).trim());
	aDate.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getAgrementDate())).trim());
	custName.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getCustomerName())).trim());
	productId.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxProductID())).trim());
	scheme.append(StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getLbxscheme())).trim());

	 
	try
	{
		
	    
		ArrayList header=null;
		CommonLoanVo fetchVo= null;
		boolean appendSQL=false;
		StringBuffer bufInsSql=new StringBuffer();
		StringBuffer bufInsSqlTempCount = new StringBuffer();
 
		

		bufInsSql.append(" select distinct d.LOAN_ID,d.LOAN_NO,DATE_FORMAT(d.LOAN_AGREEMENT_DATE,'"+dateFormat+"'),g.CUSTOMER_NAME,p.PRODUCT_DESC, s.SCHEME_DESC, DATE_FORMAT(dl.DEAL_SANCTION_VALID_TILL,'"+dateFormat+"'),(SELECT USER_NAME FROM SEC_USER_M WHERE USER_ID=D.MAKER_ID) MAKER_ID,LOAN_DEAL_ID,z.PARTNER_ID,y.NAME,IF(IFNULL(Z.partner_type,'')='',IFNULL(D.funding_type,''), IFNULL(Z.partner_type,'')) from cr_loan_dtl d");
		bufInsSqlTempCount.append("SELECT distinct COUNT(1) FROM cr_loan_dtl d ");
		
		bufInsSql.append("  join cr_business_partner_dtl z on d.loan_id=z.LOAN_ID and  z.REC_STATUS='F'  and z.service_partner_flag='N'  and  z.partner_type in('CP','CB')    ");
		bufInsSqlTempCount.append("  join cr_business_partner_dtl z on d.loan_id=z.LOAN_ID and  z.REC_STATUS='F'  and z.service_partner_flag='N'  and  z.partner_type in('CP','CB')   ");
		
		bufInsSql.append(" left join com_partner_details_m y on z.partner_ID=y.PARTNER_ID  ");
		bufInsSqlTempCount.append(" left join com_partner_details_m y on z.partner_ID=y.PARTNER_ID  ");
		
		bufInsSql.append(" left join gcd_customer_m g on g.CUSTOMER_ID=d.LOAN_CUSTOMER_ID ");
		bufInsSqlTempCount.append(" left join gcd_customer_m g on g.CUSTOMER_ID=d.LOAN_CUSTOMER_ID ");
		
		bufInsSql.append(" left join cr_product_m p on d.LOAN_PRODUCT=p.PRODUCT_ID  ");
		bufInsSqlTempCount.append(" left join cr_product_m p on d.LOAN_PRODUCT=p.PRODUCT_ID  ");
		
		bufInsSql.append(" left join cr_scheme_m s on d.LOAN_SCHEME=s.SCHEME_ID ");
		bufInsSqlTempCount.append(" left join cr_scheme_m s on d.LOAN_SCHEME=s.SCHEME_ID ");
		
		bufInsSql.append(" left join cr_deal_loan_dtl dl on d.LOAN_DEAL_ID=dl.DEAL_ID  AND dl.deal_loan_id=d.loan_deal_loan_id");
		bufInsSqlTempCount.append(" left join cr_deal_loan_dtl dl on d.LOAN_DEAL_ID=dl.DEAL_ID  AND dl.deal_loan_id=d.loan_deal_loan_id");
				
		 
		

			//Nishant Space starts
			   String branch="";
			   if(vo.getAllBranches().equalsIgnoreCase("on"))
			   {
				   String branchMappedToUserQuery="SELECT GROUP_CONCAT(BRANCH_ID) FROM sec_user_branch_dtl WHERE USER_ID='"+CommonFunction.checkNull(vo.getReportingToUserId()).trim()+"' AND REC_STATUS='A'";
				   branch=ConnectionDAO.singleReturn(branchMappedToUserQuery);
				   logger.info("branchMappedToUserQuery: "+branchMappedToUserQuery+"      branches maped to user:   "+branch);
			   }
			   else if(!(vo.getLbxBranchId().equalsIgnoreCase("")))
			   {
				   branch=vo.getLbxBranchId();
				   logger.info("i am in");
			   }

			//Nishant Space End
			if((!(vo.getLbxDealNo().equalsIgnoreCase("")))&&(!(vo.getLbxLoanNoHID().equalsIgnoreCase("")))&&(!(vo.getAgrementDate().equalsIgnoreCase("")))&&(!(vo.getCustomerName().equalsIgnoreCase("")))&&(!(vo.getLbxProductID().equalsIgnoreCase("")))&&(!(vo.getLbxscheme().equalsIgnoreCase(""))))
			{
		   	  bufInsSql.append("WHERE   D.LOAN_BRANCH IN ("+StringEscapeUtils.escapeSql(branch).trim()+")  AND d.LOAN_ID='"+vo.getLbxLoanNoHID()+"' AND g.CUSTOMER_NAME like'%"+vo.getCustomerName()+"%' AND d.LOAN_PRODUCT='"+vo.getLbxProductID()+"'AND d.LOAN_SCHEME='"+vo.getLbxscheme()+"' and ifnull(d.funding_type,'')='OF' and ifnull(d.DISBURSAL_STATUS,'')='F' and ifnull(d.rec_status,'')='A' and d.LOAN_REPAYMENT_TYPE='I' AND Z.MAKER_ID<>'"+StringEscapeUtils.escapeSql(vo.getUserId()).trim()+"' ");
		   	  bufInsSqlTempCount.append("WHERE   D.LOAN_BRANCH IN ("+StringEscapeUtils.escapeSql(branch).trim()+")  AND d.LOAN_ID='"+vo.getLbxLoanNoHID()+"' AND g.CUSTOMER_NAME like'%"+vo.getCustomerName()+"%' AND d.LOAN_PRODUCT='"+vo.getLbxProductID()+"'AND d.LOAN_SCHEME='"+vo.getLbxscheme()+"' and ifnull(d.funding_type,'')='OF' and ifnull(d.DISBURSAL_STATUS,'')='F' and ifnull(d.rec_status,'')='A'  and d.LOAN_REPAYMENT_TYPE='I' AND Z.MAKER_ID<>'"+StringEscapeUtils.escapeSql(vo.getUserId()).trim()+"' ");
			}
			
			if(((vo.getLbxDealNo().equalsIgnoreCase("")))||((vo.getLbxLoanNoHID().equalsIgnoreCase("")))||((vo.getAgrementDate().equalsIgnoreCase("")))||((vo.getCustomerName().equalsIgnoreCase("")))||((vo.getLbxProductID().equalsIgnoreCase("")))||((vo.getLbxscheme().equalsIgnoreCase("")))||((vo.getStage().equalsIgnoreCase("")))){
				appendSQL=true;
			}
			
			if(appendSQL){
				logger.info("In Where Clause");
				bufInsSql.append(" WHERE  'a'='a' and ifnull(d.funding_type,'')='OF' and ifnull(d.DISBURSAL_STATUS,'')='F' and ifnull(d.rec_status,'')='A' and d.LOAN_REPAYMENT_TYPE='I' AND Z.MAKER_ID<>'"+StringEscapeUtils.escapeSql(vo.getUserId()).trim()+"' ");
				bufInsSqlTempCount.append(" WHERE  'a'='a' and ifnull(d.funding_type,'')='OF' and ifnull(d.DISBURSAL_STATUS,'')='F' and ifnull(d.rec_status,'')='A' and d.LOAN_REPAYMENT_TYPE='I' AND  Z.MAKER_ID<>'"+StringEscapeUtils.escapeSql(vo.getUserId()).trim()+"'  ");				
			}
		
			
			if(!CommonFunction.checkNull(branch).equalsIgnoreCase("")) {
				bufInsSql.append(" AND  D.LOAN_BRANCH IN ("+StringEscapeUtils.escapeSql(branch).trim()+")  ");
				bufInsSqlTempCount.append(" AND   D.LOAN_BRANCH IN ("+StringEscapeUtils.escapeSql(branch).trim()+")    ");
		   	 appendSQL=true;
		   	  
		     }	
		
		
		 if((!(vo.getLbxLoanNoHID().equalsIgnoreCase("")))) {
	        bufInsSql.append(" and  d.LOAN_ID='"+vo.getLbxLoanNoHID()+"'");
	        bufInsSqlTempCount.append(" and  d.LOAN_ID='"+vo.getLbxLoanNoHID()+"'");
	   	 appendSQL=true;
	   	  
	     }
		 
			
		 if((!(vo.getCustomerRSPId().equalsIgnoreCase("")))) {
	        bufInsSql.append(" and  z.PARTNER_ID='"+vo.getCustomerRSPId()+"'");
	        bufInsSqlTempCount.append(" and  z.PARTNER_ID='"+vo.getCustomerRSPId()+"'");
	   	 appendSQL=true;
	   	  
	     }
		 
					
		
		if((!(vo.getCustomerName().equalsIgnoreCase("")))) {
	   	  bufInsSql.append("and  g.CUSTOMER_NAME like'%"+vo.getCustomerName()+"%' ");
	   	  bufInsSqlTempCount.append("and  g.CUSTOMER_NAME like'%"+vo.getCustomerName()+"%'");
	   	  appendSQL=true;
	     }
		if((!(vo.getLbxProductID().equalsIgnoreCase("")))) {
		   	  bufInsSql.append("and  d.LOAN_PRODUCT='"+vo.getLbxProductID()+"'");
		   	  bufInsSqlTempCount.append("and  d.LOAN_PRODUCT='"+vo.getLbxProductID()+"'");
		   	  appendSQL=true;
		     }
		if((!(vo.getLbxscheme().equalsIgnoreCase("")))) {
		   	  bufInsSql.append("and  d.LOAN_SCHEME='"+vo.getLbxscheme()+"'");
		   	  bufInsSqlTempCount.append("and  d.LOAN_SCHEME='"+vo.getLbxscheme()+"'");
		   	  appendSQL=true;
		     }
		if((!(vo.getLbxUserId().equalsIgnoreCase("")))) {
		bufInsSql.append(" AND z.MAKER_ID<>'"+StringEscapeUtils.escapeSql(vo.getLbxUserId()).trim()+"' ");	
		bufInsSqlTempCount.append(" AND z.MAKER_ID<>'"+StringEscapeUtils.escapeSql(vo.getLbxUserId()).trim()+"'");
		appendSQL=true;
		}
		logger.info("In appendSQL true---- "+appendSQL);
		
		if(appendSQL){
			logger.info("In appendSQL true---- ");
			String  tmp = bufInsSql.toString();
			String tmp1 = bufInsSqlTempCount.toString();
			
	     logger.info("In fetchLoanDetail() ## tmp ## "+tmp);
	     logger.info("In appendSQL true----  in check index Of"+tmp.lastIndexOf("AND") +"------"+(tmp.length()-3));
	   
	     if(tmp.lastIndexOf("AND")== (tmp.length()-3) || tmp1.lastIndexOf("AND")==(tmp1.length()-3))
	     {
	     logger.info("In appendSQL true----  in check index Of");
	     tmp = (tmp).substring(0,(tmp.length()-4));
	     tmp1 = (tmp1).substring(0,(tmp1.length()-4));
	     logger.info("fetchLoanDetail Query...tmp."+tmp);
	     header = ConnectionDAO.sqlSelect(tmp);
	     count =Integer.parseInt(ConnectionDAO.singleReturn(tmp1.toString()));
	     
	      }else
	      {
	     	  logger.info("fetchLoanDetail Query...tmp."+tmp);
	     	 header = ConnectionDAO.sqlSelect(tmp); 
	     	 count =Integer.parseInt(ConnectionDAO.singleReturn(tmp1.toString()));
	       }
		 }else {
	    	  
				LoggerMsg.info("search Query...else-------." + bufInsSql);
				LoggerMsg.info("bufInsSqlTempCount **************************** : "+bufInsSqlTempCount.toString());
	            
				count =Integer.parseInt(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
				
				
				if(( lonaNo.toString().trim()==null && aDate.toString().trim()==null && custName.toString().trim()==null && productId.toString().trim()==null && scheme.toString().trim()==null) || ( lonaNo.toString().trim().equalsIgnoreCase("") && aDate.toString().trim().equalsIgnoreCase("") && custName.toString().trim().equalsIgnoreCase("") && productId.toString().trim().equalsIgnoreCase("") && scheme.toString().trim().equalsIgnoreCase("")) || vo.getCurrentPageLink()>1)
				{
				
				LoggerMsg.info("current PAge Link no .................... "+vo.getCurrentPageLink());
				if(vo.getCurrentPageLink()>1)
				{
					startRecordIndex = (vo.getCurrentPageLink()-1)*no;
					endRecordIndex = no;
					LoggerMsg.info("startRecordIndex .................... "+startRecordIndex);
					LoggerMsg.info("endRecordIndex .................... "+endRecordIndex);
				}
				
				bufInsSql.append(" limit "+startRecordIndex+","+endRecordIndex);
				
				}
				LoggerMsg.info("query : "+bufInsSql);
				
				header = ConnectionDAO.sqlSelect(bufInsSql.toString());
			}

					
		for(int i=0;i<header.size();i++){
			logger.info("header"+header.size());
			ArrayList header1=(ArrayList)header.get(i);
			if(header1.size()>0)
			{
				fetchVo = new CommonLoanVo();
				fetchVo.setModifyNo("<a href=repaymentServiceAction.do?method=openPartnerDetailsForRsp&loanId="
			+ CommonFunction.checkNull(header1.get(0)).trim().toString() + "&businessType="+ CommonFunction.checkNull(header1.get(11)).trim().toString() +"&customerRSPId="+ CommonFunction.checkNull(header1.get(9)).trim().toString()+ " >"
				+ CommonFunction.checkNull(header1.get(1)).trim().toString() + "</a>");
				
				fetchVo.setLbxLoanNoHID((CommonFunction.checkNull(header1.get(0)).trim()));
				fetchVo.setLoanNo((CommonFunction.checkNull(header1.get(1)).trim()));
				fetchVo.setAgrementDate((CommonFunction.checkNull(header1.get(2)).trim()));
				fetchVo.setCustomerName((CommonFunction.checkNull(header1.get(3)).trim()));
				fetchVo.setProduct((CommonFunction.checkNull(header1.get(4)).trim()));
				fetchVo.setScheme((CommonFunction.checkNull(header1.get(5)).trim()));
				fetchVo.setValidSancTill((CommonFunction.checkNull(header1.get(6)).trim()));
				fetchVo.setReportingToUserId((CommonFunction.checkNull(header1.get(7)).trim()));
				fetchVo.setCustomerRSPName((CommonFunction.checkNull(header1.get(10)).trim()));
				fetchVo.setTotalRecordSize(count);
				list.add(fetchVo);
				
			}
		}
//		if( header.size()==0)
//		{
//			CommonLoanVo fetchMVo = new CommonLoanVo();
//			fetchMVo.setTotalRecordSize(count);
//			list.add(fetchMVo);
//			request.setAttribute("flag","yes");
//			//LoggerMsg.info("getTotalRecordSize : "+fetchMVo.getTotalRecordSize());
//		}
		bufInsSql=null;
		bufInsSqlTempCount=null;
		fetchVo=null;			
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
finally
{
 lonaNo=null;
 custName=null;
 productId=null;
 scheme=null;
 aDate=null;
 userName=null;
 vo=null;	 
}
	return list;
}
		public ArrayList getloanListInLoanForEdit(String loanId) {
			ArrayList<LoanDetailForCMVO> loanDetailList = new ArrayList<LoanDetailForCMVO>();
			logger.info("In getloanListInLoan: " + loanId);
			
			ArrayList mainlist = new ArrayList();
			ArrayList subList = new ArrayList();
			StringBuilder query = new StringBuilder(); 
			logger.info("StringBuilder: Capacity: "+query.capacity());
			LoanDetailForCMVO loanvo = null;
			try {
				
				query.append("select distinct LOAN_ID,LOAN_NO,G.CUSTOMER_NAME,LOAN_PRODUCT,p.PRODUCT_DESC,LOAN_SCHEME,s.SCHEME_DESC,DATE_FORMAT(d.DEAL_SANCTION_VALID_TILL,'"+dateFormat+"'),DATE_FORMAT(LOAN_AGREEMENT_DATE,'"+dateFormat+"'),");
				query.append(" DATE_FORMAT(LOAN_REPAY_EFF_DATE,'"+dateFormat+"'),LOAN_ASSET_COST,LOAN_MARGIN_RATE,LOAN_MARGIN_AMOUNT,LOAN_LOAN_AMOUNT,LOAN_TENURE,LOAN_REPAYMENT_FREQ,LOAN_RATE_TYPE,LOAN_RATE_METHOD,");
				query.append(" LOAN_BASE_RATE_TYPE,LOAN_BASE_RATE,LOAN_MARKUP,LOAN_FINAL_RATE,LOAN_REPAYMENT_TYPE,LOAN_INSTALLMENT_TYPE,LOAN_INSTALLMENT_MODE,");
				query.append(" LOAN_REPAYMENT_MODE,LOAN_NO_OF_INSTALLMENT,LOAN_NUMBER_DISBURSAL,LOAN_ADVANCE_INSTL,LOAN_DUE_DAY,d.DEAL_SANCTION_AMOUNT,d.DEAL_UTILIZED_AMOUNT,deal.DEAL_ID,deal.DEAL_NO,DATE_FORMAT(LOAN_MATURITY_DATE,'"+dateFormat+"'),m.DESCRIPTION,LOAN_MAKER_REMARKS,loan_remarks,PO_DO_FLAG,LOAN_DUE_DAY,d.DEAL_IRR2,DATE_FORMAT(max(A.APPROVAL_DATE),'"+dateFormat+"'),INSUARENCE_DONE_BY,");
				query.append(" p.ONE_DEAL_ONE_LOAN,DATE_FORMAT(c.NEXT_DUE_DATE,'"+dateFormat+"'),c.REC_STATUS,s.MIN_FLAT_RATE,s.MAX_FLAT_RATE,s.MIN_EFF_RATE,s.MAX_EFF_RATE,s.MIN_TENURE,s.MAX_TENURE,c.AREA_CODE," +
						"(SELECT B.AREA_CODE_NAME FROM com_areacode_m B WHERE B.AREA_CODE=c.AREA_CODE)AS areaCodeName,p.ASSET_FLAG,c.LOAN_INT_CALC_FROM,i.DESCRIPTION,c.NET_LTV,c.TENURE_IN_DAY,p.DAYS_BASIS,(select DESCRIPTION from generic_master where GENERIC_KEY='INSTALLMENT_TYPE' AND REC_STATUS='A' AND VALUE=LOAN_INSTALLMENT_TYPE)AS INSTALLMENT_TYPE_DESC,c.LOAN_FLOATING_FIXED_PERIOD,c.LOAN_SECTOR_TYPE,ifnull(c.NO_OF_ASSET,0),LOAN_REFERENCE_NO,LOAN_DEAL_ID,c.LOAN_CLASSIFICATION ,(select DESCRIPTION from generic_master where GENERIC_KEY='repayment_mode' AND REC_STATUS='A' AND VALUE=LOAN_REPAYMENT_MODE)AS LOAN_REPAYMENT_DESC,GM1.DESCRIPTION,P.ONE_DEAL_ONE_LOAN,C.SERVICE_BRANCH,(SELECT BRANCH_DESC FROM com_branch_m WHERE BRANCH_ID=C.SERVICE_BRANCH),ifnull(c.insurance_premium,0)as insurance_premium,ifnull(c.DEAL_LOAN_AMOUNT_NEW,loan_loan_amount)as req_amount ");
				////	Himanshu Verma		Changes started for Facility Details
				//query.append(" ,(SELECT PARAMETER_VALUE FROM PARAMETER_MST WHERE PARAMETER_KEY = 'FACILITY_DETAIL_FUNCTIONALITY') FACILITY_DTL_FLAG, ");
				//query.append(" P.ONE_DEAL_ONE_LOAN, (select round(loan_amount,2) from cr_facility_details where deal_id = deal.deal_id and scheme_id = s.scheme_id) PROPOSED_AMOUNT,C.SERVICE_BRANCH,(SELECT BRANCH_DESC FROM com_branch_m WHERE BRANCH_ID=C.SERVICE_BRANCH) ");
				////	Himanshu Verma		Changes ended for Facility Details
				query.append(" from cr_loan_dtl_edit c");
				query.append(" left join cr_product_m p on c.LOAN_PRODUCT=p.PRODUCT_ID");
				query.append(" left join cr_scheme_m s on c.LOAN_SCHEME=s.SCHEME_ID");
				query.append(" left join cr_deal_loan_dtl d on d.DEAL_ID=c.LOAN_DEAL_ID  AND d.deal_loan_id=c.loan_deal_loan_id");
				query.append(" left join cr_deal_dtl deal on deal.DEAL_ID=c.LOAN_DEAL_ID");
				query.append(" left join gcd_customer_m G on G.CUSTOMER_ID=c.LOAN_CUSTOMER_ID");
				query.append( " left JOIN generic_master m on LOAN_SECTOR_TYPE=m.VALUE and GENERIC_KEY='SECTOR_TYPE'");
				query.append(" left JOIN generic_master i on DEAL_INT_CALC_FROM=i.VALUE and i.GENERIC_KEY='INTERST_CAL_FROM' ");//Surendra..
				query.append(" left join cr_deal_approval_dtl A on A.DEAL_ID=c.LOAN_DEAL_ID");
				query.append(" left join GENERIC_MASTER GM1 ON  GM1.GENERIC_KEY='LOAN_PURPOSE' AND GM1.PARENT_VALUE=C.LOAN_PRODUCT AND GM1.REC_STATUS='A' ");
				query.append(" where LOAN_ID='"+loanId+"'");

				logger.info("In getloanListInLoan    :    " + query.toString());
				mainlist = ConnectionDAO.sqlSelect(query.toString());
				int size = mainlist.size();
				logger.info("In getloanListInLoan...." +size);
				for (int i = 0; i < size; i++) {

					subList = (ArrayList) mainlist.get(i);
					logger.info("In getloanListInLoan..." + subList.size());
					if (subList.size() > 0) 
					{ 

						loanvo = new LoanDetailForCMVO();
						loanvo.setLoanId ((CommonFunction.checkNull(subList.get(0)).trim()));
						loanvo.setLoanNo ((CommonFunction.checkNull(subList.get(1)).trim()));
						loanvo.setCustomerName((CommonFunction.checkNull(subList.get(2)).trim()));
						loanvo.setProduct ((CommonFunction.checkNull(subList.get(3)).trim()));
						loanvo.setShowProduct ((CommonFunction.checkNull(subList.get(4)).trim()));
						loanvo.setScheme ((CommonFunction.checkNull(subList.get(5)).trim()));
						loanvo.setShowScheme((CommonFunction.checkNull(subList.get(6)).trim()));
						loanvo.setSanctionedValidtill ((CommonFunction.checkNull(subList.get(7)).trim()));
						loanvo.setAgreementDate ((CommonFunction.checkNull(subList.get(8)).trim()));
						loanvo.setRepayEffectiveDate ((CommonFunction.checkNull(subList.get(9)).trim()));

						if(!CommonFunction.checkNull(subList.get(10)).equalsIgnoreCase(""))	
						{
							Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(10))).trim());
							loanvo.setAssetCost(myFormatter.format(reconNum));
						}
		
						if(!CommonFunction.checkNull(subList.get(11)).equalsIgnoreCase(""))	
						{
							Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(11))).trim());
							loanvo.setMargin(myFormatter.format(reconNum));
							loanvo.setLtvPerc(myFormatter.format(100-reconNum.doubleValue()));
						}
		
						if(!CommonFunction.checkNull(subList.get(12)).equalsIgnoreCase(""))	
						{
							Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(12))).trim());
							loanvo.setMarginAmount(myFormatter.format(reconNum));
						}
						if(!CommonFunction.checkNull(subList.get(13)).equalsIgnoreCase(""))	
						{
							Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(13))).trim());
							loanvo.setLoanAmount(myFormatter.format(reconNum));
						}
						loanvo.setTenureMonths ((CommonFunction.checkNull(subList.get(14)).trim()));
						
						if(CommonFunction.checkNull(subList.get(15)).equalsIgnoreCase("M"))
						{
							loanvo.setFrequency ((CommonFunction.checkNull(subList.get(15)).trim()));
							loanvo.setShowFrequency("MONTHLY");
						}
						else if(CommonFunction.checkNull(subList.get(15)).equalsIgnoreCase("B"))
						{
							loanvo.setFrequency ((CommonFunction.checkNull(subList.get(15)).trim()));
							loanvo.setShowFrequency("BIMONTHLY");
						}
						else if(CommonFunction.checkNull(subList.get(15)).equalsIgnoreCase("Q"))
						{
							loanvo.setFrequency ((CommonFunction.checkNull(subList.get(15)).trim()));
							loanvo.setShowFrequency("QUARTERLY");
						}
						else if(CommonFunction.checkNull(subList.get(15)).equalsIgnoreCase("H"))
						{
							loanvo.setFrequency ((CommonFunction.checkNull(subList.get(15)).trim()));
							loanvo.setShowFrequency("HALFYERALY");
						}
						else if(CommonFunction.checkNull(subList.get(15)).equalsIgnoreCase("Y"))
						{
							loanvo.setFrequency ((CommonFunction.checkNull(subList.get(15)).trim()));
							loanvo.setShowFrequency("YEARLY");
						}
							
						if(CommonFunction.checkNull(subList.get(16)).equalsIgnoreCase("F"))
						{
							loanvo.setRateType ((CommonFunction.checkNull(subList.get(16)).trim()));
							loanvo.setShowEffectiveRate("FLAT RATE");
						}
						else if(CommonFunction.checkNull(subList.get(16)).equalsIgnoreCase("E"))
						{
							loanvo.setRateType ((CommonFunction.checkNull(subList.get(16)).trim()));
							loanvo.setShowEffectiveRate("EFFECTIVE RATE");
						}
						
						loanvo.setDealRateMethod ((CommonFunction.checkNull(subList.get(17)).trim()));
						loanvo.setBaseRateType ((CommonFunction.checkNull(subList.get(18)).trim()));
						
						if(!CommonFunction.checkNull(subList.get(19)).equalsIgnoreCase(""))	
						{
							Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(19))).trim());
							loanvo.setBaseRate(myFormatter.format(reconNum));
						}
						if(!CommonFunction.checkNull(subList.get(20)).equalsIgnoreCase(""))	
						{
							Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(20))).trim());
							loanvo.setMarkUp(myFormatter.format(reconNum));
						}
						if(!CommonFunction.checkNull(subList.get(21)).equalsIgnoreCase(""))	
						{
							Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(21))).trim());
							loanvo.setEffectiveRate(myFormatter.format(reconNum));
						}
						
						if(CommonFunction.checkNull(subList.get(22)).equalsIgnoreCase("I"))
						{
							loanvo.setRepaymentType ((CommonFunction.checkNull(subList.get(22)).trim()));
							loanvo.setShowRepayment("INSTALLMENT");
						}
						else if(CommonFunction.checkNull(subList.get(22)).equalsIgnoreCase("N"))
						{
							loanvo.setRepaymentType ((CommonFunction.checkNull(subList.get(22)).trim()));
							loanvo.setShowRepayment("NON-INSTALLMENT");
						}
						
						loanvo.setInstallmentType ((CommonFunction.checkNull(subList.get(23)).trim()));
							
//						if(CommonFunction.checkNull(subList.get(23)).equalsIgnoreCase("E"))
//						{
//							loanvo.setInstallmentType ((CommonFunction.checkNull(subList.get(23)).trim()));
//							loanvo.setShowInstallment("Eq. INSTALLMENT");
//						}
//						if(CommonFunction.checkNull(subList.get(23)).equalsIgnoreCase("G"))
//						{
//							loanvo.setInstallmentType ((CommonFunction.checkNull(subList.get(23)).trim()));
//							loanvo.setShowInstallment("Gr. INSTALLMENT");
//						}
//						if(CommonFunction.checkNull(subList.get(23)).equalsIgnoreCase("P"))
//						{
//							loanvo.setInstallmentType ((CommonFunction.checkNull(subList.get(23)).trim()));
//							loanvo.setShowInstallment("Eq. PRINCIPAL");
//						}
//						if(CommonFunction.checkNull(subList.get(23)).equalsIgnoreCase("Q"))
//						{
//							loanvo.setInstallmentType ((CommonFunction.checkNull(subList.get(23)).trim()));
//							loanvo.setShowInstallment("Gr. PRINCIPAL1");
//						}
//						if(CommonFunction.checkNull(subList.get(23)).equalsIgnoreCase("R"))
//						{
//							loanvo.setInstallmentType ((CommonFunction.checkNull(subList.get(23)).trim()));
//							loanvo.setShowInstallment("Gr. PRINCIPAL2");
//						}
											
						if(CommonFunction.checkNull(subList.get(24)).equalsIgnoreCase("A"))
						{
							loanvo.setDealInstallmentMode ((CommonFunction.checkNull(subList.get(24)).trim()));
							loanvo.setShowInstMode("ADVANCE");
						}
						else if(CommonFunction.checkNull(subList.get(24)).equalsIgnoreCase("R"))
						{
							loanvo.setDealInstallmentMode ((CommonFunction.checkNull(subList.get(24)).trim()));
							loanvo.setShowInstMode("ARREAR");
						}
						
						
						
						loanvo.setRepaymentMode((CommonFunction.checkNull(subList.get(25)).trim()));
				
						loanvo.setLoanNoofInstall ((CommonFunction.checkNull(subList.get(26)).trim()));
						
						loanvo.setNoOfDisbursal ((CommonFunction.checkNull(subList.get(27)).trim()));
						
						if(!loanvo.getNoOfDisbursal().equalsIgnoreCase("1"))
						{
							loanvo.setTypeOfDisbursal("M");
						}
						else if(loanvo.getNoOfDisbursal().equalsIgnoreCase("1"))
						{
							loanvo.setTypeOfDisbursal("S");
						}
					    			
						loanvo.setInstallments ((CommonFunction.checkNull(subList.get(28)).trim()));
						
						loanvo.setLoanDayDue ((CommonFunction.checkNull(subList.get(29)).trim()));
						
						if(!CommonFunction.checkNull(subList.get(30)).equalsIgnoreCase(""))	
						{
							Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(30))).trim());
							loanvo.setSanctionedLoanAmount(myFormatter.format(reconNum));
						}
						if(!CommonFunction.checkNull(subList.get(31)).equalsIgnoreCase(""))	
						{
							Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(31))).trim());
							loanvo.setUtilizeLoanAmount(myFormatter.format(reconNum));
						}
						
						
						loanvo.setLbxDealNo ((CommonFunction.checkNull(subList.get(32)).trim()));
						loanvo.setDealNo ((CommonFunction.checkNull(subList.get(33)).trim()));
						loanvo.setMaturityDate((CommonFunction.checkNull(subList.get(34)).trim()));

						loanvo.setShowSectorType((CommonFunction.checkNull(subList.get(35)).trim()));
						loanvo.setRemarks((CommonFunction.checkNull(subList.get(36)).trim()));
						logger.info("Remarks: "+CommonFunction.checkNull(subList.get(36)));
						logger.info("Author Remarks: "+CommonFunction.checkNull(subList.get(37)));
						loanvo.setAuthorRemarks((CommonFunction.checkNull(subList.get(37)).trim()));
						loanvo.setPodoFlag((CommonFunction.checkNull(subList.get(38)).trim()));
						loanvo.setDueDay((CommonFunction.checkNull(subList.get(39)).trim()));
						if(!CommonFunction.checkNull(subList.get(40)).equalsIgnoreCase(""))	
						{
							Number reconNum =myFormatter.parse((CommonFunction.checkNull(subList.get(40))).trim());
							loanvo.setDealIrr2(myFormatter.format(reconNum));
						}
						loanvo.setSanctionDate((CommonFunction.checkNull(subList.get(41)).trim()));
						loanvo.setInsuranceDoneBy((CommonFunction.checkNull(subList.get(42)).trim()));
						loanvo.setOneDealOneLoan((CommonFunction.checkNull(subList.get(43)).trim()));
						loanvo.setNextDueDate((CommonFunction.checkNull(subList.get(44)).trim()));
						loanvo.setRecStatus((CommonFunction.checkNull(subList.get(45)).trim()));
						loanvo.setMinFlatRate((CommonFunction.checkNull(subList.get(46))).trim());
						loanvo.setMaxFlatRate((CommonFunction.checkNull(subList.get(47))).trim());
						loanvo.setMinEffRate((CommonFunction.checkNull(subList.get(48))).trim());
						loanvo.setMaxEffRate((CommonFunction.checkNull(subList.get(49))).trim());
						loanvo.setMinTenure((CommonFunction.checkNull(subList.get(50))).trim());
						loanvo.setMaxTenure((CommonFunction.checkNull(subList.get(51))).trim());
						loanvo.setLbxareaCodeVal((CommonFunction.checkNull(subList.get(52))).trim());
						loanvo.setAreaCodename((CommonFunction.checkNull(subList.get(53))).trim());
	//Prashant
						loanvo.setAssetFlag((CommonFunction.checkNull(subList.get(54))).trim());
						//Surendra..
						loanvo.setInterestCalc((CommonFunction.checkNull(subList.get(55))).trim());
						loanvo.setShowInterestCalc((CommonFunction.checkNull(subList.get(56))).trim());
						//Surendra 
						loanvo.setNetLtv((CommonFunction.checkNull(subList.get(57))).trim());
						loanvo.setTenureInDay((CommonFunction.checkNull(subList.get(58))).trim());
						loanvo.setDaysBasis((CommonFunction.checkNull(subList.get(59))).trim());
						loanvo.setShowInstallment ((CommonFunction.checkNull(subList.get(60)).trim()));
						loanvo.setFixPriod((CommonFunction.checkNull(subList.get(61)).trim()));
						loanvo.setSectorType((CommonFunction.checkNull(subList.get(62)).trim()));
						loanvo.setNoOfAsset((CommonFunction.checkNull(subList.get(63)).trim()));
						loanvo.setFormNo((CommonFunction.checkNull(subList.get(64)).trim()));
						loanvo.setLoanDealId((CommonFunction.checkNull(subList.get(65)).trim()));
						loanvo.setLbxLoanClassification((CommonFunction.checkNull(subList.get(66)).trim()));
						loanvo.setShowRepayMode((CommonFunction.checkNull(subList.get(67)).trim()));			
						//loanvo.setInsurancePremium((CommonFunction.checkNull(subList.get(68)).trim())+"");						
						
						
						
						/*if(!CommonFunction.checkNull(subList.get(69)).equalsIgnoreCase(""))	
						{
							Number reconNumReqAmt =myFormatter.parse((CommonFunction.checkNull(subList.get(69))).trim());
							loanvo.setRequestedLoamt(myFormatter.format(reconNumReqAmt));
						}*/
						//loanvo.setLbxLoanPurpose((CommonFunction.checkNull(subList.get(70)).trim())+"");
						loanvo.setLoanPurpose((CommonFunction.checkNull(subList.get(68)).trim())+"");
						
						//					Himanshu Verma		Changes started for Facility Details
						//loanvo.setFacilityDetailsParameterFlag((CommonFunction.checkNull(subList.get(72)).trim()));
						loanvo.setIsOneDealOneLoanProduct((CommonFunction.checkNull(subList.get(69)).trim()));
						//loanvo.setProposedAmount((CommonFunction.checkNull(subList.get(74)).trim()));
						//	Himanshu Verma		Changes ended for Facility Details
						loanvo.setLbxBranchId((CommonFunction.checkNull(subList.get(70)).trim()));
						loanvo.setServicingBranch((CommonFunction.checkNull(subList.get(71)).trim()));
						loanvo.setInsurancePremium((CommonFunction.checkNull(subList.get(72)).trim())+"");	
						if(!CommonFunction.checkNull(subList.get(73)).equalsIgnoreCase(""))	
						{
							Number reconNumReqAmt =myFormatter.parse((CommonFunction.checkNull(subList.get(73))).trim());
							loanvo.setRequestedLoamt(myFormatter.format(reconNumReqAmt));
						}
						loanDetailList.add(loanvo);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally
			{
				mainlist.clear();
				mainlist = null;
				subList.clear();
				subList = null;
				query = null;
				loanvo = null;
				loanId=null;
			}
			return loanDetailList;
		}
		
			
		
		public boolean saveEditLoan(CommonLoanVo loanDetailForCMVO) 
		{

			logger.info("In saveEditLoan()");
			ArrayList result=new ArrayList();
			ArrayList qryListUp= new ArrayList();
			boolean status = false;

			PrepStmtObject updatePrepStmtObject = new PrepStmtObject();
			StringBuilder queryUpdate = new StringBuilder();

			try 
			{

					queryUpdate.append(" update cr_loan_dtl_edit set  "); 
					queryUpdate.append(" PO_DO_FLAG=?,INSUARENCE_DONE_BY=?,MAKER_ID=?,");
					queryUpdate.append(" MAKER_DATE=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND),LOAN_ASSET_COST=?,LOAN_MARGIN_RATE=?,LOAN_MARGIN_AMOUNT=?,AREA_CODE=?," );
					queryUpdate.append(" LOAN_SECTOR_TYPE=?,NO_OF_ASSET=?,LOAN_REFERENCE_NO=?,SERVICE_BRANCH=? where LOAN_ID=?");

					
					
					if (CommonFunction.checkNull(loanDetailForCMVO.getPodoFlag()).equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getPodoFlag()).trim()));
					
						
					
					if (CommonFunction.checkNull(loanDetailForCMVO.getInsuranceDoneBy()).equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getInsuranceDoneBy()).trim()));
				
					if ((CommonFunction.checkNull(loanDetailForCMVO.getUserId())).trim().equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((loanDetailForCMVO.getUserId()).trim());
					
					if ((CommonFunction.checkNull(loanDetailForCMVO.getbDate()).trim()).equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString(CommonFunction.checkNull(loanDetailForCMVO.getbDate()).trim());
					
					if (CommonFunction.checkNull(loanDetailForCMVO.getAssetCost()).equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(loanDetailForCMVO.getAssetCost()).trim())).toString());
					
					if (CommonFunction.checkNull(loanDetailForCMVO.getMargin()).equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(loanDetailForCMVO.getMargin()).trim())).toString());
					
					
					if (CommonFunction.checkNull(loanDetailForCMVO.getMarginAmount()).equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString(myFormatter.parse((CommonFunction.checkNull(loanDetailForCMVO.getMarginAmount()).trim())).toString());
					
					if (loanDetailForCMVO.getLbxareaCodeVal().equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getLbxareaCodeVal()).trim()));
			
					
					if (CommonFunction.checkNull(loanDetailForCMVO.getSectorType()).equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getSectorType()).trim()));//sector Type
					
					if (CommonFunction.checkNull(loanDetailForCMVO.getNoOfAsset()).equalsIgnoreCase(""))
						updatePrepStmtObject.addInt(0);
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getNoOfAsset()).trim()));//NO_OF_ASSET
					
					if (CommonFunction.checkNull(loanDetailForCMVO.getFormNo()).equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getFormNo()).trim()));			
				
					if (CommonFunction.checkNull(loanDetailForCMVO.getLbxBranchId()).equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getLbxBranchId()).trim()));
							
					if (CommonFunction.checkNull(loanDetailForCMVO.getLbxLoanNoHID()).equalsIgnoreCase(""))
						updatePrepStmtObject.addNull();
					else
						updatePrepStmtObject.addString((CommonFunction.checkNull(loanDetailForCMVO.getLbxLoanNoHID()).trim()));
					
					updatePrepStmtObject.setSql(queryUpdate.toString());
					logger.info("IN saveEditLoan() insert query1 ### "+ updatePrepStmtObject.printQuery());
					qryListUp.add(updatePrepStmtObject);
					status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryListUp);
					
			} catch (Exception e) {
				e.printStackTrace();
			}		
			return status;
		}	
		

		@Override
		public ArrayList getInsuranceProviders() {
	    	
logger.info("in getInsuranceProviders() of insurance provider. ");  		
	ArrayList<Object> list=new ArrayList<Object>();
  	try
  	{	  		
  		String query="select a.CHARGE_id,b.charge_desc from cr_insurance_grid_m a join com_charge_code_m b on a.CHARGE_id=b.CHARGE_CODE group by b.charge_desc";
  		logger.info("in getInsuranceProviders() of LoanInitiationDAOImpl Query :  "+query);
  		CmInsuranceVo vo = null;
  		ArrayList source = ConnectionDAO.sqlSelect(query);
  		logger.info("getInsuranceProvider  "+source.size());
  		int size=source.size();
  		for(int i=0;i<size;i++)
		  	{
	  			ArrayList subEduDetail=(ArrayList)source.get(i);
	  			if(subEduDetail.size()>0)
	  			{
	  				vo = new CmInsuranceVo();
	  				vo.setChargeId((CommonFunction.checkNull(subEduDetail.get(0)).toString()));
	  				vo.setInsuranceProvider((CommonFunction.checkNull(subEduDetail.get(1)).toString()));
	  				
		  				list.add(vo);	  				
		  			}
		  	}
		  }
  	catch(Exception e){
		  			e.printStackTrace();
		  		}
		  		return list;}

		@Override
		public ArrayList getInsuranceProducts(String insuranceProvider) {
			logger.info("in getInsuranceProducts() of insurance tab. ");  		
			ArrayList<Object> list=new ArrayList<Object>();
		  	try
		  	{	  		
		  		String query="SELECT VALUE,DESCRIPTION,CIPM.METHOD_TYPE,cipm.sum_assured_percentage FROM generic_master A " +
		  				" left join cr_insurance_grid_m C on C.charge_id=A.parent_value " +
		  				" LEFT JOIN COM_INSURANCE_PRODUCT_METHOD_M CIPM on A.VALUE=CIPM.PRODUCT_ID and CIPM.REC_STATUS='A' "+
		  				" WHERE A.generic_key='INSURANCE_PRODUCT' AND A.REC_STATUS='A' and A.parent_value='"+insuranceProvider+"' group by A.DESCRIPTION ";
		  		logger.info("in getInsuranceProducts() of LoanInitiationDao Query :  "+query);
		  		CmInsuranceVo vo = null;
		  		ArrayList source = ConnectionDAO.sqlSelect(query);
		  		String productId="";
		  		String serviceCalled="";
		  		String serviceProduct="";
		  		String sumAssuredMapping="";
		  		int size=source.size();
		  		for(int i=0;i<size;i++)
		  		{
		  			ArrayList subEduDetail=(ArrayList)source.get(i);
		  			if(subEduDetail.size()>0)
		  			{
		  				vo = new CmInsuranceVo();
		  				productId=CommonFunction.checkNull(subEduDetail.get(0)).toString();
		  				serviceCalled=CommonFunction.checkNull(subEduDetail.get(2)).toString();
		  				sumAssuredMapping=CommonFunction.checkNull(subEduDetail.get(3)).toString();
		  				serviceProduct=productId+"|"+serviceCalled+"|"+sumAssuredMapping;
		  				vo.setInsuranceProductId(serviceProduct);
		  				vo.setInsuranceProduct((CommonFunction.checkNull(subEduDetail.get(1)).toString()));
		  				
		  				list.add(vo);	  				
		  			}
		  			serviceProduct="";
		  		}
		  		}catch(Exception e){
		  			e.printStackTrace();
		  		}
		  		return list;}

		@Override
		public ArrayList getpolicyTypes() {
			logger.info("in getpolicyTypes() of insurance tab. ");  		
			ArrayList<Object> list=new ArrayList<Object>();
		  	try
		  	{	  		
		  		String query="SELECT VALUE,DESCRIPTION FROM generic_master WHERE generic_key='POLICY_TYPE' AND REC_STATUS='A' ";
		  		logger.info("in getpolicyTypes() of LoanInitiationDao Query :  "+query);
		  		CmInsuranceVo vo = null;
		  		ArrayList source = ConnectionDAO.sqlSelect(query);
		  		int size=source.size();
		  		for(int i=0;i<size;i++)
		  		{
		  			ArrayList subEduDetail=(ArrayList)source.get(i);
		  			if(subEduDetail.size()>0)
		  			{
		  				vo = new CmInsuranceVo();
		  				vo.setPolicyTypeId((CommonFunction.checkNull(subEduDetail.get(0)).toString()));
		  				vo.setPolicyType((CommonFunction.checkNull(subEduDetail.get(1)).toString()));
		  				
		  				list.add(vo);	  				
		  			}
		  		}
		  		}catch(Exception e){
		  			e.printStackTrace();
		  		}
		  		return list;
		  	}

		@Override
		public ArrayList getCustomerDetailList(CmInsuranceVo ob, String loanId) {
			logger.info("in getpolicyTypes() of insurance tab. ");  		
			ArrayList<Object> list=new ArrayList<Object>();
			StringBuilder queryTemp = new StringBuilder();
			int count=0;
		  	try
		  	{	
		  		
		  		String query="select c.GCD_id,B.customer_name,D.description," +
		  				"date_format(B.CUSTOMER_DOB,'%d-%m-%Y'),floor (datediff(m.parameter_value,B.CUSTOMER_DOB)/365),GM.DESCRIPTION  from cr_loan_dtl A " +
		  				
		  				" left join cr_loan_customer_role C on C.loan_id=A.loan_id " +
		  				" left join gcd_customer_m B on B.customer_id=C.GCD_id" +
		  				" left join parameter_mst m on (1=1 and m.parameter_key='BUSINESS_DATE') " +
		  				" left join generic_master D on D.value=C.LOAN_customer_role_type and D.generic_key='CUST_ROLE' and D.rec_status='A' " +
		  				" LEFT JOIN generic_master GM ON GM.VALUE= B.CUSTOMER_CONSTITUTION AND GM.GENERIC_KEY='CUST_CONSTITUTION' AND GM.REC_STATUS='A' and GM.parent_value='INDV' " +
		  				" where C.LOAN_customer_role_type in('PRAPPL','COAPPL') and A.LOAN_ID='"+loanId+"' ";
		  		
		  		queryTemp.append("SELECT  COUNT(1)  from cr_loan_dtl A left join cr_loan_customer_role C on C.loan_id=A.loan_id left join gcd_customer_m B on B.customer_id=C.GCD_id left join generic_master D on D.value=C.LOAN_customer_role_type and D.generic_key='CUST_ROLE' and D.rec_status='A' where C.LOAN_customer_role_type in('PRAPPL','COAPPL') and A.LOAN_ID='"+loanId+"'  ");			
		  		
		  		logger.info("query for link ............."+queryTemp.toString());
				String dataCount=ConnectionDAO.singleReturn(queryTemp.toString());
				if(!(CommonFunction.checkNull(dataCount).equalsIgnoreCase("0")))
					count=Integer.parseInt(dataCount);
		  		logger.info("in getCustomerDetailList() of LoanInitiationDaoImpl Query :  "+query);
		  		CmInsuranceVo vo = (CmInsuranceVo)ob ;
		  		ArrayList source = ConnectionDAO.sqlSelect(query);
		  		int size=source.size();
		  		for(int i=0;i<size;i++)
		  		{
		  			ArrayList subEduDetail=(ArrayList)source.get(i);
		  			if(subEduDetail.size()>0)
		  			{
		  				vo = new CmInsuranceVo();
		  				vo.setCustomerId((CommonFunction.checkNull(subEduDetail.get(0)).toString()));
		  				vo.setBorrowerName((CommonFunction.checkNull(subEduDetail.get(1)).toString()));
		  				vo.setCustomerType((CommonFunction.checkNull(subEduDetail.get(2)).toString()));
		  				vo.setDob((CommonFunction.checkNull(subEduDetail.get(3)).toString()));
		  				vo.setAge1((CommonFunction.checkNull(subEduDetail.get(4)).toString()));
		  				vo.setCustomerConstitution((CommonFunction.checkNull(subEduDetail.get(5)).toString()));
		  				vo.setTotalRecordSize(count);
		  				list.add(vo);	  				
		  			}
		  		}
		  		}catch(Exception e){
		  			e.printStackTrace();
		  		}
		  		return list;
		  		
		}

		@Override
		public ArrayList getInsuranceData(String loanId) {
			
			ArrayList list=new ArrayList();
				try
					{
						StringBuilder query=new StringBuilder();
						 /*query.append(" select round(A.deal_loan_amount_new),TENURE_IN_MONTH_TO_YEAR(A.LOAN_TENURE/12),floor (datediff(m.parameter_value,C.CUSTOMER_DOB)/365) as age ,round(A.deal_loan_amount_new),TENURE_IN_MONTH_TO_YEAR(A.LOAN_TENURE/12)  "+
								       "from CR_LOAN_DTL A join GCD_CUSTOMER_M C on A.LOAN_CUSTOMER_ID=C.CUSTOMER_ID left join parameter_mst m on (1=1 and m.parameter_key='BUSINESS_DATE') "+
								       " where A.LOAN_ID ="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()+" ");*/

						query.append(" select round(A.loan_loan_amount),TENURE_IN_MONTH_TO_YEAR(A.LOAN_TENURE/12),floor (datediff(m.parameter_value,C.CUSTOMER_DOB)/365) as age ,ifnull(round(A.deal_loan_amount_new),round(A.loan_loan_amount)),TENURE_IN_MONTH_TO_YEAR(A.LOAN_TENURE/12)  "+
							       "from CR_LOAN_DTL A join GCD_CUSTOMER_M C on A.LOAN_CUSTOMER_ID=C.CUSTOMER_ID left join parameter_mst m on (1=1 and m.parameter_key='BUSINESS_DATE') "+
							       " where A.LOAN_ID ="+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()+" ");
		   logger.info("query::::::" +query);				
							CmInsuranceVo vo= null;
							ArrayList insurance = ConnectionDAO.sqlSelect(query.toString());
							
							query=null;
							int size=insurance.size();
							for(int i=0;i<size;i++){

								ArrayList header1=(ArrayList)insurance.get(i);
								if(header1!=null && header1.size()>0)
								{
									vo = new CmInsuranceVo();
									vo.setSumAssured((CommonFunction.checkNull(header1.get(0))).trim());
									vo.setTenure((CommonFunction.checkNull(header1.get(1))).trim());
									vo.setAge((CommonFunction.checkNull(header1.get(2))).trim());
									vo.setSum((CommonFunction.checkNull(header1.get(3))).trim());
									vo.setPolicyTenure((CommonFunction.checkNull(header1.get(4))).trim());
									list.add(vo);
									
									vo=null;
								}
							header1.clear();
							header1=null;
						}
						insurance.clear();
						insurance=null;
						
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}

					return list;
					
		}

		@Override
		public ArrayList getAllInsuranceData(CmInsuranceVo ob, String loanId) {
			
		ArrayList list=new ArrayList();
		boolean status = false;
		CmInsuranceVo vo = (CmInsuranceVo)ob ;
		String chargeId=vo.getChargeId();
		String insuranceProvider=vo.getInsuranceProvider();
		String sumAssured=vo.getSumAssured();
		String tenure=vo.getTenure();
		String age=vo.getAge();
		String insurancePremium=vo.getInsurancePremium();
		String chargesOnInsurance=vo.getChargesOnInsurance();
		String otherChargeId=vo.getOtherChargeId();
		try
		{
		
			String sqlquery1="select distinct loan_id from cr_insurance_dtl where loan_id='"+loanId+"'";
			status= ConnectionDAO.checkStatus(sqlquery1);
			
			if(status)
			
			{
				StringBuilder query=new StringBuilder();
				query.append("select a.charge_id,round(b.deal_loan_amount_new),TENURE_IN_MONTH_TO_YEAR(b.loan_tenure/12) ,a.insurance_premium,a.charges_on_premium,a.other_charge_id,a.policy_type,a.amount_to_be_financed,a.product_id,round(b.deal_loan_amount_new),TENURE_IN_MONTH_TO_YEAR(b.loan_tenure/12) from cr_insurance_dtl a " +
						" left join cr_loan_dtl b on b.loan_id=a.loan_id " +
						" join GCD_CUSTOMER_M c on b.LOAN_CUSTOMER_ID=c.CUSTOMER_ID  " +
						"where a.loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()+"' group by a.loan_tenure");
				 
				
				logger.info("query for getAllInsuranceData" +query);
		
				ArrayList view = ConnectionDAO.sqlSelect(query.toString());
				logger.info("calculate size: "+view.size());
				query=null;
				int size=view.size();
				for(int i=0;i<size;i++){
		
					ArrayList header1=(ArrayList)view.get(i);
					if(header1!=null && header1.size()>0)
					{
						vo.setInsuranceProvider((CommonFunction.checkNull(header1.get(0))).trim());
						vo.setSumAssured((CommonFunction.checkNull(header1.get(1))).trim());
						vo.setTenure((CommonFunction.checkNull(header1.get(2))).trim());
						
						vo.setInsurancePremium((CommonFunction.checkNull(header1.get(3))).trim());
						vo.setChargesOnInsurance((CommonFunction.checkNull(header1.get(4))).trim());
						vo.setOtherChargeId((CommonFunction.checkNull(header1.get(5))).trim());
						vo.setPolicyType((CommonFunction.checkNull(header1.get(6))).trim());
						vo.setPremiumFinanced((CommonFunction.checkNull(header1.get(7))).trim());
						vo.setInsuranceProduct((CommonFunction.checkNull(header1.get(8))).trim());
						vo.setSum((CommonFunction.checkNull(header1.get(9))).trim());
						vo.setPolicyTenure((CommonFunction.checkNull(header1.get(10))).trim());
						
						list.add(vo);
						logger.info("to check..:::"+list);
						vo=null;
					}
					header1.clear();
					header1=null;
				}
				view.clear();
				view=null;
				
			}
		
		
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		
			return list;
			
		}

		@Override
		public ArrayList getSaveInsuranceData(CmInsuranceVo vo,
				String insuranceProvider, String sumAssured, String tenure,
				String age, String insuranceProduct, String policyType,
				String premiumFinanced, String loanId) {
			ArrayList list=new ArrayList();
			
			String chargeId=vo.getChargeId();
			String cust=CommonFunction.checkNull(vo.getCustId());
			String insurancePremium=vo.getInsurancePremium();
			String chargesOnInsurance=vo.getChargesOnInsurance();
			String otherChargeId=vo.getOtherChargeId();
			String LOAN_DEAL_LOAN_ID = ConnectionDAO.singleReturn("SELECT LOAN_DEAL_LOAN_ID FROM cr_LOAN_DTL WHERE LOAN_ID='"+loanId+"' ");
			String DEAL_ID = ConnectionDAO.singleReturn("SELECT dEAL_ID FROM CR_DEAL_lOAN_DTL WHERE DEAL_LOAN_ID= '"+LOAN_DEAL_LOAN_ID+"' ");
			ArrayList qryList = new ArrayList();
			PrepStmtObject insertPrepStmtObject=new PrepStmtObject();
			String qryUpdate = "update cr_insurance_dtl set loan_id='"+loanId+"' where deal_id='"+DEAL_ID+"' ";
			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim().equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			insertPrepStmtObject.setSql(qryUpdate);
			qryList.add(insertPrepStmtObject);
			boolean status=false;
			try
			{
				logger.info("query : "+ insertPrepStmtObject.printQuery());
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			String str="select distinct replace(customer_id,'|',',') from cr_insurance_dtl where loan_id='"+loanId+"' ";
			int id=0;
			ArrayList abcCust= new ArrayList();
			String abc="";
			try
			{
				abc="select distinct replace(customer_id,'|',',') from cr_insurance_dtl where loan_id='"+loanId+"' ";
				abcCust=ConnectionDAO.sqlSelect(abc.toString());
				int len=abcCust.size();
				for(int j=0;j<len;j++){
					ArrayList header10=(ArrayList)abcCust.get(j);
					String str111 = header10.toString();
					String temp=str111.substring(1,(str111.length()-1));
					String xyz=temp.replace(",", "|");	
			
			
				logger.info("str is :: "+str);
				String str1=ConnectionDAO.singleReturn(str);
				logger.info("str1 "+str1);
				String gcd="select group_concat(gcd_customer_id) from cr_deal_customer_m where customer_id in ("+temp+")";
				String gcdm=ConnectionDAO.singleReturn(gcd);
				StringBuilder query=new StringBuilder();
				query.append("select b.charge_desc,product_id,gm.description,group_concat(d.customer_name),round(a.insurance_premium),a.insurance_id from cr_insurance_dtl a " +
				" join com_charge_code_m b on b.CHARGE_CODE=a.CHARGE_ID " +
				" left join generic_master gm on gm.value=a.product_id and gm.generic_key='INSURANCE_PRODUCT'  " +
				" left join gcd_customer_m d on d.customer_id in ('"+gcdm+"')  " +
			
				" where loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()+"' and a.customer_id in ('"+xyz+"')  group by product_id,insurance_id ");
		 logger.info("getSaveInsuranceData------->>> "+query);
			ArrayList view = ConnectionDAO.sqlSelect(query.toString());
			query=null;
			int size=view.size();
			for(int i=0;i<size;i++){

			ArrayList header1=(ArrayList)view.get(i);
			if(header1!=null && header1.size()>0)
			{
			vo = new CmInsuranceVo();
			vo.setInsuranceProvider((CommonFunction.checkNull(header1.get(0))).trim());
			vo.setInsuranceProductId((CommonFunction.checkNull(header1.get(1))).trim());
			vo.setInsuranceProduct((CommonFunction.checkNull(header1.get(2))).trim());
			vo.setBorrowerName((CommonFunction.checkNull(header1.get(3))).trim());
			vo.setInsurancePremium((CommonFunction.checkNull(header1.get(4))).trim());
			vo.setInsuranceId((CommonFunction.checkNull(header1.get(5))).trim());
			
			list.add(vo);
			//vo=null;
			}
		header1.clear();
		header1=null;
		}
	view.clear();
	view=null;
				}
	}
	catch(Exception e)
	{
	e.printStackTrace();
	}

			Collections.sort(list,vo.insuranceProviderSorting);
			return list;
			
	}

		@Override
		public ArrayList getPropertyType(CmInsuranceVo vo, String loanId) {
			
			ArrayList<Object> mainList=new ArrayList<Object>();
			
			try{
			String query=(" select property_type,gm.description from cr_asset_collateral_m cacm " +
					"left join cr_loan_collateral_m clcm on clcm.assetid=cacm.asset_id " +
					"left join cr_loan_dtl cld on cld.loan_id= clcm.loan_id " +
					" join generic_master gm on gm.value=cacm.property_type and gm.generic_key='PROPERTY_TYPE' and gm.rec_status='A' " +
					"  where cld.loan_id ='"+loanId+"' ");
			logger.info("query for the PropertyType *****"+query);
			ArrayList list=ConnectionDAO.sqlSelect(query);
			//ArrayList list1=ConnectionDAO.sqlSelect(query);
			logger.info("size of PropertyType list********"+list.size());
			//logger.info("size of tyhe list********"+list1.size());
			for(int i=0;i<list.size();i++){
				ArrayList data=(ArrayList)list.get(i);
				if(data.size()>0){
					 vo=new CmInsuranceVo();
					 vo.setPropertyTypeId(CommonFunction.checkNull(data.get(0)));
					 vo.setPropertyType(CommonFunction.checkNull(data.get(1)));
					mainList.add(vo);
				}
			}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			return mainList;
			
		}

		@Override
		public ArrayList getRelation(CmInsuranceVo vo, String loanId) {
			
			ArrayList<Object> mainList=new ArrayList<Object>();
			
			try{
			String query=("select VALUE,DESCRIPTION FROM generic_master  WHERE GENERIC_KEY='RELATION_TYPE'  AND REC_STATUS='A'");
			logger.info("query for the relation *****"+query);
			ArrayList list=ConnectionDAO.sqlSelect(query);
			
			logger.info("size of tyhe list********"+list.size());
			//logger.info("size of tyhe list********"+list1.size());
			for(int i=0;i<list.size();i++){
				ArrayList data=(ArrayList)list.get(i);
				if(data.size()>0){
					 vo=new CmInsuranceVo();
					 vo.setRelationshpId(CommonFunction.checkNull(data.get(0)));
					 vo.setRelationshp(CommonFunction.checkNull(data.get(1)));
					mainList.add(vo);
				}
			}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			return mainList;
			
		}

		@Override
		public Map calculateCmInsurance(CmInsuranceVo ob,
				String insuranceProvider, String sumAssured, String tenure,
				String age, String insuranceProduct, String policyType,
				String premiumFinanced) {
			ArrayList list=new ArrayList();
			CmInsuranceVo vo = (CmInsuranceVo)ob ;
			Integer totalValue = 0;boolean b = true;Map map = new HashedMap();Map map1 = new HashedMap();
			String otherChargeId=vo.getOtherChargeId();
			logger.info("insuranceProduct:::::"+insuranceProduct);
			logger.info("policyType:::11111111::"+policyType);
			String calcMethod="";
			String ageList="";
			String cust=CommonFunction.checkNull(vo.getCustId());
			String custList="";
			String policyTenure=CommonFunction.checkNull(vo.getPolicyTenure());
			String propertyType=CommonFunction.checkNull(vo.getPropertyType());
			ArrayList ageCust= new ArrayList();
			String customerConstitution="";
			String customerConstitutionValue="";
			 ArrayList res= new ArrayList();
			custList=cust.replace("|",",");
			char []chars = custList.toCharArray();
			String s1 = "";
			int abc=chars.length-1;
			for(int i = 0; i < chars.length-1; i++)
			{
				s1 += chars[i];
				
			}
			String serviceCalledValue="";
			if(CommonFunction.checkNull(policyType).equalsIgnoreCase("J"))
			{
			String qry1="SELECT CALCULATION_METHOD FROM cr_insurance_calculation_method_m WHERE REC_STATUS='A' AND PRODUCT_ID= '"+insuranceProduct+"' ";
			 calcMethod = ConnectionDAO.singleReturn(qry1);
			 map.put("calcMethod", calcMethod);
			 logger.info("qry1:::"+qry1);
			 if(CommonFunction.checkNull(calcMethod).equalsIgnoreCase("M"))
				{
			String qry2="select max(floor (datediff(m.parameter_value,CUSTOMER_DOB)/365))from gcd_customer_m join parameter_mst m on (1=1 and m.parameter_key='BUSINESS_DATE') where customer_id in ("+s1+")";
			 ageList= ConnectionDAO.singleReturn(qry2);
			 logger.info("qry2:::"+qry2);
			 
			 customerConstitution="SELECT CUSTOMER_CONSTITUTION FROM gcd_customer_m join parameter_mst m on (1=1 and m.parameter_key='BUSINESS_DATE') where customer_id in ("+s1+") and floor (datediff(m.parameter_value,CUSTOMER_DOB)/365)=(select max(floor (datediff(z.parameter_value,CUSTOMER_DOB)/365))from gcd_customer_m join parameter_mst z on (1=1 and z.parameter_key='BUSINESS_DATE') where customer_id in ("+s1+")) group by customer_id ";
			 customerConstitutionValue= ConnectionDAO.singleReturn(customerConstitution);
			 logger.info("constitution:::"+customerConstitutionValue);
			 
				}
			 if(CommonFunction.checkNull(calcMethod).equalsIgnoreCase("S"))
				{
				 try
				 {
				 String qry3="select floor (datediff(m.parameter_value,CUSTOMER_DOB)/365)from gcd_customer_m join parameter_mst m on(1=1 and m.parameter_key='BUSINESS_DATE') where customer_id in ("+s1+")";
				 ageCust= ConnectionDAO.sqlSelect(qry3.toString());
				
				 }
				 catch(Exception e)
				 {
					 e.printStackTrace();
				 }
				}
			}
			if(CommonFunction.checkNull(policyType).equalsIgnoreCase("S"))
			{
			String qry3="select floor (datediff(m.parameter_value,CUSTOMER_DOB)/365)from gcd_customer_m join parameter_mst m on (1=1 and m.parameter_key='BUSINESS_DATE') where customer_id in ("+s1+")";
			 age= ConnectionDAO.singleReturn(qry3);
			
			 
			 customerConstitution="SELECT CUSTOMER_CONSTITUTION FROM gcd_customer_m WHERE customer_id in ("+s1+")";
				customerConstitutionValue = ConnectionDAO.singleReturn(customerConstitution);
				
				 logger.info("customerConstitutionValue:::"+customerConstitutionValue);
			
			}
			
			
			String serviceCalled="SELECT METHOD_TYPE FROM COM_INSURANCE_PRODUCT_METHOD_M WHERE PRODUCT_ID= '"+insuranceProduct+"' and rec_status='A' ";
			serviceCalledValue = ConnectionDAO.singleReturn(serviceCalled);
			
			try
			{
			 if(CommonFunction.checkNull(serviceCalledValue).equalsIgnoreCase("G"))
			  {
				if(CommonFunction.checkNull(policyType).equalsIgnoreCase("J"))
				{
					if(CommonFunction.checkNull(calcMethod).equalsIgnoreCase("M"))
					{
						if(CommonFunction.checkNull(propertyType).equalsIgnoreCase("R"))
						{
						StringBuilder query=new StringBuilder();
						query.append("SELECT round(((a.PREMIUM_percentage+a.RESIDENTIAL_RATE_PERCENTAGE)*('"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(sumAssured)).trim()+"'))),a.OTHER_CHARGES,b.charge_desc,b.charge_code FROM CR_INSURANCE_GRID_M a join com_charge_code_m b on a.OTHER_CHARGE_id=b.CHARGE_CODE WHERE PRODUCT_ID= '"+insuranceProduct+"' and policy_type= '"+policyType+"' and  " );
						query.append(" IF(IFNULL(CHARGE_ID,0)=0,TRUE,CHARGE_ID= '"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(insuranceProvider)).trim()+"') " );
						query.append(" AND IF(IFNULL(sum_assured,0)=0,TRUE,SUM_ASSURED=(select distinct SUM_ASSURED from cr_insurance_grid_m where SUM_ASSURED ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(sumAssured)).trim()+"' and PRODUCT_ID= '"+insuranceProduct+"' and policy_type= '"+policyType+"'  ))" );
						query.append("  AND IF(IFNULL(loan_tenure,0)=0,TRUE,loan_tenure =(select distinct loan_tenure from cr_insurance_grid_m where loan_tenure ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(tenure)).trim()+"' and PRODUCT_ID= '"+insuranceProduct+"' and policy_type= '"+policyType+"' ))" );
						query.append("  AND IF(IFNULL(RESIDENTIAL_RATE_PERCENTAGE,0)=0,TRUE,RESIDENTIAL_RATE_PERCENTAGE =(select distinct RESIDENTIAL_RATE_PERCENTAGE from cr_insurance_grid_m where LOAN_TENURE ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(tenure)).trim()+"' and PRODUCT_ID= '"+insuranceProduct+"' and policy_type= '"+policyType+"' and customer_constitution ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(customerConstitutionValue)).trim()+"' ))" );
						query.append("  AND IF(IFNULL(customer_constitution,'')='',TRUE,customer_constitution =(select distinct customer_constitution from cr_insurance_grid_m where customer_constitution ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(customerConstitutionValue)).trim()+"' and PRODUCT_ID= '"+insuranceProduct+"' and policy_type= '"+policyType+"'  ))" );
						query.append("  AND IF(IFNULL(policy_tenure,0)=0,TRUE,policy_tenure  =(select distinct policy_tenure from cr_insurance_grid_m where policy_tenure ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(policyTenure)).trim()+"' and PRODUCT_ID= '"+insuranceProduct+"' and policy_type= '"+policyType+"' ))" );
						query.append(" and  IF(IFNULL(AGE_from,0)=0,TRUE,AGE_from =(select distinct AGE_from from cr_insurance_grid_m where AGE_from <='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(ageList)).trim()+"' and AGE_to >='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(ageList)).trim()+"')) "); 
								
					logger.info("calculate:::::"+query);	
					ArrayList calculate = ConnectionDAO.sqlSelect(query.toString());
						
						query=null;
						int size=calculate.size();
						for(int i=0;i<size;i++){

						ArrayList header1=(ArrayList)calculate.get(i);
						if(header1!=null && header1.size()>0)
						{
							vo = new CmInsuranceVo();
							vo.setInsuranceProvider((CommonFunction.checkNull(insuranceProvider)).trim());
							vo.setInsuranceProduct((CommonFunction.checkNull(insuranceProduct)).trim());
							vo.setPolicyType((CommonFunction.checkNull(policyType)).trim());
							vo.setPremiumFinanced((CommonFunction.checkNull(premiumFinanced)).trim());
							vo.setInsurancePremium((CommonFunction.checkNull(header1.get(0))).trim());
							vo.setChargesOnInsurance((CommonFunction.checkNull(header1.get(1))).trim());
							vo.setOtherChargeId((CommonFunction.checkNull(header1.get(2))).trim());
							vo.setLbxOtherChargeId((CommonFunction.checkNull(header1.get(3))).trim());
							
							list.add(vo);
						
							map.put("vo", vo);
							vo=null;
						}
						map1.put("map",map);
						header1.clear();
						header1=null;
					}
					calculate.clear();
					calculate=null;
					}
						
						if(CommonFunction.checkNull(propertyType).equalsIgnoreCase("C"))
						{
						StringBuilder query=new StringBuilder();
						query.append("SELECT round(((a.PREMIUM_percentage+a.COMMERCIAL_RATE_PERCENTAGE)*('"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(sumAssured)).trim()+"'))),a.OTHER_CHARGES,b.charge_desc,b.charge_code FROM CR_INSURANCE_GRID_M a join com_charge_code_m b on a.OTHER_CHARGE_id=b.CHARGE_CODE WHERE PRODUCT_ID= '"+insuranceProduct+"' and policy_type= '"+policyType+"' and  " );
						query.append(" IF(IFNULL(CHARGE_ID,0)=0,TRUE,CHARGE_ID= '"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(insuranceProvider)).trim()+"') " );
						query.append(" AND IF(IFNULL(sum_assured,0)=0,TRUE,SUM_ASSURED=(select distinct SUM_ASSURED from cr_insurance_grid_m where SUM_ASSURED ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(sumAssured)).trim()+"' and PRODUCT_ID= '"+insuranceProduct+"' and policy_type= '"+policyType+"'  ))" );
						query.append("  AND IF(IFNULL(loan_tenure,0)=0,TRUE,loan_tenure =(select distinct loan_tenure from cr_insurance_grid_m where loan_tenure ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(tenure)).trim()+"' and PRODUCT_ID= '"+insuranceProduct+"' and policy_type= '"+policyType+"' ))" );
						query.append("  AND IF(IFNULL(COMMERCIAL_RATE_PERCENTAGE,0)=0,TRUE,COMMERCIAL_RATE_PERCENTAGE =(select distinct COMMERCIAL_RATE_PERCENTAGE from cr_insurance_grid_m where LOAN_TENURE ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(tenure)).trim()+"' and PRODUCT_ID= '"+insuranceProduct+"' and policy_type= '"+policyType+"' and customer_constitution ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(customerConstitutionValue)).trim()+"' ))" );
						query.append("  AND IF(IFNULL(customer_constitution,'')='',TRUE,customer_constitution =(select distinct customer_constitution from cr_insurance_grid_m where customer_constitution ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(customerConstitutionValue)).trim()+"' and PRODUCT_ID= '"+insuranceProduct+"' and policy_type= '"+policyType+"'  ))" );
						query.append("  AND IF(IFNULL(policy_tenure,0)=0,TRUE,policy_tenure  =(select distinct policy_tenure from cr_insurance_grid_m where policy_tenure ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(policyTenure)).trim()+"' and PRODUCT_ID= '"+insuranceProduct+"' and policy_type= '"+policyType+"' ))" );
						query.append(" and  IF(IFNULL(AGE_from,0)=0,TRUE,AGE_from =(select distinct AGE_from from cr_insurance_grid_m where AGE_from <='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(ageList)).trim()+"' and AGE_to >='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(ageList)).trim()+"')) "); 
								
					logger.info("calculate:::::"+query);	
					ArrayList calculate = ConnectionDAO.sqlSelect(query.toString());
						
						query=null;
						int size=calculate.size();
						for(int i=0;i<size;i++){

						ArrayList header1=(ArrayList)calculate.get(i);
						if(header1!=null && header1.size()>0)
						{
							vo = new CmInsuranceVo();
							vo.setInsuranceProvider((CommonFunction.checkNull(insuranceProvider)).trim());
							vo.setInsuranceProduct((CommonFunction.checkNull(insuranceProduct)).trim());
							vo.setPolicyType((CommonFunction.checkNull(policyType)).trim());
							vo.setPremiumFinanced((CommonFunction.checkNull(premiumFinanced)).trim());
							vo.setInsurancePremium((CommonFunction.checkNull(header1.get(0))).trim());
							vo.setChargesOnInsurance((CommonFunction.checkNull(header1.get(1))).trim());
							vo.setOtherChargeId((CommonFunction.checkNull(header1.get(2))).trim());
							vo.setLbxOtherChargeId((CommonFunction.checkNull(header1.get(3))).trim());
							
							list.add(vo);
						
							map.put("vo", vo);
							vo=null;
						}
						map1.put("map",map);
						header1.clear();
						header1=null;
					}
					calculate.clear();
					calculate=null;
					}
			
						
					}
					
					if(CommonFunction.checkNull(calcMethod).equalsIgnoreCase("S"))
					{
						//-------------------
						
						 int len=ageCust.size();
							for(int k=0;k<len;k++){

							ArrayList header10=(ArrayList)ageCust.get(k);
							if(header10!=null && header10.size()>0)
							{
						
								 customerConstitution="SELECT CUSTOMER_CONSTITUTION FROM gcd_customer_m where customer_id in ("+s1+") and round (datediff(sysdate(),CUSTOMER_DOB)/365)= '"+header10.get(0).toString()+"' ";
								 customerConstitutionValue= ConnectionDAO.singleReturn(customerConstitution);
								 logger.info("constitution:::"+customerConstitutionValue);
						
						logger.info("header10.get(0).toString()  :: "+header10.get(0).toString());
						//-------------------------------
						//for(int i=0;i<abc;i++)
					//	{
						if(CommonFunction.checkNull(propertyType).equalsIgnoreCase("R"))
						{
							StringBuilder query=new StringBuilder();
									query.append("SELECT round(((a.PREMIUM_percentage+a.RESIDENTIAL_RATE_PERCENTAGE)*('"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(sumAssured)).trim()+"'))),group_concat(a.OTHER_CHARGES),b.charge_desc,b.charge_code FROM CR_INSURANCE_GRID_M a join com_charge_code_m b on a.OTHER_CHARGE_id=b.CHARGE_CODE WHERE PRODUCT_ID= '"+insuranceProduct+"' and policy_type= '"+policyType+"' and  " );
									query.append(" IF(IFNULL(CHARGE_ID,0)=0,TRUE,CHARGE_ID= '"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(insuranceProvider)).trim()+"') " );
									query.append("  and IF(IFNULL(sum_assured,0)=0,TRUE,SUM_ASSURED=(select distinct SUM_ASSURED from cr_insurance_grid_m where SUM_ASSURED ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(sumAssured)).trim()+"' and PRODUCT_ID= '"+insuranceProduct+"' and policy_type= '"+policyType+"'  ))" );
									query.append("  AND IF(IFNULL(loan_tenure,0)=0,TRUE,loan_tenure =(select distinct loan_tenure from cr_insurance_grid_m where loan_tenure ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(tenure)).trim()+"' and PRODUCT_ID= '"+insuranceProduct+"' and policy_type= '"+policyType+"' ))" );
									
									query.append("  AND IF(IFNULL(RESIDENTIAL_RATE_PERCENTAGE,0)=0,TRUE,RESIDENTIAL_RATE_PERCENTAGE =(select distinct RESIDENTIAL_RATE_PERCENTAGE from cr_insurance_grid_m where LOAN_TENURE ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(tenure)).trim()+"' and PRODUCT_ID= '"+insuranceProduct+"' and policy_type= '"+policyType+"' and customer_constitution ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(customerConstitutionValue)).trim()+"' ))" );
										
									query.append("  AND IF(IFNULL(customer_constitution,'')='',TRUE,customer_constitution =(select distinct customer_constitution from cr_insurance_grid_m where customer_constitution ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(customerConstitutionValue)).trim()+"' and PRODUCT_ID= '"+insuranceProduct+"' and policy_type= '"+policyType+"'  ))" );
										
									query.append("  AND IF(IFNULL(policy_tenure,0)=0,TRUE,policy_tenure  =(select distinct policy_tenure from cr_insurance_grid_m where policy_tenure ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(policyTenure)).trim()+"' and PRODUCT_ID= '"+insuranceProduct+"' and policy_type= '"+policyType+"' ))" );
									query.append(" and  IF(IFNULL(AGE_from,0)=0,TRUE,AGE_from in (select distinct AGE_from from cr_insurance_grid_m where AGE_from <='"+header10.get(0).toString()+"' and AGE_to >='"+header10.get(0).toString()+"')) "); 
								
							ArrayList calculate = ConnectionDAO.sqlSelect(query.toString());
							logger.info("calculate33:::::"+query);
							query=null;
							int size=calculate.size();
							for(int j=0;j<size;j++){

							ArrayList header1=(ArrayList)calculate.get(j);
							if(header1!=null && header1.size()>0)
							{
								vo = new CmInsuranceVo();
								vo.setInsuranceProvider((CommonFunction.checkNull(insuranceProvider)).trim());
								vo.setInsuranceProduct((CommonFunction.checkNull(insuranceProduct)).trim());
								vo.setPolicyType((CommonFunction.checkNull(policyType)).trim());
								vo.setPremiumFinanced((CommonFunction.checkNull(premiumFinanced)).trim());
								vo.setInsurancePremium((CommonFunction.checkNull(header1.get(0))).trim());
								
								if(!CommonFunction.checkNull(header1.get(0)).trim().equalsIgnoreCase("") && b)
								{
									totalValue = totalValue + Integer.parseInt(CommonFunction.checkNull(header1.get(0)));
								logger.info("header1.get(0) "+header1.get(0));
			logger.info("totalValue "+totalValue);
								}
								else {
									b= false;
								}
								vo.setChargesOnInsurance((CommonFunction.checkNull(header1.get(1))).trim());
								vo.setOtherChargeId((CommonFunction.checkNull(header1.get(2))).trim());
								vo.setLbxOtherChargeId((CommonFunction.checkNull(header1.get(3))).trim());
								
								list.add(vo);
								map.put("vo", vo);
								
								
								vo=null;
							}
							}
					//	}
							map1.put("map",map);
							map1.put("b",b);
							map1.put("totalValue",totalValue);
						//	header1.clear();
							//header1=null;
						}
						
						if(CommonFunction.checkNull(propertyType).equalsIgnoreCase("C"))
						{
							StringBuilder query=new StringBuilder();
									query.append("SELECT round(((a.PREMIUM_percentage+a.COMMERCIAL_RATE_PERCENTAGE)*('"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(sumAssured)).trim()+"'))),group_concat(a.OTHER_CHARGES),b.charge_desc,b.charge_code FROM CR_INSURANCE_GRID_M a join com_charge_code_m b on a.OTHER_CHARGE_id=b.CHARGE_CODE WHERE PRODUCT_ID= '"+insuranceProduct+"' and policy_type= '"+policyType+"' and  " );
									query.append(" IF(IFNULL(CHARGE_ID,0)=0,TRUE,CHARGE_ID= '"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(insuranceProvider)).trim()+"') " );
									query.append("  and IF(IFNULL(sum_assured,0)=0,TRUE,SUM_ASSURED=(select distinct SUM_ASSURED from cr_insurance_grid_m where SUM_ASSURED ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(sumAssured)).trim()+"' and PRODUCT_ID= '"+insuranceProduct+"' and policy_type= '"+policyType+"'  ))" );
									query.append("  AND IF(IFNULL(loan_tenure,0)=0,TRUE,loan_tenure =(select distinct loan_tenure from cr_insurance_grid_m where loan_tenure ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(tenure)).trim()+"' and PRODUCT_ID= '"+insuranceProduct+"' and policy_type= '"+policyType+"' ))" );
									
									query.append("  AND IF(IFNULL(COMMERCIAL_RATE_PERCENTAGE,0)=0,TRUE,COMMERCIAL_RATE_PERCENTAGE =(select distinct COMMERCIAL_RATE_PERCENTAGE from cr_insurance_grid_m where LOAN_TENURE ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(tenure)).trim()+"' and PRODUCT_ID= '"+insuranceProduct+"' and policy_type= '"+policyType+"' and customer_constitution ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(customerConstitutionValue)).trim()+"' ))" );
									query.append("  AND IF(IFNULL(customer_constitution,'')='',TRUE,customer_constitution =(select distinct customer_constitution from cr_insurance_grid_m where customer_constitution ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(customerConstitutionValue)).trim()+"' and PRODUCT_ID= '"+insuranceProduct+"' and policy_type= '"+policyType+"'  ))" );
									query.append("  AND IF(IFNULL(policy_tenure,0)=0,TRUE,policy_tenure  =(select distinct policy_tenure from cr_insurance_grid_m where policy_tenure ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(policyTenure)).trim()+"' and PRODUCT_ID= '"+insuranceProduct+"' and policy_type= '"+policyType+"' ))" );
									query.append(" and  IF(IFNULL(AGE_from,0)=0,TRUE,AGE_from in (select distinct AGE_from from cr_insurance_grid_m where AGE_from <='"+header10.get(0).toString()+"' and AGE_to >='"+header10.get(0).toString()+"')) "); 
								
							ArrayList calculate = ConnectionDAO.sqlSelect(query.toString());
							logger.info("calculate33:::::"+query);
							query=null;
							int size=calculate.size();
							for(int j=0;j<size;j++){

							ArrayList header1=(ArrayList)calculate.get(j);
							if(header1!=null && header1.size()>0)
							{
								vo = new CmInsuranceVo();
								vo.setInsuranceProvider((CommonFunction.checkNull(insuranceProvider)).trim());
								vo.setInsuranceProduct((CommonFunction.checkNull(insuranceProduct)).trim());
								vo.setPolicyType((CommonFunction.checkNull(policyType)).trim());
								vo.setPremiumFinanced((CommonFunction.checkNull(premiumFinanced)).trim());
								vo.setInsurancePremium((CommonFunction.checkNull(header1.get(0))).trim());
								
								if(!CommonFunction.checkNull(header1.get(0)).trim().equalsIgnoreCase("") && b)
								{
									totalValue = totalValue + Integer.parseInt(CommonFunction.checkNull(header1.get(0)));
								logger.info("header1.get(0) "+header1.get(0));
			logger.info("totalValue "+totalValue);
								}
								else {
									b= false;
								}
								vo.setChargesOnInsurance((CommonFunction.checkNull(header1.get(1))).trim());
								vo.setOtherChargeId((CommonFunction.checkNull(header1.get(2))).trim());
								vo.setLbxOtherChargeId((CommonFunction.checkNull(header1.get(3))).trim());
								
								list.add(vo);
								map.put("vo", vo);
								
								
								vo=null;
							}
							}
					//	}
							map1.put("map",map);
							map1.put("b",b);
							map1.put("totalValue",totalValue);
						//	header1.clear();
							//header1=null;
						}
					
						}
						
						
						
					}
					}
				}
				
				
				if(CommonFunction.checkNull(policyType).equalsIgnoreCase("S"))
				{
					if(CommonFunction.checkNull(propertyType).equalsIgnoreCase("C"))
					{
					StringBuilder query=new StringBuilder();
					query.append("SELECT round(((a.PREMIUM_percentage+a.COMMERCIAL_RATE_PERCENTAGE)*('"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(sumAssured)).trim()+"'))),a.OTHER_CHARGES,b.charge_desc,b.charge_code FROM CR_INSURANCE_GRID_M a join com_charge_code_m b on a.OTHER_CHARGE_id=b.CHARGE_CODE WHERE PRODUCT_ID= '"+insuranceProduct+"' and policy_type= '"+policyType+"' and  " );
					query.append(" IF(IFNULL(CHARGE_ID,0)=0,TRUE,CHARGE_ID= '"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(insuranceProvider)).trim()+"') " );
					query.append(" and IF(IFNULL(sum_assured,0)=0,TRUE,SUM_ASSURED=(select distinct SUM_ASSURED from cr_insurance_grid_m where SUM_ASSURED ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(sumAssured)).trim()+"' and PRODUCT_ID= '"+insuranceProduct+"' and policy_type= '"+policyType+"'  ))" );
					query.append("  AND IF(IFNULL(loan_tenure,0)=0,TRUE,loan_tenure =(select distinct loan_tenure from cr_insurance_grid_m where loan_tenure ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(tenure)).trim()+"' and PRODUCT_ID= '"+insuranceProduct+"' and policy_type= '"+policyType+"' ))" );
					query.append("  AND IF(IFNULL(customer_constitution,'')='',TRUE,customer_constitution =(select distinct customer_constitution from cr_insurance_grid_m where customer_constitution ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(customerConstitutionValue)).trim()+"' and PRODUCT_ID= '"+insuranceProduct+"' and policy_type= '"+policyType+"'  ))" );				
					query.append("  AND IF(IFNULL(COMMERCIAL_RATE_PERCENTAGE,0)=0,TRUE,COMMERCIAL_RATE_PERCENTAGE =(select distinct COMMERCIAL_RATE_PERCENTAGE from cr_insurance_grid_m where LOAN_TENURE ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(tenure)).trim()+"' and PRODUCT_ID= '"+insuranceProduct+"' and policy_type= '"+policyType+"' and customer_constitution ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(customerConstitutionValue)).trim()+"' ))" );
					query.append("  AND IF(IFNULL(policy_tenure,0)=0,TRUE,policy_tenure  =(select distinct policy_tenure from cr_insurance_grid_m where policy_tenure ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(policyTenure)).trim()+"' and PRODUCT_ID= '"+insuranceProduct+"' and policy_type= '"+policyType+"' ))" );
					query.append("  and  IF(IFNULL(AGE_from,0)=0,TRUE,AGE_from=(select distinct AGE_from from cr_insurance_grid_m where AGE_from <='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(age)).trim()+"' and AGE_to >='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(age)).trim()+"')) "); 
							
						logger.info("query::::"+query);
						ArrayList calculate = ConnectionDAO.sqlSelect(query.toString());
					
					logger.info("calculate1:::::"+query);
					query=null;
					int size=calculate.size();
					for(int i=0;i<size;i++){
						
					ArrayList header1=(ArrayList)calculate.get(i);
					if(header1!=null && header1.size()>0)
					{
						vo = new CmInsuranceVo();
						vo.setInsuranceProvider((CommonFunction.checkNull(insuranceProvider)).trim());
						vo.setInsuranceProduct((CommonFunction.checkNull(insuranceProduct)).trim());
						vo.setPolicyType((CommonFunction.checkNull(policyType)).trim());
						vo.setPremiumFinanced((CommonFunction.checkNull(premiumFinanced)).trim());
						vo.setInsurancePremium((CommonFunction.checkNull(header1.get(0))).trim());
						vo.setChargesOnInsurance((CommonFunction.checkNull(header1.get(1))).trim());
						vo.setOtherChargeId((CommonFunction.checkNull(header1.get(2))).trim());
						vo.setLbxOtherChargeId((CommonFunction.checkNull(header1.get(3))).trim());
						
						list.add(vo);
						map.put("vo", vo);
						vo=null;
					}
					map1.put("map",map);
					header1.clear();
					header1=null;
				}
				calculate.clear();
				calculate=null;
				}
					
					
					if(CommonFunction.checkNull(propertyType).equalsIgnoreCase("R"))
					{
					StringBuilder query=new StringBuilder();
					query.append("SELECT round(((a.PREMIUM_percentage+a.RESIDENTIAL_RATE_PERCENTAGE)*('"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(sumAssured)).trim()+"'))),a.OTHER_CHARGES,b.charge_desc,b.charge_code FROM CR_INSURANCE_GRID_M a join com_charge_code_m b on a.OTHER_CHARGE_id=b.CHARGE_CODE WHERE PRODUCT_ID= '"+insuranceProduct+"' and policy_type= '"+policyType+"' and  " );
					query.append(" IF(IFNULL(CHARGE_ID,0)=0,TRUE,CHARGE_ID= '"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(insuranceProvider)).trim()+"') " );
					query.append(" and IF(IFNULL(sum_assured,0)=0,TRUE,SUM_ASSURED=(select distinct SUM_ASSURED from cr_insurance_grid_m where SUM_ASSURED ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(sumAssured)).trim()+"' and PRODUCT_ID= '"+insuranceProduct+"' and policy_type= '"+policyType+"'  ))" );
					query.append("  AND IF(IFNULL(loan_tenure,0)=0,TRUE,loan_tenure =(select distinct loan_tenure from cr_insurance_grid_m where loan_tenure ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(tenure)).trim()+"' and PRODUCT_ID= '"+insuranceProduct+"' and policy_type= '"+policyType+"' ))" );
					query.append("  AND IF(IFNULL(customer_constitution,'')='',TRUE,customer_constitution =(select distinct customer_constitution from cr_insurance_grid_m where customer_constitution ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(customerConstitutionValue)).trim()+"' and PRODUCT_ID= '"+insuranceProduct+"' and policy_type= '"+policyType+"' and customer_constitution ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(customerConstitutionValue)).trim()+"' ))" );			
					query.append("  AND IF(IFNULL(RESIDENTIAL_RATE_PERCENTAGE,0)=0,TRUE,RESIDENTIAL_RATE_PERCENTAGE =(select distinct RESIDENTIAL_RATE_PERCENTAGE from cr_insurance_grid_m where LOAN_TENURE ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(tenure)).trim()+"' and PRODUCT_ID= '"+insuranceProduct+"' and policy_type= '"+policyType+"'  ))" );
					query.append("  AND IF(IFNULL(policy_tenure,0)=0,TRUE,policy_tenure  =(select distinct policy_tenure from cr_insurance_grid_m where policy_tenure ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(policyTenure)).trim()+"' and PRODUCT_ID= '"+insuranceProduct+"' and policy_type= '"+policyType+"' ))" );
					query.append("  and  IF(IFNULL(AGE_from,0)=0,TRUE,AGE_from=(select distinct AGE_from from cr_insurance_grid_m where AGE_from <='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(age)).trim()+"' and AGE_to >='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(age)).trim()+"')) "); 
							
						logger.info("query::::"+query);
						ArrayList calculate = ConnectionDAO.sqlSelect(query.toString());
					
					logger.info("calculate1:::::"+query);
					query=null;
					int size=calculate.size();
					for(int i=0;i<size;i++){
						
					ArrayList header1=(ArrayList)calculate.get(i);
					if(header1!=null && header1.size()>0)
					{
						vo = new CmInsuranceVo();
						vo.setInsuranceProvider((CommonFunction.checkNull(insuranceProvider)).trim());
						vo.setInsuranceProduct((CommonFunction.checkNull(insuranceProduct)).trim());
						vo.setPolicyType((CommonFunction.checkNull(policyType)).trim());
						vo.setPremiumFinanced((CommonFunction.checkNull(premiumFinanced)).trim());
						vo.setInsurancePremium((CommonFunction.checkNull(header1.get(0))).trim());
						vo.setChargesOnInsurance((CommonFunction.checkNull(header1.get(1))).trim());
						vo.setOtherChargeId((CommonFunction.checkNull(header1.get(2))).trim());
						vo.setLbxOtherChargeId((CommonFunction.checkNull(header1.get(3))).trim());
						
						list.add(vo);
						map.put("vo", vo);
						vo=null;
					}
					map1.put("map",map);
					header1.clear();
					header1=null;
				}
				calculate.clear();
				calculate=null;
				}
					
					
					
					
			}
				
			  }
//			 Change made by Ashish for get insurance premium from religare web service
			 if(CommonFunction.checkNull(serviceCalledValue).equalsIgnoreCase("W"))		// Check network connection before calling web services
				{
					try 
					{
						try 
						{
							URL url = new URL("http://www.google.com");
							HttpURLConnection con = (HttpURLConnection) url.openConnection();
							con.connect();

							if (con.getResponseCode() == 200) 
							{
								logger.info("Connection established!!");
							}
							else
							{
								logger.info("No Connection");
								return map1;
							}
						}
						catch (Exception exception) 
						{
							logger.info("No Connection");
							return map1;
						}
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}
				}
			//if(CommonFunction.checkNull(serviceCalledValue).equalsIgnoreCase("W") && CommonFunction.checkNull(insuranceProduct).equalsIgnoreCase("A2") && CommonFunction.checkNull(insuranceProvider).equalsIgnoreCase("265"))
			if(CommonFunction.checkNull(serviceCalledValue).equalsIgnoreCase("W") && (CommonFunction.checkNull(insuranceProduct).equalsIgnoreCase("ASSURELAP") || CommonFunction.checkNull(insuranceProduct).equalsIgnoreCase("ASSUREWCTL") || CommonFunction.checkNull(insuranceProduct).equalsIgnoreCase("SECURE")) && CommonFunction.checkNull(insuranceProvider).equalsIgnoreCase("265"))
			{
			String uri ="http://www.religarehealthinsurance.com/abacuswb/rfl-aspire/calculation.php";
				String auth="YWRtaW46MTIz";
				String dob="";
//			String productType="assure";
			String productType=insuranceProduct;
				String companyName="religare";
				String totalAmountWithServiceTax="";
				int pTenure=Integer.parseInt(policyTenure);
					pTenure=pTenure*12;
				logger.info("In Calculate Insurance Premium :");
				 try
				 {
				 String query1="select DATE_FORMAT(CUSTOMER_DOB,'"+dateFormat+"') from gcd_customer_m left join parameter_mst z on (1=1 and z.parameter_key='BUSINESS_DATE' )  where customer_id in ("+s1+")";
					 dob= ConnectionDAO.singleReturn(query1.toString());
					 logger.info("query1 :"+query1);
					 logger.info("dob :"+dob);
				 }
				 catch(Exception e)
				 {
					 e.printStackTrace();
				 }
			String urlParameters = "dob="+dob+"&auth="+auth+"&loan_amount="+sumAssured+"&product="+productType+"&loan_tenure="+pTenure+"&companyName="+companyName;
				logger.info("urlParameters :"+urlParameters);
				URL url;
				HttpURLConnection connection;
				try 
				{
					url = new URL(uri);
					connection =(HttpURLConnection) url.openConnection();
					connection.setRequestMethod("POST");
					connection.setRequestProperty("Accept","" );
			
					connection.setDoOutput(true);
					DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
					wr.writeBytes(urlParameters);
					wr.flush();
					wr.close();
					
					InputStream result = connection.getInputStream();
				String decodedString="";
					String str=readString(result);

				if (!str.contains("ERROR"))
				{
					logger.info("errorInfo::"+result.toString().indexOf("ERROR"));
				
					decodedString = new String(Base64.decodeBase64(str.getBytes()));
				
					if (!productType.equalsIgnoreCase("SECURE"))
					{

					if(decodedString.contains("totalAmountWithServiceTax"))
					{

						totalAmountWithServiceTax=decodedString.substring(
								(decodedString.indexOf("totalAmountWithServiceTax:")+"totalAmountWithServiceTax:".length()),decodedString.indexOf("-Product:"));
						totalAmountWithServiceTax=totalAmountWithServiceTax.replace(",","");
						logger.info("totalAmountWithServiceTax :"+totalAmountWithServiceTax);
		}
					else
					{
						return map1;
					}
					} else {
						
						totalAmountWithServiceTax=(decodedString.substring(
								(decodedString.indexOf("-premium:")+"-premium:".length()),decodedString.indexOf("-premiumAddOn:"))).replaceAll(",","");
						totalAmountWithServiceTax= Integer.toString((Integer.parseInt(totalAmountWithServiceTax) + 
								Integer.parseInt((decodedString.substring(
								(decodedString.indexOf("-premiumAddOn:")+"-premiumAddOn:".length()),decodedString.indexOf("-Product:"))).replaceAll(",",""))));
						totalAmountWithServiceTax=totalAmountWithServiceTax.replace(",","");
						logger.info("totalAmountWithServiceTax :"+totalAmountWithServiceTax);

					}
			} else {
					//map.put("RELIGAREERROR",result);
					

					vo = new CmInsuranceVo();
					vo.setInsurancePremium((CommonFunction.checkNull(str)).trim());
					list.add(vo);
					map.put("vo", vo);
					vo=null;
				}
					logger.info("Result :"+str);
					logger.info("decodedString :"+decodedString);
					if(!CommonFunction.checkNull(totalAmountWithServiceTax).equalsIgnoreCase(""))
					{
						vo = new CmInsuranceVo();
						vo.setInsuranceProvider((CommonFunction.checkNull(insuranceProvider)).trim());
						vo.setInsuranceProduct((CommonFunction.checkNull(insuranceProduct)).trim());
						vo.setPolicyType((CommonFunction.checkNull(policyType)).trim());
						vo.setPremiumFinanced((CommonFunction.checkNull(premiumFinanced)).trim());
						vo.setInsurancePremium((CommonFunction.checkNull(totalAmountWithServiceTax)).trim());
						vo.setChargesOnInsurance("0");
						vo.setOtherChargeId("Other Charges");
						vo.setLbxOtherChargeId("132");
						
						list.add(vo);
						map.put("vo", vo);
						vo=null;
					}
					map1.put("map",map);
					connection.disconnect();
				} 
				catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}}
			if(CommonFunction.checkNull(serviceCalledValue).equalsIgnoreCase("W") && CommonFunction.checkNull(insuranceProduct).equalsIgnoreCase("LNP") && CommonFunction.checkNull(insuranceProvider).equalsIgnoreCase("264"))
			{
				ArrayList<String> inputString=new ArrayList<String>();
				SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
				Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(vo.getMakerDate());
				String policyCommencementDate = formatter.format(date1);
				try
				 {
					 String query1="SELECT GCM.CUSTOMER_FNAME,GCM.CUSTOMER_LNAME,cast(GCM.CUSTOMER_DOB as char),GCM.GENDER,GCM.MARITAL_STATUS FROM GCD_CUSTOMER_M GCM left join parameter_mst z on (1=1 and z.parameter_key='BUSINESS_DATE' )  where customer_id in ("+s1+")";
					 ArrayList inputData= ConnectionDAO.sqlSelect(query1.toString());
					 logger.info("query1 :"+query1);
					 logger.info("calculate size: "+inputData.size());
					 query1=null;
					 int size=inputData.size();
					 for(int i=0;i<size;i++)
					 {
				
						ArrayList data1=(ArrayList)inputData.get(i);
						if(data1!=null && data1.size()>0)
						{
							inputString.add((CommonFunction.checkNull(data1.get(0))).trim());
							inputString.add((CommonFunction.checkNull(data1.get(1))).trim());
							inputString.add((CommonFunction.checkNull(data1.get(2))).trim());
							inputString.add((CommonFunction.checkNull(data1.get(3))).trim());
							if((CommonFunction.checkNull(data1.get(4))).trim().equalsIgnoreCase("U"))
								inputString.add("Single");
							else
								inputString.add("Married");
							inputString.add("0");
							inputString.add("0");
							inputString.add("TRADITIONAL");
							inputString.add("Loan Protect");
							inputString.add("T82");
							inputString.add("Yearly");
							inputString.add("0");
							inputString.add("0");
							inputString.add(policyTenure);
							inputString.add(sumAssured);
							inputString.add("1");
							inputString.add("Fixed");
							inputString.add(sumAssured);
							inputString.add(tenure);
							inputString.add("0");
							
						}
						data1.clear();
						data1=null;
					}
					inputData.clear();
					inputData=null;
				 }
		catch(Exception e)
		{
			e.printStackTrace();
		}
				String insurancePremium="0";//ICICIPrudentialInsurancePremium.getICICIInsurancePremium(inputString);
				if(!CommonFunction.checkNull(insurancePremium).contains("E01"))
				{
					vo = new CmInsuranceVo();
					vo.setInsuranceProvider((CommonFunction.checkNull(insuranceProvider)).trim());
					vo.setInsuranceProduct((CommonFunction.checkNull(insuranceProduct)).trim());
					vo.setPolicyType((CommonFunction.checkNull(policyType)).trim());
					vo.setPremiumFinanced((CommonFunction.checkNull(premiumFinanced)).trim());
					vo.setInsurancePremium((CommonFunction.checkNull(insurancePremium)).trim());
					vo.setChargesOnInsurance("0");
					vo.setOtherChargeId("Other Charges");
					vo.setLbxOtherChargeId("132");
					
					list.add(vo);
					map.put("vo", vo);
					vo=null;
				}
				else
				{
					vo = new CmInsuranceVo();
					vo.setInsurancePremium((CommonFunction.checkNull(insurancePremium)).trim());
					list.add(vo);
					map.put("vo", vo);
					vo=null;
				}
				map1.put("map",map);
			}

//			 Change end by Ashish
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	return map1;
	
}

		@Override
		public int insuranceUpdateId(CmInsuranceVo vo, String loanId) {
			
			String insuranceId ="";
			String cust=vo.getCustId();
			String custList="";
			custList=cust.replace("|",",");
						char []chars = custList.toCharArray();
						String s1 = "";
						for(int i = 0; i < chars.length-1; i++)
						{
							s1 += chars[i];
						}
						logger.info("s1::::::"+s1);
						String newCust=s1.replace(",","|");
						logger.info("newCust::::::"+newCust);
			 String insuranceProvider=vo.getInsuranceProvider();
			 String insuranceProduct=vo.getInsuranceProduct();
			 if(insuranceProduct.contains("|"))		// for removing service called from insurance product
				{
					insuranceProduct=insuranceProduct.substring(0, insuranceProduct.indexOf("|"));
					vo.setInsuranceProduct(insuranceProduct);
				}
			 String InsurancePremium=vo.getInsurancePremium();
			 String policyTenure=vo.getPolicyTenure();
			 String InsuranceFinanced=vo.getPremiumFinanced();
			 String sumAssured=vo.getSumAssured();
			 int ins=0;
			 logger.info("aman insuranceProvider::::"+insuranceProvider);
			 logger.info("aman insuranceProduct::::"+insuranceProduct);
				try{
					 String insurance_id=" select insurance_id from cr_insurance_dtl where loan_id='"+loanId+"' and charge_id= '"+insuranceProvider+"'  and product_id='"+insuranceProduct+"' and customer_id =(select customer_id from cr_deal_customer_m where gcd_customer_id ='"+newCust+"') and sum_assured='"+sumAssured+"' and policy_tenure='"+policyTenure+"' and insurance_premium='"+InsurancePremium+"' and amount_to_be_financed='"+InsuranceFinanced+"' ";
					  insuranceId = CommonFunction.checkNull(ConnectionDAO.singleReturn(insurance_id));
						logger.info("insuranceId:::"+insuranceId);
						if(insuranceId=="")
						{
							ins=0;
						}
						else{
							ins=Integer.parseInt(insuranceId);
						}
						
						
					}catch(Exception e){
						e.printStackTrace();
					}
					return ins;
					
		}

		@Override
		public String getinsuId(CmInsuranceVo vo, String loanId) {
			
			String insuranceId ="";
			String cust=vo.getCustId();
			String custList="";
			custList=cust.replace("|",",");
						char []chars = custList.toCharArray();
						String s1 = "";
						for(int i = 0; i < chars.length-1; i++)
						{
							s1 += chars[i];
						}
						logger.info("s1::::::"+s1);
						String newCust=s1.replace(",","|");
						logger.info("newCust::::::"+newCust);
			 String insuranceProvider=vo.getInsuranceProvider();
			 String insuranceProduct=vo.getInsuranceProduct();
			 if(insuranceProduct.contains("|"))		// for removing service called from insurance product
				{
					insuranceProduct=insuranceProduct.substring(0, insuranceProduct.indexOf("|"));
					vo.setInsuranceProduct(insuranceProduct);
				}
			 logger.info("aman insuranceProvider::::"+insuranceProvider);
			 logger.info("aman insuranceProduct::::"+insuranceProduct);
				try{
					 String insurance_id=" select insurance_id from cr_insurance_dtl where loan_id='"+loanId+"' and charge_id= '"+insuranceProvider+"'  and product_id='"+insuranceProduct+"' and customer_id =(select customer_id from cr_deal_customer_m where gcd_customer_id ='"+newCust+"') ";
					  insuranceId = ConnectionDAO.singleReturn(insurance_id);
						logger.info("insuranceId:::"+insuranceId);
						
						
					}catch(Exception e){
						e.printStackTrace();
					}
					return insuranceId;
					
		}

		@Override
		public int getDealSumInsuranceAmount(CmInsuranceVo vo, String loanId) {
			
			 int sum_assure1=0;
			 String insuranceProvider=vo.getInsuranceProvider();
			 String insuranceProduct=vo.getInsuranceProduct();
			 if(insuranceProduct.contains("|"))		// for removing service called from insurance product
				{
					insuranceProduct=insuranceProduct.substring(0, insuranceProduct.indexOf("|"));
					vo.setInsuranceProduct(insuranceProduct);
				}
			 logger.info("aman insuranceProvider::::"+insuranceProvider);
			 logger.info("aman insuranceProduct::::"+insuranceProduct);
				try{
					 //String deal_sum_insurance_amount=" select sum(sum_assured) from cr_insurance_dtl where loan_id='"+loanId+"' and product_id='"+insuranceProduct+"' and charge_id= '"+insuranceProvider+"' ";
					 String deal_sum_insurance_amount=" select sum(sum_assured) from cr_insurance_dtl where loan_id='"+loanId+"' and charge_id= '"+insuranceProvider+"' ";
					String sum_assure = ConnectionDAO.singleReturn(deal_sum_insurance_amount);			
					
					logger.info("sum_assure:::"+sum_assure);
					if(sum_assure!="")
					{
						sum_assure1=(int)Double.parseDouble(sum_assure);
					}
					else 
					{
						 sum_assure1 =0;
					}
						
					}catch(Exception e){
						e.printStackTrace();
					}
					return sum_assure1;
					
		}

		@Override
		public int getsumAssureDealLoan1(CmInsuranceVo vo, String loanId) {
			
			int sum_assure_deal_loan1=0;
				try{
					 String sum_loan_deal_amount=" select round(ifnull(loan_loan_amount,0)) from cr_loan_dtl where loan_id='"+loanId+"' ";
					 String sum_assure_deal_loan = ConnectionDAO.singleReturn(sum_loan_deal_amount);
						logger.info("sum_assure_deal_loan:::"+sum_assure_deal_loan);
						if(sum_assure_deal_loan!="")
						{
							sum_assure_deal_loan1 = Integer.parseInt(sum_assure_deal_loan);
						}
						else 
						{
							sum_assure_deal_loan1 =0;
						}
						
					
					}catch(Exception e){
						e.printStackTrace();
					}
					return sum_assure_deal_loan1;}

		@Override
		public int getAssetCost(CmInsuranceVo vo, String loanId) {
			
			int dealAssetCost=0;
				try{
					 String assetCost=" select round(loan_asset_cost) from cr_loan_dtl where loan_id='"+loanId+"' ";
					 String dealAssetCostValue = ConnectionDAO.singleReturn(assetCost);
						logger.info("dealAssetCostValue:::"+dealAssetCostValue);
						if(assetCost!="")
						{
							dealAssetCost = Integer.parseInt(dealAssetCostValue);
						}
						else 
						{
							dealAssetCost =0;
						}
						
					
					}catch(Exception e){
						e.printStackTrace();
					}
					return dealAssetCost;
					
		}


	public boolean insertCmInsurance(CmInsuranceVo vo, String loanId) {
			
			
			boolean status = false;
			boolean status1=false;
			String insuranceProvider=vo.getInsuranceProvider();
			String sumAssured=vo.getSumAssured();
			String tenure=vo.getTenure();
			String age=vo.getAge();
			String insurancePremium=vo.getInsurancePremium();
			String chargesOnInsurance=vo.getChargesOnInsurance();
			String makerId=vo.getMakerId();
			String makerDate=vo.getMakerDate();
			String insuranceProduct=vo.getInsuranceProduct();
			if(insuranceProduct.contains("|"))		// for removing service called from insurance product
			{
				insuranceProduct=insuranceProduct.substring(0, insuranceProduct.indexOf("|"));
				vo.setInsuranceProduct(insuranceProduct);
			}
			String qry1="select loan_deal_loan_id from cr_loan_dtl where loan_id='"+loanId+"'";
			String dealLoanId= ConnectionDAO.singleReturn(qry1);
			String customerConstitution="";
			String customerConstitutionValue="";
			String qry2="select loan_deal_id from cr_loan_dtl where loan_id='"+loanId+"'";
			String dealId= ConnectionDAO.singleReturn(qry2);
				String ins="";
				String dealCustomer="";
				String dealCustomerid="";
			ArrayList qryList = new ArrayList();
			PrepStmtObject insertPrepStmtObject=null;
			PrepStmtObject insertPrepStmt= null;
			StringBuilder bufInsSql = null;
			
			String cust=vo.getCustId();
			String custList="";
			custList=cust.replace("|",",");
			char []chars = custList.toCharArray();
			String s1 = "";
			for(int i = 0; i < chars.length-1; i++)
			{
				s1 += chars[i];
			}
				
			vo.setCustId(s1);
			//vo.setInsuranceProduct(s1);
			String newCust=s1.replace(",","|");
			logger.info("newCust::::::"+newCust);
			
			String qry3="select replace(group_concat(round (datediff(sysdate(),CUSTOMER_DOB)/365)),',','|') from gcd_customer_m  where customer_id in ("+s1+")";
			String ageList= ConnectionDAO.singleReturn(qry3);
			vo.setAge1(ageList);
			
			customerConstitution="SELECT replace(group_concat(CUSTOMER_CONSTITUTION),',','|') FROM gcd_customer_m WHERE customer_id in ("+s1+")";
			customerConstitutionValue = ConnectionDAO.singleReturn(customerConstitution);
			
			 logger.info("customerConstitutionValue:::"+customerConstitutionValue);
			
			 dealCustomer="select replace(group_concat(customer_id),',','|') from cr_deal_customer_m where gcd_customer_id in ("+s1+")";
			 dealCustomerid = ConnectionDAO.singleReturn(dealCustomer);
			 
			 if(dealCustomerid==""){
				 dealCustomer="select replace(group_concat(customer_id),',','|') from gcd_customer_m where customer_id in ("+s1+")";
				 dealCustomerid = ConnectionDAO.singleReturn(dealCustomer);
			 }
			 
				
				try {
						String sqlquery1="select insurance_id from cr_insurance_dtl where loan_id='"+loanId+"' and charge_id= '"+insuranceProvider+"' and product_id='"+insuranceProduct+"' and customer_id ='"+dealCustomerid+"' ";
						ins = ConnectionDAO.singleReturn(sqlquery1);
						status= ConnectionDAO.checkStatus(sqlquery1);
						
						if(status)
						
						{
							insertPrepStmtObject = new PrepStmtObject();
							bufInsSql = new StringBuilder();
							
							RefreshFlagVo vo1 = new RefreshFlagVo();
							if(loanId!=null && !loanId.trim().equalsIgnoreCase(""))
							vo1.setRecordId(Integer.parseInt(loanId.trim()));
				    		vo1.setTabIndex(16);
				    		vo1.setModuleName("CM");
						    RefreshFlagValueInsert.updateRefreshFlag(vo1);
							
							bufInsSql.append("UPDATE CR_INSURANCE_DTL SET DEAL_ID=?,DEAL_LOAN_ID=?,LOAN_ID=?,CHARGE_ID=?,SUM_ASSURED=?,loan_TENURE=?,AGE=?,INSURANCE_PREMIUM=?,CHARGES_ON_PREMIUM=?,OTHER_CHARGE_ID=?,MAKER_ID=?,MAKER_DATE=STR_TO_DATE(?,'"+dateFormat+"'),POLICY_TYPE=?,AMOUNT_TO_BE_FINANCED=?,CUSTOMER_ID=?,PRODUCT_ID=?,NOMINEE_NAME=?,NOMINEE_DOB=STR_TO_DATE(?,'"+dateFormat+"'),GENDER=?,NOMINEE_RELATION=? ,policy_tenure=?,SUM_ASSURED_PERCENTAGE=?,customer_constitution=?,property_type=? "
									+ " ,NOMINEE_NAME1=?,GENDER1=?,NOMINEE_DOB1=STR_TO_DATE(?,'"+dateFormat+"'),NOMINEE_RELATION1=?,NOMINEE_NAME2=?,GENDER2=?,NOMINEE_DOB2=STR_TO_DATE(?,'"+dateFormat+"'),NOMINEE_RELATION2=?,NOMINEE_NAME3=?,GENDER3=?,NOMINEE_DOB3=STR_TO_DATE(?,'"+dateFormat+"'),NOMINEE_RELATION3=?,NOMINEE_NAME4=?,GENDER4=?,NOMINEE_DOB4=STR_TO_DATE(?,'"+dateFormat+"'),NOMINEE_RELATION4=?,NOMINEE_PERCENTAGE=?,NOMINEE_PERCENTAGE1=?,NOMINEE_PERCENTAGE2=?,NOMINEE_PERCENTAGE3=?,NOMINEE_PERCENTAGE4=?,ADDR=?,ADDR1=?,ADDR2=?,ADDR3=?,ADDR4=?,"
									+ " NOMINEE_PREFIX=?,NOMINEE_MNAME=?,NOMINEE_LNAME=?,MARITAL_STATUS=?,NOMINEE_AREA=?,NOMINEE_CITY=?,NOMINEE_STATE=?,NOMINEE_PIN=?,NOMINEE_PREFIX1=?,NOMINEE_MNAME1=?,NOMINEE_LNAME1=?,MARITAL_STATUS1=?,NOMINEE_AREA1=?,NOMINEE_CITY1=?,NOMINEE_STATE1=?,NOMINEE_PIN1=?,NOMINEE_PREFIX2=?,NOMINEE_MNAME2=?,NOMINEE_LNAME2=?,MARITAL_STATUS2=?,NOMINEE_AREA2=?,NOMINEE_CITY2=?,NOMINEE_STATE2=?,NOMINEE_PIN2=?, "
									+ " NOMINEE_PREFIX3=?,NOMINEE_MNAME3=?,NOMINEE_LNAME3=?,MARITAL_STATUS3=?,NOMINEE_AREA3=?,NOMINEE_CITY3=?,NOMINEE_STATE3=?,NOMINEE_PIN3=?,NOMINEE_PREFIX4=?,NOMINEE_MNAME4=?,NOMINEE_LNAME4=?,MARITAL_STATUS4=?,NOMINEE_AREA4=?,NOMINEE_CITY4=?,NOMINEE_STATE4=?,NOMINEE_PIN4=?,NOMINEE_ADDRESS_TYPE=?,NOMINEE_STREET=?,NOMINEE_ADDRESS_TYPE1=?,NOMINEE_STREET1=?,NOMINEE_ADDRESS_TYPE2=?,NOMINEE_STREET2=?, NOMINEE_ADDRESS_TYPE3=?,NOMINEE_STREET3=?,NOMINEE_ADDRESS_TYPE4=?,NOMINEE_STREET4=?" );
							bufInsSql.append(" where LOAN_ID=? and insurance_id=? " );
							
							if (CommonFunction.checkNull(dealId).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(dealId.toUpperCase().trim());
							
							if (CommonFunction.checkNull(dealLoanId).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(dealLoanId.toUpperCase().trim());
							
							
							if (CommonFunction.checkNull(loanId).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(loanId.toUpperCase().trim());
							
							if (CommonFunction.checkNull(insuranceProvider).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(insuranceProvider.toUpperCase().trim());
							
							if (CommonFunction.checkNull(vo.getSumAssured()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(vo.getSumAssured().toUpperCase().trim());
							
							if (CommonFunction.checkNull(vo.getTenure()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(vo.getTenure().toUpperCase().trim());
							
							if (CommonFunction.checkNull(vo.getAge1()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(vo.getAge1().toUpperCase().trim());
							
							
							if (CommonFunction.checkNull(vo.getInsurancePremium()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(vo.getInsurancePremium().toUpperCase().trim());
							
							
							if (CommonFunction.checkNull(vo.getChargesOnInsurance()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(vo.getChargesOnInsurance().toUpperCase().trim());
							

							if (CommonFunction.checkNull(vo.getLbxOtherChargeId()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(vo.getLbxOtherChargeId().toUpperCase().trim());
							
							if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(vo.getMakerId());
							
							if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(vo.getMakerDate());
							
						
							if (CommonFunction.checkNull(vo.getPolicyType()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(vo.getPolicyType());
							
							if (CommonFunction.checkNull(vo.getPremiumFinanced()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(vo.getPremiumFinanced());
							
						
							if (CommonFunction.checkNull(dealCustomerid).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(dealCustomerid);
							
							if (CommonFunction.checkNull(vo.getInsuranceProduct()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(vo.getInsuranceProduct());
							
							if (CommonFunction.checkNull(vo.getNomineeName()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(vo.getNomineeName());
							
							if (CommonFunction.checkNull(vo.getDateOfbirth()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(vo.getDateOfbirth());
							
						
							if (CommonFunction.checkNull(vo.getGender()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(vo.getGender());
							
							if (CommonFunction.checkNull(vo.getRelationshp()).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(vo.getRelationshp());
							
							
							  if (CommonFunction.checkNull(vo.getPolicyTenure()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
							 else
							    insertPrepStmtObject.addString(vo.getPolicyTenure());
								
								
								
								if (CommonFunction.checkNull(vo.getSumAssuPer()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getSumAssuPer());
							
								if (CommonFunction.checkNull(customerConstitutionValue).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(customerConstitutionValue);
							// add end	
							
								if (CommonFunction.checkNull(vo.getPropertyType()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getPropertyType());
								
								if (CommonFunction.checkNull(vo.getNomineeName1()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getNomineeName1());
								
								if (CommonFunction.checkNull(vo.getGender1()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getGender1());
								
								if (CommonFunction.checkNull(vo.getDateOfbirth1()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getDateOfbirth1());
								
								if (CommonFunction.checkNull(vo.getRelationshp1()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getRelationshp1());
								
								if (CommonFunction.checkNull(vo.getNomineeName2()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getNomineeName2());
								
								if (CommonFunction.checkNull(vo.getGender2()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getGender2());
								
								if (CommonFunction.checkNull(vo.getDateOfbirth2()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getDateOfbirth2());
								
								if (CommonFunction.checkNull(vo.getRelationshp2()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getRelationshp2());
								
								if (CommonFunction.checkNull(vo.getNomineeName3()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getNomineeName3());
								
								if (CommonFunction.checkNull(vo.getGender3()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getGender3());
								
								if (CommonFunction.checkNull(vo.getDateOfbirth3()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getDateOfbirth3());
								
								if (CommonFunction.checkNull(vo.getRelationshp3()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getRelationshp3());
								
								if (CommonFunction.checkNull(vo.getNomineeName4()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getNomineeName4());
								
								if (CommonFunction.checkNull(vo.getGender4()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getGender4());
								
								if (CommonFunction.checkNull(vo.getDateOfbirth4()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getDateOfbirth4());
								
								if (CommonFunction.checkNull(vo.getRelationshp4()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getRelationshp4());
								
								if (CommonFunction.checkNull(vo.getPercentage()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getPercentage());
								
								if (CommonFunction.checkNull(vo.getPercentage1()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getPercentage1());
								
								if (CommonFunction.checkNull(vo.getPercentage2()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getPercentage2());
								
								if (CommonFunction.checkNull(vo.getPercentage3()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getPercentage3());
								
								if (CommonFunction.checkNull(vo.getPercentage4()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getPercentage4());
								
								if (CommonFunction.checkNull(vo.getAddr()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getAddr());
								
								if (CommonFunction.checkNull(vo.getAddr1()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getAddr1());
								
								if (CommonFunction.checkNull(vo.getAddr2()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getAddr2());
								
								if (CommonFunction.checkNull(vo.getAddr3()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getAddr3());
								
								if (CommonFunction.checkNull(vo.getAddr4()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getAddr4());	
								//Saurabh Changes start here
								if (CommonFunction.checkNull(vo.getsPrefix()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getsPrefix());
								if (CommonFunction.checkNull(vo.getNomineeMName()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getNomineeMName());
								if (CommonFunction.checkNull(vo.getNomineeLName()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getNomineeLName());
								if (CommonFunction.checkNull(vo.getSmaritalStatus()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getSmaritalStatus());
								if (CommonFunction.checkNull(vo.getSnomineeArea()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getSnomineeArea());
								if (CommonFunction.checkNull(vo.getSnomineeCity()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getSnomineeCity());
								if (CommonFunction.checkNull(vo.getTxtStateCode()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getTxtStateCode());
								if (CommonFunction.checkNull(vo.getSnomineePin()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getSnomineePin());
								if (CommonFunction.checkNull(vo.getsPrefix1()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getsPrefix1());
								if (CommonFunction.checkNull(vo.getNomineeMName1()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getNomineeMName1());
								if (CommonFunction.checkNull(vo.getNomineeLName1()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getNomineeLName1());
								if (CommonFunction.checkNull(vo.getSmaritalstatus1()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getSmaritalstatus1());
								if (CommonFunction.checkNull(vo.getsNomineeArea1()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getsNomineeArea1());
								if (CommonFunction.checkNull(vo.getsNomineeCity1()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getsNomineeCity1());
								if (CommonFunction.checkNull(vo.getTxtStateCode1()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getTxtStateCode1());
								if (CommonFunction.checkNull(vo.getsNomineePin1()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getsNomineePin1());
								if (CommonFunction.checkNull(vo.getsPrefix2()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getsPrefix2());
								if (CommonFunction.checkNull(vo.getNomineeMName2()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getNomineeMName2());
								if (CommonFunction.checkNull(vo.getNomineeLName2()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getNomineeLName2());
								if (CommonFunction.checkNull(vo.getSmaritalStatus2()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getSmaritalStatus2());
								if (CommonFunction.checkNull(vo.getsNomineeArea2()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getsNomineeArea2());
								if (CommonFunction.checkNull(vo.getsNomineeCity2()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getsNomineeCity2());
								if (CommonFunction.checkNull(vo.getTxtStateCode2()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getTxtStateCode2());
								if (CommonFunction.checkNull(vo.getsNomineePin2()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getsNomineePin2());
								if (CommonFunction.checkNull(vo.getsPrefix3()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getsPrefix3());
								if (CommonFunction.checkNull(vo.getNomineeMName3()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getNomineeMName3());
								if (CommonFunction.checkNull(vo.getNomineeLName3()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getNomineeLName3());
								if (CommonFunction.checkNull(vo.getSmaritalStatus3()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getSmaritalStatus3());
								if (CommonFunction.checkNull(vo.getsNomineeArea3()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getsNomineeArea3());
								if (CommonFunction.checkNull(vo.getsNomineeCity3()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getsNomineeCity3());
								if (CommonFunction.checkNull(vo.getTxtStateCode3()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getTxtStateCode3());
								if (CommonFunction.checkNull(vo.getsNomineePin3()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getsNomineePin3());
								if (CommonFunction.checkNull(vo.getsPrefix4()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getsPrefix4());
								if (CommonFunction.checkNull(vo.getNomineeMName4()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getNomineeMName4());
								if (CommonFunction.checkNull(vo.getNomineeLName4()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getNomineeLName4());
								if (CommonFunction.checkNull(vo.getSmaritalStatus4()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getSmaritalStatus4());
								if (CommonFunction.checkNull(vo.getsNomineeArea4()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getsNomineeArea4());
								if (CommonFunction.checkNull(vo.getsNomineeCity4()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getsNomineeCity4());
								if (CommonFunction.checkNull(vo.getTxtStateCode4()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getTxtStateCode4());
								if (CommonFunction.checkNull(vo.getsNomineePin4()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getsNomineePin4());
								
								if (CommonFunction.checkNull(vo.getSaddressType()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getSaddressType());
								if (CommonFunction.checkNull(vo.getSinsuranceStreet()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getSinsuranceStreet());
								
								if (CommonFunction.checkNull(vo.getSaddressType1()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getSaddressType1());
								if (CommonFunction.checkNull(vo.getSinsuranceStreet1()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getSinsuranceStreet1());
								if (CommonFunction.checkNull(vo.getSaddressType2()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getSaddressType2());
								if (CommonFunction.checkNull(vo.getSinsuranceStreet2()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getSinsuranceStreet2());
								if (CommonFunction.checkNull(vo.getSaddressType3()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getSaddressType3());
								if (CommonFunction.checkNull(vo.getSinsuranceStreet3()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getSinsuranceStreet3());
								if (CommonFunction.checkNull(vo.getSaddressType4()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getSaddressType4());
								if (CommonFunction.checkNull(vo.getSinsuranceStreet4()).equalsIgnoreCase(""))
									insertPrepStmtObject.addNull();
								else
									insertPrepStmtObject.addString(vo.getSinsuranceStreet4());
								//Saurabh Changes Ends Here
							
							if (CommonFunction.checkNull(loanId).equalsIgnoreCase(""))
							insertPrepStmtObject.addNull();
						else
							insertPrepStmtObject.addString(loanId.toUpperCase().trim());
							
							if (CommonFunction.checkNull(ins).equalsIgnoreCase(""))
								insertPrepStmtObject.addNull();
							else
								insertPrepStmtObject.addString(ins);
							
							insertPrepStmtObject.setSql(bufInsSql.toString());
							
							logger.info("IN insertCmInsurance() update query1 ### "+ insertPrepStmtObject.printQuery());	
							
							qryList.add(insertPrepStmtObject);
										
							status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
						 
						}
							else
							{
								
								insertPrepStmt = new PrepStmtObject();
								bufInsSql = new StringBuilder();
								
								
								RefreshFlagVo vo1 = new RefreshFlagVo();
								if(loanId!=null && !loanId.trim().equalsIgnoreCase(""))
								vo1.setRecordId(Integer.parseInt(loanId.trim()));
					    		vo1.setTabIndex(16);
					    		vo1.setModuleName("CM");
							    RefreshFlagValueInsert.updateRefreshFlag(vo1);
								
								bufInsSql.append("INSERT INTO CR_INSURANCE_DTL(DEAL_ID,DEAL_LOAN_ID,LOAN_ID,CHARGE_ID,SUM_ASSURED,loan_TENURE,AGE,INSURANCE_PREMIUM,CHARGES_ON_PREMIUM, OTHER_CHARGE_ID,MAKER_ID,MAKER_DATE,POLICY_TYPE,AMOUNT_TO_BE_FINANCED,CUSTOMER_ID,PRODUCT_ID,NOMINEE_NAME,NOMINEE_DOB,GENDER,NOMINEE_RELATION,policy_tenure,customer_constitution,property_type,SUM_ASSURED_PERCENTAGE, "
										+ " NOMINEE_NAME1,GENDER1,NOMINEE_DOB1,NOMINEE_RELATION1,NOMINEE_NAME2,GENDER2,NOMINEE_DOB2,NOMINEE_RELATION2,NOMINEE_NAME3,GENDER3,NOMINEE_DOB3,NOMINEE_RELATION3,NOMINEE_NAME4,GENDER4,NOMINEE_DOB4,NOMINEE_RELATION4,NOMINEE_PERCENTAGE,NOMINEE_PERCENTAGE1,NOMINEE_PERCENTAGE2,NOMINEE_PERCENTAGE3,NOMINEE_PERCENTAGE4,ADDR,ADDR1,ADDR2,ADDR3,ADDR4,"
										+ " NOMINEE_PREFIX,NOMINEE_MNAME,NOMINEE_LNAME,MARITAL_STATUS,NOMINEE_AREA,NOMINEE_CITY,NOMINEE_STATE,NOMINEE_PIN,NOMINEE_PREFIX1,NOMINEE_MNAME1,NOMINEE_LNAME1,MARITAL_STATUS1,NOMINEE_AREA1,NOMINEE_CITY1,NOMINEE_STATE1,NOMINEE_PIN1,NOMINEE_PREFIX2,NOMINEE_MNAME2,NOMINEE_LNAME2,MARITAL_STATUS2,NOMINEE_AREA2,NOMINEE_CITY2,NOMINEE_STATE2,NOMINEE_PIN2,"
										+ " NOMINEE_PREFIX3,NOMINEE_MNAME3,NOMINEE_LNAME3,MARITAL_STATUS3,NOMINEE_AREA3,NOMINEE_CITY3,NOMINEE_STATE3,NOMINEE_PIN3,NOMINEE_PREFIX4,NOMINEE_MNAME4,NOMINEE_LNAME4,MARITAL_STATUS4,NOMINEE_AREA4,NOMINEE_CITY4,NOMINEE_STATE4,NOMINEE_PIN4,NOMINEE_ADDRESS_TYPE,NOMINEE_STREET,NOMINEE_ADDRESS_TYPE1,NOMINEE_STREET1,NOMINEE_ADDRESS_TYPE2,NOMINEE_STREET2,NOMINEE_ADDRESS_TYPE3,NOMINEE_STREET3,NOMINEE_ADDRESS_TYPE4,NOMINEE_STREET4)");
								bufInsSql.append(" values ( ");
								bufInsSql.append(" ?,"); //deal_id
								bufInsSql.append(" ?,"); //deal_loan_id
								bufInsSql.append(" ?,"); //loan_id
								bufInsSql.append(" ?,");	//charge_id
								bufInsSql.append(" ?,");	//sum
								bufInsSql.append(" ?,");	//loan_tenure
								bufInsSql.append(" ?,");	//age
								bufInsSql.append(" ?,");	//i premium
								bufInsSql.append(" ?,");	//c on premium
								bufInsSql.append(" ?,");	//other charge id
								bufInsSql.append(" ?,");	//maker id
								bufInsSql.append(" STR_TO_DATE(?,'"+dateFormat+"')," ); //MAKER_DATE
								bufInsSql.append(" ?,");	//policy type
								bufInsSql.append(" ?,");	//amt to be financed
								bufInsSql.append(" ?,");	//customer id
								bufInsSql.append(" ?,");	//product id
								bufInsSql.append(" ?,");	//nominne name
								bufInsSql.append(" STR_TO_DATE(?,'"+dateFormat+"')," ); //nominee_dob
								bufInsSql.append(" ?,");	//gender
								bufInsSql.append(" ?,");	//n relation
								bufInsSql.append(" ?,");	//	policy tenure
								bufInsSql.append(" ?,");	//c constitution
								bufInsSql.append(" ?,");	//property
								bufInsSql.append(" ?,");	//sum %
								bufInsSql.append(" ?,"); // NOMINEE_NAME1 
								bufInsSql.append(" ?,"); // GENDER1 
								bufInsSql.append(" STR_TO_DATE(?,'"+dateFormat+"')," ); // NOMINEE_DOB1 
								bufInsSql.append(" ?,"); // NOMINEE_RELATION1 
								bufInsSql.append(" ?,"); // NOMINEE_NAME2 
								bufInsSql.append(" ?,"); // GENDER2 
								bufInsSql.append(" STR_TO_DATE(?,'"+dateFormat+"')," ); // NOMINEE_DOB2 
								bufInsSql.append(" ?,"); // NOMINEE_RELATION2 
								bufInsSql.append(" ?,"); // NOMINEE_NAME3 
								bufInsSql.append(" ?,"); // GENDER3 
								bufInsSql.append(" STR_TO_DATE(?,'"+dateFormat+"')," ); // NOMINEE_DOB3 
								bufInsSql.append(" ?,"); // NOMINEE_RELATION3 
								bufInsSql.append(" ?,"); // NOMINEE_NAME4 
								bufInsSql.append(" ?,"); // GENDER4 
								bufInsSql.append(" STR_TO_DATE(?,'"+dateFormat+"')," ); // NOMINEE_DOB4 
								bufInsSql.append(" ?,"); // NOMINEE_RELATION4 
								bufInsSql.append(" ?,"); // NOMINEE_PERCENTAGE 
								bufInsSql.append(" ?,"); // NOMINEE_PERCENTAGE1 
								bufInsSql.append(" ?,"); // NOMINEE_PERCENTAGE2 
								bufInsSql.append(" ?,"); // NOMINEE_PERCENTAGE3 
								bufInsSql.append(" ?,"); // NOMINEE_PERCENTAGE4 
								bufInsSql.append(" ?,"); // ADDR 
								bufInsSql.append(" ?,"); // ADDR1 
								bufInsSql.append(" ?,"); // ADDR2 
								bufInsSql.append(" ?,"); // ADDR3 
								bufInsSql.append(" ?,"); // ADDR4 
								bufInsSql.append(" ?,"); // NOMINEE_PREFIX
								bufInsSql.append(" ?,"); // NOMINEE_MNAME
								bufInsSql.append(" ?,"); // NOMINEE_LNAME
								bufInsSql.append(" ?,"); // MARITAL_STATUS
								bufInsSql.append(" ?,"); // NOMINEE_AREA
								bufInsSql.append(" ?,"); // NOMINEE_CITY
								bufInsSql.append(" ?,"); // NOMINEE_STATE
								bufInsSql.append(" ?,"); // NOMINEE_PIN
								bufInsSql.append(" ?,"); // NOMINEE_PREFIX1
								bufInsSql.append(" ?,"); // NOMINEE_MNAME1
								bufInsSql.append(" ?,"); // NOMINEE_LNAME1
								bufInsSql.append(" ?,"); // MARITAL_STATUS1
								bufInsSql.append(" ?,"); // NOMINEE_AREA1
								bufInsSql.append(" ?,"); // NOMINEE_CITY1
								bufInsSql.append(" ?,"); // NOMINEE_STATE1
								bufInsSql.append(" ?,"); // NOMINEE_PIN1
								bufInsSql.append(" ?,"); // NOMINEE_PREFIX2
								bufInsSql.append(" ?,"); // NOMINEE_MNAME2
								bufInsSql.append(" ?,"); // NOMINEE_LNAME2
								bufInsSql.append(" ?,"); // MARITAL_STATUS2
								bufInsSql.append(" ?,"); // NOMINEE_AREA2
								bufInsSql.append(" ?,"); // NOMINEE_CITY2
								bufInsSql.append(" ?,"); // NOMINEE_PIN2
								bufInsSql.append(" ?,"); // NOMINEE_PREFIX3
								bufInsSql.append(" ?,"); // NOMINEE_MNAME3
								bufInsSql.append(" ?,"); // NOMINEE_LNAME3
								bufInsSql.append(" ?,"); // MARITAL_STATUS3
								bufInsSql.append(" ?,"); // NOMINEE_AREA3
								bufInsSql.append(" ?,"); // NOMINEE_CITY3
								bufInsSql.append(" ?,"); // NOMINEE_STATE3
								bufInsSql.append(" ?,"); // NOMINEE_PIN3
								bufInsSql.append(" ?,"); // NOMINEE_PREFIX4
								bufInsSql.append(" ?,"); // NOMINEE_MNAME4
								bufInsSql.append(" ?,"); // NOMINEE_LNAME4
								bufInsSql.append(" ?,"); // MARITAL_STATUS4
								bufInsSql.append(" ?,"); // NOMINEE_AREA4
								bufInsSql.append(" ?,"); // NOMINEE_CITY4
								bufInsSql.append(" ?,"); // NOMINEE_STATE4
								bufInsSql.append(" ?,"); // NOMINEE_STATE4
								bufInsSql.append(" ?,"); // NOMINEE_PIN4
								bufInsSql.append(" ?,"); // NOMINEE_ADDRESS_TYPE
								bufInsSql.append(" ?,"); // NOMINEE_STREET
								bufInsSql.append(" ?,"); // NOMINEE_ADDRESS_TYPE1
								bufInsSql.append(" ?,"); // NOMINEE_STREET1
								bufInsSql.append(" ?,"); // NOMINEE_ADDRESS_TYPE2
								bufInsSql.append(" ?,"); // NOMINEE_STREET2
								bufInsSql.append(" ?,"); // NOMINEE_ADDRESS_TYPE3
								bufInsSql.append(" ?,"); // NOMINEE_STREET3
								bufInsSql.append(" ?,"); // NOMINEE_ADDRESS_TYPE4
								bufInsSql.append(" ?)"); // NOMINEE_STREET4
								
								logger.info("bufInsSql::::"+bufInsSql);
								if (CommonFunction.checkNull(dealId).equalsIgnoreCase(""))
									insertPrepStmt.addNull();
								else
									insertPrepStmt.addString(dealId.toUpperCase().trim());
								
								if (CommonFunction.checkNull(dealLoanId).equalsIgnoreCase(""))
									insertPrepStmt.addNull();
								else
									insertPrepStmt.addString(dealLoanId.toUpperCase().trim());
								
								if (CommonFunction.checkNull(loanId).equalsIgnoreCase(""))
									insertPrepStmt.addNull();
								else
									insertPrepStmt.addString(loanId.toUpperCase().trim());
								
								if (CommonFunction.checkNull(vo.getInsuranceProvider()).equalsIgnoreCase(""))
									insertPrepStmt.addNull();
								else
									insertPrepStmt.addString(vo.getInsuranceProvider().toUpperCase().trim());
								
								if (CommonFunction.checkNull(vo.getSumAssured()).equalsIgnoreCase(""))
									insertPrepStmt.addNull();
								else
									insertPrepStmt.addString(vo.getSumAssured().toUpperCase().trim());
								
								if (CommonFunction.checkNull(vo.getTenure()).equalsIgnoreCase(""))
									insertPrepStmt.addNull();
								else
									insertPrepStmt.addString(vo.getTenure().toUpperCase().trim());
								
								if (CommonFunction.checkNull(vo.getAge1()).equalsIgnoreCase(""))
									insertPrepStmt.addNull();
								else
									insertPrepStmt.addString(vo.getAge1().toUpperCase().trim());
								
								if (CommonFunction.checkNull(vo.getInsurancePremium()).equalsIgnoreCase(""))
									insertPrepStmt.addNull();
								else
									insertPrepStmt.addString(vo.getInsurancePremium().toUpperCase().trim());
								
								if (CommonFunction.checkNull(vo.getChargesOnInsurance()).equalsIgnoreCase(""))
									insertPrepStmt.addNull();
								else
									insertPrepStmt.addString(vo.getChargesOnInsurance().toUpperCase().trim());
								
								if (CommonFunction.checkNull(vo.getLbxOtherChargeId()).equalsIgnoreCase(""))
									insertPrepStmt.addNull();
								else
									insertPrepStmt.addString(vo.getLbxOtherChargeId().toUpperCase().trim());

								if (CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
									insertPrepStmt.addNull();
								else
									insertPrepStmt.addString(vo.getMakerId());
								
								if (CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
									insertPrepStmt.addNull();
								else
									insertPrepStmt.addString(vo.getMakerDate());
								//insertPrepStmt.setSql(bufInsSql.toString());
								//add
								if (CommonFunction.checkNull(vo.getPolicyType()).equalsIgnoreCase(""))
									insertPrepStmt.addNull();
								else
									insertPrepStmt.addString(vo.getPolicyType());
							//	insertPrepStmt.setSql(bufInsSql.toString());
								
								if (CommonFunction.checkNull(vo.getPremiumFinanced()).equalsIgnoreCase(""))
									insertPrepStmt.addNull();
								else
									insertPrepStmt.addString(vo.getPremiumFinanced());
								//insertPrepStmt.setSql(bufInsSql.toString());
								
								if (CommonFunction.checkNull(dealCustomerid).equalsIgnoreCase(""))
									insertPrepStmt.addNull();
								else
									insertPrepStmt.addString(dealCustomerid);
								//insertPrepStmt.setSql(bufInsSql.toString());
								if (CommonFunction.checkNull(vo.getInsuranceProduct()).equalsIgnoreCase(""))
									insertPrepStmt.addNull();
								else
									insertPrepStmt.addString(vo.getInsuranceProduct());
								
								
								
								
								if (CommonFunction.checkNull(vo.getNomineeName()).equalsIgnoreCase(""))
									insertPrepStmt.addNull();
								else
									insertPrepStmt.addString(vo.getNomineeName().toUpperCase().trim());
								
								if (CommonFunction.checkNull(vo.getDateOfbirth()).equalsIgnoreCase(""))
									insertPrepStmt.addNull();
								else
									insertPrepStmt.addString(vo.getDateOfbirth().toUpperCase().trim());
								
								if (CommonFunction.checkNull(vo.getGender()).equalsIgnoreCase(""))
									insertPrepStmt.addNull();
								else
									insertPrepStmt.addString(vo.getGender().toUpperCase().trim());
								
								if (CommonFunction.checkNull(vo.getRelationshp()).equalsIgnoreCase(""))
									insertPrepStmt.addNull();
								else
									insertPrepStmt.addString(vo.getRelationshp().toUpperCase().trim());
								 
								if (CommonFunction.checkNull(vo.getPolicyTenure()).equalsIgnoreCase(""))
									insertPrepStmt.addNull();
								 else
									 insertPrepStmt.addString(vo.getPolicyTenure());
									
									
								if (CommonFunction.checkNull(customerConstitutionValue).equalsIgnoreCase(""))
									insertPrepStmt.addNull();
								else
									insertPrepStmt.addString(customerConstitutionValue);
									
								if (CommonFunction.checkNull(vo.getPropertyType()).equalsIgnoreCase(""))
									insertPrepStmt.addNull();
								else
									insertPrepStmt.addString(vo.getPropertyType());
								
									if (CommonFunction.checkNull(vo.getSumAssuPer()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getSumAssuPer());
									
									
									if (CommonFunction.checkNull(vo.getNomineeName1()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getNomineeName1());
									
									if (CommonFunction.checkNull(vo.getGender1()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getGender1());
									
									if (CommonFunction.checkNull(vo.getDateOfbirth1()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getDateOfbirth1());
									
									if (CommonFunction.checkNull(vo.getRelationshp1()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getRelationshp1());
									
									if (CommonFunction.checkNull(vo.getNomineeName2()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getNomineeName2());
									
									if (CommonFunction.checkNull(vo.getGender2()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getGender2());
									
									if (CommonFunction.checkNull(vo.getDateOfbirth2()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getDateOfbirth2());
									
									if (CommonFunction.checkNull(vo.getRelationshp2()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getRelationshp2());
									
									if (CommonFunction.checkNull(vo.getNomineeName3()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getNomineeName3());
									
									if (CommonFunction.checkNull(vo.getGender3()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getGender3());
									
									if (CommonFunction.checkNull(vo.getDateOfbirth3()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getDateOfbirth3());
									
									if (CommonFunction.checkNull(vo.getRelationshp3()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getRelationshp3());
									
									if (CommonFunction.checkNull(vo.getNomineeName4()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getNomineeName4());
									
									if (CommonFunction.checkNull(vo.getGender4()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getGender4());
									
									if (CommonFunction.checkNull(vo.getDateOfbirth4()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getDateOfbirth4());
									
									if (CommonFunction.checkNull(vo.getRelationshp4()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getRelationshp4());
									
									if (CommonFunction.checkNull(vo.getPercentage()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getPercentage());
									
									if (CommonFunction.checkNull(vo.getPercentage1()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getPercentage1());
									
									if (CommonFunction.checkNull(vo.getPercentage2()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getPercentage2());
									
									if (CommonFunction.checkNull(vo.getPercentage3()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getPercentage3());
									
									if (CommonFunction.checkNull(vo.getPercentage4()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getPercentage4());
									
									if (CommonFunction.checkNull(vo.getAddr()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getAddr());
									
									if (CommonFunction.checkNull(vo.getAddr1()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getAddr1());
									
									if (CommonFunction.checkNull(vo.getAddr2()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getAddr2());
									
									if (CommonFunction.checkNull(vo.getAddr3()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getAddr3());
									
									if (CommonFunction.checkNull(vo.getAddr4()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getAddr4());
									//Saurabh Changes start here
									if (CommonFunction.checkNull(vo.getsPrefix()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getsPrefix());
									if (CommonFunction.checkNull(vo.getNomineeMName()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getNomineeMName());
									if (CommonFunction.checkNull(vo.getNomineeLName()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getNomineeLName());
									if (CommonFunction.checkNull(vo.getSmaritalStatus()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getSmaritalStatus());
									if (CommonFunction.checkNull(vo.getSnomineeArea()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getSnomineeArea());
									if (CommonFunction.checkNull(vo.getSnomineeCity()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getSnomineeCity());
									if (CommonFunction.checkNull(vo.getTxtStateCode()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getTxtStateCode());
									if (CommonFunction.checkNull(vo.getSnomineePin()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getSnomineePin());
									if (CommonFunction.checkNull(vo.getsPrefix1()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getsPrefix1());
									if (CommonFunction.checkNull(vo.getNomineeMName1()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getNomineeMName1());
									if (CommonFunction.checkNull(vo.getNomineeLName1()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getNomineeLName1());
									if (CommonFunction.checkNull(vo.getSmaritalstatus1()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getSmaritalstatus1());
									if (CommonFunction.checkNull(vo.getsNomineeArea1()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getsNomineeArea1());
									if (CommonFunction.checkNull(vo.getsNomineeCity1()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getsNomineeCity1());
									if (CommonFunction.checkNull(vo.getTxtStateCode1()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getTxtStateCode1());
									if (CommonFunction.checkNull(vo.getsNomineePin1()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getsNomineePin1());
									if (CommonFunction.checkNull(vo.getsPrefix2()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getsPrefix2());
									if (CommonFunction.checkNull(vo.getNomineeMName2()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getNomineeMName2());
									if (CommonFunction.checkNull(vo.getNomineeLName2()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getNomineeLName2());
									if (CommonFunction.checkNull(vo.getSmaritalStatus2()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getSmaritalStatus2());
									if (CommonFunction.checkNull(vo.getsNomineeArea2()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getsNomineeArea2());
									if (CommonFunction.checkNull(vo.getsNomineeCity2()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getsNomineeCity2());
									if (CommonFunction.checkNull(vo.getTxtStateCode2()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getTxtStateCode2());
									if (CommonFunction.checkNull(vo.getsNomineePin2()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getsNomineePin2());
									if (CommonFunction.checkNull(vo.getsPrefix3()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getsPrefix3());
									if (CommonFunction.checkNull(vo.getNomineeMName3()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getNomineeMName3());
									if (CommonFunction.checkNull(vo.getNomineeLName3()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getNomineeLName3());
									if (CommonFunction.checkNull(vo.getSmaritalStatus3()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getSmaritalStatus3());
									if (CommonFunction.checkNull(vo.getsNomineeArea3()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getsNomineeArea3());
									if (CommonFunction.checkNull(vo.getsNomineeCity3()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getsNomineeCity3());
									if (CommonFunction.checkNull(vo.getTxtStateCode3()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getTxtStateCode3());
									if (CommonFunction.checkNull(vo.getsNomineePin3()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getsNomineePin3());
									if (CommonFunction.checkNull(vo.getsPrefix4()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getsPrefix4());
									if (CommonFunction.checkNull(vo.getNomineeMName4()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getNomineeMName4());
									if (CommonFunction.checkNull(vo.getNomineeLName4()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getNomineeLName4());
									if (CommonFunction.checkNull(vo.getSmaritalStatus4()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getSmaritalStatus4());
									if (CommonFunction.checkNull(vo.getsNomineeArea4()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getsNomineeArea4());
									if (CommonFunction.checkNull(vo.getsNomineeCity4()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getsNomineeCity4());
									if (CommonFunction.checkNull(vo.getTxtStateCode4()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getTxtStateCode4());
									if (CommonFunction.checkNull(vo.getsNomineePin4()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getsNomineePin4());
									if (CommonFunction.checkNull(vo.getSaddressType()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getSaddressType());
									if (CommonFunction.checkNull(vo.getSinsuranceStreet()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getSinsuranceStreet());
									
									if (CommonFunction.checkNull(vo.getSaddressType1()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getSaddressType1());
									if (CommonFunction.checkNull(vo.getSinsuranceStreet1()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getSinsuranceStreet1());
									if (CommonFunction.checkNull(vo.getSaddressType2()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getSaddressType2());
									if (CommonFunction.checkNull(vo.getSinsuranceStreet2()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getSinsuranceStreet2());
									if (CommonFunction.checkNull(vo.getSaddressType3()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getSaddressType3());
									if (CommonFunction.checkNull(vo.getSinsuranceStreet3()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getSinsuranceStreet3());
									if (CommonFunction.checkNull(vo.getSaddressType4()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getSaddressType4());
									if (CommonFunction.checkNull(vo.getSinsuranceStreet4()).equalsIgnoreCase(""))
										insertPrepStmt.addNull();
									else
										insertPrepStmt.addString(vo.getSinsuranceStreet4());
									//Saurabh Changes Ends Here
								
								insertPrepStmt.setSql(bufInsSql.toString());
								
								// add end
								
								logger.info("IN insertCmInsurance() insert query1 ### "+ insertPrepStmt.printQuery());
								
								qryList.add(insertPrepStmt);
											
								status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
							 
							}
							logger.info("In insertCmInsurance......................"+ status);
						

					} catch (Exception e) {
						e.printStackTrace();
					}
					finally{
						qryList.clear();
						qryList = null;
						
					         }
					return status;
					
		}

		@Override
		public boolean updateDealLoan(CmInsuranceVo vo, String loanId) {	
			boolean status = false;
			String insurance_premium="";
			int inspre=0;
			int sum_assure_deal_loan1=0;
			int premium=0;
			int total=0;
			int marginAmt=0;
			double marginPerct=0;
			int assetCostAmount=0;
			//double qry2=0;
			String qry2;
			String qry6="";
			String premiumFinanced=CommonFunction.checkNull(vo.getPremiumFinanced());
			String qry1="select loan_deal_id from cr_loan_dtl where loan_id='"+loanId+"'";
			
			String loan_dealId=ConnectionDAO.singleReturn(qry1);
			if(loan_dealId!="")
			{
				
				 qry2="select round(sum(insurance_premium)) from cr_insurance_dtl  where amount_to_be_financed='Y' and  loan_id='"+loanId+"' ";
				insurance_premium= ConnectionDAO.singleReturn(qry2);
				
				
				
				
				// qry6="select round(ifnull(insurance_premium,0)) from cr_loan_dtl  where  loan_id='"+loanId+"' ";
				// insurance_premium= ConnectionDAO.singleReturn(qry6);
				
				
				if(insurance_premium=="")
				{
					/*if(qry6=="")
					{
						inspre=0;
						qry2=Double.parseDouble(vo.getInsurancePremium());
						
						premium=(int)qry2;
					}
					else
					{
						inspre=Integer.parseInt(insurance_premium);
						qry2=Double.parseDouble(vo.getInsurancePremium());
						
						premium=(int)qry2;
						premium=(premium+inspre);
					}*/
					insurance_premium="0";
					premium=Integer.parseInt(insurance_premium);
				}
				else
				{
					premium=Integer.parseInt(insurance_premium);
				}
				}
				/*else
				{
					if(qry6=="")
					{
						inspre=0;
				 
			}
			else
			{
						inspre=Integer.parseInt(insurance_premium);
						premium=inspre;
			}
			
					
				}
			
			}*/
			else
			{
				
				 premium=0;
			}
			
			 String sum_loan_deal_amount_new=" select round(ifnull(DEAL_LOAN_AMOUNT_NEW,loan_loan_amount)) from cr_loan_dtl where loan_id='"+loanId+"' ";
			 String sum_assure_deal_loan_new = ConnectionDAO.singleReturn(sum_loan_deal_amount_new);
				logger.info("sum_assure_deal_loan:::"+sum_loan_deal_amount_new);
				if(sum_loan_deal_amount_new!="")
				{
					sum_assure_deal_loan1 = Integer.parseInt(sum_assure_deal_loan_new);
				}
				else 
				{
					sum_assure_deal_loan1 =0;
				}
				
				
				total=(sum_assure_deal_loan1+premium);
				
				String assetCost=" select round(ifnull(loan_asset_cost,0)) from cr_loan_dtl where  loan_id='"+loanId+"' ";
				 String assetCostValue = ConnectionDAO.singleReturn(assetCost);
					logger.info("assetCostValue:::"+assetCostValue);
					if(assetCost!="")
					{
						assetCostAmount = Integer.parseInt(assetCostValue);
					}
					else 
					{
						assetCostAmount =0;
					}
					if(assetCostAmount>0){
					marginAmt=(assetCostAmount-total);
					marginPerct=((marginAmt*100.00)/assetCostAmount);
					}
					else{
						marginPerct=0;
					}
					logger.info("marginPerct::"+marginPerct);
				
			ArrayList qryList = new ArrayList();
			PrepStmtObject insertPrepStmtObject=null;
			insertPrepStmtObject = new PrepStmtObject();
			StringBuilder bufInsSql = null;
			bufInsSql = new StringBuilder();
			
			
				try {
					
				
				bufInsSql.append("UPDATE cr_loan_dtl SET insurance_premium=?, DEAL_LOAN_AMOUNT_NEW=? , loan_loan_amount=?,loan_margin_amount=?,loan_margin_rate=? where loan_ID=?  "); 
				
				
				if (CommonFunction.checkNull(premium).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addInt(premium);
				
				
				if (CommonFunction.checkNull(sum_assure_deal_loan1).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addInt(sum_assure_deal_loan1);
				
				if (CommonFunction.checkNull(total).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addInt(total);
				
				if (CommonFunction.checkNull(marginAmt).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addInt(marginAmt);
				
				
				
				if (CommonFunction.checkNull(marginPerct).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addFloat(marginPerct);
				
				
				if (CommonFunction.checkNull(loanId).equalsIgnoreCase(""))
					insertPrepStmtObject.addNull();
				else
					insertPrepStmtObject.addString(loanId.toUpperCase().trim());
				
				
				
				
				insertPrepStmtObject.setSql(bufInsSql.toString());
				
				logger.info("IN insertCmInsurance() update cr_loan_dtl query1 ### "+ insertPrepStmtObject.printQuery());
				
				qryList.add(insertPrepStmtObject);
							
				status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
			 
			
				
								
					status = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList);
				 
					
				
				logger.info("In insertCmInsurance......................"+ status);
			
				
				
		} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				qryList.clear();
				qryList = null;
				
			         }
			return status;
}

		@Override
		public boolean deleteCmInsurance(CmInsuranceVo vo, String loanId) {
			
		
		boolean status = false;
		String qry=null;
		PrepStmtObject insertPrepStmtObject=null;
		ArrayList alDeleteQuery=new ArrayList(1);
			
		RefreshFlagVo vo1 = new RefreshFlagVo();
		if(loanId!=null && !loanId.trim().equalsIgnoreCase(""))
		vo1.setRecordId(Integer.parseInt(loanId.trim()));
		vo1.setTabIndex(16);
		vo1.setModuleName("CM");
	    RefreshFlagValueInsert.updateRefreshFlag(vo1);
	
	    
	    
	    String insuranceId=vo.getInsuranceId();
		String insuranceIdList="";
		
		insuranceIdList=insuranceId.replace("|",",");
		char []chars = insuranceIdList.toCharArray();
		String s1 = "";
		int abc=chars.length-1;
		for(int i = 0; i < chars.length-1; i++)
		{
			s1 += chars[i];
			
		}
	    
	
		   try {
				 qry="delete from cr_insurance_dtl where loan_id='"+loanId+"' and insurance_Id in ("+s1+") ";
				 insertPrepStmtObject=new PrepStmtObject();
				 insertPrepStmtObject.setSql(qry);
				 alDeleteQuery.add(insertPrepStmtObject);
				 logger.info("qry to delete insurance::"+qry);
			      status = ConnectionDAO.sqlInsUpdDeletePrepStmt(alDeleteQuery);
		
			   } 
		catch (Exception e) {
					e.printStackTrace();
				}
		        logger.info("status of delete  is "+status);
				return status;
				
		}

		@Override
		public ArrayList getViewInsurance(CmInsuranceVo ob, String loanId) {ArrayList list=new ArrayList();
		CmInsuranceVo vo = (CmInsuranceVo)ob ;
		String insuranceId=vo.getInsuranceId();
		String serviceCalled="";
		String serviceProduct="";
		String qry3="select product_id from cr_insurance_dtl  where insurance_id = '"+insuranceId+"'";
		String product= ConnectionDAO.singleReturn(qry3);
		
		try
		{
			StringBuilder query=new StringBuilder();
			query.append(" select a.charge_id,a.sum_assured,a.loan_tenure,round(a.insurance_premium),a.charges_on_premium,c.CHARGE_DESC,a.policy_type, AMOUNT_TO_BE_FINANCED,a.customer_id,a.product_id,a.nominee_name,date_format(a.nominee_dob,'%d-%m-%Y'), a.gender,a.nominee_relation,a.SUM_ASSURED_PERCENTAGE,policy_tenure,GM.description,CIPM.METHOD_TYPE,'',  "
					+ " A.NOMINEE_NAME1,A.GENDER1,date_format(A.NOMINEE_DOB1,'%d-%m-%Y'),A.NOMINEE_RELATION1,A.NOMINEE_NAME2,A.GENDER2,date_format(A.NOMINEE_DOB2,'%d-%m-%Y'),A.NOMINEE_RELATION2,A.NOMINEE_NAME3,A.GENDER3,date_format(A.NOMINEE_DOB3,'%d-%m-%Y'),A.NOMINEE_RELATION3,A.NOMINEE_NAME4,A.GENDER4,date_format(A.NOMINEE_DOB4,'%d-%m-%Y'),A.NOMINEE_RELATION4,A.NOMINEE_PERCENTAGE,A.NOMINEE_PERCENTAGE1,A.NOMINEE_PERCENTAGE2,A.NOMINEE_PERCENTAGE3,A.NOMINEE_PERCENTAGE4,A.ADDR,A.ADDR1,A.ADDR2,A.ADDR3,A.ADDR4, "
					+ " a.NOMINEE_PREFIX,a.NOMINEE_MNAME,a.NOMINEE_LNAME,a.MARITAL_STATUS,a.NOMINEE_AREA,a.NOMINEE_CITY,csm.state_desc,a.NOMINEE_PIN,a.NOMINEE_PREFIX1,a.NOMINEE_MNAME1,"
					+ " a.NOMINEE_LNAME1,a.MARITAL_STATUS1,a.NOMINEE_AREA1,a.NOMINEE_CITY1,csm1.state_desc,a.NOMINEE_PIN1,a.NOMINEE_PREFIX2,a.NOMINEE_MNAME2,a.NOMINEE_LNAME2,a.MARITAL_STATUS2,"
					+ " a.NOMINEE_AREA2,a.NOMINEE_CITY2,csm2.state_desc,a.NOMINEE_PIN2,a.NOMINEE_PREFIX3,a.NOMINEE_MNAME3,a.NOMINEE_LNAME3,a.MARITAL_STATUS3,a.NOMINEE_AREA3,a.NOMINEE_CITY3,csm3.state_desc,a.NOMINEE_PIN3,"
					+ " a.NOMINEE_PREFIX4,a.NOMINEE_MNAME4,a.NOMINEE_LNAME4,a.MARITAL_STATUS4,a.NOMINEE_AREA4,a.NOMINEE_CITY4,csm4.state_desc,a.NOMINEE_PIN4,"
					+ " case a.NOMINEE_ADDRESS_TYPE when 'OFFICE' then 'OFFICE' when 'OTHER' then 'OTHER' when 'PERMANENT' then 'PERMANENT' when 'REGOFF' then 'REGISTERED OFFICE' when 'REI' then 'RESIDENCE' when 'RES' then 'RESI CUM OFFICE' when 'TP' then 'THIRD PARTY' end ,a.NOMINEE_STREET,"
					+ " case a.NOMINEE_ADDRESS_TYPE1 when 'OFFICE' then 'OFFICE' when 'OTHER' then 'OTHER' when 'PERMANENT' then 'PERMANENT' when 'REGOFF' then 'REGISTERED OFFICE' when 'REI' then 'RESIDENCE' when 'RES' then 'RESI CUM OFFICE' when 'TP' then 'THIRD PARTY' end ,a.NOMINEE_STREET1,"
					+ " case a.NOMINEE_ADDRESS_TYPE2 when 'OFFICE' then 'OFFICE' when 'OTHER' then 'OTHER' when 'PERMANENT' then 'PERMANENT' when 'REGOFF' then 'REGISTERED OFFICE' when 'REI' then 'RESIDENCE' when 'RES' then 'RESI CUM OFFICE' when 'TP' then 'THIRD PARTY' end ,a.NOMINEE_STREET2,"
					+ " case a.NOMINEE_ADDRESS_TYPE3 when 'OFFICE' then 'OFFICE' when 'OTHER' then 'OTHER' when 'PERMANENT' then 'PERMANENT' when 'REGOFF' then 'REGISTERED OFFICE' when 'REI' then 'RESIDENCE' when 'RES' then 'RESI CUM OFFICE' when 'TP' then 'THIRD PARTY' end ,a.NOMINEE_STREET3,"
					+ " case a.NOMINEE_ADDRESS_TYPE4 when 'OFFICE' then 'OFFICE' when 'OTHER' then 'OTHER' when 'PERMANENT' then 'PERMANENT' when 'REGOFF' then 'REGISTERED OFFICE' when 'REI' then 'RESIDENCE' when 'RES' then 'RESI CUM OFFICE' when 'TP' then 'THIRD PARTY' end ,a.NOMINEE_STREET4, a.NOMINEE_STATE,a.NOMINEE_STATE1,a.NOMINEE_STATE2,a.NOMINEE_STATE3,a.NOMINEE_STATE4"
					+ " from cr_insurance_dtl a " +
					" left join com_state_m csm on csm.STATE_ID=a.NOMINEE_STATE " +
					" left join com_state_m csm1 on csm1.STATE_ID=a.NOMINEE_STATE1 " +	
					" left join com_state_m csm2 on csm2.STATE_ID=a.NOMINEE_STATE2 " +	
					" left join com_state_m csm3 on csm3.STATE_ID=a.NOMINEE_STATE3 " +	
					" left join com_state_m csm4 on csm4.STATE_ID=a.NOMINEE_STATE4 " +	
					" join com_charge_code_m b on b.CHARGE_CODE=a.CHARGE_ID  " +
					" left join com_charge_code_m c on c.CHARGE_CODE=a.OTHER_CHARGE_ID " +
					" left join generic_master GM on GM.parent_value=a.charge_id and GM.value ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(product)).trim()+"'" +
					" LEFT JOIN COM_INSURANCE_PRODUCT_METHOD_M CIPM on GM.VALUE=CIPM.PRODUCT_ID and CIPM.REC_STATUS='A' "+
					" where loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()+"' and insurance_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(insuranceId)).trim()+"' ");
			 
		logger.info("query for getViewInsurance " +query);
			
			ArrayList view = ConnectionDAO.sqlSelect(query.toString());
			logger.info("calculate size: "+view.size());
			query=null;
			int size=view.size();
			for(int i=0;i<size;i++){
		
				ArrayList header1=(ArrayList)view.get(i);
				if(header1!=null && header1.size()>0)
				{
					vo = new CmInsuranceVo();
					vo.setInsuranceProvider((CommonFunction.checkNull(header1.get(0))).trim());
					vo.setSumAssured((CommonFunction.checkNull(header1.get(1))).trim());
					vo.setTenure((CommonFunction.checkNull(header1.get(2))).trim());
					vo.setInsurancePremium((CommonFunction.checkNull(header1.get(3))).trim());
					vo.setChargesOnInsurance((CommonFunction.checkNull(header1.get(4))).trim());
					vo.setOtherChargeId((CommonFunction.checkNull(header1.get(5))).trim());
					vo.setPolicyType((CommonFunction.checkNull(header1.get(6))).trim());
					vo.setPremiumFinanced((CommonFunction.checkNull(header1.get(7))).trim());
					vo.setCustId((CommonFunction.checkNull(header1.get(8))).trim());
					serviceProduct=(CommonFunction.checkNull(header1.get(9))).trim();
					serviceCalled=(CommonFunction.checkNull(header1.get(17))).trim();
					vo.setInsuranceProductId(serviceProduct+"|"+serviceCalled);
					vo.setNomineeName((CommonFunction.checkNull(header1.get(10))).trim());
					vo.setDateOfbirth((CommonFunction.checkNull(header1.get(11))).trim());
					vo.setGender((CommonFunction.checkNull(header1.get(12))).trim());
					vo.setRelationshp((CommonFunction.checkNull(header1.get(13))).trim());
					vo.setSumAssuPer((CommonFunction.checkNull(header1.get(14))).trim());
					vo.setPolicyTenure((CommonFunction.checkNull(header1.get(15))).trim());
					vo.setInsuranceProduct((CommonFunction.checkNull(header1.get(16))).trim());
					vo.setNomineeName1((CommonFunction.checkNull(header1.get(19))).trim());
					vo.setGender1((CommonFunction.checkNull(header1.get(20))).trim());
					vo.setDateOfbirth1((CommonFunction.checkNull(header1.get(21))).trim());
					vo.setRelationshp1((CommonFunction.checkNull(header1.get(22))).trim());
					vo.setNomineeName2((CommonFunction.checkNull(header1.get(23))).trim());
					vo.setGender2((CommonFunction.checkNull(header1.get(24))).trim());
					vo.setDateOfbirth2((CommonFunction.checkNull(header1.get(25))).trim());
					vo.setRelationshp2((CommonFunction.checkNull(header1.get(26))).trim());
					vo.setNomineeName3((CommonFunction.checkNull(header1.get(27))).trim());
					vo.setGender3((CommonFunction.checkNull(header1.get(28))).trim());
					vo.setDateOfbirth3((CommonFunction.checkNull(header1.get(29))).trim());
					vo.setRelationshp3((CommonFunction.checkNull(header1.get(30))).trim());
					vo.setNomineeName4((CommonFunction.checkNull(header1.get(31))).trim());
					vo.setGender4((CommonFunction.checkNull(header1.get(32))).trim());
					vo.setDateOfbirth4((CommonFunction.checkNull(header1.get(33))).trim());
					vo.setRelationshp4((CommonFunction.checkNull(header1.get(34))).trim());
					vo.setPercentage((CommonFunction.checkNull(header1.get(35))).trim());
					vo.setPercentage1((CommonFunction.checkNull(header1.get(36))).trim());
					vo.setPercentage2((CommonFunction.checkNull(header1.get(37))).trim());
					vo.setPercentage3((CommonFunction.checkNull(header1.get(38))).trim());
					vo.setPercentage4((CommonFunction.checkNull(header1.get(39))).trim());
					vo.setAddr((CommonFunction.checkNull(header1.get(40))).trim());
					vo.setAddr1((CommonFunction.checkNull(header1.get(41))).trim());
					vo.setAddr2((CommonFunction.checkNull(header1.get(42))).trim());
					vo.setAddr3((CommonFunction.checkNull(header1.get(43))).trim());
					vo.setAddr4((CommonFunction.checkNull(header1.get(44))).trim());
					//Saurabh Changes start here
					vo.setsPrefix((CommonFunction.checkNull(header1.get(45))).trim());
					vo.setNomineeMName((CommonFunction.checkNull(header1.get(46))).trim());
					vo.setNomineeLName((CommonFunction.checkNull(header1.get(47))).trim());
					vo.setSmaritalStatus((CommonFunction.checkNull(header1.get(48))).trim());
					vo.setSnomineeArea((CommonFunction.checkNull(header1.get(49))).trim());
					vo.setSnomineeCity((CommonFunction.checkNull(header1.get(50))).trim());
					vo.setSnomineeState((CommonFunction.checkNull(header1.get(51))).trim());
					vo.setSnomineePin((CommonFunction.checkNull(header1.get(52))).trim());
					
					vo.setsPrefix1((CommonFunction.checkNull(header1.get(53))).trim());
					vo.setNomineeMName1((CommonFunction.checkNull(header1.get(54))).trim());
					vo.setNomineeLName1((CommonFunction.checkNull(header1.get(55))).trim());
					vo.setSmaritalstatus1((CommonFunction.checkNull(header1.get(56))).trim());
					vo.setsNomineeArea1((CommonFunction.checkNull(header1.get(57))).trim());
					vo.setsNomineeCity1((CommonFunction.checkNull(header1.get(58))).trim());
					vo.setsNomineeState1((CommonFunction.checkNull(header1.get(59))).trim());
					vo.setsNomineePin1((CommonFunction.checkNull(header1.get(60))).trim());

					vo.setsPrefix2((CommonFunction.checkNull(header1.get(61))).trim());
					vo.setNomineeMName2((CommonFunction.checkNull(header1.get(62))).trim());
					vo.setNomineeLName2((CommonFunction.checkNull(header1.get(63))).trim());
					vo.setSmaritalStatus2((CommonFunction.checkNull(header1.get(64))).trim());
					vo.setsNomineeArea2((CommonFunction.checkNull(header1.get(65))).trim());
					vo.setsNomineeCity2((CommonFunction.checkNull(header1.get(66))).trim());
					vo.setsNomineeState2((CommonFunction.checkNull(header1.get(67))).trim());
					vo.setsNomineePin2((CommonFunction.checkNull(header1.get(68))).trim());
					
					vo.setsPrefix3((CommonFunction.checkNull(header1.get(69))).trim());
					vo.setNomineeMName3((CommonFunction.checkNull(header1.get(70))).trim());
					vo.setNomineeLName3((CommonFunction.checkNull(header1.get(71))).trim());
					vo.setSmaritalStatus3((CommonFunction.checkNull(header1.get(72))).trim());
					vo.setsNomineeArea3((CommonFunction.checkNull(header1.get(73))).trim());
					vo.setsNomineeCity3((CommonFunction.checkNull(header1.get(74))).trim());
					vo.setsNomineeState3((CommonFunction.checkNull(header1.get(75))).trim());
					vo.setsNomineePin3((CommonFunction.checkNull(header1.get(76))).trim());
					
					vo.setsPrefix4((CommonFunction.checkNull(header1.get(77))).trim());
					vo.setNomineeMName4((CommonFunction.checkNull(header1.get(78))).trim());
					vo.setNomineeLName4((CommonFunction.checkNull(header1.get(79))).trim());
					vo.setSmaritalStatus4((CommonFunction.checkNull(header1.get(80))).trim());
					vo.setsNomineeArea4((CommonFunction.checkNull(header1.get(81))).trim());
					vo.setsNomineeCity4((CommonFunction.checkNull(header1.get(82))).trim());
					vo.setsNomineeState4((CommonFunction.checkNull(header1.get(83))).trim());
					vo.setsNomineePin4((CommonFunction.checkNull(header1.get(84))).trim());
					vo.setSaddressType((CommonFunction.checkNull(header1.get(85))).trim());
					vo.setSinsuranceStreet((CommonFunction.checkNull(header1.get(86))).trim());
					
					vo.setSaddressType1((CommonFunction.checkNull(header1.get(87))).trim());
					vo.setSinsuranceStreet1((CommonFunction.checkNull(header1.get(88))).trim());
					vo.setSaddressType2((CommonFunction.checkNull(header1.get(89))).trim());
					vo.setSinsuranceStreet2((CommonFunction.checkNull(header1.get(90))).trim());
					vo.setSaddressType3((CommonFunction.checkNull(header1.get(91))).trim());
					vo.setSinsuranceStreet3((CommonFunction.checkNull(header1.get(92))).trim());
					vo.setSaddressType4((CommonFunction.checkNull(header1.get(93))).trim());
					vo.setSinsuranceStreet4((CommonFunction.checkNull(header1.get(94))).trim());
					
					vo.setTxtStateCode((CommonFunction.checkNull(header1.get(95))).trim());
					vo.setTxtStateCode1((CommonFunction.checkNull(header1.get(96))).trim());
					vo.setTxtStateCode2((CommonFunction.checkNull(header1.get(97))).trim());
					vo.setTxtStateCode3((CommonFunction.checkNull(header1.get(98))).trim());
					vo.setTxtStateCode4((CommonFunction.checkNull(header1.get(99))).trim());
					//Saurabh Ends
					list.add(vo);
					vo=null;
				}
				header1.clear();
				header1=null;
			}
			view.clear();
			view=null;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return list;
		
	}

		@Override
		public ArrayList getReloadInsurance(CmInsuranceVo ob, String loanId) {
		
		ArrayList list=new ArrayList();
		CmInsuranceVo vo = (CmInsuranceVo)ob ;
		String chargeId=vo.getChargeId();
		String insuranceProvider=vo.getInsuranceProvider();
		String sumAssured=vo.getSumAssured();
		String tenure=vo.getTenure();
		String age=vo.getAge();
		String insurancePremium=vo.getInsurancePremium();
		String chargesOnInsurance=vo.getChargesOnInsurance();
		String otherChargeId=vo.getOtherChargeId();
		String insuranceProductId=vo.getInsuranceProductId();
		String insuranceId=vo.getInsuranceId();
		String serviceCalled="";
		String serviceProduct="";
		String qry3="select product_id from cr_insurance_dtl  where insurance_id = '"+insuranceId+"'";
		String product= ConnectionDAO.singleReturn(qry3);
		
		try
		{
			StringBuilder query=new StringBuilder();
			query.append("select a.charge_id,round(a.sum_assured),a.loan_tenure,round(a.insurance_premium),a.charges_on_premium,c.CHARGE_DESC,policy_type,AMOUNT_TO_BE_FINANCED,a.customer_id,a.product_id,a.nominee_name,date_format(a.nominee_dob,'%d-%m-%Y'),a.gender,a.nominee_relation,policy_tenure,a.SUM_ASSURED_PERCENTAGE,property_type,GM.description,CIPM.METHOD_TYPE, "
					+ " A.NOMINEE_NAME1,A.GENDER1,date_format(a.nominee_dob1,'%d-%m-%Y'),A.NOMINEE_RELATION1,A.NOMINEE_NAME2,A.GENDER2,date_format(a.nominee_dob2,'%d-%m-%Y'),A.NOMINEE_RELATION2,A.NOMINEE_NAME3,A.GENDER3,date_format(a.nominee_dob3,'%d-%m-%Y'),A.NOMINEE_RELATION3,A.NOMINEE_NAME4,A.GENDER4,date_format(a.nominee_dob4,'%d-%m-%Y'),A.NOMINEE_RELATION4,A.NOMINEE_PERCENTAGE,A.NOMINEE_PERCENTAGE1,A.NOMINEE_PERCENTAGE2,A.NOMINEE_PERCENTAGE3,A.NOMINEE_PERCENTAGE4,A.ADDR,A.ADDR1,A.ADDR2,A.ADDR3,A.ADDR4, "
					+ " a.NOMINEE_PREFIX,a.NOMINEE_MNAME,a.NOMINEE_LNAME,a.MARITAL_STATUS,a.NOMINEE_AREA,a.NOMINEE_CITY,csm.state_desc,a.NOMINEE_PIN,a.NOMINEE_PREFIX1,a.NOMINEE_MNAME1,"
					+ " a.NOMINEE_LNAME1,a.MARITAL_STATUS1,a.NOMINEE_AREA1,a.NOMINEE_CITY1,csm1.state_desc,a.NOMINEE_PIN1,a.NOMINEE_PREFIX2,a.NOMINEE_MNAME2,a.NOMINEE_LNAME2,a.MARITAL_STATUS2,"
					+ " a.NOMINEE_AREA2,a.NOMINEE_CITY2,csm2.state_desc,a.NOMINEE_PIN2,a.NOMINEE_PREFIX3,a.NOMINEE_MNAME3,a.NOMINEE_LNAME3,a.MARITAL_STATUS3,a.NOMINEE_AREA3,a.NOMINEE_CITY3,csm3.state_desc,a.NOMINEE_PIN3,"
					+ " a.NOMINEE_PREFIX4,a.NOMINEE_MNAME4,a.NOMINEE_LNAME4,a.MARITAL_STATUS4,a.NOMINEE_AREA4,a.NOMINEE_CITY4,csm4.state_desc,a.NOMINEE_PIN4,a.NOMINEE_ADDRESS_TYPE,a.NOMINEE_STREET,a.NOMINEE_ADDRESS_TYPE1,a.NOMINEE_STREET1,a.NOMINEE_ADDRESS_TYPE2,a.NOMINEE_STREET2, a.NOMINEE_ADDRESS_TYPE3,a.NOMINEE_STREET3,a.NOMINEE_ADDRESS_TYPE4,a.NOMINEE_STREET4,a.NOMINEE_STATE,a.NOMINEE_STATE1,a.NOMINEE_STATE2,a.NOMINEE_STATE3,a.NOMINEE_STATE4"
					+ " from cr_insurance_dtl a " +
			" left join com_state_m csm on csm.STATE_ID=a.NOMINEE_STATE " +
			" left join com_state_m csm1 on csm1.STATE_ID=a.NOMINEE_STATE1 " +	
			" left join com_state_m csm2 on csm2.STATE_ID=a.NOMINEE_STATE2 " +	
			" left join com_state_m csm3 on csm3.STATE_ID=a.NOMINEE_STATE3 " +	
			" left join com_state_m csm4 on csm4.STATE_ID=a.NOMINEE_STATE4 " +	
			" join com_charge_code_m b on b.CHARGE_CODE=a.CHARGE_ID " +
			" left join com_charge_code_m c on c.CHARGE_CODE=a.OTHER_CHARGE_ID " +
			" left join generic_master GM on GM.parent_value=a.charge_id and GM.value='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(product)).trim()+"'" +
			" LEFT JOIN COM_INSURANCE_PRODUCT_METHOD_M CIPM on GM.VALUE=CIPM.PRODUCT_ID and CIPM.REC_STATUS='A' "+
			" where loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(loanId)).trim()+"' and insurance_id ='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull(insuranceId)).trim()+"' ");
	 
		ArrayList view = ConnectionDAO.sqlSelect(query.toString());
		logger.info("querysssssssss:::::"+query);
		query=null;
		int size=view.size();
		for(int i=0;i<size;i++){

		ArrayList header1=(ArrayList)view.get(i);
		if(header1!=null && header1.size()>0)
		{
		vo = new CmInsuranceVo();
		vo.setInsuranceProvider((CommonFunction.checkNull(header1.get(0))).trim());
		vo.setSumAssured((CommonFunction.checkNull(header1.get(1))).trim());
		vo.setTenure((CommonFunction.checkNull(header1.get(2))).trim());
		vo.setInsurancePremium((CommonFunction.checkNull(header1.get(3))).trim());
		vo.setChargesOnInsurance((CommonFunction.checkNull(header1.get(4))).trim());
		vo.setOtherChargeId((CommonFunction.checkNull(header1.get(5))).trim());
		vo.setPolicyType((CommonFunction.checkNull(header1.get(6))).trim());
		vo.setPremiumFinanced((CommonFunction.checkNull(header1.get(7))).trim());
		vo.setCustId((CommonFunction.checkNull(header1.get(8))).trim());
		serviceProduct=(CommonFunction.checkNull(header1.get(9))).trim();
		serviceCalled=(CommonFunction.checkNull(header1.get(18))).trim();
		vo.setInsuranceProductId(serviceProduct+"|"+serviceCalled);					//  Append for getting service by which insurance premium should be calculated
		vo.setNomineeName((CommonFunction.checkNull(header1.get(10))).trim());
		vo.setDateOfbirth((CommonFunction.checkNull(header1.get(11))).trim());
		vo.setGender((CommonFunction.checkNull(header1.get(12))).trim());
		vo.setRelationshp((CommonFunction.checkNull(header1.get(13))).trim());
		vo.setPolicyTenure((CommonFunction.checkNull(header1.get(14))).trim());
		vo.setSumAssuPer((CommonFunction.checkNull(header1.get(15))).trim());
		vo.setPropertyType((CommonFunction.checkNull(header1.get(16))).trim());
		vo.setInsuranceProduct((CommonFunction.checkNull(header1.get(17))).trim());
		vo.setNomineeName1((CommonFunction.checkNull(header1.get(19))).trim());
		vo.setGender1((CommonFunction.checkNull(header1.get(20))).trim());
		vo.setDateOfbirth1((CommonFunction.checkNull(header1.get(21))).trim());
		vo.setRelationshp1((CommonFunction.checkNull(header1.get(22))).trim());
		vo.setNomineeName2((CommonFunction.checkNull(header1.get(23))).trim());
		vo.setGender2((CommonFunction.checkNull(header1.get(24))).trim());
		vo.setDateOfbirth2((CommonFunction.checkNull(header1.get(25))).trim());
		vo.setRelationshp2((CommonFunction.checkNull(header1.get(26))).trim());
		vo.setNomineeName3((CommonFunction.checkNull(header1.get(27))).trim());
		vo.setGender3((CommonFunction.checkNull(header1.get(28))).trim());
		vo.setDateOfbirth3((CommonFunction.checkNull(header1.get(29))).trim());
		vo.setRelationshp3((CommonFunction.checkNull(header1.get(30))).trim());
		vo.setNomineeName4((CommonFunction.checkNull(header1.get(31))).trim());
		vo.setGender4((CommonFunction.checkNull(header1.get(32))).trim());
		vo.setDateOfbirth4((CommonFunction.checkNull(header1.get(33))).trim());
		vo.setRelationshp4((CommonFunction.checkNull(header1.get(34))).trim());
		vo.setPercentage((CommonFunction.checkNull(header1.get(35))).trim());
		vo.setPercentage1((CommonFunction.checkNull(header1.get(36))).trim());
		vo.setPercentage2((CommonFunction.checkNull(header1.get(37))).trim());
		vo.setPercentage3((CommonFunction.checkNull(header1.get(38))).trim());
		vo.setPercentage4((CommonFunction.checkNull(header1.get(39))).trim());
		vo.setAddr((CommonFunction.checkNull(header1.get(40))).trim());
		vo.setAddr1((CommonFunction.checkNull(header1.get(41))).trim());
		vo.setAddr2((CommonFunction.checkNull(header1.get(42))).trim());
		vo.setAddr3((CommonFunction.checkNull(header1.get(43))).trim());
		vo.setAddr4((CommonFunction.checkNull(header1.get(44))).trim());
		//Saurabh Changes start here
		vo.setsPrefix((CommonFunction.checkNull(header1.get(45))).trim());
		vo.setNomineeMName((CommonFunction.checkNull(header1.get(46))).trim());
		vo.setNomineeLName((CommonFunction.checkNull(header1.get(47))).trim());
		vo.setSmaritalStatus((CommonFunction.checkNull(header1.get(48))).trim());
		vo.setSnomineeArea((CommonFunction.checkNull(header1.get(49))).trim());
		vo.setSnomineeCity((CommonFunction.checkNull(header1.get(50))).trim());
		vo.setSnomineeState((CommonFunction.checkNull(header1.get(51))).trim());
		vo.setSnomineePin((CommonFunction.checkNull(header1.get(52))).trim());
		
		vo.setsPrefix1((CommonFunction.checkNull(header1.get(53))).trim());
		vo.setNomineeMName1((CommonFunction.checkNull(header1.get(54))).trim());
		vo.setNomineeLName1((CommonFunction.checkNull(header1.get(55))).trim());
		vo.setSmaritalstatus1((CommonFunction.checkNull(header1.get(56))).trim());
		vo.setsNomineeArea1((CommonFunction.checkNull(header1.get(57))).trim());
		vo.setsNomineeCity1((CommonFunction.checkNull(header1.get(58))).trim());
		vo.setsNomineeState1((CommonFunction.checkNull(header1.get(59))).trim());
		vo.setsNomineePin1((CommonFunction.checkNull(header1.get(60))).trim());

		vo.setsPrefix2((CommonFunction.checkNull(header1.get(61))).trim());
		vo.setNomineeMName2((CommonFunction.checkNull(header1.get(62))).trim());
		vo.setNomineeLName2((CommonFunction.checkNull(header1.get(63))).trim());
		vo.setSmaritalStatus2((CommonFunction.checkNull(header1.get(64))).trim());
		vo.setsNomineeArea2((CommonFunction.checkNull(header1.get(65))).trim());
		vo.setsNomineeCity2((CommonFunction.checkNull(header1.get(66))).trim());
		vo.setsNomineeState2((CommonFunction.checkNull(header1.get(67))).trim());
		vo.setsNomineePin2((CommonFunction.checkNull(header1.get(68))).trim());
		
		vo.setsPrefix3((CommonFunction.checkNull(header1.get(69))).trim());
		vo.setNomineeMName3((CommonFunction.checkNull(header1.get(70))).trim());
		vo.setNomineeLName3((CommonFunction.checkNull(header1.get(71))).trim());
		vo.setSmaritalStatus3((CommonFunction.checkNull(header1.get(72))).trim());
		vo.setsNomineeArea3((CommonFunction.checkNull(header1.get(73))).trim());
		vo.setsNomineeCity3((CommonFunction.checkNull(header1.get(74))).trim());
		vo.setsNomineeState3((CommonFunction.checkNull(header1.get(75))).trim());
		vo.setsNomineePin3((CommonFunction.checkNull(header1.get(76))).trim());
		
		vo.setsPrefix4((CommonFunction.checkNull(header1.get(77))).trim());
		vo.setNomineeMName4((CommonFunction.checkNull(header1.get(78))).trim());
		vo.setNomineeLName4((CommonFunction.checkNull(header1.get(79))).trim());
		vo.setSmaritalStatus4((CommonFunction.checkNull(header1.get(80))).trim());
		vo.setsNomineeArea4((CommonFunction.checkNull(header1.get(81))).trim());
		vo.setsNomineeCity4((CommonFunction.checkNull(header1.get(82))).trim());
		vo.setsNomineeState4((CommonFunction.checkNull(header1.get(83))).trim());
		vo.setsNomineePin4((CommonFunction.checkNull(header1.get(84))).trim());
		vo.setSaddressType((CommonFunction.checkNull(header1.get(85))).trim());
		vo.setSinsuranceStreet((CommonFunction.checkNull(header1.get(86))).trim());
		
		vo.setSaddressType1((CommonFunction.checkNull(header1.get(87))).trim());
		vo.setSinsuranceStreet1((CommonFunction.checkNull(header1.get(88))).trim());
		vo.setSaddressType2((CommonFunction.checkNull(header1.get(89))).trim());
		vo.setSinsuranceStreet2((CommonFunction.checkNull(header1.get(90))).trim());
		vo.setSaddressType3((CommonFunction.checkNull(header1.get(91))).trim());
		vo.setSinsuranceStreet3((CommonFunction.checkNull(header1.get(92))).trim());
		vo.setSaddressType4((CommonFunction.checkNull(header1.get(93))).trim());
		vo.setSinsuranceStreet4((CommonFunction.checkNull(header1.get(94))).trim());
		
		vo.setTxtStateCode((CommonFunction.checkNull(header1.get(95))).trim());
		vo.setTxtStateCode1((CommonFunction.checkNull(header1.get(96))).trim());
		vo.setTxtStateCode2((CommonFunction.checkNull(header1.get(97))).trim());
		vo.setTxtStateCode3((CommonFunction.checkNull(header1.get(98))).trim());
		vo.setTxtStateCode4((CommonFunction.checkNull(header1.get(99))).trim());
		//Saurabh Ends
		list.add(vo);
		vo=null;
		}
	header1.clear();
	header1=null;
	}
view.clear();
view=null;

}
catch(Exception e)
{
e.printStackTrace();
}

		return list;
		
}

		@Override
		public String getCust(CmInsuranceVo vo, String loanId) {
			
			String cust=vo.getCustId();
			String custList="";
			String custListId="";
			
				try{
					 custList=" select replace(group_concat(gcd_customer_id),',','|') from cr_deal_customer_m where customer_id in ("+cust+") ";
					 custListId = ConnectionDAO.singleReturn(custList);
						logger.info("insuranceId:::"+custListId);
						
						
					}catch(Exception e){
						e.printStackTrace();
					}
					return custListId;
					
		}
		
		public String readString(InputStream in){
			InputStreamReader is = new InputStreamReader(in);
			StringBuilder sb=new StringBuilder();
			BufferedReader br = new BufferedReader(is);
			String read;
			try 
			{
				read = br.readLine();
				while(read != null) {
			    //System.out.println(read);
			    sb.append(read);
			    read =br.readLine();
			}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return sb.toString();
		}

		@Override
		public String checkDealSanctionLimit(String dealId, String loanId,
				String productId, String loanAmout) {
			logger.info("In checkDealSanctionLimit......");
			logger.info("DealId--LoanId---ProductId---Amount--->"+dealId+","+loanId+","+productId+","+loanAmout);
				String finalResult = "";
			try 
			{
				ArrayList<Object> in =new ArrayList<Object>();
				ArrayList<Object> out =new ArrayList<Object>();
				ArrayList outMessages = new ArrayList();
				String s1=null;
				String s2=null;
				in.add(Integer.parseInt(loanId));
				in.add(Integer.parseInt(dealId));
				in.add(productId);
				in.add(loanAmout.replace(",",""));
				out.add(s1);
				out.add(s2);  
										
				logger.info("Get_charges ("+in.toString()+","+out.toString());
				outMessages=(ArrayList) ConnectionDAO.callSP("CALCULATE_SANCTIONED_LIMIT_AMOUNT",in,out);
				s1=CommonFunction.checkNull(outMessages.get(0));
				s2=CommonFunction.checkNull(outMessages.get(1));
				logger.info("s1  : "+s1);
				logger.info("s2  : "+s2);
				finalResult = s1;
				in.clear();
				in=null;
				out.clear();
				out=null;
				outMessages.clear();
				outMessages=null;
				s1=null;
				s2=null;
			}
			catch (Exception e) 
			{e.printStackTrace();}
			
			return finalResult;
		}

}
