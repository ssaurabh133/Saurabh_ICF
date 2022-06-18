package com.cp.actions;

import com.communication.engn.daoImplMySql.SmsDAOImpl;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.ConnectionDAOforEJB;
import com.connect.DaoImplInstanceFactory;
import com.connect.PrepStmtObject;
import com.cp.dao.CreditProcessingDAO;
import com.cp.vo.CommonDealVo;
import com.cp.vo.UnderwriterApprovalVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;
import com.cp.dao.CovenantProposalTrackingDAO;

public class UnderwritingApprovalProcessingAction extends DispatchAction
{
  private static final Logger logger = Logger.getLogger(UnderwritingApprovalProcessingAction.class.getName());

  public ActionForward saveApprovalData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    logger.info("In UnderwritingApprovalProcessingAction(saveApprovalData) ");

    ResourceBundle resource = ResourceBundle.getBundle("com.yourcompany.struts.ApplicationResources");
    String dateFormat = resource.getString("lbl.dateInDao");
    String dbo = resource.getString("lbl.dbPrefix");
    HttpSession session = request.getSession();

    UserObject userobj = (UserObject)session.getAttribute("userobject");
    String bDate = null;
    String companyId = null;
    String userId = null;
    String userName = null;
    String branch = null;
    String msg = "";
    if (userobj != null)
    {
      bDate = userobj.getBusinessdate();
      companyId = new StringBuilder().append("").append(userobj.getCompanyId()).toString();
      userId = userobj.getUserId();
      userName = userobj.getUserName();
      branch = userobj.getBranchId();
    } else {
      logger.info("here in saveApprovalData method of UnderwritingApprovalProcessingAction action the session is out----------------");
      return mapping.findForward("sessionOut");
    }
    DynaValidatorForm UnderwriterApprovalDynaValidatorForm = (DynaValidatorForm)form;
    Object sessionId = session.getAttribute("sessionID");

    ServletContext context = getServlet().getServletContext();
    String strFlag = "";
    if (sessionId != null)
    {
      strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId.toString(), "", request);
    }

    logger.info(new StringBuilder().append("strFlag .............. ").append(strFlag).toString());
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

    String role = null;
    Object role1 = session.getAttribute("roleID");
    if (role1 != null)
    {
      role = role1.toString();
    }
  
    UnderwriterApprovalVo cr = new UnderwriterApprovalVo();
    String functionId = CommonFunction.checkNull(session.getAttribute("functionId"));
  	logger.info("INSIDE searchListUnderWriter Function Id =="+functionId);
  	
    String sms = null;
    String statusProc = null;
    cr.setUserId(userId);
    cr.setUserName(userName);
    cr.setApprovalLevel(role);
    cr.setMakerDate(bDate);
	cr.setFunctionId(functionId);
    String dealId = (String)session.getAttribute("dealId");

    cr.setDealId(dealId);

    BeanUtils.copyProperties(cr, UnderwriterApprovalDynaValidatorForm);

    CreditProcessingDAO service = (CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance("CP");
    logger.info(new StringBuilder().append("Implementation class: ").append(service.getClass()).toString());
    CovenantProposalTrackingDAO dao=(CovenantProposalTrackingDAO)DaoImplInstanceFactory.getDaoImplInstance(CovenantProposalTrackingDAO.IDENTITY);
    boolean beforApp = false;
    if(cr.getFunctionId().equalsIgnoreCase("3000296")){
    if (CommonFunction.checkNull(cr.getDecision()).equalsIgnoreCase("A"))
    {
      String testQ = new StringBuilder().append("select DEAL_SANCTION_AMOUNT,DEAL_SANCTION_VALID_TILL,DEAL_TENURE,DEAL_FINAL_RATE from cr_deal_loan_dtl where DEAL_ID=").append(cr.getDealId()).toString();

      ArrayList al = ConnectionDAO.sqlSelect(testQ);
      ArrayList data = null;
      if (al != null) {
        int size = al.size();
        for (int i = 0; i < size; i++) {
          data = (ArrayList)al.get(i);
          if ((data != null) && (data.size() > 0) && 
            (!CommonFunction.checkNull(data.get(0)).trim().equalsIgnoreCase("")) && (!CommonFunction.checkNull(data.get(1)).trim().equalsIgnoreCase("")) && (!CommonFunction.checkNull(data.get(2)).trim().equalsIgnoreCase("")))
          {
            beforApp = true;
          }
        }

        al.clear();
        al = null;
        data.clear();
        data = null;
      }

    }
    else
    {
      beforApp = true;
    }

    if (beforApp) {
    	if (CommonFunction.checkNull(cr.getDecision()).equalsIgnoreCase("A")){
		int cnt2=0;
			 int cnt3=1;
			String isFacilityDetailRequired = "N";
			String facilityDetailQry=" SELECT IF ((SELECT IFNULL(PARAMETER_VALUE,'N') FROM PARAMETER_MST WHERE PARAMETER_KEY = 'FACILITY_DETAIL_FUNCTIONALITY') = 'Y' ";
			facilityDetailQry = facilityDetailQry + "AND ONE_DEAL_ONE_LOAN='N', 'S', 'E') RESULT ";
			facilityDetailQry = facilityDetailQry + "FROM CR_DEAL_LOAN_DTL A ";
			facilityDetailQry = facilityDetailQry + "JOIN CR_PRODUCT_M B ON A.DEAL_PRODUCT = B.PRODUCT_ID ";
			facilityDetailQry = facilityDetailQry + "WHERE DEAL_ID = '"+CommonFunction.checkNull(dealId).trim()+"' ";
		    logger.info("Facility Detail Query is :  "+facilityDetailQry);
		    isFacilityDetailRequired=ConnectionDAO.singleReturn(facilityDetailQry);
		    String facilityLimitButton = CommonFunction.getParameterMSTValue("FACILITY_DETAIL_FUNCTIONALITY");
		    if(facilityLimitButton.equalsIgnoreCase("Y")){
		    if (isFacilityDetailRequired.equalsIgnoreCase("S")){
		    	
		 String facilityQuery="select Count(1) from CR_DEAL_FACILITY_DTL where ifnull(REFRESH_FLAG_FACILITY,'YYY')<>'NNN' and deal_id='"+dealId+"' ";
		  cnt2=Integer.parseInt(CommonFunction.checkNull(ConnectionDAO.singleReturn(facilityQuery))); 
		 
		    }else{
		    	boolean facilityflag =service.transferFacilityData(dealId);
		    	}
		    }
		    String facilityAmtQuery="select count(1) from cr_deal_dtl where deal_id='"+dealId+"' and DEAL_SANCTION_AMOUNT=(select sum(DEAL_SANCTION_AMOUNT) from cr_deal_facility_dtl where deal_id='"+dealId+"');";
			  cnt3=Integer.parseInt(CommonFunction.checkNull(ConnectionDAO.singleReturn(facilityAmtQuery)));
			  
		    String facQuery="select count(1) from cr_deal_facility_dtl where deal_id='"+dealId+"' ";
	    	int count=Integer.parseInt(CommonFunction.checkNull(ConnectionDAO.singleReturn(facQuery)));
		    if(count>0){
		    if(cnt2==0){
				 if(cnt3!=0){
	      statusProc = service.updateApprovalData(cr);
			}else{
				statusProc="Q";
			}
			 } else{
				statusProc="Z";
			}
		    }else{
		    	statusProc="ZZ";
		    }
    }else{
    	statusProc = service.updateApprovalData(cr);
    }
     // statusProc = service.updateApprovalData(cr);
      if (statusProc.equalsIgnoreCase("S"))
      {
        if (CommonFunction.checkNull(cr.getDecision()).equalsIgnoreCase("A"))
        {
          sms = "S";

          String underwriter = service.callProcUnderWriterUsersQueue(dealId, userId, bDate, branch);
          ArrayList dealVerificationMovementList = new SmsDAOImpl().getUnderWriterDealDetail(dealId, userId);
          String query2 = new StringBuilder().append("select rec_status from cr_deal_dtl where deal_id='").append(dealId).append("'; ").toString();
          logger.info(new StringBuilder().append("query::::").append(query2.toString()).toString());
          String recStatus2 = ConnectionDAO.singleReturn(query2.toString());
          logger.info(new StringBuilder().append("userName:::").append(userName).toString());
          logger.info(new StringBuilder().append("branch::::").append(branch).toString());
          String qu1 = "Select count(1) from comm_event_list_m where event_name='UnderWriter'  and REC_STATUS='A' ";
          logger.info(new StringBuilder().append("query1::::").append(qu1.toString()).toString());
          String recS = ConnectionDAO.singleReturn(qu1.toString());
          if (!recS.equalsIgnoreCase("0"))
          {
            if ((dealVerificationMovementList.size() != 0) && (!recStatus2.equalsIgnoreCase("A")))
            {
              for (int i = 0; i < dealVerificationMovementList.size(); i++)
              {
                CommonDealVo fetchVo = new CommonDealVo();

                fetchVo = (CommonDealVo)dealVerificationMovementList.get(i);
                String user = fetchVo.getUserName();

                ArrayList stats = new SmsDAOImpl().DealApproval(dealId, bDate, user, userName, branch);

                fetchVo = null;
              }
            }

            logger.info(new StringBuilder().append("In DealMovementBehindAction(underwriterDealTracking)............................ ").append(dealVerificationMovementList.size()).toString());
          }
			//pooja code starts 
		String FacScheme=ConnectionDAO.singleReturn("select group_concat(deal_scheme) from cr_deal_facility_dtl where deal_id='"+dealId+"' ");
		String CoLndngcount=ConnectionDAO.singleReturn("select count(1) from generic_master where GENERIC_KEY='COLENDINGSCHEME' and value in('"+FacScheme+"')");
		if(CoLndngcount.equalsIgnoreCase("0")){	
          String query1 = "Select count(1) from comm_event_list_m where event_name='DEAL_APPROVED_INTERNAL' and REC_STATUS='A' ";
          logger.info(new StringBuilder().append("query1::::").append(query1.toString()).toString());
          String recStatus1 = ConnectionDAO.singleReturn(query1.toString());
          if (!recStatus1.equalsIgnoreCase("0"))
          {
            String query = new StringBuilder().append("select rec_status from cr_deal_dtl where deal_id='").append(dealId).append("'; ").toString();
            logger.info(new StringBuilder().append("query::::").append(query.toString()).toString());
            String recStatus = ConnectionDAO.singleReturn(query.toString());
            if (recStatus.equalsIgnoreCase("A"))
            {
              String EventName = "DEAL_APPROVED_INTERNAL";

              boolean stats = new SmsDAOImpl().getEmailDetails(dealId, bDate, EventName);
              logger.info(new StringBuilder().append("Email Send on event DEAL_APPROVED_INTERNAL::: ").append(stats).toString());
            }

          }
          else
          {
            logger.info("Email is not active at event 'DEAL_APPROVED_INTERNAL' from comm_event_list_m....");
          }
          query1 = "Select count(1) from comm_event_list_m where event_name='DEAL_APPROVED_EXTERNAL' and REC_STATUS='A' ";
          logger.info(new StringBuilder().append("query1::::").append(query1.toString()).toString());
          recStatus1 = ConnectionDAO.singleReturn(query1.toString());
          if (!recStatus1.equalsIgnoreCase("0"))
          {
            String query = new StringBuilder().append("select rec_status from cr_deal_dtl where deal_id='").append(dealId).append("'; ").toString();
            logger.info(new StringBuilder().append("query::::").append(query.toString()).toString());
            String recStatus = ConnectionDAO.singleReturn(query.toString());
            if (recStatus.equalsIgnoreCase("A"))
            {
              String EventName = "DEAL_APPROVED_EXTERNAL";

              boolean stats = new SmsDAOImpl().getEmailDetails(dealId, bDate, EventName);
              logger.info(new StringBuilder().append("Email Send on event DEAL_APPROVED_EXTERNAL::: ").append(stats).toString());
            }

          }
          else
				logger.info("Email is not active at event 'DEAL_APPROVED_EXTERNAL' from comm_event_list_m....");
				}
					boolean colndngStatus=dao.insertcolndngDtl(cr);
				
				//pooja code end
			}
        else if (CommonFunction.checkNull(cr.getDecision()).equalsIgnoreCase("X")) {
          sms = "E";
          String query1 = "Select count(1) from comm_event_list_m where event_name='DEAL_REJECT' and REC_STATUS='A' ";
          logger.info(new StringBuilder().append("query1_DEAL_REJECT::::").append(query1.toString()).toString());
          String recStatus1 = ConnectionDAO.singleReturn(query1.toString());
          if (!recStatus1.equalsIgnoreCase("0"))
          {
            String EventName = "DEAL_REJECT";

            boolean stats = new SmsDAOImpl().getEmailDetails(dealId, bDate, EventName);
            logger.info(new StringBuilder().append("Email Send on event DEAL_REJECT::: ").append(stats).toString());
          }
          else
          {
            logger.info("Email is not active at event 'DEAL_REJECT' from comm_event_list_m....");
          }

          query1 = "Select count(1) from comm_event_list_m where event_name='DEAL_REJECT_EXTERNAL' and REC_STATUS='A' ";
          logger.info(new StringBuilder().append("query1_DEAL_REJECT_EXTERNAL::::").append(query1.toString()).toString());
          recStatus1 = ConnectionDAO.singleReturn(query1.toString());
          if (!recStatus1.equalsIgnoreCase("0"))
          {
            String EventName = "DEAL_REJECT_EXTERNAL";

            boolean stats = new SmsDAOImpl().getEmailDetails(dealId, bDate, EventName);
            logger.info(new StringBuilder().append("Email Send on event DEAL_REJECT_EXTERNAL::: ").append(stats).toString());
          }
          else
          {
            logger.info("Email is not active at event 'DEAL_REJECT_EXTERNAL' from comm_event_list_m....");
          } } else if (CommonFunction.checkNull(cr.getDecision()).equalsIgnoreCase("P"))
        {
          sms = "P";
          String query1 = "Select count(1) from comm_event_list_m where event_name='DEAL_SEND_BACK' and REC_STATUS='A' ";
          logger.info(new StringBuilder().append("query1::::").append(query1.toString()).toString());
          String recStatus1 = ConnectionDAO.singleReturn(query1.toString());
          if (!recStatus1.equalsIgnoreCase("0"))
          {
            String EventName = "DEAL_SEND_BACK";

            boolean stats = new SmsDAOImpl().getEmailDetails(dealId, bDate, EventName);
            logger.info(new StringBuilder().append("Email Send on event Deal_Approved::: ").append(stats).toString());
          }
          else
          {
            logger.info("Email is not active at event 'DEAL_SEND_BACK' from comm_event_list_m....");
          }
        }
        request.setAttribute("sms", sms);
      }
      else if(CommonFunction.checkNull(statusProc).equalsIgnoreCase("Z"))
      {
    	  sms = "Z";
    		request.setAttribute("sms", sms);
      }
      else if(CommonFunction.checkNull(statusProc).equalsIgnoreCase("ZZ"))
      {
    	  sms = "ZZ";
    		request.setAttribute("sms", sms);
      }
      else if(CommonFunction.checkNull(statusProc).equalsIgnoreCase("Q"))
      {
    	  sms = "Q";
    		request.setAttribute("sms", sms);
      }
      else
      {
        sms = "X";
        request.setAttribute("sms", sms);
        request.setAttribute("statusProc", statusProc);
      }
    } else {
      sms = "U";
      request.setAttribute("sms", sms);
    }

    StringBuilder sactionValidDateQuery = new StringBuilder();
    sactionValidDateQuery.append("SELECT ");
    sactionValidDateQuery.append(dbo);
    sactionValidDateQuery.append(new StringBuilder().append(" DATE_FORMAT(DEAL_SANCTION_VALID_TILL,'").append(dateFormat).append("') FROM cr_deal_loan_dtl where rec_status='F' AND deal_id='").append(dealId.trim()).append("'").toString());

    String sactionValidDate = ConnectionDAO.singleReturn(sactionValidDateQuery.toString());

    request.setAttribute("sactionValidDate", sactionValidDate);

    String funId = CommonFunction.checkNull(session.getAttribute("functionId")).trim();
    ArrayList workFlowStage = service.getworkFlowStage(dealId, funId);
    request.setAttribute("workFlowStage", workFlowStage);
    ArrayList creditApprovalList = service.getApprovalData(dealId, role);
    request.setAttribute("creditApprovalList", creditApprovalList);
    request.setAttribute("userId", userId);
    form.reset(mapping, request);
    funId = null;
    sactionValidDateQuery = null;
    service = null;

    resource = null;
    dateFormat = null;
    dbo = null;
    }else if(cr.getFunctionId().equalsIgnoreCase("500000123")){
		statusProc = dao.updateApprovalData(cr);
		if (statusProc.equalsIgnoreCase("S")) {
			
			
			//service.saveApprovalData(cr);
			// logger.info("Inside Approval Processing action........After executing Procedure.");
			//logger.info(" In UnderwritingApprovalProcessingAction(saveApprovalData) Inside Approval Processing action........value of statusProc= :"
				//			+ statusProc);
			if(CommonFunction.checkNull(cr.getDecision()).equalsIgnoreCase("A"))
			{
				sms = "S";
			String query1="Select count(1) from comm_event_list_m where event_name='DEAL_APPROVED_INTERNAL' and REC_STATUS='A' ";
			logger.info("query1::::"+query1.toString());
			String recStatus1 =ConnectionDAO.singleReturn(query1.toString());
			if(!recStatus1.equalsIgnoreCase("0"))
			{
				String query="select rec_status from cr_deal_dtl where deal_id='"+dealId+"'; ";
				logger.info("query::::"+query.toString());
				String recStatus =ConnectionDAO.singleReturn(query.toString());
				if(recStatus.equalsIgnoreCase("A") )
				{
					String EventName="DEAL_APPROVED_INTERNAL";
					
						boolean stats=new SmsDAOImpl().getEmailDetails(dealId,bDate,EventName);
						logger.info("Email Send on event DEAL_APPROVED_INTERNAL::: "+stats);
					
						
				}
			}
			else
			logger.info("Email is not active at event 'DEAL_APPROVED_INTERNAL' from comm_event_list_m....");
			
			 query1="Select count(1) from comm_event_list_m where event_name='DEAL_APPROVED_EXTERNAL' and REC_STATUS='A' ";
			logger.info("query1::::"+query1.toString());
			 recStatus1 =ConnectionDAO.singleReturn(query1.toString());
			if(!recStatus1.equalsIgnoreCase("0"))
			{
				String query="select rec_status from cr_deal_dtl where deal_id='"+dealId+"'; ";
				logger.info("query::::"+query.toString());
				String recStatus =ConnectionDAO.singleReturn(query.toString());
				if(recStatus.equalsIgnoreCase("A") )
				{
					String EventName="DEAL_APPROVED_EXTERNAL";
					
						boolean stats=new SmsDAOImpl().getEmailDetails(dealId,bDate,EventName);
						logger.info("Email Send on event DEAL_APPROVED_EXTERNAL::: "+stats);
					
						
				}
			}
			else
			logger.info("Email is not active at event 'DEAL_APPROVED_EXTERNAL' from comm_event_list_m....");
		}
			/*Rohit Changes ends for SMS& EMAIL*/
			else if(CommonFunction.checkNull(cr.getDecision()).equalsIgnoreCase("X")){
				sms = "E";
				 String query1="Select count(1) from comm_event_list_m where event_name='DEAL_REJECT' and REC_STATUS='A' ";
					logger.info("query1_DEAL_REJECT::::"+query1.toString());
					String recStatus1 =ConnectionDAO.singleReturn(query1.toString());
					if(!recStatus1.equalsIgnoreCase("0"))
					{
							String EventName="DEAL_REJECT";
							
								boolean stats=new SmsDAOImpl().getEmailDetails(dealId,bDate,EventName);
								logger.info("Email Send on event DEAL_REJECT::: "+stats);
							
						
					}
					else
					logger.info("Email is not active at event 'DEAL_REJECT' from comm_event_list_m....");
					
					
					
					  query1="Select count(1) from comm_event_list_m where event_name='DEAL_REJECT_EXTERNAL' and REC_STATUS='A' ";
						logger.info("query1_DEAL_REJECT_EXTERNAL::::"+query1.toString());
						 recStatus1 =ConnectionDAO.singleReturn(query1.toString());
						if(!recStatus1.equalsIgnoreCase("0"))
						{
								String EventName="DEAL_REJECT_EXTERNAL";
								
									boolean stats=new SmsDAOImpl().getEmailDetails(dealId,bDate,EventName);
									logger.info("Email Send on event DEAL_REJECT_EXTERNAL::: "+stats);
								
							
						}
						else
						logger.info("Email is not active at event 'DEAL_REJECT_EXTERNAL' from comm_event_list_m....");
			}else if(CommonFunction.checkNull(cr.getDecision()).equalsIgnoreCase("P"))
			{
				// String checkStageM=CommonFunction.stageMovement(companyId, "UNC","S",dealId, "", bDate,userId);
				// logger.info("checkStageM : "+checkStageM);
				 sms = "P";
				 String query1="Select count(1) from comm_event_list_m where event_name='DEAL_SEND_BACK' and REC_STATUS='A' ";
					logger.info("query1::::"+query1.toString());
					String recStatus1 =ConnectionDAO.singleReturn(query1.toString());
					if(!recStatus1.equalsIgnoreCase("0"))
					{
							String EventName="DEAL_SEND_BACK";
							
								boolean stats=new SmsDAOImpl().getEmailDetails(dealId,bDate,EventName);
								logger.info("Email Send on event Deal_Approved::: "+stats);
							
						
					}
					else
					logger.info("Email is not active at event 'DEAL_SEND_BACK' from comm_event_list_m....");
			}
				
			request.setAttribute("sms", sms);
		}

		else {
			//logger.info("Proc Message/status....................................... "+statusProc);
			sms = "X";
			request.setAttribute("sms", sms);
			request.setAttribute("statusProc", statusProc);
		}
	}
    logger.info(new StringBuilder().append("Saurabh-------- ").append(cr.getDecision()).toString());
    if (CommonFunction.checkNull(cr.getDecision()).equalsIgnoreCase("A"))
    {
      boolean stauts = false;
      try
      {
        String count2 = new StringBuilder().append("select count(1) from cr_quality_check_dtl where TXN_ID='").append(dealId.trim()).append("' and TXN_TYPE='DC'").toString();
        String count3 = ConnectionDAO.singleReturn(count2.toString());

        int count4 = Integer.parseInt(count3);
        if (count4 == 0)
        {
          StringBuffer updateDealDtl = new StringBuffer();
          updateDealDtl.append(new StringBuilder().append("update cr_deal_dtl set first_approval_date= STR_TO_DATE( '").append(bDate).append("','%d-%m-%Y') where deal_id ='").append(dealId.trim()).append("'").toString());
          ArrayList list1 = new ArrayList();
          PrepStmtObject valueObject1 = new PrepStmtObject();
          valueObject1.setSql(updateDealDtl.toString());
          list1.add(valueObject1);
          stauts = ConnectionDAOforEJB.sqlInsUpdDeletePrepStmt(list1);
          msg = "good";
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }

    }
    else
    {
      msg = "fail";
    }
    return mapping.findForward("success");
  }
}