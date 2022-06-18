package com.cp.actions;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.cp.dao.UnderwritterDao;
import com.cp.vo.UnderwriterApprovalVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

public class UnderwritingApprovalBehind extends Action
{
  private static final Logger logger = Logger.getLogger(UnderwritingApprovalBehind.class.getName());

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    logger.info("In UnderwritingApprovalBehind(execute) ");
    ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
    String dateFormat = resource.getString("lbl.dateInDao");
    String dbType = resource.getString("lbl.dbType");
    String dbo = resource.getString("lbl.dbPrefix");
    HttpSession session = request.getSession();

    UserObject userobj = (UserObject)session.getAttribute("userobject");
    String userId = null;

    if (userobj != null)
    {
      userId = userobj.getUserId();
    } else {
    	 logger.info("here in execute method of UnderwritingApprovalBehind action the session is out----------------");
      return mapping.findForward("sessionOut");
    }

    Object sessionId = session.getAttribute("sessionID");

    ServletContext context = getServlet().getServletContext();
    String strFlag = "";
    if (sessionId != null)
    {
      strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId.toString(), "", request);
    }

    logger.info("strFlag .............. " + strFlag);
    if (!strFlag.equalsIgnoreCase(""))
    {
      if (strFlag.equalsIgnoreCase("sameUserSession"))
      {
        context.removeAttribute("msg");
        context.removeAttribute("msg1");
      }
      else if (strFlag.equalsIgnoreCase("BODCheck"))
      {
        context.setAttribute("msg", "B");
      }
      return mapping.findForward("logout");
    }
    String funId = CommonFunction.checkNull(session.getAttribute("functionId")).trim();

    String maker = CommonFunction.checkNull(request.getParameter("maker"));
    if (CommonFunction.checkNull(maker).equalsIgnoreCase("M"))
    {
      String dealId = null;

      if (session.getAttribute("dealId") != null)
      {
        dealId = session.getAttribute("dealId").toString();
      } else if (session.getAttribute("maxId") != null) {
        dealId = session.getAttribute("maxId").toString();
      }

      logger.info("In UnderwritingApprovalBehind(chargeInDeal) dealId: " + dealId);

      CreditProcessingDAO service = (CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance("CP");
      logger.info("Implementation class: " + service.getClass());

      ArrayList creditApprovalList = service.getMakerData(dealId);
      ArrayList workFlowStage = service.getworkFlowStage(dealId, funId);
      request.setAttribute("workFlowStage", workFlowStage);
      request.setAttribute("maker", "M");
      request.setAttribute("creditApprovalList", creditApprovalList);
      form.reset(mapping, request);
      service = null;
      maker = null;
      funId = null;
      return mapping.findForward("success");
    }

    String role = session.getAttribute("roleID").toString();
    CreditProcessingDAO service = (CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance("CP");
    logger.info("Implementation class: " + service.getClass());
    String dealId = null;

    if (session.getAttribute("dealId") != null)
    {
      dealId = session.getAttribute("dealId").toString();
    } else if (session.getAttribute("maxId") != null) {
      dealId = session.getAttribute("maxId").toString();
    }
    logger.info("In UnderwritingApprovalBehind(execute) dealId : " + 
    		 dealId);
    if (!CommonFunction.checkNull(dealId).trim().equalsIgnoreCase(""))
    {
    	String checkFCQ = "SELECT count(*) FROM cr_deal_verification_dtl where rec_status='C' and verification_action='I' and verification_type='F' and deal_id='" + dealId.trim() + "'";
      String checkFCCount = ConnectionDAO.singleReturn(checkFCQ);
      logger.info("checkFCQ: " + checkFCQ + " checkFCCount: " + checkFCCount);
      request.setAttribute("checkContactCCount", checkFCCount);
      checkFCQ = null;

      String checkTCQ = "SELECT count(*) FROM cr_deal_verification_dtl where rec_status='C' and verification_action='I' and verification_type='T' and deal_id='" + dealId.trim() + "'";
      String checkTCCount = ConnectionDAO.singleReturn(checkTCQ);
      logger.info("checkTCQ: " + checkTCQ + " checkTCCount: " + checkTCCount);
      request.setAttribute("checkTradeCCount", checkTCCount);
      checkTCQ = null;

      String checkCCQ = "SELECT count(*) FROM cr_deal_verification_dtl where rec_status='C' and verification_action='I' and verification_type='C' and deal_id='" + dealId.trim() + "'";
      String checkCCCount = ConnectionDAO.singleReturn(checkCCQ);
      logger.info("checkCCQ: " + checkCCQ + " checkCCCount: " + checkCCCount);
      request.setAttribute("checkCollateralCCount", checkCCCount);
      checkCCQ = null;

      String checkDeviation = "select count(POLICY_DECISION_ID)  from cr_policy_decision where DEAL_ID ='" + dealId.trim() + "'";
      String checkDeviationCount = ConnectionDAO.singleReturn(checkDeviation);
      logger.info("checkDeviation: " + checkDeviation + " checkDeviationCount: " + checkDeviationCount);
      request.setAttribute("checkDeviationCount", checkDeviationCount);
      checkDeviation = null;

      String leadQuery="select a.rec_status from cr_lead_dtl a join cr_deal_dtl d on d.lead_id=a.lead_id where d.deal_id='"+dealId.trim()+"'";
      String leadStatus=ConnectionDAO.singleReturn(leadQuery);
      request.setAttribute("leadStatus", leadStatus);
      leadQuery = null;
      
      StringBuilder checkGroupExposureQuery = new StringBuilder();
      if (dbType.equalsIgnoreCase("MSSQL"))
      {
    	  checkGroupExposureQuery.append("select ISNULL(CUSTOMER_GROUP_EXPOSURE_LIMIT,'0.00')  from cr_deal_customer_m a join cr_deal_customer_role b on CUSTOMER_ID=DEAL_CUSTOMER_ID  AND DEAL_ID='" + dealId.trim() + "'");
      }
      else
      {
    	  checkGroupExposureQuery.append("select IFNULL(CUSTOMER_GROUP_EXPOSURE_LIMIT,'0.00')  from cr_deal_customer_m a join cr_deal_customer_role b on CUSTOMER_ID=DEAL_CUSTOMER_ID  AND DEAL_ID='" + dealId.trim() + "'");
      }
      StringBuilder checkGroup = new StringBuilder();
      checkGroup.append("select customer_group_desc from cr_deal_customer_m a join cr_deal_customer_role b on CUSTOMER_ID=DEAL_CUSTOMER_ID  AND DEAL_ID='" + dealId.trim() + "' and customer_type='I' and customer_group_type='N' and customer_group_desc is null");
      String chkGrp = CommonFunction.checkNull(ConnectionDAO.singleReturn(checkGroup.toString()));

      logger.info("check Group Exposure Query : " + checkGroupExposureQuery.toString());
      ArrayList checkGroupExposureList = ConnectionDAO.sqlSelect(checkGroupExposureQuery.toString());
      checkGroupExposureQuery = null;
      int size = checkGroupExposureList.size();
      String expoLimit = null;
      for (int i = 0; i < size; i++)
      {
        ArrayList data = (ArrayList)checkGroupExposureList.get(i);
        if (data.size() > 0)
        {
          String checkGroupExposure = data.get(0).toString();
          logger.info("checkGroupExposure Limit: " + checkGroupExposure);

          if (!CommonFunction.checkNull(chkGrp).equalsIgnoreCase(""))
          {
            if (!CommonFunction.checkNull(checkGroupExposure).equalsIgnoreCase(""))
            {
              Double exp = Double.valueOf(Double.parseDouble(checkGroupExposure));
              if (exp.doubleValue() == 0.0D)
              {
                expoLimit = "EXPLMT";
                break;
              }

            }
            else
            {
              expoLimit = "EXPLMT";
              break;
            }
          }
        }
        data.clear();
        data = null;
      }
      checkGroupExposureList.clear();
      checkGroupExposureList = null;
      logger.info("checkGroupExposureQuery: " + checkGroupExposureQuery + " expoLimit: " + expoLimit);
      request.setAttribute("expoLimit", expoLimit);
      checkGroupExposureQuery = null;

      StringBuilder sactionValidDateQuery = new StringBuilder();
      sactionValidDateQuery.append("SELECT ");
      sactionValidDateQuery.append(dbo);
      sactionValidDateQuery.append("DATE_FORMAT(DEAL_SANCTION_VALID_TILL,'" + dateFormat + "') FROM cr_deal_loan_dtl where rec_status='F' AND deal_id='" + dealId.trim() + "'");
      logger.info("sactionValidDateQuery.toString() :::::::::::: " + sactionValidDateQuery.toString());
      String sactionValidDate = ConnectionDAO.singleReturn(sactionValidDateQuery.toString());
      request.setAttribute("sactionValidDate", sactionValidDate);
      sactionValidDateQuery = null;

      String specialRequiredQuery = "SELECT PARAMETER_VALUE  FROM parameter_mst where PARAMETER_KEY='SPECIAL_CONDITION_REQUIRED'";
      String specialRequired = ConnectionDAO.singleReturn(specialRequiredQuery);
      logger.info("specialRequiredQuery: " + specialRequiredQuery + " specialRequired: " + specialRequired);
      specialRequiredQuery = null;
      if (CommonFunction.checkNull(specialRequired).equalsIgnoreCase("Y"))
      {
    	  String specialCountQuery = "SELECT count(1) FROM cr_deal_special_condition_m where REC_STATUS='A' and DEAL_ID='" + dealId + "'";
        String specialCount = ConnectionDAO.singleReturn(specialCountQuery);
        request.setAttribute("specialCount", specialCount);
        logger.info("specialCountQuery: " + specialCountQuery + " specialCount: " + specialCount);
        specialCountQuery = null;
      }
      specialRequired = null;

      String disbursalRequiredQuery = "SELECT PARAMETER_VALUE  FROM parameter_mst where PARAMETER_KEY='DISBURSAL_SCHEDULE_REQUIRED'";
      String disbursalRequired = ConnectionDAO.singleReturn(disbursalRequiredQuery);
      logger.info("disbursalRequiredQuery: " + disbursalRequiredQuery + " disbursalRequired: " + disbursalRequired);
      disbursalRequiredQuery = null;
      if (CommonFunction.checkNull(disbursalRequired).equalsIgnoreCase("Y"))
      {
    	  String disbursalCountQuery = "SELECT count(1) FROM cr_deal_disbursalsch_dtl where DEAL_ID='" + dealId + "'";
        String disbursalCount = ConnectionDAO.singleReturn(disbursalCountQuery);
        request.setAttribute("disbursalCount", disbursalCount);
        logger.info("disbursalCountQuery: " + disbursalCountQuery + " disbursalCount: " + disbursalCount);
        disbursalCountQuery = null;
      }
      disbursalRequired = null;
      String countRepayTypeQuery = "select count(1) from cr_deal_loan_dtl b where b.DEAL_REPAYMENT_TYPE='I' and b.DEAL_ID='" + dealId + "'";
      logger.info("countRepayTypeQuery: " + countRepayTypeQuery);
      String countRepayType = ConnectionDAO.singleReturn(countRepayTypeQuery);
      countRepayTypeQuery = null;

      request.setAttribute("countRepayType", countRepayType);

      String termSheetRequiredQuery = "SELECT PARAMETER_VALUE  FROM parameter_mst where PARAMETER_KEY='TERMSHEET_MANDATORY_FLAG'";
      String termSheetRequired = ConnectionDAO.singleReturn(termSheetRequiredQuery);
      logger.info("termSheetRequiredQuery : " + termSheetRequiredQuery + " termSheetRequired flag: " + termSheetRequired);
      termSheetRequiredQuery = null;
      if (CommonFunction.checkNull(termSheetRequired).equalsIgnoreCase("Y"))
      {
    	  String termSheetCountQuery = "SELECT count(1) FROM cr_termsheet_dtl where TXN_ID='" + dealId + "'";
        String termSheetCount = ConnectionDAO.singleReturn(termSheetCountQuery);
        request.setAttribute("termSheetCount", termSheetCount);
        termSheetCountQuery = null;
        logger.info("termSheetCountQuery: " + termSheetCount + " termSheetCount: " + termSheetCount);
      }
      termSheetRequired = null;

      String IMDCHECK = "select replace(parameter_value,'|',''',''') from parameter_mst where parameter_key='UNDERWRITER_IMD_STATUS'";
      String IMDCHECKVAL = ConnectionDAO.singleReturn(IMDCHECK);
      logger.info("IMDCHECK: " + IMDCHECK + " IMDCHECKVAL: " + IMDCHECKVAL);

      String imdFlagQuery = "SELECT COUNT(1) FROM  CR_INSTRUMENT_DTL WHERE TXNID='" + dealId + "' AND TXN_TYPE='DC' AND REC_STATUS NOT IN ('" + IMDCHECKVAL + "')";
      logger.info("imdFlagQuery     ::: " + imdFlagQuery);
      String imdCount = ConnectionDAO.singleReturn(imdFlagQuery);

      logger.info("imdFlagQuery :::::" + imdFlagQuery + " imd  Count ::::: " + imdCount);
      imdFlagQuery = null;
      if (CommonFunction.checkNull(imdCount).equalsIgnoreCase("0"))
        request.setAttribute("imdFlag", "N");
      else {
        request.setAttribute("imdFlag", "Y");
      }

      logger.info("imdFlagQuery :::::" + imdFlagQuery + " termSheetCount ::::: " + imdCount);
      imdCount = null;
    }

    UnderwriterApprovalVo vo = new UnderwriterApprovalVo();
    ArrayList list = service.getApprovalRecommend(dealId, vo);
    UnderwriterApprovalVo docVo = new UnderwriterApprovalVo();
    docVo = (UnderwriterApprovalVo)list.get(0);
    String approval1 = docVo.getApprovalLevel(); //current deal approval

    String list1 = service.getApprovalLevel(docVo); // final deal approval

    String approval2 = docVo.getApprovalLevel();  //current deal approval

    String countqry = "select count(1) from cr_deal_approvedby_dtl where DEAL_ID='" + dealId + "'";
    int count = Integer.parseInt(ConnectionDAO.singleReturn(countqry));
    int i1 = Integer.parseInt(approval1); // current deal approval as int
    int i2 = Integer.parseInt(list1);  // final deal approval as int

    /*if ((i1 >= i2) || (count > 0))
    {
      request.setAttribute("approve", "approve");
    }
    else
    {
      request.setAttribute("recommend", "recommend");
    }*/
    
//jayanta  
    
    Integer level = 0;
    int listLevel  = service.getApprovalLevelfromPmst();
    int listS1 = 0;
    int approvalS2 = 0;
	UnderwritterDao uDao=(UnderwritterDao)DaoImplInstanceFactory.getDaoImplInstance(UnderwritterDao.IDENTITY);	
	
	//String policyApproval = uDao.getMaxPolicyApproval(dealId);// Max deviation level
	boolean amountFlag = uDao.checkFinalAmount(docVo,userId);// Max deviation level
	String q1="select ifnull(LEVEL,0) FROM CR_USER_APPROVAL_M WHERE USER_ROLE='U' and USER_ID='"+userId+"' and rec_status='A' ";
	String userlevel1=ConnectionDAO.singleReturn(q1);
	if(CommonFunction.checkNull(userlevel1).equalsIgnoreCase("")){
		userlevel1="0";
	}
	int userLevel=Integer.parseInt(userlevel1);
	if(userLevel>=i1){
		i1=userLevel;
	}
	
	  if (amountFlag && (i1 >= i2) || (count > 0))
	    {
	      request.setAttribute("approve", "approve");
	    }
	    else
	    {
	      request.setAttribute("recommend", "recommend");
	    }
	
	//String userPolicyApproval = CommonFunction.checkNull(uDao.getUserPolicyApproval(userId));// user policy level
	/*if(userPolicyApproval.equalsIgnoreCase(""))
		userPolicyApproval="0";
	int userPolicyApprovalInt = 0;
	try{
		userPolicyApprovalInt = Integer.parseInt(userPolicyApproval);// user policy level
	}
	catch(Exception e)
	{
		e.printStackTrace();
		userPolicyApprovalInt=0;
	}
	int policyApprovalS = 0;
	try{
		policyApprovalS = Integer.parseInt(policyApproval);// Max deviation level
	}
	catch(Exception e)
	{
		e.printStackTrace();
		policyApprovalS=0;
	}
	boolean flag = false;
	
	if(listS1>=approvalS2)
	{
		if(policyApprovalS>=listS1)
		{
			listLevel=policyApprovalS;
		}else
			listLevel=listS1;
	}
	request.setAttribute("pmstSize", listLevel);
	// check max deviation level and user policy level
	boolean deviationFlag=false;
	if (userPolicyApprovalInt>=policyApprovalS)
	{
		deviationFlag=true;
	}
	
	if(level>=listS1)
	{*/
//		request.setAttribute("pmstSize", listLevel);
//		if(amountFlag) //  level>=policyApprovalS && amountFlag
//		{
//			request.setAttribute("approve", "approve");
//		}
//		else
//		{
//			request.setAttribute("recommend", "recommend");	
//		}
	/*}
	else
	{
		request.setAttribute("recommend", "recommend");	
	}
    */
    
    
    
    //jayanta
    

    ArrayList creditApprovalList = service.getApprovalData(dealId, role);
    request.setAttribute("creditApprovalList", creditApprovalList);
    ArrayList workFlowStage = service.getworkFlowStage(dealId, funId);
    request.setAttribute("workFlowStage", workFlowStage);
    request.setAttribute("userId", userId);
    request.setAttribute("dealId", dealId);

    String recStatusQuery = "SELECT count(1) FROM CR_POLICY_DECISION  WHERE DEAL_ID='" + dealId + "' AND RULE_RESULT='T' AND REC_STATUS='X' ";
    logger.info("DECISION OF MANUAL DEVIATION AT UNDER WRITING QRY  : " + recStatusQuery.toString());
    String recStatusValue = ConnectionDAO.singleReturn(recStatusQuery);
    if (!CommonFunction.checkNull(recStatusValue).equalsIgnoreCase("0"))
      recStatusValue = "X";
    else {
      recStatusValue = "A";
    }
    logger.info("RECSTATUS VLAUE : " + recStatusValue);
    request.setAttribute("recStatus", recStatusValue);

    resource = null;
    dateFormat = null;
    dbo = null;
    dbType = null;
    service = null;

    return mapping.findForward("success");
  }
}