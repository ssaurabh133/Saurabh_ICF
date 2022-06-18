package com.cp.actions;
import com.business.CPClient.LeadProcessingRemote;
import com.business.DealClient.DealProcessingBeanRemote;
import com.business.ejbClient.CommonMasterBussinessSessionBeanRemote;
import com.connect.CommonFunction;
import com.connect.ConnectionDAO;
import com.connect.DaoImplInstanceFactory;
import com.connect.LookUpInstanceFactory;
import com.cp.leadDao.PreDealDao;
import com.cp.vo.CreditProcessingLeadDetailDataVo;
import com.login.dao.UserSessionCheck;
import com.login.roleManager.UserObject;

import java.util.ArrayList;
import java.util.Properties;

import javax.naming.InitialContext;
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

public class preDealCapturingDispatchAction extends DispatchAction
{
  private static final Logger logger = Logger.getLogger(preDealCapturingDispatchAction.class.getName());

  public ActionForward leadEntrySave(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
	 
			    logger.info("In preDealCapturingDispatchAction leadEntrySave().... ");
			    HttpSession session = request.getSession();
			    UserObject userobj = (UserObject)session.getAttribute("userobject");
			    String userId = "";
			    String bDate = "";
			    String branchId = null;
			    if (userobj != null)
			    {
			      userId = userobj.getUserId();
			      bDate = userobj.getBusinessdate();
			      branchId = userobj.getBranchId();
			    } else {
			      logger.info("here in leadEntrySave method of preDealCapturingDispatchAction action the session is out----------------");
			      return mapping.findForward("sessionOut");
			    }

			    String sessionId = session.getAttribute("sessionID").toString();

			    ServletContext context = getServlet().getServletContext();
			    String strFlag = "";
			    if (sessionId != null)
			    {
			      strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId.toString(), "", request);
			    }

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

			    String leadId = "";

			    if (session.getAttribute("leadId") != null)
			    {
			      leadId = session.getAttribute("leadId").toString();
			    } else if (session.getAttribute("maxId") != null) {
			      leadId = session.getAttribute("maxId").toString();
			    }

			    CreditProcessingLeadDetailDataVo leadIdVo = new CreditProcessingLeadDetailDataVo();
			    logger.info("lbxCustomerId=" + request.getParameter("lbxCustomerId") + ",coApplbxCustomerId=" + request.getParameter("coApplbxCustomerId") + ",gaurlbxCustomerId=" + request.getParameter("gaurlbxCustomerId"));
			    String CustomerId = request.getParameter("lbxCustomerId");
			    if (request.getParameter("coApplbxCustomerId") != null) {
			      leadIdVo.setCoApplbxCustomerId(request.getParameter("coApplbxCustomerId").toString());
			    }
			    if (request.getParameter("gaurlbxCustomerId") != null) {
			      leadIdVo.setGaurlbxCustomerId(request.getParameter("gaurlbxCustomerId").toString());
			    }
			    if (request.getParameter("coAppStatus1") != null) {
			      leadIdVo.setCoAppStatus1(request.getParameter("coAppStatus1").toString());
			    }
			    if (request.getParameter("gaurStatus1") != null) {
			      leadIdVo.setGaurStatus1(request.getParameter("gaurStatus1").toString());
			    }
			    if (request.getParameter("updateCustId") != null) {
				      leadIdVo.setUpdateCustId(request.getParameter("updateCustId").toString());
				    }
			    
			    leadIdVo.setLeadId(leadId);
			    leadIdVo.setApplicationdate(bDate);
			    leadIdVo.setMakerId(userId);
			    String custType = CommonFunction.checkNull(request.getParameter("customer"));
			    leadIdVo.setCustType(custType);
			    DynaValidatorForm LeadCapturingDynaValidatorForm = (DynaValidatorForm)form;
			    BeanUtils.copyProperties(leadIdVo, LeadCapturingDynaValidatorForm);

			    leadIdVo.setLbxRegionID2(branchId);

			    String msg = "";

			    CommonMasterBussinessSessionBeanRemote master = (CommonMasterBussinessSessionBeanRemote)LookUpInstanceFactory.getLookUpInstance("COMMONBUSSINESSMASTERREMOTE", request);

			    PreDealDao lp = (PreDealDao)DaoImplInstanceFactory.getDaoImplInstance("PreDeal");

			    DealProcessingBeanRemote dp = (DealProcessingBeanRemote)LookUpInstanceFactory.getLookUpInstance("DEALPROCESSINGBUSSINESSMASTERREMOTE", request);

			    LeadProcessingRemote lp1 = (LeadProcessingRemote)LookUpInstanceFactory.getLookUpInstance("LEADPROCESSINGBUSSINESSMASTERREMOTE", request);
			    String param = request.getParameter("saveForward");

			    leadIdVo.setTurnOver(request.getParameter("turnOver"));
			    ArrayList addresstypeList = lp.addresstype();

			    request.setAttribute("addresstypeList", addresstypeList);

			    String groupName = leadIdVo.getGroupName();
			    String cuspanid = request.getParameter("appCustPanInd");
			    leadIdVo.setCustPanInd(cuspanid);
			    String appAadhaarInd = request.getParameter("appAadhaarInd");
			    leadIdVo.setAadhaar(appAadhaarInd);
			    String check = LeadCapturingDynaValidatorForm.get("coAppcustPan").toString();
			    logger.info("coAppcustPan --->>> " + check);
			    String coAppcustPan = request.getParameter("coAppcustPan");
			    logger.info("coAppcustPan --->>> " + coAppcustPan);
			    leadIdVo.setCoAppcustPan(coAppcustPan);
			    String gaurcustPan = request.getParameter("gaurcustPan");
			    logger.info("gaurcustPan--->>> " + gaurcustPan);
			    leadIdVo.setGaurcustPan(gaurcustPan);
			    String count = "";
			    int count1 = 0;
			    String appPanCheck = null;
			    int appPanCount = 0;
			    String appUidCheck = null;
			    int appUidCount = 0;
			    String panNoExist = null;
			    String uidExist = null;

			    //Shashank Starts For Novac
			    String voterExist = null;
			    String dlExist = null;					
			    String passsportExist = null;
			    String regisExist = null;
			    
			    String  appVoterCheck = null;
			    String  sappVoterCheck = null;
			    String  appDrivingCheck = null;
			    String  sappDrivingCheck = null;
			    String  appPassportCheck = null;
			    String  sappPassportCheck = null;
			    
			    String  coAppVoterCheck = null;
			    String  scoAppVoterCheck = null;
			    String  coAppDlCheck = null;
			    String  scoAppDlCheck = null;
			    String  coAppPassCheck = null;
			    String  scoAppPassCheck = null;
			    
			    String  gaurVoterCheck = null;
			    String  sgaurVoterCheck = null;
			    String  gaurDlCheck = null;
			    String  sgaurDlCheck = null;
			    String  gaurPassCheck = null;
			    String  sgaurPassCheck = null;
			    
			    int appVoterCount = 0;
				int appDlCount = 0;
				int appPassCount = 0;
				int sappVoterCount = 0;
				int sappDlCount = 0;
				int sappPassCount = 0;
				
				int coAppVoterCount = 0;
				int coAppDlCount = 0;
				int coAppPassCount = 0;
				int scoAppVoterCount = 0;
				int scoAppDlCount = 0;
				int scoAppPassCount = 0;
				
				int gaurVoterCount = 0;
				int gaurDlCount = 0;
				int gaurPassCount = 0;
				int sgaurVoterCount = 0;
				int sgaurDlCount = 0;
				int sgaurPassCount = 0;
				
				String appRegisCheck = null;
				String sappRegisCheck = null;		
				String coAppRegisCheck = null;
				String scoAppRegisCheck = null;			
				String gaurRegisCheck = null;
				String sgaurRegisCheck = null;
				
				int appRegisCount = 0;
				int sappRegisCount = 0;
				int coAppRegisCount = 0;
				int scoAppRegisCount = 0;
				int gaurRegisCount = 0;
				int sgaurRegisCount = 0;			
			    //Shashank Ends For Novac
			    
			    int scount1 = 0;
			    String sappPanCheck = null;
			    int sappPanCount = 0;
			    String sappUidCheck = null;
			    int sappUidCount = 0;
			    String spanNoExist = null;
			    String suidExist = null;
			    String coAppPanCheck = null;
			    int coAppPanCount = 0;
			    String coAppUidCheck = null;
			    int coAppUidCount = 0;

			    String scoAppPanCheck = null;
			    int scoAppPanCount = 0;
			    String scoAppUidCheck = null;
			    int scoAppUidCount = 0;
			    String gaurPanCheck = null;
			    int gaurPanCount = 0;
			    String gaurUidCheck = null;
			    int gaurUidCount = 0;
			    String sgaurPanCheck = null;
			    int sgaurPanCount = 0;
			    String sgaurUidCheck = null;
			    int sgaurUidCount = 0;

			    boolean result = false;
			    String groupExist = "Group Already Exist";
			    if (leadIdVo.getGroupType().equalsIgnoreCase("N"))
			    {
			      count = master.checkgroupName(groupName);

			      count1 = Integer.parseInt(count);
			    }
			    if (leadIdVo.getCoAppcustPanInd().toString().equalsIgnoreCase("undefined"))
			      leadIdVo.setCoAppcustPanInd("");
			    if (leadIdVo.getCoAppaadhaar().toString().equalsIgnoreCase("undefined"))
			      leadIdVo.setCoAppaadhaar("");
			    if (leadIdVo.getGaurcustPanInd().toString().equalsIgnoreCase("undefined"))
			      leadIdVo.setGaurcustPanInd("");
			    if (leadIdVo.getGauraadhaar().toString().equalsIgnoreCase("undefined")) {
			      leadIdVo.setGauraadhaar("");
			    }
			    String applicationForm="";
			    if(!CommonFunction.checkNull(leadIdVo.getApplicationFormNoBranch()).equalsIgnoreCase("")){
			    	applicationForm=leadIdVo.getApplicationFormNoBranch();	
			    }else if(!CommonFunction.checkNull(leadIdVo.getApplicationFormNoOther()).equalsIgnoreCase("")){
			    	applicationForm=leadIdVo.getApplicationFormNoOther();
			    }else if(!CommonFunction.checkNull(leadIdVo.getApplicationFormNoRm()).equalsIgnoreCase("")){
			    	applicationForm=leadIdVo.getApplicationFormNoRm();
			    }else if(!CommonFunction.checkNull(leadIdVo.getApplicationFormNoVendor()).equalsIgnoreCase("")){
			    	applicationForm=leadIdVo.getApplicationFormNoVendor();
			    }
			    if(!CommonFunction.checkNull(applicationForm).equalsIgnoreCase("")){
			    String applicationQuery="select count(1) from cr_deal_dtl where DEAL_APPLICATION_FORM_NO='"+applicationForm+"' and ifnull(lead_id,0)<>'"+leadIdVo.getLeadId()+"' ";
			    int appFormCount=Integer.parseInt(ConnectionDAO.singleReturn(applicationQuery));
			    if (appFormCount > 0)
			      {
			        panNoExist = "Application Form No. already exist.";
			        request.setAttribute("panNoExist", panNoExist);
			        ArrayList genderIndiv = lp.getGenderList();
			        request.setAttribute("GenderCategory", genderIndiv);
			        String pincodeFlag = lp1.getPincodeFlag();
			        logger.info("value of pincode flag is ::" + pincodeFlag);
			        request.setAttribute("pincodeFlag", pincodeFlag);
			        if (leadIdVo.getCoAppcustPanInd().toString().equalsIgnoreCase("undefined"))
			          leadIdVo.setCoAppcustPanInd("");
			        if (leadIdVo.getCoAppaadhaar().toString().equalsIgnoreCase("undefined"))
			          leadIdVo.setCoAppaadhaar("");
			        if (leadIdVo.getGaurcustPanInd().toString().equalsIgnoreCase("undefined"))
			          leadIdVo.setGaurcustPanInd("");
			        if (leadIdVo.getGauraadhaar().toString().equalsIgnoreCase("undefined"))
			          leadIdVo.setGauraadhaar("");
			        ArrayList leadDetails = new ArrayList();
			        leadIdVo.setContitutionCode(leadIdVo.getIndconstitution());
			        leadDetails.add(leadIdVo);
			        logger.info("consititution  valu by rohit sahay---------------------------------" + leadIdVo.getContitutionCode());

			        request.setAttribute("leadDetails", leadDetails);
			        return mapping.findForward("success");
			      }
			    }
			    logger.info("In LeadEntrySave() ---------->>> ");
			    if (leadIdVo.getCustomerType().equalsIgnoreCase("I")) {
			      String appPanNo = request.getParameter("appCustPanInd");
			      String appVoterId = request.getParameter("voterId");
			      String appDlNumber = request.getParameter("dlNumber");
			      String appPassport = request.getParameter("passport");
			      String appAadhaarNo = request.getParameter("appAadhaarInd");
			      String corconstitution = request.getParameter("corconstitution");
			      String relationship = leadIdVo.getRelationship();
			      logger.info("Applicant Relatiinship---->>>>>> " + relationship);

			      if (relationship.trim().equalsIgnoreCase("New")) {
			        if (!CustomerId.equalsIgnoreCase("")) {
			          String query1 = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.PAN='" + appPanNo + "' AND a.PAN<>'') and a.CUSTOMER_ID<>" + CustomerId;
			          logger.info("in execute() of IdividualDetailActionPage Query : " + query1);
			          
			          //Shashank Starts For Novac
			          String voterId = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.VOTER_ID='" + appVoterId + "' AND a.VOTER_ID<>'') and a.CUSTOMER_ID<>" + CustomerId;
			          logger.info("in execute() of IdividualDetailActionPage Query : " + voterId);
			          
			          String drivingLic = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.DRIVING_LICENSE='" + appDlNumber + "' AND a.DRIVING_LICENSE<>'') and a.CUSTOMER_ID<>" + CustomerId;
			          logger.info("in execute() of IdividualDetailActionPage Query : " + drivingLic);
			          
			          String passport = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.PASSPORT_NUMBER='" + appPassport + "' AND a.PASSPORT_NUMBER<>'') and a.CUSTOMER_ID<>" + CustomerId;
			          logger.info("in execute() of IdividualDetailActionPage Query : " + passport); 
			         
			          String svoterId = "select count(1) from gcd_customer_m where (VOTER_ID='" + appVoterId + "' AND VOTER_ID<>'') and ifnull(LEAD_CUSTOMER_ID,'')<>" + CustomerId;
			          logger.info("in execute() of IdividualDetailActionPage Query : " + svoterId);
			          String sdrivingLic = "select count(1) from gcd_customer_m where (DRIVING_LICENSE='" + appDlNumber + "' AND DRIVING_LICENSE<>'') and ifnull(LEAD_CUSTOMER_ID,'')<>" + CustomerId;
			          logger.info("in execute() of IdividualDetailActionPage Query : " + sdrivingLic);
			          String spassport = "select count(1) from gcd_customer_m where (PASSPORT_NUMBER='" + appPassport + "' AND PASSPORT_NUMBER<>'') and ifnull(LEAD_CUSTOMER_ID,'')<>" + CustomerId;
			          logger.info("in execute() of IdividualDetailActionPage Query : " + spassport);
			          appVoterCheck = ConnectionDAO.singleReturn(voterId);
			          sappVoterCheck = ConnectionDAO.singleReturn(svoterId);
			          appDrivingCheck = ConnectionDAO.singleReturn(drivingLic);
			          sappDrivingCheck = ConnectionDAO.singleReturn(sdrivingLic);
			          appPassportCheck = ConnectionDAO.singleReturn(passport);
			          sappPassportCheck = ConnectionDAO.singleReturn(spassport);
			          //Shashank Ends For Novac
			          
			          String squery1 = "select count(1) from gcd_customer_m where (CUSTMER_PAN='" + appPanNo + "' AND CUSTMER_PAN<>'') and ifnull(LEAD_CUSTOMER_ID,'')<>" + CustomerId;
			          logger.info("in execute() of IdividualDetailActionPage Query : " + squery1);
			          appPanCheck = ConnectionDAO.singleReturn(query1);
			          sappPanCheck = ConnectionDAO.singleReturn(squery1);
			          String query2 = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.UID_NO='" + appAadhaarNo + "' AND a.UID_NO<>'') and a.CUSTOMER_ID<>" + CustomerId;
			          logger.info("in execute() of IdividualDetailActionPage Query : " + query2);
			          appUidCheck = ConnectionDAO.singleReturn(query2);
			          
			        
			          String squery2 = "select count(1) from gcd_customer_m where (UID_NO='" + appAadhaarNo + "' AND UID_NO<>'') and ifnull(LEAD_CUSTOMER_ID,'')<>" + CustomerId;
			          logger.info("in execute() of IdividualDetailActionPage Query : " + squery2);
			          sappUidCheck = ConnectionDAO.singleReturn(squery2);
			        }
			        else if (CustomerId.equalsIgnoreCase(""))
			        {
			          String query1 = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.PAN='" + appPanNo + "' and a.PAN<>'')";
			          logger.info("in execute() of IdividualDetailActionPage Query : " + query1);
			          String squery1 = "select count(1) from gcd_customer_m where (CUSTMER_PAN='" + appPanNo + "' and CUSTMER_PAN<>'')";
			          logger.info("in execute() of IdividualDetailActionPage Query : " + squery1);
			          appPanCheck = ConnectionDAO.singleReturn(query1);
			          sappPanCheck = ConnectionDAO.singleReturn(squery1);
			          
			          //Shashank Starts For Novac
			          String voterId = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.VOTER_ID='" + appVoterId + "' and a.VOTER_ID<>'')";
			          logger.info("in execute() of IdividualDetailActionPage Query : " + voterId);
			          String svoterId = "select count(1) from gcd_customer_m where (VOTER_ID='" + appVoterId + "' and VOTER_ID<>'')";
			          logger.info("in execute() of IdividualDetailActionPage Query : " + svoterId);
			          appVoterCheck = ConnectionDAO.singleReturn(voterId);
			          sappVoterCheck = ConnectionDAO.singleReturn(svoterId);
			          
			          String drivingLic = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.DRIVING_LICENSE='" + appDlNumber + "' and a.DRIVING_LICENSE<>'')";
			          logger.info("in execute() of IdividualDetailActionPage Query : " + drivingLic);
			          String sdrivingLic = "select count(1) from gcd_customer_m where (DRIVING_LICENSE='" + appDlNumber + "' and DRIVING_LICENSE<>'')";
			          logger.info("in execute() of IdividualDetailActionPage Query : " + sdrivingLic);
			          appDrivingCheck = ConnectionDAO.singleReturn(drivingLic);
			          sappDrivingCheck = ConnectionDAO.singleReturn(sdrivingLic);
			          
			          String passport = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.PASSPORT_NUMBER='" + appPassport + "' and a.PASSPORT_NUMBER<>'')";
			          logger.info("in execute() of IdividualDetailActionPage Query : " + passport);
			          String spassport = "select count(1) from gcd_customer_m where (PASSPORT_NUMBER='" + appPassport + "' and PASSPORT_NUMBER<>'')";
			          logger.info("in execute() of IdividualDetailActionPage Query : " + spassport);
			          appPassportCheck = ConnectionDAO.singleReturn(passport);
			          sappPassportCheck = ConnectionDAO.singleReturn(spassport);
			          //Shashank Ends For Novac
			          
			          String query2 = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.UID_NO='" + appAadhaarNo + "' and a.UID_NO<>'')";
			          logger.info("in execute() of IdividualDetailActionPage Query : " + query2);
			          appUidCheck = ConnectionDAO.singleReturn(query2);

			          String squery2 = "select count(1) from gcd_customer_m where (UID_NO='" + appAadhaarNo + "' and UID_NO<>'')";
			          logger.info("in execute() of IdividualDetailActionPage Query : " + squery2);
			          sappUidCheck = ConnectionDAO.singleReturn(squery2);
			        }
			      }
			    }
			    if (leadIdVo.getCustomerType().equalsIgnoreCase("C")) {
			      String appPanNo = leadIdVo.getCustPan();
			      String corconstitution = request.getParameter("corconstitution");
			      String relationship = leadIdVo.getRelationship();
			      
			      String appRegistrationNo = leadIdVo.getRegistrationNo();//Shahsank For Novac
			      
			      logger.info("Applicant Relatiinship---->>>>>> " + relationship);
			      if (relationship.trim().equalsIgnoreCase("New")) {
			        if (!CustomerId.equalsIgnoreCase("")) {
			          String Corconstitution = leadIdVo.getCorconstitution();
			          if (!corconstitution.equalsIgnoreCase("PROPRIETOR")) {
			            String query1 = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.PAN='" + appPanNo + "' AND a.PAN<>'') and a.CUSTOMER_ID<>" + CustomerId;
			            logger.info("in execute() of IdividualDetailActionPage Query : " + query1);
			           
			            String squery1 = "select count(1) from gcd_customer_m where (CUSTMER_PAN='" + appPanNo + "' AND CUSTMER_PAN<>'') and ifnull(LEAD_CUSTOMER_ID,'')<>" + CustomerId;
			            logger.info("in execute() of IdividualDetailActionPage Query : " + squery1);
			            appPanCheck = ConnectionDAO.singleReturn(query1);
			            sappPanCheck = ConnectionDAO.singleReturn(squery1);
			            
			            //Shashank Starts For Novac
			            String registrationNo = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.REGISTRATION_NO='" + appRegistrationNo + "' AND a.REGISTRATION_NO<>'') and a.CUSTOMER_ID<>" + CustomerId;
			            logger.info("in execute() of IdividualDetailActionPage Query : " + registrationNo);
			           
			            String sregistrationNo = "select count(1) from gcd_customer_m where (CUSTOMER_REGISTRATION_NO='" + appRegistrationNo + "' AND CUSTOMER_REGISTRATION_NO<>'') and ifnull(LEAD_CUSTOMER_ID,'')<>" + CustomerId;
			            logger.info("in execute() of IdividualDetailActionPage Query : " + sregistrationNo);
			            appRegisCheck = ConnectionDAO.singleReturn(registrationNo);
			            sappRegisCheck = ConnectionDAO.singleReturn(sregistrationNo);
			            //Shashank Ends For Novac
			          }
			        }
			        else if (CustomerId.equalsIgnoreCase(""))
			        {
			          String Corconstitution = leadIdVo.getCorconstitution();
			          if (!corconstitution.equalsIgnoreCase("PROPRIETOR")) {
			            String query1 = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.PAN='" + appPanNo + "' AND a.PAN<>'') ";
			            logger.info("in execute() of IdividualDetailActionPage Query : " + query1);
			            String squery1 = "select count(1) from gcd_customer_m where (CUSTMER_PAN='" + appPanNo + "' AND CUSTMER_PAN<>'') ";
			            logger.info("in execute() of IdividualDetailActionPage Query : " + squery1);
			            appPanCheck = ConnectionDAO.singleReturn(query1);
			            sappPanCheck = ConnectionDAO.singleReturn(squery1);
			            
			          //Shashank Starts For Novac
			            String registrationNo = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.REGISTRATION_NO='" + appPanNo + "' AND a.REGISTRATION_NO<>'') ";
			            logger.info("in execute() of IdividualDetailActionPage Query : " + registrationNo);
			            String sregistrationNo = "select count(1) from gcd_customer_m where (CUSTOMER_REGISTRATION_NO='" + appPanNo + "' AND CUSTOMER_REGISTRATION_NO<>'') ";
			            logger.info("in execute() of IdividualDetailActionPage Query : " + sregistrationNo);
			            appPanCheck = ConnectionDAO.singleReturn(registrationNo);
			            sappPanCheck = ConnectionDAO.singleReturn(sregistrationNo);
			          //Shashank Ends For Novac
			          }
			        }
			      }
			    }

			    if (leadIdVo.getCoAppcustomerType().equalsIgnoreCase("I"))
			    {
			      String coAppPanNo;
			   
			      if (leadIdVo.getCoAppcustPanInd().toString().equalsIgnoreCase("undefined"))
			        coAppPanNo = "";
			      else
			        coAppPanNo = leadIdVo.getCoAppcustPanInd();
			      String coAppAadhaarNo;
			 
			      if (leadIdVo.getCoAppaadhaar().toString().equalsIgnoreCase("undefined"))
			        coAppAadhaarNo = "";
			      else {
			        coAppAadhaarNo = leadIdVo.getCoAppaadhaar();
			      }
			      
			      //Shashank starts For Novac
			      String coAppvoterId;
					 
			      if (leadIdVo.getCoAppvoterId().toString().equalsIgnoreCase("undefined"))
			    	  coAppvoterId = "";
			      else {
			    	  coAppvoterId = leadIdVo.getCoAppvoterId();
			      }
			      
			      String coAppdlNumber;
					 
			      if (leadIdVo.getCoAppdlNumber().toString().equalsIgnoreCase("undefined"))
			    	  coAppdlNumber = "";
			      else {
			    	  coAppdlNumber = leadIdVo.getCoAppdlNumber();
			      }
			      
			      String coApppassport;
					 
			      if (leadIdVo.getCoApppassport().toString().equalsIgnoreCase("undefined"))
			    	  coApppassport = "";
			      else {
			    	  coApppassport = leadIdVo.getCoApppassport();
			      }
			      
			      //Shashank ends For Novac
			      String coAppcorconstitution = request.getParameter("coAppcorconstitution");
			      String CoappRelationship = leadIdVo.getCoApprelationship();
			      logger.info("Co-Applicant Relatiinship---->>>>>> " + CoappRelationship);
			      
			      String query3 = "";
			      String squery3 = "";
			      String coAppvoter = "";
			      String scoAppvoter = "";
			      String coAppdlNum = "";
			      String scoAppdlNum = "";
			      String coApppass = "";
			      String scoApppass = "";
			      if (!CoappRelationship.equalsIgnoreCase("Existing")) {
			    	  String q1="select PAN from cr_lead_customer_m where constitution='PROPRIETOR' and lead_id='"+leadIdVo.getLeadId()+"'";
			        	String propPan=ConnectionDAO.singleReturn(q1);
			        			
			        	if(!CommonFunction.checkNull(coAppPanNo).equalsIgnoreCase(propPan)){
			        if (!CommonFunction.checkNull(leadIdVo.getCoApplbxCustomerId()).equalsIgnoreCase("")) {
			          query3 = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.PAN='" + coAppPanNo + "' and a.PAN<>'') and a.CUSTOMER_ID<>" + leadIdVo.getCoApplbxCustomerId();
			        
			          squery3 = "select count(1) from gcd_customer_m where (CUSTMER_PAN='" + coAppPanNo + "' and CUSTMER_PAN<>'') and  ifnull(LEAD_CUSTOMER_ID,'')<>" + leadIdVo.getCoApplbxCustomerId();
			        }
			        else {
			          query3 = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.PAN='" + coAppPanNo + "' and a.PAN<>'') ";
			          squery3 = "select count(1) from gcd_customer_m where (CUSTMER_PAN='" + coAppPanNo + "' and CUSTMER_PAN<>'') ";
			         
			        }
			        	
			        logger.info("in execute() of IdividualDetailActionPage Query : " + query3);
			        logger.info("in execute() of IdividualDetailActionPage Query : " + squery3);

			        coAppPanCheck = ConnectionDAO.singleReturn(query3);
			        scoAppPanCheck = ConnectionDAO.singleReturn(squery3);
			        
			        	}
			        	 //Shashank Starts For Novac
				        if (!CommonFunction.checkNull(leadIdVo.getCoApplbxCustomerId()).equalsIgnoreCase("")) {

					          coAppvoter = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.VOTER_ID='" + coAppvoterId + "' and a.VOTER_ID<>'') and a.CUSTOMER_ID<>" + leadIdVo.getCoApplbxCustomerId();
						        
					          scoAppvoter = "select count(1) from gcd_customer_m where (VOTER_ID='" + coAppvoterId + "' and VOTER_ID<>'') and  ifnull(LEAD_CUSTOMER_ID,'')<>" + leadIdVo.getCoApplbxCustomerId();
					       
					          coAppdlNum = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.DRIVING_LICENSE='" + coAppdlNumber + "' and a.DRIVING_LICENSE<>'') and a.CUSTOMER_ID<>" + leadIdVo.getCoApplbxCustomerId();
						        
					          scoAppdlNum = "select count(1) from gcd_customer_m where (DRIVING_LICENSE='" + coAppdlNumber + "' and DRIVING_LICENSE<>'') and  ifnull(LEAD_CUSTOMER_ID,'')<>" + leadIdVo.getCoApplbxCustomerId();
					       
					          coApppass = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.PASSPORT_NUMBER='" + coApppassport + "' and a.PASSPORT_NUMBER<>'') and a.CUSTOMER_ID<>" + leadIdVo.getCoApplbxCustomerId();
						        
					          scoApppass = "select count(1) from gcd_customer_m where (PASSPORT_NUMBER='" + coApppassport + "' and PASSPORT_NUMBER<>'') and  ifnull(LEAD_CUSTOMER_ID,'')<>" + leadIdVo.getCoApplbxCustomerId();	
				        }
				        else
				        {
				        	 coAppvoter = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.VOTER_ID='" + coAppvoterId + "' and a.VOTER_ID<>'') ";
					          scoAppvoter = "select count(1) from gcd_customer_m where (VOTER_ID='" + coAppvoterId + "' and VOTER_ID<>'') ";
					          coAppdlNum = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.DRIVING_LICENSE='" + coAppdlNumber + "' and a.DRIVING_LICENSE<>'') ";
					          scoAppdlNum = "select count(1) from gcd_customer_m where (DRIVING_LICENSE='" + coAppdlNumber + "' and DRIVING_LICENSE<>'') ";
					          coApppass = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.PASSPORT_NUMBER='" + coApppassport + "' and a.PASSPORT_NUMBER<>'') ";
					          scoApppass = "select count(1) from gcd_customer_m where (PASSPORT_NUMBER='" + coApppassport + "' and PASSPORT_NUMBER<>'') ";
					        	
				        }
				        logger.info("in execute() of IdividualDetailActionPage Query : " + coAppvoter);
				        logger.info("in execute() of IdividualDetailActionPage Query : " + scoAppvoter);
				        logger.info("in execute() of IdividualDetailActionPage Query : " + coAppdlNum);
				        logger.info("in execute() of IdividualDetailActionPage Query : " + scoAppdlNum);
				        logger.info("in execute() of IdividualDetailActionPage Query : " + coApppass);
				        logger.info("in execute() of IdividualDetailActionPage Query : " + scoApppass);
				        coAppVoterCheck = ConnectionDAO.singleReturn(coAppvoter);
				        scoAppVoterCheck = ConnectionDAO.singleReturn(scoAppvoter);
				        coAppDlCheck = ConnectionDAO.singleReturn(coAppdlNum);
				        scoAppDlCheck = ConnectionDAO.singleReturn(scoAppdlNum);
				        coAppPassCheck = ConnectionDAO.singleReturn(coApppass);
				        scoAppPassCheck = ConnectionDAO.singleReturn(scoApppass);
				          //Shashank Ends For Novac
				        
			        String query4 = "";
			        String squery4 = "";
			        if (!CommonFunction.checkNull(leadIdVo.getCoApplbxCustomerId()).equalsIgnoreCase("")) {
			          query4 = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.UID_NO='" + coAppAadhaarNo + "' and a.UID_NO<>'') and a.CUSTOMER_ID<>" + leadIdVo.getCoApplbxCustomerId();
			          
			          squery4 = "select count(1) from gcd_customer_m where (UID_NO='" + coAppAadhaarNo + "' and UID_NO<>'') and  ifnull(LEAD_CUSTOMER_ID,'')<>" + leadIdVo.getCoApplbxCustomerId();
			        }
			        else {
			          query4 = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.UID_NO='" + coAppAadhaarNo + "' and a.UID_NO<>'') ";
			          squery4 = "select count(1) from gcd_customer_m where (UID_NO='" + coAppAadhaarNo + "' and UID_NO<>'')";
			        }
			        logger.info("in execute() of IdividualDetailActionPage Query : " + query4);
			        logger.info("in execute() of IdividualDetailActionPage Query : " + squery4);
			        coAppUidCheck = ConnectionDAO.singleReturn(query4);
			        scoAppUidCheck = ConnectionDAO.singleReturn(squery4);
			        String coAppNameExist=request.getParameter("coAppNameExist");
			        if(!CommonFunction.checkNull(coAppNameExist).equalsIgnoreCase("C")){
			        if (!CommonFunction.checkNull(leadIdVo.getCoApplbxCustomerId()).equalsIgnoreCase("")) {
				    String leadCustomerQuery="select count(1) from CR_LEAD_CUSTOMER_M where CUSTOMER_DOB=STR_TO_DATE('"+leadIdVo.getCoAppcustDOB()+"','%d-%m-%Y') and CUSTOMER_FNAME='"+leadIdVo.getCoAppfirstName()+"' AND IFNULL(CUSTOMER_MNAME,'')='"+leadIdVo.getCoAppmiddleName()+"' AND CUSTOMER_LNAME='"+leadIdVo.getCoApplastName()+"' and CUSTOMER_ID<>'"+leadIdVo.getCoApplbxCustomerId()+"' ";
				    int leadCustomerCount=Integer.parseInt(ConnectionDAO.singleReturn(leadCustomerQuery));
				    String dealCustomerQuery="select count(1) from CR_DEAL_CUSTOMER_M where CUSTOMER_DOB=STR_TO_DATE('"+leadIdVo.getCoAppcustDOB()+"','%d-%m-%Y') and CUSTOMER_FNAME='"+leadIdVo.getCoAppfirstName()+"' AND IFNULL(CUSTOMER_MNAME,'')='"+leadIdVo.getCoAppmiddleName()+"' AND CUSTOMER_LNAME='"+leadIdVo.getCoApplastName()+"' and IFNULL(LEAD_CUSTOMER_ID,'')<>'"+leadIdVo.getCoApplbxCustomerId()+"' ";
				    int dealCustomerCount=Integer.parseInt(ConnectionDAO.singleReturn(dealCustomerQuery));
				    if (leadCustomerCount > 0 || dealCustomerCount>0)
				      {
				       String nameExist = "Name and DOB already exist.Do you want to continue?";
				        request.setAttribute("nameExist", nameExist);
				        ArrayList genderIndiv = lp.getGenderList();
				        request.setAttribute("GenderCategory", genderIndiv);
				        String pincodeFlag = lp1.getPincodeFlag();
				        logger.info("value of pincode flag is ::" + pincodeFlag);
				        request.setAttribute("pincodeFlag", pincodeFlag);
				        ArrayList leadDetails = new ArrayList();
				        leadIdVo.setCoAppcontitutionCode(leadIdVo.getCoAppindconstitution());
				        leadDetails.add(leadIdVo);
				        request.setAttribute("leadDetails", leadDetails);
				        return mapping.findForward("success");
			      }
			    }
			      }
				    
			      }
			    }
			    if (leadIdVo.getCoAppcustomerType().equalsIgnoreCase("C"))
			    {
			      String coAppPanNo = leadIdVo.getCoAppcustPan();
			      String coAppcorconstitution = leadIdVo.getCoAppcorconstitution();
			      String CoappRelationship = leadIdVo.getCoApprelationship();
			      
			      String coAppregistration = leadIdVo.getCoAppregistrationNo();//Shashank For Novac
			      
			      logger.info("Co-Applicant Relatiinship---->>>>>> " + CoappRelationship);
			      String query3 = "";
			      String squery3 = "";
			      
			      String registrationNo = "";
			      String sregistrationNo = "";
			      if ((!CoappRelationship.equalsIgnoreCase("Existing")) && 
			        (!coAppcorconstitution.equalsIgnoreCase("PROPRIETOR")))
			     
			      {
			    	  if (!CommonFunction.checkNull(leadIdVo.getCoApplbxCustomerId()).equalsIgnoreCase("")) {
			          query3 = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and a.PAN='" + coAppPanNo + "' and a.CUSTOMER_ID<>" + leadIdVo.getCoApplbxCustomerId();
			          
			          squery3 = "select count(1) from gcd_customer_m where CUSTMER_PAN='" + coAppPanNo + "' and  ifnull(LEAD_CUSTOMER_ID,'')<>" + leadIdVo.getCoApplbxCustomerId();
			       
			          registrationNo = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and a.REGISTRATION_NO='" + coAppregistration + "' and a.CUSTOMER_ID<>" + leadIdVo.getCoApplbxCustomerId();
			          sregistrationNo = "select count(1) from gcd_customer_m where CUSTOMER_REGISTRATION_NO='" + coAppregistration + "' and  ifnull(LEAD_CUSTOMER_ID,'')<>" + leadIdVo.getCoApplbxCustomerId();
			        
			        } else {
			          query3 = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and a.PAN='" + coAppPanNo + "'";
			          squery3 = "select count(1) from gcd_customer_m where  CUSTMER_PAN='" + coAppPanNo + "' ";
			        
			          registrationNo = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and a.REGISTRATION_NO='" + coAppregistration + "'";
			          sregistrationNo = "select count(1) from gcd_customer_m where  CUSTOMER_REGISTRATION_NO='" + coAppregistration + "' ";
			        
			        }
			        logger.info("in execute() of IdividualDetailActionPage Query : " + query3);
			        logger.info("in execute() of IdividualDetailActionPage Query : " + squery3);
			        coAppPanCheck = ConnectionDAO.singleReturn(query3);
			        scoAppPanCheck = ConnectionDAO.singleReturn(squery3);
			        
			        logger.info("in execute() of IdividualDetailActionPage Query : " + registrationNo);
			        logger.info("in execute() of IdividualDetailActionPage Query : " + sregistrationNo);
			        coAppRegisCheck = ConnectionDAO.singleReturn(registrationNo);
			        scoAppRegisCheck = ConnectionDAO.singleReturn(sregistrationNo);
			      }
			    }

			    if (leadIdVo.getGaurcustomerType().equalsIgnoreCase("I"))
			    {
			      String gaurPanNo;
			  
			      if (leadIdVo.getGaurcustPanInd().toString().equalsIgnoreCase("undefined")) {
			        gaurPanNo = "";
			      }
			      else
			        gaurPanNo = leadIdVo.getGaurcustPanInd();
			      String gaurAadhaarNo;
			 
			      if (leadIdVo.getGauraadhaar().toString().equalsIgnoreCase("undefined")) {
			        gaurAadhaarNo = "";
			      }
			      else {
			        gaurAadhaarNo = leadIdVo.getGauraadhaar();
			      }
			      
			      //Shashank Starts For Novac 
			      String gaurvoterId;
					 
			      if (leadIdVo.getGaurvoterId().toString().equalsIgnoreCase("undefined")) {
			    	  gaurvoterId = "";
			      }
			      else {
			    	  gaurvoterId = leadIdVo.getGaurvoterId();
			      }
			      String gaurdlNumber;
					 
			      if (leadIdVo.getGaurdlNumber().toString().equalsIgnoreCase("undefined")) {
			    	  gaurdlNumber = "";
			      }
			      else {
			    	  gaurdlNumber = leadIdVo.getGaurdlNumber();
			      }
			      String gaurpassport;
					 
			      if (leadIdVo.getGaurpassport().toString().equalsIgnoreCase("undefined")) {
			    	  gaurpassport = "";
			      }
			      else {
			    	  gaurpassport = leadIdVo.getGaurpassport();
			      }
			      //Shashank Ends For Novac 
			      
			      String gaurcorconstitution = request.getParameter("gaurcorconstitution");
			      String GaurrRelationship = leadIdVo.getGaurrelationship();
			      logger.info("Gaur Relatiinship---->>>>>> " + GaurrRelationship);
			      String query5 = "";
			      String squery5 = "";
			      if (!GaurrRelationship.equalsIgnoreCase("Existing")) {
			    	  String q1="select PAN from cr_lead_customer_m where constitution='PROPRIETOR' and lead_id='"+leadIdVo.getLeadId()+"'";
			        	String propPan=ConnectionDAO.singleReturn(q1);
			        			
			        	if(!CommonFunction.checkNull(gaurPanNo).equalsIgnoreCase(propPan)){
			      if (!CommonFunction.checkNull(leadIdVo.getGaurlbxCustomerId()).equalsIgnoreCase("")) {
			          query5 = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.PAN='" + gaurPanNo + "' and a.PAN<>'') and a.CUSTOMER_ID<>" + leadIdVo.getGaurlbxCustomerId();
			        
			          squery5 = "select count(1) from gcd_customer_m where (CUSTMER_PAN='" + gaurPanNo + "' and CUSTMER_PAN<>'') and  ifnull(LEAD_CUSTOMER_ID,'')<>" + leadIdVo.getGaurlbxCustomerId();
			        } else {
			          query5 = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.PAN='" + gaurPanNo + "' and a.PAN<>'') ";
			          squery5 = "select count(1) from gcd_customer_m where (CUSTMER_PAN='" + gaurPanNo + "' and CUSTMER_PAN<>'') ";
			        }
			        	
			        logger.info("in execute() of IdividualDetailActionPage Query : " + query5);
			        logger.info("in execute() of IdividualDetailActionPage Query : " + squery5);
			        gaurPanCheck = ConnectionDAO.singleReturn(query5);
			        sgaurPanCheck = ConnectionDAO.singleReturn(squery5);
			        	}
			        	
			        	//Shashank Starts For Novac
			        	
			        		String gaurvoter = "";
					        String sgaurvoter = "";
					        String gaurdlNum = "";
					        String sgaurdlNum = "";
					        String gaurpass = "";
					        String sgaurpass = "";
					        if (!CommonFunction.checkNull(leadIdVo.getGaurlbxCustomerId()).equalsIgnoreCase("")) {
					        	gaurvoter = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.VOTER_ID='" + gaurvoterId + "' and a.VOTER_ID<>'') and a.CUSTOMER_ID<>" + leadIdVo.getGaurlbxCustomerId();
					          sgaurvoter = "select count(1) from gcd_customer_m where (VOTER_ID='" + gaurvoterId + "' and VOTER_ID<>'') and  ifnull(LEAD_CUSTOMER_ID,'')<>" + leadIdVo.getGaurlbxCustomerId();
					          
					          gaurdlNum = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.DRIVING_LICENSE='" + gaurdlNumber + "' and a.DRIVING_LICENSE<>'') and a.CUSTOMER_ID<>" + leadIdVo.getGaurlbxCustomerId();
					          sgaurdlNum = "select count(1) from gcd_customer_m where (DRIVING_LICENSE='" + gaurdlNumber + "' and DRIVING_LICENSE<>'') and  ifnull(LEAD_CUSTOMER_ID,'')<>" + leadIdVo.getGaurlbxCustomerId();
					       
					          gaurpass = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.PASSPORT_NUMBER='" + gaurpassport + "' and a.PASSPORT_NUMBER<>'') and a.CUSTOMER_ID<>" + leadIdVo.getGaurlbxCustomerId();
					          sgaurpass = "select count(1) from gcd_customer_m where (PASSPORT_NUMBER='" + gaurpassport + "' and PASSPORT_NUMBER<>'') and  ifnull(LEAD_CUSTOMER_ID,'')<>" + leadIdVo.getGaurlbxCustomerId();
					       
					        } else {
					        	gaurvoter = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.VOTER_ID='" + gaurvoterId + "' and a.VOTER_ID<>'') ";
					          sgaurvoter = "select count(1) from gcd_customer_m where (VOTER_ID='" + gaurvoterId + "' and VOTER_ID<>'') ";
					       
					          gaurdlNum = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.DRIVING_LICENSE='" + gaurdlNumber + "' and a.DRIVING_LICENSE<>'') ";
					          sgaurdlNum = "select count(1) from gcd_customer_m where (DRIVING_LICENSE='" + gaurdlNumber + "' and DRIVING_LICENSE<>'') ";
					        
					          gaurpass = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.PASSPORT_NUMBER='" + gaurpassport + "' and a.PASSPORT_NUMBER<>'') ";
					          sgaurpass = "select count(1) from gcd_customer_m where (PASSPORT_NUMBER='" + gaurpassport + "' and PASSPORT_NUMBER<>'') ";
					        }
					        logger.info("in execute() of IdividualDetailActionPage Query : " + gaurvoter);
					        logger.info("in execute() of IdividualDetailActionPage Query : " + sgaurvoter);
					        logger.info("in execute() of IdividualDetailActionPage Query : " + gaurdlNum);
					        logger.info("in execute() of IdividualDetailActionPage Query : " + sgaurdlNum);
					        logger.info("in execute() of IdividualDetailActionPage Query : " + gaurpass);
					        logger.info("in execute() of IdividualDetailActionPage Query : " + sgaurpass);
					        
					        gaurVoterCheck = ConnectionDAO.singleReturn(gaurvoter);
					        sgaurVoterCheck = ConnectionDAO.singleReturn(sgaurvoter);
					        gaurDlCheck = ConnectionDAO.singleReturn(gaurdlNum);
					        sgaurDlCheck = ConnectionDAO.singleReturn(sgaurdlNum);
					        gaurPassCheck = ConnectionDAO.singleReturn(gaurpass);
					        sgaurPassCheck = ConnectionDAO.singleReturn(sgaurpass);
					        
			        	//Shashank Ends For Novac
			        String query6 = "";
			        String squery6 = "";
			        if (!CommonFunction.checkNull(leadIdVo.getGaurlbxCustomerId()).equalsIgnoreCase("")) {
			          query6 = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.UID_NO='" + gaurAadhaarNo + "' and a.UID_NO<>'') and a.CUSTOMER_ID<>" + leadIdVo.getGaurlbxCustomerId();
			          
			          squery6 = "select count(1) from gcd_customer_m where (UID_NO='" + gaurAadhaarNo + "' and UID_NO<>'') and  ifnull(LEAD_CUSTOMER_ID,'')<>" + leadIdVo.getGaurlbxCustomerId();
			        } else {
			          query6 = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.UID_NO='" + gaurAadhaarNo + "' and a.UID_NO<>'') ";
			          squery6 = "select count(1) from gcd_customer_m where (UID_NO='" + gaurAadhaarNo + "' and UID_NO<>'') ";
			        }
			        logger.info("in execute() of IdividualDetailActionPage Query : " + query6);
			        gaurUidCheck = ConnectionDAO.singleReturn(query6);
			        logger.info("in execute() of IdividualDetailActionPage Query : " + squery6);
			        sgaurUidCheck = ConnectionDAO.singleReturn(squery6);
			        String gaurnameExist=request.getParameter("gaurnameExist");
			        if(!CommonFunction.checkNull(gaurnameExist).equalsIgnoreCase("G")){
			        if (!CommonFunction.checkNull(leadIdVo.getGaurlbxCustomerId()).equalsIgnoreCase("")) {
					    String leadCustomerQuery="select count(1) from CR_LEAD_CUSTOMER_M where CUSTOMER_DOB=STR_TO_DATE('"+leadIdVo.getGaurcustDOB()+"','%d-%m-%Y')  and CUSTOMER_FNAME='"+leadIdVo.getGaurfirstName()+"' AND IFNULL(CUSTOMER_MNAME,'')='"+leadIdVo.getGaurmiddleName()+"' AND CUSTOMER_LNAME='"+leadIdVo.getGaurlastName()+"' and CUSTOMER_ID<>'"+leadIdVo.getGaurlbxCustomerId()+"' ";
					    int leadCustomerCount=Integer.parseInt(ConnectionDAO.singleReturn(leadCustomerQuery));
					    String dealCustomerQuery="select count(1) from CR_DEAL_CUSTOMER_M where CUSTOMER_DOB=STR_TO_DATE('"+leadIdVo.getGaurcustDOB()+"','%d-%m-%Y')  and CUSTOMER_FNAME='"+leadIdVo.getGaurfirstName()+"' AND IFNULL(CUSTOMER_MNAME,'')='"+leadIdVo.getGaurmiddleName()+"' AND CUSTOMER_LNAME='"+leadIdVo.getGaurlastName()+"'  and IFNULL(LEAD_CUSTOMER_ID,'')<>'"+leadIdVo.getGaurlbxCustomerId()+"' ";
					    int dealCustomerCount=Integer.parseInt(ConnectionDAO.singleReturn(dealCustomerQuery));
					    if (leadCustomerCount > 0 || dealCustomerCount>0)
					      {
					        gaurnameExist = "Name and DOB already exist.Do you want to continue?";
					        request.setAttribute("gaurnameExist", gaurnameExist);
					        ArrayList genderIndiv = lp.getGenderList();
					        request.setAttribute("GenderCategory", genderIndiv);
					        String pincodeFlag = lp1.getPincodeFlag();
					        logger.info("value of pincode flag is ::" + pincodeFlag);
					        request.setAttribute("pincodeFlag", pincodeFlag);
					        ArrayList leadDetails = new ArrayList();
					        leadIdVo.setGaurcontitutionCode(leadIdVo.getGaurindconstitution());
					        leadDetails.add(leadIdVo);
					        request.setAttribute("leadDetails", leadDetails);
					        return mapping.findForward("success");
			      }
			    }
			      }
			      }
			      
			      }

			    if (leadIdVo.getGaurcustomerType().equalsIgnoreCase("C"))
			    {
			      String gaurPanNo = leadIdVo.getGaurcustPan();

			      String gaurcorconstitution = leadIdVo.getGaurcorconstitution();
			      String GaurrRelationship = leadIdVo.getGaurrelationship();
			      
			      String gaurregistration = leadIdVo.getGaurregistrationNo();//Shahsank For Novac
			      
			      logger.info("Gaur Relatiinship---->>>>>> " + GaurrRelationship);
			      String query5 = "";
			      String squery5 = "";
			      
			      String registrationNo = "";
			      String sregistrationNo = "";
			      if ((!GaurrRelationship.equalsIgnoreCase("Existing")) && 
			        (!gaurcorconstitution.equalsIgnoreCase("PROPRIETOR")))
			      
			      {
			    	  if (!CommonFunction.checkNull(leadIdVo.getGaurlbxCustomerId()).equalsIgnoreCase("")) {
			          query5 = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and a.PAN='" + gaurPanNo + "' and a.CUSTOMER_ID<>" + leadIdVo.getGaurlbxCustomerId();
			        
			          squery5 = "select count(1) from gcd_customer_m where CUSTMER_PAN='" + gaurPanNo + "' and  ifnull(LEAD_CUSTOMER_ID,'')<>" + leadIdVo.getGaurlbxCustomerId();
			        
			          registrationNo = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and a.REGISTRATION_NO='" + gaurregistration + "' and a.CUSTOMER_ID<>" + leadIdVo.getGaurlbxCustomerId();  
			          sregistrationNo = "select count(1) from gcd_customer_m where CUSTOMER_REGISTRATION_NO='" + gaurregistration + "' and  ifnull(LEAD_CUSTOMER_ID,'')<>" + leadIdVo.getGaurlbxCustomerId();
			        
			        } else {
			          query5 = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and a.PAN='" + gaurPanNo + "'";
			          squery5 = "select count(1) from gcd_customer_m where CUSTMER_PAN='" + gaurPanNo + "' ";
			        
			          registrationNo = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and a.REGISTRATION_NO='" + gaurregistration + "'";
			          sregistrationNo = "select count(1) from gcd_customer_m where CUSTOMER_REGISTRATION_NO='" + gaurregistration + "' ";
			        
			        }
			        logger.info("in execute() of IdividualDetailActionPage Query : " + query5);
			        logger.info("in execute() of IdividualDetailActionPage Query : " + squery5);
			        gaurPanCheck = ConnectionDAO.singleReturn(query5);
			        sgaurPanCheck = ConnectionDAO.singleReturn(squery5);
			        
			        logger.info("in execute() of IdividualDetailActionPage Query : " + registrationNo);
			        logger.info("in execute() of IdividualDetailActionPage Query : " + sregistrationNo);
			        gaurRegisCheck = ConnectionDAO.singleReturn(registrationNo);
			        sgaurRegisCheck = ConnectionDAO.singleReturn(sregistrationNo);
			      }

			    }

			    if (appPanCheck == null)
			      appPanCount = 0;
			    else
			      appPanCount = Integer.parseInt(appPanCheck);
			    logger.info("appPanCount :" + appPanCount);
			    if (appUidCheck == null)
			      appUidCount = 0;
			    else
			      appUidCount = Integer.parseInt(appUidCheck);
			    logger.info("appUidCount :" + appUidCount);

			    if (sappPanCheck == null)
			      sappPanCount = 0;
			    else
			      sappPanCount = Integer.parseInt(sappPanCheck);
			    logger.info("sappPanCount :" + sappPanCount);
			    if (sappUidCheck == null)
			      sappUidCount = 0;
			    else
			      sappUidCount = Integer.parseInt(sappUidCheck);
			    logger.info("sappUidCount :" + sappUidCount);

			    if (coAppPanCheck == null)
			      coAppPanCount = 0;
			    else
			      coAppPanCount = Integer.parseInt(coAppPanCheck);
			    logger.info("coAppPanCount :" + coAppPanCount);
			    if (coAppUidCheck == null)
			      coAppUidCount = 0;
			    else
			      coAppUidCount = Integer.parseInt(coAppUidCheck);
			    logger.info("coAppUidCount :" + coAppUidCount);

			    if (scoAppPanCheck == null)
			      scoAppPanCount = 0;
			    else
			      scoAppPanCount = Integer.parseInt(scoAppPanCheck);
			    logger.info("scoAppPanCount :" + scoAppPanCount);
			    if (scoAppUidCheck == null)
			      scoAppUidCount = 0;
			    else
			      scoAppUidCount = Integer.parseInt(scoAppUidCheck);
			    logger.info("scoAppUidCount :" + scoAppUidCount);

			    if (gaurPanCheck == null)
			      gaurPanCount = 0;
			    else
			      gaurPanCount = Integer.parseInt(gaurPanCheck);
			    logger.info("gaurPanCount :" + gaurPanCount);
			    if (gaurUidCheck == null)
			      gaurUidCount = 0;
			    else
			      gaurUidCount = Integer.parseInt(gaurUidCheck);
			    logger.info("gaurUidCount :" + gaurUidCount);

			    if (sgaurPanCheck == null)
			      sgaurPanCount = 0;
			    else
			      sgaurPanCount = Integer.parseInt(sgaurPanCheck);
			    logger.info("sgaurPanCount :" + sgaurPanCount);
			    if (sgaurUidCheck == null)
			      sgaurUidCount = 0;
			    else
			      sgaurUidCount = Integer.parseInt(sgaurUidCheck);
			    logger.info("sgaurUidCount :" + sgaurUidCount);

			    appPanCount += sappPanCount;
			    coAppPanCount += scoAppPanCount;
			    gaurPanCount += sgaurPanCount;
			    appUidCount += sappUidCount;
			    coAppUidCount += scoAppUidCount;
			    gaurUidCount += sgaurUidCount;

			    //Shashank Statrs For Novac
			    if (appVoterCheck == null)
			    	appVoterCount = 0;
			    	else
			    	appVoterCount = Integer.parseInt(appVoterCheck);
			    	logger.info("appVoterCount :" + appVoterCount);
			    
			    	if (appDrivingCheck == null)
			    	appDlCount = 0;
			    	else
			    	appDlCount = Integer.parseInt(appDrivingCheck);
			    	logger.info("appDlCount :" + appDlCount);

			    	if (appPassportCheck == null)
			    	appPassCount = 0;
			    	else
			    	appPassCount = Integer.parseInt(appPassportCheck);
			    	logger.info("appPassCount :" + appPassCount);

			    	if (sappVoterCheck == null)
			    	sappVoterCount = 0;
			    	else
			    	sappVoterCount = Integer.parseInt(sappVoterCheck);
			    	logger.info("sappVoterCount :" + sappVoterCount);

			    	if (sappDrivingCheck == null)
			    	sappDlCount = 0;
			    	else
			    	sappDlCount = Integer.parseInt(sappDrivingCheck);
			    	logger.info("sappDlCount :" + sappDlCount);

			    	if (sappPassportCheck == null)
			    	sappPassCount = 0;
			    	else
			    	sappPassCount = Integer.parseInt(sappPassportCheck);
			    	logger.info("sappPassCount :" + sappPassCount);

			    	if (coAppVoterCheck == null)
			    	coAppVoterCount = 0;
			    	else
			    	coAppVoterCount = Integer.parseInt(coAppVoterCheck);
			    	logger.info("coAppVoterCount :" + coAppVoterCount);

			    	if (coAppDlCheck == null)
			    	coAppDlCount = 0;
			    	else
			    	coAppDlCount = Integer.parseInt(coAppDlCheck);
			    	logger.info("coAppDlCount :" + coAppDlCount);

			    	if (coAppPassCheck == null)
			    	coAppPassCount = 0;
			    	else
			    	coAppPassCount = Integer.parseInt(coAppPassCheck);
			    	logger.info("coAppPassCount :" + coAppPassCount);

			    	if (scoAppVoterCheck == null)
			    	scoAppVoterCount = 0;
			    	else
			    	scoAppVoterCount = Integer.parseInt(scoAppVoterCheck);
			    	logger.info("scoAppVoterCount :" + scoAppVoterCount);

			    	if (scoAppDlCheck == null)
			    	scoAppDlCount = 0;
			    	else
			    	scoAppDlCount = Integer.parseInt(scoAppDlCheck);
			    	logger.info("scoAppDlCount :" + scoAppDlCount);

			    	if (scoAppPassCheck == null)
			    	scoAppPassCount = 0;
			    	else
			    	scoAppPassCount = Integer.parseInt(scoAppPassCheck);
			    	logger.info("scoAppPassCount :" + scoAppPassCount);

			    	if (gaurVoterCheck == null)
			    	gaurVoterCount = 0;
			    	else
			    	gaurVoterCount = Integer.parseInt(gaurVoterCheck);
			    	logger.info("gaurVoterCount :" + gaurVoterCount);

			    	if (gaurDlCheck == null)
			    	gaurDlCount = 0;
			    	else
			    	gaurDlCount = Integer.parseInt(gaurDlCheck);
			    	logger.info("gaurDlCount :" + gaurDlCount);

			    	if (gaurPassCheck == null)
			    	gaurPassCount = 0;
			    	else
			    	gaurPassCount = Integer.parseInt(gaurPassCheck);
			    	logger.info("gaurPassCount :" + gaurPassCount);

			    	if (sgaurVoterCheck == null)
			    	sgaurVoterCount = 0;
			    	else
			    	sgaurVoterCount = Integer.parseInt(sgaurVoterCheck);
			    	logger.info("sgaurVoterCount :" + sgaurVoterCount);

			    	if (sgaurDlCheck == null)
			    	sgaurDlCount = 0;
			    	else
			    	sgaurDlCount = Integer.parseInt(sgaurDlCheck);
			    	logger.info("sgaurDlCount :" + sgaurDlCount);

			    	if (sgaurPassCheck == null)
			    	sgaurPassCount = 0;
			    	else
			    	sgaurPassCount = Integer.parseInt(sgaurPassCheck);
			    	logger.info("sgaurPassCount :" + sgaurPassCount);
			    	
			    	if (appRegisCheck == null)
			    		appRegisCount = 0;
			    		else
			    		appRegisCount = Integer.parseInt(appRegisCheck);
			    		logger.info("appRegisCount :" + appRegisCount);
			    		if (sappRegisCheck == null)
			    		sappRegisCount = 0;
			    		else
			    		sappRegisCount = Integer.parseInt(sappRegisCheck);
			    		logger.info("sappRegisCount :" + sappRegisCount);
			    		if (coAppRegisCheck == null)
			    		coAppRegisCount = 0;
			    		else
			    		coAppRegisCount = Integer.parseInt(coAppRegisCheck);
			    		logger.info("coAppRegisCount :" + coAppRegisCount);
			    		if (scoAppRegisCheck == null)
			    		scoAppRegisCount = 0;
			    		else
			    		scoAppRegisCount = Integer.parseInt(scoAppRegisCheck);
			    		logger.info("scoAppRegisCount :" + scoAppRegisCount);
			    		if (gaurRegisCheck == null)
			    		gaurRegisCount = 0;
			    		else
			    		gaurRegisCount = Integer.parseInt(gaurRegisCheck);
			    		logger.info("gaurRegisCount :" + gaurRegisCount);
			    		if (sgaurRegisCheck == null)
			    		sgaurRegisCount = 0;
			    		else
			    		sgaurRegisCount = Integer.parseInt(sgaurRegisCheck);
			    		logger.info("sgaurRegisCount :" + sgaurRegisCount);

			    	appVoterCount += sappVoterCount;
			    	appDlCount += sappDlCount;
			    	appPassCount += sappPassCount;
			    	coAppVoterCount += scoAppVoterCount;
			    	coAppDlCount += scoAppDlCount;
			    	coAppPassCount += scoAppPassCount;
			    	gaurVoterCount += sgaurVoterCount;
			    	gaurDlCount += sgaurDlCount;
			    	gaurPassCount += sgaurPassCount;
			    	
			    	appRegisCount += sappRegisCount;
			    	coAppRegisCount += scoAppRegisCount;
			    	gaurRegisCount += sgaurRegisCount;

			    	 //Shashank Ends For Novac
			    	
			    	
			    if ((appPanCount > 0) || (coAppPanCount > 0) || (gaurPanCount > 0)) {
			      leadIdVo.setContitutionCode(leadIdVo.getIndconstitution());
			      leadIdVo.setCoAppcontitutionCode(leadIdVo.getCoAppindconstitution());
			      leadIdVo.setGaurcontitutionCode(leadIdVo.getGaurindconstitution());
			      if (appPanCount > 0)
			      {
			        panNoExist = "Applicant Pan No. already exist.";
			        request.setAttribute("panNoExist", panNoExist);
			        ArrayList genderIndiv = lp.getGenderList();
			        request.setAttribute("GenderCategory", genderIndiv);
			        String pincodeFlag = lp1.getPincodeFlag();
			        logger.info("value of pincode flag is ::" + pincodeFlag);
			        request.setAttribute("pincodeFlag", pincodeFlag);
			        if (leadIdVo.getCoAppcustPanInd().toString().equalsIgnoreCase("undefined"))
			          leadIdVo.setCoAppcustPanInd("");
			        if (leadIdVo.getCoAppaadhaar().toString().equalsIgnoreCase("undefined"))
			          leadIdVo.setCoAppaadhaar("");
			        if (leadIdVo.getGaurcustPanInd().toString().equalsIgnoreCase("undefined"))
			          leadIdVo.setGaurcustPanInd("");
			        if (leadIdVo.getGauraadhaar().toString().equalsIgnoreCase("undefined"))
			          leadIdVo.setGauraadhaar("");
			        ArrayList leadDetails = new ArrayList();
			        leadIdVo.setContitutionCode(leadIdVo.getIndconstitution());
			        leadDetails.add(leadIdVo);
			        logger.info("consititution  valu by rohit sahay---------------------------------" + leadIdVo.getContitutionCode());

			        request.setAttribute("leadDetails", leadDetails);
			        return mapping.findForward("success");
			      }
			      if (coAppPanCount > 0) {
			        panNoExist = "Co-Applicant Pan No. already exist.";
			        request.setAttribute("panNoExist", panNoExist);
			        ArrayList genderIndiv = lp.getGenderList();
			        request.setAttribute("GenderCategory", genderIndiv);
			        String pincodeFlag = lp1.getPincodeFlag();
			        logger.info("value of pincode flag is ::" + pincodeFlag);
			        request.setAttribute("pincodeFlag", pincodeFlag);
			        ArrayList leadDetails = new ArrayList();
			        leadIdVo.setCoAppcontitutionCode(leadIdVo.getCoAppindconstitution());
			        leadDetails.add(leadIdVo);
			        request.setAttribute("leadDetails", leadDetails);
			        return mapping.findForward("success");
			      }
			      if (gaurPanCount > 0) {
			        panNoExist = "Guarantor Pan No. already exist.";
			        request.setAttribute("panNoExist", panNoExist);
			        ArrayList genderIndiv = lp.getGenderList();
			        request.setAttribute("GenderCategory", genderIndiv);
			        String pincodeFlag = lp1.getPincodeFlag();
			        logger.info("value of pincode flag is ::" + pincodeFlag);
			        request.setAttribute("pincodeFlag", pincodeFlag);
			        ArrayList leadDetails = new ArrayList();
			        leadIdVo.setGaurcontitutionCode(leadIdVo.getGaurindconstitution());
			        leadDetails.add(leadIdVo);
			        request.setAttribute("leadDetails", leadDetails);
			        return mapping.findForward("success");
			      }
			    }
			    else if ((appUidCount > 0) || (coAppUidCount > 0) || (gaurUidCount > 0)) {
			      leadIdVo.setContitutionCode(leadIdVo.getIndconstitution());
			      leadIdVo.setCoAppcontitutionCode(leadIdVo.getCoAppindconstitution());
			      leadIdVo.setGaurcontitutionCode(leadIdVo.getGaurindconstitution());
			      if (appUidCount > 0) {
			        uidExist = "Applicant Aadhar No. already exist.";
			        request.setAttribute("uidExist", uidExist);
			        ArrayList genderIndiv = lp.getGenderList();
			        request.setAttribute("GenderCategory", genderIndiv);
			        String pincodeFlag = lp1.getPincodeFlag();
			        logger.info("value of pincode flag is ::" + pincodeFlag);
			        request.setAttribute("pincodeFlag", pincodeFlag);
			        if (leadIdVo.getCoAppcustPanInd().toString().equalsIgnoreCase("undefined"))
			          leadIdVo.setCoAppcustPanInd("");
			        if (leadIdVo.getCoAppaadhaar().toString().equalsIgnoreCase("undefined"))
			          leadIdVo.setCoAppaadhaar("");
			        if (leadIdVo.getGaurcustPanInd().toString().equalsIgnoreCase("undefined"))
			          leadIdVo.setGaurcustPanInd("");
			        if (leadIdVo.getGauraadhaar().toString().equalsIgnoreCase("undefined"))
			          leadIdVo.setGauraadhaar("");
			        ArrayList leadDetails = new ArrayList();
			        leadIdVo.setContitutionCode(leadIdVo.getIndconstitution());
			        leadDetails.add(leadIdVo);
			        request.setAttribute("leadDetails", leadDetails);
			        return mapping.findForward("success");
			      }
			      if (coAppUidCount > 0)
			      {
			        uidExist = "Co-Applicant Aadhar No. already exist.";
			        request.setAttribute("uidExist", uidExist);
			        ArrayList leadDetails = new ArrayList();
			        leadIdVo.setCoAppcontitutionCode(leadIdVo.getCoAppindconstitution());
			        leadDetails.add(leadIdVo);
			        ArrayList genderIndiv = lp.getGenderList();
			        request.setAttribute("GenderCategory", genderIndiv);
			        String pincodeFlag = lp1.getPincodeFlag();
			        logger.info("value of pincode flag is ::" + pincodeFlag);
			        request.setAttribute("pincodeFlag", pincodeFlag);
			        request.setAttribute("leadDetails", leadDetails);
			        return mapping.findForward("success");
			      }
			      if (gaurUidCount > 0)
			      {
			        uidExist = "Guarantor Aadhar No. already exist.";
			        request.setAttribute("uidExist", uidExist);
			        ArrayList leadDetails = new ArrayList();
			        leadIdVo.setGaurcontitutionCode(leadIdVo.getGaurindconstitution());
			        leadDetails.add(leadIdVo);
			        ArrayList genderIndiv = lp.getGenderList();
			        request.setAttribute("GenderCategory", genderIndiv);
			        String pincodeFlag = lp1.getPincodeFlag();
			        logger.info("value of pincode flag is ::" + pincodeFlag);
			        request.setAttribute("pincodeFlag", pincodeFlag);
			        request.setAttribute("leadDetails", leadDetails);
			        return mapping.findForward("success");
			      }

			    }

			    //Shashank Starts For Novac
			    
			    else if ((appVoterCount > 0) || (coAppVoterCount > 0) || (gaurVoterCount > 0)) {
				      leadIdVo.setContitutionCode(leadIdVo.getIndconstitution());
				      leadIdVo.setCoAppcontitutionCode(leadIdVo.getCoAppindconstitution());
				      leadIdVo.setGaurcontitutionCode(leadIdVo.getGaurindconstitution());
				      if (appVoterCount > 0) {
				    	  voterExist = "Applicant Voter Id already exist.";
				        request.setAttribute("voterExist", voterExist);
				        ArrayList genderIndiv = lp.getGenderList();
				        request.setAttribute("GenderCategory", genderIndiv);
				        String pincodeFlag = lp1.getPincodeFlag();
				        logger.info("value of pincode flag is ::" + pincodeFlag);
				        request.setAttribute("pincodeFlag", pincodeFlag);
				        if (leadIdVo.getCoAppcustPanInd().toString().equalsIgnoreCase("undefined"))
				          leadIdVo.setCoAppcustPanInd("");
				        if (leadIdVo.getCoAppaadhaar().toString().equalsIgnoreCase("undefined"))
				          leadIdVo.setCoAppaadhaar("");
				        if (leadIdVo.getGaurcustPanInd().toString().equalsIgnoreCase("undefined"))
				          leadIdVo.setGaurcustPanInd("");
				        if (leadIdVo.getGauraadhaar().toString().equalsIgnoreCase("undefined"))
				          leadIdVo.setGauraadhaar("");
				        ArrayList leadDetails = new ArrayList();
				        leadIdVo.setContitutionCode(leadIdVo.getIndconstitution());
				        leadDetails.add(leadIdVo);
				        request.setAttribute("leadDetails", leadDetails);
				        return mapping.findForward("success");
				      }
				      if (coAppVoterCount > 0)
				      {
				    	voterExist = "Co-Applicant Voter Id already exist.";
				        request.setAttribute("voterExist", voterExist);
				        ArrayList leadDetails = new ArrayList();
				        leadIdVo.setCoAppcontitutionCode(leadIdVo.getCoAppindconstitution());
				        leadDetails.add(leadIdVo);
				        ArrayList genderIndiv = lp.getGenderList();
				        request.setAttribute("GenderCategory", genderIndiv);
				        String pincodeFlag = lp1.getPincodeFlag();
				        logger.info("value of pincode flag is ::" + pincodeFlag);
				        request.setAttribute("pincodeFlag", pincodeFlag);
				        request.setAttribute("leadDetails", leadDetails);
				        return mapping.findForward("success");
				      }
				      if (gaurVoterCount > 0)
				      {
				    	voterExist = "Guarantor Voter Id already exist.";
				        request.setAttribute("voterExist", voterExist);
				        ArrayList leadDetails = new ArrayList();
				        leadIdVo.setGaurcontitutionCode(leadIdVo.getGaurindconstitution());
				        leadDetails.add(leadIdVo);
				        ArrayList genderIndiv = lp.getGenderList();
				        request.setAttribute("GenderCategory", genderIndiv);
				        String pincodeFlag = lp1.getPincodeFlag();
				        logger.info("value of pincode flag is ::" + pincodeFlag);
				        request.setAttribute("pincodeFlag", pincodeFlag);
				        request.setAttribute("leadDetails", leadDetails);
				        return mapping.findForward("success");
				      }

				    }
			    
			    
			    else if ((appDlCount > 0) || (coAppDlCount > 0) || (gaurDlCount > 0)) {
				      leadIdVo.setContitutionCode(leadIdVo.getIndconstitution());
				      leadIdVo.setCoAppcontitutionCode(leadIdVo.getCoAppindconstitution());
				      leadIdVo.setGaurcontitutionCode(leadIdVo.getGaurindconstitution());
				      if (appDlCount > 0) {
				    	dlExist = "Applicant Driving Licence already exist.";
				        request.setAttribute("dlExist", dlExist);
				        ArrayList genderIndiv = lp.getGenderList();
				        request.setAttribute("GenderCategory", genderIndiv);
				        String pincodeFlag = lp1.getPincodeFlag();
				        logger.info("value of pincode flag is ::" + pincodeFlag);
				        request.setAttribute("pincodeFlag", pincodeFlag);
				        if (leadIdVo.getCoAppcustPanInd().toString().equalsIgnoreCase("undefined"))
				          leadIdVo.setCoAppcustPanInd("");
				        if (leadIdVo.getCoAppaadhaar().toString().equalsIgnoreCase("undefined"))
				          leadIdVo.setCoAppaadhaar("");
				        if (leadIdVo.getGaurcustPanInd().toString().equalsIgnoreCase("undefined"))
				          leadIdVo.setGaurcustPanInd("");
				        if (leadIdVo.getGauraadhaar().toString().equalsIgnoreCase("undefined"))
				          leadIdVo.setGauraadhaar("");
				        ArrayList leadDetails = new ArrayList();
				        leadIdVo.setContitutionCode(leadIdVo.getIndconstitution());
				        leadDetails.add(leadIdVo);
				        request.setAttribute("leadDetails", leadDetails);
				        return mapping.findForward("success");
				      }
				      if (coAppDlCount > 0)
				      {
				    	dlExist = "Co-Applicant Driving Licence already exist.";
				        request.setAttribute("dlExist", dlExist);
				        ArrayList leadDetails = new ArrayList();
				        leadIdVo.setCoAppcontitutionCode(leadIdVo.getCoAppindconstitution());
				        leadDetails.add(leadIdVo);
				        ArrayList genderIndiv = lp.getGenderList();
				        request.setAttribute("GenderCategory", genderIndiv);
				        String pincodeFlag = lp1.getPincodeFlag();
				        logger.info("value of pincode flag is ::" + pincodeFlag);
				        request.setAttribute("pincodeFlag", pincodeFlag);
				        request.setAttribute("leadDetails", leadDetails);
				        return mapping.findForward("success");
				      }
				      if (gaurDlCount > 0)
				      {
				    	dlExist = "Guarantor Driving Licence already exist.";
				        request.setAttribute("dlExist", dlExist);
				        ArrayList leadDetails = new ArrayList();
				        leadIdVo.setGaurcontitutionCode(leadIdVo.getGaurindconstitution());
				        leadDetails.add(leadIdVo);
				        ArrayList genderIndiv = lp.getGenderList();
				        request.setAttribute("GenderCategory", genderIndiv);
				        String pincodeFlag = lp1.getPincodeFlag();
				        logger.info("value of pincode flag is ::" + pincodeFlag);
				        request.setAttribute("pincodeFlag", pincodeFlag);
				        request.setAttribute("leadDetails", leadDetails);
				        return mapping.findForward("success");
				      }

				    }
			    
			    else if ((appPassCount > 0) || (coAppPassCount > 0) || (gaurPassCount > 0)) {
				      leadIdVo.setContitutionCode(leadIdVo.getIndconstitution());
				      leadIdVo.setCoAppcontitutionCode(leadIdVo.getCoAppindconstitution());
				      leadIdVo.setGaurcontitutionCode(leadIdVo.getGaurindconstitution());
				      if (appPassCount > 0) {
				    	passsportExist = "Applicant Passport No. already exist.";
				        request.setAttribute("passsportExist", passsportExist);
				        ArrayList genderIndiv = lp.getGenderList();
				        request.setAttribute("GenderCategory", genderIndiv);
				        String pincodeFlag = lp1.getPincodeFlag();
				        logger.info("value of pincode flag is ::" + pincodeFlag);
				        request.setAttribute("pincodeFlag", pincodeFlag);
				        if (leadIdVo.getCoAppcustPanInd().toString().equalsIgnoreCase("undefined"))
				          leadIdVo.setCoAppcustPanInd("");
				        if (leadIdVo.getCoAppaadhaar().toString().equalsIgnoreCase("undefined"))
				          leadIdVo.setCoAppaadhaar("");
				        if (leadIdVo.getGaurcustPanInd().toString().equalsIgnoreCase("undefined"))
				          leadIdVo.setGaurcustPanInd("");
				        if (leadIdVo.getGauraadhaar().toString().equalsIgnoreCase("undefined"))
				          leadIdVo.setGauraadhaar("");
				        ArrayList leadDetails = new ArrayList();
				        leadIdVo.setContitutionCode(leadIdVo.getIndconstitution());
				        leadDetails.add(leadIdVo);
				        request.setAttribute("leadDetails", leadDetails);
				        return mapping.findForward("success");
				      }
				      if (coAppPassCount > 0)
				      {
				    	passsportExist = "Co-Applicant Passport No. already exist.";
				        request.setAttribute("passsportExist", passsportExist);
				        ArrayList leadDetails = new ArrayList();
				        leadIdVo.setCoAppcontitutionCode(leadIdVo.getCoAppindconstitution());
				        leadDetails.add(leadIdVo);
				        ArrayList genderIndiv = lp.getGenderList();
				        request.setAttribute("GenderCategory", genderIndiv);
				        String pincodeFlag = lp1.getPincodeFlag();
				        logger.info("value of pincode flag is ::" + pincodeFlag);
				        request.setAttribute("pincodeFlag", pincodeFlag);
				        request.setAttribute("leadDetails", leadDetails);
				        return mapping.findForward("success");
				      }
				      if (gaurPassCount > 0)
				      {
				    	passsportExist = "Guarantor Passport No. already exist.";
				        request.setAttribute("passsportExist", passsportExist);
				        ArrayList leadDetails = new ArrayList();
				        leadIdVo.setGaurcontitutionCode(leadIdVo.getGaurindconstitution());
				        leadDetails.add(leadIdVo);
				        ArrayList genderIndiv = lp.getGenderList();
				        request.setAttribute("GenderCategory", genderIndiv);
				        String pincodeFlag = lp1.getPincodeFlag();
				        logger.info("value of pincode flag is ::" + pincodeFlag);
				        request.setAttribute("pincodeFlag", pincodeFlag);
				        request.setAttribute("leadDetails", leadDetails);
				        return mapping.findForward("success");
				      }

				    }
			    
			    else if ((appRegisCount > 0) || (coAppRegisCount > 0) || (gaurRegisCount > 0)) {
				      leadIdVo.setContitutionCode(leadIdVo.getIndconstitution());
				      leadIdVo.setCoAppcontitutionCode(leadIdVo.getCoAppindconstitution());
				      leadIdVo.setGaurcontitutionCode(leadIdVo.getGaurindconstitution());
				      if (appRegisCount > 0) {
				    	  regisExist = "Applicant Registration No. already exist.";
				        request.setAttribute("regisExist", regisExist);
				        ArrayList genderIndiv = lp.getGenderList();
				        request.setAttribute("GenderCategory", genderIndiv);
				        String pincodeFlag = lp1.getPincodeFlag();
				        logger.info("value of pincode flag is ::" + pincodeFlag);
				        request.setAttribute("pincodeFlag", pincodeFlag);
				        if (leadIdVo.getCoAppcustPanInd().toString().equalsIgnoreCase("undefined"))
				          leadIdVo.setCoAppcustPanInd("");
				        if (leadIdVo.getCoAppaadhaar().toString().equalsIgnoreCase("undefined"))
				          leadIdVo.setCoAppaadhaar("");
				        if (leadIdVo.getGaurcustPanInd().toString().equalsIgnoreCase("undefined"))
				          leadIdVo.setGaurcustPanInd("");
				        if (leadIdVo.getGauraadhaar().toString().equalsIgnoreCase("undefined"))
				          leadIdVo.setGauraadhaar("");
				        ArrayList leadDetails = new ArrayList();
				        leadIdVo.setContitutionCode(leadIdVo.getIndconstitution());
				        leadDetails.add(leadIdVo);
				        request.setAttribute("leadDetails", leadDetails);
				        return mapping.findForward("success");
				      }
				      if (coAppRegisCount > 0)
				      {
				    	regisExist = "Co-Applicant Registration No. already exist.";
				        request.setAttribute("regisExist", regisExist);
				        ArrayList leadDetails = new ArrayList();
				        leadIdVo.setCoAppcontitutionCode(leadIdVo.getCoAppindconstitution());
				        leadDetails.add(leadIdVo);
				        ArrayList genderIndiv = lp.getGenderList();
				        request.setAttribute("GenderCategory", genderIndiv);
				        String pincodeFlag = lp1.getPincodeFlag();
				        logger.info("value of pincode flag is ::" + pincodeFlag);
				        request.setAttribute("pincodeFlag", pincodeFlag);
				        request.setAttribute("leadDetails", leadDetails);
				        return mapping.findForward("success");
				      }
				      if (gaurRegisCount > 0)
				      {
				    	regisExist = "Guarantor Registration No. already exist.";
				        request.setAttribute("regisExist", regisExist);
				        ArrayList leadDetails = new ArrayList();
				        leadIdVo.setGaurcontitutionCode(leadIdVo.getGaurindconstitution());
				        leadDetails.add(leadIdVo);
				        ArrayList genderIndiv = lp.getGenderList();
				        request.setAttribute("GenderCategory", genderIndiv);
				        String pincodeFlag = lp1.getPincodeFlag();
				        logger.info("value of pincode flag is ::" + pincodeFlag);
				        request.setAttribute("pincodeFlag", pincodeFlag);
				        request.setAttribute("leadDetails", leadDetails);
				        return mapping.findForward("success");
				      }

				    }
			    
			    
			    //Shashank Ends For Novac

			    if (count1 > 0)
			    {
			      request.setAttribute("group", groupExist);
			    }
			    else
			    {
			      String result1 = null;
			       if(CommonFunction.checkNull(custType).equalsIgnoreCase("coapp")){
				        	result1 = lp.saveCoappLead(leadIdVo);
				        }else if(CommonFunction.checkNull(custType).equalsIgnoreCase("guar")){
				        	result1 = lp.saveGuarLead(leadIdVo);
				        }else{
			    	  result1 = lp.saveNewLead(leadIdVo);
				        }
			      
			      logger.info("result" + result);
			    }

			    ArrayList constitutionlist = lp.getContitutionList();
			    ArrayList corconstitutionlist = lp.getCorContitutionList();
			    ArrayList indconstitutionlist = lp.getIndContitutionList();
			    ArrayList businessSegmentList = lp.getBusinessSegmentList();
			    ArrayList eduDetail = lp.getEduDetailList();
			    ArrayList genderIndiv = lp.getGenderList();
			    //ArrayList indConstSubprofileList = lp.getIndConSubprofile();
			    request.setAttribute("GenderCategory", genderIndiv);
			    session.setAttribute("addresstypeList", addresstypeList);
			    session.setAttribute("businessSegmentList", businessSegmentList);
			    session.setAttribute("constitutionlist", constitutionlist);
			    session.setAttribute("indconstitutionlist", indconstitutionlist);
			    session.setAttribute("corconstitutionlist", corconstitutionlist);
			   // session.setAttribute("indConstSubprofileList", indConstSubprofileList);
			    if (leadIdVo.getLeadId().equalsIgnoreCase(""))
			    {
			      String LeadID = "select max(lead_id) from cr_lead_customer_m ";
			      leadIdVo.setLeadId(ConnectionDAO.singleReturn(LeadID));
			    }
			   
			    ArrayList leadDetails = lp.getLeadHeader(leadIdVo.getLeadId());
			    request.setAttribute("leadDetails", leadDetails);
			    String coappQuery = "select count(1) from cr_lead_customer_m where lead_id='" + leadIdVo.getLeadId() + "' and CUSTOMER_ROLE_TYPE='COAPPL' ";
			    String coapp = ConnectionDAO.singleReturn(coappQuery);
			    String gaurQuery = "select count(1) from cr_lead_customer_m where lead_id='" + leadIdVo.getLeadId() + "' and CUSTOMER_ROLE_TYPE='GUARANTOR' ";
			    String gaur = ConnectionDAO.singleReturn(gaurQuery);
			    if (!CommonFunction.checkNull(coapp).equalsIgnoreCase("0"))
			      request.setAttribute("coAppStatus", "A");
			    else {
			      request.setAttribute("coAppStatus", "X");
			    }
			    if (!CommonFunction.checkNull(gaur).equalsIgnoreCase("0"))
			      request.setAttribute("gaurStatus", "A");
			    else {
			      request.setAttribute("gaurStatus", "X");
			    }
			    ArrayList leadRMDetails = new ArrayList();
			    leadRMDetails.add(leadIdVo);
			    request.setAttribute("leadRMDetails", leadRMDetails);

			    request.setAttribute("eduDetail", eduDetail);

			    ArrayList getLoanTypeList = dp.getLoanTypeList();
			    request.setAttribute("getLoanType", getLoanTypeList);

			    String source = "";
			    ArrayList getSourceDetailList = lp.getSourceDetailList(source);
			    request.setAttribute("sourceList", getSourceDetailList);

			    if (result) {
			      if (param.equalsIgnoreCase("Save")) {
			        msg = "M";
			        request.setAttribute("msg", msg);
			      } else if (param.equalsIgnoreCase("Forward")) {
			        if ((leadIdVo.getLeadGenerator().equalsIgnoreCase("RM")) || (leadIdVo.getLeadGenerator().equalsIgnoreCase("RO"))) {
			          msg = "L";
			          request.setAttribute("Al", msg);
			          session.removeAttribute("leadId");
			        } else {
			          msg = "S";
			          request.setAttribute("fw", msg);
			          session.removeAttribute("leadId");
			        }
			      }
			    } else {
			      msg = "M";
			      request.setAttribute("msg", msg);
			    }
			      String pincodeFlag = lp1.getPincodeFlag();
		          logger.info("value of pincode flag is ::" + pincodeFlag);
		          request.setAttribute("pincodeFlag", pincodeFlag);
		          ArrayList<CreditProcessingLeadDetailDataVo> coappDetails = new ArrayList<CreditProcessingLeadDetailDataVo>();
		          if(CommonFunction.checkNull(custType).equalsIgnoreCase("coapp")){
			      coappDetails = lp.getCoappDetailsList(leadId,"COAPPL");
		          }else if(CommonFunction.checkNull(custType).equalsIgnoreCase("guar")){
		          coappDetails = lp.getCoappDetailsList(leadId,"GUARANTOR");
		          }
				  session.setAttribute("coappDetails", coappDetails);
			    branchId = null;
			    dp = null;
			    lp = null;
			    leadIdVo = null;
			    form.reset(mapping, request);
			    return mapping.getInputForward();
			  }
	  


  public ActionForward saveNewLead(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
       { 
		      logger.info("In preDealCapturingDispatchAction leadEntrySave().... ");
		      HttpSession session = request.getSession();
		      UserObject userobj = (UserObject)session.getAttribute("userobject");
		      String userId = "";
		      String bDate = "";
		      String branchId = null;
		      if (userobj != null)
		      {
		        userId = userobj.getUserId();
		        bDate = userobj.getBusinessdate();
		        branchId = userobj.getBranchId();
		      } else {
		        logger.info("here in saveNewLead method of preDealCapturingDispatchAction action the session is out----------------");
		        return mapping.findForward("sessionOut");
		      }
		      String sessionId = session.getAttribute("sessionID").toString();

		      ServletContext context = getServlet().getServletContext();
		      String strFlag = "";
		      if (sessionId != null)
		      {
		        strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId.toString(), "", request);
		      }

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

		      String leadId = "";

		      if (session.getAttribute("leadId") != null)
		      {
		        leadId = session.getAttribute("leadId").toString();
		      } else if (session.getAttribute("maxId") != null) {
		        leadId = session.getAttribute("maxId").toString();
		      }

		      CreditProcessingLeadDetailDataVo leadIdVo = new CreditProcessingLeadDetailDataVo();

		      leadIdVo.setLeadId(leadId);
		      leadIdVo.setApplicationdate(bDate);
		      leadIdVo.setMakerId(userId);

		      DynaValidatorForm LeadCapturingDynaValidatorForm = (DynaValidatorForm)form;
		      BeanUtils.copyProperties(leadIdVo, LeadCapturingDynaValidatorForm);
		      String param = request.getParameter("saveForward");
		      String CustomerId = request.getParameter("lbxCustomerId");
		      String coApplbxCustomerId = request.getParameter("coApplbxCustomerId");
		      String gaurlbxCustomerId = request.getParameter("gaurlbxCustomerId");
		      String cuspanid = request.getParameter("appCustPanInd");

		      if (request.getParameter("coAppStatus1") != null) {
		        leadIdVo.setCoAppStatus1(request.getParameter("coAppStatus1").toString());
		      }
		      if (request.getParameter("gaurStatus1") != null) {
		        leadIdVo.setGaurStatus1(request.getParameter("gaurStatus1").toString());
		      }

		      String lbxCustomerId = "";
		      if (!CommonFunction.checkNull(CustomerId).equalsIgnoreCase(""))
		      {
		        lbxCustomerId = CommonFunction.checkNull(CustomerId);
		      }
		      leadIdVo.setLbxCustomerId(lbxCustomerId);
		      leadIdVo.setCoApplbxCustomerId(coApplbxCustomerId);
		      leadIdVo.setGaurlbxCustomerId(gaurlbxCustomerId);
		      leadIdVo.setLbxRegionID2(branchId);
		      leadIdVo.setCustPanInd(cuspanid);
		      String coAppcustPan = request.getParameter("coAppcustPan");
		      leadIdVo.setCoAppcustPan(coAppcustPan);
		      String gaurcustPan = request.getParameter("gaurcustPan");
		      leadIdVo.setGaurcustPan(gaurcustPan);
		      
		      String custType = CommonFunction.checkNull(request.getParameter("customer"));
		      leadIdVo.setCustType(custType);
		      CommonMasterBussinessSessionBeanRemote master = (CommonMasterBussinessSessionBeanRemote)LookUpInstanceFactory.getLookUpInstance("COMMONBUSSINESSMASTERREMOTE", request);

		      PreDealDao lp = (PreDealDao)DaoImplInstanceFactory.getDaoImplInstance("PreDeal");

		      DealProcessingBeanRemote dp = (DealProcessingBeanRemote)LookUpInstanceFactory.getLookUpInstance("DEALPROCESSINGBUSSINESSMASTERREMOTE", request);
		      LeadProcessingRemote lp1 = (LeadProcessingRemote)LookUpInstanceFactory.getLookUpInstance("LEADPROCESSINGBUSSINESSMASTERREMOTE", request);

		      String msg = "";
		      leadIdVo.setTurnOver(request.getParameter("turnOver"));
		      ArrayList addresstypeList = lp.addresstype();
		      request.setAttribute("addresstypeList", addresstypeList);
		      String groupName = leadIdVo.getGroupName();
		      String count = "";
		      int count1 = 0;
		      String appPanCheck = null;
		      int appPanCount = 0;
		      String appUidCheck = null;
		      int appUidCount = 0;
		      String panNoExist = null;
		      String uidExist = null;

		      int scount1 = 0;
		      String sappPanCheck = null;
		      int sappPanCount = 0;
		      String sappUidCheck = null;
		      int sappUidCount = 0;
		      String spanNoExist = null;
		      String suidExist = null;
		      String coAppPanCheck = null;
		      int coAppPanCount = 0;
		      String coAppUidCheck = null;
		      int coAppUidCount = 0;

		      String scoAppPanCheck = null;
		      int scoAppPanCount = 0;
		      String scoAppUidCheck = null;
		      int scoAppUidCount = 0;

		      String gaurPanCheck = null;
		      int gaurPanCount = 0;
		      String gaurUidCheck = null;
		      int gaurUidCount = 0;
		      String sgaurPanCheck = null;
		      int sgaurPanCount = 0;
		      String sgaurUidCheck = null;
		      int sgaurUidCount = 0;
		      
		    //Shashank Starts For Novac
		      String voterExist = null;
		      String dlExist = null;					
		      String passsportExist = null;
		      String regisExist = null;

		      String  appVoterCheck = null;
		      String  sappVoterCheck = null;
		      String  appDrivingCheck = null;
		      String  sappDrivingCheck = null;
		      String  appPassportCheck = null;
		      String  sappPassportCheck = null;

		      String  coAppVoterCheck = null;
		      String  scoAppVoterCheck = null;
		      String  coAppDlCheck = null;
		      String  scoAppDlCheck = null;
		      String  coAppPassCheck = null;
		      String  scoAppPassCheck = null;

		      String  gaurVoterCheck = null;
		      String  sgaurVoterCheck = null;
		      String  gaurDlCheck = null;
		      String  sgaurDlCheck = null;
		      String  gaurPassCheck = null;
		      String  sgaurPassCheck = null;

		      int appVoterCount = 0;
		      int appDlCount = 0;
		      int appPassCount = 0;
		      int sappVoterCount = 0;
		      int sappDlCount = 0;
		      int sappPassCount = 0;

		      int coAppVoterCount = 0;
		      int coAppDlCount = 0;
		      int coAppPassCount = 0;
		      int scoAppVoterCount = 0;
		      int scoAppDlCount = 0;
		      int scoAppPassCount = 0;

		      int gaurVoterCount = 0;
		      int gaurDlCount = 0;
		      int gaurPassCount = 0;
		      int sgaurVoterCount = 0;
		      int sgaurDlCount = 0;
		      int sgaurPassCount = 0;

		      String appRegisCheck = null;
		      String sappRegisCheck = null;		
		      String coAppRegisCheck = null;
		      String scoAppRegisCheck = null;			
		      String gaurRegisCheck = null;
		      String sgaurRegisCheck = null;

		      int appRegisCount = 0;
		      int sappRegisCount = 0;
		      int coAppRegisCount = 0;
		      int scoAppRegisCount = 0;
		      int gaurRegisCount = 0;
		      int sgaurRegisCount = 0;			
		      //Shashank Ends For Novac
		      
		      if (leadIdVo.getCoAppcustPanInd().toString().equalsIgnoreCase("undefined"))
		        leadIdVo.setCoAppcustPanInd("");
		      if (leadIdVo.getCoAppaadhaar().toString().equalsIgnoreCase("undefined"))
		        leadIdVo.setCoAppaadhaar("");
		      if (leadIdVo.getGaurcustPanInd().toString().equalsIgnoreCase("undefined"))
		        leadIdVo.setGaurcustPanInd("");
		      if (leadIdVo.getGauraadhaar().toString().equalsIgnoreCase("undefined")) {
		        leadIdVo.setGauraadhaar("");
		      }
		      String groupExist = "Group Already Exist";
		      if (leadIdVo.getGroupType().equalsIgnoreCase("N"))
		      {
		        count = master.checkgroupName(groupName);

		        count1 = Integer.parseInt(count);
		      }

		      String applicationForm="";
			    if(!CommonFunction.checkNull(leadIdVo.getApplicationFormNoBranch()).equalsIgnoreCase("")){
			    	applicationForm=leadIdVo.getApplicationFormNoBranch();	
			    }else if(!CommonFunction.checkNull(leadIdVo.getApplicationFormNoOther()).equalsIgnoreCase("")){
			    	applicationForm=leadIdVo.getApplicationFormNoOther();
			    }else if(!CommonFunction.checkNull(leadIdVo.getApplicationFormNoRm()).equalsIgnoreCase("")){
			    	applicationForm=leadIdVo.getApplicationFormNoRm();
			    }else if(!CommonFunction.checkNull(leadIdVo.getApplicationFormNoVendor()).equalsIgnoreCase("")){
			    	applicationForm=leadIdVo.getApplicationFormNoVendor();
			    }
			    if(!CommonFunction.checkNull(applicationForm).equalsIgnoreCase("")){
			    String applicationQuery="select count(1) from cr_deal_dtl where DEAL_APPLICATION_FORM_NO='"+applicationForm+"'  ";
			    int appFormCount=Integer.parseInt(ConnectionDAO.singleReturn(applicationQuery));
			    if (appFormCount > 0)
			      {
			        panNoExist = "Application Form No. already exist.";
			        request.setAttribute("panNoExist", panNoExist);
			        ArrayList genderIndiv = lp.getGenderList();
			        request.setAttribute("GenderCategory", genderIndiv);
			        String pincodeFlag = lp1.getPincodeFlag();
			        logger.info("value of pincode flag is ::" + pincodeFlag);
			        request.setAttribute("pincodeFlag", pincodeFlag);
			        if (leadIdVo.getCoAppcustPanInd().toString().equalsIgnoreCase("undefined"))
			          leadIdVo.setCoAppcustPanInd("");
			        if (leadIdVo.getCoAppaadhaar().toString().equalsIgnoreCase("undefined"))
			          leadIdVo.setCoAppaadhaar("");
			        if (leadIdVo.getGaurcustPanInd().toString().equalsIgnoreCase("undefined"))
			          leadIdVo.setGaurcustPanInd("");
			        if (leadIdVo.getGauraadhaar().toString().equalsIgnoreCase("undefined"))
			          leadIdVo.setGauraadhaar("");
			        ArrayList leadDetails = new ArrayList();
			        leadIdVo.setContitutionCode(leadIdVo.getIndconstitution());
			        leadDetails.add(leadIdVo);
			        logger.info("consititution  valu by rohit sahay---------------------------------" + leadIdVo.getContitutionCode());

			        request.setAttribute("leadDetails", leadDetails);
			        return mapping.findForward("success");
			      }
			    }

		      logger.info("In saveNewLead() ---------->>> ");
		      if (leadIdVo.getCustomerType().equalsIgnoreCase("I")) {
		        String appPanNo = request.getParameter("appCustPanInd");
		        String appAadhaarNo = request.getParameter("appAadhaarInd");
		        
		        //Shashank Starts For Novac
		        String appVoterId = request.getParameter("voterId");
		        String appDlNumber = request.getParameter("dlNumber");
		        String appPassport = request.getParameter("passport");
		        //Shashank Ends For Novac
		        String relationship = leadIdVo.getRelationship();
		        logger.info("Applicant Relationship---->>> " + relationship);
		        if (!relationship.equalsIgnoreCase("Existing")) {
		          String query1 = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.PAN='" + appPanNo + "' and a.PAN<>'')";
		          logger.info("in execute() of IdividualDetailActionPage Query : " + query1);
		          String squery1 = "select count(1) from gcd_customer_m where (CUSTMER_PAN='" + appPanNo + "' and CUSTMER_PAN<>'')";
		          logger.info("in execute() of IdividualDetailActionPage Query : " + squery1);
		          appPanCheck = ConnectionDAO.singleReturn(query1);
		          sappPanCheck = ConnectionDAO.singleReturn(squery1);
		          String query2 = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.UID_NO='" + appAadhaarNo + "' and a.UID_NO<>'')";
		          logger.info("in execute() of IdividualDetailActionPage Query : " + query2);
		          appUidCheck = ConnectionDAO.singleReturn(query2);

		          String squery2 = "select count(1) from gcd_customer_m where (UID_NO='" + appAadhaarNo + "' and UID_NO<>'')";
		          logger.info("in execute() of IdividualDetailActionPage Query : " + squery2);
		          sappUidCheck = ConnectionDAO.singleReturn(squery2);
		          
		          //Shashank Starts For Novac
		          String query3 = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.VOTER_ID='" + appVoterId + "' and a.VOTER_ID<>'')";
		          logger.info("in execute() of IdividualDetailActionPage Query : " + query3);
		          String squery3 = "select count(1) from gcd_customer_m where (VOTER_ID='" + appVoterId + "' and VOTER_ID<>'')";
		          logger.info("in execute() of IdividualDetailActionPage Query : " + squery3);
		          appVoterCheck = ConnectionDAO.singleReturn(query3);
		          sappVoterCheck = ConnectionDAO.singleReturn(squery3);
		          
		          String query4 = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.DRIVING_LICENSE='" + appDlNumber + "' and a.DRIVING_LICENSE<>'')";
		          logger.info("in execute() of IdividualDetailActionPage Query : " + query4);
		          String squery4 = "select count(1) from gcd_customer_m where (DRIVING_LICENSE='" + appDlNumber + "' and DRIVING_LICENSE<>'')";
		          logger.info("in execute() of IdividualDetailActionPage Query : " + squery4);
		          appDrivingCheck = ConnectionDAO.singleReturn(query4);
		          sappDrivingCheck = ConnectionDAO.singleReturn(squery4);
		          
		          
		          String query5 = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.PASSPORT_NUMBER='" + appPassport + "' and a.PASSPORT_NUMBER<>'')";
		          logger.info("in execute() of IdividualDetailActionPage Query : " + query5);
		          String squery5 = "select count(1) from gcd_customer_m where (PASSPORT_NUMBER='" + appPassport + "' and PASSPORT_NUMBER<>'')";
		          logger.info("in execute() of IdividualDetailActionPage Query : " + squery5);
		          appPassportCheck = ConnectionDAO.singleReturn(query5);
		          sappPassportCheck = ConnectionDAO.singleReturn(squery5);
		          //Shashank Ends For Novac
		          
		        }

		      }

		      if (leadIdVo.getCustomerType().equalsIgnoreCase("C"))
		      {
		        String appPanNo = leadIdVo.getCustPan();
		        String appRegistrationNo = leadIdVo.getRegistrationNo();//Shahsank For Novac
		        String corconstitution = request.getParameter("corconstitution");
		        String abcd = leadIdVo.getCorconstitution();
		        String relationship = leadIdVo.getRelationship();
		        if ((!relationship.equalsIgnoreCase("Existing")) && 
		          (!corconstitution.equalsIgnoreCase("PROPRIETOR")))
		      {
		          String query1 = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.PAN='" + appPanNo + "' and a.PAN<>'')";
		          logger.info("in execute() of IdividualDetailActionPage Query : " + query1);
		          String squery1 = "select count(1) from gcd_customer_m where (CUSTMER_PAN='" + appPanNo + "' and CUSTMER_PAN<>'') ";
		          logger.info("in execute() of IdividualDetailActionPage Query : " + squery1);
		          appPanCheck = ConnectionDAO.singleReturn(query1);
		          sappPanCheck = ConnectionDAO.singleReturn(squery1);
		          
		          //Shashank Starts For Novac
		          String query2 = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.REGISTRATION_NO='" + appRegistrationNo + "' and a.REGISTRATION_NO<>'')";
		          logger.info("in execute() of IdividualDetailActionPage Query : " + query2);
		          String squery2 = "select count(1) from gcd_customer_m where (CUSTOMER_REGISTRATION_NO='" + appRegistrationNo + "' and CUSTOMER_REGISTRATION_NO<>'') ";
		          logger.info("in execute() of IdividualDetailActionPage Query : " + squery2);
		          appRegisCheck = ConnectionDAO.singleReturn(query2);
		          sappRegisCheck = ConnectionDAO.singleReturn(squery2);
		          //Shashank Ends For Novac
		        }

		      }

		      if (leadIdVo.getCoAppcustomerType().equalsIgnoreCase("C"))
		      {
		        String coAppPanNo = request.getParameter("coAppcustPanInd");
		        String coAppregistration = request.getParameter("coAppregistrationNo");//Shashank For Novac
		        String coAppAadhaarNo = request.getParameter("coAppaadhaar");
		        String CoAppcorconstitution = leadIdVo.getCoAppcorconstitution();
		        String CoAppRelationship = leadIdVo.getCoApprelationship();
		        logger.info("Coapplicant RelationShip----->>>> " + CoAppRelationship);
		        if ((!CoAppRelationship.equalsIgnoreCase("Existing")) && 
		          (!CoAppcorconstitution.equalsIgnoreCase("PROPRIETOR")))
		        {
		          String squery3 = "";
		          String query3 = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.PAN='" + coAppPanNo + "' and a.PAN<>'')";
		          squery3 = "select count(1) from gcd_customer_m where (CUSTMER_PAN='" + coAppPanNo + "' and CUSTMER_PAN<>'')";
		          logger.info("in execute() of IdividualDetailActionPage Query : " + query3);
		          coAppPanCheck = ConnectionDAO.singleReturn(query3);
		          scoAppPanCheck = ConnectionDAO.singleReturn(squery3);
		          
		          //Shashank Starts For Novac
		          String squery4 = "";
		          String query4 = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.REGISTRATION_NO='" + coAppregistration + "' and a.REGISTRATION_NO<>'')";
		          squery4 = "select count(1) from gcd_customer_m where (CUSTOMER_REGISTRATION_NO='" + coAppregistration + "' and CUSTOMER_REGISTRATION_NO<>'')";
		          logger.info("in execute() of IdividualDetailActionPage Query : " + query4);
		          coAppRegisCheck = ConnectionDAO.singleReturn(query4);
		          scoAppRegisCheck = ConnectionDAO.singleReturn(squery4);
		          //Shashank Ends For Novac
		        }
		      }

		      if (leadIdVo.getCoAppcustomerType().equalsIgnoreCase("I"))
		      {
		        String coAppPanNo;
		    
		        if (leadIdVo.getCoAppcustPanInd().toString().equalsIgnoreCase("undefined"))
		          coAppPanNo = "";
		        else
		          coAppPanNo = leadIdVo.getCoAppcustPanInd();
		        String coAppAadhaarNo;
		   
		        if (leadIdVo.getCoAppaadhaar().toString().equalsIgnoreCase("undefined"))
		          coAppAadhaarNo = "";
		        else {
		          coAppAadhaarNo = leadIdVo.getCoAppaadhaar();
		        }
		        
		      //Shashank starts For Novac
		        String coAppvoterId;

		        if (leadIdVo.getCoAppvoterId().toString().equalsIgnoreCase("undefined"))
		        coAppvoterId = "";
		        else {
		        coAppvoterId = leadIdVo.getCoAppvoterId();
		        }

		        String coAppdlNumber;

		        if (leadIdVo.getCoAppdlNumber().toString().equalsIgnoreCase("undefined"))
		        coAppdlNumber = "";
		        else {
		        coAppdlNumber = leadIdVo.getCoAppdlNumber();
		        }

		        String coApppassport;

		        if (leadIdVo.getCoApppassport().toString().equalsIgnoreCase("undefined"))
		        coApppassport = "";
		        else {
		        coApppassport = leadIdVo.getCoApppassport();
		        }

		        //Shashank ends For Novac

		        String CoAppRelationship = leadIdVo.getCoApprelationship();
		        logger.info("Coapplicant RelationShip----->>>> " + CoAppRelationship);
		        if (!CoAppRelationship.equalsIgnoreCase("Existing")) {
		        	String q1="select PAN from cr_lead_customer_m where constitution='PROPRIETOR' and lead_id='"+leadIdVo.getLeadId()+"'";
		        	String propPan=ConnectionDAO.singleReturn(q1);
		        			
		        	if(!CommonFunction.checkNull(coAppPanNo).equalsIgnoreCase(propPan)){
		          String squery3 = "";
		          String query3 = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.PAN='" + coAppPanNo + "' and a.PAN<>'') ";
		          squery3 = "select count(1) from gcd_customer_m where (CUSTMER_PAN='" + coAppPanNo + "' and CUSTMER_PAN<>'') ";
		          logger.info("in execute() of IdividualDetailActionPage Query : " + query3);
		          coAppPanCheck = ConnectionDAO.singleReturn(query3);
		          scoAppPanCheck = ConnectionDAO.singleReturn(squery3);
		        	}
		          String query4 = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.UID_NO='" + coAppAadhaarNo + "' and a.UID_NO<>'') ";
		          String squery4 = "select count(1) from gcd_customer_m where (UID_NO='" + coAppAadhaarNo + "' and UID_NO<>'') ";
		          logger.info("in execute() of IdividualDetailActionPage Query : " + query4);
		          logger.info("in execute() of IdividualDetailActionPage Query : " + squery4);
		          coAppUidCheck = ConnectionDAO.singleReturn(query4);
		          scoAppUidCheck = ConnectionDAO.singleReturn(squery4);
		        	
		          //Shashank Starts For Novac
		          String query5 = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.VOTER_ID='" + coAppvoterId + "' and a.VOTER_ID<>'') ";
		          String squery5 = "select count(1) from gcd_customer_m where (VOTER_ID='" + coAppvoterId + "' and VOTER_ID<>'') ";
		          logger.info("in execute() of IdividualDetailActionPage Query : " + query5);
		          logger.info("in execute() of IdividualDetailActionPage Query : " + squery5);
		          coAppVoterCheck = ConnectionDAO.singleReturn(query5);
		          scoAppVoterCheck = ConnectionDAO.singleReturn(squery5);
		          
		          String query6 = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.DRIVING_LICENSE='" + coAppdlNumber + "' and a.DRIVING_LICENSE<>'') ";
		          String squery6 = "select count(1) from gcd_customer_m where (DRIVING_LICENSE='" + coAppdlNumber + "' and DRIVING_LICENSE<>'') ";
		          logger.info("in execute() of IdividualDetailActionPage Query : " + query6);
		          logger.info("in execute() of IdividualDetailActionPage Query : " + squery6);
		          coAppDlCheck = ConnectionDAO.singleReturn(query6);
		          scoAppDlCheck = ConnectionDAO.singleReturn(squery6);
		          
		          String query7 = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.PASSPORT_NUMBER='" + coApppassport + "' and a.PASSPORT_NUMBER<>'') ";
		          String squery7 = "select count(1) from gcd_customer_m where (PASSPORT_NUMBER='" + coApppassport + "' and PASSPORT_NUMBER<>'') ";
		          logger.info("in execute() of IdividualDetailActionPage Query : " + query7);
		          logger.info("in execute() of IdividualDetailActionPage Query : " + squery7);
		          coAppPassCheck = ConnectionDAO.singleReturn(query7);
		          scoAppPassCheck = ConnectionDAO.singleReturn(squery7);
		        //Shashank Ends For Novac
		        
		          String coAppNameExist=request.getParameter("coAppNameExist");
			        if(!CommonFunction.checkNull(coAppNameExist).equalsIgnoreCase("C")){
					    String leadCustomerQuery="select count(1) from CR_LEAD_CUSTOMER_M where CUSTOMER_DOB=STR_TO_DATE('"+leadIdVo.getCoAppcustDOB()+"','%d-%m-%Y') and CUSTOMER_FNAME='"+leadIdVo.getCoAppfirstName()+"' AND IFNULL(CUSTOMER_MNAME,'')='"+leadIdVo.getCoAppmiddleName()+"' AND CUSTOMER_LNAME='"+leadIdVo.getCoApplastName()+"' ";
					    int leadCustomerCount=Integer.parseInt(ConnectionDAO.singleReturn(leadCustomerQuery));
					    String dealCustomerQuery="select count(1) from CR_DEAL_CUSTOMER_M where CUSTOMER_DOB=STR_TO_DATE('"+leadIdVo.getCoAppcustDOB()+"','%d-%m-%Y')  and CUSTOMER_FNAME='"+leadIdVo.getCoAppfirstName()+"' AND IFNULL(CUSTOMER_MNAME,'')='"+leadIdVo.getCoAppmiddleName()+"' AND CUSTOMER_LNAME='"+leadIdVo.getCoApplastName()+"' ";
					    int dealCustomerCount=Integer.parseInt(ConnectionDAO.singleReturn(dealCustomerQuery));
					    if (leadCustomerCount > 0 || dealCustomerCount>0)
					      {
					       String nameExist = "Name and DOB already exist.Do you want to continue?";
					        request.setAttribute("nameExist", nameExist);
					        ArrayList genderIndiv = lp.getGenderList();
					        request.setAttribute("GenderCategory", genderIndiv);
					        String pincodeFlag = lp1.getPincodeFlag();
					        logger.info("value of pincode flag is ::" + pincodeFlag);
					        request.setAttribute("pincodeFlag", pincodeFlag);
					        ArrayList leadDetails = new ArrayList();
					        leadIdVo.setCoAppcontitutionCode(leadIdVo.getCoAppindconstitution());
					        leadDetails.add(leadIdVo);
					        request.setAttribute("leadDetails", leadDetails);
					        return mapping.findForward("success");
		        }
		      }
		        }
		      }
		      if (leadIdVo.getGaurcustomerType().equalsIgnoreCase("I"))
		      {
		        String gaurPanNo;
		  
		        if (leadIdVo.getGaurcustPanInd().toString().equalsIgnoreCase("undefined")) {
		          gaurPanNo = "";
		        }
		        else
		          gaurPanNo = leadIdVo.getGaurcustPanInd();
		        String gaurAadhaarNo;
		   
		        if (leadIdVo.getGauraadhaar().toString().equalsIgnoreCase("undefined")) {
		          gaurAadhaarNo = "";
		        }
		        else {
		          gaurAadhaarNo = leadIdVo.getGauraadhaar();
		        }
		        
		      //Shashank Starts For Novac 
		        String gaurvoterId;

		        if (leadIdVo.getGaurvoterId().toString().equalsIgnoreCase("undefined")) {
		        gaurvoterId = "";
		        }
		        else {
		        gaurvoterId = leadIdVo.getGaurvoterId();
		        }
		        String gaurdlNumber;

		        if (leadIdVo.getGaurdlNumber().toString().equalsIgnoreCase("undefined")) {
		        gaurdlNumber = "";
		        }
		        else {
		        gaurdlNumber = leadIdVo.getGaurdlNumber();
		        }
		        String gaurpassport;

		        if (leadIdVo.getGaurpassport().toString().equalsIgnoreCase("undefined")) {
		        gaurpassport = "";
		        }
		        else {
		        gaurpassport = leadIdVo.getGaurpassport();
		        }
		        //Shashank Ends For Novac 

		        String gaurRelationship = leadIdVo.getGaurrelationship();
		        logger.info("Gaurranter Relationship----->>> " + gaurRelationship);
		        if (!gaurRelationship.equalsIgnoreCase("Existing")) {
		        	String q1="select PAN from cr_lead_customer_m where constitution='PROPRIETOR' and lead_id='"+leadIdVo.getLeadId()+"'";
		        	String propPan=ConnectionDAO.singleReturn(q1);
		        			
		        	if(!CommonFunction.checkNull(gaurPanNo).equalsIgnoreCase(propPan)){
		          String query5 = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.PAN='" + gaurPanNo + "' and a.PAN<>'')";
		          String squery5 = "select count(1) from gcd_customer_m where (CUSTMER_PAN='" + gaurPanNo + "' and CUSTMER_PAN<>'')";
		          logger.info("in execute() of IdividualDetailActionPage Query : " + query5);
		          logger.info("in execute() of IdividualDetailActionPage Query : " + squery5);
		          gaurPanCheck = ConnectionDAO.singleReturn(query5);
		          sgaurPanCheck = ConnectionDAO.singleReturn(squery5);
		        	}
		          String query6 = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.UID_NO='" + gaurAadhaarNo + "' and a.UID_NO<>'') ";
		          String squery6 = "select count(1) from gcd_customer_m where (UID_NO='" + gaurAadhaarNo + "' and UID_NO<>'') ";
		          logger.info("in execute() of IdividualDetailActionPage Query : " + query6);
		          gaurUidCheck = ConnectionDAO.singleReturn(query6);
		          logger.info("in execute() of IdividualDetailActionPage Query : " + squery6);
		          sgaurUidCheck = ConnectionDAO.singleReturn(squery6);
		          
		          //Shashank Starts For Novac
		          String query7 = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.VOTER_ID='" + gaurvoterId + "' and a.VOTER_ID<>'') ";
		          String squery7 = "select count(1) from gcd_customer_m where (VOTER_ID='" + gaurvoterId + "' and VOTER_ID<>'') ";
		          logger.info("in execute() of IdividualDetailActionPage Query : " + query7);
		          gaurVoterCheck = ConnectionDAO.singleReturn(query7);
		          logger.info("in execute() of IdividualDetailActionPage Query : " + squery7);
		          sgaurVoterCheck = ConnectionDAO.singleReturn(squery7);
		          
		          String query8 = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.DRIVING_LICENSE='" + gaurdlNumber + "' and a.DRIVING_LICENSE<>'') ";
		          String squery8 = "select count(1) from gcd_customer_m where (DRIVING_LICENSE='" + gaurdlNumber + "' and DRIVING_LICENSE<>'') ";
		          logger.info("in execute() of IdividualDetailActionPage Query : " + query8);
		          gaurDlCheck = ConnectionDAO.singleReturn(query8);
		          logger.info("in execute() of IdividualDetailActionPage Query : " + squery8);
		          sgaurDlCheck = ConnectionDAO.singleReturn(squery8);
		          
		          String query9 = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.PASSPORT_NUMBER='" + gaurpassport + "' and a.PASSPORT_NUMBER<>'') ";
		          String squery9 = "select count(1) from gcd_customer_m where (PASSPORT_NUMBER='" + gaurpassport + "' and PASSPORT_NUMBER<>'') ";
		          logger.info("in execute() of IdividualDetailActionPage Query : " + query9);
		          gaurPassCheck = ConnectionDAO.singleReturn(query9);
		          logger.info("in execute() of IdividualDetailActionPage Query : " + squery9);
		          sgaurPassCheck = ConnectionDAO.singleReturn(squery9);
		          //Shashank Ends For Novac
		          
		          String gaurnameExist=request.getParameter("gaurnameExist");
			        if(!CommonFunction.checkNull(gaurnameExist).equalsIgnoreCase("G")){
					    String leadCustomerQuery="select count(1) from CR_LEAD_CUSTOMER_M where CUSTOMER_DOB=STR_TO_DATE('"+leadIdVo.getGaurcustDOB()+"','%d-%m-%Y')  and CUSTOMER_FNAME='"+leadIdVo.getGaurfirstName()+"' AND IFNULL(CUSTOMER_MNAME,'')='"+leadIdVo.getGaurmiddleName()+"' AND CUSTOMER_LNAME='"+leadIdVo.getGaurlastName()+"' ";
					    int leadCustomerCount=Integer.parseInt(ConnectionDAO.singleReturn(leadCustomerQuery));
					    String dealCustomerQuery="select count(1) from CR_DEAL_CUSTOMER_M where STR_TO_DATE('"+leadIdVo.getGaurcustDOB()+"','%d-%m-%Y')  and CUSTOMER_FNAME='"+leadIdVo.getGaurfirstName()+"' AND IFNULL(CUSTOMER_MNAME,'')='"+leadIdVo.getGaurmiddleName()+"' AND CUSTOMER_LNAME='"+leadIdVo.getGaurlastName()+"' ";
					    int dealCustomerCount=Integer.parseInt(ConnectionDAO.singleReturn(dealCustomerQuery));
					    if (leadCustomerCount > 0 || dealCustomerCount>0)
					      {
					        gaurnameExist = "Name and DOB already exist.Do you want to continue?";
					        request.setAttribute("gaurnameExist", gaurnameExist);
					        ArrayList genderIndiv = lp.getGenderList();
					        request.setAttribute("GenderCategory", genderIndiv);
					        String pincodeFlag = lp1.getPincodeFlag();
					        logger.info("value of pincode flag is ::" + pincodeFlag);
					        request.setAttribute("pincodeFlag", pincodeFlag);
					        ArrayList leadDetails = new ArrayList();
					        leadIdVo.setGaurcontitutionCode(leadIdVo.getGaurindconstitution());
					        leadDetails.add(leadIdVo);
					        request.setAttribute("leadDetails", leadDetails);
					        return mapping.findForward("success");
		        }
		      }
		        }
		      }
		      if (leadIdVo.getGaurcustomerType().equalsIgnoreCase("C"))
		      {
		        String gaurPanNo = request.getParameter("gaurcustPanInd");
		        String gaurAadhaarNo = request.getParameter("gauraadhaar");
			     String gaurregistration = request.getParameter("gaurregistrationNo");//Shahsank For Novac
		        String gaurRelationship = leadIdVo.getGaurrelationship();
		        String Gaurcorconstitution = leadIdVo.getGaurcorconstitution();
		        logger.info("Gaurranter Relationship----->>> " + gaurRelationship);
		        if ((!gaurRelationship.equalsIgnoreCase("Existing")) && 
		          (!Gaurcorconstitution.equalsIgnoreCase("PROPRIETOR"))) {
		        
		          String query5 = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.PAN='" + gaurPanNo + "' and a.PAN<>'')";
		          String squery5 = "select count(1) from gcd_customer_m where (CUSTMER_PAN='" + gaurPanNo + "' and CUSTMER_PAN<>'')";
		          logger.info("in execute() of IdividualDetailActionPage Query : " + query5);
		          logger.info("in execute() of IdividualDetailActionPage Query : " + squery5);
		          gaurPanCheck = ConnectionDAO.singleReturn(query5);
		          sgaurPanCheck = ConnectionDAO.singleReturn(squery5);
		          
		          //Shashank Starts For Novac
		          String query6 = "select count(1) from cr_Lead_customer_m a join cr_lead_dtl b on a.lead_id=b.lead_id where b.rec_status='A' and (a.REGISTRATION_NO='" + gaurregistration + "' and a.REGISTRATION_NO<>'')";
		          String squery6 = "select count(1) from gcd_customer_m where (CUSTOMER_REGISTRATION_NO='" + gaurregistration + "' and CUSTOMER_REGISTRATION_NO<>'')";
		          logger.info("in execute() of IdividualDetailActionPage Query : " + query6);
		          logger.info("in execute() of IdividualDetailActionPage Query : " + squery6);
		          gaurRegisCheck = ConnectionDAO.singleReturn(query6);
		          sgaurRegisCheck = ConnectionDAO.singleReturn(squery6);
		          //Shashank Ends For Novac
		        }

		      }

		      if (appPanCheck == null)
		        appPanCount = 0;
		      else
		        appPanCount = Integer.parseInt(appPanCheck);
		      logger.info("appPanCount :" + appPanCount);
		      if (appUidCheck == null)
		        appUidCount = 0;
		      else
		        appUidCount = Integer.parseInt(appUidCheck);
		      logger.info("appUidCount :" + appUidCount);

		      if (sappPanCheck == null)
		        sappPanCount = 0;
		      else
		        sappPanCount = Integer.parseInt(sappPanCheck);
		      logger.info("sappPanCount :" + sappPanCount);
		      if (sappUidCheck == null)
		        sappUidCount = 0;
		      else
		        sappUidCount = Integer.parseInt(sappUidCheck);
		      logger.info("sappUidCount :" + sappUidCount);

		      if (coAppPanCheck == null)
		        coAppPanCount = 0;
		      else
		        coAppPanCount = Integer.parseInt(coAppPanCheck);
		      logger.info("coAppPanCount :" + coAppPanCount);
		      if (coAppUidCheck == null)
		        coAppUidCount = 0;
		      else
		        coAppUidCount = Integer.parseInt(coAppUidCheck);
		      logger.info("coAppUidCount :" + coAppUidCount);

		      if (scoAppPanCheck == null)
		        scoAppPanCount = 0;
		      else
		        scoAppPanCount = Integer.parseInt(scoAppPanCheck);
		      logger.info("scoAppPanCount :" + scoAppPanCount);
		      if (scoAppUidCheck == null)
		        scoAppUidCount = 0;
		      else
		        scoAppUidCount = Integer.parseInt(scoAppUidCheck);
		      logger.info("scoAppUidCount :" + scoAppUidCount);

		      if (gaurPanCheck == null)
		        gaurPanCount = 0;
		      else
		        gaurPanCount = Integer.parseInt(gaurPanCheck);
		      logger.info("gaurPanCount :" + gaurPanCount);
		      if (gaurUidCheck == null)
		        gaurUidCount = 0;
		      else
		        gaurUidCount = Integer.parseInt(gaurUidCheck);
		      logger.info("gaurUidCount :" + gaurUidCount);

		      if (sgaurPanCheck == null)
		        sgaurPanCount = 0;
		      else
		        sgaurPanCount = Integer.parseInt(sgaurPanCheck);
		      logger.info("sgaurPanCount :" + sgaurPanCount);
		      if (sgaurUidCheck == null)
		        sgaurUidCount = 0;
		      else
		        sgaurUidCount = Integer.parseInt(sgaurUidCheck);
		      logger.info("sgaurUidCount :" + sgaurUidCount);

		      appPanCount += sappPanCount;
		      coAppPanCount += scoAppPanCount;
		      gaurPanCount += sgaurPanCount;
		      appUidCount += sappUidCount;
		      coAppUidCount += scoAppUidCount;
		      gaurUidCount += sgaurUidCount;

		    //Shashank Statrs For Novac
		      if (appVoterCheck == null)
		      appVoterCount = 0;
		      else
		      appVoterCount = Integer.parseInt(appVoterCheck);
		      logger.info("appVoterCount :" + appVoterCount);

		      if (appDrivingCheck == null)
		      appDlCount = 0;
		      else
		      appDlCount = Integer.parseInt(appDrivingCheck);
		      logger.info("appDlCount :" + appDlCount);

		      if (appPassportCheck == null)
		      appPassCount = 0;
		      else
		      appPassCount = Integer.parseInt(appPassportCheck);
		      logger.info("appPassCount :" + appPassCount);

		      if (sappVoterCheck == null)
		      sappVoterCount = 0;
		      else
		      sappVoterCount = Integer.parseInt(sappVoterCheck);
		      logger.info("sappVoterCount :" + sappVoterCount);

		      if (sappDrivingCheck == null)
		      sappDlCount = 0;
		      else
		      sappDlCount = Integer.parseInt(sappDrivingCheck);
		      logger.info("sappDlCount :" + sappDlCount);

		      if (sappPassportCheck == null)
		      sappPassCount = 0;
		      else
		      sappPassCount = Integer.parseInt(sappPassportCheck);
		      logger.info("sappPassCount :" + sappPassCount);

		      if (coAppVoterCheck == null)
		      coAppVoterCount = 0;
		      else
		      coAppVoterCount = Integer.parseInt(coAppVoterCheck);
		      logger.info("coAppVoterCount :" + coAppVoterCount);

		      if (coAppDlCheck == null)
		      coAppDlCount = 0;
		      else
		      coAppDlCount = Integer.parseInt(coAppDlCheck);
		      logger.info("coAppDlCount :" + coAppDlCount);

		      if (coAppPassCheck == null)
		      coAppPassCount = 0;
		      else
		      coAppPassCount = Integer.parseInt(coAppPassCheck);
		      logger.info("coAppPassCount :" + coAppPassCount);

		      if (scoAppVoterCheck == null)
		      scoAppVoterCount = 0;
		      else
		      scoAppVoterCount = Integer.parseInt(scoAppVoterCheck);
		      logger.info("scoAppVoterCount :" + scoAppVoterCount);

		      if (scoAppDlCheck == null)
		      scoAppDlCount = 0;
		      else
		      scoAppDlCount = Integer.parseInt(scoAppDlCheck);
		      logger.info("scoAppDlCount :" + scoAppDlCount);

		      if (scoAppPassCheck == null)
		      scoAppPassCount = 0;
		      else
		      scoAppPassCount = Integer.parseInt(scoAppPassCheck);
		      logger.info("scoAppPassCount :" + scoAppPassCount);

		      if (gaurVoterCheck == null)
		      gaurVoterCount = 0;
		      else
		      gaurVoterCount = Integer.parseInt(gaurVoterCheck);
		      logger.info("gaurVoterCount :" + gaurVoterCount);

		      if (gaurDlCheck == null)
		      gaurDlCount = 0;
		      else
		      gaurDlCount = Integer.parseInt(gaurDlCheck);
		      logger.info("gaurDlCount :" + gaurDlCount);

		      if (gaurPassCheck == null)
		      gaurPassCount = 0;
		      else
		      gaurPassCount = Integer.parseInt(gaurPassCheck);
		      logger.info("gaurPassCount :" + gaurPassCount);

		      if (sgaurVoterCheck == null)
		      sgaurVoterCount = 0;
		      else
		      sgaurVoterCount = Integer.parseInt(sgaurVoterCheck);
		      logger.info("sgaurVoterCount :" + sgaurVoterCount);

		      if (sgaurDlCheck == null)
		      sgaurDlCount = 0;
		      else
		      sgaurDlCount = Integer.parseInt(sgaurDlCheck);
		      logger.info("sgaurDlCount :" + sgaurDlCount);

		      if (sgaurPassCheck == null)
		      sgaurPassCount = 0;
		      else
		      sgaurPassCount = Integer.parseInt(sgaurPassCheck);
		      logger.info("sgaurPassCount :" + sgaurPassCount);

		      if (appRegisCheck == null)
		      appRegisCount = 0;
		      else
		      appRegisCount = Integer.parseInt(appRegisCheck);
		      logger.info("appRegisCount :" + appRegisCount);
		      if (sappRegisCheck == null)
		      sappRegisCount = 0;
		      else
		      sappRegisCount = Integer.parseInt(sappRegisCheck);
		      logger.info("sappRegisCount :" + sappRegisCount);
		      if (coAppRegisCheck == null)
		      coAppRegisCount = 0;
		      else
		      coAppRegisCount = Integer.parseInt(coAppRegisCheck);
		      logger.info("coAppRegisCount :" + coAppRegisCount);
		      if (scoAppRegisCheck == null)
		      scoAppRegisCount = 0;
		      else
		      scoAppRegisCount = Integer.parseInt(scoAppRegisCheck);
		      logger.info("scoAppRegisCount :" + scoAppRegisCount);
		      if (gaurRegisCheck == null)
		      gaurRegisCount = 0;
		      else
		      gaurRegisCount = Integer.parseInt(gaurRegisCheck);
		      logger.info("gaurRegisCount :" + gaurRegisCount);
		      if (sgaurRegisCheck == null)
		      sgaurRegisCount = 0;
		      else
		      sgaurRegisCount = Integer.parseInt(sgaurRegisCheck);
		      logger.info("sgaurRegisCount :" + sgaurRegisCount);

		      appVoterCount += sappVoterCount;
		      appDlCount += sappDlCount;
		      appPassCount += sappPassCount;
		      coAppVoterCount += scoAppVoterCount;
		      coAppDlCount += scoAppDlCount;
		      coAppPassCount += scoAppPassCount;
		      gaurVoterCount += sgaurVoterCount;
		      gaurDlCount += sgaurDlCount;
		      gaurPassCount += sgaurPassCount;

		      appRegisCount += sappRegisCount;
		      coAppRegisCount += scoAppRegisCount;
		      gaurRegisCount += sgaurRegisCount;

		      //Shashank Ends For Novac

		      if ((appPanCount > 0) || (coAppPanCount > 0) || (gaurPanCount > 0)) {
		        leadIdVo.setContitutionCode(leadIdVo.getIndconstitution());
		        leadIdVo.setCoAppcontitutionCode(leadIdVo.getCoAppindconstitution());
		        leadIdVo.setGaurcontitutionCode(leadIdVo.getGaurindconstitution());
		        if (appPanCount > 0)
		        {
		          panNoExist = "Applicant Pan No. already exist.";
		          request.setAttribute("panNoExist", panNoExist);

		          ArrayList genderIndiv = lp.getGenderList();
		          request.setAttribute("GenderCategory", genderIndiv);

		          String pincodeFlag = lp1.getPincodeFlag();
		          logger.info("value of pincode flag is ::" + pincodeFlag);
		          request.setAttribute("pincodeFlag", pincodeFlag);
		          if (leadIdVo.getCoAppcustPanInd().toString().equalsIgnoreCase("undefined"))
		            leadIdVo.setCoAppcustPanInd("");
		          if (leadIdVo.getCoAppaadhaar().toString().equalsIgnoreCase("undefined"))
		            leadIdVo.setCoAppaadhaar("");
		          if (leadIdVo.getGaurcustPanInd().toString().equalsIgnoreCase("undefined"))
		            leadIdVo.setGaurcustPanInd("");
		          if (leadIdVo.getGauraadhaar().toString().equalsIgnoreCase("undefined"))
		            leadIdVo.setGauraadhaar("");
		          leadIdVo.setContitutionCode(leadIdVo.getIndconstitution());
		          ArrayList leadDetails = new ArrayList();
		          leadDetails.add(leadIdVo);
		          logger.info("Saurabh logger for constitution code--------------" + leadIdVo.getContitutionCode());
		          request.setAttribute("leadDetails", leadDetails);
		          return mapping.findForward("success");
		        }
		        if (coAppPanCount > 0) {
		          panNoExist = "Co-Applicant Pan No. already exist.";
		          request.setAttribute("panNoExist", panNoExist);
		          ArrayList leadDetails = new ArrayList();
		          ArrayList genderIndiv = lp.getGenderList();
		          request.setAttribute("GenderCategory", genderIndiv);
		          String pincodeFlag = lp1.getPincodeFlag();
		          logger.info("value of pincode flag is ::" + pincodeFlag);
		          request.setAttribute("pincodeFlag", pincodeFlag);
		          leadIdVo.setCoAppcontitutionCode(leadIdVo.getCoAppindconstitution());
		          leadDetails.add(leadIdVo);
		          request.setAttribute("leadDetails", leadDetails);
		          return mapping.findForward("success");
		        }
		        if (gaurPanCount > 0) {
		          panNoExist = "Guarantor Pan No. already exist.";
		          request.setAttribute("panNoExist", panNoExist);
		          ArrayList leadDetails = new ArrayList();
		          ArrayList genderIndiv = lp.getGenderList();
		          request.setAttribute("GenderCategory", genderIndiv);
		          String pincodeFlag = lp1.getPincodeFlag();
		          logger.info("value of pincode flag is ::" + pincodeFlag);
		          request.setAttribute("pincodeFlag", pincodeFlag);
		          leadIdVo.setGaurcontitutionCode(leadIdVo.getGaurindconstitution());
		          leadDetails.add(leadIdVo);
		          request.setAttribute("leadDetails", leadDetails);
		          return mapping.findForward("success");
		        }
		      }
		      else if ((appUidCount > 0) || (coAppUidCount > 0) || (gaurUidCount > 0)) {
		        leadIdVo.setContitutionCode(leadIdVo.getIndconstitution());
		        leadIdVo.setCoAppcontitutionCode(leadIdVo.getCoAppindconstitution());
		        leadIdVo.setGaurcontitutionCode(leadIdVo.getGaurindconstitution());
		        if (appUidCount > 0) {
		          uidExist = "Applicant Aadhar No. already exist.";
		          request.setAttribute("uidExist", uidExist);
		          ArrayList leadDetails = new ArrayList();
		          ArrayList genderIndiv = lp.getGenderList();
		          request.setAttribute("GenderCategory", genderIndiv);
		          String pincodeFlag = lp1.getPincodeFlag();
		          logger.info("value of pincode flag is ::" + pincodeFlag);
		          request.setAttribute("pincodeFlag", pincodeFlag);
		          if (leadIdVo.getCoAppcustPanInd().toString().equalsIgnoreCase("undefined"))
		            leadIdVo.setCoAppcustPanInd("");
		          if (leadIdVo.getCoAppaadhaar().toString().equalsIgnoreCase("undefined"))
		            leadIdVo.setCoAppaadhaar("");
		          if (leadIdVo.getGaurcustPanInd().toString().equalsIgnoreCase("undefined"))
		            leadIdVo.setGaurcustPanInd("");
		          if (leadIdVo.getGauraadhaar().toString().equalsIgnoreCase("undefined"))
		            leadIdVo.setGauraadhaar("");
		          leadIdVo.setContitutionCode(leadIdVo.getIndconstitution());
		          leadDetails.add(leadIdVo);
		          request.setAttribute("leadDetails", leadDetails);
		          return mapping.findForward("success");
		        }
		        if (coAppUidCount > 0)
		        {
		          uidExist = "Co-Applicant Aadhar No. already exist.";
		          request.setAttribute("uidExist", uidExist);
		          ArrayList leadDetails = new ArrayList();
		          ArrayList genderIndiv = lp.getGenderList();
		          request.setAttribute("GenderCategory", genderIndiv);
		          String pincodeFlag = lp1.getPincodeFlag();
		          logger.info("value of pincode flag is ::" + pincodeFlag);
		          request.setAttribute("pincodeFlag", pincodeFlag);
		          leadIdVo.setCoAppcontitutionCode(leadIdVo.getCoAppindconstitution());
		          leadDetails.add(leadIdVo);
		          request.setAttribute("leadDetails", leadDetails);
		          return mapping.findForward("success");
		        }
		        if (gaurUidCount > 0)
		        {
		          uidExist = "Guarantor Aadhar No. already exist.";
		          request.setAttribute("uidExist", uidExist);
		          ArrayList leadDetails = new ArrayList();
		          ArrayList genderIndiv = lp.getGenderList();
		          request.setAttribute("GenderCategory", genderIndiv);
		          String pincodeFlag = lp1.getPincodeFlag();
		          logger.info("value of pincode flag is ::" + pincodeFlag);
		          request.setAttribute("pincodeFlag", pincodeFlag);
		          leadIdVo.setGaurcontitutionCode(leadIdVo.getGaurindconstitution());
		          leadDetails.add(leadIdVo);
		          request.setAttribute("leadDetails", leadDetails);
		          return mapping.findForward("success");
		        }

		      }

		    //Shashank Starts For Novac

		      else if ((appVoterCount > 0) || (coAppVoterCount > 0) || (gaurVoterCount > 0)) {
		      leadIdVo.setContitutionCode(leadIdVo.getIndconstitution());
		      leadIdVo.setCoAppcontitutionCode(leadIdVo.getCoAppindconstitution());
		      leadIdVo.setGaurcontitutionCode(leadIdVo.getGaurindconstitution());
		      if (appVoterCount > 0) {
		      voterExist = "Applicant Voter Id already exist.";
		      request.setAttribute("voterExist", voterExist);
		      ArrayList genderIndiv = lp.getGenderList();
		      request.setAttribute("GenderCategory", genderIndiv);
		      String pincodeFlag = lp1.getPincodeFlag();
		      logger.info("value of pincode flag is ::" + pincodeFlag);
		      request.setAttribute("pincodeFlag", pincodeFlag);
		      if (leadIdVo.getCoAppcustPanInd().toString().equalsIgnoreCase("undefined"))
		      leadIdVo.setCoAppcustPanInd("");
		      if (leadIdVo.getCoAppaadhaar().toString().equalsIgnoreCase("undefined"))
		      leadIdVo.setCoAppaadhaar("");
		      if (leadIdVo.getGaurcustPanInd().toString().equalsIgnoreCase("undefined"))
		      leadIdVo.setGaurcustPanInd("");
		      if (leadIdVo.getGauraadhaar().toString().equalsIgnoreCase("undefined"))
		      leadIdVo.setGauraadhaar("");
		      ArrayList leadDetails = new ArrayList();
		      leadIdVo.setContitutionCode(leadIdVo.getIndconstitution());
		      leadDetails.add(leadIdVo);
		      request.setAttribute("leadDetails", leadDetails);
		      return mapping.findForward("success");
		      }
		      if (coAppVoterCount > 0)
		      {
		      voterExist = "Co-Applicant Voter Id already exist.";
		      request.setAttribute("voterExist", voterExist);
		      ArrayList leadDetails = new ArrayList();
		      leadIdVo.setCoAppcontitutionCode(leadIdVo.getCoAppindconstitution());
		      leadDetails.add(leadIdVo);
		      ArrayList genderIndiv = lp.getGenderList();
		      request.setAttribute("GenderCategory", genderIndiv);
		      String pincodeFlag = lp1.getPincodeFlag();
		      logger.info("value of pincode flag is ::" + pincodeFlag);
		      request.setAttribute("pincodeFlag", pincodeFlag);
		      request.setAttribute("leadDetails", leadDetails);
		      return mapping.findForward("success");
		      }
		      if (gaurVoterCount > 0)
		      {
		      voterExist = "Guarantor Voter Id already exist.";
		      request.setAttribute("voterExist", voterExist);
		      ArrayList leadDetails = new ArrayList();
		      leadIdVo.setGaurcontitutionCode(leadIdVo.getGaurindconstitution());
		      leadDetails.add(leadIdVo);
		      ArrayList genderIndiv = lp.getGenderList();
		      request.setAttribute("GenderCategory", genderIndiv);
		      String pincodeFlag = lp1.getPincodeFlag();
		      logger.info("value of pincode flag is ::" + pincodeFlag);
		      request.setAttribute("pincodeFlag", pincodeFlag);
		      request.setAttribute("leadDetails", leadDetails);
		      return mapping.findForward("success");
		      }

		      }


		      else if ((appDlCount > 0) || (coAppDlCount > 0) || (gaurDlCount > 0)) {
		      leadIdVo.setContitutionCode(leadIdVo.getIndconstitution());
		      leadIdVo.setCoAppcontitutionCode(leadIdVo.getCoAppindconstitution());
		      leadIdVo.setGaurcontitutionCode(leadIdVo.getGaurindconstitution());
		      if (appDlCount > 0) {
		      dlExist = "Applicant Driving Licence already exist.";
		      request.setAttribute("dlExist", dlExist);
		      ArrayList genderIndiv = lp.getGenderList();
		      request.setAttribute("GenderCategory", genderIndiv);
		      String pincodeFlag = lp1.getPincodeFlag();
		      logger.info("value of pincode flag is ::" + pincodeFlag);
		      request.setAttribute("pincodeFlag", pincodeFlag);
		      if (leadIdVo.getCoAppcustPanInd().toString().equalsIgnoreCase("undefined"))
		      leadIdVo.setCoAppcustPanInd("");
		      if (leadIdVo.getCoAppaadhaar().toString().equalsIgnoreCase("undefined"))
		      leadIdVo.setCoAppaadhaar("");
		      if (leadIdVo.getGaurcustPanInd().toString().equalsIgnoreCase("undefined"))
		      leadIdVo.setGaurcustPanInd("");
		      if (leadIdVo.getGauraadhaar().toString().equalsIgnoreCase("undefined"))
		      leadIdVo.setGauraadhaar("");
		      ArrayList leadDetails = new ArrayList();
		      leadIdVo.setContitutionCode(leadIdVo.getIndconstitution());
		      leadDetails.add(leadIdVo);
		      request.setAttribute("leadDetails", leadDetails);
		      return mapping.findForward("success");
		      }
		      if (coAppDlCount > 0)
		      {
		      dlExist = "Co-Applicant Driving Licence already exist.";
		      request.setAttribute("dlExist", dlExist);
		      ArrayList leadDetails = new ArrayList();
		      leadIdVo.setCoAppcontitutionCode(leadIdVo.getCoAppindconstitution());
		      leadDetails.add(leadIdVo);
		      ArrayList genderIndiv = lp.getGenderList();
		      request.setAttribute("GenderCategory", genderIndiv);
		      String pincodeFlag = lp1.getPincodeFlag();
		      logger.info("value of pincode flag is ::" + pincodeFlag);
		      request.setAttribute("pincodeFlag", pincodeFlag);
		      request.setAttribute("leadDetails", leadDetails);
		      return mapping.findForward("success");
		      }
		      if (gaurDlCount > 0)
		      {
		      dlExist = "Guarantor Driving Licence already exist.";
		      request.setAttribute("dlExist", dlExist);
		      ArrayList leadDetails = new ArrayList();
		      leadIdVo.setGaurcontitutionCode(leadIdVo.getGaurindconstitution());
		      leadDetails.add(leadIdVo);
		      ArrayList genderIndiv = lp.getGenderList();
		      request.setAttribute("GenderCategory", genderIndiv);
		      String pincodeFlag = lp1.getPincodeFlag();
		      logger.info("value of pincode flag is ::" + pincodeFlag);
		      request.setAttribute("pincodeFlag", pincodeFlag);
		      request.setAttribute("leadDetails", leadDetails);
		      return mapping.findForward("success");
		      }

		      }

		      else if ((appPassCount > 0) || (coAppPassCount > 0) || (gaurPassCount > 0)) {
		      leadIdVo.setContitutionCode(leadIdVo.getIndconstitution());
		      leadIdVo.setCoAppcontitutionCode(leadIdVo.getCoAppindconstitution());
		      leadIdVo.setGaurcontitutionCode(leadIdVo.getGaurindconstitution());
		      if (appPassCount > 0) {
		      passsportExist = "Applicant Passport No. already exist.";
		      request.setAttribute("passsportExist", passsportExist);
		      ArrayList genderIndiv = lp.getGenderList();
		      request.setAttribute("GenderCategory", genderIndiv);
		      String pincodeFlag = lp1.getPincodeFlag();
		      logger.info("value of pincode flag is ::" + pincodeFlag);
		      request.setAttribute("pincodeFlag", pincodeFlag);
		      if (leadIdVo.getCoAppcustPanInd().toString().equalsIgnoreCase("undefined"))
		      leadIdVo.setCoAppcustPanInd("");
		      if (leadIdVo.getCoAppaadhaar().toString().equalsIgnoreCase("undefined"))
		      leadIdVo.setCoAppaadhaar("");
		      if (leadIdVo.getGaurcustPanInd().toString().equalsIgnoreCase("undefined"))
		      leadIdVo.setGaurcustPanInd("");
		      if (leadIdVo.getGauraadhaar().toString().equalsIgnoreCase("undefined"))
		      leadIdVo.setGauraadhaar("");
		      ArrayList leadDetails = new ArrayList();
		      leadIdVo.setContitutionCode(leadIdVo.getIndconstitution());
		      leadDetails.add(leadIdVo);
		      request.setAttribute("leadDetails", leadDetails);
		      return mapping.findForward("success");
		      }
		      if (coAppPassCount > 0)
		      {
		      passsportExist = "Co-Applicant Passport No. already exist.";
		      request.setAttribute("passsportExist", passsportExist);
		      ArrayList leadDetails = new ArrayList();
		      leadIdVo.setCoAppcontitutionCode(leadIdVo.getCoAppindconstitution());
		      leadDetails.add(leadIdVo);
		      ArrayList genderIndiv = lp.getGenderList();
		      request.setAttribute("GenderCategory", genderIndiv);
		      String pincodeFlag = lp1.getPincodeFlag();
		      logger.info("value of pincode flag is ::" + pincodeFlag);
		      request.setAttribute("pincodeFlag", pincodeFlag);
		      request.setAttribute("leadDetails", leadDetails);
		      return mapping.findForward("success");
		      }
		      if (gaurPassCount > 0)
		      {
		      passsportExist = "Guarantor Passport No. already exist.";
		      request.setAttribute("passsportExist", passsportExist);
		      ArrayList leadDetails = new ArrayList();
		      leadIdVo.setGaurcontitutionCode(leadIdVo.getGaurindconstitution());
		      leadDetails.add(leadIdVo);
		      ArrayList genderIndiv = lp.getGenderList();
		      request.setAttribute("GenderCategory", genderIndiv);
		      String pincodeFlag = lp1.getPincodeFlag();
		      logger.info("value of pincode flag is ::" + pincodeFlag);
		      request.setAttribute("pincodeFlag", pincodeFlag);
		      request.setAttribute("leadDetails", leadDetails);
		      return mapping.findForward("success");
		      }

		      }

		      else if ((appRegisCount > 0) || (coAppRegisCount > 0) || (gaurRegisCount > 0)) {
		      leadIdVo.setContitutionCode(leadIdVo.getIndconstitution());
		      leadIdVo.setCoAppcontitutionCode(leadIdVo.getCoAppindconstitution());
		      leadIdVo.setGaurcontitutionCode(leadIdVo.getGaurindconstitution());
		      if (appRegisCount > 0) {
		      regisExist = "Applicant Registration No. already exist.";
		      request.setAttribute("regisExist", regisExist);
		      ArrayList genderIndiv = lp.getGenderList();
		      request.setAttribute("GenderCategory", genderIndiv);
		      String pincodeFlag = lp1.getPincodeFlag();
		      logger.info("value of pincode flag is ::" + pincodeFlag);
		      request.setAttribute("pincodeFlag", pincodeFlag);
		      if (leadIdVo.getCoAppcustPanInd().toString().equalsIgnoreCase("undefined"))
		      leadIdVo.setCoAppcustPanInd("");
		      if (leadIdVo.getCoAppaadhaar().toString().equalsIgnoreCase("undefined"))
		      leadIdVo.setCoAppaadhaar("");
		      if (leadIdVo.getGaurcustPanInd().toString().equalsIgnoreCase("undefined"))
		      leadIdVo.setGaurcustPanInd("");
		      if (leadIdVo.getGauraadhaar().toString().equalsIgnoreCase("undefined"))
		      leadIdVo.setGauraadhaar("");
		      ArrayList leadDetails = new ArrayList();
		      leadIdVo.setContitutionCode(leadIdVo.getIndconstitution());
		      leadDetails.add(leadIdVo);
		      request.setAttribute("leadDetails", leadDetails);
		      return mapping.findForward("success");
		      }
		      if (coAppRegisCount > 0)
		      {
		      regisExist = "Co-Applicant Registration No. already exist.";
		      request.setAttribute("regisExist", regisExist);
		      ArrayList leadDetails = new ArrayList();
		      leadIdVo.setCoAppcontitutionCode(leadIdVo.getCoAppindconstitution());
		      leadDetails.add(leadIdVo);
		      ArrayList genderIndiv = lp.getGenderList();
		      request.setAttribute("GenderCategory", genderIndiv);
		      String pincodeFlag = lp1.getPincodeFlag();
		      logger.info("value of pincode flag is ::" + pincodeFlag);
		      request.setAttribute("pincodeFlag", pincodeFlag);
		      request.setAttribute("leadDetails", leadDetails);
		      return mapping.findForward("success");
		      }
		      if (gaurRegisCount > 0)
		      {
		      regisExist = "Guarantor Registration No. already exist.";
		      request.setAttribute("regisExist", regisExist);
		      ArrayList leadDetails = new ArrayList();
		      leadIdVo.setGaurcontitutionCode(leadIdVo.getGaurindconstitution());
		      leadDetails.add(leadIdVo);
		      ArrayList genderIndiv = lp.getGenderList();
		      request.setAttribute("GenderCategory", genderIndiv);
		      String pincodeFlag = lp1.getPincodeFlag();
		      logger.info("value of pincode flag is ::" + pincodeFlag);
		      request.setAttribute("pincodeFlag", pincodeFlag);
		      request.setAttribute("leadDetails", leadDetails);
		      return mapping.findForward("success");
		      }

		      }


		      //Shashank Ends For Novac

		      if (count1 > 0)
		      {
		        request.setAttribute("group", groupExist);
		        request.setAttribute("NEW", "NEW");
		        ArrayList leadDetails = new ArrayList();
		          ArrayList genderIndiv = lp.getGenderList();
		          request.setAttribute("GenderCategory", genderIndiv);
		          String pincodeFlag = lp1.getPincodeFlag();
		          logger.info("value of pincode flag is ::" + pincodeFlag);
		          request.setAttribute("pincodeFlag", pincodeFlag);
		          leadIdVo.setContitutionCode(leadIdVo.getIndconstitution());
		          leadDetails.add(leadIdVo);
		          request.setAttribute("leadDetails", leadDetails);
		          return mapping.findForward("success");
		      }
		      else
		      {
		        leadIdVo.setAadhaar(request.getParameter("appAadhaarInd"));
		        logger.info("Aadhar no for applicant---+ " + leadIdVo.getAadhaar());
		        String result=null;
		        if(CommonFunction.checkNull(custType).equalsIgnoreCase("coapp")){
		        result = lp.saveCoappLead(leadIdVo);
		        }else if(CommonFunction.checkNull(custType).equalsIgnoreCase("guar")){
		        result = lp.saveGuarLead(leadIdVo);
		        }else{
		        	 result = lp.saveNewLead(leadIdVo);
		        }
		        
		        logger.info("result" + result);
		        if ((result.equalsIgnoreCase("Some Error In The SQL Execution")) || (result.equals(""))) {
		          request.setAttribute("procval", result);
		          request.setAttribute("procvalue", "ProcError");
		        } else {
		          ArrayList leadRMDetails = new ArrayList();
		          leadRMDetails.add(leadIdVo);
		          request.setAttribute("leadRMDetails", leadRMDetails);

		          ArrayList getLoanTypeList = dp.getLoanTypeList();
		          request.setAttribute("getLoanType", getLoanTypeList);
		          /*String indConSubprofile = request.getParameter("indConSubprofile");
		          if (CommonFunction.checkNull(indConSubprofile).trim().equalsIgnoreCase(""))
		          {
		            indConSubprofile = "HOUSEWIFE";
		          }*/

		          ArrayList constitutionlist = lp.getContitutionList();
		          ArrayList corconstitutionlist = lp.getCorContitutionList();
		          ArrayList indconstitutionlist = lp.getIndContitutionList();
		          ArrayList businessSegmentList = lp.getBusinessSegmentList();
		          ArrayList eduDetail = lp.getEduDetailList();
		          ArrayList genderIndiv = lp.getGenderList();
		         // ArrayList indConstSubprofileList = lp.getIndContitutionSubprofile(indConSubprofile);
		          request.setAttribute("GenderCategory", genderIndiv);
		          session.setAttribute("addresstypeList", addresstypeList);
		          session.setAttribute("businessSegmentList", businessSegmentList);
		          session.setAttribute("constitutionlist", constitutionlist);
		          session.setAttribute("indconstitutionlist", indconstitutionlist);
		          session.setAttribute("corconstitutionlist", corconstitutionlist);
		         // session.setAttribute("indConstSubprofileList", indConstSubprofileList);
		          if (param.equalsIgnoreCase("Save")) {
		            msg = "M";
		            request.setAttribute("msg", msg);
		          }
		          ArrayList leadDetails = lp.getLeadHeader(result);
		          request.setAttribute("leadDetails", leadDetails);
		        }

		      }

		      String source = "";
		      ArrayList getSourceDetailList = lp.getSourceDetailList(source);
		      ArrayList eduDetail = lp.getEduDetailList();

		      String coappQuery = "select count(1) from cr_lead_customer_m where lead_id='" + leadId + "' and CUSTOMER_ROLE_TYPE='COAPPL' ";
		      String coapp = ConnectionDAO.singleReturn(coappQuery);
		      String gaurQuery = "select count(1) from cr_lead_customer_m where lead_id='" + leadId + "' and CUSTOMER_ROLE_TYPE='GUARANTOR' ";
		      String gaur = ConnectionDAO.singleReturn(gaurQuery);
		      if (!CommonFunction.checkNull(coapp).equalsIgnoreCase("0"))
		        request.setAttribute("coAppStatus", "A");
		      else {
		        request.setAttribute("coAppStatus", "X");
		      }
		      if (!CommonFunction.checkNull(gaur).equalsIgnoreCase("0"))
		        request.setAttribute("gaurStatus", "A");
		      else {
		        request.setAttribute("gaurStatus", "X");
		      }
		      request.setAttribute("sourceList", getSourceDetailList);
		      request.setAttribute("eduDetail", eduDetail);
		      String pincodeFlag = lp1.getPincodeFlag();
	          logger.info("value of pincode flag is ::" + pincodeFlag);
	          request.setAttribute("pincodeFlag", pincodeFlag);
	          ArrayList<CreditProcessingLeadDetailDataVo> coappDetails = new ArrayList<CreditProcessingLeadDetailDataVo>();
	          if(CommonFunction.checkNull(custType).equalsIgnoreCase("coapp")){
		      coappDetails = lp.getCoappDetailsList(leadId,"COAPPL");
	          }else if(CommonFunction.checkNull(custType).equalsIgnoreCase("guar")){
	          coappDetails = lp.getCoappDetailsList(leadId,"GUARANTOR");
	          }
			  session.setAttribute("coappDetails", coappDetails);
		      lp = null;
		      dp = null;
		      form.reset(mapping, request);
		      return mapping.findForward("success");
		    }
  

  public ActionForward saveAllocation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    logger.info("In saveAllocation.... ");
    HttpSession session = request.getSession();
    UserObject userobj = (UserObject)session.getAttribute("userobject");
    String userId = "";
    String bDate = "";
    String branchId = null;
    if (userobj != null)
    {
      userId = userobj.getUserId();
      bDate = userobj.getBusinessdate();
      branchId = userobj.getBranchId();
    } else {
      logger.info("here in saveAllocation method of preDealCapturingDispatchAction action the session is out----------------");
      return mapping.findForward("sessionOut");
    }
    String sessionId = session.getAttribute("sessionID").toString();

    ServletContext context = getServlet().getServletContext();
    String strFlag = "";
    if (sessionId != null)
    {
      strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId.toString(), "", request);
    }

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

    String leadId = "";

    if (session.getAttribute("leadId") != null)
    {
      leadId = session.getAttribute("leadId").toString();
    } else if (session.getAttribute("maxId") != null) {
      leadId = session.getAttribute("maxId").toString();
    }

    CreditProcessingLeadDetailDataVo leadIdVo = new CreditProcessingLeadDetailDataVo();

    leadIdVo.setLeadId(leadId);
    leadIdVo.setApplicationdate(bDate);
    leadIdVo.setMakerId(userId);

    DynaValidatorForm LeadCapturingDynaValidatorForm = (DynaValidatorForm)form;
    BeanUtils.copyProperties(leadIdVo, LeadCapturingDynaValidatorForm);

    String msg = "";

    DealProcessingBeanRemote dp = (DealProcessingBeanRemote)LookUpInstanceFactory.getLookUpInstance("DEALPROCESSINGBUSSINESSMASTERREMOTE", request);

    PreDealDao lp = (PreDealDao)DaoImplInstanceFactory.getDaoImplInstance("PreDeal");

    leadId = CommonFunction.checkNull(request.getParameter("leadId"));
    boolean result = lp.saveAllocation(leadIdVo);
    if (result) {
      msg = "LA";
      request.setAttribute("msg", msg);
    }

    ArrayList getLoanTypeList = dp.getLoanTypeList();
    request.setAttribute("getLoanType", getLoanTypeList);

    leadId = CommonFunction.checkNull(request.getParameter("leadId"));

    leadIdVo.setLbxProductID("");
    leadIdVo.setLbxBranchId("");
    leadIdVo.setSchemeId("");
    branchId = null;
    dp = null;
    lp = null;
    form.reset(mapping, request);
    return mapping.getInputForward();
  }

  public ActionForward saveTrackingDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    logger.info("In saveTrackingDetails.... ");
    HttpSession session = request.getSession();
    UserObject userobj = (UserObject)session.getAttribute("userobject");
    String userId = "";
    String bDate = "";
    String branchId = null;
    if (userobj != null)
    {
      userId = userobj.getUserId();
      bDate = userobj.getBusinessdate();
      branchId = userobj.getBranchId();
    } else {
      logger.info("here in saveTrackingDetails method of preDealCapturingDispatchAction action the session is out----------------");
      return mapping.findForward("sessionOut");
    }
    String sessionId = session.getAttribute("sessionID").toString();

    ServletContext context = getServlet().getServletContext();
    String strFlag = "";
    if (sessionId != null)
    {
      strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId.toString(), "", request);
    }

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

    String leadId = "";

    if (session.getAttribute("leadId") != null)
    {
      leadId = session.getAttribute("leadId").toString();
    } else if (session.getAttribute("maxId") != null) {
      leadId = session.getAttribute("maxId").toString();
    }

    CreditProcessingLeadDetailDataVo leadIdVo = new CreditProcessingLeadDetailDataVo();

    leadIdVo.setLeadId(leadId);
    leadIdVo.setApplicationdate(bDate);
    leadIdVo.setMakerId(userId);

    DynaValidatorForm LeadCapturingDynaValidatorForm = (DynaValidatorForm)form;
    BeanUtils.copyProperties(leadIdVo, LeadCapturingDynaValidatorForm);

    logger.info("userobj.getBusinessdate(): " + bDate);
    leadIdVo.setApplicationdate(bDate);
    leadIdVo.setAuthorId(userId);

    String msg = "";
    Properties props = new Properties();
    props.load(request.getSession().getServletContext().getResourceAsStream("/WEB-INF/jndi.properties"));
    InitialContext ic = new InitialContext(props);
    String applName = props.getProperty("enterprise.application.name");

    PreDealDao lp = (PreDealDao)DaoImplInstanceFactory.getDaoImplInstance("PreDeal");
    LeadProcessingRemote lp1 = (LeadProcessingRemote) LookUpInstanceFactory.getLookUpInstance(LeadProcessingRemote.REMOTE_IDENTITY, request);
    leadId = CommonFunction.checkNull(request.getParameter("leadId"));
    
    String qry1="select COUNT(1) from cr_lead_customer_m WHERE DEDUPE_DECISION is null and lead_id='"+leadId+"' ";
    int dedupeCount=Integer.parseInt(ConnectionDAO.singleReturn(qry1));
    
    String qry2="select count(1) from cr_lead_customer_m WHERE IFNULL(CIBIL_DONE,'')='' and customer_type='I' and lead_id='"+leadId+"' ";
    int CibilCount=Integer.parseInt(ConnectionDAO.singleReturn(qry2));
    String qry3="select parameter_value from parameter_mst where parameter_key='CIBIL_MAND_SINGLEPAGE'";
    String rs3=ConnectionDAO.singleReturn(qry3);
    String qr2="select max(deal_id) from cr_deal_dtl where lead_id='"+leadId+"'";
    String dealId=ConnectionDAO.singleReturn(qr2);
    
    String qr3="select count(1) from cr_instrument_dtl where TXNID='"+dealId+"' and txn_type='DC' and rec_status in ('F','A','D','R')";
    int imdCount=Integer.parseInt(ConnectionDAO.singleReturn(qr3));
    String qr4="select PRODUCT from cr_lead_dtl where lead_id='"+leadId+"'";
    String product=ConnectionDAO.singleReturn(qr4);
    
    if(dedupeCount>0){
    	msg = "dedupe";
        request.setAttribute("msg", msg);
        ArrayList leadDetails = new ArrayList();
        ArrayList genderIndiv = lp.getGenderList();
        String panQuery="select PAN from cr_lead_dtl where lead_id='"+leadId+"' ";
        String pan=ConnectionDAO.singleReturn(panQuery);
        String addharQuery="select UID_NO from cr_lead_dtl where lead_id='"+leadId+"' ";
        String adhar=ConnectionDAO.singleReturn(addharQuery);
        String constQuery="select CONSTITUTION from cr_lead_dtl where lead_id='"+leadId+"' ";
        String constitution=ConnectionDAO.singleReturn(constQuery);
        leadIdVo.setIndconstitution(constitution);
        leadIdVo.setCorconstitution(constitution);
        leadIdVo.setCustPanInd(pan);
        leadIdVo.setCustPan(pan);
        leadIdVo.setAadhaar(adhar);
        request.setAttribute("GenderCategory", genderIndiv);
        String pincodeFlag = lp1.getPincodeFlag();
        logger.info("value of pincode flag is ::" + pincodeFlag);
        request.setAttribute("pincodeFlag", pincodeFlag);
        leadIdVo.setCoAppcontitutionCode(leadIdVo.getCoAppindconstitution());
        leadDetails.add(leadIdVo);
        request.setAttribute("leadDetails", leadDetails);
    }else if(CommonFunction.checkNull(product).equalsIgnoreCase("LAP") && imdCount==0 ){
    	msg = "imd";
        request.setAttribute("msg", msg);
        ArrayList leadDetails = new ArrayList();
        ArrayList genderIndiv = lp.getGenderList();
        String panQuery="select PAN from cr_lead_dtl where lead_id='"+leadId+"' ";
        String pan=ConnectionDAO.singleReturn(panQuery);
        String addharQuery="select UID_NO from cr_lead_dtl where lead_id='"+leadId+"' ";
        String adhar=ConnectionDAO.singleReturn(addharQuery);
        String constQuery="select CONSTITUTION from cr_lead_dtl where lead_id='"+leadId+"' ";
        String constitution=ConnectionDAO.singleReturn(constQuery);
        leadIdVo.setIndconstitution(constitution);
        leadIdVo.setCorconstitution(constitution);
        leadIdVo.setCustPanInd(pan);
        leadIdVo.setCustPan(pan);
        leadIdVo.setAadhaar(adhar);
        request.setAttribute("GenderCategory", genderIndiv);
        String pincodeFlag = lp1.getPincodeFlag();
        logger.info("value of pincode flag is ::" + pincodeFlag);
        request.setAttribute("pincodeFlag", pincodeFlag);
        leadIdVo.setCoAppcontitutionCode(leadIdVo.getCoAppindconstitution());
        leadDetails.add(leadIdVo);
        request.setAttribute("leadDetails", leadDetails);
    }else if(CibilCount>0 && CommonFunction.checkNull(rs3).equalsIgnoreCase("Y")){
    	msg = "Cibil";
        request.setAttribute("msg", msg);
        ArrayList leadDetails = new ArrayList();
        ArrayList genderIndiv = lp.getGenderList();
        String panQuery="select PAN from cr_lead_dtl where lead_id='"+leadId+"' ";
        String pan=ConnectionDAO.singleReturn(panQuery);
        String addharQuery="select UID_NO from cr_lead_dtl where lead_id='"+leadId+"' ";
        String adhar=ConnectionDAO.singleReturn(addharQuery);
        String constQuery="select CONSTITUTION from cr_lead_dtl where lead_id='"+leadId+"' ";
        String constitution=ConnectionDAO.singleReturn(constQuery);
        leadIdVo.setIndconstitution(constitution);
        leadIdVo.setCorconstitution(constitution);
        leadIdVo.setCustPanInd(pan);
        leadIdVo.setCustPan(pan);
        leadIdVo.setAadhaar(adhar);
        request.setAttribute("GenderCategory", genderIndiv);
        String pincodeFlag = lp1.getPincodeFlag();
        logger.info("value of pincode flag is ::" + pincodeFlag);
        request.setAttribute("pincodeFlag", pincodeFlag);
        leadIdVo.setCoAppcontitutionCode(leadIdVo.getCoAppindconstitution());
        leadDetails.add(leadIdVo);
        request.setAttribute("leadDetails", leadDetails);
    }else{
    	
    	
    boolean result = lp.saveTrackingDetails(leadIdVo);
    String st;
    /*if (leadIdVo.getDecision().trim().equalsIgnoreCase("Approved")) {*/
     // int maxId = lp.saveCPLeadEntry(leadId);
    
      st = lp.moveDataLeadToDeal(leadId, dealId);
    /*}*/

    ArrayList leadDetails = new ArrayList();
    leadDetails.add(leadIdVo);
    request.setAttribute("leadDetails", leadDetails);
    if (result) {
      msg = "LT";
      request.setAttribute("msg", msg);
    }
    	
    leadIdVo.setLbxProductID("");
    leadIdVo.setLbxBranchId("");
    leadIdVo.setSchemeId("");
    leadId = CommonFunction.checkNull(request.getParameter("leadId"));
session.removeAttribute("leadId");
    branchId = null;
    leadIdVo = null;
    lp = null;
    
    form.reset(mapping, request);
  }
    return mapping.findForward("success");
  }

  public ActionForward deleteLead(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    logger.info("In preDealCapturingDispatchAction deleteLead().... ");
    HttpSession session = request.getSession();
    UserObject userobj = (UserObject)session.getAttribute("userobject");
    String userId = "";
    String bDate = "";
    String branchId = null;
    String result = "success";
    if (userobj != null)
    {
      userId = userobj.getUserId();
      bDate = userobj.getBusinessdate();
      branchId = userobj.getBranchId();
    } else {
      logger.info("here in deleteLead () of preDealCapturingDispatchAction action the session is out----------------");
      return mapping.findForward("sessionOut");
    }
    String sessionId = session.getAttribute("sessionID").toString();

    ServletContext context = getServlet().getServletContext();
    String strFlag = "";
    if (sessionId != null)
    {
      strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId.toString(), "", request);
    }

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

    String leadId = "";

    leadId = request.getParameter("leadId");

    PreDealDao lp = (PreDealDao)DaoImplInstanceFactory.getDaoImplInstance("PreDeal");

    boolean status = lp.deletelead(leadId);
    ArrayList addresstypeList = lp.addresstype();
    request.setAttribute("addresstypeList", addresstypeList);
    if (status)
    {
      String source = "";
      ArrayList getSourceDetailList = lp.getSourceDetailList(source);

      request.setAttribute("sourceList", getSourceDetailList);

      ArrayList leadDetails = lp.getLeadCapturingDetailsList(leadId);
      request.setAttribute("leadDetails", leadDetails);
      String coappQuery = "select count(1) from cr_lead_customer_m where lead_id='" + leadId + "' and CUSTOMER_ROLE_TYPE='COAPPL' ";
      String coapp = ConnectionDAO.singleReturn(coappQuery);
      String gaurQuery = "select count(1) from cr_lead_customer_m where lead_id='" + leadId + "' and CUSTOMER_ROLE_TYPE='GUARANTOR' ";
      String gaur = ConnectionDAO.singleReturn(gaurQuery);
      if (!CommonFunction.checkNull(coapp).equalsIgnoreCase("0"))
        request.setAttribute("coAppStatus", "A");
      else {
        request.setAttribute("coAppStatus", "X");
      }
      if (!CommonFunction.checkNull(gaur).equalsIgnoreCase("0"))
        request.setAttribute("gaurStatus", "A");
      else {
        request.setAttribute("gaurStatus", "X");
      }
      request.setAttribute("deletelead", "delete");
    }
    else
    {
      String source = "";
      ArrayList getSourceDetailList = lp.getSourceDetailList(source);

      request.setAttribute("sourceList", getSourceDetailList);

      ArrayList leadDetails = lp.getLeadCapturingDetailsList(leadId);
      request.setAttribute("leadDetails", leadDetails);
      String coappQuery = "select count(1) from cr_lead_customer_m where lead_id='" + leadId + "' and CUSTOMER_ROLE_TYPE='COAPPL' ";
      String coapp = ConnectionDAO.singleReturn(coappQuery);
      String gaurQuery = "select count(1) from cr_lead_customer_m where lead_id='" + leadId + "' and CUSTOMER_ROLE_TYPE='GUARANTOR' ";
      String gaur = ConnectionDAO.singleReturn(gaurQuery);
      if (!CommonFunction.checkNull(coapp).equalsIgnoreCase("0"))
        request.setAttribute("coAppStatus", "A");
      else {
        request.setAttribute("coAppStatus", "X");
      }
      if (!CommonFunction.checkNull(gaur).equalsIgnoreCase("0"))
        request.setAttribute("gaurStatus", "A");
      else {
        request.setAttribute("gaurStatus", "X");
      }
    }

    branchId = null;
    lp = null;
    return mapping.findForward(result);
  }

  public ActionForward searchCustomer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    HttpSession session = request.getSession();
    UserObject userobj = (UserObject)session.getAttribute("userobject");
    String bDate = userobj.getBusinessdate();

    PreDealDao lp = (PreDealDao)DaoImplInstanceFactory.getDaoImplInstance("PreDeal");

    String source = "";

    String customerId = request.getParameter("customerId");
    String addressId = request.getParameter("addressId");

    ArrayList leadDetails = lp.CustomerDetailsList(customerId, addressId, bDate);
    request.setAttribute("leadDetails", leadDetails);
    lp = null;
    return mapping.findForward("ajaxsuccesslead");
  }

  public ActionForward searchDecisionDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    PreDealDao lp = (PreDealDao)DaoImplInstanceFactory.getDaoImplInstance("PreDeal");

    String source = "";
    String LeadId = request.getParameter("leadId");
    CreditProcessingLeadDetailDataVo vo = new CreditProcessingLeadDetailDataVo();

    ArrayList leadDetailsView = lp.getDecisionList(LeadId);
    ArrayList leadDetails = lp.getLeadCapturingDetailsList(LeadId);
    String coappQuery = "select count(1) from cr_lead_customer_m where lead_id='" + LeadId + "' and CUSTOMER_ROLE_TYPE='COAPPL' ";
    String coapp = ConnectionDAO.singleReturn(coappQuery);
    String gaurQuery = "select count(1) from cr_lead_customer_m where lead_id='" + LeadId + "' and CUSTOMER_ROLE_TYPE='GUARANTOR' ";
    String gaur = ConnectionDAO.singleReturn(gaurQuery);
    if (!CommonFunction.checkNull(coapp).equalsIgnoreCase("0"))
      request.setAttribute("coAppStatus", "A");
    else {
      request.setAttribute("coAppStatus", "X");
    }
    if (!CommonFunction.checkNull(gaur).equalsIgnoreCase("0"))
      request.setAttribute("gaurStatus", "A");
    else {
      request.setAttribute("gaurStatus", "X");
    }
    request.setAttribute("leadDetails", leadDetails);
    request.setAttribute("leadDetailsView", leadDetailsView);
    lp = null;
    return mapping.findForward("decisionSuccess");
  }

	public ActionForward fetchCustDtl(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
	    UserObject userobj = (UserObject)session.getAttribute("userobject");
	    String sessionId = session.getAttribute("sessionID").toString();
		PreDealDao lp = (PreDealDao) DaoImplInstanceFactory.getDaoImplInstance("PreDeal");
		LeadProcessingRemote lp1 = (LeadProcessingRemote) LookUpInstanceFactory.getLookUpInstance(LeadProcessingRemote.REMOTE_IDENTITY, request);

		String source = "";
		String LeadId = request.getParameter("leadId");
		String customerId = request.getParameter("customerId");
		String customerType = CommonFunction.checkNull(request.getParameter("custType"));

		CreditProcessingLeadDetailDataVo vo = new CreditProcessingLeadDetailDataVo();

		ArrayList<CreditProcessingLeadDetailDataVo> leadDetails = lp.getMulCustList(LeadId, customerId,customerType);
		request.setAttribute("leadDetails", leadDetails);
		String pincodeFlag = lp1.getPincodeFlag();
        logger.info("value of pincode flag is ::" + pincodeFlag);
        request.setAttribute("pincodeFlag", pincodeFlag);
        String query="SELECT COUNT(1) FROM CR_CIBIL_REF_DTL WHERE  CIBIL_RESULT='S' AND LEAD_ID='"+LeadId+"' AND CUSTOMER_ID='"+customerId+"' ;";
        int count=Integer.parseInt(ConnectionDAO.singleReturn(query));
        if(count>0){
        	request.setAttribute("cibilDone", "cibilDone");
        }
        String query1="select EXISTING_CUSTOMER from cr_lead_customer_m where customer_id='"+customerId+"' ";
        String existingCustomer=ConnectionDAO.singleReturn(query1);
        if(CommonFunction.checkNull(existingCustomer).equalsIgnoreCase("Y")){
        request.setAttribute("existingCustomer", existingCustomer);
        }
	    ArrayList<CreditProcessingLeadDetailDataVo> coappDetails = lp.getCoappDetailsList(LeadId,customerType);
		request.setAttribute("coappDetails", coappDetails);
		if(customerType.equalsIgnoreCase("COAPPL")){
		request.setAttribute("custType", leadDetails.get(0).getCoAppcustomerType());
		}else if(customerType.equalsIgnoreCase("GUARANTOR")){
		request.setAttribute("custType", leadDetails.get(0).getGaurcustomerType());
		}
		
		lp = null;
		return mapping.findForward("success");
	}
	
	public ActionForward deleteCustomer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
		    throws Exception
		  {
		    logger.info("In preDealCapturingDispatchAction deleteLead().... ");
		    HttpSession session = request.getSession();
		    UserObject userobj = (UserObject)session.getAttribute("userobject");
		    String userId = "";
		    String bDate = "";
		    String branchId = null;
		    String result = "success";
		    if (userobj != null)
		    {
		      userId = userobj.getUserId();
		      bDate = userobj.getBusinessdate();
		      branchId = userobj.getBranchId();
		    } else {
		      logger.info("here in deleteLead () of preDealCapturingDispatchAction action the session is out----------------");
		      return mapping.findForward("sessionOut");
		    }
		    String sessionId = session.getAttribute("sessionID").toString();

		    ServletContext context = getServlet().getServletContext();
		    String strFlag = "";
		    if (sessionId != null)
		    {
		      strFlag = UserSessionCheck.checkSameUserSession(userobj, sessionId.toString(), "", request);
		    }

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

		    String leadId = "";
		    boolean status = false;

		    leadId = request.getParameter("leadId");
		    String custType=CommonFunction.checkNull(request.getParameter("custType"));

		    PreDealDao lp = (PreDealDao)DaoImplInstanceFactory.getDaoImplInstance("PreDeal");
		    LeadProcessingRemote lp1 = (LeadProcessingRemote) LookUpInstanceFactory.getLookUpInstance(LeadProcessingRemote.REMOTE_IDENTITY, request);
		    String customerName = "";
		    String[] customer_id = null;
		    if (request.getParameterValues("chk") != null)
		    {
		    	customer_id = request.getParameterValues("chk");
		      session.setAttribute("customer_id", customer_id);
		    }
		    else
		    {
		      customer_id = (String[])session.getAttribute("customer_id");
		      session.removeAttribute("customer_id");
		    }
		    
		    String confirmStatus = request.getParameter("confirmStatus");
		    logger.info("confirmStatus: " + confirmStatus);
		    int count=0;
		    String query="";
		    for (int k = 0; k < customer_id.length; k++)
		      {
		     query="SELECT COUNT(1) FROM CR_CIBIL_REF_DTL WHERE  CIBIL_RESULT='S' AND LEAD_ID='"+leadId+"' AND CUSTOMER_ID='"+customer_id[k]+"' ;";
	         int count1=Integer.parseInt(ConnectionDAO.singleReturn(query));
	         if(count1>0){
	        	 count=count1; 
	         }
		      }
	        logger.info("check Cibil Done Query:  "+query );
	        logger.info("check Cibil Done :  "+count );
	        if(count>0){
	        	request.setAttribute("msg", "L");
	        }else{
		    if (CommonFunction.checkNull(confirmStatus).equalsIgnoreCase("N"))
		    {
		    	status = lp.deleteCustomer(customer_id,leadId);
		    }
		    if (status)
		      {
		    	 request.setAttribute("msg", "S");
		      }
		    else{
		        request.setAttribute("msg", "N");
		      }
	        }
		    String pincodeFlag = lp1.getPincodeFlag();
	          logger.info("value of pincode flag is ::" + pincodeFlag);
	          request.setAttribute("pincodeFlag", pincodeFlag);
		    ArrayList<CreditProcessingLeadDetailDataVo> coappDetails = lp.getCoappDetailsList(leadId,custType);
			session.setAttribute("coappDetails", coappDetails);
			ArrayList leadDetails = lp.getLeadHeader(leadId);
			session.setAttribute("leadDetails", leadDetails);
			lp = null;
			return mapping.findForward("success");
		  }
	
	
}