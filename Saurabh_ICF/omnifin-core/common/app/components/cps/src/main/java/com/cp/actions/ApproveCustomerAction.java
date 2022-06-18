package com.cp.actions;

import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.cp.dao.CreditProcessingDAO;
import com.gcd.VO.CorporateDetailsVO;
import com.gcd.dao.CorporateDAO;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;
import com.tabDependencyCheck.RefreshFlagValueInsert;
import com.tabDependencyCheck.RefreshFlagVo;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.actions.DispatchAction;
import org.apache.commons.lang.StringEscapeUtils;
import com.connect.PrepStmtObject;

public class ApproveCustomerAction extends DispatchAction
{
  private static final Logger logger = Logger.getLogger(ApproveCustomerAction.class.getName());
  CreditProcessingDAO creditProcessingDAO = (CreditProcessingDAO)DaoImplInstanceFactory.getDaoImplInstance("CP");

  public ActionForward getApproval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    HttpSession session = request.getSession();
    UserObject userobj = (UserObject)session.getAttribute("userobject");
    String userId = "";
    String bDate = "";
    if (userobj != null)
    {
      userId = userobj.getUserId();
      bDate = userobj.getBusinessdate();
    }
    else {
      logger.info("here in getApproval method of ApproveCustomerAction action the session is out----------------");
      return mapping.findForward("sessionOut");
    }
    logger.info("In ApproveCustomerAction");
    CorporateDetailsVO corporateDetailVo = new CorporateDetailsVO();
    boolean flag = false;

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

    String updateFlag = "";
    if (session.getAttribute("updateFlag") != null)
    {
      logger.info("updateFlag..............................." + session.getAttribute("updateFlag"));
      updateFlag = session.getAttribute("updateFlag").toString();
    }

    String applType = request.getParameter("applType");
    if ((applType != null) && (!applType.equals("")))
    {
      logger.info("updateFlag..............................." + applType);
      String customerId = request.getParameter("codeId");

      String tableStatus = request.getParameter("status");

      logger.info("In ApproveCustomerAction" + customerId + " and Applicant type: " + applType + "tableStatus: " + tableStatus);

      CorporateDAO dao = (CorporateDAO)DaoImplInstanceFactory.getDaoImplInstance("CORPORATED");
      logger.info("Implementation class: " + dao.getClass());
      String dealId = "";

      if (session.getAttribute("dealId") != null)
      {
        dealId = session.getAttribute("dealId").toString();
      }
      else if (session.getAttribute("maxId") != null)
      {
        dealId = session.getAttribute("maxId").toString();
      }

      logger.info("Virender");
      ArrayList alDeleteQuery = new ArrayList(1);
      String hunterQry = "delete from cr_hunter_marking_dtl where deal_id='" + dealId + "'";
      logger.info("hunterQry " + hunterQry);
      alDeleteQuery.add(hunterQry);
      boolean status1 = ConnectionDAO.sqlInsUpdDelete(alDeleteQuery);
      hunterQry = null;

      logger.info("In ApproveCustomerAction getApproval dealid " + dealId);

      String status = "";
      String pan = (String)session.getAttribute("panNo");
      String aadhaar = (String)session.getAttribute("aadhaar");
      logger.info("pan-->" + pan + "aadhaar---->" + aadhaar);
      
      status = dao.moveFromGCD(customerId, applType, dealId, tableStatus, userId, bDate, pan, aadhaar);
		//Saurabh Code start here 
		 logger.info("Saurabh Code start for Cibil Initiation");
		//String dealId = "";					
 			boolean cibilStatus=false;
 			ArrayList qryListC = new ArrayList();
 			PrepStmtObject insertPrepStmtObject=new PrepStmtObject();
 			String qryUpdate = "UPDATE CR_DEAL_MOVEMENT_DTL SET DEAL_FORWARDED = ?, DEAL_FORWARD_USER = ? WHERE DEAL_STAGE_ID = ? AND DEAL_ID = '"+dealId+"'";	
 			
 			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull("0000-00-00 00:00:00")).trim().equalsIgnoreCase(""))
 				insertPrepStmtObject.addNull();
 			else
 				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql("0000-00-00 00:00:00").trim());
 			
 			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull("")).equalsIgnoreCase(""))
 				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql("").trim());
 			else
 				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql("").trim());
 			
 			if(StringEscapeUtils.escapeSql(CommonFunction.checkNull("CBL")).trim().equalsIgnoreCase(""))
 				insertPrepStmtObject.addNull();
 			else
 				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql("CBL").trim());
 			
 			/*if((CommonFunction.checkNull(corporateDetailVo.getDealId()).trim().equalsIgnoreCase("")))
 				insertPrepStmtObject.addNull();
 			else
 				insertPrepStmtObject.addString(StringEscapeUtils.escapeSql(corporateDetailVo.getDealId()).trim());*/
 			
 			insertPrepStmtObject.setSql(qryUpdate);
 			qryListC.add(insertPrepStmtObject);
 			/*boolean status2 = ConnectionDAO.sqlInsUpdDelete(qryListC);
 			qryUpdate=null;*/
 			try
 			{
 				logger.info("Query to initiate CIBIL: "+ insertPrepStmtObject.printQuery());
 				cibilStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryListC);
 			}
 			catch(Exception e){
 				e.printStackTrace();
 			}
		 //Saurabh Code Ends here
 			//Shyam Code Start here for Dedupe initiation
 			//String dealId = CommonFunction.checkNull(corporateDetailVo.getDealId());
 			String bday = ConnectionDAO.singleReturn("select DATE_FORMAT(PARAMETER_VALUE,'%Y-%m-%d %h:%m:%s') as bday from PARAMETER_mst where PARAMETER_KEY = 'BUSINESS_DATE' ");
 			String MakerId = ConnectionDAO.singleReturn("select deal_received_user from cr_deal_movement_dtl where deal_id = '"+dealId+"' and DEAL_STAGE_ID = 'DC' limit 1");
 			
 			String query12="select count(1) from cr_deal_movement_dtl where deal_id='"+dealId+"' and DEAL_STAGE_ID='DD'";
 			String status2 = ConnectionDAO.singleReturn(query12);
 			int count=Integer.parseInt(status2);
 			if(count>0)
 			{
 				PrepStmtObject  insertPrepStmtObject1 =  new PrepStmtObject ();
 				boolean cibilDedupeSt=false;
 				ArrayList qryListC1 = new ArrayList();
 				try
 				{
 				String updateCibil="update cr_deal_movement_dtl set deal_forwarded=?, deal_forward_user=? where DEAL_STAGE_ID='DD' and deal_id = ?";
 				
 				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull("0000-00-00 00:00:00")).trim().equalsIgnoreCase(""))
 					insertPrepStmtObject1.addNull();
 				else
 					insertPrepStmtObject1.addString(StringEscapeUtils.escapeSql("0000-00-00 00:00:00").trim());
 				
 				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull("")).equalsIgnoreCase(""))
 					insertPrepStmtObject1.addString(StringEscapeUtils.escapeSql("").trim());
 				else
 					insertPrepStmtObject1.addString(StringEscapeUtils.escapeSql("").trim());
 				
 				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull(dealId)).equalsIgnoreCase(""))
 					insertPrepStmtObject1.addString(StringEscapeUtils.escapeSql(dealId).trim());
 				else
 					insertPrepStmtObject1.addString(StringEscapeUtils.escapeSql(dealId).trim());

 				
 				insertPrepStmtObject1.setSql(updateCibil);
 				qryListC1.add(insertPrepStmtObject1);
 				cibilDedupeSt = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryListC1);
 				
 				} catch (Exception e) {
 					e.printStackTrace();
 				}
 				
 			}
 			else
 			{
 				PrepStmtObject  insertPrepStmtObject1 =  new PrepStmtObject();
 				boolean qryStatus=false;
 				ArrayList qryList1 = new ArrayList();
 				try
 				{
 				StringBuffer bufInsSql2 = new StringBuffer();
 				bufInsSql2.append("insert into cr_deal_movement_dtl(Deal_id,Deal_stage_id,Deal_action,deal_received,deal_forwarded,deal_received_user,deal_forward_user,rec_status)");
 				bufInsSql2.append("values(");
 				bufInsSql2.append("?,");
 				bufInsSql2.append("?,");
 				bufInsSql2.append("?,");
 				bufInsSql2.append("?,");
 				bufInsSql2.append("?,");
 				bufInsSql2.append("?,");
 				bufInsSql2.append("?,");
 				bufInsSql2.append("?");
 				bufInsSql2.append(")");
 				
 				if(CommonFunction.checkNull(dealId).trim().equalsIgnoreCase(""))
 					insertPrepStmtObject1.addNull();
 				else
 					insertPrepStmtObject1.addString(dealId);
 				
 				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull("DD")).equalsIgnoreCase(""))
 					insertPrepStmtObject1.addString(StringEscapeUtils.escapeSql("").trim());
 				else
 					insertPrepStmtObject1.addString(StringEscapeUtils.escapeSql("DD").trim());
 				
 				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull("I")).equalsIgnoreCase(""))
 					insertPrepStmtObject1.addString(StringEscapeUtils.escapeSql("").trim());
 				else
 					insertPrepStmtObject1.addString(StringEscapeUtils.escapeSql("I").trim());
 				
 				if(CommonFunction.checkNull(bday).trim().equalsIgnoreCase(""))
 					insertPrepStmtObject1.addNull();
 				else
 					insertPrepStmtObject1.addString(bday);
 				
 				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull("0000-00-00 00:00:00")).trim().equalsIgnoreCase(""))
 					insertPrepStmtObject1.addNull();
 				else
 					insertPrepStmtObject1.addString(StringEscapeUtils.escapeSql("0000-00-00 00:00:00").trim());
 				
 				if(CommonFunction.checkNull(MakerId).trim().equalsIgnoreCase(""))
 					insertPrepStmtObject1.addNull();
 				else
 					insertPrepStmtObject1.addString(MakerId);
 				
 				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull("")).equalsIgnoreCase(""))
 					insertPrepStmtObject1.addString(StringEscapeUtils.escapeSql("").trim());
 				else
 					insertPrepStmtObject1.addString(StringEscapeUtils.escapeSql("").trim());
 				
 				if(StringEscapeUtils.escapeSql(CommonFunction.checkNull("A")).equalsIgnoreCase(""))
 					insertPrepStmtObject1.addString(StringEscapeUtils.escapeSql("").trim());
 				else
 					insertPrepStmtObject1.addString(StringEscapeUtils.escapeSql("A").trim());
 				
 				insertPrepStmtObject1.setSql(bufInsSql2.toString());
 				qryList1.add(insertPrepStmtObject1);
 				
 				qryStatus = ConnectionDAO.sqlInsUpdDeletePrepStmt(qryList1);
 				
 				} catch (Exception e) {
 					e.printStackTrace();
 				}
 			
 			}
 			ArrayList queryList1=new ArrayList();
 			StringBuffer updatLoan = new StringBuffer();
 			updatLoan.append("update cr_deal_dtl set dedupe_status=? where deal_id=? ");
 	    	PrepStmtObject prepStmt = new PrepStmtObject();
 	    	if(CommonFunction.checkNull("Y").trim().equalsIgnoreCase(""))
 	    		prepStmt.addNull();
 	    	else
 	    		prepStmt.addString(("Y").trim());
 	    	
 	    	if(CommonFunction.checkNull(dealId).trim().equalsIgnoreCase(""))
 	    		prepStmt.addNull();
 	    	else
 	    		prepStmt.addString((dealId).trim());
 	    	
 	    	prepStmt.setSql(updatLoan.toString());	
 	    	logger.info("Update Dedupe Flag"+prepStmt.printQuery());
 	    	queryList1.add(prepStmt);		           
 	    	boolean Finalstatus=ConnectionDAO.sqlInsUpdDeletePrepStmt(queryList1); 	
 	   //Shyam Code End here for Dedupe initiation			
 			
      logger.info("Implementation class: " + this.creditProcessingDAO.getClass());
      ArrayList dealHeader = this.creditProcessingDAO.getDealHeader(dealId);
      session.setAttribute("dealHeader", dealHeader);

      logger.info("Status *************************************    :" + status);
      if (status.equalsIgnoreCase("NONE"))
      {
        logger.info("Status ******in if*******************************    :" + status);
        ArrayList roleList = dao.getRoleList(dealId);
        session.setAttribute("roleList", roleList);
        logger.info("role list size:" + roleList.size());
        RefreshFlagVo vo = new RefreshFlagVo();
        if ((dealId != null) && (!dealId.trim().equalsIgnoreCase("")))
          vo.setRecordId(Integer.parseInt(dealId.trim()));
        vo.setTabIndex(2);
        vo.setModuleName("CP");
        vo.setCustomerType(applType);
        vo.setCostomerID(customerId);
        RefreshFlagValueInsert.updateRefreshFlag(vo);
        request.setAttribute("approved", "S");
      }
      else
      {
        request.setAttribute("procval", status);
        request.removeAttribute("approved");
      }
      return mapping.getInputForward();
    }

    String pageInfo = "";
    pageInfo = request.getParameter("pageInfo");
    logger.info(".GCD Approval.....................................................: " + pageInfo);

    corporateDetailVo.setPagestatt(pageInfo);
    String code = "";
    String cusType = "";

    if (request.getParameter("codeId") != null)
    {
      code = request.getParameter("codeId");
    }
    if (session.getAttribute("cusType") != null)
    {
      cusType = session.getAttribute("cusType").toString();
      logger.info("Customer type is ....getApproval................" + cusType);
    }

    logger.info("In ApproveCustomerAction" + code);
    CorporateDAO dao = (CorporateDAO)DaoImplInstanceFactory.getDaoImplInstance("CORPORATED");
    logger.info("Implementation class: " + dao.getClass());

    String mvmtBy = "";
    mvmtBy = userobj.getUserId();
    logger.info("mvmtBy::::::::::::::" + mvmtBy);

    String dealId = "1";
    String status = dao.approve(code, cusType, mvmtBy, pageInfo, dealId);

    logger.info("gcd approved .status.................................." + status);

    if (status.equalsIgnoreCase(""))
    {
      logger.info("gcd approved ..................................." + status);
      request.setAttribute("approved", "Customer is Approved Successfully!!!");
      request.setAttribute("procval", status);
      return mapping.findForward("success");
    }

    request.setAttribute("approved", "Customer is not Approved Again Try!!!");
    request.setAttribute("procval", status);
    logger.info("procval..........." + request.getAttribute("procval"));
    return mapping.findForward("success");
  }

  public ActionForward deleteroleList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    logger.info("   deleteroleList/ roleList is to be deleted.........");

    HttpSession session = request.getSession();
    boolean flag = false;
    UserObject userobj = (UserObject)session.getAttribute("userobject");
    if (userobj == null) {
      logger.info("here in deleteroleList method of ApproveCustomerAction action the session is out----------------");
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
    boolean status = false;
    boolean status1 = false;
    boolean status2 = false;
    boolean appExist = false;
    boolean imdExist = false;
    String dealId = "";

    if (session.getAttribute("dealId") != null)
    {
      dealId = session.getAttribute("dealId").toString();
    }
    else if (session.getAttribute("maxId") != null)
    {
      dealId = session.getAttribute("maxId").toString();
    }
    logger.info("In ApproveCustomerAction deal id " + dealId);
    CorporateDAO detail = (CorporateDAO)DaoImplInstanceFactory.getDaoImplInstance("CORPORATED");
    logger.info("Implementation class: " + detail.getClass());
    String customerName = "";
    String[] role_id = null;
    if (request.getParameterValues("chk") != null)
    {
      role_id = request.getParameterValues("chk");
      session.setAttribute("role_id", role_id);
    }
    else
    {
      role_id = (String[])session.getAttribute("role_id");
      session.removeAttribute("role_id");
    }

    String[] applType = null;
    if (request.getParameterValues("applType") != null)
    {
      applType = request.getParameterValues("applType");
      session.setAttribute("applType", applType);
    }
    else
    {
      applType = (String[])session.getAttribute("applType");
      session.removeAttribute("applType");
    }

    String confirmStatus = request.getParameter("confirmStatus");
    logger.info("confirmStatus: " + confirmStatus);
    if (CommonFunction.checkNull(confirmStatus).equalsIgnoreCase("N"))
    {
      customerName = detail.checkCustomerVerifInit(role_id, applType, dealId);
    }
    logger.info("customerName: " + customerName);
    if (CommonFunction.checkNull(customerName).equalsIgnoreCase(""))
    {
      imdExist = detail.getExistingIMD(dealId, role_id);
      if (!imdExist)
      {
        appExist = detail.checkApplExistance(role_id, dealId);
        status1 = detail.deleteCustomerDocs(role_id, applType, dealId);
        status2 = detail.deleteVerificationInitCustomer(role_id, applType, dealId);
        status = detail.deleteroleList(role_id, applType, dealId);
      }
      if (status)
      {
        RefreshFlagVo vo = new RefreshFlagVo();
        if ((dealId != null) && (!dealId.trim().equalsIgnoreCase("")))
          vo.setRecordId(Integer.parseInt(dealId.trim()));
        vo.setModuleName("CP");
        vo.setTabIndex(2);
        vo.setDeleteFlag("Y");
        if (appExist)
          vo.setCustomerType("PRAPPL");
        RefreshFlagValueInsert.updateRefreshFlag(vo);
        request.setAttribute("msg", "S");
      }
      else if (imdExist) {
        request.setAttribute("msg", "IMD");
      } else {
        request.setAttribute("msg", "N");
      }
      logger.info("Implementation class: " + this.creditProcessingDAO.getClass());
      ArrayList dealHeader = this.creditProcessingDAO.getDealHeader(dealId);
      session.setAttribute("dealHeader", dealHeader);
      ArrayList roleList = detail.getRoleList(dealId);
      session.setAttribute("roleList", roleList);

      return mapping.findForward("deletesuccess");
    }

    logger.info("Implementation class: " + this.creditProcessingDAO.getClass());
    ArrayList dealHeader = this.creditProcessingDAO.getDealHeader(dealId);
    session.setAttribute("dealHeader", dealHeader);
    ArrayList roleList = detail.getRoleList(dealId);
    session.setAttribute("roleList", roleList);
    request.setAttribute("customerNameAddressVerif", customerName);
    return mapping.findForward("verifInitSuccess");
  }

  public ActionForward linkCustLoanMaker(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    HttpSession session = request.getSession();
    UserObject userobj = (UserObject)session.getAttribute("userobject");
    String userId = "";
    String bDate = "";
    if (userobj != null)
    {
      userId = userobj.getUserId();
      bDate = userobj.getBusinessdate();
    }
    else {
      logger.info("here in linkCustLoanMaker method of ApproveCustomerAction action the session is out----------------");
      return mapping.findForward("sessionOut");
    }
    logger.info("In ApproveCustomerAction(linkCustLoanMaker)");

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

    String source = "NE";
    String functionId = (String)session.getAttribute("functionId");
    int funid = Integer.parseInt(functionId);
    if ((funid == 4000122) || (funid == 4000123)) {
      source = "ED";
    }

    String updateFlag = "";
    if (session.getAttribute("updateFlag") != null)
    {
      logger.info("updateFlag..............................." + session.getAttribute("updateFlag"));
      updateFlag = session.getAttribute("updateFlag").toString();
    }

    String applType = request.getParameter("applType");
    if ((applType != null) && (!applType.equals("")))
    {
      logger.info("updateFlag..............................." + applType);
      String customerId = request.getParameter("codeId");

      String tableStatus = request.getParameter("status");

      logger.info("In linkCustLoanMaker cust ID: " + customerId + " and Applicant type: " + applType + "tableStatus: " + tableStatus);

      CorporateDAO dao = (CorporateDAO)DaoImplInstanceFactory.getDaoImplInstance("CORPORATED");
      logger.info("Implementation class: " + dao.getClass());
      String loanId = "";

      if (session.getAttribute("loanId") != null)
      {
        loanId = session.getAttribute("loanId").toString();
      }
      else if (session.getAttribute("maxIdInCM") != null)
      {
        loanId = session.getAttribute("maxIdInCM").toString();
      }

      logger.info("In linkCustLoanMaker  loanId " + loanId);

      String status = "";
      String pan = (String)session.getAttribute("panNo");
      String aadhaar = (String)session.getAttribute("aadhaar");
      logger.info("pan-->" + pan + "aadhaar---->" + aadhaar);
      status = dao.moveFromGCDATCM(customerId, applType, loanId, tableStatus, userId, bDate, source, pan, aadhaar);

      logger.info("Status *************************************    :" + status);
      if (status.equalsIgnoreCase("NONE"))
      {
        logger.info("Status ******in if*******************************    :" + status);
        ArrayList roleList = dao.getCustomerRoleList(loanId, source);
        session.setAttribute("roleList", roleList);
        logger.info("role list size:" + roleList.size());
        RefreshFlagVo vo = new RefreshFlagVo();
        vo.setRecordId(Integer.parseInt(loanId));
        vo.setTabIndex(10);
        vo.setModuleName("CM");
        vo.setCustomerType(applType);
        vo.setCostomerID(customerId);
        RefreshFlagValueInsert.updateRefreshFlag(vo);
        request.setAttribute("approved", "S");
      }
      else
      {
        request.setAttribute("procval", status);
        request.removeAttribute("approved");
      }

    }

    return mapping.findForward("linkCustomerAtCM");
  }

  public ActionForward deleteCustomerLoan(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    logger.info("   deleteCustomerLoan/ roleList is to be deleted.........");

    HttpSession session = request.getSession();
    boolean flag = false;
    UserObject userobj = (UserObject)session.getAttribute("userobject");
    if (userobj == null) {
      logger.info("here in deleteCustomerLoan method of ApproveCustomerAction action the session is out----------------");
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

    String source = "NE";
    String functionId = (String)session.getAttribute("functionId");
    int funid = Integer.parseInt(functionId);
    if ((funid == 4000122) || (funid == 4000123)) {
      source = "ED";
    }
    boolean status = false;

    String loanId = "";

    if (session.getAttribute("loanId") != null)
    {
      loanId = session.getAttribute("loanId").toString();
    }
    else
    {
      loanId = session.getAttribute("maxIdInCM").toString();
    }

    logger.info("In deleteCustomerLoan loanId id " + loanId);
    CorporateDAO detail = (CorporateDAO)DaoImplInstanceFactory.getDaoImplInstance("CORPORATED");
    logger.info("Implementation class: " + detail.getClass());
    String[] role_id = null;
    if (request.getParameterValues("chk") != null)
    {
      role_id = request.getParameterValues("chk");
      session.setAttribute("role_id", role_id);
    }
    else
    {
      role_id = (String[])session.getAttribute("role_id");
      session.removeAttribute("role_id");
    }

    String[] applType = null;
    if (request.getParameterValues("applType") != null)
    {
      applType = request.getParameterValues("applType");
    }

    String customerName = "";
    boolean status2 = false;
    String confirmStatus = request.getParameter("confirmStatus");
    logger.info("confirmStatus: " + confirmStatus);
    if (CommonFunction.checkNull(confirmStatus).equalsIgnoreCase("N"))
    {
      customerName = detail.checkCustomerVerifInitAtCM(role_id, applType, loanId);
    }
    logger.info("customerName: " + customerName);
    if (CommonFunction.checkNull(customerName).equalsIgnoreCase(""))
    {
      detail.updateCustomerPRSDocsAtCM(role_id, applType, loanId);
      detail.deleteCustomerDocsAtCM(role_id, applType, loanId, source);
      status2 = detail.deleteVerificationInitCustomerAtCM(role_id, applType, loanId);
      status = detail.deleteCustomerRoleAtCM(role_id, applType, loanId, source);

      if (status)
      {
        request.setAttribute("msg", "S");
      }
      else
      {
        request.setAttribute("msg", "N");
      }

      ArrayList roleList = detail.getCustomerRoleList(loanId, source);
      session.setAttribute("roleList", roleList);
      return mapping.findForward("deleteCustomerAtCM");
    }

    ArrayList roleList = detail.getCustomerRoleList(loanId, source);
    session.setAttribute("roleList", roleList);
    request.setAttribute("customerNameAddressVerif", customerName);
    return mapping.findForward("deleteCustomerAtCM");
  }
}