package com.cm.daoImplMYSQL;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import org.apache.commons.lang.StringEscapeUtils;

import com.cm.dao.ManualnpaMovementDAO;
import com.cm.vo.CancellationVO;
import com.cm.vo.ClosureAuthorVO;
import com.cm.vo.ClosureSearchVO;
import com.cm.vo.ClosureVO;
import com.cm.vo.LoanDetailForCMVO;
import com.cm.vo.ManualnpaMovementVO;
import com.cm.vo.PaymentMakerForCMVO;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.PrepStmtObject;
import com.cp.collateralVerification.vo.CollateralCapturingVO;
import com.cp.vo.HeaderInfoVo;
import com.cp.vo.RepayScheduleVo;
import com.masters.vo.MasterVo;

public class ManualnpaMovementDAOImpl implements ManualnpaMovementDAO{


	private static final Logger logger = Logger.getLogger(ManualnpaMovementDAOImpl.class.getName());
	java.util.ResourceBundle resource = java.util.ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
	String dateFormatWithTime=resource.getString("lbl.dateWithTimeInDao");
	String dateFormat=resource.getString("lbl.dateInDao");
	String dateForDisbursal=resource.getString("lbl.dateForDisbursal");
	DecimalFormat myFormatter = new DecimalFormat("###,##0.00######");
	int no=Integer.parseInt(resource.getString("msg.pageSizeForMaster"));



	public ArrayList<ManualnpaMovementVO> searchManualNpa(ManualnpaMovementVO vo) {
		ArrayList<ManualnpaMovementVO> searchDataList = new ArrayList<ManualnpaMovementVO>();

		logger.info("In searchmanualnpa .......-------------------->");

		ArrayList mainlist = new ArrayList();
		ArrayList subList = new ArrayList();
		StringBuilder bufInsSql=new StringBuilder();
		StringBuilder bufInsSqlTempCount = new StringBuilder();
		String countStr="";
		String userName="";

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

			bufInsSql.append("select CLD.LOAN_NO,CMN.loan_id,GDM.CUSTOMER_NAME,(select USER_NAME from sec_user_m where USER_ID=CMN.maker_id) as username,CMN.last_npa_stage,CMN.current_npa_stage,CMN.manual_npa_id " +
					"from cr_manual_npa_movement CMN,cr_loan_dtl CLD,gcd_customer_m GDM " +
					"where GDM.CUSTOMER_ID=CLD.LOAN_CUSTOMER_ID and CLD.LOAN_ID=CMN.loan_id AND CLD.LOAN_BRANCH='"+CommonFunction.checkNull(vo.getBranchId())+"' and CMN.rec_status='P'");
			bufInsSqlTempCount.append("select count(1) from cr_manual_npa_movement CMN,cr_loan_dtl CLD,gcd_customer_m GDM " +
					"where GDM.CUSTOMER_ID=CLD.LOAN_CUSTOMER_ID and CLD.LOAN_ID=CMN.loan_id AND CLD.LOAN_BRANCH='"+CommonFunction.checkNull(vo.getBranchId())+"' and CMN.rec_status='P'");
			if(!CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase("")) {
				bufInsSql.append(" AND CMN.maker_id='"+vo.getReportingToUserId()+"' ");
				bufInsSqlTempCount.append(" AND CMN.maker_id='"+vo.getReportingToUserId()+"' ");
			}else{
				bufInsSql.append(" AND CMN.maker_id='"+vo.getUserId()+"' ");
				bufInsSqlTempCount.append(" AND CMN.maker_id='"+vo.getUserId()+"' ");
			}
				 if(!CommonFunction.checkNull(vo.getLbxLoanNo()).equalsIgnoreCase("")) {
						bufInsSql.append(" AND CMN.loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull( vo.getLbxLoanNo()).trim())+ "' ");
						bufInsSqlTempCount.append(" AND CMN.loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull( vo.getLbxLoanNo()).trim())+ "' ");
					}

				  if(!CommonFunction.checkNull(vo.getSearchCName()).equalsIgnoreCase("")) {
						bufInsSql.append("  and GDM.CUSTOMER_NAME like '%"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSearchCName()).trim())+ "%'");
						bufInsSqlTempCount.append("  and GDM.CUSTOMER_NAME like '%"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSearchCName()).trim())+ "%' ");
					}

				 if (!(CommonFunction.checkNull(vo.getSearchCName()).equalsIgnoreCase(""))
							&& (!(CommonFunction.checkNull(vo.getLbxLoanNo()).equalsIgnoreCase("")))) {
				bufInsSql.append(" AND CMN.loan_id='"
						+StringEscapeUtils.escapeSql(CommonFunction.checkNull( vo.getLbxLoanNo()).trim())
						+ "' and GDM.CUSTOMER_NAME like '%"
						+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSearchCName()).trim())+ "%'");
				bufInsSqlTempCount.append(" AND CMN.loan_id='"
						+StringEscapeUtils.escapeSql(CommonFunction.checkNull( vo.getLbxLoanNo()).trim())
						+ "' and GDM.CUSTOMER_NAME like '%"
						+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSearchCName()).trim())+ "%'");
			}

			logger.info("In searchmanualnpa : " + bufInsSql.toString());
			mainlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			countStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
			logger.info("Count String: "+countStr);
			if(countStr.equalsIgnoreCase(""))
				count=0;
			else
				count = Integer.parseInt(countStr);

			// Logic for paging
			if((vo.getLbxLoanNo()==null && vo.getCustomerName()==null) || (vo.getLbxLoanNo().equalsIgnoreCase("") && vo.getSearchCName().equalsIgnoreCase(""))
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
			int size = mainlist.size();
			logger.info("In searchmanualnpa.....mainlist size: "+ size);
			for (int i = 0; i < size; i++) {

				subList = (ArrayList) mainlist.get(i);
				logger.info("In searchmanualnpa......sublist size: "
						+ subList.size());
				if (subList.size() > 0) {

					vo= new ManualnpaMovementVO();
					vo.setLoanNo("<a href=manualNpaMovementMakerAdd.do?method=editjsp&manualNpaId="
							+CommonFunction.checkNull(subList.get(6)).trim()+"&loanId="+(CommonFunction.checkNull(subList.get(1)).trim())+">"
			      					+ (CommonFunction.checkNull(subList.get(0)).trim())+"</a>");



					vo.setLbxLoanNo(CommonFunction.checkNull(subList.get(1)).trim());
					vo.setCustomerName(CommonFunction.checkNull(subList.get(2)).trim());
					vo.setUserId(CommonFunction.checkNull(subList.get(3)).trim());
					vo.setLastnpastage(CommonFunction.checkNull(subList.get(4)).trim());
					vo.setCurrnpastage(CommonFunction.checkNull(subList.get(5)).trim());
					vo.setManualnpaId(CommonFunction.checkNull(subList.get(6)).trim());
					vo.setTotalRecordSize(count);
					searchDataList.add(vo);
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
			vo= null;
		}

		return searchDataList;
	}
	public ArrayList<ManualnpaMovementVO> searchManualNpaAuthor(ManualnpaMovementVO vo) {
		ArrayList<ManualnpaMovementVO> searchDataList = new ArrayList<ManualnpaMovementVO>();

		logger.info("In searchManualNpaAuthor .......-------------------->");

		ArrayList mainlist = new ArrayList();
		ArrayList subList = new ArrayList();
		StringBuilder bufInsSql=new StringBuilder();
		StringBuilder bufInsSqlTempCount = new StringBuilder();
		String countStr="";
		String userName="";

		logger.info("here userid+++++++++searchManualNpaAuthor++++++++++++++++++ "+vo.getReportingToUserId());
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

			bufInsSql.append("select CLD.LOAN_NO,CMN.loan_id,GDM.CUSTOMER_NAME,(select USER_NAME from sec_user_m where USER_ID=CMN.maker_id) as username,CMN.last_npa_stage,CMN.current_npa_stage,CMN.manual_npa_id " +
					"from cr_manual_npa_movement CMN,cr_loan_dtl CLD,gcd_customer_m GDM " +
					"where GDM.CUSTOMER_ID=CLD.LOAN_CUSTOMER_ID and CLD.LOAN_ID=CMN.loan_id AND CLD.LOAN_BRANCH='"+CommonFunction.checkNull(vo.getBranchId())+"' and CMN.rec_status='F'");
			bufInsSqlTempCount.append("select count(1) from cr_manual_npa_movement CMN,cr_loan_dtl CLD,gcd_customer_m GDM " +
					"where GDM.CUSTOMER_ID=CLD.LOAN_CUSTOMER_ID and CLD.LOAN_ID=CMN.loan_id AND CLD.LOAN_BRANCH='"+CommonFunction.checkNull(vo.getBranchId())+"' and CMN.rec_status='F'");


			
				if(!(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(""))&&!(CommonFunction.checkNull(vo.getReportingToUserId()).equalsIgnoreCase(null))) {
					bufInsSql.append(" AND CMN.maker_id='"+vo.getReportingToUserId()+"' AND CMN.maker_id!='"+vo.getUserId()+"' ");
					bufInsSqlTempCount.append(" AND CMN.maker_id='"+vo.getReportingToUserId()+"' AND CMN.maker_id!='"+vo.getUserId()+"'  ");
				}else{
					bufInsSql.append(" AND CMN.maker_id!='"+vo.getUserId()+"' ");
					bufInsSqlTempCount.append(" AND CMN.maker_id!='"+vo.getUserId()+"' ");
				}

			 if(!CommonFunction.checkNull(vo.getLbxLoanNo()).equalsIgnoreCase("")) {
				bufInsSql.append(" AND CMN.loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull( vo.getLbxLoanNo()).trim())+ "' ");
				bufInsSqlTempCount.append(" AND CMN.loan_id='"+StringEscapeUtils.escapeSql(CommonFunction.checkNull( vo.getLbxLoanNo()).trim())+ "' ");
			}
			  if(!CommonFunction.checkNull(vo.getSearchCName()).equalsIgnoreCase("")) {
					bufInsSql.append("  and GDM.CUSTOMER_NAME like '%"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSearchCName()).trim())+ "%'");
					bufInsSqlTempCount.append("  and GDM.CUSTOMER_NAME like '%"+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSearchCName()).trim())+ "%' ");
				}



			if (!(CommonFunction.checkNull(vo.getSearchCName()).equalsIgnoreCase(""))
					&& (!(CommonFunction.checkNull(vo.getLbxLoanNo()).equalsIgnoreCase("")))) {
				bufInsSql.append(" AND CMN.loan_id='"
						+StringEscapeUtils.escapeSql(CommonFunction.checkNull( vo.getLbxLoanNo()).trim())
						+ "' and GDM.CUSTOMER_NAME like '%"
						+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSearchCName()).trim())+ "%'");
				bufInsSqlTempCount.append(" AND CMN.loan_id='"
						+StringEscapeUtils.escapeSql(CommonFunction.checkNull( vo.getLbxLoanNo()).trim())
						+ "' and GDM.CUSTOMER_NAME like '%"
						+ StringEscapeUtils.escapeSql(CommonFunction.checkNull(vo.getSearchCName()).trim())+ "%'");
			}

			logger.info("In searchManualNpaAuthor : " + bufInsSql.toString());
			mainlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			countStr = CommonFunction.checkNull(ConnectionDAO.singleReturn(bufInsSqlTempCount.toString()));
			logger.info("Count String: "+countStr);
			if(countStr.equalsIgnoreCase(""))
				count=0;
			else
				count = Integer.parseInt(countStr);

			// Logic for paging
			if((vo.getLbxLoanNo()==null && vo.getCustomerName()==null) || (vo.getLbxLoanNo().equalsIgnoreCase("") && vo.getSearchCName().equalsIgnoreCase(""))
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
			int size = mainlist.size();
			logger.info("In searchManualNpaAuthor.....mainlist size: "+ size);
			for (int i = 0; i < size; i++) {

				subList = (ArrayList) mainlist.get(i);
				logger.info("In searchManualNpaAuthor......sublist size: "
						+ subList.size());
				if (subList.size() > 0) {

					vo= new ManualnpaMovementVO();


					 vo.setLoanNo("<a href=manualNpaMovementMakerSearch.do?method=viewframe&manualNpaId="
			  					+ (CommonFunction.checkNull(subList.get(6)).trim()) +"&loanId="+(CommonFunction.checkNull(subList.get(1)).trim())+">"
			  					+ (CommonFunction.checkNull(subList.get(0)).trim()) + "</a>");
					vo.setLbxLoanNo(CommonFunction.checkNull(subList.get(1)).trim());
					vo.setCustomerName(CommonFunction.checkNull(subList.get(2)).trim());
					vo.setUserId(CommonFunction.checkNull(subList.get(3)).trim());
					vo.setLastnpastage(CommonFunction.checkNull(subList.get(4)).trim());
					vo.setCurrnpastage(CommonFunction.checkNull(subList.get(5)).trim());
					vo.setManualnpaId(CommonFunction.checkNull(subList.get(6)).trim());
					vo.setTotalRecordSize(count);
					searchDataList.add(vo);
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
			vo= null;
		}

		return searchDataList;
	}


	public ArrayList insertManualNPA(ManualnpaMovementVO vo)
	{
	logger.info("In insertManualNPA .......vo.getApprovalDate().....()-------->"+vo.getApprovalDate());

	boolean status = false;
	ArrayList queryList = new ArrayList();
	StringBuilder bufInsSql = new StringBuilder();
	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	String val="error";
	String result="save";
	String npaStageresult="";
	ArrayList list=new ArrayList();
	try {

			String query1="SELECT NPA_FLAG from  CR_LOAN_DTL where LOAN_ID='"+CommonFunction.checkNull(vo.getLbxLoanNo()).trim()+"'";
			String NpaStage=ConnectionDAO.singleReturn(query1);

//Ritu
			String query2="SELECT NPA_STAGE FROM CR_NPA_M WHERE SEQ_NO=(SELECT SEQ_NO FROM CR_NPA_M WHERE NPA_STAGE='"+NpaStage+"') AND NPA_STAGE!='SALEOFF' AND REC_STATUS='A' ORDER BY SEQ_NO ASC LIMIT 1";
			//String query3="SELECT NPA_STAGE FROM CR_NPA_M WHERE SEQ_NO=(SELECT SEQ_NO FROM CR_NPA_M WHERE NPA_STAGE='"+NpaStage+"' ) AND NPA_STAGE!='SALEOFF' AND REC_STATUS='A' ORDER BY SEQ_NO DESC LIMIT 1";
			
			logger.info("SELECT NPA_STAGE FROM CR_NPA_M WHERE SEQ_NO>(SELECT SEQ_NO FROM CR_NPA_M WHERE NPA_STAGE='"+NpaStage+"') AND NPA_STAGE!='SALEOFF' AND REC_STATUS='A' ORDER BY SEQ_NO ASC LIMIT 1");
			//logger.info("SELECT NPA_STAGE FROM CR_NPA_M WHERE SEQ_NO<(SELECT SEQ_NO FROM CR_NPA_M WHERE NPA_STAGE='"+NpaStage+"' ) AND NPA_STAGE!='SALEOFF' AND REC_STATUS='A' ORDER BY SEQ_NO DESC LIMIT 1");
			
			//String npaPerviousStage=ConnectionDAO.singleReturn(query3);
			String npaNextStage=ConnectionDAO.singleReturn(query2);
			
			//logger.info("npaPerviousStage........."+npaPerviousStage);
			logger.info("npaNextStage......."+npaNextStage);
			logger.info("vo.getCurrNpaStage()......."+vo.getCurrNpaStage());
			if(vo.getCurrNpaStage().equals(npaNextStage)){
				result="dontsave";
			}else{
				result="save";
			}
			logger.info("result........"+result);
			if(result.equalsIgnoreCase("save")){
		 bufInsSql.append("insert into cr_manual_npa_movement(loan_id,loan_amount,balance_principal,recieved_principal,overdue_principal,total_overdue_principal" +
		 		",emi_amount,dpd,dpd_string,last_npa_stage,current_npa_stage,approval_date,disbursal_date,disbursal_amt,inst_overdue_amt,rec_status,maker_id,maker_date)");

		 bufInsSql.append(" values ( ");
		 bufInsSql.append(" ?," );//loan_id
		 bufInsSql.append(" ?," );//loan_amount
		 bufInsSql.append(" ?," );//balance_principal
		 bufInsSql.append(" ?," );//recieved_principal
		 bufInsSql.append(" ?," );//overdue_principal
		 bufInsSql.append(" ?," );//total_overdue_principal
		 bufInsSql.append(" ?," );//emi_amount
		 bufInsSql.append(" ?," );//dpd
		 bufInsSql.append(" ?," );//dpd_string
		 bufInsSql.append(" ?," );//last_npa_stage
		 bufInsSql.append(" ?," );//current_npa_stage
		 bufInsSql.append(" STR_TO_DATE(?,'"+dateFormat+"')," ); //approval_date
		 bufInsSql.append("	STR_TO_DATE(?,'"+dateFormat+"') ," );//disbursal_date
		 bufInsSql.append(" ?," );//disbursal_amt
		 bufInsSql.append(" ?," );//inst_overdue_amt		
		 bufInsSql.append(" 'P'," );//rec_status
		 bufInsSql.append(" ?," );//maker_id
		 bufInsSql.append(" DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) )" );//maker_date

		if(CommonFunction.checkNull(vo.getLbxLoanNo()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getLbxLoanNo()).trim());

		if(CommonFunction.checkNull(vo.getLoanAmount()).equalsIgnoreCase(""))
			insertPrepStmtObject.addString("0.00");
		else
			insertPrepStmtObject.addString(myFormatter.parse(CommonFunction.checkNull(vo.getLoanAmount()).trim()).toString());

		logger.info("vo.getBalancePrincipal()--->"+vo.getBalancePrincipal());
		if(CommonFunction.checkNull(vo.getBalancePrincipal()).equalsIgnoreCase(""))
			insertPrepStmtObject.addString("0.00");
		else
			insertPrepStmtObject.addString(myFormatter.parse(CommonFunction.checkNull(vo.getBalancePrincipal()).trim()).toString());
		logger.info("getRecievedPrincipal()--->"+vo.getRecievedPrincipal());
		if(CommonFunction.checkNull(vo.getRecievedPrincipal()).equalsIgnoreCase(""))
			insertPrepStmtObject.addString("0.00");
		else
			insertPrepStmtObject.addString(myFormatter.parse(CommonFunction.checkNull(vo.getRecievedPrincipal()).trim()).toString());
		logger.info("getOverduePrincipal()--->"+vo.getOverduePrincipal());
		if(CommonFunction.checkNull(vo.getOverduePrincipal()).equalsIgnoreCase(""))
			insertPrepStmtObject.addString("0.00");
		else
			insertPrepStmtObject.addString(myFormatter.parse(CommonFunction.checkNull(vo.getOverduePrincipal()).trim()).toString());
		logger.info("getTotOverduePrinc()--->"+vo.getTotOverduePrinc());
		if(CommonFunction.checkNull(vo.getTotOverduePrinc()).equalsIgnoreCase(""))
			insertPrepStmtObject.addString("0.00");
		else
			insertPrepStmtObject.addString(myFormatter.parse(CommonFunction.checkNull(vo.getTotOverduePrinc()).trim()).toString());
		logger.info("getTotOverduePrinc()--->"+vo.getTotOverduePrinc());
		if(CommonFunction.checkNull(vo.getEmiAmount()).equalsIgnoreCase(""))
			insertPrepStmtObject.addString("0.00");
		else
			insertPrepStmtObject.addString(myFormatter.parse(CommonFunction.checkNull(vo.getEmiAmount()).trim()).toString());
		logger.info("getDpd()--->"+vo.getDpd());
		if(CommonFunction.checkNull(vo.getDpd()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getDpd()).trim());
		logger.info("getDpdString()--->"+vo.getDpdString());
		if(CommonFunction.checkNull(vo.getDpdString()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getDpdString()).trim());
		logger.info("getLastNpaStage()--->"+vo.getLastNpaStage());
		if(CommonFunction.checkNull(vo.getLastNpaStage()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getLastNpaStage()).trim());

		if(CommonFunction.checkNull(vo.getCurrNpaStage()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getCurrNpaStage()).trim());
		logger.info("getApprovalDate()--->"+vo.getApprovalDate());
		if(CommonFunction.checkNull(vo.getApprovalDate()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getApprovalDate()).trim());
		logger.info("getDisbursalDate()--->"+vo.getDisbursalDate());
		if(CommonFunction.checkNull(vo.getDisbursalDate()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getDisbursalDate()).trim());
		logger.info("getDisbursalAmt()--->"+vo.getDisbursalAmt());
		if(CommonFunction.checkNull(vo.getDisbursalAmt()).equalsIgnoreCase("")||CommonFunction.checkNull(vo.getDisbursalAmt()).equalsIgnoreCase("0"))
			insertPrepStmtObject.addString("0.00");
		else
			insertPrepStmtObject.addString(myFormatter.parse(CommonFunction.checkNull(vo.getDisbursalAmt()).trim()).toString());
		logger.info("getInstOverdue()--->"+vo.getInstOverdue());
		if(CommonFunction.checkNull(vo.getInstOverdue()).equalsIgnoreCase(""))
			insertPrepStmtObject.addString("0.00");
		else
			insertPrepStmtObject.addString(myFormatter.parse(CommonFunction.checkNull(vo.getInstOverdue()).trim()).toString());
		if(CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerId()).trim());

		if(CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());


		insertPrepStmtObject.setSql(bufInsSql.toString());
		logger.info("IN insertManualNPA() update query ### "+insertPrepStmtObject.printQuery());
		queryList.add(insertPrepStmtObject);
		status = ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
		
			if(status)
			{
				npaStageresult="saved";
			}
			else
			{
				npaStageresult="error";
			}

		}
			else
		{
			npaStageresult="You cannot choose "+CommonFunction.checkNull(vo.getCurrNpaStage())+" as your current stage as your previous stage was "+CommonFunction.checkNull(NpaStage)+"";
		}	
		if(status)
		{
			String query="select max(manual_npa_id) id from cr_manual_npa_movement for update";
			val=ConnectionDAO.singleReturn(query);

		}
		else
		{
			val="error";
		}
		list.add(npaStageresult);
		list.add(val);	
		list.add(vo);	
		logger.info("In insertManualNPA.........update status: "+ status);
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		vo = null;
		queryList = null;
		bufInsSql = null;
		insertPrepStmtObject = null;
	}
	
	logger.info("size of list"+list.size());
	return list;
}
	public ArrayList<ManualnpaMovementVO> selectManualNpa(String id,String loanId)
	{
	logger.info("In selectManualNpa ............()------------>");

	ArrayList mainlist = new ArrayList();
	ArrayList subList = new ArrayList();
	ArrayList dataList = new ArrayList();
	StringBuilder bufInsSql = new StringBuilder();

	String val="";
	ManualnpaMovementVO vo=null;
	try {

		
		 bufInsSql.append("select a.loan_id,loan_amount,a.balance_principal,a.recieved_principal,a.overdue_principal,a.total_overdue_principal,a.emi_amount,a.dpd, " +
		"a.dpd_string,a.last_npa_stage,a.current_npa_stage,b.LOAN_NO,g.CUSTOMER_NAME,(select p.PRODUCT_DESC from cr_product_m p  where p.PRODUCT_ID=b.LOAN_PRODUCT) as product," +
		"(select s.SCHEME_DESC from cr_scheme_m s  where s.SCHEME_ID=b.LOAN_SCHEME) as scheme" +
		",DATE_FORMAT(a.approval_date,'"+dateFormat+"') approvalDate,DATE_FORMAT(a.disbursal_date,'"+dateFormat+"') disbursalDate,a.disbursal_amt,a.inst_overdue_amt,comments from cr_manual_npa_movement a,cr_loan_dtl b,gcd_customer_m g " +
		" where manual_npa_id='"+CommonFunction.checkNull(id)+"' and b.loan_id='"+CommonFunction.checkNull(loanId)+"' and g.CUSTOMER_ID=b.LOAN_CUSTOMER_ID ");

		 logger.info("In selectManualNpa : " + bufInsSql);
			mainlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			int size = mainlist.size();
			logger.info("In selectManualNpa.....mainlist size: "+ size);
			for (int i = 0; i < size; i++) {
				subList = (ArrayList) mainlist.get(i);
				if (subList.size() > 0) {
					vo = new ManualnpaMovementVO();
					vo.setLbxLoanNo(CommonFunction.checkNull(subList.get(0)).trim());
					if((CommonFunction.checkNull(subList.get(1)).trim()).equalsIgnoreCase("")){
						vo.setLoanAmount("0.00");
					}
					else{
						Number loanamt = myFormatter.parse(CommonFunction.checkNull(subList.get(1)).trim());
						vo.setLoanAmount(myFormatter.format(loanamt));
					}
					if((CommonFunction.checkNull(subList.get(2)).trim()).equalsIgnoreCase(""))
						vo.setBalancePrincipal("0.00");
					else
					{
						Number balPrincipal = myFormatter.parse((CommonFunction.checkNull(subList.get(2)).trim()));
						vo.setBalancePrincipal(myFormatter.format(balPrincipal));
					}

					if(CommonFunction.checkNull(CommonFunction.checkNull(subList.get(3)).trim()).equalsIgnoreCase(""))
						vo.setRecievedPrincipal("0.00");
					else{
						Number recievedPrincipal = myFormatter.parse(CommonFunction.checkNull(subList.get(3)).trim());
						vo.setRecievedPrincipal(myFormatter.format(recievedPrincipal));
						}
					if(CommonFunction.checkNull(CommonFunction.checkNull(subList.get(4)).trim()).equalsIgnoreCase(""))
						vo.setOverduePrincipal("0.00");
					else{
						Number OverDuePrinc = myFormatter.parse(CommonFunction.checkNull(subList.get(4)).trim());
					vo.setOverduePrincipal(myFormatter.format(OverDuePrinc));
					}
					if(CommonFunction.checkNull(CommonFunction.checkNull(subList.get(5)).trim()).equalsIgnoreCase(""))
						vo.setTotOverduePrinc("0.00");
					else{
						Number totoverdueprinc = myFormatter.parse(CommonFunction.checkNull(subList.get(5)).trim());
					vo.setTotOverduePrinc(myFormatter.format(totoverdueprinc));
					}
					if(CommonFunction.checkNull(CommonFunction.checkNull(subList.get(6)).trim()).equalsIgnoreCase(""))
						vo.setEmiAmount("0.00");
					else{
						Number emiamt = myFormatter.parse(CommonFunction.checkNull(subList.get(6)).trim());
					vo.setEmiAmount(myFormatter.format(emiamt));
					}
					vo.setDpd((CommonFunction.checkNull(subList.get(7)).trim()).toString());

					vo.setDpdString((CommonFunction.checkNull(subList.get(8)).trim()).toString());

					vo.setLastNpaStage((CommonFunction.checkNull(subList.get(9)).trim()).toString());

					vo.setCurrNpaStage((CommonFunction.checkNull(subList.get(10)).trim()).toString());
					vo.setLoanNo((CommonFunction.checkNull(subList.get(11)).trim()).toString());
					vo.setCustomerName((CommonFunction.checkNull(subList.get(12)).trim()).toString());
					vo.setProduct((CommonFunction.checkNull(subList.get(13)).trim()).toString());
					vo.setScheme((CommonFunction.checkNull(subList.get(14)).trim()).toString());
					vo.setApprovalDate((CommonFunction.checkNull(subList.get(15)).trim()).toString());
					vo.setDisbursalDate((CommonFunction.checkNull(subList.get(16)).trim()).toString());
					if(CommonFunction.checkNull(CommonFunction.checkNull(subList.get(17)).trim()).equalsIgnoreCase(""))
						vo.setDisbursalAmt("0.00");
					else{
						Number disbursalAmt = myFormatter.parse(CommonFunction.checkNull(subList.get(17)).trim());
					vo.setDisbursalAmt(myFormatter.format(disbursalAmt));
					}
					if(CommonFunction.checkNull(CommonFunction.checkNull(subList.get(18)).trim()).equalsIgnoreCase(""))
						vo.setInstOverdue("0.00");
					else{
						Number instOverdue = myFormatter.parse(CommonFunction.checkNull(subList.get(18)).trim());
					vo.setInstOverdue(myFormatter.format(instOverdue));
					}
					vo.setAuthorRemarks((CommonFunction.checkNull(subList.get(19)).trim()).toString());
					dataList.add(vo);
				}
			}



	}catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		vo = null;
		bufInsSql = null;
		mainlist = null;
		subList = null;

	}
	return dataList;
}
	public ArrayList<ManualnpaMovementVO> ManualNpaGetDetails(String id )
	{
	logger.info("In ManualNpaGetDetails ............()------------>");

	ArrayList mainlist = new ArrayList();
	ArrayList subList = new ArrayList();
	StringBuilder bufInsSql = new StringBuilder();
	ArrayList dataList = new ArrayList();
	String val="";
	ManualnpaMovementVO vo=null;
	try {

		 bufInsSql.append("select a.loan_id,a.LOAN_LOAN_AMOUNT,a.LOAN_BALANCE_PRINCIPAL,a.LOAN_RECEIVED_PRINCIPAL,a.LOAN_OVERDUE_PRINCIPAL,ifnull(a.LOAN_OVERDUE_AMOUNT + a.LOAN_BALANCE_PRINCIPAL,0)as totaloverdue," +
		 		"a.LOAN_EMI_AMOUNT,a.LOAN_DPD,a.LOAN_DPD_STRING,a.NPA_FLAG,a.LOAN_NO,DATE_FORMAT(a.LOAN_APPROVAL_DATE,'"+dateFormat+"') approvalDate," +
		 		"(select SUM(b.DISBURSAL_AMOUNT) from cr_loan_disbursal_dtl b where b.LOAN_ID=a.loan_id) as DISBURSAL_AMOUNT" +
		 		",(select MAX(DATE_FORMAT(b.DISBURSAL_DATE,'"+dateFormat+"'))  from cr_loan_disbursal_dtl b where b.LOAN_ID=a.loan_id) as disbursalDate,a.LOAN_OVERDUE_AMOUNT"+
		 		" from cr_loan_dtl a where a.loan_id='"+CommonFunction.checkNull(id)+"' ");


		 logger.info("In ManualNpaGetDetails : " + bufInsSql);
			mainlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			int size = mainlist.size();
			logger.info("In ManualNpaGetDetails.....mainlist size: "+ size);
			for (int i = 0; i < size; i++) {
				subList = (ArrayList) mainlist.get(i);
				if (subList.size() > 0) {
					vo = new ManualnpaMovementVO();
					vo.setLbxLoanNo(CommonFunction.checkNull(subList.get(0)).trim());
					if((CommonFunction.checkNull(subList.get(1)).trim()).equalsIgnoreCase("")){
						vo.setLoanAmount("0.00");
					}
					else{
						Number loanamt = myFormatter.parse(CommonFunction.checkNull(subList.get(1)).trim());
						vo.setLoanAmount(myFormatter.format(loanamt));
					}
					if((CommonFunction.checkNull(subList.get(2)).trim()).equalsIgnoreCase(""))
						vo.setBalancePrincipal("0.00");
					else
					{
						Number balPrincipal = myFormatter.parse((CommonFunction.checkNull(subList.get(2)).trim()));
						vo.setBalancePrincipal(myFormatter.format(balPrincipal));
					}

					if(CommonFunction.checkNull(CommonFunction.checkNull(subList.get(3)).trim()).equalsIgnoreCase(""))
						vo.setRecievedPrincipal("0.00");
					else{
						Number recievedPrincipal = myFormatter.parse(CommonFunction.checkNull(subList.get(3)).trim());
						vo.setRecievedPrincipal(myFormatter.format(recievedPrincipal));
						}
					if(CommonFunction.checkNull(CommonFunction.checkNull(subList.get(4)).trim()).equalsIgnoreCase(""))
						vo.setOverduePrincipal("0.00");
					else{
						Number OverDuePrinc = myFormatter.parse(CommonFunction.checkNull(subList.get(4)).trim());
					vo.setOverduePrincipal(myFormatter.format(OverDuePrinc));
					}
					if(CommonFunction.checkNull(CommonFunction.checkNull(subList.get(5)).trim()).equalsIgnoreCase(""))
						vo.setTotOverduePrinc("0.00");
					else{
						Number totoverdueprinc = myFormatter.parse(CommonFunction.checkNull(subList.get(5)).trim());
					vo.setTotOverduePrinc(myFormatter.format(totoverdueprinc));
					}
					if(CommonFunction.checkNull(CommonFunction.checkNull(subList.get(6)).trim()).equalsIgnoreCase(""))
						vo.setEmiAmount("0.00");
					else{
						Number emiamt = myFormatter.parse(CommonFunction.checkNull(subList.get(6)).trim());
					vo.setEmiAmount(myFormatter.format(emiamt));
					}
					vo.setDpd((CommonFunction.checkNull(subList.get(7)).trim()).toString());

					vo.setDpdString((CommonFunction.checkNull(subList.get(8)).trim()).toString());

					vo.setLastNpaStage((CommonFunction.checkNull(subList.get(9)).trim()).toString());
					vo.setLoanNo((CommonFunction.checkNull(subList.get(10)).trim()).toString());
					vo.setApprovalDate((CommonFunction.checkNull(subList.get(11)).trim()).toString());
					if((CommonFunction.checkNull(subList.get(12)).trim()).equalsIgnoreCase(""))
						vo.setDisbursalAmt("0.00");
					else
					{
						Number disbursalAmt = myFormatter.parse((CommonFunction.checkNull(subList.get(12)).trim()));
						vo.setDisbursalAmt(myFormatter.format(disbursalAmt));
					}
					
					vo.setDisbursalDate((CommonFunction.checkNull(subList.get(13)).trim()).toString());
					if((CommonFunction.checkNull(subList.get(14)).trim()).equalsIgnoreCase(""))
						vo.setInstOverdue("0.00");
					else
					{
						Number InstOverdue = myFormatter.parse((CommonFunction.checkNull(subList.get(14)).trim()));
						vo.setInstOverdue(myFormatter.format(InstOverdue));
					}
					
					dataList.add(vo);
				}
			}



	}catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		vo = null;
		bufInsSql = null;
		mainlist = null;
		subList = null;

	}
	return dataList;
}
	public ArrayList<ManualnpaMovementVO> detailsForLoan(String id )
	{
	logger.info("In detailsForLoan ............()------------>");

	ArrayList mainlist = new ArrayList();
	ArrayList subList = new ArrayList();
	StringBuilder bufInsSql = new StringBuilder();
	ArrayList dataList = new ArrayList();
	String val="";
	ManualnpaMovementVO vo=null;
	try {

		 bufInsSql.append("select (select SCHEME_DESC  from cr_scheme_m where SCHEME_ID=a.LOAN_SCHEME)as scheme," +
		 		"(select PRODUCT_DESC from cr_product_m where PRODUCT_ID=a.LOAN_PRODUCT) as product,a.LOAN_ID,a.LOAN_NO,g.CUSTOMER_NAME  from cr_loan_dtl a,gcd_customer_m g"+
		 			" where a.loan_id='"+CommonFunction.checkNull(id)+"' and a.LOAN_CUSTOMER_ID=g.CUSTOMER_ID ");


		 logger.info("In detailsForLoan : " + bufInsSql);
			mainlist = ConnectionDAO.sqlSelect(bufInsSql.toString());
			int size = mainlist.size();
			logger.info("In detailsForLoan.....mainlist size: "+ size);
			for (int i = 0; i < size; i++) {
				subList = (ArrayList) mainlist.get(i);
				if (subList.size() > 0) {
					vo = new ManualnpaMovementVO();
				
					vo.setScheme(CommonFunction.checkNull(subList.get(0)).trim());
					vo.setProduct(CommonFunction.checkNull(subList.get(1)).trim());
					vo.setLbxLoanNo(CommonFunction.checkNull(subList.get(2)).trim());
					vo.setLoanNo(CommonFunction.checkNull(subList.get(3)).trim());
					vo.setCustomerName(CommonFunction.checkNull(subList.get(4)).trim());
					dataList.add(vo);
				}
			}



	}catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		vo = null;
		bufInsSql = null;
		mainlist = null;
		subList = null;

	}
	return dataList;
}
	public boolean forwardManualNPA(ManualnpaMovementVO vo)
	{
	logger.info("In forwardManualNPA ............()------------>");

	boolean status = false;
	ArrayList queryList = new ArrayList();
	StringBuilder bufInsSql = new StringBuilder();
	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	String val="error";

	try {

		 bufInsSql.append("update cr_manual_npa_movement set rec_status='F',maker_id=?,maker_date=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) where manual_npa_id=?");


		 if(CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerId()).trim());

			if(CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
				insertPrepStmtObject.addNull();
			else
				insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());

		if(CommonFunction.checkNull(vo.getManualnpaId()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getManualnpaId()).trim());

		insertPrepStmtObject.setSql(bufInsSql.toString());
		logger.info("IN forwardManualNPA() update query ### "+insertPrepStmtObject.printQuery());
		queryList.add(insertPrepStmtObject);
		status = ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);

		logger.info("In forwardManualNPA.........update status: "+ status);
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		vo = null;
		queryList = null;
		bufInsSql = null;
		insertPrepStmtObject = null;
	}
	return status;
	}


	public String updateManualNPA(ManualnpaMovementVO vo ) {
		logger.info("::::::::::::::::In updateManualNPA()------------>");

	boolean status = false;
	ArrayList queryList = new ArrayList();
	StringBuilder query = new StringBuilder();
	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	String result="save";
	String npaStageresult="";
	try {
		logger.info(" updateManualNPA;;;;;;;;");
		String query1="select LAST_NPA_STAGE from  cr_manual_npa_movement where manual_npa_id='"+CommonFunction.checkNull(vo.getManualnpaId())+"'";
		String NpaStage=ConnectionDAO.singleReturn(query1);
		
		//Ritu
		String query2="SELECT NPA_STAGE FROM CR_NPA_M WHERE SEQ_NO=(SELECT SEQ_NO FROM CR_NPA_M WHERE NPA_STAGE='"+NpaStage+"') AND NPA_STAGE!='SALEOFF' AND REC_STATUS='A' ORDER BY SEQ_NO ASC LIMIT 1";
		//String query3="SELECT NPA_STAGE FROM CR_NPA_M WHERE SEQ_NO=(SELECT SEQ_NO FROM CR_NPA_M WHERE NPA_STAGE='"+NpaStage+"' ) AND NPA_STAGE!='SALEOFF' AND REC_STATUS='A' ORDER BY SEQ_NO DESC LIMIT 1";
		
		logger.info("SELECT NPA_STAGE FROM CR_NPA_M WHERE SEQ_NO>(SELECT SEQ_NO FROM CR_NPA_M WHERE NPA_STAGE='"+NpaStage+"') AND NPA_STAGE!='SALEOFF' AND REC_STATUS='A' ORDER BY SEQ_NO ASC LIMIT 1");
		//logger.info("SELECT NPA_STAGE FROM CR_NPA_M WHERE SEQ_NO<(SELECT SEQ_NO FROM CR_NPA_M WHERE NPA_STAGE='"+NpaStage+"' ) AND NPA_STAGE!='SALEOFF' AND REC_STATUS='A' ORDER BY SEQ_NO DESC LIMIT 1");
		
		//String npaPerviousStage=ConnectionDAO.singleReturn(query3);
		String npaNextStage=ConnectionDAO.singleReturn(query2);
		
		//logger.info("npaPerviousStage........."+npaPerviousStage);
		logger.info("npaNextStage......."+npaNextStage);
		logger.info("vo.getCurrNpaStage()......."+vo.getCurrNpaStage());
		if(vo.getCurrNpaStage().equals(npaNextStage)){
			result="dontsave";
		}else{
			result="save";
		}
		
//		if(NpaStage.equalsIgnoreCase("REGULAR"))
//		{
//			if(vo.getCurrNpaStage().equalsIgnoreCase("SUBSTANDARD")){
//				result="save";
//			}else{
//				result="dontsave";
//			}
//			
//		}else if(NpaStage.equalsIgnoreCase("SUBSTANDARD")){
//			if(vo.getCurrNpaStage().equalsIgnoreCase("REGULAR")){
//				result="save";
//			}else{
//				result="dontsave";
//			}
//		}
//		else if(NpaStage.equalsIgnoreCase("DOUBTFULL")){
//			if(vo.getCurrNpaStage().equalsIgnoreCase("NPA")){
//				result="save";
//			}else{
//				result="dontsave";
//			}
//		}
//		else if(NpaStage.equalsIgnoreCase("NPA")){
//			if(vo.getCurrNpaStage().equalsIgnoreCase("DOUBTFULL")){
//				result="save";
//			}else{
//				result="dontsave";
//			}
//		}
//		else if(NpaStage.equalsIgnoreCase("DOUBTFULL")){
//			if(vo.getCurrNpaStage().equalsIgnoreCase("SUBSTANDARD")){
//				result="save";
//			}else{
//				result="dontsave";
//			}
//		}
//		if(NpaStage.equalsIgnoreCase("SUBSTANDARD"))
//		{
//			if(vo.getCurrNpaStage().equalsIgnoreCase("DOUBTFULL")){
//				result="save";
//			}else{
//				result="dontsave";
//			}
//			
//		}
		
	if(result.equalsIgnoreCase("save")){
		query.append("update cr_manual_npa_movement set current_npa_stage=?,maker_id=?,maker_date=DATE_ADD(STR_TO_DATE(?, '"+dateFormatWithTime+"'),INTERVAL CURTIME() HOUR_SECOND) where manual_npa_id='"+CommonFunction.checkNull(vo.getManualnpaId())+"'");

		if(CommonFunction.checkNull(vo.getCurrNpaStage()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getCurrNpaStage()).trim());



		if(CommonFunction.checkNull(vo.getMakerId()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerId()).trim());

		if(CommonFunction.checkNull(vo.getMakerDate()).equalsIgnoreCase(""))
			insertPrepStmtObject.addNull();
		else
			insertPrepStmtObject.addString(CommonFunction.checkNull(vo.getMakerDate()).trim());
		insertPrepStmtObject.setSql(query.toString());
		logger.info("IN updateManualNPA() update query ### "+insertPrepStmtObject.printQuery());
		queryList.add(insertPrepStmtObject);
		status = ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
		logger.info("In updateManualNPA.........update status: "+ status);
		if(status){
			npaStageresult="saved";
		}else{
			npaStageresult="notsaved";
		}

	}else{
		npaStageresult="You cannot choose "+CommonFunction.checkNull(vo.getCurrNpaStage())+" as your current stage as your previous stage was "+CommonFunction.checkNull(NpaStage)+"";
	}
	logger.info("npaStageresult------------>"+npaStageresult);
	}
	catch (Exception e) {
		e.printStackTrace();
	}
	finally
	{
		vo = null;
		queryList = null;
		query = null;
		insertPrepStmtObject = null;
	}
	return npaStageresult;
}
	public boolean deleteManualNPA(ManualnpaMovementVO vo ) {
		logger.info("::::::::::::::::In deleteManualNPA()------------>");

	boolean status = false;
	ArrayList queryList = new ArrayList();
	StringBuilder query = new StringBuilder();
	PrepStmtObject insertPrepStmtObject = new PrepStmtObject();
	String result="";
	String npaStageresult="";
	try {
		logger.info(" deleteManualNPA;;;;;;;;");
		
		query.append("delete from cr_manual_npa_movement  where manual_npa_id='"+CommonFunction.checkNull(vo.getManualnpaId())+"'");

		
		insertPrepStmtObject.setSql(query.toString());
		logger.info("IN deleteManualNPA() ### "+insertPrepStmtObject.printQuery());
		queryList.add(insertPrepStmtObject);
		status = ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList);
		logger.info("In deleteManualNPA........ status: "+ status);
		

	
	}
	catch (Exception e) {
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
	public String saveManualNpaAuthor(ManualnpaMovementVO vo){
	logger.info("In saveManualNpaAuthor..............................");
	String terminationId="";
	ArrayList<Object> in =new ArrayList<Object>();
	ArrayList<Object> out =new ArrayList<Object>();
	ArrayList outMessages = new ArrayList();
String value="";

	String s1="";
    String s2 ="";


	try {

		logger.info("IN PROC MANUAL_NPA_MOVEMENT_AUTHOR----------------------");
		in.add(CommonFunction.checkNull(vo.getCompanyId()).trim());//
		in.add(CommonFunction.checkNull(vo.getComments()).trim());//
		 in.add(CommonFunction.checkNull(vo.getDecision()).trim());//
		 in.add(CommonFunction.checkNull(vo.getAuthorId()).trim());//
		 String date=CommonFunction.changeFormat(CommonFunction.checkNull(vo.getAuthorDate()).trim());
   	    if(date != null)

  	    	in.add(date);

		 in.add(CommonFunction.checkNull(vo.getManualnpaId()).trim());//
		 in.add(CommonFunction.checkNull(vo.getLbxLoanNo()).trim());//
		 in.add(CommonFunction.checkNull(vo.getCurrNpaStage()).trim());//

		out.add(s1);
 	    out.add(s2);

 	   outMessages=(ArrayList) ConnectionDAO.callSP("MANUAL_NPA_MOVEMENT_AUTHOR",in,out);
	    s1=(CommonFunction.checkNull(outMessages.get(0)));
	    s2=(CommonFunction.checkNull(outMessages.get(1)));

        logger.info("s1: "+s1);
        logger.info("s2: "+s2);

       if(s1.equalsIgnoreCase("E")){
    	   value=s2	;
        }else{
        	value=s1;
        }
       logger.info("value: "+value);

	}
	catch (Exception e) {

		e.printStackTrace();
	}
	finally
	{

		s1= null;
        s2 = null;

	}
	return value;
}
//RITU CODE START HERE
	public ArrayList getNpaStage() {
		ArrayList list = new ArrayList();
		try {
			logger.info("In getNpaStage..........................DAOImpl");
			String query = "SELECT NPA_STAGE FROM CR_NPA_M WHERE REC_STATUS='A' AND NPA_STAGE!='SALEOFF' ORDER BY SEQ_NO";
			ManualnpaMovementVO vo = null;
			ArrayList npaStage = ConnectionDAO.sqlSelect(query);
			logger.info("getNpaStage " + npaStage.size());
			for (int i = 0; i < npaStage.size(); i++) {
				logger.info("getNpaStage "
						+ CommonFunction.checkNull(npaStage.get(i)).toString());
				ArrayList data = (ArrayList) npaStage.get(i);
				for (int k = 0; k < data.size(); k++) {
					logger.info("getNpaStage "
							+ CommonFunction.checkNull(data.get(k)).toString());
					vo = new ManualnpaMovementVO();
					vo.setStage(((String) data.get(0).toString()));
				}
				list.add(vo);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}	
	
	}